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
</head>

<body>
	<div class="page">
		<div class="headerNazevStranky">
			<f:message key="editacePrPodminky" />
		</div>
		<jsp:include page="header.jsp" />
		<BR />

		<form:form commandName="formObj" name="kontrola" action="${pageContext.servletContext.contextPath}/srv/editace/editovatPrTed/${vybranaPrPodminka.id}">

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
					<TD colspan="2" bgcolor="white"><form:input class="textovePole850" path="prPodminka" title="Vzor: +L0L+A8G+3FE/3FA"></form:input></TD>
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
				<TR height="100px;"></TR>
			</TABLE>
		</form:form>

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