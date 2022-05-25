package org.macula.cloud.po.controller;

import io.choerodon.core.annotation.Permission;
import io.choerodon.core.enums.OperationType;
import io.choerodon.core.enums.ScopeType;
import lombok.AllArgsConstructor;
import org.macula.cloud.po.domain.SysParamInfo;
import org.macula.cloud.po.service.OrderControlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 
 
 */
@RestController
@RequestMapping(value = "/v1/order_control")
@AllArgsConstructor
public class OrderControlController {

    private OrderControlService orderControlService;

    /**
     * 查询sys_param_info信息
     */
    @GetMapping("/all")
    @Permission(operationType = OperationType.SELECT, scopeType = ScopeType.NONE)
    public ResponseEntity<List<SysParamInfo>> getList() {
        List<SysParamInfo> result = orderControlService.findByParaSystem();
        return ResponseEntity.ok(result);
    }

    /**
     * 根据id更新指定记录para_value
     */
    @PutMapping("/{id}/{param}")
    @Permission(operationType = OperationType.UPDATE, scopeType = ScopeType.NONE)
    public ResponseEntity<?> updateById(@PathVariable("id") Long id, @PathVariable("param") String param) {
        orderControlService.updateById(id, param);
        return ResponseEntity.ok().build();
    }

}
