<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="-1" />
<title>APIIT Friends - Sign In</title>
</head>
<body>
	<h2>APIIT Friends Sign In</h2>
	<%-- In case of login error --%>
	<c:if test="${not empty param.login_error}">
		<div>
			Your login attempt was not successful.<br />
			Reason : <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
		</div>
	</c:if>
	<br />
	
	<form name="login" action="<c:url value='signin' />" method="post">
		<table>
			<tr>
				<td>Username</td>
				<td><input type="text" name="j_username" value="<c:if test='${not empty param.login_error}'><c:out value='${SPRING_SECURITY_LAST_USERNAME}' /></c:if>"/></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input type="password" name="j_password"/></td>
			</tr>
			<tr>
				<td><input type="checkbox" name="_spring_security_remember_me"/></td>
				<td>Remember Me</td>
			</tr>
			<tr>
				<td><input type="submit" value="Sign In"/></td>
				<td><input type="reset" value="Clear"/></td>
			</tr>
		</table>
	</form>
</body>
</html>