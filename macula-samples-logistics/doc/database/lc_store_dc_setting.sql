CREATE TABLE `lc_store_dc_setting` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `WAREHOUSE_LEVEL1` varchar(32) NOT NULL COMMENT '一级提货仓',
  `WAREHOUSE_LEVEL2` varchar(32) DEFAULT NULL COMMENT '二级提货仓',
  `WAREHOUSE_LEVEL3` varchar(32) DEFAULT NULL COMMENT '三级提货仓',
  `START_UP_COST1` int(10) unsigned DEFAULT NULL COMMENT '一级不足起运量加收服务费',
  `START_UP_COST2` int(10) unsigned DEFAULT NULL COMMENT '二级不足起运量加收服务费',
  `START_UP_COST3` int(10) unsigned DEFAULT NULL COMMENT '三级不足起运量加收服务费',
  `ADDRESS_CODE` varchar(32) NOT NULL COMMENT '地址编码',
  `EFFECTIVE_DATE` datetime DEFAULT NULL COMMENT '生效时间',
  `INACTIVE_DATE` datetime DEFAULT NULL COMMENT '失效时间',
  `REMARK` varchar(256) DEFAULT NULL COMMENT '备注',
  `EDIT_FLAG` tinyint(3) unsigned DEFAULT NULL COMMENT '编辑标识，1 是可以，0不可以',
  `OBJECT_VERSION_NUMBER` bigint(20) unsigned DEFAULT '1',
  `CREATED_BY` bigint(20) unsigned DEFAULT '0',
  `CREATION_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  `LAST_UPDATED_BY` bigint(20) unsigned DEFAULT '0',
  `LAST_UPDATE_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `LC_STORE_DC_SETTING_IDX_01``` (`ADDRESS_CODE`)
) DEFAULT CHARSET=utf8 COMMENT='店铺配送设置表';
