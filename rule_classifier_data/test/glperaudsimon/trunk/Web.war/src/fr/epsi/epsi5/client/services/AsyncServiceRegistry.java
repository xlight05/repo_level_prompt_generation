package fr.epsi.epsi5.client.services;

import com.google.gwt.core.client.GWT;

/**
 * AsyncServiceRegistry : Registre des services asynchrones.
 * @author Wilhelm Peraud-Loïc Simon
 */
public class AsyncServiceRegistry {
	private static VilleServiceGWTAsync serviceVilleAsync = (VilleServiceGWTAsync) GWT.create(VilleServiceGWT.class);
	private static PaysServiceGWTAsync paysVilleAsync = (PaysServiceGWTAsync) GWT.create(PaysServiceGWT.class);
	
	/**
	 * Getter du service asynchrone d'accès aux villes.
	 * @return Service Asynchrone d'accès aux villes
	 */
	public static VilleServiceGWTAsync getServiceVilleAsync() {
		return serviceVilleAsync;
	}
	
	/**
	 * Getter du service asynchrone d'accès aux pays.
	 * @return Service Asynchrone d'accès aux pays
	 */
	public static PaysServiceGWTAsync getServicePaysAsync() {
		return paysVilleAsync;
	}
}
