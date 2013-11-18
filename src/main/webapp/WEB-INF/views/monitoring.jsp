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
			<f:message key="monitoring" />
		</div>
		<div class="pageHeader">
			<jsp:include page="header.jsp" />
		</div>

		<H3>Test databáze</H3>
		<TABLE>
			<col width="300px" />
			<col width="150px" />
			<col width="30px" />
			<col width="200px" />
			<THEAD>
				<TH>Databáze</TH>
				<TH>Schéma</TH>
				<TH>Test</TH>
				<TH title="Aktuální čas ze serveru">Stav</TH>
			</THEAD>
			<TBODY>
				<TR>
					<TD></TD>
					<TD class="rowOdd">PRCEK</TD>
					<TD></TD>
					<TD align="center"><B>${prcekDate}</B></TD>
				</TR>
				<TR>
					<TD align="center"><B>${db}</B></TD>
					<TD class="rowOdd">MBT_P</TD>
					<TD align="center"><a
						href="${pageContext.servletContext.contextPath}/srv/monitoring"><img
							style="border: 0px; padding-top: 3px;"
							src="${pageContext.servletContext.contextPath}/resources/ico/diagona/nasledujici.png" /></a></TD>
					<TD align="center"><B>${mbtDate}</B></TD>
				</TR>
				<TR>
					<TD></TD>
					<TD class="rowOdd">KOMUNIKACE</TD>
					<TD></TD>
					<TD align="center"><B>${komunikaceDate}</B></TD>
				</TR>
			</TBODY>
		</TABLE>
		<BR />
		<BR />
		<H3>Informace o uživateli</H3>
		<DIV style="padding-left: 20px; font-size: 14px;"> ${aktualUser.prijmeni}
			${aktualUser.jmeno} (${aktualUser.netusername}) -
			${aktualUser.oddeleni}<BR />
			Role: ${userRole} <BR />
		</DIV> <BR />
		<H3>Informace o serveru</H3>
		<DIV style="padding-left: 20px; font-size: 14px;">
			Server: ${server}<BR /> IP: ${ip}
		</DIV>

		<DIV style="height: 250px;">&#160;</DIV>
		<jsp:include page="footer.jsp" />
	</div>
</body>
	</html>
</jsp:root>