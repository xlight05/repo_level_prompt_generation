var svgNS = "http://www.w3.org/2000/svg";
var xlinkNS = "http://www.w3.org/1999/xlink";
    	
function subBarChart_linkNodes(idNumber){
	var grLinkedRects = document.createElementNS(svgNS, "g");
	grLinkedRects.setAttribute("id", "subBarChart_rects_"+idNumber);
	var scaleY = SVG_STYLE.SUB_CHART_HEIGHT/SVG_STYLE.barChart.HEIGHT;
	var translateY = SVG_STYLE.barChart.HEIGHT/2 - SVG_STYLE.SUB_CHART_HEIGHT/2;
	var grLinkedTxt = document.createElementNS(svgNS, "g");
	grLinkedTxt.setAttribute("id", "subBarChart_text_"+idNumber);
	if(GRAPH_METADATA != null){
		for(var i in GRAPH_METADATA.charts){
			var graph = GRAPH_METADATA.charts[i];
			if (TAGS[graph.tagIndex].getSubChartPosition){
				var pos = TAGS[graph.tagIndex].getSubChartPosition(graph.y);
				var linkNode1 = subBarChart_createUseElement("chart_rects_"+idNumber,pos.x, pos.y ); 
				var linkNode2 = subBarChart_createUseElement("chart_currentValue_"+idNumber,pos.x, pos.y );
				linkNode2.setAttribute("transform", "translate(0,"+(pos.y)+")");
				linkNode1.setAttribute("transform", "translate(0,"+(pos.y+translateY)+") scale(1,"+scaleY+")");
				grLinkedRects.appendChild(linkNode1);
				grLinkedTxt.appendChild(linkNode2);
			}
		}
	}
		
	document.getElementById("graphviewer_body").appendChild(grLinkedRects);
	document.getElementById("graphviewer_body").appendChild(grLinkedTxt);
}

function subBarChart_createUseElement(linkId, x, y) {
	newUseEl = document.createElementNS(svgNS,"use");
	newUseEl.setAttributeNS(null,"x", x);
	newUseEl.setAttributeNS(null,"y", 0);	
	newUseEl.setAttributeNS(xlinkNS,"href","#"+linkId);
	return newUseEl;
}
