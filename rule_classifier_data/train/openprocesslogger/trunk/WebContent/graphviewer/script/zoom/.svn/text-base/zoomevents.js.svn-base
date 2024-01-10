CURRENT_ZOOMLEVEL = 0;
ZOOMDATA_ARRAY = [];
ZOOMDATA_METADATA = [];
MAX_ZOOMLEVEL = 10;

FROMX = -1;
function zoomevents_init(graphMetadataResponse){
	for (var i in graphMetadataResponse.charts){
		ZOOMDATA_ARRAY[i] = new Object();
	}
}

function zoomevents_oncontextmenu(evt){
	if (FROMX > 0){
		FROMX = -1;
		zoomevents_hideMarker();
		return true;
	}
	return false;
}

function zoomevents_onclick(x){
	
	if (FROMX < 0){
		FROMX = x;
		zoomevents_showMarker(x);
		return;
	}
	if ( Math.abs(x-FROMX) < 2){
		return;
	}
	SVG_DOC.getElementById("loadingmessage").setAttribute('visibility','visible');
	SVG_DOC.getElementById("loadingmessage_rect").setAttribute('visibility','visible');
	SVG_DOC.getElementById("loadingmessage_txt1").textContent = 'Zooming...';
	SVG_DOC.getElementById("loadingmessage_txt2").textContent = '';
	SVG_DOC.getElementById("loadingmessage_txt3").textContent = '';
	if (CURRENT_ZOOMLEVEL == MAX_ZOOMLEVEL-1){
		CURRENT_ZOOMLEVEL--;
	}
	if (FROMX > x){
		zoomevents_createZoomData(x-SVG_STYLE.MARGIN_LEFT, FROMX-SVG_STYLE.MARGIN_LEFT);
	}else{
		zoomevents_createZoomData(FROMX-SVG_STYLE.MARGIN_LEFT, x-SVG_STYLE.MARGIN_LEFT);
	}
	FROMX = -1;
	zoomevent_zoomSquareVisibility();
	SVG_DOC.getElementById("loadingmessage").setAttribute('visibility','hidden');
	SVG_DOC.getElementById("loadingmessage_rect").setAttribute('visibility','hidden');
}

function zoomevents_cancel(){
	FROMX = -1;
	zoomevents_hideMarker();
}

function zoomevents_showMarker(x){
	document.getElementById("xLeftMarker").setAttribute("x", x);
	document.getElementById("xLeftMarker").setAttribute("visibility", "visible");
	
	document.getElementById("xOpacityMarker").setAttribute("x", x);
	document.getElementById("xOpacityMarker").setAttribute("visibility", "visible");
	document.getElementById("xOpacityMarker").setAttribute("width", 0);
}
function zoomevents_hideMarker(){
	document.getElementById("xLeftMarker").setAttribute("visibility", "hidden");	
	document.getElementById("xOpacityMarker").setAttribute("visibility", "hidden");
}

function zoomevents_setZoomLevel(level){
	SVG_DOC.getElementById("loadingmessage").setAttribute('visibility','visible');
	SVG_DOC.getElementById("loadingmessage_rect").setAttribute('visibility','visible');
	SVG_DOC.getElementById("loadingmessage_txt1").textContent = 'Setting zoom level...';
	SVG_DOC.getElementById("loadingmessage_txt2").textContent = 'Redrawing graph';
	SVG_DOC.getElementById("loadingmessage_txt3").textContent = '';
	
	if (level == 0){
		FROM_MILLIS = GRAPH_METADATA.fromMillis;
		TO_MILLIS = GRAPH_METADATA.toMillis;
	}else{
		FROM_MILLIS = ZOOMDATA_METADATA[level-1].fromMillis;
		TO_MILLIS = ZOOMDATA_METADATA[level-1].toMillis; 
	}
	TIME_UNIT = (TO_MILLIS-FROM_MILLIS)/(SVG_STYLE.EVENT_WIDTH-1);
	CURRENT_ZOOMLEVEL = level;
	for(var i in GRAPH_METADATA.charts){
		SVG_DOC.getElementById("loadingmessage_txt3").textContent = 'Graph '+i +' of ' +GRAPH_METADATA.charts.length;
		var res = zoomevent_getInitialIndexes(i);
		TAGS[GRAPH_METADATA.charts[i].tagIndex].zoomRedraw(PROCESS_DATA[i],i, res.startIndex, res.endIndex);
		TAGS[GRAPH_METADATA.charts[i].tagIndex].setInfo(GRAPH_METADATA.charts[i], i);
		TAGS[GRAPH_METADATA.charts[i].tagIndex].updateDynamicData(i, PROCESS_DATA[i]);
	}
	zoomevent_zoomSquareVisibility();
	SVG_DOC.getElementById("loadingmessage").setAttribute('visibility','hidden');
	SVG_DOC.getElementById("loadingmessage_rect").setAttribute('visibility','hidden');
}

function zoomevents_createZoomData(pxfrom, pxto){
	var tStart = new Date().getTime();
	SVG_DOC.getElementById("loadingmessage_txt2").textContent = 'Calculating zoom data';
	SVG_DOC.getElementById("loadingmessage_txt3").textContent = '';
	zoomevents_calculateZoomData(pxfrom, pxto);
	zoomevents_setTimeProperties(CURRENT_ZOOMLEVEL+1);
	SVG_DOC.getElementById("loadingmessage_txt2").textContent = 'Redrawing graph';
	SVG_DOC.getElementById("loadingmessage_txt3").textContent = '';
	zoomevents_drawZoomData(pxfrom, pxto);
	CURRENT_ZOOMLEVEL += 1;
	zoomevents_hideMarker();
	var tEnd = new Date().getTime();
	top.opl_debug("Create Zoom data in "+(tEnd-tStart) +" ms");
}

