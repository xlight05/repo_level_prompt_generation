package br.com.dyad.client.widget.grid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import br.com.dyad.client.DyadBaseModel;
import br.com.dyad.client.DyadBaseTreeModel;
import br.com.dyad.client.gxt.widget.customized.ContentPanelCustomized;
import br.com.dyad.client.gxt.widget.customized.DataGridNavigator;
import br.com.dyad.client.gxt.widget.customized.FormPanelCustomized;
import br.com.dyad.client.gxt.widget.customized.GridNavigator;
import br.com.dyad.client.gxt.widget.customized.GridSearchPanel;
import br.com.dyad.client.widget.ClientAction;
import br.com.dyad.client.widget.ClientInteraction;
import br.com.dyad.client.widget.ClientProcess;
import br.com.dyad.client.widget.field.ClientField;
import br.com.dyad.client.widget.field.ClientLabelGroup;
import br.com.dyad.client.widget.field.ClientMasterDetailField;
import br.com.dyad.client.widget.field.FieldTypes;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.Layout;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Label;

/**
 * @enterprise Dyad & Associados (http://www.dyad.com.br)
 * @author Helton Goncalves (helton@dyad.com.br;heltongoncalves@gmail.com)
 */
public abstract class ClientGrid extends DyadBaseModel {
	
	protected <X extends Object> void callSetDependentProperties(String name, X value) {
		super.callSetDependentProperties(name, value);
		
		if (name.equals("title")) {
			this.getMainPanel().setHeading((String)value);
		}
	}
	
	public HashMap getColSpanConfig() {
		return get("colSpanConfig");
	}
	
	public void setColSpanConfig(HashMap colSpanConfig) {
		set("colSpanConfig", colSpanConfig);
	}
	
	public GridNavigator getGridNavigator() {
		return get("gridNavigator");
	}

	public void setGridNavigator(GridNavigator gridNavigator) {
		set("gridNavigator", gridNavigator);
	}

	public void add(ClientAction action) {
		if (getActions() == null) {
			setActions(new ArrayList<ClientAction>());
		}
		
		getActions().add(action);
	}
	
	public List<ClientAction> getActions() {
		return get("actions");
	}

	public void setActions(List<ClientAction> actions) {
		set("actions", actions);
	}

	public Long getProcessId() {
		return get("processId");
	}

	public void setProcessId(Long processId) {
		set("processId", processId);
	}

	/*protected ContentPanelCustomized mainPanel = new ContentPanelCustomized();

	protected FormPanelCustomized formViewPanel = new FormPanelCustomized();
	protected ContentPanelCustomized tableViewPanel = new ContentPanelCustomized();*/

	
	
	public void setFormViewPanel(FormPanelCustomized formViewPanel) {
		set("formViewPanel", formViewPanel);
	}

	public void setTableViewPanel(ContentPanelCustomized tableViewPanel) {
		set("tableViewPanel", tableViewPanel);
	}

	public ClientGrid( ClientProcess process, String name ){
		setProcessId(process.getProcessId());
		setProcess(process);
		setName(name);
		TableLayout layout = new TableLayout();
		this.initializeClientGrid( layout );
	}
		
	public ClientGrid( ClientProcess process, String name, Integer columnCount ){
		setProcessId(process.getProcessId());
		setProcess(process);
		setName(name);		
		TableLayout layout = new TableLayout();
		layout.setColumns(columnCount);
		this.setColumnCount(columnCount);

		this.initializeClientGrid( layout );
	}
	
	@SuppressWarnings("unchecked")
	public ClientGrid( ClientProcess process, String name, Integer columnCount, HashMap colSpanConfig ){
		setProcessId(process.getProcessId());
		setProcess(process);
		setName(name);
		TableLayout layout = new TableLayout();
		layout.setColumns(columnCount);
		this.setColumnCount(columnCount);
		this.setColSpanConfig(colSpanConfig);
		
		this.initializeClientGrid( layout );
	}
	
	private void initializeClientGrid( Layout layout ){
		this.getMainPanel().setLayout(new FlowLayout());
		this.getMainPanel().setAutoHeight(true);
		this.getMainPanel().setAutoWidth(true);
		this.getMainPanel().setScrollMode(Scroll.AUTO);
		
		this.getFormViewPanel().setLayout(layout);
		this.getFormViewPanel().setClientGrid(this);
		//this.formViewPanel.addListener(Events.AfterLayout, getAfterLayoutListener());
		
		this.getTableViewPanel().setClientGrid(this);
		this.getTableViewPanel().setVisible(false);
		
		this.getMainPanel().add(this.getFormViewPanel());
		this.getMainPanel().add(this.getTableViewPanel());
	}
	
