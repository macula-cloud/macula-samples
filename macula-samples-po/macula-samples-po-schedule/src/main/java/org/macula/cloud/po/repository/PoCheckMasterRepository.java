package org.macula.cloud.po.repository;

import java.util.Date;
import java.util.List;

import org.macula.cloud.po.domain.PoCheckMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * OMS和业务平台GBSS对账数据表
 */
public interface PoCheckMasterRepository extends JpaRepository<PoCheckMaster, Long> {

	/**
	 * 方法说明:查询对账表中最新的一条的endTime,作为下一次的startTime
	 * @return
	 */
	PoCheckMaster findFirstByOrderByEndTimeDesc();

	/**
	 * 查询OMS当前时间段的定时对账数据
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	PoCheckMaster findByStartTimeAndEndTime(Date startTime, Date endTime);

	/**
	 * 查询对账主表,根据创建时间升序获取所有对账失败数据
	 */
	@Query("from PoCheckMaster p where p.synStatus = '0' and p.checkNumber <3 order by p.createdDate asc")
	List<PoCheckMaster> findAllBySynStatus();
}
