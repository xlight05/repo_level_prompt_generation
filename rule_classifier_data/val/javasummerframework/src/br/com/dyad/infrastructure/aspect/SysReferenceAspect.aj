package br.com.dyad.infrastructure.aspect;

import java.util.Hashtable;

import br.com.dyad.infrastructure.schedule.DyadJob;
import br.com.dyad.infrastructure.widget.Action;
import br.com.dyad.infrastructure.widget.Window;

public aspect SysReferenceAspect {

	private static Hashtable<Thread, Object> reference = new Hashtable<Thread, Object>();
	
	pointcut alterFieldPointcut() : execution(* br.com.dyad.infrastructure.widget.Window+.*(..)) || 
									execution(* br.com.dyad.infrastructure.widget.BaseAction+.*(..)) ||
									execution(* br.com.dyad.infrastructure.schedule.DyadJob+.*(..));
	
	before() : alterFieldPointcut() {
		addReference(thisJoinPoint.getTarget());
	}
	
	after() : alterFieldPointcut() {
		removeReference(thisJoinPoint.getTarget());
	}
	
	//Adiciona na tabela o objeto que disparou a alteração no banco de dados
	public void addReference(Object obj) {
		if (obj == null) {
			return;
		}
		
		Object objReference = reference.get(Thread.currentThread());
		
		if (objReference != null) {
			if (objReference instanceof Action) {
				reference.put(Thread.currentThread(), obj);
			}
		} else {
			reference.put(Thread.currentThread(), obj);
		}
		
	}
	
	public void removeReference(Object obj) {	
		if (obj == null) {
			return;
		}
		
		if (obj != null) {
			if (obj instanceof Action) {
				Action action = (Action)obj;
				
				if (action.getParent() != null) {					
					reference.put(Thread.currentThread(), action.getParent());
				} else {
					reference.remove(Thread.currentThread());
				}
			} else {
				reference.remove(Thread.currentThread());
			}
		}
	}
	
	public static String getReferenceName() {
		Object obj = reference.get(Thread.currentThread());		
		
		if (obj != null) {
			if (obj instanceof Window) {
				return "Window: " + ((Window)obj).getTitle();
			} else if (obj instanceof Action) {
				return "Action: " + ((Action)obj).getText();
			} else if (obj instanceof DyadJob) {
				return "Scheduler: " + ((DyadJob)obj).getName();
			}
		}
		
		return null;
	}
	
}
