package br.com.dyad.client.widget.grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import br.com.dyad.client.DyadBaseModel;
import br.com.dyad.client.gxt.widget.customized.ActionButtonCustomized;
import br.com.dyad.client.gxt.widget.customized.CheckBoxSelectionModelCustomized;
import br.com.dyad.client.gxt.widget.customized.ContentPanelCustomized;
import br.com.dyad.client.gxt.widget.customized.DataGridNavigator;
import br.com.dyad.client.gxt.widget.customized.GridSearchPanel;
import br.com.dyad.client.widget.ClientAction;
import br.com.dyad.client.widget.ClientProcess;
import br.com.dyad.client.widget.field.ClientField;
import br.com.dyad.client.widget.field.ClientMasterDetailField;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.menu.CheckMenuItem;
import com.extjs.gxt.ui.client.widget.menu.Menu;

@SuppressWarnings("unchecked")
public class ClientDataGrid extends ClientGrid { 

	public static final Integer EDITION_MODE_INSERT = 0;
	public static final Integer EDITION_MODE_EDIT = 1;

	public static final Integer VIEW_MODE_FORMVIEW = 0;
	public static final Integer VIEW_MODE_TABLEVIEW = 1;
	
	protected <X extends Object> void callSetDependentProperties(String name, X value) {
		super.callSetDependentProperties(name, value);
		
		if (name.equals("viewMode") || name.equals("canInsert") || name.equals("canDelete")) {
			Integer viewMode = name.equals("viewMode") ? (Integer)value : getViewMode();
			
			if (viewMode != null) {				
				if ( viewMode.equals( 0 /* FormView */ ) ){
					this.getFormViewPanel().setVisible(true);
					this.getTableViewPanel().setVisible(false);			
				} else {
					this.getFormViewPanel().setVisible(false);
					this.getTableViewPanel().setVisible(true);			
				}
			}
		}
		
		if (name.equals("editionMode") || name.equals("canInsert") || name.equals("canDelete")) {
			Integer editionMode = name.equals("editionMode") ? (Integer)value : getEditionMode();
					
			if ( editionMode == null ){
				this.showFieldsAndGridInNonEditionMode();
			} else if ( editionMode.equals( ClientDataGrid.EDITION_MODE_INSERT ) ) {
				this.showFieldsAndGridInEditionMode();
			} else if ( editionMode.equals( ClientDataGrid.EDITION_MODE_EDIT ) ) {
				this.showFieldsAndGridInEditionMode();
			}		
			
			((DataGridNavigator)this.getGridNavigator()).setEditionMode(editionMode);
			
			
			if ( this.getCanInsert() != null && ! this.getCanInsert() ){
				((DataGridNavigator)this.getGridNavigator()).getActionInsert().disable();
			}
			
			if (this.getCanDelete() != null && ! this.getCanDelete() || this.getDataListSize() == null || this.getDataListSize().equals(0) ){
				((DataGridNavigator)this.getGridNavigator()).getActionDelete().disable();
			}
		}
		
		if (name.equals("licensesAvailable")) {			
			this.updateLicensesAvailable();
		}
	}	
	
	public SelectionChangedListener getTableViewChangedListener() {
		SelectionChangedListener tableViewChangedListener = get("tableViewChangedListener");
		
		if (tableViewChangedListener == null) {
			tableViewChangedListener = new SelectionChangedListener(){
				public void selectionChanged(SelectionChangedEvent se) {
					DyadBaseModel model = (DyadBaseModel )se.getSelectedItem();
					Long id = (Long)model.get("id");
					CheckBoxSelectionModelCustomized tableViewSelectionModel = (CheckBoxSelectionModelCustomized)se.getSource();
					ClientDataGrid dataGrid = tableViewSelectionModel.dataGrid;
					//TODO pensar em não setar no servidor toda vez que algo for posicionado na grid. Poderiamos fazer com que no eventos, sempre fosse mandando o ID do
					//registro posicionado.	
					dataGrid.getProcess().dispatchTableViewSelectRecord(dataGrid.getServerObjectId(), id);
					System.out.println( "id = " + id );
				}

				public void handleEvent(BaseEvent be) {
					// TODO Auto-generated method stub
					
				}		
			};
			
			setTableViewChangedListener(tableViewChangedListener);
		}
		
		return get("tableViewChangedListener");
	}
	
