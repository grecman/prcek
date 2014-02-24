package vwg.skoda.prcek.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import vwg.skoda.prcek.entities.VystupSAgregaci;

@Service
public class VystupSAgregaciService {
	static Logger log = Logger.getLogger(VystupSAgregaci.class);

	@PersistenceContext(name = "vystupSAgregaciService")
	private EntityManager entityManager;
	
	public List<VystupSAgregaci> getVystupSAgregaci (List<Long> seznamAgregaci){
		log.trace("###\t\t getVystupSAgregaci("+seznamAgregaci+")");
		return entityManager.createQuery("SELECT u FROM VystupSAgregaci u WHERE u.idOfflineJob IN (:agr) ORDER BY u.pr, u.poradi ", VystupSAgregaci.class).setParameter("agr", seznamAgregaci).getResultList();

	}
	
	
}
