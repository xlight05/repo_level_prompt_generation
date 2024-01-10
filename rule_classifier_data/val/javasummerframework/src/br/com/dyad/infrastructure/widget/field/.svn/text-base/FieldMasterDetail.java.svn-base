package br.com.dyad.infrastructure.widget.field;

import java.util.HashMap;

import org.hibernate.Session;

import br.com.dyad.client.widget.field.FieldTypes;
import br.com.dyad.commons.data.AppTransaction;
import br.com.dyad.commons.data.DataList;
import br.com.dyad.infrastructure.persistence.DataListFactory;
import br.com.dyad.infrastructure.widget.Window;
import br.com.dyad.infrastructure.widget.grid.DataGrid;
import br.com.dyad.infrastructure.widget.grid.DetailGrid;
import br.com.dyad.infrastructure.widget.grid.Grid;

public class FieldMasterDetail extends Field{
	public static final transient String MASTER_DELETE_ACTION_DELETE = "delete";
	public static final transient String MASTER_DELETE_ACTION_THROW = "throw";
	
	public FieldMasterDetail(Grid grid) throws Exception{
		this.initializeField();
		this.initializeDetailGrid(grid);
		grid.add(this);
	}

	public FieldMasterDetail(Grid grid, String name ) throws Exception{
		this.setName(name);
		this.initializeField();
		this.initializeDetailGrid(grid);
		grid.add(this);
	}
	
	private String beanName;
	private String masterFieldNames;                
	private String detailFieldNames;                
	private String detailIndexFieldNames;           
	//private String detailDescendingIndexFieldNames; 
	private Integer masterDetailMaxRecordCount;
	private String masterDeleteAction;              
	private DetailGrid detailGrid;
	private DataList dataList;
	
	@Override
	protected void initializeField(){
		super.initializeField();

		//this overrides the field definition to save protocol to send to client.
		this.setColumn(null);
		this.setWidth(null);
		this.setHeight(null);
		this.setListable(null);	
		
		this.setMasterDetailMaxRecordCount(5);
		this.setMasterDeleteAction(FieldMasterDetail.MASTER_DELETE_ACTION_THROW);
		this.setType(FieldTypes.FIELD_TYPE_MASTERDETAIL);		
	}
	
	protected void initializeDetailGrid(Grid grid){
		try {
			this.detailGrid = new DetailGrid( grid.getWindow(), "datailGrid");
			this.detailGrid.setViewMode(DataGrid.VIEW_MODE_TABLEVIEW);
			this.detailGrid.setMasterField(this);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public String getMasterFieldNames() {
		return masterFieldNames;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	
	public String getBeanName() {
		return beanName;
	}
	
	public void setMasterFieldNames(String masterFieldNames) {
		this.masterFieldNames = masterFieldNames;
	}

	public String getDetailFieldNames() {
		return detailFieldNames;
	}

	public void setDetailFieldNames(String detailFieldNames) {
		this.detailFieldNames = detailFieldNames;
	}

	public String getDetailIndexFieldNames() {
		return detailIndexFieldNames;
	}

	public void setDetailIndexFieldNames(String detailIndexFieldNames) {
		this.detailIndexFieldNames = detailIndexFieldNames;
	}

	public Integer getMasterDetailMaxRecordCount() {
		return masterDetailMaxRecordCount;
	}

	public void setMasterDetailMaxRecordCount(Integer masterDetailMaxRecordCount) {
		this.masterDetailMaxRecordCount = masterDetailMaxRecordCount;
	}

	public String getMasterDeleteAction() {
		return masterDeleteAction;
	}

	public void setMasterDeleteAction(String masterDeleteAction) {
		this.masterDeleteAction = masterDeleteAction;
	}
	
	public DetailGrid getDetailGrid() {
		return detailGrid;
	}

	public void setDetailGrid(DetailGrid detailGrid) {
		this.detailGrid = detailGrid;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addPropsToClientSincronizer() throws Exception {
		super.addPropsToClientSincronizer();

		HashMap dataGridClientSincronizer = this.getDetailGrid().toClientSynchronizer();
		if ( ! dataGridClientSincronizer.isEmpty() ){
			clientProps.put("dataGrid", dataGridClientSincronizer );
		}	
	}
	
	public void setDetailDataList() throws Exception{
		Window window = this.getGrid().getWindow();
		String dataBase = window.getDatabase();		
		//OBSERVACAO: temos que passar null como um transactionalSession para fazer com que o dataList nao seja criado
		//com uma transactionalSession embutido. Ele necessariamente tem que herdar o da grid mae.
		this.dataList = DataListFactory.newDataList((AppTransaction)null);
		this.detailGrid.setDataList(this.dataList);				
	}

	public void prepareGrid() throws Exception{
		if ( this.getBeanName() == null || this.getBeanName().equals("") ){
			throw new Exception("DetailBean cannot be null.");
		}
		
		//this.detailGrid.onMasterScroll();

		this.detailGrid.setTitle( this.getLabel() );
		this.detailGrid.setBeanName( this.getBeanName() );
		this.detailGrid.defineFieldsAndDefineGrid();
		this.getGrid().getWindow().add(this.detailGrid);
	}

}
