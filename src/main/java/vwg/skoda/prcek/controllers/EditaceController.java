package vwg.skoda.prcek.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import cz.skoda.mbt.MBT;
import cz.skoda.mbt.PRCondition;
import vwg.skoda.prcek.entities.Mt;
import vwg.skoda.prcek.entities.MtSeznam;
import vwg.skoda.prcek.entities.PrMbt;
import vwg.skoda.prcek.entities.PrPodminka;
import vwg.skoda.prcek.entities.Protokol;
import vwg.skoda.prcek.entities.Sada;
import vwg.skoda.prcek.entities.User;
import vwg.skoda.prcek.objects.FormObj;
import vwg.skoda.prcek.services.MtService;
import vwg.skoda.prcek.services.MtSeznamService;
import vwg.skoda.prcek.services.PrMbtService;
import vwg.skoda.prcek.services.PrPodminkaService;
import vwg.skoda.prcek.services.ProtokolService;
import vwg.skoda.prcek.services.SadaService;
import vwg.skoda.prcek.services.UserService;

@Controller
@RequestMapping("/editace")
public class EditaceController {

	static Logger log = Logger.getLogger(EditaceController.class);

	@Autowired
	private UserService serviceUser;

	@Autowired
	private MtSeznamService serviceMtSeznam;

	@Autowired
	private MtService serviceMt;

	@Autowired
	private SadaService serviceSada;

	@Autowired
	private PrPodminkaService servicePrPodminka;

	@Autowired
	private ProtokolService serviceProtokol;

	@Autowired
	private PrMbtService servicePrMbt;

	
	@RequestMapping
	public String editaceStart(User netusername, Mt mt, Sada sada, Model model, HttpServletRequest req) {
		log.debug("###\t editaceStart()");

		User aktualUser = serviceUser.getUser(req.getUserPrincipal().getName());
		model.addAttribute("aktualUser", aktualUser);

		List<User> uzivatelList = serviceUser.getUsers();
		model.addAttribute("uzivatelList", uzivatelList);
		return "/editace";
	}

	@RequestMapping(value = "/vyberMt")
	public String vyberMt(User netusername, Mt mt, Sada sada, Model model) {
		log.debug("###\t vyberMt(" + netusername.getNetusername() + ")");
		
		User u = serviceUser.getUser(netusername.getNetusername());
		model.addAttribute("vybranyUzivatel", u);

		List<Mt> mtList = serviceMt.getMt(u.getId());
		model.addAttribute("mtList", mtList);
		return "/editace";
	}

	// controller je zde kvuli funkci primeho odkazu na nazev MT, aby uzivatel mohl znovu vybrat jinou MT s jiz zvolenym userem	
	@RequestMapping(value = "/vyberMt/{vybranyUzivatel}")
	public String vyberMt(@PathVariable String vybranyUzivatel, User netusername, Mt mt, Sada sada, Model model) {
		log.debug("###\t vyberMt(" + vybranyUzivatel + ")");
 
		User u = serviceUser.getUser(vybranyUzivatel);
		vyberMt(u, mt, sada, model);
		return "/editace";
	}

	@RequestMapping(value = "/vyberSadu/{vybranyUzivatel}")
	public String vyberSadu(@PathVariable String vybranyUzivatel, User netusername, Mt mt, Sada sada, Model model) {
		log.debug("###\t vyberSadu(" + vybranyUzivatel + ", " + mt.getMt() + ")");

		User u = serviceUser.getUser(vybranyUzivatel);
		Mt m = serviceMt.getMtOne(u.getId(), mt.getMt());
		model.addAttribute("vybranyUzivatel", u);
		model.addAttribute("vybranaMt", m);

		List<Sada> sadaList = serviceSada.getSada(m);
		model.addAttribute("sadaList", sadaList);

		return "/editace";
	}
	
