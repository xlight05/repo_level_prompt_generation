package br.com.dyad.client.gxt.field.customized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


import br.com.dyad.client.DataClass;
import br.com.dyad.client.DyadAsyncCallback;
import br.com.dyad.client.DyadBaseModel;
import br.com.dyad.client.DyadInfrastructure;
import br.com.dyad.client.GenericServiceAsync;
import br.com.dyad.client.TimerMessageBox;
import br.com.dyad.client.gxt.widget.customized.FormPanelCustomized;
import br.com.dyad.client.portal.Application;
import br.com.dyad.client.portal.WindowTreeMenu;
import br.com.dyad.client.widget.ClientAction;
import br.com.dyad.client.widget.ClientProcess;
import br.com.dyad.client.widget.ExceptionDialog;
import br.com.dyad.client.widget.field.ClientField;
import br.com.dyad.client.widget.field.ClientLookupField;
import br.com.dyad.client.widget.grid.ClientGrid;
import br.com.dyad.client.widget.grid.ClientVariableGrid;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.PagingModelMemoryProxy;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.ToolBarEvent;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TriggerField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.ui.Widget;

@SuppressWarnings("unchecked")
public class LookupFieldCutomized extends TriggerField<String> implements Comparator{

	private ClientLookupField clientField;

	private String oldRawValue;
	private String beanName;
	private Window lookupWindow;
	private	Boolean enabledFromClass;
	
	private ArrayList<DyadBaseModel> lookupStore = null;
	private ContentPanel gridPanel = null;
	private Grid<DyadBaseModel> grid;
	protected GenericServiceAsync proxy = null;
	protected LookupAsyncCallback lookupAsyncCallback;
	private String lastValidValue = null;	
	private boolean validValue = false;	
	private final Listener listener;
	private final Listener doubleClickListener; 
	private String findExpress;
	private Boolean isClassLookup = false;
	public static String SEARCH_TOKEN = "@#searchToken#@";
	
	private Long idValue;
	
	public ColumnConfig columnConfig = null; 
	
	/**
	 * Used for multiple lookup.
	 */
	private String idValues;
	
	public Long getIdValue() {
		return idValue;
	}

	public void setIdValue(Long idValue) {
		this.idValue = idValue;
	}
	
	public Boolean getIsClassLookup() {
		return isClassLookup;
	}

	public void setIsClassLookup(Boolean isClassLookup) {
		this.isClassLookup = isClassLookup;
	}
	
	public void setFindExpress(String findExpress) {
		this.findExpress = findExpress;
	}
	
	public String getFindExpress( String searchToken ){
		return findExpress == null ? findExpress : ( findExpress.replaceAll(SEARCH_TOKEN, searchToken) ) ;
	}

	public String getIdValues() {
		return idValues;
	}

	public void setIdValues(String idValues) {
		this.idValues = idValues;
	}	

	public LookupFieldCutomized( String beanName){
		super();

		this.listener = new Listener<BaseEvent>(){
			public void handleEvent(BaseEvent be) {
				if ( ! LookupFieldCutomized.this.disabled  ){
					getOccurs();
				}	
			}			
		};	
		
		this.setBeanName(beanName);
		//this.setTriggerStyle("lookup-search");
		this.addListener(Events.TriggerClick, listener );			
		
		
		this.doubleClickListener = new Listener<FieldEvent>() {
			public void handleEvent(FieldEvent be) {
				ClientLookupField clientField = LookupFieldCutomized.this.getClientField();
				ClientProcess clientProcess = clientField.getGrid().getProcess(); 
				//DesktopMenu desktopMenu = clientProcess.getDesktopMenu();
				
				HashMap props = new HashMap();
				String label = clientField.getLabel();
				if ( label != null && label.charAt(label.length()-1) == '*' ){
					label = label.substring(0, label.length()-1);
				}
				props.put("title", label );
				props.put("beanName", clientField.getBeanName() );
				props.put("idToFind", ((LookupFieldCutomized) clientField.getFormViewField()).getIdValue() );
				
				Application.getInstance().createWindow(
						clientProcess.getProcessId(), 
						null, 
						"br.com.dyad.infrastructure.navigation.GenericEntityBeanWindow",
						props,
						true,
						false,
						true
				);			
			}
		};		
		this.sinkEvents(Events.OnDoubleClick.getEventCode());
		this.addListener(Events.OnDoubleClick, this.doubleClickListener);	
		this.setSelectOnFocus(true);
	}
	
