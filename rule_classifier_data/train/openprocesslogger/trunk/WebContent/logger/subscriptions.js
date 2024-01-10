var subscriptions_currentSubscription; //subscription;

function subscriptions_cancel()
{
	
	dijit.byId("subscriptiondialog").hide();
}

function subscriptions_save()
{
	subscriptions_saveSubscription();
	dijit.byId("subscriptiondialog").hide();
}

function subscriptions_cancelSubscription()
{
	var objNode = dijit.byId("subscription_itemsettings_data");
	if(objNode != undefined)
	{
		objNode.destroyRecursive();
	}
	g_blnCloseInfo = false;
}

function subscriptions_dimOptions()
{	
	var objvalues = document.getElementById("restofvalues");
	var onchvalue = document.getElementById("onchangevalues");
	var ontrigvalue = document.getElementById("onTriggers");
	if( (objvalues != null) && (onchvalue != null) && (ontrigvalue != null))
	{
		if (dijit.byId("onChange").checked)
		{
			objvalues.style.filter = "alpha(opacity=20)";
			objvalues.style.opacity = "0.2";
			
			ontrigvalue.style.filter = "alpha(opacity=20)";
			ontrigvalue.style.opacity = "0.2";
		}
		else 
		{
			objvalues.style.filter = "alpha(opacity=100)";
			objvalues.style.opacity = "1";
			objvalues.style.display = "block";
			
			ontrigvalue.style.filter = "alpha(opacity=100)";
			ontrigvalue.style.opacity = "1";
			ontrigvalue.style.display = "block";
			
			onchvalue.style.filter = "alpha(opacity=100)";
			onchvalue.style.opacity = "1";
			onchvalue.style.display = "block";
		}
	}
	else
	{
		opl_debug("Div tags missing");
	}
}

function subscriptions_saveSubscription(evt)
{
	var objAddress = dojo.byId("addressName");
	if(objAddress)
	{
		var address = dojo.byId("addressName").innerHTML;
		opl_debug("address: " +address);
		//var hidden = dojo.byId("hiddenState");
		//opl_debug("Hidden: " +hidden);
		//opl_debug("State: " +hidden.getAttribute("testAttr"));
		if(subscriptions_currentSubscription)
		{
			//var objNode = dijit.byId(g_objControl.strActiveEditNode);
			//objNode.activeView = false;
			//g_objControl.strActiveEditNode = "";
			g_blnCloseInfo = false;
			
			subscriptions_currentSubscription.onChange = dijit.byId("onChange").checked;
			subscriptions_currentSubscription.onTimer = dijit.byId("onInterval").checked;
			subscriptions_currentSubscription.timerInterval = (+dijit.byId("intervalTime").value);
			subscriptions_currentSubscription.useTrigger = dijit.byId("onTrigger").checked;
			subscriptions_currentSubscription.useRingBuffer = dijit.byId("useRingBuffer").checked;
			subscriptions_currentSubscription.countBefore = (+dijit.byId("bufferBeforeTrigger").value);
			subscriptions_currentSubscription.countAfter = (+dijit.byId("bufferAfterTrigger").value);
			subscriptions_currentSubscription.usingSeconds = dojo.byId("bufUsingSeconds").selected;
			subscriptions_currentSubscription.logTaskId = dojo.byId("logTaskId").innerHTML; // div in datasettings.html, set in logging.js
			subscriptions_currentSubscription.useExternalTrigger = dijit.byId("onExternalTrigger").checked;
			subscriptions_saveTriggerOptions();
			opl_debug("onChange "+subscriptions_currentSubscription.onChange);
			opl_debug("valAdr" +subscriptions_currentSubscription.valueAddress);
			
			try
			{
				jsonrpc.controller.updateSubscription(subscriptions_currentSubscription);
			}
			catch(exception)
			{
				opl_analyzeError(exception);
			}
			logging_reloadActiveTask(); // defined in logging.js.
			//alert("Subscription saved");
		}
		else
		{
			doAlert("No subscription defined (internal error)", 1);
		}
	}
}

