function eventhandler_onMouseMove(evt){
	var x = evt.clientX + this.scrollX;
	var y = evt.clientY + this.scrollY;
	if(x < SVG_STYLE.MARGIN_LEFT){
		mouseMarker_hideMarker();
		return;
	}
	else if(x > (SVG_STYLE.MARGIN_LEFT + SVG_STYLE.EVENT_WIDTH)){
		mouseMarker_hideMarker();
		return;
	}
	mouseMarker_showMarker(x);
	eventhandler_updateInfo(x-SVG_STYLE.MARGIN_LEFT, y); // x relative to event-viewer start
}

function eventhandler_onclick(evt){
	var x = evt.clientX + this.scrollX;
	if(x < SVG_STYLE.MARGIN_LEFT){
		return;
	}
	else if(x > (SVG_STYLE.MARGIN_LEFT + SVG_STYLE.EVENT_WIDTH)){
		return;
	}
	if(SVG_DOC.getElementById('contextmenu').getAttribute('visibility') != 'visible')
		zoomevents_onclick(x); // x relative to event-viewer start
	contextmenu_hide();
}

function eventhandler_contextmenu(evt){
	if(!zoomevents_oncontextmenu(evt)){
		var closest = -1;
		var x = evt.clientX + this.scrollX - SVG_STYLE.MARGIN_LEFT;
		if((x >= SVG_STYLE.MARGIN_LEFT) && (x <= (SVG_STYLE.MARGIN_LEFT + SVG_STYLE.EVENT_WIDTH))) {
			var time = timeline_getTime(x, FROM_MILLIS, TIME_UNIT, true);
			if(GRAPH_METADATA != null){
				for(var i in GRAPH_METADATA.charts){
					var graph = GRAPH_METADATA.charts[i];
					//if (GRAPH_INFO.event1 && GRAPH_INFO.event1.graphIndex == i && TAGS[graph.tagIndex].getValue && TAGS[graph.tagIndex].getTimestamp){
					if(GRAPH_INFO.event1 && TAGS[graph.tagIndex].tagType == "se.openprocesslogger.svg.taglib.EventChartShell" && TAGS[graph.tagIndex].getValue && TAGS[graph.tagIndex].getTimestamp){
						var dist = (FROM_MILLIS + x*TIME_UNIT)-TAGS[graph.tagIndex].getTimestamp(i,x);//timeline_findClosestIndex(FROM_MILLIS);
						if(closest == -1 || (closest > dist && dist > 0)){
							GRAPH_INFO.event1.dataId = TAGS[graph.tagIndex].getValue(i, x);
							GRAPH_INFO.event1.varName = graph.varName;
							GRAPH_INFO.event1.timestamp = TAGS[graph.tagIndex].getTimestamp(i, x);
							GRAPH_INFO.event1.contextlabel = "Analyze pulse, id: " +  GRAPH_INFO.event1.dataId;
							closest = dist;
						}
					}
				}
			}
		}
		contextmenu_show(evt);	
	}
}

function eventhandler_hidevalues(){
	/*
	for(var i=0; i<NBR_GRAPHS; i++){
		SVG_DOC.getElementById('currentTime'+i).textContent = 'xx:xx:xx';
		SVG_DOC.getElementById('currentValue'+i).textContent = '-';
	}
	*/
}

function eventhandler_updateInfo(x, y){
	var time = timeline_getTime(x, FROM_MILLIS, TIME_UNIT, true);
	if(GRAPH_METADATA != null){
		for(var i in GRAPH_METADATA.charts){
			var graph = GRAPH_METADATA.charts[i];
			TAGS[graph.tagIndex].updateValue(i, x);
			TAGS[graph.tagIndex].updateTime(i, time);
			if (TAGS[graph.tagIndex].followMouse){
				TAGS[graph.tagIndex].followMouse(i, x, y);
			}
		}
	}	
}

