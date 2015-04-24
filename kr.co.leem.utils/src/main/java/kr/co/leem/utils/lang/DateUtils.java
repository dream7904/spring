package kr.co.leem.utils.lang;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TimeZone;

import kr.co.leem.utils.validation.ValidationUtils;

import org.joda.time.DateTime;
import org.joda.time.DurationFieldType;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.Assert;

/**
 * Date Utils.
 *
 * @author 임성천.
 */
public class DateUtils {
	/**
	 * 기본 날짜 패턴.
	 */
	public static final String DATE_PATTERN_DASH = "yyyy-MM-dd";
	
	/**
	 * 기본 시간  패턴.
	 */
	public static final String TIME_PATTERN = "HH:mm";
	
	/**
	 * 날짜, 시간 패턴.
	 */
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 날짜 HMS 패턴.
	 */
	public static final String DATE_HMS_PATTERN = "yyyyMMddHHmmss";
	
	/**
	 * Time stamp 패턴.
	 */
	public static final String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
	
	// Enterprise ================================================================
	
	/**
	 * 년 (yyyy) 패턴
	 */
	public static final String YEAR_PATTERN = "yyyy";
	
	/**
	 * 월(MM) 패턴
	 */
	public static final String MONTH_PATTERN = "MM";
	
	/**
	 * 일(dd) pattern
	 */
	public static final String DAY_PATTERN = "dd";
	
	/**
	 * 날짜(yyyyMMdd) pattern
	 */
	public static final String DATE_PATTERN = "yyyyMMdd";
	
	/**
	 * 시간(HHmmss) 패턴
	 */
	public static final String TIME_HMS_PATTERN = "HHmmss";
	
	/**
	 * 시간(HH:mm:ss) 패턴
	 */
	public static final String TIME_HMS_PATTERN_COLONE = "HH:mm:ss";
	
	/**
	 * 평년인 경우, 매 월 일자 수
	 */
	private static final int[] lastDayOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	/**
	 * 윤년인 경우, 매 월 일자 수
	 */
	private static int[] lastDayOfMonthForLeapYear = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	/**
	 * 현재 날짜를 반환. (yyyy-MM-dd HH:mm:ss)
	 *
	 * @return 현재 날짜. (yyyy-MM-dd HH:mm:ss)
	 */
	public static String getCurrentDateTimeString() {
		return getCurrentDateTimeString(DATE_TIME_PATTERN);
	}
	
	/**
	 * 현재 시간을 반환.
	 *
	 * @param pattern 날짜 형식
	 * @return String representing current time (type of pattern)
	 */
	public static String getCurrentDateTimeString(String pattern) {
		DateTime dt = new DateTime();
		DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);

