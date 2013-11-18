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
			<f:message key="napoveda" />
		</div>
		<div class="pageHeader">
			<jsp:include page="header.jsp" />
		</div>

		<H3>Informace o aplikaci</H3>
		<DIV style="padding-left: 20px; font-size: 14px;">
			Aplikace se zabývá zjišťováním četností vyrobených vozů dle
			sledovaných kontrolních bodů s přesností na zadané PR-podmínky.<BR />
			<BR /> <B>Klíčový uživatel:</B> Zeman Libor (VSI)<BR /> <B>EO
				partner:</B> Grecman Petr (EOT)<BR />
		</DIV>
		<BR /> <BR />

		<DIV class="scroll" style="height: 400px; overflow: auto;">
			<TABLE id="tab1" width="100%" style="table-layout: fixed;">
				<col width="150px" />
				<col width="*" />
				<col width="25px" />
				<thead>
					<tr>
						<th>Téma</th>
						<th>Popis</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="i" items="${napovedaList}" varStatus="iterator">
						<tr
							class="${ (iterator.index mod 2) == 0 ? 'rowOdd' : 'rowEven' }">
							<td>${i.tema}</td>
							<td>${i.popis}</td>
							<c:if
								test="${(aktualUser.netusername=='DZC0ZEL') or (aktualUser.netusername=='DZC0GRP')}">
								<td align="center"><a onClick="return confirm('Really?')"
									href="${pageContext.servletContext.contextPath}/srv/napoveda/smazat/${i.id}"><img
										title="Smazat" style="border: 0px;"
										src="${pageContext.servletContext.contextPath}/resources/ico/smazat.png" /></a>
								</td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</TABLE>
		</DIV>
		<BR />
		<c:if
			test="${(aktualUser.netusername=='DZC0ZEL') or (aktualUser.netusername=='DZC0GRP')}">
			<form:form commandName="napoveda" name="kontrola"
				action="${pageContext.servletContext.contextPath}/srv/napoveda/nova">
				<TABLE id="tab1" width="100%" style="table-layout: fixed;">
					<col width="150px" />
					<col width="1px" />
					<col width="850px" />
					<col width="*" />
					<tbody>
						<TR>
							<TD bgcolor="WHITE"><form:input class="textovePole150"
									path="tema"></form:input></TD>
							<TD />
							<TD bgcolor="WHITE"><form:input class="textovePole850"
									path="popis"></form:input></TD>
							<TD>
								<input type="submit" value="Přidej" class="submit"/>
							<!-- <input	onclick="return check('kontrola', {'tema' : /^[a-zA-Z0-9áäéëěíóöôúůüýčďňřšťžĺľĚŠČŘŽÝÁÍÉ()!?,']{1}[/,-.a-zA-Z0-9áäéëěíóöôúůüýčďňřšťžĺľĚŠČŘŽÝÁÍÉ\u0020]{0,64}$/, 'popis' : /^[a-zA-Z0-9áäéëěíóöôúůüýčďňřšťžĺľĚŠČŘŽÝÁÍÉ]{1}[/,-.a-zA-Z0-9áäéëěíóöôúůüýčďňřšťžĺľĚŠČŘŽÝÁÍÉ\u0020]{0,2000}$/});"	type="submit" value="Přidej" class="submit"/>  -->
							</TD>
						</TR>
					</tbody>
				</TABLE>
			</form:form>
		</c:if>

		<jsp:include page="footer.jsp" />
	</div>
</body>
	</html>
</jsp:root>