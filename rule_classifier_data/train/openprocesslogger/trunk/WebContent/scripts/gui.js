function formatDate(date,format) {
	format=format+"";
	var result="";
	var i_format=0;
	var c="";
	var token="";
	var y=date.getYear()+"";
	var M=date.getMonth()+1;
	var d=date.getDate();
	var E=date.getDay();
	var H=date.getHours();
	var m=date.getMinutes();
	var s=date.getSeconds();
	var yyyy,yy,MMM,MM,dd,hh,h,mm,ss,ampm,HH,H,KK,K,kk,k;
	// Convert real date parts into formatted versions
	var value=new Object();
	if (y.length < 4) {y=""+(y-0+1900);}
	value["y"]=""+y;
	value["yyyy"]=y;
	value["yy"]=y.substring(2,4);
	value["M"]=M;
	value["MM"]=LZ(M);
	value["MMM"]=MONTH_NAMES[M-1];
	value["NNN"]=MONTH_NAMES[M+11];
	value["d"]=d;
	value["dd"]=LZ(d);
	value["E"]=DAY_NAMES[E+7];
	value["EE"]=DAY_NAMES[E];
	value["H"]=H;
	value["HH"]=LZ(H);
	if (H==0){value["h"]=12;}
	else if (H>12){value["h"]=H-12;}
	else {value["h"]=H;}
	value["hh"]=LZ(value["h"]);
	if (H>11){value["K"]=H-12;} else {value["K"]=H;}
	value["k"]=H+1;
	value["KK"]=LZ(value["K"]);
	value["kk"]=LZ(value["k"]);
	if (H > 11) { value["a"]="PM"; }
	else { value["a"]="AM"; }
	value["m"]=m;
	value["mm"]=LZ(m);
	value["s"]=s;
	value["ss"]=LZ(s);
	while (i_format < format.length) {
		c=format.charAt(i_format);
		token="";
		while ((format.charAt(i_format)==c) && (i_format < format.length)) {
			token += format.charAt(i_format++);
			}
		if (value[token] != null) { result=result + value[token]; }
		else { result=result + token; }
		}
	return result;
}

var g_objDialogContainer;
function gui_loading(strMessage)
{
	if(strMessage != "")
	{
		if(document.getElementById("gui_pageloader") == undefined)
		{
			var objMContainer = document.createElement("span");
			objMContainer.innerHTML = "<span ondblclick=\"gui_loading('');\">" + strMessage + "</span>";	// Delete on double click for debug purpose
			var objPContainer = document.createElement("div");
			objPContainer.setAttribute("id", "progresscontainer");
			objPContainer.setAttribute("style", "width: 300px;");
			var objPBContainer = document.createElement("div");
			
			var objDialog = new dijit.Dialog({id: "gui_pageloader"});
	
			objDialog.setContent(objPContainer);
			dojo.connect(objDialog, "hide", function() {gui_loading("");});
			objDialog.show();
			objPContainer.appendChild(objMContainer);
			objPContainer.appendChild(objPBContainer);
			
			var objProgressbar = new dijit.ProgressBar({id: "loadingbar", indeterminate: true}, objPBContainer);
			
			// Prevent dojo from closing the dialog on Escape keypress
			dojo.connect(objDialog.domNode, "onkeypress", function(e)
			{
				var key = e.keyCode || e.charCode;
				var k = dojo.keys;
				if (key == k.ESCAPE) {
					dojo.stopEvent(e);
				}
			});
			
			g_objDialogContainer = objDialog;
			return g_objDialogContainer;
		}
	}
	else
	{
		g_objDialogContainer.destroyRecursive();
	}
}

function removeObjectElement(arrData, strRemoveKey)
{
	var objNewData = new Object();		
	for(strKey in arrData)
	{
		if(strKey != strRemoveKey)
		{
			objNewData[strKey] = arrData[strKey];
		}
	}
	return objNewData;		
}

function gui_getLoaderText()
{
	return "<div class=\"loader\"><span>Loading...</span></div>";
}

function gui_setSelectedItem(strListItemName, intIndex, strIndexContainer)
{
	var objIndexContainer = dojo.byId(strIndexContainer);	
	if(objIndexContainer != null && objIndexContainer != undefined)
	{
		if(dojo.byId(strIndexContainer).innerHTML != "")
		{
			dojo.byId(strListItemName + dojo.byId(strIndexContainer).innerHTML).className = "";	
		}
		dojo.byId(strIndexContainer).innerHTML = intIndex;
		dojo.byId(strListItemName + intIndex).className = "selected";
	}
}

