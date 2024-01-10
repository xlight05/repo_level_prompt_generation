package br.com.dyad.client.widget;

import java.util.HashMap;

import br.com.dyad.client.DyadAsyncCallback;
import br.com.dyad.client.DyadInfrastructure;
import br.com.dyad.client.GenericServiceAsync;
import br.com.dyad.client.utilities.SincronizerActionTypes;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;

public class CloseProcessListener implements Listener<WindowEvent>{
	
	private ClientProcess clientProcess;
	
	public CloseProcessListener( ClientProcess clientProcess ){
		super();
		this.clientProcess = clientProcess;
	}
	
	public void handleEvent(WindowEvent we) {	    		
		MessageBox.confirm("Close Window", DyadInfrastructure.translate( "Are you sure?" ), new Listener<MessageBoxEvent>() {
			@Override
			public void handleEvent(MessageBoxEvent ce) {
				final MessageBoxEvent be = ce;
				HashMap params = DyadInfrastructure.getRpcParams();		
				params.put("actionToExecute", SincronizerActionTypes.OnCloseAction.toString());
				params.put("serverWindowId", clientProcess.getServerObjectId());
				
				GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();	
				proxy.getServiceValue( ClientObjectSincronizer.SINCRONIZER_SERVICE_NAME, params, new DyadAsyncCallback() {

					public void onSuccess(Object arg0) {
						try {	
							HashMap result = (HashMap)arg0;
							HashMap resultMap = (HashMap)result.get("clientSincronizerMap");
							clientProcess.synchronizeProcessWithServerInformation(resultMap);
							clientProcess.getWindow().doLayout();
							
							if (!clientProcess.isAborted()) {								
								Button btn = be.getButtonClicked();
								if ( btn.getText().equalsIgnoreCase(Dialog.YES) ){
									clientProcess.destroyServerInstance();
									clientProcess.getWindow().removeListener(Events.BeforeHide, CloseProcessListener.this);
									clientProcess.getWindow().hide();
								}
							}
														
						} catch (Exception e) {
							ExceptionDialog.show(e);
						} finally {
							clientProcess.getObjectSincronizer().enableClientProcess( clientProcess );
						}	
					}
				});				                						
			}
        });	    		             
		we.setCancelled(true);
	}
}
