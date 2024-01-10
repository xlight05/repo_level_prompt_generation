package org.seamlets.cms.deploy.template;

import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.seamlets.cms.configuration.Cmsconfiguration;
import org.seamlets.cms.entities.template.FileTemplate;
import org.seamlets.cms.xsd.template.Template;
import org.seamlets.cms.xsd.template.ViewPart;

@Name("fileTemplateImporter")
public class FileTemplateImporter implements TemplateImporter<FileTemplate> {

	@In
	private EntityManager		entityManager;

	@In
	private Cmsconfiguration	cmsconfiguration;

	private Query				viewIdQuery;

	@SuppressWarnings("unchecked")
	public Class<? extends TemplateImporter> getType() {
		return this.getClass();
	}

	public FileTemplate doImport(Template template, String viewId) {
		viewIdQuery = entityManager.createNamedQuery("templateEntity.viewId");
		viewIdQuery.setParameter("viewId", viewId);
		try {
			FileTemplate entity = (FileTemplate) viewIdQuery.getSingleResult();
			if (cmsconfiguration.isUpdateTemplates()) {
				entity = updateFileTemplateEntity(template, viewId, entity);
			}
			return entity;
		} catch (ClassCastException e) {
			throw new IllegalStateException("Entity with viewId '" + viewId
					+ "' is not of type org.seamlets.cms.admin.content.template.FileTemplate");
		} catch (NoResultException e) {
			return initFileTemplateEntity(template, viewId);
		}

	}

	private FileTemplate updateFileTemplateEntity(Template template, String viewId, FileTemplate entity) {
		entity.setTemplateViewId(viewId);
		entity.setDisplayName(template.getDisplayName());

		List<ViewPart> viewparts = template.getViewparts().getViewpart();
		entity.setViewParts(new HashSet<ViewPart>(viewparts));
		
		entityManager.joinTransaction();
		entityManager.persist(entity);
		return entity;
	}

	private FileTemplate initFileTemplateEntity(Template template, String viewId) {
		FileTemplate entity = new FileTemplate();
		return updateFileTemplateEntity(template, viewId, entity);
	}

}
