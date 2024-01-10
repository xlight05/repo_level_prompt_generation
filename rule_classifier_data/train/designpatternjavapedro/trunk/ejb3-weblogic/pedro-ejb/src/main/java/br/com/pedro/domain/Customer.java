package br.com.pedro.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CUSTOMER_ID")
	private Integer id;
	private String nome;

	@ManyToMany(mappedBy = "customers")
	private List<Reservation> reservations;

	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "TELEPHONE_ID")
	private List<Telephones> telephones;

	public List<Telephones> getTelephones() {
		if (telephones == null) {
			telephones = new ArrayList<Telephones>();
		}
		return telephones;
	}

	public void setTelephones(List<Telephones> telephones) {
		this.telephones = telephones;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

}
