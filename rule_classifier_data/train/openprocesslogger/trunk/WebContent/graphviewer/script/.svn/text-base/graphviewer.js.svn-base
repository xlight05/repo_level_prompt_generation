var SVG_DOC;
var GRAPHVIEWER_LOADING_DONE = false;
var TAGS;
var CLICK_TIMESTAMP = -1;
var GRAPHVIEWER_EVENT1;
var GRAPH_INFO;
/***
 * quick list for creating an easy customized representation of data:
 * 1. Create a "shell" in the svg page you want to use:
 * 		* In a g-tag with id: chartnameChart_
 * 		* Id all tags. Later, the shell children will be traversed and all will get unique id's depending on their positions.
 * 2. Create a js file in graphviewer/script/tags called chartnameChart.js.
 * 		* Create a function called chartnameChart_getTag()
 * 3. In the function tagfactory_initTags, add your chartnameChart_getTag-function to the array.  
 * 4. In the function chartnameChart_getTag(), return an object where all the tag interface functions are defined
 * 	 (listed in the comment to tagfactory_initTags). Check an existing implementation to see what they do.
 * 	Many functions might be re-used from other graphs.
 */
function graphviewer_onload(evt){
	SVG_DOC = evt.target.ownerDocument;
	TAGS = tagfactory_initTags();
	//SVG_DOC.rootElement.setAttribute("width",window.innerWidth);
	//SVG_DOC.rootElement.setAttribute("height", window.innerHeight);
	SVG_DOC.oncontextmenu = graphviewer_oncontextmenu;
	GRAPH_INFO = parent.input_graphviwer_graphInfo;
	SVG_DOC.onmousemove = graphviewer_onmousemove;
	SVG_DOC.onclick = graphviewer_onclick;
	dataloading_initJSON();
	dataloading_loadGraphPage(parent.input_graphviwer_graphInfo.graphInfo);
	if (GRAPH_INFO.subChartIndex)
		subBarChart_linkNodes(GRAPH_INFO.subChartIndex);
	//SVG_DOC.documentElement.setAttribute("height", window.innerHeight);
	//SVG_DOC.documentElement.setAttribute("width", window.innerWidth);
}

function graphviewer_onmousemove(evt){
	if (GRAPHVIEWER_LOADING_DONE){
		eventhandler_onMouseMove(evt);
	}
}

function graphviewer_onclick(evt){
	if(evt.which == 1){
		if (GRAPHVIEWER_LOADING_DONE){
			if(evt.timeStamp-CLICK_TIMESTAMP < 500 && (evt.timeStamp > 0) && (CLICK_TIMESTAMP > 0)){
	    		return;
			}
			CLICK_TIMESTAMP = evt.timeStamp;
			
			eventhandler_onclick(evt);
		}
	}
}

function graphviewer_oncontextmenu(evt){
	if (GRAPHVIEWER_LOADING_DONE){
		eventhandler_contextmenu(evt);
	}
	return false;
}

function graphviewer_displayShells(evt){
	document.getElementById("analogChart_").setAttribute("transform","translate(140,240)");
	document.getElementById("analogChart_").setAttribute("visibility","visible");
	document.getElementById("digitalChart_").setAttribute("transform","translate(160,260)");
	document.getElementById("barChart_").setAttribute("transform","translate(180,280)");
	document.getElementById("digitalChart_").setAttribute("visibility","visible");
	document.getElementById("barChart_").setAttribute("visibility","visible");
}
