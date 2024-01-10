package br.com.dyad.client.grid;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GridColumnDef implements IsSerializable, Comparable<GridColumnDef> {

	private FieldType fieldType;
	private String classReference;
	private String fieldName;
	private int order;	
	private String label;		
	private String group;	
	private int column;	
	private int width;	
	private int height;	
	private int tableViewWidth;	
	private String help;	
	private String caseType;	
	private boolean tableViewable;	
	private boolean visible;
	private boolean listable;	
	private boolean readOnly;
	private boolean nullable; 
	private String displayFieldName;
	private String beanName;

	public boolean getNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public String getClassReference() {
		return classReference;
	}

	public void setClassReference(String classReference) {
		this.classReference = classReference;
	}

	public FieldType getFieldType() {
		return fieldType;
	}

	public void setFieldType(FieldType fieldType) {
		this.fieldType = fieldType;
	}
	
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getTableViewWidth() {
		return tableViewWidth;
	}

	public void setTableViewWidth(int tableViewWidth) {
		this.tableViewWidth = tableViewWidth;
	}

	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public boolean isTableViewable() {
		return tableViewable;
	}

	public void setTableViewable(boolean tableViewable) {
		this.tableViewable = tableViewable;
	}

	public boolean getVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public String getDisplayFieldName() {
		return displayFieldName;
	}

	public void setDisplayFieldName(String displayFieldName) {
		this.displayFieldName = displayFieldName;
	}
	
	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	
	public int compare(GridColumnDef c1, GridColumnDef c2) {
		Integer i1 = c1 != null ? c1.getOrder() : Integer.MIN_VALUE;
		Integer i2 = c2 != null ? c2.getOrder() : Integer.MIN_VALUE;
		
		return  i1.compareTo(i2);
	}

	public int compareTo(GridColumnDef c2) {
		Integer i1 = this.getOrder();
		Integer i2 = c2 != null ? c2.getOrder() : Integer.MIN_VALUE;
		
		return  i1.compareTo(i2);
	}

	public boolean isListable() {
		return listable;
	}

	public void setListable(boolean listable) {
		this.listable = listable;
	}		

}