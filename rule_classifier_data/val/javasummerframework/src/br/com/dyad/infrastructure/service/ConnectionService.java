package br.com.dyad.infrastructure.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;

import br.com.dyad.client.DyadBaseModel;
import br.com.dyad.client.system.SystemInfo;
import br.com.dyad.infrastructure.annotations.SkipValidation;

@SkipValidation
public class ConnectionService extends DyadService {

	@SuppressWarnings("unchecked")
	@Override
	public HashMap getServiceValue(HashMap params) throws Exception {
		HashMap ret = new HashMap();
		ArrayList<DyadBaseModel> connections = new ArrayList<DyadBaseModel>();				
		File appDir = new File(SystemInfo.getInstance().getApplicationPath()+ "connections");			
		Collection files = FileUtils.listFiles(appDir, new String[]{"cfg"}, false);
		
		for (Object objFile : files) {
			File temp = (File)objFile; 
			int endIndex = temp.getName().length() - 4;
			
			DyadBaseModel model = new DyadBaseModel(); 
			model.set("connection", temp.getName().substring(0, endIndex));  
			connections.add(model);
		}		
		
		ret.put("connections", connections);	
		
		return ret;
	}
	
}
