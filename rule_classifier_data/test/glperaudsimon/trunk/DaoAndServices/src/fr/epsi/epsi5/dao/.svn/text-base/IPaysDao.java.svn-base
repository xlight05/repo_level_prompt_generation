package fr.epsi.epsi5.dao;

import java.util.Collection;
import java.util.List;

import fr.epsi.epsi5.entities.Pays;

/** 
 * Interface implémentée par PaysDaoImpl.
 * @author Wilhelm PERAUD - Loïc SIMON 
 */
public interface IPaysDao {
	/** 
	 * Méthode permettant de retrouver les pays présents en base.
	 * @return Une collection d'instance de la classe Pays
	 * @see Pays
	 * @see List
	 */
	List<Pays> getPays();
	
	/** 
	 * Méthode permettant d'ajouter un pays.
	 * @param pays Une instance de la classe Pays
	 * @see Pays
	 */
	void addPays(Pays pays);

	/** 
	 * Méthode permettant de mettre à jour un pays.
	 * @param pays Une instance de la classe Pays
	 * @see Pays
	 */
	void updatePays(Pays pays);

	/** 
	 * Méthode permettant de supprimer un pays.
	 * @param idPays L'id du Pays à supprimer
	 * @see Pays
	 */
	void deletePays(int idPays);
	
	/** 
	 * Méthode permettant de rechercher un pays.
	 * @param recherche Un nom de pays partiel ou entier
	 * @return Une collection d'instance de la classe Pays
	 * @see Pays
	 * @see Collection
	 */
	Collection<Pays> searchPays(String recherche);

	/** 
	 * Méthode permettant de retrouver un pays à partir de son ID unique.
	 * @param id L'id unique d'un pays
	 * @return Une instance de la classe Pays
	 * @see Pays
	 */
	Pays loadPays(int id);
	
	/** 
	 * Méthode permettant de compter le nombre de pays dans la base.
	 * @return Le nombre de pays dans la base
	 * @see Pays
	 */
	int countPays();
	
	/** 
	 * Méthode permettant de trouver les doublons de pays.
	 * @param nom Un nom de pays 
	 * @param id L'id unique du pays que l'on modifie. 0 s'il s'agit d'un nouveau pays
	 * @return Un booléen à vrai ou faux suivant que le Pays existe déjà ou non
	 * @see Pays
	 */
	boolean findDuplicatePays(String nom,int id);
}
