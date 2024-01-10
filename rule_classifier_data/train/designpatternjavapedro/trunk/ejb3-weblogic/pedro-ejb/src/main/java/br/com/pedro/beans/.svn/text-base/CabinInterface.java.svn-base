package br.com.pedro.beans;

import javax.ejb.Remote;

import br.com.pedro.domain.Cabin;
import br.com.pedro.domain.Reservation;

@Remote
public interface CabinInterface {
	
	//ejb#br.com.pedro.beans.CabinInterface
	static final String JNDI_NAME = "ejb.fdsfsdfcabin#br.com.pedro.beans.CabinInterface";  
	public Cabin getCabin(Integer id);
	public Cabin createCabin(Cabin cabin);
	public Reservation createReservation(Reservation reservation);
	

}
