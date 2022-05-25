package org.macula.cloud.stock.web.controller;


import io.choerodon.core.annotation.Permission;
import io.choerodon.core.enums.OperationType;
import io.choerodon.core.enums.ScopeType;
import io.swagger.annotations.ApiOperation;
import org.macula.cloud.stock.domain.StockRoute;
import org.macula.cloud.stock.web.pojo.LcCategory;
import org.macula.cloud.stock.web.service.DcGoodsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 
 
 */
@RestController
@RequestMapping(value = "/v1/goods")
public class DcGoodsController {

    private DcGoodsService dcGoodsService;

    public DcGoodsController(DcGoodsService dcGoodsService) {
        this.dcGoodsService = dcGoodsService;
    }

    @GetMapping
    @Permission(operationType = OperationType.SELECT, scopeType = ScopeType.NONE)
    @ApiOperation("根据父id 查询商品分类列表")
    public ResponseEntity<List<LcCategory>> list(@RequestParam("parent_id") Long parentId) {
        return new ResponseEntity<>(dcGoodsService.listByParentId(parentId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Permission(operationType = OperationType.SELECT, scopeType = ScopeType.NONE)
    @ApiOperation("根据id查询商品类目")
    public ResponseEntity<LcCategory> query(@PathVariable("id") Long id) {
        return new ResponseEntity<>(dcGoodsService.query(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    @Permission(operationType = OperationType.SELECT, scopeType = ScopeType.NONE)
    @ApiOperation("查询SKU配置表")
    public ResponseEntity<List<StockRoute>> findAll() {
        return new ResponseEntity<>(dcGoodsService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    @Permission(operationType = OperationType.INSERT, scopeType = ScopeType.NONE)
    @ApiOperation("保存SKU配置记录")
    public ResponseEntity<?> addOne(@RequestBody StockRoute sr) {
        return new ResponseEntity<>(dcGoodsService.save(sr), HttpStatus.OK);
    }

}
