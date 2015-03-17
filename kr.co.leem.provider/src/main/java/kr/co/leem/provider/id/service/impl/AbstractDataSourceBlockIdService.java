package kr.co.leem.provider.id.service.impl;

import java.math.BigDecimal;

import kr.co.leem.commons.exceptions.UserHandleableException;

import org.springframework.beans.factory.InitializingBean;

/**
 * AbstractDataSourceBlockIdGenerator 
 * @author 임 성천.
 */
public abstract class AbstractDataSourceBlockIdService extends
		AbstractDataSourceIdService implements InitializingBean {
	
	/**
	 * BigDecimal 변수.
	 */
	private BigDecimal firstBigDecimal;
	
	/**
	 * long 변수.
	 */
	private long firstLong;
	
	/**
	 * 블럭 사이즈 변수.
	 */
	private int blockSize = 10;
	
	private int allocated;
	
	/**
	 * BigDecimal 유형을 갖는 아이디를 반환함.
	 * 
	 * @param tableName 테이블 명.
	 * @param blockSize 블럭 사이즈.
	 * @return BigDecimal 유형을 갖는 아이디.
	 * @throws UserHandleableException
	 */
	protected abstract BigDecimal allocateBigDecimalIdBlock(String tableName,
			int blockSize) throws UserHandleableException;

	/**
	 * Long 유형을 갖는 아이디를 반환함.
	 * 
	 * @param tableName 테이블명.
	 * @param blockSize 블럭 사이즈.
	 * @return Long 유형을 갖는 아이디.
	 * @throws UserHandleableException
	 */
	protected abstract long allocateLongIdBlock(String tableName, int blockSize)
			throws UserHandleableException;

	protected BigDecimal getNextBigDecimalIdInner(String tableName)
			throws UserHandleableException {
		if (allocated >= blockSize) {
			try {
				firstBigDecimal = allocateBigDecimalIdBlock(tableName,
						blockSize);
				allocated = 0;
			} catch (UserHandleableException be) {
				allocated = Integer.MAX_VALUE;
				throw be;
			}
		}
		
		BigDecimal id = firstBigDecimal.add(new BigDecimal(new Integer(allocated).doubleValue()));
		allocated++;
		
		return id;
	}
	
	protected BigDecimal getNextBigDecimalIdInner() throws UserHandleableException {
		return getNextBigDecimalIdInner("");
	}
	
	protected long getNextLongIdInner() throws UserHandleableException {
		return getNextLongIdInner("");
	}
	
	protected long getNextLongIdInner(String tableName) throws UserHandleableException {
		if (allocated >= blockSize) {
			try {
				firstLong = allocateLongIdBlock(tableName, blockSize);
				allocated = 0;
			} catch (UserHandleableException e) {
				allocated = Integer.MAX_VALUE;
				throw e;
			}
		}
		
		long id = firstLong + allocated;
		if (id < 0) {
			getLogger()
					.error(
							"[IDGeneration Service] Unable to provide an id.   No more Ids are available, the maximum Long value has been reached.");
			throw new UserHandleableException(
					"[IDGeneration Service] Unable to provide an id.   No more Ids are available, the maximum Long value has been reached.");
		}
		allocated++;
		
		return id;
	}
	
	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}
	
	public void afterPropertiesSet() throws Exception {
		allocated = Integer.MAX_VALUE;
	}
}
