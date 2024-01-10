package br.com.dyad.client.portal;

import java.util.HashMap;
import java.util.List;

import br.com.dyad.client.DyadAsyncCallback;
import br.com.dyad.client.DyadBaseTreeModel;
import br.com.dyad.client.DyadClientListener;
import br.com.dyad.client.DyadInfrastructure;
import br.com.dyad.client.GenericService;
import br.com.dyad.client.GenericServiceAsync;
import br.com.dyad.client.Navigator;
import br.com.dyad.client.TimerMessageBox;
import br.com.dyad.client.gxt.widget.customized.ProcessWindowCustomized;
import br.com.dyad.client.widget.ClientProcess;
import br.com.dyad.client.widget.LoginDialog;

import com.extjs.gxt.ui.client.data.BaseTreeLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.ModelIconProvider;
import com.extjs.gxt.ui.client.data.TreeLoader;
import com.extjs.gxt.ui.client.data.TreeModel;
import com.extjs.gxt.ui.client.data.TreeModelReader;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.TreePanelEvent;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
import com.extjs.gxt.ui.client.widget.form.StoreFilterField;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

public class WindowTreeMenu {
	
	public static HashMap<Long, Long> groupPermissions;
	public static Long administrator = -89999999999993L;
	
	private TreeLoader<ModelData> loader;
    private TreeStore<ModelData> store;
    private TreePanel<ModelData> tree;
    
    private TreeLoader<ModelData> shortcutLoader;
    private TreeStore<ModelData> shortcutStore;
    private TreePanel<ModelData> shortcutTree;
    
    private ContentPanel navigator;
    private ContentPanel shortcuts;
   
	public ContentPanel getNavigator() {
		return navigator;
	}

	public void setNavigator(ContentPanel navigator) {
		this.navigator = navigator;
	}

	public ContentPanel getShortcuts() {
		return shortcuts;
	}

	public void setShortcuts(ContentPanel shortcuts) {
		this.shortcuts = shortcuts;
	}

	public WindowTreeMenu(ContentPanel navigator, ContentPanel shortcuts) {
		setNavigator(navigator);
		setShortcuts(shortcuts);
		
		ModelIconProvider iconProvider = new ModelIconProvider<ModelData>() {
			public AbstractImagePrototype getIcon(ModelData model) {
				String cssName = (String) model.get("cssName");
				if (cssName != null && !cssName.equals("")) {
					return IconHelper.createStyle(cssName);
				} else {
					if (((TreeModel) model).isLeaf()) {
						return IconHelper.create("resources/images/default/grid.png");
					} else {
						return IconHelper.create("resources/images/default/folder.png");
					}
				}
			}
	    };
		
		loader = new BaseTreeLoader<ModelData>(new TreeModelReader<List<ModelData>>());
	    store = new TreeStore<ModelData>(loader);
	    tree = new TreePanel<ModelData>(store);
		    
	    tree.setAutoLoad(true);
	    tree.setDisplayProperty("name");
	    tree.setWidth(250);
	    tree.setIconProvider(iconProvider);
	    
	    shortcutLoader = new BaseTreeLoader<ModelData>(new TreeModelReader<List<ModelData>>());
	    shortcutStore = new TreeStore<ModelData>(shortcutLoader);
	    shortcutTree = new TreePanel<ModelData>(shortcutStore);
		    
	    shortcutTree.setAutoLoad(true);
	    shortcutTree.setDisplayProperty("name");
	    shortcutTree.setWidth(250);
	    shortcutTree.setIconProvider(iconProvider);	

	    StoreFilterField<ModelData> filter = new StoreFilterField<ModelData>() {

	      @Override
	      protected boolean doSelect(Store<ModelData> store, ModelData parent, ModelData record, String property, String filter) {
	        String name = record.get("name");
	        name = name.toLowerCase();
	        if (name.startsWith(filter.toLowerCase())) {
	          return true;
	        }
	        return false;
	      }

	    };
	    
	    filter.bind(store);
	    Listener<TreePanelEvent> clickListener = new Listener<TreePanelEvent>() {

            public void handleEvent(TreePanelEvent be) {
        		if (be.getNode() != null) {            			
        			ModelData data = be.getNode().getModel();
        			
        			if (be.getNode().isLeaf() && data != null) {
        				Long id = data.get("id");
        				
        				if (id.equals(-1L)) {
        					DyadClientListener listener = data.get("listener");
        					listener.handleEvent(null);
        				} else {        					
        					String name = data.get("process");
        					Application.getInstance().createWindow(data);
        				}        				
        			}
        		}
            };
        };
	    tree.addListener(Events.OnDoubleClick, clickListener);
	    shortcutTree.addListener(Events.OnDoubleClick, clickListener);

	    VerticalPanel panel = new VerticalPanel();
	    //panel.addStyleName("x-small-editor");
	    panel.setSpacing(8);

	    panel.add(new Html("<span class=text>" + DyadInfrastructure.translate("Buscar") + "</span>"));
	    panel.add(filter);
	    panel.add(tree);

	    buildTreeMenu();
	    createContextMenu();
	    
	    
	    createShortcutContextMenu();
	    VerticalPanel shortcutPanel = new VerticalPanel();
	    shortcutPanel.setSpacing(8);
	    shortcutPanel.add(shortcutTree);
	    getShortcuts().add(shortcutPanel);
	    getNavigator().add(panel);
	}
	
