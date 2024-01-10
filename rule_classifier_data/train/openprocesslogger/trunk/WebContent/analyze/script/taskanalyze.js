var taskanalyze_latestTaskInfos;
var taskanalyze_chosenTask;
var input_graphviwer_graphInfo1;
var input_graphviwer_graphInfo2;

/***
 * Loads a list of latest log tasks into analyzePaneTable
 */
function taskanalyze_loadLatestTasks(){
	taskanalyze_fillTaskTable();
	dijit.byId('tabSettings').setHref("analyze/jsp/taskanalyze_settings.html");
}

function taskanalyze_fillTaskTable(){
	var table = dojo.byId("taskanalyze_analyzePaneTable");
	table.innerHTML = "<tr><td>Loading...</td></tr>";
	try {
		var tasklist = jsonrpc.controller.getLatestTasks();
		taskanalyze_loadLatestTasksCB(tasklist);
	}catch(exception){
		opl_analyzeError(exception);
	}	
}

function taskanalyze_loadLatestTasksCB(taskList, exception){
	var taskTable = dojo.byId("taskanalyze_analyzePaneTable");
	while (taskTable.hasChildNodes()) {
		taskTable.removeChild(taskTable.firstChild);
	}
	var parentNode = taskTable.parentNode;
	parentNode.removeChild(taskTable);

	var k = 0;
	taskanalyze_latestTaskInfos = taskList;
	for(i=0; i<taskList.length; i++){
		var taskName = taskList[i].name;
		var tRow = document.createElement("tr");
		tRow.setAttribute("id", "taskanalyze_tr"+taskName);
		tRow.setAttribute("onclick", "taskanalyze_loadTaskInfo("+i+")");
		var tCell1 = document.createElement("td");
		var tCell2 = document.createElement("td");
		tCell1.appendChild(getImage(false)); // getImage is in openprocesslogger.js
		tCell2.innerHTML = taskName;
		tCell2.setAttribute("id", "taskanalyze_td"+taskName);
		tRow.appendChild(tCell1);
		tRow.appendChild(tCell2);
		taskTable.appendChild(tRow);
	}

	parentNode.appendChild(taskTable);
}

/***
 * Load info into analyze settings
 */
function taskanalyze_loadTaskInfo(index){
	opl_debug("load task "+index);
	dijit.byId('tabSettings').setHref("analyze/jsp/taskanalyze_settings.html");
	var task = taskanalyze_latestTaskInfos[index];

	var titleRow = document.getElementById("taskanalyze_tr"+task.name);
	var loadingTd = document.createElement("td");
	loadingTd.setAttribute("id", "loadingTd");
	loadingTd.setAttribute("class", "loadingTaskClass");
	loadingTd.innerHTML = "Loading info...";
	titleRow.appendChild(loadingTd);
	try{
		var canDelete = jsonrpc.controller.taskHasData(task.logTaskId);
		if(canDelete == 3){
			if(confirm("There is no data for this logging task. Delete it?")){
				jsonrpc.controller.deleteTask(task.logTaskId);
				taskanalyze_loadLatestTasks();
			}
			titleRow.removeChild(loadingTd);
			return;
		}else if(canDelete == 1){
			alert("The task is still running!");
			titleRow.removeChild(loadingTd);
			return;
		}else if(canDelete == 4){
			alert("Server is still processing data for logging task");
			titleRow.removeChild(loadingTd);
		}
	}catch(exception){
		opl_analyzeError(exception);
		return;
	}

	opl_debug("task "+task.name);
	jsonrpc.controller.buildTree(taskanalyze_buildTreeCB, task);
	jsonrpc.controller.getLogTask(taskanalyze_loadTaskInfoCB, task.name);
}
function taskanalyze_loadTaskInfoCB(task, exception){
	if(exception){
		opl_analyzeError(exception);
		return;
	}

	if(taskanalyze_chosenTask){
		dojo.byId("taskanalyze_td"+taskanalyze_chosenTask.name).setAttribute("class", "unSelectedLogTask");

	}	
	taskanalyze_chosenTask = task;
	var titleRow = document.getElementById("taskanalyze_tr"+taskanalyze_chosenTask.name);
	var loadingTd = document.getElementById("loadingTd");
	titleRow.removeChild(loadingTd);

	var selTd = dojo.byId("taskanalyze_td"+taskanalyze_chosenTask.name); 
	selTd.setAttribute("class", "selectedLogTask");	

	dojo.byId("taskanalyze_nameDiv").innerHTML = task.name;
	dojo.byId("taskanalyze_infoDiv").innerHTML = task.properties.map.info;
	var startLong = (+task.startTime);
	var stopLong = (+task.stopTime);
	var startDate = new Date(startLong);
	var endDate = new Date(stopLong);
	dojo.byId("taskanalyze_startTimeDiv").innerHTML = timeutil_formatDate(startDate.getFullYear(), startDate.getMonth(), startDate.getDate()) 
		+ " " + timeutil_formatTime(startDate.getHours(), startDate.getMinutes());
	dojo.byId("taskanalyze_endTimeDiv").innerHTML = timeutil_formatDate(endDate.getFullYear(), endDate.getMonth(), endDate.getDate()) 
		+ " " + timeutil_formatTime(endDate.getHours(), endDate.getMinutes());

	taskanalyze_updateVarStateSelect();
}

