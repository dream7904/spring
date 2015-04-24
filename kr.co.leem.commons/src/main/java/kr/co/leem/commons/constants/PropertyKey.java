package kr.co.leem.commons.constants;

/**
 * 각종 프로퍼티 정의
 * @author 
 *
 */
public class PropertyKey {
	public static class SnsType {
		public final static String TWITTER = "tw.sns.type";
		public final static String FACEBOOK = "fb.sns.type";
		public final static String METODAY = "me.sns.type";
		public final static String CYWORLD = "cy.sns.type";
	}
	
	public static class TwitterAPI {
		public final static String CONSUMER_KEY = "twi.consumer.key";
		public final static String CONSUMER_SECRET = "twi.consumer.secret";
		public final static String REQUEST_TOKEN_URL = "twi.request.token.url";
		public final static String ACCESS_TOKEN_URL = "twi.access.token.url";
		public final static String AUTHORIZE_URL = "twi.authorize.url";
	}
	
	public static class FacebookAPI {
		public final static String APP_ID = "fb.app.id";
		public final static String API_KEY = "fb.api.key";
		public final static String APP_SECRET_CODE = "fb.appsecret.code";
		public final static String CONTACT_EMAIL = "fb.contact.email";
	}
	
	public static class MapAPI {
		public final static String NAVER_API_KEY = "naver.map.api.key";
		public final static String NAVER_API_KEY_INDEX = "naver.map.api.key.index";
	}
	
	public static class Thumbnail {
		public final static String COMMAND_NAME = "thumb.command.name";
		public final static String JS_PATH = "thumb.js.path";
		public final static String SAVE_PATH = "thumb.save.path";
		public final static String HOST = "thumb.host";
	}
	
	public static class Statistics {
		public final static String STATISTICS_DATE_RANGE = "statistics.date.range";
	}
	
	public static class Mail {
		public final static String SMTP_HOST = "mail.smtp.host";
		public final static String SMTP_USERNAME = "mail.smtp.username";
		public final static String SMTP_PASSWORD = "mail.smtp.password";
		public final static String SENDER = "mail.sender";
	}
}