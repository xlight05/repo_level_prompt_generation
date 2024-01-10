<%-- 
    Document   : template para copiar
    Created on : 26/09/2012, 01:46:18 PM
    Author     : Mauricio Zarate Barrera
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
        <title>SICSA</title>
        <s:if test="#session.usuario.tipo != 'admin'">
            <jsp:forward page="login.jsp" />  
        </s:if>
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
                        <span class="title">Seccion Principal</span>
                        <span class="subtitle">Subtitulo de la seccion</span>
                    </div>
                    <!-- ENDS title -->

                    <!-- page-content -->
                     <div id="page-content">

                        <h4 class="line-divider">Lista de todos los profesores </h4>
                        
                        <s:actionmessage cssClass="success-box"/>
                        <s:actionerror cssClass="error-box"/>

                        <table>
                            <thead>
                                <tr>
                                    <th><b>ID_Profesor</b></th>
                                    <th><b>Nombre</b></th>
                                    <th><b>Usuario</b></th>
                                    <th><b>Edad</b></th>
                                    <th><b>Telefono</b></th>
                                    <th><b>Direccion</b></th>
                                    <th><b>Operaciones</b></th>
                                </tr>
                            </thead>
                            <tbody>
                                <s:iterator value="lista">
                                    <tr>
                                        <td><s:property value="profesor_id"/></td>
                                        <td><s:property value="nombre"/> <s:property value="a_paterno"/> <s:property value="a_materno"/></td>
                                        <td><s:property value="usuario_id"/></td>
                                        <td><s:property value="edad"/></td>
                                        <td><s:property value="telefono"/></td>
                                        <td><s:property value="direccion"/></td>

                                        <td>
                                            <s:url action="profesorEditar" id="ediReg">
                                                <s:param name="profesor_id">
                                                    <s:property value="profesor_id"/>
                                                </s:param>
                                            </s:url>

                                            <s:a href="%{ediReg}"><img src="<s:url value="/img/mono-icons/notepencil32.png"/>" alt="Editar registro" title="Editar Registro" /></s:a>
                                            <s:a href="#"><img src="<s:url value="/img/mono-icons/stop32.png"/>" alt="Eliminar registro" title="Eliminar Registro" /></s:a>
                                            </td>
                                        </tr>
                                </s:iterator>
                            </tbody>
                            <tr>
                            </tr>
                        </table>
                        <p>
                            <s:a href="profesorNuevo.action"><img src="<s:url value="/img/mono-icons/plus32.png"/>" alt="Agregar registro" title="Agregar Registro"/></s:a>

                            </p>
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
            <sj:div id="bottom" href="%{bottomJsp}">Hasta abajo</sj:div>

        </s:div>
        <!-- ENDS Bottom -->
    </body>
</html>