package org.seamlets.resource.home;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;
import org.seamlets.resource.entity.File;
import org.seamlets.resource.tools.BlobTool;

@Name("fileHome")
@AutoCreate
public class FileHome extends EntityHome<File> {

	@In("org.seamlets.blobTool")
	private BlobTool		blobTool;

	@In
	private EntityManager	resourceEntityManager;

	@Override
	public EntityManager getEntityManager() {
		return resourceEntityManager;
	}

	public void setFileId(Long id) {
		setId(id);
	}

	public Long getFileId() {
		return (Long) getId();
	}

	public void setFileData(InputStream in) throws IOException {
		Blob data = blobTool.createBlob(in);
		instance.setData(data);
	}

	public InputStream getFileData() throws SQLException {
		return instance.getData().getBinaryStream();
	}

	@Override
	protected File createInstance() {
		File file = new File();
		return file;
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public File getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
