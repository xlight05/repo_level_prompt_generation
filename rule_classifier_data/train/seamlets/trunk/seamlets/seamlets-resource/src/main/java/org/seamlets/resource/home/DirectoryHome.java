package org.seamlets.resource.home;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;
import org.seamlets.resource.entity.Directory;

@Name("directoryHome")
@AutoCreate
public class DirectoryHome extends EntityHome<Directory> {
	
	@In
	private EntityManager resourceEntityManager;
	
	@Override
	public EntityManager getEntityManager() {
		return resourceEntityManager;
	}

	public void setDirectoryId(Long id) {
		setId(id);
	}

	public Long getDirectoryId() {
		return (Long) getId();
	}

	@Override
	protected Directory createInstance() {
		Directory directory = new Directory();
		return directory;
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public Directory getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
