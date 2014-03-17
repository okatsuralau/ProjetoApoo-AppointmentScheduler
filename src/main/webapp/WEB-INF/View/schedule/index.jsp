<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:url var="model" value="schedule"/>
<c:url var="root" value="/${model}/"/>

<title> ${title_for_layout} </title>

<h3 class="page-header">
	<a class="btn btn-sm btn-primary pull-right" href="${root}add">
		<i class="glyphicon glyphicon-plus"></i>
		<span class="hidden-xs">adicionar</span>
	</a>

	<i class="glyphicon glyphicon-list"></i>
	<c:if test="${not empty title_for_layout}">
		${title_for_layout}
	</c:if>
</h3>

<c:choose>
	<c:when test="${not empty schedule}">
		<div class="panel panel-default">
			<table class="croogo-table table table-striped table-hover no-mb">
				<thead>
					<th>ID</th>
					<th>Nome do Médico</th>
					<th>Especialidade</th>
					<th>Sala</th>
					<th>Domingo</th>
					<th>Segunda</th>
					<th>Terça</th>
					<th>Quarta</th>
					<th>Quinta</th>
					<th>Sexta</th>
					<th>Sábado</th>
					<th>Horário</th>
					<th>Quantidade</th>
				</thead>
				<tbody>
					<c:forEach var="registro" items="${schedule}">
						<tr>
							<td>${registro.id}</td>
							<td>${registro.doctor.individual.first_name}</td>
							<td>${registro.expertise.title}</td>
							<td>${registro.office.title}</td>
							<td><c:choose><c:when test="${registro.sunday}">Sim</c:when><c:otherwise>Não</c:otherwise></c:choose></td>
							<td><c:choose><c:when test="${registro.monday}">Sim</c:when><c:otherwise>Não</c:otherwise></c:choose></td>
							<td><c:choose><c:when test="${registro.tuesday}">Sim</c:when><c:otherwise>Não</c:otherwise></c:choose></td>
							<td><c:choose><c:when test="${registro.wednesday}">Sim</c:when><c:otherwise>Não</c:otherwise></c:choose></td>
							<td><c:choose><c:when test="${registro.thursday}">Sim</c:when><c:otherwise>Não</c:otherwise></c:choose></td>
							<td><c:choose><c:when test="${registro.friday}">Sim</c:when><c:otherwise>Não</c:otherwise></c:choose></td>
							<td><c:choose><c:when test="${registro.saturday}">Sim</c:when><c:otherwise>Não</c:otherwise></c:choose></td>
							<td>${registro.schedule_time}</td>
							<td>${registro.amount}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:when>
	<c:otherwise>
		<jsp:include page="../Elements/rowset_empty.jsp" />
	</c:otherwise>
</c:choose>
