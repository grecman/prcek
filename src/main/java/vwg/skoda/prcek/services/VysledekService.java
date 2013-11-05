package vwg.skoda.prcek.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.prcek.entities.OfflineJob;
import vwg.skoda.prcek.entities.Sada;
import vwg.skoda.prcek.entities.Vysledek;

@Service
public class VysledekService {
	
	static Logger log = Logger.getLogger(Vysledek.class);

	@PersistenceContext(name = "VysledekService")
	private EntityManager entityManager;

	@Transactional
 	public void addVysledek(Vysledek newVysledek) {
		log.trace("###\t\t addVysledek("+newVysledek+")");
		entityManager.merge(newVysledek);		
	}
	
	// BACHA NEFUNGUJE !!! ... reseni nize
	@Transactional
	public void removeAllVysledek (Sada sada){
		log.trace("###\t\t removeAllVysledek("+sada.getSk30tMt().getMt()+" - "+sada.getNazev()+")");
		entityManager.createQuery("DELETE Vysledek WHERE sk30tOfflineJob.sk30tSada.id=:sd ").setParameter("sd", sada.getId()).executeUpdate();
	}
	
	@Transactional
	public void removeAllVysledek (OfflineJob off){
		log.trace("###\t\t removeAllVysledek("+off.getId()+")");
		entityManager.createQuery("DELETE Vysledek WHERE sk30tOfflineJob.id=:sd ").setParameter("sd", off.getId()).executeUpdate();
	}
	
	public Vysledek getVysledekOne(long id) {
		log.trace("###\t\t getVysledekOne(" + id + ")");
		return entityManager.find(Vysledek.class, id);
	}
	
	public List<Vysledek> getVysledek (OfflineJob off){
		log.trace("###\t\t getVysledek("+off.getId()+")\t radit dle: "+off.getVystupRazeni());
		if ("poradi".startsWith(off.getVystupRazeni())){
			return entityManager.createQuery("SELECT u FROM Vysledek u WHERE u.sk30tOfflineJob.id=:oj ORDER BY u.sk30tPrPodminka.poradi ", Vysledek.class).setParameter("oj", off.getId()).getResultList();	
		} else {
			return entityManager.createQuery("SELECT u FROM Vysledek u WHERE u.sk30tOfflineJob.id=:oj ORDER BY u.sk30tPrPodminka.pr ", Vysledek.class).setParameter("oj", off.getId()).getResultList();
		}
	}
	
	public List<Vysledek> getVysledek (List<Long> seznamAgregaci){
		log.trace("###\t\t getVysledek("+seznamAgregaci+")");
		return entityManager.createQuery("SELECT u FROM Vysledek u WHERE u.sk30tOfflineJob.id IN (:agr) ORDER BY u.sk30tPrPodminka.pr, u.sk30tPrPodminka.sk30tSada.sk30tMt.mt ", Vysledek.class).setParameter("agr", seznamAgregaci).getResultList();

	}

}
