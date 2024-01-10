package br.com.pedro.beans.impl;


import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import br.com.pedro.beans.CabinInterface;
import br.com.pedro.beans.CustomerInterface;
import br.com.pedro.domain.Cabin;
import br.com.pedro.domain.Reservation;
import br.com.pedro.domain.User;

@Stateless(name="ejb.cabin",mappedName="ejb.cabin")
@EJB(name="ejb.customer",beanInterface=CustomerInterface.class)
public class CabinImplStateless implements CabinInterface {

/*	@PersistenceContext(unitName = "titan")
	EntityManager em;*/
	
	@Resource private EJBContext session;

	@PersistenceContext(unitName="zaperdb")
	private EntityManager em;
	
	
	public Cabin getCabin(Integer ID) {
	CustomerInterface cu=	(CustomerInterface) session.lookup("ejb.customer");
	em.find(User.class, new Long("1"));
		//return em.find(Cabin.class, ID);
		return null;
	}

	public Cabin createCabin(Cabin cabin) {
		//em.persist(cabin);
		// TODO Auto-generated method stub
		return null;
	}

	public Reservation createReservation(Reservation reservation) {
	//	em.persist(reservation);

		return null;
	}

}
