package br.com.dyad.infrastructure.aspect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.commons.lang.StringUtils;

import br.com.dyad.client.AppException;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.entity.BaseEntity;

public aspect SetOldValueAspect {	
	pointcut callSetMethod() : set(* br.com.dyad.infrastructure.entity.BaseEntity+.*);

	before() : callSetMethod(){
		if (thisJoinPoint.getTarget() != null 
				&& !thisJoinPoint.getSignature().getName().equalsIgnoreCase("OLDVALUES")
				&& !thisJoinPoint.getSignature().getName().equalsIgnoreCase("ORIGINALVALUES")){
		
			String fieldName = thisJoinPoint.getSignature().getName();			
			Field fld;
			try {
				fld = ReflectUtil.getDeclaredField(thisJoinPoint.getTarget().getClass(), fieldName);
				BaseEntity entity = (BaseEntity)thisJoinPoint.getTarget();
				if (!Modifier.isStatic(fld.getModifiers()) && entity.getOldValues() != null && entity.getOriginalValues() != null){
					setOldValue(thisJoinPoint.getSignature().getName(), entity, thisJoinPoint.getArgs());
				}
			} catch (Exception e) {
				throw new AppException(e.getMessage());
			}						
		}
	}
	
	public void setOldValue(String methodName, Object obj, Object[] args){
		String propertyName = methodName;			
		methodName = "get" + StringUtils.capitalize(methodName);		
		BaseEntity entity = (BaseEntity)obj;
					
		if (args.length > 0){
			Object vo = ReflectUtil.getMethodReturn(entity, methodName);

			if (!entity.getOriginalValues().containsKey(propertyName) && vo == null){
				return;
			}
			
			entity.getOldValues().put(propertyName, vo);

			if (!entity.getOriginalValues().containsKey(propertyName)){
				entity.getOriginalValues().put(propertyName, vo);
			}
		}
	}
	
}