	class LookupSelectionListener extends SelectionListener<ButtonEvent>{
		Window lookupPanel;
		LookupFieldCutomized lookup;
		public LookupSelectionListener( LookupFieldCutomized lookup, Window lookupPanel ){
			this.lookupPanel = lookupPanel;
			this.lookup = lookup;
		}
		
		public void componentSelected(ToolBarEvent tbe) {
			System.out.println("ID: " + tbe.getItem().getItemId());
			String OK_ID = "btnOkLookup";
			String CANCEL_ID = "btnCancelLookup";
			String FIRST_ID = "btnFirstLookup";
			String PREVIOUS_ID = "btnPreviousLookup";
			String NEXT_ID = "btnNextLookup";
			String LAST_ID = "btnLastLookup";
			if ( tbe.getItem().getItemId().equals(OK_ID) ){				
				if ( grid.getSelectionModel().getSelectedItem() == null ){
					MessageBox msg = new MessageBox();
					msg.setMessage("You must to select at least one record!");
					msg.setIcon(MessageBox.INFO);
					msg.show();
				} else {
					String selectedRecord;
					if (isClassLookup){
						selectedRecord = grid.getSelectionModel().getSelectedItem().get(this.lookup.getName());
					} else {
						selectedRecord = grid.getSelectionModel().getSelectedItem().get(this.lookup.getName());
					}					
					Long selectedKey = grid.getSelectionModel().getSelectedItem().get("id");
					this.lookup.setIdValue(selectedKey);
					this.lookup.setValue(selectedRecord.replaceAll("&nbsp;", ""));
					this.lookup.setLastValidValue(selectedRecord.replaceAll("&nbsp;", ""));
					this.lookup.setValidValue(true);
					this.lookupPanel.hide();
					
					lookup.focus();
					lookup.clearInvalid();
					this.lookup.dispatchValueSincronizer();
					//lookup.tryDispatchFirstActionIfFieldIsLastField();
			 		//lookup.goToNextFieldOnKeyPress();
				}				
			} else if ( tbe.getItem().getItemId().equals(CANCEL_ID) ){
				this.lookupPanel.hide();
				lookup.focus();
			} else if ( tbe.getItem().getItemId().equals(FIRST_ID) ){
					
			} else if ( tbe.getItem().getItemId().equals(PREVIOUS_ID) ){
				
			} else if ( tbe.getItem().getItemId().equals(NEXT_ID) ){
				
			} else if ( tbe.getItem().getItemId().equals(LAST_ID) ){
				
			}
		}
		@Override
		public void componentSelected(ButtonEvent ce) {
			//System.out.println("ID: " + ce.getItem().getItemId());
			String OK_ID = "btnOkLookup";
			String CANCEL_ID = "btnCancelLookup";
			String FIRST_ID = "btnFirstLookup";
			String PREVIOUS_ID = "btnPreviousLookup";
			String NEXT_ID = "btnNextLookup";
			String LAST_ID = "btnLastLookup";
			if ( ce.getButton().getItemId().equals(OK_ID) ){				
				if ( grid.getSelectionModel().getSelectedItem() == null ){
					MessageBox msg = new MessageBox();
					msg.setMessage("You must to select at least one record!");
					msg.setIcon(MessageBox.INFO);
					msg.show();
				} else {
					String selectedRecord;
					if (isClassLookup){
						selectedRecord = grid.getSelectionModel().getSelectedItem().get(this.lookup.getName());
					} else {
						selectedRecord = grid.getSelectionModel().getSelectedItem().get(this.lookup.getName());
					}
					Long selectedKey = grid.getSelectionModel().getSelectedItem().get("id");
					this.lookup.setIdValue(selectedKey);
					this.lookup.setValue(selectedRecord.replaceAll("&nbsp;", ""));
					this.lookup.setLastValidValue(selectedRecord.replaceAll("&nbsp;", ""));
					this.lookup.setValidValue(true);
					this.lookupPanel.hide();
					
					lookup.focus();
					lookup.clearInvalid();
					this.lookup.dispatchValueSincronizer();
					//lookup.tryDispatchFirstActionIfFieldIsLastField();
			 		//lookup.goToNextFieldOnKeyPress();
				}				
			} else if ( ce.getButton().getItemId().equals(CANCEL_ID) ){
				this.lookupPanel.hide();
				lookup.focus();
			} else if ( ce.getButton().getItemId().equals(FIRST_ID) ){
					
			} else if ( ce.getButton().getItemId().equals(PREVIOUS_ID) ){
				
			} else if ( ce.getButton().getItemId().equals(NEXT_ID) ){
				
			} else if ( ce.getButton().getItemId().equals(LAST_ID) ){
				
			}			
		}
	}	
	
