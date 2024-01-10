package org.seamlets.page;

import java.io.Serializable;

import javax.ejb.Remove;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.seamlets.cms.entities.Application;
import org.seamlets.cms.entities.Domain;
import org.seamlets.cms.entities.NavigationFolder;
import org.seamlets.cms.entities.PageDefinition;


@Name("pageTemplateProvider")
@AutoCreate
public class PageTemplateProvider implements Serializable {

	public enum PAGETEMPLATE {
		NON_SELECTED_TEMPLATE("/detailtemplates/nonSelectedTemplate.xhtml"), APPLICATION_SELECTED_TEMPLATE(
				"/detailtemplates/applicationSelectedTemplate.xhtml"), DOMAIN_SELECTED_TEMPLATE(
				"/detailtemplates/domainSelectedTemplate.xhtml"), FOLDER_SELECTED_TEMPLATE(
				"/detailtemplates/folderSelectedTemplate.xhtml"), PAGE_SELECTED_TEMPLATE(
				"/detailtemplates/pageSelectedTemplate.xhtml");

		private String	viewId;

		private PAGETEMPLATE(String viewId) {
			this.viewId = viewId;
		}

		public String getViewId() {
			return viewId;
		}

		public void setViewId(String viewId) {
			this.viewId = viewId;
		}
	}

	@In(required = false)
	private Object	selectedNode;

	@Create
	public void create() {
	}

	@Remove
	@Destroy
	public void remove() {
	}

	public PAGETEMPLATE getAdminGuiPageTemplate() {
		if (selectedNode == null) {
			return PAGETEMPLATE.NON_SELECTED_TEMPLATE;
		} else if (selectedNode instanceof Application) {
			return PAGETEMPLATE.APPLICATION_SELECTED_TEMPLATE;
		} else if (selectedNode instanceof Domain) {
			return PAGETEMPLATE.DOMAIN_SELECTED_TEMPLATE;
		} else if (selectedNode instanceof NavigationFolder) {
			return PAGETEMPLATE.FOLDER_SELECTED_TEMPLATE;
		} else if (selectedNode instanceof PageDefinition) {
			return PAGETEMPLATE.PAGE_SELECTED_TEMPLATE;
		} else {
			throw new IllegalStateException("Unsupportet NavigationItem");
		}
	}
}
