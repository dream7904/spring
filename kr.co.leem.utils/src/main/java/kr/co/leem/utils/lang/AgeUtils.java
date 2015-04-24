package kr.co.leem.utils.lang;


/**
 * Age Utils.
 * 
 * @author 임성천.
 */
public class AgeUtils {
	private AgeUtils() {
		throw new AssertionError();
	}
	
	/**
	 * 입력된 생년월일로 나이(만)를 반환함.
	 * 
	 * @param birthday 생년월일.
	 * @param baseDate 비교할 기준날짜.
	 * @return 나이값.(만)
	 */
	public static int getFullAge(String birthday, String baseDate) {
		int age = Integer.parseInt(baseDate.substring(0, 4)) - Integer.parseInt(birthday.substring(0, 4));
		
		if(age > 0) {
			int birthDay = Integer.parseInt(birthday.substring(4,8));
			int baseDay = Integer.parseInt(baseDate.substring(4,8));

			if(birthDay > baseDay)
				age--;
		}
		
		return age;
	}
	
	/**
	 * 입력된 생년월일로 나이를 반환함.
	 * 
	 * @param birthday 생년월일.
	 * @param baseDate 비교할 기준날짜.
	 * @return 나이값.
	 */
	public static int getAge(String birthday, String baseDate){
		return Integer.parseInt(baseDate.substring(0, 4)) - Integer.parseInt(birthday.substring(0, 4)) + 1;
	}
	
	/**
	 * 입력된 일자와 나이로 목표나이가 되는 날짜(yyyyMMdd)를 반환.
	 *  
	 * @param date 날짜값.
	 * @param age 현재나이.
	 * @param targetAge	목표나이.
	 * @return 목표나이가 되는 날짜값.
	 */
	public static String getDateByAge(String date, int age, int targetAge) {
		int ageDiff = age - targetAge;
		
		if(ageDiff > 0) {
			return DateUtils.addYears(DateUtils.string2String(date, DateUtils.DATE_PATTERN, DateUtils.DATE_PATTERN_DASH), ageDiff);
		} else {
			return DateUtils.addYears(DateUtils.string2String(date, DateUtils.DATE_PATTERN, DateUtils.DATE_PATTERN_DASH), -ageDiff);
		}
	}
}