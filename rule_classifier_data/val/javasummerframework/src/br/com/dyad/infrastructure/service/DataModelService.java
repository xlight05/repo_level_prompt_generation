package br.com.dyad.infrastructure.service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import br.com.dyad.client.GenericService;
import br.com.dyad.client.grid.FieldType;
import br.com.dyad.client.grid.GridColumnDef;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;
import br.com.dyad.infrastructure.reflect.ObjectConverter;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;

public class DataModelService extends DyadService {
		
	@SuppressWarnings("unchecked")
	private Class clazz;
	private String database;
	
	@SuppressWarnings("unchecked")
	@Override
	public HashMap getServiceValue(HashMap params) throws Exception {
		//try {
			this.database = (String)params.get(GenericService.GET_DATABASE_FILE);
			HashMap ret = new HashMap();						
			clazz = Class.forName((String)params.get("bean"));
			Object header = params.get("header");
			
			if (header != null && header.equals("S")){				
				ret.put("columns", getColumnsDef());
			}else{				
				Object config = params.get("paginator");
				if (config != null){					
					ret.put("paginator", getBeans((PagingLoadConfig)config));
				}else{
					ret.put("bean", getBeans());
				}
			}			
			
			return ret;
		//} catch (Exception e) {
		//	throw ClientServerException.createException(e);
		//}
	}
	
	@SuppressWarnings("unchecked")
	private BasePagingLoadResult getBeans(PagingLoadConfig config) throws Exception {
		//try{			
			List list = new ArrayList();
			Session session = PersistenceUtil.getSession(this.database);
			Query query = session.createQuery("from " + clazz.getName() + " where classId = '" + 
					                          BaseEntity.getClassIdentifier(clazz) + 
					                          "' order by id");
			query.setFirstResult(config.getOffset());
			query.setMaxResults(config.getLimit()); 
			
			
			for(Object object : query.list()){				
				list.add(ObjectConverter.getInstance().getModel(object));
			}
			
			int count = getCount().intValue();
			
			return new BasePagingLoadResult(list, config.getOffset(), count);
		//} catch(Exception e){
		//	throw ClientServerException.createException(e);
		//}		
	}
	
	@SuppressWarnings("unchecked")
	private List getBeans() throws Exception {
		//try{			
			List list = new ArrayList();
			Session session = PersistenceUtil.getSession(this.database);
			Query query = session.createQuery("from " + clazz.getName() + " where classId = '" + 
					                          BaseEntity.getClassIdentifier(clazz) + 
					                          "' order by id");
			
			for(Object object : query.list()){
				list.add(ObjectConverter.getInstance().getModel(object));
			}
			return list;
		//} catch(Exception e){
		//	throw ClientServerException.createException(e); 
		//}		
	}
	
	private List<GridColumnDef> getColumnsDef() throws Exception {
		//try {
			List<Field> fields = ReflectUtil.getClassFields(clazz, true);
			List<GridColumnDef> columns = new ArrayList<GridColumnDef>();		
			
			for (Field f : fields) {
				/*GridColumnDef temp = new GridColumnDef();						
				MetaField meta = (MetaField)f.getAnnotation(MetaField.class);
				Column columnDef = (Column)f.getAnnotation(Column.class);
				
				temp.setFieldName(f.getName());
				temp.setFieldType(getFieldType(f));			
				temp.setLabel(f.getName());
				temp.setVisible(true);
				
				if (columnDef != null){
					temp.setNullable(columnDef.nullable());
					temp.setWidth(columnDef.length());
				}
				
				if (meta != null){
					temp.setVisible(meta.visible());
					temp.setListable(meta.listable());
					temp.setCaseType(meta.caseType());
					temp.setColumn(meta.column());
					temp.setGroup(meta.group());
					temp.setHeight(meta.height());
					temp.setHelp(meta.help());
					if (meta.label() != null && !meta.label().equals("")){					
						temp.setLabel(meta.label());
					}
					temp.setOrder(meta.order());
					temp.setReadOnly(meta.readOnly());
					temp.setTableViewable(meta.tableViewable());
					temp.setTableViewWidth(meta.tableViewWidth());
					temp.setWidth(meta.width());
					temp.setDisplayFieldName(meta.displayFieldName());
					temp.setBeanName(meta.beanName());*/
				//}
				
				//columns.add(temp);
			}
			
	
			//Collections.sort(columns);
			
			return columns;
		//} catch ( Exception e ){
		//	throw ClientServerException.createException(e);
		//}
	}
	
	@SuppressWarnings("unchecked")
	public FieldType getFieldType(Field field) throws Exception{
		//try {
			Class fieldClass = field.getType();
			
			Class[] intClasses = {Integer.class, Long.class, BigInteger.class};
			Class[] doubleClasses = {Double.class, Float.class, BigDecimal.class};
			Class[] stringClasses = {String.class, Character.class};
			Class[] enumClasses = {Enum.class, Enumeration.class};		
					
			if (ArrayUtils.indexOf(intClasses, fieldClass) != -1){
				return FieldType.INTEGER;
			} else if (ArrayUtils.indexOf(doubleClasses, fieldClass) != -1){
				return FieldType.DOUBLE;
			} else if (ArrayUtils.indexOf(stringClasses, fieldClass) != -1){
				return FieldType.STRING;
			} else if (ArrayUtils.indexOf(enumClasses, fieldClass) != -1){
				return FieldType.ENUM;
			} else if (ReflectUtil.inheritsFrom(fieldClass, Collection.class) ||
					   ReflectUtil.inheritsFrom(fieldClass, Map.class)){
				return FieldType.DETAIL;
			} else if (ReflectUtil.inheritsFrom(fieldClass, Date.class)){
				Temporal temporal = (Temporal) field.getAnnotation(Temporal.class);
				
				if (temporal == null || temporal.value().equals(TemporalType.DATE)){				
					return FieldType.DATE;
				} else if (temporal.value().equals(TemporalType.TIME)){
					return FieldType.TIME;
				} else if (temporal.value().equals(TemporalType.TIMESTAMP)){
					return FieldType.TIMESTAMP;
				}
			} else if (ReflectUtil.inheritsFrom(fieldClass, Boolean.class)){
				return FieldType.BOOLEAN;
			}
			
			return FieldType.CLASS;		
		//} catch (Exception e ){
		//	throw ClientServerException.createException(e);
		//}
	}
	
	private Long getCount() throws Exception {
		//try {			
			Session session = PersistenceUtil.getSession(this.database);
			Query qy = session.createQuery("select count(*) from " + clazz.getName());
			Object obj = qy.uniqueResult();
			
			return (Long)obj;
		//} catch(Exception e){
		//	throw ClientServerException.createException(e);
		//}
	}

}