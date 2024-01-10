<%-- 
    Document   : index
    Created on : 03-mar-2010, 20:24:26
    Author     : rcp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="rcp.jos.core.os"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="PRAGMA" content="NO-CACHE">
<meta http-equiv="CACHE-CONTROL" content="NO-CACHE">
<meta http-equiv="EXPIRES" content="-1">

<title>Escritorio Clientes</title>

<!-- EXT JS LIBRARY -->
<link rel="stylesheet" type="text/css" href="../Ext/2.2/resources/css/ext-all.css" />
<script src="../Ext/2.2/adapter/ext/ext-base.js"></script>
<script src="../Ext/2.2/ext-all.js"></script>

<!-- DESKTOP CSS -->
<link rel="stylesheet" type="text/css" href="resources/css/desktop.css" />
<link rel="stylesheet" type="text/css" href="system/dialogs/colorpicker/colorpicker.css" />

<!-- THEME CSS -->
<!-- MODULES CSS -->

<%

String _userID = (String)request.getSession().getAttribute("_userID");
String _groupID= (String)request.getSession().getAttribute("_groupID");

if (_userID==null)
    response.sendRedirect("login.html");

out.println(os.getAllStylesCSS(_userID, _groupID));
%>

<!-- SYSTEM DIALOGS AND CORE -->
<!-- In a production environment these should be minified into one file -->
<script src="system/dialogs/colorpicker/ColorPicker.js"></script>
<script src="system/core/App.js"></script>
<script src="system/core/Desktop.js"></script>
<script src="system/core/HexField.js"></script>
<script src="system/core/Module.js"></script>
<script src="system/core/Notification.js"></script>
<script src="system/core/Shortcut.js"></script>
<script src="system/core/StartMenu.js"></script>
<script src="system/core/TaskBar.js"></script>

<!-- QoDesk -->
<script src="QoDesk.jsp"></script>
</head>

<body scroll="no">

<div id="x-desktop"></div>

<div id="ux-taskbar">
	<div id="ux-taskbar-start"></div>
	<div id="ux-taskbar-panel-wrap">
		<div id="ux-quickstart-panel"></div>
		<div id="ux-taskbuttons-panel"></div>
		<div id="ux-systemtray-panel"></div>
	</div>
	<div class="x-clear"></div>
</div>

</body>
</html>

