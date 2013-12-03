package vwg.skoda.prcek.controllers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import vwg.skoda.prcek.entities.User;
import vwg.skoda.prcek.services.PrMbtService;
import vwg.skoda.prcek.services.UserService;
import vwg.skoda.prcek.services.ZakazkyService;

@Controller
@RequestMapping("/monitoring")
public class MonitoringController {

	static Logger log = Logger.getLogger(MonitoringController.class);

	@Autowired
	private UserService serviceUser;

	@Autowired
	private ZakazkyService serviceZakazky;

	@Autowired
	private PrMbtService servicePrMbt;

	@RequestMapping
	public String monitoringUvodniZobrazeni(Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws UnknownHostException {
		log.debug("###\t monitoring()");

		User aktualUser = serviceUser.getUser(req.getUserPrincipal().getName());
		model.addAttribute("aktualUser", aktualUser);

		model.addAttribute("server", InetAddress.getLocalHost().getCanonicalHostName());
		model.addAttribute("ip", InetAddress.getLocalHost().getHostAddress());

		model.addAttribute("db", serviceUser.getDbName());

		String role = null;

		if (req.isUserInRole("USERS")) {
			role = "USERS";
		} else if (req.isUserInRole("SERVICEDESK")) {
			role = "SERVICEDESK";
		} else if (req.isUserInRole("EXPORT")) {
			role = "EXPORT";
		}
		session.setAttribute("userRole", role);

		return "/monitoring";
	}

	@RequestMapping(value = "/testDbPrcek")
	public String testDbPrcek(Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws UnknownHostException {
		log.debug("###\t testDbPrcek()");

		long start = System.currentTimeMillis();
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String prcekDate = DATE_FORMAT.format(serviceUser.getDbTime());
		
		long end = System.currentTimeMillis();
		long diff = end - start;
		model.addAttribute("prcekLatence", diff);

		model.addAttribute("prcekDate", prcekDate);

		User aktualUser = serviceUser.getUser(req.getUserPrincipal().getName());
		model.addAttribute("aktualUser", aktualUser);

		model.addAttribute("server", InetAddress.getLocalHost().getCanonicalHostName());
		model.addAttribute("ip", InetAddress.getLocalHost().getHostAddress());

		model.addAttribute("db", serviceUser.getDbName());

		String role = null;

		if (req.isUserInRole("USERS")) {
			role = "USERS";
		} else if (req.isUserInRole("SERVICEDESK")) {
			role = "SERVICEDESK";
		} else if (req.isUserInRole("EXPORT")) {
			role = "EXPORT";
		}
		session.setAttribute("userRole", role);

		return "/monitoring";
	}

	@RequestMapping(value = "/testDbMbt")
	public String testDbMbt(Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws UnknownHostException {
		log.debug("###\t testDbMbt()");

		long start = System.currentTimeMillis();
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String mbtDate = DATE_FORMAT.format(servicePrMbt.getDbTime());
		
		long end = System.currentTimeMillis();
		long diff = end - start;
		model.addAttribute("mbtLatence", diff);
		
		model.addAttribute("mbtDate", mbtDate);

		User aktualUser = serviceUser.getUser(req.getUserPrincipal().getName());
		model.addAttribute("aktualUser", aktualUser);

		model.addAttribute("server", InetAddress.getLocalHost().getCanonicalHostName());
		model.addAttribute("ip", InetAddress.getLocalHost().getHostAddress());

		model.addAttribute("db", serviceUser.getDbName());

		String role = null;

		if (req.isUserInRole("USERS")) {
			role = "USERS";
		} else if (req.isUserInRole("SERVICEDESK")) {
			role = "SERVICEDESK";
		} else if (req.isUserInRole("EXPORT")) {
			role = "EXPORT";
		}
		session.setAttribute("userRole", role);

		return "/monitoring";
	}

	@RequestMapping(value = "/testDbKomunikace")
	public String testDbKomunikace(Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws UnknownHostException {
		log.debug("###\t testDbKomunikace()");

		long start = System.currentTimeMillis();

		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String komunikaceDate = DATE_FORMAT.format(serviceZakazky.getDbTime());

		long end = System.currentTimeMillis();
		long diff = end - start;
		model.addAttribute("komunikaceLatence", diff);
		model.addAttribute("komunikaceDate", komunikaceDate);

		User aktualUser = serviceUser.getUser(req.getUserPrincipal().getName());
		model.addAttribute("aktualUser", aktualUser);

		model.addAttribute("server", InetAddress.getLocalHost().getCanonicalHostName());
		model.addAttribute("ip", InetAddress.getLocalHost().getHostAddress());

		model.addAttribute("db", serviceUser.getDbName());

		String role = null;

		if (req.isUserInRole("USERS")) {
			role = "USERS";
		} else if (req.isUserInRole("SERVICEDESK")) {
			role = "SERVICEDESK";
		} else if (req.isUserInRole("EXPORT")) {
			role = "EXPORT";
		}
		session.setAttribute("userRole", role);

		return "/monitoring";
	}

}
