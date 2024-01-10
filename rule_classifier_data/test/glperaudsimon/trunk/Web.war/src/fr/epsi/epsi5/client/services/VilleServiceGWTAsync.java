package fr.epsi.epsi5.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;

import fr.epsi.epsi5.dto.VilleDTO;

/**
 * Interface de l'appel asynchrone au service RPC pour les Villes.
 * @author Wilhelm Peraud - Loïc Simon
 */
public interface VilleServiceGWTAsync {
	/**
	 * Méthode effectuant l'appel Asynchrone à la méthode getVilles.
	 * @param paysId Id du pays dont on cherche les villes
	 * @param callback Appel Asynchrone
	 */
	void getVilles(int paysId, AsyncCallback<VilleDTO[]> callback);
	
	/**
	 * Méthode effectuant l'appel Asynchrone à la méthode addVille.
	 * @param ville Ville à ajouter
	 * @param callback Retour de l'appel asynchrone
	 */
	void addVille(VilleDTO ville, AsyncCallback<DTOException> callback);
	
	/**
	 * Méthode effectuant l'appel à la méthode updateVille.
	 * @param ville Ville à mettre à jour
	 * @param callback Retour de l'appel asynchrone
	 */
	void updateVille(VilleDTO ville, AsyncCallback<DTOException> callback);
	
	/**
	 * Méthode effectuant l'appel à la méthode deleteVille.
	 * @param villeId Id de la ville à supprimer
	 * @param callback Retour de l'appel asynchrone
	 */
	void deleteVille(int villeId, AsyncCallback<DTOException> callback);
	
	/**
	 * Méthode permettant de charger une ville.
	 * @param villeId Id de la ville à charger
	 * @param callback Retour de l'appel asynchrone
	 */
	void loadVille(int villeId, AsyncCallback<VilleDTO> callback);
}
