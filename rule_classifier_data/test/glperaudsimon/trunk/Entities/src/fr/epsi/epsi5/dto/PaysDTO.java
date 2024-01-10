package fr.epsi.epsi5.dto;

import java.io.Serializable;

/**
 * Entité Pays.
 * @author Wilhelm Peraud-Loïc Simon
 */
public class PaysDTO implements Serializable {
	/**
	 * Default Serial ID.
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	
	private String nom;
	
	/**
	 * Constructeur de la classe Pays.
	 */
	public PaysDTO() {}

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
}
