package jata.example.remoteApdater;

import jata.exception.JataException;
import jata.message.Message;

public class MsgSuccess extends Message {

	public MsgSuccess(Message Father) {
		super(Father);
		codeToType = null;
		decodeFromType = "java.lang.String";
	}
	public MsgSuccess(){
		this(null);
	}
	@Override
	protected Object Coder() throws JataException {
		return null;
	}

	@Override
	protected boolean Decoder(Object stream) throws Exception {
		if ("Success".equals(stream))
			return true;
		else
			return false;
	}

}
