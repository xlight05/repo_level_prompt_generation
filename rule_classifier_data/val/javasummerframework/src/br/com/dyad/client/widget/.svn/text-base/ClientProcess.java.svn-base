package br.com.dyad.client.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import br.com.dyad.client.ConfirmPasswordDialog;
import br.com.dyad.client.DyadBaseModel;
import br.com.dyad.client.DyadClientListener;
import br.com.dyad.client.DyadInfrastructure;
import br.com.dyad.client.ProgressDialog;
import br.com.dyad.client.PromptDialog;
import br.com.dyad.client.gxt.widget.customized.ProcessToolBar;
import br.com.dyad.client.gxt.widget.customized.ProcessWindowCustomized;
import br.com.dyad.client.portal.Application;
import br.com.dyad.client.portal.WindowTreeMenu;
import br.com.dyad.client.widget.grid.ClientGrid;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

@SuppressWarnings("unchecked")
public class ClientProcess extends DyadBaseModel {
	
	public Boolean getAbort() {
		return get("abort");
	}
	
	public void setAbort(Boolean abort) {
		set("abort", abort);
	}
	
	public boolean isAborted() {
		Boolean abort = getAbort();
		return abort == null ? false : abort.booleanValue();
	}
	
	public ClientObjectSincronizer getObjectSincronizer() {
		return get("objectSincronizer");
	}
	
	public void setObjectSincronizer(ClientObjectSincronizer objectSincronizer) {
		set("objectSincronizer", objectSincronizer);
	}
    
	public ProgressDialog getProgressDialog() {
		return get("progressDialog");
	}

	public void setProgressDialog(ProgressDialog progressDialog) {
		set("progressDialog", progressDialog);
	}

	public HashMap<String, Timer> getTimers() {
		HashMap<String, Timer> result = get("timers");
		
    	if (result == null){
    		result = new HashMap<String, Timer>();
    		setTimers(result);
    	}
    	
		return result;
	}

	public void setTimers(HashMap<String, Timer> timers) {
		set("timers", timers);
	}

	public ClientProcess( Boolean newWindow, Boolean showModal, Long processId ){	    
    	super();  
    	setProcessId(processId);
    	//TODO arrumar quando tirar o desktop
    	//if ( validateStartMenuPermission(processId) ){	    	
			this.initializeDyadProcess( newWindow, showModal );
    	//} else {
    	//	ExceptionDialog.show(new Exception("Access Denied. You have not permission to access this process."));
    	//}
    }
    
    public boolean validateStartMenuPermission( Long itemId ){
		if ( WindowTreeMenu.groupPermissions.get(WindowTreeMenu.administrator) != null ){
			return true;
		}
		return WindowTreeMenu.groupPermissions.get(itemId) != null;
	}
	    
    public boolean findAction( ClientAction action ){    	
    	return this.getActionByText(action.getButton().getText()) != null;
    }
    
    public boolean add(ClientAction action){
    	if ( ! findAction(action) ){
    		action.setProcess(this);
    		action.getButton().addSelectionListener(getProcessToolBar().getListener());
    		getActions().add(action);
    		return true;
    	}
    	return false;
    }	
	
    public boolean findActionMenuItem(ClientActionMenuItem actionMenuItem){
    	return this.getActionMenuItemByText( actionMenuItem.getText() ) != null;
    }
    
    public boolean add(ClientActionMenuItem actionMenuItem){
    	if ( ! findActionMenuItem(actionMenuItem) ){
    		actionMenuItem.setProcess(this);
    		getProcessToolBar().addClientActionMenuItem( actionMenuItem );
    		return true;
    	}
    	return false;
    }	
   
    public boolean findInteraction(ClientInteraction interaction){
    	return this.getInteractionByName(interaction.getName()) != null;
    }
    
