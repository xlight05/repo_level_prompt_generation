package se.openprocesslogger.proxy.graphviewer;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.apache.log4j.Logger;

import se.openprocesslogger.Data;
import se.openprocesslogger.db.DataFetcher;
import se.openprocesslogger.db.OPLDatabase;
import se.openprocesslogger.svg.data.DataDecoder;
import se.openprocesslogger.svg.data.DataPoint;
import se.openprocesslogger.svg.style.Style;
/***
 * 
 * 
 * 
 * @author Maja Arvehammar
 *
 */
public class GraphViewerProxy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6744333100728119133L;
	
	private static Logger log = Logger.getLogger(GraphViewerProxy.class);
	
	public GraphViewerProxy() {
	}
	
	public GraphMetadataResponse getGraphViewerShell(GraphMetadata graphInfo){
		if(graphInfo == null) return null;
		GraphMetadataResponse response = new GraphMetadataResponse();
		String[] varNames = graphInfo.variables.split(";");
		log.debug("Shell requested; " +varNames.length +" from " +getTimeString(graphInfo.getFrom()) +" to " +getTimeString(graphInfo.getTo()));
		ArrayList<SingleChartInfo> charts = new ArrayList<SingleChartInfo>();
		for (int i=0; i<varNames.length-1; i+=2){
			int count = 0;
			log.debug("Log task id is "+graphInfo.logTaskId);
			if (graphInfo.logTaskId > 0){
				count = OPLDatabase.instance().getDataCount(varNames[i], graphInfo.logTaskId);
			}else{
				count = OPLDatabase.instance().getDataCount(varNames[i], graphInfo.getFrom(), graphInfo.getTo());
			}
			
			if (count > 0){
				SingleChartInfo ch = new SingleChartInfo();
				ch.setChartName(varNames[i]);
				ch.setTagType(varNames[i+1]);
				ch.setVarName(varNames[i]);
				ch.setDataCount(count);
				charts.add(ch);
				log.debug(varNames[i] + ": " +ch.getDataCount() +" points");
			}else{
				log.debug(varNames[i] + ": 0 points");
			}
			
		}
		response.setCharts(charts.toArray(new SingleChartInfo[0]));
		response.setFromMillis(graphInfo.getFrom());
		response.setToMillis(graphInfo.getTo());
		response.setStyle(Style.defaultStyle);
		response.setLogTaskId(graphInfo.logTaskId);
		log.debug("Returned shell");
		return response;
	}
	
	private String getTimeString(long ms){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(new Date(ms));
	}
	
	public Batch getBatch(BatchInfo batchInfo){
		log.debug("Batch requested: "+batchInfo.varName +" from "+getTimeString(batchInfo.from) +" to " +getTimeString(batchInfo.to));
		//int nbr = DataFetcher.instance().getDataCount(batchInfo.getVarName(), batchInfo.getFrom(), batchInfo.getTo());
		//log.debug(nbr +" is left. Requested " +batchInfo.batchSize);
		Batch b = new Batch();
		Data[] d;
		if (batchInfo.logTaskId > 0){
			d = OPLDatabase.instance().getBatch(batchInfo.getVarName(), batchInfo.getLogTaskId(), batchInfo.getFrom(), batchInfo.getTo(), batchInfo.getBatchSize());
		}else{
			d = DataFetcher.instance().getBatch(batchInfo.getVarName(), batchInfo.getFrom(), batchInfo.getTo(), batchInfo.getBatchSize());
		}
			
		
		if (d.length > 0){
			DataPoint[] dp = DataDecoder.decode(d).pData; 
			Arrays.sort(dp);
			b.setData(dp);
			log.debug("last ts: " +dp[dp.length-1].timestamp);
		}else{
			b.setData(new DataPoint[0]);
		}
		log.debug("Batch returned");
		return b;
	}
	
	public Batch getEventBatch(BatchInfo batchInfo){
		log.debug("Event batch requested: "+batchInfo.varName +" from "+batchInfo.from +" to " +batchInfo.to);
		//int nbr = DataFetcher.instance().getDataCount(batchInfo.getVarName(), batchInfo.getFrom(), batchInfo.getTo());
		//log.debug(nbr +" is left. Requested " +batchInfo.batchSize);
		Batch b = new Batch();
		Data[] d = DataFetcher.instance().getEventBatch(batchInfo.getVarName(), batchInfo.getFrom(), batchInfo.getTo(), batchInfo.getBatchSize());
		
		if (d.length > 0){
			DataPoint[] dp = DataDecoder.decode(d).pData; 
			Arrays.sort(dp);
			b.setData(dp);
			log.debug("last ts: " +dp[dp.length-1].timestamp);
		}else{
			b.setData(new DataPoint[0]);
		}
		log.debug("Batch returned");
		
		return b;
	}
	/*
	private String getTagType(String varName){
		String tt = TagTypes.getTagType(varName);
		if (tt != null) return tt;
		
		Data d = DataFetcher.instance().getSampleData(varName);
		if (d!=null){
			if(d.getValue() instanceof Byte || d.getValue() instanceof Character || d.getValue() instanceof Short || 
					d.getValue() instanceof Integer || d.getValue() instanceof Long || d.getValue() instanceof  Float || 
					d.getValue() instanceof Double){
				return AnalogChartShell.class.getName();
			}else if(d.getValue() instanceof Boolean){
				return DigitalChartShell.class.getName();
			}else{ // No primitive type. View as event
				return EventChartShell.class.getName();
			}
		}
		return AnalogChartShell.class.getName();
	}
	*/
	
	public GraphMetadataResponse getGraphViewerShellSingleData(SingleData data, IGraphInfoCreator parser){
		GraphMetadataResponse response = new GraphMetadataResponse();
		SingleDataInfo singleData = parser.getSingleDataInfo(data);
		String[] varNames = singleData.getVariables();
		String[] tagTypes = singleData.getTagTypes();
		log.debug("Single data shell requested: "+data.getDataId() +" parser "+parser.getClass().getName());
		ArrayList<SingleChartInfo> charts = new ArrayList<SingleChartInfo>();
		for (int i=0; i<varNames.length; i++){
			SingleChartInfo ch = new SingleChartInfo();
			ch.setChartName(varNames[i]);
			ch.setTagType(tagTypes[i]);
			ch.setVarName(varNames[i]);
			charts.add(ch);			
		}
		response.setFromMillis(singleData.getFromMillis());
		response.setToMillis(singleData.getToMillis());
		response.setCharts(charts.toArray(new SingleChartInfo[0]));
		response.setStyle(Style.defaultStyle);
		
		log.debug("Returned shell");
		return response;
	}
	
	public Batch getSingleDataBatch(SingleData data, IGraphInfoCreator parser, int index){
		Batch res = parser.getBatch(data, index);
		log.debug("All done!");
		return res;
	}
	
	public Serializable getSingleDataAdditionalData(SingleData data, IGraphInfoCreator parser){
		return parser.getAdditionalData(data);
	}
}
