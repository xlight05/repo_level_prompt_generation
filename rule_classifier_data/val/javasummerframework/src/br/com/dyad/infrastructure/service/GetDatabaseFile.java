package br.com.dyad.infrastructure.service;

import java.io.File;
import java.util.HashMap;
import br.com.dyad.client.system.SystemInfo;
import br.com.dyad.infrastructure.file.upload.DownloadFile;

public class GetDatabaseFile extends DyadService {

	@SuppressWarnings("unchecked")
	@Override
	public HashMap getServiceValue(HashMap params) throws Exception {
		HashMap<String, String> ret = new HashMap<String, String>();
		String appPath = SystemInfo.getInstance().getApplicationPath();
		
		String pictureId = (String)params.get("picture");		
		String picturePath = DownloadFile.getDownloadLink(getDatabase(), Long.parseLong(pictureId));
		picturePath = picturePath.substring(appPath.length()).replace(File.separator, "/");
		ret.put("picture", picturePath);
		
		return ret;
	}
	
}
