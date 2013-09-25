package vwg.skoda.prcek.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SK30T_VYSLEDEK database table.
 * 
 */
@Entity
@Table(name="SK30T_VYSLEDEK", schema="prcek")
public class Vysledek implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SK30T_VYSLEDEK_ID_GENERATOR", sequenceName="PRCEK.HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SK30T_VYSLEDEK_ID_GENERATOR")
	private long id;

	@Column(name="ERR_LOG")
	private String errLog;

	private BigDecimal soucet;

	@Temporal(TemporalType.TIMESTAMP)
	private Date utime;

	private String uuser;

	//bi-directional many-to-one association to Sk30tPrPodminka
	@ManyToOne
	@JoinColumn(name="ID_PR_PODMINKA")
	private PrPodminka sk30tPrPodminka;

	//bi-directional many-to-one association to OfflineJob
	@ManyToOne
	@JoinColumn(name="ID_OFFLINE_JOB")
	private OfflineJob sk30tOfflineJob;

	public Vysledek() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getErrLog() {
		return this.errLog;
	}

	public void setErrLog(String errLog) {
		this.errLog = errLog;
	}

	public BigDecimal getSoucet() {
		return this.soucet;
	}

	public void setSoucet(BigDecimal soucet) {
		this.soucet = soucet;
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



	public PrPodminka getSk30tPrPodminka() {
		return sk30tPrPodminka;
	}

	public void setSk30tPrPodminka(PrPodminka sk30tPrPodminka) {
		this.sk30tPrPodminka = sk30tPrPodminka;
	}

	public OfflineJob getSk30tOfflineJob() {
		return this.sk30tOfflineJob;
	}

	public void setSk30tOfflineJob(OfflineJob sk30tOfflineJob) {
		this.sk30tOfflineJob = sk30tOfflineJob;
	}

}