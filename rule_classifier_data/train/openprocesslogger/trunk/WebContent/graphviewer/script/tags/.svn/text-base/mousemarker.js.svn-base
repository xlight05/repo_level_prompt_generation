function mouseMarker_init(height){
	var xMarker = SVG_DOC.getElementById('xMarker');
	xMarker.setAttribute("y", SVG_STYLE.analogChart.MARGIN_TOP);
	xMarker.setAttribute("height", height);
	
	var xLeftMarker = SVG_DOC.getElementById('xLeftMarker');
	xLeftMarker.setAttribute("y", SVG_STYLE.analogChart.MARGIN_TOP);
	xLeftMarker.setAttribute("height", height);
	
	var xOpacityMarker = SVG_DOC.getElementById('xOpacityMarker');
	xOpacityMarker.setAttribute("y", SVG_STYLE.analogChart.MARGIN_TOP);
	xOpacityMarker.setAttribute("height", height);
}

function mouseMarker_showMarker(x){    
	var xMarker = SVG_DOC.getElementById('xMarker');
	xMarker.setAttribute('x', x);
	xMarker.setAttribute('visibility', "visible");
	
	var xLeftMarker = SVG_DOC.getElementById('xLeftMarker');
	if(xLeftMarker.getAttribute('visibility') == 'visible'){
		var xOpacityMarker = SVG_DOC.getElementById('xOpacityMarker');
		var xStart = xLeftMarker.getAttribute('x');
		var width  = Math.abs(xStart-x);
		if(xStart > x){
			xOpacityMarker.setAttribute('x', x);
		}
		else{
			xOpacityMarker.setAttribute('x', xStart);
		}
		xOpacityMarker.setAttribute('width', width);
	}
}

function mouseMarker_hideMarker(){
  var xMarker = SVG_DOC.getElementById('xMarker');
  xMarker.setAttribute('visibility', "hidden");
}
