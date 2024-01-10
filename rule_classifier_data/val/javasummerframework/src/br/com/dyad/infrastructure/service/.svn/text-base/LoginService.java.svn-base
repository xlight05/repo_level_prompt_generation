package br.com.dyad.infrastructure.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.dyad.client.AppException;
import br.com.dyad.client.GenericService;
import br.com.dyad.infrastructure.annotations.SkipValidation;
import br.com.dyad.infrastructure.entity.User;
import br.com.dyad.infrastructure.i18n.Translation;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;

@SkipValidation
public class LoginService extends DyadService {
	@SuppressWarnings({ "unchecked", "static-access" })
	@Override
	public HashMap getServiceValue(HashMap params) throws Exception {
		//try {
			String login = (String)params.get("login");
			String password = (String)params.get("password");		 
			
			HashMap map = new HashMap();
			HttpSession session = (HttpSession) params.get(GenericService.GET_REQUEST);
			session.setAttribute(GenericService.GET_DATABASE_FILE, params.get(GenericService.GET_DATABASE_FILE));
			session.setMaxInactiveInterval(2 * 60 * 60); /* two hours */
					
			if (login != null && password != null){											
				Session s = PersistenceUtil.getSession((String)params.get(GenericService.GET_DATABASE_FILE));		
	
				Query qy = s.createQuery("from User where login = '" + login + "'" );
				List list = qy.list();
				if ( list.isEmpty() ){
					throw new AppException("Invalid User!");
				}
				User user = (User)list.get(0);
				if ( user.getLogin().equals(login) && user.getPassword().equals(password)){		
					String sessionId = session.getId();
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
		//} catch (Exception e){
		//	throw ClientServerException.createException(e);
		//}
	}

}