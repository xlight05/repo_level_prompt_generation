package jata.example.tiny;

import jata.exception.*;
import jata.message.Message;
import jata.port.*;

public class Sys extends SystemPort{
	public Sys() throws JataException{
		super();
		addInputType(Message.getICD(CheckCode.class));
        addOutputType(Message.getICD(Package.class));
        this.PortMode = SystemPort.AdapterMode;
	}

	@Override
	protected AdapterPackage Adapter(AdapterPackage pkg) throws Exception {
		if (pkg.streamType.equals("java.lang.String"))
        {
            Thread.sleep(3000);
            return new AdapterPackage("Name:UcranePassword:123456@Cdwx");
        }
        return null;
	}
}
