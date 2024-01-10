<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title>APIIT Friends - Register Student</title>
	<s:head theme="ajax"/>
</head>
<body>
	<h2>Register new Student Account</h2>
	
	<s:form action="registerstudent" validate="true">
		<s:token />
		<s:textfield name="username" label="Username" required="true"/>
		<s:textfield name="CBNo" label="CB Number" required="true"/>
		<s:password name="password" label="Password" required="true"/>
		<s:password name="confirm" label="Confirm Password" required="true"/>
		<s:textfield name="firstName" label="First Name" required="true"/>
		<s:textfield name="lastName" label="Last Name" required="true"/>
		<s:textfield name="email" label="Email" required="true"/>
		<s:textfield name="securityQuestion" label="Security Question"/>
		<s:textfield name="securityAnswer" label="Security Answer"/>
		<s:submit /><s:reset />
	</s:form>	
</body>
</html>