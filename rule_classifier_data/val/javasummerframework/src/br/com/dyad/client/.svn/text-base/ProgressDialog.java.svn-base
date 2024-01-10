package br.com.dyad.client;

import br.com.dyad.client.widget.ClientProcess;

import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.ProgressBar;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.google.gwt.user.client.Timer;

public class ProgressDialog extends Dialog {
	
	protected ProgressBar progressBar;
	protected ClientProcess process;
	protected Timer timer;
	protected int duration;
			
	public ProgressDialog(ClientProcess process, int duration) {
		this.process = process;
		this.duration = duration;
		
		CenterLayout layout = new CenterLayout();
		setLayout(layout);
		setClosable(false);
		setButtons("");
		setIconStyle("progress-user");
		setHeading(DyadInfrastructure.translate("Progress"));
		setModal(true);
		setBodyBorder(true);
		setBodyStyle("padding: 8px;background: none");
		setWidth(460);
		setResizable(false);
		
		this.progressBar = new ProgressBar();
		this.progressBar.setDuration(duration);
		this.progressBar.updateProgress(0, "");
		add(this.progressBar);
		this.progressBar.setWidth(435);

		timer = new Timer(){

			@Override
			public void run() {
				if (ProgressDialog.this.isVisible()){
					ProgressDialog.this.process.getObjectSincronizer().executeServerMethod(ProgressDialog.this.process.getServerObjectId(), null, null);
					ProgressDialog.this.doLayout();
				}
			}
			
		};
		
		timer.scheduleRepeating(2000);
	}

	public ProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	public ClientProcess getProcess() {
		return process;
	}

	public void setProcess(ClientProcess process) {
		this.process = process;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}	
	
}
