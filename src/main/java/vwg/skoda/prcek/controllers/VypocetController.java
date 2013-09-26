package vwg.skoda.prcek.controllers;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vwg.skoda.prcek.entities.EvidencniBody;
import vwg.skoda.prcek.entities.OfflineJob;
import vwg.skoda.prcek.entities.Sada;
import vwg.skoda.prcek.entities.SadyPrehled;
import vwg.skoda.prcek.entities.User;
import vwg.skoda.prcek.objects.FormObj;
import vwg.skoda.prcek.services.EvidencniBodyService;
import vwg.skoda.prcek.services.MtService;
import vwg.skoda.prcek.services.OfflineJobService;
import vwg.skoda.prcek.services.PrPodminkaService;
import vwg.skoda.prcek.services.ProtokolService;
import vwg.skoda.prcek.services.SadaService;
import vwg.skoda.prcek.services.SadyPrehledService;
import vwg.skoda.prcek.services.UserService;
import vwg.skoda.prcek.services.VysledekService;
import vwg.skoda.prcek.services.ZakazkyService;

@Controller
@RequestMapping
public class VypocetController {

	static Logger log = Logger.getLogger(VypocetController.class);

	@Autowired
	private UserService serviceUser;

	@Autowired
	private MtService serviceMt;

	@Autowired
	private SadaService serviceSada;

	@Autowired
	private SadyPrehledService serviceSady;

	@Autowired
	private PrPodminkaService servicePrPodminka;

	@Autowired
	private ProtokolService serviceProtokol;

	@Autowired
	private EvidencniBodyService serviceEvidencniBody;

	@Autowired
	private ZakazkyService serviceZakazky;

	@Autowired
	private VysledekService serviceVysledek;

	@Autowired
	private OfflineJobService serviceOfflineJob;

	@RequestMapping(value = "/vypocet", method = RequestMethod.GET)
	public String vypocet(FormObj f, EvidencniBody eb, Model model, HttpServletRequest req) {
		log.debug("###\t vypocet()");

		User u = serviceUser.getUser(req.getUserPrincipal().getName());
		List<SadyPrehled> s = serviceSady.getSady(u);

		// priprava obsahu combo boxu
		List<String> rokMesic = new ArrayList<String>();
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy.MM");
		for (int i = 0; i < 25; i++) {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, -i);
			String date = DATE_FORMAT.format(c.getTime());
			// System.out.println(date);
			rokMesic.add(date);
		}

