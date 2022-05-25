package org.macula.cloud.po.listener;

import org.macula.cloud.core.utils.SystemUtils;
import org.macula.cloud.po.configure.OMSConfig;
import org.macula.cloud.po.domain.PoProcessMaster;
import org.macula.cloud.po.event.OrderProcessFailedEvent;
import org.macula.cloud.po.service.OrderMessageService;
import org.macula.cloud.po.service.OrderProcessingService;
import org.macula.cloud.po.type.PoProcessStatus;
import org.macula.cloud.po.vo.StatusChangeRequest;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class OrderProcessFailedListener extends OrderProcessEventListener<OrderProcessFailedEvent> {
	private OrderProcessingService orderProcessingService;
	private OrderMessageService orderMessageService;
	private OMSConfig config;

	@Override
	public void doApplicationEvent(OrderProcessFailedEvent event) {
		String poNo = event.getSource();
		PoProcessMaster poProcessMaster = orderProcessingService.getProcessMaster(poNo);
		// 当前状态
		if (poProcessMaster == null) {
			return;
		}
		String status = poProcessMaster.getStatus();
		// 出错次数
		int errorTimes = poProcessMaster.getErrorTimes();

		if (PoProcessStatus.hasError(status) && errorTimes >= config.getMaxRetryTimes()) {
			if (!PoProcessStatus.hasPause(status)) {
				StatusChangeRequest request = StatusChangeRequest.builder().poNo(poNo).statusTime(SystemUtils.getCurrentTime())
						.status(PoProcessStatus.PAUSE).comments("失败次数过多暂停推送！").build();
				Boolean handleResult = orderProcessingService.handleStatusChange(poNo, request);
				if (log.isInfoEnabled()) {
					log.info("StatusStartRequest {} handleResult {}", request, handleResult);
				}
			}
			// 发送通知级别
			String level = "L" + (errorTimes > 3 ? 3 : poProcessMaster.getErrorTimes());
			String message = String.format("订单 %s 处理次数过多：%s", poNo, poProcessMaster.getComments());
			// 调用短信接口,发送告警短信
			orderMessageService.sendErrorMessage(level, message);
		}
	}

}
