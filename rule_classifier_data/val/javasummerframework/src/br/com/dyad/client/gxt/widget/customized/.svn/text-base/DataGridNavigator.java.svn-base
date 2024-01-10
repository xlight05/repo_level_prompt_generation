package br.com.dyad.client.gxt.widget.customized;

import java.util.ArrayList;
import java.util.Iterator;

import br.com.dyad.client.DyadBaseModel;
import br.com.dyad.client.widget.ClientProcess;
import br.com.dyad.client.widget.grid.ClientDataGrid;
import br.com.dyad.client.widget.grid.ClientGrid;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.menu.CheckMenuItem;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.ui.RootPanel;

public class DataGridNavigator extends GridNavigator {

	protected Integer editionMode;	
	protected TextToolItemCustomized actionChangeView = new TextToolItemCustomized();
	protected TextToolItemCustomized actionInsert = new TextToolItemCustomized();
	protected TextToolItemCustomized actionPost = new TextToolItemCustomized(); 
	protected TextToolItemCustomized actionDelete = new TextToolItemCustomized();
	protected TextToolItemCustomized actionCancel = new TextToolItemCustomized();

	protected TextToolItemCustomized pagePrevious = new TextToolItemCustomized();
	protected TextToolItemCustomized recordPrevious = new TextToolItemCustomized();
	protected TextToolItemCustomized recordNext = new TextToolItemCustomized();
	protected TextToolItemCustomized pageNext = new TextToolItemCustomized();

	//private TextToolItemCustomized actionPageCounter = new TextToolItemCustomized();

	protected TextToolItemCustomized actionSearch = new TextToolItemCustomized();
	protected TextToolItemCustomized actionLog = new TextToolItemCustomized(); 
	protected TextToolItemCustomized actionExportation = new TextToolItemCustomized();
	
	protected TextToolItemCustomized actionExport = new TextToolItemCustomized();
	
	protected TextToolItemCustomized actionHelp = new TextToolItemCustomized();
	@SuppressWarnings("unchecked")
	protected SelectionListener actionListener; 	
	
