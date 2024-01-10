package org.seamlets.resource.list;

import java.util.Arrays;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import org.seamlets.resource.entity.File;

@Name("fileList")
public class FileList extends EntityQuery<File> {
	
	private static final String		EJBQL			= "select file from File file";

	private static final String[]	RESTRICTIONS	= {
			"lower(file.name) like concat(lower(#{fileList.file.name}),'%')",
			"lower(file.mimeType) like concat(lower(#{fileList.file.mimeType}),'%')",
			"file.directory = #{fileList.file.directory}", };
	
	@In
	private EntityManager resourceEntityManager;

	private File					file			= new File();

	public FileList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}
	
	@Override
	public EntityManager getEntityManager() {
		return resourceEntityManager;
	}

	public File getFile() {
		return file;
	}
}
