<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<jsp:useBean id="_dato" scope="page" class="com.mrwlogistica.model.BEANConsulta" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
   <html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
        java.io.FileOutputStream o = new java.io.FileOutputStream("/tmp/upload.png");
        int i = -1;
         while ((i=request.getInputStream().read())!=-1){
             o.write(i);
         }

        %>
    <h1>Backend Is Running.....</h1>
    <form action="archivo.jsp" enctype="multipart/form-data" method="post">
        <input type="file" name="ff"/>
        <input type="submit" value="Go.."/>
    </form>
    </body>
</html>
