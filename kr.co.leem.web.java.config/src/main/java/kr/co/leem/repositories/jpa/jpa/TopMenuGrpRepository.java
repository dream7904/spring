package kr.co.leem.repositories.jpa.jpa;

import kr.co.leem.domains.menu.TopMenuGrp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2015-03-18.
 */
public interface TopMenuGrpRepository extends JpaRepository<TopMenuGrp, Long> {
	Page<TopMenuGrp> findAllByOrderByOrdDesc(Pageable pageable) throws Exception;
	Page<TopMenuGrp> findAllByNameContainingOrDescriptionContainingOrderByOrdDesc(Pageable pageable, String name, String description) throws Exception;
}