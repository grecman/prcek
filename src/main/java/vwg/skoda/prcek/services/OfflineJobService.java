package vwg.skoda.prcek.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.prcek.entities.OfflineJob;
import vwg.skoda.prcek.entities.User;


@Service
@Transactional
public class OfflineJobService {
	
	static Logger log = Logger.getLogger(OfflineJob.class);

	@PersistenceContext(name = "OfflineJobService")
	private EntityManager entityManager;
	
 	public void addOfflineJob(OfflineJob newOfflineJob) {
		log.debug("###\t\t addOfflineJob("+newOfflineJob+")");
		entityManager.merge(newOfflineJob);		
	}
 	
	public List<OfflineJob> getOfflineJob(User user) {
		log.debug("###\t\t getOfflineJob("+user.getNetusername()+");");
		return entityManager.createQuery("SELECT u FROM OfflineJob u WHERE u.sk30tSada.sk30tMt.sk30tUser.id=:idUser ORDER BY u.casSpusteni DESC, u.agregace DESC, u.sk30tSada.sk30tMt.mt", OfflineJob.class).setParameter("idUser", user.getId()).getResultList();
	}
	
	public Long getLastAgregace (User user){
		log.debug("###\t\t getLastAgregace("+user.getNetusername()+")");
		return entityManager.createQuery("SELECT NVL(MAX(u.agregace),0) FROM OfflineJob u WHERE u.sk30tSada.sk30tMt.sk30tUser.id=:idUser", Long.class).setParameter("idUser", user.getId()).getSingleResult();
	}

}
