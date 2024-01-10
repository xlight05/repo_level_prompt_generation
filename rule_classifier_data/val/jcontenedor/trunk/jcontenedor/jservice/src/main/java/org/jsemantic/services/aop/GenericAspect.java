package org.jsemantic.services.aop;

import org.aspectj.lang.ProceedingJoinPoint;

public interface GenericAspect {
	/**
	 * 
	 * @param pjp
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	public Object execute(ProceedingJoinPoint pjp) throws Throwable; 
}
