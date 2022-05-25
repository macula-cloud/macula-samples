package org.macula.cloud.po.service;

import java.util.Date;
import java.util.List;

import org.macula.cloud.po.domain.PoCheckDetail;
import org.macula.cloud.po.domain.PoCheckMaster;

public interface OrderCheckingService {

	/**
	 * 获取当前对账开始时间
	 * @param beginTime  
	 * @return
	 */
	Date getCurrentCheckingStartTime(Date beginTime);

	/**
	 * 获取当前对账结束时间
	 * @param startTime 开始时间
	 * @param poCheckRange TODO
	 * @return
	 */
	Date getCurrentCheckingEndTime(Date startTime, int poCheckRange);

	/**
	 * 校验对账区间是否有效
	 * @param startTime
	 * @param endTime
	 * @param poValidateRange TODO
	 * @return
	 */
	boolean validateCheckingTime(Date startTime, Date endTime, int poValidateRange);

	/**
	 * 从GBSS获取对账数据(根据时间范围内的销售单据输入时间查询GBSS数据)
	 * startTime:开始:销售单据输入时间
	 * endTime:结束:销售单据输入时间
	 */
	List<PoCheckDetail> getGBSSBillingData(Date startTime, Date endTime);

	/**
	 * 从本地获取对账数据(根据时间范围内的销售单据输入时间查询本地数据)
	 * startTime:开始:销售单据输入时间
	 * endTime:结束:销售单据输入时间
	 */
	List<PoCheckDetail> findLocaleBillingData(Date startTime, Date endTime);

	/**
	 * 合并对账信息
	 * @param gbssDetails
	 * @param localeDetails
	 * @return
	 */
	List<PoCheckDetail> matchUpCheckDetails(List<PoCheckDetail> gbssDetails, List<PoCheckDetail> localeDetails);

	/**
	 * 创建对账信息记录
	 * @param startTime
	 * @param endTime
	 * @param matchUpDetails
	 * @return
	 */
	PoCheckMaster updateCheckingResult(Date startTime, Date endTime, List<PoCheckDetail> matchUpDetails);

	/**
	 * 更新对账状态
	 * @param poNo
	 */
	void updateCheckDetailAgain(String poNo);

	/**
	 * 方法说明：admin前端页面调用,执行对账忽略操作
	 * @param gbssPoNo:对账主明细表的gbssPoNo,必传参数
	 * @param checkMasterId:对账主表的ID
	 * @return
	 */
	PoCheckDetail successByHand(String gbssPoNo, Long checkMasterId);

}
