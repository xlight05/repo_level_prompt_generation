function numericChart_getTag(){
	var tag = new Object();
	tag.tagType = "Graphviewer_NumericChartShell";
	tag.createNode = numericChart_createNode;
	tag.appendTag = numericChart_appendTag;
	tag.setInfo = numericChart_setInfo;
	tag.appendBatch = numericChart_appendBatch;
	tag.updateDynamicData = numericChart_updateDynamicData;
	tag.updateValue = analog_updateValue;
	tag.updateTime = numericChart_updateTime;
	tag.zoomRedraw = numericChart_zoomRedraw;
	tag.getSubChartPosition = numericChart_getSubChartPosition;
	tag.followMouse = numericChart_followMouse;
	return tag;
}

function numericChart_createNode(){
	var node = SVG_DOC.getElementById("numericChart_");
	if(!node){
		alert("No numeric chart template!");
		return null;
	}
	var newNode = node.cloneNode(true); //true=clone child nodes also
	return newNode;
}

function numericChart_appendTag(tag, y){
	tag.yCoord = y;
	tag.setAttribute("transform", "translate(0,0)");
	document.getElementById("graphviewer_body").appendChild(tag);
	tag.setAttribute("visibility", "visible");
	return y;
}

function numericChart_followMouse(graphId, xCoord, yCoord){
	var tag = document.getElementById("numericChart_"+graphId);
	tag.setAttribute("transform", "translate("+xCoord+","+yCoord +")");
}

function numericChart_setInfo(graphInfo, idNumber){
	if (GRAPH_INFO.graphProperties){
		for (var i in GRAPH_INFO.graphProperties){
			if (GRAPH_INFO.graphProperties[i].varName == graphInfo.chartName){
				if (SVG_DOC.getElementById("numericChart_"+idNumber)){
					SVG_DOC.getElementById("numericChart_title_"+idNumber).textContent = GRAPH_INFO.graphProperties[i].title;					
					var yOffset = GRAPH_INFO.graphProperties[i].rowNumber*20;
					SVG_DOC.getElementById("numericChart_title_"+idNumber).setAttribute('y', yOffset);
					SVG_DOC.getElementById("chart_currentValue_"+idNumber).setAttribute('y', yOffset);
				}
			}
		}
	}
}

function numericChart_appendBatch(idNumber, dataPoints){
	var graph = PROCESS_DATA[idNumber];
	var oldIndex = LOADING_DATA.nbrLoaded[idNumber];
	var isNewLimit = analog_loadData(graph, dataPoints, idNumber);
}

function numericChart_updateDynamicData(idNumber, graph){	
}
function numericChart_updateTime(idNumber, timeString){
}
function numericChart_zoomRedraw(graph, idNumber, indexFrom, indexTo){
}
function numericChart_getSubChartPosition(y){
}