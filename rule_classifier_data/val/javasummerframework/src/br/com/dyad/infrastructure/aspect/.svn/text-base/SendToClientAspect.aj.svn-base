package br.com.dyad.infrastructure.aspect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.annotations.SendToClient;
import br.com.dyad.infrastructure.widget.BaseServerWidget;


public privileged aspect SendToClientAspect {

	//Adiciona a propriedade no controle do sincronizador
	pointcut alterFieldPointcut() : set(* br.com.dyad.infrastructure.widget.BaseServerWidget+.*);	
	
	before() : alterFieldPointcut() && !within(SendToClientAspect+) {		
		BaseServerWidget target = (BaseServerWidget)thisJoinPoint.getTarget();
		
		if (target != null && thisJoinPoint.getArgs() != null && thisJoinPoint.getArgs().length > 0){
			Field fld = ReflectUtil.getDeclaredField(target.getClass(), thisJoinPoint.getSignature().getName());
			SendToClient sendToClient = (SendToClient)fld.getAnnotation(SendToClient.class);
			
			if (!calledByGet(fld) && sendToClient != null && !Modifier.isStatic(fld.getModifiers())){				
				try {														
					//As classes BigDecimal e BigInteger não são serializaveis pelo GWT
					Object value = ReflectUtil.getFieldValue(target, thisJoinPoint.getSignature().getName());
					
					if (value != null) {					
						/*if (value.getClass().equals(BigInteger.class)) {
							value = ((BigInteger)value).longValue();
						} else if (value.getClass().equals(BigDecimal.class)) {
							value = ((BigDecimal)value).doubleValue();
						}*/
						
						if (value.getClass().equals(BigInteger.class) || value.getClass().equals(BigDecimal.class)) {
							value = value.toString();
						}
					}
					
					//As classes BigDecimal e BigInteger não são serializaveis pelo GWT
					Object tempValue = thisJoinPoint.getArgs()[0];
					
					if (tempValue != null) {					
						/*if (tempValue.getClass().equals(BigInteger.class)) {
							tempValue = ((BigInteger)tempValue).longValue();
						} else if (tempValue.getClass().equals(BigDecimal.class)) {
							tempValue = ((BigDecimal)tempValue).doubleValue();
						}*/
						
						if (tempValue.getClass().equals(BigInteger.class) || tempValue.getClass().equals(BigDecimal.class)) {							
							tempValue = tempValue.toString();
						}
					}
					
					target.addPropNameToPropsToSincronize(fld.getName(), tempValue, value);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	//Adiciona o valor da propriedade no controle do sincronizador
	pointcut addProps() : call(* br.com.dyad.infrastructure.widget.BaseServerWidget+.addPropsToClientSincronizer(..));
	
	before() : addProps() && !within(SendToClientAspect+) {
		BaseServerWidget target = (BaseServerWidget)thisJoinPoint.getTarget();
		List<Field> list = ReflectUtil.getClassFields(target.getClass(), true);
		
		for (Field fld : list) {
			SendToClient sendToClient = (SendToClient)fld.getAnnotation(SendToClient.class);
			if (sendToClient != null) {
				Object value = ReflectUtil.getFieldValue(target, fld.getName());
				
				//As classes BigDecimal e BigInteger não são serializaveis pelo GWT
				if (value != null) {					
					/*if (value.getClass().equals(BigInteger.class)) {
						value = ((BigInteger)value).longValue();
					} else if (value.getClass().equals(BigDecimal.class)) {
						value = ((BigDecimal)value).doubleValue();
					}*/
					
					if (value.getClass().equals(BigInteger.class) || value.getClass().equals(BigDecimal.class)) {
						value = value.toString();
					}
				}
				
				//System.out.println("*********** fld.getName()= " + fld.getName() + " value=" + value);				
				target.addPropToClient(fld.getName(), value);
				
				//Caso esta propriedade estaja marcada como setNull pela annotation
				if (sendToClient.setNull()) {					
					ReflectUtil.setFieldValue(target, fld.getName(), null);
				}
			}
		}
	}
	
	
	//Verifica se o método set foi chamado pelo método get
	public boolean calledByGet(Field field) {
		String getMethod = "get" + StringUtils.capitalize(field.getName());
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		
		if (stack != null && stack.length > 1){
			for (int i = 1; i < stack.length; i++) {
				if (stack[i] != null && stack[i].getClassName() != null && !stack[i].getClassName().equals("")){
					try{						
						Class clazz = ClassUtils.getClass(stack[i].getClassName(), false);
						String method = stack[i].getMethodName();
						
						if (field.getDeclaringClass().equals(clazz) && method.equals(getMethod)){								
							return true;
						}												
					} catch(Exception e){
						return false;
						//throw new RuntimeException(e);
					}
				}
			}
		}
		
		return false;
	}
}
