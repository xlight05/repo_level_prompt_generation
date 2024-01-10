function graphviewer_parent_setZoomLevel(level){
	self.chart1.zoomevents_setZoomLevel(level);
}

function graphviewer_parent_setSquareClickable(squareNbr){
	self.square1.zoom_controller_setSquareClickable(squareNbr);
}

function graphviewer_parent_setSquareExisting(squareNbr){
	self.square1.zoom_controller_setSquareExisting(squareNbr);
}

function graphviewer_parent_setSquareVisible(squareNbr){
   self.square1.zoom_controller_setSquareVisible(squareNbr);
}

function graphviewer_parent_viewOverview(level){
  self.chart1.zoomevent_viewOverview(level);
}