	/**
	 * Cria o menu no botão direito para adicionar e remover os atalhos
	 * 
	 * @author eduardo
	 */
	private void createContextMenu() {
		Menu contextMenu = new Menu();  
	    contextMenu.setWidth(140);  

	    MenuItem insert = new MenuItem();  
	    insert.setText(DyadInfrastructure.translate("Adicionar atalho"));  
	    insert.setIcon(IconHelper.create("resources/images/default/add.png"));  
		insert.addSelectionListener(new SelectionListener<MenuEvent>() {
			public void componentSelected(MenuEvent ce) {
				final ModelData node = tree.getSelectionModel().getSelectedItem();
				if (node != null) {					
					final Long windowId = (Long)node.getProperties().get("id");
					
					if (windowId != null && !windowId.equals("")) {
						DyadAsyncCallback callback = new DyadAsyncCallback() {
							@Override
							public void onSuccess(Object arg0) {
								shortcutStore.add(node, true);
								getShortcuts().layout();
							}
						};
						
						GenericServiceAsync service = DyadInfrastructure.getGenericServiceProxy();
						HashMap params = DyadInfrastructure.getRpcParams();
						params.put("createUserShortcut", true);
						params.put("windowId", windowId);
						service.getServiceValue("br.com.dyad.infrastructure.service.UserProfileService", params, callback);
					}
				}
			}
		});  
	    contextMenu.add(insert);
	    tree.setContextMenu(contextMenu);
	}
	
	private void createShortcutContextMenu() {
		Menu contextMenu = new Menu();  
	    contextMenu.setWidth(140);  

	    MenuItem delete = new MenuItem();  
	    delete.setText(DyadInfrastructure.translate("Excluir atalho"));  
	    delete.setIcon(IconHelper.create("resources/images/default/delete.png"));  
		delete.addSelectionListener(new SelectionListener<MenuEvent>() {
			public void componentSelected(MenuEvent ce) {
				final ModelData node = tree.getSelectionModel().getSelectedItem();
				if (node != null) {					
					final Long windowId = (Long)node.getProperties().get("id");
					
					if (windowId != null && windowId.equals("-1")) {
						
					} else if (windowId != null && !windowId.equals("")) {
						DyadAsyncCallback callback = new DyadAsyncCallback() {
							@Override
							public void onSuccess(Object arg0) {
								shortcutStore.remove(node);
								getShortcuts().layout();
							}
						};
						
						GenericServiceAsync service = DyadInfrastructure.getGenericServiceProxy();
						HashMap params = DyadInfrastructure.getRpcParams();
						params.put("removeShortcut", true);
						params.put("windowId", windowId);
						service.getServiceValue("br.com.dyad.infrastructure.service.UserProfileService", params, callback);
					}
				}
			}
		});  
	    contextMenu.add(delete);
	    shortcutTree.setContextMenu(contextMenu);
	}

	/*
	 * Transforma um Navigator em um DyadBaseTreeModel para que possa ser exibido em uma árvore
	 */
	protected DyadBaseTreeModel getTreeNavigator(Navigator navigator) {
		DyadBaseTreeModel treeModel = new DyadBaseTreeModel();
		
		//Copia todas as propriedades do item de navegação
		for (String propName : navigator.getPropertyNames()) {
			if (!propName.equals("submenu")) {
				treeModel.set(propName, navigator.get(propName));
			}
		}
		
		//Inclui o submenu
		for (Navigator item : navigator.getSubmenu()) {			
			treeModel.add(getTreeNavigator(item));
		}
		
		return treeModel;
	}
	
