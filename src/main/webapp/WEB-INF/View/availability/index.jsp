<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:url var="model" value="availability"/>
<c:url var="root" value="/${model}/"/>

<title> ${title_for_layout} </title>
<h3 class="page-header">
	<i class="glyphicon glyphicon-list"></i>
	<c:if test="${not empty title_for_layout}">
		${title_for_layout}
	</c:if>
</h3>

<!-- Filtro de resultados -->
<nav class="navbar navbar-default" role="navigation">
	<div class="navbar-header">
		<span class="navbar-brand">Filtrar por:</span>
	</div>
	<div class="tab-content">
		<div class="tab-pane active" id="FilterByDoctor">
			<form class="navbar-form pull-right" role="search" action="${root}ajax/doctor" method="get" id="FilterByDoctorForm">
				<div class="form-group">
					<select id="doctor_id" name="doctor_id" class="form-control">
						<option value="" default>Selecione</option>
						<c:choose>
							<c:when test="${not empty doctors}">
								<c:forEach var="registro" items="${doctors}">
									<option value="${registro.key}">${registro.value}</option>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<option value="">Sem registros</option>
							</c:otherwise>
						</c:choose>
					</select>
				</div>
			</form>
		</div>
		<div class="tab-pane" id="FilterByExpertise">
			<form class="navbar-form pull-right" role="search" action="${root}ajax/expertise" method="get" id="FilterByExpertiseForm">
				<div class="form-group">
					<select id="expertise_id" name="expertise_id" class="form-control">
						<option value="" default>Selecione</option>
						<c:choose>
							<c:when test="${not empty expertises}">
								<c:forEach var="registro" items="${expertises}">
									<option value="${registro.key}">${registro.value}</option>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<option value="">Sem registros</option>
							</c:otherwise>
						</c:choose>
					</select>
				</div>
			</form>
		</div>
		<div class="tab-pane" id="FilterByOffice">
			<form class="navbar-form pull-right" role="search" action="${root}ajax/office" method="get" id="FilterByOfficeForm">
				<div class="form-group">
					<select id="office_id" name="office_id" class="form-control">
						<option value="" default>Selecione</option>
						<c:choose>
							<c:when test="${not empty offices}">
								<c:forEach var="registro" items="${offices}">
									<option value="${registro.key}">${registro.value}</option>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<option value="">Sem registros</option>
							</c:otherwise>
						</c:choose>
					</select>
				</div>
			</form>
		</div>
	</div>

	<ul class="nav navbar-nav pull-right">
		<li class="active"><a data-toggle="tab" href="#FilterByDoctor">Médico</a></li>
		<li><a data-toggle="tab" href="#FilterByExpertise">Especialidade</a></li>
		<li><a data-toggle="tab" href="#FilterByOffice">Sala</a></li>
	</ul>
</nav>

<c:choose>
	<c:when test="${not empty availabilities}">
		<div id="PanelList" class="panel panel-default">
			<table id="tabela" class="croogo-table table table-striped table-hover no-mb">
				<thead>
					<th>Nome do Médico</th>
					<th>Especialidade</th>
					<th>Sala</th>
					<th>Data</th>
					<th>Horário</th>
					<th>Quantidade máxima</th>
					<th>Quantidade agendada</th>
					<th>Quantidade disponivel</th>
				</thead>
				<tbody>
					<c:forEach var="registro" items="${availabilities}">
						<tr>
							<td>${registro.doctor.individual.first_name}</td>
							<td>${registro.expertise.title}</td>
							<td>${registro.office.title}</td>
							<td>${registro.availability_date}</td>
							<td>${registro.availability_time}</td>
							<td>${registro.available_amount}</td>
							<td>${registro.scheduled_amount}</td>
							<td>${registroval.available_amount - registro.scheduled_amount}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="panel-body">
				<!-- Mensagem de erro -->
			</div>
		</div>
	</c:when>
	<c:otherwise>
		<jsp:include page="../Elements/rowset_empty.jsp" />
	</c:otherwise>
</c:choose>

<content tag="scripts">
	<script type="text/javascript">
		//require([ROOT + 'js/common.js'], function (common) {
			require(['app/main-availability']);
		//});
	</script>
</content>
