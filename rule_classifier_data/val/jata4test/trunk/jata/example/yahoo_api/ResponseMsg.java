package jata.example.yahoo_api;

import jata.exception.JataException;
import jata.message.Message;

public class ResponseMsg extends Message{
	private String content;
	public ResponseMsg(Message Father) {
		super(Father);

		codeToType = null;
		decodeFromType = "java.lang.String";
	}
	public ResponseMsg() {
		this(null);
	}

	@Override
	protected Object Coder() throws JataException {
		return null;
	}

	@Override
	protected boolean Decoder(Object stream) throws Exception {
		content = (String)stream;
		
		if (content.length() > 1000)
			return true;
		else
			return false;
	}

}
