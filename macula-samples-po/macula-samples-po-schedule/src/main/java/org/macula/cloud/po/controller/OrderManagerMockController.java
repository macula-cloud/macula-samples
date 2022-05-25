package org.macula.cloud.po.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.macula.cloud.api.context.CloudApplicationContext;
import org.macula.cloud.api.protocol.ExecuteResponse;
import org.macula.cloud.core.utils.SystemUtils;
import org.macula.cloud.po.domain.PoCheckDetail;
import org.macula.cloud.po.domain.PoCheckMaster;
import org.macula.cloud.po.domain.PoMaster;
import org.macula.cloud.po.event.OrderProcessRequestEvent;
import org.macula.cloud.po.feign.GbssOpenApiClient;
import org.macula.cloud.po.repository.PoMasterRepository;
import org.macula.cloud.po.sap.bapi.DealerOrderZrebBapi;
import org.macula.cloud.po.sap.bapi.SapOrderStateBapi;
import org.macula.cloud.po.sap.model.SapOrderStateItem;
import org.macula.cloud.po.service.OrderCheckingService;
import org.macula.cloud.po.service.OrderProcessingService;
import org.macula.cloud.po.service.OrderScheduleService;
import org.macula.cloud.po.type.PoProcessStatus;
import org.macula.cloud.po.vo.DealerOrderVo;
import org.macula.cloud.po.vo.PoResultDetailVo;
import org.macula.cloud.po.vo.PoStatusChange;
import org.macula.cloud.po.vo.StatusStartRequest;
import org.macula.cloud.sap.SAPExecution;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/oms/manage")
@Slf4j
@AllArgsConstructor
public class OrderManagerMockController {
	private SAPExecution jcoExecution;
	//	private OrderPackageService orderPackageService;
	private OrderProcessingService orderProcessingService;
	private PoMasterRepository dsPoMasterRepository;
	private OrderCheckingService orderCheckingService;
	private OrderScheduleService orderScheduleService;
	private GbssOpenApiClient gbssOpenApiClient;

	@GetMapping("/order")
	public ResponseEntity<?> executeSAPFunction(String poNo) {
		SapOrderStateBapi bapi = new SapOrderStateBapi();
		SapOrderStateItem item = new SapOrderStateItem();
		item.setPurchNoC(poNo);
		bapi.setSapOrderStateItemList(Arrays.asList(item));
		jcoExecution.execute(poNo, bapi);
		return ResponseEntity.ok(bapi);
	}

	/**
	 * 方法说明:根据poNo从GBSS返回SAP的VO
	 * @param poNo
	 * @return
	 */
	@GetMapping("/testSAP/{poNo}")
	public ResponseEntity<?> testSAP(@PathVariable("poNo") String poNo) {
		log.info("========================");
		log.info("-----根据poNo组装SAP:{}", poNo);
		try {
			// 此方法是返回SAP的VO
			ExecuteResponse<PoResultDetailVo> dealerOrderVo = gbssOpenApiClient.getGbssOrderResultDetail(poNo);
			log.info("==========" + dealerOrderVo);
		} catch (Exception e) {
			log.error("Mapper write error", e);
			throw e;
		}
		SapOrderStateBapi bapi = new SapOrderStateBapi();
		SapOrderStateItem item = new SapOrderStateItem();
		item.setPurchNoC(poNo);
		bapi.setSapOrderStateItemList(Arrays.asList(item));
		jcoExecution.execute(poNo, bapi);
		return ResponseEntity.ok(bapi);
	}

