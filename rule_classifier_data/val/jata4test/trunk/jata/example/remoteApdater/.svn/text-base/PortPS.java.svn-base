package jata.example.remoteApdater;

import jata.port.AbstractPort;
import jata.exception.*;
import jata.message.*;

public class PortPS extends AbstractPort{
	public PortPS() throws JataException {
		super();
		addInputType(Message.getICD(MsgOK.class));
		addOutputType(Message.getICD(MsgSetPwd.class));
	}
}
