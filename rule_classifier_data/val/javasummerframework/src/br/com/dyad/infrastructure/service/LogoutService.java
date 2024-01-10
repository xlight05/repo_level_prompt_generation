package br.com.dyad.infrastructure.service;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import br.com.dyad.client.GenericService;

public class LogoutService extends DyadService {
	@SuppressWarnings("unchecked")
	@Override
	public HashMap getServiceValue(HashMap params) throws Exception {
		//try {
			String sessionId = (String)params.get(GenericService.SESSION_ID);				 
			
			HashMap map = new HashMap();
			HttpSession session = (HttpSession) params.get(GenericService.GET_REQUEST);		
					
			if (sessionId.equals(session.getId())){						
				session.invalidate();
				map.put(GenericService.SESSION_ID, "");
				return map;
			} else {			
				throw new Exception("User session is invalid.");
			}
		//} catch (Exception e ){
		//	throw ClientServerException.createException(e);
		//}
	}
}