	@SuppressWarnings("unchecked")
	public DataGridNavigator( ClientDataGrid grid ) {
		
		this.setGrid(grid);
		
		this.setActionListener(new SelectionListener<ButtonEvent>() {  
			public void componentSelected(ButtonEvent ce) {  
				if ( ce.getButton() == actionChangeView ){      
					System.out.println("Change View");

					ClientGrid grid = ((TextToolItemCustomized)ce.getButton()).getGrid();
					ClientDataGrid clientDataGrid = (ClientDataGrid)grid; 
					if ( clientDataGrid.getHasTableView() && clientDataGrid.getHasTableView() ){
						ClientProcess process = clientDataGrid.getProcess();
						process.getObjectSincronizer().dispatchServerDataGridChangeView(process.getServerObjectId(), clientDataGrid.getServerObjectId());
					}	
				} else if ( ce.getButton() == actionInsert ){
					System.out.println("Insert");
					ClientGrid grid = ((TextToolItemCustomized)ce.getButton()).getGrid();
					ClientProcess process = grid.getProcess();
					process.getObjectSincronizer().dispatchServerDataGridInsert(process.getServerObjectId(), grid.getServerObjectId());
				} else if ( ce.getButton() == actionPost ){
					System.out.println("Post");
					ClientGrid grid = ((TextToolItemCustomized)ce.getButton()).getGrid();
					String invalidFields = grid.validateFields();
					if ( invalidFields == null ){
						ClientProcess process = grid.getProcess();
						process.getObjectSincronizer().dispatchServerDataGridPost(process.getServerObjectId(), grid.getServerObjectId());
					} else {
						MessageBox.alert("Grid edition...", "You must verify the grid's fields, they are empty or invalid. Check: " + invalidFields, getActionListener());
					}
				} else if ( ce.getButton() == actionDelete ){
					System.out.println("Delete");
					MessageBox.confirm("Delete Record", "Are you sure you want to delete this record?", new Listener<MessageBoxEvent>() {
						@Override
						public void handleEvent(MessageBoxEvent ce) {
							Button btn = ce.getButtonClicked();
							if ( btn.getText().equalsIgnoreCase(Dialog.YES) ){
								ClientGrid grid = actionDelete.getGrid();
								ClientProcess process = grid.getProcess();
								process.getObjectSincronizer().dispatchServerDataGridDelete(process.getServerObjectId(), grid.getServerObjectId());      
							}        
						}
					});                    
				} else if ( ce.getButton() == actionCancel ){
					System.out.println("Cancel");
					MessageBox.confirm("Cancel Record", "Are you sure you want to cancel this record?", new Listener<MessageBoxEvent>() {

						@Override
						public void handleEvent(MessageBoxEvent ce) {
							Button btn = ce.getButtonClicked();
							if ( btn.getText().equalsIgnoreCase(Dialog.YES) ){
								ClientGrid grid = actionCancel.getGrid();
								ClientProcess process = grid.getProcess();
								process.getObjectSincronizer().dispatchServerDataGridCancel(process.getServerObjectId(), grid.getServerObjectId());
							}        
						}
					});                    
				} else if ( ce.getButton() == actionSearch ){					
					ClientDataGrid grid = (ClientDataGrid)((TextToolItemCustomized)ce.getButton()).getGrid();
					String invalidFields = grid.validateFields();
					if ( invalidFields != null ){
						MessageBox.alert("Grid search...", "You must verify the grid's fields, they are empty or invalid. Check: " + invalidFields, getActionListener());
					} else {
						//GridSearchWindow sw = new GridSearchWindow((DataGridNavigator)grid.getGridNavigator() );
						//sw.show();
						if ( grid.getSearchPanel().isVisible() ){
							grid.getSearchPanel().setVisible(false);
						} else {							
							grid.getSearchPanel().setGridFieldsStore();
							grid.getSearchPanel().setVisible(true);
							grid.getSearchPanel().getTextField().focus();
						}
					}	
				} else if ( ce.getButton() == pagePrevious ){
					System.out.println("First");
					ClientGrid grid = ((TextToolItemCustomized)ce.getButton()).getGrid();
					String invalidFields = grid.validateFields();
					if ( invalidFields == null ){
						ClientProcess process = grid.getProcess();
						process.getObjectSincronizer().dispatchServerDataGridFirst(process.getServerObjectId(), grid.getServerObjectId());
					} else {
						MessageBox.alert("Grid edition...", "You must verify the grid's fields, they are empty or invalid. Check: " + invalidFields, getActionListener());
					} 
				} else if ( ce.getButton() == recordPrevious ){
					System.out.println("Previous");
					ClientGrid grid = ((TextToolItemCustomized)ce.getButton()).getGrid();
					String invalidFields = grid.validateFields();
					if ( invalidFields == null ){
						ClientProcess process = grid.getProcess();
						process.getObjectSincronizer().dispatchServerDataGridPrevious(process.getServerObjectId(), grid.getServerObjectId());
					} else {
						MessageBox.alert("Grid edition...", "You must verify the grid's fields, they are empty or invalid. Check: " + invalidFields, getActionListener());
					} 
				} else if ( ce.getButton() == recordNext ){
					System.out.println("Next");
					ClientGrid grid = ((TextToolItemCustomized)ce.getButton()).getGrid();
					String invalidFields = grid.validateFields();
					if ( invalidFields == null ){
						ClientProcess process = grid.getProcess();
						process.getObjectSincronizer().dispatchServerDataGridNext(process.getServerObjectId(), grid.getServerObjectId());
					} else {
						MessageBox.alert("Grid edition...", "You must verify the grid's fields, they are empty or invalid. Check: " + invalidFields, getActionListener());
					} 
				} else if ( ce.getButton() == pageNext ){
					System.out.println("Last");
					ClientGrid grid = ((TextToolItemCustomized)ce.getButton()).getGrid();
					String invalidFields = grid.validateFields();
					if ( invalidFields == null ){
						ClientProcess process = grid.getProcess();
						process.getObjectSincronizer().dispatchServerDataGridLast(process.getServerObjectId(), grid.getServerObjectId());
					} else {
						MessageBox.alert("Grid edition...", "You must verify the grid's fields, they are empty or invalid. Check: " + invalidFields, getActionListener());
					} 
				} else if ( ce.getButton() == actionHelp ){
					System.out.println("actionHelp");
					ClientGrid grid = ((TextToolItemCustomized)ce.getButton()).getGrid();
					String help = grid.getHelp();

					final DialogCustomized simple = new DialogCustomized("Grid Help - " + grid.getFormViewPanel().getTitle() );
					if ( help != null && ! help.equals("") ){
						simple.addText( help );  
					} else {
						simple.addText( "No help defined to this grid." );
					}
					simple.show();      
				} else if ( ce.getButton() == actionLicense ){
					//--TODO a chamda é feita nos itens do menu
					/*System.out.println("actionLicense");
					ClientGrid grid = ((TextToolItemCustomized)ce.getButton()).getGrid();
					ClientProcess process = grid.getProcess();
					process.objectSincronizer.dispatchServerDataGridLicense(process.getServerObjectId(), grid.getServerObjectId(), -99999999999999L);*/
				} else if ( ce.getButton() == actionExport ){
					//-- TODO a chamada é feita nos itens do menu
					/*ClientGrid grid = ((TextToolItemCustomized)ce.getButton()).getGrid();
					ClientProcess process = grid.getProcess();
					process.getObjectSincronizer().dispatchServerDataExport(process.getServerObjectId(), grid.getServerObjectId(), "CSV");*/
				}
				//}
			}
		});    
		
		actionChangeView.setIconStyle("grid-changeview");
		actionChangeView.setToolTip("Change View");
		//actionChangeView.addListener(Events.Select, getActionListener());
		actionChangeView.addSelectionListener(getActionListener());
		actionChangeView.setGrid(grid);
		toolBar.add(actionChangeView);

		toolBar.add(new SeparatorToolItem());    

		actionInsert.setIconStyle("grid-insert");
		actionInsert.setToolTip("Insert");
		//actionInsert.addListener(Events.Select, getActionListener());
		actionInsert.addSelectionListener(getActionListener());
		actionInsert.setGrid(grid);
		toolBar.add(actionInsert);     

		actionPost.setIconStyle("grid-post");
		actionPost.setToolTip("Post");
		//actionPost.addListener(Events.Select, getActionListener());
		actionPost.addSelectionListener(getActionListener());
		actionPost.setGrid(grid);
		toolBar.add(actionPost);     

		actionDelete.setIconStyle("grid-delete");
		actionDelete.setToolTip("Delete");
		//actionDelete.addListener(Events.Select, getActionListener());
		actionDelete.addSelectionListener(getActionListener());
		actionDelete.setGrid(grid);
		toolBar.add(actionDelete);     

		actionCancel.setIconStyle("grid-cancel");
		actionCancel.setToolTip("Cancel");
		//actionCancel.addListener(Events.Select, getActionListener());
		actionCancel.addSelectionListener(getActionListener());
		actionCancel.setGrid(grid);
		toolBar.add(actionCancel);     

		pagePrevious.setIconStyle("grid-pageprevious");
		pagePrevious.setToolTip("First");
		//pagePrevious.addListener(Events.Select, getActionListener());
		pagePrevious.addSelectionListener(getActionListener());
		pagePrevious.setGrid(grid);
		toolBar.add(pagePrevious);     

		recordPrevious.setIconStyle("grid-recordprevious");
		recordPrevious.setToolTip("Previous");
		//recordPrevious.addListener(Events.Select, getActionListener());
		recordPrevious.addSelectionListener(getActionListener());
		recordPrevious.setGrid(grid);
		toolBar.add(recordPrevious);     

		recordNext.setIconStyle("grid-recordnext");
		recordNext.setToolTip("Next");
		//recordNext.addListener(Events.Select, getActionListener());
		recordNext.addSelectionListener(getActionListener());
		recordNext.setGrid(grid);
		toolBar.add(recordNext);     

		pageNext.setIconStyle("grid-pagenext");
		pageNext.setToolTip("Last");
		//pageNext.addListener(Events.Select, getActionListener());
		pageNext.addSelectionListener(getActionListener());
		pageNext.setGrid(grid);
		toolBar.add(pageNext);     

		toolBar.add(new SeparatorToolItem());    

		//actionPageCounter.setText("  1/5  ");
		//toolBar.add(actionPageCounter);  
		//toolBar.add(new SeparatorToolItem());    

		actionSearch.setIconStyle("grid-search");
		actionSearch.setToolTip("Search");
		//actionSearch.addListener(Events.Select, getActionListener());
		actionSearch.addSelectionListener(getActionListener());
		actionSearch.setGrid(grid);
		toolBar.add(actionSearch);

		actionLog.setIconStyle("grid-log");
		actionLog.setToolTip("Record Log");
		//actionLog.addListener(Events.Select, getActionListener());
		actionLog.addSelectionListener(getActionListener());
		actionLog.setGrid(grid);
		toolBar.add(actionLog);

		actionExportation.setIconStyle("grid-exportation");
		actionExportation.setToolTip("Exportation");
		//actionExportation.addListener(Events.Select, getActionListener());
		actionExportation.addSelectionListener(getActionListener());
		actionExportation.setGrid(grid);
		toolBar.add(actionExportation);

		actionHelp.setIconStyle("startmenu-help");
		actionHelp.setToolTip("Help");
		//actionHelp.addListener(Events.Select, getActionListener());
		actionHelp.addSelectionListener(getActionListener());
		actionHelp.setGrid(grid);
		toolBar.add(actionHelp);   
		
		toolBar.add(new SeparatorToolItem());
		
		actionLicense.setText("License");
		actionLicense.setIconStyle("license-icon");
		actionLicense.setToolTip("License");
		//actionLicense.addSelectionListener(getActionListener());
		actionLicense.setGrid(grid);		
		
		/*Menu menu = new Menu();
		ArrayList<DyadBaseModel> licenses = grid.getLicensesAvailable();
		for (Iterator iterator = licenses.iterator(); iterator.hasNext();) {
			DyadBaseModel dyadBaseModel = (DyadBaseModel) iterator.next();				  
			menu.add(new MenuItem( (String)dyadBaseModel.get("name") ));
		}		
		actionLicense.setMenu(menu);*/		
		toolBar.add(actionLicense);
		
		Menu menu = new Menu();							  
		CheckMenuItem itemCSV = new CheckMenuItem( "CSV" );
		itemCSV.setGroup("Formatos");
		itemCSV.addSelectionListener(new SelectionListener<MenuEvent>(){
			@Override
			public void componentSelected(MenuEvent ce) {
				ClientGrid grid = DataGridNavigator.this.getActionLicense().getGrid();
				ClientProcess process = grid.getProcess();
				process.getObjectSincronizer().dispatchServerDataExport(process.getServerObjectId(), grid.getServerObjectId(), "CSV");
			}			
		});
		CheckMenuItem itemPDF = new CheckMenuItem( "PDF" );
		itemPDF.setGroup("Formatos");
		itemPDF.addSelectionListener(new SelectionListener<MenuEvent>(){
			@Override
			public void componentSelected(MenuEvent ce) {
				ClientGrid grid = DataGridNavigator.this.getActionLicense().getGrid();
				ClientProcess process = grid.getProcess();
				process.getObjectSincronizer().dispatchServerDataExport(process.getServerObjectId(), grid.getServerObjectId(), "PDF");
			}			
		});
		menu.add(itemCSV);
		menu.add(itemPDF);						
		
		actionExport.setMenu(menu);
		actionExport.setText("Export");
		actionExport.setIconStyle("license-icon");
		actionExport.setToolTip("Export");
		toolBar.add(actionExport);

		this.setEditionMode(null);
	}	
	
