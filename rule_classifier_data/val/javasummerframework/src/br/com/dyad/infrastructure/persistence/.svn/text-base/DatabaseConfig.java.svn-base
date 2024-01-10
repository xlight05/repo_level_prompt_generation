package br.com.dyad.infrastructure.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class DatabaseConfig {
	
	private SessionFactory sessionFactory;
	private AnnotationConfiguration conf;
	private String configName; 
	private static Session session;
	
	public DatabaseConfig(String configName) {
		this.configName = configName;
		this.conf = new AnnotationConfiguration();
	}
	public String getConfigName() {
		return configName;
	}
	public void setConfigName(String configName) {
		this.configName = configName;
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
	public static Session getSession() {
		return session;
	}
	public static void setSession(Session session) {
		DatabaseConfig.session = session;
	}		

}
