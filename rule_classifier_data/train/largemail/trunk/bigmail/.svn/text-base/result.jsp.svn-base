<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="framework.struts.util.BoardUtils" %>
<%@ page import="framework.struts.model.FileInfo" %>
<%@ page import="java.util.List" %>
<%@ page import="framework.struts.form.MailForm" %>
<%@ page import="java.net.URLEncoder" %>
<%@ include file="header.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message key="result.title"/></title>
</head>
<body >
<center>
	 <table border="0" cellpadding="0" cellspacing="1" width="590" bgcolor="BBBBBB">
		  <tr>
			<td width=100 align=center bgcolor="E6ECDE" height="22" class="myForm">
				<bean:message key="mail.from"/>
			</td>
			<td width=490 bgcolor="ffffff" style="padding-left:10">
				<bean:write name="mailForm" property="fromAddress"/>
			</td>
		  </tr>
		  <tr>
			<td width=100 align=center bgcolor="E6ECDE" height="22" class="myForm">
				<bean:message key="mail.to"/>
			</td>
			<td width=490 bgcolor="ffffff" style="padding-left:10">
				<bean:write name="mailForm" property="toAddress"/>
			</td>
		  </tr>
		  <tr>
			<td width=100 align=center bgcolor="E6ECDE" height="22" class="myForm">
				<bean:message key="mail.title"/>
			</td>
			<td width=490 bgcolor="ffffff" style="padding-left:10">
				<bean:write name="mailForm" property="title"/>
			</td>
		  </tr>
		  <tr>
			<td width=100 align=center bgcolor="E6ECDE" height="22" class="myForm">
				<bean:message key="mail.content"/>
			</td>
			<td width=490 bgcolor="ffffff" style="padding-left:10">
			<bean:define id="content" name="mailForm" property="content"/>
				<%= BoardUtils.convertHtmlBr((String)content) %>
			</td>
		  </tr>
		  
		</table>
		<br>
		<table border="0" cellpadding="0" cellspacing="1" width="590" bgcolor="BBBBBB">
		<%
			MailForm mailForm =(MailForm)session.getAttribute("attached.file.message");
			if(mailForm!=null ){
			for(int i=0; i<mailForm.getFileList().size();i++){
				List fileList = mailForm.getFileList();
				FileInfo fileInfo = (FileInfo)fileList.get(i);
				String downParm = "tempFileName=" + (fileInfo).getTempFileName() + 
				"&fileName=" + URLEncoder.encode((fileInfo).getFileName(),"UTF-8")+"&content-type="+fileInfo.getContentType();
			
		%>
		 	

		  <tr>
			<td width=100 align=center bgcolor="E6ECDE" height="22" class="myForm">
				<bean:message key="mail.file"/> <%= i+1 %>
			</td>
			<td width=390 bgcolor="ffffff" style="padding-left:10">
				<a href="./download.do?<%= downParm %>">
					<%=fileInfo.getFileName() %>
				</a>				
			</td>
		  </tr>
<%} 
}//end if
%>
		  <tr>
			<td width=100 align=center bgcolor="E6ECDE" height="22" class="myForm" colspan="2">
				<a href="./">
					HOME
				</a>				
			</td>
		  </tr>
	  </table>
</center>		
</body>
</html>