package fr.epsi.epsi5.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;

import fr.epsi.epsi5.dto.PaysDTO;

/**
 * Interface de l'appel asynchrone au service RPC pour les Pays.
 * @author Wilhelm Peraud - Loïc Simon
 */
public interface PaysServiceGWTAsync {
	/**
	 * Méthode effectuant l'appel Asynchrone à la méthode GetPays.
	 * @param callback Appel Asynchrone
	 */
	void getPays(AsyncCallback<PaysDTO[]> callback);
		
	/**
	 * Méthode effectuant l'appel Asynchrone à la méthode. 
	 * @param pays Pays à ajouter
	 * @param callback Appel Asynchrone
	 */
	void addPays(PaysDTO pays, AsyncCallback<DTOException> callback);
	
	/**
	 * Méthode effectuant l'appel à la méthode updatePays.
	 * @param pays Pays à mettre à jour
	 * @param callback Retour de l'appel asynchrone
	 */
	void updatePays(PaysDTO pays, AsyncCallback<DTOException> callback);
	
	/**
	 * Méthode effectuant l'appel à la méthode deletePays.
	 * @param paysId Id du pays à supprimer
	 * @param callback Retour de l'appel asynchrone
	 */
	void deletePays(int paysId, AsyncCallback<DTOException> callback);
	
	/**
	 * Méthode permettant de charger un pays.
	 * @param paysId Id du pays à charger
	 * @param callback Retour de l'appel asynchrone
	 */
	void loadPays(int paysId, AsyncCallback<PaysDTO> callback);
}
