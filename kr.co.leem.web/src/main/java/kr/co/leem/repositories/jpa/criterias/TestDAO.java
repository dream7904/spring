package kr.co.leem.repositories.jpa.criterias;

import kr.co.leem.constants.ResultType;

import java.util.Map;

/**
 * Created by Administrator on 2015-03-20.
 */
public interface TestDAO {
	public void getTestData(Map<ResultType, Object> resultMap);
}