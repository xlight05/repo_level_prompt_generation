package br.com.dyad.infrastructure.service.window;

import java.util.HashMap;

import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.service.SynchronizerService;
import br.com.dyad.infrastructure.widget.Window;
import br.com.dyad.infrastructure.widget.grid.DataGrid;

public class TableViewGridSelectRecord extends SynchronizerService {
	
	@Override
	public HashMap getServiceValue(HashMap params) throws Exception {
		HashMap resultMap = new HashMap();
		
		System.out.println("SynchronizerService->table view grid select:");
		
		String serverWindowId = (String)params.get("serverWindowId");				
		String serverGridId = (String)params.get("serverGridId");
		Long objectId = (Long)params.get("objectId");
		
		System.out.println("server objectId=" + objectId);
		
		if ( serverWindowId == null || serverWindowId.equals("") ){
			throw new Exception("Sincronizer Service - Set field value: serverWindowId is empty.");
		}
		if ( serverGridId == null || serverGridId.equals("") ){
			throw new Exception("Sincronizer Service - Set field value: serverGridId is empty.");
		}
		if ( objectId == null || objectId.equals("") ){
			throw new Exception("Sincronizer Service - Set field value: serverFieldId is empty.");
		}
		
		Window window = getWindowMap().get(serverWindowId);
		DataGrid grid = (DataGrid)window.getGridByServerObjectId(serverGridId); 
		BaseEntity baseEntity = (BaseEntity) grid.getDataList().getObjectById(objectId);
		
		grid.goToEntity(baseEntity);
		
		System.out.println("Executou o TABLEVIEW_GRID_SELECT_RECORD no servidor!");

		resultMap.put("serverWindowId", serverWindowId );
		resultMap.put("clientSincronizerMap", window.toClientSynchronizer() );
		
		return resultMap;
	}

}
