package kr.co.leem.provider.id.service;

import java.math.BigDecimal;

import kr.co.leem.commons.exceptions.UserHandleableException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface IdService {
	
	/**
	 * 로그 설정.
	 */
	Logger LOGGER = LoggerFactory.getLogger(IdService.class);
	
	/**
	 * id 값을 반환함.(BigDecimal)
	 * 
	 * @return the id 값.
	 * @throws UserHandleableException
	 */
	BigDecimal getNextBigDecimalId() throws UserHandleableException;
	
	/**
	 * id 값을 반환함.(long)
	 * 
	 * @return the id 값.
	 * @throws UserHandleableException
	 */
	long getNextLongId() throws UserHandleableException;

	/**
	 * id 값을 반환함.(int)
	 * 
	 * @return the id 값.
	 * @throws UserHandleableException
	 */
	int getNextIntegerId() throws UserHandleableException;
	
	/**
	 * id 값을 반환함.(short)
	 * 
	 * @return the id 값.
	 * @throws UserHandleableException
	 */
	short getNextShortId() throws UserHandleableException;
	
	/**
	 * id 값을 반환함.(byte)
	 * 
	 * @return the id 값.
	 * @throws UserHandleableException
	 */
	byte getNextByteId() throws UserHandleableException;
	
	/**
	 * id 값을 반환함.(String)
	 * 
	 * @return the id 값.
	 * @throws UserHandleableException
	 */
	String getNextStringId() throws UserHandleableException;
	
	/**
	 * id 값을 반환함.(String)
	 * 
	 * @return the id 값.
	 * @throws UserHandleableException
	 */
	String getNextStringId(String tableName) throws UserHandleableException;
}