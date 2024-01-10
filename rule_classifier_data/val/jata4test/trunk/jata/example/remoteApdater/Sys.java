package jata.example.remoteApdater;

import jata.Component.SystemComponent;
public class Sys extends SystemComponent{
	public PortSM s_mPort;
	public PortSP s_pPort;
	
	public Sys() throws Exception{
		s_mPort = new PortSM();
		s_pPort = new PortSP();
	}
}
