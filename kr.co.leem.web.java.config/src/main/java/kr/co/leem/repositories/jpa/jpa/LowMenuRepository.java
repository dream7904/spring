package kr.co.leem.repositories.jpa.jpa;

import kr.co.leem.domains.menu.LowMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2015-03-18.
 */
public interface LowMenuRepository extends JpaRepository <LowMenu, Long> {
	public Page<LowMenu> findAllByMmgSeqOrderByOrdAsc(Pageable pageable, Long mmgSeq);
	public Page<LowMenu> findAllByMmgSeqAndNameContainingOrDescriptionContainingOrderByOrdAsc(Pageable pageable, Long mmgSeq, String name, String description);
}