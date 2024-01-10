package br.com.dyad.infrastructure.schedule;

import javax.servlet.http.HttpSession;

import br.com.dyad.infrastructure.navigation.GenericEntityBeanWindow;

public class ScheduleWindow extends GenericEntityBeanWindow {

	public ScheduleWindow(HttpSession httpSession) {
		super(httpSession);
	}

	@Override
	public void defineWindow() throws Exception {
		this.setBeanName(ScheduledTask.class.getName());
		super.defineWindow();
	}
	
	@Override
	public void onAfterPost() {
		super.onAfterPost();
		
		ScheduledTask task = (ScheduledTask)this.grid.getCurrentEntity();
		SystemScheduler.scheduleJob(task, getDatabase());
	}
		

}
