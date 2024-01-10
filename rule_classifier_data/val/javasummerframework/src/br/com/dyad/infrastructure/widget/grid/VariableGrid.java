package br.com.dyad.infrastructure.widget.grid;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.dyad.client.GenericService;
import br.com.dyad.client.widget.field.FieldTypes;
import br.com.dyad.client.widget.grid.GridTypes;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.entity.UserFieldValue;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;
import br.com.dyad.infrastructure.widget.Window;
import br.com.dyad.infrastructure.widget.field.Field;
import br.com.dyad.infrastructure.widget.field.FieldBoolean;
import br.com.dyad.infrastructure.widget.field.FieldInteger;
import br.com.dyad.infrastructure.widget.field.FieldLookup;
import br.com.dyad.infrastructure.widget.field.FieldNumber;
import br.com.dyad.infrastructure.widget.field.FieldSimpleDate;
import br.com.dyad.infrastructure.widget.field.FieldTime;
import br.com.dyad.infrastructure.widget.field.FieldUpload;

/**
 * @enterprise Dyad & Associados (http://www.dyad.com.br)
 * @author Helton Gonçalves (helton@dyad.com.br;heltongoncalves@gmail.com)
 */
public abstract class VariableGrid extends Grid{	
	public VariableGrid( Window window, String name ) throws Exception{
		super(window, name);
		this.initializeGrid();
	}
	
	public void initializeGrid(){
		this.setType(GridTypes.VARIABLE_GRID);
	}
	
	@Override
	protected void setLastFieldValues() throws Exception {
		try {
			Window window = this.getWindow();
			String gridName = window.getWindowId() + "_" + this.getName();
			Long userKey = (Long)getHttpSession().getAttribute(GenericService.USER_KEY);
			List<UserFieldValue> list = new ArrayList<UserFieldValue>();			
			Session session = PersistenceUtil.getSession((String)getHttpSession().getAttribute(GenericService.GET_DATABASE_FILE));			
			String strQuery = "from UserFieldValue where classId = '" + 
			BaseEntity.getClassIdentifier(UserFieldValue.class) + "'" +
			" and user = " + userKey + 
			" and completeFieldName = '" + gridName + "' ";  
			Query query = session.createQuery(strQuery);
			query.setMaxResults(100);

			for(Object object : query.list()){
				UserFieldValue userFieldValue = (UserFieldValue) object;
				HashMap map = PersistenceUtil.convertByteArrayToHashMap(userFieldValue.getFieldValues());
				Set keys = map.keySet();

				for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
					String keyName = (String) iterator.next();
					if ( ! keyName.equals(GenericService.USER_KEY) && ! keyName.equals("completeGridName") ){
						Field fld = this.getFieldByName(keyName);
						if ( fld != null && fld.getSaveLastValueAsDefault() != null && fld.getSaveLastValueAsDefault() ){
							fld.setFieldValueAsDefault(true);
							if ( fld.getType().equals(FieldTypes.FIELD_TYPE_LOOKUP) ){
								FieldLookup lookup = (FieldLookup)fld;
								HashMap values = (HashMap)map.get(keyName);							
								lookup.setValue(values.get("fieldIdValue"));
							} else if ( fld.getType().equals(FieldTypes.FIELD_TYPE_BOOLEAN) ){
								FieldBoolean fldBoolean = (FieldBoolean) fld;
								fldBoolean.setValue((Boolean) map.get(keyName));
							} else if ( fld.getType().equals(FieldTypes.FIELD_TYPE_INTEGER) ){
								FieldInteger intField = (FieldInteger) fld;
								intField.setValue( (Long)map.get(keyName) );
							} else if ( fld.getType().equals(FieldTypes.FIELD_TYPE_NUMBER) ){
								FieldNumber numField = (FieldNumber) fld;
								numField.setValue((Double) map.get(keyName));
							} else if ( fld.getType().equals(FieldTypes.FIELD_TYPE_SIMPLEDATE) ){
								FieldSimpleDate date = (FieldSimpleDate) fld;
								date.setValue((Date) map.get(keyName));
							} else if ( fld.getType().equals(FieldTypes.FIELD_TYPE_TIME) ){
								FieldTime date = (FieldTime) fld;
								date.setValue((Date) map.get(keyName));
							} else if ( fld.getType().equals(FieldTypes.FIELD_TYPE_UPLOAD) ){
								FieldUpload fldUpload = (FieldUpload) fld;
								String strValue = (String)map.get(keyName);							
								fldUpload.setValue(strValue == null ? null : Integer.parseInt(strValue));
							} else {
								fld.setValue((String)userFieldValue.getFieldValue());
							}
						}
					}
				}			
			}
		} catch (Exception e) {
			//TODO: Faltando mover a geracao do esquema update para uma outra tabela. Helton
			e.printStackTrace();
		}	
	}
}