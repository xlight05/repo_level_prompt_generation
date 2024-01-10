package jata.example.remoteApdater;

import jata.exception.JataException;
import jata.message.*;

public class MsgSetPwd extends Message{
	public String pwd;
	public MsgSetPwd(Message Father) {
		super(Father);
		codeToType = "java.lang.String";
		decodeFromType = "java.lang.String";
	}
	public MsgSetPwd(String str){
		this((Message)null);
		pwd = str;
	}

	@Override
	protected Object Coder() throws JataException {
		return (Object) pwd;
	}

	@Override
	protected boolean Decoder(Object stream) throws Exception {
		pwd = (String) stream;
		return true;
	}

}
