<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html" import="java.util.*"%>

<html>
    <head>
        <title>SICSA :: Check</title>
    </head>
    <body>
        DEBUG:
        <p><b>Id de Usuario:</b> <s:property value="#session.usuario.usuario_id"/></p>
        <p><b>Contraseña:</b> <s:property value="#session.usuario.password"/></p>
        <p><b>Tipo de Usuario:</b> <s:property value="#session.usuario.tipo"/></p>
        <s:if test="#session.usuario.tipo != 'admin' || #session.usuario.tipo != 'profesor'">
            <jsp:forward page="login.jsp" />  
        </s:if>
    </body>
</html>