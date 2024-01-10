package br.com.dyad.client.widget.field;

import br.com.dyad.client.gxt.field.customized.NumberFieldCustomized;

import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.google.gwt.i18n.client.NumberFormat;

/**
 * @enterprise Dyad & Associados (http://www.dyad.com.br)
 * @author Helton Goncalves (helton@dyad.com.br;heltongoncalves@gmail.com)
 */
public class ClientNumberField  extends ClientField {
	private Double min;
	private Double max;
	private Integer decimalPrecision;
	private Boolean negativeInRed;                   

	public ClientNumberField(){
		this.setFormViewField(new NumberFieldCustomized());
		this.setTableViewField(new NumberFieldCustomized());	

		((NumberFieldCustomized) this.getFormViewField()).setClientField(this);
		((NumberFieldCustomized) this.getTableViewField()).setClientField(this);

		this.setWidth(100);
	}
	
	public ClientNumberField(String name){
		this();
		this.setName(name);
	}

	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
		this.min = min;
		((NumberFieldCustomized) this.getFormViewField()).setMinValue(min);
		((NumberFieldCustomized) this.getTableViewField()).setMinValue(min);
	}

	public Double getMax() {
		return max;
	}

	public void setMax(Double max) {
		this.max = max;
		((NumberFieldCustomized) this.getFormViewField()).setMaxValue(max);
		((NumberFieldCustomized) this.getTableViewField()).setMaxValue(max);
	}

	public Integer getDecimalPrecision() {
		return decimalPrecision;
	}

	public void setDecimalPrecision(Integer decimalPrecision) {
		this.decimalPrecision = decimalPrecision;
		String moneyFormat = "";
		if ( decimalPrecision > 0 ){
			moneyFormat = "##0.";
			for (int i = 0; i < decimalPrecision; i++) {
				moneyFormat += "0";
			}
		} else {
			this.decimalPrecision = 0;
			moneyFormat = "##0";
		}
		((NumberFieldCustomized) this.getFormViewField()).setFormat(NumberFormat.getFormat(moneyFormat));
		((NumberFieldCustomized) this.getTableViewField()).setFormat(NumberFormat.getFormat(moneyFormat));
	}

	public Boolean getNegativeInRed() {
		return negativeInRed;
	}

	public void setNegativeInRed(Boolean negativeInRed) {
		this.negativeInRed = negativeInRed;
	}	

	public void setRequired(Boolean required){
		super.setRequired(required);
		if ( required != null ){
			if ( required ){	
				((NumberFieldCustomized) this.getFormViewField()).setAllowBlank(false);
				((NumberFieldCustomized) this.getTableViewField()).setAllowBlank(false);
			} else {
				((NumberFieldCustomized) this.getFormViewField()).setAllowBlank(true);
				((NumberFieldCustomized) this.getTableViewField()).setAllowBlank(true);
			}
		}					
	}

	public void setValue(Number value) {
		((NumberFieldCustomized) this.getFormViewField()).setValue(value);
		((NumberFieldCustomized) this.getTableViewField()).setValue(value);
		this.setTableViewValue(value);
	}		

	public void setOldRawValue(String value) {
		((NumberFieldCustomized) this.getFormViewField()).setOldRawValue( value );
		((NumberFieldCustomized) this.getTableViewField()).setOldRawValue( value );
	}		
	
	public ColumnConfig getColumnConfig(){
		return ((NumberFieldCustomized) this.getTableViewField()).getColumnConfig();
	}
}