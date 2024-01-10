package se.openprocesslogger.svg.utils;

import java.io.IOException;
import java.io.Writer;

public class StringFileWriter extends SvgAppender{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 716647318049098984L;
	
	public Writer f;
	
	public StringFileWriter(Writer f){
		this.f = f;
	}
	public IAppendableSvg append(int x) {
		tryWrite(""+x);
		return this;
	}
	public IAppendableSvg append(double x) {
		tryWrite(""+x);
		return this;
	}
	public IAppendableSvg append(String string) {
		tryWrite(string);
		return this;
	}
	
	public IAppendableSvg appendLine(String s){
		tryWrite(s);
		tryWrite("\n");
		return this;
	}	
	
	private void tryWrite(String s){
		try {
			f.write(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}




