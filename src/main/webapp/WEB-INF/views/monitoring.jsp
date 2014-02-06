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
	function testDbPrcekAjax() {
		var checkUrl = "/prcek/monitoring/testDbPrcekAjax.json";
		var datum = null;
		$.ajax({
			url : checkUrl,
			async : false, // default je true, pokud není false, tak následný if(result) proběhne dříve než se vrátí objekt z ajaxu !!!
			cache : false
		}).done(function(data) {

			//alert(data.prcekDate);
			$("#gre").append(data.prcekDate);

		});
	}

	$(document).ready(function() {
		$("#greTla").click(function() {
			testDbPrcekAjax();

		});
	});
</script>



</head>

<body>
	<div class="page">
		<div class="headerNazevStranky">
			<f:message key="monitoring" />
		</div>
		
		<c:set scope="request" var="actual" value="monitoring" />
		<jsp:include page="header.jsp" />

		<H3>Test databáze</H3>
		<TABLE>
			<col width="300px" />
			<col width="150px" />
			<col width="130px" />
			<col width="200px" />
			<col width="100px" />
			<THEAD>
				<TH>Databáze</TH>
				<TH>Schéma</TH>
				<TH>Test</TH>
				<TH title="Aktuální čas ze serveru">Stav</TH>
				<TH title="... v milisekundách">Latence</TH>
			</THEAD>
			<TBODY>
				<TR>
					<TD></TD>
					<TD class="rowOdd">PRCEK</TD>
					<TD align="center"></TD>
						<INPUT id="greTla" type="button" style="background: transparent url(${pageContext.servletContext.contextPath}/resources/ico/diagona/nasledujici.png) no-repeat center;" ></INPUT>
						<!--<a href="${pageContext.servletContext.contextPath}/srv/monitoring/testDbPrcek"><img	style="border: 0px; padding-top: 3px;" src="${pageContext.servletContext.contextPath}/resources/ico/diagona/nasledujici.png" /></a></TD>-->
						<!--<TD align="center"><B>${prcekDate}</B></TD> -->
						<TD align="center" id="gre"></TD>
						<TD align="center"><B>${prcekLatence}</B></TD>
					
				</TR>
				<TR>
					<TD align="center"><B>${db}</B></TD>
					<TD class="rowOdd">MBT_P</TD>
					<TD align="center"><a
						href="${pageContext.servletContext.contextPath}/srv/monitoring/testDbMbt"><img
							style="border: 0px; padding-top: 3px;"
							src="${pageContext.servletContext.contextPath}/resources/ico/diagona/nasledujici.png" /></a></TD>
					<TD align="center"><B>${mbtDate}</B></TD>
					<TD align="center"><B>${mbtLatence}</B></TD>
				</TR>
				<TR>
					<TD></TD>
					<TD class="rowOdd">KOMUNIKACE</TD>
					<TD align="center"><a
						href="${pageContext.servletContext.contextPath}/srv/monitoring/testDbKomunikace"><img
							style="border: 0px; padding-top: 3px;"
							src="${pageContext.servletContext.contextPath}/resources/ico/diagona/nasledujici.png" /></a></TD>
					<TD align="center"><B>${komunikaceDate}</B></TD>
					<TD align="center"><B>${komunikaceLatence}</B></TD>
				</TR>
			</TBODY>
		</TABLE>
		<BR /> <BR />
		<H3>Informace o uživateli</H3>
		<DIV style="padding-left: 20px; font-size: 14px;">
			${aktualUser.prijmeni} ${aktualUser.jmeno}
			(${aktualUser.netusername}) - ${aktualUser.oddeleni}<BR /> Role:
			${userRole} <BR />
		</DIV>
		<BR />
		<H3>Informace o serveru</H3>
		<DIV style="padding-left: 20px; font-size: 14px;">
			Server: ${server}<BR /> IP: ${ip} <BR /> Root aplikace:
			${pageContext.servletContext.contextPath}
		</DIV>

		<!-- 
		<script>
			document.write("<BR />Jméno prohlížeče: ");
			document.write(navigator.appName);
			document.write(navigator.userAgent); 

			document.write("<BR />Číslo verze: ");
			document.write(navigator.appVersion);

			document.write("<BR />Jednoduché číslo verze: ");
			document.write(parseInt(navigator.appVersion));

			document.write("<BR />Kódové jméno aplikace: ");
			document.write(navigator.appCodeName);
			alert(window.navigator.appName); 
		</script>
 -->

		<DIV style="height: 250px;">&#160;</DIV>
		<jsp:include page="footer.jsp" />
	</div>
</body>
	</html>
</jsp:root>