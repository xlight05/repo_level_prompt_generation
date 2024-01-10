<%-- 
    Document   : Profesor Index
    Created on : 26/09/2012, 01:46:18 PM
    Author     : Mauricio Zarate Barrera
--%>

<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<jsp:include page="/header.jspf" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mi Cuenta : SICSA</title>

        <s:if test="#session.usuario.tipo != 'profesor'">
            <jsp:forward page="login.jsp" />  
        </s:if>

        <sj:head jqueryui="true" jquerytheme="smoothness"/>

    </head>
    <body>

        <!-- HEADER -->
        <s:div id="header">
            <!-- wrapper-header -->
            <s:div class="wrapper">
                <s:a href="index.html"><img id="logo" src="<s:url value="/img/logo.png"/>" alt="SICSA" /></s:a>
                    <!-- search -->
                <s:div class="top-search">
                    <form  method="get" id="searchform" action="#">
                        <s:div>
                            <s:textfield type="text" value="Buscar..." name="s" id="s" onfocus="defaultInput(this)" onblur="clearInput(this)" />
                            <s:submit type="submit" id="searchsubmit" value=" " />
                        </s:div>
                    </form>
                </s:div>
                <!-- ENDS search -->
            </s:div>
            <!-- ENDS wrapper-header -->					
        </s:div>
        <!-- ENDS HEADER -->


        <!-- Menu -->
        <s:div id="menu">
            <s:url var="menuJsp" value="/admin/menu.action"/>
            <sj:div href="%{menuJsp}">Obteniendo datos de menú</sj:div>
        </s:div>
        <!-- ENDS Menu -->
        <!-- MAIN -->
        <div id="main">
            <!------------------------- MAIN ----------------------------->
            <div id="wrapper">

                <div id="content">

                    <div id="page-title">
                        <span class="title">Mi Cuenta</span>
                        <span class="subtitle"><b>Ultimo inicio de sessión: </b><%= new Date(session.getLastAccessedTime())%></span>
                    </div>


                    <div id="page-content">
                        <h2>Bienvenido, <s:property value="#session.datosUsuario.nombre"/> <s:property value="#session.datosUsuario.a_paterno"/> <s:property value="#session.datosUsuario.a_materno"/></h2>
                        <div class="one-half">
                            <h6 class="line-divider">Información de la cuenta</h6>
                            <p>
                                <b>Id de Usuario:</b> <s:property value="#session.usuario.usuario_id"/><br />
                                <b>Contraseña:</b> <s:property value="#session.usuario.password"/><br />
                                <b>Telefono:</b> <s:property value="#session.datosUsuario.telefono"/><br />
                                <b>Edad:</b> <s:property value="#session.datosUsuario.edad"/><br />
                                <b>Dirección:</b> <s:property value="#session.datosUsuario.direccion"/><br />
                            </p>
                        </div>
                        <div class="one-half last">
                            <h6 class="line-divider">Funciones Destacadas</h6>
                            <p>Nada que mostrar aún.</p>
                        </div>

                        <div class="clear "></div>

                    </div>
                </div>
            </div>

        </div>
        <!-- ENDS MAIN -->

        <!-- Twitter -->
        <s:div id="twitter">
            <s:div class="wrapper">
                <s:a href="#" id="prev-tweet"></s:a>
                <s:a href="#" id="next-tweet"></s:a>
                <img id="bird" src="<s:url value="/img/bird.png"/>" alt="Tweets" />
                <s:div id="tweets">
                    <ul class="tweet_list"></ul>
                </s:div>
            </s:div>
        </s:div>
        <!-- ENDS Twitter -->


        <!-- FOOTER -->
        <s:div id="footer">
            <s:url var="footerJsp" value="/footerJsp.jsp"/>
            <sj:div href="%{footerJsp}">Cargando pie de página</sj:div>
        </s:div>
        <!-- ENDS FOOTER -->


        <!-- Bottom -->
        <s:div id="bottom" >
            <s:url var="bottomJsp" value="/bottomJsp.jsp"/>
            <sj:div id="bottom" href="%{bottomJsp}">Cargando el resto del contenido</sj:div>

        </s:div>
        <!-- ENDS Bottom -->
    </body>
</html>