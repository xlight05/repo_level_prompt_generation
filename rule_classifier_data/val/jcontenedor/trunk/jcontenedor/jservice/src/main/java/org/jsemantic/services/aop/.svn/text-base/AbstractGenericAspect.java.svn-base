package org.jsemantic.services.aop;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 
 * @author Owner
 * 
 */
public abstract class AbstractGenericAspect implements GenericAspect {
	
	public Object execute(ProceedingJoinPoint pjp) throws Throwable {
		try {
			executeBefore(pjp);
			Object result = pjp.proceed();
			executeAfter(pjp);
			return result;
		}
		finally {
			destroy();
		}
	}

	public abstract void executeBefore(ProceedingJoinPoint pjp);

	public abstract void executeAfter(ProceedingJoinPoint pjp);
	
	public abstract void destroy();
}