	public void tryDispatchFirstActionIfFieldIsLastField(){
		FormPanelCustomized formViewPanel = (FormPanelCustomized)this.getParent(); 
		ClientGrid grid = (formViewPanel).getClientGrid(); 			

		if ( grid instanceof ClientVariableGrid && formViewPanel.getFields().get( formViewPanel.getFields().size() -1 ) == this ){
			ClientProcess process = grid.getProcess();
			ClientAction action = process.getFirstEnabledAndVisibleAction();
			if ( action != null ){
				action.onClick();
			}
		}
	}

	public void goToNextFieldOnKeyPress(){
		FormPanelCustomized formViewPanel = (FormPanelCustomized)this.getParent(); 
		//ClientGrid grid = (formViewPanel).getClientGrid(); 			

		int index = formViewPanel.getFields().indexOf(this) + 1;
		if ( index <= formViewPanel.getFields().size() - 1 ){
			Field fld = formViewPanel.getFields().get( index );
			int i = 0;
			while ( ( fld instanceof LabelField || ! fld.isEnabled() || ! fld.isVisible() )  && i < formViewPanel.getFields().size() ){
				try {
					fld = formViewPanel.getFields().get( formViewPanel.getFields().indexOf(this) + 1 + i );
					i++;
				} catch(Exception e){
					break;
				}
			}
			if ( fld != null ){
				fld.focus();
			}	
		}		
	}
	
	class LookupAsyncCallback extends DyadAsyncCallback{
		LookupFieldCutomized field;
		public LookupAsyncCallback( LookupFieldCutomized field ){
			this.field = field;
		}
		
		public void onFailure(Throwable arg0) {
			DyadInfrastructure.messageWindow.close();
			ExceptionDialog.show(arg0);			
		}
		
		public void onSuccess(Object arg0) {
			try {
				field.setLookupStore( (HashMap)arg0 );
				field.treatLookupResult();
			} catch( Exception e ){
				e.printStackTrace();
				ExceptionDialog.show(e);
			} finally {
				DyadInfrastructure.messageWindow.close();
			}
		}
	}
	
	private DyadBaseModel getDyadBaseModelFromDataClass(DataClass dataClass){
		DyadBaseModel lookupBaseModel = new DyadBaseModel();			
		lookupBaseModel.set("id", (Long)dataClass.get("classId") );
		String template = (String)dataClass.get("name");
		Integer inheritLevel = (Integer)dataClass.get("inheritLevel");
		String templateWithInherit = ( inheritLevel != null && inheritLevel > 0 ) ? ( getOffSetInheritLevel(inheritLevel).concat(template) ) : template;
		lookupBaseModel.set(this.getName(), templateWithInherit );
		return lookupBaseModel;
	}
	
