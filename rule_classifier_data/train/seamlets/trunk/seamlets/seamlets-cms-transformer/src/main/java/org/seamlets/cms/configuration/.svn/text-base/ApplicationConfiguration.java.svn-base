package org.seamlets.cms.configuration;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.seamlets.cms.entities.Application;
import org.seamlets.cms.entities.Domain;

import com.sun.faces.config.ConfigurationException;

@Name("applicationConfiguration")
@AutoCreate
@Scope(ScopeType.APPLICATION)
public class ApplicationConfiguration implements Serializable {
	
	@In
	private EntityManager entityManager;
	
	private String	applicationName;
	private Application application;

	private String domainName;
	private Domain domain;
	
	public String getApplicationName() {
		return applicationName;
	}
	
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	
	public Application getApplication() {
		if(application == null) {
			Query query = entityManager.createNamedQuery("application.name");
			query.setParameter("name", applicationName);
			this.application = (Application) query.getSingleResult();
			if(application == null)
				throw new ConfigurationException("No Application was found for name '" + applicationName +"'.");
		}
		return application;
	}
	
	public void setApplication(Application application) {
		this.application = application;
	}
	
	public String getDomainName() {
		return domainName;
	}
	
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	
	public Domain getDomain() {
		if(domain == null) {
			Query query = entityManager.createNamedQuery("domain.name");
			query.setParameter("name", applicationName);
			query.setParameter("rootUrl", domainName);
			this.domain = (Domain) query.getSingleResult();
			if(domain == null)
				throw new ConfigurationException("No Domain was found for rootUrl '" + domainName + "' in Application '" + applicationName +"'.");
		}
		return domain;
	}
	
	public void setDomain(Domain domain) {
		this.domain = domain;
	}
	
}