	public void setTableViewChangedListener(SelectionChangedListener tableViewChangedListener) {
		set("tableViewChangedListener", tableViewChangedListener);
	}
	
	public CheckBoxSelectionModelCustomized getTableViewSelectionModel() {
		return get("tableViewSelectionModel");
	}

	public void setTableViewSelectionModel( CheckBoxSelectionModelCustomized tableViewSelectionModel) {
		set("tableViewSelectionModel", tableViewSelectionModel);
	}

	public ClientDataGrid( ClientProcess process, String name ){
		super( process, name );
		this.initilizeClientDataGrid();
	}

	public ClientDataGrid( ClientProcess process, String name, Integer columnCount ){
		super( process, name, columnCount );
		this.initilizeClientDataGrid();
	}

	public ClientDataGrid( ClientProcess process, String name, Integer columnCount, HashMap colSpanConfig ){
		super( process, name, columnCount, colSpanConfig );
		this.initilizeClientDataGrid();
	}

	private void initilizeClientDataGrid(){
		addDefaultButtons();
		//this.getMainPanel().add(getSearchPanel());
		this.setIndexCurrentEntity(0);
		this.setDataListSize(0);
		
		this.setTableViewSelectionModel(new CheckBoxSelectionModelCustomized( this ));
		this.getTableViewColumnConfig().add(getTableViewSelectionModel().getColumn());
	}

	public boolean add(ClientField clientField) throws Exception{
		boolean addAField = super.add( clientField );		
		if ( addAField ){
			this.addColumnToTableViewColumnConfig( clientField );
		}
		return addAField;			
	}	
	
	protected void addColumnToTableViewColumnConfig(ClientField clientField) throws Exception {
		ColumnConfig columnConfig = clientField.getColumnConfig();
		if ( columnConfig == null ){
			throw new Exception( "Column config cannot be null. See clientField " + clientField );
		}
		this.getTableViewColumnConfig().add( clientField.getColumnConfig() );	
	}

	private void addDefaultButtons() {      
		setActionListener(new SelectionListener<ButtonEvent>() {  
			public void componentSelected(ButtonEvent ce) {  
				if ( ce.getButton() instanceof ActionButtonCustomized ){
					ClientAction act = ((ActionButtonCustomized)ce.getButton()).getClientAction();
					act.onClick();
				}
			}	
		});    

		//formViewNavigator = new GridNavigator( this );
		//tableViewNavigator = new GridNavigator( this );
		setGridNavigator(new DataGridNavigator( this ));

		this.getMainPanel().setTopComponent( getGridNavigator().getToolBar() );

		//this.formViewPanel.setTopComponent( formViewNavigator.getToolBar() ); 
		//this.tableViewPanel.setTopComponent( tableViewNavigator.getToolBar() );			
	}

	@Override
	public void setEditionMode(Integer editionMode) {
		super.setEditionMode(editionMode);
		
			
	}

	private void showFieldsAndGridInEditionMode() {
		List<ClientField> clientFields = this.getClientFields();
		for (Iterator iterator = clientFields.iterator(); iterator.hasNext();) {
			ClientField clientField = (ClientField) iterator.next();
			if ( ! ( clientField instanceof ClientMasterDetailField ) ){
				clientField.getFormViewField().addStyleName("x-form-edition");
			} 
		}
		this.getFormViewPanel().addStyleName("x-form-edition");
		this.getTableViewPanel().addStyleName("x-form-edition");
	}

	private void showFieldsAndGridInNonEditionMode() {
		List<ClientField> clientFields = this.getClientFields();
		for (Iterator iterator = clientFields.iterator(); iterator.hasNext();) {
			ClientField clientField = (ClientField) iterator.next();
			if ( ! ( clientField instanceof ClientMasterDetailField ) ){
				clientField.getFormViewField().removeStyleName("x-form-edition");
			} 
		}
		this.getFormViewPanel().removeStyleName("x-form-edition");
		this.getTableViewPanel().removeStyleName("x-form-edition");
	}

	private void disableAllFields() {
		List<ClientField> clientFields = this.getClientFields();
		for (Iterator iterator = clientFields.iterator(); iterator.hasNext();) {
			ClientField clientField = (ClientField) iterator.next();
			Field widgetField = clientField.getFormViewField();

			if ( clientField instanceof ClientMasterDetailField ){
				((ClientMasterDetailField)clientField).getClientDataGrid().getFormViewPanel().disable();
			} else {
				widgetField.disable();
			}	
		}
	}

