package vwg.skoda.prcek.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SK30V_ZAKAZKY database table.
 * 
 */
@Entity
@Table(name="SK30V_ZAKAZKY", schema="prcek")
public class Zakazky implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;
	
	private String barvawl;

	private String barvaws;

	private String barvawv;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DAT_NAHRANI")
	private Date datNahrani;

	@Column(name="DAT_SKUT")
	private String datSkut;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="KBOD_DAT")
	private Date kbodDat;

	@Column(name="KBOD_EVID")
	private String kbodEvid;

	@Column(name="KBOD_KOD")
	private String kbodKod;

	@Column(name="KBOD_OPAK")
	private String kbodOpak;

	@Column(name="KBOD_WK")
	private String kbodWk;

	private String kifa;

	@Column(name="KIFA_VER1")
	private String kifaVer1;

	@Column(name="KIFA_VER2")
	private String kifaVer2;

	@Column(name="KNR_NR")
	private String knrNr;

	@Column(name="KNR_ROK")
	private String knrRok;

	@Column(name="KNR_TTD")
	private String knrTtd;

	@Column(name="KNR_WK")
	private String knrWk;

	private BigDecimal mnzak;

	@Column(name="MODEL_KA")
	private String modelKa;

	@Column(name="MODEL_MO")
	private String modelMo;

	@Column(name="MODEL_PV")
	private String modelPv;

	@Column(name="MODEL_TR")
	private String modelTr;

	@Column(name="MODEL_VER")
	private String modelVer;

	@Column(name="MODEL_VY")
	private String modelVy;

	private String mrok;

	private String prpoz;

	@Column(name="ROZPAD_BOD")
	private String rozpadBod;

	@Column(name="SKD_PR")
	private String skdPr;

	private String uvety1;

	private String uvety2;

	private String vin;

	@Column(name="WK_LAK")
	private String wkLak;

	@Column(name="WK_MONT")
	private String wkMont;

	@Column(name="WK_SVAR")
	private String wkSvar;

	private String xcisl;

	public Zakazky() {
	}
	
	

	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getBarvawl() {
		return this.barvawl;
	}

	public void setBarvawl(String barvawl) {
		this.barvawl = barvawl;
	}

	public String getBarvaws() {
		return this.barvaws;
	}

	public void setBarvaws(String barvaws) {
		this.barvaws = barvaws;
	}

	public String getBarvawv() {
		return this.barvawv;
	}

	public void setBarvawv(String barvawv) {
		this.barvawv = barvawv;
	}

	public Date getDatNahrani() {
		return this.datNahrani;
	}

	public void setDatNahrani(Date datNahrani) {
		this.datNahrani = datNahrani;
	}

	public String getDatSkut() {
		return this.datSkut;
	}

	public void setDatSkut(String datSkut) {
		this.datSkut = datSkut;
	}

	public Date getKbodDat() {
		return this.kbodDat;
	}

	public void setKbodDat(Date kbodDat) {
		this.kbodDat = kbodDat;
	}

	public String getKbodEvid() {
		return this.kbodEvid;
	}

	public void setKbodEvid(String kbodEvid) {
		this.kbodEvid = kbodEvid;
	}

	public String getKbodKod() {
		return this.kbodKod;
	}

	public void setKbodKod(String kbodKod) {
		this.kbodKod = kbodKod;
	}

	public String getKbodOpak() {
		return this.kbodOpak;
	}

	public void setKbodOpak(String kbodOpak) {
		this.kbodOpak = kbodOpak;
	}

	public String getKbodWk() {
		return this.kbodWk;
	}

	public void setKbodWk(String kbodWk) {
		this.kbodWk = kbodWk;
	}

	public String getKifa() {
		return this.kifa;
	}

	public void setKifa(String kifa) {
		this.kifa = kifa;
	}

	public String getKifaVer1() {
		return this.kifaVer1;
	}

	public void setKifaVer1(String kifaVer1) {
		this.kifaVer1 = kifaVer1;
	}

	public String getKifaVer2() {
		return this.kifaVer2;
	}

	public void setKifaVer2(String kifaVer2) {
		this.kifaVer2 = kifaVer2;
	}

	public String getKnrNr() {
		return this.knrNr;
	}

	public void setKnrNr(String knrNr) {
		this.knrNr = knrNr;
	}

	public String getKnrRok() {
		return this.knrRok;
	}

	public void setKnrRok(String knrRok) {
		this.knrRok = knrRok;
	}

	public String getKnrTtd() {
		return this.knrTtd;
	}

	public void setKnrTtd(String knrTtd) {
		this.knrTtd = knrTtd;
	}

	public String getKnrWk() {
		return this.knrWk;
	}

	public void setKnrWk(String knrWk) {
		this.knrWk = knrWk;
	}

	public BigDecimal getMnzak() {
		return this.mnzak;
	}

	public void setMnzak(BigDecimal mnzak) {
		this.mnzak = mnzak;
	}

	public String getModelKa() {
		return this.modelKa;
	}

	public void setModelKa(String modelKa) {
		this.modelKa = modelKa;
	}

	public String getModelMo() {
		return this.modelMo;
	}

	public void setModelMo(String modelMo) {
		this.modelMo = modelMo;
	}

	public String getModelPv() {
		return this.modelPv;
	}

	public void setModelPv(String modelPv) {
		this.modelPv = modelPv;
	}

	public String getModelTr() {
		return this.modelTr;
	}

	public void setModelTr(String modelTr) {
		this.modelTr = modelTr;
	}

	public String getModelVer() {
		return this.modelVer;
	}

	public void setModelVer(String modelVer) {
		this.modelVer = modelVer;
	}

	public String getModelVy() {
		return this.modelVy;
	}

	public void setModelVy(String modelVy) {
		this.modelVy = modelVy;
	}

	public String getMrok() {
		return this.mrok;
	}

	public void setMrok(String mrok) {
		this.mrok = mrok;
	}

	public String getPrpoz() {
		return this.prpoz;
	}

	public void setPrpoz(String prpoz) {
		this.prpoz = prpoz;
	}

	public String getRozpadBod() {
		return this.rozpadBod;
	}

	public void setRozpadBod(String rozpadBod) {
		this.rozpadBod = rozpadBod;
	}

	public String getSkdPr() {
		return this.skdPr;
	}

	public void setSkdPr(String skdPr) {
		this.skdPr = skdPr;
	}

	public String getUvety1() {
		return this.uvety1;
	}

	public void setUvety1(String uvety1) {
		this.uvety1 = uvety1;
	}

	public String getUvety2() {
		return this.uvety2;
	}

	public void setUvety2(String uvety2) {
		this.uvety2 = uvety2;
	}

	public String getVin() {
		return this.vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getWkLak() {
		return this.wkLak;
	}

	public void setWkLak(String wkLak) {
		this.wkLak = wkLak;
	}

	public String getWkMont() {
		return this.wkMont;
	}

	public void setWkMont(String wkMont) {
		this.wkMont = wkMont;
	}

	public String getWkSvar() {
		return this.wkSvar;
	}

	public void setWkSvar(String wkSvar) {
		this.wkSvar = wkSvar;
	}

	public String getXcisl() {
		return this.xcisl;
	}

	public void setXcisl(String xcisl) {
		this.xcisl = xcisl;
	}

}