package vwg.skoda.prcek.services;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import vwg.skoda.prcek.entities.Zakazky;

@Service
public class ZakazkyService {

	static Logger log = Logger.getLogger(Zakazky.class);

	@PersistenceContext(name = "ZakazkyService")
	private EntityManager entityManager;

	public Zakazky getZakazkyOne(long id) {
		log.trace("###\t\t getZakazkyOne(" + id + ")");
		return entityManager.find(Zakazky.class, id);
	}

	public Long getZakazkyCount(String mt, String kod, String wk, String evid, String platOd, String platDo, Boolean stornoVety) {
		String sv = ((!stornoVety) ? "N" : "%"); 
		log.trace("###\t\t getZakazkyCount(" + mt + ", " + kod + ", " + wk + ", " + evid + ", " + platOd + "-" + platDo + ", " + sv + ")");
		return entityManager
				.createQuery(
						"SELECT count(s) FROM Zakazky s WHERE s.modelTr=:modt AND s.kbodKod=:kkod AND s.kbodWk=:kwk AND s.kbodEvid=:kevid AND s.datSkut>=:plOd AND s.datSkut<=:plDo AND s.kbodOpak LIKE :stv ",
						Long.class).setParameter("modt", mt).setParameter("kkod", kod).setParameter("kwk", wk).setParameter("kevid", evid)
				.setParameter("plOd", platOd).setParameter("plDo", platDo).setParameter("stv", sv).getSingleResult();
	}
	
	
	public List<Zakazky> getZakazky(String mt, String kbodkod, String wk, String evid, String platOd, String platDo, Boolean stornoVety) {
		String sv = ((!stornoVety) ? "N" : "%"); 
		log.trace("###\t\t getZakazky(" + mt + ", " + kbodkod + ", " + wk + ", " + evid + ", " + platOd + "-" + platDo + ", " + sv + ")");
		return entityManager
				.createQuery(
						"SELECT s FROM Zakazky s WHERE s.modelTr=:modt AND s.kbodKod=:kkod AND s.kbodWk=:kwk AND s.kbodEvid=:kevid AND s.datSkut>=:plOd AND s.datSkut<=:plDo AND s.kbodOpak LIKE :stv ",
						Zakazky.class).setParameter("modt", mt).setParameter("kkod", kbodkod).setParameter("kwk", wk).setParameter("kevid", evid)
				.setParameter("plOd", platOd).setParameter("plDo", platDo).setParameter("stv", sv).getResultList();
	}
	
	
	int maxPocZakazek = 25000;
	
	public List<Zakazky> getZakazkyMaxPoc(String mt, String kbodkod, String wk, String evid, String platOd, String platDo, Boolean stornoVety, int firstPos) {
		String sv = ((!stornoVety) ? "N" : "%"); 
		log.trace("###\t\t getZakazky(" + mt + ", " + kbodkod + ", " + wk + ", " + evid + ", " + platOd + "-" + platDo + ", " + sv + "," + firstPos + ")");
		return entityManager
				.createQuery(
						"SELECT s FROM Zakazky s WHERE s.modelTr=:modt AND s.kbodKod=:kkod AND s.kbodWk=:kwk AND s.kbodEvid=:kevid AND s.datSkut>=:plOd AND s.datSkut<=:plDo AND s.kbodOpak LIKE :stv ",
						Zakazky.class).setParameter("modt", mt).setParameter("kkod", kbodkod).setParameter("kwk", wk).setParameter("kevid", evid)
				.setParameter("plOd", platOd).setParameter("plDo", platDo).setParameter("stv", sv).setFirstResult(firstPos).setMaxResults(maxPocZakazek).getResultList();
	}
	
	public Date getDbTime() {
		log.debug("###\t\t getDbTime();");
		return entityManager.createQuery("SELECT current_timestamp() FROM Zakazky WHERE rownum=1", Date.class).getSingleResult();
	}
	
}
