<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="framework.struts.form.MailForm" %>
<%@ page import="framework.struts.model.FileInfo" %>
<%@ page import="framework.struts.util.FileUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="java.io.FileNotFoundException" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ include file="header.jsp" %>
<%

	MailForm mailForm = (MailForm)session.getAttribute("attached.file.message");

	String resetFlg = request.getParameter("resetFlg");
	if(mailForm != null && "true".equals(resetFlg)) {
		List fileList = mailForm.getFileList();
		if(fileList != null) {
			for(int i = 0; i < fileList.size(); i++) {
				FileInfo fileInfo = (FileInfo) fileList.get(i);
				String fileName = fileInfo.getTempFileName();
				try {
					FileUtils.delete(fileName);
				} catch(FileNotFoundException fnfe) {
					
				}
			}
		}
		session.removeAttribute("attached.file.message");
	}

	String attachedFile=""; 
	if(mailForm !=null){
		String[] str = mailForm.getAttachedFiles();
		StringBuffer sf = new StringBuffer();
 		
 		for(int j=0; j<str.length; j++){
 			sf.append(str[j]);
 			sf.append("\r\n");
 		}
		attachedFile = sf.toString();
	}
		
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="javascript">
<!--
        function updateStatusMessage(message){
       		/* DWRUtil.setValue(id, value) finds the element with the id specified in the first parameter
        	and alters its contents to be the value in the second parameter. */
          DWRUtil.setValue('updateStatusMsg', message);
		}
		
//-->
</script>
<title><bean:message key="progressbar.head.title"/></title>
 </head>
<body onload="javascript:initUploadForm(FileUploadForm);" >
   
   <html:form action="/processUpload" enctype="multipart/form-data" method="post" styleId="FileUploadForm" onsubmit="startProgress();" >

  <bean:message key="progressbar.file.upload"/>
  <bean:message key="progressbar.body.title"/> 
   <p>   
   <table align="center">
   <tr><td colspan="2"><input type="hidden" name="attachedFile" value="<%=attachedFile %>"></td></tr></table>
   	<div id="fileList0" style="display:block;" ><table align="center"><tr><td width="100" align="center" bgcolor="E6ECDE" height="15" class="myForm"><bean:message key="mail.file" /> 1</td>
   		<td width="490" bgcolor="ffffff" style="padding-left:10">
   		<html:file style="width:400" name="fileForm" property="fileList[0]" styleClass="upload_input" /></td></tr></table></div>
   	
   		<div id="fileList1" style="display:none;" ><table align="center"><tr><td width="100" align="center" bgcolor="E6ECDE" height="15" class="myForm"><bean:message key="mail.file"/> 2</td>
   		<td width="490" bgcolor="ffffff" style="padding-left:10"><html:file style="width:400" name="fileForm" property="fileList[1]" styleClass="upload_input" /></td></tr></table></div>
   		<div id="fileList2" style="display:none;"><table><tr><td width="100" align="center" bgcolor="E6ECDE" height="15" class="myForm"><bean:message key="mail.file"/> 3</td>
   		<td width="490" bgcolor="ffffff" style="padding-left:10"><html:file style="width:400" name="fileForm" property="fileList[2]" styleClass="upload_input" /></td></tr></table></div>
   		<div id="fileList3" style="display:none;"><table><tr><td width="100" align="center" bgcolor="E6ECDE" height="15" class="myForm"><bean:message key="mail.file"/> 4</td>
   		<td width="490" bgcolor="ffffff" style="padding-left:10"><html:file style="width:400" name="fileForm" property="fileList[3]" styleClass="upload_input" /></td></tr></table></div>
   		<div id="addFileButton" style="display:block;"><table><tr><td align="left" ><html:button value="+1 upload" property="button" onclick="javascript:addFileForm();" styleClass="myForm"/></td></tr></table></div>
		<table align='center'>
   	<tr><td align='center'><input type='hidden' name='orgFileName' />
			<html:submit property='action' styleClass='myForm' onclick="javascript:checkNumberOfUpload();" >
				<bean:message key='button.send' />
			</html:submit>
			&nbsp;
			<html:button property='stop' styleClass='myForm' onclick='javascript:stopProgress();'>
				<bean:message key='button.stop'/>
			</html:button></td>

	</tr>
