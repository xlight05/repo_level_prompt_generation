var logging_activeLogTasks; // array of LoggingTaskData
var logging_logTaskTemplates; // array of LogTaskProxy
var logging_chosenTemplateIndex;
var logging_chosenTaskProxy;
var logging_latestSubLoadName;
var g_arrTemplates = new Array(); // Array of templates with template name ie "tpc_template" as key and "TPC Logger Task" as value.

/**
*
* Load pane information
*
*/
function logging_refreshActiveTasks(){
	dojo.byId("logTaskPaneTable").innerHTML = gui_getLoaderText();
	jsonrpc.controller.getActiveLogTasks(logging_logTaskPaneCB);
	opl_checkForErrors();
}

function logging_reloadTemplates(){
	dojo.byId("logTaskTemplatePaneTable").innerHTML = gui_getLoaderText();
	jsonrpc.controller.getTaskTemplates(logging_logTaskTemplatePaneCB);
}

function logging_fillLogTaskPane(){
	logging_refreshActiveTasks();
	logging_reloadTemplates(); 
}

// Create task list
function logging_logTaskPaneCB(activeTasks, exception)
{
	if(exception)
	{ 
		opl_analyzeError(exception);
		return;
	}
	var chosenIndex = -1;
	logging_activeLogTasks = activeTasks.list;

	
	var objContainer = document.getElementById("TaskContainer");
	var objTaskTable = gui_createItemTable("logTaskPaneTable", objContainer);

	// Fill table with active tasks
	for(i = 0; i < logging_activeLogTasks.length; i++)
	{
		var taskName = logging_activeLogTasks[i].name;
		var started = logging_activeLogTasks[i].started;
		var tRow = document.createElement("div");
		tRow.setAttribute("id", "taskpanelist" + i);
		tRow.setAttribute("onclick", "logging_loadActiveTaskInfo("+i+")");
		tRow.appendChild(getImage(started)); // getImage is in openprocesslogger.js
		
		tRow.innerHTML += taskName + " <span id=\"taskmsg" + i + "\"></span>";
		objTaskTable.appendChild(tRow);
		
		if(logging_chosenTaskProxy && (taskName == logging_chosenTaskProxy.name))
		{
			chosenIndex = i;
			logging_chosenTemplateIndex = -1;
		}
	}
	
	objContainer.appendChild(objTaskTable);

	// Load last task
	if(chosenIndex > -1){
		logging_loadActiveTaskInfo(chosenIndex);
	}
}

// Load template list
function logging_logTaskTemplatePaneCB(templates, exception)
{
	if(exception)
	{
		opl_analyzeError(exception);
		return;
	}
	var objContainer = document.getElementById("TemplateContainer");
	var objTemplateTable = gui_createItemTable("logTaskTemplatePaneTable", objContainer);
	
	logging_logTaskTemplates = templates;
	
	for(var i = 0; i < logging_logTaskTemplates.length; i++)
	{
		g_arrTemplates["'" + logging_logTaskTemplates[i].properties.map.templateName + "'"] = logging_logTaskTemplates[i].name;
		var tRow = document.createElement("div");
		tRow.setAttribute("id", "templatepanelist" + i);
		tRow.setAttribute("onclick", "logging_createNewTaskFromTemplate("+i+");");
		tRow.appendChild(getImage(false)); // getImage is in openprocesslogger.js
		tRow.innerHTML += logging_logTaskTemplates[i].name;
		objTemplateTable.appendChild(tRow);
	}
	
	objContainer.appendChild(objTemplateTable);
}


/**
 * 
 * Load task settings info datamodel.html
 * 
 */
// onclick on active task 
function logging_loadActiveTaskInfo(index)
{
	opl_debug("logging_loadActiveTaskInfo - index: " + index);
	if(index >= 0)
	{
		gui_setSelectedItem("taskpanelist", index, "logTaskIndex");	
		var task = logging_activeLogTasks[index];
		if(task != undefined)
		{
			jsonrpc.controller.buildTree(buildActiveLogTaskTreeCB, task);
			jsonrpc.controller.getLogTask(loadActiveLogTaskCB, task.name);
		}
	}
}

