package jata.alt;

import java.util.*;

import jata.message.*;
import jata.exception.JataAltException;

class AltBranch{
	public IAltBranch port;
	public Message param;
}

public class Alt {
	public Alt(){
		branchs = new ArrayList<AltBranch>();
	}
	protected AltResult result;
	public AltResult getResult () { return result; }
	protected List<AltBranch> branchs;
	public void addBranch(IAltBranch port,Message m){
		AltBranch bch = new AltBranch();
		bch.port = port;
		bch.param = m;
		branchs.add(bch);
	}
	
	protected void lockAll(){
		for (AltBranch bch : branchs){
			bch.port.lock();
		}
	}
	protected void unlockAll(){
		for (AltBranch bch : branchs){
			bch.port.unlock();
		}
	}
	
	public void proc() throws JataAltException{
		
		while (true) {
			lockAll();

			for (AltBranch bch : branchs) {
				try {
					Message re = bch.port.waitHandle(bch.param);

					if (re != null) {
						result = new AltResult();
						result.Result = re;
						result.index = branchs.indexOf(bch);

						unlockAll();

						return;
					}
				} catch (Exception ex) {
					throw new JataAltException(ex,branchs.indexOf(bch), "proc()|F|");
				}
			}

			unlockAll();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
		}
		
	}
}
