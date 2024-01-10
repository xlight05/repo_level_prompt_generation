package fr.epsi.epsi5.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Entité Ville.
 * @author Wilhelm Peraud-Loïc Simon
 */
@NamedQueries(
		{
			//Requête nommée permettant de trouver des villes à partir d'un nom
			@NamedQuery(
				name="findCityWithName",
				query="from Ville v where upper(v.nom) like concat(upper(:name),'%') " +
						"order by v.pays.nom, v.nom"),
			//Requête nommée permettant de vérifier qu'un nom de ville n'existe pas déjà
			@NamedQuery(
				name="findDuplicateCity",
				query="from Ville v where " +
						"(upper(v.nom) = upper(:name)) and (v.id <> :id)")
		}
	)
@Entity
@Table(name = "Ville")
public class Ville {
	//L'id est générée automatiquement
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private int id;
	
	@Column(name = "NOM")
	private String nom;
	
	//L'attribut pays est issu de la relation
	//entre une ville et un pays par le PAYS_ID
	@ManyToOne
	@JoinColumn(name = "PAYS_ID")
	private Pays pays;
	
	/**
	 * Constructeur de la classe Ville.
	 */
	public Ville() {}
	
	/**
	 * Getter de l'attribut Id.
	 * @return Id de la Ville
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Setter de l'attribut Id.
	 * @param id Id de la Ville
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Getter de l'attribut Nom.
	 * @return Nom dela Ville
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * Setter de l'attribut Nom.
	 * @param nom Nom de la Ville
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	/**
	 * Setter de l'attribut Pays.
	 * @param pays Pays auquel appartient la Ville
	 */
	public void setPays(Pays pays) {
		this.pays = pays;
	}
	
	/**
	 * Getter de l'attribut Pays.
	 * @return Pays auquel appartient la Ville
	 */
	public Pays getPays() {
		return pays;
	}
}
