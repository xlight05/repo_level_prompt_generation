package org.seamlets.page;

import javax.persistence.NoResultException;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Synchronized;
import org.jboss.seam.log.Log;
import org.seamlets.cms.deploy.JAXBClassRepository;
import org.seamlets.cms.entities.PageDefinition;
import org.seamlets.exceptions.ViewIdNotManagedBySeamletsException;
import org.seamlets.list.PageDefinitionList;

@Name("org.seamlets.page.seamletsProvider")
@AutoCreate
@Install(precedence = Install.FRAMEWORK)
@Scope(ScopeType.APPLICATION)
@Synchronized
public class SeamletsAdminProvider implements SeamletsProvider {

	@Logger
	private Log							log;

	@In
	private PageDefinitionList pageDefinitionList;
	
	@Out(required = false, scope = ScopeType.PAGE)
	private PageDefinition	pageDefinition;
	
	public SeamletsPage getPage(String viewID) throws ViewIdNotManagedBySeamletsException {
		try {
			pageDefinitionList.getPageDefinition().setCanonicalViewID(viewID);
			
			pageDefinition = pageDefinitionList.getSingleResult();
			
			if(log.isDebugEnabled())
				log.debug("Locate Seamlet for viewId '#0'", viewID);
			
			return new SeamletsAdminPage(pageDefinition, "seamlets-default");
		}catch (NoResultException e) {
			log.debug("Unable to locate Seamlet for viewId '#0'", viewID, e);
			throw new ViewIdNotManagedBySeamletsException("Unable to locate Seamlet for viewId '" + viewID + "'", e);
		} catch (Exception e) {
			log.warn("Unable to locate Seamlet for viewId '#0'", viewID, e);
			throw new ViewIdNotManagedBySeamletsException("Unable to locate Seamlet for viewId '" + viewID + "'", e);
		}
	}

}
