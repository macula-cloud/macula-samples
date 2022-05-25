package org.macula.cloud.po.controller;

import java.util.List;

import org.macula.cloud.po.domain.PoCheckDetail;
import org.macula.cloud.po.domain.PoCheckMaster;
import org.macula.cloud.po.feign.OrderScheduleFeignClient;
import org.macula.cloud.po.page.PageUtils;
import org.macula.cloud.po.service.CheckService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping(value = "/v1/checking")
@AllArgsConstructor
public class CheckController {

    private CheckService checkService;
    private OrderScheduleFeignClient orderScheduleFeignClient;

    /**
     * 分页查询po_check_master列表信息
     */
    @GetMapping("/po_check_master/all")
    @Permission(operationType = OperationType.SELECT, scopeType = ScopeType.NONE)
    public ResponseEntity<Page<PoCheckMaster>> checkMasterPage(@SortDefault(value = {"startTime", "endTime"}, direction = Sort.Direction.DESC) Pageable pagRequest,
                                                               @RequestParam(required = false) String synStatus) {
        Page<PoCheckMaster> result = checkService.getList(PageUtils.pageRequest(pagRequest), synStatus);
        return ResponseEntity.ok(result);
    }

    /**
     * 根据id查询po_check_detail列表信息
     */
    @GetMapping("/po_check_detail/{id}")
    @Permission(operationType = OperationType.SELECT, scopeType = ScopeType.NONE)
    public ResponseEntity<List<PoCheckDetail>> checkDetailInfo(@PathVariable("id") Long id) {
        List<PoCheckDetail> result = checkService.findById(id);
        return ResponseEntity.ok(result);
    }

    /**
     * 根据id查询一条po_check_master信息
     */
    @GetMapping("/po_check_master/find_one/{id}")
    @Permission(operationType = OperationType.SELECT, scopeType = ScopeType.NONE)
    public ResponseEntity<PoCheckMaster> findOne(@PathVariable("id") Long id) {
        PoCheckMaster poCheckMaster = checkService.findByMstId(id);
        return ResponseEntity.ok(poCheckMaster);
    }

    /**
     * 重新对账：调用OMS微服务重新对账
     * id：对账主表的ID,必传参数
     */
    @GetMapping("/afreshChecking/{id}")
    @Permission(operationType = OperationType.SELECT, scopeType = ScopeType.NONE)
    public ResponseEntity<PoCheckMaster> afreshChecking(@PathVariable("id") Long id) {
        return orderScheduleFeignClient.afreshChecking(id);
    }

    /**
     * 对账忽略操作：调用OMS微服务更改状态为81
     * gbssPoNo：对账主明细表的gbssPoNo,必传参数
     */
    @GetMapping("/successByHand/{gbssPoNo}/{checkMasterId}")
    @Permission(operationType = OperationType.SELECT, scopeType = ScopeType.NONE)
    public ResponseEntity<PoCheckDetail> successByHand(@PathVariable("gbssPoNo") String gbssPoNo,@PathVariable("checkMasterId") Long checkMasterId) {
        return orderScheduleFeignClient.successByHand(gbssPoNo, checkMasterId);
    }

}
