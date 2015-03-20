<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="requestUri" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<sec:authorize var="isAuthenticated" access="isAuthenticated()"/>
<%--
<c:if test="${isAuthenticated == true}">
	<sec:authentication var="userId" property="principal.userDocument.account"/>
</c:if>--%>