function logging_reloadActiveTask()
{
	opl_debug("reload task: " + logging_chosenTaskProxy + "\nName:" + logging_chosenTaskProxy.name);
	if(logging_chosenTaskProxy)
	{
		jsonrpc.controller.buildProxyTree(buildActiveLogTaskTreeCB, logging_chosenTaskProxy);
		jsonrpc.controller.getLogTask(loadActiveLogTaskCB, logging_chosenTaskProxy.name);
	}
}

// onclick on template
function logging_createNewTaskFromTemplate(index)
{
	logging_chosenTemplateIndex = index;
	logging_chosenTaskProxy = null;
	var task = logging_logTaskTemplates[index];
	jsonrpc.controller.buildProxyTree(buildActiveLogTaskTreeCB, task);
	
	dojo.byId("templatepanelist" + index).className = "selected";

	dojo.byId("trTemplateName").style.display = "none";
	dojo.byId("trEquipmentID").style.display = "block";
	dojo.byId("taskStopButton").style.display = "none";
	
	dijit.byId("logTaskName").setValue("");
	dijit.byId("loggingTaskInfo").setValue("");
	dijit.byId("dtbStart").setValue("");
	dijit.byId("dtbStartTime").setValue("");
	dijit.byId("dtbEnd").setValue("");
	dijit.byId("dtbEndTime").setValue("");
	dojo.byId("templateName").innerHTML = task.properties.map.templateName;
	dojo.byId("logTaskId").innerHTML = "-1";
	dojo.byId("logTaskIndex").innerHTML = index;
	
	setButtonName("Create task");
	setTabName("New Task from template: <strong>" + g_arrTemplates["'" + task.properties.map.templateName + "'"] + "</strong>");
}
// Done at a click on a Task
function loadActiveLogTaskCB(taskProxy, exception){
	if(exception){
		opl_analyzeError(exception);
		return;
	}
	
	if(taskProxy != null && taskProxy.name != "")
	{
		logging_chosenTaskProxy = taskProxy;
		logging_chosenTemplateIndex = -1;
		
		g_objControl.strActiveEditNode = "";
		
		dijit.byId("logTaskName").setValue(taskProxy.name);
		dijit.byId("loggingTaskInfo").setValue(taskProxy.properties.map.info);
		setTabName("Settings for task: <strong>" + taskProxy.name + "</strong>");
		
		setButtonName("Save task settings");
		document.getElementById("trEquipmentID").style.display = "none";
		//dijit.byId("logTaskName").setAttribute("disabled", "disabled");
		
		var startLong = (+taskProxy.startTime);
		var stopLong = (+taskProxy.stopTime);
		if(startLong > 0)
		{
			var startDate = new Date(startLong);
			dijit.byId("dtbStart").setValue(new Date(startDate.getFullYear(), startDate.getMonth(), startDate.getDate(), 0, 0, 0));
			dijit.byId("dtbStartTime").setValue(new Date(0,0,0,startDate.getHours(),startDate.getMinutes(), 0));
		}
		if(stopLong > 0)
		{
			var endDate = new Date(stopLong);
			dijit.byId("dtbEnd").setValue(new Date(endDate.getFullYear(), endDate.getMonth(), endDate.getDate(), 0, 0, 0));
			dijit.byId("dtbEndTime").setValue(new Date(0,0,0,endDate.getHours(),endDate.getMinutes(), 0));
		}
		
		// If the task is running dont show the timer switch button and add a stop button next to the cancel and save button
		if(taskProxy.started)
		{
			dojo.byId("tastTQSwitch").style.display = "none";
			dojo.byId("taskQuickstart").style.display = "none";
			dojo.byId("taskStopButton").style.display = "inline";
		}
		else
		{
			dojo.byId("tastTQSwitch").style.display = "block";
			dojo.byId("taskQuickstart").style.display = "none";
			dojo.byId("taskStopButton").style.display = "none";
		}

		dojo.byId("templateDisplayName").innerHTML = g_arrTemplates["'" + taskProxy.properties.map.templateName + "'"]; // Get the displayname for the Template
		dojo.byId("templateName").innerHTML = taskProxy.properties.map.templateName;
		dojo.byId("logTaskId").innerHTML = taskProxy.id;
	}
}

