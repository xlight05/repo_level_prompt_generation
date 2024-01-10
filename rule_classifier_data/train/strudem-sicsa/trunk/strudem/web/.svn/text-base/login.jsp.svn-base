<%-- 
    Document   : login
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
        <title>Login : SICSA</title>
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
            <sj:div href="%{menuJsp}">Cargando menú</sj:div>
        </s:div>
        <!-- ENDS Menu -->
        <!-- MAIN -->
        <div id="main">
            <!------------------------- MAIN ----------------------------->
            <!-- wrapper-main -->
            <div class="wrapper">

                <!-- content -->
                <div id="content">

                    <!-- title -->
                    <div id="page-title">
                        <span class="title">Mi Cuenta</span>
                        <span class="subtitle">Por favor, inicie sessión para poder continuar</span>
                    </div>
                    <!-- ENDS title -->

                    <!-- column (left)-->
                    <div class="one-column">
                        <!-- form -->
                        <h2>Iniciar Sessión</h2>
                        
                        <div class="form-poshytip">
                            <s:form action="login-check" namespace="/" method="POST" >

                                <s:actionerror cssClass="error-box"/>
                                <s:fielderror  cssClass="error-box"/>


                                <s:textfield name="usr" label="Nombre de Usuario"/>
                                <s:password name="pas" label="Contraseña"/>
                                <p><s:submit id="submit" value="Acceder" align="center"/></p>
                            </s:form>
                        </div>
                        <!-- ENDS form -->
                    </div>
                    <!-- ENDS column -->

                    <!-- column (right)-->
                    <div class="one-column">
                        <!-- content -->
                        <p><img src="/strudem/images/login_side.png" alt="side"></p>
                        <p>Equipo 1.</p>					
                        <p>Universidad Tecnológica Emiliano Zapata<br/>
                            Sistemas Informáticos<br/>
                            31 de Octubre de 2012<br/>
                            <a href="mailto:morrisgrill@hotmail.com">Sugerencias o comentarios</a></p>
                        <!-- ENDS content -->
                    </div>
                    <!-- ENDS column -->							

                </div>
                <!-- ENDS content -->

            </div>
            <!-- ENDS wrapper-main -->

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