package kr.co.leem.repositories.jpa.criterias;

import kr.co.leem.commons.constants.ResultType;

import java.util.Map;

/**
 * Created by Administrator on 2015-03-20.
 */
public interface SampleCriteriaDAO {
	public void getSelectDatas(Map<ResultType, Object> resultMap);
}