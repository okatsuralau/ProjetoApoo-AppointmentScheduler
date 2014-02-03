<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%-- <jsp:forward page="index"></jsp:forward> --%>
<div class="jumbotron">
    Sistema gernciador de consultas médicas

    <c:url value="/showMessage.html" var="messageUrl" />
    <a href="${messageUrl}">Click to enter</a>
</div>
