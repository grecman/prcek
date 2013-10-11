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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import vwg.skoda.prcek.entities.Mt;
import vwg.skoda.prcek.entities.PrMbt;
import vwg.skoda.prcek.entities.PrPodminka;
import vwg.skoda.prcek.entities.Sada;
import vwg.skoda.prcek.entities.User;
import vwg.skoda.prcek.services.MtService;
import vwg.skoda.prcek.services.PrMbtService;
import vwg.skoda.prcek.services.PrPodminkaService;
import vwg.skoda.prcek.services.SadaService;
import vwg.skoda.prcek.services.UserService;
import cz.skoda.mbt.MBT;

@Controller
@RequestMapping("/fileUpload")
public class FileUploadController {

	static Logger log = Logger.getLogger(EditaceController.class);

	@Autowired
	private UserService serviceUser;

	@Autowired
	private MtService serviceMt;

	@Autowired
	private SadaService serviceSada;

	@Autowired
	private PrPodminkaService servicePrPodminka;

	@Autowired
	private PrMbtService servicePrMbt;

	@RequestMapping(value = "/importTxt/{vybranaSada}")
	public String importTxt(@PathVariable long vybranaSada, User netusername, Sada sada, Mt mt, Model model, HttpServletRequest req) {
		log.debug("###\t importTxt(" + vybranaSada + ")");

		Sada s = serviceSada.getSadaOne(vybranaSada);
		model.addAttribute("vybranaSada", s);

		return "file_upload_form";
	}

	// ZDROJ: http://viralpatel.net/blogs/spring-mvc-multiple-file-upload-example/
	// Ale ja to mam zjednodusene, protoze nabizim import vzdy pouze jednomu souboru a nepouzivam tridu FileUploadForm
	//    misto toho jako paramert moji metody volam primo MultipartHttpServletRequest 

	// tato metoda se nepousti !!!! pousti se ta asynchronni (nize)
	@RequestMapping(value = "/saveFileNoAsync/{vybranaSada}", method = RequestMethod.POST)
	public String saveFileNoAsync(@PathVariable long vybranaSada, Model model, MultipartHttpServletRequest req) throws IOException {
		log.debug("###\t saveFileNoAsync(" + vybranaSada + ",\t " + Thread.currentThread() + ")");

		final User u = serviceUser.getUser(req.getUserPrincipal().getName());
		final Sada s = serviceSada.getSadaOne(vybranaSada);

		List<MultipartFile> files = req.getFiles("filePrcek");
		List<String> fileNames = new ArrayList<String>();
		log.trace("#\t###\t Nacteny file: " + files);
		if (null != files && files.size() > 0) {
			for (MultipartFile multipartFile : files) {

				String fileName = multipartFile.getOriginalFilename();
				fileNames.add(fileName);

				LineNumberReader row = new LineNumberReader(new InputStreamReader(multipartFile.getInputStream()));
				String prpod;
				int pocetPr = 0;
				while ((prpod = row.readLine()) != null) {
					PrPodminka prp = new PrPodminka();
					// System.out.println("line: " + pocetPr + "\t" + prpod);

					prp.setPr(prpod.toUpperCase());
					prp.setPoradi(new BigDecimal(pocetPr));
					pocetPr = pocetPr + 5;
					prp.setUuser(u.getNetusername());
					prp.setUtime(new Date());
					prp.setSk30tSada(s);

					// MBT kontrola
					try {
						Mt mt = serviceMt.getMtOne(s.getSk30tMt().getId());
						List<PrMbt> pr = servicePrMbt.getPr(mt.getProdukt());
						MBT mbt = new MBT();
						mbt.setMBTSource(pr);
						mbt.getPRCondition(prpod.toUpperCase());
						prp.setErrMbt(null);
					} catch (Exception e) {
						// log.error("###\t Chyba pri obsahove kontrole PR podminky (import TXT): " + e);
						prp.setErrMbt(e.getMessage());
					}
					servicePrPodminka.addPrPodminka(prp);
				}
			}
		}
		return "redirect:/srv/editace/zobrazPr/" + u.getNetusername() + "/" + s.getSk30tMt().getMt() + "/" + s.getId();
	}

