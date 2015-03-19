package kr.co.leem.libs.jpa.params;

public enum PageSortDirectionType {
	ASC,
	DESC;

	public String value() {
		return name();
	}

	public static PageSortDirectionType fromValue(String v) {
		return valueOf(v);
	}
}