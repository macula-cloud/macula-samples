package org.macula.cloud.logistics.feign.fallback;

import org.macula.cloud.logistics.feign.FileFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import io.choerodon.core.exception.CommonException;

@Component
public class FileFeignClientFallback implements FileFeignClient {

	private static final String MSG_ERROR_UPLOAD = "error.file.upload";

	@Override
	public ResponseEntity<String> uploadFile(String bucketName, String fileName, MultipartFile multipartFile) {
		throw new CommonException(MSG_ERROR_UPLOAD);
	}
}
