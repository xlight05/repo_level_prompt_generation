package se.openprocesslogger.svg.utils.graph;


import java.io.Serializable;

import se.openprocesslogger.svg.utils.IAppendableSvg;


public class GraphBuilder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8690343742946319717L;
	
	// Contstant settings
	protected double yOffset = 30;
	protected double normalWidth = 1300;
	protected double normalHeight = 350;
	protected double xOffset = 200;
	protected double yTextField = 20;
	protected double yButtonField = 50;
	public double markerWidth = 1;
	protected double margin = yTextField+yButtonField+10;
	
	public String lineStyle = "stroke:red; stroke-width: 1; fill : none;";
	public String axesStyle = "stroke:blue; stroke-width: 1; fill : none;";
	public String textStyle = "font-family:Verdana;font-size:10";
	public String markerStyle = "fill: black;";
	public String zoomMarkerStyle = "fill: red;";
	public String resetButtonStyle = "fill:red;stroke:black;stroke-width:5;opacity:0.5";
	
	protected double yCalcOffset = 10;
	protected double xScalingFactor = 1;
	protected double yScalingFactor = 1;
	protected double maxValue = -Double.MAX_VALUE;
	protected double minValue = Double.MAX_VALUE;
	protected double[] data = null;
	
	protected double zeroLevel = 10;

	public GraphBuilder(double[] data){
		this.data = data;
		scaleToFit();
	}

	public void appendCompleteSvg(IAppendableSvg sb){
		//appendStart(sb);
		appendGraph(sb);
		//appendEnd(sb);
	}
	
	public void appendGraph(IAppendableSvg sb){
		appendAxes(sb);
		appendPolyline(sb);
		appendMarker(sb);
		appendTimeFields(sb);
		appendZoomMarker(sb);
		appendResetText(sb);
		appendZoomText(sb);
	}
	
	protected void scaleToFit(){
		double ySpan = getSpan();
		double xSpan = data.length-1;
		
		xScalingFactor = (normalWidth - margin - xOffset)/xSpan;
		yScalingFactor = (normalHeight - margin - yOffset)/ySpan;
		yCalcOffset = yOffset+ySpan*yScalingFactor;
		zeroLevel = yOffset + (ySpan+minValue)*yScalingFactor;
	}
	
	protected double getSpan(){
		maxValue = -Double.MAX_VALUE;
		minValue = Double.MAX_VALUE; 
		
		for(double d : data){
			if(d > maxValue) maxValue = d;
			if(d < minValue) minValue = d;
		}
		return(maxValue - minValue);
	}
	
	public void appendPolyline(IAppendableSvg sb) {
		sb.append("\n<polyline id=\"polyline0\" visibility=\"visible\" points=\"");
		sb.append(getPolylinePoints());
		sb.append("\" \n style=\"").append(lineStyle).append("\" />\n");
		
		sb.append("\n<polyline id=\"polylineZoom\" visibility=\"hidden\" points=\"\"\n style=\"").append(lineStyle).append("\" />\n");
		
	}

	public String getPolylinePoints(){
		StringBuilder sb = new StringBuilder();
		double x = 0;
		double y = 0;
		for(int i=0; i<data.length; i++){
			x = xOffset + (i*xScalingFactor);
			//y = yCalcOffset - (data[i]*yScalingFactor);
			y = zeroLevel - (data[i]*yScalingFactor);
			sb.append(x).append(" ").append(y);
			if(i<data.length-1)
				sb.append(",");
			if(i%10 == 0) sb.append("\n");
		}
		return sb.toString();
	}
	
	public void appendStart(IAppendableSvg sb){
		sb.appendLine("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.0//EN\"")
		.appendLine("\"http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd\">")
		//sb.appendLine("<?xml version=\"1.0\"?>")		
		.appendLine("<svg xmlns=\"http://www.w3.org/2000/svg\"")
		.appendLine("xmlns:xlink=\"http://www.w3.org/1999/xlink\"")
		.appendLine("width='").append(normalWidth).append("px' height='").append(normalHeight).append("px'>");
	}
	
	public void appendEnd(IAppendableSvg sb){
		sb.appendLine("</svg>");
	}
	
	public void appendAxes(IAppendableSvg sb){
		double x1 = xOffset;
		double y1 = yOffset;
		double y2 = yCalcOffset;
		sb.appendLine("<line x1=\"" +x1 +"\" y1=\"" +y1 +"\" x2=\"" +x1 +"\" y2=\"" +y2 +"\" ");
		sb.appendLine("style=\"").append(axesStyle).append("\" />\n");
		double x2 = xOffset+(data.length-1)*xScalingFactor;
		sb.appendLine("<line x1=\"" +x1 +"\" y1=\"" +y2 +"\" x2=\"" +x2 +"\" y2=\"" +y2 +"\" ");
		sb.appendLine("style=\"").append(axesStyle).append("\" />\n");
		
		sb.appendLine("<text x=\"" +(x1-3) +"\" y=\"" +y1 + "\" style=\"").append(textStyle).append("\" text-anchor=\"end\" id=\"currentMaxValue\">"+maxValue+"</text>");
		sb.appendLine("<text x=\"" +(x1-3) +"\" y=\"" +y2 + "\" style=\"").append(textStyle).append("\" text-anchor=\"end\" id=\"currentMinValue\">"+minValue+"</text>");
		y2 = (yCalcOffset-yOffset)/2 + yOffset;
		sb.appendLine("<text x=\"" +(x1-3) +"\" y=\"" +y2 + "\" style=\"").append(textStyle).append("\" text-anchor=\"end\" id=\"currentValue\">x</text>");
	}

	public void appendTimeFields(IAppendableSvg sb){
		double x = xOffset;
		double y = yCalcOffset+yTextField ;
		sb.append("  	<text id=\"startTime\" x=\"").append(x)
		.append("\" y=\"").append(y)
		.append("\">x</text>\n");
		
		x += data.length*xScalingFactor/2;
		
		sb.append(" 	<text text-anchor=\"middle\" id=\"currentTime\" x=\"").append(x)
		.append("\" y=\"").append(y)
		.append("\">xx:xx:xx</text>\n");
		
		x = xOffset + data.length*xScalingFactor;
		
		sb.append(" 	<text text-anchor=\"end\" id=\"endTime\" x=\"").append(x)
		.append("\" y=\"").append(y)
		.append("\">xxxx</text>\n");
	}
	
	public void appendZoomText(IAppendableSvg sb){
		double x = xOffset + 33;
		double y = yCalcOffset+yTextField +yButtonField;
		sb.append("  	<text id=\"zoomText\" x=\"").append(x)
		.append("\" y=\"").append(y)
		.append("\">-</text>\n");
	}
	public void appendResetText(IAppendableSvg sb){
		sb.append("<rect id=\"xLeftMarker\" x=\"").append(xOffset).append("\" y=\"").append(yCalcOffset+yTextField+5)
		.append("\" width=\"").append(20)
		.append("\" height=\"").append(20)
		.append("\" visibility=\"visible\" onclick=\"resetZoom()\"")
		.append(" style=\"").append(this.resetButtonStyle).append("\"/>");
	}
	
	public void appendMarker(IAppendableSvg sb){
		double markerHeight = yCalcOffset-yOffset;
		sb.append("<rect id=\"xMarker\" x=\"0\" y=\"").append(yOffset)
		.append("\" width=\"").append(markerWidth)
		.append("\" height=\"").append(markerHeight)
		.append("\" visibility=\"hidden\"")
		.append(" style=\"").append(markerStyle).append("\"/>");
	}
	
	public void appendZoomMarker(IAppendableSvg sb){
		double markerHeight = yCalcOffset-yOffset;
		sb.append("<rect id=\"xLeftMarker\" x=\"0\" y=\"").append(yOffset)
		.append("\" width=\"").append(markerWidth)
		.append("\" height=\"").append(markerHeight)
		.append("\" visibility=\"hidden\"")
		.append(" style=\"").append(zoomMarkerStyle).append("\"/>");
	}
	
	public ChartProperties exportProperties(){
		ChartProperties p = new ChartProperties();
		p.setChartHeight(yCalcOffset-yOffset);
		p.setChartStart(xOffset);
		p.setEventWidth((data.length-1)*xScalingFactor);
		p.setXScalingFactor(xScalingFactor);
		return p;
	}
	public double getGraphWidth(){
		return data.length*xScalingFactor;
	}
	
	public void setXOffset(double offset) {
		xOffset = offset;
		scaleToFit();
	}

	public double getYOffset() {
		return yOffset;
	}

	public void setYOffset(double offset) {
		yOffset = offset;
		scaleToFit();
	}

	public double getMargin() {
		return margin;
	}

	public void setMargin(double margin) {
		this.margin = margin;
		scaleToFit();
	}

	public double getNormalWidth() {
		return normalWidth;		
	}

	public void setNormalWidth(double normalWidth) {
		this.normalWidth = normalWidth;
		scaleToFit();
	}

	public double getNormalHeight() {
		return normalHeight;
	}

	public void setNormalHeight(double normalHeight) {
		this.normalHeight = normalHeight;
		scaleToFit();
	}

	public String getLineStyle() {
		return lineStyle;
	}

	public void setLineStyle(String lineStyle) {
		this.lineStyle = lineStyle;
	}

	public String getAxesStyle() {
		return axesStyle;
	}

	public void setAxesStyle(String axesStyle) {
		this.axesStyle = axesStyle;
	}
	
	public double[] getData() {
		return data;
	}

	public void setData(double[] data) {
		this.data = data;
		scaleToFit();
	}
}