/***
 *
 * Utility functions
 *
 */
// Load info from datamodel.html into LogTaskProxy
function getLogTaskBean()
{
	var info = dijit.byId("loggingTaskInfo");
	var sd = dijit.byId("dtbStart").getValue();
	var st = dijit.byId("dtbStartTime").getValue();
	var ed = dijit.byId("dtbEnd").getValue();
	var et = dijit.byId("dtbEndTime").getValue();

	var hr = 0;
	var min = 0;
	var startTime = ""+0;

	// Date but no time, set date and 00:00:00
	if(sd && !st)
	{
		startTime = "" + (new Date(sd.getFullYear(),sd.getMonth(),sd.getDate(),0,0,0)).getTime();
	}
	// time set
	else if(st)
	{ 
		if(!sd) sd = new Date(); // If date not set, assume today
		startTime = "" + (new Date(sd.getFullYear(),sd.getMonth(),sd.getDate(),st.getHours(),st.getMinutes(),0)).getTime();
	}

	var stopTime = ""+0;
	// Date but no time, set date and 00:00:00
	if(ed && !et)
	{
		stopTime = "" + (new Date(ed.getFullYear(),ed.getMonth(),ed.getDate(),0,0,0)).getTime();
	}
	else if(et)
	{
		if(!ed) ed = new Date();
		stopTime = "" + (new Date(ed.getFullYear(),ed.getMonth(),ed.getDate(),et.getHours(),et.getMinutes(),0)).getTime();
	}

	var subscriptions; // Same as loaded from server.
	if(logging_chosenTemplateIndex > -1)
	{
		subscriptions = logging_logTaskTemplates[logging_chosenTemplateIndex].subscriptions;
	}
	else
	{
		subscriptions = logging_chosenTaskProxy.subscriptions;
	}

	var propMap = {
			"javaClass" : "java.util.HashMap",
			"map" : { 
				"info" : dijit.byId("loggingTaskInfo").value,
				"templateName" : dojo.byId("templateName").innerHTML
			}
	}

	var myBean = {
		"javaClass" : "se.openprocesslogger.proxy.LogTaskProxy",
		"name" : gui_splitString(dijit.byId("logTaskName").value, 15),
		"properties" : propMap,
		"id" : dojo.byId("logTaskId").innerHTML,
		"subscriptions" : subscriptions,
		"startTime" : startTime, 
		"stopTime" : stopTime				
	}
	return myBean;
}

/***
 * *
 * * Log task actions
 * *
 */

// Check all input fields before creating task
function validateTask()
{
	var strErrMsg = "";
	var strErrReqMsg = "";
	
	var intStartTime = parseFloat(document.getElementById("dtbStartTime").value.replace(":", ""));
	var intEndTime = parseFloat(document.getElementById("dtbEndTime").value.replace(":", ""));
	
	var intStartDate = parseFloat(document.getElementById("dtbStart").value.replace(/\-/g, ""));
	var intEndDate = parseFloat(document.getElementById("dtbEnd").value.replace(/\-/g, ""));
	
	// Hantering av obligatoriska fält
	if (document.getElementById("logTaskName").value == "")
	{
		strErrReqMsg = "'Name', ";
	}
	if (document.getElementById("dtbStart").value == "")
	{
		strErrReqMsg += "'Start date', ";
	}
	if (document.getElementById("dtbEnd").value == "")
	{
		strErrReqMsg += "'End date', ";
	}
	if (document.getElementById("dtbStartTime").value == "")
	{
		strErrReqMsg += "'Start time', ";
	}
	if (document.getElementById("dtbEndTime").value == "")
	{
		strErrReqMsg += "'End time', ";
	}

	if(intStartDate > intEndDate)
	{
		strErrMsg += "'End date' occurs before 'Start date'\n";
	}
	
	if(intStartDate >= intEndDate)
	{
		if(intStartTime > intEndTime)
		{
			strErrMsg += "'End time' occurs before 'Start time'\n";
		}
		
		if(intStartTime == intEndTime)
		{
			strErrMsg += "'Start time' and 'End time' are the same. The minimum increment is 15 min.\n";
		}
	}
	
	if(strErrMsg == "" && strErrReqMsg == "")
	{
		return true;
	}
	else
	{
		if(strErrReqMsg != "")
		{
			doAlert("Please make sure that the required fields: " + strErrReqMsg.substring(0, strErrReqMsg.length - 2) + " are filled in before saving the task.", 1);
		}
		else if(strErrMsg != "")
		{
			doAlert(strErrMsg, 1);
		}
		
		return false;
	}
}

