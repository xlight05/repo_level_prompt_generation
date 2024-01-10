package br.com.pedro.domain;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name = "Cabin")
public class Cabin implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String nome;
	private int deckLevel;
	private int shipId;
	private int bedCount;
	private List<Reservation> reservations;

	@Id
	@Column(name = "cabin_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getDeckLevel() {
		return deckLevel;
	}

	public void setDeckLevel(int deckLevel) {
		this.deckLevel = deckLevel;
	}

	public int getShipId() {
		return shipId;
	}

	public void setShipId(int shipId) {
		this.shipId = shipId;
	}

	public int getBedCount() {
		return bedCount;
	}

	public void setBedCount(int bedCount) {
		this.bedCount = bedCount;
	}

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "CABIN_RESERVATION", joinColumns = { @JoinColumn(name = "cabin_id") }, inverseJoinColumns = { @JoinColumn(name = "resevertation_id") })
	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

}
