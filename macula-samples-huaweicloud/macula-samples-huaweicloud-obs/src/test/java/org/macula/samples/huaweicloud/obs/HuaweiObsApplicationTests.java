package org.macula.samples.huaweicloud.obs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import com.obs.services.ObsClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.macula.engine.assistant.constants.SymbolConstants;
import org.macula.samples.huaweicloud.obs.configure.ObsConfig;
import org.macula.samples.huaweicloud.obs.webdav.WebdavClient;
import org.macula.samples.huaweicloud.obs.webdav.WebdavUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
@SpringBootTest(classes = HuaweiObsApplication.class)
public class HuaweiObsApplicationTests {

	@Autowired
	private ObsClient obsClient;

	@Autowired
	private ObsConfig obsConfig;

	@Autowired
	private WebdavClient webdavClient;

	@Autowired
	private ExecutorService executorService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private BlockingQueue<String> queue = new LinkedBlockingQueue<>();
	private CountDownLatch countDownLatch = new CountDownLatch(20);

	@PostConstruct
	public void createConsumerThreads() {
		for (int i = 0; i < 20; i++) {
			executorService.execute(() -> {
				log.info("Thread [{}] turns running!", Thread.currentThread().getName());
				String objectKey = null;
				while (!StringUtils.equals(objectKey, "<EOF>")) {
					try {
						objectKey = queue.take();
					} catch (InterruptedException e) {
						break;
					}
					if (!StringUtils.equals(objectKey, "<EOF>")) {
						try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
							webdavClient.download(objectKey, output);
							if (output.size() > 0) {
								obsClient.putObject(obsConfig.getBucket(), objectKey, new ByteArrayInputStream(output.toByteArray()));
								log.info("file: {} complete!", objectKey);
							} else {
								log.error("file: {} not exists!", objectKey);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				countDownLatch.countDown();
				log.info("Thread {} complete!", Thread.currentThread().getName());
			});
		}
	}

	@Test
	public void testUploadFile() throws IOException, InterruptedException {
		int id = 0, size = 0;
		do {
			log.info("=================== from {} limit 100 ==================", id);
			List<Map<String, Object>> result = jdbcTemplate
					.queryForList("select id, server_path, file_name from sync_bos_file_list_a where id > ? order by id limit 100", id);
			for (Map<String, Object> map : result) {
				Integer cId = (Integer) map.get("id");
				String category = (String) map.get("server_path");
				String fileName = (String) map.get("file_name");
				// String category = "\\iplatform\\72f337e1\\2a3e\\46ab\\bf5d\\4a5b3c0a3a52";
				// String file = "A084811935FE86B6CE79A8BA7A85154D40247FC6-44278.docx";
				if (cId > id) {
					id = cId;
				}
				String file = WebdavUtils.getFormatPath(category) + SymbolConstants.SLASH + fileName;
				log.info("id -> {} add file: {}", cId, file);
				queue.put(file);
			}
			size = result.size();
			Thread.sleep(1000);
		} while (size >= 100);
		while (!countDownLatch.await(1000, TimeUnit.MILLISECONDS)) {
			try {
				log.info("Watiing threads [{}] complete.", countDownLatch.getCount());
				queue.put("<EOF>");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