	private String getOffSetInheritLevel(Integer level){
		String offSet = "";
		for (int i = 0; i < level; i++) {
			offSet += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		}
		return offSet;
	}
	
	private void addChildrenFromRoot( DataClass root, ArrayList<DyadBaseModel> list ){
		ArrayList<DataClass> children = root.getChildren();
		for (Iterator iterator = children.iterator(); iterator.hasNext();) {
			DataClass dataClass = (DataClass) iterator.next();
			DyadBaseModel mod = getDyadBaseModelFromDataClass(dataClass);
			list.add(mod);
			if (dataClass.getChildren() != null && dataClass.getChildren().size() > 0 ){
				addChildrenFromRoot(dataClass, list);
			}
		}
	}
	
	private void setLookupStore( HashMap ret ){
		lookupStore = new ArrayList<DyadBaseModel>();		
		if ( this.isClassLookup ){
			//tempLookupStore = (ArrayList<DyadBaseModel>)ret.get("classes");
			String st = (String)ret.get("searchToken");
			if ( st == null || st.trim().length() == 0 ){				
				DataClass root = (DataClass)ret.get("classes");			
				lookupStore.add(getDyadBaseModelFromDataClass(root));
				addChildrenFromRoot(root, lookupStore);			
			} else {
				ArrayList<DataClass> tempLookupStore = (ArrayList<DataClass>)ret.get("classes");	
				
				for (Iterator iterator = tempLookupStore.iterator(); iterator.hasNext();) {
					DataClass dyadBaseModel = (DataClass) iterator.next();
					DataClass lookupBaseModel = new DataClass();	
			
					lookupBaseModel.set("id", (Long)dyadBaseModel.get("id") );
					String template = (String)dyadBaseModel.get("lookupTemplate");				
					lookupBaseModel.set(this.getName(), template );				
					lookupStore.add(lookupBaseModel);			
				}
				
				Collections.sort (lookupStore, this);
			}
		} else {
			ArrayList<DyadBaseModel> tempLookupStore = (ArrayList<DyadBaseModel>)ret.get("bean");	
			
			for (Iterator iterator = tempLookupStore.iterator(); iterator.hasNext();) {
				DyadBaseModel dyadBaseModel = (DyadBaseModel) iterator.next();
				DyadBaseModel lookupBaseModel = new DyadBaseModel();	
		
				lookupBaseModel.set("id", (Long)dyadBaseModel.get("id") );
				String template = (String)dyadBaseModel.get("lookupTemplate");				
				lookupBaseModel.set(this.getName(), template );				
				lookupStore.add(lookupBaseModel);			
			}
			
			Collections.sort (lookupStore, this);
		}
		
		/*if( isClassLookup ){
			Collections.sort(tempLookupStore, new Comparator(){
				@Override
				public int compare(Object o1, Object o2) {
					Integer level1 = (Integer)((DataClass)o1).get("inheritLevel");
					Integer level2 = (Integer)((DataClass)o2).get("inheritLevel");
					return  level1 - level2;
				}				
			});
		}*/	
		
		/*for (Iterator iterator = tempLookupStore.iterator(); iterator.hasNext();) {
			DyadBaseModel dyadBaseModel = (DyadBaseModel) iterator.next();
			DyadBaseModel lookupBaseModel = new DyadBaseModel();			

	
			lookupBaseModel.set("id", (Long)dyadBaseModel.get("id") );
			String template = (String)dyadBaseModel.get("lookupTemplate");
			
			if (isClassLookup){
				Integer inheritLevel = (Integer)dyadBaseModel.get("inheritLevel");
				//String templateWithInherit = ( inheritLevel != null && inheritLevel > 0 ) ? ( StringUtils.repeat(".  ", inheritLevel).concat(template) ) : template;
				String templateWithInherit = ( inheritLevel != null && inheritLevel > 0 ) ? ( inheritLevel.toString().concat(template) ) : template;
				lookupBaseModel.set(this.getName(), templateWithInherit );				
				lookupStore.add(lookupBaseModel);
			} else {
				lookupBaseModel.set(this.getName(), template );				
				lookupStore.add(lookupBaseModel);
			}			
		}
		
		Collections.sort (lookupStore, this);*/ 
	}
	
