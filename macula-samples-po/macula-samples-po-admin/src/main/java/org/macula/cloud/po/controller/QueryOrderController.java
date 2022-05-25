package org.macula.cloud.po.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
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
import org.macula.cloud.po.feign.OrderScheduleFeignClient;
import org.macula.cloud.po.page.PageUtils;
import org.macula.cloud.po.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.choerodon.core.annotation.Permission;
import io.choerodon.core.enums.OperationType;
import io.choerodon.core.enums.ScopeType;
import lombok.AllArgsConstructor;

/**
 
 
 */
@RestController
@RequestMapping(value = "/v1/orders")
@AllArgsConstructor
public class QueryOrderController {

    private OrderService orderService;

    private OrderScheduleFeignClient orderPushFeignClient;

    /**
     * 分页查询po_master列表信息
     */
    @GetMapping
    @Permission(operationType = OperationType.SELECT, scopeType = ScopeType.NONE)
    public ResponseEntity<Page<Map<String, Object>>> orderPage(Pageable pagRequest,
                                                               @RequestParam(required = false) String poNo, @RequestParam(required = false) String poProcessCode,
                                                               @RequestParam(required = false) String sapPostingDocNo,
                                                               @RequestParam(required = false) String poDate,
                                                               @RequestParam(required = false) String status) {
        Page<Map<String, Object>> result = orderService.findPoMasters(PageUtils.pageRequest(pagRequest), poNo, poProcessCode, sapPostingDocNo, poDate, status);
        return ResponseEntity.ok(result);
    }

    /**
     * 根据poNo查询po_detail记录
     */
    @GetMapping("/orderDetail/{poNo}")
    @Permission(operationType = OperationType.SELECT, scopeType = ScopeType.NONE)
    public ResponseEntity<List<PoDetail>> orderInfo(@PathVariable("poNo") String poNo) {
        List<PoDetail> result = orderService.getOrderDetails(poNo);
        return ResponseEntity.ok(result);
    }

    /**
     * 根据poNo查询一条po_master记录
     */
    @GetMapping("/find_one/{poNo}")
    @Permission(operationType = OperationType.SELECT, scopeType = ScopeType.NONE)
    public ResponseEntity<PoMaster> findOne(@PathVariable("poNo") String poNo) {
        PoMaster result = orderService.findOne(poNo);
        return ResponseEntity.ok(result);
    }

    /**
     * 重新推送订单至SAP
     */
    @PutMapping("/order_push/master/{poNo}")
    @Permission(operationType = OperationType.UPDATE, scopeType = ScopeType.NONE)
    public ResponseEntity<?> orderPush(@PathVariable("poNo") String poNo) {
        orderPushFeignClient.handleOrderUpload(poNo);
        return ResponseEntity.ok().build();
    }

    /**
     * 分页查询po_process_master(订单处理信息)
     */
    @GetMapping("/process_list")
    @Permission(operationType = OperationType.SELECT, scopeType = ScopeType.NONE)
    public ResponseEntity<Page<PoProcessMaster>> orderProcessPage(@SortDefault(value = "id", direction = Sort.Direction.DESC) Pageable pagRequest,
                                                                  @RequestParam(required = false) String status,
                                                                  @RequestParam(required = false) String poNo,
                                                                  @RequestParam(required = false) String salesDocument,
                                                                  @RequestParam(required = false) String poProcessCode

    ) {
        Page<PoProcessMaster> result = orderService.findProcessMaster(PageUtils.pageRequest(pagRequest), status, poNo, salesDocument, poProcessCode);
        return ResponseEntity.ok(result);
    }

    /**
     * 查询po_process_detail(订单处理明细表)
     */
    @GetMapping("/process_detail/{poNo}")
    @Permission(operationType = OperationType.SELECT, scopeType = ScopeType.NONE)
    public ResponseEntity<List<PoProcessDetail>> orderProcessDetail(@PathVariable("poNo") String poNo) {
        List<PoProcessDetail> result = orderService.getProcessDetails(poNo);
        return ResponseEntity.ok(result);
    }

