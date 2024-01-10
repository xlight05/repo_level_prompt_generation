package br.com.dyad.infrastructure.widget.grid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.dyad.client.DyadBaseTreeModel;
import br.com.dyad.client.widget.grid.GridTypes;
import br.com.dyad.commons.data.DataList;
import br.com.dyad.infrastructure.annotations.SendToClient;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.persistence.DataListFactory;
import br.com.dyad.infrastructure.reflect.ObjectConverter;
import br.com.dyad.infrastructure.widget.Window;

public class TreeGrid extends DataGrid {
	@SendToClient
	private Long rootId;
	@SendToClient
	private String parentField;
	@SendToClient
	private String childField;
	@SendToClient
	private String treeField;
	@SendToClient
	private ArrayList<DyadBaseTreeModel> treeViewPageStore;
	@SendToClient
	private String leafIconStyle;
	
	/**
	 * Esta propriedade define quando um model nÃ£o tem itens filhos.
	 * Desta forma, Ã© possÃ­vel saber se o nÃ³ Ã© expansÃ­vel antes mesmo de se adicionar
	 * os filhos. Caso nÃ£o seja informado, os nÃ³s que nÃ£o tem filhos, inicialmente irÃ£o aparecer
	 * na grid como um nÃ³ expansÃ­vel, e sÃ³ depois de clicar, serÃ¡ visÃ­vel que o mesmo Ã© uma nÃ³ 'leaf'.
	 * @param model
	 * @return
	 */
	@SendToClient
	private String leafField;	

	/**
	 * 
	 * @param window
	 * @param beanName: bean que define a estrutura do datalist. Ex: br.com.dyad.infrastructure.entity.NavigatorEntity
	 * @param rootId: id do bean root.
	 * @param parentField: propriedade do bean que vincula o nÃ³ pai aos filhos. Ex:id
	 * @param childField: propriedade do bean que vincula o nÃ³ filho ao nÃ³ pai: Ex: parent
	 * @param treeField: campo da grid que possuirÃ¡ a Ã¡rvore
	 * @param name: nome da grid
	 * @throws Exception
	 */
	public TreeGrid(Window window, String beanName, Long rootId, String parentField, String childField, String treeField, String name) throws Exception {
		super(window, name);
		setBeanName(beanName);		
		setRootId(rootId);
		setParentField(parentField);
		setChildField(childField);	
		setTreeField(treeField);
	}
	
	@Override
	protected void initializeGrid() throws Exception {
		super.initializeGrid();
		setCanNavigate(false);
		setCanDelete(false);
		setCanInsert(false);
		setViewMode(DataGrid.VIEW_MODE_TABLEVIEW);
		setType(GridTypes.TREE_GRID);
	}

	public Long getRootId() {
		return rootId;
	}

	public void setRootId(Long rootId) {
		this.rootId = rootId;		
	}
	
	public String getParentField() {
		return parentField;
	}

	public void setParentField(String parentField) {
		this.parentField = parentField;				
	}

	public String getChildField() {
		return childField;
	}

	public void setChildField(String childField) {
		this.childField = childField;		
	}
	
	public String getLeafIconStyle() {
		return leafIconStyle;
	}

	public void setLeafIconStyle(String leafIconStyle) {
		this.leafIconStyle = leafIconStyle;
	}
	
	public String getTreeField() {
		return treeField;
	}

	public void setTreeField(String treeField) {
		this.treeField = treeField;
	}
	
	public String getLeafField() {
		return leafField;
	}

	public void setLeafField(String leafField) {
		this.leafField = leafField;
	}

	@Override
	public void firstPage() throws Exception {
		this.firstEntity();
		if ( ! this.dataList.getList().isEmpty() ){
			if ( ! this.dataList.find("id", rootId ) ){
				throw new Exception("root id "+ rootId +"not found!");
			}			
			this.setTreeViewPageStore( getChildren(rootId, true ) );
		}
	}
	
