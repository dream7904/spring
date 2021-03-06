package kr.co.leem.domains.menu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2015-01-15.
 */
@Entity(name = "LowMenu")
@Table(name = "LOW_MENU")
@JsonIgnoreProperties(value = {"midMenuGrp"})
public class LowMenu {
	private Long lowMenuSeq;
	private Long mmgSeq;
	private MidMenuGrp midMenuGrp;
	private String name;
	private String url;
	private String description;
	private Boolean enabled;
	private String iconNm;
	private Integer ord;

	private Date regDate;
	private String regId;
	private Date updDate;
	private String updId;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "lowMenuSeq", length = 32)
	public Long getLowMenuSeq() {
		return lowMenuSeq;
	}

	public void setLowMenuSeq(Long lowMenuSeq) {
		this.lowMenuSeq = lowMenuSeq;
	}

	@Basic
	@Column(name = "mmgSeq", length = 32)
	public Long getMmgSeq() {
		return mmgSeq;
	}

	public void setMmgSeq(Long mmgSeq) {
		this.mmgSeq = mmgSeq;
	}

	@ManyToOne(targetEntity = MidMenuGrp.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "mmgSeq", referencedColumnName = "midMenuGrpSeq", insertable = false, updatable = false, foreignKey = @ForeignKey(name="FK_LowMmgSeq_TO_MmgSeq"))
	public MidMenuGrp getMidMenuGrp() {
		return midMenuGrp;
	}

	public void setMidMenuGrp(MidMenuGrp midMenuGrp) {
		this.midMenuGrp = midMenuGrp;
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
	@Column(name = "url", length = 255)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
	@Column(name = "enabled")
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