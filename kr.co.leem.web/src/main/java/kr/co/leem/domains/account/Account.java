package kr.co.leem.domains.account;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2015-03-02.
 */
@Entity(name = "Account")
@Table(name = "ACCOUNT")
public class Account {
	private long id;
	private String accountId;
	private String password;
	private String name;
	private String telNum;
	private String hpNum;
	private String postNum;
	private String address;
	private String addrDetail;
	private String config;
	private String regId;
	private Date regDate;
	private String updId;
	private Date updDate;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seq", length = 32)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Basic
	@Column(name = "accountId", length = 32, unique = true)
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	@Basic
	@Column(name = "password", length = 256)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Basic
	@Column(name = "name", length = 32)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "telNum", length = 13)
	public String getTelNum() {
		return telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	@Basic
	@Column(name = "hpNum", length = 13)
	public String getHpNum() {
		return hpNum;
	}

	public void setHpNum(String hpNum) {
		this.hpNum = hpNum;
	}

	@Basic
	@Column(name = "postNum", length = 7)
	public String getPostNum() {
		return postNum;
	}

	public void setPostNum(String postNum) {
		this.postNum = postNum;
	}

	@Basic
	@Column(name = "address", length = 50)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Basic
	@Column(name = "addrDetail", length = 100)
	public String getAddrDetail() {
		return addrDetail;
	}

	public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}

	@Lob
	@Column(name = "config", columnDefinition="TEXT")
	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	@Basic
	@Column(name = "regId", length = 20)
	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
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
	@Column(name = "updId", length = 20)
	public String getUpdId() {
		return updId;
	}

	public void setUpdId(String updId) {
		this.updId = updId;
	}

	@Basic
	@Column(name = "updDate", length = 20)
	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}
}