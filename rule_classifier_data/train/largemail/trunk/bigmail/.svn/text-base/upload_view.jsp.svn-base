<%@page contentType="text/html;charset=UTF-8" %>
<%@ include file="header.jsp" %>
<%@ page import="javax.servlet.http.Cookie" %>
<%@ page import="framework.struts.util.MailSendUtil" %>
<%@ page import="java.util.Properties" %>
<%@ page import="java.io.File" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<!--Cookie check -->
<%
	Cookie cookie[] = request.getCookies();

	Properties props = MailSendUtil.loadConfigXML(application, "/config.properties");
	String name="";
	String mailFrom=props.getProperty("defaultMailFrom");
	
	if((cookie!=null)&&(cookie.length!=0)){
	for(int i=0; i<cookie.length;i++){
		name = cookie[i].getName();
		
		if(name.equals("mailFrom"))
			mailFrom = cookie[i].getValue();
		
		}
	}
	
	// Make Upload Directories
	String fileUploadDir = props.getProperty("fileUploadDir");
	File file = new File(fileUploadDir);
	if(! file.exists()) {
		file.mkdirs();
	}
	
	String tempFileDir = props.getProperty("tempFileDir");
	file = new File(tempFileDir);
	if(! file.exists()) {
		file.mkdirs();
	}
	
	session.removeAttribute("attached.file.message");
%>


<html>
<head>
<title><bean:message key="page.title"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body bgcolor=#FFFFFF text=#000000 >
<center>
<table width="590" border="0" cellpadding="0" cellspacing="0" >
	<tr><td>|<span style="width:10px">&nbsp;</span><a href="./locale.do?region=en">English</a><span style="width:10px">&nbsp;</span>
	|<span style="width:10px">&nbsp;</span><a href="./locale.do?region=ja">Japanese</a><span style="width:10px">&nbsp;</span>|</td></tr>
</table>
<!-- Error Presentation -->
<logic:messagesPresent>
   <bean:message key="errors.header"/>
   <ul>
   <html:messages id="error">
      <li><bean:write name="error"/></li>
   </html:messages>
   </ul><hr>
</logic:messagesPresent>
<table width=590 border=0 cellpadding=0 cellspacing=0>
	<tr>
	  
	  <td>
  <!--contents-->
	  <table width=590 border=0 cellpadding=0 cellspacing=0>
		  <tr>
			<td bgcolor="f4f4f4" height="22">&nbsp;&nbsp;
				<b><bean:message key="page.write.header"/></b>
			</td>
		  </tr>
	  </table>  
	  </td></tr>
	   <tr><td>
<table align="left" width=590 border=0 cellpadding=0 cellspacing=0>
   <bean:message key="upload.notice"/>
 </table>
 </td></tr>
    
	  <!-- write Form  -->
	  <%--   
	  <html:form action="/mailTransfer.do" method="post" enctype="multipart/form-data" 
	  		styleId="UploadForm" target="result" onsubmit="showFrame();">
 	  --%>
	  <html:form action="/mailTransfer.do" method="post" enctype="multipart/form-data" 
	  		styleId="UploadForm">
	  <tr><td>
	  <table border="0" cellpadding="0" cellspacing="1" width="590" bgcolor="BBBBBB">
		  <tr>
			<td width=100 align=center bgcolor="E6ECDE" height="22" class="myForm">
				<bean:message key="mail.from"/>
			</td>
			<td width=490 bgcolor="ffffff" style="padding-left:10" >
				<html:text style="width:300" name="mailForm" property="fromAddress" value="<%=mailFrom%>"/>
			</td>
		  </tr>
		  
		  <tr>
			<td width=100 align=center bgcolor="E6ECDE" height="22" class="myForm">
				<bean:message key="mail.to"/>
			</td>
			<td width=490 bgcolor="ffffff" style="padding-left:10" >
				<html:text style="width:300" name="mailForm" property="toAddress" value=""/>
			</td>
		  </tr>
		  <tr>
			<td width=100 align=center bgcolor="E6ECDE" height="22" class="myForm">
				<bean:message key="mail.title"/>
			</td>
			<td width=490 bgcolor="ffffff" style="padding-left:10" >
				<html:text style="width:300" name="mailForm" property="title"/>
			</td>
		  </tr>
		  <tr>
			<td width=100 align=center bgcolor="E6ECDE" height="22" class="myForm">
				<bean:message key="mail.content"/>
			</td>
			<td width=490 bgcolor="ffffff" style="padding-left:10" >
				<html:textarea rows="10" cols="60" name="mailForm" property="content"/>
			</td>
		  </tr>
		  <tr>
			<td width=100 align=center bgcolor="E6ECDE" height="50" class="myForm" rowspan="2">
				<bean:message key="mail.file"/>
			</td>
			<td width=490 bgcolor="ffffff" style="padding-left:10">
			<html:button property="attachment" onclick="OpenProgressBarWindow();" styleClass="myForm" >
				<bean:message key="button.upload_view.attachement"/>
			</html:button>
			<span style="width:22px">&nbsp;</span>
			<bean:message key="file.upload.size"/>
			<html:text name="mailForm" property="totalSize" style="width:100" readonly="true"/>
			</td>
			</tr>
			<tr><td bgcolor="ffffff">
				<html:textarea rows="4" cols="60" name="mailForm" property="attachedFile" readonly="true"/>
				
			</td>
		  </tr>
		 		
	  </table>
	  </td></tr>
	  <br>
	  <tr><td>
	  <table width=590 border=0 cellpadding=0 cellspacing=0>
		  <tr>
			<td align=center>
			<html:submit property="submit" styleClass="myForm">
				<bean:message key="button.send.mail"/>
			</html:submit>
			&nbsp;
			<html:reset property="reset" styleClass="myForm" onclick="setResetFlg()">
				<bean:message key="button.cancel"/>
			</html:reset>
			<html:hidden property="resetFlg" styleId="resetFlg" styleClass="myForm" value="false" />
		  </tr>
	  </table>	  
	  </html:form>
	  </td>
	</tr>
</table>
<!--  
<div id="resultFrame" style="display:none">  
<iframe height="300" width="600" scrolling="no" name="result" frameborder="0" align="middle">
</iframe>
</div>
-->
</center>
</body>
</html>