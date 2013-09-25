package vwg.skoda.prcek.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.prcek.entities.Mt;
import vwg.skoda.prcek.entities.Sada;

@Service
@Transactional
public class SadaService {

	static Logger log = Logger.getLogger(Sada.class);

	@PersistenceContext(name = "SadaService")
	private EntityManager entityManager;

	public void addSada(Sada newSada) {
		log.debug("###\t\t addSada(" + newSada.getNazev() + ")");
		entityManager.persist(newSada);
	}

	public void setSada(Sada sada) {
		log.debug("###\t\t setSada(" + sada.getNazev() + ")");
		sada = entityManager.merge(sada);
	}

	public void removeSada(long id) {
		log.debug("###\t\t removeSada(" + id + ")");
		Sada sada = getSadaOne(id);
		entityManager.remove(entityManager.merge(sada));
	}

	public Sada getSadaOne(long id) {
		log.debug("###\t\t getSadaOne(" + id + ")");
		return entityManager.find(Sada.class, id);
	}

	public Sada getSadaOne(Mt mt, String nazev) {
		log.debug("###\t\t getSadaOne(" + mt.getMt() + ", '" + nazev + "');");
		return entityManager.createQuery("SELECT s FROM Sada s WHERE s.sk30tMt.id=:mtId AND s.nazev=:naz", Sada.class)
				.setParameter("mtId", mt.getId()).setParameter("naz", nazev).getSingleResult();
	}

	public List<Sada> getSada(Mt mt) {
		log.debug("###\t\t getSada(" + mt.getId() + "-" + mt.getMt() + " (pro " + mt.getSk30tUser().getNetusername() + "));");
		return entityManager.createQuery("SELECT s FROM Sada s WHERE s.sk30tMt.id=:mtId ORDER BY s.nazev", Sada.class)
				.setParameter("mtId", mt.getId()).getResultList();
	}

	public Boolean existSada(String nazevSady, String mt) {
		log.debug("###\t\t existSada( '" + nazevSady + "', " + mt + ")");
		try {
			String neco = null;
			neco = entityManager.createQuery("SELECT m.nazev FROM Sada m WHERE m.nazev=:nazevSady and m.sk30tMt.mt=:mt", String.class)
					.setParameter("nazevSady", nazevSady.trim()).setParameter("mt", mt).getSingleResult();
			log.debug("\t\t  ... Sada nalezena (" + neco + ")");
			return true;
		} catch (Exception e) {
			log.debug("\t\t  ... Sada neexistuje! " + e);
			return false;
		}
	}

}
