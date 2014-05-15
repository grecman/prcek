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

<script type="text/javascript">
	$(document)
			.ready(
					function() {

						$.datepicker.regional['cs'] = {
							closeText : 'Cerrar',
							prevText : 'Předchozí',
							nextText : 'Další',
							currentText : 'Hoy',
							monthNames : [ 'Leden', 'Únor', 'Březen', 'Duben',
									'Květen', 'Červen', 'Červenec', 'Srpen',
									'Září', 'Říjen', 'Listopad', 'Prosinec' ],
							monthNamesShort : [ 'Le', 'Ún', 'Bř', 'Du', 'Kv',
									'Čn', 'Čc', 'Sr', 'Zá', 'Ří', 'Li', 'Pr' ],
							dayNames : [ 'Neděle', 'Pondělí', 'Úterý',
									'Středa', 'Čtvrtek', 'Pátek', 'Sobota' ],
							dayNamesShort : [ 'Ne', 'Po', 'Út', 'St', 'Čt',
									'Pá', 'So', ],
							dayNamesMin : [ 'Ne', 'Po', 'Út', 'St', 'Čt', 'Pá',
									'So' ],
							weekHeader : 'Sm',
							dateFormat : 'yy.mm.dd',
							firstDay : 1,
							isRTL : false,
							showMonthAfterYear : false,
							yearSuffix : ''
						};

						$.datepicker.setDefaults($.datepicker.regional['cs']);

						$("#datepickerOd").datepicker();
						$("#datepickerDo").datepicker();

						$(".klikZpracovani")
								.click(
										function() {
											var pocetChecknutychZpracovani = 0;
											$(".klikZpracovani")
													.each(
															function() {
																if ($(this)
																		.prop(
																				'checked')) {
																	pocetChecknutychZpracovani++;
																}
															});
											//alert(pocetChecknutychZpracovani);
											if (pocetChecknutychZpracovani >= 2) {
												$("#tlacitkoSpustitVypocet")
														.css("visibility",
																"visible");
												$("#RadButtagregaceVystup")
														.css("visibility",
																"visible");
											} else if (pocetChecknutychZpracovani >= 1) {
												$("#tlacitkoSpustitVypocet")
														.css("visibility",
																"visible");
												$("#RadButtagregaceVystup")
														.css("visibility",
																"hidden");
											} else {
												$("#tlacitkoSpustitVypocet")
														.css("visibility",
																"hidden");
												$("#RadButtagregaceVystup")
														.css("visibility",
																"hidden");
											}
										});

						$("#tlacitkoSpustitVypocet").click(function() {
							$("#dialogBusy").dialog("open");
							$("#dialogSpin").spin("mojeNastaveniSpinu");
							$("#formZafrontovani").submit();
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
</script>


</head>

<body>
	<div class="page">
		<div class="headerNazevStranky">
			<f:message key="zadaniVypoctu" />
		</div>

		<c:set scope="request" var="actual" value="vypocet" />
		<jsp:include page="header.jsp" />

		<div id="dialogBusy" style="background: transparent;">
			<p id="dialogSpin" style="height: 60%;"></p>
		</div>


		<BR />
		<c:choose>
			<c:when test="${empty(vybraneSady)}">
				<DIV style="height: 550px; color: red; font-weight: bold;">&#160;Nemáte
					vytvořenou žádnou sadu pro zpracování! Založte si jí v sekci
					EDITACE.</DIV>
			</c:when>
			<c:otherwise>

				<!-- OBDOBI -->
				<TABLE>

					<c:choose>
						<c:when test="${empty(platnostVyplnena)}">
							<col width="150px" />
							<col width="200px" />
							<col width="*" />
							<col width="700px" />
							<TR>
								<TD style="font-size: 14px; font-weight: bold;">Období</TD>
								<TD>Zpracování jednoho měsíce</TD>
								<TD>&#160;</TD>
								<TD align="left"><form:form commandName="formObj"
										action="${pageContext.servletContext.contextPath}/srv/vypocet/vybraneObdobiMesic">
										<form:select onchange="this.form.submit(); return true;"
											path="platnostOd">
											<form:option value="neco">- - - - - - - - - - - -</form:option>
											<c:forEach var="i" items="${rokMesicList}">
												<form:option value="${i}">${i}</form:option>
											</c:forEach>
										</form:select>
									</form:form></TD>
							</TR>
							<TR>
								<TD></TD>
								<TD>Denní zpracování</TD>
								<TD>od</TD>
								
									<TD><form:form commandName="formObj"
									action="${pageContext.servletContext.contextPath}/srv/vypocet/vybraneObdobiDenOdDo"><form:input path="platnostOd" id="datepickerOd"
											class="kalendar"></form:input>
									&#160;&#160;do&#160;&#160;
									<form:input path="platnostDo" id="datepickerDo"	class="kalendar"></form:input> &#160;
									<input type="submit" value="ok" class="submit" />
									</form:form></TD>
								
							</TR>
						</c:when>
						<c:otherwise>
							<col width="150px" />
							<col width="148px" />
							<col width="*" />
							<TR>
								<TD style="font-size: 14px; font-weight: bold;">Období</TD>
								<TD></TD>
								<TD style="font-size: 14px; font-weight: bold;"><a
									href="${pageContext.servletContext.contextPath}/srv/vypocet">
										<span style="color: #4BA82E;">${platnostVyplnena}</span>
								</a></TD>
							</TR>
						</c:otherwise>
					</c:choose>
				</TABLE>
				<BR />

				<!-- ZAKAZKY -->
				<TABLE>
					<col width="100px" />
					<col width="200px" />
					<col width="600px" />
					<TR>
						<TD style="font-size: 14px; font-weight: bold;">Zakázky</TD>
						<TD>Závod</TD>
						<TD><c:if test="${not(empty(platnostVyplnena))}">
								<c:choose>
									<c:when test="${empty(zavod)}">
										<form:form commandName="formObj"
											action="${pageContext.servletContext.contextPath}/srv/vypocet/vybranyZavod/${platnostVyplnena}/">
											<form:select onchange="this.form.submit(); return true;"
												path="zavod">
												<form:option value="neco">. . .</form:option>
												<c:forEach var="i" items="${seznamZavodu}">
													<form:option value="${i}">${i}</form:option>
												</c:forEach>
											</form:select>
										</form:form>
									</c:when>
									<c:otherwise>
										<a
											href="${pageContext.servletContext.contextPath}/srv/vypocet/${platnostVyplnena}/">
											<span
											style="color: #4BA82E; font-size: 14px; font-weight: bold;">${zavod}</span>
										</a>
									</c:otherwise>
								</c:choose>
							</c:if></TD>
					</TR>
					<TR>
						<TD></TD>
						<TD>Kontrolní / evidenční bod</TD>
						<TD><c:if
								test="${(not(empty(platnostVyplnena))) and (not(empty(zavod)))}">
								<c:choose>
									<c:when test="${empty(evidBod)}">
										<form:form commandName="evidencniBody"
											action="${pageContext.servletContext.contextPath}/srv/vypocet/vybranyBod/${platnostVyplnena}/">
											<form:select onchange="this.form.submit(); return true;"
												path="id">
												<form:option value="0">. . .</form:option>
												<c:forEach var="i" items="${evidBody}">
													<form:option value="${i.id}">${i.kbodKod} / ${i.kbodEvid} - ${i.fisNaze}  </form:option>
												</c:forEach>
											</form:select>
										</form:form>
									</c:when>
									<c:otherwise>
										<a
											href="${pageContext.servletContext.contextPath}/srv/vypocet/${platnostVyplnena}/">
											<span
											style="color: #4BA82E; font-size: 14px; font-weight: bold;">${evidBod.kbodKod}
												/ ${evidBod.kbodEvid} - ${evidBod.fisNaze}</span>
										</a>
									</c:otherwise>
								</c:choose>
							</c:if></TD>
					</TR>
				</TABLE>
				<BR />

				<!-- SADY (tabulka) -->
				<form:form commandName="formObj" id="formZafrontovani"
					action="${pageContext.servletContext.contextPath}/srv/vypocet/zafrontovani/${platnostVyplnena}/${evidBod.id}/">
					<DIV style="height: 20px; font-size: 14px; font-weight: bold;">Sady
						PR-podmínek</DIV>
					<DIV class="scroll" style="height: 250px; overflow: auto;">
						<TABLE id="tab1" width="100%" style="table-layout: fixed;">
							<col width="100px" />
							<col width="*" />
							<col width="100px" />
							<col width="100px" />
							<thead>
								<tr>
									<th>Modelová třída</th>
									<th>Název sady</th>
									<th>Počet PR</th>
									<th>Zpracování</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="i" items="${vybraneSady}" varStatus="iterator">
									<tr
										class="${ (iterator.index mod 2) == 0 ? 'rowOdd' : 'rowEven' }">
										<td align="center">${i.sk30tMt.mt}</td>
										<td><a
											href="${pageContext.servletContext.contextPath}/srv/editace/zobrazPr/${i.sk30tMt.sk30tUser.netusername}/${i.sk30tMt.mt}/${i.id}">
												<span style="color: #4BA82E; font-weight: bold;">${i.nazev}</span>
										</a>${i.rozpracovano}</td>
										<c:choose>
											<c:when test="${i.pocet > 0}">
												<td align="right">${i.pocet}</td>
											</c:when>
											<c:otherwise>
												<td align="right" style="color: red; font-weight: bold;">0</td>
											</c:otherwise>
										</c:choose>
										<td align="center"><c:if
												test="${not(empty(evidBod)) and (i.pocet > 0)}">
												<form:checkbox cssClass="klikZpracovani" path="idcka"
													value="${i.id}" />
											</c:if></td>
									</tr>
								</c:forEach>
							</tbody>
						</TABLE>
					</DIV>
					<BR />



					<!-- VYSTUPY -->
					<TABLE>
						<col width="100px" />
						<col width="5px" />
						<col width="400px" />
						<col width="100px" />
						<col width="5px" />
						<col width="400px" />
						<TR>
							<TD style="font-size: 14px; font-weight: bold;">Výstupy</TD>
							<TD><form:radiobutton path="agregaceVystup" value="bez"
									class="radiobutton" /></TD>
							<TD>Četnost bez agregace</TD>
							<TD>Seřadit dle:</TD>
							<TD><form:radiobutton path="triditDleVystup" value="poradi"
									class="radiobutton" /></TD>
							<TD>Pořadí</TD>
						</TR>
						<TR>
							<TD></TD>
							<TD
								title="Četnost s agregací má smysl pouze v případě výberů dvou a více sad s různou modelovou třídou.">
								<form:radiobutton path="agregaceVystup" value="s"
									class="radiobutton" id="RadButtagregaceVystup"
									style="visibility: hidden;" />
							</TD>
							<TD
								title="Četnost s agregací má smysl pouze v případě výberů dvou a více sad s různou modelovou třídou.">Četnost
								s agregací</TD>
							<TD></TD>
							<TD><form:radiobutton path="triditDleVystup" value="pr"
									class="radiobutton" /></TD>
							<TD>PR podmínky</TD>
						</TR>
						<TR>
							<TD></TD>
							<TD><form:checkbox path="zakazkyVystup" class="checkbox" /></TD>
							<TD>Detailní seznam zakázek</TD>
						</TR>
						<TR>
							<TD></TD>
							<TD><form:checkbox path="stornoVetyVystup" class="checkbox" /></TD>
							<TD>Zakázky včetně storno vět (opakované odvádění)</TD>
						</TR>
					</TABLE>
					<BR />

					<div class="zonaTlacitek">
						<div class="tlacitka">

							<input type="submit" class="submit" id="tlacitkoSpustitVypocet"
								value="Spustit výpočet" style="visibility: hidden;" />
						</div>
					</div>
				</form:form>
			</c:otherwise>
		</c:choose>


		<jsp:include page="footer.jsp" />


	</div>
</body>
	</html>
</jsp:root>