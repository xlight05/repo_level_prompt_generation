package se.openprocesslogger.svg.utils;

import java.io.Serializable;
import java.util.Map;

public interface IAppendableSvg extends Serializable {

	public IAppendableSvg append(int x);
	public IAppendableSvg append(double x);
	public IAppendableSvg append(String string);
	public IAppendableSvg appendLine(String s);
	
	public IAppendableSvg appendText(double x, double y, String text);
	public IAppendableSvg appendIdText(double x, double y, String text, String id);
	public IAppendableSvg appendAnchoredText(double x, double y, String text, String textAnchor);
	public IAppendableSvg appendAnchoredIdText(double x, double y, String text,String id, String textAnchor);
	public IAppendableSvg appendBaselinedIdText(double x, double y, String text, String id, String textAnchor, String dominantBaseline);
	public IAppendableSvg appendOnclickText(double x, double y, String text, String id, String textAnchor, String visibility, String onclick);
	public IAppendableSvg appendSizedText(double x, double y, String text, String fontSize, String style);
	public IAppendableSvg appendSizedIdText(double x, double y, String text, String fontSize, String style, String id);
	public IAppendableSvg appendText(double x, double y, String text, Map<String, String> attributes);
	
	public IAppendableSvg appendLine(double x1, double x2, double y1,
			double y2, String style);
	public IAppendableSvg appendOnclickRect(double x, double y, double width,
			double height, String id, String style, String onclick, String visibility);
	public IAppendableSvg appendOpacityRect(double x, double y, double width,
			double height, String id, String style, String opacity, String visibility);
	public IAppendableSvg appendPolyline(String id, String visibility, String points, String lineStyle);
	public IAppendableSvg appendMarkerLine(double x1, double x2, double y1,
			double y2, String style, String markerStartUrl, String markerEndUrl);
	public IAppendableSvg appendMarkerIdLine(double x1, double x2, double y1,
			double y2, String id, String style, String markerStartUrl, String markerEndUrl);
	
	public IAppendableSvg appendStylishRect(double x, double y, double width, double height, String style);
	public IAppendableSvg openTag(String tag);
	public IAppendableSvg closeOpenTag();
	public IAppendableSvg setAttribute(String name, String value);
	public IAppendableSvg appendEndTag(String tag);
}
