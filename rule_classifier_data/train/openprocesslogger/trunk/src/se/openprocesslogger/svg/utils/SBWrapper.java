package se.openprocesslogger.svg.utils;

public class SBWrapper extends SvgAppender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8381679163339215116L;
	
	private StringBuilder sb;
	
	public SBWrapper(){
		sb = new StringBuilder();
	}
	
	@Override
	public IAppendableSvg append(int x) {
		sb.append(x);
		return this;
	}

	@Override
	public IAppendableSvg append(double x) {
		sb.append(x);
		return this;
	}

	@Override
	public IAppendableSvg append(String string) {
		sb.append(string);
		return this;
	}

	@Override
	public IAppendableSvg appendLine(String s) {
		sb.append(s);
		return this;
	}
	
	public String toString(){
		return sb.toString();
	}
}
