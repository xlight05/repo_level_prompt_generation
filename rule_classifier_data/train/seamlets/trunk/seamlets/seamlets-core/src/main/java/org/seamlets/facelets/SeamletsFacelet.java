package org.seamlets.facelets;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.URL;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.el.ELException;
import javax.el.ExpressionFactory;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.jboss.seam.log.Log;
import org.jboss.seam.log.Logging;

import com.sun.facelets.Facelet;
import com.sun.facelets.FaceletContext;
import com.sun.facelets.FaceletException;
import com.sun.facelets.FaceletHandler;
import com.sun.facelets.impl.DefaultFaceletFactory;
import com.sun.facelets.tag.jsf.ComponentSupport;

/**
 * CMS Facelet implementation.
 * 
 * @author Daniel Zwicker Hookom
 * @version $Id: CMSFacelet.java,v 1.0 $
 */
public class SeamletsFacelet extends Facelet {

	private final static Log				log			= Logging.getLog(SeamletsFacelet.class);

	private final static String				APPLIED_KEY	= "com.sun.facelets.APPLIED";

	private final String					alias;

	private final ExpressionFactory			elFactory;

	private final SeamletsFaceletFactory	factory;

	private final long						createTime;

	private final long						refreshPeriod;

	private final FaceletHandler			root;

	private final URL						src;

	public SeamletsFacelet(SeamletsFaceletFactory factory, ExpressionFactory el, URL src, String alias,
			FaceletHandler root) {
		this.factory = factory;
		this.elFactory = el;
		this.src = src;
		this.root = root;
		this.alias = alias;
		this.createTime = System.currentTimeMillis();
		this.refreshPeriod = 0;
	}

	/**
	 * @see com.sun.facelets.Facelet#apply(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent)
	 */
	@Override
	public void apply(FacesContext facesContext, UIComponent parent) throws IOException, FacesException,
			FaceletException, ELException {
		SeamletsFaceletContext ctx = new SeamletsFaceletContext(facesContext, this);
		refresh(parent);
		ComponentSupport.markForDeletion(parent);
		root.apply(ctx, parent);
		ComponentSupport.finalizeForDeletion(parent);
		markApplied(parent);
	}

	private final void refresh(UIComponent c) {
		if (refreshPeriod > 0) {

			// finally remove any children marked as deleted
			int sz = c.getChildCount();
			if (sz > 0) {
				UIComponent cc = null;
				List<UIComponent> cl = c.getChildren();
				ApplyToken token;
				while (--sz >= 0) {
					cc = cl.get(sz);
					if (!cc.isTransient()) {
						token = (ApplyToken) cc.getAttributes().get(APPLIED_KEY);
						if (token != null && token.time < this.createTime && token.alias.equals(this.alias)) {
							if (log.isInfoEnabled()) {
								DateFormat df = DateFormat.getTimeInstance();
								log.info("Facelet[#0] was modified @ #1, flushing component applied @ #2", this.alias,
										df.format(new Date(this.createTime)), df.format(new Date(token.time)));
							}
							cl.remove(sz);
						}
					}
				}
			}

			// remove any facets marked as deleted
			if (c.getFacets().size() > 0) {
				Collection<UIComponent> col = c.getFacets().values();
				UIComponent fc;
				ApplyToken token;
				for (Iterator<UIComponent> itr = col.iterator(); itr.hasNext();) {
					fc = itr.next();
					if (!fc.isTransient()) {
						token = (ApplyToken) fc.getAttributes().get(APPLIED_KEY);
						if (token != null && token.time < this.createTime && token.alias.equals(this.alias)) {
							if (log.isInfoEnabled()) {
								DateFormat df = DateFormat.getTimeInstance();
								log.info("Facelet[#0] was modified @ #1, flushing component applied @ #2", this.alias,
										df.format(new Date(this.createTime)), df.format(new Date(token.time)));
							}
							itr.remove();
						}
					}
				}
			}
		}
	}

