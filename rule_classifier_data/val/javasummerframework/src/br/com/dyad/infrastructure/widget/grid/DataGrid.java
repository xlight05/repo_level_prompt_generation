package br.com.dyad.infrastructure.widget.grid;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ArrayUtils;

import br.com.dyad.client.AppException;
import br.com.dyad.client.DyadBaseModel;
import br.com.dyad.client.GenericService;
import br.com.dyad.client.widget.field.FieldTypes;
import br.com.dyad.client.widget.grid.GridTypes;
import br.com.dyad.commons.data.DataList;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.annotations.Calculated;
import br.com.dyad.infrastructure.annotations.MetaField;
import br.com.dyad.infrastructure.annotations.SendToClient;
import br.com.dyad.infrastructure.aspect.UserInfoAspect;
import br.com.dyad.infrastructure.entity.AbstractEntity;
import br.com.dyad.infrastructure.entity.MasterDetail;
import br.com.dyad.infrastructure.entity.ProductLicense;
import br.com.dyad.infrastructure.navigation.admin.security.PermissionTypes;
import br.com.dyad.infrastructure.navigation.admin.security.SecurityUtil;
import br.com.dyad.infrastructure.persistence.export.CSVExporterImpl;
import br.com.dyad.infrastructure.persistence.export.DataExport;
import br.com.dyad.infrastructure.persistence.export.jasperreport.JasperPDFExporterImpl;
import br.com.dyad.infrastructure.reflect.ObjectConverter;
import br.com.dyad.infrastructure.widget.DyadEvents;
import br.com.dyad.infrastructure.widget.WidgetListener;
import br.com.dyad.infrastructure.widget.Window;
import br.com.dyad.infrastructure.widget.field.Field;
import br.com.dyad.infrastructure.widget.field.FieldBoolean;
import br.com.dyad.infrastructure.widget.field.FieldCombo;
import br.com.dyad.infrastructure.widget.field.FieldHtmlEditor;
import br.com.dyad.infrastructure.widget.field.FieldInteger;
import br.com.dyad.infrastructure.widget.field.FieldLookup;
import br.com.dyad.infrastructure.widget.field.FieldMasterDetail;
import br.com.dyad.infrastructure.widget.field.FieldNumber;
import br.com.dyad.infrastructure.widget.field.FieldSimpleDate;
import br.com.dyad.infrastructure.widget.field.FieldString;
import br.com.dyad.infrastructure.widget.field.FieldTime;
import br.com.dyad.infrastructure.widget.field.FieldUpload;

public class DataGrid extends DataContainer {
		
	public static final Integer EDITION_MODE_INSERT = 0;
	public static final Integer EDITION_MODE_EDIT = 1;

	public static final Integer VIEW_MODE_FORMVIEW = 0;
	public static final Integer VIEW_MODE_TABLEVIEW = 1;
	
	@SendToClient
	private ArrayList<DyadBaseModel> tableViewPageStore;
	
	@SendToClient
	private Long licenseId = null;
	@SendToClient
	private ArrayList<DyadBaseModel> licensesAvailable = null;
	
	private String searchFieldName;
	private String searchToken;
	private List lastSearchResult;
	private Integer lastOccurIndex;

	public DataGrid( Window window, String name ) throws Exception{
		super(window, name);
		this.initializeGrid();
	}

	public DataGrid( Window window, String name, String beanName ) throws Exception{
		super( window, name );
		this.setBeanName( beanName ); 
		this.initializeGrid();
	}

	public DataGrid( Window window, String name, DataList dataList ) throws Exception{
		super( window, name );
		this.setDataList(dataList);
		this.initializeGrid();
	}
	
	public DataGrid( Window window, String name, DataList dataList, String beanName ) throws Exception{
		super( window, name );
		this.setDataList(dataList);
		this.setBeanName(beanName);
		this.initializeGrid();
	}		
			
	@SendToClient
	private boolean canInsert;
	@SendToClient
	private boolean canDelete;
	@SendToClient
	private boolean visible;
	@SendToClient
	private boolean readOnly;
	@SendToClient
	private boolean canNavigate;
	@SendToClient
	private Integer maxRecordCount;
	@SendToClient
	private boolean hasTableView;
	@SendToClient
	private boolean hasFormView;
	@SendToClient
	private Integer viewMode;
	
	//props to details grids
	private List<DataGrid> details;
	
	//props to control
	private AbstractEntity currentEntity;
	protected AbstractEntity currentEntityBeforeDispatchInsert;
	@SendToClient
	private Integer editionMode;
	
	@SendToClient
	private Long idCurrentEntity;
	@SendToClient
	private Integer indexCurrentEntity;
	@SendToClient
	private Integer dataListSize;
	
	
	//*****************************************************************************//
	//****************************NAVIGATION METHODS ******************************//	
	//*****************************************************************************//
	public void goToEntity( AbstractEntity entity ) throws Exception{
		int index = this.dataList.getList().indexOf(entity);
		if ( index == -1 ){
			throw new Exception( "Entity " + entity.toString() + " with id " + entity.getId() + " was not found." );
		} else {
			this.goToEntityIndex(index);
			if ( this.getViewMode().equals( DataGrid.VIEW_MODE_TABLEVIEW ) ){
				this.fillPageStoreWithFirstPageOnChangeViewOrScroll(index);
			}
		}
	}
	
	public void goToEntityIndex( int index ) throws Exception{
		this.setCurrentEntity( (AbstractEntity)this.dataList.getList().get(index) );
		this.setIndexCurrentEntity( index + 1 );
		this.setDataListSize( this.dataList.getList().size() );
		this.fillFieldValuesWithCurrentEntity();
		this.dispatchScroll();
	}
	
	protected void previousEntity() throws Exception{
		this.previousEntity( this.currentEntity );
	}
	
	protected void previousEntity( AbstractEntity entity ) throws Exception{
		if ( this.dataList.getList().isEmpty() ){
			this.resetGridWhenDataListIsEmpty();			
		} else {	
			if ( entity == null ){
				this.firstEntity();
			} else {				
				Integer index = this.dataList.getList().indexOf(entity);
				if ( index == -1 ){
					throw new Exception( "Can't find object with id " + entity.getId() + " in dataList."  );
				} else {
					if ( index > 0 ){
						index--;
						this.setCurrentEntity( (AbstractEntity)this.dataList.getList().get(index) );
						this.setIndexCurrentEntity( index + 1 );
						this.setDataListSize( this.dataList.getList().size() );
					} else if ( index.equals(0)) {
						this.firstEntity();
					}
				}
				this.fillFieldValuesWithCurrentEntity();
				this.dispatchScroll();
			}
		}
	}	
	
	
	protected void previousPage() throws Exception{
		if ( this.dataList.getList().isEmpty() ){
			this.resetGridWhenDataListIsEmpty();			
		} else {	
			if ( this.currentEntity == null ){
				this.firstPage();
			} else {
				Integer index = this.dataList.getList().indexOf(this.currentEntity);
				if ( index == -1 ){
					throw new Exception( "Can't find object with id " + this.currentEntity.getId() + " in dataList."  );
				} 

				Integer firstIndex = index - this.getMaxRecordCount();
				if ( firstIndex > 0 ){					
					
					firstIndex = (Integer) (firstIndex / this.getMaxRecordCount()) ;  
					firstIndex = firstIndex * this.getMaxRecordCount();
					Integer lastIndex = ( firstIndex + this.getMaxRecordCount() ) < this.dataList.getList().size() ? ( firstIndex + this.getMaxRecordCount() ) :  this.dataList.getList().size();														
					
					this.fillPageStoreWithPage( firstIndex, lastIndex );
					
					this.setCurrentEntity( (AbstractEntity)this.dataList.getList().get(firstIndex) );
					this.setIndexCurrentEntity( firstIndex + 1 );
				
					this.fillFieldValuesWithCurrentEntity();
					this.dispatchScroll();
				} else {
					this.firstPage();
				}
			}
		}
	}
	
	
	
