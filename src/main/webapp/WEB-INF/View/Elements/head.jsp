<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<meta charset="UTF-8">
<title>
	<c:choose>
		<c:when test="${not empty title_for_layout}">
			${title_for_layout} - Appointment Scheduler
		</c:when>
		<c:otherwise>
			Appointment Scheduler
		</c:otherwise>
	</c:choose>
	<decorator:title default="Appointment Scheduler" />
</title>

<c:if test="${not empty description_for_layout}">
	<meta name="description" content="${description_for_layout}" />
</c:if>

<meta name="author" content="Flávio Cortez, Gabriel, Pedro Arthur, Udson" />
<meta name="viewport" content="width=device-width; initial-scale=1.0;" />

<link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Roboto:100,300,400,700" />
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/default.css'/>" />

<script src="<c:url value='/resources/js/modernizr.custom.js' />"></script>
