// Wrapper for console.debug, don't use if undefined
function opl_debug(msg)
{
	if(console)
	{
		console.debug(msg);
	}
}

function opl_getUrl(){
	return "/OpenProcessLogger";
}

function opl_analyzeError(exception)
{
	if(exception.code == 591)
	{
		doAlert("Session has probably timed out.<br>\n Press Ctrl+R to reload page, or restart firefox.<br>\n If that does not help, restart the server.", 1);
	}
	else
	{
		var strMessage = "";
		if(exception.message.length > 0)
		{
			strMessage =  "<br>\nMessage: " + exception.message;
		}
		
		doAlert("Error code: " + exception.code + strMessage + "<br>\nName: " + exception.name.replace(/\n/g, "<br>"), 1);
	}
}