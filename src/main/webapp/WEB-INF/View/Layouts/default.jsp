<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html class="no-js">
	<head>
		<meta charset="UTF-8">
		<title>
			<decorator:title default="Appointment Scheduler"/>
		</title>

		<c:if test="${not empty description_for_layout}">
			<meta name="description" content="${description_for_layout}"/>
		</c:if>

		<meta name="author" content="Flávio Cortez, Gabriel, Pedro Arthur"/>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />

		<link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Lato:100,300,400,700" />
		<link rel="stylesheet" type="text/css" href="<c:url value='/css/default.css'/>" />

		<c:url var="root" value="/"/>
		<script src="<c:url value='/js/vendor/modernizr.custom.js' />"></script>
		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!--[if lt IE 9]>
		  <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
		  <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
		<![endif]-->
		<script>
			// Variáveis globais na aplicação
			var ROOT = "${root}";
		</script>
		<decorator:head/> <!-- Custom Html Head content -->
	</head>
	<body>
		<div id="main">
			<!-- Push Wrapper -->
			<div class="mp-pusher" id="mp-pusher">
				<jsp:include page="../Elements/header.jsp" />
				<jsp:include page="../Elements/sidebar.jsp" />

				<div class="scroller"><!-- this is for emulating position fixed of the nav -->
					<div class="scroller-inner">
						<main id="main-content" class="container">

							<c:if test="${not empty message}">
								<div class="alert alert-info">${message}</div>
							</c:if>

							<div class="clearfix">
								<!-- Conteúdo dinâmico da aplicação -->
								<decorator:body/>
							</div>

							<jsp:include page="../Elements/footer.jsp" />
						</main>
					</div>
				</div>
			</div>
		</div>

		<!-- scripts  -->
		<script data-main="<c:url value='/js/common.js' />" src="<c:url value='/js/vendor/require.js' />"></script>
		<!-- Custom javascripts -->
		<decorator:getProperty property="page.scripts"></decorator:getProperty>
	</body>
</html>
