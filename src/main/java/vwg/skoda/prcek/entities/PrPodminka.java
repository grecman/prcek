package vwg.skoda.prcek.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;



@Entity
@Table(name="SK30T_PR_PODMINKA", schema="prcek")
public class PrPodminka implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SK30T_PR_PODMINKA_ID_GENERATOR", sequenceName="PRCEK.HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SK30T_PR_PODMINKA_ID_GENERATOR")
	@Column(unique=true, nullable=false, precision=22)
	private long id;

	@Column(length=40)
	private String nazev;

	@Column(precision=22)
	private BigDecimal poradi;

	@Column(length=40)
	private String poznamka;

	@Column(length=1)
	private String test;

	@Temporal(TemporalType.TIMESTAMP)
	private Date utime;

	@Column(length=30)
	private String uuser;
	
	@Column(nullable=false, length=800)
    private String pr;
	
	@Column(name="ERR_MBT", length=2000)
    private String errMbt;

	//bi-directional many-to-one association to Sada
	@ManyToOne
	@JoinColumn(name="ID_SADA", nullable=false)
	private Sada sk30tSada;
	
	//bi-directional many-to-one association to Vysledek
	@OneToMany(mappedBy="sk30tPrPodminka")
	private List<Vysledek> sk30tVysledeks;

	public PrPodminka() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNazev() {
		return this.nazev;
	}

	public void setNazev(String nazev) {
		this.nazev = nazev;
	}

	public BigDecimal getPoradi() {
		return this.poradi;
	}

	public void setPoradi(BigDecimal poradi) {
		this.poradi = poradi;
	}

	public String getPoznamka() {
		return this.poznamka;
	}

	public void setPoznamka(String poznamka) {
		this.poznamka = poznamka;
	}

	public String getTest() {
		return this.test;
	}

	public void setTest(String test) {
		this.test = test;
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
	

	public Sada getSk30tSada() {
		return this.sk30tSada;
	}

	public void setSk30tSada(Sada sk30tSada) {
		this.sk30tSada = sk30tSada;
	}

	public String getPr() {
		return pr;
	}

	public void setPr(String pr) {
		this.pr = pr;
	}

	public String getErrMbt() {
		return errMbt;
	}

	public void setErrMbt(String errMbt) {
		this.errMbt = errMbt;
	}

	public List<Vysledek> getSk30tVysledeks() {
		return sk30tVysledeks;
	}

	public void setSk30tVysledeks(List<Vysledek> sk30tVysledeks) {
		this.sk30tVysledeks = sk30tVysledeks;
	}
	

}