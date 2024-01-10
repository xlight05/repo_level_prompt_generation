package br.com.dyad.infrastructure.persistence;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.entity.BaseEntity;



public class DatabaseUpdate {
	private SessionFactory sessionFactory;
	private AnnotationConfiguration conf = new AnnotationConfiguration();
	private Session session;
	private String url;
	private String driverClass;
	private String userName;
	private String password;
	private String dialect;
	private String database;
	private List dados = new ArrayList();
	
	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getUrl() {
		return url;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public AnnotationConfiguration getConf() {
		return conf;
	}

	public void setConf(AnnotationConfiguration conf) {
		this.conf = conf;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	public void connect() throws Exception {
		conf = conf.setProperty("hibernate.connection.url", getUrl());
		conf = conf.setProperty("hibernate.connection.driver_class",
				getDriverClass());
		conf = conf.setProperty("hibernate.connection.username", getUserName());
		conf = conf.setProperty("hibernate.connection.password", getPassword());
		conf = conf.setProperty("hibernate.dialect", getDialect());
		conf = conf.setProperty("hibernate.hbm2ddl.auto", "update");
		conf = conf.setProperty("show_sql", "true");

		List<Class> classes = PersistenceUtil.getAnnotatedClasses(getDatabase());

		for (Class clazz : classes) {
			conf = conf.addAnnotatedClass(clazz);
		}

		sessionFactory = conf.buildSessionFactory();
		session = sessionFactory.openSession();
	}

	@SuppressWarnings("unchecked")
	public void update() throws Exception {

		try {
			List<Class> classes = PersistenceUtil.getAnnotatedClasses(getDatabase());
			Session tempSession = PersistenceUtil.getSession(getDatabase());

			for (Class clazz : classes) {
				System.out.println("CLASSE: " + clazz.getName());

				Query query = tempSession.createQuery("from "
						+ clazz.getName()
						+ " where classId = '"
						+ ReflectUtil.getMethodReturn(BaseEntity.class,
								"getClassIdentifier",
								new Class[] { Class.class },
								new Object[] { clazz }) + "' and id < 0");
				List lista = query.list();
				System.out.println("FIM CLASSE: " + clazz.getName());
				if (lista != null) {
					for (Object object : lista) {
						Object objTemp = clazz.newInstance();
						BeanUtils.copyProperties(objTemp, object);
						dados.add(objTemp);
					}
				}

			}
			tempSession.close();

			Object[] arr = dados.toArray();
			Arrays.sort(arr, new Comparator() {

				public int compare(Object o1, Object o2) {

					Class clazz1 = o1.getClass();
					Class clazz2 = o2.getClass();
					try {
						Method m1 = clazz1.getMethod("getId", new Class[] {});
						Method m2 = clazz2.getMethod("getId", new Class[] {});
						Long id1 = (Long) m1.invoke(o1, new Object[] {});
						Long id2 = (Long) m2.invoke(o2, new Object[] {});

						if (id1 == null || id2 == null) {
							System.out.println(clazz1.getName() + " est� nulo");
						} else {
							return id1.compareTo(id2);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					return 0;
				}

			});

			session.beginTransaction();
			try {
				for (Object object : arr) {
					Class clazz = object.getClass();
					Method m = clazz.getMethod("getId", new Class[] {});
					Long id = (Long) m.invoke(object, new Object[] {});
					Object entity = getEntity(clazz, id);

					if (entity != null) {
						System.out.println(clazz.getName() + "," + id);
						Object oldVersion = BeanUtils.getProperty(entity,
								"version");
						BeanUtils.setProperty(object, "version", oldVersion);

						session.merge(object);
					} else {
						session.save(object);
					}
				}
				session.getTransaction().commit();
			} catch (Exception e) {
				session.getTransaction().rollback();
				throw e;
			}

		} catch (Exception e) {
			throw e;
		}
	}

	public Object getEntity(Class clazz, Long id) {
		Query query = session.createQuery("from " + clazz.getName()
				+ " where id = " + id);
		List lista = query.list();
		return lista.size() > 0 ? lista.get(0) : null;
	}

	public static void main(String[] args) {
		DatabaseUpdate du = new DatabaseUpdate();

		du.setDialect("org.hibernate.dialect.PostgreSQLDialect");
		du.setUserName("postgres");
		du.setPassword("postgres");
		du.setDriverClass("org.postgresql.Driver");
		du.setUrl("jdbc:postgresql://10.85.8.239:5432/INFRA");		

		try {
			du.connect();
			du.update();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