    /**
     * 根据poNo查询po_process_master记录
     */
    @GetMapping("/process_master/{poNo}")
    @Permission(operationType = OperationType.SELECT, scopeType = ScopeType.NONE)
    public ResponseEntity<PoProcessMaster> findOneProsMaster(@PathVariable("poNo") String poNo) {
        PoProcessMaster result = orderService.findProsMaster(poNo);
        return ResponseEntity.ok(result);
    }

    /**
     * 根据poNo查询po_detail_disconut记录
     */
    @GetMapping("/detail_discount/{poNo}")
    @Permission(operationType = OperationType.SELECT, scopeType = ScopeType.NONE)
    public ResponseEntity<List<PoDetailDiscount>> findPoDiscountDetail(@PathVariable("poNo") String poNo) {
        List<PoDetailDiscount> result = orderService.findPoDiscountDetail(poNo);
        return ResponseEntity.ok(result);
    }

    /**
     * 根据poNo,poDate,poStoreNo查询po_acc_tran_detail记录
     */
    @GetMapping("/detail_acc_tran/{poNo}")
    @Permission(operationType = OperationType.SELECT, scopeType = ScopeType.NONE)
    public ResponseEntity<List<PosAccTranDetail>> findPoAccTranDetail(@PathVariable("poNo") String poNo) {
        List<PosAccTranDetail> result = null;

        PoMaster ms = orderService.findOne(poNo);
        if (ObjectUtils.isNotEmpty(ms)) {
            result = orderService.findPoAccTranDetail(poNo, ms.getPoStoreNo(), ms.getPoDate());
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 根据poNo查询po_payment_detail记录
     */
    @GetMapping("/detail_payment/{poNo}")
    @Permission(operationType = OperationType.SELECT, scopeType = ScopeType.NONE)
    public ResponseEntity<List<PoPaymentDetail>> findPoPaymentDetail(@PathVariable("poNo") String poNo) {
        List<PoPaymentDetail> result = orderService.findPoPaymentDetail(poNo);
        return ResponseEntity.ok(result);
    }

    /**
     * 根据poNo查询mkp_dlp_po_relate记录
     */
    @GetMapping("/mkp_dlp_po_relate/{poNo}")
    @Permission(operationType = OperationType.SELECT, scopeType = ScopeType.NONE)
    public ResponseEntity<List<MkpDlpPoRelate>> findMkpDlpPoRelateDetail(@PathVariable("poNo") String poNo) {
        List<MkpDlpPoRelate> result = orderService.findMkpDlpPoRelateDetail(poNo);
        return ResponseEntity.ok(result);
    }

    /**
     * 根据poAppNo查询po_invoice_info记录
     */
    @GetMapping("/detail_invoice_info/{poNo}")
    @Permission(operationType = OperationType.SELECT, scopeType = ScopeType.NONE)
    public ResponseEntity<List<PoInvoiceInfo>> findPoInvoiceInfoDetail(@PathVariable("poNo") String poNo) {
        List<PoInvoiceInfo> result = null;

        PoMaster ms = orderService.findOne(poNo);
        if (ObjectUtils.isNotEmpty(ms)) {
            result = orderService.findPoInvoiceInfoDetail(ms.getPoAppNo());

        }
        return ResponseEntity.ok(result);
    }

    /**
     * 根据poNo查询mc_service_invoke_log记录
     */
    @GetMapping("/mc_service_invoke_log/{poNo}")
    @Permission(operationType = OperationType.SELECT, scopeType = ScopeType.NONE)
    public ResponseEntity<List<ServiceInvokeLog>> findMcSerInvokeLog(@PathVariable("poNo") String poNo) {
        List<ServiceInvokeLog> result = orderService.findServiceInvokeLog(poNo);
        return ResponseEntity.ok(result);
    }

}
