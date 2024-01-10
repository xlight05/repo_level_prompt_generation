package br.com.dyad.infrastructure.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.annotations.SendToClient;
import br.com.dyad.infrastructure.aspect.UserInfoAspect;
import br.com.dyad.infrastructure.customization.Customizable;
import br.com.dyad.infrastructure.entity.NavigatorEntity;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;
import br.com.dyad.infrastructure.service.SynchronizerService;
import br.com.dyad.infrastructure.widget.grid.Grid;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("unchecked")
/**
 * @enterprise Dyad & Associados (http://www.dyad.com.br)
 * @author Helton GonÃ§alves (helton@dyad.com.br;heltongoncalves@gmail.com)
 */
public abstract class Window extends BaseServerWidget implements IsSerializable, Customizable {
	public static HashMap<Class,Long> windowIds = new HashMap<Class,Long>();				
	
	private Stack interactionBackStack;
	private Stack interactionGoStack;
	@SendToClient
	private String title; 
	private Long windowId;
	@SendToClient(setNull=true)
	private Boolean abort = false;

	private List<Interaction> interactions;
	private List<ActionMenuItem> actionMenuItens;
	/**
	 * process.grids has references to all grids defined in interactions, including details.
	 */
	private List<Grid> grids;
	
	private Interaction currentInteraction;
	@SendToClient(setNull=true)
	private String messageToShow;
	@SendToClient(setNull=true)
	private HashMap<String, String> question;

	private HashMap windowToSincronizer = new HashMap();
	@SendToClient(setNull=true)
	private String pwdMethodName;
	@SendToClient(setNull=true)
	private String confirmPwdMessage;
	@SendToClient(setNull=true)
	private HashMap<String, String> userConfirmationMsg;	
	@SendToClient(setNull=true)
	private ArrayList<HashMap<String, String>> timerFunction;
	@SendToClient(setNull=true)
	private HashMap<String, String> progress;
	@SendToClient(setNull=true)
	private HashMap windowToCreate;
	
	private String parentWindowId;
	private String startInteractionName;
	
	public static Long getWindowId(Class windowClass){
		List list = PersistenceUtil.executeHql(UserInfoAspect.getDatabase(), "from NavigatorEntity where process = '" + windowClass.getName() + "'");
		
		if (list.size() == 0) {
			return null;
		} else {
			return ((NavigatorEntity)list.get(0)).getId();
		}
	}
	
	public Boolean getAbort() {
		return abort;
	}
	
	public void abortAction() {
		setAbort(true);
	}

	public void setAbort(Boolean abort) {
		this.abort = abort;
	}

	public void setWindowToCreate(HashMap windowToCreate) {
		this.windowToCreate = windowToCreate;
	}

	public HashMap getWindowToCreate() {
		return windowToCreate;
	}
	
	/**
	 * Tenha cuidado ao setar o duration, pois se a position nÃ£o chegar atÃ© o duration, 
	 * o progress vai ficar esperando indefinidamente. Esse comportamento talvez mudado na frente.
	 * */
	public synchronized void showProgress(String message, Integer duration, Integer position){
		HashMap<String, String> temp = new HashMap<String, String>();
		temp.put("action", "progress");
		temp.put("duration", duration.toString());
		temp.put("increment", position.toString());
		temp.put("text", message);
		setProgress(temp);
	}
	
	public synchronized void closeProgress(){
		HashMap<String, String> temp = new HashMap<String, String>();
		temp.put("action", "close");
		setProgress(temp);
	}
	
	public HashMap<String, String> getProgress() {
		return progress;
	}	

	public void setProgress(HashMap<String, String> progress) {
		this.progress = progress;
	}

	public void prompt(String method, String question){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("method", method);
		params.put("message", question);
		setQuestion(params);
	}
	
	public void confirm(String method, String question){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("method", method);
		params.put("message", question);
		setUserConfirmationMsg(params);
	}
	
	public void createClientTimer(String timerName, String methodName, Integer delay){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("method", methodName);
		map.put("delay", delay.toString());
		map.put("name", timerName);
		map.put("action", "start");
		if (timerFunction == null) {
			timerFunction = new ArrayList<HashMap<String, String>>();
		}
		timerFunction.add(map);
	}
	
	public void stopClientTimer(String timerName){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", timerName);
		map.put("action", "stop");
		getTimerFunction().add(map);
	}
			
	public ArrayList<HashMap<String, String>> getTimerFunction() {
		return timerFunction;
	}

	public void setTimerFunction(ArrayList<HashMap<String, String>> timerFunction) {
		this.timerFunction = timerFunction;
	}

	public HashMap<String, String> getUserConfirmationMsg() {
		return userConfirmationMsg;
	}

