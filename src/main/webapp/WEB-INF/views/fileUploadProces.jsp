<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:form="http://www.springframework.org/tags/form"
	xmlns:Spring="http://www.springframework.org/tags"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:f="http://java.sun.com/jsp/jstl/fmt" version="2.0">
	<jsp:output omit-xml-declaration="false" doctype-root-element="html"
		doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
		doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" />
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="lib.jsp" />
<title>P R C E K</title>
<!-- Zajisteni, aby se stranka automaticky obnovila (redirect) po urcitem casovem limitu. Lze to delat pres meta nebo jquery -->
<!-- <meta http-equiv="refresh" content="5"></meta> -->
<!-- <meta http-equiv="refresh" content="5;url=${pageContext.servletContext.contextPath}/srv/offline"></meta>  -->

<!-- ZDROJ: http://jquerybyexample.blogspot.com/2013/06/jquery-redirect-page-after-few-seconds.html  -->
<script type="text/javascript">
	$(document).ready(function() {
		window.setInterval(function() {
			var iTimeRemaining = $("#spnSeconds").html();
			iTimeRemaining = eval(iTimeRemaining);
			if (iTimeRemaining == 0) {
				//window.location.href = "${pageContext.servletContext.contextPath}/srv/fileUpload/fileUploadProces/"+${vybranaSada.id};
				window.location.href = "./"+${vybranaSada.id};
			} else {
				$("#spnSeconds").html(iTimeRemaining - 1);
			}
		}, 1000);
	});
</script>
</head>

<body>
	<div class="page">
		<div class="headerNazevStranky">
			<f:message key="importTxt" />
		</div>

		<c:set scope="request" var="actual" value="editace" />
		<jsp:include page="header.jsp" />

		<BR />
		<H2>Nahráno ${prCount} / ${vybranaSada.pocet+vybranaSada.rozpracovano}</H2>
		<BR />
		<DIV style="color: gray; font-size: smaller; width: 1050px;">
			POZOR: tato akce může trvat i několik minut! Záleží na velikosti
			sady. Buď počkáte na konec importu PR podmínek přímo na této
			obrazovce, nebo máte možnost se vrátí zpět na detail své sady pomocí
			tlačítka "Zpět", to znamená na obrazovku "Seznam PR podmínek sady" a import
			bude dobíhat na pozadí.Pomocí tlačítka "Aktualizovat"
			zjistíte počet nahraných PR podmínek v sadě. Dokud nebudou
			importovány všechny PR podmínky ze souboru, nebude možné sadu použít
			pro výpočet.
		</DIV>
		<BR /> 
		<DIV>
			<img
				src="${pageContext.servletContext.contextPath}/resources/images/import_pr_pocet.jpg" />
		</DIV>
		<p align="right" style="color: gray; font-size: xx-small;">
			Další refresh za: <span id="spnSeconds">4</span> sekund.
		</p>

		<div class="zonaTlacitek">
			<div class="tlacitka">

				<c:set var="zpetPopisek">
					<f:message>zpet</f:message>
				</c:set>
				<form:form commandName="sada"
					action="${pageContext.servletContext.contextPath}/srv/editace/zobrazPr/${vybranaSada.sk30tMt.sk30tUser.netusername}/${vybranaSada.sk30tMt.mt}/${vybranaSada.id}">
					<input type="submit" value="${zpetPopisek}" class="submit" />
				</form:form>
			</div>
		</div>

		<jsp:include page="footer.jsp" />
	</div>
</body>
	</html>
</jsp:root>