package vwg.skoda.prcek.controllers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import vwg.skoda.prcek.entities.Protokol;
import vwg.skoda.prcek.entities.User;
import vwg.skoda.prcek.objects.AjaxClass.TestDbKomunikaceAjax;
import vwg.skoda.prcek.objects.AjaxClass.TestDbMbtAjax;
import vwg.skoda.prcek.objects.AjaxClass.TestDbPrcekAjax;
import vwg.skoda.prcek.objects.FormObj;
import vwg.skoda.prcek.services.PrMbtService;
import vwg.skoda.prcek.services.ProtokolService;
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

	@Autowired
	private ProtokolService serviceProtokol;

	@RequestMapping
	public String monitoringUvodniZobrazeni(FormObj formObj, Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t monitoring()");

		model.addAttribute("userName", req.getUserPrincipal().getName().toUpperCase());
		model.addAttribute("server", InetAddress.getLocalHost().getCanonicalHostName());
		model.addAttribute("ip", InetAddress.getLocalHost().getHostAddress());

		try {
			User aktualUser = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
			model.addAttribute("aktualUser", aktualUser);

			List<Protokol> userLogin = serviceProtokol.getUserLogin(req.getUserPrincipal().getName().toUpperCase());
			model.addAttribute("userLogin", userLogin.size());
			SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			// userLogin je seznam loginu daneho uzivatele serazeny dle casu prihlaseni DESC! 0 je ten nejaktualnejsi login a 1 je tedy ten druhy.
			model.addAttribute("userLogin", userLogin.size());
			
			if (userLogin.size() <= 1){
				// pripad, dky je uzivatel poprve v aplikace s hned si klikne na monitoring
				model.addAttribute("lastUserLogin", DATE_FORMAT.format(new Date()));
			} else {
				model.addAttribute("lastUserLogin", DATE_FORMAT.format(userLogin.get(1).getTime()));	
			}

			List<Protokol> allUserLogin = serviceProtokol.getAllLogin();
			model.addAttribute("allUserLogin", allUserLogin.size());
			
			Set<String> netusernames = new TreeSet<String>();
			for (Protokol p : allUserLogin) {
				netusernames.add(p.getNetusername());
			}
			model.addAttribute("allUsers", netusernames.size());

			model.addAttribute("db", serviceUser.getDbName());
		} catch (Exception e) {
			log.error("###\t Chyby pri ziskavani dat z databaze", e);
			model.addAttribute("db", "Databaze nezjistena");
		}

		return "/monitoring";
	}

	@ResponseBody
	@RequestMapping(value = "/testDbPrcekAjax")
	public TestDbPrcekAjax testDbPrcekAjax() {
		log.debug("###\t testDbPrcekAjax()");

		long start = System.currentTimeMillis();

		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String date = DATE_FORMAT.format(serviceUser.getDbTime());

		long end = System.currentTimeMillis();
		long diff = end - start;

		TestDbPrcekAjax gre = new TestDbPrcekAjax();
		gre.setPrcekDate(date);
		gre.setPrcekLatence(diff);
		log.debug("###\t\t ajax ...: " + gre.getPrcekDate() + " (" + gre.getPrcekLatence() + "ms).");

		return gre;
	}

	@ResponseBody
	@RequestMapping(value = "/testDbMbtAjax")
	public TestDbMbtAjax testDbMbtAjax(Model model) {
		log.debug("###\t testDbMbtAjax()");

		long start = System.currentTimeMillis();

		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String date = DATE_FORMAT.format(servicePrMbt.getDbTime());

		long end = System.currentTimeMillis();
		long diff = end - start;

		TestDbMbtAjax gre = new TestDbMbtAjax();
		gre.setMbtDate(date);
		gre.setMbtLatence(diff);
		log.debug("###\t\t ajax ...: " + gre.getMbtDate() + " (" + gre.getMbtLatence() + "ms).");

		return gre;
	}

	@ResponseBody
	@RequestMapping(value = "/testDbKomunikaceAjax")
	public TestDbKomunikaceAjax testDbKomunikaceAjax(Model model) {
		log.debug("###\t testDbKomunikaceAjax()");

		long start = System.currentTimeMillis();

		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String date = DATE_FORMAT.format(serviceZakazky.getDbTime());

		long end = System.currentTimeMillis();
		long diff = end - start;

		TestDbKomunikaceAjax gre = new TestDbKomunikaceAjax();
		gre.setKomunikaceDate(date);
		gre.setKomunikaceLatence(diff);
		log.debug("###\t\t ajax ...: " + gre.getKomunikaceDate() + " (" + gre.getKomunikaceLatence() + "ms).");

		return gre;
	}

}
