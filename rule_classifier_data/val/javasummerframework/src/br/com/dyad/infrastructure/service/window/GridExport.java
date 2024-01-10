package br.com.dyad.infrastructure.service.window;

import java.util.HashMap;

import br.com.dyad.infrastructure.persistence.export.DataExport;
import br.com.dyad.infrastructure.service.SynchronizerService;
import br.com.dyad.infrastructure.widget.grid.DataGrid;

public class GridExport extends SynchronizerService {
	@Override
	public HashMap getServiceValue(HashMap params) throws Exception {
		HashMap resultMap = new HashMap();
		
		String serverWindowId = (String)params.get("serverWindowId");
		String serverDataGridId = (String)params.get("serverDataGridId");
		String format = (String)params.get("format");
		
		DataGrid dataGrid = this.getGridToDispatchEvent(serverWindowId, serverDataGridId);

		String fileUrl = dataGrid.dispatchExport(format);

		System.out.println("Executou o export da grid no servidor!");
		resultMap.put("serverWindowId", serverWindowId );
		HashMap clientSynchronizer = dataGrid.getWindow().toClientSynchronizer();
		clientSynchronizer.put("fileUrl", fileUrl );
		resultMap.put("clientSincronizerMap", clientSynchronizer);
		
		return resultMap;
	}
}