	@Override
	public int compare(Object arg0, Object arg1) {
		DyadBaseModel p1 = (DyadBaseModel) arg0;  
		DyadBaseModel p2 = (DyadBaseModel) arg1;  
		return ((String) p1.get(this.getName())).compareToIgnoreCase((String)p2.get(this.getName()));
	}
	
	private void treatLookupResult(){
		if ( lookupStore.size() == 1 ){
			this.setIdValue((Long)lookupStore.get(0).get("id"));
			this.setValue((String) lookupStore.get(0).get(this.getName()));	
			this.setLastValidValue(this.getValue()); 
			this.setValidValue(true);
			this.focus();
			this.clearInvalid();
			
			this.dispatchValueSincronizer();

			//this.tryDispatchFirstActionIfFieldIsLastField();
			//this.goToNextFieldOnKeyPress();
		} else if ( lookupStore.size() == 0 ){
			this.setLastValidValue(null);
			this.setValidValue(false);
			MessageBox msg = new MessageBox();
			msg.setMessage("No record found!");
			msg.setIcon(MessageBox.INFO);
			msg.show();
		} else {			
			defineLookupWindow();						
			defineGridPanel();	
			Application.getInstance().getCurrentWindow().add(lookupWindow);
			lookupWindow.show();
			grid.getSelectionModel().select(0, false);
			grid.focus();
			grid.getView().focusRow(0);
		}
	}
	
	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public void setValueAndSincronizer(Long value) {
		ClientField clientField = this.getClientField();
		ClientGrid grid = clientField.getGrid();
		ClientProcess process = grid.getProcess();
		
		if ( clientField.getGrid() instanceof ClientVariableGrid ){
			//((ClientVariableGrid)grid).addFieldValueToSave(this);
			((ClientVariableGrid)grid).addFieldValueToSave(clientField);
		}
		
		process.dispatchSetFieldValue( process.getServerObjectId(), grid.getServerObjectId(), clientField.getServerObjectId(), value );
		//super.setValue(value);
	}			
	
	@Override
 	protected void onBlur(ComponentEvent arg0) {
		this.dispatchValueSincronizer();
		super.onBlur(arg0);
 	}
 	
	public void dispatchValueSincronizer(){
		String oldRawValue = getOldRawValue();
		String rawValue = getRawValue();
		Boolean expression1 = oldRawValue == null && ! rawValue.equals("");
		Boolean expression2 = oldRawValue != null && ! oldRawValue.trim().equals( rawValue == null ? rawValue : rawValue.trim() );
		if ( getRawValue() != null && ( expression1 || expression2 ) ) {
			try {
				this.setOldRawValue(this.getRawValue());
				this.setValueAndSincronizer( getIdValue() );	 					
			} catch (Exception e) {
				this.setValueAndSincronizer(null);
			}
		}		
	}
	
	/*@Override
 	public void setRawValue(String value) {
 		if ( this.rendered ){
 			value = value != null ? value.trim() : null;
 			if ( value != null && ( value.length() == 0 || value.equals("") ) ){
 				value = null;
 			}
 			super.setRawValue(value);
 		}
 	}*/
 	
