package br.com.dyad.infrastructure.file.upload;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;
import br.com.dyad.client.system.SystemInfo;
import br.com.dyad.infrastructure.entity.DatabaseFile;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;

public class DownloadFile {
	
	public static String getDownloadLink(String database, Long fileKey){
		List files = PersistenceUtil.executeHql(database, "from DatabaseFile where id = " + fileKey);
		
		for (Object obj : files) {
			DatabaseFile dbFile = (DatabaseFile)obj;			
			File tempDir = new File(SystemInfo.getInstance().getTempDir() + 
					 				File.separator + database + File.separator + 
					 				dbFile.getId());
			tempDir.mkdir();
			File targetFile = new File(tempDir.getPath() + File.separator + dbFile.getFileName());					
			
			try {
				FileUtils.writeByteArrayToFile(targetFile, dbFile.getData());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			
			return targetFile.getAbsolutePath();
		}
		
		return null;
	}
	
	/**
	 * @author Danilo Sampaio
	 * @param database
	 * @param fileName
	 * @return
	 * Retorna o link para download do arquivo que está criado no sistema de arquivo.
	 */
	public static String getDownloadLink(String database, String fileName){						
		File tempDir = new File(SystemInfo.getInstance().getTempDir() + 
				 				File.separator + database + File.separator + 
				 				"downloads");
		tempDir.mkdir();
		File targetFile = new File(tempDir.getPath() + File.separator + fileName);							
		return targetFile.getAbsolutePath();		
	}

}
