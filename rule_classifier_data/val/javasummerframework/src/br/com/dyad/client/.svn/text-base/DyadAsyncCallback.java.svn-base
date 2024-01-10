package br.com.dyad.client;

import br.com.dyad.client.widget.ExceptionDialog;

import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.StatusCodeException;

public abstract class DyadAsyncCallback implements AsyncCallback {
	
	public DyadAsyncCallback(){
		super();
	}
	
	@Override
	public void onFailure(Throwable arg0) {		
		DyadInfrastructure.messageWindow.close();
		
		if (arg0 instanceof ClientServerException && ((ClientServerException)arg0).getType().equals(ClientServerException.INVALID_SESSION) ){						
			ClientServerException ex = (ClientServerException)arg0;
			MessageBox.confirm("Sessão inválida!", "Deseja reiniciar o sistema?", new Listener<MessageBoxEvent>(){
				
				public void handleEvent(MessageBoxEvent be) {
					Button btn = be.getButtonClicked();
					if ( btn.getText().equalsIgnoreCase(Dialog.YES) ){
						DyadInfrastructure.reload();
					}
				}
				
			});									
		}else if (arg0 instanceof AppException ){
			AppException ex = (AppException)arg0;

			MessageBox.confirm("Sessão inválida!", "Deseja reiniciar o sistema?", new Listener<MessageBoxEvent>(){
				
				public void handleEvent(MessageBoxEvent be) {
					Button btn = be.getButtonClicked();
					if ( btn.getText().equalsIgnoreCase(Dialog.YES) ){
						Window.open(GWT.getModuleBaseURL(), "", "");
					}
				}
				
			});
			
			//ClientServerException.createExceptionWindow((AppException)arg0);
			ExceptionDialog.show(arg0);
		}else if (arg0 instanceof StatusCodeException ){
			//Window.alert("Erro na chamada RPC! " +arg0.getMessage());
			ExceptionDialog.show(arg0);
		}else {
			//Window.alert("Falha geral no sistema! " + arg0.getMessage());
			ExceptionDialog.show(arg0);
		}
	}
	
	@Override
	public void onSuccess(Object arg0) {
				
	}

}
