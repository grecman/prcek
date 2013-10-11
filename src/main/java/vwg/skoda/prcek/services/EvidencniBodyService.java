package vwg.skoda.prcek.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import vwg.skoda.prcek.entities.EvidencniBody;

@Service
public class EvidencniBodyService {
	
	static Logger log = Logger.getLogger(EvidencniBody.class);

	@PersistenceContext(name = "EvidencniBodyService")
	private EntityManager entityManager;
	
	
	public EvidencniBody getEvidencniBodyOne(long id) {
		log.trace("###\t\t getEvidencniBodyOne(" + id + ")");
		return entityManager.find(EvidencniBody.class, id);
	}

	public List<EvidencniBody> getEvidencniBody() {
		log.trace("###\t\t getEvidencniBody()");
		return entityManager.createQuery("SELECT s FROM EvidencniBody s ORDER BY s.kbodWk, s.kbodKod, s.kbodEvid ", EvidencniBody.class).getResultList();
	}
	
	public List<EvidencniBody> getEvidencniBody(String zavod) {
		log.trace("###\t\t getEvidencniBody("+zavod+")");
		return entityManager.createQuery("SELECT s FROM EvidencniBody s WHERE s.kbodWk=:zav ORDER BY s.kbodWk, s.kbodKod, s.kbodEvid ", EvidencniBody.class).setParameter("zav", zavod).getResultList();
	}
	
	public List<String> getEvidencniBodySeznamWk() {
		log.trace("###\t\t getEvidencniBodySeznamWk()");
		return entityManager.createQuery("SELECT DISTINCT s.kbodWk FROM EvidencniBody s ORDER BY s.kbodWk ", String.class).getResultList();
	}


}
