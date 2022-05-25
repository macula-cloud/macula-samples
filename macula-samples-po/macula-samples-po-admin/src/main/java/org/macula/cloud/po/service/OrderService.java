package org.macula.cloud.po.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 
 
 */
public interface OrderService {

    /**
     * 分页查询订单信息
     *
     * @param pagRequest ...
     * @return
     */
    Page<Map<String, Object>> findPoMasters(Pageable pagRequest, String poNo, String poProcessCode, String sapPostingDocNo, String poDate, String status);

    /**
     * 查询单个订单详细信息
     *
     * @param poNo
     * @return
     */
    List<PoDetail> getOrderDetails(String poNo);

    /**
     * 分页查询订单处理信息
     *
     * @param pagRequest ...
     * @return
     */
    Page<PoProcessMaster> findProcessMaster(Pageable pagRequest, String status, String poNo, String salesDocument, String poProcessCode);

    /**
     * 查询订单处理明细表
     *
     * @param poNo
     * @return
     */
    List<PoProcessDetail> getProcessDetails(String poNo);

    /**
     * 根据poNo查询一条po_master记录
     *
     * @param poNo
     * @return
     */
    PoMaster findOne(String poNo);

    /**
     * 根据poNo查询一条po_process_master记录
     *
     * @param poNo
     * @return
     */
    PoProcessMaster findProsMaster(String poNo);

    /**
     * 根据poNo查询po_detail_disconut记录
     *
     * @param poNo
     * @return
     */
    List<PoDetailDiscount> findPoDiscountDetail(String poNo);

    /**
     * 根据poNo,poDate,poStoreNo查询po_acc_tran_detail记录
     *
     * @param poNo ...
     * @return
     */
    List<PosAccTranDetail> findPoAccTranDetail(String poNo, String poStoreNo, Date poDate);

    /**
     * 根据poNo查询po_payment_detail记录
     *
     * @param poNo
     * @return
     */
    List<PoPaymentDetail> findPoPaymentDetail(String poNo);

    /**
     * 根据poNo查询mkp_dlp_po_relate记录
     *
     * @param poNo
     * @return
     */
    List<MkpDlpPoRelate> findMkpDlpPoRelateDetail(String poNo);

    /**
     * 根据poAppNo查询po_invoice_info记录
     *
     * @param poAppNo
     * @return
     */
    List<PoInvoiceInfo> findPoInvoiceInfoDetail(String poAppNo);

    /**
     * 根据dataKey查询po_invoice_info记录
     *
     * @param dataKey
     * @return
     */
    List<ServiceInvokeLog> findServiceInvokeLog(String dataKey);

}
