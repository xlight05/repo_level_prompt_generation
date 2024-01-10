package jata.example.tiny;

import jata.exception.*;
import jata.message.Message;
import jata.port.AbstractPort;

public class Ads extends AbstractPort{
	public Ads() throws JataException{
		super();
		addOutputType(Message.getICD(CheckCode.class));
        addInputType(Message.getICD(Package.class));
	}
}
