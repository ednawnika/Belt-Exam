<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

    
<!DOCTYPE HTML>
<html>
<head>
	<title>Dashboard</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
	<div class="container">
		<form id="logoutForm" method="POST" action="/logout">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<br>
			<input id="logout" class="btn btn-link" type="submit" value="Logout" />
		</form>
	
		<h1>Welcome to Dojoscriptions, <c:out value="${user.firstName}" /> </h1>


			<h3>Due Day: </h3>
					<form action="/adduser" method="post">
						<select name="subscription">
							<c:forEach items="${subscriptions}" var="subs">
								<c:if test="${subs.isActive()}">
								<option value="${subs.id}">${ subs.name }
								${subs.cost}</option></c:if>
							</c:forEach>
						</select><br>

						<br>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<input type="submit" value="Sign Up" />
					</form>




			







	</div>
</body>
</html>