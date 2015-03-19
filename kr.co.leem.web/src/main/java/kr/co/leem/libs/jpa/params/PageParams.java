package kr.co.leem.libs.jpa.params;

import java.util.ArrayList;
import java.util.List;

public class PageParams {
	protected int current;
	protected Integer rowCount = 20;
	protected String searchPhrase;
	protected List<PageSort> sort;

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public Integer getRowCount() {
		return rowCount;
	}

	public String getSearchPhrase() {
		return searchPhrase;
	}

	public void setSearchPhrase(String searchPhrase) {
		this.searchPhrase = searchPhrase;
	}

	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}

	public List<PageSort> getSort() {
		if (sort == null) {
			sort = new ArrayList<PageSort>();
		}
		return this.sort;
	}

	public void setSort(List<PageSort> value) {
		this.sort = value;
	}
}
