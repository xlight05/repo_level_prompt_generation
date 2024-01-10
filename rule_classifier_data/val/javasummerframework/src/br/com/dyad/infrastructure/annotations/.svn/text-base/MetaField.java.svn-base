package br.com.dyad.infrastructure.annotations;

import java.util.HashMap;

@SuppressWarnings("unchecked")
public class MetaField {
	private HashMap fieldProps = new HashMap();
	
	//this props are in lower case because they are used by the 
	//coder to auto complete the props name
	public static final String label = "label";		
	public static final String group = "group";	
	public static final String help = "help";	
	public static final String caseType = "caseType";
	
	public static final String displayFieldName = "displayFieldName";
	public static final String openKeyEnabled = "openKeyEnabled";
	public static final String beanName = "beanName";
	public static final String findExpress = "findExpress";

	public static final String min = "min";
	public static final String max = "max";				
	public static final String order = "order";	
	public static final String column = "column";
	public static final String colSpan = "colSpan";
	public static final String width = "width";	
	public static final String height = "height";
	/**
	 * @see br.com.dyad.infrastructure.widget.field.Field.setVisible()
	 * */
	public static final String visible = "visible";
	public static final String required = "required";
	public static final String listable = "listable";
	public static final String password = "password";
	public static final String readOnly = "readOnly";
	public static final String options = "options";
	public static final String canCustomizeValue = "canCustomizeValue";
	public static final String decimalPrecision = "decimalPrecision";
	public static final String negativeInRed = "negativeInRed";   
	public static final String dateFormat = "dateFormat";
	//props to details
	public static final String masterFieldNames  = "masterFieldNames";               
	public static final String detailFieldNames  = "detailFieldNames";              
	public static final String detailIndexFieldNames  = "detailIndexFieldNames";          
	//public static final String detailDescendingIndexFieldNames = "detailDescendingIndexFieldNames"; 
	public static final String masterDetailMaxRecordCount = "masterDetailMaxRecordCount";
	public static final String masterDeleteAction = "masterDeleteAction";	
	public static final String lookupClassId = "lookupClassId";
	public static final String lookupType = "lookupType";
	public static final String upload = "upload";
	public static final String picture = "picture";

	/**
	 * @see br.com.dyad.infrastructure.widget.field.Field.setTableViewVisible()
	 * */
	public static final String tableViewVisible = "tableViewVisible";
	public static final String tableViewWidth = "tableViewWidth";	
	public static final String htmlEditor = "htmlEditor";  
	
	public MetaField(){}
	
	public void setProps(String propName, Object...objects) {
		HashMap attributesAndValues;
		if ( this.fieldProps.get(propName) == null ){
			attributesAndValues = new HashMap();
		} else {
			attributesAndValues = (HashMap)this.fieldProps.get(propName);
		}
		for (int i = 0; i < objects.length; i++) {
			String attribute = (String)objects[i];
			i++;
			Object value = objects[i];			
			attributesAndValues.put(attribute, value);
		}
		this.fieldProps.put(propName, attributesAndValues);
	}
	
	public Object getAttributeValueForPropName( String propName, String attribute ){
		HashMap attributesAndValues = (HashMap)this.fieldProps.get( propName );
		if ( attributesAndValues != null ){
			return attributesAndValues.get(attribute);
		}
		return null;
	}

	public Boolean containsAttributeValueForPropName( String propName, String attribute ){
		HashMap attributesAndValues = (HashMap)this.fieldProps.get( propName );
		if ( attributesAndValues != null ){
			return attributesAndValues.keySet().contains(attribute);
		}
		return false;
	}
}
