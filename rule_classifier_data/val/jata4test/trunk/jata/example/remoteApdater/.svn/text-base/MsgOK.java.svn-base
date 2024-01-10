package jata.example.remoteApdater;

import jata.exception.JataException;
import jata.message.*;

public class MsgOK extends Message{

	public MsgOK(Message Father) {
		super(Father);
		codeToType = "java.lang.String";
		decodeFromType = "java.lang.String";
	}
	public MsgOK(){
		this(null);
	}

	@Override
	protected Object Coder() throws JataException {
		return "OK";
	}

	@Override
	protected boolean Decoder(Object stream) throws Exception {
		if ("OK".equals(stream))
			return true;
		else
			return false;
	}
	
}
