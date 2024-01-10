package br.com.dyad.infrastructure.aspect;

import org.apache.commons.lang.StringUtils;

import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.widget.field.Field;

public aspect SetFieldEvents {
	
	pointcut callFieldEvents() : call(* br.com.dyad.infrastructure.widget.field.Field+.setValue(..));	
	pointcut callFieldEvents2() : call(* br.com.dyad.infrastructure.widget.field.Field+.setValueWithoutEditing(..));
	
	before() : callFieldEvents() {
		Field fld = (Field)thisJoinPoint.getTarget();
		if (fld != null){			
			fld.onBeforeChange();
		}
	}
	
	before() : callFieldEvents2() {
		Field fld = (Field)thisJoinPoint.getTarget();
		if (fld != null){			
			fld.onBeforeChange();
		}
	}
	
	after() : callFieldEvents() {
		Field fld = (Field)thisJoinPoint.getTarget();
		if (fld != null){			
			fld.onAfterChange();
		}
	}
	
	after() : callFieldEvents2() {
		Field fld = (Field)thisJoinPoint.getTarget();
		if (fld != null){			
			fld.onAfterChange();
		}
	}
	
	pointcut callSetMethod() : call(* br.com.dyad.infrastructure.entity.BaseEntity+.set*(..));	
	
	after() : callSetMethod(){
		BaseEntity entity = (BaseEntity)thisJoinPoint.getTarget();
		if (entity != null){			
			String propName = getPropNameFromMethodName(thisJoinPoint.getSignature().getName());
			entity.onAfterChange(propName);		
		}
	}
	
	before() : callSetMethod(){
		BaseEntity entity = (BaseEntity)thisJoinPoint.getTarget();
		if (entity != null){			
			String propName = getPropNameFromMethodName(thisJoinPoint.getSignature().getName());		
			entity.onBeforeChange(propName);		
		}
	}
	
	pointcut callReflectSetMethod(Object object, String name, Object value) : 
		call(* br.com.dyad.commons.reflect.ReflectUtil.setFieldValue(Object, String, Object)) && args(object, name, value);
	
	after(Object object, String name, Object value) : callReflectSetMethod(object, name, value){
		if (ReflectUtil.inheritsFrom(object.getClass(), BaseEntity.class)){			
			BaseEntity entity = (BaseEntity)object;
			if (entity != null){				
				entity.onAfterChange(name);		
			}
		}
	}
	
	before(Object object, String name, Object value) : callReflectSetMethod(object, name, value){
		if (ReflectUtil.inheritsFrom(object.getClass(), BaseEntity.class)){			
			BaseEntity entity = (BaseEntity)object;
			if (entity != null){				
				entity.onBeforeChange(name);		
			}
		}		
	}
	
	public String getPropNameFromMethodName(String methodName){
		if (methodName != null && methodName.length() > 3 && StringUtils.startsWith(methodName, "set")){			
			methodName = methodName.substring(3);
			methodName = ("" + methodName.charAt(0)).toLowerCase() +  
			             methodName.substring(1);
			return methodName;
		} else {
			return null;
		}
	}
}
