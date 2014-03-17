<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:url var="model" value="exam"/>
<c:url var="root" value="/${model}/"/>

<form:form method="post" action="${root}${action}" modelAttribute="${model}" commandName="${model}">
	<div class="row">
		<div class="panel panel-default form-horizontal">
			<div class="panel-heading">
				<h1 class="panel-title">Dados</h1>
			</div>
			<div class="panel-body">
				<div class="form-group">
					<form:label path="title" class="control-label col-md-3">Título</form:label>
					<div class="col-md-9">
						<form:input path="title" class="form-control" />
						<form:errors path="title" class="error alert alert-danger help-block pd5"></form:errors>
					</div>
				</div>
			</div>

			<div class="panel-body">
				<div class="panel-body">
					<div class="form-group">
						<form:label path="expertise_id" class="control-label col-md-3">Especialidade Médica</form:label>
						<div class="col-md-9">
							<!-- TODO: usar checkbox e permitir selecionar multiplas especialidades -->
							<!-- <form:radiobuttons path="expertise_id" items="${expertises}" /> -->
							<form:select path="expertise_id" items="${expertises}" class="form-control" />
							<form:errors path="expertise_id" class="error alert alert-danger help-block pd5"></form:errors>
						</div>
					</div>
				</div>
			</div>

			<div class="panel-footer">
				<a href="${root}index" class="btn btn-default">Cancelar</a>
				<input type="submit" value="Save" class="btn btn-success" />

				<form:hidden path="id" />
			</div>
		</div>
	</div>
</form:form>
