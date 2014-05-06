<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:form="http://www.springframework.org/tags/form"
	xmlns:Spring="http://www.springframework.org/tags"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:f="http://java.sun.com/jsp/jstl/fmt" version="2.0">
	<jsp:output omit-xml-declaration="false" doctype-root-element="html"
		doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
		doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" />
	<jsp:directive.page language="java"	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<jsp:include page="lib.jsp" />
<title>P R C E K</title>

<script type="text/javascript">
	$(document).ready(function() {
		
		$("#tlacitkoSave").click(function() {
			//alert($("#InputMT").val() +"-"+  $("#InputNazevSady").val());
			
			if(check('kontrola', {'sada' : /^[a-zA-Z0-9áäéëěíóöôúůüýčďňřšťžĺľĚŠČŘŽÝÁÍÉ]{1}[/,-.a-zA-Z0-9áäéëěíóöôúůüýčďňřšťžĺľĚŠČŘŽÝÁÍÉ\u0020]{0,35}$/})){
				//alert($("#InputMT").val() +"-"+  $("#InputNazevSady").val());
			
				if(testExistSadaAjax()){
					alert("Tato sada pro uvedeného uživatele a modelovou třídu již existuje!");	
				} else {
					$("#formSada").submit();
				}
			}
			
		});

		// kontrola duplicity SADY pri kliknuti kamkoliv jinam ... 
		// Pozor, funguje jen kdyz uz "nekdo" kliknul do pole na editaci nazvu sady, proto pouzivam ten prvni focus, abych byl uz primo v te editaci daneho pole. 
// 		$("#InputNazevSady").focus();
// 		$("#InputNazevSady").focusout(function() {
// 			alert("gre");
// 			if (testExistSadaAjax()) {
// 				alert("Sada je duplicitní!");
// 				setTimeout(function() {
// 					$("#InputNazevSady").focus();
// 				}, 1);
// 			}
// 		});
	});

	function testExistSadaAjax() {
		//alert("testExistSadaAjax() - "+ $("#InputMT").val() +"-"+  $("#InputNazevSady").val());
		var checkUrl = "/prcek/editace/testExistSadaAjax/"+ $("#InputMT").val()+"/"+ $("#InputNazevSady").val() + ".json";
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
	
	function check(f, v) {
		var fr = document.forms[f];
		var errs = "";
		for (n in v) {
			if (! fr[n].value.match(v[n])) {
				errs = errs + fr[n].name +  ': ' + fr[n].value + "\n";
			} ;
		}
		if (errs) {
			alert("Chyba syntaxe:\n" + errs);
			return false;
		}
		//fr.submit();
		return true;
	}
</script>


</head>

<body>
	<div class="page">
		<div class="headerNazevStranky">
			<f:message key="novaSada" />
		</div>
		
		<c:set scope="request" var="actual" value="editace" />
		<jsp:include page="header.jsp" />
		
		<BR />

		<form:form commandName="formObj" name="kontrola" id="formSada"
			action="${pageContext.servletContext.contextPath}/srv/editace/novaSadaTed">

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
					<TD><form:select path="mt">
							<c:forEach var="i" items="${seznamMt}">
								<form:option id="InputMT" value="${i.mt}">${i.mt}&#160;</form:option>
							</c:forEach>
						</form:select></TD>
				</TR>
				<TR height="15px;"></TR>
				<TR>
					<TD><f:message>nazevSady</f:message></TD>
					<TD bgcolor="WHITE"><form:input id="InputNazevSady" class="textovePole"
							path="sada"></form:input></TD>
				</TR>
				<TR height="15px;"></TR>
				<TR>
					<TD></TD>
					<TD><input type="button" value="ok" class="submit" id="tlacitkoSave"/></TD>
			<!-- 	onclick="return check('kontrola', {'sada' : /^[a-zA-Z0-9áäéëěíóöôúůüýčďňřšťžĺľĚŠČŘŽÝÁÍÉ]{1}[/,-.a-zA-Z0-9áäéëěíóöôúůüýčďňřšťžĺľĚŠČŘŽÝÁÍÉ\u0020]{0,35}$/});"  -->
				</TR>
				<TR height="300px;"></TR>
			</TABLE>
		</form:form>

		<div class="zonaTlacitek">
			<div class="tlacitka">

				<c:set var="zpetPopisek">
					<f:message>zpet</f:message>
				</c:set>
				<form:form 
					action="${pageContext.servletContext.contextPath}/srv/editace">
					<input type="submit" value="${zpetPopisek}" class="submit"/>
				</form:form>
			</div>
		</div>
		<jsp:include page="footer.jsp" />
	</div>
</body>
	</html>
</jsp:root>