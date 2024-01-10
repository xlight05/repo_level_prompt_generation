package br.com.dyad.infrastructure.schedule;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

import br.com.dyad.client.GenericService;

public class DyadJobListener implements JobListener{
	
	private static DyadJobListener jobListener;
	
	private DyadJobListener() {		
	}
	
	public static DyadJobListener getInstance() {
		if (jobListener == null){
			jobListener = new DyadJobListener();
		}
		return jobListener;
	}		

	@Override
	public String getName() {
		return "APP_JOB_LISTENER";
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext arg0) {
				
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext arg0) {
		String database = (String) arg0.getJobDetail().getJobDataMap().get(GenericService.GET_DATABASE_FILE);
		DyadJob dyadJob = (DyadJob)arg0.getJobInstance();
		dyadJob.setName(arg0.getJobDetail().getName());		
		dyadJob.setDatabase(database);
		SystemScheduler.addToJobList(database, arg0.getJobDetail().getName(), dyadJob);		
	}

	@Override
	public void jobWasExecuted(JobExecutionContext arg0,
			JobExecutionException arg1) {
		String database = (String) arg0.getJobDetail().getJobDataMap().get(GenericService.GET_DATABASE_FILE);
		SystemScheduler.removeFromJobList(database, arg0.getJobDetail().getName());		
	}

}
