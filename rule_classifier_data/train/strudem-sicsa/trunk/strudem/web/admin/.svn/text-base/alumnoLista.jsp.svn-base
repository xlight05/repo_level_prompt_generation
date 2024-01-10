<%-- 
    Document   : index
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
        <title>Institucion :: SICSA</title>
        <s:if test="#session.usuario.tipo != 'admin'">

            <jsp:forward page="login.jsp" />  
        </s:if>
        <sj:head jqueryui="true"/>
        <script type="text/javascript" language="javascript" src="/strudem/js/jquery.dataTables.js"></script>
        <script type="text/javascript" charset="utf-8">        
            $(document).ready(function() {
                oTable = $('#example').dataTable({
                    "bScrollInfinite": true,
                    "bScrollCollapse": true,
                    "sScrollY": 260,
                    "bJQueryUI": true,
                    "oLanguage": {
                        "sSearch": " Buscar:",
                        "sLengthMenu": " Mostrar _MENU_ registros",
                        "sZeroRecords": " Nada encontrado - intenta de nuevo",
                        "sInfo": " Mostrando _START_ a la _END_ de _TOTAL_ registros",
                        "sInfoEmpty": " Mostrando 0 al 0 de 0 registros",
                        "sInfoFiltered": "(filtrado de _MAX_ registros en total)"
                    }
                });
                
            } );
        </script>
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
                        <span class="subtitle">Control académico de la univesidad</span>
                    </div>
                    <!-- ENDS title -->

                    <!-- page-content -->
                    <div id="page-content">
                        <s:property value="message" />
                        <s:actionmessage cssClass="success-box"/>
                        <s:actionerror cssClass="error-box"/>
                        <div class="boxed">
                            <table id="example">
                                <thead>
                                    <tr>
                                        <th><b>ID</b></th>
                                        <th><b>Nombre</b></th>
                                        <th><b>Apellido Paterno</b></th>
                                        <th><b>Apellido Materno</b></th>
                                        <th><b>Matricula</b></th>
                                        <th><b>status</b></th>
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
                                            <td><s:property value="statusLetra"/></td>
                                            <td><s:property value="carrera"/></td>
                                            <td>
                                                <s:url action="alumnoEditar" id="ediReg">
                                                    <s:param name="alumno_id">
                                                        <s:property value="alumno_id"/>
                                                    </s:param>
                                                </s:url>
                                                
                                                <s:url action="inactivaralumno" id="inactivar">
                                                    <s:param name="alumno_id">
                                                        <s:property value="alumno_id"/>
                                                    </s:param>
                                                </s:url>

                                                <s:a href="%{ediReg}"><img src="<s:url value="/img/mono-icons/notepencil32.png"/>" alt="Editar registro" title="Editar Registro" /></s:a>
                                                <s:a href="%{inactivar}"><img src="<s:url value="/img/mono-icons/stop32.png"/>" alt="Eliminar registro" title="Eliminar Registro" /></s:a>
                                            </td>
                                        </tr>
                                    </s:iterator>
                                </tbody>
                                <tr>
                                </tr>
                            </table>
                        </div>
                        <p>
                            <s:a href="alumnoNuevo.action"><img src="<s:url value="/img/mono-icons/plus32.png"/>" alt="Agregar registro" title="Agregar Registro"/></s:a>

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