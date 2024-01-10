package jata.example.remoteApdater;

import jata.exception.JataException;
import jata.message.Message;

public class MsgFail extends Message {
	protected String code;
	public MsgFail(Message Father) {
		super(Father);
		codeToType = null;
		decodeFromType = "java.lang.String";
	}
	public MsgFail(){
		this(null);
	}

	@Override
	protected Object Coder() throws JataException {
		return null;
	}

	@Override
	protected boolean Decoder(Object stream) throws Exception {
		code = (String) stream;
		return true;
	}

}
