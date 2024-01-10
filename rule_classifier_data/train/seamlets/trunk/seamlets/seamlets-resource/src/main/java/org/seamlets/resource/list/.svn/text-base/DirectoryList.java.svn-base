package org.seamlets.resource.list;

import java.util.Arrays;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import org.seamlets.resource.entity.Directory;

@Name("directoryList")
public class DirectoryList extends EntityQuery<Directory> {
	
	private static final String		EJBQL			= "select directory from Directory directory";

	private static final String[]	RESTRICTIONS	= {
			"lower(directory.name) like concat(lower(#{directoryList.directory.name}),'%')",
			"directory.parent = #{directoryList.directory.parent}", };
	
	@In
	private EntityManager resourceEntityManager;

	private Directory				directory		= new Directory();

	public DirectoryList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}
	
	@Override
	public EntityManager getEntityManager() {
		return resourceEntityManager;
	}

	public Directory getDirectory() {
		return directory;
	}
}
