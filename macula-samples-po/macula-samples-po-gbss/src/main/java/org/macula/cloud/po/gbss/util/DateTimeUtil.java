package org.macula.cloud.po.gbss.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.macula.cloud.api.context.CloudApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 * <p>
 * <b>DateTimeUtil</b> 是
 * </p>
 *
 
 
 
 *
 */
public class DateTimeUtil {

	//private static SysParamInfoService sysParamInfoService = ApplicationContext.getBean("sysParamInfoServiceImpl");

	public static long secondDelta = -999999999; //本地毫秒-DB毫秒，其中毫秒都设置为0

	protected static long timeDiff = -1;

	public static final String dateDifference(long dateA, long dateB, long resolution) {
		long difference = Math.abs(dateB - dateA);
		return dateDifference(difference, resolution);
	}

	/**
	 * 获取时间间隔字符串
	 * 
	 * @param dateA
	 * @param dateB
	 * @param resolution 初始解析精度,比如resolution=1,表示只有间隔够一个月才会显示1月...，否则显示0月
	 * @return 时间间隔字符串
	 */
	public static final String dateDifference(long difference, long resolution) {
		StringBuffer sb = new StringBuffer();
		if (resolution > 0L) {
			resolution--;
			long months = difference / 0x9fa52400L;
			if (months > 0L) {
				difference %= 0x9fa52400L;
				sb.append(months + " 月, ");
			}
		}
		if (resolution <= 0L && sb.length() == 0)
			return "0 月";
		resolution--;
		long days = difference / 0x5265c00L;
		if (days > 0L) {
			difference %= 0x5265c00L;
			sb.append(days + " 天, ");
		}
		if (resolution <= 0L && sb.length() == 0)
			return "0 天";
		resolution--;
		long hours = difference / 0x36ee80L;
		if (hours > 0L) {
			difference %= 0x36ee80L;
			sb.append(hours + " 小时, ");
		}
		if (resolution <= 0L && sb.length() == 0)
			return "0 小时";
		resolution--;
		long minutes = difference / 60000L;
		if (minutes > 0L) {
			difference %= 60000L;
			sb.append(minutes + " 分钟, ");
		}
		if (resolution <= 0L && sb.length() == 0)
			return "0 分钟";
		resolution--;
		long seconds = difference / 1000L;
		if (seconds > 0L) {
			//			difference %= 1000L;
			sb.append(seconds + " 秒, ");
		}
		if (resolution <= 0L && sb.length() == 0)
			return "0 秒";
		if (sb.length() > 2)
			return sb.substring(0, sb.length() - 2);
		else
			return "";
	}

