package vwg.skoda.prcek.services;

import java.util.List;

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
		log.trace("###\t\t addProtokol("+newProtokol+")");
		entityManager.merge(newProtokol);		
	}
 	
	public List<Protokol> getUserLogin(String netusername) {
		log.debug("###\t\t getUserProtokol("+netusername+");");
		return entityManager.createQuery("SELECT a FROM Protokol a WHERE a.action='Login' AND a.netusername=:netusername ORDER BY a.time desc", Protokol.class).setParameter("netusername", netusername).getResultList();
	}
	
	public List<Protokol> getAllLogin() {
		log.debug("###\t\t getAllUserProtokol();");
		return entityManager.createQuery("SELECT a FROM Protokol a WHERE a.action='Login'", Protokol.class).getResultList();
	}

 	

}
