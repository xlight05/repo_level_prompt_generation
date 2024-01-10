function debug_loadPane()
{
	dijit.byId('tabContainer').selectChild('tabSettings');
	var settings = dijit.byId("tabSettings");
	settings.setHref("debug/debugform.html");
}

function debug_triggerLogger(intAddressValue)
{
	var strAddressValuePath = "Demo Machine/Demo Component 3/Trigg";
	jsonrpc.controller.updateAddressValue(debug_triggerSet, strAddressValuePath, intAddressValue);
}

function debug_triggerSet(blnSuccess)
{
	var objTriggerOutput = document.getElementById("debug_trigger_output");
	if(blnSuccess)
	{
		objTriggerOutput.innerHTML = "Trigger was updated successfully.";
	}
	else 
	{
		objTriggerOutput.innerHTML = "Trigger update failed!";
	}
}

function debug_viewArray(arrData)
{
	var strData = "";
	for(strKey in arrData)
	{
		strData += strKey + "; " + arrData[strKey] + "\n";
	}
	return strData;
}