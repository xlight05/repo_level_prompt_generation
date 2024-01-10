package se.openprocesslogger.svg.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import se.openprocesslogger.svg.style.Style;
import se.openprocesslogger.svg.utils.IAppendableSvg;
import se.openprocesslogger.svg.utils.StringAppender;

public class MouseMarker extends SvgTagTemplate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4256507261575540366L;
		
	protected void buildContent(JspWriter jspWriter) throws IOException{		
		StringAppender sb = new StringAppender();
		appendMarker(sb);
		jspWriter.write(sb.toString());
	}

	public void appendMarker(IAppendableSvg sb) {
		Style style = Style.defaultStyle;
		sb.appendOnclickRect(0, 0, style.mouseMarker.markerWidth, 1, "xMarker", style.mouseMarker.markerStyle, "", "hidden");
		sb.appendOpacityRect(0, 0, 1, 1, "xOpacityMarker", style.mouseMarker.zoomMarkerStyle, ".1", "hidden");
		sb.appendOnclickRect(0, 0, style.mouseMarker.markerWidth, 0, "xLeftMarker", style.mouseMarker.markerStyle, "", "hidden");
	}

}