	// controller je zde kvuli funkci primeho odkazu na nazev sady, aby uzivatel mohl znovu vybrat jinou sadu s jiz zvolenym userem a mt	
	@RequestMapping(value = "/vyberSadu/{vybranyUzivatel}/{vybranaMt}")
	public String vyberSadu(@PathVariable String vybranyUzivatel, @PathVariable String vybranaMt, User netusername, Mt mt, Sada sada, Model model) {
		log.debug("###\t vyberSadu(" + vybranyUzivatel + ", " + vybranaMt + ")");

		User u = serviceUser.getUser(vybranyUzivatel);
		Mt m = serviceMt.getMtOne(u.getId(), vybranaMt);

		vyberSadu(vybranyUzivatel, u, m, sada, model);
		return "/editace";
	}

	@RequestMapping(value = "/zobrazPr/{vybranyUzivatel}/{vybranaMt}")
	public String zobrazPr(@PathVariable String vybranyUzivatel, @PathVariable String vybranaMt, User netusername, Mt mt, Sada sada, Model model, HttpServletRequest req) {
	//public String zobrazPr(@PathVariable String vybranyUzivatel, @PathVariable String vybranaMt, @RequestParam(required = false) String sadada, User netusername, Mt mt, Sada sada, Model model, HttpServletRequest req) {
		//if(sadada != null)	sada.setNazev(sadada);
		
		log.debug("###\t zobrazPr(" + vybranyUzivatel + ", " + vybranaMt + ", " + sada.getNazev() + ")");

		User u = serviceUser.getUser(vybranyUzivatel);
		Mt m = serviceMt.getMtOne(u.getId(), vybranaMt);
		Sada s = serviceSada.getSadaOne(m, sada.getNazev());

		// zajistit aby se editacni tlacitka pro sadu objevilo jen uzivateli, ktery muze sadu editovat (mazat/prejmenovat)!
		User uPrihlaseny = serviceUser.getUser(req.getUserPrincipal().getName());
		if (uPrihlaseny.getId() == u.getId()) {
			model.addAttribute("moznoEditovatSadu", true);
		} else {
			model.addAttribute("moznoEditovatSadu", false);
		}

		List<PrPodminka> prPodminkaList = servicePrPodminka.getPrPodminka(s);
		model.addAttribute("prPodminkaList", prPodminkaList);
		
		Long prCount = servicePrPodminka.getPrPodminkaCount(s);
		model.addAttribute("prCount", prCount);

		model.addAttribute("vybranyUzivatel", u);
		model.addAttribute("vybranaMt", m);
		model.addAttribute("vybranaSada", s);

		return "/editace";
	}
	
	@RequestMapping(value = "/zobrazPr/{vybranyUzivatel}/{vybranaMt}/{vybranaSada}")
	public String zobrazPr(@PathVariable String vybranyUzivatel, @PathVariable String vybranaMt, @PathVariable long vybranaSada, User netusername, Mt mt, Sada sada, Model model,
			HttpServletRequest req) {
		log.debug("###\t zobrazPr(" + vybranyUzivatel + ", " + vybranaMt + ", " + vybranaSada+ ")");

		User u = serviceUser.getUser(vybranyUzivatel);
		Mt m = serviceMt.getMtOne(u.getId(), vybranaMt);
		Sada s = serviceSada.getSadaOne(vybranaSada);

		// zajistit aby se editacni tlacitka pro sadu objevilo jen uzivateli, ktery muze sadu editovat (mazat/prejmenovat)!
		User uPrihlaseny = serviceUser.getUser(req.getUserPrincipal().getName());
		if (uPrihlaseny.getId() == u.getId()) {
			model.addAttribute("moznoEditovatSadu", true);
		} else {
			model.addAttribute("moznoEditovatSadu", false);
		}

		List<PrPodminka> prPodminkaList = servicePrPodminka.getPrPodminka(s);
		model.addAttribute("prPodminkaList", prPodminkaList);
		
		Long prCount = servicePrPodminka.getPrPodminkaCount(s);
		model.addAttribute("prCount", prCount);

		model.addAttribute("vybranyUzivatel", u);
		model.addAttribute("vybranaMt", m);
		model.addAttribute("vybranaSada", s);

		return "/editace";
	}
	
