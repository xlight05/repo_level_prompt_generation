package br.com.dyad.infrastructure.widget.field;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;

import org.apache.commons.collections.Predicate;
import org.hibernate.tool.hbm2x.StringUtils;

import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.annotations.SendToClient;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.widget.BaseServerWidget;
import br.com.dyad.infrastructure.widget.grid.DataGrid;
import br.com.dyad.infrastructure.widget.grid.Grid;

/**
 * @enterprise Dyad & Associados (http://www.dyad.com.br)
 * @author Helton Gonçalves (helton@dyad.com.br;heltongoncalves@gmail.com)
 */
public abstract class Field extends BaseServerWidget{	
	private HashMap<String, Object> dataMap = new HashMap<String, Object>();
	@SendToClient
	private String name;
	@SendToClient
	private Integer order;
	@SendToClient
	private Integer column;
	@SendToClient
	private Integer colSpan;
	private Integer type;
	@SendToClient
	private String label;
	@SendToClient
	private String group;
	@SendToClient
	private Boolean visible;
	@SendToClient
	private Boolean readOnly;
	@SendToClient
	private Boolean readOnlyFromClass;
	@SendToClient
	private Boolean required;
	@SendToClient
	private Boolean saveLastValueAsDefault;
	@SendToClient
	private Boolean fieldValueAsDefault;
	@SendToClient
	private Long fieldIdValueAsDefault;
	@SendToClient
	protected Object value;
	//options
	//defaultValue
	@SendToClient
	private Integer width;
	@SendToClient
	private Integer height;
	@SendToClient
	private String alignment;
	@SendToClient
	private Boolean canCustomizeValue;
	@SendToClient
	private Boolean tableViewVisible;
	@SendToClient
	private Integer tableViewWidth;
	@SendToClient
	private Boolean listable;	
	private Grid grid;
	@SendToClient
	private Boolean isCalculated = false;
	
	protected Predicate predicate; 
	
	//private links                           
	//private linkParametersValuesFieldNames	
		
	protected Field(){	
	}
		
	public Field(Grid grid) throws Exception {
		this.initializeField();
		grid.add(this);
	}

	public Field(Grid grid, String name ) throws Exception{
		this.setName(name);
		this.initializeField();
		grid.add(this);
	}
	
	protected void initializeField() {
		this.setOrder(0);
		this.setColumn(0);
		this.setVisible(true);
		this.setReadOnly(false);
		this.setWidth(100);
		this.setHeight(1);
		this.setRequired(false);
		this.setListable(true);
	}

	public Boolean getIsCalculated() {
		return isCalculated;
	}

	public void setIsCalculated(Boolean isCalculated) {
		this.isCalculated = isCalculated;
	}

	public void setData(String key, Object value) {
		this.dataMap.put(key, value);
	}
	
	public Object getData(String key) {
		return this.dataMap.get(key);
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
		
		if ( this.getTableViewWidth() == null && this.propsToSynchronize.contains("width") && this.width != null ){
			this.setTableViewWidth(this.width);
		}	
		
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.setLabelByName( name );
		this.name = name;
	}

	protected void setLabelByName( String name ){
		this.setLabel( Field.getLabelByName(name) );
	}

	public static String getLabelByName( String name ){
		if ( name != null && ! name.trim().equals("") ){
			String capitalizedName = StringUtils.capitalise(name);
			if ( capitalizedName.length() > 1 ){
				String label = "";
				for (int i = 0; i < capitalizedName.length(); i++) {
					if ( i > 0 ){
						if ( capitalizedName.charAt(i) >= 65 && capitalizedName.charAt(i) <= 95 ){
							label = label + " " + capitalizedName.charAt( i );		
						} else {
							label = label + capitalizedName.charAt( i );		
						}
					} else {
						label = "" + capitalizedName.charAt( i );
					}					
				}
				return label;
			}
			return capitalizedName;
		}
		return null;
	}
	
	public void setRequired(Boolean required) {
		this.required = required;
	}

	public Boolean getSaveLastValueAsDefault() {
		return saveLastValueAsDefault;
	}

	public void setSaveLastValueAsDefault(Boolean saveLastValueAsDefault) {
		this.saveLastValueAsDefault = saveLastValueAsDefault;
	}

	public Boolean getFieldValueAsDefault() {
		return fieldValueAsDefault;
	}

	public void setFieldValueAsDefault(Boolean fieldValueAsDefault) {
		this.fieldValueAsDefault = fieldValueAsDefault;
	}

	public Long getFieldIdValueAsDefault() {
		return fieldIdValueAsDefault;
	}

