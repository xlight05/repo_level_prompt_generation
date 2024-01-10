package br.com.dyad.infrastructure.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import br.com.dyad.client.GenericService;
import br.com.dyad.infrastructure.entity.Language;
import br.com.dyad.infrastructure.i18n.Translation;

@SuppressWarnings("unused")
public abstract class DyadService {
	
	private HttpSession httpSession;
			
	public HttpSession getHttpSession() {
		return httpSession;
	}

	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}
	
	public String getDatabase() {
		return (String)httpSession.getAttribute(GenericService.GET_DATABASE_FILE);
	}
	
	public String translate(String code){
		Language language = (Language)getHttpSession().getAttribute(GenericService.USER_LANGUAGE);
		
		if (getHttpSession() != null && language != null){
			return Translation.get(getDatabase(), language, code);
		} else {
			return code;
		}
		
	}

	@SuppressWarnings("unchecked")
	public HashMap getServiceValue(HashMap params) throws Exception {				
		return null;
	}

	public static String stackTraceToString(Exception error){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		error.printStackTrace(pw);		
		error.printStackTrace();
		return sw.toString();			
	}
}
