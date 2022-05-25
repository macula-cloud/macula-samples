package org.macula.cloud.po.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.macula.cloud.core.domain.LegacyUpdateable;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * <b>SapDailyUplPoV2</b> 是服务中心SAP上传销售单据标02
 * </p>
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@Table(name = "SAP_DAILY_UPL_PO_V2")
public class SapDailyUplPoV2 extends LegacyUpdateable<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**订单单号*/
	@Column(name = "PO_NO", nullable = false, length = 15)
	private String poNo;

	/**销售店铺*/
	@Column(name = "PO_STORE_NO", nullable = false, length = 15)
	private String poStoreNo;

	/**单据类型*/
	@Column(name = "PO_DOC_TYPE", nullable = false, length = 10)
	private String poDocType;

	/**同步类型*/
	@Column(name = "SYN_TYPE", nullable = false, length = 1)
	private String synType;

	/**同步优先级*/
	@Column(name = "SYN_PRIORITY", nullable = false, length = 4)
	private Long synPriority;

	/**同步状态*/
	@Column(name = "SYN_STATUS", nullable = false, length = 1)
	private String synStatus;

	/**同步错误代码*/
	@Column(name = "SYN_ERR_CODE", nullable = true, length = 4)
	private String synErrCode;

	/**同步错误信息*/
	@Column(name = "SYN_ERR_MSG", nullable = true, length = 1000)
	private String synErrMsg;

	/**同步错误次数*/
	@Column(name = "SYN_ERR_CNT", nullable = false, length = 8)
	private int synErrCnt;

	/**步骤01结束时间*/
	@Column(name = "STEP01_END_TIME", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date step01EndTime;

	/**步骤02结束时间*/
	@Column(name = "STEP02_END_TIME", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date step02EndTime;

	/**步骤03结束时间*/
	@Column(name = "STEP03_END_TIME", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date step03EndTime;

	/**备注*/
	@Column(name = "COMMENTS", nullable = true, length = 200)
	private String comments;

}
