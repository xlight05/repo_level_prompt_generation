var timeAnalyze_graphInfo1;
var timeAnalyze_graphInfo2;
var timeAnalyze_eventIndex1;

function timeAnalyze_createGraph(){				
	var sd = dijit.byId("timeAnalyze_dateStart").getValue();
	var st = dijit.byId("timeAnalyze_timeStart").getValue();
	var ed = dijit.byId("timeAnalyze_dateEnd").getValue();
	var et = dijit.byId("timeAnalyze_timeEnd").getValue();
	
	var startTime = timeutil_getTimeMillis(sd,st);
	var stopTime = timeutil_getTimeMillis(ed,et);
	
	var analyzeBean1 = {
		"javaClass" : "se.openprocesslogger.tpclogger.AnalyzeBean",
		"startTime" : startTime,
		"stopTime" : stopTime,
		"right" : true
	};
	var analyzeBean2 = {
		"javaClass" : "se.openprocesslogger.tpclogger.AnalyzeBean",
		"startTime" : startTime,
		"stopTime" : stopTime,
		"right" : false
	};
	
	try{
		var graphId1 = jsonrpc.analyzer.registerGraphInfo(analyzeBean1);
		var graphId2 = jsonrpc.analyzer.registerGraphInfo(analyzeBean2);
		timeAnalyze_registerGraphCB(graphId1, graphId2, null);
	}catch(exception){
		opl_analyzeError(exception);
	}	
	//jsonrpc.analyzer.registerGraphInfo(analyzeBean, timeAnalyze_registerGraphCB);
}

function timeAnalyze_registerGraphCB(graphId1, graphId2, exception){
	if(exception){
		opl_analyzeError(exception);
		return;
	}
	var graphTitle = 'Chart';
	timeAnalyze_loadNewTab("analyze/svgSplitHolder.jsp?id="+graphId1+"&id2="+graphId2+"&singleData=true", graphTitle);
}

function timeAnalyze_registerGraphCB2(graphId, dataId, exception){
	if(exception){
		opl_analyzeError(exception);
		return;
	}
	var graphTitle = 'Chart';
	timeAnalyze_loadNewTab("analyze/tpcAnalyze.jsp?id="+graphId+"&dataId="+dataId, graphTitle);
}

function timeAnalyze_loadNewTab(href, graphTitle){
  	var tabCon = dijit.byId('tabContainer');
  	console.debug(tabCon);
  	var children = tabCon.getChildren();
  	console.debug(children);
  	var newDataTab = new dijit.layout.ContentPane({title:graphTitle,closable:true });
  	console.debug(newDataTab);
  	newDataTab.setHref(href);
  	console.debug("hr " +href);
  	tabCon.addChild(newDataTab);
  	console.debug("add");
  	tabCon.selectChild(newDataTab);
  	console.debug("sel");
}

function timeAnalyze_analyzeSingleData(dataId){
	var analyzeBean = {
		"javaClass" : "se.openprocesslogger.tpclogger.SingleData",
		"dataId" : dataId
	};
	try{
		var graphId = jsonrpc.analyzer.registerGraphInfo(analyzeBean);
		timeAnalyze_registerGraphCB2(graphId, dataId, null);
	}catch(exception){
		opl_analyzeError(exception);
	}
}

function tpclogger_setIsOk(dataId, parameterId){
	var pulseInfo = jsonrpc.analyzer.setIsOk(dataId, parameterId);
	if(pulseInfo){
		tpclogger_refresh(pulseInfo, parameterId);
	}
}

function tpclogger_refresh(pulseInfo, parameterId){
	var tdName = document.getElementById("tpc_name_"+pulseInfo.dataId+"_"+parameterId);
	var tdLimit = document.getElementById("tpc_lim_"+pulseInfo.dataId+"_"+parameterId);
	var tdBtn = document.getElementById("tpc_btn_"+pulseInfo.dataId+"_"+parameterId);
	for(var i=0; i<pulseInfo.valueNames.length; i++){
		var val = pulseInfo.valueNames[i];
		if(val.id == parameterId){
			tdLimit.innerHTML = tpclogger_getLimitText(val);
		}
	}
	tdName.setAttribute("style", "background-color:PaleGreen;");
	tdBtn.setAttribute("style","display:none");
}
function tpclogger_refreshAll(pulseInfo){
	for(var i=0; i<pulseInfo.valueNames.length; i++){
		var val = pulseInfo.valueNames[i];
		var tdName = document.getElementById("tpc_name_"+pulseInfo.dataId+"_"+val.id);
		var tdLimit = document.getElementById("tpc_lim_"+pulseInfo.dataId+"_"+val.id);
		var tdBtn = document.getElementById("tpc_btn_"+pulseInfo.dataId+"_"+val.id);
		tdLimit.innerHTML = tpclogger_getLimitText(val);
		tdName.setAttribute("style", "background-color:PaleGreen;");
		tdBtn.setAttribute("style","display:none");
	}	
}


