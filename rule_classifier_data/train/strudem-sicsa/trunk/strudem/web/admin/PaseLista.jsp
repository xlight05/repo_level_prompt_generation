<%-- 
    Document   : PaseLista
    Created on : 27-nov-2012, 8:48:14
    Author     : alumno
--%>
<%@page import="bean.UsuarioBean"%>
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
                        <span class="title">Horario</span>
                        <span class="subtitle">Subtitulo de la seccion</span>
                    </div>
                    <!-- ENDS title -->

                    <!-- page-content -->
                    <div id="page-content">
                            
                        <html xmlns:v="urn:schemas-microsoft-com:vml"
xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=windows-1252">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 14">
<link rel=File-List href="Horarios%20profesores_archivos/filelist.xml">
<!--[if !mso]>
<style>
v\:* {behavior:url(#default#VML);}
o\:* {behavior:url(#default#VML);}
x\:* {behavior:url(#default#VML);}
.shape {behavior:url(#default#VML);}
</style>
<![endif]-->
<html xmlns:v="urn:schemas-microsoft-com:vml"
xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=windows-1252">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 14">
<link rel=File-List
href="Lista%20de%20Asistencia%20Admon%20BD_archivos/filelist.xml">
<!--[if !mso]>
<style>
v\:* {behavior:url(#default#VML);}
o\:* {behavior:url(#default#VML);}
x\:* {behavior:url(#default#VML);}
.shape {behavior:url(#default#VML);}
</style>
<![endif]-->
<style id="Lista de Asistencia Admon BD_18418_Styles">
<!--table
	{mso-displayed-decimal-separator:"\.";
	mso-displayed-thousand-separator:"\,";}
.xl6818418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Arial, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border-top:1.0pt solid windowtext;
	border-right:none;
	border-bottom:none;
	border-left:1.0pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl6918418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Arial, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border-top:1.0pt solid windowtext;
	border-right:none;
	border-bottom:none;
	border-left:none;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl7018418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Arial, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl7118418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border-top:none;
	border-right:none;
	border-bottom:none;
	border-left:1.0pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl7218418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl7318418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	background:#D9D9D9;
	mso-pattern:black none;
	mso-protection:unlocked visible;
	white-space:nowrap;}
.xl7418418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	mso-background-source:auto;
	mso-pattern:auto;
	mso-protection:unlocked visible;
	white-space:nowrap;}
.xl7518418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl7618418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:none;
	border-left:none;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl7718418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	background:#D9D9D9;
	mso-pattern:black none;
	white-space:nowrap;}
.xl7818418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:left;
	vertical-align:middle;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl7918418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:left;
	vertical-align:middle;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:none;
	border-left:none;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl8018418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border-top:none;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:1.0pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl8118418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border-top:none;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl8218418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:left;
	vertical-align:middle;
	border-top:none;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl8318418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Arial, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:left;
	vertical-align:middle;
	border-top:none;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl8418418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl8518418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border-top:1.0pt solid windowtext;
	border-right:none;
	border-bottom:none;
	border-left:1.0pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl8618418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border-top:1.0pt solid windowtext;
	border-right:none;
	border-bottom:none;
	border-left:none;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl8718418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:1.0pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:none;
	border-left:.5pt solid windowtext;
	background:#D9D9D9;
	mso-pattern:black none;
	white-space:nowrap;
	mso-rotate:90;}
.xl8818418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:1.0pt solid windowtext;
	border-right:1.0pt solid windowtext;
	border-bottom:none;
	border-left:.5pt solid windowtext;
	background:#BFBFBF;
	mso-pattern:black none;
	white-space:nowrap;
	mso-rotate:90;}
.xl8918418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:right;
	vertical-align:middle;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl9018418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:none;
	border-left:.5pt solid windowtext;
	background:#BFBFBF;
	mso-pattern:black none;
	white-space:nowrap;
	mso-rotate:90;}
.xl9118418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border:.5pt solid windowtext;
	background:#BFBFBF;
	mso-pattern:black none;
	mso-protection:unlocked visible;
	white-space:nowrap;}
.xl9218418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:1.0pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;
	background:#BFBFBF;
	mso-pattern:black none;
	white-space:nowrap;
	mso-rotate:90;}
.xl9318418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	mso-protection:unlocked visible;
	white-space:nowrap;}
.xl9418418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;
	mso-background-source:auto;
	mso-pattern:auto;
	mso-protection:unlocked visible;
	white-space:nowrap;}
.xl9518418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;
	mso-background-source:auto;
	mso-pattern:auto;
	mso-protection:unlocked visible;
	white-space:nowrap;}
.xl9618418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border:.5pt solid windowtext;
	background:#BFBFBF;
	mso-pattern:black none;
	white-space:nowrap;
	mso-rotate:90;}
.xl9718418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border-top:none;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:1.0pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl9818418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:6.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	mso-protection:unlocked visible;
	white-space:nowrap;
	mso-rotate:90;}
.xl9918418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:6.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;
	background:#D9D9D9;
	mso-pattern:black none;
	mso-protection:unlocked visible;
	white-space:nowrap;
	mso-rotate:90;}
.xl10018418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:7.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	mso-protection:unlocked visible;
	white-space:nowrap;
	mso-rotate:90;}
.xl10118418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:7.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;
	background:#BFBFBF;
	mso-pattern:black none;
	mso-protection:unlocked visible;
	white-space:nowrap;
	mso-rotate:90;}
