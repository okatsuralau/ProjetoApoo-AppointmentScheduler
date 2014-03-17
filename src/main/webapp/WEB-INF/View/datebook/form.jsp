<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:url var="model" value="datebook"/>
<c:url var="root" value="/${model}/"/>

<title> ${title_for_layout} </title>
<h3 class="page-header">
	<i class="glyphicon glyphicon-list"></i>
	<c:if test="${not empty title_for_layout}">
		${title_for_layout}
	</c:if>
</h3>

<nav class="navbar navbar-default" role="navigation">
	<div class="navbar-header">
		<span class="navbar-brand">Escolha uma Especialidade Médica:</span>
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
	</div>

	<ul class="nav navbar-nav pull-right">
		<li class="active"><a data-toggle="tab" href="#FilterByDoctor">Médico</a></li>
		<li><a data-toggle="tab" href="#FilterByExpertise">Especialidade</a></li>

		<!-- TODO: incluir filtro por intervalo de data -->

	</ul>
</nav>


<form:form method="post" action="${root}${action}" modelAttribute="${model}" commandName="${model}">
	<div class="row">
		<div class="col-md-8">






			<div id="PanelList" class="panel panel-default">
				<div class="panel-heading">
					<h1 class="panel-title">Horários disponíveis</h1>
				</div>
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
					</tbody>
				</table>
				<div class="panel-body">
					<!-- Mensagem de erro -->
				</div>
			</div>


		</div>
		<div class="col-md-4">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h1 class="panel-title">Ações</h1>
				</div>
				<div class="panel-footer">
					<form:hidden path="id" />

					<a href="${root}index" class="btn btn-default">Cancelar</a>
					<input type="submit" value="Registrar a consulta" class="btn btn-success" />

				</div>
			</div>
		</div>
	</div>
</form:form>



<content tag="scripts">
	<script type="text/javascript">
		//require([ROOT + 'js/common.js'], function (common) {
			require(['app/main-datebook']);
		//});
	</script>
</content>