	private final void markApplied(UIComponent parent) {
		if (this.refreshPeriod > 0) {
			Iterator<UIComponent> itr = parent.getFacetsAndChildren();
			UIComponent c;
			Map<String, Object> attr;
			ApplyToken token = new ApplyToken(this.alias, System.currentTimeMillis() + this.refreshPeriod);
			while (itr.hasNext()) {
				c = itr.next();
				if (!c.isTransient()) {
					attr = c.getAttributes();
					if (!attr.containsKey(APPLIED_KEY)) {
						attr.put(APPLIED_KEY, token);
					}
				}
			}
		}
	}

	/**
	 * Return the alias name for error messages and logging
	 * 
	 * @return alias name
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * Return this Facelet's ExpressionFactory instance
	 * 
	 * @return internal ExpressionFactory instance
	 */
	public ExpressionFactory getExpressionFactory() {
		return elFactory;
	}

	/**
	 * The time when this Facelet was created, NOT the URL source code
	 * 
	 * @return final timestamp of when this Facelet was created
	 */
	public long getCreateTime() {
		return createTime;
	}

	/**
	 * The URL this Facelet was created from.
	 * 
	 * @return the URL this Facelet was created from
	 */
	public URL getSource() {
		return src;
	}

	/**
	 * Given the passed FaceletContext, apply our child FaceletHandlers to the
	 * passed parent
	 * 
	 * @see FaceletHandler#apply(FaceletContext, UIComponent)
	 * @param ctx
	 *            the FaceletContext to use for applying our FaceletHandlers
	 * @param parent
	 *            the parent component to apply changes to
	 * @throws IOException
	 * @throws FacesException
	 * @throws FaceletException
	 * @throws ELException
	 */
	private void include(SeamletsFaceletContext ctx, UIComponent parent) throws IOException, FacesException,
			FaceletException, ELException {
		refresh(parent);
		root.apply(new SeamletsFaceletContext(ctx, this), parent);
		markApplied(parent);
	}

	/**
	 * Used for delegation by the DefaultFaceletContext. First pulls the URL
	 * from {@link #getRelativePath(String) getRelativePath(String)}, then calls
	 * {@link #include(FaceletContext, UIComponent, URL) include(FaceletContext,
	 * UIComponent, URL)}.
	 * 
	 * @see FaceletContext#includeFacelet(UIComponent, String)
	 * @param ctx
	 *            FaceletContext to pass to the included Facelet
	 * @param parent
	 *            UIComponent to apply changes to
	 * @param path
	 *            relative path to the desired Facelet from the FaceletContext
	 * @throws IOException
	 * @throws FacesException
	 * @throws FaceletException
	 * @throws ELException
	 */
	public void include(SeamletsFaceletContext ctx, UIComponent parent, String viewId) throws IOException,
			FacesException, FaceletException, ELException {
		SeamletsFacelet facelet = (SeamletsFacelet) factory.getFacelet(viewId);
		facelet.include(ctx, parent);
	}

	/**
	 * Grabs a DefaultFacelet from referenced DefaultFaceletFacotry
	 * 
	 * @see DefaultFaceletFactory#getFacelet(URL)
	 * @param ctx
	 *            FaceletContext to pass to the included Facelet
	 * @param parent
	 *            UIComponent to apply changes to
	 * @param url
	 *            URL source to include Facelet from
	 * @throws IOException
	 * @throws FacesException
	 * @throws FaceletException
	 * @throws ELException
	 */
	public void include(SeamletsFaceletContext ctx, UIComponent parent, URL url) throws IOException, FacesException,
			FaceletException, ELException {
		include(ctx, parent, url.toString());
	}

	private static class ApplyToken implements Externalizable {

		public String	alias;

		public long		time;

		public ApplyToken() {
		}

		public ApplyToken(String alias, long time) {
			this.alias = alias;
			this.time = time;
		}

		public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
			alias = in.readUTF();
			time = in.readLong();
		}

		public void writeExternal(ObjectOutput out) throws IOException {
			out.writeUTF(alias);
			out.writeLong(time);
		}
	}

	@Override
	public String toString() {
		return alias;
	}
}
