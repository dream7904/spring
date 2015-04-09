package kr.co.leem.repositories.jpa.criterias;

import kr.co.leem.commons.constants.ResultType;
import kr.co.leem.domains.account.Account;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

/**
 * Criteria 기반 DAO 구현.
 *
 * Created by Administrator on 2015-03-20.
 */
@Repository
public class SampleCriteriaDAOImpl implements SampleCriteriaDAO {
	@PersistenceContext private EntityManager entityManager;

	@Override
	public void getSelectDatas(Map<ResultType, Object> resultMap) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Account> cq = builder.createQuery(Account.class);

		Root<Account> root = cq.from(Account.class);

		cq.select(root).where(builder.equal(root.get("accountId"), "admin"));

		List<Account> accounts = entityManager.createQuery(cq).getResultList();

		CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
		countQuery.select(builder.count(countQuery.from(Account.class))).where(builder.equal(root.get("accountId"), "admin"));
		Long count = entityManager.createQuery(countQuery).getSingleResult();

		System.out.println(count);
	}
}