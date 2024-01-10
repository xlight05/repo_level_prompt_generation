package org.seamlets.cms.admin.contoll.application;

import java.io.Serializable;

import javax.ejb.Local;

import org.seamlets.cms.entities.Application;

@Local
public interface AddApplicationAction extends Serializable {

	public void create();

	public void remove();

	public void showDialog();

	public void addApplication();

	public void hideDialog();

	public boolean isShow();

	public boolean isStartWorking();

	public void setStartWorking(boolean startWorking);

	public Application getApplication();

	public void setApplication(Application application);

}