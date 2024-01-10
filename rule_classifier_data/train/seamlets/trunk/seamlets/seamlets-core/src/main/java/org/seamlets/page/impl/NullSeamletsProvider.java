package org.seamlets.page.impl;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;
import org.seamlets.exceptions.ViewIdNotManagedBySeamletsException;
import org.seamlets.page.SeamletsPage;
import org.seamlets.page.SeamletsProvider;


@Name("org.seamlets.page.seamletsProvider")
@AutoCreate
@Install(precedence = Install.BUILT_IN)
public class NullSeamletsProvider implements SeamletsProvider {
	
	@Logger
	private Log log;
	
	@Create
	public void create() {
		log.warn("No SeamletsProvider deployed!");
	}

	public SeamletsPage getPage(String viewID) throws ViewIdNotManagedBySeamletsException {
		throw new ViewIdNotManagedBySeamletsException("No SeamletsProvider deployed");
	}

}