.xl10218418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:7.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;
	background:#BFBFBF;
	mso-pattern:black none;
	mso-protection:unlocked visible;
	white-space:nowrap;
	mso-rotate:90;}
.xl10318418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:1.0pt solid windowtext;
	background:#D9D9D9;
	mso-pattern:black none;
	white-space:nowrap;}
.xl10418418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;
	background:#D9D9D9;
	mso-pattern:black none;
	white-space:nowrap;}
.xl10518418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border:.5pt solid windowtext;
	background:#D9D9D9;
	mso-pattern:black none;
	white-space:nowrap;}
.xl10618418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border:.5pt solid windowtext;
	background:#BFBFBF;
	mso-pattern:black none;
	white-space:nowrap;}
.xl10718418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:1.0pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;
	background:#BFBFBF;
	mso-pattern:black none;
	white-space:nowrap;}
.xl10818418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:1.0pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl10918418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Calibri, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border:.5pt solid black;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;
	mso-text-control:shrinktofit;}
.xl11018418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border:.5pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl11118418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border:.5pt solid windowtext;
	background:#BFBFBF;
	mso-pattern:black none;
	white-space:nowrap;}
.xl11218418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:1.0pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;
	background:#BFBFBF;
	mso-pattern:black none;
	white-space:nowrap;}
.xl11318418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Calibri, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border:.5pt solid black;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl11418418
	{padding:0px;
	mso-ignore:padding;
	color:black;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Calibri, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border:.5pt solid black;
	background:white;
	mso-pattern:yellow none;
	white-space:nowrap;
	mso-text-control:shrinktofit;}
.xl11518418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Calibri, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border-top:.5pt solid black;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid black;
	border-left:.5pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;
	mso-text-control:shrinktofit;}
.xl11618418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:7.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border:.5pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	mso-protection:unlocked visible;
	white-space:nowrap;}
.xl11718418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:12.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:1.0pt solid windowtext;
	border-right:none;
	border-bottom:none;
	border-left:none;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl11818418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:12.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:1.0pt solid windowtext;
	border-right:1.0pt solid windowtext;
	border-bottom:none;
	border-left:none;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl11918418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:left;
	vertical-align:middle;
	background:#D9D9D9;
	mso-pattern:black none;
	white-space:nowrap;}
.xl12018418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	mso-background-source:auto;
	mso-pattern:auto;
	mso-protection:unlocked visible;
	white-space:nowrap;}
.xl12118418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:left;
	vertical-align:middle;
	mso-background-source:auto;
	mso-pattern:auto;
	mso-protection:unlocked visible;
	white-space:nowrap;}
.xl12218418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:left;
	vertical-align:middle;
	border-top:none;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	background:#D9D9D9;
	mso-pattern:black none;
	white-space:nowrap;}
.xl12318418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:1.0pt solid windowtext;
	border-right:none;
	border-bottom:none;
	border-left:.5pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl12418418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:1.0pt solid windowtext;
	border-right:none;
	border-bottom:none;
	border-left:none;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl12518418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:1.0pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:none;
	border-left:none;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl12618418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:none;
	border-left:.5pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	mso-protection:unlocked visible;
	white-space:nowrap;}
