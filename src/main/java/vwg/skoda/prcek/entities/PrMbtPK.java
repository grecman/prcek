package vwg.skoda.prcek.entities;

import java.io.Serializable;

import javax.persistence.Column;

public class PrMbtPK  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column
	private String pkz;

	@Column
	private String prnr;
	
	// GRE: nutno vytvorit (vygenerovat) tento konstruktor, abychom to v servisni vrstve meli jak volat 
	public PrMbtPK(String pkz, String prnr) {
		super();
		this.pkz = pkz;
		this.prnr = prnr;
	}
	
	// GRE: je mozne ze tento construktor bude vnitrne potrebovat hibernate pri komunikaci s DB, ale neni to jiste :) vse bude urcite ve specifikaci
	public PrMbtPK() {
	}
	
	// GRE: nutno vygenerovat hashCode a equals !!!
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pkz == null) ? 0 : pkz.hashCode());
		result = prime * result + ((prnr == null) ? 0 : prnr.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrMbtPK other = (PrMbtPK) obj;
		if (pkz == null) {
			if (other.pkz != null)
				return false;
		} else if (!pkz.equals(other.pkz))
			return false;
		if (prnr == null) {
			if (other.prnr != null)
				return false;
		} else if (!prnr.equals(other.prnr))
			return false;
		return true;
	}
	
	

}
