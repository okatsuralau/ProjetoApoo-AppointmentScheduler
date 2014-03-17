<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:url var="model" value="user"/>
<c:url var="root" value="/${model}/"/>

<form:form method="post" action="${root}${action}" modelAttribute="${model}" commandName="${model}">
	<div class="row">
		<div class="col-md-8">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h1 class="panel-title">Dados Pessoais</h1>
				</div>
				<div class="panel-body form-horizontal">
					<div class="form-group">
						<form:label path="first_name" class="control-label col-md-3">Nome</form:label>
						<div class="col-md-9">
							<form:input path="first_name" value="${user.individual.first_name}" class="form-control" required="required" />
							<form:errors path="first_name" class="error alert alert-danger help-block pd5"></form:errors>
						</div>
					</div>

					<div class="form-group">
						<form:label path="last_name" class="control-label col-md-3">Sobrenome</form:label>
						<div class="col-md-9">
							<form:input path="last_name" value="${user.individual.last_name}" class="form-control" />
							<form:errors path="last_name" class="error alert alert-danger help-block pd5"></form:errors>
						</div>
					</div>

					<div class="form-group">
						<form:label path="cpf" class="control-label col-md-3">CPF</form:label>
						<div class="col-md-9">
							<form:input path="cpf" value="${user.individual.cpf}" class="form-control" maxlength="11" required="required" />
							<form:errors path="cpf" class="error alert alert-danger help-block pd5"></form:errors>
						</div>
					</div>

					<div class="form-group">
						<form:label path="birthday" class="control-label col-md-3">Data de Aniversário</form:label>
						<div class="col-md-9">
							<form:input path="birthday" value="${user.individual.birthday}" class="form-control" />
							<form:errors path="birthday" class="error alert alert-danger help-block pd5"></form:errors>
						</div>
					</div>

					<div class="form-group">
						<form:label path="email" class="control-label col-md-3">Email</form:label>
						<div class="col-md-9">
							<form:input path="email" value="${user.individual.email}" class="form-control" />
							<form:errors path="email" class="error alert alert-danger help-block pd5"></form:errors>
						</div>
					</div>

					<div class="form-group">
						<form:label path="skin_id" class="control-label col-md-3">Etnias</form:label>
						<div class="col-md-9">
							<form:select path="skin_id" items="${skins}" class="form-control" />
							<form:errors path="skin_id" class="error alert alert-danger help-block pd5"></form:errors>
						</div>
					</div>

					<div class="form-group">
						<form:label path="educationlevel_id" class="control-label col-md-3">Escolaridade</form:label>
						<div class="col-md-9">
							<form:select path="educationlevel_id" items="${educationlevels}" class="form-control" />
							<form:errors path="educationlevel_id" class="error alert alert-danger help-block pd5"></form:errors>
						</div>
					</div>

					<div class="form-group">
						<form:label path="civilstatus_id" class="control-label col-md-3">Estado Civil</form:label>
						<div class="col-md-9">
							<form:select path="civilstatus_id" items="${civilstatus}" class="form-control" />
							<form:errors path="civilstatus_id" class="error alert alert-danger help-block pd5"></form:errors>
						</div>
					</div>
				</div>
			</div>

			<div class="panel panel-default">
				<div class="panel-heading">
					<h1 class="panel-title">Dados Administrativos</h1>
				</div>
				<div class="panel-body form-horizontal">
					<div class="form-group">
						<form:label path="username" class="control-label col-md-3">Username</form:label>
						<div class="col-md-9">
							<form:input path="username" class="form-control" requied="required" />
							<form:errors path="username" class="error alert alert-danger help-block pd5"></form:errors>
						</div>
					</div>

					<div class="form-group">
						<form:label path="role_id" class="control-label col-md-3">Categoria</form:label>
						<div class="col-md-9">
							<form:select path="role_id" items="${roles}" class="form-control" />
							<form:errors path="role_id" class="error alert alert-danger help-block pd5"></form:errors>
						</div>
					</div>
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
					<form:hidden path="individual_id" />
					<form:hidden path="activated" />
					<form:hidden path="deleted" />

					<a href="${root}index" class="btn btn-default">Cancelar</a>
					<input type="submit" value="Save" class="btn btn-success" />
				</div>
			</div>
		</div>
	</div>
</form:form>
