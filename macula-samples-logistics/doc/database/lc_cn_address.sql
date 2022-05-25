CREATE TABLE `lc_cn_address` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `CODE` varchar(32) NOT NULL COMMENT '地址编码',
  `NAME` varchar(128) NOT NULL COMMENT '地址名称',
  `ADDRESS_TYPE` tinyint(3) unsigned NOT NULL COMMENT '地址类型',
  `PARENT_CODE` varchar(32) DEFAULT NULL COMMENT '父级地址编码',
  `EFFECTIVE_DATE` datetime DEFAULT NULL COMMENT '生效时间',
  `INACTIVE_DATE` datetime DEFAULT NULL COMMENT '失效时间',
  `DOWNTOWN_FLAG` tinyint(3) unsigned DEFAULT NULL COMMENT '是否为城区，1 为城区，0 为不是',
  `SHOW_FLAG` tinyint(3) unsigned DEFAULT NULL COMMENT '是否显示，1 为显示，0为不显示',
  `LEAF_FLAG` tinyint(3) unsigned DEFAULT NULL COMMENT '是否为叶子节点，1 为是，0为否',
  `DELETED_FLAG` tinyint(3) unsigned DEFAULT NULL COMMENT '是否作废，1 是作废，0不是',
  `ADDRESS` varchar(512) DEFAULT NULL COMMENT '地址',
  `PROV` varchar(128) DEFAULT NULL COMMENT '省',
  `PROV_ID` varchar(32) DEFAULT NULL COMMENT '省 ID',
  `CITY` varchar(128) DEFAULT NULL COMMENT '市',
  `CITY_ID` varchar(32) DEFAULT NULL COMMENT '市ID',
  `DISTRICT` varchar(128) DEFAULT NULL COMMENT '区',
  `DISTRICT_ID` varchar(32) DEFAULT NULL COMMENT '区ID',
  `TOWN` varchar(128) DEFAULT NULL COMMENT '镇',
  `TOWN_ID` varchar(32) DEFAULT NULL COMMENT '镇ID',
  `OBJECT_VERSION_NUMBER` bigint(20) unsigned DEFAULT '1',
  `CREATED_BY` bigint(20) unsigned DEFAULT '0',
  `CREATION_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  `LAST_UPDATED_BY` bigint(20) unsigned DEFAULT '0',
  `LAST_UPDATE_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `LC_CN_ADDRESS_UDX_01` (`CODE`),
  KEY `LC_CN_ADDRESS_IDX_01` (`PARENT_CODE`)
) DEFAULT CHARSET=utf8 COMMENT='地址表';