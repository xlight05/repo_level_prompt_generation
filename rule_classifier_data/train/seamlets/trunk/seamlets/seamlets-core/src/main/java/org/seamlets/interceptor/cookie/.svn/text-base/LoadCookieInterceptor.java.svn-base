package org.seamlets.interceptor.cookie;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;

import org.jboss.seam.Component;
import org.jboss.seam.CyclicDependencyException;
import org.jboss.seam.annotations.intercept.AroundInvoke;
import org.jboss.seam.annotations.intercept.Interceptor;
import org.jboss.seam.intercept.AbstractInterceptor;
import org.jboss.seam.intercept.InvocationContext;
import org.jboss.seam.log.Log;
import org.jboss.seam.log.Logging;
import org.jboss.seam.util.Exceptions;
import org.seamlets.annotation.LoadCookies;
import org.seamlets.deploy.Cookies;
import org.seamlets.deploy.Cookies.CookieField;

/**
 * Before invoking the component, inject all cookie dependencies. After
 * invoking, outject cookie dependencies back to client.
 * 
 * @author Daniel Zwicker
 */
@Interceptor
public class LoadCookieInterceptor extends AbstractInterceptor {

	private static final Log	log		= Logging.getLog(LoadCookieInterceptor.class);

	private boolean				injected;

	private boolean				injecting;

	private ReentrantLock		lock	= new ReentrantLock();

	private Cookies				cookieValueDeployer;

	@PostConstruct
	public void postConstruct(@SuppressWarnings("unused") InvocationContext invocation) {
		cookieValueDeployer = (Cookies) Component.getInstance("org.seamlets.deploy.Cookies");
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

			return invocation.proceed();

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

	private void injectAttributes(Object bean) {
		for (CookieField field : cookieValueDeployer.getCookieAttributes().get(bean.getClass())) {
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

}