	private void enableAllFieldsBasedInClass() {
		List<ClientField> clientFields = this.getClientFields();
		for (Iterator iterator = clientFields.iterator(); iterator.hasNext();) {
			ClientField clientField = (ClientField) iterator.next();

			if ( clientField instanceof ClientMasterDetailField ){
					ClientDataGrid clientDataGridDetail = ((ClientMasterDetailField)clientField).getClientDataGrid();
					if ( clientDataGridDetail.getEnabledFromClass() ){
						clientDataGridDetail.getFormViewPanel().enable();
					} else {
						clientDataGridDetail.getFormViewPanel().disable();
					} 
			} else {
				clientField.setReadOnly( clientField.getReadOnlyFromClass() );
			}	
		}
	}

	//Override the ext enable because it enable all fields of the component. 
	//Calling this.updateReadOnlyOnFields() we must set the right definition.
	public void enable() {
		this.getFormViewPanel().enable();
		this.updateReadOnlyOnFields();
	}

	private void updateReadOnlyOnFields(){
		if ( this.getDataListSize() != null && this.getDataListSize() > 0 ){
			this.enableAllFieldsBasedInClass();
		} else {
			this.disableAllFields();
		}
	}

	public void updateNavigation(){
		//this.formViewNavigator.updateNavigation();
		//this.tableViewNavigator.updateNavigation();

		((DataGridNavigator)this.getGridNavigator()).updateNavigation();
		
		this.updateReadOnlyOnFields();
	}

	public String getBeanName() {
		return get("beanName");
	}


	public void setBeanName(String beanName) {
		set("beanName", beanName);
	}
	
	public Boolean getCanInsert() {
		return get("canInsert");
	}


	public void setCanInsert(Boolean canInsert) {
		set("canInsert", canInsert);
	}


	public Boolean getCanDelete() {
		return get("canDelete");
	}

	public void setCanDelete(Boolean canDelete) {
		set("canDelete", canDelete);
	}

	public Integer getIndexCurrentEntity() {
		return get("indexCurrentEntity");
	}

	public void setIndexCurrentEntity(Integer indexCurrentEntity) {
		set("indexCurrentEntity", indexCurrentEntity);
	}

	public Integer getDataListSize() {
		return get("dataListSize");
	}

	public void setDataListSize(Integer dataListSize) {
		set("dataListSize", dataListSize);
	}

	public void updateTitle() {
		String formatedHeading = this.getTitle();  
		formatedHeading = formatedHeading + "  " + this.getIndexCurrentEntity() + " / " + this.getDataListSize();
		if ( this.getEditionMode() == null ){
			//nothing
		} else if ( this.getEditionMode().equals(ClientDataGrid.EDITION_MODE_INSERT)){
			formatedHeading += " Inserting...";
		} else if ( this.getEditionMode().equals(ClientDataGrid.EDITION_MODE_EDIT)){
			formatedHeading += " Editing...";
		}
		this.getMainPanel().setHeading(formatedHeading);
	}

	public void setActionListener(SelectionListener actionListener) {
		set("actionListener", actionListener);
	}

	public SelectionListener getActionListener() {
		return get("actionListener");
	}

	public List<ClientMasterDetailField> getClientMasterDetailFields() {
		ArrayList<ClientMasterDetailField> clientMasterDetailFields = get("clientMasterDetailFields");  
		
		if (clientMasterDetailFields == null) {			
			clientMasterDetailFields = new ArrayList<ClientMasterDetailField>();
			setClientMasterDetailFields(clientMasterDetailFields);
		}
		
		return clientMasterDetailFields;
	}

	public void setClientMasterDetailFields(List<ClientMasterDetailField> clientMasterDetailFields) {
		set("clientMasterDetailFields", clientMasterDetailFields);
	}

	public ClientField getClientField() {
		return get("clientField");
	}

	public void setClientField(ClientField clientField) {
		set("clientField", clientField);
	}

	
	public Boolean getHasTableView() {
		return get("hasTableView");
	}

	public void setHasTableView(Boolean hasTableView) {
		set("hasTableView", hasTableView);
	}

