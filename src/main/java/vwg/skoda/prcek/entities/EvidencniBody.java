package vwg.skoda.prcek.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the SK30T_EVIDENCNI_BODY database table.
 * 
 */
@Entity
@Table(name="SK30T_EVIDENCNI_BODY", schema="PRCEK")
public class EvidencniBody implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SK30T_EVIDENCNI_BODY_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SK30T_EVIDENCNI_BODY_ID_GENERATOR")
	private long id;

	@Column(name="FIS_NAZE")
	private String fisNaze;

	@Column(name="KBOD_EVID")
	private String kbodEvid;
	
	@Column(name="KBOD_KOD")
	private String kbodKod;

	@Column(name="KBOD_WK")
	private String kbodWk;

	@Temporal(TemporalType.TIMESTAMP)
	private Date utime;

	private String uuser;
	
	//bi-directional many-to-one association to OfflineJob
	@OneToMany(mappedBy="sk30tEvidencniBody")
	private List<OfflineJob> sk30tOfflineJobs;

	public EvidencniBody() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFisNaze() {
		return this.fisNaze;
	}

	public void setFisNaze(String fisNaze) {
		this.fisNaze = fisNaze;
	}

	public String getKbodEvid() {
		return this.kbodEvid;
	}

	public void setKbodEvid(String kbodEvid) {
		this.kbodEvid = kbodEvid;
	}
	
	
	public String getKbodKod() {
		return kbodKod;
	}

	public void setKbodKod(String kbodKod) {
		this.kbodKod = kbodKod;
	}

	public String getKbodWk() {
		return this.kbodWk;
	}

	public void setKbodWk(String kbodWk) {
		this.kbodWk = kbodWk;
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

	public List<OfflineJob> getSk30tOfflineJobs() {
		return sk30tOfflineJobs;
	}

	public void setSk30tOfflineJobs(List<OfflineJob> sk30tOfflineJobs) {
		this.sk30tOfflineJobs = sk30tOfflineJobs;
	}
	
}