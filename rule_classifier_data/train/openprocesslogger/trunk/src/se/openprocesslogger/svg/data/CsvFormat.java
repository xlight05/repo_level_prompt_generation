package se.openprocesslogger.svg.data;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CsvFormat {
	public static final String DELIM = ",";
	public static final String NEW_LINE = "\r\n";
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); // micros will be added after ms as .xxx
	public static final DecimalFormat decimalFormat = new DecimalFormat("####0.####");
		
	public static String exportData(ProcDataHolder[] dataHolder, long fromMillis, long toMillis){
		StringBuilder sb = new StringBuilder();
		for(ProcDataHolder holder : dataHolder){
			sb.append("Timestamp").append(DELIM).append(holder.getTitle()).append(NEW_LINE);
			for(DataPoint dp : holder.pData){
				appendPoint(dp, sb);
			}
			sb.append(NEW_LINE).append(NEW_LINE);
		}
		return sb.toString();
	}
	
	private static void appendPoint(DataPoint dp, StringBuilder sb){
		sb.append(dateFormat.format(new Date(dp.timestamp)));
		sb.append(DELIM).append(dp.value);
		sb.append(NEW_LINE);
	}
}
