package vwg.skoda.prcek.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.async.WebAsyncTask;

import vwg.skoda.prcek.entities.OfflineJob;
import vwg.skoda.prcek.entities.PrPodminka;
import vwg.skoda.prcek.entities.User;
import vwg.skoda.prcek.entities.Vysledek;
import vwg.skoda.prcek.entities.Zakazky;
import vwg.skoda.prcek.objects.VysledekExpSAgregaci;
import vwg.skoda.prcek.outputs.ExportXls;
import vwg.skoda.prcek.services.OfflineJobService;
import vwg.skoda.prcek.services.PrPodminkaService;
import vwg.skoda.prcek.services.UserService;
import vwg.skoda.prcek.services.VysledekService;
import vwg.skoda.prcek.services.ZakazkyService;
import cz.skoda.mbt.JobPRCondition;

@Controller
public class OfflineController {

	static Logger log = Logger.getLogger(OfflineController.class);

	@Autowired
	private UserService serviceUser;

	@Autowired
	private OfflineJobService serviceOfflineJob;

	@Autowired
	private PrPodminkaService servicePrPodminka;

	@Autowired
	private ZakazkyService serviceZakazky;

	@Autowired
	private VysledekService serviceVysledek;

	@RequestMapping(value = "/offline")
	public String offline(Model model, HttpServletRequest req) {
		log.debug("###\t offline()");

		if (req.isUserInRole("SERVICEDESK")) {
			return "redirect:/srv/monitoring";
		}

		User u = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());

		List<OfflineJob> off = serviceOfflineJob.getOfflineJob(u);
		model.addAttribute("offList", off);
		model.addAttribute("aktualUser", u);
		
		if (req.isUserInRole("ADMINS")) {
			model.addAttribute("adminRole", true);
		} else {
			model.addAttribute("adminRole", false);
		}

