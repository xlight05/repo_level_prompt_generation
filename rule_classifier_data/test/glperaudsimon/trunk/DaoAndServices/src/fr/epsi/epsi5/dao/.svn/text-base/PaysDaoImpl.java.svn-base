package fr.epsi.epsi5.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.orm.jpa.support.JpaDaoSupport;

import fr.epsi.epsi5.entities.Pays;

/** 
 * Classe d'accès aux données pour les pays .
 * @author Wilhelm PERAUD - Loïc SIMON 
 */
public class PaysDaoImpl extends JpaDaoSupport implements IPaysDao {
	/** 
	 * Méthode permettant d'ajouter un pays.
	 * @param pays Une instance de la classe Pays
	 * @see Pays
	 */
	public void addPays(Pays pays) {
		getJpaTemplate().persist(pays);
	}

	/** 
	 * Méthode permettant de supprimer un pays.
	 * @param idPays L'id du pays à supprimer
	 */
	public void deletePays(int idPays) {
		Pays paysBase = loadPays(idPays);
		if(paysBase != null) {
			getJpaTemplate().remove(paysBase);
		}
	}

	/** 
	 * Méthode permettant de retrouver les pays présents en base.
	 * @return Une collection d'instance de la classe Pays
	 * @see Pays
	 * @see List
	 */
	@SuppressWarnings("unchecked")
	public List<Pays> getPays() {
		//On instancie une requête à partir de la requête
		//nommée dans la classe Pays
		try {
			return getJpaTemplate().findByNamedQuery("findAllCountries");
		} catch(Exception e) {
			return null;
		}
	}

	/** 
	 * Méthode permettant de retrouver un pays à partir de son ID unique.
	 * @param id L'id unique d'un pays
	 * @return Une instance de la classe Pays
	 * @see Pays
	 */
	public Pays loadPays(int id) {
		Pays pays = getJpaTemplate().find(Pays.class, id);
		return pays;
	}

	/** 
	 * Méthode permettant de mettre à jour un pays.
	 * @param pays Une instance de la classe Pays
	 * @see Pays
	 */
	public void updatePays(Pays pays) {
		getJpaTemplate().merge(pays);
	}
	
	/** 
	 * Méthode permettant de rechercher un pays.
	 * @param recherche Un nom de pays partiel ou entier
	 * @return Une collection d'instance de la classe Pays
	 * @see Pays
	 * @see Collection
	 */
	@SuppressWarnings("unchecked")
	public Collection<Pays> searchPays(String recherche) {
		//On crée la Map qui va contenir les paramètres
		Map<String,String> params = new HashMap<String,String>();
		//On met le paramètre de recherche
		params.put("name", recherche);
		//
		return getJpaTemplate().findByNamedQueryAndNamedParams("findCountryWithName", params);
	}
	
	/** 
	 * Méthode permettant de compter le nombre de pays dans la base.
	 * @return Le nombre de pays dans la base
	 * @see Pays
	 */
	public int countPays() {
		/* On instancie une requête à partir de la requête
		 * nommée dans la classe Pays
		 */
		Query q = getJpaTemplate().getEntityManager().createNamedQuery("countriesCount");
		/* On récupère le résultat en prenant la première ligne de
		 * la liste d'enregistrements retournés.
		 */
		Long nbrPays = (Long)q.getSingleResult();
		return nbrPays.intValue();
	}
	
	/** 
	 * Méthode permettant de trouver les doublons de pays.
	 * @param nom Un nom de pays 
	 * @param id L'id unique du pays que l'on modifie. 0 s'il s'agit d'un nouveau pays
	 * @return Un booléen à vrai ou faux suivant que le Pays existe déjà ou non
	 * @see Pays
	 */
	public boolean findDuplicatePays(String nom,int id) {
		//On crée la Map qui va contenir les paramètres
		Map<String,Object> params = new HashMap<String,Object>();
		//On rajoute les paramètres
		params.put("name", nom);
		params.put("id",id);
		/* Si jamais la liste de résultat contient
		 * un enregistrement, alors ce nom existe déjà
		 * en base.
		 */
		if(getJpaTemplate().findByNamedQueryAndNamedParams("findDuplicateCountry", params).size() > 0) {
			return true;
		} else {
			return false;
		}
	}
}