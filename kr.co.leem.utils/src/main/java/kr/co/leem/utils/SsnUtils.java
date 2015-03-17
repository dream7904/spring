package kr.co.leem.utils;

import kr.co.leem.utils.lang.DateUtils;
import kr.co.leem.utils.lang.StringUtils;

import org.springframework.util.Assert;

/**
 * RegistrationNumberUtil class 
 * 
 * @author 임성천.
 */
public class SsnUtils {
	
	private SsnUtils() {
		throw new AssertionError();
	}
	
	/**
	 * 주민등록번호 13자리를 입력받아 유효성을 체크하여 결과를 반환함.<br>
	 * <div class="ko">
	 * 
	 * <pre>
	 * CheckResultEnum result = RegistrationNumberUtil.checkSsn("12345678912340");
	 * if(result == CheckResultEnum.VALID) {
	 *		debug("검증에 성공하였습니다.");
	 * } else {
	 *		debug("[오류유형] : " + result); // [오류유형] : 자릿수오류
	 * } 
	 * <pre>
	 * </div>
	 * 
	 * @param ssn 주민등록번호.
	 * @return 주민등록 번호 체크 결과.
	 */
	public static CheckResultEnum checkSsn(String ssn) {
		Assert.hasLength(ssn);
		
		if(ssn.length() == 13) {
			String birthday =ssn.substring(0,6);
			String num = ssn.substring(6,13);
			
			int n1 = 0;
			int n2 = 0;
			int n3 = 0;
			int n4 = 0;
			int n5 = 0;
			int n6 = 0;
			int n7 = 0;
			
			n1 = Integer.parseInt(num.substring(0, 1));
			n2 = Integer.parseInt(num.substring(1, 2));
			n3 = Integer.parseInt(num.substring(2, 3));
			n4 = Integer.parseInt(num.substring(3, 4));
			n5 = Integer.parseInt(num.substring(4, 5));
			n6 = Integer.parseInt(num.substring(5, 6));
			n7 = Integer.parseInt(num.substring(6, 7));
			
			if(n1 != 0 && n1 != 1 && n1 != 2 && n1 != 3 && n1 != 4)
				return CheckResultEnum.INVALID_GENDER_CODE;
			
			if(birthday.length() != 6 || !DateUtils.isDate(getBirthDateBySsn(ssn),DateUtils.DATE_PATTERN))
				return CheckResultEnum.INVALID_BIRTHDAY;
			
			if(num.length() != 7)
				return CheckResultEnum.INVALID_FORMAT;
			
			if(Integer.parseInt(birthday) == 0 || Integer.parseInt(num) == 0 )
				return CheckResultEnum.INVALID_BIRTHDAY;
			
			int sum = 0;
			
			for (int i = 0; i < 6; i++) {
				int temp = Integer.parseInt(birthday.substring(i,i+1)) * (i+2);
				sum += temp;
			}
			
			sum += n1 * 8 + n2 * 9 + n3 * 2 + n4 * 3 + n5 * 4 + n6 * 5;
			sum %= 11;
			sum = 11 - sum;
			sum %= 10;
			
			if(sum == n7) {
				return CheckResultEnum.VALID;
			} else {
				return CheckResultEnum.INVALID_VALUE;
			}
			
		} else {
			return CheckResultEnum.INVALID_FORMAT;
		}
	}
	
