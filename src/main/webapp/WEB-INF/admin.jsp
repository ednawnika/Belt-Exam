<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Admin</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
	<div class="container">
		<form id="logoutForm" method="POST" action="/logout">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<br>
			<input id="logout" class="btn btn-link" type="submit" value="Logout" />
		</form>
	
		<h1>Welcome, <c:out value="${user.firstName}" /> </h1>

	


		<h2>Customers</h2>
			<table class="table table-bordered table-striped table-hover">
				<tr>
					<th>Name</th>
					<th>Next Due Date</th>
					<th>AmountDue</th>
					<th>PackageType</th>
				</tr>
			
				<c:forEach items="${users}" var="user">
					<tr>
						<td>
							<c:out value="${user.firstName}" />
							<c:out value="${user.lastName}" />
						</td>
						<td>
							<c:out value="${user.email}" />
						</td>
			
						<c:choose>
							<c:when test="${user.isAdmin()}">
								<td>Admin</td>
							</c:when>
			
							<c:otherwise>
								<td>
									<a href="/admin/delete/${user.id}">Delete</a>
									<a href="/admin/promote/${user.id}">Make-Admin</a>
								</td>
							</c:otherwise>
						</c:choose>
					</tr>
				</c:forEach>
			</table>


					<h2>Packages</h2>
					<table class="table table-bordered table-striped table-hover">
						<tr>
							<th>Package Name</th>
							<th>Package Cost</th>
							<th>Available</th>
							<th>Users</th>
							<th>Actions</th>
						</tr>
					
						<c:forEach items="${subscriptions}" var="subs">
							<tr>
								<td>
									<c:out value="${subs.name}"/>
								</td>
								<td>
									<c:out value="${subs.cost}"/>
								</td>
									<td>
										<c:if test="${!subs.isActive()}"><a href="/admin/deactivate/${subs.id}">Activate</a></c:if>
										<c:if test="${subs.isActive()}"><a href="/admin/deactivate/${subs.id}">Deactivate</a>
										</c:if>

									</td>
									<td>
									<c:forEach items="${users}" var="user">
										<c:out value="${subs.user.firstName}" /> 
									</c:forEach>
									</td>					
									<tr>
									<td>
									</td>

									</tr>
							</c:forEach>
					</table>









					<h1>New Package</h1>
					<fieldset>
					<form action="/subscription/new" method="post">
						Package Name:<input type="text" name="name"><br>
						Price: <input type="text" name="cost"><br>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<input type="submit" value="Create new Subscription" />
					</form>
				</fieldset>

</body>
</html>