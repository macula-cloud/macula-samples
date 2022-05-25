package org.macula.cloud.po.controller;

import java.security.Principal;
import java.util.List;

import org.macula.cloud.po.domain.PoMessageLevel;
import org.macula.cloud.po.page.PageUtils;
import org.macula.cloud.po.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping(value = "/v1/messages")
@AllArgsConstructor
public class MessageController {

    private MessageService messageService;

    @GetMapping
    @Permission(operationType = OperationType.SELECT, scopeType = ScopeType.NONE)
    public ResponseEntity<Page<PoMessageLevel>> messagePage(@SortDefault(value = "id", direction = Sort.Direction.DESC) Pageable pagRequest,
                                                            @RequestParam(required = false) String userName,
                                                            @RequestParam(required = false) String mobile, @RequestParam(required = false) String level) {
        Page<PoMessageLevel> result = messageService.getList(PageUtils.pageRequest(pagRequest), userName, mobile, level);
        return ResponseEntity.ok(result);
    }

    /**
     * save
     */
    @PostMapping("/add")
    @Permission(operationType = OperationType.INSERT, scopeType = ScopeType.NONE)
    public ResponseEntity<?> messageSave(@RequestBody List<PoMessageLevel> message, Principal principal) {
        messageService.add(message, principal.getName());
        return ResponseEntity.ok().build();
    }

    /**
     * delete by ids
     */
    @DeleteMapping("/deleteByIds")
    @Permission(operationType = OperationType.DELETE, scopeType = ScopeType.NONE)
    public ResponseEntity<?> messageDelete(@RequestBody Long[] ids) {
        messageService.deleteByIds(ids);
        return ResponseEntity.ok().build();
    }

    /**
     * update by one entity
     */
    @PutMapping("/update")
    @Permission(operationType = OperationType.UPDATE, scopeType = ScopeType.NONE)
    public ResponseEntity<?> messageUpdate(@RequestBody PoMessageLevel mg, Principal pr) {
        mg.setLastModifiedBy(pr.getName());
        messageService.updateByOneEntity(mg);
        return ResponseEntity.ok().build();
    }

}
