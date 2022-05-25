CREATE TABLE `lc_wh_info` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `wh_code` varchar(4) DEFAULT NULL COMMENT '仓库代码',
  `wh_name` varchar(50) DEFAULT NULL COMMENT '仓库名称',
  `wh_type` varchar(3) DEFAULT NULL COMMENT '仓库类型',
  `pos_ref_wh_code` varchar(4) DEFAULT NULL COMMENT 'POS关联仓库代码',
  `contact_tel` varchar(25) DEFAULT NULL COMMENT '联系电话',
  `contact_fax` varchar(25) DEFAULT NULL COMMENT '联系传真',
  `addr_zip_code` varchar(6) DEFAULT NULL COMMENT '联系地址邮编',
  `addr_detail` varchar(150) DEFAULT NULL COMMENT '联系地址',
  `comments` varchar(200) DEFAULT NULL COMMENT '备注说明',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `created_by` varchar(20) NOT NULL COMMENT '创建人',
  `last_updated_time` datetime NOT NULL COMMENT '最后更新时间',
  `last_updated_by` varchar(20) NOT NULL COMMENT '最后更新用户',
  `estimate_arrival_day` decimal(3,0) DEFAULT NULL COMMENT '预计到货时间（天）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_WH_INFO_U1` (`wh_code`)
) DEFAULT CHARSET=utf8  COMMENT='货仓信息表';
