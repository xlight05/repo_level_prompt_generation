package se.openprocesslogger.svg.utils;

import java.util.Map;
import java.util.Map.Entry;


public abstract class SvgAppender implements IAppendableSvg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7432913131039275177L;
	public static String defaultTextStyle = "font-family:Helvetica, Arial, Verdana;";
	public static String defaultFontSize = "12";
	
	@Override
	public IAppendableSvg appendText(double x, double y, String text){
		append("<text ");
		x(x);
		y(y);
		style(defaultTextStyle);
		attr("font-size", defaultFontSize);
		append(">").append(text);
		append("</text>\r\n");
		return this;
	}
	
	@Override
	public IAppendableSvg appendIdText(double x, double y, String text, String id){
		append("<text ");
		baseAttributes(x, y, id);
		style(defaultTextStyle);
		attr("font-size", defaultFontSize);
		append(">").append(text);
		append("</text>\r\n");
		return this;
	}
	
	@Override
	public IAppendableSvg appendAnchoredText(double x, double y, String text, String textAnchor){
		append("<text ");
		x(x);
		y(y);
		style(defaultTextStyle);
		attr("font-size", defaultFontSize);
		attr("text-anchor", textAnchor);
		append(">").append(text);
		append("</text>\r\n");
		return this;
	}
	
	@Override
	public IAppendableSvg appendAnchoredIdText(double x, double y, String text, String id, String textAnchor){
		append("<text ");
		baseAttributes(x, y, id);
		style(defaultTextStyle);
		attr("font-size", defaultFontSize);
		attr("text-anchor", textAnchor);
		append(">").append(text);
		append("</text>\r\n");
		return this;
	}
	
	@Override
	public IAppendableSvg appendText(double x, double y, String text, Map<String, String> attributes) {
		append("<text ");
		x(x);
		y(y);
		for(Entry<String, String> a : attributes.entrySet()){
			attr(a.getKey(), a.getValue());
		}
		append(">").append(text);
		append("</text>\r\n");
		return this;
	}
	
	@Override
	public IAppendableSvg appendBaselinedIdText(double x, double y, String text,String id, String textAnchor, String dominantBaseline){
		append("<text ");
		baseAttributes(x, y, id);
		style(defaultTextStyle);
		attr("font-size", defaultFontSize);
		attr("text-anchor", textAnchor);
		attr("dominant-baseline", dominantBaseline);
		append(">").append(text);
		append("</text>\r\n");
		return this;
	}
	
	@Override
	public IAppendableSvg appendOnclickText(double x, double y, String text, String id, String textAnchor, String visibility, String onclick){
		append("<text ");
		baseAttributes(x, y, id);
		style(defaultTextStyle);
		attr("font-size", defaultFontSize);
		attr("text-anchor", textAnchor);
		attr("visibility", visibility);
		attr("onclick", onclick);
		append(">").append(text);
		append("</text>\r\n");
		return this;
	}
	private void baseAttributes(double x, double y, String id){
		attr("x",x);
		attr("y",y);
		attr("id",id);
	}
	
	@Override
	public IAppendableSvg appendSizedText(double x, double y, String text, String fontSize, String style) {
		append("<text ");
		x(x);
		y(y);
		style(style);
		attr("font-size", fontSize);
		append(">").append(text);
		append("</text>\r\n");
		return this;
	}

	@Override
	public IAppendableSvg appendSizedIdText(double x, double y, String text, String fontSize, String style, String id) {
		append("<text ");
		id(id);
		x(x);
		y(y);
		style(style);
		attr("font-size", fontSize);
		append(">").append(text);
		append("</text>\r\n");
		return this;
	}
	
	private void attr(String name, String val){
		append(name).append("=\"").append(val).append("\" ");
	}
	private void attr(String name, double val){
		append(name).append("=\"").append(val).append("\" ");
	}
	
	@Override
	public IAppendableSvg appendLine(double x1, double x2, double y1, double y2, String style){
		append("<line ");
		attr("x1",x1);
		attr("y1",y1);
		attr("x2",x2);
		attr("y2",y2);
		style(style);
		append("/>\r\n");
		return this;
	}
	
	@Override
	public IAppendableSvg appendMarkerLine(double x1, double x2, double y1, double y2, String style, String markerStartUrl, String markerEndUrl){
		append("<line ");
		attr("x1",x1);
		attr("y1",y1);
		attr("x2",x2);
		attr("y2",y2);
		style(style);
		if(markerStartUrl != null && markerStartUrl != "" )
			attr("marker-start","url(#"+markerStartUrl+")");
		if(markerEndUrl != null && markerEndUrl != "" )
			attr("marker-end","url(#"+markerEndUrl+")");
		append("/>\r\n");
		return this;
	}
	
	@Override
	public IAppendableSvg appendMarkerIdLine(double x1, double x2, double y1, double y2,
			String id, String style, String markerStartUrl, String markerEndUrl) {
		append("<line ");
		attr("x1",x1);
		attr("y1",y1);
		attr("x2",x2);
		attr("y2",y2);
		id(id);
		style(style);
		if(markerStartUrl != null && markerStartUrl != "" )
			attr("marker-start","url(#"+markerStartUrl+")");
		if(markerEndUrl != null && markerEndUrl != "" )
			attr("marker-end","url(#"+markerEndUrl+")");
		append("/>\r\n");
		return this;
	}

	@Override
	public IAppendableSvg appendOnclickRect(double x, double y, double width, double height, String id, String style, String onclick, String visibility){
		append("<rect ");
		x(x);
		y(y);
		id(id);
		style(style);
		attr("width", width);
		attr("height", height);
		attr("visibility", visibility);
		attr("onclick", onclick);
		append("/>\r\n");
		return this;
	}
	
	@Override
	public IAppendableSvg appendOpacityRect(double x, double y, double width, double height, String id, String style, String opacity, String visibility){
		append("<rect ");
		x(x);
		y(y);
		id(id);
		style(style);
		attr("width", width);
		attr("height", height);
		attr("visibility", visibility);
		attr("opacity", opacity);
		append("/>\r\n");
		return this;
	}
	
	@Override
	public IAppendableSvg appendPolyline(String id, String visibility, String points, String lineStyle){
		append("<polyline ");
		id(id);
		style(lineStyle);
		attr("visibility",visibility);
		attr("points",points);		
		append(" />\r\n");
		return this;
	}
	
	@Override
	public IAppendableSvg appendStylishRect(double x, double y, double width,
			double height, String style) {
		append("<rect ");
		x(x);
		y(y);
		attr("width", width);
		attr("height", height);
		style(style);		
		append(" />\r\n");
		return this;
	}

	@Override
	public IAppendableSvg openTag(String tag){
		append("<").append(tag).append(" ");
		return this;
	}

	@Override
	public IAppendableSvg closeOpenTag(){ 
		append(">");
		return this;
	}
	@Override
	public IAppendableSvg appendEndTag(String tag){
		append("</").append(tag).append(">\r\n");
		return this;
	}
	
	@Override
	public IAppendableSvg setAttribute(String name, String value){
		attr(name, value);
		return this;
	}
	
	IAppendableSvg x(double x){
		attr("x", x);
		return this;
	}
	IAppendableSvg y(double y){
		attr("y", y);
		return this;
	}
	IAppendableSvg style(String style){
		attr("style", style);
		return this;
	}
	IAppendableSvg id(String id) {
		attr("id",id);
		return this;
	}
}
