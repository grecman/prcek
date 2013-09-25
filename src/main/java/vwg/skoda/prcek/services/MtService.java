package vwg.skoda.prcek.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.prcek.entities.Mt;

@Service
@Transactional
public class MtService {
	
	static Logger log = Logger.getLogger(Mt.class);

	@PersistenceContext(name = "MtService")
	private EntityManager entityManager;
	
 	public void addMt(Mt newMt) {
		log.debug("###\t\t addMt("+newMt+")");
		entityManager.persist(newMt);		
	}

	public Mt getMtOne(long id) {
		log.debug("###\t\t getOneBrand("+id+")");
		return entityManager.find(Mt.class, id);
	}
	
	public Mt getMtOne(long idUser, String mt) {
		log.debug("###\t\t getMtOne("+mt+");");
		return entityManager.createQuery("SELECT u FROM Mt u WHERE u.sk30tUser.id=:idUser and u.mt=:mt", Mt.class).setParameter("idUser", idUser).setParameter("mt", mt).getSingleResult();
	}
	
	public List<Mt> getMt() {
		log.debug("###\t\t getMt();");
		return entityManager.createQuery("SELECT u FROM Mt u ORDER BY u.mt", Mt.class).getResultList();
	}

	public List<Mt> getMt(long idUser) {
		log.debug("###\t\t getMt("+idUser+");");
		return entityManager.createQuery("SELECT u FROM Mt u WHERE u.sk30tUser.id=:idUser ORDER BY u.mt", Mt.class).setParameter("idUser", idUser).getResultList();
	}
	
	public Boolean existMt(String netusername, String mt) {
		log.debug("###\t\t existMt(" + netusername +", "+mt +")");
		try {
			String neco = null;
			neco =  entityManager.createQuery("SELECT m.mt FROM Mt m WHERE m.sk30tUser.netusername=:netusername and m.mt=:mt ",	String.class).setParameter("netusername", netusername).setParameter("mt", mt).getSingleResult();
			log.debug("\t\t   ... MT nalezena ("+neco+")");
			return true;
		} catch (Exception e) {
			log.debug("\t\t   ... MT neexistuje! "+ e);
			return false;
		}
	}


	
}
