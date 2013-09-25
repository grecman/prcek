package vwg.skoda.prcek.entities;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.Set;



@Entity
@Table(name="SK30T_MT", schema="prcek")
public class Mt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SK30T_MT_ID_GENERATOR", sequenceName="PRCEK.HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SK30T_MT_ID_GENERATOR")
	@Column(unique=true, nullable=false, precision=22)
	private long id;

	@Column(nullable=false, length=2)
	private String mt;

	@Column(length=4)
	private String produkt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date utime;

	@Column(length=30)
	private String uuser;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="ID_USER", nullable=false)
	private User sk30tUser;

	//bi-directional many-to-one association to Sada
	@OneToMany(mappedBy="sk30tMt")
	private Set<Sada> sk30tSadas;

	public Mt() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMt() {
		return this.mt;
	}

	public void setMt(String mt) {
		this.mt = mt;
	}

	public String getProdukt() {
		return this.produkt;
	}

	public void setProdukt(String produkt) {
		this.produkt = produkt;
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

	public User getSk30tUser() {
		return this.sk30tUser;
	}

	public void setSk30tUser(User sk30tUser) {
		this.sk30tUser = sk30tUser;
	}

	public Set<Sada> getSk30tSadas() {
		return this.sk30tSadas;
	}

	public void setSk30tSadas(Set<Sada> sk30tSadas) {
		this.sk30tSadas = sk30tSadas;
	}

}