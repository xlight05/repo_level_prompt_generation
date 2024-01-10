/*
 * Tag interface
 * Each tag is a struct with properties and a function interface. 
 * 
 	tag.tagType
	tag.createNode
	tag.appendTag
	tag.setInfo
	tag.appendBatch
	tag.updateDynamicData
	tag.updateValue
	tag.updateTime
	tag.zoomRedraw
	
	optional:
	tag.followMouse(graphId, x,y);
*/

var DATA_EVENT = 1;

function tagfactory_initTags(){
	var tags = [];
	tags[0] = analogGraphChart_getTag();
	tags[1] = digitalGraphChart_getTag();
	tags[2] = barChart_getTag();
	tags[3] = eventChart_getTag();
	tags[4] = analogStepGraphChart_getTag();
	tags[5] = numericChart_getTag();
	return tags;
}

/***
 * 
 * Function interface
 */
function tagfactory_createNode(){}
function tagfactory_appendTag(tag, y){}

function tagfactory_setInfo(graphInfo, idNumber){
	SVG_DOC.getElementById("chart_title_"+idNumber).textContent = graphInfo.chartName;
	SVG_DOC.getElementById("chart_startTime_"+idNumber).textContent = timeutil_formatTs(FROM_MILLIS);
    SVG_DOC.getElementById("chart_endTime_"+idNumber).textContent = timeutil_formatTs(TO_MILLIS);
}

function tagfactory_appendBatch(idNumber, dataPoints){}
function tagfactory_updateDynamicData(i, chart){} // chart = PROCESS_DATA[i]
function tagfactory_updateValue(){}
function tagfactory_updateTime(idNumber, timeString){}
function tagfactory_zoomRedraw(graph, idNumber, indexFrom, indexTo){}

function tagfactory_getTagIndex(tagType){
	for(var i in TAGS){
		if (TAGS[i].tagType == tagType)
			return i;
	}
	alert("Unknown tag: " +tagType);
}

function tagfactory_setNodeIds(newNode, idNumber)
{
	var oldId = newNode.id;
	if(oldId){
		newNode.setAttribute("id", oldId+idNumber);	
	}
	if (!newNode.childNodes || newNode.childNodes.length == 0){
		return;
	}
	
	for( var i in newNode.childNodes){
		tagfactory_setNodeIds(newNode.childNodes[i], idNumber);
	}
}

function tagfactory_setPoints(pid, idNumber){
	var polyline = document.getElementById(pid);
	polyline.setAttribute("points", PROCESS_DATA[idNumber].polylinePoints);
}


