<%-- 
    Document   : Busqueda
    Created on : 06-oct-2012, 21:58:10
    Author     : seter
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>busqueda de alumno!</h1>
        <s:form action="usuarioBusqueda">
            <s:textfield name="usuario_id" label="Clave" />
        <s:submit value="Aceptar" name="Aceptar"/>

        </s:form>
    </body>
</html>
