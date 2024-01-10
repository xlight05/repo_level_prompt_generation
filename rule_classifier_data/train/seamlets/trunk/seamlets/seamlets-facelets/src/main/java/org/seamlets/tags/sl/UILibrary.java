package org.seamlets.tags.sl;

import com.sun.facelets.tag.AbstractTagLibrary;

/**
 * @author Daniel Zwicker
 */
public final class UILibrary extends AbstractTagLibrary {

	public final static String		Namespace	= "http://seamlets.org/jsf/facelets";

	public final static UILibrary	Instance	= new UILibrary();

	public UILibrary() {
        super(Namespace);

        addTagHandler("stream", StreamHandler.class);
        addComponent("listnavigation", "de.webarts.cmslets.ui.listnavigation.UIListNavigationRenderer", 
        		"org.seamlets.tags.sl.listnavigation.UIListNavigationRenderer");
    }
}
