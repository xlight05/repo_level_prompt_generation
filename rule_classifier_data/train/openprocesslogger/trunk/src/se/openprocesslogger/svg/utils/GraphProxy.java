package se.openprocesslogger.svg.utils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import se.openprocesslogger.svg.utils.graph.ChartProperties;
import se.openprocesslogger.svg.utils.graph.GraphBuilder;
import se.openprocesslogger.svg.utils.graph.ZoomSettings;

public class GraphProxy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -926567701620340253L;
	
	private Date tStart;
	private Date tEnd;
	private GraphBuilder builder;
	private double[] data;
	
	public GraphProxy(double[] data, Date tStart, Date tEnd){
		this.tStart = tStart;
		this.tEnd = tEnd;
		builder = new GraphBuilder(data);
		this.data = data;
		
	}
	
	private String getTimeString(Date d){
		//Date d = new Date(millis);
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		//SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss (SSS)");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(d);
	}
	
	public double[] getProcData(){
		return data;
	}
	
	public String getGraphWidth(){
		return ""+builder.getGraphWidth();
	}
	
	public ZoomSettings getZoomSettings(double[] data){
		ZoomSettings zoom = new ZoomSettings(builder,data);
		return zoom;
	}
	
	public String getPolylineZoom(){
		return "";
	}

	public ChartProperties getProperties(){
		ChartProperties p = builder.exportProperties();
		p.setStartDate(getTimeString(tStart));
		p.setEndDate(getTimeString(tEnd));
		p.setData(data);
		p.setStartTime(daySeconds(tStart));
		p.setEndTime(daySeconds(tEnd));
		p.setTimeUnit((p.getEndTime()-p.getStartTime())/(p.getEventWidth()-1));
		return p;
	}
	
	private long daySeconds(Date d){
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		long sec = c.get(Calendar.HOUR_OF_DAY)*3600 + c.get(Calendar.MINUTE)*60 + c.get(Calendar.SECOND);
		return sec;
	}
	
	public String getSvgBase(){
		SBWrapper sb = new SBWrapper();
		builder.appendCompleteSvg(sb);
		return sb.toString();
	}
	
	public static void main(String[] args){
		Date d = new Date(System.currentTimeMillis());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String myString = df.format(d);
		System.out.println(myString);
	}
}
