package se.openprocesslogger.svg.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import se.openprocesslogger.svg.utils.IAppendableSvg;
import se.openprocesslogger.svg.utils.StringAppender;

public class Markers extends SvgTagTemplate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4256507261575540366L;
		
	protected void buildContent(JspWriter jspWriter) throws IOException{		
		StringAppender sb = new StringAppender();
		appendAxes(sb);
		jspWriter.write(sb.toString());
	}

	public void appendAxes(IAppendableSvg sb) {
		sb.append("<defs>\r\n");
		sb.append("<marker id=\"endArrow\" viewBox=\"0 0 10 10\" refX=\"1\" refY=\"5\" markerUnits=\"strokeWidth\" orient=\"auto\" markerWidth=\"7\" markerHeight=\"10\">\r\n");
		sb.append("	<polyline points=\"0,0 10,5 0,10 1,5\" fill=\"darkblue\" />\r\n");
		sb.append("</marker>\r\n");
		sb.append("<marker id=\"startArrow\" viewBox=\"0 0 10 10\" refX=\"1\" refY=\"5\" markerUnits=\"strokeWidth\" orient=\"auto\" markerWidth=\"7\" markerHeight=\"10\">\r\n");
		sb.append("	<polyline points=\"10,0 0,5 10,10 9,5\" fill=\"darkblue\" />\r\n");
		sb.append("</marker>\r\n");
		sb.append("<marker id=\"pointMarker\" viewBox = \"0 0 10 10\" refX = \"5\" refY = \"5\" markerUnits=\"strokeWidth\" markerWidth = \"2\" markerHeight = \"2\" fill = \"blue\" orient = \"auto\">\r\n");
		sb.append("	<circle cx = \"5\" cy = \"5\" r = \"5\"/>\r\n");
		sb.append("</marker>\r\n");
		sb.append("</defs>\r\n");
	}

}
