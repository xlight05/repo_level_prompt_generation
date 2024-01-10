package org.seamlets.interceptor.cookie;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jboss.seam.Component;
import org.jboss.seam.CyclicDependencyException;
import org.jboss.seam.annotations.intercept.AroundInvoke;
import org.jboss.seam.annotations.intercept.Interceptor;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.core.Conversation;
import org.jboss.seam.intercept.AbstractInterceptor;
import org.jboss.seam.intercept.InvocationContext;
import org.jboss.seam.log.Log;
import org.jboss.seam.log.Logging;
import org.jboss.seam.security.Identity;
import org.jboss.seam.util.Exceptions;
import org.seamlets.annotation.CookieValue;
import org.seamlets.annotation.LoadCookies;
import org.seamlets.annotation.SaveCookies;
import org.seamlets.deploy.Cookies;
import org.seamlets.deploy.Cookies.CookieField;

/**
 * Before invoking the component, inject all cookie dependencies. After
 * invoking, outject cookie dependencies back to client.
 * 
 * @author Daniel Zwicker
 */
@Interceptor
public class CookieInterceptor extends AbstractInterceptor {

	private static final Log					log		= Logging.getLog(CookieInterceptor.class);

	private boolean								injected;

	private boolean								injecting;

	private ReentrantLock						lock	= new ReentrantLock();

	private Map<Class<?>, List<CookieField>>	cookieAttributes;

	@PostConstruct
	public void postConstruct(@SuppressWarnings("unused") InvocationContext invocation) {
		Cookies cookies = (Cookies) Component.getInstance("org.seamlets.deploy.Cookies");
		cookieAttributes = cookies.getCookieAttributes();
	}

	@AroundInvoke
	public Object aroundInvoke(InvocationContext invocation) throws Exception {
		try {
			if (invocation.getMethod().isAnnotationPresent(LoadCookies.class)) {
				lock.lock();
				try {
					if (!injected) {
						if (injecting) {
							throw new CyclicDependencyException();
						}

						injecting = true;
						try {
							inject(invocation.getTarget());
						} finally {
							injecting = false;
						}
						injected = true;
					}

				} finally {
					lock.unlock();
				}
			}

			Object result = invocation.proceed();

			if (invocation.getMethod().isAnnotationPresent(SaveCookies.class)) {
				lock.lock();
				try {
					outject(invocation.getTarget());
				} finally {
					lock.unlock();
				}
			}

			return result;
		} catch (Exception e) {
			Exception root = e;
			while (Exceptions.getCause(root) != null) {
				root = Exceptions.getCause(root);
			}
			if (root instanceof CyclicDependencyException) {
				CyclicDependencyException cyclicDependencyException = (CyclicDependencyException) root;
				cyclicDependencyException.addInvocation(getComponent().getName(), invocation.getMethod());
			}
			throw e;
		}
	}

	public boolean isInterceptorEnabled() {
		return true;
	}

	/**
	 * Inject context variable values into @In attributes of a component
	 * instance.
	 * 
	 * @param bean
	 *            a Seam component instance
	 * @param enforceRequired
	 *            should we enforce required=true?
	 */
	public void inject(Object bean) {
		if (log.isTraceEnabled()) {
			log.trace("injecting cookie-dependencies of: " + bean.getClass().getCanonicalName());
		}
		injectAttributes(bean);
	}

	/**
	 * Outject context variable values from @Out attributes of a component
	 * instance.
	 * 
	 * @param bean
	 *            a Seam component instance
	 * @param enforceRequired
	 *            should we enforce required=true?
	 */
	public void outject(Object bean) {
		if (log.isTraceEnabled()) {
			log.trace("outjecting cookie-dependencies of: " + bean.getClass().getCanonicalName());
		}
		outjectAttributes(bean);
	}

	private void injectAttributes(Object bean) {
		for (CookieField field : cookieAttributes.get(bean.getClass())) {
			Object value = getCookieValueToInject(field);
			if (value != null)
				field.set(bean, value);
		}
	}

	private Object getCookieValueToInject(CookieField annotation) {
		Cookie cookie = (Cookie) FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap().get(
				annotation.getName());
		if (cookie == null)
			return null;

		String value = cookie.getValue();
		if (value == null || value.length() == 0 || value.equals("null"))
			return null;

		String split = annotation.getAnnotation().split();
		if (split == null || split.length() == 0)
			return value;

		SplitType splitType = annotation.getAnnotation().splitType();
		switch (splitType) {
			case LIST:
				return Arrays.asList(value.split(split));
			case SET:
				Set<String> result = new HashSet<String>();
				Collections.addAll(result, value.split(split));
				return result;
			default:
				return value.split(split);
		}
	}

	private void outjectAttributes(Object bean) {
		for (CookieField field : cookieAttributes.get(bean.getClass())) {
			outjectAttribute(field.getAnnotation(), field.getName(), field.get(bean));
		}
	}

	private void outjectAttribute(CookieValue annotation, String name, Object fieldValue) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (facesContext != null) {
			String value = getCookieValueToOutject(annotation, fieldValue);

			if (value == null || value.equals("null"))
				return;

			Cookie cookie = new Cookie(name, value);

			String comment = annotation.comment();
			if (comment != null && comment.length() != 0)
				cookie.setComment(comment);

			String domain = annotation.domain();
			if (domain != null && domain.length() != 0)
				cookie.setDomain(domain);

			int maxAge = annotation.maxAge();
			cookie.setMaxAge(maxAge);

			String path = annotation.path();
			if (path != null && path.length() != 0)
				cookie.setPath(path);

			boolean secure = annotation.secure();
			cookie.setSecure(secure);

			int version = annotation.version();
			cookie.setVersion(version);

			((HttpServletResponse) facesContext.getExternalContext().getResponse()).addCookie(cookie);

			if (log.isDebugEnabled()) {
				log.debug("Write Cookies for User '#0' CID: #1", Identity.instance().getCredentials().getUsername(),
						Contexts.isConversationContextActive() ? Conversation.instance().getId() : "n/a");
			}
		}
	}

	@SuppressWarnings("unchecked")
	private String getCookieValueToOutject(CookieValue annotation, Object fieldValue) {
		if (fieldValue == null)
			return null;

		if (fieldValue instanceof String) {
			String stringValue = (String) fieldValue;
			if (stringValue.length() == 0 || stringValue.equals("null"))
				return null;
		}

		if (fieldValue instanceof Collection) {
			Collection collection = (Collection) fieldValue;
			if (collection.isEmpty())
				return null;
		}

		String split = annotation.split();
		if (split == null || split.length() == 0)
			return (String) fieldValue;

		SplitType splitType = annotation.splitType();
		switch (splitType) {
			case LIST:
			case SET:
				return StringUtils.join((Collection<String>) fieldValue, annotation.split());
			default:
				return StringUtils.join((Object[]) fieldValue, annotation.split());
		}
	}

}
