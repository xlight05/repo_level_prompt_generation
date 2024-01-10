<%-- 
    Document   : index
    Created on : 26/09/2012, 01:46:18 PM
    Author     : Mauricio Zarate Barrera
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

<%@ page language="java"  contentType="text/html" import="java.util.*"%>
<jsp:include page="/header.jspf" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Institucion :: SICSA</title>
        <s:if test="#session.usuario.tipo != 'profesor'">

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
                        <span class="title">Alumnos</span>
                        <span class="subtitle">Gestión de los recursos de la institución</span>
                    </div>
                    <!-- ENDS title -->

                    <!-- page-content -->
                    <div id="page-content">

                        <h4 class="line-divider">Lista de alumnos 4 ° "A"</h4>
                        <s:property value="message" />
                        <s:actionmessage cssClass="success-box"/>
                        <s:actionerror cssClass="error-box"/>

                        <table>
                            <thead>
                                <tr>
                                    <th><b>ID</b></th>
                                    <th><b>Nombre</b></th>
                                    <th><b>Apellido Paterno</b></th>
                                    <th><b>Apellido Materno</b></th>
                                    <th><b>Matricula</b></th>
                                    <th><b>Carrera</b></th>
                                    <th><b>Operaciones</b></th>
                                </tr>
                            </thead>
                            <tbody>
                                <s:iterator value="lista">
                                    <tr>
                                        <td><s:property value="alumno_id"/></td>
                                        <td><s:property value="nombre"/></td>
                                        <td><s:property value="a_paterno"/></td>
                                        <td><s:property value="a_materno"/></td>
                                        <td><s:property value="matricula"/></td>
                                        <td><s:property value="carrera"/></td>
                                        <td>
                                            <s:url action="alumnoEditar" id="ediReg">
                                                <s:param name="alumno_id">
                                                    <s:property value="alumno_id"/>
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
                            <s:a href="alumnoNuevo.action"><img src="<s:url value="/img/mono-icons/plus32.png"/>" alt="Agregar registro" title="Agregar Registro"/></s:a>

                        </p>

                        <s:url var="remoteurl" action="alumnoDemo"/>
                        <sjg:grid
                            id="gridtable"
                            caption="Customer Examples"
                            dataType="json"
                            href="%{remoteurl}"
                            pager="true"
                            gridModel="gridModel"
                            rowList="10,15,20"
                            rowNum="15"
                            rownumbers="true" 
                            >
                            <sjg:gridColumn name="alumno_id" index="alumno_id" title="ID" formatter="integer" sortable="false"/>
                            <sjg:gridColumn name="nombre" index="nombre" title="Nombre" sortable="true"/>
                            <sjg:gridColumn name="a_paterno" index="a_paterno" title="Apellido Paterno" sortable="true"/>
                            <sjg:gridColumn name="a_materno" index="a_materno" title="Apellido Materno" sortable="true"/>
                            <sjg:gridColumn name="matricula" index="matricula" title="Matricula" sortable="true"/>
                            <sjg:gridColumn name="carrera" index="carrera" title="Carrera" sortable="true"/>
                        </sjg:grid>
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