function tpclogger_getLimitText(val){
	var limitText= "";
	if(val.minEnabled){
		limitText = "["+(Math.round(1000*val.minValue)/1000)+",";
	}else{
		limitText = "[disabled,";
	}
	if(val.maxEnabled){
		limitText = limitText+(Math.round(1000*val.maxValue)/1000)+"]";
	}else{
		limitText = limitText+"disabled]";
	}
	
	return limitText;
}

function tpc_getButtonRow(){
	var btn = document.createElement("button");
	btn.innerHTML = "Is ok";
	var dojoBtn = new dijit.form.Button({},btn);
	dojo.connect(btn,"onclick",function(){alert("hello");});
	document.getElementById("tpc_containerDiv").appendChild(btn);
	
}
function tpc_loadAnalyze(){
	tpc_getButtonRow();
	tpc_getButtonRow();
	tpc_getButtonRow();
}

function tpclogger_useAsReference(dataId){
	var pulseInfo = jsonrpc.analyzer.setReferencePulse(dataId, 0.25);
	if(pulseInfo){
		tpclogger_refreshAll(pulseInfo);
	}
}


/*
function tpclogger_fillInfoTable(dataId){
	var pulseInfo = jsonrpc.analyzer.getPulseInfo(dataId);
	
	for(var valName in pulseInfo.valueNames){
	    //var taskName = logging_activeLogTasks[i].name;
	    //var started = logging_activeLogTasks[i].started;
	    var tRow = document.createElement("tr");
	    //tRow.setAttribute("onclick", "logging_loadActiveTaskInfo("+i+")");
	    
	    var tCell1 = document.createElement("td");
	    tCell1.innerHTML = val.name;
	   	var isOk = tpclogger_isOk(val);
	   	if(isOk) tCell1.setAttribute("style","background-color: PaleGreen");
	   	else tCell1.setAttribute("style","background-color: red");
	   	
	   	var tCell2 = document.createElement("td");
	   	var analogVal = "";
	   	if(val.otherValue){
	   		analogVal = "["+(Math.round(1000*val.value)/1000)+","+(Math.round(1000*val.otherValue)/1000)+"]";
	   	}else{
	   		analogVal = (Math.round(1000*val.value)/1000);
	   	}
	   	tCell2.innerHTML = analogVal;
	   	
	   	var tCell3 = document.createElement("td");
	   	var limitText= "";
	   	if(val.isMaxEnabled) limitText = "["+(Math.round(1000*val.maxValue)/1000)+",";
	   	else limitText = "[disabled,";
	   	if(val.isMinEnabled) limitText =limitText+(Math.round(1000*val.minValue)/1000)+"]";
	   	else limitText = "disabled]";
	   	tCell3.innerHTML = analogVal;
	   	
	    var tCell4 = document.createElement("td");
	   	if(!isOk){
	   		var btn = new dijit.form.Button({onclick:"tpclogger_setIsOk("+dataId+",'"+val.id+"')"},tCell4);
	   		
	   	}
	   	else limitText = "[disabled,";
	   	if(val.isMinEnabled) limitText =limitText+(Math.round(1000*val.minValue)/1000)+"]";
	   	else limitText = "disabled]";
	   	tCell3.innerHTML = analogVal;
	   	
	    
	    tRow.appendChild(tCell1);
	    tRow.appendChild(tCell2);
	    taskTable.appendChild(tRow);
	    if(logging_chosenTaskProxy && (taskName == logging_chosenTaskProxy.name)){
	    	chosenIndex = i;
	    	logging_chosenTemplateIndex = -1;
	    }
	}		
}

function tpclogger_isOk(val){
	var isOk = true;
	isOk = isOk && (val.isMaxEnabled && val.value > val.maxValue);
	isOk = isOk && (val.isMinEnabled && val.value < val.minValue);
	isOk = isOk && (val.otherValue && val.isMaxEnabled && val.otherValue > val.maxValue);
	isOk = isOk && (val.otherValue && val.isMinEnabled && val.otherValue < val.minValue);
	return isOk;
}
*/