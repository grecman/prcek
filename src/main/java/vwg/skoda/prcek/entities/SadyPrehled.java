package vwg.skoda.prcek.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="SK30V_SADY_PREHLED", schema="prcek")
public class SadyPrehled implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@Column(name="ID_SADA")
	private long idSada;

	private String mt;

	private String nazev;

	private String netusername;

	@Column(name="POCET_VALIDNICH_PR")
	private BigDecimal pocetValidnichPr;
	
	@Column(name="POCET_NEVALIDNICH_PR")
	private BigDecimal pocetNevalidnichPr;	

	private String prijmeni;

	public SadyPrehled() {
	}

	public long getId() {
		return this.id;
	}

	public long getIdSada() {
		return this.idSada;
	}


	public String getMt() {
		return this.mt;
	}

	public String getNazev() {
		return this.nazev;
	}

	public String getNetusername() {
		return this.netusername;
	}

	public BigDecimal getPocetValidnichPr() {
		return this.pocetValidnichPr;
	}
	
	public BigDecimal getPocetNevalidnichPr() {
		return this.pocetNevalidnichPr;
	}

	public String getPrijmeni() {
		return this.prijmeni;
	}



}