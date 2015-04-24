package kr.co.leem.commons.constants;

/**
 * 상수 정의 Class.
 * @author Dream7904
 */
public final class Default {
	/**
	 * MessageSource 
	 */
	public static class MessageSourceName {
		public static final String BEAN_NAME_MESSAGE_SOURCE = "messageSource";
	}
	
	/**
	 * Session
	 */
	public static class Session {
		/** 로그인 세션 (범용적으로 사용 ) */
		public static String USER_LOGIN_SESSION = "userLoginSession";
		/** 관리자 세션  (범용적으로 사용 ) */
		public static String ADMIN_LOGIN_SESSION = "adminLoginSession";
		/** IP 주소 세션 (IP를 받아오기위해 사용) */
		public static String IP_ADDR = "ipAddr";
		/** 소설 서비스 연동시 사용하는 세션  */
		public static String SOCIAL_LINKAGE_SESSION = "socialLinkageSession";
	}
	
	/**
	 * 
	 * @author Dream7904
	 */
	public static class ResultType {
		/** 성공 */
		public static String SUCCESS = "success";
		/** 실패 */
		public static String FAIL = "fail";
		/** 로그인 실패 */
		public final static String LOGIN_FAIL = "loginFail";
		/** 로그아웃 실패 */
		public final static String LOGOUT_FAIL = "logoutFail";
		/** 세션이 없음 */
		public final static String NO_SESSION = "noSession";
		/** 서비스 세션이 없음 */
		public final static String NO_SERVICE_SESSION = "noServiceSession";
		/** 권한 없음 */
		public final static String NO_AUTH = "noAuth";
		/** 결과 없음 */
		public final static String NO_RESULT = "noResult";
		/** 파라메터 갯수 불일치 */
		public final static String NO_PARAM_COUNT_CONCUR = "noParamCountConcur";
		/** 잘못된 파라메터 */
		public final static String WRONG_PARAM = "wrongParam";		
		/** 이미 중복됨 */
		public final static String DUPLICATION = "duplication";
		/** 중복 없음 */
		public final static String NO_DUPLICATION = "noDuplication";
		/** 다른사람의 계정으로 접근하고 있음 */
		public final static String OTHER_PEOPLE_ACCOUNT = "otherPeopleAccount";
		/** 서로 FOLLWING하고 있지 않음 : 비밀글 지정은 서로 FOLLOWING 하는 경우에만 가능 */
		public final static String EACH_OTHER_NOT_FOLLWING = "eachOtherNotFollowing";
		/** 타인의 사이트에서 아이템 관련 COMMENT 를 신규 작성 할 수 없음 */
		public final static String STRANGER_AREA_NOT_COMMENT_WRITE = "strangerAreaNotCommetWrite";	
		
		/** 가입승인 안됨 */
		public final static String NO_JOIN_APPROVAL = "noJoinApproval";
		/** 판매자 계정은 LUUVLET 로그인이 안됨 */
		public final static String NO_SALE_LOGIN = "noSaleLogin";
		/** 가입시 등록한 계정이 아닙니다. (연동은 되어있지만 가입시 연동가입을 한게 아닌경우) */
		public final static String NO_REGIST_TIME_ACCOUNT = "noRegistTimeAccount";
	}
	
	/**
	 * 
	 * @author Dream7904
	 */
	public static class KeyType {
		public static String MESSAGE = "message";
	}
	
	/** 
	 * View에 보여질 형태
	 */
	public static class ViewType {
		/** 일반 뷰화면 */
		public static String NORMAL = "normal";
		/** SNS 뷰화면 */
		public static String SNS = "sns";
		/** EMAIL 뷰화면 */
		public static String EMAIL = "email";
	}	
	
	public static class UserType {
		/** 일반 사용자 */
		public static String GENERAL = "N";
		/** 패션 종사자 */
		public static String FASHION = "F";
		/** 판매자 */
		public static String SALE = "S";
	}
	
	public static class Policy {
		public static class Login {
			/** 상단 로그인에서 로그인시 정책(기본정책) */
			public static String DEFAULT = "Default";
			/** LUUVLET에서 로그인시 정책 */
			public static String LUUVLET = "Luuvlet";
		}
	}
	