		return fmt.print(dt);
	}
	
	/**
	 * 현재 날짜 년/월까지 반환.
	 *
	 * @return 현재날짜. (yyyy-MM)
	 */
	public static String getThisMonth() {
		return getCurrentDateTimeString("yyyy-MM");
	}
	
	/**
	 * 현재 해를 반환.
	 *
	 * @return 현재 해.(yyyy)
	 */
	public static String getThisYear() {
		return getCurrentDateTimeString("yyyy");
	}
	
	/**
	 * 입력된 날짜에 대한 요일을 반환함.
	 * 예) DateUtil.getDayOfWeek(&quot;2011-04-15&quot;) = &quot;일&quot;;
	 *
	 * @param str 날짜(yyyy-MM-dd)
	 * @return 입력된 날짜에 대한 요일.
	 */
	public static String getDayOfWeek(String str) {
		return getDayOfWeek(str, true, LocaleContextHolder.getLocale());
	}
	
	/**
	 * 입력된 날짜에 대한 요일을 반환함.
	 * <p/>
	 * 예) DateUtil.getDayOfWeek(&quot;2011-04-15&quot;, true, Locale.US) = &quot;일&quot;;
	 *
	 * @param str          날짜(yyyy-MM-dd)
	 * @param abbreviation 축약형 여부.
	 * @param locale
	 * @return 입력된 날짜에 대한 요일.
	 */
	public static String getDayOfWeek(String str, Boolean abbreviation, Locale locale) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern(DATE_PATTERN_DASH);
		DateTime dt = fmt.parseDateTime(str);
		DateTime.Property dayOfWeek = dt.dayOfWeek();
		
		if (abbreviation)
			return dayOfWeek.getAsShortText(locale);
		else
			return dayOfWeek.getAsText(locale);
	}
	
	/**
	 * 두 날짜 사이의 일수 차이를 반환.
	 * cal1: 2005-08-15, cal2: 2005-09-14 => 30 days)
	 *
	 * @param fromDate 시작일.
	 * @param toDate   종료일.
	 * @return 두 날짜 사이의 일수 차이.
	 */
	public static int getDays(Calendar fromDate, Calendar toDate) {
		long min = getMinutes(fromDate, toDate);
		
		return (int) (min / (24 * 60));
	}
	
	/**
	 * 두 날짜 사이의 일수 차이를 반환.
	 *
	 * @param fromDate 시작일.(yyyy-MM-dd)
	 * @param toDate   종료일.(yyyy-MM-dd)
	 * @return 두 날짜 사이의 일수 차이.
	 */
	public static int getDays(String fromDate, String toDate) {
		return getDays(fromDate, toDate, DATE_HMS_PATTERN);
	}
	
	/**
	 * 두 날짜 사이의 일수 차이를 반환.
	 *
	 * @param fromDate 시작일.
	 * @param toDate   종료일.
	 * @param pattern   날짜 형식.
	 * @return 두 날짜 사이의 일수 차이.
	 */
	public static int getDays(String fromDate, String toDate, String pattern) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
		
		DateTime fromDateTime = fmt.parseDateTime(fromDate);
		DateTime toDateTime = fmt.parseDateTime(toDate);
		
		long fromMillis = fromDateTime.getMillis();
		long toMillis = toDateTime.getMillis();
		
		int fromDays = (int) (fromMillis / (60 * 60 * 1000 * 24));
		int toDays = (int) (toMillis / (60 * 60 * 1000 * 24));
		
		return toDays - fromDays;
	}
	
	/**
	 * 두 날짜가 같은지를 비교함.
	 *
	 * @param sourceDate 기준날짜.
	 * @param targetDate 문자열로 구성된 날짜. (yyyy-MM-dd)
	 * @return 같으면 true, 다르면 false.
	 */
	public static boolean equals(Date sourceDate, String targetDate) {
		return equals(sourceDate, targetDate, DATE_PATTERN_DASH);
	}
	
	/**
	 * 두 날짜가 같은지를 비교함.
	 *
	 * @param sourceDate        기준날짜.
	 * @param targetDate        문자열로 구성된 날짜. (yyyy-MM-dd)
	 * @param targetDatePattern 날짜 패턴.
	 * @return 같으면 true, 다르면 false.
	 */
	public static boolean equals(Date sourceDate, String targetDate, String targetDatePattern) {
		Date date = string2Date(targetDate, targetDatePattern);

		return equals(sourceDate, date);
	}
	
	/**
	 * 두 날짜가 같은지를 비교함.
	 *
	 * @param sourceDate 기준날짜.
	 * @param targetDate 비교할 날짜.
	 * @return 같으면 true, 다르면 false.
	 */
	public static boolean equals(Date sourceDate, Date targetDate) {
		if (sourceDate.getTime() == targetDate.getTime()) {
			return true;
		}

		return false;
	}
	
	/**
	 * 두 날짜의 크기 비교. (크거나 같은 경우.)
	 *
	 * @param sourceDate 기준날짜.
	 * @param targetDate 비교할 날짜. (yyyy-MM-dd)
	 * @return 기준날짜보가 비교할 날짜가 크면 true, 아니면 false.
	 */
	public static boolean greaterThan(Date sourceDate, String targetDate) {
		return greaterThan(sourceDate, targetDate, DATE_PATTERN_DASH);
	}
	
	/**
	 * 두 날짜의 크기 비교. (크거나 같은 경우.)
	 *
	 * @param sourceDate        기준날짜.
	 * @param targetDate        비교할 날짜. (yyyy-MM-dd)
	 * @param targetDatePattern 비교날짜의 날짜 패턴.
	 * @return 기준날짜보가 비교할 날짜가 크면 true, 아니면 false.
	 */
	public static boolean greaterThan(Date sourceDate, String targetDate, String targetDatePattern) {
		Date date = string2Date(targetDate, targetDatePattern);

		return greaterThan(sourceDate, date);
	}
	
	/**
	 * 두 날짜의 크기 비교. (크거나 같은 경우.)
	 *
	 * @param sourceDate 기준날짜.
	 * @param targetDate 비교할 날짜.
	 * @return 기준날짜보가 비교할 날짜가 크면 true, 아니면 false.
	 */
	public static boolean greaterThan(Date sourceDate, Date targetDate) {
		if (sourceDate.getTime() > targetDate.getTime()) {
			return true;
		}

		return false;
	}
	
	/**
	 * 두 날짜의 크기 비교. (크거나 같은 경우.)
	 *
	 * @param sourceTimestamp 기준날짜.
	 * @param targetTimestamp 비교할 날짜.
	 * @return 기준날짜보가 비교할 날짜가 크면 true, 아니면 false.
	 */
	public static boolean greaterThan(Timestamp sourceTimestamp, Timestamp targetTimestamp) {
		if (sourceTimestamp.getTime() > targetTimestamp.getTime()) {
			return true;
		}

		return false;
	}
	
	/**
	 * 두 날짜의 크기 비교. (크거나 같은 경우.)
	 *
	 * @param sourceTimestamp 기준날짜.
	 * @param targetTimestamp 비교할 날짜. (yyyy-MM-dd)
	 * @return 기준날짜보가 비교할 날짜가 크면 true, 아니면 false.
	 */
	public static boolean greaterThan(Timestamp sourceTimestamp, String targetTimestamp) {
		return greaterThan(sourceTimestamp, targetTimestamp, TIMESTAMP_PATTERN);
	}
	
	/**
	 * 두 날짜의 크기 비교. (크거나 같은 경우.)
	 *
	 * @param sourceTimestamp 기준날짜.
	 * @param targetTimestamp 비교할 날짜. (yyyy-MM-dd)
	 * @param targetTimestamp 날짜 패턴
	 * @return 기준날짜보가 비교할 날짜가 크면 true, 아니면 false.
	 */
	public static boolean greaterThan(Timestamp sourceTimestamp, String targetTimestamp, String targetTimestampPattern) {
		Timestamp date = string2Timestamp(targetTimestamp, targetTimestampPattern);

		return greaterThan(sourceTimestamp, date);
	}
	
	/**
	 * 종료일 반환.
	 *
	 * @param fromDay     시작일 (yyyy-MM-dd)
	 * @param intervalDays 날짜간격.
	 * @return 특정 숫자를 더한 날짜. (yyyy-MM-dd)
	 */
	public static String getEndDate(String fromDay, int intervalDays) {
		StringTokenizer st = new StringTokenizer(fromDay, "-");

		int year = 0;
		int mon = 0;
		int day = 0;

		for (int i = 0; st.hasMoreTokens(); i++) {
			if (i == 0) {
				year = Integer.parseInt(st.nextToken());
			}

			if (i == 1) {
				String sMon = st.nextToken();

				if (sMon.startsWith("0")) {
					sMon = sMon.substring(1);
				}
				
				mon = Integer.parseInt(sMon);
			}

			if (i == 2) {
				String sDay = st.nextToken();

				if (sDay.startsWith("0")) {
					sDay = sDay.substring(1);
				}

				day = Integer.parseInt(sDay);
			}
		}

		DateTime start = new DateTime(year, mon, day, 0, 0, 0, 0);
		
		Period p1 = new Period(20 * 86400000);
		Period p2 = new Period((intervalDays - 20) * 86400000);
		
		DateTime end = start.plus(p1);
		
		end = end.plus(p2);
		year = end.getYear();
		mon = end.getMonthOfYear();
		day = end.getDayOfMonth();
		
		String xMon = "";
		String xDay = "";
		
		if (mon < 10) {
			xMon = "0" + (new Integer(mon)).toString();
		} else {
			xMon = (new Integer(mon)).toString();
		}
		
		if (day < 10) {
			xDay = "0" + (new Integer(day)).toString();
		} else {
			xDay = (new Integer(day)).toString();
		}
		
		return (new Integer(year)).toString() + "-" + xMon + "-" + xDay;
	}
	
	/**
	 * 일수를 더한 날짜 반환.
	 *
	 * @param str  시작일. (yyyy-MM-dd)
	 * @param days 더할 일 수.
	 * @return 특정 숫자를 더한 날짜.
	 */
	public static String addDays(String str, int days) {
		if (days == 0) {
			return str;
		}
		
		DateTimeFormatter fmt = DateTimeFormat.forPattern(DATE_PATTERN_DASH);
		DateTime dt = fmt.parseDateTime(str);
		DateTime subtracted = dt.withFieldAdded(DurationFieldType.days(), days);
		
		return fmt.print(subtracted);
	}
	
	/**
	 * 월수를 더한 날짜 계산.
	 *
	 * @param str    기준날짜. (yyyy-MM-dd)
	 * @param months 더할 월수 .
	 * @return 특정 월를 더한 날짜.
	 */
	public static String addMonths(String str, int months) {
		if (months == 0) {
			return str;
		}
		
		DateTimeFormatter fmt = DateTimeFormat.forPattern(DATE_PATTERN_DASH);
		DateTime dt = fmt.parseDateTime(str);
		DateTime subtracted = dt.withFieldAdded(DurationFieldType.months(), months);
		
		return fmt.print(subtracted);
	}
	
	/**
	 * 년수를 더한 날짜 계산.
	 *
	 * @param str   기준날짜 (yyyy-MM-dd)
	 * @param years 더할 년수.
	 * @return String calculated date
	 */
	public static String addYears(String str, int years) {
		if (years == 0) {
			return str;
		}
		
		DateTimeFormatter fmt = DateTimeFormat.forPattern(DATE_PATTERN_DASH);
		DateTime dt = fmt.parseDateTime(str);
		DateTime subtracted = dt.withFieldAdded(DurationFieldType.years(), years);
		
		return fmt.print(subtracted);
	}
	
	/**
	 * 년, 월, 일을 더한 날짜 계산.
	 *
	 * @param str    기준 날짜. (yyyy-MM-dd)
	 * @param years  더할 년수.
	 * @param months 더할 월수.
	 * @param days   더할 일수.
	 * @return 계산된 날짜.
	 */
	public static String addYearMonthDay(String str, int years, int months, int days) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern(DATE_PATTERN_DASH);
		DateTime dt = fmt.parseDateTime(str);
		
		if (years != 0)
			dt = dt.withFieldAdded(DurationFieldType.years(), years);
		if (months != 0)
			dt = dt.withFieldAdded(DurationFieldType.months(), months);
		if (days != 0)
			dt = dt.withFieldAdded(DurationFieldType.days(), days);
		
		return fmt.print(dt);
	}
	
	/**
	 * 입력된 날짜값에 대한 첫 날짜를 반환.
	 * 예) 2012-04-15 => 2012-04-01
	 *
	 * @param str 기준날짜 (yyyy-MM-dd)
	 * @return 날짜값에 대한 첫 날짜.
	 */
	public static String getFirstDateOfMonthString(String str) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern(DATE_PATTERN_DASH);
		DateTime dt = fmt.parseDateTime(str);
		DateTime dtRet = new DateTime(dt.getYear(), dt.getMonthOfYear(), 1, 0, 0, 0, 0);
		
		return fmt.print(dtRet);
	}
	
	/**
	 * 날짜값에 대한 마지막 날짜를 반환.
	 *
	 * @param str 기준날짜 (yyyy-MM-dd)
	 * @return 날짜값에 대한 마지막 날짜.
	 */
	public static String getLastDateOfMonthString(String str) {
		String firstDateOfMonth = getFirstDateOfMonthString(str);
		
		DateTimeFormatter fmt = DateTimeFormat.forPattern(DATE_PATTERN_DASH);
		DateTime dt = fmt.parseDateTime(firstDateOfMonth);
		dt = dt.plusMonths(1).minusDays(1);
		
		return fmt.print(dt);
	}
	
	/**
	 * 날짜값에 대한 이전달의 첫번째 날짜를 반환.
	 *
	 * @param str 기준날짜 (yyyy-MM-dd)
	 * @return 날짜값에 대한 이전달의 첫번째 날짜.
	 */
	public static String getFirstDateOfPrevMonthString(String str) {
		String firstDateOfMonth = getFirstDateOfMonthString(str);
		
		DateTimeFormatter fmt = DateTimeFormat.forPattern(DATE_PATTERN_DASH);
		DateTime dt = fmt.parseDateTime(firstDateOfMonth);
		dt = dt.minusMonths(1);
		
		return fmt.print(dt);
	}
	
	/**
	 * 날짜값에 대한 이전달의 마지막 날짜를 반환.
	 *
	 * @param str 기준날짜 (yyyy-MM-dd)
	 * @return 날짜값에 대한 이전달의 마지막 날짜
	 */
	public static String getLastDateOfPrevMonthString(String str) {
		String firstDateOfMonth = getFirstDateOfMonthString(str);
		
		DateTimeFormatter fmt = DateTimeFormat.forPattern(DATE_PATTERN_DASH);
		DateTime dt = fmt.parseDateTime(firstDateOfMonth);
		dt = dt.minusDays(1);
		
		return fmt.print(dt);
	}
	
	/**
	 * 입력된 값이 날짜 형식인지 체크.
	 *
	 * @param str 날짜값. (yyyy-MM-dd)
	 * @return 날짜패턴인 경우 true.
	 */
	public static boolean isDate(String str) {
		return isDate(str, DATE_PATTERN_DASH);
	}
	
	/**
	 * 입력된 값이 날짜 형식인지 체크.
	 *
	 * @param str     날짜값.
	 * @param pattern 날짜 패턴.
	 * @return 날짜패턴인 경우 true.
	 */
	public static boolean isDate(String str, String pattern) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
		DateTime dt = new DateTime();
		dt = fmt.parseDateTime(str);
		
		if (fmt.print(dt).equals(str) == false) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 입력된 값이 시간 형식인지 체크.
	 *
	 * @param str 시간값. (HH:mm)
	 * @return 시간패턴인 경우 true.
	 */
	public static boolean isTime(String str) {
		return isTime(str, TIME_PATTERN);
	}
	
	/**
	 * 입력된 값이 시간 형식인지 체크.
	 *
	 * @param str     시간값.
	 * @param pattern 시간 패턴.
	 * @return 시간패턴인 경우 true.
	 */
	public static boolean isTime(String str, String pattern) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
		DateTime dt = new DateTime();
		dt = fmt.parseDateTime(str);
		
		if (fmt.print(dt).equals(str) == false) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 문자열을 날짜(<code>java.util.Date</code>)형식으로 변환.
	 *
	 * @param str 날짜값. (yyyy-MM-dd)
	 * @return <code>java.util.Date</code>
	 */
	public static Date string2Date(String str) {
		return string2Date(str, DATE_PATTERN_DASH);
	}
	
	/**
	 * 문자열을 날짜(<code>java.util.Date</code>)형식으로 변환.
	 *
	 * @param str     날짜값.
	 * @param pattern 날짜패턴.
	 * @return <code>java.util.Date</code>
	 */
	public static Date string2Date(String str, String pattern) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
		return fmt.parseDateTime(str).toDate();
	}
	
	/**
	 * 날짜값을 문자열로 변환.
	 *
	 * @param date 날짜값.
	 * @return 변환된 날짜. (yyyy-MM-dd)
	 */
	public static String date2String(Date date) {
		return date2String(date, DATE_PATTERN_DASH);
	}
	
	/**
	 * 날짜값을 문자열로 변환.
	 *
	 * @param date    날짜값.
	 * @param pattern 날짜 패턴.
	 * @return 변환된 날짜.
	 */
	public static String date2String(Date date, String pattern) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
		
		return fmt.print(date.getTime());
	}
	
	/**
	 * 날짜 형태의 문자열의 날짜 패턴 변환.
	 * <p/>
	 * <pre>
	 * DateUtil.string2String("20120612", "yyyyMMdd", "yyyy-MM-dd") = "2012-06-12"
	 * DateUtil.string2String("2012.06.12", "yyyy.MM.dd", "yyyy/MM/dd") = "2012/06/12"
	 * </pre>
	 *
	 * @param str           string : 날짜값.
	 * @param basePattern   : 입력된 날짜 값의 패턴.
	 * @param wantedPattern : 변환하고자 하는 날짜값의 패턴.
	 */
	public static String string2String(String str, String basePattern, String wantedPattern) {
		DateTimeFormatter basefmt = DateTimeFormat.forPattern(basePattern);
		DateTimeFormatter wantedfmt = DateTimeFormat.forPattern(wantedPattern);
		DateTime dt = basefmt.parseDateTime(str);
		
		return wantedfmt.print(dt);
	}
	
	/**
	 * 1900~2100 사이의 랜덤한 날짜값 반환.
	 *
	 * @return 1900~2100 사이의 랜덤한 날짜값.
	 */
	public static Date getRandomDate() {
		DateTimeFormatter fmt = DateTimeFormat.forPattern(DATE_PATTERN_DASH);
		
		Random generator = new Random(System.currentTimeMillis());
		
		String pattern = "(?:19|20)[0-9]{2}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:(?!02)(?:0[1-9]|1[0-2])-(?:29|30))|(?:(?:0[13578]|1[02])-31))";
		String date = "";
		
		while (ValidationUtils.isRegexPatternMatch(date, pattern) == false) {
			String yyyy = StringUtils.leftPad(String.valueOf(generator.nextInt(200) + 1900), 4, '0');
			String mm = StringUtils.leftPad(String.valueOf(generator.nextInt(12)), 2, '0');
			String dd = StringUtils.leftPad(String.valueOf(generator.nextInt(30)), 2, '0');
			
			date = yyyy + "-" + mm + "-" + dd;
		}
		
		DateTime dt = fmt.parseDateTime(date);
		
		return dt.toDate();
	}
	
	/**
	 * 문자열을 날짜(<code>java.sql.Date</code>)형식으로 변환.
	 *
	 * @param str 날짜값. (yyyy-MM-dd)
	 * @return <code>java.sql.Date</code>
	 * @throws <code>Exception<code>
	 */
	public static java.sql.Date stringToSQLDate(String str) {
		return string2SQLDate(str, DATE_PATTERN_DASH);
	}
	
	/**
	 * 문자열을 날짜(<code>java.sql.Date</code>)형식으로 변환.
	 *
	 * @param str     str 날짜값.
	 * @param pattern 변환하고자 하는 날짜패턴.
	 * @return <code>java.sql.Date</code>
	 */
	public static java.sql.Date string2SQLDate(String str, String pattern) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);

		return new java.sql.Date(fmt.parseDateTime(str).getMillis());
	}
	
	/**
	 * 문자열을 날짜 (<code>java.sq.Timestamp</code>) 형식으로 변환.
	 *
	 * @param str 날짜값. (yyyy-MM-dd)
	 * @return <code>java.sql.Timestamp</code>
	 */
	public static Timestamp string2Timestamp(String str) {
		return string2Timestamp(str, DATE_PATTERN_DASH);
	}
	
	/**
	 * 문자열을 날짜 (<code>java.sq.Timestamp</code>) 형식으로 변환.
	 *
	 * @param str     날짜값.
	 * @param pattern 변환하고자 하는 날짜패턴.
	 * @return <code>java.sql.Timestamp</code>
	 */
	public static Timestamp string2Timestamp(String str, String pattern) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);

		return new Timestamp(fmt.parseDateTime(str).getMillis());
	}
	
	/**
	 * 날짜(<code>java.sq.Timestamp</code>)값을 문자열(<code>String</code>)형식으로 변경.
	 *
	 * @param date 날짜값.
	 * @return 변환된 날짜형식의 문자열 (yyyy-MM-dd)
	 */
	public static String timestamp2String(Timestamp date) {
		return timestamp2String(date, DATE_PATTERN_DASH);
	}
	
	/**
	 * 날짜(<code>java.sq.Timestamp</code>)값을 문자열(<code>String</code>)형식으로 변경.
	 *
	 * @param date    날짜값.
	 * @param pattern 변환하고자 하는 날짜 패턴.
	 * @return 변환된 날짜형식의 문자열.
	 */
	public static String timestamp2String(Timestamp date, String pattern) {
		if (date == null) {
			return "";
		}

		return date2String(date, pattern);
	}
	
	/**
	 * 문자열을 날짜(<code>java.util.Calendar</code>)형식으로 변환.
	 *
	 * @param str 날짜값. (yyyyMMddHHmmss)
	 * @return <code>java.util.Calendar</code>
	 */
	public static Calendar string2Calender(String str) {
		if ((str == null) || (str.length() < 14))
			return null;
		
		String year = str.substring(0, 4);
		String month = str.substring(4, 6);
		String day = str.substring(6, 8);
		String hour = str.substring(8, 10);
		String minute = str.substring(10, 12);
		String second = str.substring(12, 14);
		
		return (new GregorianCalendar(StringUtils.stringToInteger(year), StringUtils.stringToInteger(month) - 1, StringUtils
				.stringToInteger(day), StringUtils.stringToInteger(hour), StringUtils.stringToInteger(minute), StringUtils
				.stringToInteger(second)));
	}
	
	/**
	 * 날짜값(<code>java.util.Calendar</code>)을 문자열로 변환.
	 *
	 * @param calendar 날짜값.
	 * @return 변환된 날짜 형식의 문자열. (yyyyMMddHHmmss)
	 */
	public static String calendar2String(Calendar calendar) {
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		
		return (StringUtils.integerToString(year) + StringUtils.integerToString(month) + StringUtils.integerToString(day)
				+ StringUtils.integerToString(hour) + StringUtils.integerToString(minute)
				+ StringUtils.integerToString(second) + "000");
	}
	
	/**
	 * 두 날짜 사이의 차이(분)를 반환.
	 *
	 * @param cal1 시작 날짜.
	 * @param cal2 마지막 날짜.
	 * @return 두 날짜 사이의 차이(분).
	 */
	public static int getMinutes(Calendar cal1, Calendar cal2) {
		long utc1 = cal1.getTimeInMillis();
		long utc2 = cal2.getTimeInMillis();
		
		long result = (utc2 - utc1) / (60 * 1000);
		
		return (int) result;
	}
	
	/**
	 * 두 날짜 사이의 차이(분)를 반환.
	 *
	 * @param date1 시작 날짜. (yyyyMMddHHmmss)
	 * @param date2 마지막 날짜. (yyyyMMddHHmmss)
	 * @return 두 날짜 사이의 차이(분).
	 */
	public static int getMinutes(String date1, String date2) {
		Calendar cal1 = string2Calender(date1);
		Calendar cal2 = string2Calender(date2);
		
		return getMinutes(cal1, cal2);
	}
	
	/**
	 * 어제 날짜를 반환함.
	 *
	 * @return 어제날짜. (yyyy-MM-dd)
	 */
	public static String getYesterday() {
		return getYesterday(DATE_PATTERN_DASH);
	}
	
	/**
	 * 어제 날짜를 반환함.
	 *
	 * @param pattern 변환하고자 하는 날짜 패턴.
	 * @return 어제 날짜.
	 */
	public static String getYesterday(String pattern) {
		Calendar cal = getCalendar();
		cal.roll(Calendar.DATE, -1);
		Date date = cal.getTime();

		return date2String(date, pattern);
	}
	
	/**
	 * 한국 지역 기준의 현재 날짜를 반환함.
	 *
	 * @return <code>java.util.Calendar</code>
	 */
	private static Calendar getCalendar() {
		Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT+09:00"), Locale.KOREA);
		calendar.setTime(new Date());
		
		return calendar;
	}
	
	/**
	 * 시작일과 종료일 사이의 날짜를 배열로 반환함.
	 *
	 * @param startDay 시작일. (yyyy-MM-dd)
	 * @param endDay   종료일. (yyyy-MM-dd)
	 * @return 시작일과 종료일 사이의 날짜 배열값.
	 */
	public static String[] getDates(String startDay, String endDay) {
		return getDates(startDay, endDay, DATE_PATTERN_DASH);
	}
	
	/**
	 * 시작일과 종료일 사이의 날짜를 배열로 반환함.
	 *
	 * @param fromDay 시작일
	 * @param toDay   end day 종료일.
	 * @param pattern  날짜 패턴.
	 * @return 시작일과 종료일 사이의 날짜 배열값.
	 */
	public static String[] getDates(String fromDay, String toDay, String pattern) {
		List<String> result = new ArrayList<String>();
		result.add(fromDay);
		
		Calendar cal = getCalendar();
		cal.setTime(string2Date(fromDay, pattern));
		String nextDay = date2String(cal.getTime(), pattern);
		
		while (nextDay.equals(toDay) == false) {
			cal.add(Calendar.DATE, 1);
			nextDay = date2String(cal.getTime(), pattern);
			result.add(nextDay);
		}
		
		return result.toArray(new String[0]);
	}
	
	/**
	 * 현재 날짜를 가져옴. (yyyy-MM-dd)
	 *
	 * @return 현재날짜. (yyyy-MM-dd)
	 */
	public static String getCurrentDateString() {
		return getCurrentDateString(DATE_PATTERN_DASH);
	}
	
	/**
	 * 현재 날짜를 반환함.
	 *
	 * @param pattern 변환하고자 하는 날짜 패턴.
	 * @return 현재날짜.
	 */
	public static String getCurrentDateString(String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);

		return df.format(new Date());
	}
	
	/**
	 * 현재날짜를 {@link java.sql.Date} 형식으로 반환함.
	 *
	 * @return 현재날짜.
	 */
	public static java.sql.Date getCurrentDate() {
		return new java.sql.Date((new java.util.Date()).getTime());
	}
	
	/**
	 * 현재시간을 {@link java.sql.Time} 형식으로 반환함.
	 *
	 * @return 현재날짜.
	 */
	public static Time getCurrentTime() {
		return new Time(new Date().getTime());
	}
	
	/**
	 * 현재 시간을 반환함.
	 *
	 * @return 현재시간.
	 */
	public static String getCurrentTimeString() {
		return new Time(new Date().getTime()).toString();
	}
	
	/**
	 * 현재날짜를 {@link java.sql.Timestamp} 형식으로 반환함.
	 *
	 * @return 현재날짜.
	 */
	public static Timestamp getCurrentTimestamp() {
		Timestamp timestamp = new Timestamp(new Date().getTime());
		
		return timestamp;
	}
	
	/**
	 * 현재날짜({@link java.sql.Timestamp})를 문자열로 반환.
	 *
	 * @return 현재날짜.
	 */
	public static String getCurrentTimestampString() {
		return getCurrentTimestamp().toString();
	}
	
	/**
	 * 날짜의 Year값을 입력된 값으로 교체하여 반환함.
	 *
	 * @param date 날짜값.
	 * @param year 교체할 Year값.
	 * @return 교체된 날짜값.
	 */
	public static Date replaceYear(Date date, int year) {
		Assert.notNull(date);
		
		Assert.isTrue(("" + year).length() <= 4, year + " 교체랑 Year값은 4자릿수를 넘을 수 없습니다.");
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.YEAR, year);
		
		return calendar.getTime();
	}
	
	/**
	 * 입력된 날짜 값으로  Date객체를 생성하여 리턴함.
	 *
	 * @param year      year값.
	 * @param month     month값.
	 * @param day       day값.
	 * @param hourOfDay hour값.
	 * @param minute    minute값.
	 * @param second    second값.
	 * @return 생성된 날짜값({@link java.util.Date})
	 */
	public static Date getDate(int year, int month, int day, int hourOfDay, int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day, hourOfDay, minute, second);
		
		return cal.getTime();
	}
	
	/**
	 * 입력된 날짜의 해당 월의 마지막 일자를 리턴함.
	 *
	 * @param inputDate 날짜값. ( yyyy-MM-dd )
	 * @return 해당 월의 마지막 일자.
	 */
	public static int getLastDayOfMonth(String inputDate) {
		String examYmd = StringUtils.left(inputDate.trim(), 8) + "01";
		Assert.isTrue(isDate(examYmd), inputDate + "입력된 날짜의 형식은 반드시 'yyyy-MM-dd' 형식이어야 합니다.");
		
		return getLastDayOfMonthInt(examYmd);
	}
	
	/**
	 * 입력된 날짜값이 해당월의 마지막일인지를 반환함.
	 *
	 * @param inputDate 날짜값.(yyyy-MM-dd)
	 * @return 해당월의 마지막일인 경우 true.
	 */
	public static boolean isLastDateOfMonth(String inputDate) {
		String examYmd = StringUtils.left(inputDate.trim(), 8) + "01";
		int inputDay = Integer.parseInt(StringUtils.right(inputDate.trim(), 2));
		
		int lastDay = getLastDayOfMonthInt(examYmd);
		
		if (inputDay == lastDay) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 입력된 날짜값이 해당월의 마지막 일값을 반환함.
	 *
	 * @param inputDate 날짜값.(yyyy-MM-dd)
	 * @return 입력된 날짜값이 해당월의 마지막 일값.
	 */
	private static int getLastDayOfMonthInt(String inputDate) {
		Assert.notNull(inputDate);
		Assert.isTrue(isDate(inputDate), inputDate + " must be in 'yyyy-MM-dd' pattern");

		int month = Integer.parseInt(inputDate.substring(5, 7));
		int lastDayOfMonthValue = 0;

		if (isLeapYear(inputDate)) {
			lastDayOfMonthValue = lastDayOfMonthForLeapYear[month - 1];
		} else {
			lastDayOfMonthValue = lastDayOfMonth[month - 1];
		}
		return lastDayOfMonthValue;
		
	}
	
	/**
	 * 윤년 여부를 반환함.
	 *
	 * @param inputDate 날짜값. (yyyy-MM-dd)
	 * @return 윤년이면 true, 아니면 false.
	 */
	public static boolean isLeapYear(String inputDate) {
		Assert.hasLength(inputDate);
		Assert.isTrue(isDate(inputDate), inputDate + " 날짜값의 형태는 'yyyy-MM-dd' 형식이어야 합니다.");
		
		return isLeapYear(Integer.parseInt(inputDate.substring(0, 4)));
	}
	
	/**
	 * 입력된 년도로 윤년 여부를 반환함.
	 *
	 * @param year 년도값.
	 * @return true if it is a leap year
	 */
	public static boolean isLeapYear(int year) {
		Assert.isTrue(year > 0, year + " 반드시 정수형이어야 합니다.");

		return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) ? true : false;
	}
}