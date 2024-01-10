<%-- 
    Document   : formatoAsistencia
    Created on : 22/11/2012, 06:09:28 PM
    Author     : Mauricio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Asistencias</title>
        <meta name=ProgId content=Excel.Sheet>
        <meta name=Generator content="Microsoft Excel 14">
        <link rel=File-List href="ListadeAsistenciaAdmonBD_archivos/filelist.xml">

        <style id="Lista de Asistencia Admon BD_13674_Styles">
            <!--table
            {mso-displayed-decimal-separator:"\.";
             mso-displayed-thousand-separator:"\,";}
            .xl6813674
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
            .xl6913674
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
            .xl7013674
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
            .xl7113674
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
            .xl7213674
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
            .xl7313674
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
            .xl7413674
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
            .xl7513674
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
            .xl7613674
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
            .xl7713674
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
            .xl7813674
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
            .xl7913674
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
            .xl8013674
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
            .xl8113674
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
            .xl8213674
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
            .xl8313674
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
            .xl8413674
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
            .xl8513674
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
            .xl8613674
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
            .xl8713674
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
            .xl8813674
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
            .xl8913674
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
            .xl9013674
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
            .xl9113674
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
            .xl9213674
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
            .xl9313674
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
            .xl9413674
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
            .xl9513674
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
            .xl9613674
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
            .xl9713674
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
            .xl9813674
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
            .xl9913674
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
            .xl10013674
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
            .xl10113674
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
            .xl10213674
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
            .xl10313674
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
            .xl10413674
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
            .xl10513674
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
            .xl10613674
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
            .xl10713674
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
            .xl10813674
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
            .xl10913674
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
            .xl11013674
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
            .xl11113674
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
            .xl11213674
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
            .xl11313674
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
            .xl11413674
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
            .xl11513674
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
            .xl11613674
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
            .xl11713674
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
             border-bottom:1.0pt solid windowtext;
             border-left:1.0pt solid windowtext;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl11813674
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
             border-top:.5pt solid windowtext;
             border-right:none;
             border-bottom:1.0pt solid windowtext;
             border-left:.5pt solid windowtext;
             mso-background-source:auto;
             mso-pattern:auto;
             mso-protection:unlocked visible;
             white-space:nowrap;}
            .xl11913674
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
             border-bottom:1.0pt solid windowtext;
             border-left:.5pt solid windowtext;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl12013674
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
             border-bottom:1.0pt solid windowtext;
             border-left:.5pt solid windowtext;
             background:#BFBFBF;
             mso-pattern:black none;
             white-space:nowrap;}
            .xl12113674
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
             border-bottom:1.0pt solid windowtext;
             border-left:.5pt solid windowtext;
             background:#BFBFBF;
             mso-pattern:black none;
             white-space:nowrap;}
            .xl12213674
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
             text-align:left;
             vertical-align:middle;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl12313674
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
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl12413674
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
             border-left:none;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl12513674
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
            .xl12613674
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
            .xl12713674
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
            .xl12813674
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
            .xl12913674
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
            .xl13013674
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
            .xl13113674
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
            .xl13213674
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
            .xl13313674
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
            .xl13413674
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
             text-align:left;
             vertical-align:middle;
             border-top:1.0pt solid windowtext;
             border-right:none;
             border-bottom:none;
             border-left:none;
             mso-background-source:auto;
             mso-pattern:auto;
             white-space:nowrap;}
            .xl13513674
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
            .xl13613674
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
            .xl13713674
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

            .xl13813674
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
            .xl72136741 {padding:0px;
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
            .xl721367411 {padding:0px;
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
            -->
        </style>

    </head>
    <body>
        <div id="Lista de Asistencia Admon BD_13674" align=center
             x:publishsource="Excel">
            <table border=0 cellpadding=0 cellspacing=0 width=974 class=xl7013674
                   style='border-collapse:collapse;table-layout:fixed;width:714pt'>
                <col class=xl7013674 width=21 style='mso-width-source:userset;mso-width-alt:
                     768;width:16pt'>
                <col class=xl7013674 width=227 style='mso-width-source:userset;mso-width-alt:
                     8301;width:170pt'>
                <col class=xl7013674 width=11 span=66 style='mso-width-source:userset;
                     mso-width-alt:402;width:8pt'>
                <tr height=24 style='mso-height-source:userset;height:18.0pt'>
                    <td height=24 width=21 style='height:18.0pt;width:16pt' align=left
                        valign=top>

                        <![if !vml]><![endif]><span
                            style='mso-ignore:vglayout2'>
                            <table cellpadding=0 cellspacing=0>
                                <tr>
                                    <td height=24 class=xl6813674 width=21 style='height:18.0pt;width:16pt'>&nbsp;</td>
                                </tr>
                            </table>
                        </span></td>
                    <td class=xl6913674 width=227 style='width:170pt'>&nbsp;</td>
                    <td colspan=66 height=24 width=726 style='border-right:1.0pt solid black;
                        height:18.0pt;width:528pt' align=left valign=top>


                        <![if !vml]><![endif]><span style='mso-ignore:vglayout2'>
                            <table cellpadding=0 cellspacing=0>
                                <!-- INICIA SECCION DE ENCABEZADO -->
                                <tr>
                                    <td colspan=66 height=24 class=xl12513674 width=726 style='border-right:
                                        1.0pt solid black;height:18.0pt;width:528pt'>CONTROL Y SEGUIMIENTO DEL
                                        ALUMNO (ASISTENCIAS)<span style='mso-spacerun:yes'> </span></td>
                                </tr>
                            </table>
                        </span></td>
                </tr>
                <!-- INICIA AREA DE DATOS DE ENCABEZADO -->
                <s:iterator value="encabezado">
                    <tr height=15 style='height:11.25pt'>
                        <td height=15 class=xl7113674 style='height:11.25pt'>&nbsp;</td>
                        <td class=xl7213674></td>
                        <td colspan=5 class=xl7713674>CARRERA</td>
                        <td class=xl7313674>&nbsp;</td>
                        <td class=xl7313674>&nbsp;</td>
                        <td class=xl7413674 colspan=21><s:property value="carrera" /></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td colspan=5 class=xl12713674>PERIODO<span style='mso-spacerun:yes'> </span></td>
                        <td colspan=16 class=xl12813674><s:property value="periodo" /></td>
                        <td class=xl7513674></td>
                        <td class=xl7513674></td>
                        <td class=xl7513674></td>
                        <td class=xl7513674></td>
                        <td class=xl7513674></td>
                        <td class=xl7513674></td>
                        <td class=xl7513674></td>
                        <td class=xl7513674></td>
                        <td class=xl7513674></td>
                        <td class=xl7613674>&nbsp;</td>
                    </tr>
                    <tr height=15 style='height:11.25pt'>
                        <td height=15 class=xl7113674 style='height:11.25pt'>&nbsp;</td>
                        <td class=xl7213674></td>
                        <td class=xl7713674 colspan=5>ASIGNATURA</td>
                        <td class=xl7313674>&nbsp;</td>
                        <td class=xl7313674>&nbsp;</td>
                        <td class=xl7413674 colspan=15><s:property value="materia" /></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td colspan=8 class=xl7713674>CUATRIMESTRE</td>
                        <td colspan=5 class=xl12913674> <s:property value="cuatrimestre" /><span style='mso-spacerun:yes'> </span></td>
                        <td colspan=4 class=xl12713674>GRUPO</td>
                        <td colspan=2 class=xl12813674><s:property value="grupo"/></td>
                        <td class=xl7513674></td>
                        <td class=xl7813674></td>
                        <td class=xl7013674></td>
                        <td class=xl7813674></td>
                        <td class=xl7813674></td>
                        <td class=xl7813674></td>
                        <td class=xl7813674></td>
                        <td class=xl7813674></td>
                        <td class=xl7813674></td>
                        <td class=xl7813674></td>
                        <td class=xl7813674></td>
                        <td class=xl7913674>&nbsp;</td>
                    </tr>
                    <tr height=15 style='height:11.25pt'>
                        <td height=15 class=xl7113674 style='height:11.25pt'>&nbsp;</td>
                        <td class=xl7213674>&nbsp;</td>
                        <td colspan=5 class=xl7713674>DOCENTE</td>
                        <td class=xl7313674>&nbsp;</td>
                        <td class=xl7313674>&nbsp;</td>
                        <td class=xl7413674 colspan=13><s:property value="profesor"/></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td class=xl7413674></td>
                        <td colspan=10 class=xl12713674>HORAS POR SEMANA</td>
                        <td colspan=6 class=xl12913674> <s:property value="horas_por_semana"/></td>
                        <td class=xl7213674></td>
                        <td class=xl7213674></td>
                        <td class=xl7213674></td>
                        <td class=xl7213674></td>
                        <td class=xl7513674></td>
                        <td class=xl7513674></td>
                        <td class=xl7513674></td>
                        <td class=xl7513674></td>
                        <td class=xl7513674></td>
                        <td class=xl7513674></td>
                        <td class=xl7513674></td>
                        <td class=xl7513674></td>
                        <td class=xl7513674></td>
                        <td class=xl7513674></td>
                        <td class=xl7613674>&nbsp;</td>
                    </tr>
                </s:iterator>
                <!-- TERMINA AREA DE DATOS DE ENCABEZADO -->
                <tr height=15 style='height:11.25pt'>
                    <td height=15 class=xl7113674 style='height:11.25pt'>&nbsp;</td>
                    <td class=xl7213674></td>
                    <td colspan=10 rowspan=2 class=xl12713674 style='border-bottom:1.0pt solid black'>FIRMA
                        DEL DOCENTE</td>
                    <td class=xl7813674></td>
                    <td class=xl7813674></td>
                    <td class=xl7813674></td>
                    <td class=xl7813674></td>
                    <td class=xl7813674></td>
                    <td class=xl7813674></td>
                    <td class=xl7813674></td>
                    <td class=xl7813674></td>
                    <td class=xl7813674></td>
                    <td class=xl7813674></td>
                    <td class=xl7813674></td>
                    <td class=xl7813674></td>
                    <td class=xl7813674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7513674></td>
                    <td class=xl7513674></td>
                    <td class=xl7513674></td>
                    <td class=xl7513674></td>
                    <td class=xl7513674></td>
                    <td class=xl7513674></td>
                    <td class=xl7513674></td>
                    <td class=xl7513674></td>
                    <td class=xl7513674></td>
                    <td class=xl7513674></td>
                    <td class=xl7613674>&nbsp;</td>
                </tr>
                <tr height=16 style='height:12.0pt'>
                    <td height=16 class=xl8013674 style='height:12.0pt'>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8213674>&nbsp;</td>
                    <td class=xl8213674>&nbsp;</td>
                    <td class=xl8213674>&nbsp;</td>
                    <td class=xl8213674>&nbsp;</td>
                    <td class=xl8313674>&nbsp;</td>
                    <td class=xl8313674>&nbsp;</td>
                    <td class=xl8213674>&nbsp;</td>
                    <td class=xl8213674>&nbsp;</td>
                    <td class=xl8213674>&nbsp;</td>
                    <td class=xl8213674>&nbsp;</td>
                    <td class=xl8213674>&nbsp;</td>
                    <td class=xl8213674>&nbsp;</td>
                    <td class=xl8213674>&nbsp;</td>
                    <td class=xl8213674>&nbsp;</td>
                    <td class=xl8213674>&nbsp;</td>
                    <td class=xl8213674>&nbsp;</td>
                    <td class=xl8213674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8113674>&nbsp;</td>
                    <td class=xl8413674>&nbsp;</td>
                </tr>
                <!-- TERMINA SECCION DE ENCABEZADO -->

                <!-- INICIA SECCION DE ASISTENCIAS DEL GRUPO -->
                <!-- INICIA AREA DE NUMERO DE REVISIONES -->
                <tr height=16 style='mso-height-source:userset;height:12.0pt'>
                    <td height=16 class=xl8513674 style='height:12.0pt;border-top:none'>&nbsp;</td>
                    <td class=xl8613674 style='border-top:none'>&nbsp;</td>
                    <td colspan=21 class=xl13113674 style='border-right:.5pt solid black'>Revisión
                        1<span style='mso-spacerun:yes'> </span></td>
                    <td class=xl8713674 style='border-top:none;border-left:none'>%</td>
                    <td colspan=21 class=xl13113674 style='border-right:.5pt solid black;
                        border-left:none'>Revisión 2<span style='mso-spacerun:yes'>   </span></td>
                    <td class=xl8713674 style='border-top:none;border-left:none'>%</td>
                    <td colspan=21 class=xl13113674 style='border-right:.5pt solid black;
                        border-left:none'>Revisión 3<span style='mso-spacerun:yes'> </span></td>
                    <td class=xl8813674 style='border-top:none;border-left:none'>%</td>
                </tr>
                <!-- TERMINA AREA DE NUMERO DE REVISIONES -->
                <!-- INICIA AREA DE MESES -->
                <tr height=16 style='mso-height-source:userset;height:12.0pt'>
                    <td height=16 class=xl7113674 style='height:12.0pt'>&nbsp;</td>
                    <td class=xl8913674><span style='mso-spacerun:yes'>              </span>MES:</td>
                    <td colspan=21 class=xl13513674>Agosto-Septiembre</td>
                    <td class=xl9013674>&nbsp;</td>
                    <td colspan=21 class=xl9513674>Septiembre-Octubre</td>
                    <td class=xl9113674>&nbsp;</td>
                    <td colspan=21 class=xl13713674 style='border-right:.5pt solid black;
                        border-left:none'>Noviembre-Diciembre</td>
                    <td class=xl9213674 style='border-left:none'>&nbsp;</td>
                </tr>
                <!-- TERMINA AREA DE MESES -->
                <!-- INICIA AREA DE UNIDADES TEMATICAS -->
                <tr height=16 style='mso-height-source:userset;height:12.0pt'>
                    <td height=16 class=xl7113674 style='height:12.0pt'>&nbsp;</td>
                    <td class=xl8913674>Unidad Temática</td>
                    <td class=xl9313674>I</td>
                    <td class=xl9413674>&nbsp;</td>
                    <td class=xl9413674>&nbsp;</td>
                    <td class=xl9413674>&nbsp;</td>
                    <td class=xl9413674>&nbsp;</td>
                    <td class=xl9413674>&nbsp;</td>
                    <td class=xl9413674>&nbsp;</td>
                    <td class=xl9413674>&nbsp;</td>
                    <td class=xl9413674>&nbsp;</td>
                    <td class=xl9413674 colspan=2>II</td>
                    <td class=xl9513674>&nbsp;</td>
                    <td class=xl9513674>&nbsp;</td>
                    <td class=xl9513674>&nbsp;</td>
                    <td class=xl9513674>&nbsp;</td>
                    <td class=xl9513674>&nbsp;</td>
                    <td class=xl9513674>&nbsp;</td>
                    <td class=xl9513674>&nbsp;</td>
                    <td class=xl9513674>&nbsp;</td>
                    <td class=xl9513674>&nbsp;</td>
                    <td class=xl9513674>&nbsp;</td>
                    <td class=xl9613674>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>III</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9613674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>IV</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl9213674 style='border-top:none'>&nbsp;</td>
                </tr>
                <!-- TERMINA AREA DE UNIDADES TEMATICAS -->
                <!-- INICIA AREA DE DIAS DE CLASE -->
                <tr height=24 style='mso-height-source:userset;height:18.0pt'>
                    <td height=24 class=xl9713674 style='height:18.0pt'>&nbsp;</td>
                    <td class=xl8913674><span style='mso-spacerun:yes'>              </span>DIAS:</td>
                    <!--  -->
                    <s:iterator value="dias">
                        <s:if test="%{dia!=0}"><td class=xl9813674 style='border-left:none'><s:property value="dia"/></td></s:if>
                        <s:else><td class=xl9813674 style='border-left:none'>&nbsp;</td></s:else>
                    </s:iterator>
                </tr>
                <!-- TERMINA AREA DE DIAS DE CLASE -->
                <!-- INICIA AREA DE ENCABEZADO DE ALUMNOS -->
                <tr height=15 style='height:11.25pt'>
                    <td height=15 class=xl10313674 style='height:11.25pt;border-top:none'>No</td>
                    <td class=xl10413674 style='border-left:none'>Nombre del alumno</td>
                    <td class=xl10513674 style='border-top:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10613674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10613674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10513674 style='border-top:none;border-left:none'>&nbsp;</td>
                    <td class=xl10713674 style='border-top:none;border-left:none'>&nbsp;</td>
                </tr>
                <!-- TERMINA AREA DE ENCABEZADO DE ALUMNOS AREA DE MESES -->
                <!-- INICIA AREA DE DATOS DE ASISTENCIA DE ALUMNOS -->
                <s:iterator value="alumnos">
                    <tr height=16 style='mso-height-source:userset;height:12.0pt'>
                        <td height=16 class=xl10813674 style='height:12.0pt;border-top:none'><s:property value="alumno_id"/></td>
                        <td class=xl10913674 style='border-top:none;border-left:none'><s:property value="nombre"/> <s:property value="a_paterno"/> <s:property value="a_materno"/> </td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11113674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11113674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11013674 style='border-top:none;border-left:none'>&nbsp;</td>
                        <td class=xl11213674 style='border-top:none;border-left:none'>&nbsp;</td>
                    </tr>
                </s:iterator>
                <!-- TERMINA AREA DE DATOS DE ASISTENCIA DE ALUMNOS -->
                <!-- TERMINA SECCION DE ASISTENCIAS DEL GRUPO -->

                <!-- INICIA SECCION DE PIE DE PAGINA -->
                <tr height=15 style='height:11.25pt'>
                    <td height=15 class=xl7213674 style='height:11.25pt'></td>
                    <td class=xl7213674></td>
                    <td colspan=8 class=xl13413674>ASISTENCIAS</td>
                    <td class=xl12213674></td>
                    <td class=xl7013674></td>
                    <td class=xl12313674>.</td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl12213674></td>
                    <td class=xl12213674></td>
                    <td colspan=8 class=xl13413674>&nbsp;</td>
                    <td class=xl7013674></td>
                    <td class=xl12313674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl12213674></td>
                    <td class=xl7013674></td>
                    <td class=xl12213674></td>
                    <td class=xl12213674></td>
                    <td class=xl12213674></td>
                    <td class=xl12213674></td>
                    <td class=xl12213674></td>
                    <td class=xl12213674></td>
                    <td class=xl12213674></td>
                    <td class=xl12213674></td>
                    <td class=xl12213674></td>
                    <td class=xl12213674></td>
                    <td class=xl12213674></td>
                    <td class=xl12213674></td>
                    <td class=xl12213674></td>
                    <td class=xl12213674></td>
                    <td class=xl12213674></td>
                    <td class=xl12213674></td>
                    <td class=xl12213674></td>
                    <td class=xl12213674></td>
                    <td class=xl12213674></td>
                    <td class=xl12213674></td>
                    <td class=xl12213674>R<span style='display:none'>ev. 05</span></td>
                    <td class=xl8613674 style='border-top:none'>&nbsp;</td>
                    <td class=xl8613674 style='border-top:none'>&nbsp;</td>
                    <td class=xl8613674 style='border-top:none'>&nbsp;</td>
                    <td class=xl8613674 style='border-top:none'>&nbsp;</td>
                </tr>
                <tr height=15 style='height:11.25pt'>
                    <td height=15 class=xl7213674 style='height:11.25pt'></td>
                    <td class=xl7213674></td>
                    <td colspan=9 class=xl12213674>JUSTIFICACION</td>
                    <td class=xl12213674></td>
                    <td class=xl12313674>X</td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl12213674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td colspan=17 class=xl12213674>TOTAL DE CLASES IMPARTIDAS:</td>
                    <td class=xl12213674></td>
                    <td class=xl12413674>&nbsp;</td>
                    <td class=xl12413674>&nbsp;</td>
                    <td class=xl12413674>&nbsp;</td>
                    <td class=xl12413674>&nbsp;</td>
                </tr>
                <tr height=15 style='height:11.25pt'>
                    <td height=15 class=xl7213674 style='height:11.25pt'></td>
                    <td class=xl7213674></td>
                    <td colspan=5 class=xl12213674>FALTAS</td>
                    <td class=xl12213674></td>
                    <td class=xl12213674></td>
                    <td class=xl12213674></td>
                    <td class=xl12213674></td>
                    <td class=xl7013674></td>
                    <td class=xl12313674>/</td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7513674></td>
                    <td class=xl7513674></td>
                    <td colspan=9 class=xl7813674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7013674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7213674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                    <td class=xl7013674></td>
                </tr>
                <![if supportMisalignedColumns]>
                <tr height=0 style='display:none'>
                    <td width=21 style='width:16pt'></td>
                    <td width=227 style='width:170pt'></td>
                    <td width=726 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                    <td width=11 style='width:8pt'></td>
                </tr>
                <![endif]>
                <!-- TERMINA SECCION DE PIE DE PAGINA -->
            </table>

        </div>
    </body>
</html>
