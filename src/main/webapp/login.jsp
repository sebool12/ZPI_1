<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sign In</title>
<link href="favicon.ico" rel="icon">
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<link rel="stylesheet" href="<spring:url value="/resources/css/bootstrap.min.css" />" />
<link rel="stylesheet" href="<spring:url value="/resources/css/signin.css" />" />
<!-- JQuery -->
<script type="text/javascript" src="<spring:url value="/resources/js/jquery-2.0.3.min.js" />"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/jquery-2.0.3.min.map" />"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/bootstrap.min.js" />"></script>
</head>
<body>

<div class="container">
	<p>
		This project is to showcase Vaadin. Based on Maven, Hibernate, 
		HSQLDB, Spring Framework, Spring Security, Spring Data JPA 
		and JavaMail.
	</p>
	
	<p>
		This project contains embedded HSQL database, which destroys all
		data at shutdown. If you want different database, add JDBC driver to
		pom.xml and change WEB-INF/spring/db.xml
	</p>
	
	<p class="alert alert-info">
		By default this application runs in preview mode, which means that it 
		won't send any actual emails and database will be each hour re-initialized.
	</p>

	<form action='/j_spring_security_check' method='post' class="form-signin">
		<h2 class="form-signin-heading">Please Sign In:</h2>
		<input type='text' name='j_username' placeholder='Username' class="form-control" />
		<input type='password' name='j_password' placeholder="Password" class="form-control" />
		<input type='submit' class="btn btn-lg btn-primary btn-block" />
	</form>

</div>

</body>
</html>