	@Override
 	protected void onKeyPress(FieldEvent fe) {
		Integer lookupTrigger = 10; //-- Control + Enter
		Integer nextTriger = 13; //-- Enter
		
		/*if ( fe.isControlKey() && lookupTrigger.equals(fe.getKeyCode()) ){		
			this.showOccurs( fe, this, lookupTrigger );			
		} else if ( nextTriger.equals(fe.getKeyCode()) ){
			if ( this.getValue() != null && ( !isValidValue() || !this.getValue().equals(this.getLastValidValue() ) ) ){
				this.forceInvalid("Invalid value!");
				System.out.println("Invalid value!");
			} else {				
				this.tryDispatchFirstActionIfFieldIsLastField();
		 		this.goToNextFieldOnKeyPress();
			}
			
		}*/
		
		if ( ( fe.isControlKey() && lookupTrigger.equals(fe.getKeyCode())) ){
			this.showOccurs( fe, this, lookupTrigger );
		} else {			
			if ( nextTriger.equals(fe.getKeyCode()) ){
				if ( this.getValue() == null ){
					this.tryDispatchFirstActionIfFieldIsLastField();
					this.goToNextFieldOnKeyPress();
				} else {				
					if ( !this.getValue().equals(this.getLastValidValue() ) ){
						this.showOccurs( fe, this, lookupTrigger );
					} else {				
						this.tryDispatchFirstActionIfFieldIsLastField();
						this.goToNextFieldOnKeyPress();
					}
				}			
			}
		}
		
		ClientField.dispatchGetFieldInformation( fe, this, this.getClientField(), 8 );		
	}

	protected void showOccurs( FieldEvent fe, Widget field, Integer trigger){
		getOccurs();				
	}	
	
	protected void getOccurs(){				
		String value = this.getValue() != null ? this.getValue().trim() : "";
		String searchToken = ( getLastValidValue() != null && getLastValidValue().trim().equals(value)) ? "" : ( value );
		
		HashMap params = DyadInfrastructure.getRpcParams();
		params.put("bean", this.getBeanName());
		params.put("isClassLookup", this.getIsClassLookup());
		params.put("searchToken", searchToken);
		params.put("findExpress", getFindExpress(searchToken));
		
		if ( proxy == null ){
			proxy = DyadInfrastructure.getGenericServiceProxy();
		}
		lookupAsyncCallback = new LookupAsyncCallback(this);
		
		DyadInfrastructure.messageWindow = TimerMessageBox.wait(this.getFieldLabel(), "",
				"Buscando ocorrências...");
		DyadInfrastructure.messageWindow.setModal(true);
		proxy.getServiceValue("br.com.dyad.infrastructure.service.LookupService", params, lookupAsyncCallback);				
	}
	
	protected void defineLookupWindow(){
		lookupWindow = new Window();
		lookupWindow.setWidth(440);
		lookupWindow.setModal(true);
		lookupWindow.setHeight(275);
		lookupWindow.setResizable(false);
		lookupWindow.setScrollMode(Scroll.NONE);
		lookupWindow.setHeading(this.getFieldLabel() + " - ( 1 / " + lookupStore.size() + " ) " );
		lookupWindow.setPosition(this.getAbsoluteLeft(), this.getAbsoluteTop() + 20);
	}
	
	class LookupSelectionChangedListener extends SelectionChangedListener<DyadBaseModel>{
		LookupFieldCutomized field;
		public LookupSelectionChangedListener( LookupFieldCutomized field ){
			this.field = field;
		}
		@Override
		public void selectionChanged(SelectionChangedEvent<DyadBaseModel> se) {
			lookupWindow.setHeading( field.getFieldLabel() + " - ( " + ( 1 + grid.getStore().indexOf(grid.getSelectionModel().getSelectedItem() ) ) + 
									 " / " + lookupStore.size() + " ) " );	
		}
	}
	
