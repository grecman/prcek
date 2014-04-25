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

<script type="text/javascript">
	$(document).ready(function() {

		$("#buttonNahratSoubor").click(function() {
			if ($("#inputFilePrcek").val().length == 0) {
				alert("Nevybrán žádný soubor k nahrání");
			} else {
				$("#dialogBusy").dialog("open");
				$("#dialogSpin").spin("mojeNastaveniSpinu");
				$("#formNahratSoubor").submit();
			}
		});

		$("#dialogBusy").dialog({
			autoOpen : false,
			resizable : false,
			position : "center",
			open : function() {
				$(".ui-dialog-titlebar-close").hide();
				$(".ui-dialog .ui-dialog-titlebar").hide();
				$(".ui-dialog-title").hide();
				$(".ui-widget-content").css({
					"border" : "0"
				});
				$(".ui-dialog").css({
					"padding" : "0",
					"background" : "transparent"
				});
			},
			height : 500,
			width : 500,
			modal : true
		});
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

		<div id="dialogBusy" style="background: transparent;">
			<p id="dialogSpin" style="height: 60%;"></p>
		</div>


		<DIV>
			Import PR podmínek pro sadu: <B>${vybranaSada.sk30tMt.mt} -
				${vybranaSada.nazev}</B>
		</DIV>
		<BR /> <BR />
		<form:form method="post" id="formNahratSoubor"
			action="${pageContext.servletContext.contextPath}/srv/fileUpload/saveFileAsync/${vybranaSada.id}"
			modelAttribute="uploadForm" enctype="multipart/form-data">

			<table id="fileTable">
				<TR>
					<TD style="font-weight: bold;">Krok 1. <input name="filePrcek"
						type="file" id="inputFilePrcek"
						style="background: none; width: 300px; color: red; padding-left: 0px;" /></TD>
					<TD></TD>
				</TR>
				<TR height="75px;">
					<TD style="font-weight: bold;">Krok 2. <input type="button"
						id="buttonNahratSoubor" value="Nahrát vybraný soubor" />
					</TD>
					<TD></TD>
				</TR>
				<TR height="100px">
					<TD></TD>
					<TD></TD>
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
		<DIV style="height: 120px;">&#160;</DIV>

		<!--  
		<div class="zonaTlacitek">
			<div class="tlacitka">

				<c:set var="zpetPopisek">
					<f:message>zpet</f:message>
				</c:set>
				<form:form
					action="${pageContext.servletContext.contextPath}/srv/editace/zobrazPr/${vybranaSada.sk30tMt.sk30tUser.netusername}/${vybranaSada.sk30tMt.mt}/${vybranaSada.id}">
					<input type="submit" value="${zpetPopisek}" class="submit" />
				</form:form>

			</div>
		</div>
		-->
		<jsp:include page="footer.jsp" />
	</div>
</body>
	</html>
</jsp:root>