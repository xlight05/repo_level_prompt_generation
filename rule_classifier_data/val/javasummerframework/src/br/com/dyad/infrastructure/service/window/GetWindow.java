package br.com.dyad.infrastructure.service.window;

import java.util.HashMap;

import br.com.dyad.infrastructure.service.SynchronizerService;
import br.com.dyad.infrastructure.widget.Window;

public class GetWindow extends SynchronizerService {
	
	@Override
	public HashMap getServiceValue(HashMap params) throws Exception {
		HashMap resultMap = new HashMap();
		
		String serverWindowId = (String)params.get("serverWindowId");
		if ( serverWindowId == null || serverWindowId.equals("") ){
			throw new Exception("Sincronizer Service - Get Window: serverWindowId is empty.");
		}
		
		HashMap clientSincronizerMap = null;
		Window window = getWindowMap().get( serverWindowId );				
		if ( window != null ){
			clientSincronizerMap = window.toClientSynchronizer();
			System.out.println("Achou o window. Ele ja existia. serverWindowId=" + serverWindowId);					
		} else {
			System.out.println("Não achou o window. serverWindowId=" + serverWindowId);
		}
		resultMap.put("serverWindowId", serverWindowId );	
		resultMap.put("clientSincronizerMap", clientSincronizerMap );
		
		return resultMap;
	}

}