	public void setUserConfirmationMsg(HashMap<String, String> userConfirmationMsg) {
		this.userConfirmationMsg = userConfirmationMsg;
	}

	public HashMap<String, String> getQuestion() {
		return question;
	}

	public void setQuestion(HashMap<String, String> question) {
		this.question = question;
	}

	public void confirmUserPassword(String methodName, String confirmPwdMessage){
		setPwdMethodName(methodName);
		setConfirmPwdMessage(confirmPwdMessage);
	}	
	
	public String getConfirmPwdMessage() {
		return confirmPwdMessage;
	}

	public void setConfirmPwdMessage(String confirmPwdMessage) {
		this.confirmPwdMessage = confirmPwdMessage;
	}

	public String getPwdMethodName() {
		return pwdMethodName;
	}

	public void setPwdMethodName(String methodName) {
		this.pwdMethodName = methodName;
	}
	
	public Long getWindowId() {
		return windowId;
	}

	public void setWindowId(Long windowId) {
		this.windowId = windowId;
	}

	private void initialize(){
    	this.interactionBackStack = new Stack();
    	this.interactionGoStack = new Stack();
		
    	this.interactions = new ArrayList<Interaction>();
    	this.actionMenuItens = new ArrayList<ActionMenuItem>();
    	
    	this.grids = new ArrayList<Grid>();
	}	
	
	public Window(HttpSession httpSession){
		super();
		setHttpSession(httpSession);
		this.initialize();
	}		
	    
	/*public Window(String title){
		this.setTitle( title );
		this.initialize();
	}*/
	
	private void verifyIfExistsAnyInteractionsDefined() throws Exception {
		if ( this.interactions.isEmpty() ){
			throw new Exception("You must define at least one interaction.");
		} else {
			if ( this.startInteractionName != null && ! this.startInteractionName.equals("") ){
				currentInteraction = this.getInteractionByName( this.startInteractionName ); 
				this.dispatchCurrentInteraction();
			} else {			
				currentInteraction = this.interactions.get(0);			
				this.dispatchCurrentInteraction();
			}	
		}
	}
	
	private void dispatchCurrentInteraction() throws Exception{
		System.out.println("Disparou a definiÃ§Ã£o da interaction: " + currentInteraction.getName() );
		currentInteraction.defineInteraction();
	}		
	
	public void hideActionsAndActionMenu(){
		hideActions();
		hideActionMenu();
	}
	
	private void hideActionMenu() {
		List<ActionMenuItem> actionMenuItens = this.getActionMenuItens();
		for (ActionMenuItem actionMenuItem : actionMenuItens) {
			actionMenuItem.hide();
		}					
	}
	
	private void hideActions() {
		List<BaseAction> actions = this.getActions();
		for (BaseAction action : actions) {
			action.hide();
		}					
	}	

	public void setNextInteraction( Interaction interaction ) throws Exception{
		this.setNextInteraction(interaction.getName());
	}
	
	public void setNextInteraction( String interactionName ) throws Exception{
		this.interactionBackStack.push(currentInteraction);
		this.interactionGoStack.clear();
		this.setAndGoToCurrentInteraction( interactionName );
	}
	
	private NavigatorEntity getNavigatorEntityFromWindowClassName( Class windowClass ){
		Session session = PersistenceUtil.getSession(this.getDatabase());
		String query = "from NavigatorEntity where process ='" + windowClass.getName() + "'";
		List<NavigatorEntity> entityWindow = (List<NavigatorEntity>) PersistenceUtil.executeHql(session, query );
		if ( entityWindow.isEmpty() ){
			return null;
		} else {
			return entityWindow.get(0);
		}		
	}
		
	public void setNextInteraction(String interactionName, Class windowClass, HashMap props, Boolean newWindow, Boolean showModal ) throws Exception {
		Long windowId = null;
		String iconStyle = null;
		String title = null;

		HashMap newWindowToCreate = new HashMap();

		//Nao obrigamos a existencia de uma referencia na sysnavigator pela questao da existencia de process genéricos.
		//Isto pode ser revisto. Helton, Haron e Franklin.
		NavigatorEntity entityWindow = getNavigatorEntityFromWindowClassName(windowClass);
		if ( entityWindow != null ) {
			windowId = entityWindow.getId();
			iconStyle = entityWindow.getCssName();
			title = entityWindow.getName();
			newWindowToCreate.put("title", this.translate(title));
			newWindowToCreate.put("windowId", windowId);
			newWindowToCreate.put("iconStyle", iconStyle);
			
		}	

		newWindowToCreate.put("windowClass", windowClass.getName());
		newWindowToCreate.put("newWindow", newWindow);
		newWindowToCreate.put("showModal", showModal);

		// Adiciona o titulo do window para ser inserido na grid.
		if ( props == null ){
			props = new HashMap();
		}
		props.put("parentWindowId", this.getObjectId() );		
		props.put("startInteractionName", interactionName);
		newWindowToCreate.put("props", props);

		this.setWindowToCreate(newWindowToCreate);
	}