	protected void defineGridPanel(){
		gridPanel = new ContentPanel();
		gridPanel.setHeaderVisible(false);
		gridPanel.setScrollMode(Scroll.NONE);
						
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

		CheckBoxSelectionModel<DyadBaseModel> sm = new CheckBoxSelectionModel<DyadBaseModel>();

		configs.add(sm.getColumn());
		sm.addSelectionChangedListener(new LookupSelectionChangedListener(this));
		sm.setSelectionMode(SelectionMode.SINGLE);

		ColumnConfig column = new ColumnConfig();		
		column.setId(this.getName());
		column.setHeader(this.getFieldLabel());
		column.setWidth(400);
		configs.add(column);	

		PagingModelMemoryProxy lookupProxy = new PagingModelMemoryProxy(lookupStore);		
		PagingLoader<PagingLoadResult<ModelData>> loader = new BasePagingLoader<PagingLoadResult<ModelData>>(lookupProxy); 
		loader.setRemoteSort(true);
		
		loader.setLimit(7);
		loader.load();
		
		ListStore<DyadBaseModel> store = new ListStore<DyadBaseModel>(loader);	
		store.add(lookupStore);		
		
		final PagingToolBar pageToolBar = new PagingToolBar(7);  
		pageToolBar.bind(loader);  
		loader.load(0, 7);

		ColumnModel cm = new ColumnModel(configs);

		ContentPanel cp = new ContentPanel();
		cp.setHeaderVisible(false);
		cp.setLayout(new FitLayout());
		cp.setSize(430, 245);
		cp.setBottomComponent(pageToolBar);
				
		ToolBar toolBar = new ToolBar();
		Button btOk = new Button("Ok");
		btOk.setId("btnOkLookup");
		LookupSelectionListener lookupSelectionListener = new LookupSelectionListener(this, lookupWindow);
		btOk.addSelectionListener(lookupSelectionListener);
		toolBar.add(btOk);
		
		toolBar.add(new SeparatorToolItem());  
		Button btCancel = new Button("Cancel");
		btCancel.setId("btnCancelLookup");
		btCancel.addSelectionListener(lookupSelectionListener);
		toolBar.add(btCancel);    
		
		cp.setTopComponent(toolBar);

		grid = new Grid<DyadBaseModel>(store, cm);
		grid.setSelectionModel(sm);
		grid.setBorders(true);
		grid.setStripeRows(true);
		grid.addPlugin(sm);	
		
		cp.add(grid);
		gridPanel.add(cp);
		lookupWindow.add(gridPanel);
	}	
	
	public ClientLookupField getClientField() {
		return clientField;
	}

	public void setClientField(ClientLookupField clientField) {
		this.clientField = clientField;
	}

	public String getOldRawValue() {
		return oldRawValue;
	}

	public void setOldRawValue(String oldRawValue) {
		this.oldRawValue = oldRawValue;
	}

	public Boolean getEnabledFromClass() {
		return enabledFromClass;
	}

	public void setEnabledFromClass(Boolean enabledFromClass) {
		this.enabledFromClass = enabledFromClass;
	}

	@Override
	public boolean validate() {
		if ( this.isEnabled() ){
			return super.validate();
		} else {
			return true;
		}
	}
	
	public String getLastValidValue() {
		return lastValidValue;
	}

	public void setLastValidValue(String lastValidValue) {
		this.lastValidValue = lastValidValue;
	}
	public boolean isValidValue() {
		return validValue;
	}

	public void setValidValue(boolean validValue) {
		this.validValue = validValue;
	}	

	public ColumnConfig getColumnConfig(){		
		if ( this.columnConfig == null ){
			
			LookupFieldCutomized lookupTemplate = (LookupFieldCutomized) this.getClientField().getTableViewField();
			
			this.columnConfig = new ColumnConfig();  
			this.columnConfig.setId( this.getClientField().getName() );  
			this.columnConfig.setHeader( this.getClientField().getLabel() );  
			this.columnConfig.setWidth( this.getClientField().getTableViewWidth() );  
			this.columnConfig.setEditor(new CellEditor( lookupTemplate )); //{
			/*@Override  
			public Object preProcessValue(Object value) {  
				if (value == null) {  
					return value;  
				}  
				if ( value != null && value instanceof HashMap ){
					HashMap map = (HashMap)value;						
					((LookupFieldCutomized)this.getField()).setIdValue( (Long)map.get( "id") );
					return (String)map.get( "lookupTemplate" );
				}

				return value;
			} */ 

			/*@Override  
			public Object postProcessValue(Object value) {  
				return this.preProcessValue(value);
			}*/  			
			//}
			//);
		}	
			
		return this.columnConfig;
	}
}
