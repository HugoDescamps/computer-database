<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page session="true"%>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<link href="resources/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="resources/css/font-awesome.css" rel="stylesheet"
	media="screen">
<link href="resources/css/main.css" rel="stylesheet" media="screen">
</head>
<body onload='document.loginForm.username.focus();'>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application - Computer
				Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="col-xs-8 col-xs-offset-2 box">
			<h1><spring:message code="login.welcome"/></h1>
				<form name='loginForm' action="<c:url value='/login' />"
					method='POST'>
					<fieldset>
						<div class="form-group">
							<label for="username"><spring:message code="login.user"/></label> <input type="text"
								class="form-control" id="username" name="username"
								placeholder="<spring:message code="login.user"/>" required>
						</div>
						<div class="form-group">
							<label for="password"><spring:message code="login.password"/></label> <input type="password"
								class="form-control" id="password" name="password"
								placeholder="<spring:message code="login.password"/>" required>
						</div>
						<div class="actions pull-right">
							<input name="submit" type="submit" class="btn btn-primary"
								value="<spring:message code="login.signIn"/>" />
						</div>
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</fieldset>
				</form>
				<div class="form-group" style = "margin-top : 15px">
						<c:if test="${!empty error}">
							<div class="error"><spring:message code="${error}"/></div>
						</c:if>
						<c:if test="${!empty msg}">
							<div class="msg"><spring:message code="${msg}"/></div>
						</c:if>
				</div>
			</div>
		</div>
	</section>
</body>
</html>
