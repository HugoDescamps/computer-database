<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="resources/css/font-awesome.css" rel="stylesheet"
	media="screen">
<link href="resources/css/main.css" rel="stylesheet" media="screen">
<link
	href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"
	rel="stylesheet">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application - Computer
				Database </a>
			<div class="btn-group btn-group-sm pull-right" role="group"
				style="padding: 15px 15px">
				<a href="dashboard?locale=fr"><button type="button"
						title="<spring:message code="dashboard.switchToFrenchButtonTitle"/>"
						class="btn btn-default" style="padding: 1px 2px">FR</button></a> <a
					href="dashboard?locale=en"><button type="button"
						title="<spring:message code="dashboard.switchToEnglishButtonTitle"/>"
						class="btn btn-default" style="padding: 1px 2px">EN</button></a> <a
					href="login?logout"><button type="button"
						title="<spring:message code="dashboard.signOutButtonTitle"/>"
						class="btn btn-default" style="padding: 1px 2px">
						<spring:message code="dashboard.signOutButton"/>
					</button></a>

			</div>
		</div>

	</header>
	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${computersCount}
				<spring:message code="dashboard.computersCount" />
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control"
							placeholder="<spring:message code="dashboard.searchPlaceholder"/>"
							value="${search}" /> <input type="submit" id="searchsubmit"
							value="<spring:message code="dashboard.searchButton"/>"
							class="btn btn-primary" /> <input type="hidden"
							name="${_csrf.parameterName}" value="${_csrf.token}" />
					</form>
				</div>
				<c:if test="${role == 'ROLE_ADMIN'}">
					<div class="pull-right">
						<a class="btn btn-success" id="addComputerBtn" href="addComputer"><spring:message
								code="dashboard.addButton" /></a> <a class="btn btn-default"
							id="editComputerBtn" href="#"
							onclick="$.fn.toggleEditMode('<spring:message
								code="dashboard.editButton"/>', '<spring:message
								code="dashboard.viewButton"/>');"><spring:message
								code="dashboard.editButton" /></a>
					</div>
				</c:if>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection" value=""> <input
				type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected"
								onclick="$.fn.deleteSelected('<spring:message code="dashboard.deletionConfirmation"/>');">
									<i class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<c:choose>
							<c:when test="${empty order}">
								<c:choose>
									<c:when test="${empty search && empty size}">
										<th><a href="dashboard"
											title="<spring:message code="dashboard.idColumnTitle"/>">#<i
												class="fa fa-fw fa-sort-asc"></i></a></th>
									</c:when>
									<c:when test="${empty search && !empty size}">
										<th><a href="dashboard?size=${size}"
											title="<spring:message code="dashboard.idColumnTitle"/>">#<i
												class="fa fa-fw fa-sort-asc"></i></a></th>
									</c:when>
									<c:when test="${!empty search && empty size}">
										<th><a href="dashboard?search=${search}"
											title="<spring:message code="dashboard.idColumnTitle"/>">#<i
												class="fa fa-fw fa-sort-asc"></i></a></th>
									</c:when>
									<c:otherwise>
										<th><a href="dashboard?size=${size}&search=${search}"
											title="<spring:message code="dashboard.idColumnTitle"/>">#<i
												class="fa fa-fw fa-sort-asc"></i></a></th>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${empty search && empty size}">
										<th><a href="dashboard"
											title="<spring:message code="dashboard.idColumnTitle"/>">#<i
												class="fa fa-fw fa-sort"></i></a></th>
									</c:when>
									<c:when test="${empty search && !empty size}">
										<th><a href="dashboard?size=${size}"
											title="<spring:message code="dashboard.idColumnTitle"/>">#<i
												class="fa fa-fw fa-sort"></i></a></th>
									</c:when>
									<c:when test="${!empty search && empty size}">
										<th><a href="dashboard?search=${search}"
											title="<spring:message code="dashboard.idColumnTitle"/>">#<i
												class="fa fa-fw fa-sort"></i></a></th>
									</c:when>
									<c:otherwise>
										<th><a href="dashboard?size=${size}&search=${search}"
											title="<spring:message code="dashboard.idColumnTitle"/>">#<i
												class="fa fa-fw fa-sort"></i></a></th>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${order != 'computerAsc'}">
								<c:choose>
									<c:when test="${order == 'computerDesc'}">
										<c:choose>
											<c:when test="${empty search && empty size}">
												<th><a href="dashboard?order=computerAsc"
													title="<spring:message code="dashboard.computerColumnTitle"/>"><spring:message
															code="dashboard.computerColumn" /><i
														class="fa fa-fw fa-sort-desc"></i></a></th>
											</c:when>
											<c:when test="${empty search && !empty size}">
												<th><a href="dashboard?order=computerAsc&size=${size}"
													title="<spring:message code="dashboard.computerColumnTitle"/>"><spring:message
															code="dashboard.computerColumn" /><i
														class="fa fa-fw fa-sort-desc"></i></a></th>
											</c:when>
											<c:when test="${!empty search && empty size}">
												<th><a
													href="dashboard?order=computerAsc&search=${search}"
													title="<spring:message code="dashboard.computerColumnTitle"/>"><spring:message
															code="dashboard.computerColumn" /><i
														class="fa fa-fw fa-sort-desc"></i></a></th>
											</c:when>
											<c:otherwise>
												<th><a
													href="dashboard?order=computerAsc&size=${size}&search=${search}"
													title="<spring:message code="dashboard.computerColumnTitle"/>"><spring:message
															code="dashboard.computerColumn" /><i
														class="fa fa-fw fa-sort-desc"></i></a></th>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${empty search && empty size}">
												<th><a href="dashboard?order=computerAsc"
													title="<spring:message code="dashboard.computerColumnTitle"/>"><spring:message
															code="dashboard.computerColumn" /><i
														class="fa fa-fw fa-sort"></i></a></th>
											</c:when>
											<c:when test="${empty search && !empty size}">
												<th><a href="dashboard?order=computerAsc&size=${size}"
													title="<spring:message code="dashboard.computerColumnTitle"/>"><spring:message
															code="dashboard.computerColumn" /><i
														class="fa fa-fw fa-sort"></i></a></th>
											</c:when>
											<c:when test="${!empty search && empty size}">
												<th><a
													href="dashboard?order=computerAsc&search=${search}"
													title="<spring:message code="dashboard.computerColumnTitle"/>"><spring:message
															code="dashboard.computerColumn" /><i
														class="fa fa-fw fa-sort"></i></a></th>
											</c:when>
											<c:otherwise>
												<th><a
													href="dashboard?order=computerAsc&size=${size}&search=${search}"
													title="<spring:message code="dashboard.computerColumnTitle"/>"><spring:message
															code="dashboard.computerColumn" /><i
														class="fa fa-fw fa-sort"></i></a></th>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${empty search && empty size}">
										<th><a href="dashboard?order=computerDesc"
											title="<spring:message code="dashboard.computerColumnTitle"/>"><spring:message
													code="dashboard.computerColumn" /><i
												class="fa fa-fw fa-sort-asc"></i></a></th>
									</c:when>
									<c:when test="${empty search && !empty size}">
										<th><a href="dashboard?order=computerDesc&size=${size}"
											title="<spring:message code="dashboard.computerColumnTitle"/>"><spring:message
													code="dashboard.computerColumn" /><i
												class="fa fa-fw fa-sort-asc"></i></a></th>
									</c:when>
									<c:when test="${!empty search && empty size}">
										<th><a
											href="dashboard?order=computerDesc&search=${search}"
											title="<spring:message code="dashboard.computerColumnTitle"/>"><spring:message
													code="dashboard.computerColumn" /><i
												class="fa fa-fw fa-sort-asc"></i></a></th>
									</c:when>
									<c:otherwise>
										<th><a
											href="dashboard?order=computerDesc&size=${size}&search=${search}"
											title="<spring:message code="dashboard.computerColumnTitle"/>"><spring:message
													code="dashboard.computerColumn" /><i
												class="fa fa-fw fa-sort-asc"></i></a></th>
									</c:otherwise>
								</c:choose>

							</c:otherwise>
						</c:choose>
						<th><spring:message code="dashboard.introducedColumn" /></th>
						<!-- Table header for Discontinued Date -->
						<th><spring:message code="dashboard.discontinuedColumn" /></th>
						<!-- Table header for Company -->
						<c:choose>
							<c:when test="${order != 'companyAsc'}">
								<c:choose>
									<c:when test="${order == 'companyDesc'}">
										<c:choose>
											<c:when test="${empty search && empty size}">
												<th><a href="dashboard?order=companyAsc"
													title="<spring:message code="dashboard.computerColumnTitle"/>"><spring:message
															code="dashboard.companyColumn" /><i
														class="fa fa-fw fa-sort-desc"></i></a></th>
											</c:when>
											<c:when test="${empty search && !empty size}">
												<th><a href="dashboard?order=companyAsc&size=${size}"
													title="<spring:message code="dashboard.computerColumnTitle"/>"><spring:message
															code="dashboard.companyColumn" /><i
														class="fa fa-fw fa-sort-desc"></i></a></th>
											</c:when>
											<c:when test="${!empty search && empty size}">
												<th><a
													href="dashboard?order=companyAsc&search=${search}"
													title="<spring:message code="dashboard.computerColumnTitle"/>"><spring:message
															code="dashboard.companyColumn" /><i
														class="fa fa-fw fa-sort-desc"></i></a></th>
											</c:when>
											<c:otherwise>
												<th><a
													href="dashboard?order=companyAsc&size=${size}&search=${search}"
													title="<spring:message code="dashboard.computerColumnTitle"/>"><spring:message
															code="dashboard.companyColumn" /><i
														class="fa fa-fw fa-sort-desc"></i></a></th>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${empty search && empty size}">
												<th><a href="dashboard?order=companyAsc"
													title="<spring:message code="dashboard.computerColumnTitle"/>"><spring:message
															code="dashboard.companyColumn" /><i
														class="fa fa-fw fa-sort"></i></a></th>
											</c:when>
											<c:when test="${empty search && !empty size}">
												<th><a href="dashboard?order=companyAsc&size=${size}"
													title="<spring:message code="dashboard.computerColumnTitle"/>"><spring:message
															code="dashboard.companyColumn" /><i
														class="fa fa-fw fa-sort"></i></a></th>
											</c:when>
											<c:when test="${!empty search && empty size}">
												<th><a
													href="dashboard?order=companyAsc&search=${search}"
													title="<spring:message code="dashboard.computerColumnTitle"/>"><spring:message
															code="dashboard.companyColumn" /><i
														class="fa fa-fw fa-sort"></i></a></th>
											</c:when>
											<c:otherwise>
												<th><a
													href="dashboard?order=companyAsc&size=${size}&search=${search}"
													title="<spring:message code="dashboard.computerColumnTitle"/>"><spring:message
															code="dashboard.companyColumn" /><i
														class="fa fa-fw fa-sort"></i></a></th>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${empty search && empty size}">
										<th><a href="dashboard?order=companyDesc"
											title="<spring:message code="dashboard.computerColumnTitle"/>"><spring:message
													code="dashboard.companyColumn" /><i
												class="fa fa-fw fa-sort-asc"></i></a></th>
									</c:when>
									<c:when test="${empty search && !empty size}">
										<th><a href="dashboard?order=companyDesc&size=${size}"
											title="<spring:message code="dashboard.computerColumnTitle"/>"><spring:message
													code="dashboard.companyColumn" /><i
												class="fa fa-fw fa-sort-asc"></i></a></th>
									</c:when>
									<c:when test="${!empty search && empty size}">
										<th><a
											href="dashboard?order=companyDesc&search=${search}"
											title="<spring:message code="dashboard.computerColumnTitle"/>"><spring:message
													code="dashboard.companyColumn" /><i
												class="fa fa-fw fa-sort-asc"></i></a></th>
									</c:when>
									<c:otherwise>
										<th><a
											href="dashboard?order=companyDesc&size=${size}&search=${search}"
											title="<spring:message code="dashboard.computerColumnTitle"/>"><spring:message
													code="dashboard.companyColumn" /><i
												class="fa fa-fw fa-sort-asc"></i></a></th>
									</c:otherwise>
								</c:choose>

							</c:otherwise>
						</c:choose>
					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${computersDTO}" var="computerDTO">
						<tr>

							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computerDTO.id}"></td>
							<td>${computerDTO.id}</td>
							<td><a class="editComputerLink"
								href="editComputer?id=${computerDTO.id}" title="<spring:message code="dashboard.editComputerTitle"/>" onclick="">${computerDTO.name}</a></td>
							<td>${computerDTO.introduced}</td>
							<td>${computerDTO.discontinued}</td>
							<td>${computerDTO.company.name}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<c:choose>
					<c:when test="${empty search && empty size && empty order}">
						<li><a href="dashboard?page=1" aria-label="Previous"> <span
								aria-hidden="true">&laquo;</span>
						</a></li>
					</c:when>
					<c:when test="${empty search && empty size && !empty order}">
						<li><a href="dashboard?page=1&order=${order}"
							aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
						</a></li>
					</c:when>
					<c:when test="${empty search && !empty size && empty order}">
						<li><a href="dashboard?page=1&size=${size}"
							aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
						</a></li>
					</c:when>
					<c:when test="${empty search && !empty size && !empty order}">
						<li><a href="dashboard?page=1&order=${order}&size=${size}"
							aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
						</a></li>
					</c:when>
					<c:when test="${!empty search && empty size && empty order}">
						<li><a href="dashboard?page=1&search=${search}"
							aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
						</a></li>
					</c:when>
					<c:when test="${!empty search && empty size && !empty order}">
						<li><a
							href="dashboard?page=1&order=${order}&search=${search}"
							aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
						</a></li>
					</c:when>
					<c:otherwise>
						<li><a
							href="dashboard?page=1&order=${order}&size=${size}&search=${search}"
							aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
						</a></li>
					</c:otherwise>
				</c:choose>


				<c:forEach items="${numberOfPagesArray}" var="numberofPage">
					<c:choose>
						<c:when test="${empty search && empty size && empty order}">
							<li><a href="dashboard?page=${numberofPage}">${numberofPage}</a></li>
						</c:when>
						<c:when test="${empty search && empty size && !empty order}">
							<li><a href="dashboard?page=${numberofPage}&order=${order}">${numberofPage}</a></li>
						</c:when>
						<c:when test="${empty search && !empty size && empty order}">
							<li><a href="dashboard?page=${numberofPage}&size=${size}">${numberofPage}</a></li>
						</c:when>
						<c:when test="${empty search && !empty size && !empty order}">
							<li><a
								href="dashboard?page=${numberofPage}&order=${order}&size=${size}">${numberofPage}</a></li>
						</c:when>
						<c:when test="${!empty search && empty size && empty order}">
							<li><a
								href="dashboard?page=${numberofPage}&search=${search}">${numberofPage}</a></li>
						</c:when>
						<c:when test="${!empty search && empty size && !empty order}">
							<li><a
								href="dashboard?page=${numberofPage}&order=${order}&search=${search}">${numberofPage}</a></li>
						</c:when>
						<c:otherwise>
							<li><a
								href="dashboard?page=${numberofPage}&order=${order}&size=${size}&search=${search}">${numberofPage}</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>

				<c:choose>
					<c:when test="${empty search && empty size && empty order}">
						<li><a href="dashboard?page=${numberOfPages}"
							aria-label="Next"> <span aria-hidden="true">&raquo;</span>
						</a></li>
					</c:when>
					<c:when test="${empty search && empty size && !empty order}">
						<li><a href="dashboard?page=${numberOfPages}&order=${order}"
							aria-label="Next"> <span aria-hidden="true">&raquo;</span>
						</a></li>
					</c:when>
					<c:when test="${empty search && !empty size && empty order}">
						<li><a href="dashboard?page=${numberOfPages}&size=${size}"
							aria-label="Next"> <span aria-hidden="true">&raquo;</span>
						</a></li>
					</c:when>
					<c:when test="${empty search && !empty size && !empty order}">
						<li><a
							href="dashboard?page=${numberOfPages}&order=${order}&size=${size}"
							aria-label="Next"> <span aria-hidden="true">&raquo;</span>
						</a></li>
					</c:when>
					<c:when test="${!empty search && empty size && empty order}">
						<li><a
							href="dashboard?page=${numberOfPages}&search=${search}"
							aria-label="Next"> <span aria-hidden="true">&raquo;</span>
						</a></li>
					</c:when>
					<c:when test="${!empty search && empty size && !empty order}">
						<li><a
							href="dashboard?page=${numberOfPages}&order=${order}&search=${search}"
							aria-label="Next"> <span aria-hidden="true">&raquo;</span>
						</a></li>
					</c:when>
					<c:otherwise>
						<li><a
							href="dashboard?page=${numberOfPages}&order=${order}&size=${size}&search=${search}"
							aria-label="Next"> <span aria-hidden="true">&raquo;</span>
						</a></li>
					</c:otherwise>
				</c:choose>

			</ul>
		</div>

		<div class="btn-group btn-group-sm pull-right" role="group">
			<c:choose>
				<c:when test="${empty search && empty order}">
					<a href="dashboard?size=10"><button type="button"
							class="btn btn-default">10</button></a>
					<a href="dashboard?size=50"><button type="button"
							class="btn btn-default">50</button></a>
					<a href="dashboard?size=100"><button type="button"
							class="btn btn-default">100</button></a>
				</c:when>
				<c:when test="${!empty search && empty order}">
					<a href="dashboard?size=10&search=${search}"><button
							type="button" class="btn btn-default">10</button></a>
					<a href="dashboard?size=50&search=${search}"><button
							type="button" class="btn btn-default">50</button></a>
					<a href="dashboard?size=100&search=${search}"><button
							type="button" class="btn btn-default">100</button></a>
				</c:when>
				<c:when test="${empty search && !empty order}">
					<a href="dashboard?order=${order}&size=10"><button
							type="button" class="btn btn-default">10</button></a>
					<a href="dashboard?order=${order}&size=50"><button
							type="button" class="btn btn-default">50</button></a>
					<a href="dashboard?order=${order}&size=100"><button
							type="button" class="btn btn-default">100</button></a>
				</c:when>
				<c:otherwise>
					<a href="dashboard?order=${order}&size=10&search=${search}"><button
							type="button" class="btn btn-default">10</button></a>
					<a href="dashboard?order=${order}&size=50&search=${search}"><button
							type="button" class="btn btn-default">50</button></a>
					<a href="dashboard?order=${order}&size=100&search=${search}"><button
							type="button" class="btn btn-default">100</button></a>
				</c:otherwise>
			</c:choose>

		</div>
		<input type="hidden" name="role" value="${role}" id="role" />
	</footer>
	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/dashboard.js"></script>

</body>
</html>
