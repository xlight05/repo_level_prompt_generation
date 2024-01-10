function digitalGraphChart_getTag(){
	var tag = new Object();
	tag.tagType = "se.openprocesslogger.svg.taglib.DigitalChartShell";
	tag.createNode = digitalGraphChart_createNode;
	tag.appendTag = digitalGraphChart_appendTag;
	tag.setInfo = tagfactory_setInfo;
	tag.appendBatch = digitalGraphChart_appendBatch;
	tag.updateDynamicData = digitalGraphChart_updateDynamicData;
	tag.updateValue = digitalGraphChart_updateValue;
	tag.updateTime = timeline_updateTime;
	tag.zoomRedraw = digitalGraphChart_zoomRedraw;
	tag.getSubChartPosition = digitalGraphChart_getSubChartPosition;
	return tag;
}

function digitalGraphChart_appendTag(tag, y){
	var y2 = y+ SVG_STYLE.digitalChart.MARGIN_TOP + SVG_STYLE.digitalChart.VALUE_HEIGHT;
	top.opl_debug(y2);
	tag.setAttribute("transform", "translate("+SVG_STYLE.digitalChart.MARGIN_LEFT+","+y2+")");
	tag.setAttribute("visibility", "visible");
	document.getElementById("graphviewer_body").appendChild(tag);	
	return y + SVG_STYLE.digitalChart.TOTAL_HEIGHT;
}

function digitalGraphChart_getSubChartPosition(y){
	var pos = new Object();
	pos.y = y + SVG_STYLE.digitalChart.MARGIN_TOP + SVG_STYLE.digitalChart.VALUE_HEIGHT;
	pos.x = (+SVG_STYLE.digitalChart.MARGIN_LEFT);
	return pos;
}

function digitalGraphChart_setInfo(graphInfo, idNumber){
	SVG_DOC.getElementById("chart_title_"+idNumber).textContent = graphInfo.chartName;
}

function digitalGraphChart_createNode(){
	var node = SVG_DOC.getElementById("digitalChart_");
	if(!node){
		alert("No digital chart template!");
		return null;
	}
	var newNode = node.cloneNode(true); //true=clone child nodes also
	return newNode;
}

function digitalGraphChart_appendBatch(idNumber, dataPoints){
	var graph = PROCESS_DATA[idNumber];
	var oldIndex = LOADING_DATA.nbrLoaded[idNumber];
	top.opl_debug("o"+oldIndex);
	graph.max = 1;
	graph.min = 0;
	digitalGraphChart_fillData(graph, dataPoints, idNumber);
	digitalGraphChart_redraw(graph, idNumber, oldIndex);
	digitalGraphChart_updateDynamicData(idNumber, graph);
}

function digitalGraphChart_updateDynamicData(idNumber, graph){
	SVG_DOC.getElementById("chart_currentMaxValue_"+idNumber).textContent = "On";
    SVG_DOC.getElementById("chart_currentMinValue_"+idNumber).textContent = "Off";
}

