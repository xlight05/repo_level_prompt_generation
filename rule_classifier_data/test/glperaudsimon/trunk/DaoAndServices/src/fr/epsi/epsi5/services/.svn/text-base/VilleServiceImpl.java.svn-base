package fr.epsi.epsi5.services;

import java.util.Collection;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import fr.epsi.epsi5.dao.IVilleDao;
import fr.epsi.epsi5.dto.PaysDTO;
import fr.epsi.epsi5.entities.Ville;

/**
 * Implémentation du service d'accès aux Villes.
 * Toutes les méthodes englobent l'accès aux Villes dans des transactions
 * @author Wilhelm Peraud - Loïc Simon
 */
@Transactional
public class VilleServiceImpl implements IVilleService {
	private IVilleDao dao;

	/**
	 * Setter du Dao permettant l'accès aux Villes.
	 * @param dao Dao permettant l'accès aux Villes
	 */
	public void setDao(IVilleDao dao) {
		this.dao = dao;
	}
	
	/** 
	 * Méthode permettant d'ajouter une ville.
	 * @param ville Une instance de la classe Ville
	 * @see Ville
	 */
	public void addVille(Ville ville) {
		dao.addVille(ville);
	}
	
	/** 
	 * Méthode permettant de supprimer une ville.
	 * @param idVille Id de la ville à supprimer
	 */
	public void deleteVille(int idVille) {
		dao.deleteVille(idVille);
	}

	/** 
	 * Méthode permettant de trouver les doublons de ville.
	 * @param nom Un nom de ville 
	 * @param id L'id unique de ville que l'on modifie. 0 s'il s'agit d'une nouvelle ville
	 * @return Un booléen à vrai ou faux suivant que la Ville existe déjà ou non
	 * @see Ville
	 */
	public boolean findDuplicateVille(String nom, int id) {
		return dao.findDuplicateVille(nom, id);
	}

	/** 
	 * Méthode permettant de charger une ville.
	 * @param id L'id unique d'une ville
	 * @return Une instance de la classe Ville
	 * @see Ville
	 */
	public Ville loadVille(int id) {
		return dao.loadVille(id);
	}
	
	/** 
	 * Méthode permettant de rechercher une ville.
	 * @param recherche Un nom de ville partiel ou entier
	 * @return Une collection d'instance de la classe Ville
	 * @see Ville
	 * @see Collection
	 */
	public Collection<Ville> searchVille(String recherche) {
		return dao.searchVille(recherche);
	}

	/** 
	 * Méthode permettant de mettre à jour une ville.
	 * @param ville Une instance de la classe Ville
	 * @see Ville
	 */
	public void updateVille(Ville ville) {
		dao.updateVille(ville);
	}

	/** 
	 * Méthode permettant d'entourer le rapatriement des Villes.
	 * d'un Pays dans une transaction
	 * @param paysId L'id unique d'un pays
	 * @return Un ensemble d'instance de la classe Ville
	 * @see PaysDTO
	 * @see List
	 */
	@Override
	public List<Ville> getVilles(int paysId) {					
		return dao.getVilles(paysId);
	}

}
