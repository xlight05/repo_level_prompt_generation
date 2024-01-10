package se.openprocesslogger.svg.data;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import se.openprocesslogger.Data;
import se.openprocesslogger.db.LogTaskFetcher;

/***
 * Decodes Data into the format DataPoint that is used for graphic representation.
 * @author Maja Arvehammar (maja.arvehammar@ipbyran.se)
 *
 */
public class DataDecoder {
	private static Logger log = Logger.getLogger(DataDecoder.class);
	
	public static ProcDataHolder decode(Data[] data){
		ArrayList<DataPoint> dp = new ArrayList<DataPoint>();
		boolean isAnalog = false;
		String title = getTitle(data[0]);
		for(int i=0; i<data.length; i++){
			if(data[i].getValue() instanceof Byte || data[i].getValue() instanceof Character || data[i].getValue() instanceof Short || 
					data[i].getValue() instanceof Integer || data[i].getValue() instanceof Long || data[i].getValue() instanceof  Float || 
					data[i].getValue() instanceof Double){
				dp.add(parseAnalog(data[i]));
				isAnalog = true;
			}else if(data[i].getValue() instanceof Boolean){
				dp.add(parseDigital(data[i]));
				isAnalog = false;
			}else{				
				log.error("Unknown data type: "+data[i].getValue());
			}
		}
		DataPoint[] dpArr = dp.toArray(new DataPoint[dp.size()]);
		log.debug(title+" has "+dpArr.length +" data points");
		ProcDataHolder ph = new ProcDataHolder(dpArr, isAnalog, title);
		return ph;
	}

	public static double decode(Data d){
		if(d.getValue() instanceof Byte || d.getValue() instanceof Character || d.getValue() instanceof Short || 
				d.getValue() instanceof Integer || d.getValue() instanceof Long || d.getValue() instanceof  Float || 
				d.getValue() instanceof Double){
			return parseAnalog(d).value;
		}else if(d.getValue() instanceof Boolean){
			return parseDigital(d).value;
		}
		return 0;
	}
	
	private static DataPoint parseAnalog(Data data){
		return new DataPoint(Double.valueOf(data.getValue().toString()), data.getTimestamp().getTime(), data.getPointType());
	}
	
	private static DataPoint parseDigital(Data data){
		return new DataPoint(((Boolean)data.getValue()) ? 1 : 0, data.getTimestamp().getTime(), data.getPointType());
	}
		
	private static String getTitle(Data data){
		String task = LogTaskFetcher.instance().getLogTask(data.getLoggingTask()).getName();
		return task +": " +data.getName();
	}
}
