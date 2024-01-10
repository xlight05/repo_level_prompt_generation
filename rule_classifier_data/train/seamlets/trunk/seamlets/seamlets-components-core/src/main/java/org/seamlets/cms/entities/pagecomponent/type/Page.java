package org.seamlets.cms.entities.pagecomponent.type;

import javax.persistence.Transient;

import org.seamlets.cms.entities.pagecomponent.type.handler.PageHandler;



public interface Page {
	@Transient
	public PageHandler getPageHandler();
}
