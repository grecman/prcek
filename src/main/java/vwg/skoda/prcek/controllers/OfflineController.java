package vwg.skoda.prcek.controllers;

import java.util.List;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.WebAsyncTask;

import vwg.skoda.prcek.entities.OfflineJob;
import vwg.skoda.prcek.entities.User;
import vwg.skoda.prcek.services.OfflineJobService;
import vwg.skoda.prcek.services.UserService;

@Controller
public class OfflineController {
	
	static Logger log = Logger.getLogger(OfflineController.class);
	
	@Autowired
	private UserService serviceUser;
	
	@Autowired
	private OfflineJobService serviceOfflineJob;

	@RequestMapping(value = "/offline/rozpad", method = RequestMethod.POST)
	public WebAsyncTask<String> rozpad(Model model, final HttpServletRequest req) {
		log.debug("### ASYNC ###\t rozpad(" + Thread.currentThread() + ")");

		final User u = serviceUser.getUser(req.getUserPrincipal().getName());

		// V tomto bloku v implementovane metode call() se pousti asynchrone nejaky "dlouhy" proces (export/import/rozpad...)
		// return v teto metode se provede pouze pokud proces nepresahne nize uvedeny casovy limit
		Callable<String> callAsyncThread = new Callable<String>() {

			@Override
			public String call() throws Exception {
				log.debug("### ASYNC ###\t call(" + Thread.currentThread() + ")");
				
				return "redirect:/srv/offline";
			}
		};

		// nastavi casovy limit pro vyse uvedeny proces 
		// tento WebAsyncTask jede vlastne soubezne s tim Callable a po uplynulem casovem limitu ji opusti a vrati "return" ktery je v tom ".onTimeout"
		WebAsyncTask<String> webAs = new WebAsyncTask<String>(1000, callAsyncThread); // 1000ms = 10s; 60000 = 1min;
		// pokud je limit prekrocenm, tak implementovana metoda call() okamzite vrati
		webAs.onTimeout(new Callable<String>() {

			@Override
			public String call() throws Exception {
				log.debug("###ASYNC###\t call ... onTimeOut (" + Thread.currentThread() + ")");
				return "redirect:/srv/offline";
			}

		});
		return webAs;
	}
	
	
	@RequestMapping(value = "/offline")
	public String offline(Model model, HttpServletRequest req) {
		log.debug("###\t offline    (refresh po 60s, nastaveno v <meta> v offline.jsp ");
		
		User u = serviceUser.getUser(req.getUserPrincipal().getName());
		
		List<OfflineJob> off = serviceOfflineJob.getOfflineJob(u);
		
		model.addAttribute("offList",off);	
		return "/offline";
	}
	


}
