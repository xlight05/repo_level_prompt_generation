package jata.example.yahoo_api;

import jata.Component.SystemComponent;
import jata.exception.JataException;

public class SysComp extends SystemComponent{
	public SysPort sysPort;
	
	public SysComp() throws JataException{
		super();
		sysPort = new SysPort();
	}
}
