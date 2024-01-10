package org.seamlets.deploy;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.log.Log;
import org.jboss.seam.util.Reflections;
import org.seamlets.annotation.CookieValue;

@Name("org.seamlets.deploy.Cookies")
@Scope(ScopeType.APPLICATION)
@Startup
public class Cookies implements Serializable {

	@Logger
	private Log log;
	
	@In(required = false, value = "#{deploymentStrategy.annotatedClasses['org.seamlets.annotation.CookieSavedState']}")
	private Set<Class<?>>		cookieSavedStateClasses;

	private Map<Class<?>,List<CookieField>>	cookieAttributes	= new HashMap<Class<?>, List<CookieField>>();

	@Create
	public void startup() {
		for (Class<?> clazz : cookieSavedStateClasses) {
			for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
				
				List<CookieField> cookieAttributesList = new ArrayList<CookieField>();
				for (Field field : clazz.getDeclaredFields()) {
					scanField(field, clazz, cookieAttributesList);
				}
				cookieAttributes.put(clazz, cookieAttributesList);
				
			}
		}
	}

	private void scanField(Field field, Class<?> clazz, List<CookieField> cookieAttributesList) {
		if (field.isAnnotationPresent(CookieValue.class)) {
			if (!field.isAccessible()) {
				field.setAccessible(true);
			}

			CookieValue cookieValue = field.getAnnotation(CookieValue.class);
			String name = toName(cookieValue, field, clazz);
			cookieAttributesList.add(new CookieField(name, field, cookieValue));
			
			log.info("CookieValue: #0", name);
		}
	}

	private static String toName(CookieValue cookieValue, Field field, Class<?> clazz) {

		String name = cookieValue.name();
		if (name == null || name.length() == 0) {
			name = field.getName();
		}

		name = clazz.getCanonicalName() + "." + name;
		return name;
	}

	private String getAttributeMessage(Object bean, String attributeName) {
		return bean.getClass().getCanonicalName() + '.' + attributeName;
	}

	void setFieldValue(Object bean, Field field, String name, Object value) {
		try {
			Reflections.set(field, bean, value);
		} catch (Exception e) {
			throw new IllegalArgumentException("could not set cookie-field value: " + getAttributeMessage(bean, name),
					e);
		}
	}

	Object getFieldValue(Object bean, Field field, String name) {
		try {
			return Reflections.get(field, bean);
		} catch (Exception e) {
			throw new IllegalArgumentException("could not get cookie-field value: " + getAttributeMessage(bean, name),
					e);
		}
	}

	public Map<Class<?>, List<CookieField>> getCookieAttributes() {
		return Collections.unmodifiableMap(cookieAttributes);
	}

	public final class CookieField {

		private final String		name;
		private final Field			field;
		private final CookieValue	annotation;

		CookieField(String name, Field field, CookieValue annotation) {
			this.name = name;
			this.field = field;
			this.annotation = annotation;
		}

		public String getName() {
			return name;
		}

		public Field getField() {
			return field;
		}

		public CookieValue getAnnotation() {
			return annotation;
		}

		public Class<?> getType() {
			return field.getType();
		}

		public void set(Object bean, Object value) {
			setFieldValue(bean, field, name, value);
		}

		public Object get(Object bean) {
			return getFieldValue(bean, field, name);
		}

		@Override
		public String toString() {
			return "CookieField(" + name + ')';
		}
	}

}