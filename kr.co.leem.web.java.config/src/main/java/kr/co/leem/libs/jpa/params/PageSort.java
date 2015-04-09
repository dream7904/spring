package kr.co.leem.libs.jpa.params;

public class PageSort {
	protected PageSortDirectionType direction = PageSortDirectionType.ASC;
	protected String property;

	public PageSortDirectionType getDirection() {
		return direction;
	}

	public void setDirection(PageSortDirectionType value) {
		this.direction = value;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String value) {
		this.property = value;
	}
}