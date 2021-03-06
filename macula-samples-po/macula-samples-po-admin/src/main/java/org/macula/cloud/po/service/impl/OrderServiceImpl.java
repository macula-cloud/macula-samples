package org.macula.cloud.po.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.macula.cloud.log.domain.ServiceInvokeLog;
import org.macula.cloud.po.domain.MkpDlpPoRelate;
import org.macula.cloud.po.domain.PoDetail;
import org.macula.cloud.po.domain.PoDetailDiscount;
import org.macula.cloud.po.domain.PoInvoiceInfo;
import org.macula.cloud.po.domain.PoMaster;
import org.macula.cloud.po.domain.PoPaymentDetail;
import org.macula.cloud.po.domain.PoProcessDetail;
import org.macula.cloud.po.domain.PoProcessMaster;
import org.macula.cloud.po.domain.PosAccTranDetail;
import org.macula.cloud.po.repository.MkpDlpPoRelateRepository;
import org.macula.cloud.po.repository.PoDetailDiscountRepository;
import org.macula.cloud.po.repository.PoDetailRepository;
import org.macula.cloud.po.repository.PoInvoiceInfoRepository;
import org.macula.cloud.po.repository.PoMasterRepository;
import org.macula.cloud.po.repository.PoPaymentDetailRepository;
import org.macula.cloud.po.repository.PoProcessDetailRepository;
import org.macula.cloud.po.repository.PoProcessMasterRepository;
import org.macula.cloud.po.repository.PoServiceInvokeLogRepository;
import org.macula.cloud.po.repository.PosAccTranDetailRepository;
import org.macula.cloud.po.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

