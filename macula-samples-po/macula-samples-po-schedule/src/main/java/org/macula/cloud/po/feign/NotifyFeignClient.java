package org.macula.cloud.po.feign;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.choerodon.core.notify.NoticeSendDTO;

@FeignClient(value = "notify-service", path = "/v1", fallback = NotifyFeignClientFallback.class)
public interface NotifyFeignClient {

	@PostMapping("/notices")
	void postNotice(@RequestBody @Valid NoticeSendDTO dto);

}