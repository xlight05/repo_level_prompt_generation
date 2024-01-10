package br.com.dyad.infrastructure.widget.grid;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.ArrayUtils;

import br.com.dyad.client.widget.grid.GridTypes;
import br.com.dyad.commons.data.DataList;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.entity.MasterDetail;
import br.com.dyad.infrastructure.widget.Window;
import br.com.dyad.infrastructure.widget.field.Field;
import br.com.dyad.infrastructure.widget.field.FieldBoolean;
import br.com.dyad.infrastructure.widget.field.FieldInteger;
import br.com.dyad.infrastructure.widget.field.FieldLookup;
import br.com.dyad.infrastructure.widget.field.FieldMasterDetail;
import br.com.dyad.infrastructure.widget.field.FieldNumber;
import br.com.dyad.infrastructure.widget.field.FieldSimpleDate;
import br.com.dyad.infrastructure.widget.field.FieldString;

/**
 * @enterprise Dyad & Associados (http://www.dyad.com.br)
 * @author Helton Gonçalves (helton@dyad.com.br;heltongoncalves@gmail.com)
 */
public abstract class Layout extends DataContainer{	

	public Layout( Window window, String name ) throws Exception{
		super(window, name);
		this.initializeGrid();
	}
	
	public void initializeGrid(){
		this.setType(GridTypes.LAYOUT);
	}

	public Layout( Window window, String name, String bean ) throws Exception{
		super(window, name);
		this.initializeGrid();
	}

	public Layout( Window window, String name, DataList dataList ) throws Exception{
		super(window, name);
		this.initializeGrid();
		this.setDataList(dataList);
	}
	
	public Layout( Window window, String name, DataList dataList, String bean ) throws Exception{
		super(window, name);
		this.initializeGrid();
		this.setDataList(dataList);
	}		
			
