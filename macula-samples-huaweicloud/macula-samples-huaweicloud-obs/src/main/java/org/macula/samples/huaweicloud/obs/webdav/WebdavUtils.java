package org.macula.samples.huaweicloud.obs.webdav;

import java.security.MessageDigest;

import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Hex;
import org.macula.engine.assistant.constants.SymbolConstants;

public class WebdavUtils {

	@SneakyThrows
	public static String getDigestFilename(byte[] content, String ext) {
		long size = content.length;
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		byte[] digest = md.digest(content);
		String hash = Hex.encodeHexString(digest).replace("-", "").toUpperCase();
		return String.format("%s-%d-v%d%s", hash, size, System.currentTimeMillis(), getExtension(ext));
	}

	public static String getFormatPath(String path) {
		return path.replaceAll("(-)|(\\\\)|(\\\\\\\\\\\\)|//", SymbolConstants.SLASH).toLowerCase();
	}

	public static String getExtension(String filename) {
		int index = filename.lastIndexOf(SymbolConstants.PERIOD);
		if (index < 0) {
			return SymbolConstants.PERIOD.concat(filename);
		}
		return filename.substring(index);
	}

}