	private void setAndGoToCurrentInteraction( String interactionName ) throws Exception {		
		this.hideActionsAndActionMenu();
		currentInteraction = this.getInteractionByName(interactionName);				
		this.dispatchCurrentInteraction();			
	}

		
	public void interactionGo() throws Exception {
		if ( ! interactionGoStack.isEmpty() ){
			Interaction interactionToGo = (Interaction)interactionGoStack.pop(); 
			interactionBackStack.push(currentInteraction);
			setAndGoToCurrentInteraction(interactionToGo.getName());
		}		
	}

	public void interactionBack() throws Exception {
		if ( ! interactionBackStack.isEmpty() ){
			Interaction interactionToBack = (Interaction)interactionBackStack.pop();
			interactionGoStack.push(currentInteraction);
			setAndGoToCurrentInteraction(interactionToBack.getName());
		}		
	}  
	
	public void clearInteractionNavigationHistory(){
		this.interactionBackStack.clear();
		this.interactionGoStack.clear();
	}	
	
    public boolean findActionMenuItem(ActionMenuItem actionMenuItem){
    	return (ActionMenuItem)this.getObjectByServerId((ArrayList)this.getActionMenuItens(), actionMenuItem.getObjectId() ) != null;
    }
    
    public boolean add(ActionMenuItem actionMenuItem){
    	if ( ! findActionMenuItem(actionMenuItem) ){
    		actionMenuItem.setParent(this);
    		actionMenuItem.setHttpSession(this.getHttpSession());
    		actionMenuItem.hide();
    		actionMenuItem.disable();
    		this.actionMenuItens.add(actionMenuItem);    		
    		return true;
    	}
    	return false;
    }	
    
    public boolean findInteraction(Interaction interaction){
    	return (Interaction)this.getObjectByServerId((ArrayList)this.getInteractions(), interaction.getObjectId() ) != null;
    }
    
    public boolean add(Interaction interaction){
    	if ( ! findInteraction(interaction) ){
    		interaction.setWindow(this);
    		interaction.setHttpSession(this.getHttpSession());
    		this.interactions.add(interaction);
    		return true;
    	}
    	return false;
    }	
    
 	public abstract void defineWindow() throws Exception;
	
	public void onAfterDefineWindow() throws Exception{
		verifyIfExistsAnyInteractionsDefined();
	}

	public List<Interaction> getInteractions() {
		return interactions;
	}

	public void setInteractions(List<Interaction> interactions) {
		this.interactions = interactions;
	}

	
	public BaseAction getActionByText( String actionName ){
		List<BaseAction> actions = this.getActions();
		for (BaseAction action : actions) {
			if ( action.getText().equalsIgnoreCase( actionName )){
				return action;
			}
		}					
		return null;
	}
	
	public ActionMenuItem getActionMenuItemByText( String actionMenuItemName ){
		List<ActionMenuItem> itens = this.getActionMenuItens();
		for (ActionMenuItem item : itens) {
			if ( item.getText().equalsIgnoreCase( actionMenuItemName )){
				return item;
			}
		}					
		return null;
	}
	
	
	public Interaction getInteractionByName( String interactionName ){
		List<Interaction> interactions = this.getInteractions();
		for (Interaction interaction : interactions) {
			if ( interaction.getName().equalsIgnoreCase( interactionName )){
				return interaction;
			}
		}					
		return null;
	}

	public void enableAndShowActions( BaseAction action ){
		enableAndShowActions( action.getText() );
	}

	public void enableAndShowActions( String actionNames ){
		enableAndShowActions( actionNames.split(";") );
	}
	
	public void enableAndShowActions( String[] actionNames ){
		//this.hideActions();
		for (int i = 0; i < actionNames.length; i++) {
			enableAndShowAction( actionNames[i] );
			enableAndShowActionMenuItem( actionNames[i] );
		}
	}
	
	private void enableAndShowAction( String actionName ){
		BaseAction action = this.getActionByText(actionName);
		if ( action != null ){
			action.enable();
			action.show();
		}	
	}
	
	private void enableAndShowActionMenuItem( String actionName ){
		ActionMenuItem actionMenuItem = this.getActionMenuItemByText(actionName);
		if ( actionMenuItem != null ){
			actionMenuItem.enable();
			actionMenuItem.show();			
		}	
	}

