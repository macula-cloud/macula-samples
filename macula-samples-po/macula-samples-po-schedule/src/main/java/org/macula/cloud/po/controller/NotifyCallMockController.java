package org.macula.cloud.po.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.macula.cloud.po.feign.NotifyFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.choerodon.core.notify.NoticeSendDTO;
import io.choerodon.core.notify.NoticeSendDTO.User;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/oms/notify")
@AllArgsConstructor
public class NotifyCallMockController {

	private NotifyFeignClient notifyClient;

	@GetMapping("/{message}")
	public ResponseEntity<?> sendMessage(@PathVariable("message") String message) {
		String messageCode = "oms-notice-upload-error";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("message", message);
		User from = new User();
		from.setId(14L);
		User target = new User();
		target.setId(14L);

		NoticeSendDTO vo = new NoticeSendDTO();
		vo.setParams(params);
		vo.setCode(messageCode);
		vo.setFromUser(from);
		vo.setTargetUsers(Arrays.asList(target));
		notifyClient.postNotice(vo);
		return ResponseEntity.ok("OK");
	};

}
