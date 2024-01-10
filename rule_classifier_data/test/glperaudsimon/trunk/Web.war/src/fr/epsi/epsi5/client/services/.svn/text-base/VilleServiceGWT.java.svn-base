package fr.epsi.epsi5.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import fr.epsi.epsi5.dto.VilleDTO;

/**
 * Interface du service RPC pour les Villes.
 * @author Wilhelm Peraud - Loïc Simon
 */
@RemoteServiceRelativePath("VilleService.rpc")
public interface VilleServiceGWT extends RemoteService {
	
	/**
	 * Méthode getVilles renvoyant la liste des villes liées à un Pays.
	 * @param paysId Id du pays dont on cherche les villes
	 * @return Les villes du pays
	 */
	VilleDTO[] getVilles(int paysId);
	
	/**
	 * Méthode permettant d'ajouter une ville.
	 * @param ville Ville à ajouter
	 * @return Exception pouvant avoir eu lieu lors de l'ajout
	 */
	DTOException addVille(VilleDTO ville);
	
	/**
	 * Méthode permettant de mettre à jour une ville.
	 * @param ville Ville à mettre à jour
	 * @return Exception pouvant avoir lieu lors de la mise à jour
	 */
	DTOException updateVille(VilleDTO ville);
	
	/**
	 * Méthode permettant de supprimer une ville.
	 * @param villeId Id de la ville à supprimer
	 * @return Exception pouvant avoir lieu lors de la suppression
	 */
	DTOException deleteVille(int villeId);
	
	/**
	 * Méthode permettant de charger une ville.
	 * @param villeId Id de la ville à charger
	 * @return Ville chargée
	 */
	VilleDTO loadVille(int villeId);
}
