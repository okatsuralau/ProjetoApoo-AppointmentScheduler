<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<header id="header" class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container">

    	<div class="navbar-header">
            <button type="button" id="trigger" class="menu-trigger- trigger-menu btn btn-default btn-lg navbar-btn navbar-left">
                <i class="glyphicon glyphicon-th-list"></i>
                <span class="sr-only">Open/Close Menu</span>
            </button>

            <a href="${root}" class="navbar-brand">Appointment Scheduler</a>
        </div>

        <div>
        	<!-- <p class="navbar-text">
                <c:choose>
                    <c:when test="${not empty title_for_layout}">
                        - <i class="glyphicon glyphicon-home"></i> ${title_for_layout}
                    </c:when>
                    <c:otherwise>
                        - <i class="glyphicon glyphicon-home"></i> Dashboard
                    </c:otherwise>
                </c:choose>
            </p> -->
        	<ul class="nav navbar-nav navbar-right">
        		<li class="dropdown">
        			<!-- TODO: pegar o nome do usuário autenticado -->
                    <c:url var="user_name" value='Gabriel Lau' />

                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                    	<span class="hidden-xs-only- hidden-xs"> ${user_name} </span>
                        <span class="text-center">
                            <i class="glyphicon glyphicon-user valign-m"></i>
                        </span>
                        <b class="caret hidden-xs-only"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="${root}user/profile"><i class="glyphicon glyphicon-list-alt"></i> Meus dados</a></li>
                        <li><a href="${root}user/change_password"><i class="glyphicon glyphicon-lock"></i> Alterar a minha senha</a></li>
                        <li class="divider"></li>
                        <li><a href="${root}logout"><i class="glyphicon glyphicon-log-out"></i>Sair</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</header>
