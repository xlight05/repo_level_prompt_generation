package jata.example.remoteApdater;

import jata.port.AbstractPort;
import jata.exception.*;
import jata.message.*;

public class PortMP extends AbstractPort{
	public PortMP() throws JataException{
		super();
		addInputType(Message.getICD(MsgRightN.class));
		addOutputType(Message.getICD(MsgFail.class));
		addOutputType(Message.getICD(MsgOK.class));
	}
}
