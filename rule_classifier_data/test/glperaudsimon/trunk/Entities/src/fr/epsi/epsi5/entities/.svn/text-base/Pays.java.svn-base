package fr.epsi.epsi5.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * Entité Pays.
 * @author Wilhelm Peraud-Loïc Simon
 */
@NamedQueries(
	{
		//Requête nommée permettant de trouver tous les pays
		@NamedQuery(
			name="findAllCountries",
			query="from Pays order by nom"),
		//Requête nommée permettant de calculer le nombre de pays
		@NamedQuery(
			name="countriesCount",
			query="select count(*) as nbrPays from Pays"),
		//Requête nommée permettant de trouver des pays à partir d'un nom
		@NamedQuery(
			name="findCountryWithName",
			query="from Pays p where upper(p.nom) like concat(upper(:name),'%') " +
					"order by p.nom ASC"),
		//Requête nommée permettant de vérifier qu'un nom de pays n'existe pas déjà
		@NamedQuery(
			name="findDuplicateCountry",
			query="from Pays p where (upper(p.nom) = upper(:name)) and id <> :id")
	}
)
@Entity
@Table(name="Pays")
public class Pays {
	@Id
	@GeneratedValue
	@Column(name="ID")
	private int id;
	
	@Column(name="NOM")
	private String nom;
	
	/** L'ensemble des villes du pays est retrouvé à l'aide
	* de l'attribut pays de la classe Ville.
	* Si un pays est supprimé, ses villes le sont aussi **/
	@OneToMany(
		mappedBy = "pays",
		cascade={CascadeType.REMOVE},
		fetch=FetchType.EAGER)
	@OrderBy("nom") //Les départements sont ordonnés par nom
	private List<Ville> villes = new ArrayList<Ville>();
	
	/**
	 * Constructeur de la classe Pays.
	 */
	public Pays() {}

	/**
	 * Getter de l'attribut Id.
	 * @return Id du Pays
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter de l'attribut Id.
	 * @param id Id du Pays
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/** Getter de l'attribut Nom.
	 * @return Getter de l'attribut Nom du Pays
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Setter de l'attribut Nom.
	 * @param nom Nom du Pays
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	/**
	 * Setter de la liste des Villes du Pays.
	 * @return Ensemble des villes du Pays
	 */
	public List<Ville> getVilles() {
		return villes;
	}
}
