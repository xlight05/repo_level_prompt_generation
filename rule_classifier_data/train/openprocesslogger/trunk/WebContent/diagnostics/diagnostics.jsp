<%@page import="se.openprocesslogger.proxy.DiagnosticsProxy"%>
<jsp:useBean id="JSONRPCBridge" scope="session" class="org.jabsorb.JSONRPCBridge" />
<%
	if(JSONRPCBridge.lookupObject("diagnostic") == null){
		DiagnosticsProxy diag = new DiagnosticsProxy();
		JSONRPCBridge.registerObject("diagnostic", diag);
	}
%>
<div>
  	<button dojoType="dijit.form.Button" onclick="diagnostics_start();">Start polling</button>
	<button dojoType="dijit.form.Button" onclick="diagnostics_stop();">Stop polling</button>
	<div class="formSubArea" id="addressvaluelist"></div>
</div>