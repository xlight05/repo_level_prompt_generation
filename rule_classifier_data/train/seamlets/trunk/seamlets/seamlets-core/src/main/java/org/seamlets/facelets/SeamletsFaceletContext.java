package org.seamlets.facelets;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.el.FunctionMapper;
import javax.el.ValueExpression;
import javax.el.VariableMapper;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.FaceletException;
import com.sun.facelets.TemplateClient;
import com.sun.facelets.el.DefaultVariableMapper;
import com.sun.facelets.el.ELAdaptor;


/**
 * CMS FaceletContext implementation.
 * 
 * A single FaceletContext is used for all Facelets involved in an invocation of
 * {@link com.sun.facelets.Facelet#apply(FacesContext, UIComponent)
 * Facelet#apply(FacesContext, UIComponent)}. This means that included Facelets
 * are treated the same as the JSP include directive.
 * 
 * @author Daniel Zwicker
 * @version $Id: CMSFaceletContext.java,v 1.0 $
 */
public class SeamletsFaceletContext extends FaceletContext {

	private final FacesContext			faces;

	private final ELContext				ctx;

	private final SeamletsFacelet			facelet;
	private final List<SeamletsFacelet>		faceletHierarchy;

	private VariableMapper				varMapper;

	private FunctionMapper				fnMapper;

	private final Map<String, Integer>	ids;
	private final Map<Integer, Integer>	prefixes;
	private String						prefix;
	private final StringBuilder			uniqueIdBuilder	= new StringBuilder(30);

	public SeamletsFaceletContext(SeamletsFaceletContext ctx, SeamletsFacelet facelet) {
		this.ctx = ctx.ctx;
		this.clients = ctx.clients;
		this.faces = ctx.faces;
		this.fnMapper = ctx.fnMapper;
		this.ids = ctx.ids;
		this.prefixes = ctx.prefixes;
		this.varMapper = ctx.varMapper;
		this.faceletHierarchy = new ArrayList<SeamletsFacelet>(ctx.faceletHierarchy
				.size() + 1);
		this.faceletHierarchy.addAll(ctx.faceletHierarchy);
		this.faceletHierarchy.add(facelet);
		this.facelet = facelet;
	}

