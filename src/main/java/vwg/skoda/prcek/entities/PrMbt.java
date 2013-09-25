package vwg.skoda.prcek.entities;

import java.io.Serializable;

import javax.persistence.*;

import cz.skoda.mbt.IMBTRow;

@Entity
@Table(name = "SK30V_PR", schema="prcek")
@IdClass(PrMbtPK.class)
public class PrMbt implements Serializable, IMBTRow {
	private static final long serialVersionUID = 1L;

	@Id
	private String pkz;

	@Id
	private String prnr;
	
	@Column
	private String famkz;

	@Column
	private String eindat;

	@Column
	private String einschl;

	@Column
	private String entdat;

	@Column
	private String entschl;

	@Column
	private String status;

	@Column(name = "UPD_DATUM")
	private String updDatum;


	public PrMbt() {
	}

	public String getPkz() {
		return pkz;
	}

	public String getFamkz() {
		return famkz;
	}
	
	public String getPrnr() {
		return prnr;
	}

	public String getEindat() {
		return eindat;
	}

	public String getEinschl() {
		return einschl;
	}

	public String getEntdat() {
		return entdat;
	}

	public String getEntschl() {
		return entschl;
	}

	public String getStatus() {
		return status;
	}

	public String getUpdDatum() {
		return updDatum;
	}
	


}