	protected void nextEntity() throws Exception {
		if ( this.dataList.getList().isEmpty() ){
			this.resetGridWhenDataListIsEmpty();			
		} else {	
			if ( this.currentEntity == null ){
				this.firstEntity();
			} else {
				Integer index = this.dataList.getList().indexOf(this.currentEntity);
				if ( index == -1 ){
					throw new Exception( "Can't find object with id " + this.currentEntity.getId() + " in dataList."  );
				} else {
					if ( index < this.dataList.getList().size() ){
						index++;
						this.setCurrentEntity( (AbstractEntity)this.dataList.getList().get(index) );
						this.setIndexCurrentEntity( index + 1 );
						this.setDataListSize( this.dataList.getList().size() );
					}
				}
				this.fillFieldValuesWithCurrentEntity();
				this.dispatchScroll();
			}
		}
	}
	protected void nextPage() throws Exception{
		if ( this.dataList.getList().isEmpty() ){
			this.resetGridWhenDataListIsEmpty();			
		} else {	
			if ( this.currentEntity == null ){
				this.firstPage();
			} else {
				Integer index = this.dataList.getList().indexOf(this.currentEntity);
				if ( index == -1 ){
					throw new Exception( "Can't find object with id " + this.currentEntity.getId() + " in dataList."  );
				} 

				if ( index + this.getMaxRecordCount() < this.dataList.getList().size() ){

					int firstIndex = (int) (index / this.getMaxRecordCount()) ;  
					firstIndex = ( firstIndex + 1 ) * this.getMaxRecordCount();
					Integer lastIndex = ( firstIndex + this.getMaxRecordCount() ) < this.dataList.getList().size() ? ( firstIndex + this.getMaxRecordCount() ) :  this.dataList.getList().size();					
					
					this.fillPageStoreWithPage( firstIndex, lastIndex );

					this.setCurrentEntity( (AbstractEntity)this.dataList.getList().get(firstIndex) );
					this.setIndexCurrentEntity( firstIndex + 1 );
					this.setDataListSize( this.dataList.getList().size() );

					this.fillFieldValuesWithCurrentEntity();
					this.dispatchScroll();
				}
			}
		}
	}
	
	private void resetGridWhenDataListIsEmpty() throws Exception{
		this.setIndexCurrentEntity( 0 );
		this.setDataListSize( 0 );
		this.currentEntity = null;
		this.currentEntityBeforeDispatchInsert = null;
		this.clearFields();
	}
	
	public void firstEntity() throws Exception{
		if ( this.dataList.getList().isEmpty() ){
			this.resetGridWhenDataListIsEmpty();
		} else {	
			this.setCurrentEntity( (AbstractEntity)this.dataList.getList().get(0) );
			this.setIndexCurrentEntity( 1 );
			this.setDataListSize( this.dataList.getList().size() );
			this.fillFieldValuesWithCurrentEntity();
			this.dispatchScroll();
		}
	}
	
	public void lastEntity() throws Exception{
		if ( this.dataList.getList().isEmpty() ){
			this.resetGridWhenDataListIsEmpty();			
		} else {	
			this.setCurrentEntity( (AbstractEntity)this.dataList.getList().get( this.dataList.getList().size() - 1 ) );
			this.setIndexCurrentEntity( this.dataList.getList().size() );
			this.setDataListSize( this.dataList.getList().size() );
			this.fillFieldValuesWithCurrentEntity();
			this.dispatchScroll();
		}
	}	
	
	public void firstPage() throws Exception{
		this.firstEntity();
		if ( ! this.dataList.getList().isEmpty() ){
			this.fillPageStoreWithFirstPage();	
		}
	}

	public void fillPageStoreWithFirstPage() throws Exception{
		Integer index = 0;			
		Integer lastIndex = getLastIndex();
		this.fillPageStoreWithPage( index, lastIndex );
	}
	
	protected Integer getLastIndex(){
		Integer index = 0;
		return ( index + this.getMaxRecordCount() ) < this.dataList.getList().size() ? ( index + this.getMaxRecordCount() ) :  this.dataList.getList().size();
	}	
	
	public void lastPage() throws Exception{
		if ( ! this.dataList.getList().isEmpty() ){
			int size = this.dataList.getList().size();
			if ( size == 1 ){
				this.lastEntity();
			} else {
				if ( size > this.getMaxRecordCount() ){
					Integer index = size - 1;
					Integer firstPageIndex = index - this.getMaxRecordCount();
					Integer lastPageIndex = index;

					this.fillPageStoreWithPage( firstPageIndex, lastPageIndex );

					this.setCurrentEntity( (AbstractEntity)this.dataList.getList().get(firstPageIndex) );
					this.setIndexCurrentEntity( firstPageIndex + 1 );
					this.setDataListSize( this.dataList.getList().size() );

					this.fillFieldValuesWithCurrentEntity();
					this.dispatchScroll();
					
				} else {
					this.firstPage();
				}	
			}	
		}
	}
	
	public void first() throws Exception{
		if ( this.viewMode.equals( DataGrid.VIEW_MODE_FORMVIEW ) ){
			this.firstEntity();
		} else {
			this.firstPage();
		}
	}
		
	public void previous() throws Exception{
		if ( this.viewMode.equals( DataGrid.VIEW_MODE_FORMVIEW ) ){
			this.previousEntity();
		} else {
			this.previousPage();
		}
	}
	
	public void next() throws Exception{
		if ( this.viewMode.equals( DataGrid.VIEW_MODE_FORMVIEW ) ){
			this.nextEntity();
		} else {
			this.nextPage();
		}
	}

	public void last() throws Exception{
		if ( this.viewMode.equals( DataGrid.VIEW_MODE_FORMVIEW ) ){
			this.lastEntity();
		} else {
			this.lastPage();
		}
	}
	
	//TODO tirar campos invisiveis
	protected void fillPageStoreWithPage( Integer index, Integer lastIndex ) throws Exception{
		ArrayList<DyadBaseModel> page = new ArrayList<DyadBaseModel>();
		for (int i = index; i < lastIndex; i++) {
			AbstractEntity entityToModelStore = (AbstractEntity)this.dataList.getList().get(i);
			DyadBaseModel model = ObjectConverter.getInstance().getTableViewModel( entityToModelStore );
			page.add( model );
		}							
		this.setTableViewPageStore( page );
	}
	
	public void onBeforeInsert()throws Exception{};
	public void onAfterInsert()throws Exception{};

	private WidgetListener onInsertListener = new WidgetListener(){

		@Override
		public void handleEvent(Object sender) throws Exception {
			if ( ! DataGrid.this.canInsert ){
				throw new Exception("This grid doesn't allow insert.");
			}	
			if ( DataGrid.this.readOnly ){
				throw new Exception("This grid is read only.");
			}	
			
			ProductLicense license = getCurrentEntityLicense();
			
			if (license != null && !SecurityUtil.userCancustomizeLicense(getDatabase(), UserInfoAspect.getUserKey(), license.getId())) {
				throw new AppException(translate("User can't customize this license!"));
			}
					
			DataGrid.this.currentEntityBeforeDispatchInsert = DataGrid.this.currentEntity;
			
			DataGrid.this.setCurrentEntity((AbstractEntity)DataGrid.this.dataList.newEntity( getDatabase(), DataGrid.this.beanName, getLicenseId() ));
			DataGrid.this.setIndexCurrentEntity( 0 );
			DataGrid.this.setDataListSize( DataGrid.this.dataList.getList().size() );

			DataGrid.this.fillFieldValuesWithCurrentEntity();
			

			int size = DataGrid.this.dataList.getList().size();
			if ( size == 1 ){
				DataGrid.this.fillPageStoreWithPage( 0, 1 );
			} else {
				if ( size > DataGrid.this.getMaxRecordCount() ){
					Integer firstPageIndex = size - DataGrid.this.getMaxRecordCount();
					Integer lastPageIndex = size;

					DataGrid.this.fillPageStoreWithPage( firstPageIndex, lastPageIndex );
				} else {
					DataGrid.this.fillPageStoreWithPage( 0, size );
				}	
			}			
			
			
			DataGrid.this.dispatchScroll();		

			DataGrid.this.setEditionMode(DataGrid.EDITION_MODE_INSERT);
			
		}
		
	};
	
