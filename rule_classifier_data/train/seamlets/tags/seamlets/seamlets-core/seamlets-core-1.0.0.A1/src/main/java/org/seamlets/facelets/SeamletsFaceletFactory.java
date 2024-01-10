package org.seamlets.facelets;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import javax.el.ELException;
import javax.faces.FacesException;

import org.jboss.seam.Component;
import org.jboss.seam.log.Log;
import org.jboss.seam.log.Logging;
import org.seamlets.cache.SeamletsFacletsCache;
import org.seamlets.exceptions.ViewIdNotManagedBySeamletsException;
import org.seamlets.page.SeamletsPage;
import org.seamlets.page.SeamletsProvider;
import org.seamlets.page.impl.NullSeamletsProvider;

import com.sun.facelets.Facelet;
import com.sun.facelets.FaceletException;
import com.sun.facelets.FaceletFactory;
import com.sun.facelets.FaceletHandler;
import com.sun.facelets.compiler.XSLTCompiler;
import com.sun.facelets.impl.ResourceResolver;
import com.sun.facelets.util.ParameterCheck;

/**
 * Seamlets FaceletFactory implementation.
 * 
 * @author Daniel Zwicker
 * @version $Id: SeamletsFaceletFactory.java,v 1.0 $
 */
public class SeamletsFaceletFactory extends FaceletFactory {

	/**
	 * Logger for this class
	 */
	private final static Log		log	= Logging.getLog(SeamletsFaceletFactory.class);

	private final SeamletsFacletsCache	faceletsCache;

	private final SeamletsProvider		seamletsProvider;

	private final ResourceResolver		resolver;

	private final XSLTCompiler			compiler;

	private final URL					baseUrl;

	private String						relativepath;

	public SeamletsFaceletFactory(@SuppressWarnings("unused") long refreshPeriod, XSLTCompiler compiler,
			ResourceResolver resolver) {
		this.compiler = compiler;
		this.resolver = resolver;
		this.baseUrl = resolver.resolveUrl("/");
		this.relativepath = "";
		this.faceletsCache = (SeamletsFacletsCache) Component.getInstance("org.seamlets.cache.faceletsCache");
		this.seamletsProvider = (SeamletsProvider) Component.getInstance("org.seamlets.page.seamletsProvider");
		if (this.seamletsProvider instanceof NullSeamletsProvider)
			log.warn("No SeamletsProvider deployed! Please provide one in your components.xml or deploy a jar with an implemention.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * WARNUNG: Der Facelet-Cache, der hier verwendet wird, ist sehr ungesund
	 * (einfache HashMap) daher jetzt einfach mal kurzgeschlossen. Caching
	 * können wir gerne später noch einbauen!
	 * 
	 * @see com.sun.facelets.FaceletFactory#getFacelet(java.lang.String)
	 */
	@Override
	public Facelet getFacelet(String viewId) throws IOException, FaceletException, FacesException, ELException {
		ParameterCheck.notNull("viewId", viewId);

		SeamletsFacelet facelet = faceletsCache.load(viewId);

		if (facelet == null) {
			facelet = createFacelet(viewId);
			faceletsCache.cache(viewId, facelet);
		}

		return facelet;
	}

	/**
	 * Uses the internal Compiler reference to build a Facelet given the passed
	 * URL.
	 * 
	 * @param url
	 *            source
	 * @return a Facelet instance
	 * @throws IOException
	 * @throws FaceletException
	 * @throws FacesException
	 * @throws ELException
	 */
	private SeamletsFacelet createFacelet(String viewId) throws IOException, FaceletException, FacesException,
			ELException {
		if (log.isDebugEnabled()) {
			log.debug("Creating Facelet for: #0", viewId);
		}
		if (viewId.contains("/")) {
			this.relativepath = viewId.substring(0, viewId.lastIndexOf("/"));
		}
		String alias = viewId;

		try {
			SeamletsPage dseamletsPage = seamletsProvider.getPage(alias);
			FaceletHandler c = this.compiler.doXsltCompile(alias, dseamletsPage);
			SeamletsFacelet fc = new SeamletsFacelet(this, this.compiler.createExpressionFactory(), this.baseUrl,
					alias, c);
			return fc;
		} catch (ViewIdNotManagedBySeamletsException e) {
			try {
				URL url = this.resolveURL(this.baseUrl, viewId);
				if (!viewId.contains(this.relativepath) && !this.relativepath.equals("")) {
					url = this.resolveURL(this.baseUrl, this.relativepath + "/" + viewId);
				}
				alias = "/" + url.getFile().replaceFirst(this.baseUrl.getFile(), "");
				FaceletHandler h = this.compiler.doCompile(url, alias);
				SeamletsFacelet f = new SeamletsFacelet(this, this.compiler.createExpressionFactory(), url, alias, h);
				return f;
			} catch (FileNotFoundException fnfe) {
				if (log.isDebugEnabled()) {
					log.warn("#0 not found at #1", alias, viewId);
				}
				throw new ViewIdNotManagedBySeamletsException("Facelet Not Found: " + viewId, fnfe);
			}
		}

	}

	/**
	 * Resolves a path based on the passed URL. If the path starts with '/',
	 * then resolve the path against
	 * {@link javax.faces.context.ExternalContext#getResource(java.lang.String)
	 * javax.faces.context.ExternalContext#getResource(java.lang.String)}.
	 * Otherwise create a new URL via
	 * {@link URL#URL(java.net.URL, java.lang.String) URL(URL, String)}.
	 * 
	 * @param source
	 *            base to resolve from
	 * @param path
	 *            relative path to the source
	 * @return resolved URL
	 * @throws IOException
	 */
	public URL resolveURL(URL source, String path) throws IOException {
		if (path.startsWith("/")) {
			URL url = this.resolver.resolveUrl(path);
			if (url == null) {
				throw new FileNotFoundException(path + " Not Found in ExternalContext as a Resource");
			}
			return url;
		}
		return new URL(source, path);
	}

}
