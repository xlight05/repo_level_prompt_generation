package br.com.dyad.infrastructure.service.window;

import java.util.HashMap;

import br.com.dyad.infrastructure.service.SynchronizerService;
import br.com.dyad.infrastructure.widget.Window;

public class InteractionGo extends SynchronizerService {
	
	@Override
	public HashMap getServiceValue(HashMap params) throws Exception {
		HashMap resultMap = new HashMap();
		
		String serverWindowId = (String)params.get("serverWindowId");
		if ( serverWindowId == null || serverWindowId.equals("") ){
			throw new Exception("Sincronizer Service - Interaction Go: serverWindowId is empty.");
		}
		
		Window window = getWindowMap().get(serverWindowId);
		window.interactionGo();
		System.out.println("Executou o GO no servidor!");
		resultMap.put("serverWindowId", serverWindowId );
		resultMap.put("clientSincronizerMap", window.toClientSynchronizer() );
		
		return resultMap;
	}

}