	public void onInsert() throws Exception{
		
	};	
	
	public void dispatchInsert()throws Exception{
		this.onBeforeInsert();
		this.onInsert();
		this.onAfterInsert();
	}
	
	public void onBeforeSearch(String fieldName, String searchToken)throws Exception{
		this.setSearchFieldName(fieldName);
		this.setSearchToken(searchToken);
	};
	public void onAfterSearch(String fieldName, String searchToken)throws Exception{
		this.setSearchFieldName(null);
		this.setSearchToken(null);
	};
	
	private WidgetListener onSearchListener = new WidgetListener(){

		@Override
		public void handleEvent(Object sender) throws Exception {					
			DataList list = DataGrid.this.dataList;
			String field = getSearchFieldName();
			Object searchToken = null;
			Field fld = DataGrid.this.getFieldByName(field);
			if ( fld instanceof FieldCombo || fld instanceof FieldString ||
				 fld instanceof FieldLookup || fld instanceof FieldSimpleDate || 
				 fld instanceof FieldTime ){
				
				searchToken = getSearchToken();
			} else if ( fld instanceof FieldInteger ){
				searchToken = new Long( getSearchToken() );
			} else if ( fld instanceof FieldNumber ){
				searchToken = new Double(getSearchToken());
			} 
			
			Predicate pred = fld.getPredicate(getSearchFieldName(), searchToken);
			lastSearchResult = (List)CollectionUtils.select(list.getList(), pred);
			
			if ( lastSearchResult != null && lastSearchResult.size() > 0 ){
				DataGrid.this.goToEntity((AbstractEntity)lastSearchResult.get(0));
				lastOccurIndex = 0;
			}
		}		
	};
	
	public void onSearch(String fieldName, String searchToken) throws Exception{
		
	}

	public void dispatchSearch(String fieldName, String searchToken)throws Exception{
		this.onBeforeSearch(fieldName, searchToken);
		this.onSearch(fieldName, searchToken);
		this.onAfterSearch(fieldName, searchToken);
	}
	
	public void searchNextOccur() throws Exception{
		if ( lastSearchResult != null && lastOccurIndex != null && lastSearchResult.size() > lastOccurIndex + 1 ){
			lastOccurIndex++;
			goToEntity((AbstractEntity)lastSearchResult.get(lastOccurIndex));
		}
	}
	
	public void onBeforeScroll()throws Exception{};
	public void onAfterScroll()throws Exception{};
	
	private WidgetListener onScrollListener = new WidgetListener(){		
		@Override
		public void handleEvent(Object sender) throws Exception {
			DataGrid.this.dispatchDetailMasterScroll();			
		}
		
	};
	public void onScroll()throws Exception{}
	public void dispatchScroll()throws Exception{
		this.onBeforeScroll();
		this.onScroll();
		this.onAfterScroll();
	}
	
	@SuppressWarnings("unchecked")
	private void dispatchDetailMasterScroll() throws Exception{
		for (Iterator iterator = this.getFields().iterator(); iterator.hasNext();) {
			Field field = (Field) iterator.next();
			if ( field instanceof FieldMasterDetail ){
				((FieldMasterDetail)field).getDetailGrid().onMasterScroll();
			}
		}		
	}
	
	//*****************************************************************************//
	//********************************* POST **************************************//	
	//*****************************************************************************//

	@SuppressWarnings("unchecked")
	private void validatePost() throws Exception{		
		if ( this.readOnly ){
			throw new Exception("This grid is read only.");
		}
		
		String strKey = AbstractEntity.getClassIdentifier(getClassReference());		
		
		if (strKey != null){
			/*ProductLicense license = getCurrentEntityLicense();
			if (license != null && !SecurityUtil.userCancustomizeLicense(getDatabase(), UserInfoAspect.getUserKey(), license.getId())) {
				throw new AppException(translate("User can't customize this license!"));
			}*/
			
			Long classKey = Long.parseLong(strKey);
			
			if (getEditionMode().equals(EDITION_MODE_EDIT)){
				if (!SecurityUtil.userHasPermission(getDatabase(), UserInfoAspect.getUserKey(), classKey, PermissionTypes.Update.toString())){
					throw new AppException("Access denied!");
				}
			} else if (getEditionMode().equals(EDITION_MODE_INSERT)){
				if (!SecurityUtil.userHasPermission(getDatabase(), UserInfoAspect.getUserKey(), classKey, PermissionTypes.Insert.toString())){
					throw new AppException("Access denied!");
				}
			}
		}
		
		List<Field> allRequiredEmptyFields = this.getAllRequiredEmptyFieldsFromTheCurrentEntity();
		if ( ! allRequiredEmptyFields.isEmpty() ){
			String labels = "";
			for (Iterator iterator = allRequiredEmptyFields.iterator(); iterator.hasNext();) {
				Field field = (Field) iterator.next();
				labels += field.getLabel() + ";";
			}
			throw new Exception( "Can't execute post. This fields are required and they are still empty: " + labels + ";" );
		}
		if ( this.dataList.getList().indexOf(this.currentEntity) == -1 ){
			throw new Exception( "Internal error. Can't find an object with id " + this.currentEntity + "." );
		}	
	}	
	
	@SuppressWarnings("unchecked")
	protected void dispatchBeforePostAndBeforePostFromDetailGrids()throws Exception{
		this.onBeforePost();
		for (Iterator iterator = this.getFields().iterator(); iterator.hasNext();) {
			Field field = (Field) iterator.next();
			if ( field instanceof FieldMasterDetail ){
				DetailGrid detailGrid = ((FieldMasterDetail) field).getDetailGrid();
				if ( detailGrid.getEditionMode() != null ){
					detailGrid.dispatchBeforePostAndBeforePostFromDetailGrids();				
				}	
			}	
		}		
	};
	
	@SuppressWarnings("unchecked")
	protected void dispatchAfterPostAndAfterPostFromDetailGrids()throws Exception{
		this.onAfterPost();
		for (Iterator iterator = this.getFields().iterator(); iterator.hasNext();) {
			Field field = (Field) iterator.next();
			if ( field instanceof FieldMasterDetail ){
				DetailGrid detailGrid = ((FieldMasterDetail) field).getDetailGrid();
				if ( detailGrid.getEditionMode() != null ){
					detailGrid.dispatchAfterPostAndAfterPostFromDetailGrids();				
				}	
			}	
		}		
	};	
	
	//to be override by the coder.
	public void onBeforePost()throws Exception{};
	public void onAfterPost()throws Exception{};

	private WidgetListener onPostListener = new WidgetListener(){

		@Override
		public void handleEvent(Object sender) throws Exception {
			DataGrid.this.validatePost();		
			
			//Dispatch post of detail grids.
			for (Iterator iterator = DataGrid.this.getFields().iterator(); iterator.hasNext();) {
				Field field = (Field) iterator.next();
				if ( field instanceof FieldMasterDetail ){
					DetailGrid detailGrid = ((FieldMasterDetail) field).getDetailGrid();
					if ( detailGrid.getEditionMode() != null ){
						detailGrid.onPost();				
					}	
				}	
			}				
			
			Boolean changed = DataGrid.this.fillCurrentEntityWithFieldValues();
			//System.out.println("Irá salvar o registro: changed:" + changed);
			//helton: comentei o teste do change pq acho que ele sempre está retornando false, pois houve uma alteracao do comportamento do servico de setValue minha e do eduardo.
			//if ( changed ){
				DataGrid.this.dataList.save( DataGrid.this.currentEntity );
			//}
			
			DataGrid.this.setIndexCurrentEntity( DataGrid.this.dataList.getList().indexOf(DataGrid.this.currentEntity) + 1 );
			DataGrid.this.setDataListSize( DataGrid.this.dataList.getList().size() );

			DataGrid.this.setEditionMode(null);			
		}
		
	};
	
