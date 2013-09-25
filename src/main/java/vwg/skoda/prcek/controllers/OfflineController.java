package vwg.skoda.prcek.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@RequestMapping(value = "/offline")
	public String offline(Model model, HttpServletRequest req) {
		log.debug("###\t offline");
		
		User u = serviceUser.getUser(req.getUserPrincipal().getName());
		
		List<OfflineJob> off = serviceOfflineJob.getOfflineJob(u);
		
		model.addAttribute("offList",off);	
		return "/offline";
	}
	
//	@RequestMapping
//	public String offline(@PathVariable String platnost, @PathVariable long idEvidBod, FormObj f, Model model) {
//		log.debug("###\t offline");
//
//		return "/offline";
//	}

}
