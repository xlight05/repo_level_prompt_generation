package org.seamlets.cms.components.richeditor;

import java.io.Serializable;
import java.util.Locale;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("editorController")
@Scope(ScopeType.CONVERSATION)
public class EditorControllerBean implements Serializable {

	@In
	private Locale	locale;

	private String	value		= "Some value....";

	private String	viewMode	= "visual";

	private boolean	useSeamText	= false;

	public String getLanguage() {
		return locale.getLanguage();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isUseSeamText() {
		return useSeamText;
	}

	public void setUseSeamText(boolean useSeamText) {
		this.useSeamText = useSeamText;
	}
	
	public String getViewMode() {
		return viewMode;
	}

	
	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

}
