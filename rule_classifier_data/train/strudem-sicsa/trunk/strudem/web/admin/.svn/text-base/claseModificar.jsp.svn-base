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
                        <span class="title">Configuración</span>
                        <span class="subtitle">Información detallada sobre los usuarios del sistema</span>
                    </div>
                    <!-- ENDS title -->

                    <!-- page-content -->
                    <div id="page-content">
                        <img id="indicador" src="/strudem/images/indicator.gif" alt="Cargando..." style="display:none"/>
                        <h4 class="line-divider">Modificar un registro de usuario</h4>

                        <s:fielderror cssClass="error-box" />
                        <s:a cssClass="link-button" href="javascript: history.go(-1)"><span>Regresar</span></s:a>
                        <s:iterator value="lista">

                            <s:form action="usuarioModificar" theme="xhtml">
                                <fieldset> 
                                    <s:textfield label="ID" name="usuario_id" readonly="true"><s:property value="usuario_id"/></s:textfield>
                                    <s:textfield label="Usuario" name="usuario" required="true"><s:property value="usuario"/></s:textfield>
                                    <s:textfield label="Contraseña" name="password" required="true"><s:property value="password"/></s:textfield>
                                    <s:select label="Tipo de Usuario" name="tipo"
                                              headerValue="-- Seleccionar opcion --"
                                              headerKey="0" list="{'admin','profesor'}"><s:property value="tipo"/></s:select>

                                    <sj:submit name="Guardar" validate="true" value="Guardar" indicator="indicator" loadingText="Validando..." cssClass="link-button"/>
                                </fieldset> 
                            </s:form>

                        </s:iterator>
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