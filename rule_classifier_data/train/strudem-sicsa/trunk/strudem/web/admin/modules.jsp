<%-- 
    Document   : Administrador Index
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
        <title>: SICSA</title>

        <s:if test="#session.usuario.tipo != 'admin'">
            <jsp:forward page="login.jsp" />  
        </s:if>

        <sj:head jqueryui="true" jquerytheme="smoothness"/>
        <script type="text/javascript">
            $.subscribe('beforeDiv', function(event,data) {
                alert('Before request ');
            });
            $.subscribe('completeDiv', function(event,data) {
                if(event.originalEvent.status == "success")
                {
                    $('#resultnormal').append('<br/><br/><strong>Completed request '+event.originalEvent.request.statusText+' completed with '+event.originalEvent.status+ '.</strong><br/>Status: '+event.originalEvent.request.status);
                }
            });
            $.subscribe('errorDiv', function(event,data) {
                $('#resulterror').html('<br/><br/><strong>Error request '+event.originalEvent.request.statusText+' completed with '+event.originalEvent.status+ '.</strong><br/>Status: '+event.originalEvent.request.status);
            });
        </script>  
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

        <!-- ENDS menu-holder -->
        <div id="menu-holder">
            <!-- wrapper-menu -->
            <div class="wrapper">
                <!-- Navigation -->
                <ul id="nav" class="sf-menu">
                    <li class="current-menu-item"><s:a href="admin.action">Inicio<span class="subheader">Admin</span></s:a></li>
                    <li><s:a href="institucion.action">Institución<span class="subheader">Administración</span></s:a>
                            <ul>

                                <li><s:a href="aulaLista.action"><span> Aulas</span></s:a></li>
                            <li><s:a href="edificioLista.action"><span> Edificios</span></s:a></li>
                            <li><s:a href="ubicacionLista.action"><span> Ubicaciones</span></s:a></li>
                            <li><s:a href="cuatrimestreLista.action"><span> Cuatrimestres</span></s:a></li>
                            <li><s:a href="materiaLista.action"><span> Materias</span></s:a></li>
                            <li><s:a href="unidadLista.action"><span> Unidades</span></s:a></li>
                            <li><s:a href="especialidadLista.action"><span> Especialidades</span></s:a></li>
                            <li><s:a href="carreraLista.action"><span> Carreras</span></s:a></li>
                            <li><s:a href="periodoLista.action"><span> Periodos</span></s:a></li>
                            </ul>

                        </li>
                        <li><s:a href="/admin/profesor.action">Profesor<span class="subheader">Panel Docente</span></s:a>
                            <ul>

                                <li><s:a href="#"><span> Mi Horario</span></s:a></li>
                            <li><s:a href="HorariosTodosLista.action"><span> Horarios</span></s:a></li>
                            <li><s:a href="#"><span> Mis Grupos</span></s:a></li>

                            </ul>
                        </li>
                        <li><s:a href="alumnos.action">Alumnos<span class="subheader">Académico</span></s:a>
                            <ul>

                                <li><s:a href="alumnoHistorial.action"><span> Historial Académico</span></s:a></li>
                            <li><s:a href="grupoLista.action"><span> Grupos</span></s:a></li>
                            <li><s:a href="asistenciaGrupo.action"><span> Asistencias</span></s:a></li>
                            </ul>
                        </li>
                        <li><s:a href="configuracion.action">Configuración<span class="subheader">Sistema</span></s:a>
                            <ul>
                                <li><s:a href="usuarioLista.action"><span> Usuarios</span></s:a></li>

                            </ul>
                        </li>
                        <li><s:a href="">Perfíl<span class="subheader">Mi Cuenta</span></s:a>
                            <ul>
                                <li><s:a href="cerrarSession.action"><span> Cerrar Sessión</span></s:a></li>

                            </ul>
                        </li>
                    </ul>
                    <!-- Navigation -->
                </div>
                <!-- wrapper-menu -->
            </div>
            <!-- ENDS menu-holder -->

            <!-- ENDS Menu -->
            <!-- MAIN -->
            <div id="main">
                <!------------------------- MAIN ----------------------------->
                <div id="wrapper">

                    <div id="content">

                        <div id="page-title">
                            <span class="title">Titulo</span>
                            <span class="subtitle">Subtitulo</span>
                        </div>


                        <div id="page-content">
                        <s:url var="ajax" value="/admin/usuarioInsertar.jsp"><s:param name="echo" value="%{'We love jQuery'}"/></s:url>
                        <sj:div id="resultnormal" href="%{ajax}" indicator="indicator" onBeforeTopics="beforeDiv" onCompleteTopics="completeDiv" onErrorTopics="errorDiv" cssClass="result ui-widget-content ui-corner-all">
                            <img id="indicator" src="images/indicator.gif" alt="Loading..." style="display:none"/>
                        </sj:div>

                        <br/><br/>

                        <strong>Div with invalid URL:</strong>
                        <sj:div id="resulterror" href="not_exist.html" indicator="indicator" onCompleteTopics="completeDiv" onErrorTopics="errorDiv" cssClass="result ui-widget-content ui-corner-all">
                            <img id="indicator" src="images/indicator.gif" alt="Loading..." style="display:none"/>
                        </sj:div>
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