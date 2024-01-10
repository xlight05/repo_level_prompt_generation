package jata.Component;

import jata.alt.IAltBranch;
import jata.exception.*;
import jata.message.ComponentDoneMessage;
import jata.message.Message;
import jata.util.Verdict;

import java.lang.reflect.Constructor;
import java.util.*;


public abstract class TestCase <Tmtc extends TestComponent ,Tsys extends SystemComponent> implements IAltBranch{
	protected Tmtc mtc;
	protected Tsys sys;
	protected List<TestComponent> Ptcs;
	protected abstract void Case(Tmtc mtc, Tsys sys) throws JataException;
	
	public Verdict getVerdict(){
		return mtc.getVerdict();
	}
	public String getName() {
		return this.getClass().getName();
	}
	
	@SuppressWarnings("unchecked")
	public TestCase(Class<? extends TestComponent> TMTC ,Class<? extends SystemComponent> TSYS) throws JataTestCaseException
    {
		try {
			Ptcs = new ArrayList<TestComponent>();
			Constructor<?> constructor1 = TMTC.getConstructor(new Class[0]);
			mtc = (Tmtc) constructor1.newInstance(new Object[0]);
			
			Constructor<?> constructor2 = TSYS.getConstructor(new Class[0]);
			sys = (Tsys) constructor2.newInstance(new Object[0]);
			
			Assert.C(TestComponent.class.isInstance(mtc), "TestComponent.class.isInstance(m)|Fr|");
			Assert.C(SystemComponent.class.isInstance(sys), "SystemComponent.class.isInstance(sys)|Fr|");
		
		} catch (Exception e) {
			throw new JataTestCaseException(e,this,"Constructor()|F|");
		} 
    }
	
	public void start() throws JataException{
		mtc.thread = Thread.currentThread();
		try {
			Case(mtc,sys);
		} catch (Exception e) {
			mtc.Error();
			throw new JataTestCaseException(e,this,"Start()|F|");
		}
	}
	
	@SuppressWarnings("unchecked")
	public synchronized <Tptc extends TestComponent> Tptc createPtc (Class<?> TPTC) throws JataException{
		try{
			Tptc t;
			Constructor<?> constructor1 = TPTC.getConstructor(new Class[0]);
			t = (Tptc) constructor1.newInstance(new Object[0]);
			Ptcs.add(t);
			return t;
		} catch (Exception e) {
			throw new JataTestCaseException(e,this,"createPtc()|F|");
		} 
	}
	
	@SuppressWarnings("unchecked")
	public synchronized void createFunction (Class <? extends TestFunction> TF,TestComponent component) throws JataException{
		try{
			TestFunction tf;
			Class<?> [] para = new Class[1];
			para[0] = component.getClass();
			Constructor<?> constructor1 = TF.getConstructor(para);
			
			Object[] pms = new Object[1];
			pms[0] = (Object) component;
			tf = (TestFunction) constructor1.newInstance(pms);
			
			component.setThread(tf);
			tf.start();
		} catch (Exception e) {
			throw new JataTestCaseException(e,this,"createFunction()|F|");
		}
	}
	
	public synchronized Message waitHandle(Message m) throws JataException {
		try {
			Iterator<TestComponent> it = Ptcs.iterator();
			Message re;
			TestComponent tc;
			while(it.hasNext()){
				tc = it.next();
				re = tc.waitHandle(m);
				if (re == null)
					return null;
				else
					it.remove();
			}

			Assert.C(Ptcs.isEmpty(), "Ptcs.isEmpty()|Fr|");
			Assert.C(ComponentDoneMessage.class.isInstance(m),
					"ComponentDoneMessage.class.isInstance(m)|Fr");
			
			((ComponentDoneMessage) m).setComponentDoneMessage(this);
			return m;
		} catch (Exception e){
			throw new JataTestCaseException(e,this,"waitHandle()|F|");
		}
	}
	//bug:如果多个线程同时在alt中处理waitHandle，那么快照将混乱
	public void lock(){
		for (TestComponent tc : Ptcs) {
			tc.lock();
		}
	}
	public void unlock() {}; 
}
