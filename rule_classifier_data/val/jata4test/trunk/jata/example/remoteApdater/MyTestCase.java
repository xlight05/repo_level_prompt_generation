package jata.example.remoteApdater;

import jata.Component.*;
import jata.exception.*;
import jata.port.*;
import jata.alt.*;
import jata.message.*;

public class MyTestCase extends TestCase<Mtc,Sys>{
	public String tt="abcd";
	public MyTestCase()
			throws JataTestCaseException {
		super(Mtc.class, Sys.class);
		
	}

	@Override
	protected void Case(Mtc mtc, Sys sys) throws JataException {
		Ptc ptc = createPtc(Ptc.class);
		
		PipeCenter.Map(mtc.m_sPort, sys.s_mPort);
		PipeCenter.Map(mtc.m_pPort, ptc.p_mPort);
		PipeCenter.Map(ptc.p_sPort, sys.s_pPort);
		
		mtc.m_sPort.send(new MsgTryPwd("abcd"));
		
		if (mtc.isNone()){
			Alt alt = new Alt();
			Timer t = new Timer();
			t.start();
		
			alt.addBranch(mtc.m_sPort, new MsgSuccess());
			alt.addBranch(t, new SetTimeMessage(1000));
			alt.proc();
		
			AltResult r = alt.getResult();
			if (r.index != 0)
				mtc.Fail();
		}
		
		
		createFunction(MyTestFunc.class,ptc);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("1");

		String pwd="";
		int N=0;
		for (int i=0;i<10;i++){
			for (int j=0;j<4;j++){
				if (mtc.isNone()){
					int n = tryOnce(mtc,ptc,pwd+tt.charAt(j));
					
					if (n == -1){
						mtc.m_pPort.send(new MsgOK());
						
						Alt alt = new Alt();
						Timer t = new Timer();
						t.start();
					
						alt.addBranch(ptc, new ComponentDoneMessage());
						alt.addBranch(t, new SetTimeMessage(4000));
						alt.proc();

						if ((alt.getResult().index == 0)&&ptc.isPass()) {
							mtc.Pass();
							return;
						} else {
							mtc.Fail();
						}
					}
					
					if (n == -2){
						Alt alt = new Alt();
						Timer t = new Timer();
						t.start();
					
						alt.addBranch(ptc, new ComponentDoneMessage());
						alt.addBranch(t, new SetTimeMessage(5000));
						alt.proc();
						
						mtc.setVerdict(ptc.getVerdict().getValue());
						mtc.Fail();
						
						return;
					}
					
					
					if (n>N){
						System.out.println("add");
						pwd += tt.charAt(j)+"";
						N++;
						break;
					}
				}
			}
		}
	}
	
	public int tryOnce(Mtc mtc,Ptc ptc,String pwd) throws JataException{
		mtc.m_sPort.send(new MsgTryPwd(pwd));
		
		Alt alt = new Alt();
		Timer t = new Timer();
		t.start();
		
		alt.addBranch(mtc.m_sPort, new MsgSuccess());
		alt.addBranch(mtc.m_sPort, new MsgFail());
		alt.addBranch(t, new SetTimeMessage(1000));
		alt.proc();
		
		switch (alt.getResult().index){
		case 0:
			return -1;
		case 1:
			break;
		case 2:
			return -2;
		default :
			return -2;
		}
		
		MsgFail fail = (MsgFail)alt.getResult().Result;
		mtc.m_pPort.send(fail);
		
		Alt alt2 = new Alt();
		Timer t2 = new Timer();
		t2.start();
		
		alt2.addBranch(mtc.m_pPort, new MsgRightN(0));
		alt2.addBranch(t, new SetTimeMessage(2000));
		alt2.proc();
		
		switch (alt2.getResult().index){
		case 0:
			break;
		case 1:
			return -2;
		default :
			return -2;
		}
		
		
		return ((MsgRightN)alt2.getResult().Result).n;
	}

}
