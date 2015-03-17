package kr.co.leem.commons.domains;

import org.joda.time.DateTime;

/**
 * 공통코드 그룹 도메인.
 * 
 * @author Dream.
 */
public class CommCdGrp {
	private String commGrpCd;
	private String commGrpNm;
	private String commGrpCdDesc;
	private String relMdulCd;
	private String useYn;
	private DateTime regDt;
	
	public String getCommGrpCd() {
		return commGrpCd;
	}
	public void setCommGrpCd(String commGrpCd) {
		this.commGrpCd = commGrpCd;
	}
	public String getCommGrpNm() {
		return commGrpNm;
	}
	public void setCommGrpNm(String commGrpNm) {
		this.commGrpNm = commGrpNm;
	}
	public String getCommGrpCdDesc() {
		return commGrpCdDesc;
	}
	public void setCommGrpCdDesc(String commGrpCdDesc) {
		this.commGrpCdDesc = commGrpCdDesc;
	}
	public String getRelMdulCd() {
		return relMdulCd;
	}
	public void setRelMdulCd(String relMdulCd) {
		this.relMdulCd = relMdulCd;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public DateTime getRegDt() {
		return regDt;
	}
	public void setRegDt(DateTime regDt) {
		this.regDt = regDt;
	}
}