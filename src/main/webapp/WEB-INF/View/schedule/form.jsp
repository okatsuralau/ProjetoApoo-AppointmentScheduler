<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:url var="model" value="schedule"/>
<c:url var="root" value="/${model}/"/>

<form:form method="post" action="${root}${action}" modelAttribute="${model}" commandName="${model}">
	<div class="row">
		<div class="col-md-6">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h1 class="panel-title">Adicionar configuração de agenda médica</h1>
				</div>
				<div class="panel-body form-horizontal">
					<div class="form-group">
						<form:label path="doctor_id" class="control-label col-md-3">Médico</form:label>
						<div class="col-md-9">
							<form:select path="doctor_id" items="${doctors}" class="form-control" required="required" />
							<form:errors path="doctor_id" class="error alert alert-danger help-block pd5"></form:errors>
						</div>
					</div>

					<div class="form-group">
						<form:label path="expertise_id" class="control-label col-md-3">Especialidade</form:label>
						<div class="col-md-9">
							<form:select path="expertise_id" items="${expertises}" class="form-control" />
							<form:errors path="expertise_id" class="error alert alert-danger help-block pd5"></form:errors>
						</div>
					</div>

					<div class="form-group">
						<form:label path="office_id" class="control-label col-md-3">Sala</form:label>
						<div class="col-md-9">
							<form:select path="office_id" items="${offices}" class="form-control" />
							<form:errors path="office_id" class="error alert alert-danger help-block pd5"></form:errors>
						</div>
					</div>

					<div class="form-group">
						<form:label path="sunday" for="sunday" class="control-label col-md-3">Domingo</form:label>
						<div class="col-md-9">
							<form:checkbox path="sunday" id="sunday" />
							<form:errors path="sunday" class="error alert alert-danger help-block pd5"></form:errors>
						</div>
					</div>

					<div class="form-group">
						<form:label path="monday" for="monday" class="control-label col-md-3">Segunda</form:label>
						<div class="col-md-9">
							<form:checkbox path="monday" id="monday"/>
							<form:errors path="monday" class="error alert alert-danger help-block pd5"></form:errors>
						</div>
					</div>

					<div class="form-group">
						<form:label path="tuesday" for="tuesday" class="control-label col-md-3">Terça</form:label>
						<div class="col-md-9">
							<form:checkbox path="tuesday" id="tuesday"/>
							<form:errors path="tuesday" class="error alert alert-danger help-block pd5"></form:errors>
						</div>
					</div>

					<div class="form-group">
						<form:label path="wednesday" for="wednesday" class="control-label col-md-3">Quarta</form:label>
						<div class="col-md-9">
							<form:checkbox path="wednesday" id="wednesday" />
							<form:errors path="wednesday" class="error alert alert-danger help-block pd5"></form:errors>
						</div>
					</div>

					<div class="form-group">
						<form:label path="thursday" for="thursday" class="control-label col-md-3">Quinta</form:label>
						<div class="col-md-9">
							<form:checkbox path="thursday" id="thursday" />
							<form:errors path="thursday" class="error alert alert-danger help-block pd5"></form:errors>
						</div>
					</div>

					<div class="form-group">
						<form:label path="friday" for="friday" class="control-label col-md-3">Sexta</form:label>
						<div class="col-md-9">
							<form:checkbox path="friday" id="friday"/>
							<form:errors path="friday" class="error alert alert-danger help-block pd5"></form:errors>
						</div>
					</div>

					<div class="form-group">
						<form:label path="saturday" for="saturday" class="control-label col-md-3">Sábado</form:label>
						<div class="col-md-9">
							<form:checkbox path="saturday" id="saturday" />
							<form:errors path="saturday" class="error alert alert-danger help-block pd5"></form:errors>
						</div>
					</div>

					<div class="form-group">
						<form:label path="schedule_time" class="control-label col-md-3">Horário</form:label>
						<div class="col-md-9">
							<form:select path="schedule_time" items="${horarios}" class="form-control" maxlenght="8" />
							<form:errors path="schedule_time" class="error alert alert-danger help-block pd5"></form:errors>
						</div>
					</div>

					<div class="form-group">
						<form:label path="amount" class="control-label col-md-3">Quantidade</form:label>
						<div class="col-md-9">
							<form:select path="amount" items="${quantidades}" class="form-control" />
							<form:errors path="amount" class="error alert alert-danger help-block pd5"></form:errors>
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

					<a href="${root}index" class="btn btn-default">Cancelar</a>
					<input type="submit" value="Save" class="btn btn-success" />

				</div>
			</div>
		</div>
	</div>
</form:form>
