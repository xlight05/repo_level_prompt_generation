package se.openprocesslogger.svg.utils;

public class StringAppender extends SvgAppender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7987316863319697223L;

	StringBuilder sb;
	public StringAppender(){
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
		sb.append(s).append("\r\n");
		return this;
	}
	
	@Override
	public String toString() {
		return sb.toString();
	}
	
}
