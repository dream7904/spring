package kr.co.leem.domains.menu;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015-03-18.
 */
@Entity(name = "TopMenuGrp")
@Table(name = "TOP_MENU_GRP")
public class TopMenuGrp {
	private Long topMenuGrpSeq;
	private String name;
	private String description;
	private String url;
	private String onMenuImg;
	private String offMenuImg;
	private Boolean enabled;
	private String iconNm;
	private Integer ord;
	private List<MidMenuGrp> midMenuGrps;
	private Date regDate;
	private String regId;
	private Date updDate;
	private String updId;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "topMenuGrpSeq", length = 32)
	public Long getTopMenuGrpSeq() {
		return topMenuGrpSeq;
	}

	public void setTopMenuGrpSeq(Long topMenuGrpSeq) {
		this.topMenuGrpSeq = topMenuGrpSeq;
	}

	@Basic
	@Column(name = "name", length = 30)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "description", length = 120)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Basic
	@Column(name = "url", length = 255)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Basic
	@Column(name = "onMenuImg", length = 255)
	public String getOnMenuImg() {
		return onMenuImg;
	}

	public void setOnMenuImg(String onMenuImg) {
		this.onMenuImg = onMenuImg;
	}

	@Basic
	@Column(name = "offMenuImg", length = 255)
	public String getOffMenuImg() {
		return offMenuImg;
	}

	public void setOffMenuImg(String offMenuImg) {
		this.offMenuImg = offMenuImg;
	}

	@Basic
	@Column(name = "enabled", nullable = false)
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Basic
	@Column(name = "iconNm", length = 50)
	public String getIconNm() {
		return iconNm;
	}

	public void setIconNm(String iconNm) {
		this.iconNm = iconNm;
	}

	@Basic
	@Column(name = "ord", length = 10)
	public Integer getOrd() {
		return ord;
	}

	public void setOrd(Integer ord) {
		this.ord = ord;
	}

	@OneToMany(mappedBy = "topMenuGrp", fetch = FetchType.EAGER)
	public List<MidMenuGrp> getMidMenuGrps() {
		return midMenuGrps;
	}

	public void setMidMenuGrps(List<MidMenuGrp> midMenuGrps) {
		this.midMenuGrps = midMenuGrps;
	}

	@Basic
	@Column(name = "regDate", length = 20)
	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	@Basic
	@Column(name = "regId", length = 32)
	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	@Basic
	@Column(name = "updDate", length = 20)
	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	@Basic
	@Column(name = "updId", length = 32)
	public String getUpdId() {
		return updId;
	}

	public void setUpdId(String updId) {
		this.updId = updId;
	}
}