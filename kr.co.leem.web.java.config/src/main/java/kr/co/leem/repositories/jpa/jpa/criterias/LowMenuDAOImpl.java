package kr.co.leem.repositories.jpa.jpa.criterias;

import kr.co.leem.domains.menu.LowMenu;
import kr.co.leem.domains.menu.MenuReq;
import kr.co.leem.utils.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Administrator on 2015-03-20.
 */
@Repository
public class LowMenuDAOImpl implements LowMenuDAO {
	@PersistenceContext private EntityManager entityManager;

	@Override
	public long cntTotalLowMenus(MenuReq menuReq) throws Exception {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> cq = builder.createQuery(Long.class);
		Root<LowMenu> root = cq.from(LowMenu.class);

		cq.select(builder.count(root)).where(builder.equal(root.get("mmgSeq"), menuReq.getMidMenuGrpSeq()));

		if (StringUtils.isNotEmpty(menuReq.getSearchPhrase())) {
			cq.where(
				builder.equal(root.get("mmgSeq"), menuReq.getMidMenuGrpSeq()),
				builder.like((Expression)root.get("name"), "%" + menuReq.getSearchPhrase() + "%"),
				builder.like((Expression)root.get("description"), "%" + menuReq.getSearchPhrase() + "%")
			);
		}

		return entityManager.createQuery(cq).getSingleResult();
	}

	@Override
	public List<LowMenu> getLowMenus(MenuReq menuReq) throws Exception {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<LowMenu> cq = builder.createQuery(LowMenu.class);

		Root<LowMenu> root = cq.from(LowMenu.class);

		cq.select(root).where(builder.equal(root.get("mmgSeq"), menuReq.getMidMenuGrpSeq()));

		if (StringUtils.isNotEmpty(menuReq.getSearchPhrase())) {
			cq.where(
					builder.equal(root.get("mmgSeq"), menuReq.getMidMenuGrpSeq()),
					builder.like((Expression)root.get("name"), "%" + menuReq.getSearchPhrase() + "%"),
					builder.like((Expression)root.get("description"), "%" + menuReq.getSearchPhrase() + "%")
			);
		}

		List<LowMenu> lowMenus = entityManager.createQuery(cq).getResultList();

		return lowMenus;
	}
}