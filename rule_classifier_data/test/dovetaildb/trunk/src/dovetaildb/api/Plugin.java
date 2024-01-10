package dovetaildb.api;

import dovetaildb.dbrepository.ParsedRequest;

public interface Plugin {

	public ApiService wrapApiService(ApiService api, ParsedRequest request);
/*
	public ApiService preWrapApiService(ApiService api, ParsedRequest request) {
		return api;
	}
	public boolean allowedUnder(Plugin other) {
		return true;
	}
	public boolean allowedOver(Plugin other) {
		return true;
	}
	*/
}
