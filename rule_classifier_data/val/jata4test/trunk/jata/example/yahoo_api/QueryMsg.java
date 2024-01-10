package jata.example.yahoo_api;

import jata.exception.JataException;
import jata.message.Message;

public class QueryMsg extends Message{
	private String key;
	
	public QueryMsg(String key){
		this((Message)null);
		this.key = key;
	}
	
	public QueryMsg(Message Father) {
		super(Father);
		codeToType = "java.lang.String";
		decodeFromType = null;
	}

	@Override
	protected Object Coder() throws JataException {
		return "http://answers.yahooapis.com/AnswersService/V1/questionSearch?appid=YahooDemo&output=xml&query="+key;
	}

	@Override
	protected boolean Decoder(Object stream) throws Exception {
		return false;
	}

}
