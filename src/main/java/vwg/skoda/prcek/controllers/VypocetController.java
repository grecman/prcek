package vwg.skoda.prcek.controllers;

import java.math.BigDecimal;
import java.text.ParseException;
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
import vwg.skoda.prcek.entities.User;
import vwg.skoda.prcek.objects.FormObj;
import vwg.skoda.prcek.services.EvidencniBodyService;
import vwg.skoda.prcek.services.MtService;
import vwg.skoda.prcek.services.OfflineJobService;
import vwg.skoda.prcek.services.PrPodminkaService;
import vwg.skoda.prcek.services.ProtokolService;
import vwg.skoda.prcek.services.SadaService;
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

		if(req.isUserInRole("SERVICEDESK")){
			return "redirect:/srv/monitoring";
		}

		User u = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		//List<SadyPrehled> s = serviceSady.getSady(u);
		List<Sada> s = serviceSada.getSady(u);
		if(s.size()>0){
			model.addAttribute("vybraneSady", s);
			model.addAttribute("noSada",null);
		} else {
			model.addAttribute("noSada","Nemáte vytvořenou žádnou sadu pro zpracování! Založte si jí v sekci EDITACE.");
		}
		

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

		User u = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		//List<SadyPrehled> s = serviceSady.getSady(u);
		List<Sada> s = serviceSada.getSady(u);

		List<String> zavody = serviceEvidencniBody.getEvidencniBodySeznamWk();

		model.addAttribute("seznamZavodu", zavody);
		model.addAttribute("vybraneSady", s);
		model.addAttribute("platnostVyplnena", platnost);

		return "/vypocet";
	}

	@RequestMapping(value = "/vypocet/vybranyZavod/{platnost}/")
	public String vybranyZavod(@PathVariable String platnost, FormObj f, EvidencniBody eb, Model model, HttpServletRequest req) {
		log.debug("###\t vybranyZavod(" + f.getZavod() + ", " + platnost + ")");

		User u = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		//List<SadyPrehled> s = serviceSady.getSady(u);
		List<Sada> s = serviceSada.getSady(u);

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

		User u = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		//List<SadyPrehled> s = serviceSady.getSady(u);
		List<Sada> s = serviceSada.getSady(u);
		EvidencniBody e = serviceEvidencniBody.getEvidencniBodyOne(eb.getId());

		model.addAttribute("zavod", e.getKbodWk());
		model.addAttribute("evidBod", e);
		model.addAttribute("vybraneSady", s);
		model.addAttribute("platnostVyplnena", platnost);

		return "/vypocet";
	}

	@RequestMapping(value = "/vypocet/zafrontovani/{platnost}/{idEvidBod}/")
	public String zafrontovani(@PathVariable String platnost, @PathVariable long idEvidBod, FormObj f, EvidencniBody eb, Model model, HttpServletRequest req) throws ParseException {
		log.debug("###\t zafrontovani(" + platnost + ", " + idEvidBod + ")");

		User u = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());

		// pokud neni na obrazovce zaskrtnuta zadna sada, tak nema smysl pokracovat
		if (f.getIdcka().length < 1) {
			log.debug("###\t\t  ... nebyla vybrana ani jedna sada ke zpracovani !!!");

			//List<SadyPrehled> s = serviceSady.getSady(u);
			List<Sada> s = serviceSada.getSady(u);
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
			platnost = platnost.replace(".", "").trim();
			String datumOd = null;
			String datumDo = null;
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			Date platnostOd;
			Date platnostDo;
			if (platnost.indexOf('-') > 0) {
				datumOd = platnost.substring(0, platnost.indexOf('-'));
				datumDo = platnost.substring(platnost.indexOf('-') + 1, platnost.length());
				platnostOd = formatter.parse(datumOd);
				platnostDo = formatter.parse(datumDo);
			} else {
				datumOd = platnost + "01";
				platnostOd = formatter.parse(datumOd);
				
				// ziskani posledniho dne v ZADANEM mesici
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
				Date prac = dateFormat.parse(platnost);
				Calendar c = Calendar.getInstance();
				c.setTime(prac);
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));

				datumDo = platnost + c.getActualMaximum(Calendar.DAY_OF_MONTH);

				platnostDo = formatter.parse(datumDo);

			}
			log.debug("###\t\t ... rozsah obdobi: " + platnostOd + " - " + platnostDo);
			log.debug("###\t\t ... vystup " + f.getAgregaceVystup() + " agregace/i");
			log.debug("###\t\t ... vystup serazen dle: " + f.getTriditDleVystup());
			log.debug("###\t\t ... vystup vcetne detailu zakazek: " + f.getZakazkyVystup());
			log.debug("###\t\t ... vystup vcetne storno vet: " + f.getStornoVetyVystup());
			int pocetSad = 1;
			for (int i = 0; i < f.getIdcka().length; i++) {
				log.debug("###\t\t ... vybrane sady (IDcka) pro agregaci: " + pocetSad++ + ". " + f.getIdcka()[i]);
			}

			// nastaveni parametru ZPRACOVANI
			Long lastAgr = serviceOfflineJob.getLastAgregace(u);

			for (int i = 0; i < f.getIdcka().length; i++) {
				Sada s = serviceSada.getSadaOne(f.getIdcka()[i]);

				Long zakCount = serviceZakazky.getZakazkyCount(s.getSk30tMt().getMt(), e.getKbodKod(), e.getKbodWk(), e.getKbodEvid(), datumOd, datumDo, f.getStornoVetyVystup());
				log.debug("###\t\t Nacteno zakazek COUNT: " + zakCount);

				OfflineJob off = new OfflineJob();
				off.setSk30tEvidencniBody(e);
				off.setSk30tSada(s);
				off.setCasSpusteni(new Date());
				// off.setStorno((f.getStornoVetyVystup()) ? "ano" : "ne");
				off.setStornoZakazky(f.getStornoVetyVystup());
				off.setPocetZakazek(zakCount);
				if (zakCount < 1) {
					off.setProces("Nebude zpracováno");
				}
				off.setPlatnostOd(platnostOd);
				off.setPlatnostDo(platnostDo);
				off.setVystupRazeni(f.getTriditDleVystup());
				off.setUtime(new Date());
				off.setUuser(u.getNetusername());
				off.setZakazkyVystup(f.getZakazkyVystup());
				if ("s".startsWith(f.getAgregaceVystup())) {
					off.setAgregace((f.getIdcka().length <= 1) ? null : (lastAgr + 1));
				}
				serviceOfflineJob.addOfflineJob(off);
			}
			return "redirect:/srv/offline/startJob";
		}
	}
}
