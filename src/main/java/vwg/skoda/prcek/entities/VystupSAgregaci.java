package vwg.skoda.prcek.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="SK30V_VYSTUP_S_AGREGACI", schema="prcek")
public class VystupSAgregaci implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;
	
	@Column(name="ERR_MBT")
	private String errMbt;

	@Column(name="ID_OFFLINE_JOB")
	private long idOfflineJob;

	private String mt;

	private String nazev;

	private double poradi;

	private String poznamka;

	private String pr;

	private long suma;
	
	@Column(name="POCET_ZAKAZEK")
	private long pocetZakazek;

	public VystupSAgregaci() {
	}

	public long getId() {
		return id;
	}

	public String getErrMbt() {
		return errMbt;
	}

	public long getIdOfflineJob() {
		return idOfflineJob;
	}

	public String getMt() {
		return mt;
	}

	public String getNazev() {
		return nazev;
	}

	public double getPoradi() {
		return poradi;
	}

	public String getPoznamka() {
		return poznamka;
	}

	public String getPr() {
		return pr;
	}

	public long getSuma() {
		return suma;
	}

	public long getPocetZakazek() {
		return pocetZakazek;
	}
	


}