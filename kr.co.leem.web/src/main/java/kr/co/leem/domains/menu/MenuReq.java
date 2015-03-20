package kr.co.leem.domains.menu;

import kr.co.leem.libs.jpa.params.PageParams;

/**
 * Created by Administrator on 2015-03-19.
 */
public class MenuReq extends PageParams {
	private Long topMenuGrpSeq;
	private Long midMenuGrpSeq;
	private Long lowMenuSeq;
	private Boolean enabled;

	public Long getTopMenuGrpSeq() {
		return topMenuGrpSeq;
	}

	public void setTopMenuGrpSeq(Long topMenuGrpSeq) {
		this.topMenuGrpSeq = topMenuGrpSeq;
	}

	public Long getMidMenuGrpSeq() {
		return midMenuGrpSeq;
	}

	public void setMidMenuGrpSeq(Long midMenuGrpSeq) {
		this.midMenuGrpSeq = midMenuGrpSeq;
	}

	public Long getLowMenuSeq() {
		return lowMenuSeq;
	}

	public void setLowMenuSeq(Long lowMenuSeq) {
		this.lowMenuSeq = lowMenuSeq;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}