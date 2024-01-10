package br.com.dyad.infrastructure.reflect;

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

import br.com.dyad.client.DyadBaseModel;
import br.com.dyad.client.DyadBaseTreeModel;
import br.com.dyad.client.grid.FieldType;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.entity.BaseEntity;

public class ObjectConverter {
	
	public static String CLASS_PROP_NAME = "__CLASS_ID__";
	private static ObjectConverter objectConverter;
	@SuppressWarnings("unchecked")
	private HashMap<Integer, Class> classMapping = new HashMap<Integer, Class>();
	private static int classCounter = 0;
	
	private ObjectConverter(){}	
	
	@SuppressWarnings("unchecked")
	public HashMap<Integer, Class> getClassMapping() {
		return classMapping;
	}

	public static ObjectConverter getInstance(){
		if (ObjectConverter.objectConverter == null){
			ObjectConverter.objectConverter = new ObjectConverter();
		}
		
		return ObjectConverter.objectConverter;
	}
	
	@SuppressWarnings("unchecked")
	private synchronized Integer addClassMaping(Class clazz) {
		if (!classMapping.containsValue(clazz)){
			classCounter++;
			classMapping.put(classCounter, clazz);
		}
		
		return classCounter;
	}	
	
	private Object getNewInstance(Integer id) throws Exception {
		if (classMapping.containsKey(id)){
			//try{
				return classMapping.get(id).newInstance();				
			//} catch(Exception e){
			//	throw AppException.createException(e);
			//}
		} else {
			throw new Exception("Class " + id + " not found");
		}
	}
	
	@SuppressWarnings("unchecked")
	private Integer getClassId(Class clazz){
		for(Integer key : classMapping.keySet()){
			if (classMapping.get(key).equals(clazz)){
				return key;
			}
		}		
		
		return addClassMaping(clazz);
	}

	public DyadBaseModel getModel(Object object) throws Exception {
		return getModel(object, -1, null);
	}

	@SuppressWarnings("unchecked")
	public DyadBaseModel getModel(Object object, int depth, HashMap<Object, DyadBaseModel> created) throws Exception {
		if (object != null){
			addClassMaping(object.getClass());
		}
		
		if (depth == 0) {
			return null;
		}

		depth--;

		if (created == null) {
			created = new HashMap<Object, DyadBaseModel>();
		}
		
		DyadBaseModel model = new DyadBaseModel();

		if (created.containsKey(object)) {
			return (DyadBaseModel)created.get(object);
		} else {
			created.put(object, model);
		}

		//try {						
			model.set(ObjectConverter.CLASS_PROP_NAME, getClassId(object.getClass()));
			List<String> properties = ReflectUtil.getClassProperties(object.getClass(), true);

			for (String string : properties) {
				Field field = ReflectUtil.getDeclaredField(object.getClass(), string);

				if (ReflectUtil.inheritsFrom(field.getType(), Map.class)) {
					HashMap<Object, Object> map = new HashMap<Object, Object>();
					Map<Object, Object> values = (Map<Object, Object>)ReflectUtil.getFieldValue(object, string);

					for (Object item : values.keySet()) {
						map.put(item, getModel(values.get(item), depth, created));
					}

					model.set(string, map);
				} else if (ReflectUtil.inheritsFrom(field.getType(), Collection.class)) {
					ArrayList<Object> list = new ArrayList<Object>();
					Collection<Object> values = (Collection<Object>) ReflectUtil.getFieldValue(object, string);

					for (Object item : values) {
						list.add(getModel(item, depth, created));
					}

					model.set(string, list);
				} else if (getDataModelType(field).equals(FieldType.CLASS)) {
					Object prop = ReflectUtil.getFieldValue(object, string);
					prop = changeNonSerializableField(prop);
					model.set(string, getModel(prop, depth, created));
				} else {					
					Object obj = ReflectUtil.getFieldValue(object, string);					
					model.set(string, changeNonSerializableField(obj));
				}
			}

			return model;
		//} catch (Exception e) {
			//throw AppException.createException(e);
		//}
	}
	
