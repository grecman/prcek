package vwg.skoda.prcek.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.prcek.entities.SadyPrehled;
import vwg.skoda.prcek.entities.User;

@Service
@Transactional
public class SadyPrehledService {
	

	static Logger log = Logger.getLogger(SadyPrehled.class);

	@PersistenceContext(name = "SadyService")
	private EntityManager entityManager;
	
	public List<SadyPrehled> getSady(User u) {
		log.debug("###\t\t getSady(" + u.getNetusername() + "));");
		return entityManager
				.createQuery("SELECT s FROM SadyPrehled s WHERE s.netusername=:user ORDER BY s.mt, s.nazev",
						SadyPrehled.class).setParameter("user", u.getNetusername()).getResultList();
	}


}
