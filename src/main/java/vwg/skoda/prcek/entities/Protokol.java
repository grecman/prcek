package vwg.skoda.prcek.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the SK30T_PROTOKOL database table.
 * 
 */
@Entity
@Table(name="SK30T_PROTOKOL", schema="PRCEK")
public class Protokol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SK30T_PROTOKOL_ID_GENERATOR", sequenceName="PRCEK.HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SK30T_PROTOKOL_ID_GENERATOR")
	private long id;

	private String action;

	private String info;

	private String netusername;

	private String sessionid;

	@Temporal(TemporalType.TIMESTAMP)
	private Date time;

	public Protokol() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getNetusername() {
		return this.netusername;
	}

	public void setNetusername(String netusername) {
		this.netusername = netusername;
	}

	public String getSessionid() {
		return this.sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}