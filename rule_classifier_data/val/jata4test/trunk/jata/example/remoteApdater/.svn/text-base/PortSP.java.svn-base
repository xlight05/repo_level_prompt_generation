package jata.example.remoteApdater;

import jata.port.RemoteSystemPort;
import jata.message.*;

public class PortSP extends RemoteSystemPort{
	public PortSP() throws Exception{
		super();
		addInputType(Message.getICD(MsgSetPwd.class));
		addOutputType(Message.getICD(MsgOK.class));
		init("rmi://127.0.0.1:1199/SP");
	}
}
