package vwg.skoda.prcek.outputs;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import vwg.skoda.prcek.entities.OfflineJob;
import vwg.skoda.prcek.entities.PrPodminka;
import vwg.skoda.prcek.entities.Vysledek;
import vwg.skoda.prcek.entities.Zakazky;
import vwg.skoda.prcek.objects.VysledekExpSAgregaci;

public class ExportXls {

	static Logger log = Logger.getLogger(ExportXls.class);

	NumberFormat floatToText = new DecimalFormat("##############0.00000", new DecimalFormatSymbols(new Locale("cs")));

	public void vysledek(List<Vysledek> vysledek, List<Zakazky> zakazky, HttpServletResponse res) throws IOException {

		log.debug("###\t vysledek(" + vysledek.size() + ", " + (zakazky == null ? null : zakazky.size()) + ")");
		//res.setContentType("application/ms-excel");
		res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		res.setHeader("Content-Disposition", "attachment; filename=\"PRCEK_"+vysledek.get(0).getSk30tPrPodminka().getSk30tSada().getSk30tMt().getMt() + "_" + vysledek.get(0).getSk30tPrPodminka().getSk30tSada().getNazev()+".xlsx\"");
		res.setHeader("Pragma", "public");
		res.setHeader("Cache-Control", "max-age=0");

		XSSFWorkbook wb = new XSSFWorkbook();

		XSSFCellStyle dateFormat = wb.createCellStyle();
		dateFormat.setDataFormat(wb.createDataFormat().getFormat("yyyy-MM-dd"));

		XSSFCellStyle dateFormatFull = wb.createCellStyle();
		dateFormatFull.setDataFormat(wb.createDataFormat().getFormat("yyyy-MM-dd, hh:mm:ss"));

		XSSFCellStyle floatFormat5 = wb.createCellStyle();
		floatFormat5.setDataFormat(wb.createDataFormat().getFormat("##############0.00000"));
		XSSFCellStyle floatFormat2 = wb.createCellStyle();
		floatFormat2.setDataFormat(wb.createDataFormat().getFormat("##############0.00"));

		XSSFFont boldFont = wb.createFont();
		boldFont.setBold(true);
		XSSFCellStyle boldFontStyle = wb.createCellStyle();
		boldFontStyle.setFont(boldFont);

		XSSFFont greenFont = wb.createFont();
		greenFont.setColor(IndexedColors.LIGHT_BLUE.getIndex());
		XSSFCellStyle greenFontStyle = wb.createCellStyle();
		greenFontStyle.setFont(greenFont);

		XSSFFont smallFont = wb.createFont();
		smallFont.setFontHeightInPoints((short) 7);
		XSSFCellStyle smallFontStyle = wb.createCellStyle();
		smallFontStyle.setFont(smallFont);
		
		Cell cell = null;
		Row row = null;

		XSSFCellStyle alignCenter = wb.createCellStyle();
		alignCenter.setAlignment(HorizontalAlignment.CENTER);

		String nazevSady = vysledek.get(0).getSk30tPrPodminka().getSk30tSada().getNazev();
		Sheet sheet = wb.createSheet(nazevSady);

		
		int radka = 0;
		int bunka = 0;
		radka = 0;
		bunka = 0;

		row = sheet.createRow(radka++);

		cell = row.createCell(bunka++);
		cell.setCellValue("Modelova trida");
		cell.setCellStyle(boldFontStyle);
		cell = row.createCell(bunka++);
		cell.setCellValue("PR podminka");
		cell.setCellStyle(boldFontStyle);
		cell = row.createCell(bunka++);
		cell.setCellValue("Cetnost");
		cell.setCellStyle(boldFontStyle);
		cell = row.createCell(bunka++);
		cell.setCellValue("Pocet zakazek".toString());
		cell.setCellStyle(boldFontStyle);
		cell = row.createCell(bunka++);
		cell.setCellValue("Poznamka");
		cell.setCellStyle(boldFontStyle);
		cell = row.createCell(bunka++);
		cell.setCellValue("Poradi"); 
		cell.setCellStyle(boldFontStyle);
		cell = row.createCell(bunka++);
		cell.setCellValue("MBT kontrola");
		cell.setCellStyle(boldFontStyle);
//		cell = row.createCell(bunka++);
//		cell.setCellValue(new String("Pořadí".getBytes(), "UTF-8"));
//		cell = row.createCell(bunka++);
//		cell.setCellValue(new String("Pořadí".getBytes(), "Cp1250")); 
//		cell = row.createCell(bunka++);
//		cell.setCellValue(new String("Pořadí".getBytes(), "ASCII")); 
//		cell = row.createCell(bunka++);
//		cell.setCellValue(new String("Pořadí".getBytes(), "ISO646-US")); 
//		cell = row.createCell(bunka++);
//		cell.setCellValue(new String("Pořadí".getBytes(), "ISO_8859-2")); 		
//		cell = row.createCell(bunka++);
//		cell.setCellValue(new String("Pořadí".getBytes(), "ISO_8859_1")); 
//		cell = row.createCell(bunka++);
//		cell.setCellValue(new String("Pořadí".getBytes(), "UTF_16")); 
//		cell = row.createCell(bunka++);
//		cell.setCellValue(new String("Pořadí".getBytes(), "UTF_16LE")); 
//		cell = row.createCell(bunka++);
//		cell.setCellValue(new String("Pořadí".getBytes(), "UTF_16BE")); 

		for (Vysledek v : vysledek) {
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue(v.getSk30tPrPodminka().getSk30tSada().getSk30tMt().getMt());
			row.createCell(bunka++).setCellValue(v.getSk30tPrPodminka().getPr());
			row.createCell(bunka++).setCellValue(v.getSoucet().doubleValue());
			row.createCell(bunka++).setCellValue(v.getSk30tOfflineJob().getPocetZakazek().doubleValue());
			row.createCell(bunka++).setCellValue(v.getSk30tPrPodminka().getPoznamka());
			if (v.getSk30tPrPodminka().getPoradi() == null) {
				row.createCell(bunka++).setCellValue("");
			} else {
				row.createCell(bunka++).setCellValue(v.getSk30tPrPodminka().getPoradi().doubleValue());
			}
			if (v.getSk30tPrPodminka().getErrMbt() == null) {
				row.createCell(bunka++).setCellValue("Neproverena PR podminka");
			} else {
				row.createCell(bunka++).setCellValue(v.getSk30tPrPodminka().getErrMbt().startsWith("zzzKontrolovano") ? "" : v.getSk30tPrPodminka().getErrMbt());
			}
			
		}

		// popis exportu
		sheet = wb.createSheet("Popis exportu");

		radka = 0;
		bunka = 0;

		row = sheet.createRow(radka++);
		cell = row.createCell(bunka++);
		cell.setCellValue("Uzivatel:");
		cell.setCellStyle(boldFontStyle);
		row.createCell(bunka++).setCellValue(
				vysledek.get(0).getSk30tPrPodminka().getSk30tSada().getSk30tMt().getSk30tUser().getNetusername() + " - "
						+ vysledek.get(0).getSk30tPrPodminka().getSk30tSada().getSk30tMt().getSk30tUser().getPrijmeni() + " "
						+ vysledek.get(0).getSk30tPrPodminka().getSk30tSada().getSk30tMt().getSk30tUser().getJmeno() + ", "
						+ vysledek.get(0).getSk30tPrPodminka().getSk30tSada().getSk30tMt().getSk30tUser().getOddeleni());

		bunka = 0;
		row = sheet.createRow(radka++);
		cell = row.createCell(bunka++);
		cell.setCellValue("Datum:");
		cell.setCellStyle(boldFontStyle);
		cell = row.createCell(bunka++);
		cell.setCellValue(new Date());
		cell.setCellStyle(dateFormatFull);

		row = sheet.createRow(radka++);

		bunka = 0;
		row = sheet.createRow(radka++);
		cell = row.createCell(bunka++);
		cell.setCellValue("Zavod:");
		cell.setCellStyle(boldFontStyle);
		row.createCell(bunka++).setCellValue(vysledek.get(0).getSk30tOfflineJob().getSk30tEvidencniBody().getKbodWk());

		bunka = 0;
		row = sheet.createRow(radka++);
		cell = row.createCell(bunka++);
		cell.setCellValue("Kontrolni bod:");
		cell.setCellStyle(boldFontStyle);
		row.createCell(bunka++).setCellValue(
				vysledek.get(0).getSk30tOfflineJob().getSk30tEvidencniBody().getKbodKod() + " / " + vysledek.get(0).getSk30tOfflineJob().getSk30tEvidencniBody().getKbodEvid() + " / "
						+ vysledek.get(0).getSk30tOfflineJob().getSk30tEvidencniBody().getFisNaze().trim());

		bunka = 0;
		row = sheet.createRow(radka++);
		cell = row.createCell(bunka++);
		cell.setCellValue("Obdobi od:");
		cell.setCellStyle(boldFontStyle);
		cell = row.createCell(bunka++);
		cell.setCellValue(vysledek.get(0).getSk30tOfflineJob().getPlatnostOd());
		cell.setCellStyle(dateFormat);

		bunka = 0;
		row = sheet.createRow(radka++);
		cell = row.createCell(bunka++);
		cell.setCellValue("Obdobi do:");
		cell.setCellStyle(boldFontStyle);
		cell = row.createCell(bunka++);
		cell.setCellValue(vysledek.get(0).getSk30tOfflineJob().getPlatnostDo());
		cell.setCellStyle(dateFormat);

		bunka = 0;
		row = sheet.createRow(radka++);
		cell = row.createCell(bunka++);
		cell.setCellValue("Sada:");
		cell.setCellStyle(boldFontStyle);
		row.createCell(bunka++).setCellValue(vysledek.get(0).getSk30tPrPodminka().getSk30tSada().getSk30tMt().getMt() + " - " + vysledek.get(0).getSk30tPrPodminka().getSk30tSada().getNazev());

		bunka = 0;
		row = sheet.createRow(radka++);
		cell = row.createCell(bunka++);
		cell.setCellValue("Agregace:");
		cell.setCellStyle(boldFontStyle);
		if (vysledek.get(0).getSk30tOfflineJob().getAgregace() == null) {
			row.createCell(bunka++).setCellValue("neni");
		} else {
			row.createCell(bunka++).setCellValue(vysledek.get(0).getSk30tOfflineJob().getAgregace());
		}

		bunka = 0;
		row = sheet.createRow(radka++);
		cell = row.createCell(bunka++);
		cell.setCellValue("Setrideno:");
		cell.setCellStyle(boldFontStyle);
		row.createCell(bunka++).setCellValue(vysledek.get(0).getSk30tOfflineJob().getVystupRazeni());

		// export zakazek
		if (zakazky != null && zakazky.size() > 0) {

			sheet = wb.createSheet("Zakazky");
			radka = 0;

			bunka = 0;
			row = sheet.createRow(radka++);
			cell = row.createCell(bunka++);
			cell.setCellValue("Uzivatel:");
			cell.setCellStyle(boldFontStyle);
			row.createCell(bunka++).setCellValue(
					vysledek.get(0).getSk30tPrPodminka().getSk30tSada().getSk30tMt().getSk30tUser().getNetusername() + " - "
							+ vysledek.get(0).getSk30tPrPodminka().getSk30tSada().getSk30tMt().getSk30tUser().getPrijmeni() + " "
							+ vysledek.get(0).getSk30tPrPodminka().getSk30tSada().getSk30tMt().getSk30tUser().getJmeno() + ", "
							+ vysledek.get(0).getSk30tPrPodminka().getSk30tSada().getSk30tMt().getSk30tUser().getOddeleni());

			bunka = 0;
			row = sheet.createRow(radka++);
			cell = row.createCell(bunka++);
			cell.setCellValue("Datum:");
			cell.setCellStyle(boldFontStyle);
			cell = row.createCell(bunka++);
			cell.setCellValue(new Date());
			cell.setCellStyle(dateFormatFull);

			row = sheet.createRow(radka++);

			bunka = 0;
			row = sheet.createRow(radka++);
			cell = row.createCell(bunka++);
			cell.setCellValue("Zavod:");
			cell.setCellStyle(boldFontStyle);
			row.createCell(bunka++).setCellValue(vysledek.get(0).getSk30tOfflineJob().getSk30tEvidencniBody().getKbodWk());

			bunka = 0;
			row = sheet.createRow(radka++);
			cell = row.createCell(bunka++);
			cell.setCellValue("Kontrolni bod:");
			cell.setCellStyle(boldFontStyle);
			row.createCell(bunka++).setCellValue(
					vysledek.get(0).getSk30tOfflineJob().getSk30tEvidencniBody().getKbodKod() + " / " + vysledek.get(0).getSk30tOfflineJob().getSk30tEvidencniBody().getKbodEvid() + " / "
							+ vysledek.get(0).getSk30tOfflineJob().getSk30tEvidencniBody().getFisNaze().trim());

			bunka = 0;
			row = sheet.createRow(radka++);
			cell = row.createCell(bunka++);
			cell.setCellValue("Obdobi od:");
			cell.setCellStyle(boldFontStyle);
			cell = row.createCell(bunka++);
			cell.setCellValue(vysledek.get(0).getSk30tOfflineJob().getPlatnostOd());
			cell.setCellStyle(dateFormat);

			bunka = 0;
			row = sheet.createRow(radka++);
			cell = row.createCell(bunka++);
			cell.setCellValue("Obdobi do:");
			cell.setCellStyle(boldFontStyle);
			cell = row.createCell(bunka++);
			cell.setCellValue(vysledek.get(0).getSk30tOfflineJob().getPlatnostDo());
			cell.setCellStyle(dateFormat);

			bunka = 0;
			row = sheet.createRow(radka++);
			cell = row.createCell(bunka++);
			cell.setCellValue("Sada:");
			cell.setCellStyle(boldFontStyle);
			row.createCell(bunka++).setCellValue(vysledek.get(0).getSk30tPrPodminka().getSk30tSada().getSk30tMt().getMt() + " - " + vysledek.get(0).getSk30tPrPodminka().getSk30tSada().getNazev());

			row = sheet.createRow(radka++);

			bunka = 0;
			row = sheet.createRow(radka++);
			cell = row.createCell(bunka++);
			cell.setCellValue("Modelovy klic");
			cell.setCellStyle(boldFontStyle);
			cell = row.createCell(bunka++);
			cell.setCellValue("KIFA");
			cell.setCellStyle(boldFontStyle);
			cell = row.createCell(bunka++);
			cell.setCellValue("KNR");
			cell.setCellStyle(boldFontStyle);
			cell = row.createCell(bunka++);
			cell.setCellValue("VIN");
			cell.setCellStyle(boldFontStyle);
			cell = row.createCell(bunka++);
			cell.setCellValue("Verze MK");
			cell.setCellStyle(boldFontStyle);
			cell = row.createCell(bunka++);
			cell.setCellValue("Mod.rok");
			cell.setCellStyle(boldFontStyle);
			cell = row.createCell(bunka++);
			cell.setCellValue("Barva");
			cell.setCellStyle(boldFontStyle);
			cell = row.createCell(bunka++);
			cell.setCellValue("Kod zeme");
			cell.setCellStyle(boldFontStyle);
			cell = row.createCell(bunka++);
			cell.setCellValue("SKD PR");
			cell.setCellStyle(boldFontStyle);
			cell = row.createCell(bunka++);
			cell.setCellValue("Pruchod KB");
			cell.setCellStyle(boldFontStyle);
			cell = row.createCell(bunka++);
			cell.setCellValue("Datum odvadeni");
			cell.setCellStyle(boldFontStyle);
			cell = row.createCell(bunka++);
			cell.setCellValue("Datum platnosti");
			cell.setCellStyle(boldFontStyle);
			cell = row.createCell(bunka++);
			cell.setCellValue("Storno veta");
			cell.setCellStyle(boldFontStyle);
			cell = row.createCell(bunka++);
			cell.setCellValue("PR popis");
			cell.setCellStyle(boldFontStyle);

			for (Zakazky z : zakazky) {
				bunka = 0;
				row = sheet.createRow(radka++);
				row.createCell(bunka++).setCellValue(z.getModelTr() + z.getModelKa() + z.getModelVy() + z.getModelMo() + z.getModelPv());
				row.createCell(bunka++).setCellValue(z.getKifa());
				row.createCell(bunka++).setCellValue(z.getKnrWk() + z.getKnrRok() + z.getKnrTtd() + z.getKnrNr());
				row.createCell(bunka++).setCellValue(z.getVin());
				row.createCell(bunka++).setCellValue(z.getModelVer());
				row.createCell(bunka++).setCellValue(z.getMrok());
				row.createCell(bunka++).setCellValue(z.getBarvawl() + z.getBarvaws() + z.getBarvawv());
				row.createCell(bunka++).setCellValue(z.getXcisl());
				row.createCell(bunka++).setCellValue(z.getSkdPr());

				cell = row.createCell(bunka++);
				cell.setCellValue(z.getKbodDat());
				cell.setCellStyle(dateFormatFull);

				row.createCell(bunka++).setCellValue(z.getDatSkut());
				row.createCell(bunka++).setCellValue(z.getRozpadBod());
				row.createCell(bunka++).setCellValue(z.getKbodOpak());
				cell = row.createCell(bunka++);
				cell.setCellValue(z.getPrpoz());
				cell.setCellStyle(smallFontStyle);
				// row.createCell(bunka++).setCellValue(z.getPrpoz());
			}

		}

		OutputStream os = res.getOutputStream();
		wb.write(os);
		os.close();

	}

