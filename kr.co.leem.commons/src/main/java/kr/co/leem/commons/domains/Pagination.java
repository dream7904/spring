package kr.co.leem.commons.domains;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
	
@SuppressWarnings({"serial", "rawtypes"})
public class Pagination implements Serializable {
	public static final String PAGE_KEY = "PAGE_KEY";
	protected String searchPrefix = "search";
	protected Map searchParams;
	protected int currentPage;
	protected int totalCount;
	protected int pageGroupSize = 10;
	protected int pageSize = 10;
	protected int totalPage;
	protected int beginUnitPage;
	protected int endUnitPage;
	protected List<?> datas;
	protected int startNum;
	protected int endNum;
	protected String searchKey;
	protected String searchValue;
	protected String searchValue1;
	protected String searchValue2;
	protected String searchValue3;
	
	protected Collection commonCodeList;
	
	public String getSearchPrefix() {
		return this.searchPrefix;
	}
	
	public void setSearchPrefix(String searchPrefix) {
		this.searchPrefix = searchPrefix;
	}
	
	public Map getSearchParams() {
		return this.searchParams;
	}
	
	public void setSearchParams(Map searchParams) {
		this.searchParams = searchParams;
	}
	
	public int getCurrentPage() {
		return this.currentPage;
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public int getTotalCount() {
		return this.totalCount;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		setPageInfo();
	}
	
	public int getPageGroupSize() {
		return this.pageGroupSize;
	}
	
	public void setPageGroupSize(int pageGroupSize) {
		this.pageGroupSize = pageGroupSize;
	}
	
	public int getPageSize() {
		return this.pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getTotalPage() {
		return this.totalPage;
	}
	
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	public int getBeginUnitPage() {
		return this.beginUnitPage;
	}
	
	public void setBeginUnitPage(int beginUnitPage) {
		this.beginUnitPage = beginUnitPage;
	}
	
	public int getEndUnitPage() {
		return this.endUnitPage;
	}
	
	public void setEndUnitPage(int endUnitPage) {
		this.endUnitPage = endUnitPage;
	}
	
	public List<?> getDatas() {
		return datas;
	}

	public void setDatas(List<?> datas) {
		this.datas = datas;
	}

	public int getStartNum() {
		return this.startNum;
	}
	
	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}
	
	public int getEndNum() {
		return this.endNum;
	}
	
	public void setEndNum(int endNum) {
		this.endNum = endNum;
	}
	
//	public PageInfo(HttpServletRequest request, int currentPage, int pageunit, int pagesize) {
	public Pagination(int currentPage, int pageunit, int pagesize) {
//		paramMap(request);
		this.currentPage = ((currentPage < 1) ? 1 : currentPage);
		this.pageGroupSize = pageunit;
		this.pageSize = pagesize;
		this.startNum = ((this.currentPage - 1) * this.pageSize);
		this.endNum = (this.currentPage * this.pageSize);
	}
	
	public Pagination(int currentPage) {
//		paramMap(request);
		this.currentPage = currentPage;
	}
	
	public void setPageInfo() {
		this.totalPage = ((this.pageSize == 0) ? this.totalCount : (this.totalCount - 1) / this.pageSize + 1);
		this.currentPage = ((this.currentPage > this.totalPage) ? this.totalPage : this.currentPage);
		this.beginUnitPage = ((this.currentPage - 1) / this.pageGroupSize * this.pageGroupSize + 1);
		this.endUnitPage = (this.beginUnitPage + this.pageGroupSize - 1);
	}
	
//	public void paramMap(HttpServletRequest request) {
//		Enumeration paramNames = request.getParameterNames();
//		this.searchParams = new HashMap();
//		try {
//			while (paramNames.hasMoreElements()) {
//			String paramKey = (String)paramNames.nextElement();
//			if (paramKey.startsWith(this.searchPrefix))
//				this.searchParams.put(paramKey, URLEncoder.encode(
//				StringUtils.defaultIfEmpty(request.getParameter(paramKey), ""), "UTF-8"));
//			}
//		}
//		catch (Exception e)
//		{
//		}
//	}
//	
	public String getParamToString() {
		String result = "";
		StringBuffer sb = new StringBuffer();
		Iterator paramKey = this.searchParams.keySet().iterator();
		int i = 0;
		while (paramKey.hasNext()) {
			String key = (String)paramKey.next();
			if (i != 0) {
				sb.append("&");
			}
			sb.append(key);
			sb.append("=");
			sb.append(this.searchParams.get(key));
			++i;
		}
		try {
			result = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean hasPreviousPageUnit() {
		return (this.currentPage >= this.pageGroupSize + 1);
	}
	
	public int getPageOfPreviousPageUnit() {
		return ((this.currentPage - this.pageGroupSize > 1) ? this.currentPage - this.pageGroupSize : 1);
	}
	
	public boolean hasPreviousPage() {
		return (this.currentPage > 1);
	}
	
	public int getPreviousPage() {
		return (this.currentPage - 1);
	}
	
	public boolean isEmptyPage() {
		return (getTotalCount() < 1);
	}
	
	public int getEndListPage() {
		return ((this.endUnitPage > this.totalPage) ? this.totalPage : this.endUnitPage);
	}
	
	public boolean hasNextPage() {
		return (this.currentPage < this.totalPage);
	}
	
	public int getNextPage() {
		return (this.currentPage + 1);
	}
	
	public boolean hasNextPageUnit() {
		return (this.endUnitPage < this.totalPage);
	}
	
	public int getPageOfNextPageUnit() {
		return ((this.currentPage + this.pageGroupSize < this.totalPage) ? this.currentPage + this.pageGroupSize : this.totalPage);
	}

	/**
	 * @return the commonCodeList
	 */
	public Collection getCommonCodeList() {
		return commonCodeList;
	}

	/**
	 * @param commonCodeList the commonCodeList to set
	 */
	public void setCommonCodeList(Collection commonCodeList) {
		this.commonCodeList = commonCodeList;
	}

	/**
	 * @return the searchKey
	 */
	public String getSearchKey() {
		return searchKey;
	}

	/**
	 * @param searchKey the searchKey to set
	 */
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	/**
	 * @return the searchValue
	 */
	public String getSearchValue() {
		return searchValue;
	}

	/**
	 * @param searchValue the searchValue to set
	 */
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	/**
	 * @return the searchValue1
	 */
	public String getSearchValue1() {
		return searchValue1;
	}

	/**
	 * @param searchValue1 the searchValue1 to set
	 */
	public void setSearchValue1(String searchValue1) {
		this.searchValue1 = searchValue1;
	}

	/**
	 * @return the searchValue2
	 */
	public String getSearchValue2() {
		return searchValue2;
	}

	/**
	 * @param searchValue2 the searchValue2 to set
	 */
	public void setSearchValue2(String searchValue2) {
		this.searchValue2 = searchValue2;
	}

	/**
	 * @return the searchValue3
	 */
	public String getSearchValue3() {
		return searchValue3;
	}

	/**
	 * @param searchValue3 the searchValue3 to set
	 */
	public void setSearchValue3(String searchValue3) {
		this.searchValue3 = searchValue3;
	}
}