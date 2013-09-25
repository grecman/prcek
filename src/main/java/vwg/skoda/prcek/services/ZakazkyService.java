package vwg.skoda.prcek.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.prcek.entities.Zakazky;

@Service
@Transactional
public class ZakazkyService {

	static Logger log = Logger.getLogger(Zakazky.class);

	@PersistenceContext(name = "ZakazkyService")
	private EntityManager entityManager;

	public Zakazky getZakazkyOne(long id) {
		log.debug("###\t\t getZakazkyOne(" + id + ")");
		return entityManager.find(Zakazky.class, id);
	}

	public Long getZakazkyCount(String mt, String kod, String wk, String evid, String platOd, String platDo, Boolean stornoVety) {
		String sv = ((!stornoVety) ? "N" : "%"); 
		log.debug("###\t\t getZakazkyCount(" + mt + ", " + kod + ", " + wk + ", " + evid + ", " + platOd + "-" + platDo + ", " + sv + ")");
		return entityManager
				.createQuery(
						"SELECT count(s) FROM Zakazky s WHERE s.modelTr=:modt AND s.kbodKod=:kkod AND s.kbodWk=:kwk AND s.kbodEvid=:kevid AND s.datSkut>=:plOd AND s.datSkut<:plDo AND s.kbodOpak LIKE :stv ",
						Long.class).setParameter("modt", mt).setParameter("kkod", kod).setParameter("kwk", wk).setParameter("kevid", evid)
				.setParameter("plOd", platOd).setParameter("plDo", platDo).setParameter("stv", sv).getSingleResult();
	}

	public List<Zakazky> getZakazky(String mt, String kod, String wk, String evid, String platOd, String platDo, Boolean stornoVety) {
		String sv = ((!stornoVety) ? "N" : "%"); 
		log.debug("###\t\t getZakazky(" + mt + ", " + kod + ", " + wk + ", " + evid + ", " + platOd + "-" + platDo + ", " + sv + ")");
		return entityManager
				.createQuery(
						"SELECT s FROM Zakazky s WHERE s.modelTr=:modt AND s.kbodKod=:kkod AND s.kbodWk=:kwk AND s.kbodEvid=:kevid AND s.datSkut>=:plOd AND s.datSkut<:plDo AND s.kbodOpak LIKE :stv ",
						Zakazky.class).setParameter("modt", mt).setParameter("kkod", kod).setParameter("kwk", wk).setParameter("kevid", evid)
				.setParameter("plOd", platOd).setParameter("plDo", platDo).setParameter("stv", sv).getResultList();
	}

}
