package br.com.dyad.infrastructure.widget.grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import br.com.dyad.commons.data.DataList;
import br.com.dyad.infrastructure.annotations.SendToClient;
import br.com.dyad.infrastructure.widget.BaseAction;
import br.com.dyad.infrastructure.widget.BaseServerWidget;
import br.com.dyad.infrastructure.widget.Window;
import br.com.dyad.infrastructure.widget.field.Field;
import br.com.dyad.infrastructure.widget.field.FieldBoolean;
import br.com.dyad.infrastructure.widget.field.FieldInteger;
import br.com.dyad.infrastructure.widget.field.FieldLookup;
import br.com.dyad.infrastructure.widget.field.FieldMasterDetail;
import br.com.dyad.infrastructure.widget.field.FieldNumber;
import br.com.dyad.infrastructure.widget.field.FieldSimpleDate;
import br.com.dyad.infrastructure.widget.field.FieldString;
import br.com.dyad.infrastructure.widget.field.FieldTime;

/**
 * @enterprise Dyad & Associados (http://www.dyad.com.br)
 * @author Helton Gonçalves (helton@dyad.com.br;heltongoncalves@gmail.com)
 */
public abstract class Grid extends BaseServerWidget {
	@SendToClient
	private String title;
	@SendToClient
	private String name;	
	private Integer type;
	private Window window;
	private List<Field> fields;

	@Override
	public boolean add(BaseAction action) {
		boolean ok = super.add(action);
		if (ok) {
			action.setEnabled(true);
		}
		
		return ok;
	}
	
	private void initialize(){
		fields = new ArrayList<Field>();
	}
	
	public Grid( Window window ) throws Exception {
		super();
		setWindow(window);
	}

	public Grid( Window window, String name) throws Exception{
		this( window );
		this.initialize();
		this.setName(name);
	}
	
	public void defineFieldsAndDefineGrid() throws Exception{
		this.defineGrid();
		if ( this.fields.isEmpty() ){
			throw new Exception( "No fields defined in this grid. See grid " + this.getName() );
		}
		
		setLastFieldValues();		
	}
	
	protected void setLastFieldValues() throws Exception{};
	
	/*@SuppressWarnings("unchecked")
	private void addDeclaredFields() throws Exception{
		List<java.lang.reflect.Field> javaFields = ReflectUtil.getClassFields( this.getClass(), true );
		for (Iterator iterator = javaFields.iterator(); iterator.hasNext();) {
			java.lang.reflect.Field javaField = (java.lang.reflect.Field) iterator.next();
			if ( ReflectUtil.inheritsFrom( javaField.getType(), Field.class ) ){
				if ( Modifier.isPublic( javaField.getModifiers() ) && Modifier.isProtected( javaField.getModifiers() ) ){
					System.out.println( "->>Public " + javaField.getName() + " é um field." );
					Field field = (Field)javaField.get(this);
					this.add(field);
				} else {
					System.out.println( "->>" + javaField.getName() + " é um field." );
					Field field = (Field)ReflectUtil.getFieldValue( this, javaField.getName() );
					this.add(field);
				}	
			}
		}
	}*/
	
	public abstract void defineGrid() throws Exception;
	
    @SuppressWarnings("unchecked")
	public boolean findField(Field field){
    	return (Field)this.getObjectByServerId((ArrayList)this.getFields(), field.getObjectId() ) != null;
    }
	
	public Field getFieldByName( String fieldName ){
		for (Field field : this.getFields()) {
			if ( field.getName().equals( fieldName )){
				return field;
			}
		}					
		return null;
	}    
    
	public Field getFieldByServerObjectId( String objectId ){
		for (Field field : fields) {
			if ( field.getObjectId().equals( objectId ) ){
				return field;
			}
		}					
		return null;
	}	
		
