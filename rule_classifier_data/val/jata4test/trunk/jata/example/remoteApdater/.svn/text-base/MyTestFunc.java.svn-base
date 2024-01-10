package jata.example.remoteApdater;

import jata.Component.*;
import jata.alt.Alt;
import jata.alt.Timer;
import jata.exception.*;
import jata.message.*;

public class MyTestFunc extends TestFunction<Ptc>{

	public MyTestFunc(Ptc comp) {
		super(comp);
	}

	@Override
	public void Func(Ptc ptc) throws JataException {
		ptc.p_sPort.send(new MsgSetPwd("abcd"));
		
		Alt alt = new Alt();
		Timer t = new Timer();
		t.start();
	
		alt.addBranch(ptc.p_sPort, new MsgOK());
		alt.addBranch(t, new SetTimeMessage(2000));
		alt.proc();
		
		if (alt.getResult().index !=0)
		{	
			ptc.Fail();
			return;
		}
		
		while(true){
			Alt alt2 = new Alt();
			Timer t2 = new Timer();
			t2.start();
		
			alt2.addBranch(ptc.p_mPort, new MsgFail());
			alt2.addBranch(ptc.p_mPort, new MsgOK());
			alt2.addBranch(t, new SetTimeMessage(5000));
			alt2.proc();
			
			switch (alt2.getResult().index){
			case 0:
				break;
			case 1:
				
				ptc.Pass();
				return;
			case 2:
				ptc.Fail();
				return;
			}
			
			String code = ((MsgFail)alt2.getResult().Result).code;
			ptc.p_mPort.send(new MsgRightN(Integer.parseInt(code) -100));
		}
	}

}