	/**
	 * 方法说明:将本地的数据根据poNo封装成SAP的VO
	 * @param poNo
	 * @return
	 */
	@GetMapping("/testLocalSAP/{poNo}")
	public DealerOrderVo testLocalSAP(@PathVariable("poNo") String poNo) {
		log.info("========================" + poNo);
		try {
			orderProcessingService.changePoAddrDetail(poNo);
			// 此方法是返回SAP的VO
			/*			PoResultDetailVo vo = orderProcessingService.loadLocalPoResultDetailVo(poNo);
						DealerOrderVo dealerOrderVo = orderScheduleService.doUploadFailedOrderSchedule();
						log.info("========== 拿到了SAP的VO ==========" + dealerOrderVo);*/
			return null;
		} catch (Exception e) {
			log.error("Mapper write error", e);
			throw e;
		}
	}

	/**
	 * 测试方法说明:通过poNo单号,将本系统OMS中对应的数据推送至SAP
	 * @param poNo
	 * @return
	 */
	@GetMapping("/testPushLocalSapVO")
	public ResponseEntity<DealerOrderZrebBapi> testPushLocalSapVO(@RequestParam("poNo") String poNo) {
		try {
			// 从本地数据库获取并组装PoResultDetailVo
			PoResultDetailVo poResultDetailVo = orderProcessingService.loadLocalPoResultDetailVo(poNo);
			// 将PoResultDetailVo转化为SAP上传格式
			DealerOrderZrebBapi dealerOrderZrebBapi1 = orderProcessingService.translatePoResultDetailVo2Bapi(poNo, poResultDetailVo);
			// 执行订单上传SAP接口
			DealerOrderZrebBapi dealerOrderZrebBapi2 = orderProcessingService.executeUploadBapi(poNo, dealerOrderZrebBapi1);
			return ResponseEntity.ok(dealerOrderZrebBapi2);
		} catch (Exception e) {
			log.error("Mapper write error", e);
			throw e;
		}
	}

	/**
	 * 方法说明:模拟定时任务重推之前推送SAP失败的数据
	 * @return
	 */
	@GetMapping("/afreshPushSapDealerOrderVo")
	public void afreshPushSapDealerOrderVo() {
		orderScheduleService.doUploadFailedOrderSchedule();
	}

	/**
	 * 方法说明:测试定时对账,根据当前时间前十分钟的支付完成时间的数据
	 * @param startTime
	 * @param endTime
	 */
	@GetMapping("/testChecking")
	public List<PoCheckDetail> testChecking(@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime)
			throws ParseException {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startTime1 = formatter.parse(startTime);
		Date endTime1 = formatter.parse(endTime);

		List<PoCheckDetail> gbssDetails = orderCheckingService.getGBSSBillingData(startTime1, endTime1);
		List<PoCheckDetail> omsDetails = orderCheckingService.findLocaleBillingData(startTime1, endTime1);
		List<PoCheckDetail> matchupDetails = orderCheckingService.matchUpCheckDetails(gbssDetails, omsDetails);

		PoCheckMaster poCheckMaster = orderCheckingService.updateCheckingResult(startTime1, endTime1, matchupDetails);
		System.out.println("----------------" + poCheckMaster);

		return matchupDetails;
	}

	/**
	 * 方法说明:测试定时对账,定时任务可直接调用执行对账
	 * 		1:当前对账间隔:10分钟
	 * 		2:对账时的endTime不能大于当前系统时间
	 * @throws Object:可能是返回对账的数据,可能是其他的提示
	 */
	@GetMapping("/tenMinuteCheck")
	public Object tenMinuteCheck() {
		return orderScheduleService.doCheckBillingSchedule(null, null, null);
	}

	/**
	 * 方法说明:测试定时任务重新拉取对账失败的数据
	 * @return
	 */
	@GetMapping("/afreshPullBillingData")
	public long afreshPullBillingData() {
		return orderScheduleService.doCheckBillingAgainSchedule();
	}

	/**
	 * 方法说明:测试重新拉取对账数据更新对账记录表
	 * @return
	 */
	@PostMapping("/updatePoCheckMasterAndPoCheckDetail")
	public void updatePoCheckMasterAndPoCheckDetail(@RequestBody PoMaster poMaster) {
		orderCheckingService.updateCheckDetailAgain(poMaster.getPoNo());
	}