</table>

 
<!-- Progress Bar Start -->

<p><span align='left' id='updateStatusMsg'></span>
	<div id="progressBar"  class="layer1" style="visibility: hidden;">
		<div id="theMeter">
			<div id="progressBarText"></div>
			<div id="progressBarBox">
				<div id="progressBarBoxContent"></div>
			</div>
			<div id="fileName"></div>
			
			<div class="data" id="status" style="visibility:hidden">		
				<div id="numfiles" class="row">
					<span class="label"><bean:message key="progressbar.status.files"/></span>
					<span class="value"><font id="files"></font></span>
				</div>
				<div id="position" class="row">
					<span class="label"><bean:message key="progressbar.status.position"/></span>
					<span class="value"><font id="current"></font></span>
				</div>
				<div id="uploadspeed" class="row">
					<span class="label"><bean:message key="progressbar.status.speed"/></span>
					<span class="value"><font id="speed"></font></span>
				</div>

				<div id="timespent" class="row">
					<span class="label"><bean:message key="progressbar.status.spent"/></span>
					<span class="value"><font id="time"></font></span>
				</div>
				<div id="timeleft" class="row">
					<span class="label"><bean:message key="progressbar.status.left"/></span>
					<span class="value"><font id="left"></font></span>
				</div>
			</div>
		</div>
	</div> 

 <!-- Progress Bar End -->

	  <table align="center">
   <tr>
			<td width=100 align=center bgcolor="E6ECDE" height="50" class="myForm">
			
				<bean:message key="progressbar.attached.file" />
			</td>
			<td width=490 bgcolor="eeeeee" style="padding-left:10">
			<table border="1" width="100%" height="50" bordercolor="E6ECDE">
			<tr><td>		
  				<table align="center" border="0" width="490" height="50">
<%
	
	if(mailForm !=null){
		int totalSize = mailForm.getTotalSize();
%>
		<input type="hidden" name="totalSize" value="<%=totalSize %>"/>
<%
		List fileList = mailForm.getFileList();
 		if(fileList!=null){
 			
		for(int i=0; i<fileList.size();i++){
			FileInfo fileInfo = (FileInfo)fileList.get(i);
			String fileIndex = "fileIndex["+i+"]";
			
%>
			<tr valign="top">	<td width="50" height="10">				
						<html:checkbox styleId="<%=fileIndex%>" name="fileForm" property="<%=fileIndex%>" ></html:checkbox></td>
						<td class="file_list"><%=fileInfo.getFileName()%></td>
			</tr>
<%			
		}//end for
		
 		}else{
%>
			<tr valign="top">	<td height="10">				
						<%=attachedFile %></td>
			</tr>
<% 			
		}//end if~else
	}
%>				

					</table>
					</td></tr>
				</table>
				</td></tr>
	</table>
	<div id="checkbox">
	 <table align="center">
		  <tr>
		  <td width=100 align=center height="22"></td>
		  <td align="left"><html:button property="selectAll" onclick="javascript:set(1);" styleClass="myForm">
		  <bean:message key="button.select.all"/></html:button>
		  <html:button property="unSelectAll" onclick="javascript:set(0);" styleClass="myForm">
		  <bean:message key="button.unselect.all"/></html:button>
		  <html:submit property="action" styleClass="myForm">
		  <bean:message key="button.delete.file"/></html:submit>
		  </td>
		  
		  </tr>
   </table></div>
   <table border="0" width="590">
   	<tr><td align="center"><html:button property="close" styleClass="myForm" onclick="javascript:window.close()">
				<bean:message key="button.close"/>
			</html:button></td></td></tr>
   </table>
   
 </html:form>  
  </body>
</html>   