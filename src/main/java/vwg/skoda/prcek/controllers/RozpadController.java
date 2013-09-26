package vwg.skoda.prcek.controllers;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.multipart.MultipartFile;

import cz.skoda.mbt.MBT;
import vwg.skoda.prcek.entities.Mt;
import vwg.skoda.prcek.entities.PrMbt;
import vwg.skoda.prcek.entities.PrPodminka;
import vwg.skoda.prcek.entities.Sada;
import vwg.skoda.prcek.entities.User;
import vwg.skoda.prcek.objects.FileUploadForm;
import vwg.skoda.prcek.services.EvidencniBodyService;
import vwg.skoda.prcek.services.MtService;
import vwg.skoda.prcek.services.MtSeznamService;
import vwg.skoda.prcek.services.PrMbtService;
import vwg.skoda.prcek.services.PrPodminkaService;
import vwg.skoda.prcek.services.ProtokolService;
import vwg.skoda.prcek.services.SadaService;
import vwg.skoda.prcek.services.UserService;

@Controller
public class RozpadController {

	static Logger log = Logger.getLogger(EditaceController.class);

	@Autowired
	private UserService serviceUser;

	@Autowired
	private PrPodminkaService servicePrPodminka;

	@Autowired
	private PrMbtService servicePrMbt;

	@Autowired
	private EvidencniBodyService serviceEvidencniBody;

	@RequestMapping(value = "/rozpad", method = RequestMethod.POST)
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
		WebAsyncTask<String> webAs = new WebAsyncTask<String>(10000, callAsyncThread); // 10000 = 10s
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

}