	// ###############################################################################################################################
	// ###############################################################################################################################
	// ###############################################################################################################################

	public void vysledekSAgregaci(List<VysledekExpSAgregaci> vysledek, List<OfflineJob> offlineJobs, HttpServletResponse res) throws IOException {

		log.debug("###\t vysledekSAgregaci(" + vysledek.size() + ", " + offlineJobs.size() + ")");
		res.setContentType("application/ms-excel");
		res.setHeader("Content-Disposition", "attachment; filename=\"PRCEK_"+offlineJobs.get(0).getAgregace()+".xlsx\"");
		res.setHeader("Pragma", "public");
		res.setHeader("Cache-Control", "max-age=0");

		XSSFWorkbook wb = new XSSFWorkbook();

		// nadefinovani vsech moznych veci 
		
		XSSFFont boldFont = wb.createFont();
		boldFont.setBold(true);
		
		XSSFFont blueFont = wb.createFont();
		blueFont.setColor(IndexedColors.LIGHT_BLUE.getIndex());
		
		XSSFColor lightGrey = new XSSFColor(new java.awt.Color(242, 242, 242));
		
		XSSFFont smallFont = wb.createFont();
		smallFont.setFontHeightInPoints((short) 7);
		

		// aplikace vyse nadefinovanych "veci" na styly

		XSSFCellStyle boldFontStyle = wb.createCellStyle();
		boldFontStyle.setFont(boldFont);
		
		XSSFCellStyle blueFontStyle = wb.createCellStyle();
		blueFontStyle.setFont(blueFont);

		XSSFCellStyle aquaBackground = wb.createCellStyle();
		aquaBackground.setFillForegroundColor(IndexedColors.AQUA.getIndex());
		aquaBackground.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		
		XSSFCellStyle lightGreyBackground = wb.createCellStyle();
 		lightGreyBackground.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		lightGreyBackground.setFillForegroundColor(lightGrey);
		lightGreyBackground.setFont(boldFont);


		XSSFCellStyle smallFontStyle = wb.createCellStyle();
		smallFontStyle.setFont(smallFont);

		XSSFCellStyle alignCenter = wb.createCellStyle();
		alignCenter.setAlignment(HorizontalAlignment.CENTER);
		
		XSSFCellStyle dateFormat = wb.createCellStyle();
		dateFormat.setDataFormat(wb.createDataFormat().getFormat("yyyy-MM-dd"));

		XSSFCellStyle dateFormatFull = wb.createCellStyle();
		dateFormatFull.setDataFormat(wb.createDataFormat().getFormat("yyyy-MM-dd, hh:mm:ss"));

		XSSFCellStyle floatFormat5 = wb.createCellStyle();
		floatFormat5.setDataFormat(wb.createDataFormat().getFormat("##############0.00000"));
		XSSFCellStyle floatFormat2 = wb.createCellStyle();
		floatFormat2.setDataFormat(wb.createDataFormat().getFormat("##############0.00"));

		// ---------------------------------------------------------------------
		
		Sheet sheet = wb.createSheet("Vystup s agregaci");

		Cell cell = null;
		Row row = null;
		int radka = 0;
		int bunka = 0;

		radka = 0;
		bunka = 0;

		row = sheet.createRow(radka++);
		cell = row.createCell(bunka++);
		cell.setCellValue("Modelova trida");
		cell.setCellStyle(boldFontStyle);
		cell = row.createCell(bunka++);
		cell.setCellValue("PR podminka");
		cell.setCellStyle(boldFontStyle);
		cell = row.createCell(bunka++);
		cell.setCellValue("cetnost");
		cell.setCellStyle(boldFontStyle);
		cell = row.createCell(bunka++);
		cell.setCellValue("Pocet zakazek");
		cell.setCellStyle(boldFontStyle);
		cell = row.createCell(bunka++);
		cell.setCellValue("Poznamka");
		cell.setCellStyle(boldFontStyle);
		cell = row.createCell(bunka++);
		cell.setCellValue("Poradi");
		cell.setCellStyle(boldFontStyle);
		cell = row.createCell(bunka++);
		cell.setCellValue("Poradi celkove");
		cell.setCellStyle(boldFontStyle);
		cell = row.createCell(bunka++);
		cell.setCellValue("MBT kontrola");
		cell.setCellStyle(boldFontStyle);

		for (VysledekExpSAgregaci v : vysledek) {
			
			// IF jen kvuli stylum :(
			if(v.getMt().startsWith("Suma")){
				bunka = 0;
				row = sheet.createRow(radka++);
				cell = row.createCell(bunka++);
				cell.setCellValue("Suma");
				cell.setCellStyle(lightGreyBackground);

				cell = row.createCell(bunka++);
				cell.setCellValue(v.getPr());
				cell.setCellStyle(lightGreyBackground);

				cell = row.createCell(bunka++);
				cell.setCellValue(v.getCetnost());
				cell.setCellStyle(lightGreyBackground);

				cell = row.createCell(bunka++);
				cell.setCellValue(v.getPocZak());
				cell.setCellStyle(lightGreyBackground);
				
				cell = row.createCell(bunka++);
				cell.setCellValue("");
				cell.setCellStyle(lightGreyBackground);
				
				cell = row.createCell(bunka++);
				cell.setCellValue("");
				cell.setCellStyle(lightGreyBackground);
				
				cell = row.createCell(bunka++);
				cell.setCellValue(v.getPorCelk());
				cell.setCellStyle(lightGreyBackground);
				
				cell = row.createCell(bunka++);
				cell.setCellValue("");
				cell.setCellStyle(lightGreyBackground);
				
			} else {
				bunka = 0;
				row = sheet.createRow(radka++);
				row.createCell(bunka++).setCellValue(v.getMt());
				row.createCell(bunka++).setCellValue(v.getPr());
				row.createCell(bunka++).setCellValue(v.getCetnost());
				row.createCell(bunka++).setCellValue(v.getPocZak());
				row.createCell(bunka++).setCellValue(v.getPozn());
				if (v.getPor()<0) {
					row.createCell(bunka++).setCellValue("");
				} else {
					row.createCell(bunka++).setCellValue(v.getPor());
				}
				row.createCell(bunka++).setCellValue(v.getPorCelk());
				if (v.getMbt() == null){
					row.createCell(bunka++).setCellValue("Neproverena PR podminka");
				} else {
					row.createCell(bunka++).setCellValue(v.getMbt().startsWith("zzzKontrolovano") ? "" : v.getMbt());
				}				
			}
			
		}
		

		// popis exportu
		sheet = wb.createSheet("Popis exportu");

		radka = 0;
		bunka = 0;

		row = sheet.createRow(radka++);
		cell = row.createCell(bunka++);
		cell.setCellValue("Uzivatel:");
		cell.setCellStyle(boldFontStyle);
		row.createCell(bunka++).setCellValue(
				offlineJobs.get(0).getSk30tSada().getSk30tMt().getSk30tUser().getNetusername() + " - "
						+ offlineJobs.get(0).getSk30tSada().getSk30tMt().getSk30tUser().getPrijmeni() + " "
						+ offlineJobs.get(0).getSk30tSada().getSk30tMt().getSk30tUser().getJmeno() + ", "
						+ offlineJobs.get(0).getSk30tSada().getSk30tMt().getSk30tUser().getOddeleni());

		bunka = 0;
		row = sheet.createRow(radka++);
		cell = row.createCell(bunka++);
		cell.setCellValue("Datum:");
		cell.setCellStyle(boldFontStyle);
		cell = row.createCell(bunka++);
		cell.setCellValue(new Date());
		cell.setCellStyle(dateFormatFull);

		row = sheet.createRow(radka++);

		bunka = 0;
		row = sheet.createRow(radka++);
		cell = row.createCell(bunka++);
		cell.setCellValue("Zavod:");
		cell.setCellStyle(boldFontStyle);
		row.createCell(bunka++).setCellValue(offlineJobs.get(0).getSk30tEvidencniBody().getKbodWk());

		bunka = 0;
		row = sheet.createRow(radka++);
		cell = row.createCell(bunka++);
		cell.setCellValue("Kontrolni bod:");
		cell.setCellStyle(boldFontStyle);
		row.createCell(bunka++).setCellValue(
				offlineJobs.get(0).getSk30tEvidencniBody().getKbodKod() + " / " + offlineJobs.get(0).getSk30tEvidencniBody().getKbodEvid() + " / "
						+ offlineJobs.get(0).getSk30tEvidencniBody().getFisNaze().trim());

		bunka = 0;
		row = sheet.createRow(radka++);
		cell = row.createCell(bunka++);
		cell.setCellValue("Obdobi od:");
		cell.setCellStyle(boldFontStyle);
		cell = row.createCell(bunka++);
		cell.setCellValue(offlineJobs.get(0).getPlatnostOd());
		cell.setCellStyle(dateFormat);

		bunka = 0;
		row = sheet.createRow(radka++);
		cell = row.createCell(bunka++);
		cell.setCellValue("Obdobi do:");
		cell.setCellStyle(boldFontStyle);
		cell = row.createCell(bunka++);
		cell.setCellValue(offlineJobs.get(0).getPlatnostDo());
		cell.setCellStyle(dateFormat);

		for (OfflineJob j : offlineJobs) {
			bunka = 0;
			row = sheet.createRow(radka++);
			cell = row.createCell(bunka++);
			cell.setCellValue("Sada:");
			cell.setCellStyle(boldFontStyle);
			row.createCell(bunka++).setCellValue(j.getSk30tSada().getSk30tMt().getMt() + " - " + j.getSk30tSada().getNazev());
		}

		bunka = 0;
		row = sheet.createRow(radka++);
		cell = row.createCell(bunka++);
		cell.setCellValue("Agregace:");
		cell.setCellStyle(boldFontStyle);
		if (offlineJobs.get(0).getAgregace() == null) {
			row.createCell(bunka++).setCellValue("neni");
		} else {
			row.createCell(bunka++).setCellValue(offlineJobs.get(0).getAgregace());
		}

		OutputStream os = res.getOutputStream();
		wb.write(os);
		os.close();

	}