	/**
	 * Caso tenha um BigInteger ou BigDecimal, tem que ser convertido para String
	 * 
	 * @param obj
	 * @return
	 */
	private Object changeNonSerializableField(Object obj) {
		if (obj != null) {						
			if (ReflectUtil.inheritsFrom(obj.getClass(), BigInteger.class) || 
				ReflectUtil.inheritsFrom(obj.getClass(), BigDecimal.class)) {
				
				obj = obj.toString();							
			}
		}
		
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	public DyadBaseModel getTableViewModel(Object object) throws Exception {
		
		DyadBaseModel model = new DyadBaseModel();

			model.set(ObjectConverter.CLASS_PROP_NAME, getClassId(object.getClass()));
			List<String> properties = ReflectUtil.getClassProperties(object.getClass(), true);

			for (String string : properties) {
				Field field = ReflectUtil.getDeclaredField(object.getClass(), string);
				 
				if (getDataModelType(field).equals(FieldType.CLASS)) {

					BaseEntity entity = (BaseEntity)ReflectUtil.getFieldValue(object, string);

					HashMap lookupProps = new HashMap();
					lookupProps.put("id", entity == null ? null : entity.getId() );
					lookupProps.put("lookupTemplate", entity == null ? null : entity.toString() );
					
					model.set(string, lookupProps );
					
				} else if ( ! getDataModelType(field).equals(FieldType.DETAIL)) {
					Object obj = ReflectUtil.getFieldValue(object, string);
					model.set(string, changeNonSerializableField(obj));
				}
			}

			return model;
	}

	@SuppressWarnings("unchecked")
	public DyadBaseTreeModel getTreeViewModel(Object object) throws Exception {
		
		DyadBaseTreeModel model = new DyadBaseTreeModel();

			model.set(ObjectConverter.CLASS_PROP_NAME, getClassId(object.getClass()));
			List<String> properties = ReflectUtil.getClassProperties(object.getClass(), true);

			for (String string : properties) {
				Field field = ReflectUtil.getDeclaredField(object.getClass(), string);
				
				if (getDataModelType(field).equals(FieldType.CLASS)) {

					BaseEntity entity = (BaseEntity)ReflectUtil.getFieldValue(object, string);

					HashMap lookupProps = new HashMap();
					lookupProps.put("id", entity == null ? null : entity.getId() );
					lookupProps.put("lookupTemplate", entity == null ? null : entity.toString() );
					
					model.set(string, lookupProps );
					
				} else if ( ! getDataModelType(field).equals(FieldType.DETAIL)) {
					model.set(string, ReflectUtil.getFieldValue(object, string));
				}
			}

			return model;
	}
	
	@SuppressWarnings("unchecked")
	public FieldType getDataModelType(Field field) {
		Class fieldClass = field.getType();
		
		//Class[] intClasses = {Integer.class, Long.class, BigInteger.class, int.class, long.class};
		//Class[] doubleClasses = {Double.class, Float.class, BigDecimal.class, double.class};
		Class[] intClasses = {Integer.class, Long.class, int.class, long.class};
		Class[] doubleClasses = {Double.class, Float.class, double.class};
		Class[] stringClasses = {BigInteger.class, BigDecimal.class, String.class, Character.class};
		Class[] enumClasses = {Enum.class, Enumeration.class};		
		Class[] arrayClasses = {byte[].class};
				
		if (ArrayUtils.indexOf(intClasses, fieldClass) != -1){
			return FieldType.INTEGER;
		} else if (ArrayUtils.indexOf(doubleClasses, fieldClass) != -1) {
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
		} else if (ArrayUtils.indexOf(arrayClasses, fieldClass) != -1){
			return FieldType.ARRAY;
		}
		
		return FieldType.CLASS;		
	}

	@SuppressWarnings("unchecked")
	public DyadBaseModel getLookupModel(Object object) throws Exception {
		//try {
			DyadBaseModel model = new DyadBaseModel();
			model.set(ObjectConverter.CLASS_PROP_NAME, getClassId(object.getClass()));
			model.set("id", ReflectUtil.getFieldValue(object,"id") );
			model.set("lookupTemplate", object.toString() );

			return model;
		//} catch (Exception e) {
			//throw AppException.createException(e);
		//}
	}

	public Object getBeanFromModel(DyadBaseModel model) throws Exception {
		return getBeanFromModel(model, null);
	}
	
	@SuppressWarnings("unchecked")
	public Object getBeanFromModel(DyadBaseModel model, HashMap<DyadBaseModel, Object> created) throws Exception {				
		//try {
			if (model == null){
				return null;
			}
			
			if (created == null){
				created = new HashMap<DyadBaseModel, Object>();
			}						
			
			/**
			 * Create instance
			 */
			Integer id = (Integer)model.get(ObjectConverter.CLASS_PROP_NAME);
			if (id == null || !classMapping.containsKey(id)){
				throw new Exception("Class " + id + " not found");
			}
			Object bean = getNewInstance(id);
			//
			
			if (created.containsKey(model)){
				return created.get(model); 
			} else {
				created.put(model, bean);
			}
			
			List<String> properties = ReflectUtil.getClassProperties(bean.getClass(), true);

			for (String string : properties) {
				Field field = ReflectUtil.getDeclaredField(bean.getClass(), string);
				if (ReflectUtil.inheritsFrom(field.getType(), Map.class)) {
					HashMap<Object, Object> map = (HashMap<Object, Object>)model.get(string);
					HashMap<Object, Object> copy = new HashMap<Object, Object>();

					for (Object key : map.keySet()) {
						Object value = map.get(key);
						if (value != null){							
							Object newInstance = getBeanFromModel((DyadBaseModel)value, created);
							copy.put(key, newInstance);
						}
					}

					ReflectUtil.setFieldValue(bean, string, copy);
				} else if (ReflectUtil.inheritsFrom(field.getType(),
						Collection.class)) {
					ArrayList<Object> list = (ArrayList<Object>)model.get(string);
					ArrayList<Object> copy = new ArrayList<Object>();

					for (Object item : list) {
						if (item != null){							
							Object newInstance = getBeanFromModel((DyadBaseModel) item, created);
							copy.add(newInstance);
						}
					}

					ReflectUtil.setFieldValue(bean, string, copy);
				} else if (getDataModelType(field).equals(FieldType.CLASS)) {
					Object value = getBeanFromModel((DyadBaseModel)model.get(string), created);
					ReflectUtil.setFieldValue(bean, string, value);					
				} else {
					ReflectUtil.setFieldValue(bean, string, model.get(string));
				}
			}
			
			return bean;
		//} catch (Exception e) {
			//throw AppException.createException(e);
		//}
		
	}	

}
