package vwg.skoda.prcek.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.prcek.entities.PrPodminka;
import vwg.skoda.prcek.entities.Sada;


@Service
@Transactional
public class PrPodminkaService {
	
	static Logger log = Logger.getLogger(PrPodminka.class);

	@PersistenceContext(name = "PrPodminkaService")
	private EntityManager entityManager;
	
	public PrPodminka getPrPodminkaOne(long id) {
		log.debug("###\t\t getPrPodminkaOne("+id+")");
		return entityManager.find(PrPodminka.class, id);
	}
	
	public void removePrPodminka(long id) {
		log.debug("###\t\t removePrPodminka(" + id + ")");
		PrPodminka pr = getPrPodminkaOne(id);
		entityManager.remove(entityManager.merge(pr));
	}
	
	public void removeAllPrPodminka (Sada sada){
		log.debug("###\t\t removeAllPrPodminka("+sada.getSk30tMt().getMt()+" - "+sada.getNazev()+")");
		entityManager.createQuery("DELETE u PrPodminka u WHERE u.sk30tSada=:sd ", PrPodminka.class).setParameter("sd", sada).getResultList();
		//entityManager.createNativeQuery("DELETE u PrPodminka u WHERE u.sk30tSada=:sd ", PrPodminka.class).setParameter("sd", sada).getResultList();
	}
	
 	public void addPrPodminka(PrPodminka newPrPodminka) {
		log.debug("###\t\t addPrPodminka("+newPrPodminka+")");
		entityManager.persist(newPrPodminka);		
	}
 	
 	public void setPrPodminka(PrPodminka prPodminka) {
		log.debug("###\t\t setPrPodminka("+prPodminka.getPr()+")");
		prPodminka = entityManager.merge(prPodminka);		
	}
	
	public List<PrPodminka> getPrPodminka (Sada sada){
		log.debug("###\t\t getPrPodminka("+sada.getSk30tMt().getMt()+" - "+sada.getNazev()+")");
		return entityManager.createQuery("SELECT u FROM PrPodminka u WHERE u.sk30tSada=:sd ORDER BY u.poradi, u.pr", PrPodminka.class).setParameter("sd", sada).getResultList();
	}
	
	public List<PrPodminka> getPrPodminkaOrderByTest (Sada sada){
		log.debug("###\t\t getPrPodminkaOrderByTest("+sada.getSk30tMt().getMt()+" - "+sada.getNazev()+")");
		return entityManager.createQuery("SELECT u FROM PrPodminka u WHERE u.sk30tSada=:sd ORDER BY u.errMbt, u.pr", PrPodminka.class).setParameter("sd", sada).getResultList();
	}
	
	public Long getPrPodminkaCount (Sada sada){
		log.debug("###\t\t getPrPodminkaCount("+sada.getSk30tMt().getMt()+" - "+sada.getNazev()+")");
		return entityManager.createQuery("SELECT count(*) FROM PrPodminka u WHERE u.sk30tSada=:sd", Long.class).setParameter("sd", sada).getSingleResult();
	}
}
