package br.com.dyad.client.widget.grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import br.com.dyad.client.DyadAsyncCallback;
import br.com.dyad.client.DyadBaseModel;
import br.com.dyad.client.DyadBaseTreeModel;
import br.com.dyad.client.DyadInfrastructure;
import br.com.dyad.client.GenericServiceAsync;
import br.com.dyad.client.widget.ClientProcess;
import br.com.dyad.client.widget.ExceptionDialog;
import br.com.dyad.client.widget.field.ClientField;
import br.com.dyad.client.widget.field.ClientLookupField;
import br.com.dyad.client.widget.field.ClientMemoField;
import br.com.dyad.client.widget.field.ClientStringField;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.TreeGridEvent;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.treegrid.TreeGrid;
import com.extjs.gxt.ui.client.widget.treegrid.TreeGridCellRenderer;

public class ClientTreeGrid extends ClientDataGrid{
	
	protected <X extends Object> void callSetDependentProperties(String name, X value) {
		super.callSetDependentProperties(name, value);
		
		if (name.equals("treeViewPageStore")) {
			this.getTreeStore().removeAll();
			
			ArrayList<DyadBaseTreeModel> page = (ArrayList<DyadBaseTreeModel>)value;
			DyadBaseTreeModel rootModel = null;
			
			for (Iterator iterator = page.iterator(); iterator.hasNext();) {
				DyadBaseTreeModel dyadBaseModel = (DyadBaseTreeModel) iterator.next();							
				if ( rootModel == null && ((Long)dyadBaseModel.get("id")).equals(this.getRootId()) ){
					rootModel = dyadBaseModel;
				}					
			}
			
			DyadBaseTreeModel tempChild = new DyadBaseTreeModel();
			tempChild.set("parent", rootModel);
			tempChild.set("id", 1L);
			tempChild.set(this.getTreeField(), "Carregando...");
			rootModel.add(tempChild);
			this.getTreeStore().add( rootModel, true );
		}
			
	}
	
	public void initializeTreeProps(HashMap config) {
		this.setRootId((Long)config.get("rootId"));		
		this.setLeafIconStyle((String)config.get("leafIconStyle"));
		this.setTreeField((String)config.get("treeField"));
		this.setLeafField((String)config.get("leafField"));		
		this.setBeanName((String)config.get("beanName"));
	}
	
	/*private TreeStore<DyadBaseTreeModel> treeStore;
	private TreeGrid<DyadBaseTreeModel> treeViewGrid;
	private CheckBoxSelectionModel<DyadBaseTreeModel> treeViewSelectionModel = new CheckBoxSelectionModel<DyadBaseTreeModel>();
	private Boolean treeFieldAdd = false;	*/
	
	public Boolean getTreeFieldAdd() {
		Boolean result = get("treeFieldAdd");
		
		if (result == null) {
			result = false;
			setTreeFieldAdd(result);
		}
		
		return result;
	}

	public void setTreeFieldAdd(Boolean treeFieldAdd) {
		set("treeFieldAdd", treeFieldAdd);
	}

	public ClientTreeGrid(ClientProcess process, String name) {
		super(process, name);
	}

	public ClientTreeGrid(ClientProcess process, String name, Integer columnCount) {
		super(process, name, columnCount);
	}
	
	@SuppressWarnings("unchecked")
	public ClientTreeGrid(ClientProcess process, String name, Integer columnCount, HashMap colSpanConfig) {
		super(process, name, columnCount, colSpanConfig);
	}
	
	@Override
	public void createTabsToDetailFields(){
		// do nothing
	}
	
	@SuppressWarnings("unchecked")
	public void createTreeViewGrid() {
		ColumnModel cm = new ColumnModel( getTableViewColumnConfig());  

		setTreeStore(new TreeStore());  
		
		setTreeViewGrid(new TreeGrid<DyadBaseTreeModel>( getTreeStore(), cm ));
		getTreeViewGrid().setBorders(true);		
		getTreeViewGrid().setBorders(true);
		getTreeViewGrid().addPlugin(getTableViewSelectionModel());
		
		getTreeViewGrid().setStyleAttribute("borderTop", "none");  
		getTreeViewGrid().setBorders(true);  
		getTreeViewGrid().setAutoHeight(true);
		getTreeViewGrid().getTreeStore().setMonitorChanges(true);
		getTreeViewGrid().getTreeView().setAutoFill(true);
		getTreeViewGrid().setTrackMouseOver(false);
		getTreeViewGrid().getTreeView().setAutoFill(true);
		getTreeViewGrid().getTreeView().setForceFit(true);
		getTreeViewGrid().addListener(Events.OnClick, new Listener<BaseEvent>(){
			@Override
			public void handleEvent(BaseEvent be) {
				if ( getTreeViewGrid().getSelectionModel().getSelectedItem() != null ){
					//MessageBox.alert("teste", "ID: " + (Long)getTreeViewGrid().getSelectionModel().getSelectedItem().get("id"), null);
				}
			}			
		});
		if ( getTreeField() != null ){
			getTreeViewGrid().setAutoExpandColumn(getTreeField());
		}
		getTreeViewGrid().getStyle().setLeafIcon( this.getLeafIconStyle() == null ? 
				IconHelper.createStyle("startmenu-process") : IconHelper.createStyle(this.getLeafIconStyle()) );
						
		getTreeViewGrid().addListener(Events.BeforeExpand, new Listener<TreeGridEvent>() {
	      public void handleEvent(TreeGridEvent te) {
	    	  DyadBaseTreeModel parentNode = ((DyadBaseTreeModel)te.getModel());
	    	  
	    	  parentNode.removeAll();
	    	  
	    	  GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();
		  	  HashMap params = DyadInfrastructure.getRpcParams();
		  	  params.put("parent", parentNode);
		  	  params.put("beanName", getBeanName());
		  	  params.put("serverWindowId", getProcess().getServerObjectId());
		  	  params.put("serverDataGridId", getServerObjectId());	  	    	  
		  	  proxy.getServiceValue("br.com.dyad.infrastructure.service.window.GridExpand", params, new TreeGridAsyncCallback(parentNode));
	      }
		});
		
		this.getTableViewPanel().add(getTreeViewGrid());  		
	}
	
