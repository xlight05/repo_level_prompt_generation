package org.seamlets.cms.admin.contoll.application;

import java.io.Serializable;

import javax.ejb.Local;

import org.seamlets.cms.entities.Application;


@Local
public interface ApplicationController extends Serializable {

	public static final String	PERMISSION_NAME		= "cms.application";

	public static final String	PERMISSION_CREATE	= "create";
	public static final String	PERMISSION_READ		= "read";
	public static final String	PERMISSION_UPDATE	= "update";
	public static final String	PERMISSION_DELETE	= "delete";

	public void create();
	
	public void remove();
	
	public Application getAktiveApplication();

	public void setAktiveApplication(Application aktiveApplication);

	public void updateApplication();

	public void removeApplication();
	
	public void refresh();

}