	@RequestMapping(value = "/zobrazPrOrderByTest/{vybranyUzivatel}/{vybranaMt}/{vybranaSada}")
	public String zobrazPrOrderByTest(@PathVariable String vybranyUzivatel, @PathVariable String vybranaMt, @PathVariable long vybranaSada, User netusername, Mt mt, Sada sada, Model model,
			HttpServletRequest req) {
		log.debug("###\t zobrazPrOrderByTest(" + vybranyUzivatel + ", " + vybranaMt + ", " + vybranaSada+ ")");

		User u = serviceUser.getUser(vybranyUzivatel);
		Mt m = serviceMt.getMtOne(u.getId(), vybranaMt);
		Sada s = serviceSada.getSadaOne(vybranaSada);

		// zajistit aby se editacni tlacitka pro sadu objevilo jen uzivateli, ktery muze sadu editovat (mazat/prejmenovat)!
		User uPrihlaseny = serviceUser.getUser(req.getUserPrincipal().getName());
		if (uPrihlaseny.getId() == u.getId()) {
			model.addAttribute("moznoEditovatSadu", true);
		} else {
			model.addAttribute("moznoEditovatSadu", false);
		}

		List<PrPodminka> prPodminkaList = servicePrPodminka.getPrPodminkaOrderByTest(s);
		model.addAttribute("prPodminkaList", prPodminkaList);
		
		Long prCount = servicePrPodminka.getPrPodminkaCount(s);
		model.addAttribute("prCount", prCount);

		model.addAttribute("vybranyUzivatel", u);
		model.addAttribute("vybranaMt", m);
		model.addAttribute("vybranaSada", s);

		return "/editace";
	}

	@RequestMapping(value = "/smazatSadu/{vybranyUziv}/{vybranaMt}/{vybranaSada}")
	public String smazatSadu(@PathVariable String vybranyUziv, @PathVariable String vybranaMt, @PathVariable long vybranaSada, User netusername,
			Sada sada, Mt mt, Model model, HttpServletRequest req) {
		log.debug("###\t smazatSadu(" + vybranyUziv + ", " + vybranaMt + ", " + vybranaSada + ")");

		//User u = serviceUser.getUser(vybranyUziv);
		//Mt modTr = serviceMt.getMtOne(u.getId(), vybranaMt);
		Sada s = serviceSada.getSadaOne(vybranaSada);

		try {
			serviceSada.removeSada(s.getId());
		} catch (Exception e) {
			log.debug("ERROR - smazatSadu(" + vybranyUziv + ", " + vybranaMt + ", " + vybranaSada + ") ...: " + e);

		} finally {
			Protokol newProtokol = new Protokol();
			newProtokol.setNetusername(req.getUserPrincipal().getName().toUpperCase());
			newProtokol.setAction("Smazani sady");
			newProtokol.setInfo(vybranaMt + " - " + vybranaSada + "   ###   " + req.getSession().getServletContext().getServerInfo());
			newProtokol.setTime(new Date());
			newProtokol.setSessionid(req.getSession().getId());
			serviceProtokol.addProtokol(newProtokol);
		}

		return "redirect:/srv/editace/vyberSadu/"+s.getSk30tMt().getSk30tUser().getNetusername()+"/"+s.getSk30tMt().getMt();
	}

	@RequestMapping(value = "/novaSada")
	public String novaSada(FormObj f, Model model, HttpServletRequest req) {
		log.debug("###\t novaSada()");

		List<MtSeznam> mt = serviceMtSeznam.getMt();
		model.addAttribute("seznamMt", mt);

		User u = serviceUser.getUser(req.getUserPrincipal().getName());
		model.addAttribute("prihlasenyUzivatel", u.getPrijmeni() + " " + u.getJmeno() + ", " + u.getOddeleni() + " (" + u.getNetusername() + ")");
		return "/sadaNova";
	}

