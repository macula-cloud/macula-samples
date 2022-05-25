package org.macula.cloud.logistics.service;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import io.choerodon.core.exception.CommonException;
import lombok.extern.slf4j.Slf4j;

/**
 
 */
@Service
@Slf4j
public class TemplateService {

	public ResponseEntity<Resource> downloadTemplates(String templateName, String fileName) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		headers.add("charset", "utf-8");
		//设置下载文件名
		String filename = fileName + ".xls";
		try {
			filename = URLEncoder.encode(filename, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.info("url encodes exception: {}", e.getMessage());
			throw new CommonException("error.encode.url");
		}
		headers.add("Content-Disposition", "attachment;filename=\"" + filename + "\"");
		InputStream inputStream;
		inputStream = this.getClass().getResourceAsStream("/templates/" + templateName + ".xls");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
				.body(new InputStreamResource(inputStream));
	}

}
