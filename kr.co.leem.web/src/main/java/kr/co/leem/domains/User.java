package kr.co.leem.domains;

import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Created by Administrator on 2015-03-02.
 */
@Entity(name = "User")
@Table(name = "USER")
public class User {
	private long id;
	private String account;
	private String password;
	private String accountName;
	private String telNum;
	private String hpNum;
	private String postNum;
	private String address;
	private String addrDetail;
	private String config;
	private String regId;
	private DateTime regDate;
	private String updId;
	private DateTime updDate;

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
	@Column(name = "account", length = 32)
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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
	@Column(name = "accountName", length = 32)
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
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

	@Column(name = "regDate")
	public DateTime getRegDate() {
		return regDate;
	}

	public void setRegDate(DateTime regDate) {
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
	@Column(name = "updDate")
	public DateTime getUpdDate() {
		return updDate;
	}

	public void setUpdDate(DateTime updDate) {
		this.updDate = updDate;
	}
}