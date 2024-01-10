<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="se.openprocesslogger.proxy.OplControllerProxy"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Open Process Logger</title>
 <!-- 
Open process logger.
Uses the standard logging/analyzing tools with several plc systems.

 -->
 
<style type="text/css">
	@import "scripts/dojo-release-1.3.2/dijit/themes/tundra/tundra.css";
	@import "css/ipb.css";
	@import "scripts/dojo-release-1.3.2/dojox/grid/_grid/Grid.css";
	@import "scripts/dojo-release-1.3.2/dojox/grid/_grid/tundraGrid.css";
</style>

<script type="text/javascript" djConfig.locale="sv-se" djconfig="parseOnLoad: true" src="scripts/dojo-release-1.3.2/dojo/dojo.js"></script>
<script type="text/javascript" src="scripts/jabsorb-1.3.1/jsonrpc.js"></script>
<script type="text/javascript" src="scripts/openprocesslogger.js"></script>
<script type="text/javascript" src="scripts/icons.js"></script>
<script type="text/javascript" language="javascript" src="scripts/timeutil.js"></script>
<script type="text/javascript" language="javascript" src="scripts/opldebug.js"></script>
<script type="text/javascript" language="javascript" src="analyze/script/analyze.js"></script>
<script type="text/javascript" language="javascript" src="analyze/script/taskanalyze.js"></script>
<script type="text/javascript" language="javascript" src="logger/logging.js"></script>
<script type="text/javascript" language="javascript" src="logger/subscriptions.js"></script>
<script type="text/javascript" language="javascript" src="scripts/gui.js"></script>
<script type="text/javascript" language="javascript" src="scripts/debug.js"></script>
<script type="text/javascript" language="javascript" src="diagnostics/diagnostics.js"></script>

<jsp:useBean id="JSONRPCBridge" scope="session" class="org.jabsorb.JSONRPCBridge" />
<%
OplControllerProxy controller = new OplControllerProxy("appconfig.xml", false);
JSONRPCBridge.registerObject("controller", controller);
%>

</head>
<body class="tundra">
	<div id="alertfield"></div>
	<div id="main" dojoType="dijit.layout.LayoutContainer" class="tundra" style="padding:0px; margin:0px">
		<div id="header" align="center" dojoType="dijit.layout.ContentPane" layoutAlign="top" style="padding:0px;margin:0px;float:center;vertical-align:middle;display:none;z-index:1000;width:100%;height:100%;">
			<div align="center" style="float:center;margin-top:100px;"><img src="images/opl_demo.gif"></div>
		</div>

		<div id="pageheader" dojoType="dijit.layout.ContentPane" layoutAlign="top">
			<p>version 0.1b</p>
			<h1>Open Process Logger</h1>
		</div>

		<div dojoType="dijit.layout.SplitContainer" id="mainSplit" sizerWidth="7" orientation="horizontal" layoutAlign="client" style="padding:0px; margin:0px">
			<div id="mainAccordionContainer" dojoType="dijit.layout.AccordionContainer" duration="200" sizeMin="20" sizeShare="10">

				<div id="TemplatesPane" dojoType="dijit.layout.AccordionPane" title="Templates" onSelected="onLogTreeAction(); setTabName('Templates');">
					<div id="TemplateContainer" class="listcontainer">
						<div id="logTaskTemplatePaneTable"></div>
					</div>
					<div class="buttoncontainer">
						<button id="refreshTemplates" dojoType="dijit.form.Button" onclick="logging_reloadTemplates();">Refresh</button>
					</div>					
				</div>

				<div id="TasksPane" dojoType="dijit.layout.AccordionPane" title="Tasks" onSelected="onLogTreeAction(); setTabName('Tasks');">
					<div id="TaskContainer" class="listcontainer">
						<div id="logTaskPaneTable"></div>
					</div>
					<div id="logging_loadingMessage" style="display:block;"></div>

					<div class="buttoncontainer">
						<button dojoType="dijit.form.Button" onclick="logging_refreshActiveTasks();">Refresh</button>
					</div>
				</div>

				<div id="AnalyzePane" dojoType="dijit.layout.AccordionPane" title="Analyze" onSelected="taskanalyze_loadLatestTasks(); setTabName('Analyze');">
					<table id="taskanalyze_analyzePaneTable"></table>
				</div>
				
				<div id="DebugPane" dojoType="dijit.layout.AccordionPane" title="Debug" onSelected="debug_loadPane(); setTabName('Debug');">
					<div id="DebugContainer" class="listcontainer"></div>
				</div>
				<div id="DiagnosticsPane" dojoType="dijit.layout.AccordionPane" title="Diagnostics" onSelected="diagnostics_loadPane(); setTabName('Diagnostics');">
				</div>
			</div>

			<div id="tabContainer" dojoType="dijit.layout.TabContainer" sizeShare="40">
				<div id="tabSettings" dojoType="dijit.layout.ContentPane" title="Templates" style="padding:0px; margin:0px; overflow:scrollable;"></div>
			</div>
		</div>
	</div>

	<div id="subscriptiondialog" dojoType="dijit.Dialog" title="Subscription settings">
		<div id="subscription_itemsettings"></div>
	</div>
</body>
</html>