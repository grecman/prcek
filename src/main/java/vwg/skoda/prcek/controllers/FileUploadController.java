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
import vwg.skoda.prcek.services.MtService;
import vwg.skoda.prcek.services.MtSeznamService;
import vwg.skoda.prcek.services.PrMbtService;
import vwg.skoda.prcek.services.PrPodminkaService;
import vwg.skoda.prcek.services.ProtokolService;
import vwg.skoda.prcek.services.SadaService;
import vwg.skoda.prcek.services.UserService;

@Controller
@RequestMapping("/fileUpload")
public class FileUploadController {

	static Logger log = Logger.getLogger(EditaceController.class);

	@Autowired
	private UserService serviceUser;

	@Autowired
	private MtSeznamService serviceMtSeznam;

	@Autowired
	private MtService serviceMt;

	@Autowired
	private SadaService serviceSada;

	@Autowired
	private PrPodminkaService servicePrPodminka;

	@Autowired
	private ProtokolService serviceProtokol;

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
	// ZDROJ: http://blog.springsource.org/2012/05/06/spring-mvc-3-2-preview-introducing-servlet-3-async-support/

	// kontroler nacita soubor a zaroven jede cela metoda asynchrone
	@RequestMapping(value = "/saveFile/{vybranaSada}", method = RequestMethod.POST)
	public WebAsyncTask<String> saveFile(@PathVariable final long vybranaSada, final @ModelAttribute("uploadForm") FileUploadForm uploadForm,
			Model model, final HttpServletRequest req) throws IOException {
		log.debug("###\t saveFile(" + vybranaSada + "\t " + Thread.currentThread() + ")");

		final User u = serviceUser.getUser(req.getUserPrincipal().getName());
		final Sada s = serviceSada.getSadaOne(vybranaSada);

		Callable<String> c = new Callable<String>() {
			public String call() throws Exception {
				log.debug("###\t call(" + Thread.currentThread() + ")");

				// LISTy a FOR cyklus jsou tady kvuli tomu, ze je moznost importovat vice souboru najednou (coz ja v JSP zakazuji a pracuji jen s
				// prvnim) ... ale jde to :)
				List<MultipartFile> files = uploadForm.getFiles();
				List<String> fileNames = new ArrayList<String>();

				if (null != files && files.size() > 0) {
					for (MultipartFile multipartFile : files) {

						String fileName = multipartFile.getOriginalFilename();
						fileNames.add(fileName);

						LineNumberReader row = new LineNumberReader(new InputStreamReader(multipartFile.getInputStream()));
						String prpod;
						int pocetPr = 0;
						while ((prpod = row.readLine()) != null) {
							PrPodminka prp = new PrPodminka();
							// System.out.println("line: " + poradi + "\t" + prpod);

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
								//log.error("###\t Chyba pri obsahove kontrole PR podminky (import TXT): " + e);
								prp.setErrMbt(e.getMessage());
							}
							servicePrPodminka.addPrPodminka(prp);
						}
					}
				}
				return "redirect:/srv/editace/zobrazPr/" + u.getNetusername() + "/" + s.getSk30tMt().getMt() + "/" + s.getId();
			};
		};

		// 
		WebAsyncTask<String> w = new WebAsyncTask<String>(10000, c); //10000 = 10s
		w.onTimeout(new Callable<String>() {

			@Override
			public String call() throws Exception {
				log.trace("### Prekrocen povoleny casovy limit = skok na nejake jsp.");
				return "redirect:/srv/editace/zobrazPr/" + u.getNetusername() + "/" + s.getSk30tMt().getMt() + "/" + s.getId();
			}

		});
		return w;
	}
}