	@SuppressWarnings("unchecked")
	protected ArrayList<DyadBaseTreeModel> getChildren( Long rootId, boolean addRoot ) throws Exception{
		ArrayList<DyadBaseTreeModel> page = new ArrayList<DyadBaseTreeModel>();
		
		BaseEntity root = (BaseEntity)this.dataList.getObjectById(rootId);
		if ( root == null ){
			throw new Exception("root id "+ rootId +"not found!");
		}
		if ( addRoot ){						
			DyadBaseTreeModel model = ObjectConverter.getInstance().getTreeViewModel( root );
			page.add(model);
		}		
		List<BaseEntity> list = (List<BaseEntity>) this.dataList.getRange(this.childField, root);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			BaseEntity baseEntity = (BaseEntity) iterator.next();
			DyadBaseTreeModel model = ObjectConverter.getInstance().getTreeViewModel( baseEntity );
			if ( ! model.get("id").equals(this.getRootId()) ){
				page.add(model);
			}
		}
		return page;
	}

	public ArrayList<DyadBaseTreeModel> getTreeViewPageStore() {
		return treeViewPageStore;
	}

	public void setTreeViewPageStore(ArrayList<DyadBaseTreeModel> treeViewPageStore) {
		this.treeViewPageStore = treeViewPageStore;
	}
	
	@Override
	protected void fillPageStoreWithPage( Integer index, Integer lastIndex ) throws Exception{
		ArrayList<DyadBaseTreeModel> page = new ArrayList<DyadBaseTreeModel>();
		for (int i = index; i < lastIndex; i++) {
			BaseEntity entityToModelStore = (BaseEntity)this.dataList.getList().get(i);
			DyadBaseTreeModel model = ObjectConverter.getInstance().getTreeViewModel( entityToModelStore );
			page.add(model);
		}									
		this.setTreeViewPageStore( page );
	}
	
	@Override	
	protected Integer getLastIndex(){
		return this.dataList.getList().size();
	}
	
	public List<BaseEntity> dispatchExpand( String beanName, BaseEntity parent )throws Exception{
		onBeforeExpand(beanName, parent);
		List<BaseEntity> ret = onExpand(beanName, parent);		
		onAfterExpand(beanName, parent);
		return ret;
	}

	public void onAfterExpand( String beanName, BaseEntity parent )throws Exception{};
	
	/**
	 * Esse mÃ©todo deve ser implementado quando a forma de recuperar os itens filhos for diferente do padrÃ£o.
	 * @param beanName
	 * @param parent
	 * @return
	 * @throws Exception
	 */
	public List<BaseEntity> onExpand( String beanName, BaseEntity parent ) throws Exception{
		DataList dataList = DataListFactory.newDataList(getDatabase());
		String query = "from " + beanName + " where parentId = " + parent.getId();
		dataList.executeQuery(query);
		dataList.setCommitOnSave(true);		
		
		return dataList.getList();
	};
	
	public void onBeforeExpand( String beanName, BaseEntity parent ) throws Exception{};
	
	public List<BaseEntity> dispatchCollapse(String beanName, BaseEntity parent)throws Exception{
		onBeforeCollapse(beanName, parent);
		List<BaseEntity> ret = onCollapse(beanName, parent);
		onAfterCollapse(beanName, parent);
		return ret;
	}

	public void onAfterCollapse( String beanName, BaseEntity parent )throws Exception{};
	public List<BaseEntity> onCollapse( String beanName, BaseEntity parent ) throws Exception{return null;};
	public void onBeforeCollapse( String beanName, BaseEntity parent ) throws Exception{};
	
	public List<DyadBaseTreeModel> getModelLisFromBaseEntityList(List<BaseEntity> list ) throws Exception{ 
		ArrayList<DyadBaseTreeModel> modelList = new ArrayList<DyadBaseTreeModel>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			BaseEntity baseEntity = (BaseEntity) iterator.next();
			DyadBaseTreeModel model = ObjectConverter.getInstance().getTreeViewModel( baseEntity );
			modelList.add(model);
		}
		return modelList;
	}
	
	public void dispatchChangeView() throws Exception {
		if ( this.getViewMode().equals(DataGrid.VIEW_MODE_FORMVIEW) ){
			this.setViewMode( DataGrid.VIEW_MODE_TABLEVIEW );
			this.fillPageStoreWithFirstPageOnChangeViewOrScroll(0);	
		} else {
			this.setViewMode( DataGrid.VIEW_MODE_FORMVIEW );
		}
	}
	
	public void fillPageStoreWithFirstPageOnChangeViewOrScroll( Integer index ) throws Exception{ 
		this.fillPageStoreWithFirstPage();	
	}
}