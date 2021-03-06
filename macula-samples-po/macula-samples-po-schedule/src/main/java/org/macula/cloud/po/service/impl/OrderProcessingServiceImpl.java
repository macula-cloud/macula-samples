package org.macula.cloud.po.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.macula.cloud.api.context.CloudApplicationContext;
import org.macula.cloud.api.protocol.ExecuteResponse;
import org.macula.cloud.core.annotation.RedisLock;
import org.macula.cloud.core.annotation.RedisLock.LockFailedPolicy;
import org.macula.cloud.core.utils.SystemUtils;
import org.macula.cloud.log.annotation.ServiceInvokeProxy;
import org.macula.cloud.po.configure.OMSConfig;
import org.macula.cloud.po.domain.PoAddrDetail;
import org.macula.cloud.po.domain.PoHeader;
import org.macula.cloud.po.domain.PoMaster;
import org.macula.cloud.po.domain.PoProcessDetail;
import org.macula.cloud.po.domain.PoProcessMaster;
import org.macula.cloud.po.domain.PosAccTranDetail;
import org.macula.cloud.po.event.OrderProcessFailedEvent;
import org.macula.cloud.po.event.OrderProcessRequestEvent;
import org.macula.cloud.po.event.OrderProcessSuccessEvent;
import org.macula.cloud.po.exception.OMSException;
import org.macula.cloud.po.feign.GbssOpenApiClient;
import org.macula.cloud.po.repository.MkpDlpPoRelateRepository;
import org.macula.cloud.po.repository.PoAddrDetailRepository;
import org.macula.cloud.po.repository.PoDetailDiscountRepository;
import org.macula.cloud.po.repository.PoDetailRepository;
import org.macula.cloud.po.repository.PoHeaderRepository;
import org.macula.cloud.po.repository.PoInvoiceInfoRepository;
import org.macula.cloud.po.repository.PoMasterRepository;
import org.macula.cloud.po.repository.PoPaymentDetailRepository;
import org.macula.cloud.po.repository.PoProcessDetailRepository;
import org.macula.cloud.po.repository.PoProcessMasterRepository;
import org.macula.cloud.po.repository.PosAccTranDetailRepository;
import org.macula.cloud.po.sap.bapi.DealerOrderZrebBapi;
import org.macula.cloud.po.service.OrderPackageService;
import org.macula.cloud.po.service.OrderProcessControlService;
import org.macula.cloud.po.service.OrderProcessingService;
import org.macula.cloud.po.type.PoProcessSource;
import org.macula.cloud.po.type.PoProcessStatus;
import org.macula.cloud.po.type.PoStatusConstants;
import org.macula.cloud.po.vo.DealerOrderVo;
import org.macula.cloud.po.vo.OrderUploadMessage;
import org.macula.cloud.po.vo.PoResultDetailVo;
import org.macula.cloud.po.vo.PoStatusChange;
import org.macula.cloud.po.vo.StatusChangeRequest;
import org.macula.cloud.po.vo.StatusStartRequest;
import org.macula.cloud.sap.SAPExecution;
import org.springframework.stereotype.Service;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class OrderProcessingServiceImpl implements OrderProcessingService {

	private PoProcessMasterRepository poProcessMasterRepository;
	private PoProcessDetailRepository poProcessDetailRepository;
	private PoMasterRepository poMasterRepository;
	private PoDetailRepository poDetailRepository;
	private PoDetailDiscountRepository poDetailDiscountRepository;
	private PosAccTranDetailRepository posAccTranDetailRepository;
	private PoPaymentDetailRepository poPaymentDetailRepository;
	private MkpDlpPoRelateRepository mkpDlpPoRelateRepository;
	private PoInvoiceInfoRepository poInvoiceInfoRepository;
	private PoAddrDetailRepository poAddrDetailRepository;
	private PoHeaderRepository poHeaderRepository;
	private OrderPackageService orderPackageService;
	private OrderProcessControlService processControlService;
	private GbssOpenApiClient gbssOpenApiClient;
	private SAPExecution jcoExecution;
	private Producer producer;
	private OMSConfig config;

	private static ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 0.??????????????????
	 */
	@Override
	@RedisLock(prefix = "oms:processing", value = "#poNo", expireTime = 30000, action = LockFailedPolicy.GIVEUP)
	public Boolean handleStartRequest(String poNo, StatusStartRequest request) {
		if (log.isInfoEnabled()) {
			log.info(" === HandleProcessRequest -> {} ", request);
		}
		PoProcessMaster poProcessMaster = poProcessMasterRepository.findByPoNo(poNo);

		if (poProcessMaster == null) {
			poProcessMaster = new PoProcessMaster();
			poProcessMaster.setPoNo(poNo);
		}
		String currentStatus = poProcessMaster == null ? null : poProcessMaster.getStatus();
		String requestStatus = request.getStatus();
		boolean shouldChange = PoProcessStatus.shouldStartRequest(currentStatus, requestStatus);
		if (shouldChange) {
			poProcessMaster.setStatus(request.getStatus());
			poProcessMaster.setStatusTime(request.getStatusTime());
			poProcessMaster.setComments(request.getComments());
		}
		String poSource = poProcessMaster.getPoSource();
		if (poSource == null || PoProcessSource.shouldUpdate(request.getPoSource())) {
			poProcessMaster.setPoSource(request.getPoSource());
		}
		if (request.getRefSourceId() != null) {
			poProcessMaster.setRefSourceId(request.getRefSourceId());
		}
		if (request.getRefSourceNo() != null) {
			poProcessMaster.setRefSourceNo(request.getRefSourceNo());
		}
		if (request.getPoProcessCode() != null) {
			poProcessMaster.setPoProcessCode(request.getPoProcessCode());
		}
		poProcessMasterRepository.saveAndFlush(poProcessMaster);
		PoProcessDetail detail = new PoProcessDetail();
		detail.setPoNo(poNo);
		detail.setProcState(request.getStatus());
		detail.setProcTime(request.getStatusTime());
		detail.setComments(request.getComments());
		poProcessDetailRepository.save(detail);
		return shouldChange;
	}

	/**
	 * 1.??????????????????
	 */
	@Override
	@RedisLock(prefix = "oms:processing", value = "#poNo", expireTime = 30000, action = LockFailedPolicy.GIVEUP)
	public Boolean handleStatusChange(String poNo, StatusChangeRequest request) {
		if (log.isInfoEnabled()) {
			log.info(" === HandleStatusChange -> {} ", request.toString());
		}
		PoProcessMaster poProcessMaster = poProcessMasterRepository.findByPoNo(poNo);
		if (poProcessMaster == null) {
			return false;
		}
		if (request.getPoProcessCode() != null) {
			poProcessMaster.setPoProcessCode(request.getPoProcessCode());
		}
		poProcessMaster.setStatus(request.getStatus());
		poProcessMaster.setStatusTime(request.getStatusTime());
		if (PoProcessStatus.hasError(request.getStatus()) && !PoProcessStatus.hasPause(request.getStatus())) {
			poProcessMaster.setErrorTimes(poProcessMaster.getErrorTimes() + 1);
		}
		String comments = request.getComments();
		if (!PoProcessStatus.hasPause(request.getStatus())) {
			poProcessMaster.setComments(comments);
		}
		if (request.getResultId() != null) {
			poProcessMaster.setSendMessageId(request.getResultId());
			comments += request.getResultId();
		}
		poProcessMaster = poProcessMasterRepository.saveAndFlush(poProcessMaster);
		PoProcessDetail detail = new PoProcessDetail();
		detail.setPoNo(poNo);
		detail.setProcState(request.getStatus());
		detail.setProcTime(request.getStatusTime());
		detail.setComments(comments);
		poProcessDetailRepository.save(detail);
		return true;
	}

	/**
	 * ?????????????????????????????????PoResultDetailVo
	 */
	@Override
	public PoResultDetailVo loadLocalPoResultDetailVo(String poNo) {
		if (log.isDebugEnabled()) {
			log.debug("1. loadLocalPoResultDetailVo:{}, ", poNo);
		}
		PoResultDetailVo vo = new PoResultDetailVo();
		// 1?????????????????????
		PoMaster poMaster = poMasterRepository.findByPoNo(poNo);
		vo.setPoMaster(poMaster);
		// 2:??????????????????PoDetail
		vo.setPoDetails(poDetailRepository.findByPoNoOrderByLineNoAsc(poNo));
		// 3:??????????????????
		vo.setPoDetailDiscounts(poDetailDiscountRepository.findByPoNo(poNo));
		// 4???????????????????????????/????????????
		List<PosAccTranDetail> posAccTranDetailList = posAccTranDetailRepository.findByPosTranDateAndPosStoreNoAndRefDocNo(poMaster.getPoDate(),
				poMaster.getPoStoreNo(), poMaster.getPoNo());
		vo.setPosAccTranDetails(posAccTranDetailList);
		// 5?????????????????????
		vo.setPoPaymentDetails(poPaymentDetailRepository.findByPoNo(poNo));
		// 6:???????????????
		vo.setMkpDlpPoRelate(mkpDlpPoRelateRepository.findByPoNo(poNo));
		// 7:????????????
		vo.setPoInvoiceInfo(poInvoiceInfoRepository.findByPoAppNo(vo.getPoMaster().getPoAppNo()));
		// 8:??????????????????
		vo.setPoAddrDetail(poAddrDetailRepository.findByPoNo(poNo));
		// 9:??????SAP???????????????????????????
		vo.setPoHeader(poHeaderRepository.findByPoNo(poNo));
		return vo;
	}

	/**
	 * 2.?????????????????????PoResultDetailVo
	 */
	@Override
	public PoResultDetailVo loadGbssPoResultDetailVo(String poNo) {
		if (log.isDebugEnabled()) {
			log.debug("2. loadGbssPoResultDetailVo:{}, ", poNo);
		}
		ExecuteResponse<PoResultDetailVo> gbssOrderResultDetail = gbssOpenApiClient.getGbssOrderResultDetail(poNo);
		if (gbssOrderResultDetail == null || !gbssOrderResultDetail.isSuccess()) {
			throw new OMSException("gbssOpenApiClient.getGbssOrderResultDetail error: "
					+ (gbssOrderResultDetail == null ? null : gbssOrderResultDetail.getExceptionMessage()));
		}
		PoResultDetailVo returnObject = gbssOrderResultDetail.getReturnObject();
		return returnObject;
	}

	/**
	 * 3.?????????????????????????????????PoResultDetailVo??????????????????
	 */
	@Override
	public PoResultDetailVo persistancePoResultDetailVo(PoResultDetailVo vo) {
		if (log.isDebugEnabled()) {
			log.debug("3. persistancePoResultDetailVo:{}, ", vo.getPoMaster().getPoNo());
		}
		// 1?????????????????????
		if (vo.getPoMaster() != null) {
			poMasterRepository.save(vo.getPoMaster());
		}
		// 2:??????????????????PoDetail
		if (vo.getPoDetails() != null) {
			poDetailRepository.saveAll(vo.getPoDetails());
		}
		// 3:??????????????????
		if (vo.getPoDetailDiscounts() != null) {
			poDetailDiscountRepository.saveAll(vo.getPoDetailDiscounts());
		}
		// 4???????????????????????????/????????????
		if (vo.getPosAccTranDetails() != null) {
			posAccTranDetailRepository.saveAll(vo.getPosAccTranDetails());
		}
		// 5?????????????????????
		if (vo.getPoPaymentDetails() != null) {
			poPaymentDetailRepository.saveAll(vo.getPoPaymentDetails());
		}
		// 6:???????????????
		if (vo.getMkpDlpPoRelate() != null) {
			mkpDlpPoRelateRepository.save(vo.getMkpDlpPoRelate());
		}
		// 7:????????????
		if (vo.getPoInvoiceInfo() != null) {
			poInvoiceInfoRepository.save(vo.getPoInvoiceInfo());
		}
		// 8:??????????????????
		if (vo.getPoAddrDetail() != null) {
			poAddrDetailRepository.save(vo.getPoAddrDetail());
		}
		// 9:??????SAP???????????????????????????
		if (vo.getPoHeader() != null) {
			PoMaster m = vo.getPoMaster();
			PoHeader poHeader = vo.getPoHeader();
			m.cloneId(poHeader);
			poHeader.setPoNo(m.getPoNo());
			poHeader.setOrderDealerNo(m.getOrderDealerNo());
			poHeader.setCustomerDealerNo(m.getOrderCustomerNo());
			poHeader.setDeliveryDealerNo(vo.getPoAddrDetail().getDeliveryDealerNo());
			poHeader.setLastUpdatedBy(vo.getPoMaster().getLastUpdatedBy());
			poHeader.setLastUpdatedTime(vo.getPoMaster().getLastUpdatedTime());
			poHeaderRepository.save(poHeader);
		}
		return vo;
	}

	/**
	 * 4.???PoResultDetailVo?????????SAP????????????
	 */
	@Override
	public DealerOrderZrebBapi translatePoResultDetailVo2Bapi(String poNo, PoResultDetailVo vo) {
		if (log.isDebugEnabled()) {
			log.debug("4. translatePoResultDetailVo2Bapi:{}, ", vo.getPoMaster().getPoNo());
		}
		DealerOrderVo dealerOrderVo = orderPackageService.packageSapOrderVo(poNo, vo);
		DealerOrderZrebBapi bapi = new DealerOrderZrebBapi();
		bapi.setPOrderHeader(dealerOrderVo.getPOrderHeader());
		bapi.setTOrderItemsList(dealerOrderVo.gettOrderItemsList());
		bapi.setTConditionList(dealerOrderVo.gettConditionList());
		return bapi;
	}

	/**
	 *5. ??????????????????SAP??????
	 */
	@Override
	public DealerOrderZrebBapi executeUploadBapi(String poNo, DealerOrderZrebBapi bapi) {
		if (log.isDebugEnabled()) {
			log.debug("5. executeUploadBapi:{}, ", poNo);
		}
		String prefix = StringUtils.trim(config.getSapMock());
		if (StringUtils.isNotBlank(prefix) && StringUtils.length(prefix) == 1) {
			if (log.isErrorEnabled()) {
				log.error(" Mock SAP PoNo:{}, ", prefix + bapi.getPOrderHeader().getPurchNoC());
			}
			bapi.getPOrderHeader().setPurchNoC(prefix + bapi.getPOrderHeader().getPurchNoC());
		}
		return jcoExecution.execute(poNo, bapi);
	}

	/**
	 * 6.????????????SAP?????????
	 */
	@Override
	public boolean executeLocaleResultCallback(String poNo, String sapPostingDocNo) {
		if (log.isDebugEnabled()) {
			log.debug("6. sendBapiResultCallback:{}, ", poNo);
		}
		PoMaster poMaster = poMasterRepository.findByPoNo(poNo);
		poMaster.setSapPostingDate(SystemUtils.getCurrentTime());
		poMaster.setSapPostingDocNo(sapPostingDocNo);
		poMaster.setSapPostingFlag("Y");
		poMasterRepository.save(poMaster);

		PoProcessMaster poProcessMaster = poProcessMasterRepository.findByPoNo(poNo);
		if (poProcessMaster != null) {
			poProcessMaster.setSalesDocument(sapPostingDocNo);
			poProcessMasterRepository.save(poProcessMaster);
		}

		return true;
	}

	/**
	 * 7. ??????GBSS??????SAP??????????????????MQ?????????
	 */
	@Override
	public String executeGbssResultCallback(String poNo, OrderUploadMessage uploadResult) {
		PoMaster poMaster = poMasterRepository.findByPoNo(poNo);
		if (poMaster == null) {
			throw new OMSException(String.format("PoMaster %s is null  !", poNo));
		}

		String poProcessCode = poMaster.getPoProcessCode();
		if (StringUtils.isNotBlank(poMaster.getSapPostingDocNo())) {
			uploadResult.updateSuccess(poMaster.getSapPostingDocNo(), poMaster.getSapPostingDate());
		}
		byte[] messageContent;
		try {
			messageContent = objectMapper.writeValueAsBytes(uploadResult);
		} catch (JsonProcessingException e) {
			throw new OMSException("OrderUploadMessage translate to json error!", e);
		}
		Message message = new Message(config.getCallbackTopic(), poProcessCode, poNo, messageContent);
		SendResult result = producer.send(message);
		String messageId = result.getMessageId();
		if (StringUtils.isNotEmpty(messageId)) {
			PoProcessMaster poProcessMaster = poProcessMasterRepository.findByPoNo(poNo);
			if (poProcessMaster != null) {
				poProcessMaster.setSendMessageId(messageId);
				poProcessMasterRepository.save(poProcessMaster);
			}
		}
		return messageId;
	}

	/**
	 * 8. GBSS?????????????????????OMS
	 */
	@Override
	@ServiceInvokeProxy(key = "args[0]", description = "'??????????????????'", source = "'MQ'", sourceMessage = "args[1]", target = "'OMS'", targetMethod = "'handlePoStatusChange'")
	public PoStatusChange handlePoStatusChange(String poNo, PoStatusChange poStatusChange) {
		//PoMaster poMaster = poMasterRepository.findByPoNo(poStatusChange.getPoNo());
		PoMaster poMaster = poMasterRepository.findByPoNo(poNo);
		String sapPostingDocNo = null;
		// 1:???????????????????????????OMS,?????????????????????
		if (poMaster != null) {
			poStatusChange.setOldStatus(poMaster.getPoStatus());
			poMaster.setPoStatus(poStatusChange.getPoStatus());
			poMasterRepository.save(poMaster);
			sapPostingDocNo = poMaster.getSapPostingDocNo();

			// ??????????????????????????????
			PoMaster byRefSelectedNo = poMasterRepository.findByRefSelectedNo(poNo);
			// ???????????????,???????????????????????????
			if (byRefSelectedNo != null) {
				byRefSelectedNo.setPoStatus(poStatusChange.getPoStatus());
				poMasterRepository.save(byRefSelectedNo);
				// ??????????????????????????????????????????????????????
				String comments = String.format("?????????????????? %s -> %s ???%s", poStatusChange.getOldStatus(), poStatusChange.getPoStatus(), poStatusChange);
				String processStatus = PoProcessStatus.CHANGED;
				handleStatusChange(byRefSelectedNo.getPoNo(), StatusChangeRequest.builder().poNo(byRefSelectedNo.getPoNo()).status(processStatus)
						.statusTime(SystemUtils.getCurrentTime()).comments(comments).build());
				if (sapPostingDocNo == null) {
					CloudApplicationContext.getContainer().publishEvent(new OrderProcessRequestEvent(byRefSelectedNo.getPoNo()));
				}
			}
		} else { // 2:?????????????????????????????????,????????????????????????????????????,???GBSS????????????
			// ???????????????poNo,???GBSS?????????????????????????????????????????????????????????????????????OMS
			PoResultDetailVo poResultDetailVo = loadGbssPoResultDetailVo(poNo);
			// ??????????????????????????????
			persistancePoResultDetailVo(poResultDetailVo);
		}
		// ????????????po_addr_detail???????????????
		changePoAddrDetail(poNo);
		// ??????????????????????????????????????????????????????
		String comments = String.format("?????????????????? %s -> %s ???%s", poStatusChange.getOldStatus(), poStatusChange.getPoStatus(), poStatusChange);
		String processStatus = PoProcessStatus.CHANGED;
		handleStatusChange(poNo,
				StatusChangeRequest.builder().poNo(poNo).status(processStatus).statusTime(SystemUtils.getCurrentTime()).comments(comments).build());
		if (sapPostingDocNo == null) {
			CloudApplicationContext.getContainer().publishEvent(new OrderProcessRequestEvent(poNo));
		}
		return poStatusChange;
	}

	/**
	 * GBSS?????????????????????OMS???,????????????????????????????????????
	 */
	@Override
	public void changePoAddrDetail(String poNo) {
		// ???????????????poNo,???GBSS??????????????????????????????????????????????????????
		PoResultDetailVo poResultDetailVo = loadGbssPoResultDetailVo(poNo);
		// ??????????????????????????????
		PoAddrDetail poAddrDetail = poResultDetailVo.getPoAddrDetail();
		if (poAddrDetail != null) {
			// ?????????OMS?????????????????????
			PoAddrDetail byPoNo = poAddrDetailRepository.findByPoNo(poNo);
			if (byPoNo != null) {
				poAddrDetail.setId(byPoNo.getId());
				// ???OMS??????????????????????????????
				poAddrDetailRepository.save(poAddrDetail);
			}
		}
	}

	/**
	 * ??????PoProcessMaster??????
	 */
	@Override
	public PoProcessMaster getProcessMaster(String poNo) {
		return poProcessMasterRepository.findByPoNo(poNo);
	}

	@Override
	@RedisLock(prefix = "oms:processing", value = "#poNo", expireTime = 30000, action = LockFailedPolicy.GIVEUP)
	public void doProcessingLogic(String poNo) {
		if (log.isInfoEnabled()) {
			log.info("Processing doProcessingLogic: {}", poNo);
		}

		try {
			doProcessInternal(poNo);
		} catch (Exception ex) {
			String message = String.format("??????[ %s ] ????????????%s ,  %s", poNo, ex.getMessage(), ExceptionUtils.getRootCauseMessage(ex));
			log.error(message);
			StatusChangeRequest statusRequest = StatusChangeRequest.builder().poNo(poNo).statusTime(SystemUtils.getCurrentTime())
					.status(PoProcessStatus.ERROR).comments(StringUtils.substring(message, 0, 255)).build();
			handleStatusChange(poNo, statusRequest);
			// ??????????????????
			CloudApplicationContext.getContainer().publishEvent(new OrderProcessFailedEvent(poNo));
		}
	}

	// ??????poNo?????????????????????po??????
	protected void doProcessInternal(String poNo) throws OMSException {
		PoResultDetailVo vo = null;
		String stepErrorMessage = null;
		String poProcessCode = null;
		String omsPostingDocNo = null;
		try {
			PoProcessMaster poProcessMaster = getProcessMaster(poNo);
			if (poProcessMaster == null || !PoProcessStatus.hasRequest(poProcessMaster.getStatus())) {
				log.error("Po {} needn't process???", poNo);
				return;
			}
			stepErrorMessage = "???GBSS?????????????????????";
			vo = loadGbssPoResultDetailVo(poNo);
			if (vo != null && vo.getPoMaster() != null) {
				poProcessCode = vo.getPoMaster().getPoProcessCode();
			}
			handleStatusChange(poNo, StatusChangeRequest.builder().poNo(poNo).statusTime(SystemUtils.getCurrentTime())
					.status(PoProcessStatus.PO_GET_GBSS_DATA).poProcessCode(poProcessCode).comments("???GBSS?????????????????????").build());
			stepErrorMessage = "???????????????????????????";
			// ?????????GBSS????????????????????????OMS
			persistancePoResultDetailVo(vo);
			// ???????????????????????????
			handleStatusChange(poNo, StatusChangeRequest.builder().poNo(poNo).statusTime(SystemUtils.getCurrentTime())
					.status(PoProcessStatus.PO_SAVE_LOCAL).comments(" ???????????????????????????").build());

			stepErrorMessage = "?????????????????????????????????";
			vo = (vo == null) ? loadLocalPoResultDetailVo(poNo) : vo;
			if (vo == null || vo.getPoMaster() == null || !Arrays.asList(PoStatusConstants.PO_STATUS_NEW).contains(vo.getPoMaster().getPoStatus())) {
				handleStatusChange(poNo, StatusChangeRequest.builder().poNo(poNo).statusTime(SystemUtils.getCurrentTime())
						.status(PoProcessStatus.CHANGED).comments("????????????????????????????????????").build());
				return;
			}

			// ??????????????????SAP??????
			if (!processControlService.shouldSAPUploadProcessing()) {
				handleStatusChange(poNo, StatusChangeRequest.builder().poNo(poNo).statusTime(SystemUtils.getCurrentTime())
						.status(PoProcessStatus.ERROR).comments("??????SAP?????????????????????????????????").build());
				log.error("== SHOULD NOT DO SAP UPLOAD  ==");
				log.error("Po {} ????????????????????????SAP", poNo);
				return;
			}

			stepErrorMessage = "??????SAP???????????????";
			DealerOrderZrebBapi bapi = translatePoResultDetailVo2Bapi(poNo, vo);
			handleStatusChange(poNo, StatusChangeRequest.builder().poNo(poNo).statusTime(SystemUtils.getCurrentTime())
					.status(PoProcessStatus.PO_DATA_PACKAGE).comments("??????SAP???????????????").build());
			stepErrorMessage = "??????SAP???????????????";
			bapi = executeUploadBapi(poNo, bapi);
			if (bapi.getPoHeader() != null) {
				omsPostingDocNo = bapi.getPoHeader().getSalesdocument();
			}

			if (StringUtils.isEmpty(omsPostingDocNo)) {
				stepErrorMessage = bapi.getErrorMessage();
				stepErrorMessage = StringUtils.mid(stepErrorMessage, 0, 255);
				throw new OMSException(stepErrorMessage);
			}
			handleStatusChange(poNo, StatusChangeRequest.builder().poNo(poNo).statusTime(SystemUtils.getCurrentTime())
					.status(PoProcessStatus.PO_UPLOAD_SAP).comments("??????SAP???????????????").build());

			if (!StringUtils.isEmpty(omsPostingDocNo)) {
				stepErrorMessage = "????????????SAP???????????????";
				executeLocaleResultCallback(poNo, omsPostingDocNo);
				handleStatusChange(poNo, StatusChangeRequest.builder().poNo(poNo).statusTime(SystemUtils.getCurrentTime())
						.status(PoProcessStatus.PO_SAP_REWRITEN).comments("????????????SAP??????" + omsPostingDocNo + "?????????").build());
			}

			stepErrorMessage = "????????????SAP?????????MQ?????????";
			String messageId = executeGbssResultCallback(poNo, new OrderUploadMessage(poNo));
			handleStatusChange(poNo, StatusChangeRequest.builder().poNo(poNo).statusTime(SystemUtils.getCurrentTime()).status(PoProcessStatus.SUCCESS)
					.resultId(messageId).comments("????????????SAP??????" + omsPostingDocNo + "???MQ?????????").build());
			// ??????????????????
			CloudApplicationContext.getContainer().publishEvent(new OrderProcessSuccessEvent(poNo));
		} catch (Exception ex) {
			String errorMessage = stepErrorMessage;
			String messageId = executeGbssResultCallback(poNo,
					new OrderUploadMessage(poNo).updateFailed(StringUtils.substring(errorMessage, 0, 255)));
			if (!(ex instanceof OMSException)) {
				errorMessage += " -> " + ExceptionUtils.getRootCauseMessage(ex);
			}
			throw new OMSException(errorMessage + " \r\nMQ-ID:" + messageId, ex);
		}
	}
}
