<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

		<meta name="author" content="Flávio Cortez, Gabriel"/>
		<meta name="viewport" content="width=device-width; initial-scale=1.0;" />

		<link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Lato:100,300,400,700" />
		<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/default.css'/>" />

		<script src="<c:url value='/resources/js/modernizr.custom.js' />"></script>
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
		<script src="<c:url value='/resources/js/jquery.js' />"></script>
		<script src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
		<script src="<c:url value='/resources/js/classie.js' />"></script>
		<script src="<c:url value='/resources/js/mlpushmenu.js' />"></script>
		<script src="<c:url value='/resources/js/default.js' />"></script>
	</body>
</html>
