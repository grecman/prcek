package vwg.skoda.prcek.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name="gz09t52", schema="zentaadm")
public class UserZentaAdm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="NET_USERNAME")
	private String netUsername;

	private String building;

	@Column(name="COST_CENTER")
	private String costCenter;

	private String department;

	private String domain;

	private String email;

	private String fax;

	@Column(name="FIRST_NAME")
	private String firstName;

	@Column(name="ID_SKONET")
	private BigDecimal idSkonet;

	@Column(name="INFO_SOURCE")
	private String infoSource;

	private String mobile;

	@Column(name="NET_DOMAIN")
	private String netDomain;

	private String office;

	private String phone;

	private String phone2;

	private String surname;

	private String title;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="USER_END_DATE")
	private Date userEndDate;

	@Column(name="USER_NR")
	private String userNr;

	@Column(name="USER_TYPE")
	private String userType;

	private String username;

	public UserZentaAdm() {
	}

	public String getNetUsername() {
		return this.netUsername;
	}

	public void setNetUsername(String netUsername) {
		this.netUsername = netUsername;
	}

	public String getBuilding() {
		return this.building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getCostCenter() {
		return this.costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public BigDecimal getIdSkonet() {
		return this.idSkonet;
	}

	public void setIdSkonet(BigDecimal idSkonet) {
		this.idSkonet = idSkonet;
	}

	public String getInfoSource() {
		return this.infoSource;
	}

	public void setInfoSource(String infoSource) {
		this.infoSource = infoSource;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNetDomain() {
		return this.netDomain;
	}

	public void setNetDomain(String netDomain) {
		this.netDomain = netDomain;
	}

	public String getOffice() {
		return this.office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone2() {
		return this.phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getUserEndDate() {
		return this.userEndDate;
	}

	public void setUserEndDate(Date userEndDate) {
		this.userEndDate = userEndDate;
	}

	public String getUserNr() {
		return this.userNr;
	}

	public void setUserNr(String userNr) {
		this.userNr = userNr;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}