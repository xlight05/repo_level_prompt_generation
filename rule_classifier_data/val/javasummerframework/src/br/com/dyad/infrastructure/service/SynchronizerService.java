package br.com.dyad.infrastructure.service;

import java.util.HashMap;
import java.util.Hashtable;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import br.com.dyad.client.GenericService;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.annotations.Singleton;
import br.com.dyad.infrastructure.widget.Window;
import br.com.dyad.infrastructure.widget.grid.DataGrid;

public class SynchronizerService extends DyadService{
	protected static final String PACKAGE_NAME = "br.com.dyad.infrastructure.service.window.";
	protected static Hashtable<String, DyadService> serviceTable = new Hashtable<String, DyadService>();
	
	/**
	 * @param serviceName
	 * @return DyadService
	 * @throws Exception
	 * @author Eduardo
	 * 
	 * retorna uma instância do servico solicitado, caso seja a primeira solicitação do serviço,
	 * ele será instanciado e inserido na tabela hash dos serviços
	 */
	protected static DyadService getService(String serviceName) throws Exception {
		DyadService service = serviceTable.get(serviceName);
		
		if (service == null) { 
			String className = StringUtils.capitalize(serviceName);
			service = (DyadService)ReflectUtil.getClassInstance(PACKAGE_NAME + className, null);
			serviceTable.put(serviceName, service);
		}
		
		return service;
	}
	
	/**
	 * @return HashMap<String, Window>
	 * 
	 * pega a tabela de process da seção do usuário
	 */
	protected HashMap<String, Window> getWindowMap() {
		HashMap<String, Window> map = (HashMap<String, Window>)getHttpSession().getAttribute(GenericService.WINDOW_MAP_TOKEN);
		
		if (map == null) {
			map = new HashMap<String, Window>();
			getHttpSession().setAttribute(GenericService.WINDOW_MAP_TOKEN, map);			
		}
		
		return map;
	}
	
	/**
	 * @author Eduardo
	 * 
	 * localiza o serviço baseado na variável actionToExecute e chama o método getServiceValue do mesmo
	 */
	@SuppressWarnings("unchecked")
	@Override
	public HashMap getServiceValue(HashMap params) throws Exception{
		HashMap resultMap = new HashMap();
		String actionToExecute = (String)params.get("actionToExecute");				
		
		DyadService service = getService(actionToExecute);		
		service.setHttpSession(getHttpSession());
		
		return service.getServiceValue(params);
	}
	
	protected boolean isSingletonWindow(Class windowClass) {
		Singleton singleton = (Singleton)windowClass.getAnnotation(Singleton.class);
		return singleton != null;
	}
	
	protected Window getCreatedWindow(Class windowClass) {
		for(Window window : getWindowMap().values()) {				
			if (windowClass.equals(window.getClass()) && getHttpSession().getId().equals(window.getHttpSession().getId())) {
				return window;
			}
		}
		
		return null;
	}

	protected DataGrid getGridToDispatchEvent( String serverWindowId, String serverDataGridId ) throws Exception{
		if ( serverWindowId == null || serverWindowId.equals("") ){
			throw new Exception("Sincronizer Service: serverWindowId is empty.");
		}
		if ( serverDataGridId == null || serverDataGridId.equals("") ){
			throw new Exception("Sincronizer Service: serverGridId is empty.");
		}				

		Window window = getWindowMap().get(serverWindowId);
		if ( window == null ){
			throw new Exception("Window with serverWindowId " + serverWindowId + " was not found.");
		}	

		DataGrid dataGrid = (DataGrid)window.getGridByServerObjectId(serverDataGridId);
		if ( dataGrid == null ){
			throw new Exception("Grid with serverWindowId " + serverWindowId + " was not found.");
		}	
		return dataGrid;
	}

	public static Window getWindowByWindowId(HttpSession session, String serverWindowId ){
		HashMap<String, Window> map = (HashMap<String, Window>)session.getAttribute(GenericService.WINDOW_MAP_TOKEN);
		return map.get(serverWindowId);
	}
}