function subscriptions_renderTriggType(blnRenderOptions)
{
	var arrTypes = new Array();
	arrTypes["DIGITAL"] = new Object();
	arrTypes["DIGITAL"].options = ["onChange", "onEqualTo", "onNotEqualTo", "onFallingEdge", "onRisingEdge"];
	
	arrTypes["ANALOG"] = new Object();
	arrTypes["ANALOG"].options = ["onChange", "onEqualTo", "onNotEqualTo", "onOverLimit", "onUnderLimit", "onPassingLimit", "onInScope", "onOutOfScope"];
	/*
	 * Removed since there is no support on the Java side. TriggerBuilder.java
	arrTypes["STRING"] = new Object();
	arrTypes["STRING"].options = ["onEqualTo", "onNotEqualTo"];
	*/
	dijit.byId("addtriggerbutton").setAttribute("disabled", true);
	var objContainer = null;
	if(document.getElementById("addtriggtypecontainer") == null)
	{
		objContainer = document.createElement("div");
		objContainer.setAttribute("id", "addtriggtypecontainer");
	}
	else
	{
		objContainer = dojo.byId("addtriggtypecontainer");
	}
	
	if(document.getElementById("addtriggtypeoption") != null)
	{
		objContainer.removeChild(document.getElementById("addtriggtypeoption"));
		subscriptions_renderTriggOptions()
	}
	var objTSelect = null;
	if(document.getElementById("addtriggtype") == null)
	{
		objTSelect = document.createElement("select");
		objTSelect.setAttribute("id", "addtriggtype");
		objTSelect.setAttribute("onchange", "subscriptions_renderTriggType(true)");

		objTSelect.options[objTSelect.length] = new Option("Select signal type", "-1", false, false);

		for(var objItem in arrTypes)
		{			
			var objOption = new Option(objItem, objItem, false, false);
			objTSelect.options[objTSelect.length] = objOption;
		}
		objContainer.appendChild(objTSelect);
	}
	else
	{
		objTSelect = document.getElementById("addtriggtype");
	}

	if(blnRenderOptions)
	{	
		if(objTSelect.options.selectedIndex != 0)
		{
			var objOSelect = document.createElement("select");
			objOSelect.setAttribute("id", "addtriggtypeoption");
			
			var strType = document.getElementById("addtriggtype").options[document.getElementById("addtriggtype").selectedIndex].value;
			
			for(intNum = 0; intNum < arrTypes[strType].options.length; intNum++)
			{	
				objOSelect.options[objOSelect.length] = new Option(arrTypes[strType].options[intNum], arrTypes[strType].options[intNum], false, false);
			}
			
			objOSelect.setAttribute("onchange", "subscriptions_renderTriggOptions()");
			objContainer.appendChild(objOSelect);
			dijit.byId("addtriggerbutton").setAttribute("disabled", false);
		}
	}

	return objContainer;
}

function subscriptions_renderTriggOptions(){
	var objContainer = dojo.byId("addtriggtypecontainer");
	subscriptions_hideAllTrigOptionsNew();
	
	var objTrigg = document.getElementById("addtriggtypeoption");
	if(objTrigg != null){
		var selOpt = objTrigg.options[objTrigg.options.selectedIndex].value;
		if(selOpt == "onEqualTo" || selOpt == "onNotEqualTo"){
			dojo.byId("equalityNew").setAttribute("style","");  
		}else if(selOpt == "onOverLimit" || selOpt == "onUnderLimit" || selOpt == "onPassingLimit"){
			dojo.byId("limitTypeNew").setAttribute("style","");
		}else if(selOpt == "onInScope" || selOpt == "onOutOfScope"){
			dojo.byId("scopeTypeNew").setAttribute("style","");
		}
	}
}

function subscriptions_hideAllTrigOptions(){
	dojo.byId("equality").setAttribute("style","display:none");
	dojo.byId("limitType").setAttribute("style","display:none");
	dojo.byId("scopeType").setAttribute("style","display:none");
}

function subscriptions_hideAllTrigOptionsNew(){
	dojo.byId("equalityNew").setAttribute("style","display:none");
	dojo.byId("limitTypeNew").setAttribute("style","display:none");
	dojo.byId("scopeTypeNew").setAttribute("style","display:none");
}
	
