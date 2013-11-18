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
			<f:message key="vysledekZpracovani" />
		</div>

		<div class="pageHeader">
			<jsp:include page="header.jsp" />
		</div>

		<BR />
		<DIV style="font-size: 14px; font-weight: bold;">Sady:</DIV>
		<TABLE>
			<col width="55px" />
			<col width="200px" />
			<c:forEach var="i" items="${idOfflineJobs}" varStatus="iterator">
				<TR>
					<TD>${i.sk30tSada.sk30tMt.mt}</TD>
					<TD>${i.sk30tSada.nazev}</TD>
				</TR>
			</c:forEach>
		</TABLE>

		<BR />

		<DIV class="scroll" style="height: 500px; overflow: auto;">
			<TABLE id="tab1" width="100%" style="table-layout: fixed;">
				<col width="55px" />
				<col width="200px" />
				<col width="*" />
				<col width="75px" />
				<col width="75px" />
				<col width="175px" />
				<col width="55px" />
				<thead>
					<tr>
						<th>MT</th>
						<th>Název sady</th>
						<th>PR</th>
						<th>Četnost PR</th>
						<th>Počet zakázek</th>
						<th>Poznámka</th>
						<th>Pořadí</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="i" items="${vysledek}" varStatus="iterator">
						<tr
							class="${ (iterator.index mod 2) == 0 ? 'rowOdd' : 'rowEven' }">
							<td align="center">${i.sk30tPrPodminka.sk30tSada.sk30tMt.mt}</td>
							<td>${i.sk30tPrPodminka.sk30tSada.nazev}</td>
							<td>${i.sk30tPrPodminka.pr}</td>
							<td align="right">${i.soucet}</td>
							<td align="right">${i.sk30tOfflineJob.pocetZakazek}</td>
							<td>${i.sk30tPrPodminka.poznamka}</td>
							<td align="right">${i.sk30tPrPodminka.poradi}</td>
						</tr>
					</c:forEach>
				</tbody>
			</TABLE>
		</DIV>

		<div class="zonaTlacitek">

			<div class="tlacitka">
				<c:if test="${not(empty(vysledek))}">
					<form:form
						action="${pageContext.servletContext.contextPath}/srv/offline/exportXlsSAgregaci/${idOfflineJob}">
						<input type="submit" value="Export do XLS"  class="submit"/>
					</form:form>
				</c:if>
			</div>

		</div>

		<jsp:include page="footer.jsp" />
	</div>
</body>
	</html>
</jsp:root>