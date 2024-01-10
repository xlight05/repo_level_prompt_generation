// highlight the corect tab
// this is such a cheat, make this better
// this is based on url... look up existing menu items and then find a match
function change(newClass, pageURI) {
	var url = window.location
	var identity = null;

	// alert (replaceChars(document.title));
	
	if (pageURI != null) {
		identity = document.getElementById("topNav_" + extractTopLevelLocation(pageURI));
	}
	
	if (identity != null) {
		identity.className=newClass;	
	}
}

// uses regular experssions to get the 3rd word in the pageURI			
function extractTopLevelLocation(pageURI) {
	
	var match = /[/]\w{1,}[/]\w{1,}[/](\w{1,})[/]/ .exec(pageURI);
	return match[1];
}

// ========== 

// nolonger used here,
// todo , make it nicer and then put into javascript toolbox
function replaceChars(entry) {
	var out = " "; // replace this
	var add = "_"; // with this
	var temp = "" + entry; // temporary holder
	var pos = null;
	while (temp.indexOf(out)>-1) {
		pos = temp.indexOf(out);
		temp = "" + (temp.substring(0, pos) + add + temp.substring((pos + out.length), temp.length));
	}
	return temp;
}