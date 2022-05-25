package org.macula.cloud.po.feign;

import io.choerodon.core.exception.CommonException;
import io.choerodon.core.notify.NoticeSendDTO;

public class NotifyFeignClientFallback implements NotifyFeignClient {

	private static final String FEIGN_ERROR = "notify.error";

	@Override
	public void postNotice(NoticeSendDTO dto) {
		throw new CommonException(FEIGN_ERROR);
	}

}
