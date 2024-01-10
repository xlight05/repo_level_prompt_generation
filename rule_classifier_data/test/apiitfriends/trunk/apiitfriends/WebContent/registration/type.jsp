<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>APIIT Friends - Register</title>
<s:head theme="ajax"/>
</head>
<body>
<h2>Register - Account Type</h2>

<s:form action="select-type">
	<s:select list="types" name="type" label="Type" />
	<s:submit value="Proceed"/>
</s:form>
</body>
</html>