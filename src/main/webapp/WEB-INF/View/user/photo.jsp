<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:url var="model" value="user"/>
<c:url var="root" value="/${model}/"/>

<form method="post" action="${root}savePhoto" enctype="multipart/form-data">
	<div class="row">
		<div class="col-md-8">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h1 class="panel-title">Foto do perfil</h1>
				</div>
				<div class="panel-body form-horizontal">
					<div class="form-group">
						<label for="file" class="control-label col-md-3">Escolha uma imagem</label>
						<div class="col-md-9">
							<input type="file" id="file" name="file" class="form-control" required="required" />
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
					<a href="${root}index" class="btn btn-default">Cancelar</a>
					<input type="submit" value="Save" class="btn btn-success" />
				</div>
			</div>
		</div>
	</div>
</form>
