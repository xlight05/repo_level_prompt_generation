package org.seamlets.cms.admin.content.pagecontent.dnd;

import java.io.Serializable;

import javax.ejb.Local;

import org.richfaces.event.DropListener;

@Local
public interface ComponentDragDrop extends DropListener, Serializable {
	
}