.xl12718418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:none;
	border-left:none;
	mso-background-source:auto;
	mso-pattern:auto;
	mso-protection:unlocked visible;
	white-space:nowrap;}
.xl12818418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	mso-protection:unlocked visible;
	white-space:nowrap;}
.xl12918418
	{padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:8.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Tahoma, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;
	mso-background-source:auto;
	mso-pattern:auto;
	mso-protection:unlocked visible;
	white-space:nowrap;}
-->
</style>
</head>

<body>

<div id="Lista de Asistencia Admon BD_18418" align=center
x:publishsource="Excel">

<table border=0 cellpadding=0 cellspacing=0 width=1001 class=xl7018418
 style='border-collapse:collapse;table-layout:fixed;width:734pt'>
 <col class=xl7018418 width=21 style='mso-width-source:userset;mso-width-alt:
 768;width:16pt'>
 <col class=xl7018418 width=227 style='mso-width-source:userset;mso-width-alt:
 8301;width:170pt'>
 <col class=xl7018418 width=11 span=66 style='mso-width-source:userset;
 mso-width-alt:402;width:8pt'>
 <col class=xl7018418 width=27 style='mso-width-source:userset;mso-width-alt:
 987;width:20pt'>
 <tr height=24 style='mso-height-source:userset;height:18.0pt'>
  <td height=24 width=21 style='height:18.0pt;width:16pt' align=left
  valign=top>
  <![if !vml]><span style='mso-ignore:vglayout;
  position:absolute;z-index:1;margin-left:0px;margin-top:0px;width:132px;
  height:75px'><img width=132 height=75
  src="Lista%20de%20Asistencia%20Admon%20BD_archivos/Lista%20de%20Asistencia%20Admon%20BD_18418_image002.png"
  alt="UTEZdel estado de morelos" v:shapes="Picture_x0020_2"></span><![endif]><span
  style='mso-ignore:vglayout2'>
  <table cellpadding=0 cellspacing=0>
   <tr>
    <td height=24 class=xl6818418 width=21 style='height:18.0pt;width:16pt'>&nbsp;</td>
   </tr>
  </table>
  </span></td>
  <td class=xl6918418 width=227 style='width:170pt'>&nbsp;</td>
  <td colspan=66 height=24 width=726 style='border-right:1.0pt solid black;
  height:18.0pt;width:528pt' align=left valign=top>
  
  <![if !vml]><span style='mso-ignore:vglayout;
  position:absolute;z-index:2;margin-left:607px;margin-top:1px;width:118px;
  height:53px'><img width=118 height=53
  src="Lista%20de%20Asistencia%20Admon%20BD_archivos/Lista%20de%20Asistencia%20Admon%20BD_18418_image004.png"
  v:shapes="Picture_x0020_66"></span><![endif]><span style='mso-ignore:vglayout2'>
  <table cellpadding=0 cellspacing=0>
   <tr>
    <td colspan=66 height=24 class=xl11718418 width=726 style='border-right:
    1.0pt solid black;height:18.0pt;width:528pt'>CONTROL Y SEGUIMIENTO DEL
    ALUMNO (ASISTENCIAS)<span style='mso-spacerun:yes'> </span></td>
   </tr>
  </table>
  </span></td>
  <td class=xl7018418 width=27 style='width:20pt'></td>
 </tr>
 <tr height=15 style='height:11.25pt'>
  <td height=15 class=xl7118418 style='height:11.25pt'>&nbsp;</td>
  <td class=xl7218418></td>
  <td colspan=5 class=xl7718418>CARRERA</td>
  <td class=xl7318418>&nbsp;</td>
  <td class=xl7318418>&nbsp;</td>
  <td class=xl7418418 colspan=21><s:iterator value="asistencia"><s:property value="carrera"/></s:iterator></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td colspan=5 class=xl11918418>PERIODO<span style='mso-spacerun:yes'> </span></td>
  <td colspan=16 class=xl12018418><s:iterator value="asistencia"><s:property value="periodo"/></s:iterator></td>
  <td class=xl7518418></td>
  <td class=xl7518418></td>
  <td class=xl7518418></td>
  <td class=xl7518418></td>
  <td class=xl7518418></td>
  <td class=xl7518418></td>
  <td class=xl7518418></td>
  <td class=xl7518418></td>
  <td class=xl7518418></td>
  <td class=xl7618418>&nbsp;</td>
  <td class=xl7018418></td>
 </tr>
 <tr height=15 style='height:11.25pt'>
  <td height=15 class=xl7118418 style='height:11.25pt'>&nbsp;</td>
  <td class=xl7218418></td>
  <td class=xl7718418 colspan=5>ASIGNATU<span style='display:none'>RA</span></td>
  <td class=xl7318418>&nbsp;</td>
  <td class=xl7318418>&nbsp;</td>
  <td class=xl7418418 colspan=15><s:iterator value="asistencia"><s:property value="materia"/></s:iterator></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td colspan=8 class=xl7718418>CUATRIMESTRE</td>
  <td colspan=5 class=xl12118418><s:iterator value="asistencia"><s:property value="cuatrimestre"/></s:iterator> º<span style='mso-spacerun:yes'> </span></td>
  <td colspan=4 class=xl11918418>GRUPO</td>
  <td colspan=2 class=xl12018418><s:iterator value="asistencia"><s:property value="grupo"/></s:iterator></td>
  <td class=xl7518418></td>
  <td class=xl7818418></td>
  <td class=xl7018418></td>
  <td class=xl7818418></td>
  <td class=xl7818418></td>
  <td class=xl7818418></td>
  <td class=xl7818418></td>
  <td class=xl7818418></td>
  <td class=xl7818418></td>
  <td class=xl7818418></td>
  <td class=xl7818418></td>
  <td class=xl7918418>&nbsp;</td>
  <td class=xl7018418></td>
 </tr>
 <tr height=15 style='height:11.25pt'>
  <td height=15 class=xl7118418 style='height:11.25pt'>&nbsp;</td>
  <td class=xl7218418></td>
  <td colspan=5 class=xl7718418>DOCENTE</td>
  <td class=xl7318418>&nbsp;</td>
  <td class=xl7318418>&nbsp;</td>
  <td class=xl7418418 colspan=13><s:iterator value="asistencia"><s:property value="profesor"/></s:iterator></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td class=xl7418418></td>
  <td colspan=10 class=xl11918418>HORAS POR SEMANA</td>
  <td colspan=6 class=xl12118418><s:iterator value="asistencia"><s:property value="horasporsemana"/></s:iterator></td>
  <td class=xl7218418></td>
  <td class=xl7218418></td>
  <td class=xl7218418></td>
  <td class=xl7218418></td>
  <td class=xl7518418></td>
  <td class=xl7518418></td>
  <td class=xl7518418></td>
  <td class=xl7518418></td>
  <td class=xl7518418></td>
  <td class=xl7518418></td>
  <td class=xl7518418></td>
  <td class=xl7518418></td>
  <td class=xl7518418></td>
  <td class=xl7518418></td>
  <td class=xl7618418>&nbsp;</td>
  <td class=xl7018418></td>
 </tr>
 <tr height=15 style='height:11.25pt'>
  <td height=15 class=xl7118418 style='height:11.25pt'>&nbsp;</td>
  <td class=xl7218418></td>
  <td colspan=10 rowspan=2 class=xl11918418 style='border-bottom:1.0pt solid black'>FIRMA
  DEL DOCENTE</td>

  <td class=xl7818418></td>
  <td class=xl7818418></td>
  <td class=xl7818418></td>
  <td class=xl7818418></td>
  <td class=xl7818418></td>
  <td class=xl7818418></td>
  <td class=xl7818418></td>
  <td class=xl7818418></td>
  <td class=xl7818418></td>
  <td class=xl7818418></td>
  <td class=xl7818418></td>
  <td class=xl7818418></td>
  <td class=xl7818418></td>
  <td class=xl7218418></td>
  <td class=xl7218418></td>
  <td class=xl7218418></td>
  <td class=xl7218418></td>
  <td class=xl7218418></td>
  <td class=xl7018418></td>
  <td class=xl7018418></td>
  <td class=xl7218418></td>
  <td class=xl7218418></td>
  <td class=xl7218418></td>
  <td class=xl7218418></td>
  <td class=xl7218418></td>
  <td class=xl7018418></td>
  <td class=xl7018418></td>
  <td class=xl7018418></td>
  <td class=xl7018418></td>
  <td class=xl7018418></td>
  <td class=xl7018418></td>
  <td class=xl7018418></td>
  <td class=xl7018418></td>
  <td class=xl7018418></td>
  <td class=xl7018418></td>
  <td class=xl7018418></td>
  <td class=xl7018418></td>
  <td class=xl7018418></td>
  <td class=xl7018418></td>
  <td class=xl7018418></td>
  <td class=xl7018418></td>
  <td class=xl7218418></td>
  <td class=xl7218418></td>
  <td class=xl7218418></td>
  <td class=xl7218418></td>
  <td class=xl7518418></td>
  <td class=xl7518418></td>
  <td class=xl7518418></td>
  <td class=xl7518418></td>
  <td class=xl7518418></td>
  <td class=xl7518418></td>
  <td class=xl7518418></td>
  <td class=xl7518418></td>
  <td class=xl7518418></td>
  <td class=xl7518418></td>
  <td class=xl7618418>&nbsp;</td>
  <td class=xl7018418></td>
 </tr>
 <tr height=16 style='height:12.0pt'>
  <td height=16 class=xl8018418 style='height:12.0pt'>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8218418>&nbsp;</td>
  <td class=xl8218418>&nbsp;</td>
  <td class=xl8218418>&nbsp;</td>
  <td class=xl8218418>&nbsp;</td>
  <td class=xl8318418>&nbsp;</td>
  <td class=xl8318418>&nbsp;</td>
  <td class=xl8218418>&nbsp;</td>
  <td class=xl8218418>&nbsp;</td>
  <td class=xl8218418>&nbsp;</td>
  <td class=xl8218418>&nbsp;</td>
  <td class=xl8218418>&nbsp;</td>
  <td class=xl8218418>&nbsp;</td>
  <td class=xl8218418>&nbsp;</td>
  <td class=xl8218418>&nbsp;</td>
  <td class=xl8218418>&nbsp;</td>
  <td class=xl8218418>&nbsp;</td>
  <td class=xl8218418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8118418>&nbsp;</td>
  <td class=xl8418418>&nbsp;</td>
  <td class=xl7018418></td>
 </tr>
 <tr height=16 style='mso-height-source:userset;height:12.0pt'>
  <td height=16 class=xl8518418 style='height:12.0pt;border-top:none'>&nbsp;</td>
  <td class=xl8618418 style='border-top:none'>&nbsp;</td>
  <td colspan=21 class=xl12318418 style='border-right:.5pt solid black'>Revisión
  1<span style='mso-spacerun:yes'> </span></td>
  <td class=xl8718418 style='border-top:none;border-left:none'>%</td>
 
 </tr>
 <tr height=16 style='mso-height-source:userset;height:12.0pt'>
  <td height=16 class=xl7118418 style='height:12.0pt'>&nbsp;</td>
  <td class=xl8918418><span style='mso-spacerun:yes'>              </span>MES:</td>
  <td colspan=21 class=xl12618418>Agosto-Septiembre</td>
  <td class=xl9018418>&nbsp;</td>
  
 </tr>
 <tr height=16 style='mso-height-source:userset;height:12.0pt'>
  <td height=16 class=xl7118418 style='height:12.0pt'>&nbsp;</td>
  <td class=xl8918418>Unidad Temática</td>
  <td class=xl9318418>I</td>
  <td class=xl9418418>&nbsp;</td>
  <td class=xl9418418>&nbsp;</td>
  <td class=xl9418418>&nbsp;</td>
  <td class=xl9418418>&nbsp;</td>
  <td class=xl9418418>&nbsp;</td>
  <td class=xl9418418>&nbsp;</td>
  <td class=xl9418418>&nbsp;</td>
  <td class=xl9418418>&nbsp;</td>
  <td class=xl9418418 colspan=2>II</td>
  <td class=xl9518418>&nbsp;</td>
  <td class=xl9518418>&nbsp;</td>
  <td class=xl9518418>&nbsp;</td>
  <td class=xl9518418>&nbsp;</td>
  <td class=xl9518418>&nbsp;</td>
  <td class=xl9518418>&nbsp;</td>
  <td class=xl9518418>&nbsp;</td>
  <td class=xl9518418>&nbsp;</td>
  <td class=xl9518418>&nbsp;</td>
  <td class=xl9518418>&nbsp;</td>
  <td class=xl9618418>&nbsp;</td>
 
 </tr>
 <tr height=24 style='mso-height-source:userset;height:18.0pt'>
  <td height=24 class=xl9718418 style='height:18.0pt'>&nbsp;</td>
  <td class=xl8918418><span style='mso-spacerun:yes'>              </span>DIAS:</td>
  <td class=xl9818418>27</td>
  <td class=xl9818418 style='border-left:none'>28</td>
  <td class=xl9818418 style='border-left:none'>29</td>
  <td class=xl9918418 style='border-left:none'>3</td>
  <td class=xl9918418 style='border-left:none'>4</td>
  <td class=xl9918418 style='border-left:none'>5</td>
  <td class=xl9818418 style='border-left:none'>10</td>
  <td class=xl9818418 style='border-left:none'>11</td>
  <td class=xl9818418 style='border-left:none'>12</td>
  <td class=xl9918418 style='border-left:none'>17</td>
  <td class=xl9918418 style='border-left:none'>18</td>
  <td class=xl9918418 style='border-left:none'>19</td>
  <td class=xl9818418 style='border-left:none'>24</td>
  <td class=xl9818418 style='border-left:none'>25</td>
  <td class=xl9818418 style='border-left:none'>26</td>
  <td class=xl9818418 style='border-left:none'>&nbsp;</td>
  <td class=xl9818418 style='border-left:none'>&nbsp;</td>
  <td class=xl9818418 style='border-left:none'>&nbsp;</td>
  <td class=xl9818418 style='border-left:none'>&nbsp;</td>
  <td class=xl10018418 style='border-left:none'>&nbsp;</td>
  <td class=xl10018418 style='border-left:none'>&nbsp;</td>
  <td class=xl10118418 style='border-left:none'>&nbsp;</td>
 
 </tr>
 <tr height=15 style='height:11.25pt'>
  <td height=15 class=xl10318418 style='height:11.25pt;border-top:none'>No</td>
  <td class=xl10418418 style='border-left:none'>Nombre del alumno</td>
  <td class=xl10518418 style='border-top:none'>&nbsp;</td>
  <td class=xl10518418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl10518418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl10518418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl10518418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl10518418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl10518418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl10518418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl10518418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl10518418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl10518418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl10518418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl10518418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl10518418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl10518418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl10518418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl10518418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl10518418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl10518418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl10518418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl10518418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl10618418 style='border-top:none;border-left:none'>&nbsp;</td>
  
 </tr> <s:form action="paselitaAlumno">
      
 <s:iterator value="listaalumno">
     <tr><INPUT TYPE='hidden' name ='alumnomatricula' id='alumnomatricula<s:property value="historialalumno"/>' value = '<s:property value="historialalumno"/>'>

 <tr height=16 style='mso-height-source:userset;height:12.0pt'>
     <td height=16 class=xl10818418 style='height:12.0pt;border-top:none'><s:property value="alumno_id"/></td>
  <td class=xl10918418 style='border-top:none;border-left:none'>
    

                  <s:property value="nombre"/> &nbsp; <s:property value="a_paterno"/>
             &nbsp;
          <s:property value="a_materno"/>

  <td class=xl11018418 style='border-top:none;border-left:none'>
  <SELECT name = 'estado'  id ='estado<s:property value="historialalumno"/>'>
   <option value='1'></option>
   <option value='2'>/</option>
   <option value='3'>X</option>
   <option value='4'>.</option>
</SELECT>

  <td class=xl11018418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl11018418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl11018418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl11018418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl11018418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl11018418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl11018418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl11018418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl11018418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl11018418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl11018418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl11018418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl11018418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl11018418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl11018418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl11018418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl11018418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl11018418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl11018418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl11018418 style='border-top:none;border-left:none'>&nbsp;</td>
  <td class=xl11118418 style='border-top:none;border-left:none'>&nbsp;</td>
  </s:iterator>
 </tr>
 
 <![endif]>
</table>
    <s:submit name="Gurdar" value="Gurdar"></s:submit>
        </s:form>
</div>


<!----------------------------->
<!--FINAL DE LOS RESULTADOS DEL ASISTENTE PARA PUBLICAR COMO PÁGINA WEB DE
EXCEL-->
<!----------------------------->
</body>

</html>



</div>


<!----------------------------->
<!--FINAL DE LOS RESULTADOS DEL ASISTENTE PARA PUBLICAR COMO PÁGINA WEB DE
EXCEL-->
<!----------------------------->
</body>

</html>
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