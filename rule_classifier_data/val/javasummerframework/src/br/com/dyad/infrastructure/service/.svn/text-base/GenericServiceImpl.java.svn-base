package br.com.dyad.infrastructure.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import br.com.dyad.client.ClientServerException;
import br.com.dyad.client.DyadBaseModel;
import br.com.dyad.client.GenericService;
import br.com.dyad.client.system.SystemInfo;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.annotations.SkipValidation;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class GenericServiceImpl extends RemoteServiceServlet implements GenericService {
	public static ServletContext servletContext; 
	
	public void getSystemInfo() throws Exception {		
		getSystemInfo(getServletContext());
		
	}

	@SuppressWarnings("unchecked")
	public HashMap getServiceValue(String serviceClass, HashMap params)
			throws Exception {		
		try {									
			if (SystemInfo.getInstance().getApplicationPath() == null || SystemInfo.getInstance().getApplicationPath().equals("")){
				getSystemInfo();
			}
			
			Class clazz = Class.forName(serviceClass);
			DyadService classInstance = (DyadService)clazz.newInstance();
			
			if (GenericServiceImpl.servletContext == null){				
				GenericServiceImpl.servletContext = getServletContext();
			}
			
			//if (params != null && params.containsKey(GenericServiceImpl.GET_REQUEST)){					
				params.put(GenericServiceImpl.GET_REQUEST, getThreadLocalRequest().getSession() == null ? 
														   getThreadLocalRequest().getSession(true) : getThreadLocalRequest().getSession());
			//} else {	
				//if (!clazz.equals(ConnectionService.class) && !clazz.equals(LoginService.class)){
				if (clazz.getAnnotation(SkipValidation.class) == null) {
					String sessionId = (String)params.get(GenericService.SESSION_ID);
					validateSession(sessionId);
				}
			//}
				
			if (params.get(GenericService.GET_DATABASE_FILE) == null){				
				params.put(GenericService.GET_DATABASE_FILE, getThreadLocalRequest().getSession().getAttribute(GenericService.GET_DATABASE_FILE));
			}
			classInstance.setHttpSession(getThreadLocalRequest().getSession());
			
			System.out.println( "GenericServiceImpl.getServiceValue(); params.keySet().toString(): "+ params.keySet().toString() );
			HashMap returnValue = classInstance.getServiceValue(params);			
			
			//returnValue.put(GenericService.GET_DATABASE_FILE, getThreadLocalRequest().getSession().getAttribute(GenericService.GET_DATABASE_FILE));			
			
			return returnValue;			
		} catch (Exception e) {
			if ( e.getStackTrace() == null ){
				e.setStackTrace(new StackTraceElement[]{ new StackTraceElement(e.getMessage(), e.getMessage(), e.getMessage(), 0 )});
			}
			throw ClientServerException.createException(e, DyadService.stackTraceToString(e));
		}
	}
	
	protected void validateSession( String sessionId) throws Exception{
		String serverSessionId = getThreadLocalRequest().getSession().getId();
		if ( !serverSessionId.equals(sessionId) ){
			Exception e = new Exception("User session is invalid.");
			ClientServerException ex = ClientServerException.createException(e, DyadService.stackTraceToString(e));
			ex.setType(ClientServerException.INVALID_SESSION);
			throw ex;
		}
	}
	
	public static void getSystemInfo(ServletContext servletContext) throws Exception {		
		try {		
			SystemInfo systemInfo = SystemInfo.getInstance();
			String applicationPath = servletContext.getRealPath("/");
						
			systemInfo.setApplicationPath(applicationPath);
			systemInfo.setTempDir(applicationPath + "temp");						
			
			File dir = new File(systemInfo.getTempDir());
			dir.mkdir();
		} catch (Exception e) {
			throw ClientServerException.createException(e, DyadService.stackTraceToString(e));
		}		
		
	}
	
	public HttpServletRequest getServletRequest() {
		return getThreadLocalRequest();
	}
	
	/**
	 * método usado para analisar o retorno do HashMap do GenericService, fica mais fácil 
	 * de identificar algo que não seja serializável
	 * 
	 * @param map
	 */
	public void analizeHashMap(HashMap map) {
		List<String> types = new ArrayList<String>();				
		
		types.addAll(getDeclaredTypes(map.keySet()));
		types.addAll(getDeclaredTypes(map.values()));
		
		System.out.println("***************** MAP *****************");
		for(String t : types) {
			System.out.println("##### " + t);
		}
		System.out.println("***************** MAP *****************");
	}
	
	public List<String> getDeclaredTypes(Collection elements) {
		List<String> list = new ArrayList<String>();
		
		for (Object obj : elements) {
			if (obj != null) {
				if (ReflectUtil.inheritsFrom(obj.getClass(), DyadBaseModel.class)) {
					DyadBaseModel model = (DyadBaseModel)obj;
					Map map = model.getProperties();
					
					list.addAll(getDeclaredTypes(map.keySet()));
					list.addAll(getDeclaredTypes(map.values()));
				} else if (ReflectUtil.inheritsFrom(obj.getClass(), Map.class)) {
					list.addAll(getDeclaredTypes(((Map)obj).keySet()));
					list.addAll(getDeclaredTypes(((Map)obj).values()));
				} else if (ReflectUtil.inheritsFrom(obj.getClass(), Collection.class)) {
					list.addAll(getDeclaredTypes((List)obj));
				} else if (list.indexOf(obj.getClass().getName()) == -1) {					
					list.add(obj.getClass().getName());
				}
			}
		}
		
		return list;
	}
	
}