package kr.co.leem.domains.menu;

import kr.co.leem.libs.jpa.params.PageParams;

/**
 * Created by Administrator on 2015-03-19.
 */
public class MenuReq extends PageParams {
	private long topMenuGrpSeq;
	private boolean using;

	public long getTopMenuGrpSeq() {
		return topMenuGrpSeq;
	}

	public void setTopMenuGrpSeq(long topMenuGrpSeq) {
		this.topMenuGrpSeq = topMenuGrpSeq;
	}

	public boolean isUsing() {
		return using;
	}

	public void setUsing(boolean using) {
		this.using = using;
	}
}