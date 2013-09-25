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
			<f:message key="prejmenovatSadu" />
		</div>
		<jsp:include page="header.jsp" />
		<BR />

		<form:form commandName="formObj" name="kontrola"
			action="${pageContext.servletContext.contextPath}/srv/editace/prejmenovatSaduTed/${vybranaSada.id}">

			<TABLE style="table-layout: auto; overflow: auto;">
				<col width="180px" />
				<col width="250px" />

				<TR>
					<TD><f:message>uzivatel</f:message></TD>
					<TD bgcolor="WHITE" style="color: gray;">${prihlasenyUzivatel}</TD>
				</TR>
				<TR height="15px;"></TR>
				<TR>
					<TD><f:message>modelovaTrida</f:message>&#160;(<f:message>produktM</f:message>)</TD>
					<TD bgcolor="WHITE" style="color: gray;">${vybranaMt.mt}</TD>
					<form:hidden path="mt" value="${vybranaMt.mt}"></form:hidden> 
				</TR>
				<TR height="15px;"></TR>
				<TR>
					<TD><f:message>nazevSady</f:message></TD>
					<TD bgcolor="WHITE"><form:input class="textovePole"
							path="sada"></form:input></TD>
				</TR>
				<TR height="15px;"></TR>
				<TR>
					<TD></TD>
					<TD><input
						onclick="return check('kontrola', {'sada' : /^[a-zA-Z0-9áäéëěíóöôúůüýčďňřšťžĺľĚŠČŘŽÝÁÍÉ]{1}[/,-.a-zA-Z0-9áäéëěíóöôúůüýčďňřšťžĺľĚŠČŘŽÝÁÍÉ\u0020]{0,35}$/});"
						type="submit" value="ok" /></TD>
					<!-- <td><button onclick="return check('loc', {'partNumber' : /^[a-zA-Z0-9\u0020]{1}[a-zA-Z0-9\u0020]{0,14}$/, 'partDescription' : /^[a-zA-Z0-9]{1}[/,-.a-zA-Z0-9\u0020]{0,35}$/,'quantity' : /^([,.0-9])+$/, 'uom' : /^[1-5]{1}$/});">Add</button></td>-->
				</TR>
				<TR height="300px;"></TR>
			</TABLE>
		</form:form>

		<div class="zonaTlacitek">
			<div class="tlacitka">

				<c:set var="zpetPopisek">
					<f:message>zpet</f:message>
				</c:set>
				<form:form commandName="sada"
					action="${pageContext.servletContext.contextPath}/srv/editace">
					<input type="submit" value="${zpetPopisek}" />
				</form:form>
			</div>
		</div>
		<jsp:include page="footer.jsp" />
	</div>
</body>
	</html>
</jsp:root>