	// ###############################################################################################################################
	// ###############################################################################################################################
	// ###############################################################################################################################

	public void prPodminkySady(List<PrPodminka> prPodminky, HttpServletResponse res) throws IOException {

		log.debug("###\t prPodminkySady(" + prPodminky.get(0).getSk30tSada().getNazev() + ", " + prPodminky.size() + ")");
		res.setContentType("application/ms-excel");
		res.setHeader("Content-Disposition", "attachment; filename=\"PRCEK.xlsx\"");
		res.setHeader("Pragma", "public");
		res.setHeader("Cache-Control", "max-age=0");

		XSSFWorkbook wb = new XSSFWorkbook();

		XSSFCellStyle dateFormat = wb.createCellStyle();
		dateFormat.setDataFormat(wb.createDataFormat().getFormat("yyyy-MM-dd"));

		XSSFCellStyle dateFormatFull = wb.createCellStyle();
		dateFormatFull.setDataFormat(wb.createDataFormat().getFormat("yyyy-MM-dd, hh:mm:ss"));

		XSSFCellStyle floatFormat5 = wb.createCellStyle();
		floatFormat5.setDataFormat(wb.createDataFormat().getFormat("##############0.00000"));
		XSSFCellStyle floatFormat2 = wb.createCellStyle();
		floatFormat2.setDataFormat(wb.createDataFormat().getFormat("##############0.00"));

		XSSFFont boldFont = wb.createFont();
		boldFont.setBold(true);
		XSSFCellStyle boldFontStyle = wb.createCellStyle();
		boldFontStyle.setFont(boldFont);

		XSSFFont greenFont = wb.createFont();
		greenFont.setColor(IndexedColors.LIGHT_BLUE.getIndex());
		XSSFCellStyle greenFontStyle = wb.createCellStyle();
		greenFontStyle.setFont(greenFont);

		XSSFFont smallFont = wb.createFont();
		smallFont.setFontHeightInPoints((short) 7);
		XSSFCellStyle smallFontStyle = wb.createCellStyle();
		smallFontStyle.setFont(smallFont);

		XSSFCellStyle alignCenter = wb.createCellStyle();
		alignCenter.setAlignment(HorizontalAlignment.CENTER);

		Sheet sheet = wb.createSheet("PR");

		Cell cell = null;
		Row row = null;
		int radka = 0;
		int bunka = 0;

		radka = 0;
		bunka = 0;

		// row = sheet.createRow(radka++);
		// cell = row.createCell(bunka++);
		// cell.setCellValue("PR podminka");
		// cell.setCellStyle(boldFontStyle);

		for (PrPodminka p : prPodminky) {

			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue(p.getPr());

		}

		// popis exportu
		sheet = wb.createSheet("Popis exportu");

		radka = 0;
		bunka = 0;

		row = sheet.createRow(radka++);
		cell = row.createCell(bunka++);
		cell.setCellValue("Uzivatel:");
		cell.setCellStyle(boldFontStyle);
		row.createCell(bunka++).setCellValue(
				prPodminky.get(0).getSk30tSada().getSk30tMt().getSk30tUser().getNetusername() + " - " + prPodminky.get(0).getSk30tSada().getSk30tMt().getSk30tUser().getPrijmeni() + " "
						+ prPodminky.get(0).getSk30tSada().getSk30tMt().getSk30tUser().getJmeno() + ", " + prPodminky.get(0).getSk30tSada().getSk30tMt().getSk30tUser().getOddeleni());

		bunka = 0;
		row = sheet.createRow(radka++);
		cell = row.createCell(bunka++);
		cell.setCellValue("Datum:");
		cell.setCellStyle(boldFontStyle);
		cell = row.createCell(bunka++);
		cell.setCellValue(new Date());
		cell.setCellStyle(dateFormatFull);

		bunka = 0;
		row = sheet.createRow(radka++);
		cell = row.createCell(bunka++);
		cell.setCellValue("Modelova trida:");
		cell.setCellStyle(boldFontStyle);
		cell = row.createCell(bunka++);
		cell.setCellValue(prPodminky.get(0).getSk30tSada().getSk30tMt().getMt());

		bunka = 0;
		row = sheet.createRow(radka++);
		cell = row.createCell(bunka++);
		cell.setCellValue("Sada:");
		cell.setCellStyle(boldFontStyle);
		cell = row.createCell(bunka++);
		cell.setCellValue(prPodminky.get(0).getSk30tSada().getNazev());

		OutputStream os = res.getOutputStream();
		wb.write(os);
		os.close();

	}

	// ###################################################################################################

	// Kdyz bude float 0 nebo NULL, tak at je to String="" (prazdna bunka v xls)
	Object n0(Float f) {
		if (f == null || f == 0f)
			return "";
		return f;
	}

	Object n0(Double d) {
		if (d == null || d == 0f)
			return "";
		return d;
	}

	Integer n(Integer f) {
		return f == null ? 0 : f;
	}

	Float n(Float f) {
		return f == null ? 0f : f;
	}

	Long n(Long f) {
		return f == null ? 0 : f;
	}

	String n(String s) {
		return s == null ? "" : s;
	}

	public static void main(String[] args) throws IOException {

		XSSFWorkbook wb = new XSSFWorkbook();

		Sheet sheet = wb.createSheet("Sheet one");
		Row row = sheet.createRow(0);
		row.createCell(1).setCellValue("Value 2");
		row.createCell(2).setCellValue("Value 3");

		// file se vytvori do rootu aplikace !!! (jako je treba pom.xml)
		FileOutputStream outputStream = new FileOutputStream("vysledek.xls");
		wb.write(outputStream);
		outputStream.close();
	}


}