	/**
	 * 方法说明:同步GBSS修改后的数据至OMS/以及GBSS逻辑删除的数据
	 * @param poStatus:最新的状态
	 * @param poNo:单号
	 * @return
	 */
	@GetMapping("/updatePoMaster")
	public PoStatusChange updatePoMaster(@RequestParam("poNo") String poNo, @RequestParam("poStatus") String poStatus) {
		PoStatusChange change = new PoStatusChange();
		change.setPoNo(poNo);
		change.setPoStatus(poStatus);
		change.setMessageId(UUID.randomUUID().toString());
		return orderProcessingService.handlePoStatusChange(poNo, change);
	}

	/**
	 * 根据ID查询poMaster
	 * @param poMaster
	 * @return
	 */
	@PostMapping("/selectPoMaster")
	public PoMaster selectPoMaster(@RequestBody PoMaster poMaster) {
		PoMaster poMaster1 = dsPoMasterRepository.findById(poMaster.getId()).orElse(new PoMaster());
		return poMaster1;
	}

	/**
	 * 测试方法说明:测试跟GBSS对账接口的联调
	 * @return
	 */
	@GetMapping("/testCheckForGBSS")
	public List<PoCheckDetail> testCheckForGBSS(@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {

		// 测试联调业务平台获取po详情
		ExecuteResponse<PoResultDetailVo> p1040487221 = gbssOpenApiClient.getGbssOrderResultDetail("P1040487221");
		p1040487221.getReturnObject();

		// 测试联调从业务平台获取对账数据
		ExecuteResponse<List<PoCheckDetail>> gbssBillingData = gbssOpenApiClient.getGbssBillingData(startTime, endTime);
		List<PoCheckDetail> list = gbssBillingData.getReturnObject();
		return list;
	}

	/**
	 * 测试方法说明:模拟MQ获取了poNo去GBSS拉取数据知道推送至SAP
	 * @param poNo
	 * @return
	 */
	@GetMapping("/testPullGBSSData")
	public void testPullGBSSData(@RequestParam("poNo") String poNo) {
		try {
			// 更新日志记录状态
			StatusStartRequest request = StatusStartRequest.builder().poNo(poNo).statusTime(SystemUtils.getCurrentTime())
					.status(PoProcessStatus.PO_RETRY_REQUEST).comments("重推SAP订单！").build();
			// 发送执行业务事件请求
			Boolean handleResult = orderProcessingService.handleStartRequest(poNo, request);
			if (log.isInfoEnabled()) {
				log.info("StatusStartRequest {} handleResult {}", request, handleResult);
			}
			if (handleResult != null && handleResult.booleanValue()) {
				CloudApplicationContext.getContainer().publishEvent(new OrderProcessRequestEvent(poNo));
			}
		} catch (Exception e) {
			log.error("Mapper write error", e);
			throw e;
		}
	}

	/**
	 * 测试方法说明:模拟定时任务重新对账
	 * @return
	 */
	@GetMapping("/testCheck")
	public void testCheck() {
		try {
			orderScheduleService.doCheckBillingAgainSchedule();
		} catch (Exception e) {
			log.error("Mapper write error", e);
			throw e;
		}
	}

	/**
	 * 测试方法说明：根据poNo从GBSS业务平台获取po详情
	 * @param poNo
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/takeGBSS/{poNo}")
	public PoResultDetailVo doOldLogic(@PathVariable("poNo") String poNo) throws Exception {
		try {
			// 测试联调业务平台获取po详情
			ExecuteResponse<PoResultDetailVo> vo = gbssOpenApiClient.getGbssOrderResultDetail(poNo);
			PoResultDetailVo poResultDetailVo = vo.getReturnObject();
			return poResultDetailVo;
		} catch (Exception e) {
			log.error("Mapper write error", e);
			throw e;
		}
	}
}
