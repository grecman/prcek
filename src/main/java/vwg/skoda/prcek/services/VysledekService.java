package vwg.skoda.prcek.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.prcek.entities.Vysledek;

@Service
@Transactional
public class VysledekService {
	
	static Logger log = Logger.getLogger(Vysledek.class);

	@PersistenceContext(name = "VysledekService")
	private EntityManager entityManager;
	
 	public void addVysledek(Vysledek newVysledek) {
		log.debug("###\t\t addVysledek("+newVysledek+")");
		entityManager.merge(newVysledek);		
	}

}
