package br.com.dyad.infrastructure.navigation;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpSession;

import br.com.dyad.commons.data.AppTempEntity;
import br.com.dyad.commons.data.DataList;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.entity.AbstractEntity;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.persistence.DataListFactory;
import br.com.dyad.infrastructure.widget.Interaction;
import br.com.dyad.infrastructure.widget.Window;
import br.com.dyad.infrastructure.widget.grid.DataGrid;

public class GenericEntityBeanWindow extends Window {

	protected String beanName;
	protected Long idToFind;
	
	protected Object valueToFind;
	protected String columnToFind;
	
	protected ArrayList valuesToFilterQuery;
	protected ArrayList<String> columnsToFilterQuery;

	protected Integer gridViewMode = DataGrid.VIEW_MODE_TABLEVIEW;
	
	public DataList dataList;
	public DataGrid grid = null;	
	public InteractionShowData showData;			
	
	public GenericEntityBeanWindow(HttpSession httpSession) {
		super(httpSession);
		dataList = DataListFactory.newDataList(getDatabase());
	}
	
	public void setDataToDataList( Class clazz ) throws Exception{
		System.out.println("******** ENTROU NO MÉTODO setDataToDataList( Class clazz ) ***********");
		if (ReflectUtil.inheritsFrom(clazz, AppTempEntity.class)){
			System.out.println("******** PRIMEIRO ***********");
			dataList.setCommitOnSave(false);
		} else if (dataList == null || dataList.getList() == null || dataList.getList().size() == 0) {
			System.out.println("******** SEGUNDO ***********");
			
			/*String query = "from " + this.getBeanName();
			String discriminator = BaseEntity.getClassIdentifierByClassName( this.getBeanName());
			if ( discriminator != null && ! discriminator.equals("") ){
				query += " where classId = '" + discriminator + "'";
			}			
			query += " order by id";
			dataList.executeQuery( query );*/
			
			this.executeQuery();
			dataList.setCommitOnSave(true);
		}
	}	
	
	public void executeQuery() throws Exception{
		String query = "from " + this.getBeanName(); 
		String discriminator = BaseEntity.getClassIdentifierByClassName( this.getBeanName() );
		if ( ( discriminator != null && ! discriminator.equals("") ) || this.valuesToFilterQuery != null ){
			query += " where ";
			boolean usarAnd = false;
			if ( discriminator != null && ! discriminator.equals("") ){
				query += " classId = '" + discriminator + "' ";
				usarAnd = true;
			}		
			
			if ( this.valuesToFilterQuery != null ){
				for (int i = 0; i < this.valuesToFilterQuery.size(); i++) {
					Object value = this.valuesToFilterQuery.get(i);
					Object columnName = this.columnsToFilterQuery.get(i);								
					if ( usarAnd ){
						query += " and ";
					}
					
					if ( value instanceof String ){
						value = "'" + value + "'"; 
					} //TODO faltando implementar o tratamento para os demais tipos.
					
					query += columnName + " = " + value;
					
					usarAnd = true;
				}
			}
			
		}			
		query += " order by id";
		
		dataList.executeQuery( query );		
	}	

	@Override
	public void defineWindow() throws Exception {		
		
		if (getBeanName() == null){			
			throw new Exception( "You must define beanName for use Generic Window." );
		}

		Class clazz = null;		
		clazz = Class.forName(getBeanName());
		
		this.setHelp( "This window is a general feature and manipulates all entities from this application." );
		
		this.setDataToDataList(clazz);

		grid = new DataGrid( this, "dataGrid" ){
			@Override
			public void onAfterInsert() throws Exception {
				super.onAfterInsert();
				GenericEntityBeanWindow.this.onAfterInsert();
			}
			
			@Override
			public void onAfterPost() throws Exception {
				super.onAfterPost();
				GenericEntityBeanWindow.this.onAfterPost();
			}
		};

		add( this.getShowData() );					
		
		if (ReflectUtil.inheritsFrom(clazz, AppTempEntity.class)){
			dataList.setCommitOnSave(false);
		} else {			
			this.executeQuery();
		}
	}

	public String getBeanName() {
		return beanName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	public Long getIdToFind() {
		return idToFind;
	}
	public void setIdToFind(Long idToFind) {
		this.idToFind = idToFind;
	}

	public Interaction getShowData(){	
		if ( this.showData == null ){
			this.showData = new InteractionShowData( this, "showData" );	
		}	
		return (Interaction)this.showData;
	}	
	
	public class InteractionShowData extends Interaction{
		public InteractionShowData(Window window, String string) {
			super(window, string);
		}
		
		@Override
		public void defineInteraction() throws Exception {
			GenericEntityBeanWindow window = (GenericEntityBeanWindow)this.getWindow();

			if ( grid != null ){
				this.remove( grid );
			}
			
			grid = new DataGrid( this.getWindow(), "dataGrid" ){
				@Override
				public void onAfterInsert() throws Exception {
					super.onAfterInsert();
					GenericEntityBeanWindow.this.onAfterInsert();
				}
				
				@Override
				public void onAfterPost() throws Exception {
					super.onAfterPost();
					GenericEntityBeanWindow.this.onAfterPost();
				}
			};			
			
			grid.setHelp("This grid is a general grid to show entities from a class.");
			grid.setViewMode( gridViewMode );
			grid.setBeanName( window.getBeanName() );
			grid.setDataList(dataList);
			grid.setTitle( window.getTitle() );
			grid.inheritBeanEvents();
			
			this.add(grid);

			Long idToFind = window.getIdToFind();
			if ( idToFind != null && dataList.findId( idToFind ) ){
				BaseEntity foundObject = (BaseEntity)dataList.getObjectById( idToFind );				
				grid.goToEntity( foundObject );
			}
			
			Object valueToFind = window.getValueToFind();
			String columnToFind = window.getColumnToFind();; 
			if ( valueToFind != null && columnToFind != null && dataList.find( columnToFind, valueToFind ) ){
				AbstractEntity foundObject = (AbstractEntity)dataList.getOne(columnToFind, valueToFind );
				grid.goToEntity( foundObject );
			}
			
		}
	}
	
	public Object getValueToFind() {
		return valueToFind;
	}

	public void setValueToFind(Object valueToFind) {
		this.valueToFind = valueToFind;
	}

	public String getColumnToFind() {
		return columnToFind;
	}

	public void setColumnToFind(String columnToFind) {
		this.columnToFind = columnToFind;
	}
	
	public ArrayList getValuesToFilterQuery() {
		return valuesToFilterQuery;
	}

	public void setValuesToFilterQuery(ArrayList valuesToFilterQuery) {
		this.valuesToFilterQuery = valuesToFilterQuery;
	}

	public ArrayList<String> getColumnsToFilterQuery() {
		return columnsToFilterQuery;
	}

	public void setColumnsToFilterQuery(ArrayList<String> columnsToFilterQuery) {
		this.columnsToFilterQuery = columnsToFilterQuery;
	}

	public Integer getGridViewMode() {
		return gridViewMode;
	}

	public void setGridViewMode(Integer gridViewMode) {
		this.gridViewMode = gridViewMode;
	}

	public void onAfterInsert() {}
	
	public void onAfterPost() {}
	
}