/*
Navicat MySQL Data Transfer

Source Server         : 10.86.72.28_prd
Source Server Version : 50730
Source Host           : 10.86.72.28:3306
Source Database       : db_oms

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2020-10-21 16:14:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for mc_service_invoke_log
-- ----------------------------
DROP TABLE IF EXISTS `mc_service_invoke_log`;
CREATE TABLE `mc_service_invoke_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_key` varchar(255) DEFAULT NULL COMMENT '业务数据值',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `node` varchar(100) DEFAULT NULL COMMENT '执行机器节点',
  `source` varchar(255) DEFAULT NULL COMMENT '接口调用源',
  `source_message` longtext COMMENT '接口调用信息',
  `source_method` varchar(255) DEFAULT NULL COMMENT '接口调用方法',
  `source_timestamp` datetime(6) DEFAULT NULL COMMENT '接口调用时间',
  `status` varchar(10) DEFAULT NULL COMMENT '接口调用状态',
  `target` varchar(255) DEFAULT NULL COMMENT '目标系统',
  `target_message` longtext COMMENT '返回信息',
  `target_method` varchar(255) DEFAULT NULL COMMENT '目标方法',
  `target_timestamp` datetime(6) DEFAULT NULL COMMENT '返回时间',
  `exception_message` longtext COMMENT '异常信息',
  `transaction_id` varchar(255) DEFAULT '' COMMENT '相关线程号',
  `comments` varchar(200) DEFAULT NULL COMMENT '备注',
  `created_time` datetime(6) NOT NULL COMMENT '创建时间',
  `created_by` varchar(20) NOT NULL COMMENT '创建人',
  `last_updated_time` datetime(6) NOT NULL COMMENT '最后更新时间',
  `last_updated_by` varchar(255) NOT NULL COMMENT '最后更新人',
  PRIMARY KEY (`id`),
  KEY `IDX_SERVICE_LOG_01` (`data_key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=667000 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for mkp_dlp_po_relate
-- ----------------------------
DROP TABLE IF EXISTS `mkp_dlp_po_relate`;
CREATE TABLE `mkp_dlp_po_relate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `po_no` varchar(15) DEFAULT NULL COMMENT '销售单号',
  `store_no` varchar(15) DEFAULT NULL COMMENT '店铺编号',
  `dealer_no` varchar(15) DEFAULT NULL COMMENT '会员卡号',
  `delivery_type` varchar(2) DEFAULT NULL COMMENT '配送方式',
  `cash_amt` decimal(11,2) DEFAULT NULL COMMENT '现金支付总金额',
  `total_dlp_amt` decimal(11,2) DEFAULT NULL COMMENT '积分金额',
  `total_discount_amt` decimal(11,2) DEFAULT NULL COMMENT '积分折扣金额',
  `transport_amt` decimal(11,2) DEFAULT NULL COMMENT '运费金额',
  `comments` varchar(200) DEFAULT NULL COMMENT '备注',
  `created_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(20) DEFAULT NULL COMMENT '创建人',
  `last_updated_time` datetime(6) DEFAULT NULL COMMENT '最后更新时间',
  `last_updated_by` varchar(20) DEFAULT NULL COMMENT '最后更新用户',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_MKP_DLP_PO_RELATE_U1` (`po_no`) USING BTREE,
  KEY `IDX_MKP_DLP_PO_RELATE_02` (`store_no`) USING BTREE,
  KEY `IDX_MKP_DLP_PO_RELATE_03` (`dealer_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4790166 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for pos_acc_tran_detail
-- ----------------------------
DROP TABLE IF EXISTS `pos_acc_tran_detail`;
CREATE TABLE `pos_acc_tran_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序列号',
  `pos_tran_date` date NOT NULL COMMENT 'POS交易日期',
  `pos_store_no` varchar(15) NOT NULL COMMENT 'POS自营店号',
  `pos_user_name` varchar(7) NOT NULL COMMENT 'POS收银员',
  `pos_no` varchar(1) NOT NULL COMMENT 'POS机号',
  `pos_tran_type` varchar(3) NOT NULL COMMENT 'POS交易项目',
  `pos_tran_amt` decimal(11,2) NOT NULL COMMENT 'POS交易金额',
  `pos_payment_type` varchar(6) NOT NULL COMMENT 'POS支付类型',
  `pos_tran_status` varchar(1) NOT NULL COMMENT 'POS交易状态',
  `ref_doc_no` varchar(15) DEFAULT NULL COMMENT '参考单号',
  `ref_order_dealer_no` varchar(15) DEFAULT NULL COMMENT '参考购货人卡号',
  `pos_ack_time` datetime(6) DEFAULT NULL COMMENT 'POS交易确认时间',
  `pos_ack_by` varchar(20) DEFAULT NULL COMMENT 'POS交易确认人',
  `sap_posting_flag` varchar(1) NOT NULL COMMENT 'SAP记账标志',
  `sap_posting_date` date DEFAULT NULL COMMENT 'SAP记账日期',
  `sap_posting_doc_no` varchar(20) DEFAULT NULL COMMENT 'SAP记账凭证',
  `comments` varchar(200) DEFAULT NULL COMMENT '备注',
  `created_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(20) DEFAULT NULL COMMENT '创建人',
  `last_updated_time` datetime(6) DEFAULT NULL COMMENT '最后更新时间',
  `last_updated_by` varchar(20) DEFAULT NULL COMMENT '最后更新用户',
  PRIMARY KEY (`id`),
  KEY `IDX_POS_ACC_TRAN_DETAIL_01` (`pos_tran_date`,`pos_store_no`) USING BTREE,
  KEY `IDX_POS_ACC_TRAN_DETAIL_02` (`pos_user_name`) USING BTREE,
  KEY `IDX_POS_ACC_TRAN_DETAIL_03` (`pos_tran_date`) USING BTREE,
  KEY `IDX_POS_ACC_TRAN_DETAIL_04` (`ref_doc_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for po_addr_detail
-- ----------------------------
DROP TABLE IF EXISTS `po_addr_detail`;
CREATE TABLE `po_addr_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `po_no` varchar(15) NOT NULL COMMENT '订货单号',
  `delivery_hold_stock_flag` varchar(1) NOT NULL COMMENT '配送扣库存标志',
  `delivery_wh_code` varchar(4) DEFAULT NULL COMMENT '配送出货RDC',
  `delivery_wh_loc_code` varchar(4) DEFAULT NULL COMMENT '配送出货RDC库区',
  `delivery_plan_date` date DEFAULT NULL COMMENT '计划触发处理日期',
  `delivery_plan_proc_flag` varchar(1) NOT NULL COMMENT '计划触发处理标志',
  `delivery_plan_doc_no` varchar(20) DEFAULT NULL COMMENT '触发交货文件号',
  `delivery_attr` varchar(1) NOT NULL COMMENT '销售单据配送属性',
  `delivery_status` varchar(1) NOT NULL COMMENT '销售单据配送状态',
  `delivery_type` varchar(2) NOT NULL COMMENT '提货类型',
  `delivery_dealer_no` varchar(15) NOT NULL COMMENT '提货地点',
  `delivery_store_run_type` varchar(1) DEFAULT NULL COMMENT '提货店铺运作类型',
  `addr_send_id` varchar(36) DEFAULT NULL COMMENT '收货地址ID',
  `addr_area_code` varchar(32) DEFAULT NULL COMMENT '收货地址区域ID',
  `addr_province` varchar(50) DEFAULT NULL COMMENT '收货地址省',
  `addr_city` varchar(50) DEFAULT NULL COMMENT '收货地址市(县)',
  `addr_county` varchar(50) DEFAULT NULL COMMENT '收货地址区',
  `addr_district` varchar(50) DEFAULT NULL COMMENT '收货地址镇',
  `addr_detail` varchar(100) DEFAULT NULL COMMENT '收货详细地址',
  `r01_full_name` varchar(50) DEFAULT NULL COMMENT '收货人1姓名',
  `r01_certificate_no` varchar(50) DEFAULT NULL COMMENT '收货人1身份证号',
  `r01_teles` varchar(50) DEFAULT NULL COMMENT '收货人1电话',
  `r02_full_name` varchar(50) DEFAULT NULL COMMENT '收货人2姓名',
  `r02_certificate_no` varchar(50) DEFAULT NULL COMMENT '收货人2身份证号',
  `r02_teles` varchar(50) DEFAULT NULL COMMENT '收货人2电话',
  `r03_full_name` varchar(50) DEFAULT NULL COMMENT '收货人3姓名',
  `r03_certificate_no` varchar(50) DEFAULT NULL COMMENT '收货人3身份证号',
  `r03_teles` varchar(50) DEFAULT NULL COMMENT '收货人3电话',
  `stock_proc_memo` varchar(50) DEFAULT NULL COMMENT '库存处理备注',
  `stock_recheck_flag` varchar(1) DEFAULT NULL COMMENT '库存处理检查标志',
  `stock_recheck_time` datetime(6) DEFAULT NULL COMMENT '库存处理检查时间',
  `pick_up_verify_code` varchar(10) DEFAULT NULL,
  `pick_up_status` varchar(1) NOT NULL,
  `pick_up_date` date DEFAULT NULL,
  `pick_up_entry_time` datetime(6) DEFAULT NULL,
  `pick_up_entry_by` varchar(20) DEFAULT NULL,
  `comments` varchar(200) DEFAULT NULL,
  `last_updated_time` datetime(6) NOT NULL COMMENT '最后更新时间',
  `last_updated_by` varchar(20) NOT NULL COMMENT '最后更新用户',
  `shipment_no` varchar(15) DEFAULT NULL COMMENT '运单号',
  `shipment_date` date DEFAULT NULL COMMENT '运单日期',
  `real_delivery_date` date DEFAULT NULL COMMENT '实际发货日期',
  `shipment_logistics_status` varchar(2) DEFAULT '' COMMENT '运单物流状态',
  `shipment_receive_status` varchar(2) DEFAULT '' COMMENT '收货状态',
  `r01_certificate_type` varchar(2) DEFAULT NULL COMMENT '收货人1证件类型',
  `r02_certificate_type` varchar(2) DEFAULT NULL COMMENT '收货人2证件类型',
  `r03_certificate_type` varchar(2) DEFAULT NULL COMMENT '收货人3证件类型',
  `is_partial_shipment` varchar(2) DEFAULT NULL COMMENT '是否部分发货',
  `triger_date` date DEFAULT NULL COMMENT '触发日期',
  `ship_date` date DEFAULT NULL COMMENT '发货日期',
  `onroad_date` date DEFAULT NULL COMMENT '在途日期',
  `receive_date` date DEFAULT NULL COMMENT '收货日期',
  `delivery_package_type` varchar(10) DEFAULT NULL COMMENT '配送打包类型',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_PO_ADDR_DETAIL_U1` (`po_no`) USING BTREE,
  KEY `IDX_PO_ADDR_DETAIL_01` (`pick_up_status`) USING BTREE,
  KEY `IDX_PO_ADDR_DETAIL_02` (`shipment_date`,`shipment_no`) USING BTREE,
  KEY `IDX_PO_ADDR_DETAIL_03` (`delivery_plan_date`,`delivery_plan_doc_no`) USING BTREE,
  KEY `IDX_PO_ADDR_DETAIL_04` (`po_no`,`delivery_dealer_no`) USING BTREE,
  KEY `IDX_PO_ADDR_DETAIL_05` (`delivery_plan_proc_flag`) USING BTREE,
  KEY `IDX_PO_ADDR_DETAIL_06` (`delivery_dealer_no`,`pick_up_status`) USING BTREE,
  KEY `IDX_PO_ADDR_DETAIL_07` (`delivery_dealer_no`,`pick_up_status`,`delivery_plan_date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=56799069 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for po_check_detail
-- ----------------------------
DROP TABLE IF EXISTS `po_check_detail`;
CREATE TABLE `po_check_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `check_master_id` bigint(20) NOT NULL COMMENT '关联MASTER ID',
  `gbss_po_no` varchar(15) DEFAULT NULL COMMENT '业务平台订单号',
  `gbss_po_amount` decimal(11,2) DEFAULT NULL COMMENT '业务平台订单金额',
  `gbss_sap_doc_no` varchar(20) DEFAULT NULL COMMENT '业务平台SAP单号',
  `gbss_po_status` varchar(10) DEFAULT NULL COMMENT 'GBSS销售单据状态',
  `oms_po_no` varchar(15) DEFAULT NULL COMMENT 'OMS订单号',
  `oms_po_amount` decimal(11,2) DEFAULT NULL COMMENT 'OMS订单金额',
  `oms_sap_doc_no` varchar(20) DEFAULT NULL COMMENT 'OMS的SAP单号',
  `oms_po_status` varchar(10) DEFAULT NULL COMMENT 'OMS销售单据状态',
  `syn_status` varchar(10) NOT NULL DEFAULT '1' COMMENT 'GBSS和OMS数据对账状态:0:不一致;1:一致;2:手动忽略对账',
  `comments` varchar(255) DEFAULT NULL COMMENT '备注',
  `created_by` varchar(50) NOT NULL COMMENT '创建人',
  `created_time` datetime(6) NOT NULL COMMENT '创建时间',
  `last_updated_by` varchar(50) NOT NULL COMMENT '最后更新人',
  `last_updated_time` datetime(6) NOT NULL COMMENT '最后更新时间',
  `object_version_number` bigint(20) NOT NULL DEFAULT '0' COMMENT '数据版本',
  PRIMARY KEY (`id`),
  KEY `IDX_PO_CHECK_DETAIL_01` (`check_master_id`) USING BTREE,
  KEY `IDX_PO_CHECK_DETAIL_02` (`gbss_po_no`) USING BTREE,
  KEY `IDX_PO_CHECK_DETAIL_03` (`oms_po_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=702821 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for po_check_master
-- ----------------------------
DROP TABLE IF EXISTS `po_check_master`;
CREATE TABLE `po_check_master` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `start_time` datetime(6) NOT NULL COMMENT '开始时间',
  `end_time` datetime(6) NOT NULL COMMENT '结束时间',
  `gbss_po_count` decimal(11,0) NOT NULL DEFAULT '0' COMMENT '业务平台订单总量',
  `gbss_po_amount` decimal(11,2) NOT NULL COMMENT '业务平台订单总金额',
  `oms_po_count` decimal(11,0) NOT NULL DEFAULT '0' COMMENT 'OMS订单总量',
  `oms_po_amount` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT 'OMS订单总金额',
  `ignore_po_count` bigint(20) NOT NULL DEFAULT '0' COMMENT '忽略订单总量',
  `ignore_po_amount` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '忽略订单总金额',
  `syn_status` varchar(10) NOT NULL DEFAULT '1' COMMENT 'GBSS和OMS数据对账状态:0:不一致;1:一致',
  `check_number` int(11) NOT NULL DEFAULT '0' COMMENT '重新定时对账次数(超过3次不再进行定时重新对账,并发送告警通知)',
  `created_by` varchar(50) NOT NULL COMMENT '创建人',
  `created_time` datetime(6) NOT NULL COMMENT '创建时间',
  `last_updated_by` varchar(50) NOT NULL COMMENT '最后更新人',
  `last_updated_time` datetime(6) NOT NULL COMMENT '最后更新时间',
  `object_version_number` bigint(20) NOT NULL DEFAULT '0' COMMENT '数据版本',
  PRIMARY KEY (`id`),
  KEY `IDX_PO_CHECK_MASTER_01` (`start_time`,`end_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1627 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for po_detail
-- ----------------------------
DROP TABLE IF EXISTS `po_detail`;
CREATE TABLE `po_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `po_no` varchar(15) NOT NULL COMMENT '订货单号',
  `line_no` int(6) NOT NULL COMMENT '行号',
  `product_code` varchar(10) NOT NULL COMMENT '产品代码',
  `product_type` varchar(1) NOT NULL COMMENT '产品类型',
  `product_attr` varchar(1) NOT NULL COMMENT '产品属性',
  `sale_qty` int(11) NOT NULL COMMENT '订货产品数量',
  `sale_price` decimal(11,2) NOT NULL COMMENT '购买单价',
  `sale_rebate` decimal(11,2) NOT NULL COMMENT '购买优差',
  `sale_point` decimal(11,2) NOT NULL COMMENT '购买点数',
  `product_price` decimal(11,2) NOT NULL COMMENT '购买原优惠价',
  `product_point` decimal(11,2) NOT NULL COMMENT '产品原点数',
  `product_bom_code` varchar(10) DEFAULT NULL COMMENT '产品BOM代码',
  `prom_code` varchar(8) NOT NULL COMMENT '促销代码',
  `prom_product_attr` varchar(1) NOT NULL COMMENT '促销产品属性',
  `comments` varchar(200) DEFAULT NULL COMMENT '备注',
  `last_updated_time` datetime(6) DEFAULT NULL COMMENT '最后更新时间',
  `last_updated_by` varchar(20) DEFAULT NULL COMMENT '最后更新用户',
  `product_unit_weight` decimal(11,3) DEFAULT '0.000' COMMENT '单位产品重量',
  `is_direct_sale` bit(1) DEFAULT b'1' COMMENT '是否直销产品',
  `vat_rate` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '产品增值税率',
  `consum_tax_rate` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '产品消费税率',
  `compre_tax_rate` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '产品综合税率',
  `transport_amt` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '产品运费总金额',
  `vat_amt` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '产品增值税金额',
  `consum_tax_amt` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '产品消费税金额',
  `compre_tax_amt` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '产品综合税金额',
  `prom_price` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '活动优惠价格',
  `prom_point` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '活动优惠点数',
  `prom_qty` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '活动优惠数量',
  `prom_rebate` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '活动单位优差',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_PO_DETAIL_U1` (`po_no`,`line_no`) USING BTREE,
  KEY `IDX_PO_DETAIL_01` (`po_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=272372605 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for po_detail_discount
-- ----------------------------
DROP TABLE IF EXISTS `po_detail_discount`;
CREATE TABLE `po_detail_discount` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `po_no` varchar(20) NOT NULL COMMENT '订货单号',
  `line_no` int(11) NOT NULL COMMENT '本地行号',
  `product_code` varchar(10) NOT NULL COMMENT '产品代码',
  `product_attr` varchar(1) NOT NULL COMMENT '产品属性',
  `rebate_dis_amt_product` decimal(11,2) NOT NULL COMMENT '优差折扣金额（单品）',
  `rebate_dis_amt_coupon` decimal(11,2) NOT NULL COMMENT '优差折扣金额（电子券分摊）',
  `rebate_dis_amt_whole` decimal(11,2) NOT NULL COMMENT '优差折扣金额（整单减分摊）',
  `company_dis_amt_product` decimal(11,2) NOT NULL COMMENT '公司折扣金额（单品）',
  `company_dis_amt_coupon` decimal(11,2) NOT NULL COMMENT '公司折扣金额（电子券分摊）',
  `company_dis_amt_whole` decimal(11,2) NOT NULL COMMENT '公司折扣金额（整单减分摊）',
  `comments` varchar(200) DEFAULT NULL COMMENT '备注说明',
  `created_time` datetime(6) NOT NULL COMMENT '创建时间',
  `created_by` varchar(20) NOT NULL COMMENT '创建人',
  `last_updated_time` datetime(6) NOT NULL COMMENT '最后更新时间',
  `last_updated_by` varchar(20) NOT NULL COMMENT '最后更新用户',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_PO_DETAIL_DISCOUNT_U1` (`po_no`,`line_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4002088 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for po_header
-- ----------------------------
DROP TABLE IF EXISTS `po_header`;
CREATE TABLE `po_header` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `po_no` varchar(255) NOT NULL COMMENT '订单号',
  `sap_process_code` varchar(255) NOT NULL COMMENT 'SAP的订货流程代码',
  `order_dealer_no` varchar(20) DEFAULT NULL COMMENT '订货卡号',
  `sale_branch_no` varchar(255) DEFAULT NULL COMMENT '所属分公司编号',
  `sale_zone_no` varchar(255) DEFAULT NULL COMMENT '销售片区编号',
  `store_full_name` varchar(255) DEFAULT NULL COMMENT '店主卡号姓名',
  `store_ntax_register_no` varchar(255) DEFAULT NULL COMMENT '国税注册号',
  `customer_dealer_no` varchar(20) DEFAULT NULL COMMENT '优消卡号',
  `customer_full_name` varchar(255) DEFAULT NULL COMMENT '优消姓名',
  `customer_mobile` varchar(255) DEFAULT NULL COMMENT '优消手机号',
  `delivery_dealer_no` varchar(20) DEFAULT NULL COMMENT '到货店铺卡号',
  `delivery_full_name` varchar(255) DEFAULT NULL COMMENT '到货店铺姓名',
  `last_updated_by` varchar(255) DEFAULT NULL COMMENT '更新人',
  `last_updated_time` datetime(6) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_PO_HEADER_U01` (`po_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=57043626 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for po_invoice_info
-- ----------------------------
DROP TABLE IF EXISTS `po_invoice_info`;
CREATE TABLE `po_invoice_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `po_app_no` varchar(20) NOT NULL COMMENT '订货申请单号',
  `dealer_no` varchar(15) NOT NULL COMMENT '卡号',
  `invoice_type` varchar(6) NOT NULL COMMENT '发票类型',
  `invoice_title` varchar(256) NOT NULL COMMENT '发票抬头',
  `invoice_mobile` varchar(20) DEFAULT NULL COMMENT '收票人手机号',
  `invoice_email` varchar(40) DEFAULT NULL COMMENT '收票人邮箱',
  `comments` varchar(200) DEFAULT NULL COMMENT '备注',
  `created_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(20) DEFAULT NULL COMMENT '创建人',
  `last_updated_time` datetime(6) DEFAULT NULL COMMENT '最后更新时间',
  `last_updated_by` varchar(20) DEFAULT NULL COMMENT '最后更新用户',
  `invoice_photo_id` varchar(255) DEFAULT NULL COMMENT '电子发票图片ID',
  `tax_payer_code` varchar(32) DEFAULT NULL COMMENT '纳税人识别号',
  `recipient_name` varchar(50) DEFAULT NULL COMMENT '收件人姓名',
  `recipient_area_code` varchar(36) DEFAULT NULL COMMENT '收件人地区编号',
  `recipient_addr_tail` varchar(100) DEFAULT NULL COMMENT '收件人详细地址',
  `recipient_tele` varchar(30) DEFAULT NULL COMMENT '收件人联系电话',
  `invoice_addr_tail` varchar(100) DEFAULT NULL COMMENT '发票地址',
  `bank_name` varchar(64) DEFAULT NULL COMMENT '开户行名称',
  `bank_account` varchar(30) DEFAULT NULL COMMENT '账号',
  `contact_tele` varchar(30) DEFAULT NULL COMMENT '单位联系电话',
  `invoice_status` decimal(1,0) NOT NULL DEFAULT '-1' COMMENT '发票状态',
  `invoice_app_count` decimal(2,0) NOT NULL DEFAULT '1' COMMENT '发票申请次数',
  `po_no` varchar(15) DEFAULT NULL COMMENT '销售单号',
  `po_process_code` varchar(4) DEFAULT NULL COMMENT '订货流程代码',
  `po_date` date DEFAULT NULL COMMENT '销售日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_PO_INVOICE_INFO_U1` (`po_app_no`) USING BTREE,
  KEY `IDX_PO_INVOICE_INFO_01` (`dealer_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=125253 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for po_master
-- ----------------------------
DROP TABLE IF EXISTS `po_master`;
CREATE TABLE `po_master` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `po_no` varchar(15) NOT NULL COMMENT '销售单号',
  `po_process_code` varchar(4) NOT NULL COMMENT '订货流程代码',
  `po_type` varchar(2) NOT NULL COMMENT '销售单类型',
  `po_period` varchar(6) NOT NULL COMMENT '销售月份',
  `po_date` date NOT NULL COMMENT '销售日期',
  `po_store_no` varchar(15) NOT NULL COMMENT '销售店铺',
  `po_branch_no` varchar(4) NOT NULL COMMENT '销售分公司',
  `po_region_no` varchar(4) NOT NULL COMMENT '销售大区代号',
  `po_status` varchar(2) NOT NULL COMMENT '销售单据状态',
  `order_dealer_no` varchar(15) NOT NULL COMMENT '购货人卡号',
  `order_dealer_name` varchar(50) DEFAULT NULL COMMENT '购货人姓名',
  `order_type` varchar(4) NOT NULL COMMENT '购货类型',
  `ref_po_no` varchar(15) DEFAULT NULL COMMENT '参考源订货单号',
  `ref_po_period` varchar(6) DEFAULT NULL COMMENT '参考源订货单月份',
  `ref_po_date` date DEFAULT NULL COMMENT '参考源订货单日期',
  `total_sale_amt` decimal(11,2) NOT NULL COMMENT '整单订货总金额',
  `total_sale_product_amt` decimal(11,2) NOT NULL COMMENT '整单产品金额',
  `total_sale_non_product_amt` decimal(11,2) NOT NULL COMMENT '整单非产品金额',
  `total_sale_discount_amt` decimal(11,2) NOT NULL COMMENT '整单销售折让',
  `total_sale_coupon_amt` decimal(11,2) NOT NULL COMMENT '整单使用优惠券',
  `total_sale_net_amt` decimal(11,2) NOT NULL COMMENT '整单订货净金额',
  `total_transport_amt` decimal(11,2) NOT NULL COMMENT '订单实际运费',
  `total_calc_point` decimal(11,2) NOT NULL COMMENT '计分合计总点数',
  `total_calc_discount_point` decimal(11,2) NOT NULL COMMENT '计分合计折扣点数',
  `total_calc_rebate` decimal(11,2) NOT NULL COMMENT '计分合计总优差',
  `po_entry_class` varchar(1) NOT NULL COMMENT '销售办理来源',
  `po_entry_dealer_no` varchar(15) NOT NULL COMMENT '销售办代办人',
  `po_entry_time` datetime(6) NOT NULL COMMENT '销售单据输入时间',
  `po_entry_by` varchar(20) NOT NULL COMMENT '销售单据输入用户',
  `po_entry_memo` varchar(255) DEFAULT NULL COMMENT '销售单据输入备注',
  `po_app_no` varchar(20) DEFAULT NULL COMMENT '销售订货申请单号',
  `po_lcl_no` varchar(20) DEFAULT NULL COMMENT '销售辅单申请单号',
  `po_group_no` varchar(20) DEFAULT NULL COMMENT '团购单号',
  `ref_selected_no` varchar(20) DEFAULT NULL COMMENT '参考单据号',
  `po_prom_flag` varchar(1) NOT NULL COMMENT '销售促销标示',
  `po_whole_prom_code` varchar(8) DEFAULT NULL COMMENT '整单促销代码',
  `po_price_group_type` varchar(5) NOT NULL COMMENT '定价价格组',
  `po_price_attr` varchar(1) NOT NULL COMMENT '定价属性',
  `po_return_restrict_flag` varchar(1) NOT NULL COMMENT '退货约束标志',
  `payment_total_amt` decimal(11,2) NOT NULL COMMENT '支付金额',
  `payment_status` varchar(1) NOT NULL COMMENT '付款状态',
  `payment_time` datetime(6) NOT NULL COMMENT '付款完成时间',
  `payment_doc_no` varchar(20) DEFAULT NULL COMMENT '支付文件序号',
  `payment_memo` varchar(200) DEFAULT NULL COMMENT '支付备注',
  `sap_posting_flag` varchar(1) NOT NULL COMMENT 'SAP记账标志',
  `sap_posting_date` date DEFAULT NULL COMMENT 'SAP记账日期',
  `sap_posting_doc_no` varchar(20) DEFAULT NULL COMMENT 'SAP记账凭证',
  `comments` varchar(200) DEFAULT NULL COMMENT '备注',
  `last_updated_time` datetime(6) DEFAULT NULL COMMENT '最后更新时间',
  `last_updated_by` varchar(20) DEFAULT NULL COMMENT '最后更新人',
  `po_owe_no` varchar(20) DEFAULT NULL COMMENT '记欠申请号',
  `order_dealer_branch_no` varchar(4) DEFAULT NULL COMMENT '购货人对应的分公司',
  `order_desc` varchar(200) DEFAULT NULL COMMENT '订单摘要',
  `company_no` varchar(15) NOT NULL DEFAULT '3001' COMMENT '所属公司编码',
  `profit_center_no` varchar(10) DEFAULT NULL COMMENT '利润中心编号',
  `order_customer_no` varchar(15) DEFAULT NULL COMMENT '销售顾客号',
  `total_weight` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '订单总重量',
  `total_calc_rebate_ds` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '计分合计直销产品优差',
  `total_vat_amt` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '订单增值税总金额',
  `total_consum_tax_amt` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '订单消费税总金额',
  `total_compre_tax_amt` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '订单综合税总金额',
  `total_rebate_dis_amt` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '总优差折让金额',
  `total_transport_coupon_amt` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '运费优惠金额',
  `po_entry_store_no` varchar(15) DEFAULT NULL COMMENT '销售代办店铺',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_PO_MASTER_U1` (`po_no`) USING BTREE,
  KEY `IDX_PO_MASTER_01` (`order_dealer_no`) USING BTREE,
  KEY `IDX_PO_MASTER_02` (`po_period`) USING BTREE,
  KEY `IDX_PO_MASTER_03` (`po_period`,`po_entry_dealer_no`) USING BTREE,
  KEY `IDX_PO_MASTER_04` (`po_lcl_no`) USING BTREE,
  KEY `IDX_PO_MASTER_05` (`po_period`,`po_date`,`po_no`,`po_process_code`) USING BTREE,
  KEY `IDX_PO_MASTER_11` (`ref_po_no`) USING BTREE,
  KEY `IDX_PO_MASTER_12` (`sap_posting_flag`,`sap_posting_doc_no`) USING BTREE,
  KEY `IDX_PO_MASTER_13` (`ref_selected_no`) USING BTREE,
  KEY `IDX_PO_MASTER_14` (`order_customer_no`) USING BTREE,
  KEY `IDX_PO_MASTER_15` (`ref_po_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=57043626 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for po_message_level
-- ----------------------------
DROP TABLE IF EXISTS `po_message_level`;
CREATE TABLE `po_message_level` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(255) DEFAULT NULL COMMENT '电子邮箱',
  `level` varchar(200) DEFAULT NULL COMMENT '级别',
  `comments` varchar(500) DEFAULT NULL COMMENT '备注',
  `created_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `created_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `last_updated_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `last_updated_time` datetime(6) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for po_payment_detail
-- ----------------------------
DROP TABLE IF EXISTS `po_payment_detail`;
CREATE TABLE `po_payment_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `po_no` varchar(15) NOT NULL COMMENT '订货单号',
  `po_payment_type` varchar(6) NOT NULL COMMENT '支付类型',
  `po_payment_amt` decimal(11,2) NOT NULL COMMENT '支付金额',
  `po_payment_dealer_no` varchar(15) DEFAULT NULL COMMENT '支付卡号',
  `acc_tran_type` varchar(3) NOT NULL COMMENT '账户处理类型',
  `epay_agent_code` varchar(4) DEFAULT NULL COMMENT '在线支付交易方式',
  `epay_card_no` varchar(30) DEFAULT NULL COMMENT '在线支付卡号',
  `discount_point` decimal(11,2) NOT NULL COMMENT '订单抵扣点数',
  `ref_tran_info` varchar(15) DEFAULT NULL COMMENT '相关记录信息',
  `comments` varchar(20) DEFAULT NULL,
  `last_updated_time` datetime(6) DEFAULT NULL COMMENT '最后更新时间',
  `last_updated_by` varchar(20) DEFAULT NULL COMMENT '最后更新用户',
  PRIMARY KEY (`id`),
  KEY `IDX_PO_PAYMENT_DETAIL_01` (`po_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=48029922 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for po_process_code_info
-- ----------------------------
DROP TABLE IF EXISTS `po_process_code_info`;
CREATE TABLE `po_process_code_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序列号',
  `po_process_code` varchar(4) NOT NULL COMMENT '订货流程代码',
  `po_process_desc` varchar(50) NOT NULL COMMENT '订货流程说明',
  `po_type` varchar(2) DEFAULT NULL COMMENT '销售单类型',
  `so_type` varchar(2) DEFAULT NULL COMMENT '定价价格组',
  `is_for_inv` decimal(1,0) NOT NULL COMMENT '是否属于订货',
  `is_for_po` decimal(1,0) NOT NULL COMMENT '是否属于购货',
  `comments` varchar(200) DEFAULT NULL COMMENT '备注',
  `created_time` datetime(6) NOT NULL COMMENT '创建时间',
  `created_by` varchar(20) NOT NULL COMMENT '创建人',
  `last_updated_time` datetime(6) NOT NULL COMMENT '最后更新时间',
  `last_updated_by` varchar(20) NOT NULL COMMENT '最后更新用户',
  `sap_process_code` varchar(4) NOT NULL COMMENT 'SAP的订货流程代码',
  `gcs_process_code` varchar(4) NOT NULL COMMENT '计分系统的回单流程代码',
  `display_desc_dealer` varchar(32) DEFAULT NULL COMMENT '显示名称（个人）',
  `display_desc_store` varchar(32) DEFAULT NULL COMMENT '显示名称（店铺）',
  `display_desc_company` varchar(32) DEFAULT NULL COMMENT '显示名称（公司）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_PO_PROCESS_CODE_INFO_U1` (`po_process_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for po_process_detail
-- ----------------------------
DROP TABLE IF EXISTS `po_process_detail`;
CREATE TABLE `po_process_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `po_no` varchar(20) NOT NULL COMMENT '订单号',
  `proc_state` varchar(4) NOT NULL COMMENT '处理状态',
  `proc_time` datetime(6) NOT NULL COMMENT '状态时间',
  `comments` varchar(512) DEFAULT NULL COMMENT '备注',
  `created_by` varchar(20) NOT NULL COMMENT '创建人',
  `created_time` datetime(6) NOT NULL COMMENT '创建时间',
  `last_updated_by` varchar(20) NOT NULL COMMENT '最后更新人',
  `last_updated_time` datetime(6) NOT NULL COMMENT '最后更新时间',
  `object_version_number` bigint(20) NOT NULL COMMENT '版本',
  PRIMARY KEY (`id`),
  KEY `PO_PROCESS_DETAIL_01` (`po_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2187943 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for po_process_master
-- ----------------------------
DROP TABLE IF EXISTS `po_process_master`;
CREATE TABLE `po_process_master` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `po_no` varchar(20) NOT NULL COMMENT '订单号',
  `po_process_code` varchar(10) DEFAULT NULL COMMENT '订单流程号',
  `po_source` varchar(10) DEFAULT NULL COMMENT 'PO来源',
  `ref_source_id` bigint(20) DEFAULT NULL COMMENT '来源id',
  `ref_source_no` varchar(32) DEFAULT NULL COMMENT '来源号(MQ MessageId)',
  `status` varchar(4) NOT NULL COMMENT '状态',
  `status_time` datetime(6) NOT NULL COMMENT '状态时间',
  `error_times` int(11) NOT NULL COMMENT '失败次数',
  `sales_document` varchar(20) DEFAULT NULL COMMENT 'SAP订单号',
  `comments` varchar(512) DEFAULT NULL COMMENT '备注',
  `created_by` varchar(20) NOT NULL COMMENT '创建人',
  `send_mq_id` varchar(50) DEFAULT NULL COMMENT 'MQ返回SAP单号发送消息ID',
  `created_time` datetime(6) NOT NULL COMMENT '创建时间',
  `last_updated_by` varchar(20) NOT NULL COMMENT '最后更新人',
  `last_updated_time` datetime(6) NOT NULL COMMENT '最后更新时间',
  `object_version_number` bigint(20) NOT NULL COMMENT '版本',
  PRIMARY KEY (`id`),
  UNIQUE KEY `PO_PROCESS_MASTER_U01` (`po_no`) USING BTREE,
  KEY `PO_PROCESS_MASTER_IDX_01` (`po_source`,`ref_source_no`) USING BTREE,
  KEY `PO_PROCESS_MASTER_IDX_02` (`po_source`,`ref_source_id`) USING BTREE,
  KEY `PO_PROCESS_MASTER_IDX_03` (`status_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=280507 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for sale_branch_info
-- ----------------------------
DROP TABLE IF EXISTS `sale_branch_info`;
CREATE TABLE `sale_branch_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `sale_org_code` varchar(3) NOT NULL COMMENT '销售机构代号',
  `sale_branch_no` varchar(4) NOT NULL COMMENT '销售分支机构代号',
  `sale_branch_desc` varchar(50) NOT NULL COMMENT '销售分支机构简称',
  `sale_branch_desc2` varchar(50) NOT NULL COMMENT '销售分支机构全称',
  `sale_region_no` varchar(4) NOT NULL COMMENT '销售大区代号',
  `sap_profit_center` varchar(5) NOT NULL COMMENT 'SAP利润中心',
  `show_line_no` decimal(3,0) NOT NULL COMMENT '显示顺序号',
  `comments` varchar(200) DEFAULT NULL COMMENT '备注说明',
  `created_time` datetime(6) NOT NULL COMMENT '创建时间',
  `created_by` varchar(20) NOT NULL COMMENT '创建人',
  `last_updated_time` datetime(6) NOT NULL COMMENT '最后更新时间',
  `last_updated_by` varchar(20) NOT NULL COMMENT '最后更新用户',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_SALE_BRANCH_INFO_U1` (`sale_org_code`,`sale_branch_no`) USING BTREE,
  UNIQUE KEY `IDX_SALE_BRANCH_INFO_U2` (`sale_branch_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1002 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for sap_daily_upl_po
-- ----------------------------
DROP TABLE IF EXISTS `sap_daily_upl_po`;
CREATE TABLE `sap_daily_upl_po` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `po_no` varchar(15) DEFAULT NULL COMMENT '订单单号',
  `syn_type` varchar(1) DEFAULT NULL COMMENT '同步类型',
  `syn_status` varchar(1) DEFAULT NULL COMMENT '同步状态',
  `syn_err_code` varchar(4) DEFAULT NULL COMMENT '同步错误信息',
  `syn_err_msg` varchar(1000) DEFAULT NULL COMMENT '同步错误信息',
  `syn_err_cnt` bigint(8) DEFAULT NULL COMMENT '同步错误次数',
  `step01_end_time` datetime(6) DEFAULT NULL COMMENT '步骤01结束时间',
  `step02_end_time` datetime(6) DEFAULT NULL COMMENT '步骤02结束时间',
  `step03_end_time` datetime(6) DEFAULT NULL COMMENT '步骤03结束时间',
  `comments` varchar(200) DEFAULT NULL COMMENT '备注',
  `created_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(20) DEFAULT NULL COMMENT '创建人',
  `last_updated_time` datetime(6) DEFAULT NULL COMMENT '最后更新时间',
  `last_updated_by` varchar(20) DEFAULT NULL COMMENT '最后更新用户',
  `syn_priority` bigint(4) DEFAULT '1' COMMENT '同步优先级',
  `plan_syn_time` datetime(6) DEFAULT NULL COMMENT '计划同步时间',
  PRIMARY KEY (`id`),
  KEY `IDX_SAP_DAILY_UPL_PO_01` (`syn_status`) USING BTREE,
  KEY `IDX_SAP_DAILY_UPL_PO_03` (`syn_type`) USING BTREE,
  KEY `IDX_SAP_DAILY_UPL_PO_02` (`po_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=50566704 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for sap_ds_mapping
-- ----------------------------
DROP TABLE IF EXISTS `sap_ds_mapping`;
CREATE TABLE `sap_ds_mapping` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `mapping_type` varchar(20) NOT NULL COMMENT '映射类型',
  `ds_code` varchar(100) NOT NULL COMMENT 'DS数据',
  `ds_desc` varchar(100) NOT NULL COMMENT 'DS数据描述',
  `sap_code` varchar(100) NOT NULL COMMENT '映射到SAP的数据',
  `is_enabled` decimal(1,0) NOT NULL COMMENT '是否有效',
  `comments` varchar(200) DEFAULT NULL COMMENT '备注',
  `last_updated_time` datetime(6) NOT NULL COMMENT '最后更新时间',
  `last_updated_by` varchar(20) NOT NULL COMMENT '最后更新用户',
  `created_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_SAP_DS_MAPPING_U1` (`mapping_type`,`ds_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for sys_company
-- ----------------------------
DROP TABLE IF EXISTS `sys_company`;
CREATE TABLE `sys_company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `company_no` varchar(15) NOT NULL COMMENT '公司编号',
  `sap_sale_org_no` varchar(15) NOT NULL COMMENT 'SAP销售组织编码',
  `company_name` varchar(50) NOT NULL COMMENT '公司简称',
  `company_full_name` varchar(50) NOT NULL COMMENT '公司全称',
  `legal_entity_no` varchar(15) NOT NULL COMMENT '法人实体代码',
  `legal_entity_name` varchar(50) NOT NULL COMMENT '法人实体名称',
  `comments` varchar(200) DEFAULT NULL COMMENT '备注说明',
  `created_time` datetime(6) NOT NULL COMMENT '创建时间',
  `created_by` varchar(20) NOT NULL COMMENT '创建人',
  `last_updated_time` datetime(6) NOT NULL COMMENT '最后更新时间',
  `last_updated_by` varchar(20) NOT NULL COMMENT '最后更新用户',
  `legal_credit_code` varchar(18) DEFAULT NULL COMMENT '统一社会信用代码',
  `delegate_company_no` varchar(15) DEFAULT NULL COMMENT '代收款公司编号',
  `effective_date` datetime(6) NOT NULL COMMENT '有效日期',
  `inactive_date` datetime(6) DEFAULT NULL COMMENT '失效日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_SYS_COMPANY_U1` (`company_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1502 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for sys_enum_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_enum_info`;
CREATE TABLE `sys_enum_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `language_code` varchar(3) NOT NULL COMMENT '语言种类',
  `table_name` varchar(30) NOT NULL COMMENT '数据表名',
  `field_name` varchar(30) NOT NULL COMMENT '数据字段名',
  `line_no` decimal(5,0) NOT NULL COMMENT '显示时行号',
  `data_value` varchar(50) NOT NULL COMMENT '枚举值',
  `data_short_desc` varchar(50) NOT NULL COMMENT '枚举显示简称',
  `data_full_desc` varchar(200) NOT NULL COMMENT '枚举显示全称',
  `is_enabled` decimal(1,0) NOT NULL COMMENT '是否有效',
  `comments` varchar(200) DEFAULT NULL COMMENT '备注说明',
  `created_time` datetime(6) NOT NULL COMMENT '创建时间',
  `created_by` varchar(20) NOT NULL COMMENT '创建人',
  `last_updated_time` datetime(6) NOT NULL COMMENT '最后更新时间',
  `last_updated_by` varchar(20) NOT NULL COMMENT '最后更新用户',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_SYS_ENUM_INFO_U1` (`language_code`,`table_name`,`field_name`) USING BTREE,
  KEY `IDX_SYS_ENUM_INFO_01` (`table_name`,`field_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for sys_param_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_param_info`;
CREATE TABLE `sys_param_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `para_system` varchar(32) NOT NULL COMMENT '参数所属系统',
  `para_code` varchar(50) NOT NULL COMMENT '参数代码',
  `para_value` varchar(500) NOT NULL COMMENT '参数值',
  `para_type` varchar(20) NOT NULL COMMENT '参数类型',
  `para_scope` varchar(500) NOT NULL COMMENT '参数范围',
  `para_default` varchar(500) NOT NULL COMMENT '参数默认值',
  `para_desc` varchar(200) NOT NULL COMMENT '参数说明',
  `comments` varchar(200) DEFAULT NULL COMMENT '备注说明',
  `created_time` datetime(6) NOT NULL COMMENT '创建时间',
  `created_by` varchar(20) NOT NULL COMMENT '创建人',
  `last_updated_time` datetime(6) NOT NULL COMMENT '最后更新时间',
  `last_updated_by` varchar(20) NOT NULL COMMENT '最后更新用户',
  `para_group_code` varchar(15) DEFAULT NULL COMMENT '参数分组编号',
  `display_ord` decimal(3,0) NOT NULL DEFAULT '0' COMMENT '显示顺序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_SYS_PARAM_INFO_U1` (`para_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
