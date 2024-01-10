package com.shautvast.realtimegc;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.shautvast.realtimegc.util.NBBufferedInputStreamReader;
import com.shautvast.realtimegc.util.TailStream;

/**
 * standard sun verbose:gc format
 * @author sander
 *
 */
public class SimpleSunGcLogReader implements GcLogReader {
	private String fileName;
	private BufferedReader reader;
	private Pattern mp=Pattern.compile("->(.*)K\\((.*)K\\)");
	
	public SimpleSunGcLogReader(String filename) {
		this.fileName = filename;
		try {
			reader=new BufferedReader(new NBBufferedInputStreamReader(new TailStream(fileName, false)));
		} catch (IOException e) {
			throw new GcLogViewException(e);
		}
	}

	public String getFileName() {
		return fileName;
	}

	public Measurement read() {
		String line;
		try {
			while ((line=reader.readLine())!=null){
				Matcher matcher = mp.matcher(line);
				if (matcher.find()){
					Measurement m = new Measurement();
					m.setTime(new Date());//no absolute datetime in log...
					m.setFreeMem(Integer.parseInt(matcher.group(1)));
					m.setTotalMem(Integer.parseInt(matcher.group(2)));
					return m;
				}
			}
		} catch (IOException e) {
			throw new GcLogViewException(e);
		}
		return null;
	}

}
