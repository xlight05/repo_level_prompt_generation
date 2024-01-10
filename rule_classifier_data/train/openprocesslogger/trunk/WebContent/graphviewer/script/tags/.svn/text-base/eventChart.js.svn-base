
function eventChart_getTag(){
	var tag = new Object();
	tag.tagType = "se.openprocesslogger.svg.taglib.EventChartShell";
	tag.createNode = eventChart_createNode;
	tag.appendTag = eventChart_appendTag;
	tag.setInfo = tagfactory_setInfo;
	tag.appendBatch = eventChart_appendBatch;
	tag.updateDynamicData = eventChart_updateDynamicAnalogData;
	tag.updateValue = analog_updateValue;
	tag.updateTime = timeline_updateTime;
	tag.zoomRedraw = eventChart_zoomRedraw;
	tag.dataInfo = DATA_EVENT;
	tag.getValue = analog_getValue;
	tag.getTimestamp = analog_getTimestamp;
	return tag;
}

function eventChart_appendTag(tag, y){
	var y2 = y+ SVG_STYLE.eventChart.MARGIN_TOP + SVG_STYLE.eventChart.HEIGHT;
	top.opl_debug("y2: " +y2);
	tag.setAttribute("transform", "translate("+SVG_STYLE.eventChart.MARGIN_LEFT+","+y2+")");
	document.getElementById("graphviewer_body").appendChild(tag);
	tag.setAttribute("visibility", "visible");
	return y + SVG_STYLE.eventChart.TOTAL_HEIGHT;
}

function eventChart_createNode(){
	var node = SVG_DOC.getElementById("eventChart_");
	if(!node){
		alert("No event chart template!");
		return;
	}
	var newNode = node.cloneNode(true); //true=clone child nodes also
	return newNode;
}

function eventChart_appendBatch(idNumber, dataPoints){
	var graph = PROCESS_DATA[idNumber];
	var oldIndex = LOADING_DATA.nbrLoaded[idNumber];
	eventChart_fillData(graph, dataPoints, idNumber);
	eventChart_redraw(graph, idNumber, oldIndex);
}

function eventChart_redraw(graph, idNumber, oldIndex){
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
	    		var rect = eventChart_getRect(oldX, x-oldX, i%colorArray.length);
	    		rects.appendChild(rect);
	    	}
	    	oldX = x;
	    }
	    i++;
	}
	var rect = eventChart_getRect(oldX, x-oldX, i%colorArray.length);
	rects.appendChild(rect);
	top.opl_debug("eventchart skip: " +skip);
}

function eventChart_getRect(xCoord, rectWidth, rectColorIndex){
	var rect = document.createElementNS("http://www.w3.org/2000/svg", "rect");
	rect.setAttribute("height", SVG_STYLE.eventChart.HEIGHT);
	rect.setAttribute("y", 0);
	//rect.setAttribute("y", SVG_STYLE.eventChart.HEIGHT);
	rect.setAttribute("x", xCoord);
	rect.setAttribute("width", SVG_STYLE.eventChart.EVENT_WIDTH);
	rect.setAttribute("style", "fill:"+colorArray[rectColorIndex]);
	return rect;
}

function eventChart_fillData(graph, dataPoints, idNumber){
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

function eventChart_updateValue(idNumber, x){
	var id = 'chart_currentValue_'+idNumber;
	var currentValue = SVG_DOC.getElementById(id);
	var yVal = analog_findData(x, idNumber);
	if(yVal == -1){
		currentValue.textContent = '-';
	}else{
		currentValue.textContent = Math.round(yVal*10000)/10000;
	}
}

function eventChart_zoomRedraw(graph, idNumber, indexFrom, indexTo){
	top.opl_debug("First point: "+graph.data[indexFrom].timestamp +" last point " +graph.data[indexTo].timestamp);
	var pxFrom = (graph.data[indexFrom].timestamp - FROM_MILLIS)/TIME_UNIT;
	var pxTo = (graph.data[indexTo].timestamp - FROM_MILLIS)/TIME_UNIT;
	top.opl_debug("Eventchart Zooming from "+indexFrom +" to " +indexTo +" from pixel " +pxFrom +" to " +pxTo);
	var parentNode = document.getElementById("chart_rects_"+idNumber);
	while(parentNode.hasChildNodes()){
        parentNode.removeChild(parentNode.firstChild);
	}
	eventChart_draw(graph, idNumber, indexFrom, indexTo);
}

function eventChart_draw(graph, idNumber, fromIndex, toIndex){
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
	    		var rect = eventChart_getRect(oldX, x-oldX, i%colorArray.length);
	    		rects.appendChild(rect);
	    	}
	    	oldX = x;
	    }
	    i++;
	}
	var rect = eventChart_getRect(oldX, x-oldX, i%colorArray.length);
	rects.appendChild(rect);
	top.opl_debug("eventchart skip: " +skip);
}

function eventChart_updateDynamicAnalogData(idNumber, graph){
	
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