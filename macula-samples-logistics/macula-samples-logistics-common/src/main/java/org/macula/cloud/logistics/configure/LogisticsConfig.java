package org.macula.cloud.logistics.configure;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.macula.cloud.api.exception.ConvertException;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Setter;

@Setter
@ConfigurationProperties(prefix = "macula.cloud.logistics")
public class LogisticsConfig {

	private String initialEffectiveDate = "2020-09-01 16:00:00";
	private String initialInactiveDate = "2100-01-01 00:00:00";
	private String ignoreCodes = "710000,810000,820000,990000";

	public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

	public Date getInitialEffectiveDate() {
		try {
			return DateUtils.parseDateStrictly(initialEffectiveDate, PATTERN);
		} catch (ParseException e) {
			throw new ConvertException(e);
		}
	}

	public Date getInitialInactiveDate() {
		try {
			return DateUtils.parseDateStrictly(initialInactiveDate, PATTERN);
		} catch (ParseException e) {
			throw new ConvertException(e);
		}
	}

	public List<String> getIgnoreCodes() {
		List<String> ignores = new ArrayList<String>();
		if (ignoreCodes != null) {
			ignores = Arrays.asList(ignoreCodes.split(","));
		}
		return ignores;
	}

}
