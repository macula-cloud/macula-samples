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
				String token = null;
				while (!StringUtils.equals(token, "<EOF>")) {
					try {
						token = queue.take();
					} catch (InterruptedException e) {
						break;
					}
					if (!StringUtils.equals(token, "<EOF>")) {
						String[] keys = token.split("\\^");
						int id = Integer.valueOf(keys[0]);
						String obsKey = keys[1];
						String comments;

						try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
							webdavClient.download(obsKey, output);
							if (output.size() > 0) {
								obsClient.putObject(obsConfig.getBucket(), obsKey, new ByteArrayInputStream(output.toByteArray()));
								log.info("file: {} complete!", token);
								comments = "OK";
							} else {
								log.error("file: {} not exists!", token);
								comments = "NotExists";
							}
						} catch (IOException e) {
							e.printStackTrace();
							comments = "ERROR";
						}

						jdbcTemplate.update(
								"insert into sync_obs_result values(?, ?, ?) ON DUPLICATE KEY UPDATE obs_key = VALUES(obs_key), comments = VALUES(comments) ",
								id, obsKey, comments);

					}
				}
				countDownLatch.countDown();
				log.info("Thread {} complete!", Thread.currentThread().getName());
			});
		}
	}

	@Test
	public void testUploadFile() throws IOException, InterruptedException {
		int startPos = 0;
		try {
			startPos = jdbcTemplate.queryForObject("select max(id) from sync_obs_result", Integer.class);
		} catch (Exception ex) {
			// IGNORE
		}
		int size = 0;
		do {
			log.info("=================== from {} limit 100 ==================", startPos);
			List<Map<String, Object>> result = jdbcTemplate
					.queryForList("select id, server_path, file_name from sync_bos_file_list_a where id > ? order by id limit 100", startPos);
			for (Map<String, Object> map : result) {
				Integer rowId = (Integer) map.get("id");
				String path = (String) map.get("server_path");
				String fileName = (String) map.get("file_name");
				// String category = "\\iplatform\\72f337e1\\2a3e\\46ab\\bf5d\\4a5b3c0a3a52";
				// String file = "A084811935FE86B6CE79A8BA7A85154D40247FC6-44278.docx";
				if (rowId > startPos) {
					startPos = rowId;
				}
				String url = WebdavUtils.getFormatPath(path) + SymbolConstants.SLASH + fileName;
				log.info("id -> {} add file: {}", rowId, url);
				queue.put(rowId + "^" + url);
			}
			size = result.size();
			Thread.sleep(200);
		} while (size > 0);

		while (!countDownLatch.await(1000, TimeUnit.MILLISECONDS)) {
			log.info("Waiting threads [{}] complete.", countDownLatch.getCount());
			queue.put("<EOF>");
		}
	}

}
