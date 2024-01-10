/***
 * 
 * Dynamic data
 * 
 */

function analogGraphChart_zoomRedraw(graph, idNumber, indexFrom, indexTo){
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
	
	analog_calculateMaxMin(graph, indexFrom, indexTo);
	var valUnit;
	if (graph.max != graph.min)
		valUnit = SVG_STYLE.analogChart.VALUE_HEIGHT/(graph.max - graph.min);
	else
		valUnit = SVG_STYLE.analogChart.VALUE_HEIGHT/0.4;
		
	var i = indexFrom;
	var x;
	var y;
	var oldX = 0;
	var oldY = -1;
	var skip = 0;
	while (i <= indexTo){
		x = (graph.data[i].timestamp - FROM_MILLIS)/TIME_UNIT;
	    if(graph.data[i].javaClass == "com.ipbyran.opl.web.svg.data.FineDataPoint"){
	    	x += (graph.data[i].micros/1000)/TIME_UNIT;
	    }
	    y = graph.data[i].value*valUnit;
	    if ((Math.round(x) == Math.round(oldX)) && (Math.round(y) == Math.round(oldY))){
	    	skip++;
	    }else{
	    	oldX = x;
	    	oldY = y;
	    	/*var point = polyline.ownerDocument.documentElement.createSVGPoint(); 
		    point.x = x;
		   	if (y != 0)
		    	point.y = -y;
		    else // value is 0
		    	point.y = 0.0;
		    
		    polyline.points.appendItem(point);
		    */
	    	PROCESS_DATA[idNumber].polylinePoints += ""+x+","+(-y)+" ";
	    }	    
	    i++;
	}
	top.opl_debug(skip);
	tagfactory_setPoints("chart_polyline_"+idNumber, idNumber);
	analogGraphChart_translate(idNumber, valUnit);
	top.opl_debug("Zoom redraw in "+((new Date()).getTime()-tStart) +" ms");
}

/***
 * 
 * Initialization
 * 
 */
function analogGraphChart_getTag(){
	var tag = new Object();
	tag.tagType = "se.openprocesslogger.svg.taglib.AnalogChartShell";
	tag.createNode = analogGraphChart_createNode;
	tag.appendTag = analogGraphChart_appendTag;
	tag.setInfo = tagfactory_setInfo;
	tag.appendBatch = analogGraphChart_appendBatch;
	tag.updateDynamicData = analog_updateDynamicData;
	tag.updateValue = analog_updateValue;
	tag.updateTime = timeline_updateTime;
	tag.zoomRedraw = analogGraphChart_zoomRedraw;
	tag.getSubChartPosition = analogGraphChart_getSubChartPosition;
	return tag;
}

function analogGraphChart_appendTag(tag, y){
	tag.yCoord = y;
	var y2 = y + SVG_STYLE.analogChart.MARGIN_TOP + SVG_STYLE.analogChart.VALUE_HEIGHT;
	top.opl_debug("y2: " +y2);
	tag.setAttribute("transform", "translate("+SVG_STYLE.analogChart.MARGIN_LEFT+","+y2+")");
	document.getElementById("graphviewer_body").appendChild(tag);
	tag.setAttribute("visibility", "visible");
	return y + SVG_STYLE.analogChart.TOTAL_HEIGHT;
}

function analogGraphChart_getSubChartPosition(y){
	var pos = new Object();
	pos.y = y +SVG_STYLE.analogChart.MARGIN_TOP + SVG_STYLE.analogChart.VALUE_HEIGHT;
	pos.x = (+SVG_STYLE.analogChart.MARGIN_LEFT);
	return pos;
}

function analogGraphChart_createNode(){
	var node = SVG_DOC.getElementById("analogChart_");
	if(!node){
		alert("No analog chart template!");
		return null;
	}
	var newNode = node.cloneNode(true); //true=clone child nodes also
	return newNode;
}

/***
 * 
 * Data loading
 * 
 */
function analogGraphChart_appendBatch(idNumber, dataPoints){
	var graph = PROCESS_DATA[idNumber];
	var oldIndex = LOADING_DATA.nbrLoaded[idNumber];
	var isNewLimit = analog_loadData(graph, dataPoints, idNumber);
	analogGraphChart_redraw(graph, idNumber, isNewLimit, oldIndex);
	analog_updateDynamicData(idNumber, graph);
}

function analogGraphChart_redraw(graph, idNumber, isNewLimit, oldIndex){
	var tStart = new Date().getTime();
	var valUnit;
	if (graph.max != graph.min)
		valUnit = SVG_STYLE.analogChart.VALUE_HEIGHT/(graph.max - graph.min);
	else
		valUnit = SVG_STYLE.analogChart.VALUE_HEIGHT/0.4;
		
	top.opl_debug("chart_polyline_"+idNumber);
	var polyline = document.getElementById("chart_polyline_"+idNumber);
	var i = oldIndex;
	if(isNewLimit) // Redraw everything
	{	
		polyline.points.clear();
		PROCESS_DATA[idNumber].polylinePoints = "";
		i = 0;
	}
	var x = +0;
	var y = +0;
	var oldX = 0;
	var oldY = -1;
	var skip = 0;
	while (i < LOADING_DATA.nbrLoaded[idNumber]){
		var dx = graph.data[i].timestamp - FROM_MILLIS;
		x = dx/TIME_UNIT;
	    if(graph.data[i].javaClass == "com.ipbyran.opl.web.svg.data.FineDataPoint"){
	    	dx = graph.data[i].micros/1000;
	    	x += dx/TIME_UNIT;
	    }
	    y = graph.data[i].value*valUnit;
	    if ((Math.round(x) == Math.round(oldX)) && (Math.round(y) == Math.round(oldY))){
	    	skip++;
	    }else{
	    	oldX = x;
	    	oldY = y;
	    	/*var point = polyline.ownerDocument.documentElement.createSVGPoint(); 
		    point.x = x;
		    try{
		    	point.y = -y;
		    }catch(exception){
		    	top.opl_debug("CAUGHT IT: ."+y+".");
		    }
		    
		    polyline.points.appendItem(point);
		    */
	    	PROCESS_DATA[idNumber].polylinePoints += ""+x+","+(-y)+" ";
	    }	    
	    i++;
	}
	top.opl_debug(skip);
	tagfactory_setPoints("chart_polyline_"+idNumber, idNumber);
	analogGraphChart_translate(idNumber, valUnit);
	top.opl_debug("Redraw in "+((new Date()).getTime()-tStart) +" ms");
}

function analogGraphChart_translate(idNumber, valUnit){
	var graph = PROCESS_DATA[idNumber];
	var dy = graph.min*valUnit;
	var transform = "translate(0," +dy +")";
	document.getElementById("chart_polyline_"+idNumber).setAttribute("transform", transform);
}
