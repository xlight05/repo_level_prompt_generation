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
	private final TagAttribute	uri;

	/**
	 * @param config
	 */
	public StreamHandler(TagConfig config) {
		super(config);
		value = this.getAttribute("value");
		uri = this.getAttribute("uri");
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
		if(value == null && uri == null)
			throw new IllegalStateException("StreamHandler need one of the attributes 'value' or 'uri'");
		
		if(value != null && uri != null)
			throw new IllegalStateException("Only one of the attributes 'value' or 'uri' are permitted");
		
		VariableMapper orig = ctx.getVariableMapper();
		ctx.setVariableMapper(new VariableMapperWrapper(orig));
		try {
			URL absolutePath  = null;
			if(value != null)
				absolutePath = (URL) value.getObject(ctx, URL.class);
			else {
				String path = (String) uri.getObject(ctx, String.class);
				absolutePath = getClass().getResource(path); 
			}
			
			this.nextHandler.apply(ctx, null);
			if(absolutePath != null)
				ctx.includeFacelet(parent, absolutePath);
		} finally {
			ctx.setVariableMapper(orig);
		}
	}

}
