<%-- 
    Document   : menu
    Created on : 9/10/2012, 06:57:25 PM
    Author     : Mauricio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menu de Encabezado</title>
        <!-- JS -->
        <script type="text/javascript" src="/strudem/js/jquery-1.5.1.min.js"></script>
        <script type="text/javascript" src="/strudem/js/jquery-ui-1.8.13.custom.min.js"></script>
        <script type="text/javascript" src="/strudem/js/easing.js"></script>
        <script type="text/javascript" src="/strudem/js/jquery.scrollTo-1.4.2-min.js"></script>
        <script type="text/javascript" src="/strudem/js/jquery.cycle.all.js"></script>
        <script type="text/javascript" src="/strudem/js/custom.js"></script>

        <!-- Isotope -->
        <script src="/strudem/js/jquery.isotope.min.js"></script>

        <!--[if IE]>
                <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->

        <!--[if IE 6]>
                <script type="text/javascript" src="/strudem/js/DD_belatedPNG.js"></script>
                <script>
                /* EXAMPLE */
                //DD_belatedPNG.fix('*');
        </script>
        <![endif]-->
        <!-- ENDS JS -->


        <!-- Nivo slider -->
        <link rel="stylesheet" href="/strudem/css/nivo-slider.css" type="text/css" media="screen" />
        <script src="/strudem/js/nivo-slider/jquery.nivo.slider.js" type="text/javascript"></script>
        <!-- ENDS Nivo slider -->

        <!-- tabs -->
        <link rel="stylesheet" href="/strudem/css/tabs.css" type="text/css" media="screen" />
        <script type="text/javascript" src="/strudem/js/tabs.js"></script>
        <!-- ENDS tabs -->

        <!-- prettyPhoto -->
        <script type="text/javascript" src="/strudem/js/prettyPhoto/js/jquery.prettyPhoto.js"></script>
        <link rel="stylesheet" href="/strudem/js/prettyPhoto/css/prettyPhoto.css" type="text/css" media="screen" />
        <!-- ENDS prettyPhoto -->

        <!-- superfish -->
        <link rel="stylesheet" media="screen" href="/strudem/css/superfish.css" /> 
        <link rel="stylesheet" media="screen" href="/strudem/css/superfish-left.css" /> 
        <script type="text/javascript" src="/strudem/js/superfish-1.4.8/js/hoverIntent.js"></script>
        <script type="text/javascript" src="/strudem/js/superfish-1.4.8/js/superfish.js"></script>
        <script type="text/javascript" src="/strudem/js/superfish-1.4.8/js/supersubs.js"></script>
        <!-- ENDS superfish -->

        <!-- poshytip -->
        <link rel="stylesheet" href="/strudem/js/poshytip-1.0/src/tip-twitter/tip-twitter.css" type="text/css" />
        <link rel="stylesheet" href="/strudem/js/poshytip-1.0/src/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
        <script type="text/javascript" src="/strudem/js/poshytip-1.0/src/jquery.poshytip.min.js"></script>
        <!-- ENDS poshytip -->

        <!-- Tweet -->
        <link rel="stylesheet" href="/strudem/css/jquery.tweet.css" media="all"  type="text/css"/> 
        <script src="/strudem/js/tweet/jquery.tweet.js" type="text/javascript"></script> 
        <!-- ENDS Tweet -->

        <!-- Fancybox -->
        <link rel="stylesheet" href="/strudem/js/jquery.fancybox-1.3.4/fancybox/jquery.fancybox-1.3.4.css" type="text/css" media="screen" />
        <script type="text/javascript" src="/strudem/js/jquery.fancybox-1.3.4/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
        <!-- ENDS Fancybox -->


        <s:head />
    </head>
    <body>

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
                            <li><s:a href="profesorLista.action"><span> Profesores</span></s:a></li>
                            </ul>

                        </li>
                        <li><s:a href="/admin/profesor.action">Profesor<span class="subheader">Panel Docente</span></s:a>
                            <ul>

                                <li><s:a href="seleccionHorario.action"><span> Horarios </span></s:a></li>
                            <li><s:a href="MiHorarioLista.action"><span> Mi Horario</span></s:a></li>
                            <li><s:a href="misGrupos.action"><span> Mis Grupos</span></s:a></li>

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
                        <li><s:a href="admin.action">Perfíl<span class="subheader">Mi Cuenta</span></s:a>
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
    </body>
</html>
