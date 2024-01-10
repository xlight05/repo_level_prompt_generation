var jsonrpc;

function diagnostics_loadPane(){
	jsonrpc = new JSONRpcClient(opl_getUrl()+"/JSON-RPC");
	dijit.byId('tabContainer').selectChild('tabSettings');
	var settings = dijit.byId("tabSettings");
	settings.setHref("diagnostics/diagnostics.jsp");
	setTimeout("diagnostics_getValues()",delay);
}

//jsonrpc.odecontroller.getAllValues();

var delay = 1000;
var stopped = true;

//setTimeout("diagnostics_getValues()",delay); //poll server again after delay miliseconds

function diagnostics_start(){
	if(jsonrpc.diagnostic != null){
		jsonrpc.diagnostic.startPolling();
	}
	stopped = false;
	setTimeout("diagnostics_getValues()",delay);
}

function diagnostics_stop(){
	if(jsonrpc.diagnostic != null){
		jsonrpc.diagnostic.stopPolling();
	}
	stopped = true;
}

function diagnostics_getValues(){
	if(stopped)
		return;
	if(jsonrpc.diagnostic != null){
		var values = jsonrpc.diagnostic.getAllValues();
		var objAddressValueList = document.getElementById("addressvaluelist");
		if(objAddressValueList != null){
			objAddressValueList.innerHTML = "<ul>";
			var keys = new Array();
			var i = 0;
			for(key in values.map) 
			{
				keys[i] = key;
				++i;
			}
			keys.sort();
			var j = 0;
			for(j=0; j<i; ++j)
			{
				objAddressValueList.innerHTML += "<li>" + keys[j] + "<br><span>" + values.map[keys[j]] + "</span></li>";
			}
			objAddressValueList.innerHTML += "</ul>";
		}
	}
	setTimeout("diagnostics_getValues()",delay);
}
