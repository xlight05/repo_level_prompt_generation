package jata.example.remoteApdater;

import jata.exception.JataException;
import jata.message.*;

public class MsgTryPwd extends Message{
	protected String pwd;
	public MsgTryPwd(Message Father) {
		super(Father);
		codeToType = "java.lang.String";
		decodeFromType = null;
	}
	public MsgTryPwd(String pwd){
		this((Message)null);
		this.pwd = pwd;
	}

	@Override
	protected Object Coder() throws JataException {
		return (String) pwd;
	}

	@Override
	protected boolean Decoder(Object stream) throws Exception {
		return false;
	}
	@Override
	public String toString() {
		return "<"+pwd+">";
	}

}
