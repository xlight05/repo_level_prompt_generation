package br.com.dyad.infrastructure.widget.grid;

import br.com.dyad.commons.data.DataList;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.entity.AbstractEntity;
import br.com.dyad.infrastructure.widget.DyadEvents;
import br.com.dyad.infrastructure.widget.Window;
import br.com.dyad.infrastructure.widget.WidgetListener;
import br.com.dyad.infrastructure.widget.field.FieldMasterDetail;

public class DetailGrid extends DataGrid{
	
	protected DataGrid master;
	protected FieldMasterDetail masterField;	
	private WidgetListener masterScrollListener;	
	
	public DetailGrid( Window window, String name ) throws Exception{
		super(window, name);
	}

	public DetailGrid( Window window, String name, String beanName ) throws Exception{
		super(window, name);
		this.setBeanName(beanName);
	}

	public DetailGrid( Window window, String name, DataList dataList ) throws Exception{
		super(window, name);
		this.setDataList(dataList);
	}
	
	public DetailGrid( Window window, String name, DataList dataList, String beanName ) throws Exception{
		super(window, name);
		this.setDataList(dataList);
		this.setBeanName(beanName);
	}

	@Override
	protected void initializeGrid() throws Exception {
		super.initializeGrid();
		addWidgetListener(DyadEvents.onMasterScroll, this.getMasterScrollListener() );
	}

	private WidgetListener getMasterScrollListener() {
		if ( this.masterScrollListener == null ){
			this.masterScrollListener = new WidgetListener(){
				@Override
				public void handleEvent(Object sender) throws Exception{
					AbstractEntity masterEntity = DetailGrid.this.master.getCurrentEntity();

					if ( masterEntity == null ){
						throw new Exception( "MasterEntity cannot be null." );
					}

					String whereExpress = createFilterToDetail( masterEntity );

					if ( whereExpress == null || whereExpress.equals("") ){
						throw new Exception( "WhereExpress cannot be null." );
					}

					String query = " from " + DetailGrid.this.masterField.getBeanName() + " where 1=1 " +  whereExpress;
					System.out.println( "query na Detail:" + query );

					//sets the same transactionalSession from master.
					//comentado por helton em 28/09/2009
					//if ( DetailGrid.this.dataList.getTransactionalSession() == null ){
						DetailGrid.this.dataList.setTransactionalSession( DetailGrid.this.master.getDataList().getTransactionalSession() );
					//}

					DetailGrid.this.dataList.executeQuery( query );
					DetailGrid.this.dataList.setCommitOnSave( true );
					DetailGrid.this.setDataList(DetailGrid.this.dataList);
					DetailGrid.this.firstEntity();			
					
					DetailGrid.this.fillPageStoreWithFirstPageOnChangeViewOrScroll(0);
				}

			};
		}
		return this.masterScrollListener;
	}

	public FieldMasterDetail getMasterField() {
		return masterField;
	}

	public void setMasterField(FieldMasterDetail masterField) {
		this.masterField = masterField;
	}		

	public DataGrid getMaster() {
		return master;
	}

	public void setMaster(DataGrid master) {
		this.master = master;
	}
	

	//*****************************************************************************//
	//********************************* POST **************************************//	
	//*****************************************************************************//
	@Override
	public void dispatchPost()throws Exception{
		if ( this.getEditionMode() != null ){
			this.dispatchMasterPost();
		}	
	}

	private void dispatchMasterPost() throws Exception {
		DataGrid master = this.master;
		while ( master instanceof DetailGrid ){
			master = ((DetailGrid)master).getMaster();
		}
		master.dispatchPost();
	}	
	
	@Override
	public void onInsert() throws Exception {
		super.onInsert();
		this.setMasterFieldsValuesToEntity();
		//needs to call set fillFieldValuesWithCurrentEntity again because
		//setMasterFieldsNamesToEntity() has set some props at currentEntity.
		this.fillFieldValuesWithCurrentEntity();
	}

