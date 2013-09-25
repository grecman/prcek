package vwg.skoda.prcek.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="SK30T_MT_SEZNAM", schema="PRCEK")
public class MtSeznam implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SK30T_MT_SEZNAM_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SK30T_MT_SEZNAM_ID_GENERATOR")
	private long id;

	@Column
	private String mt;

	@Column
	private String produkt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date utime;

	@Column
	private String uuser;

	public MtSeznam() {
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

}