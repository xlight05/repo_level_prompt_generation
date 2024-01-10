function barChart_getTag(){
	var tag = new Object();
	tag.tagType = "se.openprocesslogger.svg.taglib.BarChartShell";
	tag.createNode = barChart_createNode;
	tag.appendTag = barChart_appendTag;
	tag.setInfo = tagfactory_setInfo;
	tag.appendBatch = barChart_appendBatch;
	tag.updateValue = analog_updateValue;
	tag.updateTime = timeline_updateTime;
	tag.zoomRedraw = barChart_zoomRedraw;
	tag.getValue = analog_getValue;
	tag.updateDynamicData = eventChart_updateDynamicAnalogData;
	return tag;
}

function barChart_appendTag(tag, y){
	tag.yCoord = y;
	var y2 = y + SVG_STYLE.barChart.MARGIN_TOP + SVG_STYLE.barChart.HEIGHT;
	top.opl_debug("y2: " +y2);
	tag.setAttribute("transform", "translate("+SVG_STYLE.barChart.MARGIN_LEFT+","+y2+")");
	document.getElementById("graphviewer_body").appendChild(tag);
	tag.setAttribute("visibility", "visible");
	return y + SVG_STYLE.barChart.TOTAL_HEIGHT;
}

function barChart_createNode(){
	var node = SVG_DOC.getElementById("barChart_");
	if(!node){
		alert("No analog chart template!");
		return null;
	}
	var newNode = node.cloneNode(true); //true=clone child nodes also
	return newNode;
}

function barChart_appendBatch(idNumber, dataPoints){
	var graph = PROCESS_DATA[idNumber];
	var oldIndex = LOADING_DATA.nbrLoaded[idNumber];
	barChart_fillData(graph, dataPoints, idNumber);
	barChart_redraw(graph, idNumber, oldIndex);
}

function barChart_redraw(graph, idNumber, oldIndex){
	var rects = document.getElementById("chart_rects_"+idNumber);
	var i = oldIndex;
	var x;
	var oldX = -1;
	if (i > 0)
		oldX = (graph.data[i-1].timestamp - FROM_MILLIS)/TIME_UNIT;
	var skip = 0;
	
	while (i < LOADING_DATA.nbrLoaded[idNumber]){
		x = (graph.data[i].timestamp - FROM_MILLIS)/TIME_UNIT;
	    if ((Math.round(x) == Math.round(oldX))){
	    	skip++;
	    }else{
	    	if (oldX > 0){
	    		var rect = barChart_getRect(oldX, x-oldX, graph.data[i].value%colorArray.length);
	    		rects.appendChild(rect);
	    	}
	    	oldX = x;
	    }
	    i++;
	}
	var rect = barChart_getRect(oldX, x-oldX, graph.data[i-1].value%colorArray.length);
	rects.appendChild(rect);
	top.opl_debug("barchart skip: " +skip);
}

function barChart_getRect(xCoord, rectWidth, rectColorIndex){
	var rect = document.createElementNS("http://www.w3.org/2000/svg", "rect");
	rect.setAttribute("height", SVG_STYLE.barChart.HEIGHT);
	rect.setAttribute("y", 0);
	//rect.setAttribute("y", SVG_STYLE.barChart.HEIGHT);
	rect.setAttribute("x", xCoord);
	rect.setAttribute("width", rectWidth);
	rect.setAttribute("style", "fill:"+colorArray[rectColorIndex]);
	return rect;
}


function barChart_zoomRedraw(graph, idNumber, indexFrom, indexTo){
	var parentNode = document.getElementById("chart_rects_"+idNumber);
	while(parentNode.hasChildNodes()){
        parentNode.removeChild(parentNode.firstChild);
	}
	barChart_draw(graph, idNumber, indexFrom, indexTo);
}

function barChart_draw(graph, idNumber, fromIndex, toIndex){
	var rects = document.getElementById("chart_rects_"+idNumber);
	
	var i = fromIndex;
	var x;
	var oldX = -1;
	var skip = 0;
	
	while (i <= toIndex){
		x = (graph.data[i].timestamp - FROM_MILLIS)/TIME_UNIT;
	    if ((Math.round(x) == Math.round(oldX))){
	    	skip++;
	    }else{
	    	if (oldX > 0){
	    		var rect = barChart_getRect(oldX, x-oldX, graph.data[i].value%colorArray.length);
	    		rects.appendChild(rect);
	    	}
	    	oldX = x;
	    }
	    i++;
	}
	if(i >= 0 && i < graph.data.length){
		var rect = barChart_getRect(oldX, x-oldX, graph.data[i].value%colorArray.length);
		rects.appendChild(rect);
	}
}


function barChart_fillData(graph, dataPoints, idNumber){
	var indexx = 0 + LOADING_DATA.nbrLoaded[idNumber];
	top.opl_debug("st: " +indexx);
	
	var ts = dataPoints[0].timestamp;
	if (indexx>0 && dataPoints.length > 0){ // Avoid double timestamps
		while (indexx > 0 && graph.data[indexx-1].timestamp == ts){
			indexx--;
		}
		top.opl_debug("Backed "+(LOADING_DATA.nbrLoaded[idNumber]-indexx));
	}
	
	for(var i in dataPoints){	
		graph.data[indexx] = dataPoints[i];
		indexx++;
	}
	LOADING_DATA.nbrLoaded[idNumber] = indexx;
	top.opl_debug("end: " +indexx);
}

var colorArray = [
			"#F6B7B4",
			"#F6E2B4",
			"#F5F6B4",
			"#C0F6B4",
			"#B4F6ED",
			"#B4BAF6",
			"#F6B4F5",
			"#FF0600",
			"#FFB800",
			"#FCFF00",
			"#44FF00",
			"#00FFFC",
			"#0019FF",
			"#FF00F0",
			"#9E150B",
			"#9E750B",
			"#989E0B",
			"#0E9E0B",
			"#0B9E98",
			"#0B0B9E",
			"#9E0B95"];