package br.com.dyad.infrastructure.service.window;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import br.com.dyad.client.AppException;
import br.com.dyad.client.DyadInfrastructure;
import br.com.dyad.infrastructure.service.SynchronizerService;
import br.com.dyad.infrastructure.widget.Window;

public class NewWindow extends SynchronizerService {
	
	@Override
	public HashMap getServiceValue(HashMap params) throws Exception {
		HashMap resultMap = new HashMap();
		
		String serverWindowPath = (String)params.get("serverWindowPath");				
		HashMap props = (HashMap)params.get("props");
		
		if ( serverWindowPath == null || serverWindowPath.equals("") ){
			throw new Exception("Sincronizer Service - New Window: serverWindowPath is empty.");
		}				
		
		Class windowClass = Class.forName(serverWindowPath);
		boolean singleton = isSingletonWindow(windowClass);
		Window window = (singleton ? getCreatedWindow(windowClass) : null);
		
		if (singleton && window != null) {					
			throw new AppException(DyadInfrastructure.translate("Esta tela não pode ser aberta mais de uma vez"));
		} else {					
			Constructor constructor = windowClass.getConstructor(HttpSession.class);
			window = (Window)constructor.newInstance(this.getHttpSession());
			window.populateProps(props);
			window.setWindowId((Long)params.get("windowId"));
			
			String serverWindowId = window.getObjectId();
			window.defineWindow();
			window.onAfterDefineWindow();
			
			getWindowMap().put(serverWindowId, window);
			
			System.out.println("Instanciou o process!;serverWindowId=" + serverWindowId);				
			resultMap.put("serverWindowId", serverWindowId );
			resultMap.put("clientSincronizerMap", window.toClientSynchronizer() );				
			
		}
		
		return resultMap;
	}

}