function taskanalyze_buildTreeCB(tree, exception){
	if(exception){
		opl_analyzeError(exception);
		return;
	}
	// opl_debug("Building tree");
	var taskListStore=new dojo.data.ItemFileWriteStore(tree);
	var oldTree = dijit.byId("taskanalyze_subscriptionTree");
	if(oldTree){
		oldTree.destroy();
	}

	var myTree = new dijit.Tree({
									store: taskListStore,
									id: "taskanalyze_subscriptionTree"
								}, document.createElement("div"));


	myTree.startup();
	dojo.connect(myTree,'onClick','taskanalyze_selectVariable');
	
	dojo.byId("taskanalyze_taskListTree").appendChild(myTree.domNode);
	dojo.byId("taskanalyze_settingsBodyDiv").setAttribute("style","visibility:visible");
	dojo.byId("taskanalyze_statusMessage").setAttribute("style","visibility:hidden");    
	dojo.byId("taskanalyze_selectedVariablesList").innerHTML = "";
	
	taskanalyze_initTaskCompareSelect();
}

// Init select to compare setup with another task
function taskanalyze_initTaskCompareSelect(){
	var selectVals = new Array(taskanalyze_latestTaskInfos.length+1);
	for(var i=0; i<taskanalyze_latestTaskInfos.length; i++){
		selectVals[i] = {
							name : taskanalyze_latestTaskInfos[i].name,
	   						taskIndex : i}      
						};
		selectVals[taskanalyze_latestTaskInfos.length] = { 
														name : "(none)", taskIndex : -1
													};

	var taskInnerData = {
							identifier : "taskIndex",
			 				items : selectVals
			 			};

	var taskData = {data:taskInnerData};
	var oldSelect = dijit.byId("taskanalyze_taskSelect");
	if(oldSelect){
		oldSelect.destroy();
	}

	var taskListStore2 = new dojo.data.ItemFileReadStore(taskData);

	var taskSelect = new dijit.form.FilteringSelect({
														id:"taskanalyze_taskSelect",
														store: taskListStore2
													}, document.createElement("select"));
	taskSelect.setValue(selectVals[taskanalyze_latestTaskInfos.length]);
	taskSelect.startup();
	dojo.byId("taskanalyze_taskSelectHolder").appendChild(taskSelect.domNode);
}

function taskanalyze_updateVarStateSelect(){
	var list = dojo.byId("taskanalyze_selectedVariablesList");
	var selectVals = new Array(list.childNodes.length);
	for(var i=0; i<list.childNodes.length; i++){
		selectVals[i] = {
			name : list.childNodes[i].id
		};
	}
	selectVals[list.childNodes.length] = { name : "(none)"}
	selectVals.sort();
	var selValInnerData = {
							identifier : "name",
			 				items : selectVals
							};
	var selValData = {data:selValInnerData};
	var oldSel = dijit.byId("taskanalyze_varSelect");
	if(oldSel){
		oldSel.destroy();
	}
	var selListStore = new dojo.data.ItemFileReadStore(selValData);
	var valSelect = new dijit.form.FilteringSelect({
														id:"taskanalyze_varSelect",
														store: selListStore,
														style: "width:450px"
													}, document.createElement("select"));
	valSelect.setValue(selectVals[taskanalyze_latestTaskInfos.length]);
	valSelect.startup();
	dojo.byId("taskanalyze_varSelectHolder").appendChild(valSelect.domNode);
}

function taskanalyze_getSelectedStateVariable(){
	var selectedName = "";
	if(dijit.byId("taskanalyze_varSelect").item){
		var selItem =dijit.byId("taskanalyze_varSelect").item;
		selectedName = selItem.name[0];
		if(selectedName == "(none)"){
			return "";
		}
	}
	return selectedName;
}

/***
 * Manipulate analyze settings
 */

// Select a variable to show in graph
function taskanalyze_selectVariable(item, node){
	if(item.children){
		// Don't add subtree to list, add it's children
		for(var i in item.children){
			taskanalyze_selectVariable(item.children[i], node);
		}
		return;
	}
	var list = dojo.byId("taskanalyze_selectedVariablesList");
	var selectVals = new Array(list.childNodes.length);
	for(var i=0; i<list.childNodes.length; i++){
		if (list.childNodes[i].id == item.id)
			return;
	}
	
	var newNode = document.createElement("li");
	newNode.setAttribute("id",item.id);
	newNode.setAttribute('onClick', "taskanalyze_removeVariableFromList('"+item.id[0]+"')");
	var itemId = item.id[0];
	itemId = itemId.replace("/","-");
	newNode.innerHTML = item.name +"\t(" +itemId +")";
	list.appendChild(newNode);

	taskanalyze_updateVarStateSelect();
}

