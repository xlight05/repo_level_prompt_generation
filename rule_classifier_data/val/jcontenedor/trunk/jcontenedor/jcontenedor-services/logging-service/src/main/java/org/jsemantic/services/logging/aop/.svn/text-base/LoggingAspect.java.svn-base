package org.jsemantic.services.logging.aop;

import org.apache.commons.logging.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.jsemantic.services.aop.AbstractGenericAspect;

public class LoggingAspect extends AbstractGenericAspect {

	private Log log = null;

	public void setLog(Log log) {
		this.log = log;
	}

	@Override
	public void destroy() {
		this.log = null;
	}

	@Override
	public void executeAfter(ProceedingJoinPoint pjp) {
		if (log.isDebugEnabled()) {
			log.debug("After: " + pjp.getTarget());
		} else if (log.isInfoEnabled()) {
			log.info("After: " + pjp.getTarget());
		}
	}

	@Override
	public void executeBefore(ProceedingJoinPoint pjp) {
		if (log.isDebugEnabled()) {
			log.debug("Before: " + pjp.getTarget() + "\n" + "Params: "
					+ pjp.getArgs() + "\n" + pjp.toLongString() + "\n");
		} else if (log.isInfoEnabled()) {
			log.info("Before: " + pjp.getTarget());
		}
	}

}