	@RequestMapping(value = "/novaSadaTed")
	public String novaSadaTed(User user, Sada sada, FormObj f, Model model, HttpServletRequest req) {
		log.debug("###\t novaSadaTed(" + req.getUserPrincipal().getName() + ", " + f.getMt() + ", '" + f.getSada() + "')");

		User u = serviceUser.getUser(req.getUserPrincipal().getName());
		MtSeznam mts = serviceMtSeznam.getMtSeznamOne(f.getMt());

		if (!serviceMt.existMt(u.getNetusername(), f.getMt())) {
			Mt newMt = new Mt();
			newMt.setMt(f.getMt());
			newMt.setProdukt(mts.getProdukt());
			newMt.setUuser(u.getNetusername());
			newMt.setUtime(new Date());
			newMt.setSk30tUser(u);
			serviceMt.addMt(newMt);
		}

		if (!serviceSada.existSada(f.getSada(), f.getMt())) {
			Sada newSada = new Sada();
			newSada.setNazev(f.getSada().trim());
			newSada.setUuser(u.getNetusername());
			newSada.setUtime(new Date());
			newSada.setSk30tMt(serviceMt.getMtOne(u.getId(), f.getMt()));
			serviceSada.addSada(newSada);
		}

		Mt mt = serviceMt.getMtOne(u.getId(), mts.getMt());
		Sada s = serviceSada.getSadaOne(mt, f.getSada().trim());

		//return "redirect:/srv/editace/zobrazPr/"+u.getNetusername()+"/"+s.getSk30tMt().getMt()+"?sada="+s.getNazev();
		return "redirect:/srv/editace/zobrazPr/"+u.getNetusername()+"/"+s.getSk30tMt().getMt()+"/"+s.getId();
	}

	@RequestMapping(value = "/prejmenovatSadu/{vybranaSada}")
	public String prejmenovatSadu(@PathVariable long vybranaSada, FormObj f, Model model, HttpServletRequest req) {
		log.debug("###\t prejmenovatSadu(" + vybranaSada + ")");
		Sada s = serviceSada.getSadaOne(vybranaSada);
		model.addAttribute("prihlasenyUzivatel", s.getSk30tMt().getSk30tUser().getPrijmeni() + " " + s.getSk30tMt().getSk30tUser().getJmeno() + ", "
				+ s.getSk30tMt().getSk30tUser().getOddeleni() + " (" + s.getSk30tMt().getSk30tUser().getNetusername() + ")");
		model.addAttribute("vybranaMt", s.getSk30tMt());
		model.addAttribute("vybranaSada", s);
		f.setSada(s.getNazev());
		return "/sadaPrejmenovat";
	}

	@RequestMapping(value = "/prejmenovatSaduTed/{vybranaSada}")
	public String prejmenovatSaduTed(@PathVariable long vybranaSada, User user, Sada sada, FormObj f, Model model, HttpServletRequest req) {
		log.debug("###\t prejmenovatSaduTed(" + req.getUserPrincipal().getName() + ", " + f.getMt() + ", '" + f.getSada() + "', " + vybranaSada + ")");

		User u = serviceUser.getUser(req.getUserPrincipal().getName());
		Sada s = serviceSada.getSadaOne(vybranaSada);

		s.setNazev(f.getSada());
		s.setUuser(u.getNetusername());
		s.setUtime(new Date());
		serviceSada.setSada(s);

		return "redirect:/srv/editace/zobrazPr/"+u.getNetusername()+"/"+s.getSk30tMt().getMt()+"/"+s.getId();
	}

	@RequestMapping(value = "/novaPrPodminka/{vybranaSada}")
	public String novaPrPodminka(@PathVariable long vybranaSada, FormObj f, Model model, HttpServletRequest req) {
		log.debug("###\t novaPrPodminka(" + vybranaSada + ")");

		Sada s = serviceSada.getSadaOne(vybranaSada);
		model.addAttribute("prihlasenyUzivatel", s.getSk30tMt().getSk30tUser().getPrijmeni() + " " + s.getSk30tMt().getSk30tUser().getJmeno() + ", "
				+ s.getSk30tMt().getSk30tUser().getOddeleni() + " (" + s.getSk30tMt().getSk30tUser().getNetusername() + ")");
		model.addAttribute("vybranaMt", s.getSk30tMt());
		model.addAttribute("vybranaSada", s);
		return "/prPodminkaNova";
	}

