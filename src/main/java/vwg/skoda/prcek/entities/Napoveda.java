package vwg.skoda.prcek.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="SK30T_NAPOVEDA", schema="prcek")
public class Napoveda implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="SK30T_NAPOVEDA_ID_GENERATOR", sequenceName="PRCEK.HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SK30T_NAPOVEDA_ID_GENERATOR")
	private long id;

	@Column(nullable=false, length=64)
	private String tema;

	@Column(nullable=false, length=2000)
	private String popis;

	@Temporal(TemporalType.TIMESTAMP)
	private Date utime;

	@Column(length=30)
	private String uuser;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public String getPopis() {
		return popis;
	}

	public void setPopis(String popis) {
		this.popis = popis;
	}

	public Date getUtime() {
		return utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

	public String getUuser() {
		return uuser;
	}

	public void setUuser(String uuser) {
		this.uuser = uuser;
	}
	
	

}
