package br.com.dyad.infrastructure.aspect;

import java.util.Hashtable;

import javax.servlet.http.HttpSession;

import br.com.dyad.client.GenericService;
import br.com.dyad.infrastructure.entity.Language;
import br.com.dyad.infrastructure.service.GenericServiceImpl;

public aspect UserInfoAspect /*percflow(servletCflow())*/ {

	private static Hashtable<Thread, HttpSession> requests = new Hashtable<Thread, HttpSession>();
	pointcut servletCflow() : execution(* br.com.dyad.infrastructure.service.GenericServiceImpl+.getServiceValue(..)) && 
		within(br.com.dyad.infrastructure.service.GenericServiceImpl+);	 
	//pointcut cflowPointcut() : cflow(servletCflow());
	
	// Advice declaration
	//before() : cflowPointcut() && !within(UserInfoAspect+){
	before() : servletCflow() && !within(UserInfoAspect+){
		if (thisJoinPoint.getSignature() != null
			&& thisJoinPoint.getSignature().getDeclaringType() != null
			&& thisJoinPoint.getSignature().getDeclaringType().equals(GenericServiceImpl.class)
			&& thisJoinPoint.getSignature().getName().equals("getServiceValue")) {
			
			GenericServiceImpl servlet = (GenericServiceImpl)thisJoinPoint.getTarget();
			requests.put(Thread.currentThread(), servlet.getServletRequest().getSession());
		}
	}
	
	//after() : servletCflow() && !within(UserInfoAspect+){
	after() : servletCflow() && !within(UserInfoAspect+){
		if (thisJoinPoint.getSignature() != null
			&& thisJoinPoint.getSignature().getDeclaringType() != null
			&& thisJoinPoint.getSignature().getDeclaringType().equals(GenericServiceImpl.class)
			&& thisJoinPoint.getSignature().getName().equals("getServiceValue")) {
			
			requests.remove(Thread.currentThread());
		}
	}
	
	public static String getUser(){
		return (String)getSessionToken(GenericService.USER_LOGIN);
	}
	
	public static Long getUserKey(){
		return (Long)getSessionToken(GenericService.USER_KEY);
	}
	
	public static Language getUserLanguage(){
		return (Language)getSessionToken(GenericService.USER_LANGUAGE);
	}
	
	public static String getDatabase(){
		return (String)getSessionToken(GenericService.GET_DATABASE_FILE);
	}
	
	public static Object getSessionToken(String token){
		HttpSession session = requests.get(Thread.currentThread());
		if (session != null){			
			return session.getAttribute(token);
		} else {
			return null;
		}
	}
	
	pointcut newThreadPointcut() : execution(java.lang.Thread+.new(..));
	
	before() : newThreadPointcut() {		
		Thread thread = (Thread)thisJoinPoint.getTarget();
		
		if (requests.get(Thread.currentThread()) != null){
			requests.put(thread, requests.get(Thread.currentThread()));
		}
	}
	
}
