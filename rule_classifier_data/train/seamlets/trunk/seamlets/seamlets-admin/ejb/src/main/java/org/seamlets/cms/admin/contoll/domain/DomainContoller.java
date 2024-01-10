package org.seamlets.cms.admin.contoll.domain;

import java.io.Serializable;

import javax.ejb.Local;

@Local
public interface DomainContoller extends Serializable {

	public static final String	PERMISSION_NAME		= "cms.domain";

	public static final String	PERMISSION_CREATE	= "create";
	public static final String	PERMISSION_READ		= "read";
	public static final String	PERMISSION_UPDATE	= "update";
	public static final String	PERMISSION_DELETE	= "delete";

	public void updateDomain();

	public void removeDomain();
}
