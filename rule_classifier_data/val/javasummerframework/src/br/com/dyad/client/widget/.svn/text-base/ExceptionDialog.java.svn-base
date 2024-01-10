package br.com.dyad.client.widget;

import java.util.HashMap;

import br.com.dyad.client.ClientServerException;
import br.com.dyad.client.DyadAsyncCallback;
import br.com.dyad.client.DyadInfrastructure;
import br.com.dyad.client.GenericServiceAsync;
import br.com.dyad.client.TimerMessageBox;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.google.gwt.user.client.rpc.StatusCodeException;

public class ExceptionDialog {	
	public static Dialog dialog = null;
	public static String message = "";
	public static String stackTrace = "";
	public static Button details = new Button(DyadInfrastructure.translate("Details"));
	public static Button sendEmail = new Button(DyadInfrastructure.translate("Send E-mail"));
	public static Button hide = new Button(DyadInfrastructure.translate("Hide"));
	public static TextArea detailsArea = new TextArea();

	public static final String TECHNICAL_DETAILS = "Technical Details";
	public static final String UNEXPECTED_ERROR = "Unexpected Error";
	
	
	public static void show( Throwable e ){		
		message = "";
		stackTrace = "";
		detailsArea.setValue(null);
		
		dialog = new Dialog();
		dialog.setSize(400, 200);
		dialog.setHideOnButtonClick(true);
		dialog.setButtons(Dialog.OK);
		dialog.addButton(details);
		dialog.addButton(sendEmail);
		dialog.addButton(hide);
				
		hide.hide();
		details.show();
		sendEmail.show();
		details.addSelectionListener(new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				dialog.setSize(600, 400);
				detailsArea.setReadOnly(true);
				detailsArea.setValue(stackTrace);
				detailsArea.setSize(550, 300);
				dialog.add(detailsArea);
				details.hide();
				hide.show();
				dialog.layout();
			}
			
		});
		sendEmail.addSelectionListener(new SelectionListener<ButtonEvent>(){
			@SuppressWarnings("unchecked")
			@Override
			public void componentSelected(ButtonEvent ce) {
				GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();
				HashMap params = DyadInfrastructure.getRpcParams();
				params.put("emailContent", stackTrace);
				params.put("subject", DyadInfrastructure.translate( "Exception sent from user!" ) );
				params.put("recipients", new String[]{"admin_dyadinfra@dyad.com.br", "danilo@dyad.com.br"});	
				
				DyadInfrastructure.messageWindow = TimerMessageBox.wait( DyadInfrastructure.translate("Sending email..."), "", DyadInfrastructure.translate("Sending email...") );
				DyadInfrastructure.messageWindow.setModal(true);
				
				proxy.getServiceValue("br.com.dyad.infrastructure.service.SendMailService",
						params, new DyadAsyncCallback() {
							@Override
							public void onFailure(Throwable arg0) {
								DyadInfrastructure.messageWindow.close();
								MessageBox.alert("Enviar email", 
										"Não foi possível enviar o email para o administrador do sistema.", null);						
							}

							public void onSuccess(Object arg0) {
								DyadInfrastructure.messageWindow.close();
								MessageBox.alert("Enviar enviado", 
										"Email enviado com sucesso para o administrador do sistema.", null);						
							}
						});
			}
			
		});
		hide.addSelectionListener(new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				dialog.remove(detailsArea);
				dialog.setSize(400, 200);
				hide.hide();
				details.show();
				dialog.layout();
			}
			
		});		

		if ( e instanceof StatusCodeException){
			dialog.setHeading( DyadInfrastructure.translate( ExceptionDialog.UNEXPECTED_ERROR ) );
			dialog.setIconStyle("exception-dialog-error");
			message = "Não foi possível conectar-se ao servidor " + DyadInfrastructure.database + ".\n";
			stackTrace = DyadInfrastructure.translate( ExceptionDialog.TECHNICAL_DETAILS ) + " \n" + e.getMessage();
			dialog.addText(message );
			dialog.setBodyStyle("fontWeight:bold;padding:13px;background-color:#FAF0E6");		
		} else if ( e instanceof Exception ){
			ClientServerException error = (ClientServerException) e;											  
			if ( error.getType().equals( ClientServerException.TREATED_EXCEPTION ) ){
				dialog.setHeading("Atenção");
				dialog.setIconStyle("exception-dialog-treated-error");
				dialog.setBodyStyle("fontWeight:bold;padding:13px");
				message = error.getMessage();
				stackTrace = DyadInfrastructure.translate( ExceptionDialog.TECHNICAL_DETAILS ) + " \n" + error.getFullStackTrace();
				dialog.addText(message);
			} else {				
				dialog.setHeading( DyadInfrastructure.translate( ExceptionDialog.UNEXPECTED_ERROR ) );
				dialog.setIconStyle("exception-dialog-error");
				message = error.getMessage() != null ? error.getMessage() : ( error.getFullStackTrace() != null ? error.getFullStackTrace().substring(0, 100) + "..." : "");
				stackTrace = DyadInfrastructure.translate( ExceptionDialog.TECHNICAL_DETAILS ) + " \n" + error.getFullStackTrace();
				dialog.addText(message);
				dialog.setBodyStyle("fontWeight:bold;padding:13px;background-color:#FAF0E6");				
			}			
		} else {
			dialog.setHeading( DyadInfrastructure.translate( ExceptionDialog.UNEXPECTED_ERROR ) );
			dialog.setIconStyle("exception-dialog-error");
			message = e.getMessage(); 
			dialog.addText( DyadInfrastructure.translate( "General System Error!" ) + " \n" + message);
			dialog.setBodyStyle("fontWeight:bold;padding:13px;background-color:#FAF0E6");			
		}
		dialog.show();
	}
	
	/**
	 * 
	 * @param title
	 * @param msg
	 * @param stack
	 * @param exceptionTipe: ClientServerException.TREATED_EXCEPTION or ClientServerException.UNEXPECTED_EXCEPTION.
	 */
	public static void show( String title, String msg, String stack, Integer exceptionTipe ){
		message = msg;
		stackTrace = stack;
		detailsArea.setValue(null);
		
		dialog = new Dialog();
		dialog.setSize(400, 200);
		dialog.setHideOnButtonClick(true);
		dialog.setButtons(Dialog.OK);
		dialog.addButton(details);
		dialog.addButton(sendEmail);
		dialog.addButton(hide);
				
		hide.hide();
		details.show();
		sendEmail.show();
		details.addSelectionListener(new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				dialog.setSize(600, 400);
				detailsArea.setReadOnly(true);
				detailsArea.setValue(stackTrace);
				detailsArea.setSize(550, 300);
				dialog.add(detailsArea);
				details.hide();
				hide.show();
				dialog.layout();
			}
			
		});
		hide.addSelectionListener(new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				dialog.remove(detailsArea);
				dialog.setSize(400, 200);
				hide.hide();
				details.show();
				dialog.layout();
			}
			
		});		
										  
		if ( exceptionTipe == null || exceptionTipe.equals(ClientServerException.TREATED_EXCEPTION) ){
			dialog.setHeading(title);
			dialog.setIconStyle("exception-dialog-treated-error");
			dialog.setBodyStyle("fontWeight:bold;padding:13px");
			dialog.addText(message);
		} else {				
			dialog.setHeading(title);
			dialog.setIconStyle("exception-dialog-error");
			dialog.addText(message);
			dialog.setBodyStyle("fontWeight:bold;padding:13px;background-color:#FAF0E6");
			
		}			
		dialog.show();
	}
}