<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:form="http://www.springframework.org/tags/form"
	xmlns:Spring="http://www.springframework.org/tags" xmlns:c="http://java.sun.com/jsp/jstl/core" xmlns:f="http://java.sun.com/jsp/jstl/fmt"
	version="2.0">
	<jsp:output omit-xml-declaration="false" doctype-root-element="html" doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
		doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" />
	<jsp:directive.page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="lib.jsp" />
<title>P R C E K</title>

<script type="text/javascript">
	$(document).ready(function() {

		//		// kontrola jeste pred tim, nez se provede submit
		// 		$('form[name=kontrola]').submit(function() {
		// 			if (testExistPrAjax()) {
		// 				alert("Nelze provést uložení, PR číslo je duplicitní!");
		// 				// z nejakeho divneho duvodu zde nefunguje ten rytern :(		
		// 				return false;
		// 			} 
		// 		});

		// kontrola PR duplicity v aktualni SADE pri kliknuti kamkoliv jinam ... 
		// Pozor, funguje jen kdyz uz "nekdo" kliknul do pole na editaci PR podminky, proto pouzivam ten prvni focus, abych byl uz primo v te editaci daneho pole. 
		// ... a cele to delam tak proto, pac nefunguje ten vyse uvedeny script!
		$("#InputPrPodminka").focus();
		$("#InputPrPodminka").focusout(function() {
			//alert(testExistPrAjax());
			if (testExistPrAjax()) {
				alert("PR číslo je duplicitní!");
				setTimeout(function() {
					$("#InputPrPodminka").focus();
				}, 1);
			}
		});
	});

	function testExistPrAjax() {
		var checkUrl = "/prcek/editace/testExistPrAjax/${vybranaSada.id}/"+ $("#InputPrPodminka").val() + ".json";
		var vysledek = false;
		$.ajax({
			url : checkUrl,
			async : false, // default je true, pokud není false, tak následný if(result) proběhne dříve než se vrátí objekt z ajaxu !!!
			cache : false
		}).done(function(data) {
			if (data) {
				vysledek = true;
			}
		});
		if (vysledek) {
			return true;
		}
		return false;
	}
</script>
</head>

<body>
	<div class="page">
		<div class="headerNazevStranky">
			<f:message key="novaPrPodminka" />
		</div>
		
		<c:set scope="request" var="actual" value="editace" />
		<jsp:include page="header.jsp" />
		
		<BR />

		<form:form  acceptCharset="UTF-8"  commandName="formObj" name="kontrola" action="${pageContext.servletContext.contextPath}/srv/editace/novaPrPodminkaTed/${vybranaSada.id}">

			<TABLE style="table-layout: auto; overflow: auto;">
				<col width="180px" />
				<col width="250px" />
				<col width="600px" />
				<TR>
					<TD><f:message>uzivatel</f:message></TD>
					<TD bgcolor="WHITE" style="color: gray;">${prihlasenyUzivatel}</TD>
					<TD></TD>
				</TR>
				<TR height="15px;"></TR>
				<TR>
					<TD><f:message>modelovaTrida</f:message>&#160;(<f:message>produktM</f:message>)</TD>
					<TD bgcolor="WHITE" style="color: gray;">${vybranaMt.mt}</TD>
					<form:hidden path="mt" value="${vybranaMt.mt}"></form:hidden>
					<TD></TD>
				</TR>
				<TR height="15px;"></TR>
				<TR>
					<TD><f:message>nazevSady</f:message></TD>
					<TD bgcolor="WHITE" style="color: gray;">${vybranaSada.nazev}</TD>
					<TD></TD>
				</TR>
				<TR height="15px;"></TR>
				<TR>
					<TD><f:message>poradi</f:message></TD>
					<TD bgcolor="WHITE"><form:input class="textovePole" path="poradi"></form:input></TD>
					<TD></TD>
				</TR>
				<TR height="15px;"></TR>
				<TR>
					<TD ><f:message>prPodminka</f:message></TD>
					<TD colspan="2" bgcolor="white"><form:input id="InputPrPodminka" class="textovePole850 uppercase" path="prPodminka" title="Vzor: +L0L+A8G+3FE/3FA"></form:input></TD>
				</TR>
				<TR height="15px;"></TR>
				<TR>
					<TD><f:message>poznamka</f:message></TD>
					<TD bgcolor="WHITE"><form:input class="textovePole" path="poznamka"></form:input></TD>
					<TD></TD>
				</TR>
				<TR height="15px;"></TR>
				<TR>
					<TD></TD>
					<TD><input
						onclick="return check('kontrola', {'poradi' : /^[0-9]{0,5}$/, 'prPodminka' : /^((([/+]{1})([A-Za-z0-9]{3}))+)$/, 'poznamka' : /^[,-.a-zA-Z0-9áäéëěíóöôúůüýčďňřšťžĺľĚŠČŘŽÝÁÍÉ\u0020]{0,39}$/});" 
						type="submit" value="ok" class="submit"/></TD>
					<TD></TD>
				</TR>
			</TABLE>
		</form:form>

		<DIV style="height: 250px;">&#160;</DIV>
		
		<div class="zonaTlacitek">
			<div class="tlacitka">

				<c:set var="zpetPopisek">
					<f:message>zpet</f:message>
				</c:set>
				<form:form commandName="sada" action="${pageContext.servletContext.contextPath}/srv/editace/zobrazPr/${vybranaMt.sk30tUser.netusername}/${vybranaMt.mt}/${vybranaSada.id}">
					<input type="submit" value="${zpetPopisek}" class="submit"/>
				</form:form>
			</div>
		</div>
		<jsp:include page="footer.jsp" />
	</div>
</body>
	</html>
</jsp:root>