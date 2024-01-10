package br.com.dyad.infrastructure.schedule;

import javax.servlet.http.HttpSession;

import br.com.dyad.infrastructure.navigation.GenericEntityBeanWindow;
import br.com.dyad.infrastructure.widget.Action;
import br.com.dyad.infrastructure.widget.Interaction;
import br.com.dyad.infrastructure.widget.grid.DataGrid;

public class RunningTasks extends GenericEntityBeanWindow {
	
	Action refresh;	
	Action stopTask;
	
	public RunningTasks(HttpSession httpSession) {
		super(httpSession);
		add(refresh);
		add(stopTask);
	}
	
	public InteractionShowData runningTasksShowData = new InteractionShowData( this, "showData"){
		@Override
		public void defineInteraction() throws Exception {
			super.defineInteraction();			
			this.getWindow().enableAndShowActions(new String[]{"refresh", "RemoveFromScheduler"});
		}
	};

	@Override
	public void defineWindow() throws Exception {
		refresh = new Action( this, "refresh"){		
			@Override
			public void onClick() throws Exception {
				runningTasksShowData.remove(grid);
				grid = new DataGrid(RunningTasks.this, "grid");
				dataList.setList(SystemScheduler.getScheduledTasks(getDatabase()));
				grid.setDataList(dataList);
				grid.setBeanName(DyadJob.class.getName());
				grid.setViewMode(DataGrid.VIEW_MODE_TABLEVIEW);
				runningTasksShowData.add(grid);
			}		
		};
		
		stopTask = new Action( this, "RemoveFromScheduler"){			
			@Override
			public void onClick() throws Exception {
				if (RunningTasks.this.grid.getCurrentEntity() != null){
					DyadJob job = (DyadJob)RunningTasks.this.grid.getCurrentEntity();
					SystemScheduler.sched.deleteJob(job.getName(), job.getDatabase());
				}
			}		
		};		
		
		setBeanName(DyadJob.class.getName());
		this.dataList.setList(SystemScheduler.getScheduledTasks(getDatabase()));
		super.defineWindow();
	}
	
	public Interaction getShowData() {		
		return this.runningTasksShowData;
	}	
	
}
