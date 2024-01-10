package org.seamlets.facelets;

import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.context.FacesContext;

import com.sun.facelets.FaceletFactory;
import com.sun.facelets.FaceletViewHandler;
import com.sun.facelets.compiler.Compiler;
import com.sun.facelets.compiler.XSLTCompiler;
import com.sun.facelets.impl.DefaultResourceResolver;
import com.sun.facelets.impl.ResourceResolver;
import com.sun.facelets.util.ReflectionUtil;

/**
 * CMS FaceletViewHandler implementation.
 * 
 * @author Daniel Zwicker
 * @version $Id: SeamletsViewHandler.java,v 1.0 $
 */

public class SeamletsViewHandler extends FaceletViewHandler {

	public SeamletsViewHandler(ViewHandler parent) {
		super(parent);
	}

	@Override
	protected FaceletFactory createFaceletFactory(Compiler compiler) {

		FacesContext ctx = FacesContext.getCurrentInstance();

		// TODO Beispiel für Verarbeitung von Parametern
		// String derParameter =
		// ctx.getExternalContext().getInitParameter("parametername");

		// resource resolver
		ResourceResolver resolver = new DefaultResourceResolver();
		String resolverName = ctx.getExternalContext().getInitParameter(PARAM_RESOURCE_RESOLVER);
		if (resolverName != null && resolverName.length() > 0) {
			try {
				resolver = (ResourceResolver) ReflectionUtil.forName(resolverName).newInstance();
			} catch (Exception e) {
				throw new FacesException("Error Initializing ResourceResolver[" + resolverName + "]", e);
			}
		}

		return new SeamletsFaceletFactory(0, (XSLTCompiler) compiler, resolver);
	}

	@Override
	protected Compiler createCompiler() {
		return new XSLTCompiler();
	}
}
