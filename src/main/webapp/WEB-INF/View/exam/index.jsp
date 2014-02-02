<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


	<c:if test="${not empty mensagem}">
		<div class="alert alert-info">${mensagem}</div>
	</c:if>

	<c:url var="model" value="exam"/>
	<c:url var="root" value="/${model}/"/>
	<a href="${root}add" class="btn btn-primary">Inserir novo registro</a>

	<c:choose>
	    <c:when test="${not empty exams}">
	        <table class="croogo-table table table-striped table-hover no-mb">
	           	<thead>
	           		<th>ID</th>
	           		<th>Título</th>
	           	</thead>
	           	<tbody>
	           		<c:forEach var="registro" items="${exams}">
		                <tr>
		                    <td>${registro.id}</td>
							<td>${registro.title}</td>
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
	    </c:when>
	    <c:otherwise>
	        <div class="jumbotron text-center">
			    <h1><i class="icon-confused"></i></h1>
			    <p class="lead">Nenhum registro encontrado</p>
			</div>
	    </c:otherwise>
	</c:choose>

