package se.openprocesslogger.svg.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import se.openprocesslogger.svg.style.AnalogChartStyle;
import se.openprocesslogger.svg.style.Style;
import se.openprocesslogger.svg.utils.IAppendableSvg;
import se.openprocesslogger.svg.utils.StringAppender;

public class AnalogStepChartShell extends SvgTagTemplate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4256507261575540366L;
		
	protected void buildContent(JspWriter jspWriter) throws IOException{		
		StringAppender sb = new StringAppender();
		appendAxes(sb, "analogChart_", Style.defaultStyle.analogChart);
		jspWriter.write(sb.toString());
	}

	public void appendAxes(IAppendableSvg sb, String idStr, AnalogChartStyle chartStyle) {
		Style style = Style.defaultStyle;
		
		double x1 = 0;
		double y1 = -chartStyle.VALUE_HEIGHT;
		double y2 = 0;
		double x2 = chartStyle.WIDTH;
		double yMiddle = y1 + chartStyle.VALUE_HEIGHT/2;
		
		sb.openTag("g");
		sb.setAttribute("id", idStr);
		sb.setAttribute("visibility", "hidden");
		sb.closeOpenTag();
		
		// Axes
		sb.appendMarkerLine(x1, x1, y1-chartStyle.ARROW_SPACE, y2+chartStyle.ARROW_SPACE, style.chartAxesStyle,"startArrow", null);
		sb.appendMarkerLine(x1, x2, y2+chartStyle.ARROW_SPACE, y2+chartStyle.ARROW_SPACE, style.chartAxesStyle, null, "endArrow");
		sb.appendLine(x1-chartStyle.VAL_CROSS_LINE, x1+chartStyle.VAL_CROSS_LINE, y1, y1, style.valLineStyle);
		sb.appendLine(x1-chartStyle.VAL_CROSS_LINE, x1+chartStyle.VAL_CROSS_LINE, y2, y2, style.valLineStyle);
		
		// Value fields
		sb.appendBaselinedIdText(x1-chartStyle.TEXT_X_DIST, y1, "xx.xx","chart_currentMaxValue_", "end", "middle");
		sb.appendBaselinedIdText(x1-chartStyle.TEXT_X_DIST, y2, "xx.xx","chart_currentMinValue_", "end", "middle");
		sb.appendBaselinedIdText(x1-chartStyle.TEXT_X_DIST, yMiddle, "", "chart_currentValue_", "end", "middle");
		
		// Time fields
		double x = 0;
		double y = chartStyle.TEXT_Y_DIST;
		sb.appendAnchoredIdText(x, y, "-","chart_startTime_", "begin");
		x += chartStyle.WIDTH/2;
		sb.appendAnchoredIdText(x, y, "xx:xx:xx", "chart_currentTime_", "center");		
		x += chartStyle.WIDTH/2;
		sb.appendAnchoredIdText(x, y, "-","chart_endTime_", "end");
		
		// Title field
		sb.appendSizedIdText(chartStyle.TITLE_X_DIST, y1-chartStyle.TITLE_Y_DIST,"Graph title"
				,style.titleTextSize, style.titleTextStyle, "chart_title_");
		
		// Polyline
		sb.appendPolyline("chart_polyline_", "visible", "", style.lineStyle);
		
		sb.appendEndTag("g");
	}
}
