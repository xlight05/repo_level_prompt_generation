package edu.spbsu.eshop.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Util class for working with Hibernate
 */
public class HibernateUtil {

    private static EntityManagerFactory factory;

    static {
	factory = Persistence.createEntityManagerFactory("main");
    }

    public static EntityManager getEntityManager() {
	return factory.createEntityManager();
    }

    public static void close() {
	factory.close();
    }
}
