package br.com.dyad.client.gxt.widget.customized;

import java.util.List;

import br.com.dyad.client.widget.ClientAction;
import br.com.dyad.client.widget.ClientActionMenuItem;
import br.com.dyad.client.widget.ClientProcess;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;

public class ProcessToolBar extends ToolBar {

	private ClientProcess clientProcess;

	private Button actionBack;    
    private Button actionGo;    
    
	private Button actionHelp;    
    private Button actionPrint;    
    private Button actionLayouts;        

    private SeparatorToolItem actionSeparator;
	
	private Button toolItemToActionMenu;   
    private Menu actionMenu;
    private SeparatorToolItem separatorTotoolItemToActionMenu;

	@SuppressWarnings("unchecked")
	private SelectionListener listener;   
    
	@SuppressWarnings("unchecked")
	public ProcessToolBar( ClientProcess clientProcess ) {
		this.setClientProcess(clientProcess);

	    this.listener = new SelectionListener<ButtonEvent>() {  
			public void componentSelected(ButtonEvent ce) {  
				ClientProcess clientProcess = ProcessToolBar.this.getClientProcess();
				
				if ( ce.getButton() instanceof ActionButtonCustomized ){
					ClientAction act = ((ActionButtonCustomized)ce.getButton()).getClientAction();
					act.onClick();						
				} else if ( ce.getButton() == actionHelp ){
					System.out.println("actionHelp");
					String help = clientProcess.getHelp();

					final DialogCustomized simple = new DialogCustomized("Window Help - " + clientProcess.getWindow().getTitle() );
					if ( help != null && ! help.equals("") ){
						simple.addText( help );  
					} else {
						simple.addText( "No help defined to this process." );
					}
					simple.show();
				} else if ( ce.getButton() == actionPrint ){						
					clientProcess.printProcess();
				} else if ( ce.getButton() == actionBack ){						
					clientProcess.interactionBack();
					System.out.println("Client click Back");
				} else if ( ce.getButton() == actionBack ){						
					clientProcess.interactionBack();
					System.out.println("Client click Back");
				} else if ( ce.getButton() == actionGo ){
					clientProcess.interactionGo();
					System.out.println("Client click Go");
				}
			}
		};
				
    	actionBack = new Button(); 
    	actionBack.disable();
        actionGo = new Button();    
        actionGo.disable();

        toolItemToActionMenu = new Button("More Actions");   
        actionMenu = new Menu();
        separatorTotoolItemToActionMenu = new SeparatorToolItem();
        actionSeparator = new SeparatorToolItem();

	    actionBack.setIconStyle("process-back");
	    actionBack.setToolTip("Previous Page");
	    actionBack.disable();
		actionBack.addSelectionListener(this.listener);
	    this.add(actionBack);

	    actionGo.setToolTip("Next Page");
	    actionGo.setIconStyle("process-go");
	    actionGo.disable();
		actionGo.addSelectionListener(this.listener);
		this.add(actionGo);        
		
		
	    this.add(separatorTotoolItemToActionMenu);

	    toolItemToActionMenu.setIconStyle("process-actionmenu");
	    toolItemToActionMenu.setMenu(actionMenu);
	    this.add(toolItemToActionMenu);
		
	}

	@SuppressWarnings("unchecked")
	public void addPrintAndHelpButtons(){
		this.add(new SeparatorToolItem());

	    actionHelp = new Button();
	    actionHelp.setIconStyle("startmenu-help");
	    actionHelp.setToolTip("Window Help");
	    actionHelp.addSelectionListener(listener);
	    this.add(actionHelp);	    

	    actionPrint = new Button();
	    actionPrint.setIconStyle("process-print");
	    actionPrint.setToolTip("Print");
	    actionPrint.addSelectionListener(listener);
	    this.add(actionPrint);

	    actionLayouts = new Button();
	    actionLayouts.setIconStyle("process-layouts");
	    actionLayouts.setToolTip("Layouts");
	    actionLayouts.addSelectionListener(listener);
	    this.add(actionLayouts);	
	}
	
	@Override
	public void disable() {
		if (rendered) {
			onDisable();
		}
		disabled = true;
		fireEvent(Events.Disable);
	}

	@Override
	public void enable() {
		if (rendered) {
			onEnable();
		}
		disabled = false;
		fireEvent(Events.Enable);
	}

	public ClientProcess getClientProcess() {
		return clientProcess;
	}

	public void setClientProcess(ClientProcess clientProcess) {
		this.clientProcess = clientProcess;
	}

	public Button getActionBack() {
		return actionBack;
	}

	public Button getActionGo() {
		return actionGo;
	}

	public Button getActionHelp() {
		return actionHelp;
	}

	public Button getActionPrint() {
		return actionPrint;
	}

	public Button getActionLayouts() {
		return actionLayouts;
	}

	public Button getToolItemToActionMenu() {
		return toolItemToActionMenu;
	}

	public Menu getActionMenu() {
		return actionMenu;
	}

	public SeparatorToolItem getSeparatorTotoolItemToActionMenu() {
		return separatorTotoolItemToActionMenu;
	}

	public SeparatorToolItem getActionSeparator() {
		return actionSeparator;
	}

	@SuppressWarnings("unchecked")
	public SelectionListener getListener() {
		return listener;
	}

	@SuppressWarnings("unchecked")
	public void addClientActionMenuItem(ClientActionMenuItem actionMenuItem){
		actionMenuItem.addSelectionListener(this.listener);
		this.getActionMenu().add(actionMenuItem);		
	}

	public void enableOrDisableActionMenu() {
		if ( toolItemToActionMenu.getMenu().getItemCount() == 0 ){
			separatorTotoolItemToActionMenu.hide();
			toolItemToActionMenu.hide();
		} else {
			separatorTotoolItemToActionMenu.show();
			toolItemToActionMenu.show();
		}	
	}

	public void showActionMenu() {
		this.getActionMenu().show();
		this.getToolItemToActionMenu().show();		
	}

	public void enableOrDisableActionsBack(boolean interactionBackStackIsEmpty) {
		if ( interactionBackStackIsEmpty ){
			actionBack.disable();
			System.out.println("disableBack");
		} else {
			actionBack.enable();
			System.out.println("enableBack");
		}
	}

	public void enableOrDisableActionsGo(boolean interactionGoStackIsEmpty) {
		if ( interactionGoStackIsEmpty ){
			actionGo.disable();
			System.out.println("disableGo");
		} else {
			actionGo.enable();
			System.out.println("enableGo");
		}
	}

	public void hideActionMenu() {
		this.actionMenu.hide();
		this.toolItemToActionMenu.hide();		
	}
	
	public void hideActions() {
		List<ClientAction> actions = this.clientProcess.getActions();
		for (ClientAction action : actions) {
			action.getButton().hide();
		}					
		this.actionSeparator.hide();
	}

	public void disableActionMenu(){
		this.actionMenu.disable();
		this.toolItemToActionMenu.disable();
	}

}