	public ToolBar getToolBar() {
		return toolBar;
	}
	public void setToolBar(ToolBar toolBar) {
		this.toolBar = toolBar;
	}
	public TextToolItemCustomized getActionChangeView() {
		return actionChangeView;
	}
	public void setActionChangeView(TextToolItemCustomized actionChangeView) {
		this.actionChangeView = actionChangeView;
	}
	public TextToolItemCustomized getActionInsert() {
		return actionInsert;
	}
	public void setActionInsert(TextToolItemCustomized actionInsert) {
		this.actionInsert = actionInsert;
	}
	public TextToolItemCustomized getActionPost() {
		return actionPost;
	}
	public void setActionPost(TextToolItemCustomized actionPost) {
		this.actionPost = actionPost;
	}
	public TextToolItemCustomized getActionDelete() {
		return actionDelete;
	}
	public void setActionDelete(TextToolItemCustomized actionDelete) {
		this.actionDelete = actionDelete;
	}
	public TextToolItemCustomized getActionCancel() {
		return actionCancel;
	}
	public void setActionCancel(TextToolItemCustomized actionCancel) {
		this.actionCancel = actionCancel;
	}
	public TextToolItemCustomized getPagePrevious() {
		return pagePrevious;
	}
	public void setPagePrevious(TextToolItemCustomized pagePrevious) {
		this.pagePrevious = pagePrevious;
	}
	public TextToolItemCustomized getRecordPrevious() {
		return recordPrevious;
	}
	public void setRecordPrevious(TextToolItemCustomized recordPrevious) {
		this.recordPrevious = recordPrevious;
	}
	public TextToolItemCustomized getRecordNext() {
		return recordNext;
	}
	public void setRecordNext(TextToolItemCustomized recordNext) {
		this.recordNext = recordNext;
	}
	public TextToolItemCustomized getPageNext() {
		return pageNext;
	}
	public void setPageNext(TextToolItemCustomized pageNext) {
		this.pageNext = pageNext;
	}
	public TextToolItemCustomized getActionSearch() {
		return actionSearch;
	}
	public void setActionSearch(TextToolItemCustomized actionSearch) {
		this.actionSearch = actionSearch;
	}
	public TextToolItemCustomized getActionLog() {
		return actionLog;
	}
	public void setActionLog(TextToolItemCustomized actionLog) {
		this.actionLog = actionLog;
	}
	public TextToolItemCustomized getActionExportation() {
		return actionExportation;
	}
	public void setActionExportation(TextToolItemCustomized actionExportation) {
		this.actionExportation = actionExportation;
	}
	public TextToolItemCustomized getActionHelp() {
		return actionHelp;
	}
	public void setActionHelp(TextToolItemCustomized actionHelp) {
		this.actionHelp = actionHelp;
	}
	public TextToolItemCustomized getActionLicense() {
		return actionLicense;
	}
	public void setActionLicense(TextToolItemCustomized actionLicense) {
		this.actionLicense = actionLicense;
	}