function subscriptions_triggerTypeChanged(){
	var trigSelect = dojo.byId("triggerSignal");
	var selOpt = trigSelect.options[trigSelect.selectedIndex].value;
	
	subscriptions_hideAllTrigOptions();
	
	if(selOpt == "onEqualTo" || selOpt == "onNotEqualTo"){
		dojo.byId("equality").setAttribute("style","");  
	}else if(selOpt == "onOverLimit" || selOpt == "onUnderLimit" || selOpt == "onPassingLimit"){
		dojo.byId("limitType").setAttribute("style","");
	}else if(selOpt == "onInScope" || selOpt == "onOutOfScope"){
		dojo.byId("scopeType").setAttribute("style","");
	}
}
 
function subscriptions_saveTriggerOptions() {
	var trigSelect = dojo.byId("triggerSignal");
	var selOpt = trigSelect.options[trigSelect.selectedIndex].value;
	subscriptions_currentSubscription.trigger.trigOption = selOpt;
	subscriptions_currentSubscription.trigger.settings.map.equalityValue = (+dijit.byId("equalityValue").value);
	subscriptions_currentSubscription.trigger.settings.map.limit = (+dijit.byId("limitValue").value);
	subscriptions_currentSubscription.trigger.settings.map.topScope = (+dijit.byId("topScope").value);
	subscriptions_currentSubscription.trigger.settings.map.bottomScope = (+dijit.byId("bottomScope").value);
}

function subscriptions_loadSubscription(subId, task)
{
	var sub = task.subscriptions.map[subId];
	dojo.byId("addressName").innerHTML = sub.valueAddress;
	dijit.byId("onChange").setValue(sub.onChange);
	dijit.byId("onInterval").setValue(sub.onTimer);
	dijit.byId("intervalTime").setValue(sub.timerInterval);
	dijit.byId("onTrigger").setValue(sub.useTrigger);
	dijit.byId("useRingBuffer").setValue(sub.useRingBuffer);
	dijit.byId("bufferBeforeTrigger").setValue(sub.countBefore);  
	dijit.byId("bufferAfterTrigger").setValue(sub.countAfter);
	dijit.byId("onExternalTrigger").setValue(sub.useExternalTrigger);
	if(sub.usingSeconds)
	{
		dojo.byId("bufUsingSeconds").selected = true;
	}
	else
	{
		dojo.byId("bufUsingValues").selected = true;
	}
	
	subscriptions_currentSubscription = sub;
	
	subscriptions_loadTriggerOptions(sub);
	subscriptions_loadAddressValues(sub);
	
	jsonrpc.controller.getAddressValues(subscriptions_loadAddressValuesSelect);	
	
	dojo.byId("taskNameSubDiv").innerHTML = task.name;
	
	subscriptions_dimOptions();
}

function subscriptions_loadAddressValues(sub)
{
	if(typeof(sub) == "object")
	{
		var objAddressValueList = dojo.byId("addressvaluelist");
		objAddressValueList.innerHTML = "<ul>";
		
		for(opt in sub.triggers.map) 
		{
			var selOpt = sub.triggers.map[opt].trigOption;
			var limits = "";
			if(selOpt == "onEqualTo" || selOpt == "onNotEqualTo"){
				limits += sub.triggers.map[opt].settings.map.equalityValue;
			}else if(selOpt == "onOverLimit" || selOpt == "onUnderLimit" || selOpt == "onPassingLimit"){
				limits += sub.triggers.map[opt].settings.map.limit;
			}else if(selOpt == "onInScope" || selOpt == "onOutOfScope"){
				limits += sub.triggers.map[opt].settings.map.bottomScope + "-"+ sub.triggers.map[opt].settings.map.topScope;
			}
			objAddressValueList.innerHTML += "<li>" 
				+ opt 
				+ "&nbsp;<span onclick=\"subscriptions_removeTrigger('" + opt + "');\" class=\"closeicon\">remove</span>"
				+ "<br>" + sub.triggers.map[opt].trigOption + ": " + limits + " (" + sub.triggers.map[opt].settings.map.type + ")</span></li>";
		}
		
		objAddressValueList.innerHTML += "</ul>";
	}
}

