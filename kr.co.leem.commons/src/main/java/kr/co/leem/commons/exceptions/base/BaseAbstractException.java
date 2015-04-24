package kr.co.leem.commons.exceptions.base;

public abstract class BaseAbstractException extends RuntimeException implements BaseErrorCoded {
	private static final long serialVersionUID = 1L;

	protected String errorMessageParameter;

	public BaseAbstractException() {
		super();
	}
	
	public BaseAbstractException(Throwable throwable) {
		super(throwable);
	}
	
	public BaseAbstractException(String errorMessage) {
		super(errorMessage);
		this.errorMessageParameter = errorMessage;
	}
		
	public Object[] getErrorMessageElement() {
		return new Object[]{errorMessageParameter};
	}
}