// Save or update 
function saveLogTask()
{
	var name = gui_splitString(document.getElementById("logTaskName").value, 15);  // in gui.js

	if(validateTask())
	{
		var id = dojo.byId("logTaskId").innerHTML;
		var myBean = getLogTaskBean();

		// Create new from template
		if(logging_chosenTemplateIndex > -1)
		{
			try
			{
				var k = jsonrpc.controller.logTaskExists(name, id);
				if(k==2)
				{
					doAlert("The name is already in use! \n Please choose another name.", 1);
					return false;
				}
				logging_chosenTaskProxy = myBean;
				jsonrpc.controller.addLoggingTask(myBean);
				logging_fillLogTaskPane();
			}
			catch(exception)
			{
				opl_analyzeError(exception);
				return;
			}
		}
		else
		{
			try
			{
				var k = jsonrpc.controller.logTaskExists(name, id);
				opl_debug("Task exists. " +k);
				if(k==2)
				{
					doAlert("The name is already in use! \n Please choose another name.", 1);
					return false;
				}
				else if(k==1)
				{
					logging_chosenTaskProxy = myBean;
					jsonrpc.controller.updateLoggingTask(myBean);
					logging_fillLogTaskPane();
				}
				else if(k==4)
				{
					if(confirm("Save as new task? \n (It is not possible to change the name of a task)"))
					{
						logging_chosenTaskProxy = myBean;
						jsonrpc.controller.addLoggingTask(myBean);
						logging_fillLogTaskPane();
					}
				}
			}
			catch(exception)
			{
				opl_analyzeError(exception);
				return;
			}
		}
		// Switch to the tasks pane after saving
		dijit.byId("mainAccordionContainer").selectChild(dijit.byId("TasksPane"));
		return true;
	}
	else
	{
		return false;
	}
}

// Start
function logging_startLogTask(){
	var myBean = getLogTaskBean();
	var saved = true;
	if(logging_chosenTemplateIndex > -1){
		saved = saveLogTask();
	}else if(!myBean || myBean.name != logging_chosenTaskProxy.name){
		saved = saveLogTask();
	}else{
		try{
			jsonrpc.controller.updateLoggingTask(myBean);
		}catch(exception){
			opl_analyzeError(exception);
			return;
		}
	}
	if(!saved){
		return;
	}
	logging_addLoadingText("Starting...");
	jsonrpc.controller.startLoggingTask(logging_startLoggingTaskCB, myBean.name);
	logging_chosenTemplateIndex = -1;
	for(var i=0; i<logging_activeLogTasks.length; i++){
		if(logging_activeLogTasks[i].name == myBean.name){
			logging_chosenTaskProxy = logging_activeLogTasks[i];
		}
	}
}

function logging_startLoggingTaskCB(exception){
	logging_removeLoadingText();
	if(exception) { 
		opl_analyzeError(exception);
		return;
	}
	logging_fillLogTaskPane();
	if(dijit.byId("logTaskName").value != "")
	{
		jsonrpc.controller.getLogTask(loadActiveLogTaskCB, dijit.byId("logTaskName").value);
	}
}

