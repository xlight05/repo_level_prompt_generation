package br.com.dyad.client;


import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import br.com.dyad.client.portal.Application;
import br.com.dyad.client.widget.ClientProcess;
import br.com.dyad.client.widget.ExceptionDialog;
import br.com.dyad.client.widget.LoginDialog;

import com.extjs.gxt.desktop.client.Desktop;
import com.extjs.gxt.desktop.client.Shortcut;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.custom.Portal;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.RootPanel;
import com.extjs.gxt.ui.client.event.ComponentEvent;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DyadInfrastructure implements EntryPoint {

	public static LoginDialog loginDialog;
	public static String sessionId = null;
	public static String userName = null;
	public static Long userKey = null;
	public static String database;
	public static HashMap<String, String> clientMessages = new HashMap<String, String>();
	public static MessageBox messageWindow;
	public static HashMap< String, ClientProcess > clientProcessCreated = new HashMap< String, ClientProcess >();

	/**
	 * This is the entry point method.
	 */
	@SuppressWarnings("unchecked")
	public void onModuleLoad() {
		GenericServiceAsync proxy = getGenericServiceProxy();
		HashMap params = getRpcParams();
		proxy.getServiceValue("br.com.dyad.infrastructure.service.GetSessionId", params, new DyadAsyncCallback(){
			@Override
			public void onSuccess(Object arg0) {
				HashMap result = (HashMap)arg0;
				String sess = (String)result.get(GenericService.SESSION_ID);
								
				if ( sess != null ){
					DyadInfrastructure.sessionId = (String)result.get(GenericService.SESSION_ID);
					DyadInfrastructure.userName = (String)result.get(GenericService.USER_LOGIN);
					DyadInfrastructure.userKey = (Long)result.get(GenericService.USER_KEY);
					DyadInfrastructure.clientMessages = (HashMap<String, String>)result.get("clientMessages");
					DyadInfrastructure.database = (String)result.get(GenericService.GET_DATABASE_FILE);
					
					createDesktop(true);
				} else {
					initializeApp();
				}
			}
		});				
	}

	public static GenericServiceAsync getGenericServiceProxy(){
		GenericServiceAsync serviceProxy = (GenericServiceAsync) GWT.create(GenericService.class);
		ServiceDefTarget target = (ServiceDefTarget) serviceProxy;
		target.setServiceEntryPoint(GWT.getModuleBaseURL() + "genericservice");
		//target.setServiceEntryPoint("genericservice");
		
		return serviceProxy;
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap getRpcParams(){
		HashMap params = new HashMap();
		params.put(GenericService.SESSION_ID, sessionId);
		return params;
	}
	
	public static void createDesktop(boolean restore){
		//Cria a tela inicial da aplicação
		Application.getInstance();
	    
		//Tela de permissões
		//WindowTreeMenu treeMenu = new WindowTreeMenu();
		/*desktop.addWindow(treeMenu);*/
		//treeMenu.setVisible(true);
		//
		
		NodeList<Element> elements = RootPanel.getBodyElement().getElementsByTagName("div");
		
		for (int i = 0; i < elements.getLength(); i++) {
			Element elem = elements.getItem(i); 
			if (elem.getId().equals("connectedDatabase")){
				elem.setInnerHTML("<table><tr><td align=\"right\"><img src=\"resources/logo.gif\"></img></td></tr><tr><td>Database: <b>" + database + "</b></td></tr></table>");
			}
		}
		
		if (restore) {
			GenericServiceAsync proxy = getGenericServiceProxy();
			HashMap map = getRpcParams();
									
			proxy.getServiceValue("br.com.dyad.infrastructure.service.window.RestoreWindowsAfterRefresh", map, new DyadAsyncCallback(){
				@Override
				public void onSuccess(Object arg0) {
					HashMap map = (HashMap)arg0;
					List windows = (List)map.get("windows");
					
					for (Object object : windows) {
						HashMap windowDef = (HashMap)object; 
						
						Application.getInstance().createWindow( 
								(Long)windowDef.get("windowId"), 
								(String)windowDef.get("styleName"), 
								(String)windowDef.get("windowName"), 
								new HashMap(), 
								true,
								false, 
								true );
					}
				}
			});
		}
	}
	
	public static void initializeApp(){
		GenericServiceAsync proxy = getGenericServiceProxy();
		proxy.getSystemInfo(new DyadAsyncCallback(){});
		
		if ( GWT.isScript() ){
			//-- Browser mode
			showLoginDialog();
		} else {
			//-- Hosted mode
			//-- Default login só deve acontecer em hosted mode
			HashMap params = getRpcParams();
			params.put(GenericService.GET_REQUEST, "");
			
			DyadInfrastructure.messageWindow = TimerMessageBox.wait("Login", "", "Verificando usuário padrão...");
			DyadInfrastructure.messageWindow.setModal(true);
			
			proxy.getServiceValue("br.com.dyad.infrastructure.service.DefaultLoginService", params, new DyadAsyncCallback(){
				@Override
				public void onFailure(Throwable arg0) {
					DyadInfrastructure.messageWindow.close();
					showLoginDialog();
					ExceptionDialog.show(arg0);
				}

				public void onSuccess(Object arg0) {
					HashMap result = (HashMap)arg0;
					if ( result == null ){
						DyadInfrastructure.messageWindow.close();
						showLoginDialog();						
					} else {
						DyadInfrastructure.messageWindow.close();
						DyadInfrastructure.database = (String)result.get(GenericService.GET_DATABASE_FILE);
						DyadInfrastructure.messageWindow.close();					
						DyadInfrastructure.sessionId = (String)result.get(GenericService.SESSION_ID);
						DyadInfrastructure.userName = (String)result.get(GenericService.USER_LOGIN);
						DyadInfrastructure.userKey = (Long)result.get(GenericService.USER_KEY);
						DyadInfrastructure.clientMessages = (HashMap<String, String>)result.get("clientMessages");
						createDesktop(false);
					}
				}
			});
		}		
	}
		
	public static native void reload() /*-{
	   $wnd.location.reload();
	}-*/;
	
	public static String translate(String key) {
		return clientMessages.get(key) != null ? clientMessages.get(key) : key; 
	}
	
	public static void showLoginDialog(){
		loginDialog = new LoginDialog();
		loginDialog.setClosable(false);
		loginDialog.addListener(Events.Hide, new Listener<WindowEvent>() {
			public void handleEvent(WindowEvent we) {
				createDesktop(false);
			}
		});		
		loginDialog.show();
	}	
}