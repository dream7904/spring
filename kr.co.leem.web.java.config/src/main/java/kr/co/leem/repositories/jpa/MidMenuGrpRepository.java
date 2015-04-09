package kr.co.leem.repositories.jpa;

import kr.co.leem.domains.menu.MidMenuGrp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2015-03-18.
 */
public interface MidMenuGrpRepository extends JpaRepository<MidMenuGrp, Long> {
	public Page<MidMenuGrp> findAllByTmgSeqOrderByOrdAsc(Pageable pageable, Long tmgSeq);
	public Page<MidMenuGrp> findAllByTmgSeqAndNameContainingOrDescriptionContainingOrderByOrdAsc(Pageable pageable, Long tmgSeq, String name, String description);
}