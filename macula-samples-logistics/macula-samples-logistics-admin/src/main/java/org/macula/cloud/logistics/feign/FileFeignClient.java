package org.macula.cloud.logistics.feign;

import org.macula.cloud.logistics.feign.fallback.FileFeignClientFallback;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;

@FeignClient(name = "file-service", configuration = FileFeignClient.MultipartSupportConfig.class, fallback = FileFeignClientFallback.class)
public interface FileFeignClient {

	@PostMapping(value = "/v1/files", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<String> uploadFile(@RequestParam("bucket_name") String bucketName, @RequestParam("file_name") String fileName,
			@RequestPart("file") MultipartFile multipartFile);

	class MultipartSupportConfig {
		@Autowired
		private ObjectFactory<HttpMessageConverters> messageConverters;

		@Bean
		@Primary
		@Scope("prototype")
		public Encoder feignFormEncoder() {
			return new SpringFormEncoder(new SpringEncoder(messageConverters));
		}
	}
}
