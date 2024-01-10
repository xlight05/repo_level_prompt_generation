<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="s" uri="/struts-tags" %>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title>APIIT Friends - Registration</title>

	<s:head theme="ajax" debug="true"/>
	
	<script type="text/javascript" src="/js/registration.js"></script>
</head>
<body>
	
	<div>
	Hi, you are in the registration page.
	</div>
	<s:url id="typesURL" action="type" namespace="registration" />
	
	
	
	<s:div id="reg_form">
		<!-- Ajax Content -->
		<h3>Terms of Service</h3>
		Blah blah blah blah.
		<br />
		<s:a href="%{typesURL}" theme="ajax" targets="reg_form">Load</s:a>	
	</s:div>
</body>
</html>