	public boolean getHasFormView() {
		return get("hasFormView");
	}

	public void setHasFormView(boolean hasFormView) {
		set("hasFormView", hasFormView);
	}

	public Integer getViewMode() {
		return get("viewMode");
	}

	public void setViewMode(Integer viewMode) {		
		set("viewMode", viewMode);
	}

	public boolean add(ClientDataGrid widget, ClientMasterDetailField clientField){
		if ( clientField.getGrid() != null 
				&& clientField.getGrid().getFormViewPanel().getParent() != null
				&& clientField.getGrid().getFormViewPanel().getParent() == this.getFormViewPanel() ){
			return false;
		} else {
			this.getClientFields().add((ClientField)clientField);
			this.getClientMasterDetailFields().add(clientField);
		}

		clientField.setGrid(this);

		return true;
	}

	public Boolean getEnabledFromClass() {
		return get("enabledFromClass");
	}
	public void setEnabledFromClass(Boolean enabledFromClass) {
		set("enabledFromClass", enabledFromClass);
	}

	public void createTabsToDetailFields(){
		if ( ! getClientMasterDetailFields().isEmpty() ){

			for (Iterator iterator = getClientMasterDetailFields().iterator(); iterator.hasNext();) {
				ClientMasterDetailField clientMasterDetailField = (ClientMasterDetailField) iterator.next();    

				VerticalPanel panel = new VerticalPanel();
				panel.setLayout(new FlowLayout());
				panel.setAutoHeight(true);
				panel.setAutoWidth(true);
				panel.setScrollMode(Scroll.AUTO);				
				
				panel.add( clientMasterDetailField.getClientDataGrid().getMainPanel() );

				TableData col = new TableData();
				col.setColspan(this.getColumnCount());
				
				this.getFormViewPanel().add(panel, col);
			}
		}
	}	

	public void createSearchPanel(){
		GridSearchPanel p = getSearchPanel();
		p.setVisible(false);
		//this.getMainPanel().add(p);
		this.getTableViewPanel().add(p);
		//this.getFormViewPanel().add(p);
	}
	
	public void createTableViewGrid() {
		this.addSelectionChangedListenerToTableViewStore();

		this.getTableViewSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		this.getTableViewGrid().setBorders(true);
		
		this.getTableViewGrid().setSelectionModel(this.getTableViewSelectionModel());
		this.getTableViewGrid().addPlugin(this.getTableViewSelectionModel());
		
		this.getTableViewGrid().setStyleAttribute("borderTop", "none");  
		this.getTableViewGrid().setStyleAttribute("borderBottom", "none");  
		this.getTableViewGrid().setStyleAttribute("borderRight", "none");  
		this.getTableViewGrid().setStyleAttribute("borderLeft", "none");  
		
		this.getTableViewGrid().setAutoHeight(true);
		this.getTableViewGrid().setStripeRows(true);
		
		this.getTableViewPanel().add(this.getTableViewGrid());  		
	}

	public void addSelectionChangedListenerToTableViewStore(){
		this.getTableViewSelectionModel().addSelectionChangedListener( this.getTableViewChangedListener() );		
	}
	
	public void removeSelectionChangedListenerToTableViewStore(){
		this.getTableViewSelectionModel().removeSelectionListener( this.getTableViewChangedListener() );		
	}

	public List<ColumnConfig> getTableViewColumnConfig() {
		List<ColumnConfig> result = get("tableViewColumnConfig"); 
		if (result == null) {
			result = new ArrayList<ColumnConfig>();
			setTableViewColumnConfig(result);
		}
		return result;
	}
	
	public void setTableViewColumnConfig(List<ColumnConfig> tableViewColumnConfig) {
		set("tableViewColumnConfig", tableViewColumnConfig);
	}

	public ListStore<DyadBaseModel> getTableViewStore() {				
		ListStore<DyadBaseModel> result = get("tableViewStore"); 
		if (result == null) {
			result = new ListStore();
			setTableViewStore(result);
		}
		
		return result;
	}

	public void setTableViewStore(ListStore<DyadBaseModel> tableViewStore) {
		set("tableViewStore", tableViewStore);
	}

	public GridSearchPanel getSearchPanel() {
		GridSearchPanel panel = get("searchPanel"); 
		if (panel == null) {
			panel = new GridSearchPanel( (DataGridNavigator)this.getGridNavigator() );
			setSearchPanel(panel);
		}
		
		return panel;
	}
	
