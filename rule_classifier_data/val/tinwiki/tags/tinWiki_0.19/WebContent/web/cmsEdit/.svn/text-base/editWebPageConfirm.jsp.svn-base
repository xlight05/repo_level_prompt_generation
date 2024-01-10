
<?xml version="1.0" encoding="ISO-8859-1" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>


<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title>editWebPageConfirm.jsp</title>
	</head>
	
	<body>
	
		<h1>Confirm</h1>
		
		<p>Here is the information being submitted in it's raw xml form.</p>

		<p>formbean webPage.content = <bean:write property="content" name="webPage" /></p>
		<p>formbean webPage.fileName = <bean:write property="fileName" name="webPage"/></p>
		<p>formbean webPage.sectionName = <bean:write property="sectionName" name="webPage"/></p>

		<html:form action="/web/cmsEdit/editWebPageSubmit">
			<html:hidden property="content" />
			<html:hidden property="fileName" />
			<html:hidden property="sectionName" />
		<html:submit property="submit" value="Submit" />
</html:form>

</body>
</html>
