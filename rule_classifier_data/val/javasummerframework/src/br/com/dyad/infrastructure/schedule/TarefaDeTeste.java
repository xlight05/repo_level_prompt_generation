package br.com.dyad.infrastructure.schedule;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TarefaDeTeste extends DyadJob {
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		setStatus("Executando");
		for (int i = 0; i < 1000; i++) {
			setCurrentStep("Executando passo " + i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Parâmetros passados: " + context.getJobDetail().getJobDataMap().get("params"));		
	}

}