	public void setFieldIdValueAsDefault(Long fieldIdValueAsDefault) {
		this.fieldIdValueAsDefault = fieldIdValueAsDefault;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Integer getColumn() {
		return column;
	}

	public void setColumn(Integer column) {
		this.column = column;
	}
	
	public Integer getColSpan() {
		return colSpan;
	}

	public void setColSpan(Integer colSpan) {
		this.colSpan = colSpan;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Boolean getRequired() {
		return required;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}


	public Boolean getVisible() {
		return visible;
	}

	/**
	 * When you set the visible prop, if its value is set, it fires the setTableViewVisible() method.
	 * This is reasonable because is rarely when you hide or show one formview field and you don't want to
	 * do this in tableView.  
	 */
	public void setVisible(Boolean visible) {
		this.visible = visible;
		
		if ( this.getTableViewVisible() == null && this.propsToSynchronize.contains("visible") && this.visible != null ){
			this.setTableViewVisible(this.visible);
		}	
	}

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public Boolean getReadOnlyFromClass() {
		return readOnlyFromClass;
	}

	public void setReadOnlyFromClass(Boolean readOnlyFromClass) {
		this.readOnlyFromClass = readOnlyFromClass;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getAlignment() {
		return alignment;
	}

	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}
	
	public Boolean getTableViewVisible() {
		return tableViewVisible;
	}

	public void setTableViewVisible(Boolean tableViewVisible) {
		this.tableViewVisible = tableViewVisible;
	}

	public Integer getTableViewWidth() {
		return tableViewWidth;
	}

	public void setTableViewWidth(Integer tableViewWidth) {
		this.tableViewWidth = tableViewWidth;
		
	}

	public Object getValue() {
		return value;
	}

	public void setValueWithoutEditing(Object value) throws Exception {
		if (value != null) {					
			if (value.getClass().equals(BigInteger.class) || value.getClass().equals(BigDecimal.class)) {
				value = value.toString();
			}
		}		
		
		this.value = value;
	}

	public Boolean getCanCustomizeValue() {
		return canCustomizeValue;
	}

	public void setCanCustomizeValue(Boolean canCustomizeValue) {
		this.canCustomizeValue = canCustomizeValue;
	}

	public void setValue(Object value) throws Exception {
		//Agora tem que verificar pela annotation, porque o aspecto só é executado quando o valor 
		//da variável é alterado
		
		java.lang.reflect.Field field = ReflectUtil.getDeclaredField(this.getClass(), "value");
		SendToClient annotation = field.getAnnotation(SendToClient.class);
		
		//if ( this.propsToSynchronize.contains("value") ){
		if ( annotation != null ){
			if ( this.grid instanceof DataGrid ){
				DataGrid dataGrid = (DataGrid)grid;
				if ( dataGrid.getEditionMode() == null ){
					dataGrid.setEditionMode(DataGrid.EDITION_MODE_EDIT);
				}	
				java.lang.reflect.Field entityField = ReflectUtil.getDeclaredField(dataGrid.getCurrentEntity().getClass(), this.getName()); 
					
				if (entityField.getType().equals(BigInteger.class)) {
					value = new BigInteger(value == null ? "" : value + "");
				} else if (entityField.getType().equals(BigDecimal.class)) {
					value = new BigDecimal(value == null ? "" : value + "");
				}
				
				ReflectUtil.setFieldValue(dataGrid.getCurrentEntity(), this.getName(), value);
			}
		}	
	
		this.value = value;
	}

	
	public Boolean getListable() {
		return listable;
	}

	public void setListable(Boolean listable) {
		this.listable = listable;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void addObjecIdToClientSincronizer() {
		super.addObjecIdToClientSincronizer();
		if ( ! clientProps.isEmpty() ){
			clientProps.put("type", this.getType());
		}
	}

	@SuppressWarnings("unchecked")
	public HashMap getFieldInformation() throws Exception{
		HashMap fieldInformation = new HashMap();
		
		fieldInformation.put("Help", this.getHelp() );

		if ( this.getGrid() != null && this.getGrid() instanceof DataGrid ){
			String beanName = ((DataGrid)this.getGrid()).getBeanName();
			fieldInformation.put( "Entity", beanName + " (" + BaseEntity.getClassIdentifierByClassName( beanName ) + ")");
		}	
		
		fieldInformation.put("Name", this.getName());
		fieldInformation.put("Grid", this.getGrid().getTitle());
		fieldInformation.put("Group", this.getGroup() );
		fieldInformation.put("Order", this.getOrder() );
		
		return fieldInformation;
	}
	
	public void onCalcField() {}
	
	public void onBeforeChange(){};
	public void onAfterChange(){};
	
	@Override
	protected void addPropsToClientSincronizer() throws Exception {		
	}

	public Predicate getPredicate( String fieldName, Object searchToken ) {
		return predicate;
	}

	public void setPredicate(Predicate predicate) {
		this.predicate = predicate;
	}

	public static Boolean containsAttributeValueForPropName( Object object, String propName, String attribute ){
		try {
			Boolean result = (Boolean)ReflectUtil.getMethodReturn(object, "containsAttributeValueForPropName" , new Class[]{String.class, String.class}, new Object[]{ propName, attribute });
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	

	public static Object getAttributeValueForPropName( Object object, String propName, String attribute ){
		try {
			Object result = ReflectUtil.getMethodReturn(object, "getAttributeValueForPropName" , new Class[]{String.class, String.class}, new Object[]{ propName, attribute });
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	

}

