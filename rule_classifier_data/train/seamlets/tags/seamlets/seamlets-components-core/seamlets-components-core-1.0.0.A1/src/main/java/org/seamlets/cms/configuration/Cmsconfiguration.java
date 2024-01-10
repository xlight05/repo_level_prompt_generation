package org.seamlets.cms.configuration;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("cmsconfiguration")
@AutoCreate
@Scope(ScopeType.APPLICATION)
public class Cmsconfiguration implements Serializable {

	private boolean	updateTemplates;

	public boolean isUpdateTemplates() {
		return updateTemplates;
	}

	public void setUpdateTemplates(boolean updateTemplates) {
		this.updateTemplates = updateTemplates;
	}

}
