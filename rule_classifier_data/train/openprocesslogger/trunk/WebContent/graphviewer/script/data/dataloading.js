/***
 * PROCESS_DATA [i]
 * 		|- data [j]			All data points
 * 			|- value
 * 			|- timestamp
 * 		|- min				Min value
 * 		|- max
 */
var PROCESS_DATA; /* All data points */

/***
 * LOADING_DATA
 * 		|- nbrLoaded[] 		Number datas loaded on each graph
 * 		|- varIndex			Chart index currently loading
 * 		|- totalDataCount
 * 		|- currentDataCount
 * 		|- totalBatchCount
 * 		|- currentBatchCount
 */
var LOADING_DATA; // Metadata used during loading
var FROM_MILLIS;
var TO_MILLIS;
var DATE_MILLIS;
var TIME_UNIT;	/* ms/pixel */
var BATCH_SIZE = 3000;
/***
 * se.openprocesslogger.proxy.graphviewer.GraphMetadataResponse
 *  
 * GRAPH_METADATA
 * 		|- style
 * 		|- fromMillis
 * 		|- toMillis
 * 		|- charts [i]
 * 			|- varName
 * 			|- chartName
 *			|- dataCount
 * 			|- tagType
 * 			|- tagIndex 		Initialized by graphbuilder_buildChartShell
 * 		|- logTaskId
 * 			
 */
var GRAPH_METADATA;

function dataloading_initJSON(){
	jsonrpc = new JSONRpcClient(top.opl_getUrl()+"/JSON-RPC");
	
}

/***
 * Initializes the graph loading by requesting shell-content from the server
 */
function dataloading_loadGraphPage(graphInfo){
	SVG_DOC.getElementById("loadingmessage").setAttribute('visibility','visible');
	top.opl_debug("Loading shell..");
	var res = jsonrpc.graphviewer.getGraphViewerShell(graphInfo);
	top.opl_debug("Shell loaded");	
	dataloading_cb(res, null);	
}

function dataloading_cb(result, exception){
	if(exception){
		alert(exception);
		return;
	}
	SVG_STYLE = result.style;
	if (result.charts.length == 0){
		GRAPHVIEWER_LOADING_DONE = true; // defined in graphviewer.js
		top.opl_debug("Loading done");
		return;
	}
	dataloading_createDataArrays(result);
	dataloading_setTimeParameters(result);
	graphbuilder_buildChartShell(result);	
	LOADING_DATA.varIndex = 0; /* What data is currently loading */
	dataloading_requestBatch();
}

function dataloading_createDataArrays(graphMetadataResponse){
	PROCESS_DATA = [graphMetadataResponse.charts.length];
	LOADING_DATA = new Object();
	LOADING_DATA.nbrLoaded = [];
	LOADING_DATA.totalDataCount = 0;
	LOADING_DATA.currentDataCount = 0;
	GRAPH_METADATA = graphMetadataResponse;
	for (var i in GRAPH_METADATA.charts){
		PROCESS_DATA[i] = new Object();
		PROCESS_DATA[i].data = [];
		PROCESS_DATA[i].min = 1.7976931348623157E308;
		PROCESS_DATA[i].max = -PROCESS_DATA[i].min;
		LOADING_DATA.nbrLoaded[i] = 0; /* Current index used during batch loading of data */
		PROCESS_DATA[i].polylinePoints = "";
		PROCESS_DATA[i].lastIndex = 0;
		LOADING_DATA.totalDataCount += GRAPH_METADATA.charts[i].dataCount;
	} 
	LOADING_DATA.totalBatchCount = Math.ceil(LOADING_DATA.totalDataCount /BATCH_SIZE);
	LOADING_DATA.currentBatchCount = 0;
	SVG_DOC.getElementById("loadingmessage_txt1").textContent = 'Loading.. 0 % done';
	SVG_DOC.getElementById("loadingmessage_txt2").textContent = 'Initialization...';
	LOADING_DATA.startTime = new Date().getTime();
	setTimeout('dataloading_updateLoadingTimer()',1000);
	zoomevents_init(GRAPH_METADATA);
}

function dataloading_updateLoadingTimer(){
	var te = (new Date().getTime()-LOADING_DATA.startTime)/1000;
	var p = (LOADING_DATA.currentDataCount/LOADING_DATA.totalDataCount)*100;
	var left = (te/p)*200;
	SVG_DOC.getElementById("loadingmessage_txt3").textContent = 'Estimated remaining time: '+Math.floor(left-te);
	if(!GRAPHVIEWER_LOADING_DONE){
		setTimeout('dataloading_updateLoadingTimer()',1000);
	}else{
		top.opl_debug("Total loading time: " +te +" s");
	}
}

