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
        <title>SICSA</title>
        <s:if test="#session.usuario.tipo != 'admin'">
            <jsp:forward page="login.jsp" />  
        </s:if>
        <sj:head jqueryui="true" jquerytheme="smoothness" compressed="true" loadFromGoogle="true" defaultIndicator="indicador"/>
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
            <sj:div href="%{menuJsp}">Cargando contenido de menu</sj:div>
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
                        <span class="subtitle">Información académica de los alumnos de la universidad</span>
                    </div>
                    <!-- ENDS title -->

                    <!-- page-content -->
                    <div id="page-content">
                        <img id="indicador" src="/strudem/images/indicator.gif" alt="Cargando..." style="display:none"/>
                        <h4 class="line-divider">Agregar un nuego alumno</h4>

                        <s:fielderror cssClass="error-box" />
                        <s:a cssClass="link-button" href="javascript: history.go(-1)"><span>Regresar</span></s:a>

                        <s:form action="alumnoInsertar" theme="xhtml">
                            <fieldset> 
                                <s:textfield label="Nombre" name="nombre" required="true"/>
                                <s:textfield label="Apellido Paterno" name="a_paterno" required="true"/>
                                <s:textfield label="Apellido Materno" name="a_materno" required="true"/>
                                <s:textfield label="Matrícula" name="matricula" required="false"/>
                                <s:select label="Carrera" name="carrera_id"
                                          headerValue="-- Seleccionar opcion --"
                                          headerKey="1" list="listaCuatrimestre_id"></s:select>

                                <sj:submit name="Guardar" validate="true" value="Guardar" indicator="indicator" loadingText="Validando..." cssClass="link-button"/>
                            </fieldset> 
                        </s:form>


                        <img id="indicator" src="/strudem/images/indicator.gif" alt="Cargando..." style="display:none"/>
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