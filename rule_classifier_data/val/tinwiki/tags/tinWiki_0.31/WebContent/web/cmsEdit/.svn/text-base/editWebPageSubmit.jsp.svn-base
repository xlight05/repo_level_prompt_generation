<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title></title>
	</head>
	
	<body>

		<h1>Processed</h1>

		<p>Here is the final rendered xhtml 1.0 transitional page.</p>
		<p>
			<%-- There is a struts way of doing this. --%>
			<a href="<%=request.getContextPath()%><bean:write property="fileName" name="webPage"/>">View Updated Change - <bean:write property="fileName" name="webPage"/></a>
		</p>

		<p>formbean webPage.content = </p>
		<pre><bean:write property="content" name="webPage"/></pre>

	</body>
</html>