	class TreeGridAsyncCallback extends DyadAsyncCallback{
		DyadBaseTreeModel parent;
		public TreeGridAsyncCallback( DyadBaseTreeModel parent ){
			this.parent = parent;
		}
		
		public void onFailure(Throwable arg0) {
			DyadInfrastructure.messageWindow.close();
			ExceptionDialog.show(arg0);			
		}
		
		public void onSuccess(Object arg0) {
			try {
				HashMap ret = (HashMap)arg0;
	  			List<DyadBaseTreeModel> children = (List<DyadBaseTreeModel>)ret.get("resultExpand");	  			
	  			//parent.removeAll();
	  			
	  			for (Iterator iterator = children.iterator(); iterator.hasNext();) {
					DyadBaseTreeModel dyadBaseTreeModel = (DyadBaseTreeModel) iterator.next();			
					
					//if ( ! parent.getChildren().contains(dyadBaseTreeModel)){
						parent.add(dyadBaseTreeModel);
						
						if ( getLeafField() == null || dyadBaseTreeModel.get(getLeafField()) == null ){
							//-- Deve ser criado um item filho temporÃ¡rio, para que seja possÃ­vel 
							//-- expandir o não mesmo antes de adicionar os filhos.
							//-- IMPORTANTE: o parent.add(dyadBaseTreeModel); deve ser executado
							//-- antes da criaÃ§Ã£o do item temporÃ¡rio, caso contrÃ¡rio, nÃ£o funciona.
							DyadBaseTreeModel tempChild = new DyadBaseTreeModel();
							tempChild.set("parent", dyadBaseTreeModel);
							tempChild.set("id", 1L);
							tempChild.set(getTreeField(), "Carregando...");					
							dyadBaseTreeModel.add(tempChild);
						}	
					//}	
				}
			} catch( Exception e ){
				e.printStackTrace();
				ExceptionDialog.show(e);
			} finally {
				DyadInfrastructure.messageWindow.close();
			}
		}
	}
	
	@Override
	protected void addColumnToTableViewColumnConfig(ClientField clientField) {
		ColumnConfig column = clientField.getColumnConfig();;

		if ( clientField instanceof ClientLookupField ||
			 clientField instanceof ClientMemoField ||
			 clientField instanceof ClientStringField ){
			if ( ! getTreeFieldAdd() ){				
				TreeGridCellRenderer<DyadBaseModel> renderer = new TreeGridCellRenderer<DyadBaseModel>();
				column.setRenderer(renderer);				
				setTreeFieldAdd(true);
			}
		}
		
		if ( column != null){
			this.getTableViewColumnConfig().add( column );
		}
	}
	
	public TreeStore<DyadBaseTreeModel> getTreeStore() {
		return get("treeStore");
	}

	public void setTreeStore(TreeStore<DyadBaseTreeModel> treeStore) {
		set("treeStore", treeStore);
	}

	public TreeGrid<DyadBaseTreeModel> getTreeViewGrid() {
		return get("treeViewGrid");
	}

	public void setTreeViewGrid(TreeGrid<DyadBaseTreeModel> treeViewGrid) {
		set("treeViewGrid", treeViewGrid);
	}

	public Long getRootId() {
		return get("rootId");
	}

	public void setRootId(Long rootId) {
		set("rootId", rootId);
	}

	public CheckBoxSelectionModel<DyadBaseTreeModel> getTreeViewSelectionModel() {
		CheckBoxSelectionModel<DyadBaseTreeModel> treeViewSelectionModel = get("treeViewSelectionModel");
		
		if (treeViewSelectionModel == null) {			
			treeViewSelectionModel = new CheckBoxSelectionModel<DyadBaseTreeModel>();
			setTreeViewSelectionModel(treeViewSelectionModel);
		}
		
		return treeViewSelectionModel; 
	}

	public void setTreeViewSelectionModel(
			CheckBoxSelectionModel<DyadBaseTreeModel> treeViewSelectionModel) {
		set("treeViewSelectionModel", treeViewSelectionModel);
	}
	
	public String getLeafIconStyle() {
		return get("leafIconStyle");
	}

	public void setLeafIconStyle(String leafIconStyle) {
		set("leafIconStyle", leafIconStyle);
	}

	public String getTreeField() {
		return get("treeField");
	}

	public void setTreeField(String treeField) {
		set("treeField", treeField);
	}
	
	public String getLeafField() {
		return get("leafField");
	}

	public void setLeafField(String leafField) {
		set("leafField", leafField);
	}
}