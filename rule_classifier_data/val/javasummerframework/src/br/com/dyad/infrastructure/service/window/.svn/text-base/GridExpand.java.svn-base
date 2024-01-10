package br.com.dyad.infrastructure.service.window;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import br.com.dyad.client.DyadBaseTreeModel;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.service.SynchronizerService;
import br.com.dyad.infrastructure.widget.grid.TreeGrid;

public class GridExpand extends SynchronizerService {
	
	@Override
	public HashMap getServiceValue(HashMap params) throws Exception {
		HashMap resultMap = new HashMap();
		
		String serverWindowId = (String)params.get("serverWindowId");
		String serverDataGridId = (String)params.get("serverDataGridId");
		String beanName = (String)params.get("beanName");
		DyadBaseTreeModel parent = (DyadBaseTreeModel)params.get("parent");
		Long id = (Long)parent.get("id");
		
		TreeGrid treeGrid = (TreeGrid)this.getGridToDispatchEvent(serverWindowId, serverDataGridId);

		if ( ! treeGrid.getDataList().findId( id ) ){
			throw new Exception( "Id " + id + " was not found in the TreeGrid list." );
		}
		
		BaseEntity baseEntity = (BaseEntity)treeGrid.getDataList().getOne("id", id);			
		List<BaseEntity> list = treeGrid.dispatchExpand(beanName, baseEntity);
		
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			BaseEntity entity = (BaseEntity) iterator.next();
			if ( ! treeGrid.getDataList().findId(entity.getId()) ){
				treeGrid.getDataList().add(entity);
			}
		}
		
		List<DyadBaseTreeModel> children = treeGrid.getModelLisFromBaseEntityList(list);

		System.out.println("Executou o expand da grid no servidor!");
		resultMap.put("serverWindowId", serverWindowId );
		resultMap.put("clientSincronizerMap", treeGrid.getWindow().toClientSynchronizer() );
		resultMap.put("resultExpand", children);
		
		return resultMap;
	}

}