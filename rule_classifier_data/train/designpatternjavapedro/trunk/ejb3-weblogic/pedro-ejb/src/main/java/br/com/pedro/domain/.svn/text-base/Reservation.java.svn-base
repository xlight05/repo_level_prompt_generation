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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Reservation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "RESERVATION_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String name;

	/**
	 * Mapeamento BI-Direcional N-N entre Reservations e Custumers , o lado
	 * possuidor do relacionamento eh Reservations. Por exemplo se quiser
	 * remover um Custumer de reservation, vc deve remover do list de custumers
	 * de reservations. Se nao sera replicado na base a mudanca
	 */
	// X
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "RESEVERTION_CUSTOMER", joinColumns = { @JoinColumn(name = "RESERVATION_ID") }, inverseJoinColumns = { @JoinColumn(name = "CUSTOMER_ID") })
	private List<Customer> customers;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Customer> getCustomers() {
		if(customers==null){
			customers=new ArrayList<Customer>();
		}
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

}