function digitalGraphChart_zoomRedraw(graph, idNumber, indexFrom, indexTo){
	var tStart = new Date().getTime();
	var polyline = document.getElementById("chart_polyline_"+idNumber);
	polyline.points.clear();
	PROCESS_DATA[idNumber].polylinePoints = "";
	if (indexFrom <0 || indexTo < 0 || indexTo <= indexFrom){
		top.opl_debug("Outside range");
		return;		
	}
	var dt = graph.data[indexFrom].timestamp - FROM_MILLIS;
	top.opl_debug("dt: "+dt +" tu: "+TIME_UNIT);
	var pxFrom = dt/TIME_UNIT;
	dt = graph.data[indexTo].timestamp - FROM_MILLIS;
	var pxTo = dt/TIME_UNIT;
	top.opl_debug("dt: "+dt +" tu: "+TIME_UNIT);
	top.opl_debug("Drawing from: "+timeutil_formatTs(graph.data[indexFrom].timestamp) +" to " +timeutil_formatTs(graph.data[indexTo].timestamp));
	top.opl_debug("Index from "+indexFrom +" to " +indexTo +" from pixel " +pxFrom +" to " +pxTo);
	
	var valUnit = SVG_STYLE.digitalChart.VALUE_HEIGHT;
	var i = indexFrom;
	var x;
	var y;
	var oldX = 0;
	var oldY = -1;
	var skip = 0;
	while (i <= indexTo){
		x = (graph.data[i].timestamp - FROM_MILLIS)/TIME_UNIT;
	    y = graph.data[i].value*valUnit;
	   	if ((Math.round(x) == Math.round(oldX)) && (Math.round(y) == Math.round(oldY))){
	    	skip++;
	    }else{
	    	//var point = polyline.ownerDocument.documentElement.createSVGPoint();
	    	if (oldY >= 0){ // Not first. To make square appearance; draw same y with new x
	    		/*
	    		point.x = x;
		    	point.y = -oldY;
		    	polyline.points.appendItem(point);
		    	*/
	    		PROCESS_DATA[idNumber].polylinePoints += ""+x+","+(-oldY)+" ";
	    	}
	    	oldX = x;
	    	oldY = y;
	    	/*
	    	point = polyline.ownerDocument.documentElement.createSVGPoint();
		    point.x = x;
		    point.y = -y; 
		    polyline.points.appendItem(point);
		    */
	    	PROCESS_DATA[idNumber].polylinePoints += ""+x+","+(-y)+" ";
	    }	    
	    i++;
	}
	tagfactory_setPoints("chart_polyline_"+idNumber, idNumber);
	var tEnd = new Date().getTime();
	top.opl_debug("Zoom redraw in "+(tEnd-tStart) +" ms");	
}

function digitalGraphChart_redraw(graph, idNumber, oldIndex){
	var tStart = new Date().getTime();
	var valUnit = SVG_STYLE.digitalChart.VALUE_HEIGHT;
	var polyline = document.getElementById("chart_polyline_"+idNumber);
	var i = oldIndex;
	var x;
	var y;
	var oldX = 0;
	var oldY = -1;
	if (i > 0)
		oldX = (graph.data[i-1].timestamp - FROM_MILLIS)/TIME_UNIT;
	var skip = 0;
	while (i < LOADING_DATA.nbrLoaded[idNumber]){
		x = (graph.data[i].timestamp - FROM_MILLIS)/TIME_UNIT;
	    y = graph.data[i].value*valUnit;
	   	if ((Math.round(x) == Math.round(oldX)) && (Math.round(y) == Math.round(oldY))){
	    	skip++;
	    }else{
	    	//var point = polyline.ownerDocument.documentElement.createSVGPoint();
	    	if (oldY >= 0){ // Not first. To make square appearance; draw same y with new x
	    		/*
	    		point.x = x;
		    	point.y = -oldY;
		    	polyline.points.appendItem(point);
		    	*/
	    		PROCESS_DATA[idNumber].polylinePoints += ""+x+","+(-oldY)+" "; 
	    	}
	    	oldX = x;
	    	oldY = y;
	    	/*
	    	point = polyline.ownerDocument.documentElement.createSVGPoint();
		    point.x = x;
		    point.y = -y; 
		    polyline.points.appendItem(point);
		    */
	    	PROCESS_DATA[idNumber].polylinePoints += ""+x+","+(-oldY)+" ";
	    }	    
	    i++;
	}
	top.opl_debug("Skipped " +skip +" pts");
	tagfactory_setPoints("chart_polyline_"+idNumber, idNumber);
	var tEnd = new Date().getTime();
	top.opl_debug("Redraw in "+(tEnd-tStart) +" ms");	
}

function digitalGraphChart_fillData(graph, dataPoints, idNumber){
	var tStart = new Date().getTime();
	var indexx = 0 + LOADING_DATA.nbrLoaded[idNumber];
	
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
	var tEnd = new Date().getTime();
	top.opl_debug("Filled data in "+(tEnd-tStart) +" ms");
}

function digitalGraphChart_updateValue(idNumber, x){
	var id = 'chart_currentValue_'+idNumber;
	var currentValue = SVG_DOC.getElementById(id);
	var yVal = analog_findData(x, idNumber);
	if(yVal == -1){
		currentValue.textContent = '-';
	}else if(yVal == 0){        
		currentValue.textContent = "Off";
	}else{
		currentValue.textContent = "On";
	}
}

