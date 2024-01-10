var SELECTED_ITEM = null;
var DATA_TAB = null;

dojo.require("dojo.data.ItemFileWriteStore");
dojo.require("dijit.layout.LayoutContainer");
dojo.require("dijit.layout.SplitContainer");
dojo.require("dijit.layout.BorderContainer");
dojo.require("dijit.layout.ContentPane");
dojo.require("dijit.layout.AccordionContainer");
dojo.require("dijit.layout.TabContainer");
dojo.require("dijit.Dialog");
dojo.require("dijit.ProgressBar");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.FilteringSelect");
dojo.require("dijit.Tree");
dojo.require("dijit.form.DateTextBox");
dojo.require("dijit.form.TimeTextBox");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.Textarea");
dojo.require("dijit.form.CheckBox");
dojo.require("dijit.form.NumberTextBox");
dojo.require("dijit.form.ComboBox");
dojo.require("dojox.layout.ScrollPane");
dojo.require("dojox.grid.Grid");
dojo.require("dojo.parser");

var loggStore;
var analyzeStore;

function init()
{
	window.setTimeout(opl_hideLogo,1000);
	var headerNode = document.getElementById("header");
	headerNode.setAttribute("style","display:inline;padding:0px;margin:0px;float:center;vertical-align:middle;z-index:1;width:100%;height:100%;");
	try
	{
		jsonrpc = new JSONRpcClient(opl_getUrl()+"/JSON-RPC");
	}
	catch(exception)
	{
		opl_analyzeError(exception);
		return;
	}
	onLogTreeAction("");
}

function opl_hideLogo(){
	//alert("hide logo");
	//dojo.byId("header").setAttribute("style","display:none;");
	var headerNode = document.getElementById("header");
	var hparentNode = document.getElementById("main");
	if(hparentNode)
	{
		hparentNode.removeChild(headerNode);
		//header.domNode.remove();
	}
}

function opl_checkForErrors(){
	/* Disabled by Tomi. The alert was already commentated so the function would never do anything. 
	 * The getError caused a hang so the refresh button for the tasks never stopped loading.
	var s = jsonrpc.controller.getError();
	if(s){
		alert("Error info: "+s.message);
	}
	*/
}

function loggingSettings(){
    tab = dijit.byId('dataTab');
    if(tab!=null)dijit.byId('tabContainer').removeChild(tab);
}
/*
function analyzeSettings(){
	analyze_fillAnalyzePane(); // analyze.js
	var settings = dijit.byId('tabSettings');
	//settings.onFocus = analyzeSettingsInit();
	settings.setHref("analyze/jsp/taskanalyze_settings.html");
	var tabC = dijit.byId("tabContainer");
  	//opl_debug("select");
  	tabC.selectChild(settings);
  	opl_checkForErrors();
}
*/

function opl_loadAnalyzeForm(){
	var settings = dijit.byId('tabSettings');
	//settings.onFocus = analyzeSettingsInit();
	settings.setHref("analyze/jsp/analyzeForm.html");
	var tabC = dijit.byId("tabContainer");
 	//opl_debug("select");
  	tabC.selectChild(settings);
	opl_checkForErrors();
}

function onLogTreeAction(message){
	dijit.byId('tabContainer').selectChild('tabSettings');
	dijit.byId('tabSettings').setHref("logger/datamodel.html");
	//dijit.byId('tabSettings').onLoad = buildLogTaskView;
	logging_fillLogTaskPane(); // logging.js
}

dojo.addOnLoad(init);