/**
 
 
 */
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private PoMasterRepository poMasterRepository;

    private PoDetailRepository poDetailRepository;

    private PoProcessMasterRepository poProcessRepository;

    private PoProcessDetailRepository processDetailRepository;

    private PoDetailDiscountRepository poDetailDiscountRepository;

    private PosAccTranDetailRepository posAccTranDetailRepository;

    private PoPaymentDetailRepository poPaymentDetailRepository;

    private MkpDlpPoRelateRepository mkpDlpPoRelateRepository;

    private PoInvoiceInfoRepository poInvoiceInfoRepository;

    private PoServiceInvokeLogRepository orderServiceInvokeLogRepository;

    @Override
    public Page<Map<String, Object>> findPoMasters(Pageable pageRequest, String poNo, String poProcessCode, String sapPostingDocNo,
                                                   String poDate, String status) {
        //????????????????????????????????????????????????????????????????????????
        List<String> statusLs = Arrays.asList("00", "01", "99");
        if (ObjectUtils.isNotEmpty(status)) {
            String[] split = status.split(",");
            statusLs = Arrays.asList(split);
        }
        //???????????????????????????????????????
        List<Map<String, String>> result = poMasterRepository.findPoMasterByCondition(poNo, poProcessCode, sapPostingDocNo, poDate,
                statusLs, pageRequest.getOffset(), pageRequest.getPageSize());

        //?????????????????????????????????
        int total = poMasterRepository.findPoMasterByCondition(poNo, poProcessCode, sapPostingDocNo, poDate, statusLs);

        //formatHumpNameForList(result)???List???map???key?????????????????????????????????????????????
        return new PageImpl<>(formatHumpNameForList(result), pageRequest, total);
    }

    @Override
    public List<PoDetail> getOrderDetails(String poNo) {
        return poDetailRepository.findByPoNo(poNo);
    }

    /**
     * ??????????????????????????????
     *
     * @param pagRequest
     * @param status
     */
    @Override
    public Page<PoProcessMaster> findProcessMaster(Pageable pagRequest, String status, String poNo, String salesDocument, String poProcessCode) {
        Specification<PoProcessMaster> sp = (Specification<PoProcessMaster>) (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();

            if (!ObjectUtils.isEmpty(status)) {
                String[] st = status.split(",");
                CriteriaBuilder.In<String> in = cb.in(root.get("status"));
                for (String s : st) {
                    in.value(s);
                }
                list.add(in);

            }
            if (StringUtils.isNotBlank(poNo)) {
                list.add(cb.like(root.get("poNo").as(String.class), '%' + poNo + '%'));
            }

            if (StringUtils.isNotBlank(salesDocument)) {

                list.add(cb.like(root.get("salesDocument").as(String.class), "%" + salesDocument + "%"));
            }

            if (StringUtils.isNotBlank(poProcessCode)) {

                list.add(cb.like(root.get("poProcessCode").as(String.class), "%" + poProcessCode + "%"));

            }
            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        };

        Page<PoProcessMaster> all = poProcessRepository.findAll(sp, pagRequest);
        return all;
    }

    /**
     * ???????????????????????????
     *
     * @param poNo
     * @return
     */
    @Override
    public List<PoProcessDetail> getProcessDetails(String poNo) {

        return processDetailRepository.findByPoNo(poNo);
    }

    /**
     * ??????poNo????????????po_master??????
     *
     * @param poNo
     */
    @Override
    public PoMaster findOne(String poNo) {
        return poMasterRepository.findByPoNo(poNo);
    }

    /**
     * ??????poNo????????????po_process_master??????
     *
     * @param poNo
     */
    @Override
    public PoProcessMaster findProsMaster(String poNo) {

        return poProcessRepository.findByPoNo(poNo);

    }

    /**
     * ??????poNo??????po_detail_disconut??????
     *
     * @param poNo
     * @return
     */
    @Override
    public List<PoDetailDiscount> findPoDiscountDetail(String poNo) {
        return poDetailDiscountRepository.findByPoNo(poNo);
    }

    /**
     * ??????poNo,poDate,poStoreNo??????po_acc_tran_detail??????
     *
     * @param refDocNo    ...
     * @param posStoreNo
     * @param posTranDate
     * @return
     */
    @Override
    public List<PosAccTranDetail> findPoAccTranDetail(String refDocNo, String posStoreNo, Date posTranDate) {
        return posAccTranDetailRepository.findByRefDocNoAndPosStoreNoAndPosTranDate(refDocNo, posStoreNo, posTranDate);
    }

    /**
     * ??????poNo??????po_payment_detail??????
     *
     * @param poNo
     * @return
     */
    @Override
    public List<PoPaymentDetail> findPoPaymentDetail(String poNo) {
        return poPaymentDetailRepository.findByPoNo(poNo);
    }

    /**
     * ??????poNo??????mkp_dlp_po_relate??????
     *
     * @param poNo
     * @return
     */
    @Override
    public List<MkpDlpPoRelate> findMkpDlpPoRelateDetail(String poNo) {
        return mkpDlpPoRelateRepository.findByPoNo(poNo);
    }

    /**
     * ??????poAppNo??????po_invoice_info??????
     *
     * @param poAppNo
     * @return
     */
    @Override
    public List<PoInvoiceInfo> findPoInvoiceInfoDetail(String poAppNo) {
        return poInvoiceInfoRepository.findByPoAppNo(poAppNo);
    }

    /**
     * ??????dataKey??????po_invoice_info??????
     *
     * @param dataKey
     * @return
     */
    @Override
    public List<ServiceInvokeLog> findServiceInvokeLog(String dataKey) {
        return orderServiceInvokeLogRepository.findByDataKey(dataKey);
    }

    /**
     * ???List???map???key?????????????????????????????????
     *
     * @param
     * @return
     */
    public static List<Map<String, Object>> formatHumpNameForList(List<Map<String, String>> list) {
        List<Map<String, Object>> newList = new ArrayList<>();
        for (Map<String, String> o : list) {
            newList.add(formatHumpName(o));
        }
        return newList;
    }

    public static Map<String, Object> formatHumpName(Map<String, String> map) {
        Map<String, Object> newMap = new HashMap<>();
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            String key = entry.getKey();
            String newKey = toFormatCol(key);
            newMap.put(newKey, entry.getValue());
        }
        return newMap;
    }

    public static String toFormatCol(String colName) {
        StringBuilder sb = new StringBuilder();
        String[] str = colName.toLowerCase().split("_");
        int i = 0;
        for (String s : str) {
            if (s.length() == 1) {
                s = s.toUpperCase();
            }
            i++;
            if (i == 1) {
                sb.append(s);
                continue;
            }
            if (s.length() > 0) {
                sb.append(s.substring(0, 1).toUpperCase());
                sb.append(s.substring(1));
            }
        }
        return sb.toString();
    }

}