<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<style>
.error {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}

.msg {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}

#login-box {
	width: 300px;
	padding: 20px;
	margin: 100px auto;
	background: #fff;
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border: 1px solid #000;
}
</style>
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
			<h1>Welcome to Computer Database</h1>
			<div class="col-xs-8 col-xs-offset-2 box">
				<form name='loginForm' action="<c:url value='/login' />"
					method='POST'>
					<fieldset>
						<div class="form-group">
							<label for="username">User</label> <input type="text"
								class="form-control" id="username" name="username"
								placeholder="User" required>
						</div>
						<div class="form-group">
							<label for="password">Password</label> <input type="password"
								class="form-control" id="password" name="password"
								placeholder="Password" required>
						</div>
						<div class="actions pull-right">
							<input name="submit" type="submit" class="btn btn-primary"
								value="Sign in" />
						</div>
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</fieldset>
				</form>
				<div class="form-group" style = "margin-top : 15px">
						<c:if test="${!empty error}">
							<div class="error">${error}</div>
						</c:if>
						<c:if test="${!empty msg}">
							<div class="msg">${msg}</div>
						</c:if>
				</div>
			</div>
		</div>
	</section>
</body>
</html>
