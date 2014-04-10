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
				//window.location.href = "${pageContext.servletContext.contextPath}/srv/offline/startJob";
				window.location.href = "./offline/startJob";
			} else {
				$("#spnSeconds").html(iTimeRemaining - 1);
			}
		}, 1000);
	});
	
	function displayProgressBar() {
		var browser = "${header['User-Agent']}";
		if (browser.indexOf("MSIE") >= 0) {
			$("#progressBarIE")
					.append(
							'<img src="${pageContext.servletContext.contextPath}/resources/images/progress_bar_mix.gif" />');
			$("#progressBarIE").show();
		} else {
			$("#progressBarFireFox").show();
		}
	};	
	
</script>

</head>

<body>
	<div class="page">
		<div class="headerNazevStranky">
			<f:message key="prehledVysledkuZpracovani" />
		</div>

		<c:set scope="request" var="actual" value="offline" />
		<jsp:include page="header.jsp" />

		<BR />
		<DIV class="scroll" style="height: 500px; overflow: auto;">
			<TABLE id="tab1" width="100%" style="table-layout: fixed;">
				<col width="30px" />
				<col width="*" />
				<col width="45px" />
				<col width="55px" />
				<col width="100px" />
				<col width="175px" />
				<col width="135px" />
				<col width="135px" />
				<col width="40px" />
				<col width="30px" />
				<col width="60px" />
				<col width="30px" />
				<thead>
					<tr>
						<th>MT</th>
						<th>Název sady</th>
						<th>Počet PR</th>
						<th>Počet zakázek</th>
						<th>K.bod</th>
						<th>Platnost</th>
						<th
							title="Zpracování starší jak 3 měsíce bude automaticky smazáno">Čas
							spuštění</th>
						<th>Čas ukončení</th>
						<th colspan="2">Výsledek s agregací</th>
						<th>Výsledek</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="i" items="${offList}" varStatus="iterator">
						<tr style="height: 30px;"
							class="${ (iterator.index mod 2) == 0 ? 'rowOdd' : 'rowEven' }">
							<td align="center" title="id:${i.id}">${i.sk30tSada.sk30tMt.mt}</td>
							<td><a
								href="${pageContext.servletContext.contextPath}/srv/editace/zobrazPr/${i.sk30tSada.sk30tMt.sk30tUser.netusername}/${i.sk30tSada.sk30tMt.mt}/${i.sk30tSada.id}">
									<span style="color: #4BA82E; font-weight: bold;">${i.sk30tSada.nazev}</span>
							</a></td>
							<td align="right">${i.sk30tSada.pocet}</td>
							<c:choose>
								<c:when test="${i.stornoZakazky}">
									<c:set var="stornoVety">Včetně storno vět</c:set>
								</c:when>
								<c:otherwise>
									<c:set var="stornoVety">Bez storno vět</c:set>
								</c:otherwise>
							</c:choose>
							<td align="right" title="${stornoVety}">${i.pocetZakazek}</td>
							<td align="center">${i.sk30tEvidencniBody.kbodWk}-${i.sk30tEvidencniBody.kbodKod}-${i.sk30tEvidencniBody.kbodEvid}</td>
							<td><f:formatDate pattern="yyyy-MM-dd"
									value="${i.platnostOd}" /> - <f:formatDate
									pattern="yyyy-MM-dd" value="${i.platnostDo}" /></td>
							<td><f:formatDate pattern="yyyy-MM-dd HH:mm"
									value="${i.casSpusteni}" /></td>

							<td>
								<c:choose>
									<c:when test="${empty(i.casUkonceni)}">
										<SPAN style="color: red; height: 5px;"> ${i.proces}</SPAN>
									</c:when>
									<c:otherwise>
										<f:formatDate pattern="yyyy-MM-dd HH:mm"
											value="${i.casUkonceni}" />
									</c:otherwise>
								</c:choose>
							</td>

							<td align="center">${i.agregace}
								<SPAN id="progressBarFireFox" style="display: none; z-index: 100; position: absolute;">
									<img src="${pageContext.servletContext.contextPath}/resources/images/progress_bar_mix.gif" />
								</SPAN>
								<SPAN id="progressBarIE" style="display: none; z-index: 100; position: absolute;">&#160;</SPAN>									
							</td>
							<td align="center"><c:if
									test="${not(empty(i.casUkonceni)) and not(empty(i.agregace))}">
									<a  onclick="displayProgressBar();"
										href="${pageContext.servletContext.contextPath}/srv/offline/vysledekSAgregaci/${i.id}"><img
										style="border: 0px; padding-top: 3px;"
										src="${pageContext.servletContext.contextPath}/resources/ico/diagona/nasledujici.png" /></a>
								</c:if></td>
							<td align="center"><c:if test="${not(empty(i.casUkonceni))}">
									<a  onclick="displayProgressBar();"
										href="${pageContext.servletContext.contextPath}/srv/offline/vysledek/${i.id}"><img
										style="border: 0px; padding-top: 3px;"
										src="${pageContext.servletContext.contextPath}/resources/ico/diagona/nasledujici.png" /></a>
								</c:if>
							</td>
							<td align="center">
									<a onClick="return confirm('Opravdu smazat toto zpracování ???')"
									href="${pageContext.servletContext.contextPath}/srv/offline/smazatVysledek/${i.id}"><img
									title="Smazat" style="border: 0px;"
									src="${pageContext.servletContext.contextPath}/resources/ico/smazat.png" /></a>							
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</TABLE>
		</DIV>

		<p align="left" style="color: gray; font-size: xx-small;">
			Další výpočet bude automaticky spuštěn za: <span id="spnSeconds">59</span>
			sekund.
		</p>

		<c:if
			test="${(aktualUser.netusername=='DZC0ZEL') or (aktualUser.netusername=='DZC0GRP')}">
			<!-- PREDELAT NA role PRCEK.ADMINS -->
			<div class="zonaTlacitek">
				<div class="tlacitka">
					<form:form
						action="${pageContext.servletContext.contextPath}/srv/offline/startJob">
						<input type="submit" value="Ruční start" class="submit" />
					</form:form>
				</div>
			</div>
		</c:if>

		<jsp:include page="footer.jsp" />
	</div>
</body>
	</html>
</jsp:root>