package br.com.pedro.client;

import java.io.IOException;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.pedro.dao.UserDao;
import br.com.pedro.domain.User;

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
			Properties properties = new Properties();
			properties.put(Context.INITIAL_CONTEXT_FACTORY,
					"org.jnp.interfaces.NamingContextFactory");
			properties.put(Context.URL_PKG_PREFIXES,
					"org.jboss.naming:org.jnp.interfaces");
			properties.put(Context.PROVIDER_URL, "jnp://localhost:1099");
			ctx = new InitialContext(properties);

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
	public UserDao lookupBean() {
		try {

			Object dao = getJndiContext().lookup(
					"ejb3-maven-1.0/UserDaoImpl/remote");
			return (UserDao) dao;
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

		UserDao dao = (UserDao) invoker.lookupBean();

		System.out.println(dao.getUser().getLastName());

		// System.out.println(dao.list());

		// // create
		// User u = new User();
		// u.setFirstName("First name");
		// u.setLastName("Last name");
		// u.setBirthday(new Date());
		// System.out.println(dao.create(u));

		/*
		 * Context context = null; try { Environment env = new Environment(); //
		 * set connection parameters env.setProviderUrl("t3s://localhost:7002");
		 * // The next two set methodes are optional if you are using // a
		 * UserNameMapper interface. env.setSecurityPrincipal("weblogic");
		 * env.setSecurityCredentials("Accenture01"); InputStream key = new
		 * FileInputStream
		 * ("C:\\Oracle\\Middleware\\wlserver_10.3\\server\\lib\\DemoIdentity.jks"
		 * ); InputStream cert = new FileInputStream(
		 * "C:\\Oracle\\Middleware\\wlserver_10.3\\server\\lib\\DemoTrust.jks");
		 * // wrap input streams if key/cert are in pem files key = new
		 * PEMInputStream(key); cert = new PEMInputStream(cert);
		 * env.setSSLClientCertificate(new InputStream[] { key, cert});
		 * env.setInitialContextFactory
		 * (Environment.DEFAULT_INITIAL_CONTEXT_FACTORY); context =
		 * env.getInitialContext(); Object myEJB = (Object)
		 * context.lookup("userdao"); } finally { if (context != null)
		 * context.close(); }
		 */
	}
}