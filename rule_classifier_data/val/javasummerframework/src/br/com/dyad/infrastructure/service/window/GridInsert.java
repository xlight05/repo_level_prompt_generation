package br.com.dyad.infrastructure.service.window;

import java.util.HashMap;

import br.com.dyad.infrastructure.service.SynchronizerService;
import br.com.dyad.infrastructure.widget.grid.DataGrid;

public class GridInsert extends SynchronizerService {
	
	@Override
	public HashMap getServiceValue(HashMap params) throws Exception {
		HashMap resultMap = new HashMap();
		
		String serverWindowId = (String)params.get("serverWindowId");
		String serverDataGridId = (String)params.get("serverDataGridId");
		
		DataGrid dataGrid = this.getGridToDispatchEvent(serverWindowId, serverDataGridId);

		dataGrid.dispatchInsert();

		System.out.println("Executou o insert da grid no servidor!");
		resultMap.put("serverWindowId", serverWindowId );
		resultMap.put("clientSincronizerMap", dataGrid.getWindow().toClientSynchronizer() );
		
		return resultMap;
	}

}
