package vwg.skoda.prcek.entities;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.Set;

/**
 * pokud neuvedu nejake konkretni jmeno sequence (v oraclu) tak je defaultne pouzite jmeno HIBERNATE_SEQUENCE
 */
@Entity
@Table(name="SK30T_USER", schema="prcek")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SK30T_USER_ID_GENERATOR", sequenceName="PRCEK.HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SK30T_USER_ID_GENERATOR")
	@Column(unique=true, nullable=false, precision=22)
	private long id;

	@Column(length=30)
	private String jmeno;

	@Column(nullable=false, length=30)
	private String netusername;

	@Column(length=10)
	private String oddeleni;

	@Column(length=30)
	private String prijmeni;

	@Temporal(TemporalType.TIMESTAMP)
	private Date utime;

	@Column(length=30)
	private String uuser;

	//bi-directional many-to-one association to Mt
	@OneToMany(mappedBy="sk30tUser")
	private Set<Mt> sk30tMts;

	public User() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getJmeno() {
		return this.jmeno;
	}

	public void setJmeno(String jmeno) {
		this.jmeno = jmeno;
	}

	public String getNetusername() {
		return this.netusername;
	}

	public void setNetusername(String netusername) {
		this.netusername = netusername;
	}

	public String getOddeleni() {
		return this.oddeleni;
	}

	public void setOddeleni(String oddeleni) {
		this.oddeleni = oddeleni;
	}

	public String getPrijmeni() {
		return this.prijmeni;
	}

	public void setPrijmeni(String prijmeni) {
		this.prijmeni = prijmeni;
	}

	public Date getUtime() {
		return this.utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

	public String getUuser() {
		return this.uuser;
	}

	public void setUuser(String uuser) {
		this.uuser = uuser;
	}

	public Set<Mt> getSk30tMts() {
		return this.sk30tMts;
	}

	public void setSk30tMts(Set<Mt> sk30tMts) {
		this.sk30tMts = sk30tMts;
	}

}