package br.com.dyad.infrastructure.service;

import java.io.File;
import java.util.HashMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import br.com.dyad.client.ClientServerException;
import br.com.dyad.client.GenericService;
import br.com.dyad.client.RestoreService;
import br.com.dyad.client.system.SystemInfo;
import br.com.dyad.infrastructure.navigation.admin.backup.JdbcBackup;
import br.com.dyad.infrastructure.navigation.admin.backup.JdbcImport;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class RestoreServiceImpl extends RemoteServiceServlet implements RestoreService {

	@Override
	public Boolean login(String user, String password) {
		final String USER_ID = "user";
		final String PASSWORD_ID = "password";		
		boolean userOk = false, passwordOk = false;
				
		try {						
			File cfg = new File(SystemInfo.getInstance().getApplicationPath() + "restore.cfg");
			if (!cfg.exists()){
				throw new RuntimeException("Configuration file not found!");
			}
			String content = FileUtils.readFileToString(cfg);
			String[] lines = StringUtils.split(content, "\n");
			
			for (String item : lines) {
				String temp = StringUtils.substringBefore(item, "=");
				String value = DigestUtils.md5Hex(StringUtils.substringAfter(item, "=").trim());
				if (temp.equals(USER_ID)){
					userOk = value.equals(user);
				} else if (temp.equals(PASSWORD_ID)){
					passwordOk = value.equals(password);
				}
			}
			
			return userOk && passwordOk;
		} catch (Exception e) {
			ClientServerException.createException(e, DyadService.stackTraceToString(e));
		}
		return null;
	}

	@Override
	public void executeRestore(HashMap<String, String> params) {
		try {
			JdbcBackup jdbcBackup = new JdbcBackup(params.get(GenericService.GET_DATABASE_FILE));
			
			jdbcBackup.setDatabase(params.get("database"));
			jdbcBackup.connect();
			
			
			JdbcImport jdbcImport = new JdbcImport(jdbcBackup.getDatabaseConfig().getConf(), jdbcBackup.getDatabaseConfig().getSessionFactory().openSession());
			jdbcImport.importFile(params.get("backupFile"), params.get("filePassword"));
		} catch (Exception e) {
			ClientServerException.createException(e, DyadService.stackTraceToString(e));
		}		
	}

}
