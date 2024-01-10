package jata.message;

import jata.exception.JataException;
import jata.exception.JataNeverReachException;

public abstract class NoCDMessage extends Message{

	public NoCDMessage(Message Father) {
		super(Father);
	}

	@Override
	protected Object Coder() throws JataException {
		throw new JataNeverReachException(null,this,"Coder()|F|");
	}

	@Override
	protected boolean Decoder(Object stream) throws Exception {
		throw new JataNeverReachException(null,this,"Decoder()|F|");
	}

}