function subscriptions_loadAddressValuesSelect(arrAddressV)
{
	var objAddressValueSelect = dojo.byId("addressvalues");
	objAddressValueSelect.innerHTML = "";
	
	if(arrAddressV.length > 0)
	{
		var newOpt = new Option("Select new trigger", "-1", false, false);
		objAddressValueSelect.options[objAddressValueSelect.length] = newOpt;
		for(var i = 0; i < arrAddressV.length; i++) 
		{
			var newOpt = new Option(arrAddressV[i], arrAddressV[i], false, false);
			objAddressValueSelect.options[objAddressValueSelect.length] = newOpt;
		}
	}
	else
	{
		dojo.byId("newtrigger").style.display = "none";
	}
	
	dojo.byId("equalityNew").setAttribute("style","display:none");
	dojo.byId("limitTypeNew").setAttribute("style","display:none");
	dojo.byId("scopeTypeNew").setAttribute("style","display:none");
	
}

function subscriptions_renderAddressValueOptions()
{
	var objTContainer = subscriptions_renderTriggType("");
	var objContainer = dojo.byId("addressvalueoptions");
	objContainer.appendChild(objTContainer);
}

function subscriptions_addTrigger()
{
	var objAddVal = document.getElementById("addressvalues");
	var objType = document.getElementById("addtriggtype");
	var objTigg = document.getElementById("addtriggtypeoption");

	if(objAddVal != null && objType != null && objTigg != null)
	{
		var strAddressValue = objAddVal.options[objAddVal.options.selectedIndex].value;
		var strType = objType.options[objType.options.selectedIndex].value;

		if(objAddVal.options.selectedIndex != 0)
		{
			if(objType.options.selectedIndex != 0)
			{
				var strTrigg = objTigg.options[objTigg.options.selectedIndex].value;
	
				var objData = new Object();
				objData.javaClass					= "se.openprocesslogger.proxy.TriggerProxy";
				objData.settings = new Object();
				objData.settings.javaClass			= "java.util.HashMap";
				objData.settings.map = new Object();
				objData.settings.map.type			= strType;
				objData.trigOption					= strTrigg;
				
				objData.settings.map.equalityValue	= (+dijit.byId("equalityValueNew").value);
				objData.settings.map.limit			= (+dijit.byId("limitValueNew").value);
				objData.settings.map.topScope		= (+dijit.byId("topScopeNew").value);
				objData.settings.map.bottomScope	= (+dijit.byId("bottomScopeNew").value);
				
				subscriptions_currentSubscription.triggers.map[""+strAddressValue+""] = objData;
				subscriptions_loadAddressValues(subscriptions_currentSubscription);
			}
			else
			{
				doAlert("Please select a valid type before adding a new trigger.", 1);
			}
		}
		else
		{
			doAlert("Please select an address value before adding a new trigger.", 1);
		}
	}
}

function subscriptions_removeTrigger(strIndex)
{
	//subscriptions_currentSubscription.triggers.map = removeObjectElement(subscriptions_currentSubscription.triggers.map, strIndex);
	delete subscriptions_currentSubscription.triggers.map[strIndex];
	
	subscriptions_loadAddressValues(subscriptions_currentSubscription);
}

function subscriptions_loadTriggerOptions(sub) {
	var trigSelect = dojo.byId("triggerSignal");
	trigSelect.innerHTML = "";
	for(opt in sub.triggerOptions) {
		var newOpt = new Option(sub.triggerOptions[opt], sub.triggerOptions[opt], false, false);
		if(sub.trigger.trigOption == newOpt.value) {
			newOpt.selected = true;
		}
		trigSelect.options[trigSelect.length] = newOpt;
	}
	subscriptions_triggerTypeChanged();
	var temp = (+sub.trigger.settings.map.equalityValue);
	if(temp != null) dijit.byId("equalityValue").setValue(temp);
	temp = (+sub.trigger.settings.map.limit);
	if(temp != null) dijit.byId("limitValue").setValue(temp);
	temp = (+sub.trigger.settings.map.topScope);
	if(temp != null) dijit.byId("topScope").setValue(temp);
	temp = (+sub.trigger.settings.map.bottomScope);
	if(temp != null) dijit.byId("bottomScope").setValue(temp);
}