	/**
	 * Add a field to this grid. To add a field to a grid, you must call
	 * explicitly add(), or must declare get's and set's to the variable.
	 */ 
    public boolean add(Field field) throws Exception{
    	if ( field == null ){
    		throw new Exception("Field cannot be null.");
    	}
    	
    	field.setHttpSession(this.getHttpSession());
    	
    	if ( ! findField(field) ){
    		field.setGrid(this);    		
    		this.fields.add(field);
    		return true;
    	}
    	return false;
    }		
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
		
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) throws Exception {
		/**
		 * O segundo parâmetro oldValue deve ser sempre null, porque a propriedade windowId só
		 * é setada uma vez, no construtor, e não é mais alterado. Não é possível passar a propriedade
		 * this..getProcessId() como o oldValue pois neste momento a proprieade window ainda é null. 
		 */
		addPropNameToPropsToSincronize("windowId", null, window.getWindowId());
		this.window = window;				
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}		
	
	@SuppressWarnings("unchecked")
	protected void addPropsToClientSincronizer() throws Exception {
		addPropToClient("windowId", this.getWindow().getWindowId());

		List<Field> fields = this.getFields();
		DataList.sort(fields, "order" );
		
		HashMap fieldsConfig;
		
		int  j = 0;			
		for (BaseAction action : getActions()) {			
			fieldsConfig = action.toClientSynchronizer();
			if ( ! fieldsConfig.isEmpty() ){
				clientProps.put( "ACTION" + j, fieldsConfig  );
				j++;
			}
		}
		
		if ( j > 0 ){
			clientProps.put( "actionCount", j );
		}
		
		System.out.println("GridName:" + this.getName());
		//for (Field field : fields) {
		int cont = 0; 
		for ( int i = 0 ; i < fields.size(); i++ ){
			Field field = fields.get(i);
			System.out.println("field.order=" + field.getOrder());
			System.out.println("field.name=" + field.getName());

			if ( field instanceof FieldBoolean ){
				fieldsConfig = ((FieldBoolean)field).toClientSynchronizer();
			} else if ( field instanceof FieldInteger ){
				fieldsConfig = ((FieldInteger)field).toClientSynchronizer();
			} else if ( field instanceof FieldLookup){
				fieldsConfig = ((FieldLookup)field).toClientSynchronizer();
			} else if ( field instanceof FieldMasterDetail){
				fieldsConfig = ((FieldMasterDetail)field).toClientSynchronizer();
			} else if ( field instanceof FieldNumber){
				fieldsConfig = ((FieldNumber)field).toClientSynchronizer();
			} else if ( field instanceof FieldSimpleDate){
				fieldsConfig = ((FieldSimpleDate)field).toClientSynchronizer();
			} else if ( field instanceof FieldString){
				fieldsConfig = ((FieldString)field).toClientSynchronizer();
			} else if ( field instanceof FieldMasterDetail){
				fieldsConfig = ((FieldMasterDetail)field).toClientSynchronizer();
			} else if ( field instanceof FieldTime){
				fieldsConfig = ((FieldTime)field).toClientSynchronizer();
			} else {
				fieldsConfig = field.toClientSynchronizer();
			}	
			if( ! fieldsConfig.isEmpty() ){
				clientProps.put( "FIELD" + cont, fieldsConfig );
				cont++;
			}	
		}
		if ( cont > 0 ){
			clientProps.put( "fieldCount", cont );
		}			
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void addObjecIdToClientSincronizer() {
		super.addObjecIdToClientSincronizer();
		if ( ! this.clientProps.isEmpty() ){
			this.clientProps.put("type", this.getType());
		}
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * This method does not make the grid in edition mode. 
	 */
	public void clearFields() throws Exception{
		for (Iterator iterator = this.getFields().iterator(); iterator.hasNext();) {
			Field field = (Field) iterator.next();
			System.out.println("Clearing field-> " + field.getName() );
			if ( field instanceof FieldMasterDetail ){
				
			} else {
				field.setValueWithoutEditing( null );
			}	
		}		
	}	
}
