<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:url var="model" value="doctor"/>
<c:url var="root" value="/${model}/"/>

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
	<c:when test="${not empty doctors}">
		<div class="panel panel-default">
			<table class="croogo-table table table-striped table-hover no-mb">
				<thead>
					<th>ID</th>
					<th>Nome</th>
					<th>Sobrenome</th>
					<th>Especialidade</th>
				</thead>
				<tbody>
					<c:forEach var="registro" items="${doctors}">
						<tr>
							<td>${registro.id}</td>
							<td>${registro.individual.first_name}</td>
							<td>${registro.individual.last_name}</td>
							<td>${registro.expertise.title}</td>
							<td class="column-actions actions text-right">
								<div class="btn-group">
									<button title="Opções" data-toggle="dropdown" class="btn btn-default btn-labeled dropdown-toggle tooltip-alert" type="button">
										<i class="btn-label icon-settings icon-lg"></i>
										<span class="sr-only">Opções</span>
										<span class="btn-text caret"></span>
									</button>
									<ul role="menu" class="dropdown-menu text-left">
										<li><a href="${root}edit/${registro.id}"><i class="icon-lg icon-pencil"></i> Editar</a></li>
										<li><a href="${root}delete/${registro.id}"><i class="icon-lg trash"></i> Excluir</a></li>
									</ul>
								</div>
							</td>
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
