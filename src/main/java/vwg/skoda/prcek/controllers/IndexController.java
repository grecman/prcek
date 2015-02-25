package vwg.skoda.prcek.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vwg.skoda.prcek.entities.Protokol;
import vwg.skoda.prcek.entities.User;
import vwg.skoda.prcek.entities.UserZentaAdm;
import vwg.skoda.prcek.services.ProtokolService;
import vwg.skoda.prcek.services.UserService;
import vwg.skoda.prcek.services.UserZentaAdmService;

@Controller
@RequestMapping
public class IndexController {

	static Logger log = Logger.getLogger(IndexController.class);

	@Autowired
	private UserService serviceUser;

	@Autowired
	private UserZentaAdmService serviceUserZentaAdm;

	@Autowired
	private ProtokolService serviceProtokol;

	@RequestMapping(value = "/index.html", method = RequestMethod.GET)
	public String vypocetUvodniStranka(HttpServletRequest req, HttpSession session) {
		log.debug("###\t Index()");

		/*
		 * Tento controller existuje jen kvuli tomu, aby se aplikace spoustela s URL ../prcek/index.html (namapovano v web.xml) ktera se hned
		 * presmeruje na pozadovane JPS. Vsechny ostatni stranky se mapuji s prefixem /srv/* ... to je asi kvuli tomu, aby dispatcherController
		 * neprohledaval cely WebContent (ale nejsem si uplne jisty :)).
		 */

		// Ulozeni role do promenne, ktera je videt v cele sessione ... pak v kteremkoliv JSP mohu vypsat stringovou hodnotu jako ${userRole} nebo
		// session.getAttribute v controlleru

		String role = null;

		if (req.isUserInRole("USERS")) {
			if (role == null) {
				role = "USERS";
			}
		}

		if (req.isUserInRole("ADMINS")) {
			if (role == null) {
				role = "ADMINS";
			} else
				role = role + ",ADMINS";
		}

		if (req.isUserInRole("SERVICEDESK")) {
			if (role == null) {
				role = "SERVICEDESK";
			} else
				role = role + ",SERVICEDESK";
		}

		session.setAttribute("userRole", role);
		log.debug("###\t ... for user:" + req.getUserPrincipal().getName().toUpperCase() + " (" + role + ")");

		if (req.isUserInRole("SERVICEDESK")) {
			return "redirect:/srv/monitoring";
		}

		// zalozeni noveho uzivatele pokud jeste neexistuje v entite USER !!!
		if (!serviceUser.existUser(req.getUserPrincipal().getName().toUpperCase())) {
			log.debug("###\t Uzivatel " + req.getUserPrincipal().getName().toUpperCase() + " neexistuje v entite USER ... zakladam!!!");

			UserZentaAdm userZentaAdm = serviceUserZentaAdm.getUserZentaAdm(req.getUserPrincipal().getName().toUpperCase());
			if (userZentaAdm == null || userZentaAdm.getNetUsername().isEmpty()) {
				log.error("###\t Prihlaseny uzivatel " + req.getUserPrincipal().getName().toUpperCase() + " nenalezen v db ZentaAdm");
			}

			User newUser = new User();
			newUser.setNetusername(req.getUserPrincipal().getName().toUpperCase());
			newUser.setJmeno(userZentaAdm.getFirstName());
			newUser.setPrijmeni(userZentaAdm.getSurname());
			newUser.setOddeleni(userZentaAdm.getDepartment());
			newUser.setUuser(req.getUserPrincipal().getName().toUpperCase());
			newUser.setUtime(new Date());
			serviceUser.addUser(newUser);

			Protokol newProtokol = new Protokol();
			newProtokol.setNetusername(req.getUserPrincipal().getName().toUpperCase());
			newProtokol.setAction("Prvni prihlaseni");
			newProtokol.setInfo("Zalozeni prihlaseneho usera do entity USER   ###   " + req.getSession().getServletContext().getServerInfo());
			newProtokol.setTime(new Date());
			newProtokol.setSessionid(req.getSession().getId());
			serviceProtokol.addProtokol(newProtokol);
		}

		Protokol newProtokol = new Protokol();
		newProtokol.setNetusername(req.getUserPrincipal().getName().toUpperCase());
		newProtokol.setAction("Login");
		newProtokol.setInfo("Prihlaseni uzivatele do aplikace   ###   " + req.getSession().getServletContext().getServerInfo());
		newProtokol.setTime(new Date());
		newProtokol.setSessionid(req.getSession().getId());
		serviceProtokol.addProtokol(newProtokol);

		return "redirect:/srv/vypocet";
	}

}
