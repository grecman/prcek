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
		$("#ButtonPrcekDate").click(function() {
			testDbPrcekAjax();
		});
	});

	$(document).ready(function() {
		$("#ButtonMbtDate").click(function() {
			testDbMbtAjax();
		});
	});

	$(document).ready(function() {
		$("#ButtonKomunikaceDate").click(function() {
			testDbKomunikaceAjax();
		});
	});

	function testDbPrcekAjax() {
		var checkUrl = "/prcek/monitoring/testDbPrcekAjax.json";
		var datum = null;
		$.ajax({
			url : checkUrl,
			async : false, // default je true, pokud není false, tak následný if(result) proběhne dříve než se vrátí objekt z ajaxu !!!
			cache : false
		}).done(function(data) {
			$("#datumPrcek").html(data.prcekDate);
			$("#latencePrcek").html(data.prcekLatence);
			//alert(data.prcekDate+ " - "+data.prcekLatence); 
		});
	}

	function testDbMbtAjax() {
		var checkUrl = "/prcek/monitoring/testDbMbtAjax.json";
		var datum = null;
		$.ajax({
			url : checkUrl,
			async : false, // default je true, pokud není false, tak následný if(result) proběhne dříve než se vrátí objekt z ajaxu !!!
			cache : false
		}).done(function(data) {
			$("#datumMbt").html(data.mbtDate);
			$("#latenceMbt").html(data.mbtLatence);
		});
	}

// 	function testDbKomunikaceAjax() {
// 		var checkUrl = "/prcek/monitoring/testDbKomunikaceAjax.json";
// 		var datum = null;
// 		$.ajax({
// 			url : checkUrl,
// 			async : false, // default je true, pokud není false, tak následný if(result) proběhne dříve než se vrátí objekt z ajaxu !!!
// 			cache : false
// 		}).done(function(data) {
// 			$("#datumKomunikace").html(data.komunikaceDate);
// 			$("#latenceKomunikace").html(data.komunikaceLatence);
// 		});
// 	}
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
			<col width="230px" />
			<col width="130px" />
			<col width="50px" />
			<col width="170px" />
			<col width="100px" />
			<THEAD>
				<TH>Databáze</TH>
				<TH>Schéma</TH>
				<TH title="AJAX!">Test</TH>
				<TH title="Aktuální čas ze serveru">Stav</TH>
				<TH>Latence (ms)</TH>
			</THEAD>
			<TBODY>
				<TR>
					<TD align="center"><B>${db}</B></TD>
					<TD class="rowOdd" title="Všechny uživatelské data">PRCEK</TD>
					<TD align="center"><INPUT id="ButtonPrcekDate" type="button"
						style="background: transparent url(${pageContext.servletContext.contextPath}/resources/ico/diagona/nasledujici.png) no-repeat center;"></INPUT></TD>
					<TD align="center" id="datumPrcek"></TD>
					<TD align="center" id="latencePrcek"></TD>
				</TR>
				<TR>
					<TD align="center"><B>${db}</B></TD>
					<TD class="rowOdd" title="Databáze PR podmínke (kontkola)">TIWH</TD>
					<TD align="center"><INPUT id="ButtonMbtDate" type="button"
						style="background: transparent url(${pageContext.servletContext.contextPath}/resources/ico/diagona/nasledujici.png) no-repeat center;"></INPUT></TD>
					<TD align="center" id="datumMbt"></TD>
					<TD align="center" id="latenceMbt"></TD>
				</TR>
<!-- 			<TR>
					<TD></TD>
					<TD class="rowOdd" title="Databáze zakázek">KOMUNIKACE</TD>
					<TD align="center"><INPUT id="ButtonKomunikaceDate"
						type="button"
						style="background: transparent url(${pageContext.servletContext.contextPath}/resources/ico/diagona/nasledujici.png) no-repeat center;"></INPUT></TD>
					<TD align="center" id="datumKomunikace"></TD>
					<TD align="center" id="latenceKomunikace"></TD>
				</TR>
 -->		</TBODY>
		</TABLE>
		<BR /><HR />
		<H3>Informace o uživateli</H3>
		<DIV style="padding-left: 20px; font-size: 14px;">
			Uživatelské jméno:<B>${userName}</B><BR />
			Uživatel: <B>${aktualUser.prijmeni} ${aktualUser.jmeno} - ${aktualUser.oddeleni}</B><BR /> 
			Role: <B>${userRole}</B>
		</DIV>
		<BR /><HR />
		<H3>Informace o serveru</H3>
		<DIV style="padding-left: 20px; font-size: 14px;">
			Server: <B>${server}</B><BR /> 
			IP: <B>${ip}</B><BR /> 
			Root aplikace: <B>${pageContext.servletContext.contextPath}</B>
		</DIV>
		<BR /><HR />
		<H3>Návštěvní kniha</H3>
		<DIV style="padding-left: 20px; font-size: 14px;">
			Vaše poslední přihlášení: <B>${lastUserLogin}</B><BR /> 
			Do aplikace jste se celkem přihlásil: <B>${userLogin}</B><BR /><BR /> 
			Aplikaci používá celkem: <B>${allUsers}</B> uživatelů. <BR /> 
			Počet všech přihlášení: <B>${allUserLogin}</B>
		</DIV>

<!-- 		 
		<script>
			document.write("<BR />Jméno prohlížeče: ");
			document.write(navigator.appName);
			
			document.write("<BR />User Agent: ");
			document.write(navigator.userAgent); 

			document.write("<BR />Číslo verze: ");
			document.write(navigator.appVersion);

			document.write("<BR />Jednoduché číslo verze: ");
			document.write(parseInt(navigator.appVersion));

			document.write("<BR />Kódové jméno aplikace: ");
			document.write(navigator.appCodeName);
		</script>
 --> 

		<DIV style="height: 50px;">&#160;</DIV>
		<jsp:include page="footer.jsp" />
	</div>
</body>
	</html>
</jsp:root>