	/**
	 * 외국인 등록번호 체크.<br>
	 * <div class="ko">
	 * <pre>
	 * CheckResultEnum result = RegistrationNumberUtil.checkFrgn("12345678912340");
	 * if(result == CheckResultEnum.VALID) {
	 *		debug("검증에 성공하였습니다.");
	 * } else {
	 *		debug("[오류유형] : " + result); // [오류유형] : 자릿수오류
	 * } 
	 * <pre>
	 * </div>
	 * 
	 * @param frgn 외국인 등록번호.
	 * @return 외국인 등록번호 체크 결과.
	 */
	public static CheckResultEnum checkFrgn(String frgn) {
		Assert.hasLength(frgn);
		
		if(frgn.length() == 13) {
			String birthday = frgn.substring(0, 6);
			String num = frgn.substring(6, 13);
			
			int n1 = 0;
			int n2 = 0;
			int n3 = 0;
			int n4 = 0;
			int n5 = 0;
			int n6 = 0;
			int n7 = 0;
			
			n1 = Integer.parseInt(num.substring(0, 1));
			n2 = Integer.parseInt(num.substring(1, 2));
			n3 = Integer.parseInt(num.substring(2, 3));
			n4 = Integer.parseInt(num.substring(3, 4));
			n5 = Integer.parseInt(num.substring(4, 5));
			n6 = Integer.parseInt(num.substring(5, 6));
			n7 = Integer.parseInt(num.substring(6, 7));
			
			if(n1 != 5 && n1 != 6 && n1 != 7 && n1 != 8 )
				return CheckResultEnum.INVALID_GENDER_CODE;
			
			if((n2 * 10 + n3) % 2 != 0)
				return CheckResultEnum.INVALID_REG_INST_CODE;
			
			if(Integer.parseInt(birthday) == 0 || Integer.parseInt(num) == 0 || !DateUtils.isDate(getBirthDateBySsn(frgn),DateUtils.DATE_PATTERN))
				return CheckResultEnum.INVALID_BIRTHDAY;
			
			// registration type 7=Foreign nationality Koreans, 8=Foreigners residing in Korea , 9=Foreigners
			if(n6 != 7 && n6 != 8 && n6 != 9)
				return CheckResultEnum.INVALID_REG_TYPE;   
			
			int sum = 0;
			for (int i = 0; i < 6; i++) {
				int temp = Integer.parseInt(birthday.substring(i, i + 1)) * (i + 2);
				sum += temp;
			}
			
			sum += n1 * 8 + n2 * 9 + n3 * 2 + n4 * 3 + n5 * 4 + n6 * 5;
			sum %= 11;
			sum = 11 - sum;
			
			if(sum >= 10)
				sum -= 10;
			
			sum += 2;
			
			if(sum >= 10)
				sum -= 10;
			
			if(sum == n7)
				return CheckResultEnum.VALID;
			else
				return CheckResultEnum.INVALID_VALUE;
		} else {
			return CheckResultEnum.INVALID_FORMAT;
		}
	}
	
	/**
	 * 외국인 등록증 및 재외국인 국내거소신고증이 없는 경우, 여권번호를 기반으로 조합하여 부여되는<br>
	 * 번호의 유효성을 체크하여 체크결과를 반환함.<br>
	 * <div class="ko">
	 * <pre>
	 * 설명) 123456-ABCDEFG 
	 * - 1~6 : 생년월일
	 * - A : 성별
	 * - B : 국적구분 (1:미국, 2:일본, 3:중국, 4:기타) 
	 * - C~G : 여권번호 마지막 5자리 숫자
	 * </pre>
	 * 
	 * <pre>
	 * CheckResultEnum result = RegistrationNumberUtil.checkFrgnPassportNo("12345678912340");
	 * if(result == CheckResultEnum.VALID) {
	 *		debug("검증에 성공하였습니다.");
	 * } else {
	 *		debug("[오류유형] : " + result); // [오류유형] : 자릿수 오류 
	 * }
	 * <pre>
	 * </div>
	 * 
	 * @param frgn 외국인 여권번호.
	 * @return 검증결과.
	 */
	public static CheckResultEnum checkFrgnByPassportNo(String frgn) {
		Assert.hasLength(frgn);
		
		// 등록번호 길이 체크
		if(frgn.length() == 13) {
			String birthday = frgn.substring(0, 6);
			String num = frgn.substring(6, 13);
			
			int n1 = 0;
			int n2 = 0;
			
			n1 = Integer.parseInt(num.substring(0, 1)); // 성별
			n2 = Integer.parseInt(num.substring(1, 2)); // 국적
			
			if(n1 != 5 && n1 != 6 && n1 != 7 && n1 != 8 )
				return CheckResultEnum.INVALID_GENDER_CODE;
			
			if(Integer.parseInt(birthday) == 0 || Integer.parseInt(num) == 0 || DateUtils.isDate(getBirthDateBySsn(frgn),DateUtils.DATE_PATTERN) == false)
				return CheckResultEnum.INVALID_BIRTHDAY;

			// 국가체크 (1:USA, 2:Japan, 3:China, 4:ohter country)
			if(n2 != 1 && n2 != 2 && n2 != 3 && n2 != 4)
				return CheckResultEnum.INVALID_NATIONAL_CODE;
			   
			return CheckResultEnum.VALID;
		} else {
			return CheckResultEnum.INVALID_FORMAT;
		}
	}
	
