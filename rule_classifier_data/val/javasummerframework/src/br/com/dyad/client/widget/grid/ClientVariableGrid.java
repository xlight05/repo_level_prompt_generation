package br.com.dyad.client.widget.grid;

import java.util.HashMap;

import br.com.dyad.client.DyadInfrastructure;
import br.com.dyad.client.GenericService;
import br.com.dyad.client.gxt.field.customized.CheckBoxCustomized;
import br.com.dyad.client.gxt.field.customized.CheckBoxGroupCustomized;
import br.com.dyad.client.gxt.field.customized.IntegerFieldCustomized;
import br.com.dyad.client.gxt.field.customized.LookupFieldCutomized;
import br.com.dyad.client.gxt.field.customized.ServerValue;
import br.com.dyad.client.widget.ClientProcess;
import br.com.dyad.client.widget.field.ClientField;

import com.extjs.gxt.ui.client.widget.form.Field;


/**
 * @enterprise Dyad & Associados (http://www.dyad.com.br)
 * @author Helton Gonçalves (helton@dyad.com.br;heltongoncalves@gmail.com)
 */
public class ClientVariableGrid extends ClientGrid {
	
	public ClientVariableGrid( ClientProcess process, String name ){
		super( process, name );
		setFieldValuesHeader();
	}
	
	public ClientVariableGrid( ClientProcess process, String name, Integer columnCount ){
		super( process, name, columnCount );
		setFieldValuesHeader();
	}
	
	@SuppressWarnings("unchecked")
	public ClientVariableGrid( ClientProcess process, String name, Integer columnCount, HashMap colSpanConfig ){
		super( process, name, columnCount, colSpanConfig );
		setFieldValuesHeader();
	}

	public HashMap getFieldValuesToSave() {
		HashMap fieldValuesToSave = get("fieldValuesToSave");
		
		if (fieldValuesToSave == null) {			
			fieldValuesToSave = new HashMap();
			setFieldValuesToSave(fieldValuesToSave);
		}
		
		return get("fieldValuesToSave");
	}

	public void setFieldValuesToSave(HashMap fieldValuesToSave) {
		set("fieldValuesToSave", fieldValuesToSave);
	}
	
	/*@SuppressWarnings("unchecked")
	public void addFieldValueToSave( Field field ){
		Boolean slv = (Boolean)get("saveLastValueAsDefault");
		if ( slv != null && slv ){
			if ( getFieldValuesToSave().containsKey(field.getName()) ){
				removeFieldValueToSave(field);			
			} 
			if ( field instanceof LookupFieldCutomized ){
				if ( field.getValue() != null ){
					HashMap values = new HashMap();
					values.put("fieldValue", field.getValue());
					values.put("fieldIdValue", ((LookupFieldCutomized) field).getIdValue());
					getFieldValuesToSave().put(field.getName(), values);
				}
			} else if ( field instanceof IntegerFieldCustomized ){
				getFieldValuesToSave().put(field.getName(), ((IntegerFieldCustomized)field).getValue());
			} else if ( field instanceof ServerValue ){
				getFieldValuesToSave().put(field.getName(), ((ServerValue)field).getSerializableValue());
			} else if ( field instanceof CheckBoxCustomized){
				getFieldValuesToSave().put(((CheckBoxCustomized) field).getCheckBoxGroup().getName(), field.getValue());
			} else if ( field instanceof CheckBoxGroupCustomized ){
				getFieldValuesToSave().put(((CheckBoxGroupCustomized) field).getName(), field.getValue());
			} else {
				getFieldValuesToSave().put(field.getName(), field.getValue());
			}
		}
	}*/
	
	@SuppressWarnings("unchecked")
	public void addFieldValueToSave( ClientField field ){
		Boolean slv = field.getSaveLastValueAsDefault();
		if ( slv != null && slv ){
			if ( getFieldValuesToSave().containsKey(field.getName()) ){
				removeFieldValueToSave(field.getFormViewField());			
			} 
			if ( field.getFormViewField() instanceof LookupFieldCutomized ){
				if ( field.getFormViewField().getValue() != null ){
					HashMap values = new HashMap();
					values.put("fieldValue", field.getFormViewField().getValue());
					values.put("fieldIdValue", ((LookupFieldCutomized) field.getFormViewField()).getIdValue());
					getFieldValuesToSave().put(field.getName(), values);
				}
			} else if ( field.getFormViewField() instanceof IntegerFieldCustomized ){
				getFieldValuesToSave().put(field.getName(), ((IntegerFieldCustomized)field.getFormViewField()).getValue());
			} else if ( field instanceof ServerValue ){
				getFieldValuesToSave().put(field.getName(), ((ServerValue)field).getSerializableValue());
			} else if ( field.getFormViewField() instanceof CheckBoxCustomized){
				getFieldValuesToSave().put(((CheckBoxCustomized) field.getFormViewField()).getCheckBoxGroup().getName(), field.getFormViewField().getValue());
			} else if ( field.getFormViewField() instanceof CheckBoxGroupCustomized ){
				getFieldValuesToSave().put(((CheckBoxGroupCustomized) field.getFormViewField()).getName(), field.getFormViewField().getValue());
			} else {
				getFieldValuesToSave().put(field.getName(), field.getFormViewField().getValue());
			}
		}
	}
	
	public void removeFieldValueToSave( Field field ){
		getFieldValuesToSave().remove(field.getName());
	}
	
	@SuppressWarnings("unchecked")
	private void setFieldValuesHeader(){
		getFieldValuesToSave().put(GenericService.USER_KEY, DyadInfrastructure.userKey);
		getFieldValuesToSave().put("completeGridName", this.getProcessId() + "_" + this.getName() );
	}

	@Override
	protected void updateServerInfo(ClientProcess clientProcess, HashMap config) throws Exception {
		Integer size = (Integer)config.get("fieldCount");
		size = size == null ? 0 : size;
		
		for (int i = 0; i < size; i++) {
			Object ob;			
			ob = config.get("FIELD" + i);
			if ( ob != null ){
				HashMap configField = (HashMap)ob;
				if ( ! configField.isEmpty() ){
					ClientField.synchronizeFieldWithServerInformation( clientProcess, this, configField, config );
				}	
			}		
		}		
	}
}