// Remove variable
function taskanalyze_removeVariableFromList(id){
	var node = dojo.byId("taskanalyze_selectedVariablesList");
	var child = dojo.byId(id);
	node.removeChild(child);

	taskanalyze_updateVarStateSelect();

}


/***
* Graph loading
* */


function taskanalyze_viewAnalyze(){

	var list = dojo.byId("taskanalyze_selectedVariablesList");

	var selectedIndex = -1;
	if(dijit.byId("taskanalyze_taskSelect").item){
		selectedIndex = dijit.byId("taskanalyze_taskSelect").item.taskIndex;
	}

	if(list.childNodes.length==0){
		alert("No variables chosen.");
		return;
	}
	
	var infoString1 = taskanalyze_getGraphMetadata(list);
	
	var startLong = (+taskanalyze_chosenTask.startTime);
	var stopLong = (+taskanalyze_chosenTask.stopTime);
	
	// Variable selected as subBarChart
	var selectedStateVariable = taskanalyze_getSelectedStateVariable();
	var selectedGraphIndex = -1;
	if (selectedStateVariable){
		infoString1 += selectedStateVariable+";"+"se.openprocesslogger.svg.taglib.BarChartShell;";
		selectedGraphIndex = list.childNodes.length;
	}
	
	var analyzeBean1 = {
		"javaClass" : "se.openprocesslogger.proxy.graphviewer.GraphMetadata",
		"from" : startLong,
		"to" : stopLong,
		"variables" : infoString1,
		"logTaskId" : taskanalyze_chosenTask.id
	};
	
	var taskCompareSelect = dijit.byId("taskanalyze_taskSelect");
	var analyzeBean2 = null;
	
	if (taskCompareSelect.getValue() && taskCompareSelect.getValue()>= 0){
		var compareTaskName = taskCompareSelect.item.name[taskCompareSelect.getValue()];
		var compareTask;
		try{
			console.debug("find log task "+compareTaskName);
			compareTask = jsonrpc.controller.getLogTask(compareTaskName);
			var startLong2 = (+compareTask.startTime);
			var stopLong2 = (+compareTask.stopTime);
			analyzeBean2 = {
				"javaClass" : "se.openprocesslogger.proxy.graphviewer.GraphMetadata",
				"from" : startLong2,
				"to" : stopLong2,
				"variables" : infoString1,
				"logTaskId" : compareTask.id
			};
		}catch(exception){
			alert("ex: "+exception);
			throw(exception);
		}		
	}
	if (analyzeBean2){
		input_graphviwer_graphInfo1 = new Object();
		input_graphviwer_graphInfo1.graphInfo = analyzeBean1;
		input_graphviwer_graphInfo2 = new Object();
		input_graphviwer_graphInfo2.graphInfo = analyzeBean2;
		
		if (selectedStateVariable){
			input_graphviwer_graphInfo1.subChartIndex = selectedGraphIndex;
			input_graphviwer_graphInfo2.subChartIndex = selectedGraphIndex;
		}
		
		taskanalyze_loadNewTab("graphviewer/jsp/timeAnalyze_2.jsp", "Task "+taskanalyze_chosenTask.name);
	}else{
		input_graphviwer_graphInfo2 = new Object();
		input_graphviwer_graphInfo2.graphInfo = analyzeBean1;
		
		if (selectedStateVariable){
			input_graphviwer_graphInfo2.subChartIndex = selectedGraphIndex;
		}		
		taskanalyze_loadNewTab("graphviewer/jsp/timeAnalyze_1.jsp", "Task "+taskanalyze_chosenTask.name);
	}
}

function taskanalyze_getGraphMetadata(list){
	var chosenArr = new Array(list.childNodes.length);
	for(var i=0; i<list.childNodes.length; i++){
		chosenArr[i] = list.childNodes[i].id; 
	}
	
	var chartTypes = jsonrpc.controller.getDefaultChartTypes(chosenArr, taskanalyze_chosenTask.id);
	var infoString = "";
	for(var i=0; i<chosenArr.length; i++){
		infoString += chosenArr[i]+";"+chartTypes[i]+";";
	}
	return infoString;
}


function taskanalyze_loadNewTab(tab_href, tab_title) 
{
	var tabCon = dijit.byId('tabContainer');
	console.debug(tabCon);
	var children = tabCon.getChildren();
	console.debug(children);
	
	var newDataTab = new dijit.layout.ContentPane( 
	{
		title: tab_title,
		closable: true
	});
	console.debug(newDataTab);
	newDataTab.setHref(tab_href);
	
	console.debug("hr ");
	tabCon.addChild(newDataTab);
	console.debug("add");
	tabCon.selectChild(newDataTab);
	console.debug("sel");
	return newDataTab;
}

