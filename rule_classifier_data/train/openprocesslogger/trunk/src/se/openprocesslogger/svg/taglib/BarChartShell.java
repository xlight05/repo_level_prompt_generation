package se.openprocesslogger.svg.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import se.openprocesslogger.svg.style.BarChartStyle;
import se.openprocesslogger.svg.style.Style;
import se.openprocesslogger.svg.utils.IAppendableSvg;
import se.openprocesslogger.svg.utils.StringAppender;

public class BarChartShell extends SvgTagTemplate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4256507261575540366L;
		
	protected void buildContent(JspWriter jspWriter) throws IOException{		
		StringAppender sb = new StringAppender();
		appendBarShell(sb, "barChart_", Style.defaultStyle.barChart);
		jspWriter.write(sb.toString());
	}

	public void appendBarShell(IAppendableSvg sb, String idStr, BarChartStyle chartStyle) {
		Style style = Style.defaultStyle;
		sb.openTag("g");
		sb.setAttribute("id", idStr);
		sb.setAttribute("visibility", "hidden");
		sb.closeOpenTag();
		
		sb.appendBaselinedIdText(-chartStyle.TEXT_MARGIN, chartStyle.HEIGHT/2, "-", "chart_currentValue_", "end", "middle");
		
		sb.appendSizedIdText(chartStyle.TITLE_X_DIST, -chartStyle.TITLE_Y_DIST,"Graph title"
				,style.titleTextSize, style.titleTextStyle, "chart_title_");
		
		// Time fields
		double x = 0;
		double y = chartStyle.TEXT_Y_DIST;
		sb.appendAnchoredIdText(x, y, "-","chart_startTime_", "begin");
		x += chartStyle.WIDTH/2;
		sb.appendAnchoredIdText(x, y, "xx:xx:xx", "chart_currentTime_", "center");		
		x += chartStyle.WIDTH/2;
		sb.appendAnchoredIdText(x, y, "-","chart_endTime_", "end");
				
		sb.openTag("g");
		sb.setAttribute("id", "chart_rects_");
		sb.closeOpenTag();
		sb.appendEndTag("g");
		sb.appendEndTag("g");
	}

}
