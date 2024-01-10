package br.com.dyad.infrastructure.service;

import java.util.HashMap;
import java.util.List;

import br.com.dyad.client.GenericService;
import br.com.dyad.infrastructure.entity.User;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;

public class ConfirmPasswordService extends DyadService {
	
	@Override
	public HashMap getServiceValue(HashMap params) throws Exception {		
		String informedPassword = (String)params.get("password");
		Long userId = (Long)getHttpSession().getAttribute(GenericService.USER_KEY);		
		List list = PersistenceUtil.executeHql(getDatabase(), "from User where id=" + userId);
				
		HashMap<String, Boolean> ret = new HashMap<String, Boolean>();
		ret.put("result", false);
		
		if (list.size() > 0){
			User user = (User)list.get(0);
			ret.put("result", user.getPassword().equals(informedPassword));
		}
		
		return ret;
	}

}
