package org.seamlets.cms.admin.content.pagecontent.dnd;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;
import org.richfaces.event.DropEvent;
import org.seamlets.cms.entities.pagecomponent.CMSComponentHelper;
import org.seamlets.cms.entities.pagecomponent.PageContentEntity;
import org.seamlets.cms.entities.viewpart.ViewPart;

@Stateless
@Name("componentDragDrop")
public class ComponentDragDropBean implements ComponentDragDrop {
	
	@Logger
	private Log log;
	
	@PersistenceContext(unitName = "seamlets-components-datasource")
	private EntityManager entityManager;
	
	public void processDrop(DropEvent dropEvent) {
		Object dragValue = dropEvent.getDragValue();
		if (dragValue instanceof CMSComponentHelper) {
			CMSComponentHelper cmsComponentHelper = (CMSComponentHelper) dragValue;
			
			Object dropValue = dropEvent.getDropValue();
			if (dropValue instanceof ViewPart) {
				ViewPart viewPart = (ViewPart) dropValue;
				try {
					PageContentEntity pageContent = cmsComponentHelper.createNewInstance();
					pageContent.setViewPart(viewPart);
					viewPart.getViewPartContent().add(pageContent);
					entityManager.persist(pageContent);
				} catch (InstantiationException e) {
					log.error("Unable to load PageContent", e);
				} catch (IllegalAccessException e) {
					log.error("Unable to load PageContent", e);
				}
				
			}
			else if(dropValue instanceof PageContentEntity){
				System.out.println("Container ADD");
			}
		}
	}

}