	private Listener<ComponentEvent> getAfterLayoutListener(){
		return new Listener<ComponentEvent>() {
            public void handleEvent(ComponentEvent event) {
                DeferredCommand.addCommand(new Command() {
                    public void execute() { 
                    	int index = 0;
        				if ( index <= getFormViewPanel().getFields().size() - 1 ){
        					Field fld = getFormViewPanel().getFields().get( index );
        					int i = 0;
        					while ( ( fld instanceof LabelField || ! fld.isEnabled() || ! fld.isVisible() || fld.isReadOnly() )  && i < getFormViewPanel().getFields().size() ){
        						try {
        							fld = getFormViewPanel().getFields().get( i );
        							i++;
        						} catch(Exception e){
        							e.printStackTrace();
        							break;
        						}
        					}
        					if ( fld != null && ! fld.isReadOnly() ){
        						fld.focus();
        					}	
        				} 
                    }
                });
            }
        };
	}
	
	public String getName() {
		return get("name");
	}

	public void setName(String name) {
		set("name", name);
	}

	public String getServerObjectId() {
		return get("objectId");
	}

	public void setServerObjectId(String serverObjectId) {
		set("objectId", serverObjectId);
	}	
	
	public boolean add(Label widget ){
		TableData col = new TableData();
		col.setColspan(getColumnCount());
		col.setPadding(5);
		col.setHorizontalAlign(Style.HorizontalAlignment.CENTER);
		this.getFormViewPanel().add(widget, col);
		return true;
	}
	
	public boolean add(ClientField clientField) throws Exception{
		if ( clientField == null ){
			throw new Exception( "ClientField cannot be null. See ClientGrid.add()" );
		}
		if ( clientField.getFormViewField().getParent() == this.getFormViewPanel() ){
			return false;
		} else {			
			getClientFields().add(clientField);
			System.out.println("CAMPO: " + clientField.getName() );
			if ( clientField.getVisible() ){
				if ( ! ( clientField instanceof ClientLabelGroup ) && 
						 ! ( clientField instanceof ClientMasterDetailField ) ){					
					this.getFormViewPanel().add(clientField.getLabelField(), new TableData());
				}
			}	
		}
		
		clientField.setGrid(this);
		
		if ( clientField.getVisible() ){
			TableData col = new TableData();
			col.setWidth("" + ( clientField.getWidth() + 5 ) + "px" );
			if ( getColSpanConfig() != null ){
				Integer colSpan = (Integer)getColSpanConfig().get(clientField.getName());
				if ( colSpan != null ){
					if ( clientField instanceof ClientMasterDetailField ){
						col.setColspan( colSpan * 2 );
					} else {
						col.setColspan( colSpan * 2 - 1 );
					}
				}
			}
			return this.getFormViewPanel().add(clientField.getFormViewField(), col );
		} else {
			return false;
		}
	}

	
	public List<ClientField> getClientFields() {		
		List<ClientField> result = get("clientFields");
		
		if (result == null) {
			result = new ArrayList<ClientField>();
			setClientFields(result);
		}
		
		return result;
	}

	public void setClientFields(List<ClientField> clientFields) {
		set("clientFields", clientFields);
	}
	
	public Integer getEditionMode() {
		return get("editionMode");
	}

	public void setEditionMode(Integer editionMode) {
		set("editionMode", editionMode);
	}

	public ClientField getFieldByName( String fieldName ){
		for (ClientField field : this.getClientFields()) {
			if ( field.equals( fieldName )){
				return field;
			}
		}					
		return null;
	}    

	public ClientField getFieldByObjectId( String objectId ){
		for (ClientField field : this.getClientFields()) {
			if ( objectId.equals(field.getServerObjectId()) ){
				return field;
			}
		};					
		return null;
	}

	public ClientProcess getProcess() {
		return get("process");
	}

	public void setProcess(ClientProcess process) {
		set("process", process);
	}

	@SuppressWarnings("unchecked")
	public String validateFields() {
		String result = null;
		if ( getEditionMode() != null || this instanceof ClientVariableGrid ){
			for (Iterator iterator = getClientFields().iterator(); iterator.hasNext();) {			
				ClientField clientField = (ClientField) iterator.next();
				if ( clientField.getVisible() != null && clientField.getVisible() ){
					if ( ( clientField instanceof ClientMasterDetailField ) ){
						ClientDataGrid clientDataGrid = ((ClientMasterDetailField)clientField).getClientDataGrid();
						String invalidDetailFields = clientDataGrid.validateFields();
						if ( invalidDetailFields != null ){
							if ( result == null ){
								result = "\r\n" + "Detail Grid " + clientDataGrid.getTitle() + ":" + invalidDetailFields;
							} else {
								result += "\r\n" + "Detail Grid " + clientDataGrid.getTitle() + ":" + invalidDetailFields;
							}
						}
					} else if ( ! ( clientField instanceof ClientLabelGroup ) ){
						Field field = clientField.getFormViewField();
						//conversar com o Helton
						if (field != null){							
							Boolean validate = field.validate();
							if ( ! validate ){
								if ( result == null ){
									result = "\r\n-" + clientField.getLabel();
								} else {
									result += "\r\n-" + clientField.getLabel();
								}
							}
							System.out.println( "Name: " + field.getName() + " ; validade: " + validate);
						}
					}
				}
			}
		}	
		return result;
	}

