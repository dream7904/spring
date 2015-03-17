package kr.co.leem.provider.id.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import kr.co.leem.provider.id.service.IdService;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.jdbc.datasource.DataSourceUtils;

/**
 * AbstractDataSourceIdService.
 * 
 * @author 임 성천.
 */
public abstract class AbstractDataSourceIdService extends
		AbstractIdService implements IdService, DisposableBean {
	
	protected DataSource dataSource = null;
	
	protected int allocated;

	protected long nextId;

	/**
	 * 커넥션 객체를 가져옴.
	 * 
	 * @return 커넥션 객체.
	 * @throws SQLException
	 */
	protected Connection getConnection() throws SQLException {
		// 2009.10.08 - without handling connection directly
		return DataSourceUtils.getConnection(getDataSource());
	}
	
	/**
	 * lifecycle method
	 */
	public void destroy() {
		dataSource = null;
	}
	
	/**
	 * 데이터 소스 설정.
	 * 
	 * @param dataSource 데이터 소스 객체.
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * 데이터 소스 객체를 반환함.
	 * 
	 * @return 데이터 객체.
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

}
