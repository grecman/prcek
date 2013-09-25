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
<title>P R C E K</title>
<jsp:include page="lib.jsp" />

<!-- 
<script type="text/javascript">
	$(document).ready(function() {
		$("#tab1").tablePagination();
	});

	$(function() {
		'use strict';
		// Change this to the location of your server-side upload handler:
		var url = window.location.hostname === 'blueimp.github.io' ? '//jquery-file-upload.appspot.com/'
				: 'server/php/';
		$('#fileupload').fileupload({
			url : url,
			dataType : 'json',
			done : function(e, data) {
				$.each(data.result.files, function(index, file) {
					$('<p/>').text(file.name).appendTo('#files');
				});
			},
			progressall : function(e, data) {
				var progress = parseInt(data.loaded / data.total * 100, 10);
				$('#progress .bar').css('width', progress + '%');
			}
		}).prop('disabled', !$.support.fileInput).parent().addClass(
				$.support.fileInput ? undefined : 'disabled');
	});
</script>
 -->

</head>

<body>
	<div class="page">
		<div class="headerNazevStranky" title="${userRole}">
			<f:message key="seznamPrPodminekSady" />
		</div>
		<jsp:include page="header.jsp" />

		<div class="zonaFiltru">
			<div class="filtry">
				<c:choose>
					<c:when test="${(empty(vybranyUzivatel))}">
						<form:form commandName="user"
							action="${pageContext.servletContext.contextPath}/srv/editace/vyberMt">
							<f:message>uzivatel</f:message>:&#160;
	 						<form:select onchange="this.form.submit(); return true;"
								path="netusername">
								<form:option value="neco"> --- </form:option>
								<form:option value="${aktualUser.netusername}">${aktualUser.prijmeni} ${aktualUser.jmeno}, ${aktualUser.oddeleni} (${aktualUser.netusername})</form:option>
								<c:forEach var="i" items="${uzivatelList}">
									<form:option value="${i.netusername}">${i.prijmeni} ${i.jmeno}, ${i.oddeleni} (${i.netusername})</form:option>
								</c:forEach>
							</form:select>
						</form:form>
					</c:when>
					<c:otherwise>
						<f:message>uzivatel</f:message>:&#160;
						<a href="${pageContext.servletContext.contextPath}/srv/editace">
							<span style="color: #4BA82E; font-weight: bold;">${vybranyUzivatel.prijmeni}
								${vybranyUzivatel.jmeno}, ${vybranyUzivatel.oddeleni}
								(${vybranyUzivatel.netusername})</span>
						</a>
					</c:otherwise>
				</c:choose>
				&#160;&#160;
				<c:if test="${not(empty(vybranyUzivatel))}">
					<c:choose>
						<c:when test="${(empty(vybranaMt))}">
							<form:form commandName="mt"
								action="${pageContext.servletContext.contextPath}/srv/editace/vyberSadu/${vybranyUzivatel.netusername}">
								<f:message>modelovaTrida</f:message>&#160;(<f:message>produktM</f:message>):&#160;
		 						<form:select onchange="this.form.submit(); return true;"
									path="mt">
									<form:option value="neco"> --- </form:option>
									<c:forEach var="i" items="${mtList}">
										<form:option value="${i.mt}">${i.mt} (${i.produkt})</form:option>
									</c:forEach>
								</form:select>
							</form:form>
						</c:when>
						<c:otherwise>
							<f:message>modelovaTrida</f:message>&#160;(<f:message>produktM</f:message>):&#160;
							<a
								href="${pageContext.servletContext.contextPath}/srv/editace/vyberMt/${vybranyUzivatel.netusername}">
								<span style="color: #4BA82E; font-weight: bold;">${vybranaMt.mt}
									(${vybranaMt.produkt})</span>
							</a>
						</c:otherwise>
					</c:choose>
				</c:if>
				&#160;&#160;
				<c:if test="${not(empty(vybranaMt))}">
					<c:choose>
						<c:when test="${(empty(vybranaSada))}">
							<form:form commandName="sada"
								action="${pageContext.servletContext.contextPath}/srv/editace/zobrazPr/${vybranyUzivatel.netusername}/${vybranaMt.mt}">
								<f:message>nazevSady</f:message>:&#160;
		 						<form:select onchange="this.form.submit(); return true;"
									path="nazev">
									<form:option value="neco"> --- </form:option>
									<c:forEach var="i" items="${sadaList}">
										<form:option value="${i.nazev}">${i.nazev}</form:option>
									</c:forEach>
								</form:select>
							</form:form>
						</c:when>
						<c:otherwise>
							<f:message>nazevSady</f:message>:&#160;
							<a
								href="${pageContext.servletContext.contextPath}/srv/editace/vyberSadu/${vybranyUzivatel.netusername}/${vybranaMt.mt}">
								<span style="color: #4BA82E; font-weight: bold;">${vybranaSada.nazev}</span>
							</a>
						</c:otherwise>
					</c:choose>
				</c:if>
			</div>
		</div>

		<div class="zonaTlacitek" style="text-align: right;">

			<div class="tlacitka">

				<c:set var="novaSadaPopisek">
					<f:message>novaSada</f:message>
				</c:set>
				<form:form commandName="sada"
					action="${pageContext.servletContext.contextPath}/srv/editace/novaSada">
					<input type="submit" value="${novaSadaPopisek}" />
				</form:form>

				<c:if test="${not(empty(vybranaSada))}">
					<c:set var="duplikovatSaduPopisek">
						<f:message>duplikovatSadu</f:message>
					</c:set>
					<form:form commandName="sada"
						action="${pageContext.servletContext.contextPath}/srv/editace/duplikovatSadu">
						<input type="submit" value="${duplikovatSaduPopisek}" />
					</form:form>

					<c:if test="${moznoEditovatSadu}">
						<c:set var="smazatSaduPopisek">
							<f:message>smazatSadu</f:message>
						</c:set>
						<form:form commandName="sada"
							action="${pageContext.servletContext.contextPath}/srv/editace/smazatSadu/${vybranyUzivatel.netusername}/${vybranaMt.mt}/${vybranaSada.id}">
							<input onClick="return confirm('Opravdu?!')" type="submit"
								value="${smazatSaduPopisek}" />
						</form:form>

						<c:set var="prejmenovatSaduPopisek">
							<f:message>prejmenovatSaduShort</f:message>
						</c:set>
						<form:form commandName="sada"
							action="${pageContext.servletContext.contextPath}/srv/editace/prejmenovatSadu/${vybranaSada.id}">
							<input type="submit" value="${prejmenovatSaduPopisek}" />
						</form:form>
					</c:if>
				</c:if>
			</div>
		</div>

		<c:if test="${(empty(prPodminkaList))}">
			<DIV style="height: 450px;">&#160;</DIV>
		</c:if>

		<c:if test="${not(empty(prPodminkaList))}">
			<DIV class="scroll" style="height: 500px; overflow: auto;">
				<TABLE id="tab1" width="100%" style="table-layout: fixed;">
					<col width="45px" />
					<col width="*" />
					<col width="200px" />
					<col width="35px" />
					<col width="45px" />
					<thead>
						<tr>
							<th style="color: #4BA82E;" title="Seřadit"><a
								href="${pageContext.servletContext.contextPath}/srv/editace/zobrazPr/${vybranaSada.sk30tMt.sk30tUser.netusername}/${vybranaSada.sk30tMt.mt}/${vybranaSada.id}">Pořadí</a></th>							
							<th title="Počet zobrazených PR podmínek">PR - ${prCount}</th>
							<th>Poznámka</th>
							<th style="color: #4BA82E;" title="Seřadit"><a
								href="${pageContext.servletContext.contextPath}/srv/editace/zobrazPrOrderByTest/${vybranaSada.sk30tMt.sk30tUser.netusername}/${vybranaSada.sk30tMt.mt}/${vybranaSada.id}">MBT</a></th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="i" items="${prPodminkaList}" varStatus="iterator">
							<tr
								class="${ (iterator.index mod 2) == 0 ? 'rowOdd' : 'rowEven' }">
								<td>${i.poradi}</td>
								<td>${i.pr}</td>
								<td>${i.poznamka}</td>
								<td align="center"><c:choose>
										<c:when test="${empty(i.errMbt)}">
											<img
												src="${pageContext.servletContext.contextPath}/resources/ico/ok.png" />
										</c:when>
										<c:otherwise>
											<img title="${i.errMbt}"
												src="${pageContext.servletContext.contextPath}/resources/ico/zrusit.png" />
										</c:otherwise>
									</c:choose></td>
								<td align="center"><c:if test="${moznoEditovatSadu}">
										<a
											href="${pageContext.servletContext.contextPath}/srv/editace/editovatPr/${i.id}"><img
											title="Editovat" style="border: 0px;"
											src="${pageContext.servletContext.contextPath}/resources/ico/edit.png" /></a>
										<a onClick="return confirm('Really?')"
											href="${pageContext.servletContext.contextPath}/srv/editace/smazatPrPodminku/${i.id}"><img
											title="Smazat" style="border: 0px;"
											src="${pageContext.servletContext.contextPath}/resources/ico/smazat.png" /></a>

									</c:if></td>
							</tr>
						</c:forEach>
					</tbody>
				</TABLE>
			</DIV>
		</c:if>



		<!-- <BR />.${vybranyUzivatel.prijmeni}..${vybranaMt.mt}..${vybranaSada.nazev}..${moznoEditovatSadu}.<BR /> -->

		<div class="zonaTlacitek">

			<div class="tlacitka">

				<c:if test="${not(empty(vybranaSada))}">
					<c:set var="pridatRadekPopisek">
						<f:message>pridatRadek</f:message>
					</c:set>
					<form:form commandName="prPodminka"
						action="${pageContext.servletContext.contextPath}/srv/editace/novaPrPodminka/${vybranaSada.id}">
						<input type="submit" value="${pridatRadekPopisek}" />
					</form:form>

					<c:if test="${not(empty(prPodminkaList))}">
						<c:set var="smazatVsePopisek">
							<f:message>smazatVse</f:message>
						</c:set>
						<form:form commandName="sada"
							action="${pageContext.servletContext.contextPath}/srv/editace/smazatVsechnyPr/${vybranaSada.id}">
							<input onClick="return confirm('Opravdu?!')" type="submit"
								value="${smazatVsePopisek}" />
						</form:form>
					</c:if>

					<c:set var="importTxt">
						<f:message>importTxt</f:message>
					</c:set>
					<form:form commandName="sada"
						action="${pageContext.servletContext.contextPath}/srv/fileUpload/importTxt/${vybranaSada.id}">
						<input type="submit" value="${importTxt}" />
					</form:form>

				</c:if>
			</div>

		</div>
		<jsp:include page="footer.jsp" />

	</div>
</body>
	</html>
</jsp:root>