package fr.epsi.epsi5.services;

import java.util.Collection;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import fr.epsi.epsi5.dao.IPaysDao;
import fr.epsi.epsi5.entities.Pays;

/**
 * Implémentation du service d'accès aux Pays.
 * Toutes les méthodes englobent l'accès aux Pays dans des transactions
 * @author Wilhelm Peraud - Loïc Simon
 */
@Transactional
public class PaysServiceImpl implements IPaysService {
	private IPaysDao dao;

	/**
	 * Setter du Dao permettant l'accès aux Pays.
	 * @param dao Dao permettant l'accès aux Pays
	 */
	public void setDao(IPaysDao dao) {
		this.dao = dao;
	}

	/**
	 * Méthode permettant l'ajout d'un Pays.
	 * @param pays Pays à ajouter
	 */
	public void addPays(Pays pays) {
		dao.addPays(pays);
	}

	/**
	 * Méthode permettant de supprimer un pays.
	 * @param idPays Id du Pays à supprimer
	 */
	public void deletePays(int idPays) {
		dao.deletePays(idPays);
	}

	/**
	 * Méthode permettant de chercher les doublons de Pays.
	 * @param libelle Nom du Pays
	 * @param id Id du Pays. Sera à 0 s'il s'agit d'un nouveau pays
	 * @return true si le Pays existe déjà. Sinon, false.
	 */
	public boolean findDuplicatePays(String libelle, int id) {
		return dao.findDuplicatePays(libelle, id);
	}

	/**
	 * Méthode permettant de charger un Pays à partir de son Id.
	 * @param id Id du Pays
	 * @return Pays trouvé
	 */
	public Pays loadPays(int id) {
		return dao.loadPays(id);
	}
	
	/**
	 * Méthode permettant de chercher un pays.
	 * @param recherche Nom complet ou partiel d'un Pays
	 * @return Une collection de Pays
	 */
	public Collection<Pays> searchPays(String recherche) {
		return dao.searchPays(recherche);
	}

	/**
	 * Méthode permettant de mettre à jour un Pays.
	 * @param pays Pays à mettre à jour
	 */
	public void updatePays(Pays pays) {
		dao.updatePays(pays);
	}

	/** 
	 * Compte des Pays dans une transaction.
	 * @return Le nombre de pays dans la base
	 * @see Pays
	 */
	@Override
	public int countPays() {
		return  dao.countPays();
	}

	/** 
	 * Rapatriement des Pays.
	 * @return Une collection d'instance de la classe Pays
	 * @see Pays
	 * @see List
	 */
	@Override
	public List<Pays> getPays() {
		return dao.getPays();
	}
}
