package vwg.skoda.prcek.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="SK30T_OFFLINE_JOB",  schema="prcek")
public class OfflineJob implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SK30T_OFFLINE_JOB_ID_GENERATOR", sequenceName="PRCEK.HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SK30T_OFFLINE_JOB_ID_GENERATOR")
	private long id;

	private Long agregace;
	
	@Column(name="POCET_ZAKAZEK")
	private Long pocetZakazek;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CAS_SPUSTENI")
	private Date casSpusteni;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CAS_UKONCENI")
	private Date casUkonceni;

	@Column(name="ERR_LOG")
	private String errLog;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="PLATNOST_OD")
	private Date platnostOd;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="PLATNOST_DO")
	private Date platnostDo;

	@Temporal(TemporalType.TIMESTAMP)
	private Date utime;

	private String uuser;

	@Column(name="VYSTUP_RAZENI")
	private String vystupRazeni;

	@Column(name="STORNO_ZAKAZKY")
	private Boolean stornoZakazky;
	
	@Column(name="ZAKAZKY_VYSTUP")
	private Boolean zakazkyVystup;
	
	private String proces;

	//bi-directional many-to-one association to Sk30tEvidencniBody
	@ManyToOne
	@JoinColumn(name="ID_EVIDENCNI_BODY")
	private EvidencniBody sk30tEvidencniBody;

	//bi-directional many-to-one association to Sk30tSada
	@ManyToOne
	@JoinColumn(name="ID_SADA")
	private Sada sk30tSada;

	//bi-directional many-to-one association to Vysledek
	@OneToMany(mappedBy="sk30tOfflineJob")
	private List<Vysledek> sk30tVysledeks;
	
	public OfflineJob() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	public Long getAgregace() {
		return agregace;
	}

	public void setAgregace(Long agregace) {
		this.agregace = agregace;
	}

	public Long getPocetZakazek() {
		return pocetZakazek;
	}

	public void setPocetZakazek(Long pocetZakazek) {
		this.pocetZakazek = pocetZakazek;
	}

	public List<Vysledek> getSk30tVysledeks() {
		return sk30tVysledeks;
	}

	public void setSk30tVysledeks(List<Vysledek> sk30tVysledeks) {
		this.sk30tVysledeks = sk30tVysledeks;
	}

	public Date getCasSpusteni() {
		return this.casSpusteni;
	}

	public void setCasSpusteni(Date casSpusteni) {
		this.casSpusteni = casSpusteni;
	}

	public Date getCasUkonceni() {
		return this.casUkonceni;
	}

	public void setCasUkonceni(Date casUkonceni) {
		this.casUkonceni = casUkonceni;
	}

	public String getErrLog() {
		return this.errLog;
	}

	public void setErrLog(String errLog) {
		this.errLog = errLog;
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

	public String getVystupRazeni() {
		return this.vystupRazeni;
	}

	public void setVystupRazeni(String vystupRazeni) {
		this.vystupRazeni = vystupRazeni;
	}

	public Date getPlatnostOd() {
		return platnostOd;
	}

	public void setPlatnostOd(Date platnostOd) {
		this.platnostOd = platnostOd;
	}

	public Date getPlatnostDo() {
		return platnostDo;
	}

	public void setPlatnostDo(Date platnostDo) {
		this.platnostDo = platnostDo;
	}

	public Boolean getStornoZakazky() {
		return stornoZakazky;
	}

	public void setStornoZakazky(Boolean stornoZakazky) {
		this.stornoZakazky = stornoZakazky;
	}

	public EvidencniBody getSk30tEvidencniBody() {
		return sk30tEvidencniBody;
	}

	public void setSk30tEvidencniBody(EvidencniBody sk30tEvidencniBody) {
		this.sk30tEvidencniBody = sk30tEvidencniBody;
	}

	public Sada getSk30tSada() {
		return sk30tSada;
	}

	public void setSk30tSada(Sada sk30tSada) {
		this.sk30tSada = sk30tSada;
	}

	public String getProces() {
		return proces;
	}

	public void setProces(String proces) {
		this.proces = proces;
	}

	public Boolean getZakazkyVystup() {
		return zakazkyVystup;
	}

	public void setZakazkyVystup(Boolean zakazkyVystup) {
		this.zakazkyVystup = zakazkyVystup;
	}

	

}