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
</head>

<body>
	<div class="page">
		<div class="headerNazevStranky">
			<f:message key="importTxt" />
		</div>
		<div class="pageHeader">
			<jsp:include page="header.jsp" />
		</div>
		<BR />
		<form:form method="post"
			action="${pageContext.servletContext.contextPath}/srv/fileUpload/saveFileAsync/${vybranaSada.id}"
			modelAttribute="uploadForm" enctype="multipart/form-data">

			<table id="fileTable">
				<TR>
					<TD><input name="filePrcek" type="file"
						style="background: none; width: 300px; color: red; padding-left: 0px;" /></TD>
					<TD><c:set var="nahratSouborPopisek">
							<f:message>nahratSoubor</f:message>
						</c:set> <input type="submit" value="${nahratSouborPopisek}" /></TD>
				</TR>
				<TR>
					<TD></TD>
					<TD width="50px;"><SPAN
						style="color: gray; font-size: smaller;">POZOR: tato akce
							může trvat i několik minut! Pokud se import nestihne do 90
							sekund, tak se aplikace vrátí zpět na obrazovku "Seznam PR
							podmínek sady" a import bude dobíhat na pozadí.</SPAN></TD>
				</TR>
				<TR height="100px">
					<TD />
					<TD />
				</TR>
				<TR>
					<TD>Očekánavý formát importovaného souboru:</TD>
					<TD style="padding-left: 100px;">Výsledek:</TD>
				</TR>
				<TR valign="top">
					<TD><img title="Vzor"
						src="${pageContext.servletContext.contextPath}/resources/images/import_pr_vzor.jpg" /></TD>
					<TD style="padding-left: 100px;"><img title="Výsledek"
						src="${pageContext.servletContext.contextPath}/resources/images/import_pr_vysledek.jpg" /></TD>
				</TR>


			</table>
		</form:form>
		<DIV style="height: 100px;">&#160;</DIV>

		<div class="zonaTlacitek">
			<div class="tlacitka">

				<c:set var="zpetPopisek">
					<f:message>zpet</f:message>
				</c:set>
				<form:form
					action="${pageContext.servletContext.contextPath}/srv/editace/zobrazPr/${vybranaSada.sk30tMt.sk30tUser.netusername}/${vybranaSada.sk30tMt.mt}/${vybranaSada.id}">
					<input type="submit" value="${zpetPopisek}" />
				</form:form>

			</div>
		</div>
		<jsp:include page="footer.jsp" />
	</div>
</body>
	</html>
</jsp:root>