<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:form="http://www.springframework.org/tags/form"
	xmlns:Spring="http://www.springframework.org/tags"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:f="http://java.sun.com/jsp/jstl/fmt" version="2.0">
	
	<div class="pageFooter">
	<f:message key="skodaAutoFooter"></f:message><a href="mailto:ServiceDesk@skoda-auto.cz" style="color: green;">ServiceDesk@skoda-auto.cz</a>
		<!--
		 ${locationInfo} <f:formatDate pattern="yyyyMMdd-kk:mm:ss" value="${timeMetter.currentTime}" />[${timeMetter.processTime}]
		  --> 
	</div>
	
	<!-- 
	<form name="weist" target="weist" method="get">
			<input type="hidden" name="_flowExecutionKey" value="${flowExecutionKey}"/>
	</form>
	<iframe style="visibility:hidden;" name="weist" width="0px" height="0px" src="${pageContext.request.contextPath}/void.txt">null</iframe>
	 -->
</jsp:root>