// Stop
function logging_stopLogTask()
{
	if(confirm("Are you sure you want to stop logging?"))
	{
		var myBean = getLogTaskBean();
		if(logging_chosenTemplateIndex > -1 || !myBean || myBean.name != logging_chosenTaskProxy.name)
		{
			doAlert("This log task has not been started or the name has been changed since started", 1);
			return;
		}
		
		if(dijit.byId("logTaskName").value != "")
		{
			document.getElementById("taskmsg" + dojo.byId("logTaskIndex").innerHTML).innerHTML = "<strong>Stopping!</strong>";
			jsonrpc.controller.stopLoggingTask(stopLoggingTaskCB, dijit.byId("logTaskName").value);
			gui_cancelForm(true);
		}
		else
		{
			doAlert("Problem stopping task since the log task name was not found. Please reload the page and try again.", 1);
		}
	}
}

function stopLoggingTaskCB(exception){
	//logging_removeLoadingText();
	if(exception) { 
		opl_analyzeError(exception);
		return;
	}
	logging_fillLogTaskPane();
	jsonrpc.controller.getLogTask(loadActiveLogTaskCB, dijit.byId("logTaskName").value);
}

/***
 * *
 * * Manage subscriptions
 * *
 */
 
 // Build subscription tree
function buildActiveLogTaskTreeCB(tree, exception){
	if(exception) { 
		opl_analyzeError(exception);
		return;
	}
	// Removed by tomi in rebuild of function
	//dojo.byId("subInfoDiv").setAttribute("style","display:none");
	if(exception) {
		alert(exception.message);
		return;
	}
	var taskListStore=new dojo.data.ItemFileWriteStore(tree);
	var oldTree = dijit.byId("myActiveLogTaskTree");
	if(oldTree){
		oldTree.destroy();
	}

	var myTree = new dijit.Tree({
		store: taskListStore,
		openOnClick: "true", 
		id: "myActiveLogTaskTree"
	}, document.createElement("div"));

	myTree.startup();
	dojo.connect(myTree,'onClick','logging_viewSubscriptionInfo');

	dojo.byId("activeLogTaskTreeHolder").appendChild(myTree.domNode);
	//dojo.byId("activeLogTaskTreeHolder").appendChild(treeTooltip.domNode);
	dojo.byId("trLogSettingsMessage").setAttribute("style", "display:none");
	dojo.byId("logTaskSettingsBody").setAttribute("style", "display:inline");
}

 // Eventuelt ta bort om det bara var logging_viewSubscriptionInfo som använde den.
function getYPos(obj){
	if(obj.offsetParent){
		return obj.offsetTop + getYPos(obj.offsetParent);
	}
	return obj.offsetTop;
}

// Global värdeshantering. Ligger i en variabel så man lätt kan nolla den.
var g_objControl = new Object();
g_objControl.strActiveEditNode = "";
g_objControl.blnCloseNode = false;

function closeInfoBox(strNodeId) 
{
	var node = dijit.byId(strNodeId);
	var objNodeInfoElement = document.getElementById(node.id + "_subinfo");
	var objRemoved = node.domNode.removeChild(objNodeInfoElement);
	node.activeView = false;	// Lägg till så att man vet status på varje nod
}
// View details on a subscription
function logging_viewSubscriptionInfo(item, node)
{
	if(!item.children && node.id != g_objControl.strActiveEditNode)
	{
		if(!node.activeView)
		{
			// Skapa infolager i noden man klickat på i trädet
			var objNodeInfoElement = document.createElement("div");
			objNodeInfoElement.setAttribute("id", node.id + "_subinfo");
			objNodeInfoElement.setAttribute("class", "NodeTreeSubInfo");
			
			// Skapa lager för texten
			var objNodeInfoContent = document.createElement("div");
			objNodeInfoContent.setAttribute("id", node.id + "_subinfocontent");
			
			// Skapa contanern för knapparna
			var objButtonElement = document.createElement("div");
			objButtonElement.setAttribute("id", node.id + "_buttoncontainer");
			objButtonElement.setAttribute("class", "buttoncontainer");
			
			// Lägg till objekten i DOMen först så att dijit kan skapa sina objekt nedan
			objNodeInfoElement.appendChild(objNodeInfoContent);
			objNodeInfoElement.appendChild(objButtonElement);
			node.domNode.appendChild(objNodeInfoElement);
			
			if(logging_chosenTemplateIndex > -1)	// If template
			{
				var a1 = logging_logTaskTemplates[logging_chosenTemplateIndex];
				var a2 = a1.subscriptions.map;
				var a3 = a2[item.id];
				var a4 = a3.description;
				objNodeInfoContent.innerHTML = "<b>" + logging_logTaskTemplates[logging_chosenTemplateIndex].subscriptions.map[item.id].description + "</b>";
			}
			else if((dijit.byId("logTaskName").value == logging_chosenTaskProxy.name))
			{
				objNodeInfoContent.innerHTML = "<b>" + logging_chosenTaskProxy.subscriptions.map[item.id].description + "</b>";
				if (!logging_chosenTaskProxy.started)
				{
					// Editera knappen på Task noderna
					var tmpFunction = function() 
					{
						// Skapa för att kunna skicka argument till editSubscription genom dijit.form.Button eftersom denna inte kan hantera anrop till funktioner med argument.
						editSubscription(item.id, node.id);
					}				
					makeButton({label: "Edit", onClick: tmpFunction}, objButtonElement);
				}
			}
			
			node.activeView = true;		// Lägg till så att man vet status på varje nod
			g_objControl.objActiveViewNode = node;
		}
		else
		{
			closeInfoBox(node.id);
		}
		
		g_objControl.blnCloseNode = false; // Kommando för att stänga noden om funktionen körs, triggas genom setCloseInfo()
	}
}

