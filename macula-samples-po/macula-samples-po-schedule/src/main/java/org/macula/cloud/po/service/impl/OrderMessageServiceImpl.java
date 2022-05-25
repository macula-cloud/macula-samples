package org.macula.cloud.po.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.macula.cloud.po.configure.MessageConfig;
import org.macula.cloud.po.domain.PoMessageLevel;
import org.macula.cloud.po.feign.NotifyFeignClient;
import org.macula.cloud.po.repository.PoMessageLevelRepository;
import org.macula.cloud.po.service.OrderMessageService;
import org.macula.cloud.po.service.SendMessage2Delegate;
import org.springframework.stereotype.Service;

import io.choerodon.core.notify.NoticeSendDTO;
import io.choerodon.core.notify.NoticeSendDTO.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class OrderMessageServiceImpl implements OrderMessageService {

	private MessageConfig messageConfig;
	private PoMessageLevelRepository poMessageLevelRepository;
	private SendMessage2Delegate sendMessage2Delegate;

	private NotifyFeignClient notifyClient;

	/**
	 * 根据级别查询对应的数据
	 * @param level
	 * @return
	 */
	@Override
	public List<PoMessageLevel> findMessageLevel(String level) {
		List<PoMessageLevel> poMessageLevelList = poMessageLevelRepository.findByLevel(level);
		return poMessageLevelList;
	}

	/**
	 * 按指定级别发送错误信息
	 * @param level
	 * @param message
	 */
	@Override
	public void sendErrorMessage(String level, String message) {
		if (log.isDebugEnabled()) {
			log.debug("Send message: ", message);
		}
		List<PoMessageLevel> levelUsers = findMessageLevel(level);
		for (PoMessageLevel poMessageLevel : levelUsers) {
			try {
				message = String.format("%s : %s", poMessageLevel.getUserName(), message);
				NoticeSendDTO target = createUploadMessageVo(poMessageLevel, message);
				if (target != null) {
					notifyClient.postNotice(target);
				}
				sendMessage2Delegate.send(poMessageLevel.getMobile(), message, messageConfig.getPassword(), messageConfig.getUsername());
			} catch (Exception ex) {
				log.error("Send message error: ", ex);
			}
		}
	}

	protected NoticeSendDTO createUploadMessageVo(PoMessageLevel poMessageLevel, String message) {
		if (poMessageLevel == null) {
			return null;
		}
		User target = new User();
		if (poMessageLevel.getUserId() != null) {
			target.setId(poMessageLevel.getUserId());
		}
		if (poMessageLevel.getEmail() != null) {
			target.setEmail(poMessageLevel.getEmail());
		}
		String messageCode = "oms-notice-upload-error";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("message", message);
		NoticeSendDTO vo = new NoticeSendDTO();
		vo.setParams(params);
		vo.setCode(messageCode);
		vo.setTargetUsers(Arrays.asList(target));
		return vo;
	}
}
