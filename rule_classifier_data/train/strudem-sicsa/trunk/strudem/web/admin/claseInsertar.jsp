<%-- 
    Document   : registro
    Created on : 03-abr-2012, 12:14:42
    Author     : Alumno
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
        <s:if test="#session.usuario.tipo != 'admin'">
            <jsp:forward page="login.jsp" />  
        </s:if>
        <s:head />
        <sj:head jqueryui="true" jquerytheme="smoothness"/>
        <script language="JavaScript" src="${pageContext.request.contextPath}/struts/utils.js" type="text/javascript"></script>
        <script language="JavaScript" src="${pageContext.request.contextPath}/struts/xhtml/validation.js" type="text/javascript"></script>
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
            <sj:div href="%{menuJsp}">Cargando menú</sj:div>
        </s:div>
        <!-- ENDS Menu -->
        <!-- MAIN -->
        <div id="main">
            <!------------------------- MAIN ----------------------------->
            <div class="wrapper">

                <div id="content">

                    <!-- title -->
                    <div id="page-title">
                        <span class="title">Configuración</span>
                        <span class="subtitle">Información detallada sobre los usuarios del sistema</span>
                    </div>
                    <!-- ENDS title -->

                    <!-- page-content -->
                    <div id="page-content">
                        <s:a cssClass="link-button" href="javascript: history.go(-1)"><span>Regresar</span></s:a>

                        <s:form id="formSelectReload" action="echo" theme="simple" cssClass="yform">
                            <s:actionerror cssClass="error-box"/>
                            <s:fielderror  cssClass="error-box"/>
                            <fieldset>
                                <s:select label="***Profesor" name="profesor_id" list="profesores"  />
                                <legend>Nueva Clase</legend>
                                <div class="type-text">
                                    <!-- SELECT PRIMARIO ANTECEDENTE -->
                                    <label for="profesor">Profesor: </label>
                                    <s:url var="remoteurl" action="claseNuevo"/>
                                    <sj:select
                                        href="%{remoteurl}"
                                        id="profesor"
                                        onChangeTopics="reloadsecondlist"
                                        name="profesor_id"
                                        list="profesores"
                                        emptyOption="true"
                                        headerKey="-1"
                                        headerValue="Selecciona un profesor"/>
                                </div>
                                <div class="type-text">
                                    <!-- SELECT PRIMARIO ANTECEDENTE -->
                                    <label for="periodo">Periodo: </label>
                                    
                                    <sj:select
                                        href="%{remoteurl}"
                                        id="periodo"
                                        onChangeTopics="reloadsecondlist"
                                        name="periodo_id"
                                        list="periodos"
                                        emptyOption="true"
                                        headerKey="-1"
                                        headerValue="Selecciona un periodo"/>
                                </div>
                                <div class="type-text">
                                    <!-- SELECT PRIMARIO ANTECEDENTE -->
                                    <label for="edificio">Edificio: </label>
                                    
                                    <sj:select
                                        href="%{remoteurl}"
                                        id="edificio"
                                        onChangeTopics="reloadsecondlist"
                                        name="edificio_id"
                                        list="edificios"
                                        
                                        emptyOption="true"
                                        headerKey="-1"
                                        headerValue="Selecciona un edificio"/>
                                </div>
                                <div class="type-text">
                                    <!-- SELECT PRIMARIO ANTECEDENTE -->
                                    <label for="aula">Aula: </label>
                                    
                                    <sj:select
                                        href="%{remoteurl}"
                                        id="aula"
                                        onChangeTopics="reloadsecondlist"
                                        name="aula_id"
                                        list="aulas"
                                       
                                        emptyOption="true"
                                        headerKey="-1"
                                        headerValue="Selecciona una aula"/>
                                </div>
                                <div class="type-text">
                                    <!-- SELECT PRIMARIO ANTECEDENTE -->
                                    <label for="periodo">Periodo: </label>
                                    
                                    <sj:select
                                        href="%{remoteurl}"
                                        id="periodo"
                                        onChangeTopics="reloadsecondlist"
                                        name="periodo_id"
                                        list="periodos"
                                       
                                        emptyOption="true"
                                        headerKey="-1"
                                        headerValue="Selecciona un periodo"/>
                                </div>
                                <div class="type-text">
                                    <!-- SELECT PRIMARIO ANTECEDENTE -->
                                    <label for="cuatrimestre">Periodo: </label>
                                    
                                    <sj:select
                                        href="%{remoteurl}"
                                        id="cuatrimestre"
                                        onChangeTopics="reloadsecondlist"
                                        name="cuatrimestre_id"
                                        list="%{cuatrimestres}"
                                        emptyOption="true"
                                        headerKey="-1"
                                        headerValue="Selecciona un cuatrimestre"/>
                                </div>
                                <div class="type-text">
                                    <!-- SELECT PRIMARIO ANTECEDENTE -->
                                    <label for="especialidad">Especialidad: </label>
                                    
                                    <sj:select
                                        href="%{remoteurl}"
                                        id="especialidad"
                                        onChangeTopics="reloadsecondlist"
                                        name="especialidad_id"
                                        list="especialidades"
                                        emptyOption="true"
                                        headerKey="-1"
                                        headerValue="Selecciona una especialidad"/>
                                </div>
                                <div class="type-text">
                                    <!-- SELECT PRIMARIO ANTECEDENTE -->
                                    <label for="carrera">Carrera: </label>
                                    
                                    <sj:select
                                        href="%{remoteurl}"
                                        id="carrera"
                                        onChangeTopics="reloadsecondlist"
                                        name="carrera_id"
                                        list="carreras"
                                        
                                        emptyOption="true"
                                        headerKey="-1"
                                        headerValue="Selecciona una carrera"/>
                                </div>
                                <div class="type-text">
                                    <!-- SELECT PRIMARIO ANTECEDENTE -->
                                    <label for="grupo">Grupo: </label>
                                    
                                    <sj:select
                                        href="%{remoteurl}"
                                        id="grupo"
                                        onChangeTopics="reloadsecondlist"
                                        name="grupo_id"
                                        list="grupos"
                                        
                                        emptyOption="true"
                                        headerKey="-1"
                                        headerValue="Selecciona un grupo"/>
                                </div>
                                <div class="type-text">
                                     <!-- SELECT SECUNDARIO PRECEDENTE -->
                                    <label for="echo">Framework: </label>
                                    <sj:select
                                        href="%{remoteurl}"
                                        id="selectWithReloadTopic"
                                        formIds="formSelectReload"
                                        reloadTopics="reloadsecondlist"
                                        name="echo" list="reloadList"
                                        emptyOption="true"
                                        headerKey="-1"
                                        headerValue="Please Select a Framework"/>
                                </div>
                                <div class="type-button">
                                    <sj:submit
                                        targets="result"
                                        value="Guardar"
                                        indicator="indicator"/>
                                    <img id="indicator" src="../images/indicator.gif" alt="Cargando..." style="display:none"/>
                                </div>
                            </fieldset>
                           
<div id="result" class="result ui-widget-content ui-corner-all">Submit a form.</div>
                        </s:form>
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