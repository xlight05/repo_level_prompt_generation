package jata.message;

public class SetTimeMessage extends NoCDMessage {
	protected long time;
	public SetTimeMessage(long t) {
		super(null);
		time = t;
	}
	
	public long getTime() { return time; }

}
