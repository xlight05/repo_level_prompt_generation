var SVG_STYLE;

function graphbuilder_buildChartShell(graphMetadataResponse){
	var y = 0;
	SVG_DOC.arrChartData = graphMetadataResponse.charts;
	for (var i in graphMetadataResponse.charts){
		if(GRAPH_INFO.subChartIndex 
			&& graphMetadataResponse.charts[i].varName == graphMetadataResponse.charts[GRAPH_INFO.subChartIndex].varName
			&& GRAPH_INFO.subChartIndex != i){
			graphMetadataResponse.charts[i].tagType = analogStepGraphChart_getTag().tagType;
		}
		var tagIndex = tagfactory_getTagIndex(graphMetadataResponse.charts[i].tagType);
		graphMetadataResponse.charts[i].tagIndex = tagIndex;
		graphMetadataResponse.charts[i].y = y;
		top.opl_debug("Tag: "+graphMetadataResponse.charts[i].tagType +" i "+tagIndex +" id "+i);
		tag = TAGS[tagIndex].createNode();
		tagfactory_setNodeIds(tag, i);
		top.opl_debug("At: " +y);
		y = TAGS[tagIndex].appendTag(tag, y);
		TAGS[tagIndex].setInfo(graphMetadataResponse.charts[i], i);
	}
	mouseMarker_init(y);
	SVG_DOC.rootElement.setAttribute("height", (y+20)+"px");
	SVG_DOC.rootElement.setAttribute("width", SVG_STYLE.graphWidth+"px");
	
	graphbuilder_adjustLoadingMessage(SVG_STYLE.graphWidth,y+20);
}

function graphbuilder_adjustLoadingMessage(wd,ht){
	SVG_DOC.getElementById("loadingmessage").setAttribute("transform", "translate("+(wd/2-100)+",50)");
	SVG_DOC.getElementById("loadingmessage_rect").setAttribute("height", ht+"px");
	SVG_DOC.getElementById("loadingmessage_rect").setAttribute("width", wd+"px");
	/*
	SVG_DOC.getElementById("loadingmessage_bg").setAttribute("x", (wd/2-80)+"px");
	SVG_DOC.getElementById("loadingmessage_bg").setAttribute("y", (ht/2-20)+"px");
	SVG_DOC.getElementById("loadingmessage_txt").setAttribute("x", (wd/2)+"px");
	SVG_DOC.getElementById("loadingmessage_txt").setAttribute("y", (ht/2)+"px");
	*/
	var node = SVG_DOC.getElementById("loadingmessage_rect");
	var pNode = node.parentNode;
	pNode.removeChild(node);
	SVG_DOC.rootElement.appendChild(node);	
	
	node = SVG_DOC.getElementById("loadingmessage");
	pNode = node.parentNode;
	pNode.removeChild(node);
	SVG_DOC.rootElement.appendChild(node);	
	
}