	@SuppressWarnings("unchecked")
	public SelectionListener getActionListener() {
		return actionListener;
	}
	@SuppressWarnings("unchecked")
	public void setActionListener(SelectionListener actionListener) {
		this.actionListener = actionListener;
	}

	public Integer getEditionMode() {		
		return editionMode;
	}

	public void setEditionMode(Integer editionMode) {
		this.editionMode = editionMode;
		this.updateButtonStatus();
	}

	public void updateButtonStatus(){
		if ( this.editionMode == null ){
			this.actionPost.disable();
			this.actionCancel.disable();

			this.actionInsert.enable();            
			this.actionDelete.enable();

		} else if ( editionMode.equals( ClientDataGrid.EDITION_MODE_INSERT ) ) {
			this.actionPost.enable();
			this.actionCancel.enable();

			this.actionInsert.disable();
			this.actionDelete.disable();

		} else if ( editionMode.equals( ClientDataGrid.EDITION_MODE_EDIT ) ) {
			this.actionPost.enable();
			this.actionDelete.enable();
			this.actionCancel.enable();

			this.actionInsert.disable();   
		}
	}

	public void updateNavigation(){
		if ( ((ClientDataGrid)this.grid).getDataListSize() != null && ((ClientDataGrid)this.grid).getDataListSize() > 1 ){
			this.pagePrevious.enable();      
			this.recordPrevious.enable();
			this.recordNext.enable();
			this.pageNext.enable();

			if ( ((ClientDataGrid)this.grid).getIndexCurrentEntity().equals(1) ){
				this.pagePrevious.disable();      
				this.recordPrevious.disable();
			} else if ( ((ClientDataGrid)this.grid).getIndexCurrentEntity().equals(((ClientDataGrid)this.grid).getDataListSize())  ) {
				this.recordNext.disable();      
				this.pageNext.disable();
			}
			
			if ( ((ClientDataGrid)this.grid).getViewMode() != null && ((ClientDataGrid)this.grid).getViewMode().equals( 1 /* TableView */ ) ){
				if ( ((ClientDataGrid)this.grid).getIndexCurrentEntity() + ((ClientDataGrid)this.grid).getMaxRecordCount() >= ((ClientDataGrid)this.grid).getDataListSize()  ){
					this.recordNext.disable();
					this.pageNext.disable();
				} 	
			}
		} else {
			this.pagePrevious.disable();      
			this.recordPrevious.disable();
			this.recordNext.disable();
			this.pageNext.disable();
		}      

		if ( this.getEditionMode() != null && this.getEditionMode().equals( ClientDataGrid.EDITION_MODE_INSERT ) ) {
			this.pagePrevious.disable();      
			this.recordPrevious.disable();
			this.recordNext.disable();
			this.pageNext.disable();
		}			
	}
	
	public ClientDataGrid getGrid() {
		return (ClientDataGrid)this.grid;
	}

	public void setGrid(ClientDataGrid grid) {
		this.grid = grid;
	}
	
	public void executeGridSearch( String fieldName, String searchToken){
		ClientProcess process = grid.getProcess();
		process.getObjectSincronizer().dispatchServerDataGridSearch(process.getServerObjectId(), grid.getServerObjectId(), fieldName, searchToken);
	}
	
	public void executeSearchNextOccur( String fieldName, String searchToken){
		ClientProcess process = grid.getProcess();
		process.getObjectSincronizer().dispatchServerDataSearchNextOccur(process.getServerObjectId(), grid.getServerObjectId(), fieldName, searchToken);
	}
	
	//private TextToolItem toolItemToActionMenu = new TextToolItem("More Actions");   
	//private Menu actionMenu = new Menu();
	//private SeparatorToolItem separatorTotoolItemToActionMenu = new SeparatorToolItem();    
	//private List<ClientAction> actions = new ArrayList<ClientAction>();
	//private SeparatorToolItem actionSeparator = new SeparatorToolItem(); 
}
