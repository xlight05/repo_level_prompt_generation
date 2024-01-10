<%-- 
    Document   : footerJsp
    Created on : 9/10/2012, 05:12:48 PM
    Author     : Mauricio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pie de página</title>
        <s:head/>
    </head>
    <body>
        <!-- wrapper-footer -->
            <div class="wrapper">
                <!-- footer-cols -->
                <ul id="footer-cols">
                    <li class="col">
                        <h6>Páginas</h6>
                        <ul>
                            <li class="page_item"><s:a href="index.html">Inicio</s:a></li>
                            <li class="page_item"><s:a href="features.html">Institución</s:a></li>
                            <li class="page_item"><s:a href="blog.html">Profesores</s:a></li>
                            <li class="page_item"><s:a href="portfolio.html">Alumnos</s:a></li>
                            <li class="page_item"><s:a href="gallery.html">Configuración</s:a></li>
                            <li class="page_item"><s:a href="contact.html">Mi Cuenta</s:a></li>
                        </ul>
                    </li>

                    <li class="col">
                        <h6>Categorias</h6>
                        <ul>
                            <li><s:a href="#">Webdesign projects senectus</s:a></li>
                            <li><s:a href="#/">Wordpress projects senectus</s:a></li>
                            <li><s:a href="#">Vestibulum tortor quam</s:a></li>
                            <li><s:a href="#">Code projects amet quam egestas</s:a></li>
                            <li><s:a href="#">Web design projects senectus</s:a></li>
                            <li><s:a href="#/">Marketplace projects</s:a></li>
                            <li><s:a href="#">Writting projects senectus</s:a></li>
                            <li><s:a href="#">Drawings projects fames Aenean</s:a></li>
                            <li><s:a href="#/">Wordpress projects Aenean ultricies</s:a></li>
                        </ul>
                    </li>
                    <li class="col">
                        <h6>Acerca de nosotros</h6>
                        Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas semper. Aenean ultricies mi vitae est. Mauris placerat eleifend leo. Cursus faucibus, tortor neque egestas augue, eu vulputate magna eros.
                    </li>

                </ul>
                <!-- ENDS footer-cols -->
            </div>
            <!-- ENDS wrapper-footer -->
    </body>
</html>