	private void addToolsMenu(DyadBaseTreeModel tree) {
		DyadBaseTreeModel data = new DyadBaseTreeModel();
		data.set("cssName", "startmenu-lock");
		data.set("id", -1L);
		data.set("name", DyadInfrastructure.translate( "Lock Session" ));
		data.set("listener", new DyadClientListener() {

			@Override
			public void handleEvent(HashMap params) {
				final Window wind = new Window();
				wind.setClosable(false);
				wind.setMinimizable(false);
				wind.setDraggable(false);
				wind.setResizable(false);
				wind.setCollapsible(false);
				wind.setTitle( DyadInfrastructure.translate( "Session locked" ) );
				
				LoginDialog loginDialog = new LoginDialog(DyadInfrastructure.userName,DyadInfrastructure.database);
				loginDialog.setClosable(false);
				loginDialog.setHeading( DyadInfrastructure.translate( "Unlock session" ) );
				loginDialog.setIconStyle("unlock-session-user");
				loginDialog.addListener(Events.Hide, new Listener<WindowEvent>() {
					public void handleEvent(WindowEvent we) {
						wind.hide();
					}
				});						
				wind.add(loginDialog);
				wind.show();
				loginDialog.show();
				//wind.maximize();
				wind.setModal(true);
			}
			
		});
		tree.add(data);
		
		data = new DyadBaseTreeModel();
		data.set("cssName", "startmenu-help");
		data.set("id", -1L);
		data.set("name", DyadInfrastructure.translate("Help"));
		tree.add(data);
		
		data = new DyadBaseTreeModel();
		data.set("cssName", "startmenu-logout");
		data.set("id", -1L);
		data.set("name", DyadInfrastructure.translate("Logout"));
		data.set("listener", new DyadClientListener(){

			@Override
			public void handleEvent(HashMap params) {
				MessageBox.confirm( DyadInfrastructure.translate( "Logout" ), DyadInfrastructure.translate( "Are you sure?" ), new Listener<MessageBoxEvent>() {
					@Override
					public void handleEvent(MessageBoxEvent ce) {												
		                Button btn = ce.getButtonClicked();
		                if ( btn.getText().equalsIgnoreCase(Dialog.YES) ){
		    				dispatchLogout();
		                }						
					}
		        });	    		             
				//ce.setCancelled(true);				
			}			
		});
		tree.add(data);
		
	}
	
	public static void dispatchLogout(){
		GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();
		HashMap params = DyadInfrastructure.getRpcParams();
		params.put(GenericService.GET_REQUEST, "");

		proxy.getServiceValue("br.com.dyad.infrastructure.service.LogoutService",
				params, new DyadAsyncCallback() {
					@Override
					public void onFailure(Throwable arg0) {								
						super.onFailure(arg0);
					}

					public void onSuccess(Object arg0) {
						DyadInfrastructure.sessionId = null;	
						DyadInfrastructure.userName = null;
						DyadInfrastructure.reload();
					}
				});
	}
	
	private void buildTreeMenu() {
		DyadInfrastructure.messageWindow = TimerMessageBox.wait("Loading", "Loading system resources", "loading...");
		DyadInfrastructure.messageWindow.setModal(true);
		GenericServiceAsync serviceProxy = DyadInfrastructure.getGenericServiceProxy();		
			
		
		AsyncCallback callback = new DyadAsyncCallback() {
			HashMap<Long, Long> groupPermissions = null;
	
			private boolean validateStartMenuPermission( Long itemId ){
				if ( groupPermissions.get(WindowTreeMenu.administrator) != null ){
					return true;
				}
				return groupPermissions.get(itemId) != null;
			}
			
			@SuppressWarnings("unchecked")
			public void onSuccess(Object result) {
				if (result != null) {
					HashMap temp = (HashMap) result;
					Navigator info = (Navigator) temp.get("navigator");
					DyadBaseTreeModel mainMenu = getTreeNavigator(info); 
					addToolsMenu(mainMenu);
					loader.load(mainMenu);
					
					Navigator shortcutInfo = (Navigator) temp.get("shortcuts");
					shortcutLoader.load(getTreeNavigator(shortcutInfo));
					
					groupPermissions = (HashMap<Long, Long>)temp.get("groupPermission");
		
					/*for (Navigator item : info.getSubmenu()) {
						if ( validateStartMenuPermission( item.getId()) ){
							MenuItem menuItem = new MenuItem(item.getName());													
							//menu.add(menuItem);			
							menuItem.setIconStyle("startmenu-module");
							//addEventHandler(menuItem, item);
		
							if (item.getSubmenu() != null
									&& item.getSubmenu().size() > 0) {
								Menu subMenu = new Menu();
								menuItem.setSubMenu(subMenu);
								//addMenu(item, subMenu);
							}
						}						
					}*/
					
					getNavigator().layout();
					getShortcuts().layout();
				}				
				//desktop.getDesktop().setEnabled(true);
				DyadInfrastructure.messageWindow.close();
			}
		};
		
		HashMap params = DyadInfrastructure.getRpcParams();
		params.put("getShortcuts", true);
		serviceProxy.getServiceValue("br.com.dyad.infrastructure.service.NavigatorService", params, callback);

	}

}