	public static Date getCurrentTime() {

		// ValueWrapper value = CacheUtils.getInstanceCache().get("_TIMEDIFF_");

		//if (value != null) {
		// modify by yx 直接查数据库  不采用缓存方案了
		JdbcTemplate jdbcTemplate = (JdbcTemplate) CloudApplicationContext.getBean(JdbcTemplate.class);
		Date t = jdbcTemplate.query("select systimestamp from dual", new ResultSetExtractor<Date>() {

			@Override
			public Date extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs != null) {
					if (rs.next()) {
						return rs.getTimestamp(1);
					}
				}
				return null;
			}

		});
		return t;
		//	timeDiff = t.getTime() - (new Date()).getTime();
		//	CacheUtils.getInstanceCache().put("_TIMEDIFF_", Long.valueOf(timeDiff));
		//}

		//return new Date(new Date().getTime() + timeDiff);
	}

	/** 获取当前得日期，没有时分秒 */
	public static Date getToday() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获得当前月度（自然月）
	 * @return
	 */
	public static String getCurrentPeriod() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		return dateFormat.format(new Date());
	}

	/**
	 * 根据date获得月度（自然月）
	 * @return
	 */
	public static String getPeriod(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		return dateFormat.format(date);
	}

	/**
	 * 获得当前日期 
	 * @param format 格式
	 * @return
	 */
	public static String getCurrentDate(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(new Date());
	}

	/**
	 * 获得当月的前preMonth个月份
	 * @return
	 */
	public static String getPrePeriod(int preMonth) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, -preMonth);
		String prePeriod = calendar.get(Calendar.YEAR) + "";
		if (calendar.get(Calendar.MONTH) < 10) {
			prePeriod += "0" + calendar.get(Calendar.MONTH);
		} else {
			prePeriod += calendar.get(Calendar.MONTH);
		}
		return prePeriod;
	}

	/**
	 * 获得当月的后preMonth个月份
	 * @return
	 */
	public static String getAfterPeriod(int preMonth) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, preMonth);
		String prePeriod = calendar.get(Calendar.YEAR) + "";
		if (calendar.get(Calendar.MONTH) < 10) {
			prePeriod += "0" + calendar.get(Calendar.MONTH);
		} else {
			prePeriod += calendar.get(Calendar.MONTH);
		}
		return prePeriod;
	}

	//	public static void main(String args[]) {
	//
	//		String period = getPrePeriod(0);
	//		System.out.println(period);
	//
	//	}

	/** 月份字符串加月 */
	public static String addMonthOnPeriod(String period, int month) {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		String afterPeriod = period;
		try {
			Date date = dateFormat.parse(period);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MONTH, month);
			afterPeriod = dateFormat.format(cal.getTime());
		} catch (ParseException e) {
			System.out.println("wrong message=" + e.getMessage());
		}
		return afterPeriod;
	}

	/**
	 * 检测给定的日期是否在两个日期之间（包括开始及结束日期，只比较日期，不比较时间部分）
	 * 即：
	 * startDate <= givenDate <= endDate
	 * @param givenDate
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean isDateBetween(Date givenDate, Date startDate, Date endDate) {
		return (givenDate.after(startDate) || DateUtils.isSameDay(givenDate, startDate))
				&& (givenDate.before(endDate) || DateUtils.isSameDay(givenDate, endDate));
	}

	/**
	 * 获取当天的小时时间
	 * 案例场景：判断当天的9点前，获取当天9点的时间，格式为：20XX-XX-XX 09:00:00    Date类型
	 * @param format
	 * @param hour
	 * @return
	 */
	public static Date getCurrentDateHour(int hour) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	/**
	 * format
	 * @return
	 */
	public static String format(Date date, String p) {
		DateFormat dateFormat = new SimpleDateFormat(p);
		return dateFormat.format(date);
	}

	/**
	 * 取开始时间
	 * @param date
	 * @return
	 */
	public static Date getStartOfDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 取结束时间
	 * @param date
	 * @return
	 */
	public static Date getEndOfDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	/** 
	* 获得指定日期的前一天 
	*  
	* @param specifiedDay 
	* @return 
	* @throws Exception 
	*/
	public static String getSpecifiedDayBefore(String specifiedDay) {//可以用new Date().toLocalString()传递参数  
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayBefore;
	}

	/** 
	 * 获得指定日期的后一天 
	 *  
	 * @param specifiedDay 
	 * @return 
	 */
	public static String getSpecifiedDayAfter(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayAfter;
	}

	/**
	 * 获取当前日期  只有年月日 没有时分秒
	 * @return
	 */
	public static Date getCurDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取当前时间 Date类型，包含时分秒  （对于时间要求很精确的场景才使用此方法，比如支付业务，不需要精确到时分秒的场景请使用getCurDate()方法）
	 * @return
	 */
	public static Date getCurDateTime() {
		if (secondDelta == -999999999) {
			JdbcTemplate jdbcTemplate = (JdbcTemplate) CloudApplicationContext.getBean(JdbcTemplate.class);
			if (jdbcTemplate != null) {
				Date dbDate = jdbcTemplate.query("select sysdate from dual", new ResultSetExtractor<Date>() {

					@Override
					public Date extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs != null) {
							if (rs.next()) {
								return rs.getTimestamp(1);
							}
						}
						return null;
					}

				});
				Calendar cal = Calendar.getInstance();
				long localMillis = cal.getTimeInMillis();
				long dbMillis = dbDate.getTime();
				secondDelta = localMillis - dbMillis;
				return dbDate;
			} else {
				return new Date();
			}
		} else {
			Calendar cal = Calendar.getInstance();
			long localMillis = cal.getTimeInMillis();
			long dbMillis = localMillis - secondDelta;
			cal.setTimeInMillis(dbMillis);
			return cal.getTime();
		}
	}

	public static String getPeriod(int periodIndex) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, periodIndex);
		Date m = c.getTime();
		String mon = format.format(m);
		return mon;
	}
}