	/**
	 * 주민등록 번호로 성별을 판별하여 결과를 반환함.<br>
	 * <div class="ko">
	 * 입력된 주민등록번호로 여성인지 확인 <p>
	 * 
	 * <pre>
	 * (참고)
	 * 1 - 1900년대 출생한 남성
	 * 2 - 1900년대 출생한 여성
	 * 3 - 2000년대 출생한 남성
	 * 4 - 2000년대 출생한 여성
	 * 5 - 1900년대 출생한 외국인 남성(외국인등록번호)
	 * 6 - 1900년대 출생한 외국인 여성(외국인등록번호)
	 * 7 - 2000년대 출생한 외국인 남성(외국인등록번호)
	 * 8 - 2000년대 출생한 외국인 여성(외국인등록번호)
	 * 9 - 1800년대 출생한 남성
	 * 0 - 1800년대 출생한 여성
	 * </pre>
	 * </div>
	 * @param ssn 주민등록 번호.
	 * @return 남자인 경우 true를 반환함.
	 */
	public static boolean isFemale(String ssn) {
		Assert.hasLength(ssn);
		
		if(ssn.length() > 13) {
			ssn = StringUtils.delChar(ssn, '-');
			ssn = ssn.trim();
		}
		
		int gender = Integer.parseInt(ssn.substring(6,7)) % 2;
		
		return (gender == 1) ? false : true;
	}
	
	/**
	 * 주민등록 번호로 성별을 판별하여 결과를 반환함.<br>
	 * <div class="ko">
	 * <pre>
	 * (참고)
	 * 1 - 1900년대 출생한 남성
	 * 2 - 1900년대 출생한 여성
	 * 3 - 2000년대 출생한 남성
	 * 4 - 2000년대 출생한 여성
	 * 5 - 1900년대 출생한 외국인 남성(외국인등록번호)
	 * 6 - 1900년대 출생한 외국인 여성(외국인등록번호)
	 * 7 - 2000년대 출생한 외국인 남성(외국인등록번호)
	 * 8 - 2000년대 출생한 외국인 여성(외국인등록번호)
	 * 9 - 1800년대 출생한 남성
	 * 0 - 1800년대 출생한 여성
	 * </pre>
	 * </div>
	 * @param ssn 주민등록번호.
	 * @return 여자인 경우 true, 아니면 false.
	 */
	public static boolean isMale(String ssn) {
		return !isFemale(ssn);
	}
	
	/**
	 * 주민번호로 생일을 반환함.<br>
	 * 
	 * @param ssn 주민등록번호.
	 * @return 생일. (yyyyMMdd)
	 */
	public static String getBirthDateBySsn(String ssn) {
		Assert.hasLength(ssn);
		
		if(ssn.length() > 13) {
			ssn = StringUtils.delChar(ssn, '-');
			ssn = ssn.trim();
		}
		
		String birthDate = null;
		birthDate = ssn.substring(0, 6);
		
		int genderDigit = Integer.parseInt(ssn.substring(6, 7));
		
		// 5,6,7,8 : foreinger 
		if(genderDigit == 3 || genderDigit == 4 || genderDigit == 7 || genderDigit == 8){ 
			birthDate = "20" + birthDate;
		} else {
			birthDate = "19" + birthDate;
		} 
		
		return birthDate;
	}
	
	/**
	 * 주민등록번호 체크 결과 Enum Class.
	 * 
	 * @author 임성천.
	 */
	public static enum CheckResultEnum {
		
		VALID("정상"),
		
		INVALID_GENDER_CODE("성별코드오류"),
		
		INVALID_BIRTHDAY("생년월일오류"),
		
		INVALID_FORMAT("자릿수오류"),
		
		INVALID_REG_INST_CODE("등록기관오류"),
		
		INVALID_REG_TYPE("등록자유형오류"),	//7=Foreign nationality Koreans, 8=Foreigners residing in Korea , 9=Foreigners
		
		INVALID_VALUE("검증값오류"),
		
		INVALID_NATIONAL_CODE("국적구분오류");
		
		private String msg;
		
		private CheckResultEnum(String msg) {
			this.msg = msg;
		}
		
		/**
		 * 결과에 대한 메시지를 반환한다.<br>
		 * @return 메세지.  
		 */
		public String toString() {
			return this.msg;
		}
	}
}