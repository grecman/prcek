package vwg.skoda.prcek.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.prcek.entities.Protokol;

@Service
@Transactional
public class ProtokolService {
	
	static Logger log = Logger.getLogger(Protokol.class);

	@PersistenceContext(name = "ProtokolService")
	private EntityManager entityManager;
	
 	public void addProtokol(Protokol newProtokol) {
		log.debug("###\t\t addProtokol("+newProtokol+")");
		entityManager.merge(newProtokol);		
	}

}