//Creates a new table an attatches it to a container, if the table exists remove it and recreate it.
function gui_createItemTable(strTableName, objContainer)
{
	var objTable = document.getElementById(strTableName);
	if(typeof(objTable) == "object")
	{
		objContainer.removeChild(objTable);
	}
	
	//var objItemTable = document.createElement("table");
	var objItemTable = document.createElement("div");
	objItemTable.setAttribute("id", strTableName);
	
	return objItemTable;
}

function gui_closeElement(objRef)
{
	var objElement = document.getElementById("alertfield");
	if(typeof(objElement) == "object")
	{
		objElement.removeChild(objRef);
	}
}

function gui_setHover(objRef, blnOver)
{
	if(objRef != undefined)
	{
		if(blnOver)
		{
			objRef.setAttribute("class", "message over");
		}
		else
		{
			objRef.setAttribute("class", "message");
		}
	}
}

function doAlert(strMessage, intType)
{
	var objOutput = document.getElementById("alertfield");
	
	if(typeof(objOutput) == "object")
	{
		switch(intType)
		{
			case 1:	/* Basic alert message */
				var objAlertField = document.createElement("div");
				objAlertField.setAttribute("class", "message");
				objAlertField.setAttribute("onClick", "gui_closeElement(this)");
				objAlertField.setAttribute("onMouseOver", "gui_setHover(this, true)");
				objAlertField.setAttribute("onMouseOut", "gui_setHover(this, false)");
				objAlertField.innerHTML += "<div>" + strMessage + " (click to confirm)</div>";
				objOutput.appendChild(objAlertField);
				break;
	
			case 2: /* Confirmation message will return true or false */
				return confirm(strMessage);
				break;
		}
	}
}

function doConfirm(blnConfirm)
{
	toggleElement(false);
	return blnConfirm;
}

function setTabName(strName)
{	
	var objTabSettings = dijit.byId("tabSettings");
	
	if(typeof(objTabSettings) == "object")
	{
		objTabSettings.controlButton.containerNode.innerHTML = strName || "";
	}
}

function setButtonName(strName)
{
	var objButton = dijit.byId("saveLogTaskButton")
	
	if(typeof(objButton) == "object")
	{
		objButton.setLabel(strName);
	}
}

function toggleTimer()
{
	var objTimer = document.getElementById("taskTimer");
	var objQuick = document.getElementById("taskQuickstart");
	
	if(typeof(objTimer) == "object" && typeof(objQuick) == "object")
	{
		if(objQuick.style.display == "none")
		{
			objTimer.style.display = "none";
			objQuick.style.display = "block";
		}
		else
		{
			objQuick.style.display = "none";		
			objTimer.style.display = "block";
		}
	}
}

function gui_cancelForm(blnSkipConfirm)
{
	blnConfirmed = false;

	if(blnSkipConfirm)
	{
		blnConfirmed = true;		
	}
	else
	{
		if(confirm("Are you sure you want to cancel?"))
		{
			blnConfirmed = true;
		}
	}
	
	if(blnConfirmed)
	{
		if(document.getElementById("trEquipmentID").style.display == "block")
		{
			setTabName("Templates");
			if(dojo.byId("logTaskIndex").innerHTML != "")
			{
				dojo.byId("templatepanelist" + dojo.byId("logTaskIndex").innerHTML).className = "";	
			}
		}
		else
		{
			setTabName("Tasks");
			if(dojo.byId("logTaskIndex").innerHTML != "")
			{
				dojo.byId("taskpanelist" + dojo.byId("logTaskIndex").innerHTML).className = "";	
			}

		}

		dojo.byId("trLogSettingsMessage").setAttribute("style", "display:block");
		dojo.byId("logTaskSettingsBody").setAttribute("style", "display:none");
		
		dijit.byId("logTaskName").setValue("");
		dijit.byId("loggingTaskInfo").setValue("");
		dijit.byId("dtbStart").setValue("");
		dijit.byId("dtbStartTime").setValue("");
		dijit.byId("dtbEnd").setValue("");
		dijit.byId("dtbEndTime").setValue("");
		dojo.byId("templateName").innerHTML = "";
		dojo.byId("logTaskId").innerHTML = "";
		dojo.byId("logTaskIndex").innerHTML = "";
	}
}

// Insert blanks spaces in long string to break them
function gui_splitString(strText, intLength)
{
	var arrWords = strText.split(" ");

	for(var intNum = 0; intNum < arrWords.length; intNum++)
	{
		if(arrWords[intNum].length > intLength)
		{
			var intCounter = 0;
			var strBuild = "";
			for(var i = 0; i < arrWords[intNum].length; i++)
			{
				strBuild += arrWords[intNum].substring(i, i + 1);
				intCounter++;
				if(intCounter == intLength)
				{
					strBuild += " ";
					intCounter = 0;
				}
			}
			arrWords[intNum] = strBuild.replace(/^\s+|\s+$/g, "");
		}
	}
	return arrWords.join(" ");
}
