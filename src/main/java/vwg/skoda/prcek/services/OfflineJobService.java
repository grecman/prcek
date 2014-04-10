package vwg.skoda.prcek.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.prcek.entities.OfflineJob;
import vwg.skoda.prcek.entities.Sada;
import vwg.skoda.prcek.entities.User;

@Service
public class OfflineJobService {
	
	static Logger log = Logger.getLogger(OfflineJob.class);

	@PersistenceContext(name = "OfflineJobService")
	private EntityManager entityManager;
	
	public OfflineJob getOfflineJobOne(long id) {
		log.trace("###\t\t getOfflineJobOne("+id+")");
		return entityManager.find(OfflineJob.class, id);
	}

	@Transactional
 	public void addOfflineJob(OfflineJob newOfflineJob) {
		log.trace("###\t\t addOfflineJob("+newOfflineJob+")");
		entityManager.merge(newOfflineJob);		
	}
	
	@Transactional
	public void setOfflineJob(OfflineJob offlineJob) {
		log.trace("###\t\t setOfflineJob(" + offlineJob.getId() + ")");
		offlineJob = entityManager.merge(offlineJob);
	}
	
	@Transactional
	public void removeOfflineJob(long id) {
		log.trace("###\t\t removeOfflineJob(" + id + ")");
		OfflineJob offlineJob = getOfflineJobOne(id);
		entityManager.remove(entityManager.merge(offlineJob));
	}
	
	@Transactional
	public void removeOldOfflineJob(User user) {
		log.trace("###\t\t removeOldOfflineJob("+user.getNetusername()+");\t ... mazu joby starsi jak 92 dnu");
		entityManager.createQuery("DELETE OfflineJob WHERE id IN (SELECT a.id FROM OfflineJob a WHERE a.sk30tSada.sk30tMt.sk30tUser.id=:idUser AND sysdate-92>a.casSpusteni)").setParameter("idUser", user.getId()).executeUpdate();
	}
 	
	public List<OfflineJob> getOfflineJob(User user) {
		log.trace("###\t\t getOfflineJob("+user.getNetusername()+");");
		return entityManager.createQuery("SELECT u FROM OfflineJob u WHERE u.sk30tSada.sk30tMt.sk30tUser.id=:idUser ORDER BY u.casSpusteni DESC, u.agregace DESC, u.sk30tSada.sk30tMt.mt", OfflineJob.class).setParameter("idUser", user.getId()).getResultList();
	}
	
	public List<OfflineJob> getOfflineJobKeZpracovani(User user) {
		log.trace("###\t\t getOfflineJobKeZpracovani("+user.getNetusername()+");");
		return entityManager.createQuery("SELECT u FROM OfflineJob u WHERE u.pocetZakazek>0 AND u.casUkonceni IS NULL AND u.proces ='ve frontě' AND u.sk30tSada.sk30tMt.sk30tUser.id=:idUser ORDER BY u.casSpusteni DESC, u.agregace DESC, u.sk30tSada.sk30tMt.mt", OfflineJob.class).setParameter("idUser", user.getId()).getResultList();
	}
	
	public List<OfflineJob> getOfflineJob(User user, Long agregace) {
		log.trace("###\t\t getOfflineJob("+user.getNetusername()+", "+agregace+");");
		return entityManager.createQuery("SELECT u FROM OfflineJob u WHERE u.sk30tSada.sk30tMt.sk30tUser.id=:idUser AND u.agregace=:agr", OfflineJob.class).setParameter("idUser", user.getId()).setParameter("agr", agregace).getResultList();
	}	
	
	public Long getLastAgregace (User user){
		log.trace("###\t\t getLastAgregace("+user.getNetusername()+")");
		return entityManager.createQuery("SELECT NVL(MAX(u.agregace),0) FROM OfflineJob u WHERE u.sk30tSada.sk30tMt.sk30tUser.id=:idUser", Long.class).setParameter("idUser", user.getId()).getSingleResult();
	}
	
	public Boolean existOfflineJob(Sada s) {
		log.trace("###\t\t existOfflineJob( " + s.getNazev()+ ")");
		try {
			String neco = null;
			neco = entityManager.createQuery("SELECT m.id FROM OfflineJob m WHERE m.sk30tSada.id=:idSady ", String.class).setParameter("idSady", s.getId()).getSingleResult();
			log.trace("\t\t  ... nejaky OfflineJob pro sadu nalezen (" + neco + ")");
			return true;
		} catch (Exception e) {
			log.trace("\t\t  ... zadny OfflineJob pro sadu neexistuje! " + e);
			return false;
		}
	}

}