		return "/offline";
	}
	
	@RequestMapping(value = "/offline/showAllUsers")
	public String showAllUsers(Model model, HttpServletRequest req) {
		log.debug("###\t showAllUsers()");

		User u = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());

		List<OfflineJob> off = serviceOfflineJob.getOfflineJobAll();
		model.addAttribute("offList", off);
		model.addAttribute("aktualUser", u);
		
		if (req.isUserInRole("ADMINS")) {
			model.addAttribute("adminRole", true);
		} else {
			model.addAttribute("adminRole", false);
		}

		return "/offline";
	}

	@RequestMapping(value = "/offline/startJob")
	public String startJob(Model model, HttpServletRequest req) {
		log.debug("###\t startJob ()");
		User u = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());

		// mazani jobu starsich jak 92 dnu
		serviceOfflineJob.removeOldOfflineJob(u);

		List<OfflineJob> off = serviceOfflineJob.getOfflineJobKeZpracovani(u);
		log.debug("###\t\t ... nacteno jobu ke zpracovani: " + off.size());

		if (off.isEmpty() || off.size() < 1) {
			return "redirect:/srv/offline";
		} else {

			OfflineJob offOne = serviceOfflineJob.getOfflineJobOne(off.get(0).getId());
			offOne.setProces("v procesu");
			serviceOfflineJob.setOfflineJob(offOne);
			
			return "redirect:/srv/offline/rozpad/" + off.get(0).getId();
		}
	}


	@RequestMapping(value = "/offline/rozpad/{idOfflineJob}")
	public WebAsyncTask<String> rozpad(@PathVariable final long idOfflineJob, User u, Model model, final HttpServletRequest req) {
		log.debug("### ASYNC ###\t rozpad(" + idOfflineJob + ",\t" + Thread.currentThread() + ")");

		// V tomto bloku v implementovane metode call() se pousti asynchrone nejaky "dlouhy" proces (export/import/rozpad...)
		// return v teto metode se provede pouze pokud proces nepresahne nize uvedeny casovy limit
		Callable<String> callAsyncThread = new Callable<String>() {

			@Override
			public String call() throws Exception {
				log.debug("### ASYNC ###\t\t call(" + Thread.currentThread() + ")");

				OfflineJob off = serviceOfflineJob.getOfflineJobOne(idOfflineJob);
				off.setProces("v procesu");
				off.setCasSpusteni(new Date());
				serviceOfflineJob.setOfflineJob(off);

				List<PrPodminka> pr = servicePrPodminka.getPrPodminka(off.getSk30tSada());

				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
				String datumOd = DATE_FORMAT.format(off.getPlatnostOd());
				String datumDo = DATE_FORMAT.format(off.getPlatnostDo());
				
//				List<Zakazky> zak = serviceZakazky.getZakazky(off.getSk30tSada().getSk30tMt().getMt(), off.getSk30tEvidencniBody().getKbodKod(), off.getSk30tEvidencniBody().getKbodWk(), off
//						.getSk30tEvidencniBody().getKbodEvid(), datumOd, datumDo, off.getStornoZakazky());

				try {

					Map<PrPodminka, Integer> m = new HashMap<PrPodminka, Integer>();
					for (PrPodminka prPodminka : pr){
						m.put(prPodminka, 0);
					}
					
					Long celkPocZak = off.getPocetZakazek();
				    int zpracovanyPocZak = 0;
				    
				    int grePocitadlo = 0;
				    
					while (zpracovanyPocZak < celkPocZak) {
						log.debug("### Zpracovano zakazek: " + zpracovanyPocZak + " / " + celkPocZak);
						List<Zakazky> zak = serviceZakazky.getZakazkyMaxPoc(off.getSk30tSada().getSk30tMt().getMt(), off.getSk30tEvidencniBody().getKbodKod(), off.getSk30tEvidencniBody().getKbodWk(), off
								.getSk30tEvidencniBody().getKbodEvid(), datumOd, datumDo, off.getStornoZakazky(), zpracovanyPocZak);
						zpracovanyPocZak += zak.size();

						for (Zakazky z : zak) {
							//log.info("###\tZPRACOVANO:\t"+celkPocZak+"\t-\t"+grePocitadlo++);
							for (Map.Entry<PrPodminka, Integer> e : m.entrySet()) {
								
								vwg.skoda.mpz.core.matchers.PrPodminka prp = new vwg.skoda.mpz.core.matchers.PrPodminka(z.getPrpoz());
								String s = e.getKey().getPr();
								if (new vwg.skoda.mpz.core.matchers.PrPodminka(s).match(prp)) {
									e.setValue(e.getValue() + 1);
								}
							}
						}
					}
					
					log.trace("Vypocet: " + m);
					for (Map.Entry<PrPodminka, Integer> e: m.entrySet()) {
						Vysledek v = new Vysledek();
						v.setSk30tOfflineJob(off);
						v.setSk30tPrPodminka(e.getKey());
						v.setSoucet(e.getValue().longValue());
						v.setUtime(new Date());
						v.setUuser(off.getSk30tSada().getSk30tMt().getSk30tUser().getNetusername());
						serviceVysledek.addVysledek(v);
						
					}				
/*					
					
					for (PrPodminka prPodminka : pr) {
						long soucet = 0;
						for (Zakazky zakazka : zak) {
							if (JobPRCondition.correspond(zakazka.getPrpoz(), prPodminka.getPr())) {
								soucet++;
							}
						}
						Vysledek v = new Vysledek();
						v.setSk30tOfflineJob(off);
						v.setSk30tPrPodminka(prPodminka);
						v.setSoucet(soucet);
						v.setUtime(new Date());
						v.setUuser(off.getSk30tSada().getSk30tMt().getSk30tUser().getNetusername());
						serviceVysledek.addVysledek(v);
					}
*/				
					off.setProces("hotovo");
					off.setCasUkonceni(new Date());
					serviceOfflineJob.setOfflineJob(off);
				} catch (Exception e) {
					log.error("###\t Chyba porovnani PR zakazky s PR podminkou: ", e);
				}
	
				return "redirect:/srv/offline";
			}
		};

		// nastavi casovy limit pro vyse uvedeny proces
		// tento WebAsyncTask jede vlastne soubezne s tim Callable a po uplynulem casovem limitu ji opusti a vrati "return" ktery je v tom
		// ".onTimeout"
		WebAsyncTask<String> webAs = new WebAsyncTask<String>(1000, callAsyncThread); // 1000ms = 1s; 60000 = 1min;
		webAs.onTimeout(new Callable<String>() {

			@Override
			public String call() throws Exception {
				log.debug("###ASYNC###\t\t call ... onTimeOut (" + Thread.currentThread() + ")");

				return "redirect:/srv/offline";
			}

		});
		return webAs;
	}

	@RequestMapping(value = "/offline/vysledek/{idOfflineJob}")
	public String vysledek(@PathVariable long idOfflineJob, Model model, HttpServletRequest req) {
		log.debug("###\t vysledek(" + idOfflineJob + ")");

		OfflineJob off = serviceOfflineJob.getOfflineJobOne(idOfflineJob);

		List<Vysledek> vys = serviceVysledek.getVysledek(off);
		model.addAttribute("vysledek", vys);
		model.addAttribute("idOfflineJob", idOfflineJob);

		return "/vysledek";
	}

	@RequestMapping(value = "/offline/vysledekSAgregaci/{idOfflineJob}")
	public String vysledekSAgregaci(@PathVariable long idOfflineJob, Model model, HttpServletRequest req) {
		log.debug("###\t vysledekSAgregaci(" + idOfflineJob + ")");

		OfflineJob off = serviceOfflineJob.getOfflineJobOne(idOfflineJob);
		User user = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		List<OfflineJob> offAgregace = serviceOfflineJob.getOfflineJob(user, off.getAgregace());

		List<Long> seznamAgregaci = new ArrayList<Long>();

		for (OfflineJob offAgr : offAgregace) {
			seznamAgregaci.add(offAgr.getId());
		}
		log.debug("###\t  ... agregace cislo " + off.getAgregace() + " pro ID: " + seznamAgregaci);

		List<Vysledek> vys = serviceVysledek.getVysledek(seznamAgregaci);

		model.addAttribute("vysledek", vys);
		model.addAttribute("idOfflineJob", idOfflineJob);
		model.addAttribute("idOfflineJobs", offAgregace);

		return "/vysledekSAgregaci";
	}

	@RequestMapping(value = "/offline/exportXls/{idOfflineJob}")
	public void exportXls(@PathVariable long idOfflineJob, Model model, HttpServletRequest req, HttpServletResponse res) throws IOException {
		log.debug("###\t exportXls(" + idOfflineJob + ")");
		OfflineJob off = serviceOfflineJob.getOfflineJobOne(idOfflineJob);

		List<Zakazky> zak = null;
		if (off.getZakazkyVystup() && off.getPocetZakazek()<30001) {
			SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
			String datumOd = DATE_FORMAT.format(off.getPlatnostOd());
			String datumDo = DATE_FORMAT.format(off.getPlatnostDo());
			zak = serviceZakazky.getZakazky(off.getSk30tSada().getSk30tMt().getMt(), off.getSk30tEvidencniBody().getKbodKod(), off.getSk30tEvidencniBody().getKbodWk(), off.getSk30tEvidencniBody()
					.getKbodEvid(), datumOd, datumDo, off.getStornoZakazky());
		}

		List<Vysledek> vys = serviceVysledek.getVysledek(off);
		ExportXls exp = new ExportXls();
		exp.vysledek(vys, zak, res);
	}

	@RequestMapping(value = "/offline/exportXlsSAgregaci/{idOfflineJob}")
	public void exportXlsSAgregaci(@PathVariable long idOfflineJob, Model model, HttpServletRequest req, HttpServletResponse res) throws IOException {
		log.debug("###\t exportXlsSAgregaci(" + idOfflineJob + ")");

		OfflineJob off = serviceOfflineJob.getOfflineJobOne(idOfflineJob);
		User user = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		List<OfflineJob> offAgregace = serviceOfflineJob.getOfflineJob(user, off.getAgregace());

		List<Long> seznamAgregaci = new ArrayList<Long>();

		for (OfflineJob offAgr : offAgregace) {
			seznamAgregaci.add(offAgr.getId());
		}
		log.debug("###\t  ... agregace cislo " + off.getAgregace() + " pro ID: " + seznamAgregaci);

		List<Vysledek> vysBezSumy = serviceVysledek.getVysledek(seznamAgregaci);
		List<VysledekExpSAgregaci> vysJenSumy = serviceVysledek.getVysledekSumy(seznamAgregaci);
		List<VysledekExpSAgregaci> kompletVysledekVcetneSumy = new ArrayList<VysledekExpSAgregaci>();
		String predchoziPR = "XXX";
		int cyklus = 1;

//		Iterator<VysledekExpSAgregaci> it = kompletVysledekVcetneSumy.iterator();
//		while(it.hasNext()) {
//			VysledekExpSAgregaci v = it.next();
//			if (! it.hasNext()) {
//			}
//		}
		
		Iterator<Vysledek> it = vysBezSumy.iterator();
		while(it.hasNext()) {
			Vysledek v = it.next();
			
			VysledekExpSAgregaci struk; 
			// prvni cyklus preskakuji, protoze je nutne nejdrive zapsat prvni radek
			// v pripade, ze aktualni PR neni stejna jako predchozi, tak zapisuji SUMArizacni radek
			if (cyklus >= 2 && !v.getSk30tPrPodminka().getPr().equals(predchoziPR)) {
				for (VysledekExpSAgregaci w : vysJenSumy) {
					if(predchoziPR.equals(w.getPr())){
						struk = new VysledekExpSAgregaci();
						struk.setMt("Suma");
						struk.setPr(predchoziPR);
						struk.setCetnost(w.getCetnost());
						struk.setPocZak(w.getPocZak());
						struk.setPozn("");
						struk.setPor(-1);
						struk.setPorCelk(cyklus);
						struk.setMbt("");
						
						kompletVysledekVcetneSumy.add(struk);
						cyklus++;
					}
				}
			}	
			
			struk = new VysledekExpSAgregaci();
			struk.setMt(v.getSk30tPrPodminka().getSk30tSada().getSk30tMt().getMt());
			struk.setPr(v.getSk30tPrPodminka().getPr());
			struk.setCetnost(v.getSoucet());
			struk.setPocZak(v.getSk30tOfflineJob().getPocetZakazek());
			struk.setPozn(v.getSk30tPrPodminka().getPoznamka());
			struk.setPor(v.getSk30tPrPodminka().getPoradi()==null ? -1 : v.getSk30tPrPodminka().getPoradi().longValue());
			struk.setPorCelk(cyklus);
			struk.setMbt((v.getSk30tPrPodminka().getErrMbt()==null || v.getSk30tPrPodminka().getErrMbt().startsWith("zzzKontrolovano")) ? "" : v.getSk30tPrPodminka().getErrMbt());
			kompletVysledekVcetneSumy.add(struk);
			
			predchoziPR = v.getSk30tPrPodminka().getPr();
			cyklus++;

			// pridani posledniho SUMArizacniho radku, toto se provede, kdyz uz v iteratoru "stojime" na poslednim zaznamu
			if (! it.hasNext()) {
				Boolean maPosledniPrCisloSumu = false;
				for (VysledekExpSAgregaci w : vysJenSumy) {
					if(predchoziPR.equals(w.getPr())){
						struk = new VysledekExpSAgregaci();
						struk.setMt("Suma");
						struk.setPr(predchoziPR);
						struk.setCetnost(w.getCetnost());
						struk.setPocZak(w.getPocZak());
						struk.setPozn("");
						struk.setPor(-1);
						struk.setPorCelk(cyklus);
						struk.setMbt("");
						kompletVysledekVcetneSumy.add(struk);
						maPosledniPrCisloSumu = true;
					}
				}

				
				if(!maPosledniPrCisloSumu){
					struk = new VysledekExpSAgregaci();
					struk.setMt("Suma");
					struk.setPr(predchoziPR);
					struk.setCetnost(v.getSoucet());
					struk.setPocZak(v.getSk30tOfflineJob().getPocetZakazek());
					struk.setPozn("");
					struk.setPor(-1);
					struk.setPorCelk(cyklus);
					struk.setMbt("");
					kompletVysledekVcetneSumy.add(struk);
				}
			}
		}
		
	
//		for (VysledekExpSAgregaci vv : kompletVysledekVcetneSumy) {
//			System.out.println(vv.getMt()+"\t"+vv.getPr()+"\t"+vv.getCetnost()+"\t"+vv.getPocZak()+"\t"+vv.getPor()+"\t"+vv.getPorCelk());
//		}

		ExportXls exp = new ExportXls();
		exp.vysledekSAgregaci(kompletVysledekVcetneSumy, offAgregace, res);
		
	}
	
	@RequestMapping(value = "/offline/smazatVysledek/{idOfflineJob}")
	public String smazatVysledek(@PathVariable final long idOfflineJob, Model model, HttpServletRequest req) {
		log.debug("###\t smazatVysledek (" + idOfflineJob + ")");

		serviceOfflineJob.removeOfflineJob(idOfflineJob);

		return "redirect:/srv/offline";
	}

}
