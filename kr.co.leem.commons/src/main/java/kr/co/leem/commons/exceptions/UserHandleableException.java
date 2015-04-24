package kr.co.leem.commons.exceptions;

/**
 * 사용자 정의 Exception.
 * @author user
 *
 */
public class UserHandleableException extends Exception {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 오류 코드
	 */
	protected String errorCode = "";
	
	/**
	 * 오류 메시지
	 */
	protected String errorMessage = "";
	
	public UserHandleableException() {
		super();
	}
	
	/**
	 * 에러코드
	 * @param errorCode
	 */
	public UserHandleableException(String errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
	}
	
	/**
	 * 에러코드와 메세지
	 * @param errorCode
	 * @param errorMessage
	 */
	public UserHandleableException(String errorCode, String errorMessage) throws Exception {
		super(errorCode);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}