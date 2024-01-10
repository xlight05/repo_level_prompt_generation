package fr.epsi.epsi5.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import fr.epsi.epsi5.dto.PaysDTO;

/**
 * Interface du service RPC pour les Pays.
 * @author Wilhelm Peraud - Loïc Simon
 */
@RemoteServiceRelativePath("PaysService.rpc")
public interface PaysServiceGWT extends RemoteService {
	
	/**
	 * Méthode getPays renvoyant la liste des pays.
	 * @return Les pays
	 */
	PaysDTO[] getPays();
		
	/**
	 * Méthode permettant d'ajouter un Pays.
	 * @param pays Pays à ajouter
	 * @return Exception pouvant avoir lieu lors de l'ajout
	 */
	DTOException addPays(PaysDTO pays);
	
	/**
	 * Méthode permettant de mettre à jour un pays.
	 * @param pays Pays à mettre à jour
	 * @return Exception pouvant avoir lieu lors de la mise à jour
	 */
	DTOException updatePays(PaysDTO pays);
	
	/**
	 * Méthode permettant de supprimer un pays.
	 * @param paysId Id du pays à supprimer
	 * @return Exception pouvant avoir lieu lors de la suppression
	 */
	DTOException deletePays(int paysId);
	
	/**
	 * Méthode permettant de charger un pays.
	 * @param paysId Id du pays à charger
	 * @return Pays chargé
	 */
	PaysDTO loadPays(int paysId);
}
