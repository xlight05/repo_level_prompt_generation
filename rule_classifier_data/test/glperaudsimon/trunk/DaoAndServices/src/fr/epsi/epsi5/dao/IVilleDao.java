package fr.epsi.epsi5.dao;

import java.util.Collection;
import java.util.List;

import fr.epsi.epsi5.entities.Pays;
import fr.epsi.epsi5.entities.Ville;

/** 
 * Interface implémentée par VilleDaoImpl. 
 * @author Wilhelm PERAUD - Loïc SIMON 
 */
public interface IVilleDao {
	/** 
	 * Méthode permettant de retrouver les villes d'un pays.
	 * @param paysId L'id unique d'un pays
	 * @return Un ensemble d'instance de la classe Ville
	 * @see Pays
	 * @see List
	 */
	List<Ville> getVilles(int paysId);
	
	/** 
	 * Méthode permettant d'ajouter une ville.
	 * @param ville Une instance de la classe Ville
	 * @see Ville
	 */
	void addVille(Ville ville);
	
	/** 
	 * Méthode permettant de mettre à jour une ville.
	 * @param ville Une instance de la classe Ville
	 * @see Ville
	 */
	void updateVille(Ville ville);
	
	/** 
	 * Méthode permettant de supprimer une ville.
	 * @param idVille Id de la ville à supprimer
	 */
	void deleteVille(int idVille);
	
	/** 
	 * Méthode permettant de charger une ville.
	 * @param id L'id unique d'une ville
	 * @return Une instance de la classe Ville
	 * @see Ville
	 */
	Ville loadVille(int id);
	
	/** 
	 * Méthode permettant de rechercher une ville.
	 * @param recherche Un nom de ville partiel ou entier
	 * @return Une collection d'instance de la classe Ville
	 * @see Ville
	 * @see Collection
	 */
	Collection<Ville> searchVille(String recherche);
	
	/** 
	 * Méthode permettant de trouver les doublons de ville.
	 * @param nom Un nom de ville 
	 * @param id L'id unique de ville que l'on modifie. 0 s'il s'agit d'une nouvelle ville
	 * @return Un booléen à vrai ou faux suivant que la Ville existe déjà ou non
	 * @see Ville
	 */
	boolean findDuplicateVille(String nom, int id);
}