function dataloading_setTimeParameters(graphMetadataResponse){
	FROM_MILLIS = graphMetadataResponse.fromMillis;
	TO_MILLIS = graphMetadataResponse.toMillis;
	TIME_UNIT = (TO_MILLIS-FROM_MILLIS)/(SVG_STYLE.EVENT_WIDTH-1);
	top.opl_debug("Viewing from "+timeutil_formatTs(FROM_MILLIS) +" to "+timeutil_formatTs(TO_MILLIS));
}

function dataloading_requestBatch(){
	var chart = GRAPH_METADATA.charts[LOADING_DATA.varIndex]; 
	var size = chart.dataCount - LOADING_DATA.nbrLoaded[LOADING_DATA.varIndex];
	top.opl_debug("Request batch for var: "+(LOADING_DATA.varIndex+1) + " of " +GRAPH_METADATA.charts.length);
	top.opl_debug("Loaded: "+LOADING_DATA.nbrLoaded[LOADING_DATA.varIndex] +" of " +size);
	var tsFrom = dataloading_getFrom(LOADING_DATA.varIndex);
	size = BATCH_SIZE;
	var batchInfo = {
      "javaClass" : "se.openprocesslogger.proxy.graphviewer.BatchInfo",
      "varName" : chart.varName,
      "from" : tsFrom,
      "to" : GRAPH_INFO.graphInfo.to,
      "batchSize" : size,
      "logTaskId" : GRAPH_METADATA.logTaskId
    };
    
    //jsonrpc.graphviewer.getBatch(dataloading_newBatch, batchInfo);
    var tagIndex = GRAPH_METADATA.charts[LOADING_DATA.varIndex].tagIndex;
    var batch;
    SVG_DOC.getElementById("loadingmessage_txt2").textContent = 'Requesting data from server...';
    if(TAGS[tagIndex].dataInfo && TAGS[tagIndex].dataInfo == DATA_EVENT){
    	batch = jsonrpc.graphviewer.getEventBatch(dataloading_newBatch, batchInfo);
    	top.opl_debug("event batch");
    }else{
    	batch = jsonrpc.graphviewer.getBatch(dataloading_newBatch, batchInfo);
    	//batch = jsonrpc.graphviewer.getBatch(batchInfo);
    }
    //top.opl_debug("Got new batch");
    //dataloading_newBatch(batch, null);    
}

function dataloading_getFrom(ind){
	var nbrLoaded = LOADING_DATA.nbrLoaded[LOADING_DATA.varIndex];
	if (nbrLoaded == 0){
		return GRAPH_INFO.graphInfo.from;
	}
	top.opl_debug("Nbr loaded "+nbrLoaded);
	return PROCESS_DATA[ind].data[nbrLoaded-1].timestamp;
}

function dataloading_newBatch(batch, exception){
	top.opl_debug("New batch receieved: "+batch.data.length);
	SVG_DOC.getElementById("loadingmessage_txt2").textContent = 'Drawing graph...';
	if(exception){
		alert(exception);
		return;
	}
	var tagIndex = GRAPH_METADATA.charts[LOADING_DATA.varIndex].tagIndex;
	
	if (batch.data.length > 0){
		TAGS[tagIndex].appendBatch(LOADING_DATA.varIndex, batch.data);
	}
	
	top.opl_debug("Batch, loaded "+LOADING_DATA.nbrLoaded[LOADING_DATA.varIndex] +" of " +GRAPH_METADATA.charts[LOADING_DATA.varIndex].dataCount);
	LOADING_DATA.currentBatchCount += 1;
	if (LOADING_DATA.nbrLoaded[LOADING_DATA.varIndex] == GRAPH_METADATA.charts[LOADING_DATA.varIndex].dataCount){
		dataloading_nextVar();
	}
	else if (LOADING_DATA.currentBatchCount > LOADING_DATA.totalBatchCount)
	{
		top.opl_debug("BUG ERROR: Loader hung");
		dataloading_nextVar();
		LOADING_DATA.currentBatchCount = 0;
	}
	
	if (!GRAPHVIEWER_LOADING_DONE){
		setTimeout('dataloading_requestBatch()',100);
	}
	LOADING_DATA.currentDataCount += batch.data.length;
	
	var p = Math.floor((LOADING_DATA.currentDataCount/LOADING_DATA.totalDataCount)*100);
	SVG_DOC.getElementById("loadingmessage_txt1").textContent = 'Loading.. '+p +' % done';
}

function dataloading_nextVar(){
	LOADING_DATA.varIndex ++;
	if (LOADING_DATA.varIndex == LOADING_DATA.nbrLoaded.length){
		GRAPHVIEWER_LOADING_DONE = true; // defined in graphviewer.js
		SVG_DOC.getElementById("loadingmessage").setAttribute('visibility','hidden');
		SVG_DOC.getElementById("loadingmessage_rect").setAttribute('visibility','hidden');
		top.opl_debug("Loading done.");
		return;
	}
}
