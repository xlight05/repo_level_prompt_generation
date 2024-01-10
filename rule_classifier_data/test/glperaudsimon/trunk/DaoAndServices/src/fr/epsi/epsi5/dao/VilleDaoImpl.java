package fr.epsi.epsi5.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.jpa.support.JpaDaoSupport;

import fr.epsi.epsi5.entities.Pays;
import fr.epsi.epsi5.entities.Ville;

/** 
 * Classe d'accès aux données pour les villes. 
 * @author Wilhelm PERAUD - Loïc SIMON 
 */
public class VilleDaoImpl extends JpaDaoSupport implements IVilleDao {

	/** 
	 * Méthode permettant d'ajouter une ville.
	 * @param ville Une instance de la classe Ville
	 * @see Ville
	 */
	public void addVille(Ville ville) {
		getJpaTemplate().persist(ville);
	}
	
	/** 
	 * Méthode permettant de supprimer une ville.
	 * @param idVille Id de la ville à supprimer
	 * @see Ville
	 */
	public void deleteVille(int idVille) {
		Ville villeBase = loadVille(idVille);
		if(villeBase != null) {
			getJpaTemplate().remove(villeBase);
		}
	}

	/** 
	 * Méthode permettant de retrouver les villes d'un pays.
	 * @param paysId L'id unique d'un pays
	 * @return Un ensemble d'instance de la classe Ville
	 * @see Pays
	 * @see List
	 */
	public List<Ville> getVilles(int paysId) {
		Pays unpays = getJpaTemplate().find(Pays.class, Integer.valueOf(paysId));
		if(unpays != null) {
			return unpays.getVilles();
		} else {
			return null;
		}
	}

	/** 
	 * Méthode permettant de charger une ville.
	 * @param id L'id unique d'une ville
	 * @return Une instance de la classe Ville
	 * @see Ville
	 */
	public Ville loadVille(int id) {
		Ville ville = getJpaTemplate().find(Ville.class, Integer.valueOf(id));
		return ville;
	}

	/** 
	 * Méthode permettant de mettre à jour une ville.
	 * @param ville Une instance de la classe Ville
	 * @see Ville
	 */
	public void updateVille(Ville ville) {
		getJpaTemplate().merge(ville);
	}
	
	/** 
	 * Méthode permettant de rechercher une ville.
	 * @param recherche Un nom de ville partiel ou entier
	 * @return Une collection d'instance de la classe Ville
	 * @see Ville
	 * @see Collection
	 */
	@SuppressWarnings("unchecked")
	public Collection<Ville> searchVille(String recherche) {
		//On crée la Map qui va contenir les paramètres
		Map<String,String> params = new HashMap<String,String>();
		//On passe le paramètre de recherche
		params.put("name", recherche);
		//On récupère le résultat et on le retourne
		return getJpaTemplate().findByNamedQueryAndNamedParams("findCityWithName", params);
	}

	/** 
	 * Méthode permettant de trouver les doublons de ville.
	 * @param nom Un nom de ville 
	 * @param id L'id unique de ville que l'on modifie. 0 s'il s'agit d'une nouvelle ville
	 * @return Un booléen à vrai ou faux suivant que la Ville existe déjà ou non
	 * @see Ville
	 */
	public boolean findDuplicateVille(String nom, int id) {
		//On crée la Map qui va contenir les paramètres
		Map<String,Object> params = new HashMap<String,Object>();
		//On rajoute les paramètres
		params.put("name", nom);
		params.put("id", id);
		
		//Si jamais la liste de résultat contient
		//un enregistrement, alors ce nom existe déjà
		//en base.
		if(getJpaTemplate().findByNamedQueryAndNamedParams("findDuplicateCity", params).size() > 0) {
			return true;
		} else {
			return false;
		}
	}
}
