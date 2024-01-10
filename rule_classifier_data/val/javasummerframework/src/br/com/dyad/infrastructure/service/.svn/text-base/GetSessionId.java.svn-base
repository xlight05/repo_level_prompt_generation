package br.com.dyad.infrastructure.service;

import java.util.HashMap;import br.com.dyad.client.GenericService;
import br.com.dyad.infrastructure.annotations.SkipValidation;
import br.com.dyad.infrastructure.entity.Language;
import br.com.dyad.infrastructure.i18n.Translation;

@SkipValidation
public class GetSessionId extends DyadService {

	@Override
	public HashMap getServiceValue(HashMap params) throws Exception {				
		if (getHttpSession().getAttribute(GenericServiceImpl.USER_KEY) != null){
			params.clear();
			
			params.put(GenericService.SESSION_ID, getHttpSession().getId());
			params.put(GenericService.USER_LOGIN, getHttpSession().getAttribute(GenericService.USER_LOGIN));
			params.put(GenericService.USER_KEY, getHttpSession().getAttribute(GenericService.USER_KEY));
			params.put(GenericService.GET_DATABASE_FILE, getHttpSession().getAttribute(GenericService.GET_DATABASE_FILE));
			params.put("clientMessages", Translation.loadTokens(getDatabase(), (Language)getHttpSession().getAttribute(GenericService.USER_LANGUAGE), true));
			return params;
		} else {
			HashMap map = new HashMap();
			return map;
		}
	}
	
}
