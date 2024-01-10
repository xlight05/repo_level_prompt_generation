<%@ page contentType="image/svg+xml"%>
<%@ taglib uri="svglib" prefix="s1"%>
<%@page import="se.openprocesslogger.proxy.graphviewer.GraphViewerProxy"%>
<jsp:useBean id="JSONRPCBridge" scope="session"
     class="org.jabsorb.JSONRPCBridge" />
<%
	JSONRPCBridge.registerObject("graphviewer", new GraphViewerProxy());
%>

<svg xmlns="http://www.w3.org/2000/svg" 
  xmlns:xlink="http://www.w3.org/1999/xlink" 
  width='1000px' height='1000px'
  onload="graphviewer_onload(evt);">
  	<script type="text/ecmascript"><![CDATA[
	<jsp:include page="../script/graphviewer.js"></jsp:include>
	<jsp:include page="../script/data/timeline.js"></jsp:include>
	<jsp:include page="../script/data/analog.js"></jsp:include>	
	<jsp:include page="../script/zoom/zoomevents.js"></jsp:include>
	<jsp:include page="../script/graphbuilder.js"></jsp:include>
	<jsp:include page="../script/eventhandler.js"></jsp:include>
	<jsp:include page="../script/data/dataloading.js"></jsp:include>	
	<jsp:include page="../script/tags/tagfactory.js"></jsp:include>
	<jsp:include page="../script/tags/analogGraphChart.js"></jsp:include>
	<jsp:include page="../script/tags/analogStepGraphChart.js"></jsp:include>
	<jsp:include page="../script/tags/digitalGraphChart.js"></jsp:include>
	<jsp:include page="../script/tags/barChart.js"></jsp:include>
	<jsp:include page="../script/tags/eventChart.js"></jsp:include>
	<jsp:include page="../script/tags/subBarChart.js"></jsp:include>
	<jsp:include page="../script/tags/mousemarker.js"></jsp:include>
	<jsp:include page="../script/tags/numericChart.js"></jsp:include>
	<jsp:include page="../script/contextmenu.js"></jsp:include>
	<jsp:include page="../../scripts/timeutil.js"></jsp:include>
	]]>	</script>
	
  	<g id="graphviewer_body">
	</g>
	
	<s1:AnalogChartShell />
	<s1:Markers/>
	<s1:DigitalChartShell/>
	<s1:BarChartShell/>
	<s1:EventChartShell/>
	<s1:MouseMarkers/>
	
	<g id="contextmenu" visibility="hidden">
  		<rect x="0" y="0" width="150" height="100" fill="#dde7f2" rx="10" stroke="#B1C9E2" stroke-width="10" id="contextmenu_bg"></rect>
  		<text x="13" y="20" onclick="contextmenu_exportData()" id="contextmenu_expcsv">Export data as csv</text>
  		<text x="13" y="40" onclick="contextmenu_event1()" id="contextmenu_event1" visibility="hidden">Event 1</text>
  	</g>
  	
  	<rect x="0" y="0" width="100" height="100" style="fill:white;fill-opacity:0.5" id="loadingmessage_rect"></rect>
  	
  	<g id="loadingmessage" visibility="hidden">
  		<rect x="0" y="0" width="200" height="80" fill="#dde7f2" rx="10" stroke="#B1C9E2" stroke-width="10" id="loadingmessage_bg"></rect>
  		<text x="100" y="20" dominant-baseline="middle" text-anchor="middle" id="loadingmessage_txt1">Loading message</text>
  		<text x="100" y="40" dominant-baseline="middle" text-anchor="middle" id="loadingmessage_txt2">Current action</text>
  		<text x="100" y="60" dominant-baseline="middle" text-anchor="middle" id="loadingmessage_txt3">Loading time</text>
  	</g>
  	
  	<g id="numericChart_" visibility="hidden">
  		<text x="-90" y="0" dominant-baseline="middle" text-anchor="right" id="numericChart_title_" style="font-family:Helvetica, Arial, Verdana;" font-size="10">Title1</text>
  		<text x="0" y="0" dominant-baseline="middle" text-anchor="right" id="chart_currentValue_" style="font-family:Helvetica, Arial, Verdana;"  font-size="10">Value</text>
  	</g>
  	
	<script type="text/ecmascript"><![CDATA[
		<jsp:include page="../../scripts/jabsorb-1.3.1/jsonrpc.js"></jsp:include>
	]]>	</script>
</svg>