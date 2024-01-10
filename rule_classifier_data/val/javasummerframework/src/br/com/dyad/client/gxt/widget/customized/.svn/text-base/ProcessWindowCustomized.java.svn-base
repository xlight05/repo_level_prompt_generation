package br.com.dyad.client.gxt.widget.customized;

import br.com.dyad.client.widget.ClientProcess;
import br.com.dyad.client.widget.CloseProcessListener;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

public class ProcessWindowCustomized extends ContentPanel{

	private Listener<WindowEvent> closeListener;

	private ClientProcess clientProcess;
	
	public ProcessWindowCustomized( ClientProcess clientProcess, Boolean newWindow, Boolean showModal ) {
		super();
		setHeaderVisible(false);
		setBorders(false);
		//só usava isso quando herdava da Window do ext
    	//this.setOnEsc(false);
		this.clientProcess = clientProcess;
		
		this.setLayout(new FitLayout());
	    this.setScrollMode(Scroll.AUTO);
	    
	    this.closeListener = new CloseProcessListener( clientProcess );
	    this.addListener(Events.BeforeHide, this.closeListener );	    
	    //só usava isso quando herdava da Window do ext
	    //this.setMinimizable(true);
	    //this.setMaximizable(true);
	    //this.setCollapsible(true);
	    this.setSize(500, 500);
	    //this.setModal( showModal );
	}

	public ClientProcess getClientProcess() {
		return clientProcess;
	}

	public void setClientProcess(ClientProcess clientProcess) {
		this.clientProcess = clientProcess;
	}

	public void beforeRender(){
		this.clientProcess.finalizeDyadProcess();
	}

	@Override
	public boolean doLayout() {
		// this method was redefined because we can call it, so it was changed 
		// to public.
		return super.doLayout();
	}
}