	@SuppressWarnings("unchecked")
	private void setMasterFieldsValuesToEntity() throws Exception{
		 AbstractEntity masterEntity = this.master.getCurrentEntity();

		if ( this.masterField.getMasterFieldNames() == null || this.masterField.getMasterFieldNames().equals("") ){
			throw new Exception("MasterFieldNames cannot be null.");
		}
		if ( this.masterField.getDetailFieldNames() == null || this.masterField.getDetailFieldNames().equals("") ){
			throw new Exception("DetailFieldNames cannot be null.");
		}
		
		String[] masterFieldNames = this.masterField.getMasterFieldNames().split(";");  
		String[] detailFieldNames = this.masterField.getDetailFieldNames().split(";");  
		
		if ( masterFieldNames.length != detailFieldNames.length ){
			throw new Exception("MasterFieldNames must have the same number of elements of DetailFieldNames.");
		}

		for (int i = 0; i < detailFieldNames.length; i++) {
			String detailFieldName = detailFieldNames[i];
			String masterFieldName = masterFieldNames[i];

			Object masterFieldValue = null;
			Class detailFieldClass = ReflectUtil.getFieldType( this.getCurrentEntity().getClass(), detailFieldName );			
			if ( ReflectUtil.inheritsFrom(detailFieldClass, AbstractEntity.class ) ){
				if ( masterFieldName.equalsIgnoreCase("id") ){
					masterFieldValue = masterEntity;
				} else {
					masterFieldValue = ReflectUtil.getFieldValue( masterEntity, masterFieldName );			
				}
			} else {
				masterFieldValue = ReflectUtil.getFieldValue( masterEntity, masterFieldName );			
			}	
			ReflectUtil.setFieldValue(this.getCurrentEntity(), detailFieldName, masterFieldValue );
			System.out.println("Setting detail field value -> detailName:" + detailFieldName + " value: " + masterFieldValue );
		}
	};


	public void onMasterScroll() throws Exception {}

	private String createFilterToDetail( AbstractEntity masterEntity ) throws Exception{
		String whereExpress = "";
		if ( this.masterField.getMasterFieldNames() == null || this.masterField.getMasterFieldNames().equals("") ){
			throw new Exception("MasterFieldNames cannot be null.");
		}
		if ( this.masterField.getDetailFieldNames() == null || this.masterField.getDetailFieldNames().equals("") ){
			throw new Exception("DetailFieldNames cannot be null.");
		}
		
		String[] masterFieldNames = this.masterField.getMasterFieldNames().split(";");  
		String[] detailFieldNames = this.masterField.getDetailFieldNames().split(";");  
		
		if ( masterFieldNames.length != detailFieldNames.length ){
			throw new Exception("MasterFieldNames must have the same number of elements of DetailFieldNames.");
		}

		for (int i = 0; i < detailFieldNames.length; i++) {
			String detailFieldName = detailFieldNames[i];
			String masterFieldName = masterFieldNames[i];
			Object masterFieldValue = ReflectUtil.getFieldValue( masterEntity, masterFieldName );
			if ( masterFieldName == null ){
				whereExpress += " and " + detailFieldName + " is null ";
			} else {
				if ( masterFieldValue instanceof String ){
					whereExpress += " and " + detailFieldName + " = '" + masterFieldValue + "'" ;
				} else {
					whereExpress += " and " + detailFieldName + " = " + masterFieldValue;
				}
			}
		}
		
		return whereExpress;
	};


	@Override
	public void setEditionMode(Integer editionMode) {
		super.setEditionMode(editionMode);
		if ( editionMode != null  ){
			if ( this.master != null && this.master.getEditionMode() == null ){
				this.master.setEditionMode(DataGrid.EDITION_MODE_EDIT);
			}	
		}	
	}
	
	@Override
	public void defineGrid() throws Exception {
		super.defineGrid();
	}
}
