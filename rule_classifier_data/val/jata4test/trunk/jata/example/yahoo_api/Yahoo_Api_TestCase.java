package jata.example.yahoo_api;

import jata.Component.TestCase;
import jata.alt.Alt;
import jata.alt.AltResult;
import jata.alt.Timer;
import jata.exception.JataException;
import jata.exception.JataTestCaseException;
import jata.message.SetTimeMessage;
import jata.port.PipeCenter;

public class Yahoo_Api_TestCase extends TestCase<AbsComp,SysComp>{
	public Yahoo_Api_TestCase() throws JataTestCaseException {
		super(AbsComp.class, SysComp.class);
	}

	@Override
	protected void Case(AbsComp mtc, SysComp sys) throws JataException {
		try {
			System.out.println("make a querymsg of iphone");
			QueryMsg msg = new QueryMsg("iphone");

			PipeCenter.Map(mtc.absPort, sys.sysPort);
			
			System.out.println("send the query");
			mtc.absPort.send(msg);

			Alt alt = new Alt();
			Timer t = new Timer();
			t.start();

			alt.addBranch(mtc.absPort, new ResponseMsg());
			alt.addBranch(t, new SetTimeMessage(7000));
			alt.proc();

			AltResult r = alt.getResult();
			
			System.out.println("the "+r.index+" branch is hitted");
			
			if (r.index == 0)
				mtc.Pass();
			else{
				System.out.println("OOOh no timeout");
				mtc.Fail();
			}
		} catch (JataException e) {
			System.out.println(e.toString());
		}
	}

	public static void main(String[] argv) {
		try {
			Yahoo_Api_TestCase apicase = new Yahoo_Api_TestCase();
			apicase.start();
			
			System.out.println("----------------------------------");
			System.out.println("MyTestCase result:"+apicase.getVerdict());
		} catch (JataException e) {
			e.printStackTrace();
		}
		
	}

}
