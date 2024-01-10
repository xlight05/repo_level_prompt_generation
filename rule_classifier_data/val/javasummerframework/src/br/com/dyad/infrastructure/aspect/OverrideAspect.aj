package br.com.dyad.infrastructure.aspect;

import java.lang.reflect.Method;
import org.apache.commons.lang.ClassUtils;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.annotations.Customize;
import br.com.dyad.infrastructure.customization.CustomLoader;
import br.com.dyad.infrastructure.customization.MethodConfig;

//Customização de métodos em classes do produto 
public aspect OverrideAspect {	
	pointcut callMethodVoid() : call(public void br.com.dyad.infrastructure.customization.Customizable+.*(..)) 
	    	&& !within(OverrideAspect) && !call(br.com.dyad.infrastructure.customization.Customizable+.new(..));	

	//métodos void
	void around() : callMethodVoid(){		
		try {			
			Object target = thisJoinPoint.getTarget();
			Class clazz = thisJoinPoint.getSignature().getDeclaringType();
			String method = thisJoinPoint.getSignature().getName();
			Method methodRef = ReflectUtil.findMethod(clazz, method, thisJoinPoint.getArgs());
			boolean calledbyCustomized = calledByCustomizedMethod(methodRef);
			MethodConfig conf = CustomLoader.getInstance().getNewMethod(clazz, methodRef);
			
			//Método estático
			if (target == null){
				if (calledbyCustomized || conf == null || conf.getCallSuper()){
					proceed();
				} 
				
				if (!calledbyCustomized && conf != null){
					conf.getNewMethod().invoke(null, thisJoinPoint.getArgs());
				}
			} else {
				Object[] params = null;
				if (thisJoinPoint.getArgs() == null){
					params = new Object[1];				
				} else {				
					params = new Object[thisJoinPoint.getArgs().length + 1];
					for (int i = 0; i < thisJoinPoint.getArgs().length; i++) {
						params[i+1] = thisJoinPoint.getArgs()[i];
					}
				}
				
				params[0] = thisJoinPoint.getTarget();											
				
				if (calledbyCustomized || conf == null || conf.getCallSuper()){
					proceed();
				} 

				if (!calledbyCustomized && conf != null){				
					conf.getNewMethod().invoke(null, params);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	pointcut callMethodReturn() : call(public * br.com.dyad.infrastructure.customization.Customizable+.*(..)) 
		&&	!call(void br.com.dyad.infrastructure.customization.Customizable+.*(..))
		&& !call(br.com.dyad.infrastructure.customization.Customizable+.new(..))
		&& !within(OverrideAspect);
	
	//métodos com retorno
	Object around() : callMethodReturn(){		
		try {			
			String method = thisJoinPoint.getSignature().getName();
			Object target = thisJoinPoint.getTarget();
			Class clazz = thisJoinPoint.getSignature().getDeclaringType();
			Object ret = null;		
			Method methodRef = ReflectUtil.findMethod(clazz, method, thisJoinPoint.getArgs());
			boolean calledbyCustomized = calledByCustomizedMethod(methodRef);
			MethodConfig conf = CustomLoader.getInstance().getNewMethod(clazz, methodRef);
			
			//Método estático
			if (target == null){				
				if (calledbyCustomized || conf == null || conf.getCallSuper()){
					//ret = ReflectUtil.getMethodReturn(clazz, method, thisJoinPoint.getArgs());
					ret = proceed();
				} 
				
				if (!calledbyCustomized && conf != null){
					ret = conf.getNewMethod().invoke(null, thisJoinPoint.getArgs());
				}
			} else {
				Object[] params = null;
				if (thisJoinPoint.getArgs() == null){
					params = new Object[1];				
				} else {
					params = new Object[thisJoinPoint.getArgs().length + 1];
					for (int i = 0; i < thisJoinPoint.getArgs().length; i++) {
						params[i+1] = thisJoinPoint.getArgs()[i];
					}
				}
				
				params[0] = thisJoinPoint.getTarget();
				
				if (calledbyCustomized || conf == null || conf.getCallSuper()){
					//ret = ReflectUtil.getMethodReturn(thisJoinPoint.getTarget(), method, thisJoinPoint.getArgs());
					ret = proceed();					
				} 

				if (!calledbyCustomized && conf != null){
					ret = conf.getNewMethod().invoke(null, params);
				}
			}
			
			return ret;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}				
	}
	
	public boolean calledByCustomizedMethod(Method calledMethod) throws ClassNotFoundException{
		if (calledMethod == null){
			return false;
		}
				
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		
		if (stack != null && stack.length > 1){
			for (int i = 1; i < stack.length; i++) {
				if (stack[i] != null && stack[i].getClassName() != null && !stack[i].getClassName().equals("")){
					//Não sei porque, mas quando chamo o class.forName da pau em algumas classes do HostedMode
					//Class clazz = Class.forName(stack[i].getClassName());					
					//Class clazz = ClassLoader.getSystemClassLoader().loadClass(stack[i].getClassName());
					try{						
						//Class clazz = ClassLoader.getSystemClassLoader().loadClass(stack[i].getClassName());
						Class clazz = ClassUtils.getClass(stack[i].getClassName(), false);
						String method = stack[i].getMethodName();
						Method m = ReflectUtil.findMethod(clazz, method);
						
						if (m != null){								
							boolean customizedBy = CustomLoader.getInstance().isCustomizedBy(calledMethod, m);
							
							if (customizedBy && m != null && m.getAnnotation(Customize.class) != null){
								return true;
							}
						}
					} catch(Exception e){
						return false;
					}
				}
			}
		}
		
		return false;
	}
}