    public boolean add(ClientInteraction interaction){
    	if ( ! findInteraction(interaction) ){
    		interaction.setProcess(this);
    		getInteractions().add(interaction);
    		return getWindow().add(interaction.getPanel());
    	}
    	return false;
    }	
    
    
    private void initializeDyadProcess( Boolean newWindow, Boolean showModal ){
    	this.setWindow(new ProcessWindowCustomized( this, newWindow, showModal ));
    	
    	setObjectSincronizer(new ClientObjectSincronizer());
    	
    	setProcessToolBar(new ProcessToolBar( this ));    	
        
        setActions(new ArrayList<ClientAction>());
        
        setInteractions(new ArrayList<ClientInteraction>());
        
    }
    
    public void printProcess(){}
	
    public void interactionGo() {
		getObjectSincronizer().dispatchServerInteractionGo(this.getServerObjectId());
	}

    public void interactionBack() {
    	getObjectSincronizer().dispatchServerInteractionBack(this.getServerObjectId());
	}  
   	
	public void dispatchSetFieldValue(String serverWindowId, String serverGridId, String serverFieldId, Object value ){
		getObjectSincronizer().dispatchSetFieldValue(serverWindowId, serverGridId, serverFieldId, value);
	}	
	
	public void dispatchTableViewSelectRecord(String serverGridId, Long objectId){
		getObjectSincronizer().dispatchTableViewSelectRecord(this.getServerObjectId(), serverGridId, objectId);
	}	

	public void defineProcess(){};
	
	public void finalizeDyadProcess(){
	    getWindow().setTopComponent(getProcessToolBar());
		//verifyIfExistsAnyInteractionsDefined();
		addActionsToToolBar();
		enableOrDisableActionMenu();
		//setDefaultIconStyleToMenuItens();	
		getProcessToolBar().addPrintAndHelpButtons();
	}

