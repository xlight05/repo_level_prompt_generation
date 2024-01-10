package lk.apiit.friends.persistence.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import lk.apiit.friends.model.LoginDetails;
import lk.apiit.friends.model.Staff;
import lk.apiit.friends.model.Student;
import lk.apiit.friends.model.User;
import lk.apiit.friends.model.UserRole;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * Schema Generation Support
 * 
 * @author Yohan Liyanage
 * @version 12-Sep-2008
 * @since 12-Sep-2008
 */
public class SchemaSupport {

	/**
	 * Builds SchemaExport instance for the Database.
	 * 
	 * @return SchemaExport
	 * @throws IOException
	 */
	public static SchemaExport buildExport() throws IOException {

		// Schema Exporter
		return new SchemaExport(buildConfiguration());

	}
	
	/**
	 * Builds HibernateConfiguration instance
	 * @return Hibernate Configuration
	 * @throws IOException
	 */
	public static Configuration buildConfiguration() throws IOException {
		
		AnnotationConfiguration cfg = new AnnotationConfiguration();

		// Setup Hibernate Configuration Properties
		Properties db = new Properties();
		
		FileInputStream fis = null;
		try {
			fis = new FileInputStream("src/config.properties"); 
			db.load(fis);
		} finally {
			if (fis!=null) fis.close();
		}
		
		Properties hib = new Properties();
		hib.setProperty("hibernate.connection.url", db.getProperty("db.url"));
		hib.setProperty("hibernate.connection.username", db.getProperty("db.username"));
		hib.setProperty("hibernate.connection.password", db.getProperty("db.password"));
		hib.setProperty("hibernate.connection.driver_class", db.getProperty("db.driverclass"));
		hib.setProperty("hibernate.dialect", db.getProperty("db.jpa.databasePlatform"));

		cfg.setProperties(hib);

		// Map Classes
		cfg.addAnnotatedClass(User.class);
		cfg.addAnnotatedClass(LoginDetails.class);
		cfg.addAnnotatedClass(Student.class);
		cfg.addAnnotatedClass(Staff.class);
		cfg.addAnnotatedClass(UserRole.class);
		
		return cfg;
		
	}
	
	/**
	 * Returns Hibernate Session Factory instance.
	 * @return Hibernate Session Factory
	 * @throws IOException
	 */
	public static SessionFactory buildSessionFactory() throws IOException {
		return buildConfiguration().buildSessionFactory();
	}
}
