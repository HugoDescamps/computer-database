<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="resources/css/font-awesome.css" rel="stylesheet"
	media="screen">
<link href="resources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application - Computer
				Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>
						<spring:message code="formAddComputer.formTitle" />
					</h1>
					<form action="addComputer" method="POST">
						<fieldset>
							<div class="form-group">
								<label for="computerName"><spring:message
										code="forms.nameField" /></label> <input type="text"
									class="form-control" id="computerName" name="computerName"
									placeholder="<spring:message code="forms.nameField"/>"
									value="${computerName}" required>
							</div>
							<div class="form-group">
								<label for="introduced"><spring:message
										code="forms.introducedField" /></label> <input type="date"
									class="form-control" id="introduced" name="introduced"
									placeholder="Introduced date" value="${introduced}">
							</div>
							<div class="form-group">
								<label for="discontinued"><spring:message
										code="forms.discontinuedField" /></label> <input type="date"
									class="form-control" id="discontinued" name="discontinued"
									placeholder="Discontinued date" value="${discontinued}">
							</div>
							<div class="form-group">
								<label for="companyId"><spring:message
										code="forms.companyField" /></label> <select class="form-control"
									id="companyId" name="companyId">
									<option value="0">--</option>
									<c:forEach items="${companiesList}" var="company">
										<c:choose>
											<c:when test="${company.id == companyId}">
												<option value="${company.id}" selected>${company.name}</option>
											</c:when>
											<c:otherwise>
												<option value="${company.id}">${company.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>

								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit"
								value="<spring:message code="forms.addButton"/>"
								class="btn btn-primary"> <a href="dashboard"
								class="btn btn-default"><spring:message
									code="forms.cancelButton" /></a>
						</div>
						<div class="col-xs-8 box">
							<c:if test="${!empty inputError}">
								 <h1>
									<spring:message code="${inputError}" />
								</h1>
							</c:if>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>