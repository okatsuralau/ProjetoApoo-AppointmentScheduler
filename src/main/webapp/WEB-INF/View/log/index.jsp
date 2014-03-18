<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:url var="model" value="log"/>
<c:url var="root" value="/${model}/"/>

<h3 class="page-header">
	<i class="glyphicon glyphicon-list"></i>
	<c:if test="${not empty title_for_layout}">
		${title_for_layout}
	</c:if>
</h3>

<div class="alert alert-info">
	<p>A desenvolver</p>
	<p>Lista de atividades realizadas no sistema</p>
</div>

<jsp:include page="../Elements/rowset_empty.jsp" />