	@Override
	public void defineGrid() throws Exception {
		if ( this.dataList == null ){
			throw new Exception("Its not possible call defineGrid with dataList null.");
		}
		if ( this.getBeanName() != null ){
			this.createFieldsBasedInBean();
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
		
		Class[] intClasses = {Integer.class, Long.class, BigInteger.class};
		Class[] doubleClasses = {Double.class, Float.class, BigDecimal.class};
		Class[] stringClasses = {String.class, Character.class};
		Class[] masterDetailClasses = {MasterDetail.class};
		
		//Class[] enumClasses = {Enum.class, Enumeration.class};										

		for (java.lang.reflect.Field f : fields) {
				
			Field gridField = null;		
			
			String name = f.getName(); 

			Class fieldClass = f.getType();

			if ( ReflectUtil.inheritsFrom(fieldClass, BaseEntity.class ) &&
					Field.containsAttributeValueForPropName( object, name, "beanName" )	){				

				if ( Field.containsAttributeValueForPropName( object, name, "beanName" ) ){
					String beanName = (String)Field.getAttributeValueForPropName(object, name, "beanName" );				
					gridField = new FieldLookup( this, name );
					((FieldLookup)gridField).setBeanName( beanName );
					
					if ( Field.containsAttributeValueForPropName( object, name, "lookupClassId" ) ){
						Long lookupClassId = (Long)Field.getAttributeValueForPropName(object, name, "lookupClassId" );				
						((FieldLookup)gridField).setLookupClassId( lookupClassId  );
					}

					if ( Field.containsAttributeValueForPropName( object, name, "lookupType" ) ){
						Integer lookupType = (Integer)Field.getAttributeValueForPropName(object, name, "lookupType" );				
						((FieldLookup)gridField).setLookupType( lookupType );
					}
				} else {
					throw new Exception("Bean name must be informed.");
				}
			} else if (ArrayUtils.indexOf(intClasses, fieldClass) != -1){
				gridField = new FieldInteger( this, name );

				if ( Field.containsAttributeValueForPropName( object, name, "min" ) ){
					Double min = (Double)Field.getAttributeValueForPropName(object, name, "min" );				
					((FieldInteger)gridField).setMin(min);
				}

				if ( Field.containsAttributeValueForPropName( object, name, "max" ) ){
					Double max = (Double)Field.getAttributeValueForPropName(object, name, "max" );				
					((FieldInteger)gridField).setMax(max);
				}	

				if ( Field.containsAttributeValueForPropName( object, name, "decimalPrecision" ) ){
					Integer decimalPrecision = (Integer)Field.getAttributeValueForPropName(object, name, "decimalPrecision" );
					((FieldInteger)gridField).setDecimalPrecision( decimalPrecision );
				}	

				if ( Field.containsAttributeValueForPropName( object, name, "negativeInRed" ) ){
					Boolean negativeInRed = (Boolean)Field.getAttributeValueForPropName(object, name, "negativeInRed" );
					((FieldInteger)gridField).setNegativeInRed(negativeInRed);
				}	
			} else if (ArrayUtils.indexOf(doubleClasses, fieldClass) != -1){
				gridField = new FieldNumber( this, name );

				if ( Field.containsAttributeValueForPropName( object, name, "min" ) ){
					Double min = (Double)Field.getAttributeValueForPropName(object, name, "min" );				
					((FieldNumber)gridField).setMin(min);
				}	

				if ( Field.containsAttributeValueForPropName( object, name, "max" ) ){
					Double max = (Double)Field.getAttributeValueForPropName(object, name, "max" );				
					((FieldNumber)gridField).setMax(max);
				}

				if ( Field.containsAttributeValueForPropName( object, name, "decimalPrecision" ) ){
					Integer decimalPrecision = (Integer)Field.getAttributeValueForPropName(object, name, "decimalPrecision" );
					((FieldNumber)gridField).setDecimalPrecision( decimalPrecision );
				}

				if ( Field.containsAttributeValueForPropName( object, name, "negativeInRed" ) ){
					Boolean negativeInRed = (Boolean)Field.getAttributeValueForPropName(object, name, "negativeInRed" );
					((FieldNumber)gridField).setNegativeInRed(negativeInRed);
				}	
			} else if (ArrayUtils.indexOf(stringClasses, fieldClass) != -1){
				gridField = new FieldString( this, name );

				if ( Field.containsAttributeValueForPropName( object, name, "caseType" ) ){
					String caseType = (String)Field.getAttributeValueForPropName(object, name, "caseType" );
					((FieldString)gridField).setCaseType(caseType);
				}	

				if ( Field.containsAttributeValueForPropName( object, name, "password" ) ){
					Boolean password = (Boolean)Field.getAttributeValueForPropName(object, name, "password" );
					((FieldString)gridField).setPassword(password);				
				}	
				
				if (f.getType().equals(BigDecimal.class)) {
					((FieldString)gridField).setOnlyNumbers(true);
					((FieldString)gridField).setAllowDecimals(true);
				}
				
				if (f.getType().equals(BigInteger.class)) {
					((FieldString)gridField).setOnlyNumbers(true);
					((FieldString)gridField).setAllowDecimals(false);
				}
			} else if (ArrayUtils.indexOf(masterDetailClasses, fieldClass) != -1){
				Boolean isVisible = true;
				if ( Field.containsAttributeValueForPropName( object, name, "visible" ) ){
					isVisible = (Boolean)Field.getAttributeValueForPropName(object, name, "visible" );
				}	

				if ( isVisible ){
					gridField = new FieldMasterDetail( this, name );
					FieldMasterDetail fieldMasterDetail = (FieldMasterDetail)gridField;

					if ( Field.containsAttributeValueForPropName( object, name, "beanName" ) ){
						fieldMasterDetail.setBeanName( (String)Field.getAttributeValueForPropName(object, name, "beanName" ) );
					}	

					if ( Field.containsAttributeValueForPropName( object, name, "masterFieldNames" ) ){
						fieldMasterDetail.setMasterFieldNames( (String)Field.getAttributeValueForPropName(object, name, "masterFieldNames" ) );
					}	

					if ( Field.containsAttributeValueForPropName( object, name, "detailFieldNames" ) ){
						fieldMasterDetail.setDetailFieldNames( (String)Field.getAttributeValueForPropName(object, name, "detailFieldNames" ) );
					}	

					if ( Field.containsAttributeValueForPropName( object, name, "detailIndexFieldNames" ) ){
						fieldMasterDetail.setDetailIndexFieldNames( (String)Field.getAttributeValueForPropName(object, name, "detailIndexFieldNames" ) );
					}	

					if ( Field.containsAttributeValueForPropName( object, name, "masterDeleteAction" ) ){
						fieldMasterDetail.setMasterDeleteAction( (String)Field.getAttributeValueForPropName(object, name, "masterDeleteAction" ) );
					}	

					if ( Field.containsAttributeValueForPropName( object, name, "masterDetailMaxRecordCount" ) ){
						fieldMasterDetail.setMasterDetailMaxRecordCount( (Integer)Field.getAttributeValueForPropName(object, name, "masterDeleteAction" ) );
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
				} else if (temporal.value().equals(TemporalType.TIMESTAMP)){
					gridField = new FieldSimpleDate( this, name );
				}

				if ( Field.containsAttributeValueForPropName( object, name, "dateFormat" ) ){
					String dateFormat = (String)Field.getAttributeValueForPropName(object, name, "dateFormat" );
					((FieldSimpleDate)gridField).setDateFormat(dateFormat);
				}	
			} else if (ReflectUtil.inheritsFrom(fieldClass, Boolean.class)){
				gridField = new FieldBoolean( this, name );
			}

			//Bypass if gridField is null.
			if ( gridField != null ){
				System.out.println("Criou o field:" + name);

				if ( Field.containsAttributeValueForPropName( object, name, "required" ) ){
					Boolean required = (Boolean)Field.getAttributeValueForPropName(object, name, "required" );
					gridField.setRequired( required );
					System.out.println("required:" + required);
				}

				if ( Field.containsAttributeValueForPropName( object, name, "visible" ) ){
					Boolean visible = (Boolean)Field.getAttributeValueForPropName(object, name, "visible" );
					gridField.setVisible(visible);
				}	

				if ( Field.containsAttributeValueForPropName( object, name, "listable" ) ){
					Boolean listable = (Boolean)Field.getAttributeValueForPropName(object, name, "listable" );
					gridField.setListable(listable);
				}

				if ( Field.containsAttributeValueForPropName( object, name, "column" ) ){
					Integer column = (Integer)Field.getAttributeValueForPropName(object, name, "column" );
					gridField.setColumn(column);
				}	

				if ( Field.containsAttributeValueForPropName( object, name, "group" ) ){
					String group = (String)Field.getAttributeValueForPropName(object, name, "group" );
					gridField.setGroup(group);
				}	

				if ( Field.containsAttributeValueForPropName( object, name, "height" ) ){
					Integer height = (Integer)Field.getAttributeValueForPropName(object, name, "height" );
					gridField.setHeight(height);
				}	

				if ( Field.containsAttributeValueForPropName( object, name, "help" ) ){
					String help = (String)Field.getAttributeValueForPropName(object, name, "help" );
					gridField.setHelp(help);
				}	

				if ( Field.containsAttributeValueForPropName( object, name, "label" ) ){
					String label = (String)Field.getAttributeValueForPropName(object, name, "label" );
					gridField.setLabel(label);
				}	

				if ( Field.containsAttributeValueForPropName( object, name, "order" ) ){
					Integer order = (Integer)Field.getAttributeValueForPropName(object, name, "order" );
					gridField.setOrder(order);
				}

				if ( Field.containsAttributeValueForPropName( object, name, "readOnly" ) ){
					Boolean readOnly = (Boolean)Field.getAttributeValueForPropName(object, name, "readOnly" );
					gridField.setReadOnly(readOnly);
				}	

				if ( Field.containsAttributeValueForPropName( object, name, "width" ) ){
					Integer width = (Integer)Field.getAttributeValueForPropName(object, name, "width" );
					gridField.setWidth(width);
				}	

				//gridField.setTableViewable(meta.tableViewable());
				//gridField.setTableViewWidth(meta.tableViewWidth());
				//gridField.setDisplayFieldName(meta.displayFieldName());
				//gridField.setBeanName(meta.beanName());
				/*if (gridField instanceof FieldMasterDetail ){
					//this.add((FieldMasterDetail)gridField);
					//this.addFieldMasterDetail( (FieldMasterDetail)gridField );
				} else {
					this.add(gridField);
				}*/
			}	
		}	
	}	

}