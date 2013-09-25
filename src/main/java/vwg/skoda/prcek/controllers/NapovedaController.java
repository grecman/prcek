package vwg.skoda.prcek.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import vwg.skoda.prcek.services.UserService;

@Controller
@RequestMapping("/napoveda")
public class NapovedaController {
	
	static Logger log = Logger.getLogger(NapovedaController.class);
	
	@Autowired
	private UserService serviceUser;
	
	@RequestMapping
	public String napovedaUvodniZobrazeni(Model model) {
		log.debug("###\t napovedaUvodniZobrazeni()");


	
		return "/napoveda";
	}

}
