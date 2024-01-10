<%-- 
    Document   : login
    Created on : 03-mar-2010, 20:31:46
    Author     : rcp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="rcp.jos.core.os"%>

   <%

   String _modulo = request.getParameter("module");
   String _usuario= request.getParameter("user");
   String _password= request.getParameter("pass");
   String _grupo = request.getParameter("group");


   if (_usuario!=null){
     //out.println(osix._login(_usuario, _password, _grupo));
       out.println("{success:true, sessionId:'AAAABBBCCDDEIIFK30303920'}");
       request.getSession().setAttribute("_userID", "3");
       request.getSession().setAttribute("_groupID", "3");
   } else {
     out.println("{success: false}");
  }
   %>
