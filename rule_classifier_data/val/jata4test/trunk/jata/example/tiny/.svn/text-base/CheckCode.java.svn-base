package jata.example.tiny;

import jata.message.Message;

public class CheckCode extends Message {

	public CheckCode(Message Father) {
		super(Father);
		decodeFromType = "java.lang.String";
        codeToType = "java.lang.String";
	}
	
	public CheckCode(){
		this(null);
	}
	
	public String code;

	@Override
	protected Object Coder() {
		
		return code;
	}

	@Override
	protected boolean Decoder(Object stream) {
		code = (String)stream;
		return true;
	}

}
