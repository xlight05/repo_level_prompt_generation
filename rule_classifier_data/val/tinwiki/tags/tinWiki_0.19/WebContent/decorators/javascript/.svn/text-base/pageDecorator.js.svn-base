


function addEditLinks(contextPath, fileName) {
	// Create or reuse TOC div
	var toc = document.createElement("div");
	toc.setAttribute('id','toc');
	document.body.insertBefore(toc,document.body.firstChild)    
	
	
	var currNode = null;
	var mouseOver = null;
	var mouseOut = null;
	
	// Find all divs in document.
	sections = document.getElementsByTagName('div');
	for ( var i = 0; i < sections.length; i++ ) {
		// Skip non-section divs
		if (sections[i].id.indexOf('section') != 0) { 
			continue;
		}
		
		// Significance to the document.

		currNode = sections[i].firstChild;		
		while (currNode) {
			if (currNode.tagName && currNode.tagName.charAt(0) == 'H' && currNode.tagName.charAt(1) != 'R') {
				section_significance = parseInt(currNode.tagName.charAt(1));
				section_title = currNode.innerHTML; // Not sure why innerText doesn't work here... but whatever
				break;
			}
			currNode = currNode.nextSibling;
		}
		
		mouseOver = 'onmouseover="highLightEditableArea(';
		mouseOver += "'" + sections[i].id + "'";
		mouseOver += ');"';
		
		mouseOut = 'onmouseout="unHighLightEditableArea(';
		mouseOut += "'" + sections[i].id + "'";
		mouseOut += ');"';
				
		// Add edit link to section.
		sections[i].innerHTML='<a class="edit" ' + mouseOver + ' ' + mouseOut + 'href="' + contextPath + '/web/cmsEdit/editWebPageInput.do?sectionName='+sections[i].id+'&fileName='+fileName+'">[edit]</a>'+ sections[i].innerHTML;
		
	}
}

// write more elegant solutions later on
function highLightEditableArea (areaID) {
	var myAreaID;
	var pTags;
	myAreaID = document.getElementById(areaID);
	myAreaID.className = "highLightEditArea";
	
	pTags = myAreaID.getElementsByTagName("p");	
	for (var i=0; i<pTags.length; i++) {
		pTags[i].className = "highLighter";
	}

	for (var c=0; c<4; c++) {
		hTags = myAreaID.getElementsByTagName("h"+c);
		for (var i=0; i<hTags.length; i++) {
			hTags[i].className = "highLighter";
		}
	}

	lTags = myAreaID.getElementsByTagName("li");	
	for (var i=0; i<lTags.length; i++) {
		lTags[i].className = "highLighter";
	}

}

function unHighLightEditableArea (areaID) {
	var myAreaID;
	myAreaID = document.getElementById(areaID);
	myAreaID.className = "unHighLightEditArea";

	pTags = myAreaID.getElementsByTagName("p");
	for (var i=0; i<pTags.length; i++) {
		pTags[i].className = "clearHighLighter";
	}

	for (var c=0; c<4; c++) {
		hTags = myAreaID.getElementsByTagName("h"+c);
		for (var i=0; i<hTags.length; i++) {
			hTags[i].className = "clearHighLighter";
		}
	}

	lTags = myAreaID.getElementsByTagName("li");
	for (var i=0; i<lTags.length; i++) {
		lTags[i].className = "clearHighLighter";
	}
	
}


// Adds Div Insertion link
function addAddLinks(contextPath, fileName) {
	// Create or reuse TOC div
	var toc = document.createElement("div");
	toc.setAttribute('id','toc');
	document.body.insertBefore(toc,document.body.firstChild)    
	
	
	var currNode = null;
	var mouseOver = null;
	var mouseOut = null;
	
	// Find all divs in document.
	sections = document.getElementsByTagName('div');
	for ( var i = 0; i < sections.length; i++ ) {
		// Skip non-section divs
		if (sections[i].id.indexOf('section') != 0) { 
			continue;
		}
		
		// Significance to the document.

		currNode = sections[i].firstChild;		
		while (currNode) {
			if (currNode.tagName && currNode.tagName.charAt(0) == 'H' && currNode.tagName.charAt(1) != 'R') {
				section_significance = parseInt(currNode.tagName.charAt(1));
				section_title = currNode.innerHTML; // Not sure why innerText doesn't work here... but whatever
				break;
			}
			currNode = currNode.nextSibling;
		}
		
		mouseOver = 'onmouseover="highLightAddableArea(';
		mouseOver += "'" + sections[i].id + "'";
		mouseOver += ');"';
		
		mouseOut = 'onmouseout="unHighLightAddableArea(';
		mouseOut += "'" + sections[i].id + "'";
		mouseOut += ');"';
				
		// Add insert section link.
		sections[i].innerHTML='<a class="add" ' + mouseOver + ' ' + mouseOut + 'href="' + contextPath + '/web/addSection/addSection.do?sectionName='+sections[i].id+'&fileName='+fileName+'">[insert section <img alt="above" src="'+ contextPath +'/decorators/images/newTheme/butt_addabove.png" />]</a>'+ sections[i].innerHTML;
	}
	// Adding insert section link on the bottom of the page
	sections[sections.length-2].innerHTML = sections[sections.length-2].innerHTML + '<a class="add" href="' + contextPath + '/web/addSection/addSection.do?sectionName='+'lastsection'+'&fileName='+fileName+'">[insert section <img alt="below" src="'+ contextPath +'/decorators/images/newTheme/butt_addbelow.png" />]</a>';

}
function highLightAddableArea (areaID) {
	var myAreaID;
	var pTags;
	myAreaID = document.getElementById(areaID);
	myAreaID.className = "highLightEditAreaAdd";
	
	pTags = myAreaID.getElementsByTagName("p");	
	for (var i=0; i<pTags.length; i++) {
		pTags[i].className = "highLighterAdd";
	}

	for (var c=0; c<4; c++) {
		hTags = myAreaID.getElementsByTagName("h"+c);
		for (var i=0; i<hTags.length; i++) {
			hTags[i].className = "highLighterAdd";
		}
	}

	lTags = myAreaID.getElementsByTagName("li");	
	for (var i=0; i<lTags.length; i++) {
		lTags[i].className = "highLighterAdd";
	}

}

function unHighLightAddableArea (areaID) {
	var myAreaID;
	myAreaID = document.getElementById(areaID);
	myAreaID.className = "unHighLightEditArea";

	pTags = myAreaID.getElementsByTagName("p");
	for (var i=0; i<pTags.length; i++) {
		pTags[i].className = "clearHighLighter";
	}

	for (var c=0; c<4; c++) {
		hTags = myAreaID.getElementsByTagName("h"+c);
		for (var i=0; i<hTags.length; i++) {
			hTags[i].className = "clearHighLighter";
		}
	}

	lTags = myAreaID.getElementsByTagName("li");
	for (var i=0; i<lTags.length; i++) {
		lTags[i].className = "clearHighLighter";
	}
	
}