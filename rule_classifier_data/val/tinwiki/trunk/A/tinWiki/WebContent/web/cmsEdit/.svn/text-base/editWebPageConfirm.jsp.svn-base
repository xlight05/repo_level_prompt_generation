<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>editWebPageConfirm.jsp</title>
	</head>
	
	<body>
	
		<h1>Confirm</h1>
		
		<p>Here is the information being submitted in it's raw xml form.</p>

		<html:form action="/web/cmsEdit/editWebPageSubmit">
			<html:hidden property="content" />
			<html:hidden property="fileName" />
			<html:hidden property="sectionName" />
		<html:submit property="submit" value="Submit" />

		<p>formbean webPage.fileName = <bean:write property="fileName" name="webPage"/></p>
		<p>formbean webPage.sectionName = <bean:write property="sectionName" name="webPage"/></p>
		<p>formbean webPage.content = </p>
		<pre><bean:write property="content" name="webPage" /></pre>

</html:form>

</body>
</html>