	protected void onPost()throws Exception{};
	
	public void dispatchPost()throws Exception{
		if ( this.getEditionMode() != null ){
			this.dispatchBeforePostAndBeforePostFromDetailGrids();		
			this.onPost();
			this.dispatchAfterPostAndAfterPostFromDetailGrids();
		}	
	}
	
	
	//*****************************************************************************//
	//********************************* DELETE ************************************//	
	//*****************************************************************************//	
	private void validateDelete() throws Exception{
		if ( ! this.canDelete ){
			throw new Exception("This grid doesn't allow delete.");
		}

		ProductLicense license = getCurrentEntityLicense();
		
		if (license != null && !SecurityUtil.userCancustomizeLicense(getDatabase(), UserInfoAspect.getUserKey(), license.getId())) {
			throw new AppException(translate("User can't customize this license!"));
		}
		
		String strKey = AbstractEntity.getClassIdentifier(getClassReference());		
		Long classKey = strKey == null ? null : Long.parseLong(strKey);
		
		if (classKey != null && !SecurityUtil.userHasPermission(getDatabase(), UserInfoAspect.getUserKey(), classKey, PermissionTypes.Delete.toString())){
			throw new AppException("Access denied!");
		}
		
		if ( this.dataList.getList().indexOf( this.currentEntity ) == -1 ){
			throw new Exception( "Can't find an object with id " + this.currentEntity.getId() + ". Contact the system administrator and show this error." );
		}	
		if ( this.readOnly ){
			throw new Exception("This grid is read only.");
		}	
	}
	
	public void onBeforeDelete() throws Exception{};	
	public void onAfterDelete()throws Exception{};
	
	private WidgetListener onDeleteListener = new WidgetListener(){

		@Override
		public void handleEvent(Object sender) throws Exception {
			DataGrid.this.validateDelete();
			
			//Dispatch delete of detail grids.
			for (Iterator iterator = DataGrid.this.getFields().iterator(); iterator.hasNext();) {
				Field field = (Field) iterator.next();
				if ( field instanceof FieldMasterDetail ){
					DetailGrid detailGrid = ((FieldMasterDetail) field).getDetailGrid();				
					
					DataList dataList = detailGrid.getDataList();
					int size = dataList.getList().size();
					for (int i = 0; i < size; i++) {
						detailGrid.dispatchDelete();				
					}
				}	
			}			
			
			if ( DataGrid.this.editionMode == DataGrid.EDITION_MODE_INSERT ){
				DataGrid.this.dispatchCancel();
			} else {
				int index = DataGrid.this.dataList.getList().indexOf( DataGrid.this.currentEntity ); 
				if ( index == -1 ){
					throw new Exception("Internal erro. Cant find current Entity.");
				}
				
				DataGrid.this.dataList.delete( DataGrid.this.currentEntity );		
				
				if ( DataGrid.this.dataList.getList().isEmpty() || DataGrid.this.dataList.getList().size() == 1 ){
					DataGrid.this.firstEntity();
				} else {
					if ( index >= DataGrid.this.dataList.getList().size() ){
						DataGrid.this.lastEntity();
					} else {
						DataGrid.this.goToEntityIndex( index );
					}
				}			
				
				/*AbstractEntity previousEntity = null;
				try{
					if ( index > ( this.dataList.getList().size() - 1 ) ){
						previousEntity = (AbstractEntity)this.dataList.getList().get( this.dataList.getList().size() - 1 );
					} else {
						previousEntity = (AbstractEntity)this.dataList.getList().get(index);
					}	
				} catch(Exception e){}

				if ( this.dataList.isEmpty() ){
					this.firstEntity();
				} else {
					this.previousEntity( previousEntity );				
				}
				this.setDataListSize( this.dataList.getList().size() );
				*/
							
				DataGrid.this.setEditionMode(null);
			}			
		}
		
	};
	
	public void onDelete() throws Exception{};
	
	public void dispatchDelete() throws Exception{
		this.onBeforeDelete();
		this.onDelete();
		this.onAfterDelete();
	}
	
	
	//*****************************************************************************//
	//********************************* CANCEL ************************************//	
	//*****************************************************************************//
	@SuppressWarnings("unchecked")
	protected void dispatchBeforeCancelAndBeforeCancelFromDetailGrids()throws Exception{
		this.onBeforeCancel();
		for (Iterator iterator = this.getFields().iterator(); iterator.hasNext();) {
			Field field = (Field) iterator.next();
			if ( field instanceof FieldMasterDetail ){
				DetailGrid detailGrid = ((FieldMasterDetail) field).getDetailGrid();
				if ( detailGrid.getEditionMode() != null ){
					detailGrid.dispatchBeforeCancelAndBeforeCancelFromDetailGrids();				
				}	
			}	
		}		
	};
	
	@SuppressWarnings("unchecked")
	protected void dispatchAfterCancelAndAfterCancelFromDetailGrids()throws Exception{
		this.onAfterCancel();
		for (Iterator iterator = this.getFields().iterator(); iterator.hasNext();) {
			Field field = (Field) iterator.next();
			if ( field instanceof FieldMasterDetail ){
				DetailGrid detailGrid = ((FieldMasterDetail) field).getDetailGrid();
				if ( detailGrid.getEditionMode() != null ){
					detailGrid.dispatchAfterCancelAndAfterCancelFromDetailGrids();				
				}	
			}	
		}		
	};	
	
	//to be override by the coder
	public void onBeforeCancel()throws Exception{};
	public void onAfterCancel()throws Exception{};

	WidgetListener onCancelListener = new WidgetListener(){

		@Override
		public void handleEvent(Object sender) throws Exception {
			if ( DataGrid.this.readOnly ){
				throw new Exception("This grid is read only.");
			}	
					
			//Dispatch post of detail grids.
			for (Iterator iterator = DataGrid.this.getFields().iterator(); iterator.hasNext();) {
				Field field = (Field) iterator.next();
				if ( field instanceof FieldMasterDetail ){
					DetailGrid detailGrid = ((FieldMasterDetail) field).getDetailGrid();
					if ( detailGrid.getEditionMode() != null ){
						detailGrid.onCancel();				
					}	
				}	
			}					
			
			if ( DataGrid.this.editionMode == DataGrid.EDITION_MODE_INSERT ){
				int index = DataGrid.this.dataList.getList().indexOf( DataGrid.this.currentEntity ); 
				if ( index == -1 ){
					throw new Exception("Internal erro. Cant find current Entity.");
				}
				DataGrid.this.dataList.getList().remove(index);
				//this.lastEntity();
				DataGrid.this.setCurrentEntity( DataGrid.this.currentEntityBeforeDispatchInsert );
				DataGrid.this.setIndexCurrentEntity( DataGrid.this.dataList.getList().indexOf(DataGrid.this.currentEntity) + 1 );
				DataGrid.this.setDataListSize( DataGrid.this.dataList.getList().size() );			
				DataGrid.this.fillFieldValuesWithCurrentEntity();
				DataGrid.this.dispatchScroll();	
				
				DataGrid.this.currentEntityBeforeDispatchInsert = null;
			} else {
				DataGrid.this.fillFieldValuesWithCurrentEntity();			
			}		
			
			
			DataGrid.this.setEditionMode(null);			
		}
		
	};
	
	@SuppressWarnings("unchecked")
	public void onCancel()throws Exception{};
	
	public void dispatchCancel()throws Exception{
		if ( this.getEditionMode() != null ){
			this.dispatchBeforeCancelAndBeforeCancelFromDetailGrids();
			this.onCancel();
			this.dispatchAfterCancelAndAfterCancelFromDetailGrids();
		}	
	}
	
	
	//*****************************************************************************//
	//********************************** EDIT *************************************//	
	//*****************************************************************************//
	public void onBeforeEdit()throws Exception{};
	public void onAfterEdit()throws Exception{};
	