	public Action getActionByServerObjectId( String objectId ){
		Action act = (Action)this.getObjectByServerId((ArrayList)this.getActions(), objectId);
		
		if (act == null) {			
			for (Grid g : getGrids()) {
				for (BaseAction temp : g.getActions()) {					
					if (temp.getObjectId().equals(objectId)) {
						return (Action)temp;
					}
				}
			}
		}
		
		return act;
	}

	public Grid getGridByServerObjectId( String objectId ){
		return (Grid)this.getObjectByServerId((ArrayList)this.getGrids(), objectId);
	}	
	
	public String getTitle() {		
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<ActionMenuItem> getActionMenuItens() {
		return actionMenuItens;
	}

	public void setActionMenuItens(List<ActionMenuItem> actionMenuItens) {
		this.actionMenuItens = actionMenuItens;
	}
	
	public String getMessageToShow() {
		return messageToShow;
	}

	public void setMessageToShow(String messageToShow) {
		this.messageToShow = messageToShow;
	}

	public void alert(String message){
		if ( this.getMessageToShow() != null && ! this.getMessageToShow().equals("") ){
			this.setMessageToShow(this.getMessageToShow() + "\r\n" + message);
		} else {
			this.setMessageToShow(message);
		}
	}
		
	protected void addWindowToSincronization( Window window ) throws Exception {
		windowToSincronizer.put( window.getObjectId(), window.toClientSynchronizer() );
	}		
	
	protected void addPropsToClientSincronizer() throws Exception {		
		//-- Environment configuration
		clientProps.put("interactionBackStackIsEmpty", this.interactionBackStack.isEmpty() );
		clientProps.put("interactionGoStackIsEmpty", this.interactionGoStack.isEmpty() );
		clientProps.put("currentInteractionName", currentInteraction.getName() );
		
		//-- Objects lists
		HashMap listsConfig;
		List<BaseAction> actions = this.getActions();
		int i = 0;
		for (BaseAction action : actions) {			
			listsConfig = action.toClientSynchronizer();
			if ( ! listsConfig.isEmpty() ){
				clientProps.put( "ACTION" + i, listsConfig  );
				i++;
			}
		}
		System.out.println("Server - actionCount" + i);
		if ( i > 0 ){
			clientProps.put("actionCount", i );
		}
		
		List<ActionMenuItem> itens = this.getActionMenuItens();
		i = 0;
		for (ActionMenuItem item : itens) {
			listsConfig = item.toClientSynchronizer();
			if ( ! listsConfig.isEmpty() ){
				clientProps.put( "ACTIONMENUITEM" + i, listsConfig );
				i++;
			}	
		}					
		System.out.println("Server - actionMenuItemCount" + i);
		if ( i > 0 ){
			clientProps.put("actionMenuItemCount", i );
		}
		
		List<Interaction> interactions = this.getInteractions();
		i = 0;
		for (Interaction interaction : interactions) {
			listsConfig = interaction.toClientSynchronizer();
			if ( ! listsConfig.isEmpty() ){
				clientProps.put( "INTERACTION" + i, listsConfig );
				i++;
			}	
		}
		System.out.println("Server - interactionCount" + i);
		if ( i > 0 ){
			clientProps.put("interactionCount", i );
		}	
		
		if ( ! this.windowToSincronizer.isEmpty() ){
			HashMap windowToSincronizerMaps = (HashMap)this.windowToSincronizer.clone();
			this.clientProps.put( "windowToSincronizer", windowToSincronizerMaps );			
			this.windowToSincronizer.clear();
		}
	}

	public List<Grid> getGrids() {
		return grids;
	}	
	
	public Boolean add(Grid grid) throws Exception{
		if ( this.grids.indexOf(grid) == -1 ){
			grid.setWindow(this);
			this.grids.add(grid);
			return true;
		}
		return false;
	}			
	
	public void populateProps(HashMap props) throws Exception {
		if ( props == null ) {
			throw new Exception(
					"Parameter props cannot be null. See Window.populateProps()");
		}
		ReflectUtil.populateBean(this, props);
	}

	public String getParentWindowId() {
		return parentWindowId;
	}

	public void setParentWindowId(String parentWindowId) {
		this.parentWindowId = parentWindowId;
	}
	
	public String getStartInteractionName() {
		return startInteractionName;
	}

	public void setStartInteractionName(String startInteractionName) {
		this.startInteractionName = startInteractionName;
	}

	public Window getParentWindow(){
		return SynchronizerService.getWindowByWindowId(getHttpSession(), this.parentWindowId );
	}

	/*
	 * This method can return null if parent process was destroyed.
	 * */
	public Window getWindowByWindowId( String serverObjectId ){
		return SynchronizerService.getWindowByWindowId(getHttpSession(), serverObjectId );
	}
	
	public void onClose(){}

}