package vwg.skoda.prcek.controllers;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;

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
import vwg.skoda.prcek.entities.Zakazky;
import vwg.skoda.prcek.services.OfflineJobService;
import vwg.skoda.prcek.services.PrPodminkaService;
import vwg.skoda.prcek.services.UserService;
import vwg.skoda.prcek.services.ZakazkyService;

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

	@RequestMapping(value = "/offline")
	public String offline(Model model, HttpServletRequest req) {
		log.debug("###\t offline()");

		User u = serviceUser.getUser(req.getUserPrincipal().getName());

		List<OfflineJob> off = serviceOfflineJob.getOfflineJob(u);
		model.addAttribute("offList", off);

		return "/offline";
	}

	@RequestMapping(value = "/offline/startJob")
	public String startJob(Model model, HttpServletRequest req) {
		log.debug("###\t startJob ()");
		User u = serviceUser.getUser(req.getUserPrincipal().getName());

		List<OfflineJob> off = serviceOfflineJob.getOfflineJobKeZpracovani(u);
		log.debug("###\t\t ... nacteno jobu ke zpracovani: "+off.size());		

		if(off.isEmpty() || off.size()<1){
			return "redirect:/srv/offline";
		} else {
			return "redirect:/srv/offline/rozpad/" + off.get(0).getId();
		}
	}

	@RequestMapping(value = "/offline/rozpad/{idOfflineJob}")
	public WebAsyncTask<String> rozpad(@PathVariable final long idOfflineJob, User u, Model model, final HttpServletRequest req) {
		log.debug("### ASYNC ###\t rozpad("+idOfflineJob+",\t" + Thread.currentThread() + ")");

		// V tomto bloku v implementovane metode call() se pousti asynchrone nejaky "dlouhy" proces (export/import/rozpad...)
		// return v teto metode se provede pouze pokud proces nepresahne nize uvedeny casovy limit
		Callable<String> callAsyncThread = new Callable<String>() {
			
			@Override
			public String call() throws Exception {
				log.debug("### ASYNC ###\t\t call(" + Thread.currentThread() + ")");
				
				OfflineJob off = serviceOfflineJob.getOfflineJobOne(idOfflineJob);
				off.setProces("v procesu");
				serviceOfflineJob.setOfflineJob(off);
				
				List<PrPodminka> pr = servicePrPodminka.getPrPodminka(off.getSk30tSada());
				
		        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
		        String datumOd = DATE_FORMAT.format(off.getPlatnostOd());
				String datumDo = DATE_FORMAT.format(off.getPlatnostDo());
				List<Zakazky> zak = serviceZakazky.getZakazky(off.getSk30tSada().getSk30tMt().getMt(), off.getSk30tEvidencniBody().getKbodKod(), off.getSk30tEvidencniBody().getKbodWk(), off.getSk30tEvidencniBody().getKbodEvid(), datumOd, datumDo, off.getStorno());
				
				//JobPRCondition roz = JobPRCondition();
				
				
				System.out.println(pr.size()+"\t"+zak.size()+"\t"+datumOd+"-"+datumDo);
				
				return "redirect:/srv/offline";
			}
		};

		// nastavi casovy limit pro vyse uvedeny proces 
		// tento WebAsyncTask jede vlastne soubezne s tim Callable a po uplynulem casovem limitu ji opusti a vrati "return" ktery je v tom ".onTimeout"
		WebAsyncTask<String> webAs = new WebAsyncTask<String>(20000, callAsyncThread); // 1000ms = 1s; 60000 = 1min;
		// pokud je limit prekrocenm, tak implementovana metoda call() okamzite vrati
		webAs.onTimeout(new Callable<String>() {

			@Override
			public String call() throws Exception {
				log.debug("###ASYNC###\t\t call ... onTimeOut (" + Thread.currentThread() + ")");
				
				return "redirect:/srv/offline";
			}

		});
		return webAs;
	}
}
