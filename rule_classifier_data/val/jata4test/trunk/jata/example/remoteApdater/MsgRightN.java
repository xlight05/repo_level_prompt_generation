package jata.example.remoteApdater;

import jata.message.*;

public class MsgRightN extends NoCDMessage {
	public int n;
	public MsgRightN(Message Father) {
		super(Father);
	}
	public MsgRightN(int n){
		this(null);
		this.n = n;
	}
	@Override
	public String toString() {
		return "["+n+"]";
	}
	
}
