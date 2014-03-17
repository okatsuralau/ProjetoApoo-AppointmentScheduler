<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<title> ${title_for_layout} </title>

<!-- DASHBOARD -->
<div class="jumbotron">
    <h1><i class="icon-accessibility"></i> Seja bem vindo, Gabriel Lau</h1>
    <p class="lead">Utilize o menu ao lado para escolher a operação desejada</p>
</div>

<div class="col-md-6 col-lg-6">
    <div class="panel panel-default">
        <div class="panel-heading">
        	<h1 class="panel-title">Resumo do sistema</h1>
        </div>
        ${chart_list}
    </div>
</div>
