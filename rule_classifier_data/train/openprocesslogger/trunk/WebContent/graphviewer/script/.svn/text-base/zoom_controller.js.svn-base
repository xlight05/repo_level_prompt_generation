
function zoom_controller_setZoomLevel(zoomlevel){
  parent.graphviewer_parent_setZoomLevel(zoomlevel);
}

function zoom_controller_setSquareClickable(squareNbr){
  document.getElementById("zoom_controller"+squareNbr).setAttribute("opacity", "1");
  document.getElementById("zoomText"+squareNbr).setAttribute("opacity", "1");
}

function zoom_controller_setSquareExisting(squareNbr){
  document.getElementById("zoom_controller"+squareNbr).setAttribute("opacity", "0.2");
  document.getElementById("zoomText"+squareNbr).setAttribute("opacity", "0.2");
}

function zoom_controller_setSquareVisible(squareNbr){
  document.getElementById("zoom_controller"+squareNbr).setAttribute("visibility", "visible");
  document.getElementById("zoomText"+squareNbr).setAttribute("visibility", "visible");
}

function zoom_controller_viewOverview(level){
  for(var i=1; i<=3; i++){
  	document.getElementById("overviewSquare"+i).setAttribute("opacity", "1");
  }
  document.getElementById("overviewSquare"+level).setAttribute("opacity", "0.2");
  parent.graphviewer_parent_viewOverview(level);
}

function zoom_controller_onLoad(evt){
	alert("loaded");
}
