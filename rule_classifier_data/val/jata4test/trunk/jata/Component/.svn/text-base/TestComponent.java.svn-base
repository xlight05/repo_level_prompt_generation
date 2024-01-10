package jata.Component;

import jata.alt.IAltBranch;
import jata.exception.*;
import jata.message.ComponentDoneMessage;
import jata.message.Message;
import jata.util.*;

public abstract class TestComponent implements IAltBranch{
	protected Verdict verdict;
    public void Pass() { verdict.Pass(); }
    public void Fail() { verdict.Fail(); }
    public void None() { verdict.None(); }
    public void Inconc() { verdict.Inconc(); }
    public void Error() { verdict.Error(); }
    public boolean isPass() { return verdict.isPass(); }
    public boolean isFail() { return verdict.isFail(); }
    public boolean isNone() { return verdict.isNone(); }
    public boolean isInconc() { return verdict.isInconc(); }
    public boolean isError() { return verdict.isError(); }
	public Verdict getVerdict() {
		return verdict;
	}
	public void setVerdict(VerdictEnum v) {
		this.verdict.setValue(v);
	}
	
    public String getName(){
    	return this.getClass().getName();
    }
    
    public TestComponent(){
    	verdict = new Verdict();
    }
    
    protected Thread thread;
    protected boolean isThreadAlive;
	public Thread getThread() {
		return thread;
	}
	public void setThread(Thread thread) {
		this.thread = thread;
	}
	public void lock() {
		isThreadAlive = thread.isAlive();
	}
	
	public void unlock() {
	}
	
	public Message waitHandle(Message m) throws JataException {
		if (!isThreadAlive) {
			try {
				thread.join(20);

				Assert.C(ComponentDoneMessage.class.isInstance(m),
						"ComponentDoneMessage.class.isInstance(m)|Fr|");

				((ComponentDoneMessage) m).setComponentDoneMessage(this);
				return m;
			} catch (InterruptedException e) {
				throw new JataTestComponentException(e, this, "waitHandle()|F|");
			}
		}
		return null;
	}
    
}
