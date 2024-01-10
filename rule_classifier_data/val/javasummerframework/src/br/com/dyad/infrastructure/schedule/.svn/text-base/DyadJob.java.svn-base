package br.com.dyad.infrastructure.schedule;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.dyad.commons.data.AppTempEntity;
import br.com.dyad.infrastructure.entity.BaseEntity;

public class DyadJob extends BaseEntity implements Job, AppTempEntity {

	protected String currentStep = "";
	protected String status = "";
	protected int percentageDone = 0;
	protected String name;
	protected String database;
	
	
	public String getCurrentStep() {
		return currentStep;
	}
	public void setCurrentStep(String currentStep) {
		this.currentStep = currentStep;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getPercentageDone() {
		return percentageDone;
	}
	public void setPercentageDone(int percentageDone) {
		this.percentageDone = percentageDone;
	}			
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}		
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub		
	}			

	public String getParams(JobExecutionContext context){
		return (String) context.getJobDetail().getJobDataMap().get("params");
	}
	
	public HashMap<String, String> getParamsMap(JobExecutionContext context){
		
		String[] linhas = StringUtils.split(this.getParams(context), ";");
		HashMap<String,String> paramsMap = new HashMap<String,String>();
		for (int i =0 ; i < linhas.length; i++){
			String[] linha = StringUtils.split(linhas[i],"=");
			for(int j =0 ; j < linha.length; j++){
				paramsMap.put(linha[0], linha[1]);
			}
		}
		
		return paramsMap;
		
	}
}
