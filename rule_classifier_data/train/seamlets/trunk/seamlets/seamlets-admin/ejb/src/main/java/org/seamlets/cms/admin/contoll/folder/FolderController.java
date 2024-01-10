package org.seamlets.cms.admin.contoll.folder;

import java.io.Serializable;

import javax.ejb.Local;

@Local
public interface FolderController extends Serializable {
	
	public static final String	PERMISSION_NAME		= "cms.folder";

	public static final String	PERMISSION_CREATE	= "create";
	public static final String	PERMISSION_READ		= "read";
	public static final String	PERMISSION_UPDATE	= "update";
	public static final String	PERMISSION_DELETE	= "delete";

	public void updateFolder();

	public void removeFolder();
}
