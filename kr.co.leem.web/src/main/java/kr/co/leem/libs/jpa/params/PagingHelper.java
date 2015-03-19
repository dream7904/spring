package kr.co.leem.libs.jpa.params;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PagingHelper {
	public static PageInfo createPageInfo(Page<?> page) {
		PageInfo pi = null;
		if (page != null) {
			pi = new PageInfo();
			pi.setTotalPage(page.getTotalPages());
			pi.setTotalElements(page.getTotalElements());
			pi.setHasPreviousPage(page.hasPrevious());
			pi.setHasNextPage(page.hasNext());
			pi.setIsFirstPage(page.isFirst());
			pi.setIsLastPage(page.isLast());
			PageParams pp = createPageParam(page);
			pi.setPageParam(pp);
		}
		return pi;
	}

	public static PageParams createPageParam(Page<?> page) {
		PageParams pp = null;
		if (page != null) {
			pp = new PageParams();
			pp.setCurrent(page.getNumber() + 1);
			pp.setRowCount(page.getSize());
			Sort sort = page.getSort();
			List<PageSort> ps = createPageSort(sort);
			pp.setSort(ps);
		}
		return pp;
	}

	public static List<PageSort> createPageSort(Sort sort) {
		List<PageSort> psList = null;
		if (sort != null && sort.iterator() != null) {
			psList = new ArrayList<PageSort>();
			Iterator<Order> oIter = sort.iterator();
			while (oIter.hasNext()) {
				Order order = oIter.next();
				if (order != null) {
					PageSort ps = new PageSort();
					PageSortDirectionType psdt = createPageSortDirectionType(order.getDirection());
					ps.setDirection(psdt);
					ps.setProperty(order.getProperty());
					psList.add(ps);
				}
			}
		}
		return psList;
	}

	public static PageRequest createPageRequest(PageParams pageparam) {
		PageRequest pageRequest = null;
		if (pageparam != null) {
			int pageNumber = pageparam.getCurrent();
			if (pageNumber != 0)
				pageNumber -= 1;
			int pageSize = pageparam.getRowCount();
			Sort sort = createSort(pageparam.getSort());
			if (sort != null) {
				pageRequest = new PageRequest(pageNumber, pageSize, sort);
			} else {
				pageRequest = new PageRequest(pageNumber, pageSize);
			}
		}
		return pageRequest;
	}

	public static Sort createSort(List<PageSort> pageSorts) {
		Sort sort = null;
		if (pageSorts != null && pageSorts.size() > 0) {
			List<Order> orders = new ArrayList<Order>();
			for (PageSort ps : pageSorts) {
				Sort.Direction sd = createSortDirection(ps.getDirection());
				String property = ps.getProperty();
				Order order = null;
				if (sd == null) {
					order = new Order(property);
				} else {
					order = new Order(sd, property);
				}
				orders.add(order);
			}
			sort = new Sort(orders);
		}
		return sort;
	}

	public static Sort.Direction createSortDirection(PageSortDirectionType psdt) {
		Sort.Direction sd = null;
		if (psdt != null) {
			if (PageSortDirectionType.ASC.equals(psdt)) {
				sd = Sort.Direction.ASC;
			} else if (PageSortDirectionType.DESC.equals(psdt)) {
				sd = Sort.Direction.DESC;
			}
		}
		return sd;
	}

	public static PageSortDirectionType createPageSortDirectionType(Sort.Direction sd) {
		PageSortDirectionType psdt = null;
		if (sd != null) {
			if (Sort.Direction.ASC.equals(sd)) {
				psdt = PageSortDirectionType.ASC;
			} else if (Sort.Direction.DESC.equals(sd)) {
				psdt = PageSortDirectionType.DESC;
			}
		}
		return psdt;
	}
}
