package vwg.skoda.prcek.services;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import vwg.skoda.prcek.entities.PrMbt;
import vwg.skoda.prcek.entities.PrMbtPK;

@Service
public class PrMbtService {
	
	static Logger log = Logger.getLogger(PrMbt.class);

	@PersistenceContext(name = "EvidencniBodyService")
	private EntityManager entityManager;
	
	// slozeny PK!
	public PrMbt getPr(String produkt, String pr) {
		log.trace("###\t\t getPr("+produkt+", "+pr+");");
		return entityManager.find(PrMbt.class, new PrMbtPK(produkt, pr) );
	}
	
/*	
	public PrMbt getPr(String produkt, String pr) {
		log.trace("###\t getPr("+produkt+" - "+pr+");");
		return entityManager.createQuery("SELECT u FROM PrMbt u WHERE u.produkt=:prod AND u.pr=:prnr", PrMbt.class).setParameter("prod", produkt).setParameter("prnr", pr).getSingleResult();
	}
*/	
	public List<PrMbt> getPr(String produkt) {
		log.trace("###\t\t getPr("+produkt+");");
		return entityManager.createQuery("SELECT u FROM PrMbt u WHERE u.pkz=:prod", PrMbt.class).setParameter("prod", produkt).getResultList();
	}

	public Date getDbTime() {
		log.debug("###\t\t getDbTime();");
		return entityManager.createQuery("SELECT current_timestamp() FROM PrMbt  WHERE rownum=1", Date.class).getSingleResult();
	}
}
