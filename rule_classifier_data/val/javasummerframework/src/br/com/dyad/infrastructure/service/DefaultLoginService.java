package br.com.dyad.infrastructure.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import br.com.dyad.client.AppException;
import br.com.dyad.client.GenericService;
import br.com.dyad.infrastructure.annotations.SkipValidation;
import br.com.dyad.infrastructure.entity.Language;
import br.com.dyad.infrastructure.entity.User;
import br.com.dyad.infrastructure.i18n.Translation;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;

@SkipValidation
public class DefaultLoginService extends DyadService {
	@Override
	public HashMap getServiceValue(HashMap params) throws Exception {				
		
		String classPath = System.getProperty("app.dyad.projectClasspath");
		classPath = classPath.substring(0, classPath.length() - 3 );
		System.out.println("classPath: " + classPath);
		
		File f = null;
		FileReader file = null;
		String linha = null;
		String login = null;
		String password = null;
		String database = null;
		
		try {
			f = new File(classPath + "defaultLogin.conf");
			if (!f.exists()){
				return null;
			}
			file = new FileReader(f);
			BufferedReader leitor = new BufferedReader(file);			
			
			while( (linha = leitor.readLine()) != null ){
				String[]props = linha.split("=");
				if ( props[0].equals("user")){
					login = props[1];
				} else if (props[0].equals("password")){
					password = User.encryptPasswork(props[1]);
				} else if (props[0].equals("database")){
					database = props[1];
				}
			}				 
			file.close();
		} catch (Exception e ){
			e.printStackTrace();
		} finally {
			if ( file != null ){
				file.close();
			}
		}
		
		HashMap map = new HashMap();
		HttpSession session = (HttpSession) params.get(GenericService.GET_REQUEST);
		session.setAttribute(GenericService.GET_DATABASE_FILE, database);
		session.setMaxInactiveInterval(2 * 60 * 60); /* two hours */ 
				
		if (login != null && password != null){											
			Session s = PersistenceUtil.getSession(database);		

			Query qy = s.createQuery("from User where login = '" + login + "'" );
			List list = qy.list();
			if ( list.isEmpty() ){
				throw new AppException("Invalid User!");
			}
			User user = (User)list.get(0);
			if ( user.getLogin().equals(login) && user.getPassword().equals(password)){		
				String sessionId = session.getId();
				map.put(GenericService.GET_DATABASE_FILE, database);
				map.put(GenericService.SESSION_ID, sessionId);
				map.put(GenericService.USER_KEY, user.getId());
				map.put("clientMessages", Translation.loadTokens(getDatabase(), user.getLanguage(), true));
				session.setAttribute(GenericService.USER_LANGUAGE, user.getLanguage());
				session.setAttribute(GenericService.USER_KEY, user.getId());
				session.setAttribute(GenericService.USER_LOGIN, user.getLogin());
				System.out.println("sessionId: " + sessionId);
			} else {
				throw new AppException("User and password are invalid!"); 
			}
			return map;
		} else {			
			throw new AppException("User and password must be informed.");
		}
	}
}
