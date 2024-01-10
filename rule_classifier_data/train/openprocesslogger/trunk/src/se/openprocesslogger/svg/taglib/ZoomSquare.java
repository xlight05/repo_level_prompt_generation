package se.openprocesslogger.svg.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import se.openprocesslogger.svg.style.Style;
import se.openprocesslogger.svg.style.ZoomSquareStyle;
import se.openprocesslogger.svg.utils.IAppendableSvg;
import se.openprocesslogger.svg.utils.StringAppender;

public class ZoomSquare extends SvgTagTemplate {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1166138369937455955L;

	@Override
	protected void buildContent(JspWriter out) throws IOException {
		out.write(getSvg(Style.defaultStyle.getZoomSquare()));
	}

	public String getSvg(ZoomSquareStyle style) {
		StringAppender sb = new StringAppender();
		
		sb.openTag("g ");
		sb.setAttribute("id", "zoom_panel");
		sb.setAttribute("visibility", "visible");
		sb.closeOpenTag();
		appendZoomSquares(sb, style);
		appendOverviewButtons(sb, style);
		sb.appendEndTag("g");
		
		return sb.toString();
	}
	
	public void appendZoomSquares(IAppendableSvg sb, ZoomSquareStyle style){
		//double x = style.getX0Offset() + style.getX0Offset();
		double x = 3*(style.getOverviewbuttonWidth()+style.getButtonDist())+style.getButtonDist()*2;
		double y = style.getButtonOffset();
		
		for(int i=0; i<ZoomSquareStyle.ZOOM_LEVELS; i++){
			sb.appendOnclickRect(x, y, style.getButtonWidth(), style.getButtonWidth(), "zoom_controller"+i, style.getZoomButtonsStyle(), "zoom_controller_setZoomLevel("+i+")","hidden");
			sb.appendOnclickText(x+6, y+15, ""+i,"zoomText"+i, "begin", "hidden","zoom_controller_setZoomLevel("+i+")");
			x += style.getButtonDist()+style.getButtonWidth();
		}
	}
	
	public void appendOverviewButtons(IAppendableSvg sb, ZoomSquareStyle style){
		double x = style.getButtonDist();//style.getX0Offset() + style.getX0Offset() + ZoomSquareStyle.ZOOM_LEVELS*(style.getButtonDist()+style.getButtonWidth());
		double y = style.getButtonOffset();
		
		sb.appendOnclickRect(x, y, style.getOverviewbuttonWidth(), style.getButtonWidth(), "overviewSquare1", style.getZoomButtonsStyle(), "zoom_controller_viewOverview(1)","visible");
		sb.appendOnclickText(x+4, y+15, "100%","overviewText1", "begin", "visible","zoom_controller_viewOverview(1)");
		x += style.getButtonDist()+style.getOverviewbuttonWidth();
		
		sb.appendOnclickRect(x, y, style.getOverviewbuttonWidth(), style.getButtonWidth(), "overviewSquare2", style.getZoomButtonsStyle(), "zoom_controller_viewOverview(2)","visible");
		sb.appendOnclickText(x+7, y+15, "50%","overviewText2", "begin", "visible","zoom_controller_viewOverview(2)");
		x += style.getButtonDist()+style.getOverviewbuttonWidth();
		
		sb.appendOnclickRect(x, y, style.getOverviewbuttonWidth(), style.getButtonWidth(), "overviewSquare3", style.getZoomButtonsStyle(), "zoom_controller_viewOverview(3)","visible");
		sb.appendOnclickText(x+7, y+15, "20%","overviewText3", "begin", "visible","zoom_controller_viewOverview(3)");
		x += style.getButtonDist()+style.getOverviewbuttonWidth();
	}
}
