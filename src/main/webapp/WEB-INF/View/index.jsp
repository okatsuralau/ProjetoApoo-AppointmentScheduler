<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


	<!-- DASHBOARD -->
	<c:url var="root" value='/' />

	<div class="jumbotron">
	    <h1><i class="icon-accessibility"></i> Seja bem vindo, Gabriel Lau</h1>
	    <p class="lead">Utilize o menu ao lado para escolher a operação desejada</p>
	</div>

	<div class="col-md-6 col-lg-6">
	    <div class="panel panel-default">
	        <div class="panel-heading">
	        	<h1 class="panel-title">Resumo do sistema</h1>
	        </div>
	        <div class="list-group chart-progress">
	            <a class="list-group-item chart" href="${root}expertise">
				    <span class="pull-right col-md-4">
				        <strong class="list-group-item-heading pdl5 icon-lg icon-text"> Especialidades</strong>
				    </span>
				    <span class="progress no-mb show">
				        <span aria-valuenow="83" aria-valuemax="100" aria-valuemin="0" role="progressbar" class="progress-bar progress-bar-success" style="width:83%;"><small class="badge">${expertise_count}</small></span>
				    </span>
				</a>
				<a class="list-group-item chart" href="${root}skin">
				    <span class="pull-right col-md-4">
				        <strong class="list-group-item-heading pdl5 icon-lg icon-users"> Etnias</strong>
				    </span>
				    <span class="progress no-mb show">
				        <span aria-valuenow="100" aria-valuemax="100" aria-valuemin="0" role="progressbar" class="progress-bar progress-bar-success" style="width:100%;"><small class="badge">${skin_count}</small></span>
				    </span>
				</a>

				<a class="list-group-item chart" href="${root}role">
				    <span class="pull-right col-md-4">
				        <strong class="list-group-item-heading pdl5 icon-lg icon-users"> Roles</strong>
				    </span>
				    <span class="progress no-mb show">
				        <span aria-valuenow="100" aria-valuemax="100" aria-valuemin="0" role="progressbar" class="progress-bar progress-bar-success" style="width:30%;"><small class="badge">${role_count}</small></span>
				    </span>
				</a>

				<a class="list-group-item chart" href="${root}office">
				    <span class="pull-right col-md-4">
				        <strong class="list-group-item-heading pdl5 icon-lg icon-users"> Salas</strong>
				    </span>
				    <span class="progress no-mb show">
				        <span aria-valuenow="100" aria-valuemax="100" aria-valuemin="0" role="progressbar" class="progress-bar progress-bar-success" style="width:20%;"><small class="badge">${office_count}</small></span>
				    </span>
				</a>

			</div>
	    </div>
	</div>




