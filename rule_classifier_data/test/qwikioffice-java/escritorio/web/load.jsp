<%--
    Document   : index
    Created on : 30-may-2010, 18:13:54
    Author     : rcp
--%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="java.io.*,java.net.*,rcp.jos.core.os"%>

<%

/*
 * Carga de los *.js asociados a los módulos (aplicaciones) del escritorio.
 */
 

  String moduleId       = request.getParameter("moduleId");

  os.loadModule(response.getWriter(), moduleId);
  /*

  String contextPath    = "http://localhost:8080/escritorio";
  String fileURL        ="";
  URL uc                = null;
  URLConnection cnx     = null;
  String line           ="";

  System.out.println(request.getParameter("moduleId"));
  System.out.println(contextPath);


  if (null==moduleId) moduleId=" ";

  if (moduleId.equals("demo-acc")){
      fileURL = contextPath+"/system/modules/acc-win/acc-win-override.js";
  }

  if (moduleId.equals("demo-grid")){
       fileURL = contextPath+"/system/modules/grid-win/grid-win-override.js";
   }

   if (moduleId.equals("demo-layout")){
       fileURL = contextPath+"/system/modules/layout-win/layout-win-override.js";
   }

  if (moduleId.equals("qo-preferences")){
       fileURL = contextPath+"/system/modules/qo-preferences/qo-preferences-override.js";
  }

   if (moduleId.equals("demo-bogus")){
       fileURL = contextPath+"/system/modules/bogus/bogus-win/bogus-win-override.js";
   }
   if (moduleId.equals("demo-tabs")){
       fileURL = contextPath+"/system/modules/tab-win/tab-win-override.js";
   }

  System.out.println(fileURL);

  if (fileURL != null){
      
     uc = new URL(fileURL);
     cnx = uc.openConnection();


     BufferedReader a  = new BufferedReader(new InputStreamReader(cnx.getInputStream(),"UTF-8"));


     while ((line=a.readLine())!=null)
     out.println(line);
  }*/
%>