// Create a dijit button and append to an element
function makeButton(objParam, objElement)
{		
	var objButton = new dijit.form.Button(objParam);
	objElement.appendChild(objButton.domNode);
}

function editSubscription(subId, strNodeId){
	myBean = getLogTaskBean();
	opl_debug("Current name: " +myBean.name +" vs chosen name "+logging_chosenTaskProxy.name);
	if(!myBean || myBean.name != logging_chosenTaskProxy.name){
		doAlert("Cannot manage subscriptions on an unsaved task.\n", 1);
		return;
	}
	//if(!confirm("Logging task must be saved before managing subscriptions. Save now?")){
	//  return;
	//}
	try{
		jsonrpc.controller.updateLoggingTask(myBean);
	}catch(exception){
		opl_analyzeError(exception);
		return;
	}

	selectSub(subId, strNodeId);
}

// Open edit task 
function selectSub(subId, strNodeId)
{	
	logging_latestSubLoadName = subId;
	//var objDataSubInfo = dojo.byId(strNodeId + "_subinfo");
	//var objDataField = new dijit.layout.ContentPane({id: strNodeId + "_datafield", href: "logger/itemsettings.html", preload: true, onLoad: logging_fillSubscriptionInformation}, objDataSubInfo);
	
	var objDataSubInfo = document.getElementById("subscription_itemsettings");
	var objDataPane = document.createElement("div");
	
	var objDataField = new dijit.layout.ContentPane({id: "subscription_itemsettings_data", href: "logger/itemsettings.html", onLoad: logging_fillSubscriptionInformation}, objDataPane);
	objDataField.startup();
	
	var objDialog = dijit.byId("subscriptiondialog");
	//dojo.connect(objDialog, "execute", "subscriptions_saveSubscription");
	dojo.connect(objDialog, "hide", "subscriptions_cancelSubscription");
	objDataSubInfo.appendChild(objDataPane);
	objDialog.show();
}

function logging_fillSubscriptionInformation()
{
	opl_debug("Loading subscription "+logging_latestSubLoadName +" for task "+dojo.byId("logTaskId").innerHTML);
	subscriptions_loadSubscription(logging_latestSubLoadName, logging_chosenTaskProxy); //def in manageSubscriptions.js
	return true;
}

function closeSubTab(evt){
	doAlert("call to closeSubTab", 1);	// debug, för att se om denna funktionen används
	return true;
}

function logging_addLoadingText(text){
	var titleRow = document.getElementById("logging_loadingMessage");
	titleRow.innerHTML = text;
}

function logging_removeLoadingText(){
	var titleRow = document.getElementById("logging_loadingMessage");
	titleRow.innerHTML = "";
}
