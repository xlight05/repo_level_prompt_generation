package se.openprocesslogger.svg.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import se.openprocesslogger.svg.style.Style;
import se.openprocesslogger.svg.utils.StringAppender;

public class DigitalChartShell extends AnalogChartShell {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4256507261575540366L;
	
	
	protected void buildContent(JspWriter jspWriter) throws IOException{		
		StringAppender sb = new StringAppender();
		appendAxes(sb, "digitalChart_", Style.defaultStyle.digitalChart);
		jspWriter.write(sb.toString());
	}
}
