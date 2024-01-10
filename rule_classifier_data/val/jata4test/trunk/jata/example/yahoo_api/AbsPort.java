package jata.example.yahoo_api;

import jata.exception.JataException;
import jata.message.Message;
import jata.port.AbstractPort;

public class AbsPort extends AbstractPort{
	public AbsPort() throws JataException{
		super();
		addOutputType(Message.getICD(QueryMsg.class));
        addInputType(Message.getICD(ResponseMsg.class));
	}
}
