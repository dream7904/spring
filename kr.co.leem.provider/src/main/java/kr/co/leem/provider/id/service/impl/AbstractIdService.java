package kr.co.leem.provider.id.service.impl;

import java.math.BigDecimal;

import kr.co.leem.commons.exceptions.UserHandleableException;
import kr.co.leem.provider.id.service.IdPolicy;
import kr.co.leem.provider.id.service.IdService;

import org.slf4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * Abstract class for IdGenService This service is developed to work on Spring
 * Framework by modifying Excalibur-datasource id generator.
 * <ul>
 * <li>Spring can't recognize Avalon based lifecycle interfaces, so it is
 * impossible to access reference services such as datasource or to configure
 * external properties.</li>
 * <li>Avalon logkit can't be used in Spring or Anyframe, because those
 * frameworks use Apache slf4j for logging.</li>
 * </ul>
 * 
 * @author <a href="mailto:dev@avalon.apache.org">Avalon Development Team</a>
 * @author modified by SoYon Lim
 * @author modified by JongHoon Kim
 */
public abstract class AbstractIdService implements IdService,
		BeanFactoryAware {
	@SuppressWarnings("unused")
	private BeanFactory beanFactory;

	private static final BigDecimal BIG_DECIMAL_MAX_LONG = new BigDecimal(
			new Long(Long.MAX_VALUE).doubleValue());

	/**
	 * Used to manage internal synchronization.
	 */
	private Object mSemaphore = new Object();

	private IdPolicy strategy = new IdPolicy() {
		public String makeId(String originalId) {
			return originalId;
		}
	};

	/**
	 * Data type for the Id Pool.
	 */
	private boolean mUseBigDecimals = false;

	/*---------------------------------------------------------------
	 * Constructors
	 *-------------------------------------------------------------*/
	/**
	 * default constructor
	 */
	public AbstractIdService() {
	}

	/**
	 * get IdGenService logger
	 * 
	 * @return Log IdGenService logger
	 */
	protected Logger getLogger() {
		return IdService.LOGGER;
	}

	/*---------------------------------------------------------------
	 * Methods
	 *-------------------------------------------------------------*/
	/**
	 * Gets the next id as a Big Decimal. This method will only be called when
	 * synchronized and when the data type is configured to be BigDecimal.
	 * 
	 * @return the next id as a BigDecimal.
	 * @throws UserHandleableException
	 *             if an Id could not be allocated for any reason.
	 */
	protected abstract BigDecimal getNextBigDecimalIdInner()
			throws UserHandleableException;

	/**
	 * Gets the next id as a Big Decimal. This method will only be called when
	 * synchronized and when the data type is configured to be BigDecimal.
	 * 
	 * @param tableName
	 *            key of id management table
	 * @return the next id as a BigDecimal.
	 * @throws UserHandleableException
	 *             if an Id could not be allocated for any reason.
	 */
	protected abstract BigDecimal getNextBigDecimalIdInner(String tableName)
			throws UserHandleableException;

	/**
	 * Gets the next id as a long. This method will only be called when
	 * synchronized and when the data type is configured to be long.
	 * 
	 * @return the next id as a long.
	 * @throws UserHandleableException
	 *             if an Id could not be allocated for any reason.
	 */
	protected abstract long getNextLongIdInner() throws UserHandleableException;

	/**
	 * Gets the next id as a long. This method will only be called when
	 * synchronized and when the data type is configured to be long.
	 * 
	 * @param tableName
	 *            key of id management table
	 * @return the next id as a long.
	 * @throws UserHandleableException
	 *             if an Id could not be allocated for any reason.
	 */
	protected abstract long getNextLongIdInner(String tableName)
			throws UserHandleableException;

	/**
	 * By default, the IdGenerator will operate using a backend datatype of type
	 * long. This is the most efficient, however it does not allow for Ids that
	 * are larger than Long.MAX_VALUE. To allow very large Ids, it is necessary
	 * to make use of the BigDecimal data storage type. This method should only
	 * be called durring initialization.
	 * 
	 * @param useBigDecimals
	 *            True to set BigDecimal as the internal data type.
	 */
	public void setUseBigDecimals(boolean useBigDecimals) {
		mUseBigDecimals = useBigDecimals;
	}

	/**
	 * Returns true if the internal data type is using BigDecimals, false if it
	 * is using longs.
	 * 
	 * @return boolean check using BigDecimal
	 */
	protected final boolean isUsingBigDecimals() {
		return mUseBigDecimals;
	}

	/**
	 * Gets the next Long Id constraining the value to be less than the
	 * specified maxId.
	 * 
	 * @param maxId
	 *            max id
	 * @return long value to be less than the specified maxId
	 * @throws UserHandleableException
	 *             if the next id is larger than the specified maxId.
	 */
	protected final long getNextLongIdChecked(long maxId) throws UserHandleableException {
		long nextId;
		if (mUseBigDecimals) {
			// Use BigDecimal data type
			BigDecimal bd;
			synchronized (mSemaphore) {
				bd = getNextBigDecimalIdInner();
			}

			// Make sure that the Big Decimal value can
			// be assigned to a
			// long before continuing.
			if (bd.compareTo(BIG_DECIMAL_MAX_LONG) > 0) {
				getLogger()
						.error(
								"[IDGeneration Service] Unable to provide an id.   No more Ids are available, the maximum Long value has been reached.");
				throw new UserHandleableException(
						"[IDGeneration Service] Unable to provide an id.   No more Ids are available, the maximum Long value has been reached.");
			}
			nextId = bd.longValue();
		} else {
			// Use long data type
			synchronized (mSemaphore) {
				nextId = getNextLongIdInner();
			}
		}

		// Make sure that the id is valid for the
		// requested data type.
		if (nextId > maxId) {
			getLogger()
					.error(
							"[IDGeneration Service] Unable to provide an id.   No more Ids are available, the maximum Long value has been reached.");
			throw new UserHandleableException(
					"[IDGeneration Service] Unable to provide an id.   No more Ids are available, the maximum Long value has been reached.");
		}

		return nextId;
	}

	/*---------------------------------------------------------------
	 * IdGenerator Methods
	 *-------------------------------------------------------------*/

	protected final BigDecimal getNextBigDecimalId(String tableName)
			throws UserHandleableException {
		BigDecimal bd;
		if (mUseBigDecimals) {
			// Use BigDecimal data type
			synchronized (mSemaphore) {
				bd = getNextBigDecimalIdInner(tableName);
			}
		} else {
			// Use long data type
			synchronized (mSemaphore) {
				bd = new BigDecimal(new Long(getNextLongIdInner(tableName))
						.doubleValue());
			}
		}

		return bd;
	}

	/**
	 * Returns the next Id from the pool.
	 * 
	 * @return the next Id
	 * @throws UserHandleableException
	 *             if the next id is outside of the range of valid big-decimals
	 */
	public final BigDecimal getNextBigDecimalId() throws UserHandleableException {
		return getNextBigDecimalId("");
	}

	/**
	 * Returns the next Id from the pool.
	 * 
	 * @return the next Id
	 * @throws UserHandleableException
	 *             if the next id is outside of the range of valid longs
	 */
	public final long getNextLongId() throws UserHandleableException {
		return getNextLongIdChecked(Long.MAX_VALUE);
	}

	/**
	 * Returns the next Id from the pool.
	 * 
	 * @return the next Id
	 * @throws UserHandleableException
	 *             if the next id is outside of the range of valid integers
	 */
	public final int getNextIntegerId() throws UserHandleableException {
		return (int) getNextLongIdChecked(Integer.MAX_VALUE);
	}

	/**
	 * Returns the next Id from the pool.
	 * 
	 * @return the next Id
	 * @throws UserHandleableException
	 *             if the next id is outside of the range of valid shorts
	 */
	public final short getNextShortId() throws UserHandleableException {
		return (short) getNextLongIdChecked(Short.MAX_VALUE);
	}

	/**
	 * Returns the next Id from the pool.
	 * 
	 * @return the next Id
	 * @throws UserHandleableException
	 *             if the next id is outside of the range of valid bytes
	 */
	public final byte getNextByteId() throws UserHandleableException {
		return (byte) getNextLongIdChecked(Byte.MAX_VALUE);
	}

	/**
	 * Returns the next Id from the pool. If there is a id generation strategy,
	 * apply next id to strategy. Otherwise return original next id.
	 * 
	 * @return the next Id
	 * @throws UserHandleableException
	 *             if the next id is outside of the valid range
	 */
	public final String getNextStringId() throws UserHandleableException {
		return strategy.makeId(getNextBigDecimalId().toString());
	}

	/**
	 * Get the next Id from the pool and apply a specific generation strategy to
	 * that id.
	 * 
	 * @param tableName
	 *            key of id management table
	 * @return the next Id
	 * @throws UserHandleableException
	 *             if the next id is outside of the valid range
	 */
	public String getNextStringId(String tableName) throws UserHandleableException {
		return strategy.makeId(getNextBigDecimalId(tableName).toString());
	}

	/**
	 * getter
	 * 
	 * @return IdGenStrategy
	 */
	public IdPolicy getStrategy() {
		return strategy;
	}

	/**
	 * setter
	 * 
	 * @param strategy
	 *            to be set by Spring Framework
	 */
	public void setStrategy(IdPolicy strategy) {
		this.strategy = strategy;
	}

	/**
	 * set BeanFactory
	 * 
	 * @param beanFactory
	 *            to be set by Spring Framework
	 */
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
}