		model.addAttribute("vybraneSady", s);
		model.addAttribute("rokMesicList", rokMesic);
		return "/vypocet";
	}

	@RequestMapping(value = "/vypocet/vybraneObdobiMesic")
	public String vybraneObdobiMesic(FormObj f, EvidencniBody eb, Model model, HttpServletRequest req) {
		log.debug("###\t vybraneObdobiMesic(" + f.getPlatnostOd() + ")");
		return "redirect:/srv/vypocet/" + f.getPlatnostOd() + "/";
	}

	@RequestMapping(value = "/vypocet/vybraneObdobiDenOdDo")
	public String obdobiDenOdDo(FormObj f, EvidencniBody eb, Model model, HttpServletRequest req) {
		log.debug("###\t obdobiDenOdDo(" + f.getPlatnostOd() + " - " + f.getPlatnostDo() + ")");
		return "redirect:/srv/vypocet/" + f.getPlatnostOd() + "-" + f.getPlatnostDo() + "/";
	}

	// Lomitko za URL je z bezpecnostnich duvodu, protoze kdyz v predavanych parametrech jsou tecky, tak to "parser" posere (urizne) !!!
	@RequestMapping(value = "/vypocet/{platnost}/")
	public String vypocet(@PathVariable String platnost, FormObj f, EvidencniBody eb, Model model, HttpServletRequest req) {
		log.debug("###\t vypocet(" + platnost + ")");

		User u = serviceUser.getUser(req.getUserPrincipal().getName());
		List<SadyPrehled> s = serviceSady.getSady(u);

		List<String> zavody = serviceEvidencniBody.getEvidencniBodySeznamWk();

		model.addAttribute("seznamZavodu", zavody);
		model.addAttribute("vybraneSady", s);
		model.addAttribute("platnostVyplnena", platnost);

		return "/vypocet";
	}

	@RequestMapping(value = "/vypocet/vybranyZavod/{platnost}/")
	public String vybranyZavod(@PathVariable String platnost, FormObj f, EvidencniBody eb, Model model, HttpServletRequest req) {
		log.debug("###\t vybranyZavod(" + f.getZavod() + ", " + platnost + ")");

		User u = serviceUser.getUser(req.getUserPrincipal().getName());
		List<SadyPrehled> s = serviceSady.getSady(u);

		List<EvidencniBody> e = serviceEvidencniBody.getEvidencniBody(f.getZavod());

		model.addAttribute("zavod", f.getZavod());
		model.addAttribute("evidBody", e);
		model.addAttribute("vybraneSady", s);
		model.addAttribute("platnostVyplnena", platnost);

		return "/vypocet";
	}

	@RequestMapping(value = "/vypocet/vybranyBod/{platnost}/")
	public String vybranyBod(@PathVariable String platnost, FormObj f, EvidencniBody eb, Model model, HttpServletRequest req) {
		log.debug("###\t vybranyBod(" + eb.getId() + ", " + platnost + ")");

		User u = serviceUser.getUser(req.getUserPrincipal().getName());
		List<SadyPrehled> s = serviceSady.getSady(u);
		EvidencniBody e = serviceEvidencniBody.getEvidencniBodyOne(eb.getId());

		model.addAttribute("zavod", e.getKbodWk());
		model.addAttribute("evidBod", e);
		model.addAttribute("vybraneSady", s);
		model.addAttribute("platnostVyplnena", platnost);

		return "/vypocet";
	}

	@RequestMapping(value = "/vypocet/spusteni/{platnost}/{idEvidBod}/")
	public String spusteni(@PathVariable String platnost, @PathVariable long idEvidBod, FormObj f, EvidencniBody eb, Model model, HttpServletRequest req) {
		log.debug("###\t spusteni(" + platnost + ", " + idEvidBod + ")");

		User u = serviceUser.getUser(req.getUserPrincipal().getName());

		// pokud neni na obrazovce zaskrtnuta zadna sada, tak nema smysl pokracovat
		if (f.getIdcka().length < 1) {
			log.debug("###\t\t  ... nebyla vybrana ani jedna sada ke zpracovani !!!");

			List<SadyPrehled> s = serviceSady.getSady(u);
			EvidencniBody e = serviceEvidencniBody.getEvidencniBodyOne(idEvidBod);

			model.addAttribute("zavod", e.getKbodWk());
			model.addAttribute("evidBod", e);
			model.addAttribute("vybraneSady", s);
			model.addAttribute("platnostVyplnena", platnost);
			model.addAttribute("info", "Nebyla vybrána žádná sada ke zpracování!");
			return "/vypocet";
		} else {
			EvidencniBody e = serviceEvidencniBody.getEvidencniBodyOne(idEvidBod);
			log.debug("###\t\t ... zakazky na evidencnim bodu: " + e.getKbodKod() + " - " + e.getKbodWk() + " - " + e.getKbodEvid() + " - " + e.getFisNaze());

			// vypisy parametru a nastaveni datumu
			String datumOd = null;
			String datumDo = null;
			platnost = platnost.replace(".", "").trim();
			if (platnost.indexOf('-') > 0) {
				datumOd = platnost.substring(0, platnost.indexOf('-'));
				datumDo = platnost.substring(platnost.indexOf('-') + 1, platnost.length());
				;
				log.debug("###\t\t ... rozsah obdobi: " + datumOd + " - " + datumDo);
			} else {
				datumOd = platnost + "01";
				datumDo = platnost + "31";
				log.debug("###\t\t ... rozsah obdobi: " + datumOd + " - " + datumDo);
			}
			log.debug("###\t\t ... vystup " + f.getAgregaceVystup() + " agregace/i");
			log.debug("###\t\t ... vystup serazen dle: " + f.getTriditDleVystup());
			log.debug("###\t\t ... vystup vcetne detailu zakazek: " + f.getZakazkyVystup());
			log.debug("###\t\t ... vystup vcetne storno vet: " + f.getStornoVetyVystup());
			for (int i = 0; i < f.getIdcka().length; i++) {
				log.debug("###\t\t ... vybrane sady pro agregaci (id): " + f.getIdcka()[i]);
			}

			// nastaveni parametru AGREGACE
			Long lastAgr = serviceOfflineJob.getLastAgregace(u);

			for (int i = 0; i < f.getIdcka().length; i++) {
				Sada s = serviceSada.getSadaOne(f.getIdcka()[i]);

				Long zakCount = serviceZakazky.getZakazkyCount(s.getSk30tMt().getMt(), e.getKbodKod(), e.getKbodWk(), e.getKbodEvid(), datumOd, datumDo, f.getStornoVetyVystup());
				log.debug("\t\t ... nacteno zakazek COUNT: " + zakCount);

				OfflineJob off = new OfflineJob();

				off.setSk30tEvidencniBody(e);
				off.setSk30tSada(s);
				off.setCasSpusteni(new Date());
				off.setPocetZakazek(new BigDecimal(zakCount));
				off.setPlatnost(platnost);
				off.setVystupRazeni(f.getTriditDleVystup());
				off.setVystupZakazky((f.getZakazkyVystup()) ? "ano" : "ne");
				off.setAgregace((f.getIdcka().length <= 1) ? null : (lastAgr + 1));
				off.setUtime(new Date());
				off.setUuser(u.getNetusername());

				serviceOfflineJob.addOfflineJob(off);

			}

			return "redirect:/srv/offline";
		}
	}
}
