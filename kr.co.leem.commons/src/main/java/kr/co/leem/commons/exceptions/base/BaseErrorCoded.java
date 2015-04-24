package kr.co.leem.commons.exceptions.base;

import org.springframework.core.ErrorCoded;

public interface BaseErrorCoded extends ErrorCoded {
	public abstract Object[] getErrorMessageElement();
}