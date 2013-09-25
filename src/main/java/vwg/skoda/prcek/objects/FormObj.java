package vwg.skoda.prcek.objects;

import java.math.BigDecimal;

// Universalni GRE objekt, ktery je pouzit treba u vsech formularu nebo jako navratovy typ servisni metody. 
public class FormObj {

	long id;

	long[] idcka;

	String mt;
	String sada;
	BigDecimal poradi;
	String prPodminka;
	String poznamka;
	int pocet;
	String nazev;
	String platnostOd;
	String platnostDo;
	String zavod;
	String agregaceVystup = "bez";
	String triditDleVystup = "poradi";
	Boolean zakazkyVystup = false;
	Boolean stornoVetyVystup = false;

	// public FormObj() {
	// super();
	// }
	//
	// public FormObj(String nazev, String mt) {
	// super();
	// this.nazev = nazev;
	// this.mt = mt;
	// }
	//
	// public FormObj(String mt, int pocet, String nazev) {
	// super();
	// this.mt = mt;
	// this.pocet = pocet;
	// this.nazev = nazev;
	// }

	public long[] getIdcka() {
		return idcka;
	}

	public void setIdcka(long[] idcka) {
		this.idcka = idcka;
	}

	public String getMt() {
		return mt;
	}

	public void setMt(String mt) {
		this.mt = mt;
	}

	public String getSada() {
		return sada;
	}

	public void setSada(String sada) {
		this.sada = sada;
	}

	public BigDecimal getPoradi() {
		return poradi;
	}

	public void setPoradi(BigDecimal poradi) {
		this.poradi = poradi;
	}

	public String getPrPodminka() {
		return prPodminka;
	}

	public void setPrPodminka(String prPodminka) {
		this.prPodminka = prPodminka;
	}

	public String getPoznamka() {
		return poznamka;
	}

	public void setPoznamka(String poznamka) {
		this.poznamka = poznamka;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getPocet() {
		return pocet;
	}

	public void setPocet(int pocet) {
		this.pocet = pocet;
	}

	public String getNazev() {
		return nazev;
	}

	public void setNazev(String nazev) {
		this.nazev = nazev;
	}

	public String getPlatnostOd() {
		return platnostOd;
	}

	public void setPlatnostOd(String platnostOd) {
		this.platnostOd = platnostOd;
	}

	public String getPlatnostDo() {
		return platnostDo;
	}

	public void setPlatnostDo(String platnostDo) {
		this.platnostDo = platnostDo;
	}

	public String getZavod() {
		return zavod;
	}

	public void setZavod(String zavod) {
		this.zavod = zavod;
	}

	public String getAgregaceVystup() {
		return agregaceVystup;
	}

	public void setAgregaceVystup(String agregaceVystup) {
		this.agregaceVystup = agregaceVystup;
	}

	public String getTriditDleVystup() {
		return triditDleVystup;
	}

	public void setTriditDleVystup(String triditDleVystup) {
		this.triditDleVystup = triditDleVystup;
	}

	public Boolean getZakazkyVystup() {
		return zakazkyVystup;
	}

	public void setZakazkyVystup(Boolean zakazkyVystup) {
		this.zakazkyVystup = zakazkyVystup;
	}

	public Boolean getStornoVetyVystup() {
		return stornoVetyVystup;
	}

	public void setStornoVetyVystup(Boolean stornoVetyVystup) {
		this.stornoVetyVystup = stornoVetyVystup;
	}
	
}
