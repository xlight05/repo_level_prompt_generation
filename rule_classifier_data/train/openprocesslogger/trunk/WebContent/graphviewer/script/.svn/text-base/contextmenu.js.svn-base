function contextmenu_show(evt){
	var x = evt.clientX + this.scrollX;
	var y = evt.clientY + this.scrollY;
	
	// Display context menu
	
	if(GRAPH_INFO.event1 && GRAPH_INFO.event1.dataId > 0){
		SVG_DOC.getElementById('contextmenu_event1').setAttribute('visibility','visible');
		SVG_DOC.getElementById('contextmenu_event1').textContent = GRAPH_INFO.event1.contextlabel;
	}
	
	SVG_DOC.getElementById('contextmenu_bg').setAttribute('x',x);
	SVG_DOC.getElementById('contextmenu_bg').setAttribute('y',y);
	SVG_DOC.getElementById('contextmenu_expcsv').setAttribute('x',x+13);
	SVG_DOC.getElementById('contextmenu_expcsv').setAttribute('y',y+20);
	SVG_DOC.getElementById('contextmenu_event1').setAttribute('x',x+13);
	SVG_DOC.getElementById('contextmenu_event1').setAttribute('y',y+40);
	
	SVG_DOC.getElementById('contextmenu').setAttribute('visibility','visible');
	return false; // If this returns true, the other context menu will not be shown!
}

function contextmenu_hide(){
	SVG_DOC.getElementById('contextmenu_event1').setAttribute('visibility','hidden');
	SVG_DOC.getElementById('contextmenu').setAttribute('visibility','hidden');
}

function contextmenu_exportData(){
	var q = this;
	var tabCon = null;
	while(!tabCon){
		if(q.dijit){
			tabCon = q.dijit.byId('tabContainer');
		}else{
			q = q.parent;	
		}
	}
	
	var strRequestParameters = "";
	var arrStrCharts = "";
	var intFrom = 0;
	var intTo = 0;
	var intDataId = 0;

	for(var i=0 ; i<SVG_DOC.arrChartData.length ; i++)
	{
		var regexp = new RegExp("\/","g");
		arrStrCharts += SVG_DOC.arrChartData[i].chartName.replace(regexp, "|")+"$";
	}
	arrStrCharts = arrStrCharts.substring(0, arrStrCharts.length-1);		
	
	if(GRAPH_INFO.singledata != null && GRAPH_INFO.singledata.dataId != null && GRAPH_INFO.singledata.dataId && 'undefined' && GRAPH_INFO.singledata.dataId != -1){	
		intDataId = GRAPH_INFO.singledata.dataId;
		arrStrCharts=GRAPH_INFO.singledata.varName;
	}
	
	intFrom = Math.floor(FROM_MILLIS);
	intTo = Math.ceil(TO_MILLIS);
  	strRequestParameters = "?varNames="+arrStrCharts+"&from="+intFrom+"&to="+intTo+"&dataId="+intDataId;
	window.open(top.opl_getUrl()+"/CSVExportServlet"+strRequestParameters, "_blank");
}

function contextmenu_event1(){
	if(GRAPH_INFO.event1 && GRAPH_INFO.event1.dataId > 0 && GRAPH_INFO.event1.timestamp > 0){
		var q = parent; // graphviewer.jsp or graphviwer_v2.jsp
		var r = q.parent; // graphcontainer.jsp or timeanalyze_2.jsp
		var s = r.parent; // timeanalyze_1 or root page
		GRAPH_INFO.event1.contextfunction(GRAPH_INFO.event1.dataId, GRAPH_INFO.event1.varName, GRAPH_INFO.event1.timestamp);
	}
	contextmenu_hide();
}