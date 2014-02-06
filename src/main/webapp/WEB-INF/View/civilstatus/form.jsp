
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:url var="model" value="civilstatus"/>
<c:url var="root" value="/${model}/"/>

<form:form method="post" action="${root}${action}" modelAttribute="${model}" commandName="${model}" class="col-md-6">
	<div class="row">
		<div class="panel panel-default">
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
			<div class="panel-footer">
				<a href="${root}index" class="btn btn-default">Cancelar</a>
				<input type="submit" value="Save" class="btn btn-success" />

				<form:hidden path="id" />
			</div>
		</div>
	</div>
</form:form>
