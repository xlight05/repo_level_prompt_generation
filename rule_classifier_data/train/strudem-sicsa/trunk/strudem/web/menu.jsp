<%-- 
    Document   : menu
    Created on : 9/10/2012, 06:57:25 PM
    Author     : Mauricio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menu Global</title>
        <s:head />
    </head>
    <body>

        <!-- ENDS menu-holder -->
        <div id="menu-holder">
            <!-- wrapper-menu -->
            <div class="wrapper">
                <!-- Navigation -->
                <ul id="nav" class="sf-menu">
                    <li class="current-menu-item"><s:a href="/strudem/">Inicio<span class="subheader">Invitado</span></s:a></li>
                    <li><s:a href="/strudem/admin/admin.action">Perfíl<span class="subheader">Mi Cuenta</span></s:a>
                        <ul>
                            <li><s:a href="/strudem/login.action"><span> Iniciar Sessión</span></s:a></li>

                        </ul>
                    </li>
                </ul>
                <!-- Navigation -->
            </div>
            <!-- wrapper-menu -->
        </div>
        <!-- ENDS menu-holder -->
    </body>
</html>
