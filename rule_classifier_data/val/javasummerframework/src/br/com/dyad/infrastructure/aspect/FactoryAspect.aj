package br.com.dyad.infrastructure.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import br.com.dyad.client.AppException;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.customization.CustomLoader;

public aspect FactoryAspect {
	//Substitui todos os construtores explícitos 
	public pointcut userClassConstructor() : call(br.com.dyad.infrastructure.customization.Customizable+.new(..)) 
			&& !within(FactoryAspect+);

	@SuppressWarnings("unchecked")
	Object around() : userClassConstructor( ){
		Class clazz = thisJoinPointStaticPart.getSignature().getDeclaringType();
		Class customClass = CustomLoader.getInstance().getCustomClass(clazz);
			
		try{					
			return ReflectUtil.getClassInstance(customClass, thisJoinPoint.getArgs());
		} catch(AppException e){
			throw e;
		} catch(Exception e){
			throw new RuntimeException(e);
		}		
	}
	
	//Substitui todas os objetos que são instanciados por reflexão
	public pointcut javaClassInstance() : call(Object java.lang.Class.newInstance()) && !within(FactoryAspect+);

	@SuppressWarnings("unchecked")
	Object around() : javaClassInstance( ){				
		Class clazz = (Class)thisJoinPoint.getTarget();
		Class customClass = CustomLoader.getInstance().getCustomClass(clazz);
			
		try{
			return customClass.newInstance();
		} catch(AppException e){
			throw e;
		} catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	//Alterando a API de reflexão para pegar as clases customizadas
	@SuppressWarnings("unchecked")
	public pointcut getClassProperties(Class classRef, boolean getSuperclassProperties) : 
		call(List<String> br.com.dyad.commons.reflect.ReflectUtil+.getClassProperties(Class, boolean)) && 
		!within(FactoryAspect+) && args(classRef, getSuperclassProperties);
	
	@SuppressWarnings("unchecked")
	List<String> around(Class classRef, boolean getSuperclassProperties) : getClassProperties(classRef, getSuperclassProperties){
		Class customClass = CustomLoader.getInstance().getCustomClass(classRef);
		return ReflectUtil.getClassProperties(customClass, getSuperclassProperties);
	}
	
	//public static List<Annotation> getAnnotations(Class annotation) {
	@SuppressWarnings("unchecked")
	public pointcut getAnnotations(Class annotation) : 
		call(List<Annotation> br.com.dyad.commons.reflect.ReflectUtil+.getAnnotations(Class)) && 
		!within(FactoryAspect+) && args(annotation);
	
	@SuppressWarnings("unchecked")
	List<Annotation> around(Class annotation) : getAnnotations(annotation){
		Class customClass = CustomLoader.getInstance().getCustomClass(annotation);
		return ReflectUtil.getAnnotations(customClass);
	}
	
	//public static Field getDeclaredField(Class clazz, String name)
	@SuppressWarnings("unchecked")
	public pointcut getDeclaredField(Class clazz, String name) : 
		call(List<Annotation> br.com.dyad.commons.reflect.ReflectUtil+.getDeclaredField(Class, String)) && 
		!within(FactoryAspect+) && args(clazz, name);
	
	@SuppressWarnings("unchecked")
	Field around(Class clazz, String name) : getDeclaredField(clazz, name){
		Class customClass = CustomLoader.getInstance().getCustomClass(clazz);
		return ReflectUtil.getDeclaredField(customClass, name);
	}
	
	//public static List<Field> getClassFields(Class classRef, boolean getSuperclassProperties)
	@SuppressWarnings("unchecked")
	public pointcut getClassFields(Class classRef, boolean getSuperclassProperties) : 
		call(List<Field> br.com.dyad.commons.reflect.ReflectUtil+.getClassFields(Class, boolean)) && 
		!within(FactoryAspect+) && args(classRef, getSuperclassProperties);
	
	@SuppressWarnings("unchecked")
	List<Field> around(Class classRef, boolean getSuperclassProperties) : getClassFields(classRef, getSuperclassProperties){
		Class customClass = CustomLoader.getInstance().getCustomClass(classRef);
		return ReflectUtil.getClassFields(customClass, getSuperclassProperties);
	}
	
	//public static List<String> getClassTypesProperties(Class classRef, boolean getSuperclassProperties)
	@SuppressWarnings("unchecked")
	public pointcut getClassTypesProperties(Class classRef, boolean getSuperclassProperties) : 
		call(List<String> br.com.dyad.commons.reflect.ReflectUtil+.getClassTypesProperties(Class, boolean)) && 
		!within(FactoryAspect+) && args(classRef, getSuperclassProperties);
	
	@SuppressWarnings("unchecked")
	List<String> around(Class classRef, boolean getSuperclassProperties) : getClassTypesProperties(classRef, getSuperclassProperties){
		Class customClass = CustomLoader.getInstance().getCustomClass(classRef);
		return ReflectUtil.getClassTypesProperties(customClass, getSuperclassProperties);
	}
	
	//public static List<String> getClassMethods(Class classRef, boolean getSuperclassMethods)
	public pointcut getClassMethods(Class classRef, boolean getSuperclassProperties) : 
		call(List<String> br.com.dyad.commons.reflect.ReflectUtil+.getClassMethods(Class, boolean)) && 
		!within(FactoryAspect+) && args(classRef, getSuperclassProperties);
	
	@SuppressWarnings("unchecked")
	List<String> around(Class classRef, boolean getSuperclassProperties) : getClassMethods(classRef, getSuperclassProperties){
		Class customClass = CustomLoader.getInstance().getCustomClass(classRef);
		return ReflectUtil.getClassMethods(customClass, getSuperclassProperties);
	}
	

	//Aviso em tempo de compilação para o uso do console
	/*
	pointcut printing(): (get(* System.out) || get(* System.err) || call(* printStackTrace())) && !within(FactoryAspect) ;
 
	declare warning
		: printing()
		: "Substituir essa chamada por um log!";
	*/

}