	private WidgetListener onEditListener = new WidgetListener(){

		@Override
		public void handleEvent(Object sender) throws Exception {
			if ( DataGrid.this.readOnly ){
				throw new Exception("This grid is read only.");
			}			
			DataGrid.this.setEditionMode(DataGrid.EDITION_MODE_EDIT);			
		}
		
	};
	
	public void onEdit()throws Exception{};
	
	public void dispatchEdit()throws Exception{
		this.onBeforeEdit();
		this.onEdit();
		this.onAfterEdit();
	}
		
	
	
	public boolean getCanInsert() {
		return canInsert;
	}
	public void setCanInsert(boolean canInsert) {
		this.canInsert = canInsert;
	}
	public boolean getCanDelete() {
		return canDelete;
	}
	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}
	public boolean getVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public boolean getReadOnly() {
		return readOnly;
	}
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	public Integer getMaxRecordCount() {
		return maxRecordCount;
	}
	public void setMaxRecordCount(Integer maxRecordCount) {
		this.maxRecordCount = maxRecordCount;
	}
	public boolean getHasTableView() {
		return hasTableView;
	}
	public void setHasTableView(boolean hasTableView) throws Exception {
		this.hasTableView = hasTableView;
		if (  hasTableView && ! this.hasFormView && this.viewMode != null && this.viewMode.equals( DataGrid.VIEW_MODE_FORMVIEW ) ){
			this.setViewMode( DataGrid.VIEW_MODE_TABLEVIEW );
		}
	}
	public boolean getHasFormView() {
		return hasFormView;
	}
	public void setHasFormView(boolean hasFormView) throws Exception {
		this.hasFormView = hasFormView;
		if ( hasFormView && ! this.hasTableView && this.viewMode != null && this.viewMode.equals( DataGrid.VIEW_MODE_TABLEVIEW ) ){
			this.setViewMode( DataGrid.VIEW_MODE_FORMVIEW );
		}
	};
			
	public Integer getEditionMode() {
		return editionMode;
	}
	public void setEditionMode(Integer editionMode) {
		this.editionMode = editionMode;
	}

	public Integer getViewMode() {
		return viewMode;
	}

	public void setViewMode(Integer viewMode) throws Exception {
		if ( viewMode != null ){
			if ( viewMode.equals(DataGrid.VIEW_MODE_FORMVIEW) && ! this.hasFormView ){
				throw new Exception("This grid does not allow form view mode.");
			}
			if ( viewMode.equals(DataGrid.VIEW_MODE_TABLEVIEW) && ! this.hasTableView ){
				throw new Exception("This grid does not allow form table view mode.");
			}
		}
		
		this.viewMode = viewMode;
	}
	
	protected void initializeGrid() throws Exception{
		addWidgetListener(DyadEvents.onInsert, onInsertListener, false);
		addWidgetListener(DyadEvents.onScroll, onScrollListener, false);
		addWidgetListener(DyadEvents.onPost, onPostListener, false);
		addWidgetListener(DyadEvents.onDelete, onDeleteListener, false);
		addWidgetListener(DyadEvents.onCancel, onCancelListener, false);
		addWidgetListener(DyadEvents.onEdit, onEditListener, false);
		addWidgetListener(DyadEvents.onSearch, onSearchListener, false);
		
		this.setMaxRecordCount(10);
		this.setCanInsert(true);
		this.setCanDelete(true);
		this.setCanNavigate(true);

		//forces send null to client
		this.setEditionMode(DataGrid.EDITION_MODE_INSERT);
		this.setEditionMode(null);
		
		try {
			this.setHasFormView(true);
			this.setHasTableView(true);
			this.setViewMode(DataGrid.VIEW_MODE_FORMVIEW);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.setType(GridTypes.DATA_GRID);
	}

	public Integer getIndexCurrentEntity() {
		return indexCurrentEntity;
	}
	public void setIndexCurrentEntity(Integer indexCurrentEntity) {
		this.indexCurrentEntity = indexCurrentEntity;
	}

	public Integer getDataListSize() {
		return dataListSize;
	}
	public void setDataListSize(Integer dataListSize) {
		this.dataListSize = dataListSize;
	}

	public List<DataGrid> getDetails() {
		if ( this.details == null ){
			this.details = new ArrayList<DataGrid>();
		}
		return details;
	}

	@Override
	public boolean add(Field field) throws Exception {
		if ( field instanceof FieldMasterDetail){			
			return addFieldMasterDetail( (FieldMasterDetail) field );
		} else {
			return super.add(field);
		}
	}
	
	protected boolean addFieldMasterDetail(FieldMasterDetail fieldMasterDetail) throws Exception{	
		DetailGrid detailGrid = fieldMasterDetail.getDetailGrid();
		
		if ( this == detailGrid ){
			throw new Exception( "This grid cannot be its own detail." );
		}
		
		detailGrid.setHttpSession(getHttpSession());

		if ( this.getFields().indexOf(fieldMasterDetail) == -1 ){
			detailGrid.setMaster(this);
    		this.getDetails().add(detailGrid);
    		super.add(fieldMasterDetail);
    		fieldMasterDetail.setDetailDataList();
    		return true;
    	}
    	return false;
    }	
	
	@SuppressWarnings("unchecked")
	public List<Field> getAllRequiredEmptyFieldsFromTheCurrentEntity() {
		List<Field> allRequiredEmptyFields = new ArrayList<Field>();
		for (Iterator iterator = this.getFields().iterator(); iterator.hasNext();) {
			Field field = (Field) iterator.next();
			Boolean required = field.getRequired();
			Boolean visible = field.getVisible();
			System.out.println( "AllRequired:" + field.getName() );
			System.out.println( "required:" + required );
			if ( required && visible ){
				if ( field instanceof FieldMasterDetail ){
					DataGrid dataGrid = (DataGrid)((FieldMasterDetail)field).getDetailGrid();
					List<Field> detailFields = dataGrid.getAllRequiredEmptyFieldsFromTheCurrentEntity();
					if ( ! detailFields.isEmpty() ){
						allRequiredEmptyFields.add(field);
					}
				} else {
					if ( ( field.getValue() == null || ( ( field.getValue() instanceof String ) && field.getValue().toString().trim().equals("") ) ) ){
						allRequiredEmptyFields.add(field);
					}
				}	
			}
		}		
		return allRequiredEmptyFields;
	}

	@SuppressWarnings("unchecked")
	public void fillFieldValuesWithCurrentEntity() throws Exception{
		if ( this.currentEntity == null ){
			this.clearFields();
		} else {	
			for (Iterator iterator = this.getFields().iterator(); iterator.hasNext();) {
				Field field = (Field) iterator.next();
				if ( ! ( field instanceof FieldMasterDetail ) ){
					String fieldName = field.getName();
					System.out.println("FieldName:-> " + fieldName );
					Object value = ReflectUtil.getFieldValue(this.currentEntity, fieldName );
					System.out.println("Setting-> " + fieldName + " : " + value);															
					
					field.setValueWithoutEditing( value );
				}	
			}		
		}
	}	

	@SuppressWarnings({ "unchecked" })
	private Boolean fillCurrentEntityWithFieldValues() throws Exception{
		Boolean changed = false;
		if ( this.currentEntity != null ){
			for (Iterator iterator = this.getFields().iterator(); iterator.hasNext();) {
				Field field = (Field) iterator.next();
				if ( ! ( field instanceof FieldMasterDetail ) ){
					Object fieldValue = field.getValue();
					String fieldName = field.getName();
					System.out.println("fieldName=" + fieldName);
					String fieldNameLowerCase = fieldName.toLowerCase();					
					if ( ! fieldNameLowerCase.equals("version")  ){
						/*Antes do oldValue
						 * 
						 * Object entityFieldValue = ReflectUtil.getFieldValue( this.currentEntity, fieldName  );
						if ( ( fieldValue == null && entityFieldValue != null ) || 
 							 ( fieldValue != null && entityFieldValue == null ) ||  
							 ( fieldValue != null && entityFieldValue != null
							   && ! fieldValue.equals(entityFieldValue) ) ){
							System.out.println("Setting in the Entity -> " + fieldName + " :  new " + fieldValue + "; old " + entityFieldValue );
							ReflectUtil.setFieldValue(this.currentEntity, fieldName, fieldValue );
							changed = true;
						}
						*/
						Object oldValue = this.currentEntity.getOldValue(field.getName());
						Object currentValue = ReflectUtil.getFieldValue( this.currentEntity, fieldName );
						if ( ( currentValue == null && oldValue != null ) || 
 							 ( currentValue != null && oldValue == null ) ||  
							 ( currentValue != null && oldValue != null
							   && ! oldValue.equals(currentValue) ) ){
							changed = true;
						}
					}
				}	
			}		
		}
		return changed;
	}

	
	@Override
	public void defineGrid() throws Exception {
		if ( this.dataList == null ){
			throw new Exception("Its not possible call defineGrid with dataList null.");
		}
		if ( this.getBeanName() != null ){
			this.createFieldsBasedInBean();
		}
		this.validateViewMode();
		
		if ( this.getViewMode().equals( DataGrid.VIEW_MODE_FORMVIEW )){
			this.firstEntity();
		} else {
			this.firstPage();
		}
		
		this.prepareFieldDetails();				
	}	

	@SuppressWarnings("unchecked")
	protected void prepareFieldDetails() throws Exception{
		for (Iterator iterator = this.getFields().iterator(); iterator.hasNext();) {
			Field field = (Field) iterator.next();
			if ( field instanceof FieldMasterDetail ){
				((FieldMasterDetail)field).prepareGrid();
			}
		}		
	}	
			
	@SuppressWarnings("unchecked")
	private void createFieldsBasedInBean() throws Exception {
		if ( this.getBeanName().equals("")){
			throw new Exception( "This grid cannot be its own detail." );
		}
		Class clazz = Class.forName(this.getBeanName());
		Object object = clazz.newInstance();
		
		List<java.lang.reflect.Field> fields = ReflectUtil.getClassFields(clazz, true);
		
		Class[] intClasses = {Integer.class, Long.class};
		Class[] doubleClasses = {Double.class, Float.class};
		Class[] stringClasses = {String.class, Character.class, BigInteger.class, BigDecimal.class};
		Class[] masterDetailClasses = {MasterDetail.class};
		
		//Class[] enumClasses = {Enum.class, Enumeration.class};										

		for (java.lang.reflect.Field f : fields) {

			Class fieldClass = f.getType();

			if (ReflectUtil.isTransient(f)){
				if (f.getAnnotation(Calculated.class) == null && ArrayUtils.indexOf(masterDetailClasses, fieldClass) == -1 ){					
					continue;
				}
			}
			
			Field gridField = null;		
			
			String name = f.getName(); 
			
			Boolean uploadField = (Boolean)Field.getAttributeValueForPropName(object, name, MetaField.upload);
			Boolean pictureField = (Boolean)Field.getAttributeValueForPropName(object, name, MetaField.picture);

			if ( Field.containsAttributeValueForPropName( object, name, MetaField.options ) ){				
				gridField = new FieldCombo( this, name );				
				ArrayList options = (ArrayList)Field.getAttributeValueForPropName(object, name, MetaField.options );				
				((FieldCombo)gridField).setOptions(options);				
			} else if ( ReflectUtil.inheritsFrom(fieldClass, AbstractEntity.class ) &&
					Field.containsAttributeValueForPropName( object, name, MetaField.beanName )	){				

				if ( ! Field.containsAttributeValueForPropName( object, name, MetaField.beanName ) ){
					throw new Exception("Bean name must be informed.");
				}
				String beanName = (String)Field.getAttributeValueForPropName(object, name, MetaField.beanName );				
				gridField = new FieldLookup( this, name );
				((FieldLookup)gridField).setBeanName(beanName);
				
				if ( Field.containsAttributeValueForPropName( object, name, MetaField.lookupClassId ) ){
					Long lookupClassId = (Long)Field.getAttributeValueForPropName(object, name, MetaField.lookupClassId );				
					((FieldLookup)gridField).setLookupClassId( lookupClassId  );
				}

				if ( Field.containsAttributeValueForPropName( object, name, MetaField.lookupType ) ){
					Integer lookupType = (Integer)Field.getAttributeValueForPropName(object, name, MetaField.lookupType );				
					((FieldLookup)gridField).setLookupType( lookupType );
				}
			} else if (ArrayUtils.indexOf(intClasses, fieldClass) != -1 && 
					   ((uploadField != null && uploadField) || (pictureField != null && pictureField))){
				gridField = new FieldUpload( this, name );
				((FieldUpload)gridField).setPictureField(pictureField);
			} else if (ArrayUtils.indexOf(intClasses, fieldClass) != -1){
				gridField = new FieldInteger( this, name );
				
				if ( Field.containsAttributeValueForPropName( object, name, MetaField.min ) ){
					Double min = (Double)Field.getAttributeValueForPropName(object, name, MetaField.min );				
					((FieldInteger)gridField).setMin(min);
				}

				if ( Field.containsAttributeValueForPropName( object, name, MetaField.max ) ){
					Double max = (Double)Field.getAttributeValueForPropName(object, name, MetaField.max );				
					((FieldInteger)gridField).setMax(max);
				}	

				if ( Field.containsAttributeValueForPropName( object, name, MetaField.decimalPrecision ) ){
					Integer decimalPrecision = (Integer)Field.getAttributeValueForPropName(object, name, MetaField.decimalPrecision );
					((FieldInteger)gridField).setDecimalPrecision( decimalPrecision );
				}	

				if ( Field.containsAttributeValueForPropName( object, name, MetaField.negativeInRed ) ){
					Boolean negativeInRed = (Boolean)Field.getAttributeValueForPropName(object, name, MetaField.negativeInRed );
					((FieldInteger)gridField).setNegativeInRed(negativeInRed);
				}	
			} else if (ArrayUtils.indexOf(doubleClasses, fieldClass) != -1){
				gridField = new FieldNumber( this, name );

				if ( Field.containsAttributeValueForPropName( object, name, MetaField.min ) ){
					Double min = (Double)Field.getAttributeValueForPropName(object, name, MetaField.min );				
					((FieldNumber)gridField).setMin(min);
				}	

				if ( Field.containsAttributeValueForPropName( object, name, MetaField.max ) ){
					Double max = (Double)Field.getAttributeValueForPropName(object, name, MetaField.max );				
					((FieldNumber)gridField).setMax(max);
				}

				if ( Field.containsAttributeValueForPropName( object, name, MetaField.decimalPrecision ) ){
					Integer decimalPrecision = (Integer)Field.getAttributeValueForPropName(object, name, MetaField.decimalPrecision );
					((FieldNumber)gridField).setDecimalPrecision( decimalPrecision );
				}

				if ( Field.containsAttributeValueForPropName( object, name, MetaField.negativeInRed ) ){
					Boolean negativeInRed = (Boolean)Field.getAttributeValueForPropName(object, name, MetaField.negativeInRed );
					((FieldNumber)gridField).setNegativeInRed(negativeInRed);
				}	
			}  else if (ArrayUtils.indexOf(stringClasses, fieldClass) != -1 && Field.containsAttributeValueForPropName( object, name, MetaField.htmlEditor )){
				gridField = new FieldHtmlEditor( this, name );
					
			} else if (ArrayUtils.indexOf(stringClasses, fieldClass) != -1){
				gridField = new FieldString( this, name );

				if ( Field.containsAttributeValueForPropName( object, name, MetaField.caseType ) ){
					String caseType = (String)Field.getAttributeValueForPropName(object, name, MetaField.caseType );
					((FieldString)gridField).setCaseType(caseType);
				}	

				if ( Field.containsAttributeValueForPropName( object, name, MetaField.password ) ){
					Boolean password = (Boolean)Field.getAttributeValueForPropName(object, name, MetaField.password );
					((FieldString)gridField).setPassword(password);				
				}	
				
				if (fieldClass.equals(BigDecimal.class)) {
					((FieldString)gridField).setOnlyNumbers(true);
					((FieldString)gridField).setAllowDecimals(true);
				}
				
				if (fieldClass.equals(BigInteger.class)) {
					((FieldString)gridField).setOnlyNumbers(true);
					((FieldString)gridField).setAllowDecimals(false);
				}
			} else if (ArrayUtils.indexOf(masterDetailClasses, fieldClass) != -1){
				Boolean isVisible = true;
				if ( Field.containsAttributeValueForPropName( object, name, MetaField.visible ) ){
					isVisible = (Boolean)Field.getAttributeValueForPropName(object, name, MetaField.visible );
				}	

				if ( isVisible ){
					gridField = new FieldMasterDetail( this, name );
					FieldMasterDetail fieldMasterDetail = (FieldMasterDetail)gridField;

					if ( Field.containsAttributeValueForPropName( object, name, MetaField.beanName ) ){
						fieldMasterDetail.setBeanName( (String)Field.getAttributeValueForPropName(object, name, MetaField.beanName ) );
					}	

					if ( Field.containsAttributeValueForPropName( object, name, MetaField.masterFieldNames ) ){
						fieldMasterDetail.setMasterFieldNames( (String)Field.getAttributeValueForPropName(object, name, MetaField.masterFieldNames ) );
					}	

					if ( Field.containsAttributeValueForPropName( object, name, MetaField.detailFieldNames ) ){
						fieldMasterDetail.setDetailFieldNames( (String)Field.getAttributeValueForPropName(object, name, MetaField.detailFieldNames ) );
					}	

					if ( Field.containsAttributeValueForPropName( object, name, MetaField.detailIndexFieldNames ) ){
						fieldMasterDetail.setDetailIndexFieldNames( (String)Field.getAttributeValueForPropName(object, name, MetaField.detailIndexFieldNames ) );
					}	

					if ( Field.containsAttributeValueForPropName( object, name, MetaField.masterDeleteAction ) ){
						fieldMasterDetail.setMasterDeleteAction( (String)Field.getAttributeValueForPropName(object, name, MetaField.masterDeleteAction ) );
					}	

					if ( Field.containsAttributeValueForPropName( object, name, MetaField.masterDetailMaxRecordCount ) ){
						fieldMasterDetail.setMasterDetailMaxRecordCount( (Integer)Field.getAttributeValueForPropName(object, name, MetaField.masterDeleteAction ) );
					}	

					//
					//((FieldMasterDetail)gridField).setMasterFieldNames();
					//((FieldMasterDetail)gridField).setDetailFieldNames();
				}

				//} else if (ArrayUtils.indexOf(enumClasses, fieldClass) != -1){
				//return FieldType.ENUM;
				//} else if (ReflectUtil.inheritsFrom(fieldClass, Collection.class) ||
				//ReflectUtil.inheritsFrom(fieldClass, Map.class)){
				//return FieldType.DETAIL;
			} else if (ReflectUtil.inheritsFrom(fieldClass, Date.class)){
				Temporal temporal = (Temporal) f.getAnnotation(Temporal.class);
				if (temporal == null || temporal.value().equals(TemporalType.DATE)){				
					gridField = new FieldSimpleDate( this, name );
				} else if (temporal.value().equals(TemporalType.TIME)){
					//return FieldType.TIME;
					gridField = new FieldTime( this, name );
				} else if (temporal.value().equals(TemporalType.TIMESTAMP)){
					gridField = new FieldSimpleDate( this, name );
				}

				if ( Field.containsAttributeValueForPropName( object, name, MetaField.dateFormat ) ){
					String dateFormat = (String)Field.getAttributeValueForPropName(object, name, MetaField.dateFormat );
					((FieldSimpleDate)gridField).setDateFormat(dateFormat);
				}	
			} else if (ReflectUtil.inheritsFrom(fieldClass, Boolean.class)){
				gridField = new FieldBoolean( this, name );
			}

			//Bypass if gridField is null.
			if ( gridField != null ){
				System.out.println("Criou o field:" + name);

				if ( Field.containsAttributeValueForPropName( object, name, MetaField.required ) ){
					Boolean required = (Boolean)Field.getAttributeValueForPropName(object, name, MetaField.required );
					gridField.setRequired( required );
					System.out.println("required:" + required);
				}

				if ( Field.containsAttributeValueForPropName( object, name, MetaField.visible ) ){
					Boolean visible = (Boolean)Field.getAttributeValueForPropName(object, name, MetaField.visible );
					gridField.setVisible(visible);
				}	

				if ( Field.containsAttributeValueForPropName( object, name, MetaField.listable ) ){
					Boolean listable = (Boolean)Field.getAttributeValueForPropName(object, name, MetaField.listable );
					gridField.setListable(listable);
				}

				if ( Field.containsAttributeValueForPropName( object, name, MetaField.column ) ){
					Integer column = (Integer)Field.getAttributeValueForPropName(object, name, MetaField.column );
					gridField.setColumn(column);
				}	

				if ( Field.containsAttributeValueForPropName( object, name, MetaField.group ) ){
					String group = (String)Field.getAttributeValueForPropName(object, name, MetaField.group );
					gridField.setGroup(group);
				}	

				if ( Field.containsAttributeValueForPropName( object, name, MetaField.height ) ){
					Integer height = (Integer)Field.getAttributeValueForPropName(object, name, MetaField.height );
					gridField.setHeight(height);
				}	

				if ( Field.containsAttributeValueForPropName( object, name, MetaField.help ) ){
					String help = (String)Field.getAttributeValueForPropName(object, name, MetaField.help );
					gridField.setHelp(help);
				}	

				if ( Field.containsAttributeValueForPropName( object, name, MetaField.label ) ){
					String label = (String)Field.getAttributeValueForPropName(object, name, MetaField.label );
					gridField.setLabel(label);
				}	

				if ( Field.containsAttributeValueForPropName( object, name, MetaField.order ) ){
					Integer order = (Integer)Field.getAttributeValueForPropName(object, name, MetaField.order );
					gridField.setOrder(order);
				}

				if ( Field.containsAttributeValueForPropName( object, name, MetaField.readOnly ) ){
					Boolean readOnly = (Boolean)Field.getAttributeValueForPropName(object, name, MetaField.readOnly );
					gridField.setReadOnly(readOnly);
					gridField.setReadOnlyFromClass(readOnly);
				} else {
					gridField.setReadOnlyFromClass(false);
				}

				if ( Field.containsAttributeValueForPropName( object, name, MetaField.width ) ){
					Integer width = (Integer)Field.getAttributeValueForPropName(object, name, MetaField.width );
					gridField.setWidth(width);
				}	

				if ( Field.containsAttributeValueForPropName( object, name, MetaField.canCustomizeValue ) ){
					Boolean canCustomizeValue = (Boolean)Field.getAttributeValueForPropName(object, name, MetaField.canCustomizeValue );
					gridField.setCanCustomizeValue(canCustomizeValue);
				}	
				
				if ( Field.containsAttributeValueForPropName( object, name, MetaField.tableViewVisible ) ){
					Boolean tableViewVisible = (Boolean)Field.getAttributeValueForPropName(object, name, MetaField.tableViewVisible );
					gridField.setTableViewVisible(tableViewVisible);
				}	
				
				//gridField.setTableViewable(meta.tableViewable());
				//gridField.setTableViewWidth(meta.tableViewWidth());
				//gridField.setDisplayFieldName(meta.displayFieldName());
				//gridField.setBeanName(meta.beanName());
				
				/*if (gridField instanceof FieldMasterDetail ){
					//this.add((FieldMasterDetail)gridField);
					this.addFieldMasterDetail( (FieldMasterDetail)gridField );
				} else {
					this.add(gridField);
				}*/
			}
			
			if (gridField != null && ReflectUtil.isTransient(f) && ! ( gridField instanceof FieldMasterDetail ) ){
				gridField.setIsCalculated(true);
			}
		}	
	}	

	@Override
	protected void addPropsToClientSincronizer() throws Exception {
		super.addPropsToClientSincronizer();

		validateViewMode();
	}
	
	private void validateViewMode() throws Exception{
		if ( ! this.getHasFormView() && !this.getHasTableView() ){
			throw new Exception("No view was defined to grid.");
		}
		
		if ( !this.getHasFormView() ){
			setViewMode(DataGrid.VIEW_MODE_TABLEVIEW);
		} else if ( !this.getHasTableView() ){
			setViewMode(DataGrid.VIEW_MODE_FORMVIEW );
		}
	}
	
	public void dispatchChangeView() throws Exception {
		if ( this.getViewMode().equals(DataGrid.VIEW_MODE_FORMVIEW) ){
			this.setViewMode( DataGrid.VIEW_MODE_TABLEVIEW );

			if( this.dataList.getList().size() > 0 ){
				Integer index = this.dataList.getList().indexOf(this.currentEntity);
				if ( index == -1 ){
					throw new Exception( "Can't find object with id " + this.currentEntity.getId() + " in dataList."  );
				} 
				
				this.fillPageStoreWithFirstPageOnChangeViewOrScroll(index);
			}	
		} else {
			this.setViewMode( DataGrid.VIEW_MODE_FORMVIEW );
		}
	}
	
	/**
	 * @author Danilo Sampaio
	 * @return
	 * @throws Exception
	 * Exporta os dados da grid e retorna a url do arquivo para download.
	 * O arquivo ficarÃ¡ disponÃ­vel por 30 minutos, depois serÃ¡ apagado.
	 */
	public String dispatchExport( String format) throws Exception {
		DataExport de = new DataExport( this.getDataList().getList() );		
		if ( format.equals(DataExport.CSV)){
			return de.toCSV(new CSVExporterImpl(this.getDatabase()));
		} else if ( format.equals(DataExport.PDF)){
			List<Field> gridFields = this.getFields();
			
			int ind = 0;
			int fieldCount = 0;
			for (Iterator iterator = gridFields.iterator(); iterator.hasNext();) {
				Field field = (Field) iterator.next();
				if ( field.getVisible() && !field.getType().equals(FieldTypes.FIELD_TYPE_LABEL) && 
					!field.getType().equals(FieldTypes.FIELD_TYPE_HTML_EDITOR) && 
					!field.getType().equals(FieldTypes.FIELD_TYPE_MASTERDETAIL) && 
					!field.getType().equals(FieldTypes.FIELD_TYPE_UPLOAD )){
					fieldCount++;
				}
			}
			String[] fields = new String[fieldCount];
			String[] fieldLabels = new String[fieldCount];
			for (Iterator iterator = gridFields.iterator(); iterator.hasNext();) {
				Field field = (Field) iterator.next();
				if ( field.getVisible() && !field.getType().equals(FieldTypes.FIELD_TYPE_LABEL) && 
						!field.getType().equals(FieldTypes.FIELD_TYPE_HTML_EDITOR) && 
						!field.getType().equals(FieldTypes.FIELD_TYPE_MASTERDETAIL) && 
						!field.getType().equals(FieldTypes.FIELD_TYPE_UPLOAD )){
					fields[ind] = field.getName();
					fieldLabels[ind] = field.getLabel();
					ind++;
				}	
			}
			return (String) de.toPDF(new JasperPDFExporterImpl(this.getDatabase(), fields, fieldLabels));
		}
		return null;
	}

	public void fillPageStoreWithFirstPageOnChangeViewOrScroll( Integer index ) throws Exception{
		Integer size = this.dataList.getList().size(); 
		if ( size < this.getMaxRecordCount() ) { 
			this.fillPageStoreWithFirstPage();
		} else {
			if ( index + this.getMaxRecordCount() < size ){
				int firstIndex = (int) (index / this.getMaxRecordCount()) ;  
				firstIndex = firstIndex * this.getMaxRecordCount();
				Integer lastIndex = firstIndex + this.getMaxRecordCount();
				this.fillPageStoreWithPage( firstIndex, lastIndex );							
			} else {
				int firstIndex = size - this.getMaxRecordCount();
				Integer lastIndex = size;
				this.fillPageStoreWithPage( firstIndex, lastIndex );							
			}
		}	
	}	
	
	
	public ArrayList<DyadBaseModel> getTableViewPageStore() {
		return tableViewPageStore;
	}

	public void setTableViewPageStore(ArrayList<DyadBaseModel> tableViewPageStore) {		
		this.tableViewPageStore = tableViewPageStore;
	}

	public boolean getCanNavigate() {
		return canNavigate;
	}

	public void setCanNavigate(boolean canNavigate) {
		this.canNavigate = canNavigate;		
	}

	private void setCurrentEntity(AbstractEntity AbstractEntity) {
		if ( AbstractEntity == null){
			this.setIdCurrentEntity(null);
		} else {
			this.setIdCurrentEntity(AbstractEntity.getId());
		}
		this.currentEntity = AbstractEntity;
	}
	public AbstractEntity getCurrentEntity() {
		return currentEntity;
	}

	public Long getIdCurrentEntity() {
		return idCurrentEntity;
	}
	protected void setIdCurrentEntity(Long idCurrentEntity) {
		this.idCurrentEntity = idCurrentEntity;
	}

	public Long getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(Long licenseId) {
		this.licenseId = licenseId;
	}

	public ArrayList<DyadBaseModel> getLicensesAvailable() {
		return licensesAvailable;
	}

	@Override
	public void setWindow(Window window) throws Exception {
		super.setWindow(window);
		this.createComboForAvaliableLicenses();
	}
	
	public void createComboForAvaliableLicenses() throws Exception{
		HttpSession httpSession = getWindow().getHttpSession();
		Long userKey = (Long)httpSession.getAttribute(GenericService.USER_KEY);
		String dataBaseFile = (String)httpSession.getAttribute(GenericService.GET_DATABASE_FILE);
		ArrayList<DyadBaseModel> licensesAvailable = SecurityUtil.getLicensesByUser( userKey, dataBaseFile );

		this.setLicensesAvailable( licensesAvailable );
	
	}
	
	public void setLicensesAvailable(ArrayList<DyadBaseModel> licensesAvailable) {
		this.licensesAvailable = licensesAvailable;
	}	
		
	@SuppressWarnings("unchecked")
	public void inheritBeanEvents() throws Exception {
		if (getBeanName() != null){
			Class clazz = Class.forName(getBeanName());			
			Hashtable<DyadEvents, Vector<WidgetListener>> map = AbstractEntity.getClassListeners(clazz, null); //AbstractEntity.getWidgetListeners().get(clazz);
			
			for (DyadEvents evt : map.keySet()){
				List<WidgetListener> listeners = (List<WidgetListener>)map.get(evt);
				for (int i = listeners.size() - 1; i >= 0; i--) {
					this.addWidgetListener(evt, listeners.get(i), true);
				}
			}						
		}
	}
	
	public ProductLicense getCurrentEntityLicense(){
		if (getCurrentEntity() == null){
			return null;
		} else {			
			ProductLicense license = ProductLicense.getLicenseById(getDatabase(), getCurrentEntity().getId());
			return license;
		}
	}

	public String getSearchFieldName() {
		return searchFieldName;
	}

	public void setSearchFieldName(String searchFieldName) {
		this.searchFieldName = searchFieldName;
	}

	public String getSearchToken() {
		return searchToken;
	}

	public void setSearchToken(String searchToken) {
		this.searchToken = searchToken;
	}

	public List getLastSearchResult() {
		return lastSearchResult;
	}

	public void setLastSearchResult(List lastSearchResult) {
		this.lastSearchResult = lastSearchResult;
	}

	public Integer getLastOccurIndex() {
		return lastOccurIndex;
	}

	public void setLastOccurIndex(Integer lastOccurIndex) {
		this.lastOccurIndex = lastOccurIndex;
	}
}


