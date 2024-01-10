package br.com.pedro.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.pedro.beans.CabinInterface;
import br.com.pedro.beans.CustomerInterface;
import br.com.pedro.dao.UserDao;
import br.com.pedro.domain.Cabin;
import br.com.pedro.domain.Customer;
import br.com.pedro.domain.Reservation;
import br.com.pedro.domain.Telephones;
import br.com.pedro.domain.User;
import br.com.tst.services.SVNServices;

public class JndiInvoker {

	/**
	 * default name "localhost". Could be IP address or domain name too.
	 */
	private String server = "localhost";

	/**
	 * The method retrieves JNDI naming context using populated server instance
	 * variable. If no server has been specified, the default "localhost" is
	 * used.
	 * <p>
	 * 
	 * @return the JNDI context
	 */
	public Context getJndiContext() {
		/*
		 * System.setProperty("java.protocol.handler.pkgs", "weblogic.net");
		 * System.setProperty("weblogic.security.TrustKeyStore","CustomTrust");
		 * System.setProperty("weblogic.security.CustomTrustKeyStoreFileName",
		 * "aoki.kls");
		 * System.setProperty("weblogic.security.CustomTrustKeyStorePassPhrase"
		 * ,"aoki06");
		 * System.setProperty("weblogic.security.CustomTrustKeyStoreType"
		 * ,"kls");
		 */

		Context ctx;
		try {
			Hashtable<String, String> h = new Hashtable<String, String>(7);
			
			h.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
			h.put(Context.PROVIDER_URL, "t3://localhost:7001");
			h.put(Context.SECURITY_PRINCIPAL, "weblogic");
			h.put(Context.SECURITY_CREDENTIALS,"Accenture01");
			ctx = new InitialContext(h);

		} catch (NamingException e) {
			throw new RuntimeException(e);
		}

		return ctx;
	}

	/**
	 * This method returns a remote BaseDao from JNDI. Following assumptions are
	 * made:
	 * <ul>
	 * <li>DAO interface is remote</li>
	 * <li>It follows naming convention of remote interface <code>SomeDao</code>
	 * with implementation <code>SomeDaoImpl</code></li>
	 * </ul>
	 * 
	 * @param daoClass
	 * @return the concrete BaseDao instance
	 */
	public CabinInterface lookupBeanCabin() {
		try {

			Object dao = getJndiContext().lookup(
					"ejb.cabin#br.com.pedro.beans.CabinInterface");
			return (CabinInterface) dao;
		} catch (NamingException ne) {
			throw new RuntimeException(ne);
		}
	}
	public SVNServices lookupSVNServices() {
		try {

			Object dao = getJndiContext().lookup(
					"svnservice#br.com.tst.services.SVNServices");
			return (SVNServices) dao;
		} catch (NamingException ne) {
			throw new RuntimeException(ne);
		}
	}
	public CustomerInterface lookupBeanCustomer() {
		try {
			Object dao = getJndiContext().lookup(
					
					"ejb#br.com.pedro.beans.CabinInterface");
			return (CustomerInterface) dao;
		} catch (NamingException ne) {
			throw new RuntimeException(ne);
		}
	}

	public String getServer() {
		return server;
	}

	/**
	 * Use this method to override the default JNDI server of "localhost". E.g.
	 * the IP address "192.168.16.183" or domain name
	 * <p>
	 * 
	 * @param server
	 *            the new server name
	 */
	public void setServer(String server) {
		this.server = server;
	}

	/**
	 * Example usage
	 * 
	 * @throws IOException
	 * @throws NamingException
	 */
	public static void main(String[] args) throws IOException, NamingException {

		JndiInvoker invoker = new JndiInvoker();

		/*
		 * CabinInterface dao = (CabinInterface) invoker.lookupBean(); Cabin
		 * cabin = new Cabin(); cabin.setNome("Cabine1"); Customer user = new
		 * Customer(); user.setNome("Pedro Daniel Aoki"); Reservation res1 = new
		 * Reservation();
		 * 
		 * res1.getCustomers().add(user);
		 * 
		 * res1.setName("Reserva Europa"); dao.createReservation(res1);
		 * 
		 * 
		 * Reservation res2 = new Reservation();
		 * 
		 * res2.setName("Reserva Canada"); List<Reservation> reservas = new
		 * ArrayList<Reservation>(); reservas.add(res1); reservas.add(res2);
		 * cabin.setReservations(reservas); dao.createCabin(cabin);
		 */

/*		CabinInterface cabin= invoker.lookupBeanCabin();
		cabin.getCabin(new Integer("12"));
		Customer customer = new Customer();
		customer.setNome("aoki");
		Telephones t1 = new Telephones();
		t1.setNumero("19-96064731");
		customer.getTelephones().add(t1);*/

		//customerInterface.createCustumer(customer);
		
		
		
		SVNServices svn = invoker.lookupSVNServices();
		System.out.println(svn.getSVNLog(new Integer("1")));
		

	}

}