	@RequestMapping(value = "/novaPrPodminkaTed/{vybranaSada}")
	public String novaPrPodminkaTed(@PathVariable long vybranaSada, User user, Sada sada, FormObj f, Model model, HttpServletRequest req) {
		log.debug("###\t novaPrPodminkaTed(" + f.getPoradi() + ", " + f.getPrPodminka() + ", " + f.getPoznamka() + ", " + vybranaSada + ")");

		User u = serviceUser.getUser(req.getUserPrincipal().getName());
		Sada s = serviceSada.getSadaOne(vybranaSada);

		PrPodminka prp = new PrPodminka();
		prp.setPr(f.getPrPodminka().toUpperCase());
		prp.setPoradi(f.getPoradi());
		prp.setPoznamka(f.getPoznamka());
		prp.setUuser(u.getNetusername());
		prp.setUtime(new Date());
		prp.setSk30tSada(s);
		
		// MBT kontrola
		try {
			Mt mt = serviceMt.getMtOne(s.getSk30tMt().getId());
			List<PrMbt> pr =  servicePrMbt.getPr(mt.getProdukt());
			MBT mbt = new MBT();
			mbt.setMBTSource(pr);
			PRCondition prCond = mbt.getPRCondition(f.getPrPodminka().toUpperCase());
			prp.setErrMbt(null);
			log.debug("###\t Kontrola PR podminky MBT (rucni zadani) "+prCond.toString()+" ... OK");
		} catch (Exception e) {
			log.error("###\t Chyba pri obsahove kontrole PR podminky (rucni zadani): " + e);
			prp.setErrMbt(e.getMessage());
		}
		
		servicePrPodminka.addPrPodminka(prp);

		return "redirect:/srv/editace/zobrazPr/"+u.getNetusername()+"/"+s.getSk30tMt().getMt()+"/"+s.getId();
	}
	
	@RequestMapping(value = "/editovatPr/{idPr}")
	public String editovatPr(@PathVariable long idPr, FormObj f, Model model, HttpServletRequest req) {
		log.debug("###\t editovatPr(" + idPr + ")");

		PrPodminka pr = servicePrPodminka.getPrPodminkaOne(idPr);		
		Sada s = serviceSada.getSadaOne(pr.getSk30tSada().getId());
		model.addAttribute("prihlasenyUzivatel", s.getSk30tMt().getSk30tUser().getPrijmeni() + " " + s.getSk30tMt().getSk30tUser().getJmeno() + ", "
				+ s.getSk30tMt().getSk30tUser().getOddeleni() + " (" + s.getSk30tMt().getSk30tUser().getNetusername() + ")");
		model.addAttribute("vybranaMt", s.getSk30tMt());
		model.addAttribute("vybranaSada", s);
		model.addAttribute("vybranaPrPodminka", pr);
		f.setPoradi(pr.getPoradi());
		f.setPrPodminka(pr.getPr());
		f.setPoznamka(pr.getPoznamka());
		
		return "/prPodminkaEditovat";
	}

	@RequestMapping(value = "/editovatPrTed/{vybranaPrPodminka}")
	public String editovatPrTed(@PathVariable long vybranaPrPodminka, User user, Sada sada, FormObj f, Model model, HttpServletRequest req) {
		log.debug("###\t editovatPrTed(" + f.getPoradi() + ", " + f.getPrPodminka() + ", " + f.getPoznamka() + ", " + vybranaPrPodminka + ")");

		User u = serviceUser.getUser(req.getUserPrincipal().getName());
		PrPodminka prp = servicePrPodminka.getPrPodminkaOne(vybranaPrPodminka);
		Sada s = serviceSada.getSadaOne(prp.getSk30tSada().getId());
		
		prp.setPr(f.getPrPodminka().toUpperCase());
		prp.setPoradi(f.getPoradi());
		prp.setPoznamka(f.getPoznamka());
		prp.setUuser(u.getNetusername());
		prp.setUtime(new Date());

		try {
			Mt mt = serviceMt.getMtOne(s.getSk30tMt().getId());
			List<PrMbt> pr =  servicePrMbt.getPr(mt.getProdukt());
			MBT mbt = new MBT();
			mbt.setMBTSource(pr);
			PRCondition prCond = mbt.getPRCondition(f.getPrPodminka().toUpperCase());
			prp.setErrMbt(null);
			log.debug("###\t Kontrola PR podminky (MBT) "+prCond.toString()+" ... OK");
		} catch (Exception e) {
			log.error("###\t Chyba pri obsahove kontrole PR podminky: " + e);
			prp.setErrMbt(e.getMessage());
		}
		
		servicePrPodminka.setPrPodminka(prp);

		return "redirect:/srv/editace/zobrazPr/"+u.getNetusername()+"/"+s.getSk30tMt().getMt()+"/"+s.getId();
	}

