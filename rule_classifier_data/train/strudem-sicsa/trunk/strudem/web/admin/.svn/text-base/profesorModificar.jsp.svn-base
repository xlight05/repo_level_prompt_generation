<%-- 
    Document   : usuarioModificar
    Created on : 25/10/2012, 12:34:39 PM
    Author     : Mauricio
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ page language="java"  contentType="text/html" import="java.util.*"%>
<jsp:include page="/header.jspf" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Configuracion :: SICSA</title>

        <sj:head jqueryui="true" jquerytheme="smoothness" compressed="true" loadFromGoogle="true"/>
    </head>
    <body class="home">

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
            <sj:div href="%{menuJsp}">Cargando datos de menu</sj:div>
        </s:div>
        <!-- ENDS Menu -->
        <!-- MAIN -->
        <div id="main">
            <!------------------------- MAIN ----------------------------->
            <div class="wrapper">

                <div id="content">

                    <!-- title -->
                    <div id="page-title">
                        <span class="title">Institución</span>
                        <span class="subtitle">Gestión de los recursos de la institución</span>
                    </div>
                    <!-- ENDS title -->

                    <!-- page-content -->
                    <div id="page-content">

                        <h4 class="line-divider">Modificar un registro de profesor</h4>
                        <s:fielderror cssClass="error-box" />

                        <s:iterator value="lista">
                            <s:form action="profesorModificar">
                                <s:textfield label="ID" name="profesor_id" ><s:property value="profesor_id"/></s:textfield>
                                <s:textfield label="Nombre" name="nombre"><s:property value="nombre"/></s:textfield>
                                <s:textfield label="Apellido Paterno" name="a_paterno"><s:property value="a_paterno"/></s:textfield>
                                <s:textfield label="Apellido Materno" name="a_materno"><s:property value="a_materno"/></s:textfield>
                                <s:textfield label="Usuario" name="usuario_id"><s:property value="usuario_id"/></s:textfield>
                                <s:textfield label="Edad" name="edad"><s:property value="edad"/></s:textfield>
                                <s:textfield label="Telefono" name="telefono"><s:property value="telefono"/></s:textfield>
                                <s:textfield label="Direccion" name="direccion"><s:property value="direccion"/></s:textfield>
                                <s:submit name="Guardar" value="Guardar" cssClass="link-button"/>
                            </s:form>
                        </s:iterator>

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