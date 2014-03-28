<?xml version="1.0" encoding="UTF-8" ?>

<jsp:root
	xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:form="http://www.springframework.org/tags/form"
	xmlns:Spring="http://www.springframework.org/tags"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:f="http://java.sun.com/jsp/jstl/fmt" version="2.0">
		<c:set scope="request" var="sp"><span style="background:#0D9EFF">&#160;</span></c:set>
		<div class="pageHeader">
			<div class="headerNazevAplikace">PRCEK</div> 
			
			<div class="info" title="${userRole}">
				<div class="user">${pageContext.request.userPrincipal.name}</div>
		         &#160;
				<div class="lang"><a href="#" onClick="switchLocale('CZ');">CZ</a>|<a href="#" onClick="switchLocale('DE');" style="color: gray;">DE</a>|<a href="#" onClick="switchLocale('EN');" style="color: gray;">EN</a></div>
			</div>
			<div class="headerLinks">
		<!--	&#160;|&#160;<a class="${actual eq 'editace' ? 'active' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/editace"><f:message key="editace"/></a>  -->
				&#160;|&#160;<a class="${actual eq 'editace' ? 'active' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/editace/vyberMt/${pageContext.request.userPrincipal.name}"><f:message key="editace"/></a>
				&#160;|&#160;<a class="${actual eq 'vypocet' ? 'active' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/vypocet"><f:message key="vypocet"/></a>
				&#160;|&#160;<a class="${actual eq 'offline' ? 'active' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/offline"><f:message key="vysledkyZpracovani"/></a>
				&#160;|&#160;<a class="${actual eq 'monitoring' ? 'active' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/monitoring"><f:message key="monitoring"/></a>
				&#160;|&#160;<a class="${actual eq 'napoveda' ? 'active' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/napoveda"><f:message key="napoveda"/></a>
			</div>
		</div>
</jsp:root>
