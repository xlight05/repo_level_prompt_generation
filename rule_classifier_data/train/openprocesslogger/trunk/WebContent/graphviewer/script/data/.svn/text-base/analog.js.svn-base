/***
 * 
 * To use this interface the shell needs to contain the following tags:
 * 
 * chart_currentMaxValue_
 * chart_currentMinValue_
 * chart_currentValue_
 * 
 * and the PROCESS_DATA[i].data[j].value must be of analog type.
 * 
 * 
 */

function analog_findData(pxvalue, i){
	var startIndex;
	var endIndex;
	if (CURRENT_ZOOMLEVEL > 0){
		startIndex = ZOOMDATA_ARRAY[i][CURRENT_ZOOMLEVEL-1].startIndex;
		endIndex = ZOOMDATA_ARRAY[i][CURRENT_ZOOMLEVEL-1].endIndex;
	}else{
		startIndex = 0;
		endIndex = PROCESS_DATA[i].data.length-1;
	}
	var ts = FROM_MILLIS + pxvalue*TIME_UNIT;
	var index = timeline_findClosestIndex(ts, i, startIndex, endIndex);
	if(!PROCESS_DATA[i].data[index]){
		//top.opl_debug("ill" +index);
		return -1
	}
	else {
		return PROCESS_DATA[i].data[index].value;
	}	
}

function analog_findTimestamp(pxvalue, i){
	var startIndex;
	var endIndex;
	if (CURRENT_ZOOMLEVEL > 0){
		startIndex = ZOOMDATA_ARRAY[i][CURRENT_ZOOMLEVEL-1].startIndex;
		endIndex = ZOOMDATA_ARRAY[i][CURRENT_ZOOMLEVEL-1].endIndex;
	}else{
		startIndex = 0;
		endIndex = PROCESS_DATA[i].data.length-1;
	}
	var ts = FROM_MILLIS + pxvalue*TIME_UNIT;
	var index = timeline_findClosestIndex(ts, i, startIndex, endIndex);
	if(!PROCESS_DATA[i].data[index]){
		//console.debug("ill" +index);
		return -1
	}
	else {
		return PROCESS_DATA[i].data[index].timestamp;
	}	
}

function analog_calculateMaxMin(graph, indexFrom, indexTo){
	graph.min = 1.7976931348623157E308;
	graph.max = -graph.min;
	for(var i=indexFrom; i<=indexTo; i++){	
		if (graph.data[i].value > graph.max){
			graph.max = graph.data[i].value;
		}
		if (graph.data[i].value < graph.min){
			graph.min = graph.data[i].value;
		}
	}
}


/***
 * 
 * Svg visibility functions
 * 
 */

function analog_updateValue(idNumber, x){
	var id = 'chart_currentValue_'+idNumber;
	var currentValue = SVG_DOC.getElementById(id);
	var yVal = analog_findData(x, idNumber);
	if(!yVal){
		currentValue.textContent = '-';
	}else{        
		currentValue.textContent = Math.round(yVal*10000)/10000;
	}	        
}

function analog_getValue(idNumber, x){
	var yVal = analog_findData(x, idNumber);
	return yVal;
}

function analog_getTimestamp(idNumber, x){
	var yVal = analog_findTimestamp(x, idNumber);
	return yVal;
}

function analog_updateDynamicData(idNumber, graph){
	SVG_DOC.getElementById("chart_currentMaxValue_"+idNumber).textContent = Math.round(graph.max*1000)/1000;
    SVG_DOC.getElementById("chart_currentMinValue_"+idNumber).textContent = Math.round(graph.min*1000)/1000;	
}

/***
 * 
 * Data loading functions
 * 
 */

function analog_loadData(graph, dataPoints, idNumber){
	var isNewLimit = false;
	var indexx = 0 + LOADING_DATA.nbrLoaded[idNumber];
	var temp;
	
	var ts = dataPoints[0].timestamp;
	if (indexx>0 && dataPoints.length > 0){ // Avoid double timestamps
		while (indexx > 0 && graph.data[indexx-1].timestamp == ts){
			indexx--;
		}
		top.opl_debug("Backed "+(LOADING_DATA.nbrLoaded[idNumber]-indexx));
	}
	
	for(var i in dataPoints){	
		graph.data[indexx] = dataPoints[i];
		if (graph.data[indexx].value > graph.max){
			graph.max = graph.data[indexx].value;
			isNewLimit = true;
		}
		temp = graph.data[indexx].value;
		if (graph.data[indexx].value < graph.min){
			graph.min = graph.data[indexx].value;
			isNewLimit = true;
		}
		indexx++;
	}
	LOADING_DATA.nbrLoaded[idNumber] = indexx;
	top.opl_debug(indexx +" loaded");
	return isNewLimit;
}
