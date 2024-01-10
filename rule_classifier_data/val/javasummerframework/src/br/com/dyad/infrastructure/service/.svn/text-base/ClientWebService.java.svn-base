package br.com.dyad.infrastructure.service;

import java.util.HashMap;

import br.com.dyad.infrastructure.webservice.client.WebserviceClient;

public class ClientWebService extends DyadService{
	
	@Override
	public HashMap getServiceValue(HashMap params) throws Exception {
		String serviceClass = (String)params.get("webserviceClass");
		Class clazz = Class.forName(serviceClass);
		WebserviceClient client = (WebserviceClient)clazz.newInstance();
		return client.getService(params);
	}

}
