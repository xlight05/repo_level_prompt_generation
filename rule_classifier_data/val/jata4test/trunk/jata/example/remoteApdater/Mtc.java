package jata.example.remoteApdater;

import jata.Component.TestComponent;
import jata.exception.JataException;

public class Mtc extends TestComponent {
	public PortMS m_sPort;
	public PortMP m_pPort;
	
	public Mtc() throws JataException{
		super();
		m_sPort = new PortMS();
		m_pPort = new PortMP();
	}
}
