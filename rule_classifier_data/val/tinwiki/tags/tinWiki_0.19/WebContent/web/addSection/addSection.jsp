
<?xml version="1.0" encoding="ISO-8859-1" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>


<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title>addSection</title>
	</head>
	
	<body>
		<h1>New section has been successfully added</h1>


		<html:form action="/web/addSection/addSection">
			<html:hidden property="content" />
			<html:hidden property="fileName" />
			<html:hidden property="sectionName" />
		<html:submit property="submit" value="Add One More Section" />
		<p>
			<a href="<%=request.getContextPath()%><bean:write property="fileName" name="webPage"/>">View Updated Change - <bean:write property="fileName" name="webPage"/></a>
		</p>
</html:form>

</body>
</html>