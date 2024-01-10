package jata.example.remoteApdater;
import jata.port.AbstractPort;
import jata.exception.*;
import jata.message.*;

public class PortPM extends AbstractPort {
	public PortPM() throws JataException{
		super();
		addOutputType(Message.getICD(MsgRightN.class));
		addInputType(Message.getICD(MsgFail.class));
		addInputType(Message.getICD(MsgOK.class));
	}
}
