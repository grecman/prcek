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
					$("#dialogBusy").dialog("open");
					$("#dialogSpin").spin("mojeNastaveniSpinu");
					$("#formSada").submit();
				}
			}
		});
		
		$("#dialogBusy").dialog({
			autoOpen : false,
			resizable : false,
			position : "center",
			open : function() {
				$(".ui-dialog-titlebar-close").hide();
				$(".ui-dialog .ui-dialog-titlebar").hide();
				$(".ui-dialog-title").hide();
				$(".ui-widget-content").css({
					"border" : "0"
				});
				$(".ui-dialog").css({
					"padding" : "0",
					"background" : "transparent"
				});
			},
			height : 500,
			width : 500,
			modal : true
		});
		
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
			<f:message key="duplikovatSadu" />
		</div>
		<jsp:include page="header.jsp" />
		<BR />
		
		<div id="dialogBusy" style="background: transparent;">
			<p id="dialogSpin" style="height: 60%;"></p>
		</div>

		<form:form commandName="formObj" name="kontrola" id="formSada"
			action="${pageContext.servletContext.contextPath}/srv/editace/duplikovatSaduTed/${vybranaSada.id}">

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
					<TD bgcolor="WHITE"><form:input  id="InputNazevSady" class="textovePole"
							path="sada"></form:input></TD>
				</TR>
				<TR height="15px;"></TR>
				<TR>
					<TD></TD>
					<TD><input type="button" value="ok" class="submit" id="tlacitkoSave"/></TD>
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