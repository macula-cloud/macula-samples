package org.macula.cloud.po.service;

import java.util.List;

import org.macula.cloud.po.domain.PoMessageLevel;

public interface OrderMessageService {

	/**
	 * 根据级别查询对应的数据
	 * @param level
	 * @return
	 */
	List<PoMessageLevel> findMessageLevel(String level);

	/**
	 * 按指定级别发送错误信息
	 * @param level:告警级别
	 * @param errorMessage：告警内容
	 */
	void sendErrorMessage(String level, String errorMessage);

}
