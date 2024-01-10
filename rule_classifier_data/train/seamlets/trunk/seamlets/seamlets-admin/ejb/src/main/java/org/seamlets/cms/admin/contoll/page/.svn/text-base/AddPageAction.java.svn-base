package org.seamlets.cms.admin.contoll.page;

import java.io.Serializable;

import javax.ejb.Local;

import org.seamlets.cms.entities.PageDefinition;


@Local
public interface AddPageAction extends Serializable {

	public void create();

	public void remove();

	public boolean isDisabled();

	public void showDialog();

	public void addPage();

	public void hideDialog();

	public boolean isShow();

	public PageDefinition getPageDefinition();

	public void setPageDefinition(PageDefinition pageDefinition);
	
	public boolean isValid();

}