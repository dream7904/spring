package kr.co.leem.web.services;

import kr.co.leem.constants.ResultType;
import kr.co.leem.domains.menu.MenuReq;
import kr.co.leem.domains.menu.MidMenuGrp;
import kr.co.leem.domains.menu.TopMenuGrp;
import kr.co.leem.libs.jpa.params.PagingHelper;
import kr.co.leem.repositories.jpa.MidMenuGrpRepository;
import kr.co.leem.repositories.jpa.TopMenuGrpRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Administrator on 2015-03-19.
 */
@Service
public class MenuServiceImpl implements MenuService {
	@Autowired private TopMenuGrpRepository topMenuGrpRepository;
	@Autowired private MidMenuGrpRepository midMenuGrpRepository;

	@Override
	public void getTopMenuGrps(MenuReq menuReq, Map<ResultType, Object> resultMap) throws Exception {
		Pageable pageable = PagingHelper.createPageRequest(menuReq);
		String searchPhrase = menuReq.getSearchPhrase();

		Page<TopMenuGrp> page = null;

		if (StringUtils.isEmpty(searchPhrase)) {
			page = topMenuGrpRepository.findAllByOrderByOrdDesc(pageable);
		} else {
			page = topMenuGrpRepository.findAllByNameContainingOrDescriptionContainingOrderByOrdDesc(pageable, searchPhrase, searchPhrase);
		}

		if (page != null) {
			resultMap.put(ResultType.total, page.getTotalElements()); // 총 페이지수
			resultMap.put(ResultType.current, menuReq.getCurrent());	// 현재 페이지
			resultMap.put(ResultType.record, menuReq.getRowCount()); // 총 레코드 수
			resultMap.put(ResultType.rows, page.getContent());
		}
	}

	@Override
	public void getTopMenuGrp(MenuReq menuReq, Map<ResultType, Object> resultMap) throws Exception {
		resultMap.put(ResultType.row, topMenuGrpRepository.findOne(menuReq.getTopMenuGrpSeq()));
	}

	@Override
	public void saveTopMenuGrp(TopMenuGrp topMenuGrp, Map<ResultType, Object> resultMap) throws Exception {
		resultMap.put(ResultType.row, topMenuGrpRepository.save(topMenuGrp));
	}

	@Override
	public void delTopMenuGrp(MenuReq menuReq, Map<ResultType, Object> resultMap) throws Exception {
		topMenuGrpRepository.delete(menuReq.getTopMenuGrpSeq());
	}

	@Override
	public void getMidMenuGrps(MenuReq menuReq, Map<ResultType, Object> resultMap) throws Exception {
		Pageable pageable = PagingHelper.createPageRequest(menuReq);
		String searchPhrase = menuReq.getSearchPhrase();

		Page<MidMenuGrp> page = null;

		if (StringUtils.isEmpty(searchPhrase)) {
			page = midMenuGrpRepository.findAllByTmgSeqOrderByOrdAsc(pageable, menuReq.getTopMenuGrpSeq());
		} else {
			page = midMenuGrpRepository.findAllByTmgSeqAndNameContainingOrDescriptionContainingOrderByOrdAsc(pageable, menuReq.getTopMenuGrpSeq(), menuReq.getSearchPhrase(), menuReq.getSearchPhrase());
		}

		if (page != null) {
			resultMap.put(ResultType.total, page.getTotalElements()); // 총 페이지수
			resultMap.put(ResultType.current, menuReq.getCurrent());	// 현재 페이지
			resultMap.put(ResultType.record, menuReq.getRowCount()); // 총 레코드 수
			resultMap.put(ResultType.rows, page.getContent());
		}
	}

	@Override
	public void getMidMenuGrp(MenuReq menuReq, Map<ResultType, Object> resultMap) throws Exception {
		resultMap.put(ResultType.row, midMenuGrpRepository.findOne(menuReq.getMidMenuGrpSeq()));
	}

	@Override
	public void saveMidMenuGrp(MidMenuGrp midMenuGrp, Map<ResultType, Object> resultMap) throws Exception {
		resultMap.put(ResultType.row, midMenuGrpRepository.save(midMenuGrp));
	}

	@Override
	public void delMidMenuGrp(MenuReq menuReq, Map<ResultType, Object> resultMap) throws Exception {
		midMenuGrpRepository.delete(menuReq.getMidMenuGrpSeq());
	}
}