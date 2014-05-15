package vwg.skoda.prcek.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="SK30T_SADA", schema="prcek")
public class Sada implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SK30T_SADA_ID_GENERATOR", sequenceName="PRCEK.HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SK30T_SADA_ID_GENERATOR")
	@Column(unique=true, nullable=false, precision=22)
	private long id;

	@Column(nullable=false, length=40)
	private String nazev;

	@Temporal(TemporalType.TIMESTAMP)
	private Date utime;

	@Column(length=30)
	private String uuser;
	
	// pocet importovanych PR ze vstupniho souboru
	@Column
	private Integer pocet;
	
	@Column
	private String rozpracovano;

	//bi-directional many-to-one association to PrPodminka
	@OneToMany(mappedBy="sk30tSada")
	private Set<PrPodminka> sk30tPrPodminkas;

	//bi-directional many-to-one association to OfflineJob
	@OneToMany(mappedBy="sk30tSada")
	private List<OfflineJob> sk30tOfflineJobs;
	
	//bi-directional many-to-one association to Mt
	@ManyToOne
	@JoinColumn(name="ID_MT", nullable=false)
	private Mt sk30tMt;
	


	public Sada() {
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

	public Set<PrPodminka> getSk30tPrPodminkas() {
		return this.sk30tPrPodminkas;
	}

	public void setSk30tPrPodminkas(Set<PrPodminka> sk30tPrPodminkas) {
		this.sk30tPrPodminkas = sk30tPrPodminkas;
	}
	
	
	public List<OfflineJob> getSk30tOfflineJobs() {
		return sk30tOfflineJobs;
	}

	public void setSk30tOfflineJobs(List<OfflineJob> sk30tOfflineJobs) {
		this.sk30tOfflineJobs = sk30tOfflineJobs;
	}

	public Mt getSk30tMt() {
		return this.sk30tMt;
	}

	public void setSk30tMt(Mt sk30tMt) {
		this.sk30tMt = sk30tMt;
	}

	public Integer getPocet() {
		return pocet;
	}

	public void setPocet(Integer pocet) {
		this.pocet = pocet;
	}

	public String getRozpracovano() {
		return rozpracovano;
	}

	public void setRozpracovano(String rozpracovano) {
		this.rozpracovano = rozpracovano;
	}	
	
	

}