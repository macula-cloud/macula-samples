package org.macula.samples.huaweicloud.obs.webdav;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Date;
import java.util.List;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.jackrabbit.webdav.client.methods.HttpDelete;
import org.apache.jackrabbit.webdav.client.methods.HttpMkcol;
import org.macula.engine.assistant.constants.SymbolConstants;

import org.springframework.util.StreamUtils;

@Slf4j
public class WebdavClient {

	private String contextPath;
	private HttpHost host;
	private HttpClientContext httpContext;
	private CloseableHttpClient httpClient;

	/**
	 * @param endpoint    Webdav 地址
	 * @param username   登录用户名
	 * @param password   登录密码
	 */
	@SneakyThrows
	public WebdavClient(String endpoint, String username, String password) {
		URI uri = new URI(endpoint);
		this.host = new HttpHost(uri.getHost(), uri.getPort());
		this.contextPath = uri.getPath();

		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
		credentialsProvider.setCredentials(AuthScope.ANY, credentials);

		AuthCache authCache = new BasicAuthCache();
		BasicScheme basicScheme = new BasicScheme();
		authCache.put(host, basicScheme);

		httpContext = HttpClientContext.create();
		httpContext.setCredentialsProvider(credentialsProvider);
		httpContext.setAuthCache(authCache);

		httpClient = HttpClients.createDefault();

		log.debug("[Maula] |- Created webdav template:  {}", endpoint);
	}

	/**
	 * 创建目录
	 * 
	 * @param dir 为目录(/a/b/c) 或形如'a-b-c' 会转换为 a/b/c处理
	 */
	@SneakyThrows
	public void mkdir(String dir) {
		String[] dirs = WebdavUtils.getFormatPath(dir).split(SymbolConstants.SLASH);

		if (dirs.length == 1) {
			doCreateDirectory(getDirectory(dirs[0]));
		} else {
			StringBuilder path = null;
			for (String d : dirs) {
				if (d.isEmpty()) {
					continue;
				}
				if (path == null) {
					path = new StringBuilder(d);
				} else {
					path.append(SymbolConstants.SLASH.concat(d));
				}
				doCreateDirectory(getDirectory(path.toString()));
			}
		}
	}

	@SneakyThrows
	private Integer doCreateDirectory(String dir) {
		String requestPath = getRequestPath(dir);
		HttpMkcol mkcol = new HttpMkcol(requestPath);
		try (CloseableHttpResponse response = httpClient.execute(host, mkcol, httpContext)) {
			int retCode = response.getStatusLine().getStatusCode();
			log.debug("[Maula] |- Created webdav dir  {} , result: {}", requestPath, retCode);
			return retCode;
		}
	}

	private String getRequestPath(String path) {
		return contextPath.concat(path).replaceAll("//", SymbolConstants.SLASH);
	}

	/**
	 * 文件路径
	 * 
	 * @param dir 为目录(/a/b/c) 或形如'a-b-c' 会转换为 a/b/c处理
	 * 
	 * @return
	 */
	@SneakyThrows
	public String getDirectory(String dir) {
		return WebdavUtils.getFormatPath(dir);
	}

	/**
	 * 文件路径
	 * 
	 * @param dir      为目录(/a/b/c) 或形如'a-b-c' 会转换为 a/b/c处理
	 * @param filename 文件名
	 * 
	 * @return
	 */
	@SneakyThrows
	public String getPath(String dir, String filename) {
		return getDirectory(dir.concat(SymbolConstants.SLASH)).concat(filename);
	}

	/**
	 * 文件全路径地址
	 * 
	 * @param dir      为目录(/a/b/c) 或形如'a-b-c' 会转换为 a/b/c处理
	 * @param filename 文件名
	 * @return
	 */
	@SneakyThrows
	public String getLink(String root, String dir, String filename) {
		return this.host.toURI().concat(getRequestPath(getPath(dir, filename)));
	}

	/**
	 * 上传文件
	 * 
	 * @param dir    为目录(/a/b/c) 或形如'a-b-c' 会转换为 a/b/c处理
	 * @param ext    文件扩展名
	 * @param stream 文件流
	 * 
	 * @return
	 */
	@SneakyThrows
	public OssFile upload(String dir, String ext, InputStream stream) {
		mkdir(dir);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		IOUtils.copy(stream, out);
		byte[] content = out.toByteArray();
		String filename = WebdavUtils.getDigestFilename(content, ext);

		String fullPath = getPath(dir, filename);
		String requestPath = getRequestPath(fullPath);
		HttpPut put = new HttpPut(requestPath);
		InputStreamEntity entity = new InputStreamEntity(new ByteArrayInputStream(content));
		put.setEntity(entity);
		try (CloseableHttpResponse response = httpClient.execute(host, put, httpContext)) {
			log.info("[Maula] |- Upload webdav path: {} , result: {}", requestPath, response.getStatusLine());
			OssFile result = new OssFile();
			result.setLength(content.length);
			result.setLink(fullPath);
			result.setName(filename);
			result.setPutTime(new Date());
			return result;
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param path 为目录(/a/b/c) 或形如'a-b-c' 会转换为 a/b/c处理
	 */
	@SneakyThrows
	public void delete(String path) {
		String requestPath = getRequestPath(path);
		HttpDelete delete = new HttpDelete(requestPath);
		try (CloseableHttpResponse response = httpClient.execute(host, delete, httpContext)) {
			log.debug("[Maula] |-Delete webdav path: {} , result: {} ", requestPath, response.getStatusLine());
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param dir      为目录(/a/b/c) 或形如'a-b-c' 会转换为 a/b/c处理
	 * @param filename 文件名
	 */
	@SneakyThrows
	public void delete(String dir, String filename) {
		delete(getPath(dir, filename));
	}

	/**
	 * 删除文件
	 * 
	 * @param dir       为目录(/a/b/c) 或形如'a-b-c' 会转换为 a/b/c处理
	 * @param filenames 文件名清单
	 */
	@SneakyThrows
	public void delete(String dir, List<String> filenames) {
		filenames.forEach(filename -> delete(dir, filename));
	}

	/**
	 * 下载文件
	 * 
	 * @param dir      为目录(/a/b/c) 或形如'a-b-c' 会转换为 a/b/c处理
	 * @param filename 文件名
	 * @param out      输出流
	 */
	@SneakyThrows
	public void download(String dir, String filename, OutputStream out) {
		String path = getDirectory(filename);
		download(path, out);
	}

	@SneakyThrows
	public long download(String path, OutputStream out) {
		long length = 0;
		String requestPath = getRequestPath(path);
		HttpGet get = new HttpGet(requestPath);
		try (CloseableHttpResponse response = httpClient.execute(host, get, httpContext)) {
			int statusCode = response.getStatusLine().getStatusCode();
			log.info("[Maula] |- Download webdav file:  {} , result: {}", requestPath, statusCode);
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				length = entity.getContentLength();
				StreamUtils.copy(entity.getContent(), out);
				EntityUtils.consumeQuietly(entity);
			}
		}
		return length;
	}
}