	public EditorGrid<DyadBaseModel> getTableViewGrid() {
		EditorGrid<DyadBaseModel> result = get("tableViewGrid"); 
		if (result == null) {
			result = new EditorGrid<DyadBaseModel>( getTableViewStore(), getColumnModel() );
			setTableViewGrid(result);
		}
		
		return result;
	}
	
	public ColumnModel getColumnModel() {
		ColumnModel result = get("columnModel");
		
		if (result == null) {		
			result = new ColumnModel(getTableViewColumnConfig());
			setColumnModel(result);
		}
		
		return result;
	}
	
	public void setColumnModel(ColumnModel columnModel) {
		set("columnModel", columnModel);
	}

	public void setSearchPanel(ContentPanelCustomized panel ){
		set("searchPanel", panel);
	}
	
	public void setTableViewGrid(EditorGrid<DyadBaseModel> tableViewGrid) {
		set("tableViewGrid", tableViewGrid);
	}

	public Integer getMaxRecordCount() {
		return get("maxRecordCount");
	}

	public void setMaxRecordCount(Integer maxRecordCount) {
		set("maxRecordCount", maxRecordCount);
	}

	public Long getIdCurrentEntity() {
		return get("idCurrentEntity");
	}

	public void setIdCurrentEntity(Long idCurrentEntity) {
		set("idCurrentEntity", idCurrentEntity);
	}

	public ArrayList<DyadBaseModel> getLicensesAvailable() {
		return get("licensesAvailable");
	}

	public void setLicensesAvailable(ArrayList<DyadBaseModel> licensesAvailable) {
		set("licensesAvailable", licensesAvailable);
	}
	
	public void updateLicensesAvailable(){
		class MenuItemSelecionListener extends SelectionListener<MenuEvent>{
			DyadBaseModel dyadBaseModel = null;
			public MenuItemSelecionListener( DyadBaseModel dyadBaseModel ){
				this.dyadBaseModel = dyadBaseModel;
			}
			@Override
			public void componentSelected(MenuEvent ce) {
				Long selectedLicense = (Long)dyadBaseModel.get("id");
				System.out.println("License: " + selectedLicense );
				//ClientGrid grid = formViewNavigator.getActionLicense().getGrid();
				ClientGrid grid = getGridNavigator().getActionLicense().getGrid();
				ClientProcess process = grid.getProcess();
				process.getObjectSincronizer().dispatchServerDataGridLicense(process.getServerObjectId(), grid.getServerObjectId(), selectedLicense);
			}
			
		}
		Menu menu = new Menu();
		ArrayList<DyadBaseModel> licenses = this.getLicensesAvailable();
		for (Iterator iterator = licenses.iterator(); iterator.hasNext();) {
			DyadBaseModel dyadBaseModel = (DyadBaseModel) iterator.next();				  
			CheckMenuItem item = new CheckMenuItem( (String)dyadBaseModel.get("name") );
			item.setGroup("Licenses");
			//-- No License
			if ( (Long)dyadBaseModel.get("id") == null ){
				item.setChecked(true);
			}
			item.addSelectionListener(new MenuItemSelecionListener(dyadBaseModel));
			menu.add(item);						
		}
		
		//formViewNavigator.getActionLicense().setMenu(menu);
		//tableViewNavigator.getActionLicense().setMenu(menu);
		getGridNavigator().getActionLicense().setMenu(menu);
	}

	@Override
	protected void updateServerInfo(ClientProcess clientProcess, HashMap config) throws Exception {							
		ClientGrid.sincronizeTableViewPageStore(clientProcess, this, config);								
		
		Integer size = (Integer)config.get("fieldCount");
		size = size == null ? 0 : size;
		
		for (int i = 0; i < size; i++) {
			Object ob;			
			ob = config.get("FIELD" + i);
			if ( ob != null ){
				HashMap configField = (HashMap)ob;
				if ( ! configField.isEmpty() ){
					ClientField.synchronizeFieldWithServerInformation( clientProcess, this, configField, config );
				}	
			}		
		}
		
		//updates the heading with information about title, size and edition mode.
		//this needs to be after set edition mode.
		this.updateTitle();			
		this.updateNavigation();
		
	}
	
}