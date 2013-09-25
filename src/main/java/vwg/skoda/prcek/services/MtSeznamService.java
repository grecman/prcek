package vwg.skoda.prcek.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.prcek.entities.MtSeznam;

@Service
@Transactional
public class MtSeznamService {
	
	static Logger log = Logger.getLogger(MtSeznam.class);
	
	@PersistenceContext(name = "MtSeznamService")
	private EntityManager entityManager;

	public MtSeznam getMtSeznamOne(String mt) {
		log.debug("###\t\t getMtOne("+mt+");");
		return entityManager.createQuery("SELECT u FROM MtSeznam u WHERE u.mt=:mt", MtSeznam.class).setParameter("mt", mt).getSingleResult();
	}
	
	public List<MtSeznam> getMt() {
		log.debug("###\t\t getMt();");
		return entityManager.createQuery("SELECT u FROM MtSeznam u ORDER BY u.mt", MtSeznam.class).getResultList();
	}

}