	// ZDROJ: http://viralpatel.net/blogs/spring-mvc-multiple-file-upload-example/
	// Ale ja to mam zjednodusene, protoze nabizim import vzdy pouze jednomu souboru a nepouzivam tridu FileUploadForm
	//    misto toho jako paramert moji metody volam primo MultipartHttpServletRequest 
	
	// kontroler nacita soubor a zaroven jede cela metoda asynchrone
	@RequestMapping(value = "/saveFileAsync/{vybranaSada}")
	public WebAsyncTask<String> saveFileAsync(@PathVariable final long vybranaSada, Model model, final MultipartHttpServletRequest req)
			throws IOException {
		log.debug("###ASYNC###\t saveFile(" + vybranaSada + ",\t " + Thread.currentThread() + ")");

		final User u = serviceUser.getUser(req.getUserPrincipal().getName());
		final Sada s = serviceSada.getSadaOne(vybranaSada);

		// V tomto bloku v implementovane metode call() se pousti asynchrone nejaky "dlouhy" proces (export/import/rozpad...)
		// return v teto metode se provede pouze pokud proces nepresahne nize uvedeny casovy limit
		Callable<String> c = new Callable<String>() {
			public String call() throws Exception {
				log.debug("###ASYNC###\t call(" + Thread.currentThread() + ")");

				List<MultipartFile> files = req.getFiles("filePrcek");
				List<String> fileNames = new ArrayList<String>();
				log.trace("#\t###\t Nacteny file: " + files);

				if (null != files && files.size() > 0) {
					for (MultipartFile multipartFile : files) {

						String fileName = multipartFile.getOriginalFilename();
						fileNames.add(fileName);

						LineNumberReader row = new LineNumberReader(new InputStreamReader(multipartFile.getInputStream()));
						String prpod;
						int pocetPr = 0;
						while ((prpod = row.readLine()) != null) {
							PrPodminka prp = new PrPodminka();
							//System.out.println("line: " + pocetPr + "\t" + prpod);

							prp.setPr(prpod.toUpperCase());
							prp.setPoradi(new BigDecimal(pocetPr));
							pocetPr = pocetPr + 5;
							prp.setUuser(u.getNetusername());
							prp.setUtime(new Date());
							prp.setSk30tSada(s);

							// MBT kontrola
							try {
								Mt mt = serviceMt.getMtOne(s.getSk30tMt().getId());
								List<PrMbt> pr = servicePrMbt.getPr(mt.getProdukt());
								MBT mbt = new MBT();
								mbt.setMBTSource(pr);
								mbt.getPRCondition(prpod.toUpperCase());
								prp.setErrMbt(null);
							} catch (Exception e) {
								// log.error("###\t Chyba pri obsahove kontrole PR podminky (import TXT): " + e);
								prp.setErrMbt(e.getMessage());
							}
							servicePrPodminka.addPrPodminka(prp);
						}
					}
				}
				return "redirect:/srv/editace/zobrazPr/" + u.getNetusername() + "/" + s.getSk30tMt().getMt() + "/" + s.getId();
			};
		};

		// nastavi casovy limit pro vyse uvedeny proces
		// tento WebAsyncTask jede vlastne soubezne s tim Callable a po uplynulem casovem limitu ji opusti a vrati "return" ktery je v tom
		// ".onTimeout"
		WebAsyncTask<String> w = new WebAsyncTask<String>(30000, c); // 1000 = 1s; 60000 = 1min

		// pokud je limit prekrocenm, tak implementovana metoda call() okamzite vrati
		w.onTimeout(new Callable<String>() {

			@Override
			public String call() throws Exception {
				log.debug("###ASYNC###\t call ... onTimeOut (" + Thread.currentThread() + ")");
				return "redirect:/srv/editace/zobrazPr/" + u.getNetusername() + "/" + s.getSk30tMt().getMt() + "/" + s.getId();
			}

		});
		return w;
	}

}
