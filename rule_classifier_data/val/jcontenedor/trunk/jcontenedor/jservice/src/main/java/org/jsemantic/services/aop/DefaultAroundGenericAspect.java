package org.jsemantic.services.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;

public class DefaultAroundGenericAspect extends AbstractGenericAspect {
	private Log log = LogFactory.getLog(DefaultAroundGenericAspect.class);
	@Override
	public void executeAfter(ProceedingJoinPoint pjp) {
		// TODO Auto-generated method stub
		log.info("After execution method : " + pjp.getClass());
	}

	@Override
	public void executeBefore(ProceedingJoinPoint pjp) {
		// TODO Auto-generated method stub
		log.info("Before execution method : " + pjp.getClass());
	}

	@Override
	public void destroy() {
		
	}

	

}
