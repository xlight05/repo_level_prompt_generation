package br.com.dyad.infrastructure.service.window;

import java.util.HashMap;

import br.com.dyad.infrastructure.service.SynchronizerService;
import br.com.dyad.infrastructure.widget.Window;
import br.com.dyad.infrastructure.widget.field.Field;
import br.com.dyad.infrastructure.widget.grid.Grid;

public class GetFieldInformation extends SynchronizerService {
	
	@Override
	public HashMap getServiceValue(HashMap params) throws Exception {
		HashMap resultMap = new HashMap();
		
		System.out.println("SynchronizerService->pegando as informações de um field:");
		
		String serverWindowId = (String)params.get("serverWindowId");				
		String serverGridId = (String)params.get("serverGridId");
		String serverFieldId = (String)params.get("serverFieldId");
		
		if ( serverWindowId == null || serverWindowId.equals("") ){
			throw new Exception("Sincronizer Service - Set field value: serverWindowId is empty.");
		}
		if ( serverGridId == null || serverGridId.equals("") ){
			throw new Exception("Sincronizer Service - Set field value: serverGridId is empty.");
		}
		if ( serverFieldId == null || serverFieldId.equals("") ){
			throw new Exception("Sincronizer Service - Set field value: serverFieldId is empty.");
		}
		
		Window window = getWindowMap().get(serverWindowId);
		Grid grid = window.getGridByServerObjectId(serverGridId); 
		Field field = grid.getFieldByServerObjectId(serverFieldId);
						
		System.out.println("Executou o GET_FIELD_INFORMATION no servidor!");

		resultMap.put("serverWindowId", serverWindowId );
		resultMap.put("clientSincronizerMap", field.getFieldInformation() );
		
		return resultMap;
	}

}