	public String getHelp() {
		return get("help");
	}

	public void setHelp(String help) {
		set("help", help);
	}

	public Integer getColumnCount() {
		return get("columnCount");
	}

	public void setColumnCount(Integer columnCount) {
		set("columnCount", columnCount);
	}

	public FormPanelCustomized getFormViewPanel() {					
		FormPanelCustomized result = get("formViewPanel");
		
		if (result == null) {			
			result = new FormPanelCustomized();
			setFormViewPanel(result);
		}
		
		return result;
	}

	protected ContentPanelCustomized getTableViewPanel() {
		ContentPanelCustomized result = get("tableViewPanel");
		
		if (result == null) {			
			result = new ContentPanelCustomized();
			setTableViewPanel(result);
		}
		
		return result;
	}
	
	public ContentPanelCustomized getMainPanel() {
		ContentPanelCustomized result = get("mainPanel");
		
		if (result == null) {			
			result = new ContentPanelCustomized();
			setMainPanel(result);
		}
		
		return result;
	}

	public void setMainPanel(ContentPanelCustomized mainPanel) {
		set("mainPanel", mainPanel);
	}

	public void setTitle(String title ){		
		set("title", title);		
	}
	
	public String getTitle() {
		return get("title");
	}

	/**
	 * Return the column count. 
	 * @param configGrid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Integer getGridColumnCount( HashMap configGrid ){
		Integer size = (Integer)configGrid.get("fieldCount");
		Integer lastColumn = 0;
		for (int i = 0; i < size; i++) {
			Object ob;			
			ob = configGrid.get("FIELD" + i);
			if ( ob != null ){
				HashMap configField = (HashMap)ob;
				if ( ! configField.isEmpty() ){
					Integer fieldType = (Integer)configField.get("type");
					if ( configField.get("visible") != null && (Boolean)configField.get("visible") && !fieldType.equals(FieldTypes.FIELD_TYPE_MASTERDETAIL) ){
						Integer fieldColumn = (Integer)configField.get("column");
						if ( fieldColumn != null && lastColumn < fieldColumn ){
							lastColumn = fieldColumn;
						}
					}	
				}	
			}		
		}
		lastColumn++;				
		return lastColumn;
	}

	@SuppressWarnings("unchecked")
	public static void synchronizeGridWithServerInformation( ClientProcess clientProcess, ClientInteraction clientInteraction, HashMap configGrid) throws Exception {
		
		String gridServerObjectId = (String)configGrid.get("objectId");
		
		if ( gridServerObjectId == null || gridServerObjectId.equals("") ){
			throw new Exception("Grid server object id must not be null or empty.");
		} 
	
		Integer gridType = (Integer)configGrid.get("type");		
		ClientGrid grid = clientInteraction.getGridByServerObjectId( gridServerObjectId );					
		boolean newGrid = false;
			
		if (grid == null) {
			newGrid = true;
			TableLayout layout = new TableLayout();
			Integer lastColumn = ClientGrid.getGridColumnCount( configGrid );
			Integer columnCount = lastColumn == 1 ? 2 : ( lastColumn * 2 );
			layout.setColumns(columnCount );
			
			if ( gridType.equals(GridTypes.VARIABLE_GRID) ){
				grid = new ClientVariableGrid( clientProcess, (String)configGrid.get("name"), columnCount, ClientField.getFieldsColSpan(configGrid, lastColumn) );
			} else if ( gridType.equals(GridTypes.DATA_GRID) ){
				grid = new ClientDataGrid( clientProcess, (String)configGrid.get("name"), columnCount, ClientField.getFieldsColSpan(configGrid, lastColumn) );
			} else if ( gridType.equals(GridTypes.TREE_GRID) ){
				grid = new ClientTreeGrid( clientProcess, (String)configGrid.get("title"), columnCount, ClientField.getFieldsColSpan(configGrid, lastColumn) );
			}
			
			grid.getFormViewPanel().setLayout(layout);
			
			Integer actionCount = (Integer)configGrid.get("actionCount");				
			if (actionCount != null && actionCount > 0) {
				if (grid.getGridNavigator() == null) {					
					GridNavigator gridNavigator = new GridNavigator();
					gridNavigator.setGrid(grid);
					grid.setGridNavigator(gridNavigator);
					grid.getMainPanel().setTopComponent( gridNavigator.getToolBar() );
				}
				
				grid.getGridNavigator().synchronizeNavigatorActions(configGrid);				
			}
			
			if (grid instanceof ClientTreeGrid) {
				((ClientTreeGrid) grid).initializeTreeProps(configGrid);
				((ClientTreeGrid)grid).createTreeViewGrid();
			}
			
			grid.addProps(configGrid);
			clientInteraction.add(grid);
			
			if (grid instanceof ClientDataGrid) {
				((ClientDataGrid)grid).createSearchPanel();
				((ClientDataGrid)grid).createTableViewGrid();
				((ClientDataGrid)grid).createTabsToDetailFields();				
			}
		} else {			
			grid.addProps(configGrid);
		}
		
		grid.updateServerInfo(clientProcess, configGrid);
		
		if (newGrid && grid instanceof ClientDataGrid) {
			((ClientDataGrid)grid).createTabsToDetailFields();
		}		
	}
	
	@SuppressWarnings("unchecked")
	public static void sincronizeGridDetailWithServerInformation( ClientProcess clientProcess, ClientMasterDetailField clientMasterDetailField, HashMap configGrid) throws Exception {
		
		String gridServerObjectId = (String)configGrid.get("objectId");

		if ( gridServerObjectId == null || gridServerObjectId.equals("") ){
			throw new Exception("Grid detail server object id must not be null or empty.");
		} 

		Integer gridType = (Integer)configGrid.get("type");

		if ( gridType != 20 /*DataGrid*/ ){
			throw new Exception( "This grid is not a dataGrid." );
		}	

		ClientDataGrid clientDataGrid = clientMasterDetailField.getClientDataGrid();
		if ( clientDataGrid.getServerObjectId() == null ){
			clientDataGrid.setServerObjectId( gridServerObjectId );
		} else {
			if ( ! clientDataGrid.getServerObjectId().equals(gridServerObjectId) ){
				throw new Exception( "This grid is not the grid with serverobjectid " + gridServerObjectId + "." );
			}
		}
							
		clientDataGrid.updateServerInfo( clientProcess, configGrid );		
	}

	@SuppressWarnings("unchecked")
	public static void sincronizeTableViewPageStore( ClientProcess clientProces, ClientDataGrid clientDataGrid, HashMap configGrid  ){
			try {
				clientDataGrid.removeSelectionChangedListenerToTableViewStore();

				if ( configGrid.containsKey("tableViewPageStore") ){
					clientDataGrid.getTableViewStore().removeAll();

					ArrayList<DyadBaseModel> page = (ArrayList<DyadBaseModel>)configGrid.get("tableViewPageStore");
					for (Iterator iterator = page.iterator(); iterator.hasNext();) {
						DyadBaseModel dyadBaseModel = (DyadBaseModel) iterator.next();

						Collection<String> propNames = dyadBaseModel.getPropertyNames();
						for (Iterator iterator2 = propNames.iterator(); iterator2.hasNext();) {
							String property = (String) iterator2.next();
							Object value = dyadBaseModel.get(property);
							if ( value != null && value instanceof HashMap ){
								HashMap map = (HashMap)value;	
								map.get( "id");
								map.get( "lookupTemplate" );
								dyadBaseModel.set(property, map.get( "lookupTemplate" ));
							}
						}					
						clientDataGrid.getTableViewStore().add( dyadBaseModel );
					}
					if ( clientDataGrid.getTableViewGrid().isRendered() && page.size() > 0 ){
						if ( clientDataGrid.getIdCurrentEntity() == null ){
							clientDataGrid.getTableViewSelectionModel().select(0, true);
						} else {	
							DyadBaseModel model = (DyadBaseModel) clientDataGrid.getTableViewStore().findModel("id", clientDataGrid.getIdCurrentEntity() );
							clientDataGrid.getTableViewSelectionModel().select(model, true);
						}						
					}
				}
			} finally {
				clientDataGrid.addSelectionChangedListenerToTableViewStore();
			}
	}
	
	protected void addProps(HashMap props) {
		for(Object key : props.keySet()) {
			if (key instanceof String) {				
				set((String)key, props.get(key));
			}
		}
	}
	
	protected abstract void updateServerInfo(ClientProcess clientProcess, HashMap config) throws Exception;
}