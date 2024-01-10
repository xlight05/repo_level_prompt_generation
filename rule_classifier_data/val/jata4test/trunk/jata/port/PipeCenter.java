package jata.port;

import jata.exception.*;

public class PipeCenter {
	public static void Map(Port a,Port b) throws JataPipeCenterException{
		try {
			
			DebugOut.println(a + " connect to "+b);
			
			Assert.C(!a.isMapped(),	"!a.isMapped()|Fr|");
			Assert.C(!b.isMapped(),	"!b.isMapped()|Fr|");
			
			a.setPipe(b);
			b.setPipe(a);
			
		} catch (Exception e) {
			throw new JataPipeCenterException(e,a,b,"Map()|F|");
		}
	}
	
	public static void UnMap(Port a,Port b) throws JataPipeCenterException{
		try {
			
			DebugOut.println(a + " disconnect to "+b);
			
			Assert.C(a.isMapped(),	"a.isMapped()|Fr|");
			Assert.C(b.isMapped(),	"b.isMapped()|Fr|");
			
			a.UnMap();
			b.UnMap();
			
		} catch (Exception e) {
			throw new JataPipeCenterException(e,a,b,"Map()|F|");
		}
	}
}
