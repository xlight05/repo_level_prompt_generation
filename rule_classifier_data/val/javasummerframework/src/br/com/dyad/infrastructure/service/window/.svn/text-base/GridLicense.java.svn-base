package br.com.dyad.infrastructure.service.window;

import java.util.HashMap;

import br.com.dyad.infrastructure.service.SynchronizerService;
import br.com.dyad.infrastructure.widget.grid.DataGrid;

public class GridLicense extends SynchronizerService {
	
	@Override
	public HashMap getServiceValue(HashMap params) throws Exception {
		HashMap resultMap = new HashMap();
		
		String serverWindowId = (String)params.get("serverWindowId");
		String serverDataGridId = (String)params.get("serverDataGridId");
		Long serverDataGridLicenseId = (Long)params.get("serverDataGridLicenseId");
		
		DataGrid dataGrid = this.getGridToDispatchEvent(serverWindowId, serverDataGridId);
		dataGrid.setLicenseId(serverDataGridLicenseId);
		
		resultMap.put("serverWindowId", serverWindowId );
		resultMap.put("clientSincronizerMap", dataGrid.getWindow().toClientSynchronizer());
		
		return resultMap;
	}

}