	public static class UserAgent {
		/** MOBILE_TYPE2인경우 ua.substring(0,4)을 한후 match한다. */
		public static String MOBILE_TYPE1 = ".*(android|avantgo|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\\/|plucker|pocket|psp|symbian|treo|up\\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino).*";
		public static String MOBILE_TYPE2 = "1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\\-(n|u)|c55\\/|capi|ccwa|cdm\\-|cell|chtm|cldc|cmd\\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\\-s|devi|dica|dmob|do(c|p)o|ds(12|\\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\\-|_)|g1 u|g560|gene|gf\\-5|g\\-mo|go(\\.w|od)|gr(ad|un)|haie|hcit|hd\\-(m|p|t)|hei\\-|hi(pt|ta)|hp( i|ip)|hs\\-c|ht(c(\\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\\-(20|go|ma)|i230|iac( |\\-|\\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\\/)|klon|kpt |kwc\\-|kyo(c|k)|le(no|xi)|lg( g|\\/(k|l|u)|50|54|e\\-|e\\/|\\-[a-w])|libw|lynx|m1\\-w|m3ga|m50\\/|ma(te|ui|xo)|mc(01|21|ca)|m\\-cr|me(di|rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\\-2|po(ck|rt|se)|prox|psio|pt\\-g|qa\\-a|qc(07|12|21|32|60|\\-[2-7]|i\\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\\-|oo|p\\-)|sdk\\/|se(c(\\-|0|1)|47|mc|nd|ri)|sgh\\-|shar|sie(\\-|m)|sk\\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\\-|v\\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\\-|tdg\\-|tel(i|m)|tim\\-|t\\-mo|to(pl|sh)|ts(70|m\\-|m3|m5)|tx\\-9|up(\\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|xda(\\-|2|g)|yas\\-|your|zeto|zte\\-";
	}
	
	
	/** 정규식 패턴 */
	public static class Regex {
		public static class IdName {
			public static String PATTERN = "(@|\\♡|\\>)\\[([0-9]*)\\|([a-zA-Z0-9ㄱ-ㅎ가-힣]*)]"; //[@|\\>|\\♡][ID|NAME} 형식의 패턴 (♡은 속이 빈 하트를 의미)
			public static int INDICATOR = 1;	// [@|\\>|\\♡]을 말함
			public static int ID = 2;
			public static int NAME = 3;	
		}
		public static class Type {
			public static String NO_AUTH = "ALSDJF23423342SJDLFJLNOAUTHSADJKFLASJFAKSJDLFJ2398234";
			public static String NO_RESULT = "ASDFLAJKSDF3782384LAJSNORESULTDKLJF8A987233LKASD83";
		}
	}
	
	/**
	 * 
	 * @author Dream7904
	 */
	public static class File {
		public static String UPLOAD_ROOT_PATH = "upload.file.root.path";
		public static String UPLOAD_TEMP_PATH = "upload.file.temp.path";
		public static String NOIMG_ROOT_PATH = "noimg.file.root.path";
	}
	
	public static class DBType { 
		public static final String TYPE_MYSQL = "MYSQL";
		public static final String TYPE_MSSQL = "MSSQL";
		public static final String TYPE_SYBASE = "SYBASE";
		public static final String TYPE_DERBY = "DERBY";
		public static final String TYPE_HSQL = "HSQL";	
		public static final String TYPE_ORACLE = "ORACLE";
		public static final String TYPE_HSQL_SEQ = "HSQL_SEQ";
		public static final String TYPE_H2 = "H2";
		public static final String TYPE_DB2 = "DB2";
		public static final String TYPE_POSTGRE = "POSTGRE";
		public static final String TYPE_DB2MAINFRAME = "DB2MAINFRAME";
		public static final String TYPE_UUID = "UUID";
		
		public static final String [] TYPES = {
			TYPE_MYSQL, TYPE_MSSQL, TYPE_SYBASE, TYPE_DERBY, TYPE_HSQL, 
			TYPE_ORACLE, TYPE_HSQL_SEQ, TYPE_H2, TYPE_DB2, TYPE_POSTGRE, TYPE_DB2MAINFRAME, 
			TYPE_UUID
		};
	}	
	
	public static class Header {
		/** User-Agent */
		public final static String X_REQUESTED_WITH = "x-requested-with";
		/** User-Agent */
		public final static String USER_AGENT = "User-Agent";
	}
	
	public static class Encoding {
		public final static String UTF_8 = "UTF-8";
	}
	
	public static class FTP {
		public final static String HOST = "ftp_host";
		public final static String ID = "ftp_id";
		public final static String PWD = "ftp_pwd";
	}
}