<%-- 
    Document   : template para copiar
    Created on : 26/09/2012, 01:46:18 PM
    Author     : Mauricio Zarate Barrera
--%>

<%@page import="bean.MiHorarioBean"%>
<%@page import="bean.UsuarioBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ page language="java"  contentType="text/html" import="java.util.*"%>
<jsp:include page="/header.jspf" />
<!DOCTYPE html>
<html xmlns:v="urn:schemas-microsoft-com:vml"
      xmlns:o="urn:schemas-microsoft-com:office:office"
      xmlns:x="urn:schemas-microsoft-com:office:excel"
      xmlns="http://www.w3.org/TR/REC-html40">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SICSA</title>
        <s:if test="#session.usuario.tipo != 'admin'">
            <jsp:forward page="login.jsp" />  
        </s:if>
        <style id="Horarios profesores_32144_Styles">
            <!--table
            {mso-displayed-decimal-separator:"\.";
             mso-displayed-thousand-separator:"\,";}
            .xl7032144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:10.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:general;
             vertical-align:bottom;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl7132144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:9.0pt;
             font-weight:700;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"\@";
             text-align:right;
             vertical-align:middle;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl7232144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:9.0pt;
             font-weight:700;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:left;
             vertical-align:middle;
             mso-background-source:auto;
             mso-pattern:auto;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl7332144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:10.0pt;
             font-weight:700;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl7432144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:10.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl7532144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:10.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"Short Time";
             text-align:center;
             vertical-align:middle;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl7632144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             mso-background-source:auto;
             mso-pattern:auto;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl7732144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             mso-background-source:auto;
             mso-pattern:auto;
             mso-protection:unlocked visible;
             white-space:normal;}
            .xl7832144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"\@";
             text-align:center;
             vertical-align:middle;
             border-top:1.0pt solid windowtext;
             border-right:none;
             border-bottom:none;
             border-left:1.0pt solid windowtext;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:normal;}
            .xl7932144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"\@";
             text-align:center;
             vertical-align:middle;
             border-top:1.0pt solid windowtext;
             border-right:none;
             border-bottom:none;
             border-left:none;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:normal;}
            .xl8032144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"\@";
             text-align:center;
             vertical-align:middle;
             border-top:1.0pt solid windowtext;
             border-right:1.0pt solid windowtext;
             border-bottom:none;
             border-left:none;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:normal;}
            .xl8132144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"\@";
             text-align:center;
             vertical-align:middle;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl8232144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:10.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:General;
             text-align:general;
             vertical-align:bottom;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl8332144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:10.0pt;
             font-weight:700;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border:.5pt solid windowtext;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl8432144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:11.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:bottom;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl8532144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:9.0pt;
             font-weight:700;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"\@";
             text-align:center;
             vertical-align:middle;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl8632144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:9.0pt;
             font-weight:700;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:right;
             vertical-align:middle;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl8732144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border-top:.5pt solid windowtext;
             border-right:.5pt solid windowtext;
             border-bottom:none;
             border-left:.5pt solid windowtext;
             background:white;
             mso-pattern:#C6D9F1 none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl8832144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border-top:none;
             border-right:.5pt solid windowtext;
             border-bottom:none;
             border-left:.5pt solid windowtext;
             background:white;
             mso-pattern:#C6D9F1 none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl8932144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:10.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"Short Time";
             text-align:center;
             vertical-align:middle;
             border-top:.5pt solid windowtext;
             border-right:none;
             border-bottom:.5pt solid windowtext;
             border-left:.5pt solid windowtext;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl9032144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:10.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"Short Time";
             text-align:center;
             vertical-align:middle;
             border-top:.5pt solid windowtext;
             border-right:.5pt solid windowtext;
             border-bottom:.5pt solid windowtext;
             border-left:none;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl9132144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border-top:none;
             border-right:.5pt solid windowtext;
             border-bottom:.5pt solid windowtext;
             border-left:.5pt solid windowtext;
             background:white;
             mso-pattern:#C6D9F1 none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl9232144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border-top:.5pt solid windowtext;
             border-right:.5pt solid windowtext;
             border-bottom:none;
             border-left:.5pt solid windowtext;
             background:#8DB4E2;
             mso-pattern:black none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl9332144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border-top:.5pt solid windowtext;
             border-right:none;
             border-bottom:none;
             border-left:.5pt solid windowtext;
             background:#8DB4E2;
             mso-pattern:black none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl9432144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border-top:none;
             border-right:.5pt solid windowtext;
             border-bottom:none;
             border-left:.5pt solid windowtext;
             background:#8DB4E2;
             mso-pattern:black none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl9532144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border-top:none;
             border-right:.5pt solid windowtext;
             border-bottom:.5pt solid windowtext;
             border-left:.5pt solid windowtext;
             background:#8DB4E2;
             mso-pattern:black none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl9632144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border-top:none;
             border-right:none;
             border-bottom:.5pt solid windowtext;
             border-left:.5pt solid windowtext;
             background:#8DB4E2;
             mso-pattern:black none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl9732144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:10.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"Short Time";
             text-align:center;
             vertical-align:middle;
             border-top:.5pt solid windowtext;
             border-right:none;
             border-bottom:.5pt solid windowtext;
             border-left:none;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl9832144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border-top:.5pt solid windowtext;
             border-right:.5pt solid windowtext;
             border-bottom:none;
             border-left:.5pt solid windowtext;
             background:#948A54;
             mso-pattern:black none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl9932144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border-top:.5pt solid windowtext;
             border-right:none;
             border-bottom:none;
             border-left:.5pt solid windowtext;
             background:#948A54;
             mso-pattern:black none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl10032144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border-top:.5pt solid windowtext;
             border-right:.5pt solid windowtext;
             border-bottom:none;
             border-left:.5pt solid windowtext;
             background:white;
             mso-pattern:black none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl10132144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border-top:.5pt solid windowtext;
             border-right:none;
             border-bottom:none;
             border-left:.5pt solid windowtext;
             background:white;
             mso-pattern:black none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl10232144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border-top:none;
             border-right:.5pt solid windowtext;
             border-bottom:none;
             border-left:.5pt solid windowtext;
             background:#948A54;
             mso-pattern:black none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl10332144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border-top:none;
             border-right:.5pt solid windowtext;
             border-bottom:none;
             border-left:.5pt solid windowtext;
             background:white;
             mso-pattern:black none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl10432144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border-top:none;
             border-right:.5pt solid windowtext;
             border-bottom:.5pt solid windowtext;
             border-left:.5pt solid windowtext;
             background:#948A54;
             mso-pattern:black none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl10532144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border-top:none;
             border-right:none;
             border-bottom:.5pt solid windowtext;
             border-left:.5pt solid windowtext;
             background:#948A54;
             mso-pattern:black none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl10632144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border-top:none;
             border-right:.5pt solid windowtext;
             border-bottom:.5pt solid windowtext;
             border-left:.5pt solid windowtext;
             background:white;
             mso-pattern:black none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl10732144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border-top:none;
             border-right:none;
             border-bottom:.5pt solid windowtext;
             border-left:.5pt solid windowtext;
             background:white;
             mso-pattern:black none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl10832144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:10.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"Short Time";
             text-align:center;
             vertical-align:middle;
             border-top:.5pt solid windowtext;
             border-right:none;
             border-bottom:none;
             border-left:.5pt solid windowtext;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl10932144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:10.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"Short Time";
             text-align:center;
             vertical-align:middle;
             border-top:none;
             border-right:none;
             border-bottom:none;
             border-left:.5pt solid windowtext;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl11032144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:10.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"Short Time";
             text-align:center;
             vertical-align:middle;
             border-top:none;
             border-right:none;
             border-bottom:.5pt solid windowtext;
             border-left:.5pt solid windowtext;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl11132144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:10.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"Short Time";
             text-align:center;
             vertical-align:middle;
             border-top:.5pt solid windowtext;
             border-right:.5pt solid windowtext;
             border-bottom:none;
             border-left:none;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl11232144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:10.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"Short Time";
             text-align:center;
             vertical-align:middle;
             border-top:none;
             border-right:.5pt solid windowtext;
             border-bottom:none;
             border-left:none;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl11332144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:10.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"Short Time";
             text-align:center;
             vertical-align:middle;
             border-top:none;
             border-right:.5pt solid windowtext;
             border-bottom:.5pt solid windowtext;
             border-left:none;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl11432144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:10.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"Short Time";
             text-align:center;
             vertical-align:middle;
             border-top:.5pt solid windowtext;
             border-right:none;
             border-bottom:.5pt solid windowtext;
             border-left:.5pt solid windowtext;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:normal;}
            .xl11532144
            {padding:0px;
             mso-ignore:padding;
             color:black;
             font-size:11.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Calibri, sans-serif;
             mso-font-charset:0;
             mso-number-format:"Short Time";
             text-align:center;
             vertical-align:middle;
             border-top:.5pt solid windowtext;
             border-right:none;
             border-bottom:.5pt solid windowtext;
             border-left:.5pt solid windowtext;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:normal;}
            .xl11632144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:10.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"Short Time";
             text-align:center;
             vertical-align:middle;
             border-top:.5pt solid windowtext;
             border-right:.5pt solid windowtext;
             border-bottom:.5pt solid windowtext;
             border-left:none;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:normal;}
            .xl11732144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:10.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:general;
             vertical-align:bottom;
             border-top:1.0pt solid windowtext;
             border-right:none;
             border-bottom:1.0pt solid windowtext;
             border-left:1.0pt solid windowtext;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl11832144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:10.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:general;
             vertical-align:bottom;
             border-top:1.0pt solid windowtext;
             border-right:none;
             border-bottom:1.0pt solid windowtext;
             border-left:none;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl11932144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:10.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:general;
             vertical-align:bottom;
             border-top:1.0pt solid windowtext;
             border-right:1.0pt solid windowtext;
             border-bottom:1.0pt solid windowtext;
             border-left:none;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl12032144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"\@";
             text-align:center;
             vertical-align:middle;
             border:1.0pt solid windowtext;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl12132144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:9.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border-top:none;
             border-right:1.0pt solid windowtext;
             border-bottom:1.0pt solid windowtext;
             border-left:1.0pt solid windowtext;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl12232144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:bottom;
             border:1.0pt solid windowtext;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl12332144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:9.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:General;
             text-align:center;
             vertical-align:middle;
             border-top:1.0pt solid windowtext;
             border-right:none;
             border-bottom:none;
             border-left:1.0pt solid windowtext;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl12432144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:9.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
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
            .xl12532144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:9.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
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
            .xl12632144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:9.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:General;
             text-align:center;
             vertical-align:middle;
             border-top:none;
             border-right:none;
             border-bottom:none;
             border-left:1.0pt solid windowtext;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl12732144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:9.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:General;
             text-align:center;
             vertical-align:middle;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl12832144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:9.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
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
            .xl12932144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:700;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"\@";
             text-align:center;
             vertical-align:bottom;
             border-top:none;
             border-right:1.0pt solid windowtext;
             border-bottom:none;
             border-left:1.0pt solid windowtext;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl13032144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:6.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"\@";
             text-align:center;
             vertical-align:middle;
             border-top:none;
             border-right:1.0pt solid windowtext;
             border-bottom:1.0pt solid windowtext;
             border-left:1.0pt solid windowtext;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl13132144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"\@";
             text-align:center;
             vertical-align:middle;
             border-top:none;
             border-right:1.0pt solid windowtext;
             border-bottom:1.0pt solid windowtext;
             border-left:1.0pt solid windowtext;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl13232144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border-top:.5pt solid windowtext;
             border-right:none;
             border-bottom:none;
             border-left:none;
             background:white;
             mso-pattern:black none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl13332144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border-top:.5pt solid windowtext;
             border-right:.5pt solid windowtext;
             border-bottom:none;
             border-left:none;
             background:white;
             mso-pattern:black none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl13432144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border-top:none;
             border-right:none;
             border-bottom:none;
             border-left:.5pt solid windowtext;
             background:white;
             mso-pattern:black none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl13532144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             background:white;
             mso-pattern:black none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl13632144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border-top:none;
             border-right:.5pt solid windowtext;
             border-bottom:none;
             border-left:none;
             background:white;
             mso-pattern:black none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl13732144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border-top:none;
             border-right:none;
             border-bottom:.5pt solid windowtext;
             border-left:none;
             background:white;
             mso-pattern:black none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl13832144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border-top:none;
             border-right:.5pt solid windowtext;
             border-bottom:.5pt solid windowtext;
             border-left:none;
             background:white;
             mso-pattern:black none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl13932144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border-top:.5pt solid windowtext;
             border-right:none;
             border-bottom:none;
             border-left:none;
             background:#8DB4E2;
             mso-pattern:black none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl14032144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border-top:.5pt solid windowtext;
             border-right:.5pt solid windowtext;
             border-bottom:none;
             border-left:none;
             background:#8DB4E2;
             mso-pattern:black none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl14132144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border-top:none;
             border-right:none;
             border-bottom:none;
             border-left:.5pt solid windowtext;
             background:#8DB4E2;
             mso-pattern:black none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl14232144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             background:#8DB4E2;
             mso-pattern:black none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl14332144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border-top:none;
             border-right:.5pt solid windowtext;
             border-bottom:none;
             border-left:none;
             background:#8DB4E2;
             mso-pattern:black none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl14432144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border-top:none;
             border-right:none;
             border-bottom:.5pt solid windowtext;
             border-left:none;
             background:#8DB4E2;
             mso-pattern:black none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl14532144
            {padding:0px;
             mso-ignore:padding;
             color:windowtext;
             font-size:8.0pt;
             font-weight:400;
             font-style:normal;
             text-decoration:none;
             font-family:Arial, sans-serif;
             mso-font-charset:0;
             mso-number-format:"_-* \#\,\#\#0\.00_-\;\\-* \#\,\#\#0\.00_-\;_-* \0022-\0022??_-\;_-\@_-";
             text-align:center;
             vertical-align:middle;
             border-top:none;
             border-right:.5pt solid windowtext;
             border-bottom:.5pt solid windowtext;
             border-left:none;
             background:#8DB4E2;
             mso-pattern:black none;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            -->
        </style>
        <sj:head jqueryui="true" jquerytheme="smoothness" compressed="true" loadFromGoogle="true"/>
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
                        <span class="title">Mi Horario</span>
                        <span class="subtitle">Clases que imparto en la jornada cuatrimestral</span>
                    </div>
                    <!-- ENDS title -->

                    <!-- page-content -->
                    <div id="page-content">
                        <!--[if !excel]>&nbsp;&nbsp;<![endif]-->
                        <!--La siguiente información se generó mediante el Asistente para publicar como
                        página web de Microsoft Excel.-->
                        <!--Si se vuelve a publicar el mismo elemento desde Excel, se reemplazará toda
                        la información comprendida entre las etiquetas DIV.-->
                        <!----------------------------->
                        <!--INICIO DE LOS RESULTADOS DEL ASISTENTE PARA PUBLICAR COMO PÁGINA WEB DE
                        EXCEL -->
                        <!----------------------------->

                        <div id="Horarios profesores_32144" align=center x:publishsource="Excel">

                            <table border=0 cellpadding=0 cellspacing=0 width=955 class=xl8232144
                                   style='border-collapse:collapse;table-layout:fixed;width:720pt'>
                                <col class=xl7032144 width=80 span=2 style='width:60pt'>
                                <col class=xl7032144 width=53 span=15 style='mso-width-source:userset;
                                     mso-width-alt:1938;width:40pt'>
                                <tr height=19 style='height:14.25pt'>
                                    <td colspan=17 height=19 width=955 style='height:14.25pt;width:720pt'
                                        align=left valign=top>
                                        <![if !vml]><span style='mso-ignore:vglayout;
                                                          position:absolute;z-index:1;margin-left:3px;margin-top:0px;width:911px;
                                                          height:63px'>
                                            <table cellpadding=0 cellspacing=0>
                                                <tr>
                                                    <td width=0 height=0></td>
                                                    <td width=203></td>
                                                    <td width=596></td>
                                                    <td width=112></td>
                                                </tr>
                                                <tr>
                                                <tr>
                                                    <td height=2></td>
                                                </tr>
                                            </table>
                                        </span><![endif]><span style='mso-ignore:vglayout2'>
                                            <table cellpadding=0 cellspacing=0>
                                                <tr>
                                                    <td colspan=17 height=19 class=xl8432144 width=955 style='height:14.25pt;
                                                        width:720pt'><span style='mso-spacerun:yes'> </span>UNIVERSIDAD TECNOLÓGICA
                                                        EMILIANO ZAPATA DEL ESTADO DE MORELOS<span
                                                            style='mso-spacerun:yes'> </span></td>
                                                </tr>
                                            </table>
                                        </span></td>
                                </tr>
                                <tr height=17 style='height:12.75pt'>
                                    <td colspan=17 height=17 class=xl8532144 style='height:12.75pt'>DIVISIÓN
                                        ACADÉMICA DE TECNOLOGÍAS DE LA INFORMACIÓN Y COMUNICACIÓN</td>
                                </tr>
                                <tr height=17 style='height:12.75pt'>
                                    <td colspan=8 height=17 class=xl8632144 style='height:12.75pt'><span
                                            style='mso-spacerun:yes'> </span>HORARIO DE CUATRIMESTRE:<span
                                            style='mso-spacerun:yes'> </span></td>
                                    <td colspan=9 class=xl7232144><span
                                            style='mso-spacerun:yes'> </span>SEPTIEMBRE - DICIEMBRE 2012<span
                                            style='mso-spacerun:yes'> </span></td>
                                </tr>
                                <tr height=17 style='height:12.75pt'>
                                    <td colspan=8 height=17 class=xl7132144 style='height:12.75pt'>NOMBRE DEL
                                        PROFESOR:</td>
                                    <td colspan=9 class=xl7232144><span style='mso-spacerun:yes'> </span><s:iterator value="lista2"><s:if test="#session.usuario.usuario_id.equals(profesor_id)"> <s:property value="nombreProfesor"/> <s:property value="a_paterno"/> <s:property value="a_materno"/></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                </tr>
                                <tr height=17 style='height:12.75pt'>
                                    <td colspan=2 height=17 class=xl8332144 style='height:12.75pt'><span
                                            style='mso-spacerun:yes'> </span>HORA<span style='mso-spacerun:yes'> </span></td>
                                    <td colspan=3 class=xl8332144 style='border-left:none'><span
                                            style='mso-spacerun:yes'> </span>LUNES<span style='mso-spacerun:yes'> </span></td>
                                    <td colspan=3 class=xl8332144 style='border-left:none'><span
                                            style='mso-spacerun:yes'> </span>MARTES<span
                                            style='mso-spacerun:yes'> </span></td>
                                    <td colspan=3 class=xl8332144 style='border-left:none'><span
                                            style='mso-spacerun:yes'> </span>MIERCOLES<span
                                            style='mso-spacerun:yes'> </span></td>
                                    <td colspan=3 class=xl8332144 style='border-left:none'><span
                                            style='mso-spacerun:yes'> </span>JUEVES<span
                                            style='mso-spacerun:yes'> </span></td>
                                    <td colspan=3 class=xl8332144 style='border-left:none'><span
                                            style='mso-spacerun:yes'> </span>VIERNES<span
                                            style='mso-spacerun:yes'> </span></td>
                                </tr>
                                <tr class=xl7332144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td rowspan=3 height=39 class=xl8932144 style='height:29.85pt;border-top:
                                        none'>07:00</td>
                                    <td rowspan=3 class=xl9032144 style='border-top:none'>08:00</td>
                                    <td colspan=3 class=xl8732144>&nbsp;</td>
                                    <td colspan=3 class=xl8732144>&nbsp;</td>
                                    <td colspan=3 class=xl8732144>&nbsp;</td>
                                    <td colspan=3 class=xl8732144>&nbsp;</td>
                                    <td colspan=3 class=xl8732144>&nbsp;</td>
                                </tr>
                                <tr height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td colspan=3 height=13 class=xl8832144 style='height:9.95pt;border-left:
                                        none'>&nbsp;</td>
                                    <td colspan=3 class=xl8832144 style='border-left:none'>&nbsp;</td>
                                    <td colspan=3 class=xl8832144 style='border-left:none'>&nbsp;</td>
                                    <td colspan=3 class=xl8832144 style='border-left:none'>&nbsp;</td>
                                    <td colspan=3 class=xl8832144 style='border-left:none'>&nbsp;</td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td colspan=3 height=13 class=xl9132144 style='height:9.95pt;border-left:
                                        none'>&nbsp;</td>
                                    <td colspan=3 class=xl9132144 style='border-left:none'>&nbsp;</td>
                                    <td colspan=3 class=xl9132144 style='border-left:none'>&nbsp;</td>
                                    <td colspan=3 class=xl9132144 style='border-left:none'>&nbsp;</td>
                                    <td colspan=3 class=xl9132144 style='border-left:none'>&nbsp;</td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td rowspan=3 height=39 class=xl8932144 style='height:29.85pt;border-top:
                                        none'>08:00</td>
                                    <td rowspan=3 class=xl9032144 style='border-top:none'>09:00</td>
                                            <td colspan=3 class=xl9232144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='08:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9232144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='08:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9232144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='08:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9232144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='08:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9232144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='08:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td colspan=3 height=13 class=xl9432144 style='height:9.95pt;border-left:
                                                none'><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='08:00:00.0000000'">
                                                            <s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                    <td colspan=3 class=xl9432144 style='border-left:none'><span
                                                    style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='08:00:00.0000000'">
                                                                <s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/><s:property value="grupo"/></s:a></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                    <td colspan=3 class=xl9432144 style='border-left:none'><span
                                                    style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='08:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                    <td colspan=3 class=xl9432144 style='border-left:none'><span
                                                    style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='08:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                    <td colspan=3 class=xl9432144 style='border-left:none'><span
                                                    style='mso-spacerun:yes'> </span> <s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='08:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td colspan=3 height=13 class=xl9532144 style='height:9.95pt;border-left:
                                                none'><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='08:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9532144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='08:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9532144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='08:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9532144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='08:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9532144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='08:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td rowspan=3 height=39 class=xl8932144 style='height:29.85pt;border-top:
                                        none'>09:00</td>
                                    <td rowspan=3 class=xl9732144 style='border-top:none'>10:00</td>
                                            <td colspan=3 class=xl9232144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='09:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9232144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='09:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9232144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='09:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9232144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='09:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9232144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='09:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td colspan=3 height=13 class=xl9432144 style='height:9.95pt'><span
                                                    style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='09:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                    <td colspan=3 class=xl9432144 style='border-left:none'><span
                                                    style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='09:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                    <td colspan=3 class=xl9432144 style='border-left:none'><span
                                                    style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='09:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                    <td colspan=3 class=xl9432144 style='border-left:none'><span
                                                    style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='09:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                    <td colspan=3 class=xl9432144 style='border-left:none'><span
                                                    style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='09:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td colspan=3 height=13 class=xl9532144 style='height:9.95pt'><span
                                                    style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='09:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9532144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='09:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9532144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='09:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9532144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='09:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9532144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='09:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td rowspan=3 height=39 class=xl8932144 style='height:29.85pt;border-top:
                                        none'>10:00</td>
                                    <td rowspan=3 class=xl9732144 style='border-top:none'>11:00</td>
                                    <td colspan=3 class=xl9232144><span
                                                    style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='10:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9232144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='10:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9332144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='10:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9332144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='10:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9332144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='10:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td colspan=3 height=13 class=xl14132144 style='height:9.95pt'><span
                                                    style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='10:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                    <td colspan=3 class=xl9432144 ><span
                                                    style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='10:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                    <td colspan=3 class=xl14132144 style='border-right:.5pt solid black;
                                                border-left:none'><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='10:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                    <td colspan=3 class=xl14132144 style='border-right:.5pt solid black;
                                                border-left:none'><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='10:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl14132144 style='border-left:none'><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='10:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span
                                                    style='mso-spacerun:yes'></span></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td colspan=3 height=13 class=xl9532144 style='height:9.95pt'><span
                                            style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='10:00'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9532144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='10:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9632144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='10:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9632144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='10:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9632144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='10:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td rowspan=3 height=39 class=xl8932144 style='height:29.85pt;border-top:
                                        none'>11:00</td>
                                    <td rowspan=3 class=xl9732144 style='border-top:none'>12:00</td>
                                            <td colspan=3 class=xl9232144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='11:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9232144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='11:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9332144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='11:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9332144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='11:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9332144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='11:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td colspan=3 height=13 class=xl9432144 style='height:9.95pt'><span
                                                    style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='11:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                    <td colspan=3 class=xl9432144 style='border-left:none'><span
                                                    style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='11:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9432144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='11:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9432144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='11:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9432144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='11:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td colspan=3 height=13 class=xl9532144 style='height:9.95pt'><span
                                                    style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='11:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9532144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='11:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9632144 style='border-right:.5pt solid black'><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='11:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                    <td colspan=3 class=xl9632144 style='border-right:.5pt solid black;
                                                border-left:none'><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='11:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9632144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='11:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td rowspan=3 height=39 class=xl8932144 style='height:29.85pt;border-top:
                                        none'>12:00</td>
                                    <td rowspan=3 class=xl9732144 style='border-top:none'>13:00</td>
                                            <td colspan=3 class=xl9232144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='12:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9232144><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='12:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9332144 style='border-right:.5pt solid black'><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='12:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                    <td colspan=3 class=xl9332144 style='border-right:.5pt solid black;
                                        border-left:none'><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='12:00'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9332144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='12:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td colspan=3 height=13 class=xl9432144 style='height:9.95pt'><span
                                                    style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='1200:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                    <td colspan=3 class=xl9432144 style='border-left:none'><span
                                                    style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='12:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                    <td colspan=3 class=xl14132144 style='border-right:.5pt solid black;
                                                border-left:none'><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='12:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                    <td colspan=3 class=xl14132144 style='border-right:.5pt solid black;
                                                border-left:none'><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='12:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9432144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='12:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td colspan=3 height=13 class=xl9532144 style='height:9.95pt'><span
                                                    style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='12:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9532144><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='12:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9632144 style='border-right:.5pt solid black'><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='12:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                    <td colspan=3 class=xl9632144 style='border-right:.5pt solid black;
                                                border-left:none'><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='12:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9632144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='12:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td rowspan=3 height=39 class=xl8932144 style='height:29.85pt;border-top:
                                        none'>13:00</td>
                                    <td rowspan=3 class=xl9732144 style='border-top:none'>14:00</td>
                                            <td colspan=3 class=xl9232144><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='13:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9232144><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='13:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9332144 style='border-right:.5pt solid black'><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='13:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                    <td colspan=3 class=xl9332144 style='border-right:.5pt solid black;
                                                border-left:none'><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='13:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9332144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='13:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td colspan=3 height=13 class=xl9432144 style='height:9.95pt'><span
                                                    style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='13:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span style='mso-spacerun:yes'> </span></td>
                                    <td colspan=3 class=xl9432144 style='border-left:none'><span
                                                    style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='13:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                    <td colspan=3 class=xl14132144 style='border-right:.5pt solid black;
                                                border-left:none'><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='13:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                    <td colspan=3 class=xl14132144 style='border-right:.5pt solid black;
                                                border-left:none'><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='13:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl14132144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='13:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td colspan=3 height=13 class=xl9532144 style='height:9.95pt'><span
                                                    style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='13:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9532144><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='13:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9632144 style='border-right:.5pt solid black'><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='13:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                    <td colspan=3 class=xl9632144 style='border-right:.5pt solid black;
                                                border-left:none'><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='13:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9632144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='13:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td rowspan=3 height=39 class=xl8932144 style='height:29.85pt;border-top:
                                        none'>14:00</td>
                                    <td rowspan=3 class=xl9732144 style='border-top:none'>15:00</td>
                                            <td colspan=3 class=xl9232144><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='14:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9232144><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='14:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9232144 style='border-right:.5pt solid black'><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='14:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                    <td colspan=3 class=xl9332144 style='border-right:.5pt solid black;
                                                border-left:none'><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='14:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9332144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='14:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td colspan=3 height=13 class=xl9432144 style='height:9.95pt'><span
                                                    style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='14:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                    <td colspan=3 class=xl9432144 style='border-left:none'><span
                                                    style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='14:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                    <td colspan=3 class=xl9432144 style='border-right:.5pt solid black;
                                                border-left:none'><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='14:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator></td>
                                    <td colspan=3 class=xl9432144 style='border-right:.5pt solid black;
                                                border-left:none'><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='14:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9432144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='14:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                            <td colspan=3 height=13 class=xl9532144 style='height:9.95pt'><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='14:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9532144><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='14:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9632144 style='border-right:.5pt solid black'><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='14:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                    <td colspan=3 class=xl9632144 style='border-right:.5pt solid black;
                                                border-left:none'><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='14:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9632144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='14:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td rowspan=3 height=39 class=xl8932144 style='height:29.85pt;border-top:
                                        none'>15:00</td>
                                    <td rowspan=3 class=xl9732144 style='border-top:none'>16:00</td>
                                            <td colspan=3 class=xl9332144><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='15:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9332144 style='border-right:.5pt solid black'><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='15:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9332144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='15:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9332144 style='border-right:.5pt solid black'><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='15:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9332144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='15:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td colspan=3 height=13 class=xl9432144 style='height:9.95pt'><span
                                                    style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='15:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                    <td colspan=3 class=xl9432144 style='border-right:.5pt solid black;
                                                border-left:none'><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='15:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9432144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='15:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator></td>
                                    <td colspan=3 class=xl9432144 style='border-right:.5pt solid black;
                                                border-left:none'><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='15:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9432144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='15:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                            <td colspan=3 height=13 class=xl9632144 style='height:9.95pt'><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='15:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9632144 style='border-right:.5pt solid black'><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='15:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9632144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='15:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9632144 style='border-right:.5pt solid black'><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='15:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9632144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='15:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td rowspan=3 height=39 class=xl8932144 style='height:29.85pt;border-top:
                                        none'>16:00</td>
                                    <td rowspan=3 class=xl9732144 style='border-top:none'>17:00</td>
                                            <td colspan=3 class=xl9332144><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='16:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9332144 style='border-right:.5pt solid black'><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='16:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9332144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='1600:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9332144 style='border-right:.5pt solid black'><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='16:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9332144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='16:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td colspan=3 height=13 class=xl9432144 style='height:9.95pt'><span
                                                    style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='16:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                    <td colspan=3 class=xl9432144 style='border-right:.5pt solid black;
                                                border-left:none'><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='16:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9432144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='16:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator></td>
                                    <td colspan=3 class=xl9432144 style='border-right:.5pt solid black;
                                                border-left:none'><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='16:00'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9432144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='16:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                            <td colspan=3 height=13 class=xl9632144 style='height:9.95pt'><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='16:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9632144 style='border-right:.5pt solid black'><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='16:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9632144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='16:0000:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9632144 style='border-right:.5pt solid black'><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='16:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9632144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='16:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td rowspan=3 height=39 class=xl8932144 style='height:29.85pt;border-top:
                                        none'>17:00</td>
                                    <td rowspan=3 class=xl9732144 style='border-top:none'>18:00</td>
                                            <td colspan=3 class=xl9332144><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='17:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9332144 style='border-right:.5pt solid black'><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='17:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9332144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='17:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9332144 style='border-right:.5pt solid black'><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='17:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9332144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='17:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td colspan=3 height=13 class=xl9432144 style='height:9.95pt'><span
                                                    style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='17:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                    <td colspan=3 class=xl9432144 style='border-right:.5pt solid black;
                                                border-left:none'><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='17:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9432144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='17:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator></td>
                                    <td colspan=3 class=xl9432144 style='border-right:.5pt solid black;
                                                border-left:none'><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='17:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9432144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='17:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                            <td colspan=3 height=13 class=xl9632144 style='height:9.95pt'><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='17:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9632144 style='border-right:.5pt solid black'><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='17:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9632144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='15:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9632144 style='border-right:.5pt solid black'><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='17:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9632144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='17:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td rowspan=3 height=39 class=xl8932144 style='height:29.85pt;border-top:
                                        none'>18:00</td>
                                    <td rowspan=3 class=xl9732144 style='border-top:none'>19:00</td>
                                            <td colspan=3 class=xl9332144><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='18:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9332144 style='border-right:.5pt solid black'><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='18:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9332144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='18:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9332144 style='border-right:.5pt solid black'><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='18:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9332144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='18:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td colspan=3 height=13 class=xl9432144 style='height:9.95pt'><span
                                                    style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='18:00:00.000000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                    <td colspan=3 class=xl9432144 style='border-right:.5pt solid black;
                                                border-left:none'><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='18:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9432144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='18:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator></td>
                                    <td colspan=3 class=xl9432144 style='border-right:.5pt solid black;
                                                border-left:none'><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='18:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9432144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='18:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                            <td colspan=3 height=13 class=xl9632144 style='height:9.95pt'><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='18:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9632144 style='border-right:.5pt solid black'><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='18:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9632144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='18:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9632144 style='border-right:.5pt solid black'><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='18:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9632144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='18:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td rowspan=3 height=39 class=xl8932144 style='height:29.85pt;border-top:
                                        none'>19:00</td>
                                    <td rowspan=3 class=xl9732144 style='border-top:none'>20:00</td>
                                            <td colspan=3 class=xl9332144><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='19:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9332144 style='border-right:.5pt solid black'><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='19:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9332144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='19:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9332144 style='border-right:.5pt solid black'><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='19:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9332144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='19:00:00.0000000'"><s:property value="nombre"/></s:if></s:if></s:iterator></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                    <td colspan=3 height=13 class=xl9432144 style='height:9.95pt'><span
                                                    style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='19:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                    <td colspan=3 class=xl9432144 style='border-right:.5pt solid black;
                                                border-left:none'><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='19:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9432144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='19:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator></td>
                                    <td colspan=3 class=xl9432144 style='border-right:.5pt solid black;
                                                border-left:none'><span style='mso-spacerun:yes'> </span><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='19:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator><span
                                            style='mso-spacerun:yes'> </span></td>
                                            <td colspan=3 class=xl9432144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='19:00:00.0000000'"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:url action="paselista" id="paselo"> <s:param name="clase_id"><s:property value="clase_id"/></s:param></s:url><s:a href="%{paselo}"><s:property value="grado"/> <s:property value="grupo"/></s:a></s:a></s:a></s:a></s:if></s:if></s:iterator></td>
                                </tr>
                                <tr class=xl7432144 height=13 style='mso-height-source:userset;height:9.95pt'>
                                            <td colspan=3 height=13 class=xl9632144 style='height:9.95pt'><s:iterator value="lista"><s:if test="dia=='Lunes'"><s:if test="hora_inicio=='19:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9632144 style='border-right:.5pt solid black'><s:iterator value="lista"><s:if test="dia=='Martes'"><s:if test="hora_inicio=='19:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9632144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Miercoles'"><s:if test="hora_inicio=='19:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9632144 style='border-right:.5pt solid black'><s:iterator value="lista"><s:if test="dia=='Jueves'"><s:if test="hora_inicio=='19:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                            <td colspan=3 class=xl9632144 style='border-left:none'><s:iterator value="lista"><s:if test="dia=='Viernes'"><s:if test="hora_inicio=='19:00:00.0000000'"><s:property value="aula"/>-<s:property value="edificio"/></s:if></s:if></s:iterator></td>
                                </tr>
                                <![if supportMisalignedColumns]>
                                <tr height=0 style='display:none'>
                                    <td width=955 style='width:60pt'></td>
                                    <td width=80 style='width:60pt'></td>
                                    <td width=53 style='width:40pt'></td>
                                    <td width=53 style='width:40pt'></td>
                                    <td width=53 style='width:40pt'></td>
                                    <td width=53 style='width:40pt'></td>
                                    <td width=53 style='width:40pt'></td>
                                    <td width=53 style='width:40pt'></td>
                                    <td width=53 style='width:40pt'></td>
                                    <td width=53 style='width:40pt'></td>
                                    <td width=53 style='width:40pt'></td>
                                    <td width=53 style='width:40pt'></td>
                                    <td width=53 style='width:40pt'></td>
                                    <td width=53 style='width:40pt'></td>
                                    <td width=53 style='width:40pt'></td>
                                    <td width=53 style='width:40pt'></td>
                                    <td width=53 style='width:40pt'></td>
                                </tr>
                                <![endif]>
                            </table>

                        </div>

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