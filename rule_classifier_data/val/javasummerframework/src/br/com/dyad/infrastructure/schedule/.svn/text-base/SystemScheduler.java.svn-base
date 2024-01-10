package br.com.dyad.infrastructure.schedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.hibernate.Session;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;

import br.com.dyad.client.GenericService;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;

public class SystemScheduler {
	
	public static HashMap<String, HashMap<String, DyadJob>> jobList = new HashMap<String, HashMap<String, DyadJob>>(); 
	public static SchedulerFactory sf;
    public static Scheduler sched;
    public static JobListener jobListener;
    
    private static void startFactory() {
    	if (sf == null){    		
    		sf = new StdSchedulerFactory();
    		try {
    			sched = sf.getScheduler();
    			sched.addJobListener(DyadJobListener.getInstance());
    			sched.start();			
    		} catch (SchedulerException e) {
    			throw new RuntimeException(e);
    		}
    	}
	}
	
	public static void runScheduledTasks(String database) {
		List<ScheduledTask> tasks = getTasks(database);
		try{			
			for (ScheduledTask scheduledTask : tasks) {
				scheduleJob(scheduledTask, database);
			}						
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void scheduleJob(ScheduledTask scheduledTask, String database) {
		try {
			startFactory();
			String className = scheduledTask.getJob();
			Class job = Class.forName(className);

			String jobName = "JOB_" + scheduledTask.getId() +  scheduledTask.getName();
			JobDetail jobDetail = new JobDetail(jobName, database, job);
			JobDataMap map = new JobDataMap();
			map.put("params", scheduledTask.getParameters());
			map.put(GenericService.GET_DATABASE_FILE, database);			
			jobDetail.setJobDataMap(map);			
			
			Trigger trigger = getTrigger(scheduledTask);			
			Trigger[] triggers = sched.getTriggersOfJob(jobName, database);
			
			for (Trigger t : triggers) {
				sched.unscheduleJob(t.getName(), database);
			}			
									
			jobDetail.addJobListener(DyadJobListener.getInstance().getName());
			
			sched.scheduleJob(jobDetail, trigger);						
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<ScheduledTask> getTasks(String database){ 
		return PersistenceUtil.executeHql(database, "from ScheduledTask order by id");
	}
	
	public static Trigger getTrigger(ScheduledTask task) {		
		Trigger trigger = null;
		Calendar cal = Calendar.getInstance(Locale.getDefault());
		cal.setTime(task.getTime() == null ? new Date() : task.getTime());		
		
		if (task.getType().equals(ScheduleType.Daily.getCode())){
			trigger = TriggerUtils.makeDailyTrigger(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
		} else if (task.getType().equals(ScheduleType.Interval.getCode())){
			int minutes = cal.get(Calendar.HOUR_OF_DAY) * 60;
			minutes += cal.get(Calendar.MINUTE);			
			trigger = TriggerUtils.makeMinutelyTrigger(minutes);
		} else if (task.getType().equals(ScheduleType.Monthly.getCode())){
			trigger = TriggerUtils.makeMonthlyTrigger(task.getDayOfMonth(), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE)) ;
		} else if (task.getType().equals(ScheduleType.Once.getCode())){
			trigger = new SimpleTrigger("ONCE_" + task.getName(), null, cal.getTime());
		} else if (task.getType().equals(ScheduleType.Weekly.getCode())){
			trigger = TriggerUtils.makeWeeklyTrigger(task.getDayOfWeek(), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));			
		}
		
		trigger.setName("TRIGGER_" + task.getId());		
		
		return trigger;
	}
	
	public static synchronized void addToJobList(String database, String jobName, DyadJob job) {
		HashMap<String, DyadJob> dbJobs = jobList.get(database);
		if (dbJobs == null){
			dbJobs = new HashMap<String, DyadJob>();
			jobList.put(database, dbJobs);
		}
		removeFromJobList(database, jobName);		
		dbJobs.put(jobName, job);
	}
	
	public static synchronized void removeFromJobList(String database, String jobName) {
		HashMap<String, DyadJob> dbJobs = jobList.get(database);
		if (dbJobs != null){			
			dbJobs.remove(jobName);
		}
	}
	
	public static synchronized List<DyadJob> getScheduledTasks(String database) {
		List<DyadJob> jobs = new ArrayList<DyadJob>();
		
		if (database == null){
			for(HashMap<String, DyadJob> map : jobList.values()){
				jobs.addAll(map.values());
			}
		} else {			
			HashMap<String, DyadJob> map = jobList.get(database);
			jobs.addAll(map.values());
		}
		
		return jobs;
	}
	
	public static void newTask(Class jobClass, String database, String name, Integer type, Date time,
			Date date, int dayOfMonth, int dayOfWeek, String parameters) {
		
		ScheduledTask scheduledTask = new ScheduledTask();
		scheduledTask.setName(name);
		scheduledTask.setType(type);
		scheduledTask.setTime(time);
		scheduledTask.setDate(date);
		scheduledTask.setDayOfMonth(dayOfMonth);
		scheduledTask.setDayOfWeek(dayOfWeek);
		scheduledTask.setJob(jobClass.getName());
		scheduledTask.setParameters(parameters);
		
		Session session = PersistenceUtil.getSession(database);
		session.beginTransaction();
		try{
			scheduledTask.createId(database);
			session.save(scheduledTask);						
			session.getTransaction().commit();
			
			scheduleJob(scheduledTask, database);
		} catch(Exception e){
			session.getTransaction().rollback();
			throw new RuntimeException(e);
		}

	}

}
