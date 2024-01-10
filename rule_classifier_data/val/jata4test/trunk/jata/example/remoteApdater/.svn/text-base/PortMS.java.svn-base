package jata.example.remoteApdater;

import jata.port.AbstractPort;
import jata.exception.*;
import jata.message.*;

public class PortMS extends AbstractPort {
	public PortMS() throws JataException{
		super();
		addInputType(Message.getICD(MsgSuccess.class));
		addInputType(Message.getICD(MsgFail.class));
		addOutputType(Message.getICD(MsgTryPwd.class));
	}
}
