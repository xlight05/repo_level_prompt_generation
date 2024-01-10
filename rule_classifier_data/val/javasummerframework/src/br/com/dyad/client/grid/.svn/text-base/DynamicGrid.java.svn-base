/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package br.com.dyad.client.grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.dyad.client.DyadAsyncCallback;
import br.com.dyad.client.DyadBaseModel;
import br.com.dyad.client.DyadInfrastructure;
import br.com.dyad.client.GenericServiceAsync;
import br.com.dyad.client.widget.ClientAction;
import br.com.dyad.client.widget.ClientActionMenuItem;
import br.com.dyad.client.widget.ClientProcess;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.ToolBarEvent;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.TimeField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class DynamicGrid extends ContentPanel implements AsyncCallback {
	
	private ToolBar toolBar = new ToolBar();
	private Button actionChangeView = new Button();
	private Button actionInsert = new Button();
	private Button actionDelete = new Button();
	private Button actionCancel = new Button();
	private Button actionSearch = new Button();
	private Button actionLog = new Button();	
	private Button actionExportation = new Button();
	private Button actionHelp = new Button();			
	private Button toolItemToActionMenu = new Button("More Actions");   
    private Menu actionMenu = new Menu();
    private SeparatorToolItem separatorTotoolItemToActionMenu = new SeparatorToolItem();    
    private List<ClientAction> actions = new ArrayList<ClientAction>();
    private SeparatorToolItem actionSeparator = new SeparatorToolItem();
    
	private SelectionListener actionListener;
	protected ClientProcess process;
	protected EditorGrid editor;
	protected LayoutContainer parent;
	protected String className;
	protected ListStore<DyadBaseModel> store;
	protected List<GridColumnDef> cols;
	protected DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd/MM/yyyy");
	protected DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("dd/MM/yyyy hh:mm");
	protected DateTimeFormat timeFormat = DateTimeFormat.getFormat("hh:mm");

	public static final int GRID_HEIGHT = 500;
	public static final int GRID_MARGIN = 20;
	public static final String DECIMAL_SEPARATOR = ",";
	public static final int RECORDS_PER_PAGE = 30;
	public static final String SERVICE_CLASS = "br.com.dyad.infrastructure.service.DataModelService";
	
	/**
	 * Property used for update records of lookup field
	 */
	public static ListStore<DyadBaseModel> tempLookupFieldStore;

	public FieldType getFieldType(String fieldName) {
		for (GridColumnDef e : cols) {
			if (fieldName.equals(e.getFieldName())) {
				return e.getFieldType();
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public DynamicGrid(LayoutContainer parent, String className) {
		this.parent = parent;
		this.className = className;

		setDefaultAttributes();
		addDefaultButtons();		
		getServiceValue();
	}
	
	private void getServiceValue() {
		GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();
		HashMap params = DyadInfrastructure.getRpcParams();
		params.put("bean", className);
		params.put("header", "S");

		proxy.getServiceValue("br.com.dyad.infrastructure.service.DataModelService", params, this);
	}

	private void setDefaultAttributes() {
		this.setCollapsible(true);		
		this.setIconStyle("startmenu-process");  
		this.setLayout(new FitLayout());  
		this.setSize(600, 300);  
		this.setScrollMode(Scroll.AUTO);

		actionListener = new SelectionListener<ComponentEvent>() {  
			public void componentSelected(ComponentEvent ce) {  
				if (ce instanceof ToolBarEvent){
					ToolBarEvent evt = (ToolBarEvent)ce;
					/*if ( evt.getItem() instanceof ClientAction ){
						ClientAction act = (ClientAction)evt.getItem();
						act.onClick();
					} else*/ if ( evt.getItem() == actionChangeView ){						
						System.out.println("Change View");
					} else if ( evt.getItem() == actionInsert ){
						System.out.println("Insert");
					} else if ( evt.getItem() == actionDelete ){
						System.out.println("Delete");
					}
				}
			}
		};  
	
	}

	private void addDefaultButtons() {	   				
		actionChangeView.setIconStyle("grid-changeview");
		actionChangeView.setToolTip("Change View");
		actionChangeView.addListener(Events.Select, actionListener);
		toolBar.add(actionChangeView);

	    toolBar.add(new SeparatorToolItem());				

	    actionInsert.setIconStyle("grid-insert");
	    actionInsert.setToolTip("Insert");
	    actionInsert.addListener(Events.Select, actionListener);
	    toolBar.add(actionInsert);	    

	    actionDelete.setIconStyle("grid-delete");
	    actionDelete.setToolTip("Delete");
	    actionDelete.addListener(Events.Select, actionListener);
	    toolBar.add(actionDelete);	    

	    actionCancel.setIconStyle("grid-cancel");
	    actionCancel.setToolTip("Cancel");
	    actionCancel.addListener(Events.Select, actionListener);
	    toolBar.add(actionCancel);	    

	    toolBar.add(new SeparatorToolItem());				
		
	    actionSearch.setIconStyle("grid-search");
	    actionSearch.setToolTip("Search");
	    actionSearch.addListener(Events.Select, actionListener);
	    toolBar.add(actionSearch);

	    actionLog.setIconStyle("grid-log");
	    actionLog.setToolTip("Record Log");
	    actionLog.addListener(Events.Select, actionListener);
	    toolBar.add(actionLog);

	    actionExportation.setIconStyle("grid-exportation");
	    actionExportation.setToolTip("Exportation");
	    actionExportation.addListener(Events.Select, actionListener);
	    toolBar.add(actionExportation);

	    actionHelp.setIconStyle("startmenu-help");
	    actionHelp.setToolTip("Help");
	    actionHelp.addListener(Events.Select, actionListener);
	    toolBar.add(actionHelp);	    
	    
	    this.setTopComponent( toolBar );
	}

	public void onFailure(Throwable arg0) {
		//ClientServerException.createExceptionWindow("ERROR: " + arg0.getMessage());
	}

	public void onSuccess(Object arg0) {
		try {
			HashMap ret = (HashMap) arg0;
			List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
			List<DyadBaseModel> beans = (List<DyadBaseModel>) ret.get("bean");
			cols = (List<GridColumnDef>) ret.get("columns");
			
			int width = 0;

			for (GridColumnDef c : cols) {
				ColumnConfig column = new ColumnConfig();
				column.setId(c.getFieldName());
				column.setHeader(c.getLabel());
				column.setWidth(c.getWidth());
				column.setHidden(!c.getVisible());

				width += c.getWidth();

				if (c.getFieldType().equals(FieldType.DATE)) {
					DateField field = new DateField();
					field.getPropertyEditor().setFormat(dateFormat);
					field.setAllowBlank(c.getNullable());

					column.setEditor(new CellEditor(field));
					column.setDateTimeFormat(dateFormat);
				} else if (c.getFieldType().equals(FieldType.TIMESTAMP)) {
					DateField field = new DateField();
					field.getPropertyEditor().setFormat(dateTimeFormat);
					field.setAllowBlank(c.getNullable());

					column.setEditor(new CellEditor(field));
					column.setDateTimeFormat(dateTimeFormat);
				} else if (c.getFieldType().equals(FieldType.TIME)) {
					TimeField field = new TimeField();
					//field.getPropertyEditor().setFormat(dateTimeFormat);
					field.setAllowBlank(c.getNullable());

					column.setEditor(new CellEditor(field));
					column.setDateTimeFormat(timeFormat);
				}  else if (c.getFieldType().equals(FieldType.DOUBLE)
						|| c.getFieldType().equals(FieldType.INTEGER)) {
					NumberField field = new NumberField();

					field.setAllowDecimals(c.getFieldType().equals(
							FieldType.DOUBLE));
					//2.0m2
					//field.setDecimalSeparator(DECIMAL_SEPARATOR);
					field.setAllowBlank(c.getNullable());

					column.setAlignment(HorizontalAlignment.RIGHT);
					column.setNumberFormat(NumberFormat.getCurrencyFormat());
					column.setEditor(new CellEditor(field));
				} else if (c.getFieldType().equals(FieldType.BOOLEAN)){
					//CheckBoxSelectionModel<DyadBaseModel> cb = new CheckBoxSelectionModel<DyadBaseModel>();
					//cb.getColumn()
					
					CheckBox check = new CheckBox();
					check.setId(c.getFieldName());
					check.setWidth(c.getWidth());
										
					column.setEditor(new CellEditor(check));
			    } else if (c.getFieldType().equals(FieldType.CLASS)){
			    	ListStore<DyadBaseModel> fieldStore = new ListStore<DyadBaseModel>();
			    	tempLookupFieldStore = fieldStore;
			    	
					ComboBox field = new ComboBox();
					field.setWidth(300);
					field.setDisplayField(c.getDisplayFieldName());		
					field.setStore(fieldStore);
					field.setPageSize(10);
					field.setValidateOnBlur(true);
					field.setAutoValidate(false);
					field.setTitle(c.getLabel());		
					field.setTypeAhead(false);
					field.setValueField("id");					
					column.setEditor(new CellEditor(field));
					
					GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();
					HashMap params = DyadInfrastructure.getRpcParams();
					params.put("bean", c.getBeanName());
					proxy.getServiceValue("br.com.dyad.infrastructure.service.LookupService", params, new DyadAsyncCallback(){
						public void onFailure(Throwable arg0) {
							super.onFailure(arg0);
						}
						public void onSuccess(HashMap arg0) {
							HashMap ret = (HashMap)arg0;
							List<DyadBaseModel> beans = (List<DyadBaseModel>)ret.get("bean");
							tempLookupFieldStore.add(beans);
						}						
					});
			    } else {
					TextField<String> field = new TextField<String>();
					field.setAllowBlank(c.getNullable());
					column.setEditor(new CellEditor(field));
				}

				//if (! c.getFieldType().equals(FieldType.BOOLEAN)){					
					configs.add(column);
				//}
			}			
			
			// Paginador
			final GenericServiceAsync paginatorService = DyadInfrastructure.getGenericServiceProxy();

			RpcProxy rpcProxy = new RpcProxy() {
				@Override
				public void load(Object loadConfig, AsyncCallback callback) {
					final AsyncCallback callback2 = callback;
					HashMap params = DyadInfrastructure.getRpcParams();
					params.put("bean", className);
					params.put("paginator", loadConfig);
					
					paginatorService.getServiceValue(SERVICE_CLASS, params, new AsyncCallback(){

						public void onFailure(Throwable arg0) {
							
						}

						@SuppressWarnings("unchecked")
						public void onSuccess(Object arg0) {
							HashMap map = (HashMap)arg0;
							PagingLoadResult paginator = (PagingLoadResult)map.get("paginator");
							callback2.onSuccess(paginator);							
						}
						
					});
					
				}
			};

			BasePagingLoader loader = new BasePagingLoader(rpcProxy);
			loader.load(0, RECORDS_PER_PAGE);
			PagingToolBar toolBar = new PagingToolBar(RECORDS_PER_PAGE);  
			toolBar.bind(loader);  
			// Paginador
			
			
			store = new ListStore<DyadBaseModel>(loader);
			
			store.addStoreListener(new StoreListener<DyadBaseModel>() {								
				@Override
				public void storeUpdate(StoreEvent se) {
					(new DyadGridStoreManager(se, className)).saveBean();
				}
			});

			ColumnModel cm = new ColumnModel(configs);

			this.setBodyBorder(false);
			this.setHeading("Grid");
			this.setButtonAlign(HorizontalAlignment.CENTER);

			EditorGrid<DyadBaseModel> grid = new EditorGrid<DyadBaseModel>(
					store, cm);
			// grid.setStyleAttribute("borderTop", "none");
			grid.setLoadMask(true);			
			grid.setWidth(width + GRID_MARGIN);
			grid.setHeight(400);			
			grid.setBorders(true);
			
			this.add(grid);
			this.setBottomComponent(toolBar);

			this.setScrollMode(Scroll.AUTO);			
			
			parent.add(this);
			parent.layout();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public boolean findAction( ClientAction action ){    	
    	return this.getActionByText(action.getButton().getText()) != null;
    }
    
    public boolean add(ClientAction action){
    	if ( ! findAction(action) ){
    		//action.setGrid(this);
    		//action.addListener(Events.Select, actionListener);
    		actions.add(action);
    		return true;
    	}
    	return false;
    }	
	
    public boolean findActionMenuItem(ClientActionMenuItem actionMenuItem){
    	return this.getActionMenuItemByText( actionMenuItem.getText() ) != null;
    }
    
    public boolean add(ClientActionMenuItem actionMenuItem){
    	if ( ! findActionMenuItem(actionMenuItem) ){
    		actionMenuItem.setGrid(this);
    		actionMenuItem.addListener(Events.Select, actionListener);
    		actionMenu.add(actionMenuItem);
    		return true;
    	}
    	return false;
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
	
	public ClientActionMenuItem getActionMenuItemByText( String actionMenuItemName ){
		List<Component> itens = actionMenu.getItems();
		for (Component item : itens) {
			if ( ((ClientActionMenuItem) item ).equals( actionMenuItemName )){
				return (ClientActionMenuItem) item;
			}
		}					
		return null;
	}

    public List<ClientAction> getActions() {
		return actions;
	}

	public void setActions(List<ClientAction> actions) {
		this.actions = actions;
	}

	public Menu getActionMenu() {
		return actionMenu;
	}	
	
	public void setActionMenu(Menu actionMenu) {
		this.actionMenu = actionMenu;
	}

	public void finalizeDyadProcess(){
		addActionsToToolBar();
		addActionMenuItensToToolBar();
		enableOrDisableActionMenu();
		setDefaultIconStyleToMenuItens();
	}	
	
	private void addActionMenuItensToToolBar() {
	    toolBar.add(separatorTotoolItemToActionMenu);

		toolItemToActionMenu.setIconStyle("process-actionmenu");
	    toolItemToActionMenu.setMenu(actionMenu);
	    toolBar.add(toolItemToActionMenu);
	}

	private void addActionsToToolBar(){	    
		if ( ! this.actions.isEmpty() ){
			toolBar.add( actionSeparator );
			
			for (ClientAction action : this.actions) {
				toolBar.add(action.getButton());
				//if ( action.getIconStyle() == null || action.getIconStyle().equals("") ){
					//action.setIconStyle("process-action");
				//}			
			}
		}	
	}

	private void enableOrDisableActionMenu(){
		if ( toolItemToActionMenu.getMenu().getItemCount() == 0 ){
			separatorTotoolItemToActionMenu.hide();
			toolItemToActionMenu.hide();
		} else {
			separatorTotoolItemToActionMenu.show();
			toolItemToActionMenu.show();
		}		
	}

	private void setDefaultIconStyleToMenuItens(){
		for (int i = 0; i < toolItemToActionMenu.getMenu().getItemCount(); i++) {
			MenuItem item = (MenuItem) toolItemToActionMenu.getMenu().getItem(i);
			//if ( item.getIconStyle() == null || item.getIconStyle().equals("") ){
				//item.setIconStyle("process-action");
			//}
		}
	}

	public void beforeRender(){
		//this.defineGrid();
		this.finalizeGrid();
	}

	private void finalizeGrid() {
		addActionsToToolBar();
		addActionMenuItensToToolBar();
		enableOrDisableActionMenu();
		setDefaultIconStyleToMenuItens();
	}

	public ClientProcess getProcess() {
		return process;
	}

	public void setProcess(ClientProcess process) {
		this.process = process;
	}

	public EditorGrid getEditor() {
		return editor;
	}

	public void setEditor(EditorGrid editor) {
		this.editor = editor;
	}

}
