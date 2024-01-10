package org.seamlets.tags.sl;

import java.io.IOException;
import java.net.URL;

import javax.el.ELException;
import javax.el.VariableMapper;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.FaceletException;
import com.sun.facelets.el.VariableMapperWrapper;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagHandler;

public class StreamHandler extends TagHandler {

	private final TagAttribute	value;

	/**
	 * @param config
	 */
	public StreamHandler(TagConfig config) {
		super(config);
		this.value = this.getRequiredAttribute("value");
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * 
	 * 
	 * @see
	 * com.sun.facelets.FaceletHandler#apply(com.sun.facelets.FaceletContext,
	 * 
	 * javax.faces.component.UIComponent)
	 */
	public void apply(FaceletContext ctx, UIComponent parent) throws IOException, FacesException, FaceletException,
			ELException {
		VariableMapper orig = ctx.getVariableMapper();
		ctx.setVariableMapper(new VariableMapperWrapper(orig));
		try {
			URL absolutePath = (URL) this.value.getObject(ctx, URL.class);
			
			this.nextHandler.apply(ctx, null);
			if(absolutePath != null)
				ctx.includeFacelet(parent, absolutePath);
		} finally {
			ctx.setVariableMapper(orig);
		}
	}

}