	public SeamletsFaceletContext(FacesContext faces, SeamletsFacelet facelet) {
		this.ctx = ELAdaptor.getELContext(faces);
		this.ids = new HashMap<String, Integer>();
		this.prefixes = new HashMap<Integer, Integer>();
		this.clients = new ArrayList<TemplateManager>(5);
		this.faces = faces;
		this.faceletHierarchy = new ArrayList<SeamletsFacelet>(1);
		this.faceletHierarchy.add(facelet);
		this.facelet = facelet;
		this.varMapper = this.ctx.getVariableMapper();
		if (this.varMapper == null) {
			this.varMapper = new DefaultVariableMapper();
		}
		this.fnMapper = this.ctx.getFunctionMapper();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.facelets.FaceletContext#getFacesContext()
	 */
	@Override
	public FacesContext getFacesContext() {
		return faces;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.facelets.FaceletContext#getExpressionFactory()
	 */
	@Override
	public ExpressionFactory getExpressionFactory() {
		return facelet.getExpressionFactory();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sun.facelets.FaceletContext#setVariableMapper(javax.el.VariableMapper
	 * )
	 */
	@Override
	public void setVariableMapper(VariableMapper varMapper) {
		// Assert.param("varMapper", varMapper);
		this.varMapper = varMapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sun.facelets.FaceletContext#setFunctionMapper(javax.el.FunctionMapper
	 * )
	 */
	@Override
	public void setFunctionMapper(FunctionMapper fnMapper) {
		// Assert.param("fnMapper", fnMapper);
		this.fnMapper = fnMapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sun.facelets.FaceletContext#includeFacelet(javax.faces.component.
	 * UIComponent, java.lang.String)
	 */
	@Override
	public void includeFacelet(UIComponent parent, String relativePath)
			throws IOException, FacesException, ELException {
		facelet.include(this, parent, relativePath);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.el.ELContext#getFunctionMapper()
	 */
	@Override
	public FunctionMapper getFunctionMapper() {
		return fnMapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.el.ELContext#getVariableMapper()
	 */
	@Override
	public VariableMapper getVariableMapper() {
		return varMapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.el.ELContext#getContext(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getContext(Class key) {
		return ctx.getContext(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.el.ELContext#putContext(java.lang.Class, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void putContext(Class key, Object contextObject) {
		ctx.putContext(key, contextObject);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.facelets.FaceletContext#generateUniqueId(java.lang.String)
	 */
	@Override
	public String generateUniqueId(String base) {

		if (prefix == null) {
			StringBuilder builder = new StringBuilder(
					faceletHierarchy.size() * 30);
			for (SeamletsFacelet facelet : faceletHierarchy) {
				builder.append(facelet.getAlias());
			}
			Integer prefixInt = new Integer(builder.toString().hashCode());

			Integer cnt = prefixes.get(prefixInt);
			if (cnt == null) {
				prefixes.put(prefixInt, new Integer(0));
				prefix = prefixInt.toString();
			} else {
				int i = cnt.intValue() + 1;
				prefixes.put(prefixInt, new Integer(i));
				prefix = prefixInt + "_" + i;
			}
		}

		Integer cnt = ids.get(base);
		if (cnt == null) {
			ids.put(base, new Integer(0));
			uniqueIdBuilder.delete(0, uniqueIdBuilder.length());
			uniqueIdBuilder.append(prefix);
			uniqueIdBuilder.append("_");
			uniqueIdBuilder.append(base);
			return uniqueIdBuilder.toString();
		}
		int i = cnt.intValue() + 1;
		ids.put(base, new Integer(i));
		uniqueIdBuilder.delete(0, uniqueIdBuilder.length());
		uniqueIdBuilder.append(prefix);
		uniqueIdBuilder.append("_");
		uniqueIdBuilder.append(base);
		uniqueIdBuilder.append("_");
		uniqueIdBuilder.append(i);
		return uniqueIdBuilder.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.facelets.FaceletContext#getAttribute(java.lang.String)
	 */
	@Override
	public Object getAttribute(String name) {
		if (varMapper != null) {
			ValueExpression ve = varMapper.resolveVariable(name);
			if (ve != null) {
				return ve.getValue(this);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.facelets.FaceletContext#setAttribute(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public void setAttribute(String name, Object value) {
		if (varMapper != null) {
			if (value == null) {
				varMapper.setVariable(name, null);
			} else {
				varMapper.setVariable(name, facelet.getExpressionFactory()
						.createValueExpression(value, Object.class));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sun.facelets.FaceletContext#includeFacelet(javax.faces.component.
	 * UIComponent, java.net.URL)
	 */
	@Override
	public void includeFacelet(UIComponent parent, URL absolutePath)
			throws IOException, FacesException, ELException {
		facelet.include(this, parent, absolutePath);
	}

	@Override
	public ELResolver getELResolver() {
		return ctx.getELResolver();
	}

	private final List<TemplateManager>	clients;

	@Override
	public void popClient(TemplateClient client) {
		if (!clients.isEmpty()) {
			Iterator<TemplateManager> itr = clients.iterator();
			while (itr.hasNext()) {
				if (itr.next().equals(client)) {
					itr.remove();
					return;
				}
			}
		}
		throw new IllegalStateException(client + " not found");
	}

	@Override
	public void pushClient(final TemplateClient client) {
		clients.add(0, new TemplateManager(facelet, client, true));
	}

	@Override
	public void extendClient(final TemplateClient client) {
		clients.add(new TemplateManager(facelet, client, false));
	}

	@Override
	public boolean includeDefinition(UIComponent parent, String name)
			throws IOException, FaceletException, FacesException, ELException {
		boolean found = false;
		TemplateManager client;

		for (int i = 0, size = clients.size(); i < size && !found; i++) {
			client = clients.get(i);
			if (client.equals(facelet))
				continue;
			found = client.apply(this, parent, name);
		}

		return found;
	}

	private final static class TemplateManager implements TemplateClient {

		private final SeamletsFacelet		owner;

		private final TemplateClient	target;

		private final boolean			root;

		private final Set<String>		names	= new HashSet<String>();

		public TemplateManager(SeamletsFacelet owner, TemplateClient target,
				boolean root) {
			this.owner = owner;
			this.target = target;
			this.root = root;
		}

		public boolean apply(FaceletContext ctx, UIComponent parent, String name)
				throws IOException, FacesException, FaceletException,
				ELException {
			String testName = (name != null) ? name : "facelets._NULL_DEF_";
			if (names.contains(testName)) {
				return false;
			}
			names.add(testName);
			boolean found = false;
			found = target.apply(new SeamletsFaceletContext(
					(SeamletsFaceletContext) ctx, owner), parent, name);
			names.remove(testName);
			return found;
		}

		@Override
		public boolean equals(Object o) {
			// System.out.println(owner.getAlias() + " == " +
			// ((DefaultFacelet) o).getAlias());
			return owner == o || target == o;
		}

		public boolean isRoot() {
			return root;
		}
	}

	@Override
	public boolean isPropertyResolved() {
		return ctx.isPropertyResolved();
	}

	@Override
	public void setPropertyResolved(boolean resolved) {
		ctx.setPropertyResolved(resolved);
	}
}
