var analyze_latestTaskInfos;
var analyze_chosenTask;
var analyze_nbrGraphs;
var analyze_graphId1;
var analyze_graphId2;

function analyze_registerGraphCB(graphId, exception){
	if(exception){
		opl_analyzeError(exception);
		return;
	}
	analyze_graphId1 = graphId;
	analyze_graphId2 = -1;
	var href = "analyze/svgHolder.jsp?id="+graphId +"&size="+analyze_nbrGraphs;
	var graphTitle = 'Chart';
	analyze_loadNewTab(href, graphTitle, graphId, -1);
}

function analyze_registerGraphCB1(graphId, exception){
	if(exception){
		opl_analyzeError(exception);
		return;
	}
	analyze_graphId1 = graphId;
	analyze_checkGraphsLoaded();	
}
function analyze_registerGraphCB2(graphId, exception){
	if(exception){
		opl_analyzeError(exception);
		return;
	}
	analyze_graphId2 = graphId;
	analyze_checkGraphsLoaded();  
}

function analyze_checkGraphsLoaded(){
	if(analyze_graphId2 > -1 && analyze_graphId1 > -1){
		var href = "analyze/svgSplitHolder.jsp?id="+analyze_graphId1 +"&id2="+analyze_graphId2 +"&size="+analyze_nbrGraphs;
		var graphTitle = 'Chart';
		analyze_loadNewTab(href, graphTitle, analyze_graphId1, analyze_graphId2);
		analyze_graphId1 = 0;
		analyze_graphId2 = -1;
	}
}

function analyze_loadNewTab(href, graphTitle, graphId1, graphId2){
	var tabCon = dijit.byId('tabContainer');
	var children = tabCon.getChildren();
	var newDataTab = new dijit.layout.ContentPane({id:'dataTab'+graphId1,title:graphTitle,closable:true, onClose:analyze_testClose, special1: graphId1, special2:graphId2 });
	newDataTab.setHref(href);
	tabCon.addChild(newDataTab);
	tabCon.selectChild(newDataTab);
}