	@SuppressWarnings("unused")
	private void verifyIfExistsAnyInteractionsDefined() {
		if ( getInteractions().isEmpty() ){
			Exception error = new Exception("You must define even one interaction.");
			try {
				throw error;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void enableOrDisableActionMenu(){
		getProcessToolBar().enableOrDisableActionMenu();	
	}
	
	/*private void setDefaultIconStyleToMenuItens(){
		for (int i = 0; i < toolItemToActionMenu.getMenu().getItemCount(); i++) {
			MenuItem item = (MenuItem) toolItemToActionMenu.getMenu().getItem(i);
			if ( item.getIconStyle() == null || item.getIconStyle().equals("") ){
				item.setIconStyle("process-action");
			}
		}
	}*/
	
	private void addActionsToToolBar(){	    
		if ( ! getActions().isEmpty() ){
			getProcessToolBar().add( getProcessToolBar().getActionSeparator() );
			
			for (ClientAction action : getActions()) {
				getProcessToolBar().add(action.getButton());
			}
		}	
	}
      
    
    public List<ClientAction> getActions() {
		return get("actions");
	}

	public void setActions(List<ClientAction> actions) {
		set("actions", actions);
	}

	public List<ClientInteraction> getInteractions() {
		return get("interactions");
	}

	public void setInteractions(List<ClientInteraction> interactions) {
		set("interactions", interactions);
	}

	public ProcessToolBar getProcessToolBar() {
		return get("processToolBar");
	}
	
	public void setProcessToolBar(ProcessToolBar processToolBar) {
		set("processToolBar", processToolBar);
	}

	private void showActionMenu() {
		getProcessToolBar().showActionMenu();
	}
	
	private void enableOrDisableActionsBack( boolean interactionBackStackIsEmpty){
		getProcessToolBar().enableOrDisableActionsBack( interactionBackStackIsEmpty );
	}

	private void enableOrDisableActionsGo( 	boolean interactionGoStackIsEmpty ){
		getProcessToolBar().enableOrDisableActionsGo( interactionGoStackIsEmpty );
	}

	
	private void dispatchCurrentInteraction( String currentInteractionName ){
		//currentInteraction.defineInteraction();
		ClientInteraction currentInteraction = this.getInteractionByName(currentInteractionName);		
		if ( currentInteraction == null ){
			System.out.println("Interaction " + currentInteractionName + " não foi encontrada!");
		} else {
			for (ClientInteraction interaction : this.getInteractions()) {
				if ( ! interaction.equals(currentInteraction.getName()) ){
					interaction.getPanel().hide();
				}
			}

			currentInteraction.getPanel().show();
			if (currentInteraction.getClientGrids() != null && currentInteraction.getClientGrids().size() > 0){
				currentInteraction.getClientGrids().get(0).getClientFields().get(0).getFormViewField().focus();
			}
		}
	}
	
	public ClientInteraction getCurrentVisibleInteraction(){
		for (ClientInteraction interaction : this.getInteractions()) {
			if ( interaction.getPanel().isVisible() ){
				return interaction;
			}
		}
		return null;
	}
	
	public Boolean verifyFieldsFromCurrentInteraction(){
		Boolean result = true;
		ClientInteraction interaction = this.getCurrentVisibleInteraction();	
		if ( ! interaction.getClientGrids().isEmpty() ){
			for (Iterator iterator = interaction.getClientGrids().iterator(); iterator.hasNext();) {
				ClientGrid clientGrid = (ClientGrid) iterator.next();
				Boolean ok = clientGrid.validateFields() == null;
				if ( ! ok ){
					result = false;
				}
			}
		}
		return result;
	}
	
	
	public void hideActionsAndActionMenu(){
		getProcessToolBar().hideActions();
		getProcessToolBar().hideActionMenu();
	}

	public void enableAndShowActions( String actionNames ){
		enableAndShowActions( actionNames.split(";") );
	}
	
	public void enableAndShowActions( String[] actionNames ){
		getProcessToolBar().hideActions();
		for (int i = 0; i < actionNames.length; i++) {
			enableAndShowAction( actionNames[i] );
			enableAndShowActionMenuItem( actionNames[i] );
		}
	}
	
	private void enableAndShowAction( String actionName ){
		ClientAction action = this.getActionByText(actionName);
		if ( action != null ){
			action.getButton().enable();
			action.getButton().show();
		}	
	}
	
	private void enableAndShowActionMenuItem( String actionName ){
		ClientActionMenuItem actionMenuItem = this.getActionMenuItemByText(actionName);
		if ( actionMenuItem != null ){
			actionMenuItem.enable();
			actionMenuItem.show();
			showActionMenu();
		}	
	}
	
	public ClientAction getActionByText( String actionName ){
		List<ClientAction> actions = this.getActions();
		for (ClientAction action : actions) {
			if ( action.equals( actionName )){
				return action;
			}
		}					
		return null;
	}
	
	public ClientAction getActionByServerObjectId( String objectId ){
		List<ClientAction> actions = this.getActions();
		for (ClientAction action : actions) {
			if ( action.getServerObjectId().equals( objectId ) ){
				return action;
			}
		}					
		return null;
	}
	
	public ClientInteraction getInteractionByServerObjectId( String objectId ){
		for (ClientInteraction interaction : this.getInteractions()) {
			System.out.println("B: interaction.getServerObjectId()=" + interaction.getServerObjectId());
			if ( interaction.getServerObjectId().equals( objectId ) ){
				return interaction;
			}
		}					
		return null;
	}	

	public ClientActionMenuItem getActionMenuItemByText( String actionMenuItemName ){
		List<Component> itens = getProcessToolBar().getActionMenu().getItems();
		for (Component item : itens) {
			if ( ((ClientActionMenuItem) item ).equals( actionMenuItemName )){
				return (ClientActionMenuItem) item;
			}
		}					
		return null;
	}
	
	
	public ClientInteraction getInteractionByName( String interactionName ){
		List<ClientInteraction> interactions = this.getInteractions();
		for (ClientInteraction interaction : interactions) {
			if ( interaction.equals( interactionName )){
				return interaction;
			}
		}					
		return null;
	}

	public void disableActionsAndActionMenu(){
		disableActions();
		getProcessToolBar().disableActionMenu();
	}
	
	public void disableActions(){
		List<ClientAction> actions = this.getActions();
		for (ClientAction action : actions) {
			action.getButton().disable();
		}						
	}
	
	public ClientAction getFirstEnabledAndVisibleAction(){
		List<ClientAction> actions = this.getActions();
		for (ClientAction action : actions) {
			if (action.getButton().isEnabled() && action.getButton().isVisible() ){
				return action;
			}
		}	
		return null;
	}
	
	public void destroyServerInstance(){
		for (String temp : getTimers().keySet()) {
			((Timer)getTimers().get(temp)).cancel();
		}
		getObjectSincronizer().destroyServerWindow(this.getServerObjectId());
	}

	public void dispatchServerDataGridPrevious( String serverWindowId, String serverDataGridId ){
		getObjectSincronizer().dispatchServerDataGridPrevious(serverWindowId, serverDataGridId);
	}
	
	public void dispatchServerDataGridFirst( String serverWindowId, String serverDataGridId ){
		getObjectSincronizer().dispatchServerDataGridFirst(serverWindowId, serverDataGridId);
	}

	public void dispatchServerDataGridNext( String serverWindowId, String serverDataGridId ){
		getObjectSincronizer().dispatchServerDataGridNext(serverWindowId, serverDataGridId);
	}
	
	public void dispatchServerDataGridLast( String serverWindowId, String serverDataGridId ){
		getObjectSincronizer().dispatchServerDataGridLast(serverWindowId, serverDataGridId);
	}
	

	public void dispatchServerActionClick( String serverWindowId, String serverActionId, HashMap fieldValuesToSave ){
		getObjectSincronizer().dispatchServerActionClick(serverWindowId, serverActionId, fieldValuesToSave);
	}
	
	public void synchronizeProcessWithServerInformation( HashMap config ) throws Exception {	
		setDefaultProperties(config);
		
		System.out.println("Running sincronizeProcessWithServerInformation...");
		System.out.println("Protocolo: " + config.toString() );
		System.out.println("Protocolo->size: " + config.size() );
		
		//Props
		if ( config.containsKey("title") ){
			getWindow().setTitle( (String)config.get("title") );
		}	

		if ( config.containsKey("heading") ){
			getWindow().setHeading((String)config.get("heading"));
		}	
		
		if ( config.containsKey("help") ){
			this.setHelp( (String)config.get("help") );
		}	

		//Lists
		this.sincronizeActions(config);
		
		this.sincronizeActionMenuItens(config);

		this.sincronizeInteractions(config);
		
		//Environment configuration
		System.out.println("interactionBackStackIsEmpty:" + config.get("interactionBackStackIsEmpty"));
		Boolean configInteractionBackStackIsEmpty = (Boolean)config.get("interactionBackStackIsEmpty");
		if ( configInteractionBackStackIsEmpty != null ){
			this.enableOrDisableActionsBack(configInteractionBackStackIsEmpty);
		}	
		
		System.out.println("interactionGoStackIsEmpty:"+config.get("interactionGoStackIsEmpty"));
		Boolean configInteractionGoStackIsEmpty = (Boolean)config.get("interactionGoStackIsEmpty");
		if ( configInteractionGoStackIsEmpty != null ){
			this.enableOrDisableActionsGo(configInteractionGoStackIsEmpty);
		}			

		String configCurrentInteractionName = (String)config.get("currentInteractionName");
		if ( configCurrentInteractionName != null && ! configCurrentInteractionName.equals("") ){
			this.dispatchCurrentInteraction(configCurrentInteractionName);
		}

		String messageToShow = (String)config.get("messageToShow");
		if ( messageToShow != null &&  ! messageToShow.equals("") ){
			//TODO Verificar porque não está disparando
    		MessageBox.alert(getWindow().getTitle(), messageToShow, new SelectionListener(){
				@Override
				public void componentSelected(ComponentEvent ce) {
				}
				
				public void handleEvent(BaseEvent be) {
					// TODO Auto-generated method stub
					
				}
    		});
		}	
		
		String fileUrl = (String)config.get("fileUrl");
		if (fileUrl != null && !fileUrl.equals("")){
			Window.open(fileUrl, "download", 
			"menubar=no,toolbar=no,resizable=no,scrollbars=no,status=no,width=100,height=100,top=100,left=100");
		}
		
		if ( config.containsKey("processToCreate") ){
			HashMap processToCreate = (HashMap)config.get("processToCreate");

			String processClass = (String)processToCreate.get("processClass");
			Long processId = (Long)processToCreate.get("processId");
			String iconStyle = (String)processToCreate.get("iconStyle");
			Boolean newWindow = (Boolean)processToCreate.get("newWindow"); 
			Boolean showModal = (Boolean)processToCreate.get("showModal"); 
			HashMap props = (HashMap)processToCreate.get("props"); 

			Application.getInstance().createWindow( 
					processId, 
					iconStyle, 
					processClass, 
					props,
					newWindow,
					showModal,
					false);				
		}				
		
		if ( config.containsKey("processToSincronizer") ){
			HashMap processToSincronizer = (HashMap)config.get("processToSincronizer");
			Set<String> set = processToSincronizer.keySet();
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				HashMap processConfig = (HashMap)processToSincronizer.get(key);
				ClientProcess clientProcessToSincronizer = DyadInfrastructure.clientProcessCreated.get(key);
				clientProcessToSincronizer.synchronizeProcessWithServerInformation(processConfig);				
			}			
		}							
		
		final String passwordConfirm = (String)config.get("pwdMethodName");
		if ( passwordConfirm != null && !passwordConfirm.equals("") ){
    		ConfirmPasswordDialog.getUserPassword((String)config.get("confirmPwdMessage"), new DyadClientListener(){
    			
				@Override
				public void handleEvent(HashMap params) {
					Boolean result = (Boolean)params.get("result");
					if (result != null && result){ 
						getObjectSincronizer().executeServerMethod(getServerObjectId(), passwordConfirm, params);
					}
				}
    			
    		});
		}
		
		final HashMap<String, String> question = (HashMap<String, String>)config.get("question");
		if ( question != null ){
    		PromptDialog.getUserResponse(question.get("message"), new DyadClientListener(){    			
				@Override
				public void handleEvent(HashMap params) {
					HashMap<String, String> result = new HashMap<String, String>();
					result.put("params", (String)params.get("response"));
					getObjectSincronizer().executeServerMethod(getServerObjectId(), question.get("method"), result);
				}
    			
    		});
		}
				
		final HashMap<String, String> userConfirmationMsg = (HashMap<String, String> )config.get("userConfirmationMsg");
		if ( userConfirmationMsg != null ){
    		PromptDialog.getUserConfirmation(userConfirmationMsg.get("message"), new DyadClientListener(){    			
				@Override
				public void handleEvent(HashMap params) {
					HashMap<String, String> result = new HashMap<String, String>();
					result.put("params", (String)params.get("response"));
					getObjectSincronizer().executeServerMethod(getServerObjectId(), userConfirmationMsg.get("method"), result);
				}
    			
    		});
		}
		
		final ArrayList<HashMap<String, String>> timerList = (ArrayList<HashMap<String, String>>)config.get("timerFunction");
		if (timerList != null){			
			for (HashMap<String, String> temp : timerList) {
				
				final HashMap<String, String> timerFunction = temp;//(HashMap<String, String>)config.get("timerFunction");
				if (timerFunction != null){
					String timerName = (String)timerFunction.get("name");
					
					if (timerFunction.get("action").equals("stop")){
						Timer timer = getTimers().get(timerName);
						if (timer != null){					
							timer.cancel();
							getTimers().remove(timerName);
						}
					} else {				
						Timer timer = new Timer(){
							
							@Override
							public void run() {
								getObjectSincronizer().executeServerMethod(getServerObjectId(), timerFunction.get("method"), null); 				
							}
							
						};
						getTimers().put(timerName, timer);
						timer.scheduleRepeating(Integer.parseInt(timerFunction.get("delay")));
					}
				}
			}
		}
		
		final HashMap<String, String> progress = (HashMap<String, String>)config.get("progress");
		if (progress != null){							
			String action = (String)progress.get("action");
			
			if (action != null && action.equals("progress")){				
				if (getProgressDialog() == null){														
					Integer duration = Integer.parseInt(progress.get("duration"));
					setProgressDialog(new ProgressDialog(this, duration));
					getProgressDialog().setVisible(true);
				}
				
				Integer increment = Integer.parseInt(progress.get("increment"));
				String text = (String)progress.get("text");
				getProgressDialog().getProgressBar().updateProgress((double)increment/(double)getProgressDialog().getDuration(), text == null ? "" : text);
				if (increment != null && increment >= getProgressDialog().getDuration()){
					getProgressDialog().setVisible(false);
					setProgressDialog(null);
				} else if (!getProgressDialog().isVisible()){
					getProgressDialog().setVisible(true);
				}
			} else if (action != null && action.equals("close")){
				getProgressDialog().setVisible(false);
				setProgressDialog(null);
			}
		}
		
	}

	private void sincronizeInteractions(HashMap config) throws Exception {
		Integer size = (Integer)config.get("interactionCount");
		size = size == null ? 0 : size;
		System.out.println("Client - interactionCount: " + size);
		for (int i = 0; i < size; i++) {
			Object ob;			
			ob = config.get("INTERACTION" + i);
			if ( ob != null ){
				HashMap configInteraction = (HashMap)ob;
				if ( ! configInteraction.isEmpty() ){
					ClientInteraction.synchronizeInteractionWithServerInformation( this, configInteraction );
				}	
			}
		}
	}

	private void sincronizeActionMenuItens( HashMap config) {
		Integer size = (Integer)config.get("actionMenuItemCount");
		size = size == null ? 0 : size;
		System.out.println("Client - actionMenuItemCount: " + size);
		for (int i = 0; i < size; i++) {
			Object ob;			
			ob = config.get("ACTIONMENUITEM" + i);
			if ( ob != null ){
				HashMap configActionMenuItem = (HashMap)ob;
				if ( ! configActionMenuItem.isEmpty() ){
					ClientActionMenuItem.sincronizeActionMenuItemWithServerInformation( this, configActionMenuItem );
				}	
			}
		}
	}

	private void sincronizeActions( HashMap config ) throws Exception {
		Integer size = (Integer)config.get("actionCount");
		size = size == null ? 0 : size;
		System.out.println("Client - actionCount: " + size);
		for (int i = 0; i < size; i++) {
			Object ob;			
			ob = config.get("ACTION" + i);
			if ( ob != null ){
				HashMap configAction = (HashMap)ob;
				if ( ! configAction.isEmpty() ){
					ClientAction.synchronizeActionWithServerInformation( this, configAction );
				}	
			}
		}
	}

	public ClientActionMenuItem getActionMenuItemByServerObjectId( String actionMenuItemServerObjectId ) {
		List<Component> itens = getProcessToolBar().getActionMenu().getItems();
		for (Component item : itens) {
			if ( ((ClientActionMenuItem) item ).getServerObjectId().equals(actionMenuItemServerObjectId ) ){
				return (ClientActionMenuItem) item;
			}
		}					
		return null;
	}

	public String getServerObjectId() {
		return get("serverObjectId");
	}

	public void setServerObjectId(String serverWindowId) {
		set("serverObjectId", serverWindowId);
	}

	public Long getProcessId() {
		return get("processId");
	}

	public void setProcessId(Long processId) {
		set("processId", processId);
	}

	public String getHelp() {
		return get("help");
	}

	public void setHelp(String help) {
		set("help", help);
	}


	public String getLastGroup() {
		return get("lastGroup");
	}

	public void setLastGroup(String lastGroup) {
		set("lastGroup", lastGroup);
	}

	public ProcessWindowCustomized getWindow() {
		return get("window");
	}

	public void setWindow(ProcessWindowCustomized window) {
		set("window", window);
	}	
	
	public void getNewServerInstance(String serverWindowPath, Long processId, HashMap props,
			Boolean showMessageWindow) {
		getObjectSincronizer().setClientProcess(this);
		getObjectSincronizer().newServerWindow(serverWindowPath, processId, props, showMessageWindow);
	}
}