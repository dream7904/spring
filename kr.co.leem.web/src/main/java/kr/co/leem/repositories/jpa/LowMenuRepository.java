package kr.co.leem.repositories.jpa;

import kr.co.leem.domains.menu.LowMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2015-03-18.
 */
public interface LowMenuRepository extends JpaRepository <LowMenu, Long> {
	public Page<LowMenu> findAllByMidMenuGrpTmgSeqAndMmgSeqOrderByOrdAsc(Pageable pageable);
	public Page<LowMenu> findAllByMidMenuGrpTmgSeqAndMmgSeqAndNameContainingOrDescriptionContainingOrderByOrdAsc(Pageable pageable, String name, String description);
}