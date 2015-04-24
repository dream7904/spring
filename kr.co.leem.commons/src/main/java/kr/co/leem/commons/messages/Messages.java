package kr.co.leem.commons.messages;

import java.util.Locale;

import org.springframework.context.MessageSource;

/**
 * Messages
 * Properties 파일에서값을 파일에서 값을 불러오는 Class.
 * @author Dream7904
 */
public class Messages {
	private MessageSource messages;
	
	/**
	 * Properties 파일의 경로 설정. 
	 * @param messageSource
	 */
	public void setMessages(MessageSource messageSource) {
		this.messages = messageSource;
	}
	
	/**
	 * Properties 파일에서값을 파일에서 값을 가져옴.
	 * @param key
	 * @param args
	 * @return
	 */
	public String getMessage(String key, Object[] args) {
		return this.messages.getMessage(key, args, Locale.getDefault());
	}
	
	/**
	 * Properties 파일에서값을 파일에서 값을 가져옴.
	 * @param key
	 * @param args
	 * @return
	 */
	public String getMessage(String key) {
		return this.messages.getMessage(key, null, Locale.getDefault());
	}
}