package br.com.dyad.infrastructure.service.window;

import java.util.HashMap;

import br.com.dyad.infrastructure.service.SynchronizerService;
import br.com.dyad.infrastructure.widget.Window;

public class DestroyWindow extends SynchronizerService {
	
	@Override
	public HashMap getServiceValue(HashMap params) throws Exception {
		HashMap resultMap = new HashMap();
		
		String serverWindowId = (String)params.get("serverWindowId");
		if ( serverWindowId == null || serverWindowId.equals("") ){
			throw new Exception("Sincronizer Service - Destroy Window: serverWindowId is empty.");
		}
		
		boolean windowDestroyed = false;
		Window window = getWindowMap().get(serverWindowId);
		if ( window != null ){
			window = null;
			//Assim ainda ficava lixo no hashmap
			//getWindowMap().put(serverWindowId, null);
			getWindowMap().remove(serverWindowId);
			windowDestroyed = true;
			System.out.println("O Window de id " + serverWindowId + " foi destruido com êxito!!!");
		}
		resultMap.put("serverWindowId", serverWindowId );
		resultMap.put("windowDestroyed", windowDestroyed );
		
		return resultMap;
	}

}