	@RequestMapping(value = "/smazatVsechnyPr/{vybranaSada}")
	public String smazatVsechnyPr(@PathVariable long vybranaSada, User netusername, Sada sada, Mt mt, Model model, HttpServletRequest req) {
		log.debug("###\t smazatVsechnyPr(" + vybranaSada + ")");

		Sada s = serviceSada.getSadaOne(vybranaSada);
		User u = serviceUser.getUser(s.getSk30tMt().getSk30tUser().getNetusername());

		List<PrPodminka> pr = servicePrPodminka.getPrPodminka(s);

		int pocetSmazanychPr = 0;
		for (PrPodminka p : pr) {
			try {
				servicePrPodminka.removePrPodminka(p.getId());
			} catch (Exception e) {
				log.debug("ERROR - smazatVsechnyPr(" + u.getNetusername() + ", " + s.getSk30tMt().getMt() + ", " + s.getNazev() + ", " + p.getPr()
						+ ") ...: " + e);
			} finally {
				pocetSmazanychPr++;
			}
		}
		Protokol newProtokol = new Protokol();
		newProtokol.setNetusername(req.getUserPrincipal().getName().toUpperCase());
		newProtokol.setAction("Smazani vsech PR podminek");
		newProtokol.setInfo(s.getSk30tMt().getMt() + " - " + s.getNazev() + " ("+ pocetSmazanychPr +")   ###   " + req.getSession().getServletContext().getServerInfo());
		//newProtokol.setInfo(s.getSk30tMt().getMt() + " - " + s.getNazev() +"   ###   " + req.getSession().getServletContext().getServerInfo());
		newProtokol.setTime(new Date());
		newProtokol.setSessionid(req.getSession().getId());
		serviceProtokol.addProtokol(newProtokol);
		
		return "redirect:/srv/editace/zobrazPr/"+u.getNetusername()+"/"+s.getSk30tMt().getMt()+"/"+s.getId();
	}
	
	@RequestMapping(value = "/smazatPrPodminku/{idPr}")
	public String smazatPrPodminku(@PathVariable long idPr, User netusername, Sada sada, Mt mt, Model model, HttpServletRequest req) {
		log.debug("###\t smazatPrPodminku(" + idPr + ")");
		
		PrPodminka pr = servicePrPodminka.getPrPodminkaOne(idPr);
		User u = serviceUser.getUser(pr.getSk30tSada().getSk30tMt().getSk30tUser().getNetusername());

		servicePrPodminka.removePrPodminka(idPr);
		
		Protokol newProtokol = new Protokol();
		newProtokol.setNetusername(req.getUserPrincipal().getName().toUpperCase());
		newProtokol.setAction("Smazani PR podminky");
		newProtokol.setInfo(pr.getSk30tSada().getSk30tMt().getMt() + " - " + pr.getSk30tSada().getNazev() + " ("+ pr.getNazev() +")   ###   " + req.getSession().getServletContext().getServerInfo());
		newProtokol.setTime(new Date());
		newProtokol.setSessionid(req.getSession().getId());
		serviceProtokol.addProtokol(newProtokol);
		
		return "redirect:/srv/editace/zobrazPr/"+u.getNetusername()+"/"+pr.getSk30tSada().getSk30tMt().getMt()+"/"+pr.getSk30tSada().getId();
	}
/*	
	@RequestMapping(value = "/importTxt/{vybranaSada}")
	public String importTxt(@PathVariable long vybranaSada, User netusername, Sada sada, Mt mt, Model model, HttpServletRequest req) {
		log.debug("###\t importTxt(" + vybranaSada + ")");

		Sada s = serviceSada.getSadaOne(vybranaSada);
		User u = serviceUser.getUser(s.getSk30tMt().getSk30tUser().getNetusername());

		
		return "redirect:/srv/editace/zobrazPr/"+u.getNetusername()+"/"+s.getSk30tMt().getMt()+"/"+s.getId();
	}
*/
	
}
