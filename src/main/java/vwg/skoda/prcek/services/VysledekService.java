package vwg.skoda.prcek.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.prcek.entities.OfflineJob;
import vwg.skoda.prcek.entities.Sada;
import vwg.skoda.prcek.entities.Vysledek;
import vwg.skoda.prcek.entities.VystupSAgregaci;
import vwg.skoda.prcek.objects.VysledekExpSAgregaci;

@Service
public class VysledekService {

	static Logger log = Logger.getLogger(Vysledek.class);

	@PersistenceContext(name = "VysledekService")
	private EntityManager entityManager;

	@Transactional
	public void addVysledek(Vysledek newVysledek) {
		log.trace("###\t\t addVysledek(" + newVysledek + ")");
		entityManager.merge(newVysledek);
	}

	// BACHA NEFUNGUJE !!! ... reseni nize
	@Transactional
	public void removeAllVysledek(Sada sada) {
		log.trace("###\t\t removeAllVysledek(" + sada.getSk30tMt().getMt() + " - " + sada.getNazev() + ")");
		entityManager.createQuery("DELETE Vysledek WHERE sk30tOfflineJob.sk30tSada.id=:sd ").setParameter("sd", sada.getId()).executeUpdate();
	}

	@Transactional
	public void removeAllVysledek(OfflineJob off) {
		log.trace("###\t\t removeAllVysledek(" + off.getId() + ")");
		entityManager.createQuery("DELETE Vysledek WHERE sk30tOfflineJob.id=:sd ").setParameter("sd", off.getId()).executeUpdate();
	}

	public Vysledek getVysledekOne(long id) {
		log.trace("###\t\t getVysledekOne(" + id + ")");
		return entityManager.find(Vysledek.class, id);
	}

	public List<Vysledek> getVysledek(OfflineJob off) {
		log.trace("###\t\t getVysledek(" + off.getId() + ")\t radit dle: " + off.getVystupRazeni());
		if ("poradi".startsWith(off.getVystupRazeni())) {
			return entityManager.createQuery("SELECT u FROM Vysledek u WHERE u.sk30tOfflineJob.id=:oj ORDER BY u.sk30tPrPodminka.poradi ", Vysledek.class).setParameter("oj", off.getId())
					.getResultList();
		} else {
			return entityManager.createQuery("SELECT u FROM Vysledek u WHERE u.sk30tOfflineJob.id=:oj ORDER BY u.sk30tPrPodminka.pr ", Vysledek.class).setParameter("oj", off.getId()).getResultList();
		}
	}

	public List<Vysledek> getVysledek(List<Long> seznamAgregaci) {
		log.trace("###\t\t getVysledek(" + seznamAgregaci + ")");
		return entityManager.createQuery("SELECT u FROM Vysledek u WHERE u.sk30tOfflineJob.id IN (:agr) ORDER BY u.sk30tPrPodminka.pr, u.sk30tPrPodminka.sk30tSada.sk30tMt.mt ", Vysledek.class)
				.setParameter("agr", seznamAgregaci).getResultList();
	}

	public List<VysledekExpSAgregaci> getVysledekSumy(List<Long> seznamAgregaci) {
		log.trace("###\t\t getVysledekSumy(" + seznamAgregaci + ")");
		
		List<VysledekExpSAgregaci> gre = new ArrayList<VysledekExpSAgregaci>();
		
		for (Object o: (entityManager
				.createQuery(
						"SELECT u.sk30tPrPodminka.pr,SUM(u.soucet),SUM(u.sk30tOfflineJob.pocetZakazek) FROM Vysledek u WHERE u.sk30tOfflineJob.id IN (:agr) GROUP BY u.sk30tPrPodminka.pr ").setParameter("agr", seznamAgregaci).getResultList()))  {
			String pr = (String) ((Object[])o)[0];
			Long cetnost = (Long) ((Object[])o)[1];
			Long pocZak = (Long) ((Object[])o)[2];
			
			VysledekExpSAgregaci struk = new VysledekExpSAgregaci(pr, cetnost, pocZak);
			
			struk.setPr(pr);
			struk.setCetnost(cetnost);
			struk.setPocZak(pocZak);
			gre.add(struk);
		}
		return gre;
	}

}
