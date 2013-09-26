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
<!-- <meta http-equiv="refresh" content="60"></meta> -->
<!-- <meta http-equiv="refresh" content="5;url=${pageContext.servletContext.contextPath}/srv/offline"></meta>  -->

<!-- ZDROJ: http://jquerybyexample.blogspot.com/2013/06/jquery-redirect-page-after-few-seconds.html  -->
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						window
								.setInterval(
										function() {
											var iTimeRemaining = $(
													"#spnSeconds").html();
											iTimeRemaining = eval(iTimeRemaining);
											if (iTimeRemaining == 0) {
												window.location.href = "${pageContext.servletContext.contextPath}/srv/offline";
											} else {
												$("#spnSeconds").html(
														iTimeRemaining - 1);
											}
										}, 1000);
					});
</script>

</head>

<body>
	<div class="page">
		<div class="headerNazevStranky">
			<f:message key="offlineZpracovani" />
		</div>

		<div class="pageHeader">
			<jsp:include page="header.jsp" />
		</div>

		<BR />
		<DIV class="scroll" style="height: 350px; overflow: auto;">
			<TABLE id="tab1" width="100%" style="table-layout: fixed;">
				<col width="30px" />
				<col width="*" />
				<col width="55px" />
				<col width="55px" />
				<col width="100px" />
				<col width="145px" />
				<col width="135px" />
				<col width="135px" />
				<col width="45px" />
				<thead>
					<tr>
						<th>MT</th>
						<th>Název sady</th>
						<th>Agregace</th>
						<th>Počet zakázek</th>
						<th>K.bod</th>
						<th>Platnost</th>
						<th>Čas spuštění</th>
						<th>Čas ukončení</th>
						<th>Export</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="i" items="${offList}" varStatus="iterator">
						<tr height="25px"
							class="${ (iterator.index mod 2) == 0 ? 'rowOdd' : 'rowEven' }">
							<td align="center">${i.sk30tSada.sk30tMt.mt}</td>
							<td>${i.sk30tSada.nazev}</td>
							<td align="center">${i.agregace}</td>
							<td align="right">${i.pocetZakazek}</td>
							<td align="center">${i.sk30tEvidencniBody.kbodWk}-${i.sk30tEvidencniBody.kbodKod}-${i.sk30tEvidencniBody.kbodEvid}</td>
							<td>${i.platnost}</td>
							<td><f:formatDate pattern="yyyy-MM-dd HH:mm"
									value="${i.casSpusteni}" /></td>
							<td><f:formatDate pattern="yyyy-MM-dd HH:mm"
									value="${i.casUkonceni}" /></td>
							<td align="center"><c:if test="${not(empty(i.casUkonceni))}">
									<img
										src="${pageContext.servletContext.contextPath}/resources/ico/diagona/nasledujici.png" />
								</c:if></td>
						</tr>
					</c:forEach>
				</tbody>
			</TABLE>
		</DIV>
		<BR />
		<p align="right" style="color: gray; font-size: xx-small;">
			Stránka bude automaticky obnovena za: <span id="spnSeconds">59</span>
			seconds.
		</p>

		<jsp:include page="footer.jsp" />
	</div>
</body>
	</html>
</jsp:root>