function zoomevents_setTimeProperties(level){
	if (level == 0){
		FROM_MILLIS = GRAPH_METADATA.fromMillis;
		TO_MILLIS = GRAPH_METADATA.toMillis;
	}else{
		FROM_MILLIS = ZOOMDATA_METADATA[level-1].fromMillis;
		TO_MILLIS = ZOOMDATA_METADATA[level-1].toMillis;
	}	
	TIME_UNIT = (TO_MILLIS-FROM_MILLIS)/(SVG_STYLE.EVENT_WIDTH-1);
	top.opl_debug("New time properties: " +(TO_MILLIS-FROM_MILLIS) +" width " +SVG_STYLE.EVENT_WIDTH);
	top.opl_debug("Time unit: " +TIME_UNIT +" last point at: " +((TO_MILLIS-FROM_MILLIS)/TIME_UNIT) +" width " +SVG_STYLE.EVENT_WIDTH);
}

function zoomevents_drawZoomData(pxfrom, pxto){
	for(var i in GRAPH_METADATA.charts){
		SVG_DOC.getElementById("loadingmessage_txt3").textContent = 'Graph '+i +' of ' +GRAPH_METADATA.charts.length;
		TAGS[GRAPH_METADATA.charts[i].tagIndex].zoomRedraw(PROCESS_DATA[i],i, ZOOMDATA_ARRAY[i][CURRENT_ZOOMLEVEL].startIndex, ZOOMDATA_ARRAY[i][CURRENT_ZOOMLEVEL].endIndex);
		TAGS[GRAPH_METADATA.charts[i].tagIndex].setInfo(GRAPH_METADATA.charts[i], i);
		TAGS[GRAPH_METADATA.charts[i].tagIndex].updateDynamicData(i, PROCESS_DATA[i]);
	}	
}

function zoomevents_calculateZoomData(pxfrom, pxto){
	var newFrom = FROM_MILLIS + pxfrom*TIME_UNIT;
	var newTo =  FROM_MILLIS + pxto*TIME_UNIT;
	top.opl_debug("Zooming from "+timeutil_formatTs(newFrom) +" to "+timeutil_formatTs(newTo));
	ZOOMDATA_METADATA[CURRENT_ZOOMLEVEL] = new Object();
	ZOOMDATA_METADATA[CURRENT_ZOOMLEVEL].fromMillis = newFrom;
	ZOOMDATA_METADATA[CURRENT_ZOOMLEVEL].toMillis = newTo;
	for(var i in GRAPH_METADATA.charts){
		SVG_DOC.getElementById("loadingmessage_txt3").textContent = 'Graph '+i +' of ' +GRAPH_METADATA.charts.length;
		var last = zoomevent_getInitialIndexes(i);
		top.opl_debug("Between index "+last.startIndex +" and " +last.endIndex);
		ZOOMDATA_ARRAY[i][CURRENT_ZOOMLEVEL] = new Object();
		var intStartIndex = timeline_findClosestIndexAbove(pxfrom, i, last.startIndex, last.endIndex);
		var intEndIndex = timeline_findClosestIndexBelow(pxto, i, last.startIndex, last.endIndex);
		if (intStartIndex < 0 && intEndIndex < 0){
			intStartIndex = 0;
			intEndIndex = 0;
		}
		else if (intStartIndex < 0){
			intStartIndex = intEndIndex;
		}
		else if (intEndIndex < 0){
			intEndIndex = intStartIndex;
		}
		
		ZOOMDATA_ARRAY[i][CURRENT_ZOOMLEVEL].startIndex =intStartIndex;  
		ZOOMDATA_ARRAY[i][CURRENT_ZOOMLEVEL].endIndex = intEndIndex;
	}
}

function zoomevent_getInitialIndexes(i){
	var res = new Object();
	if (CURRENT_ZOOMLEVEL == 0){
		res.startIndex = 0;
		res.endIndex = PROCESS_DATA[i].data.length-1;
	}else{
		res.startIndex = ZOOMDATA_ARRAY[i][CURRENT_ZOOMLEVEL-1].startIndex;
		res.endIndex = ZOOMDATA_ARRAY[i][CURRENT_ZOOMLEVEL-1].endIndex;
	}
	return res;
}

function zoomevent_zoomSquareVisibility(){
	
	// Make new levels visibl
	if(CURRENT_ZOOMLEVEL < MAX_ZOOMLEVEL && CURRENT_ZOOMLEVEL>0){
		parent.graphviewer_parent_setSquareVisible(CURRENT_ZOOMLEVEL-1);
		parent.graphviewer_parent_setSquareVisible(CURRENT_ZOOMLEVEL);
	}
  	
	// Up to CURRENT_ZOOMLEVEL-1 set clickable
	var temp = 0;
	while (temp < CURRENT_ZOOMLEVEL){
		parent.graphviewer_parent_setSquareClickable(temp);
		temp++;
	}
  	
	// Set the rest existing (even though they might not be visible yet)
	top.opl_debug(temp);
	while (temp < MAX_ZOOMLEVEL){
		parent.graphviewer_parent_setSquareExisting(temp);
		temp++;	
	}
}

function zoomevent_viewOverview(level){
	if(document.getElementById("graphviewer_body") != null){
		var scale = 1;
		switch(level){
		case 2:
			scale = 0.5;
			break;
		case 3:
			scale = 0.2;
			break;
		}
		document.getElementById("graphviewer_body").setAttribute("transform", "scale(1," + scale + ")");
	}
}
