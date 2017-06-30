<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
			<h1 id="homeTitle">${computersCount} computers found</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name"
							value="${search}" /> <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer">Add
						Computer</a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection" value="">
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
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<c:choose>
							<c:when test="${order != 'computerAsc'}">
								<c:choose>
									<c:when test="${empty search}">
										<th><a href="dashboard?order=computerAsc"
											title="Order by computer name">Computer name</a></th>
									</c:when>
									<c:otherwise>
										<th><a
											href="dashboard?order=computerAsc&search=${search}"
											title="Order by computer name">Computer name</a></th>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${empty search}">
										<th><a href="dashboard?order=computerDesc"
											title="Order by computer name">Computer name</a></th>
									</c:when>
									<c:otherwise>
										<th><a
											href="dashboard?order=computerDesc&search=${search}"
											title="Order by computer name">Computer name</a></th>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
						<th>Introduced date</th>
						<!-- Table header for Discontinued Date -->
						<th>Discontinued date</th>
						<!-- Table header for Company -->
						<c:choose>
							<c:when test="${order != 'companyAsc'}">
								<c:choose>
									<c:when test="${empty search}">
										<th><a href="dashboard?order=companyAsc"
											title="Order by company">Company</a></th>
									</c:when>
									<c:otherwise>
										<th><a href="dashboard?order=companyAsc&search=${search}"
											title="Order by company">Company</a></th>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${empty search}">
										<th><a href="dashboard?order=companyDesc"
											title="Order by company">Company</a></th>
									</c:when>
									<c:otherwise>
										<th><a
											href="dashboard?order=companyDesc&search=${search}"
											title="Order by company">Company</a></th>
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
							<td><a href="editComputer?id=${computerDTO.id}" onclick="">${computerDTO.name}</a></td>
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
				<c:if test="${numberOfPages > 1}">
					<c:choose>
						<c:when test="${empty search && empty order}">
							<li><a href="dashboard?page=1" aria-label="Previous"> <span
									aria-hidden="true">&laquo;</span>
							</a></li>
						</c:when>
						<c:when test="${empty search && !empty order}">
							<li><a href="dashboard?page=1&order=${order}"
								aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
							</a></li>
						</c:when>
						<c:when test="${!empty search && empty order}">
							<li><a href="dashboard?page=1&search=${search}"
								aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
							</a></li>
						</c:when>
						<c:otherwise>
							<li><a
								href="dashboard?page=1&order=${order}&search=${search}"
								aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
							</a></li>
						</c:otherwise>
					</c:choose>
				</c:if>

				<c:forEach items="${numberOfPagesArray}" var="numberofPage">
					<c:choose>
						<c:when test="${empty search && empty order}">
							<li><a href="dashboard?page=${numberofPage}">${numberofPage}</a></li>
						</c:when>
						<c:when test="${empty search && !empty order}">
							<li><a href="dashboard?page=${numberofPage}&order=${order}">${numberofPage}</a></li>
						</c:when>
						<c:when test="${!empty search && empty order}">
							<li><a
								href="dashboard?page=${numberofPage}&search=${search}">${numberofPage}</a></li>
						</c:when>
						<c:otherwise>
							<li><a
								href="dashboard?page=${numberofPage}&order=${order}&search=${search}">${numberofPage}</a></li>
						</c:otherwise>
					</c:choose>

				</c:forEach>

				<c:if test="${numberOfPages > 1}">
					<c:choose>
						<c:when test="${empty search && empty order}">
							<li><a href="dashboard?page=${numberOfPages}"
								aria-label="Next"> <span aria-hidden="true">&raquo;</span>
							</a></li>
						</c:when>
						<c:when test="${empty search && !empty order}">
							<li><a href="dashboard?page=${numberOfPages}&order=${order}"
								aria-label="Next"> <span aria-hidden="true">&raquo;</span>
							</a></li>
						</c:when>
						<c:when test="${!empty search && empty order}">
							<li><a
								href="dashboard?page=${numberOfPages}&search=${search}"
								aria-label="Next"> <span aria-hidden="true">&raquo;</span>
							</a></li>
						</c:when>
						<c:otherwise>
							<li><a
								href="dashboard?page=${numberOfPages}&order=${order}&search=${search}"
								aria-label="Next"> <span aria-hidden="true">&raquo;</span>
							</a></li>
						</c:otherwise>
					</c:choose>
				</c:if>
			</ul>
		</div>

		<div class="btn-group btn-group-sm pull-right" role="group">
			<button type="button" class="btn btn-default">10</button>
			<button type="button" class="btn btn-default">50</button>
			<button type="button" class="btn btn-default">100</button>
		</div>

	</footer>
	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/dashboard.js"></script>

</body>
</html>