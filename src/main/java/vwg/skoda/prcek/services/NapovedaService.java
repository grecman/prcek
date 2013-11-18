package vwg.skoda.prcek.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.prcek.entities.Napoveda;

@Service
public class NapovedaService {
	
	static Logger log = Logger.getLogger(Napoveda.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Napoveda getNapoveda(long id) {
		log.trace("###\t\t getNapoveda("+id+");");
		return entityManager.find(Napoveda.class, id);
	}	
	

	public List<Napoveda> getNapoveda() {
		log.trace("###\t\t getNapoveda();");
		return entityManager.createQuery("SELECT u FROM Napoveda u ORDER BY u.tema", Napoveda.class).getResultList();
	}

	@Transactional
 	public void addNapoveda(Napoveda napoveda) {
		log.trace("###\t\t addNapoveda("+napoveda+")");
		entityManager.persist(napoveda);		
	}	
	
	@Transactional
	public void setNapoveda(Napoveda napoveda) {
		log.trace("###\t\t setNapoveda(" + napoveda.getTema() + ")");
		entityManager.merge(napoveda);
		//napoveda = entityManager.merge(napoveda);
	}
	
	@Transactional
	public void removeNapoveda(long id) {
		log.trace("###\t\t removeNapoveda(" + id + ")");
		Napoveda n = getNapoveda(id);
		entityManager.remove(entityManager.merge(n));
	}


}
