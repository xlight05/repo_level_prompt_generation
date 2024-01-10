package org.jsemantic.services.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.StopWatch;

public class ProfilingAspect extends AbstractGenericAspect {
	
	private StopWatch stopWatch = null;
	
	@Override
	public void executeAfter(ProceedingJoinPoint pjp) {
		
	}

	@Override
	public void executeBefore(ProceedingJoinPoint pjp) {
		stopWatch = new StopWatch();
		stopWatch.start(pjp.toShortString());
	}

	@Override
	public void destroy() {
		if (stopWatch != null) {
			stopWatch.stop();
			System.out.println(stopWatch.prettyPrint());
			stopWatch = null;
		}
	}

}
