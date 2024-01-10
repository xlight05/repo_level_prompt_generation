package br.com.dyad.infrastructure.service.window;

import java.util.HashMap;

import br.com.dyad.client.utilities.SincronizerActionTypes;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.service.SynchronizerService;
import br.com.dyad.infrastructure.widget.Window;

public class ExecuteServerMethod extends SynchronizerService {
	
	@Override
	public HashMap getServiceValue(HashMap params) throws Exception {
		HashMap resultMap = new HashMap();
		
		String serverWindowId = (String)params.get("serverWindowId");
		Window window = getWindowMap().get(serverWindowId);		
		HashMap props = (HashMap)params.get("props");
		if (props != null && props.containsKey("params")){
			ReflectUtil.getMethodReturn(window, (String)params.get(SincronizerActionTypes.ExecuteServerMethod.toString()), new Class[]{Object.class}, new Object[]{props.get("params")});
		} else if (params.get(SincronizerActionTypes.ExecuteServerMethod.toString()) != null) {					
			ReflectUtil.getMethodReturn(window, (String)params.get(SincronizerActionTypes.ExecuteServerMethod.toString()));
		}
		
		resultMap.put("serverWindowId", serverWindowId );
		resultMap.put("clientSincronizerMap", window.toClientSynchronizer() );
		
		return resultMap;
	}

}
