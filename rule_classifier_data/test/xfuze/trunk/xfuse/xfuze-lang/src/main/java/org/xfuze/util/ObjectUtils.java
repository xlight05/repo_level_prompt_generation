/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.xfuze.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jason Chan
 *
 */
public final class ObjectUtils {
	private static final transient Logger logger = LoggerFactory.getLogger(ObjectUtils.class);

	// constants to ignore during copy
	private static final String CONSTANT_PRIMARY_KEY_ARRAY = "PRIMARY_KEY_ARRAY";
	private static final String CONSTANT_PRIMARY_KEY = "PRIMARY_KEY";
	private static final String CONSTANT_IGNORE_PROPERTY_ARRAY = "IGNORE_PROPERTY_ARRAY";
	private static final String CONSTANT_IGNORE_PROPERTIES = "IGNORE_PROPERTIES";

	private static final String GETTER_PREFIX = "get";
	private static final String SETTER_PREFIX = "set";

	public enum Flag {
		INCLUDE {
			public String toString() {
				return "INCLUDE";
			}
		},
		EXCLUDE {
			public String toString() {
				return "EXCLUDE";
			}
		}
	}

	// Avoid constructing objects of this class.
	private ObjectUtils() {
	}

	private static boolean ignore(String fieldName) {
		boolean result = false;
		if (CONSTANT_PRIMARY_KEY_ARRAY.equals(fieldName) || CONSTANT_PRIMARY_KEY.equals(fieldName)
				|| CONSTANT_IGNORE_PROPERTY_ARRAY.equals(fieldName) || CONSTANT_IGNORE_PROPERTIES.equals(fieldName)) {
			result = true;
		}
		return result;
	}

	/**
	 * Copy the property values of the given source object into the given target object.
	 *
	 * @param source
	 *            the source object, cannot be null
	 * @param target
	 *            the target object, cannot be null
	 * @param flag
	 *            the indicator for include or exclude the given properties
	 * @param properties
	 *            the array of property names to include or exclude in the copy operation
	 */
	public static void copyProperties(Object source, Object target, Flag flag, String[] properties) {
		if (source == null) {
			throw new IllegalArgumentException("'source' is required");
		}
		if (target == null) {
			throw new IllegalArgumentException("'target' is required");
		}
		if (properties == null || (properties != null && properties.length == 0)) {
			throw new IllegalArgumentException("'properties' is required");
		}

		List<String> propertyList = new ArrayList<String>();
		propertyList = Arrays.asList(properties);

		try {
			Class<?> sourceClazz = Class.forName(source.getClass().getCanonicalName());
			Class<?> targetClazz = Class.forName(target.getClass().getCanonicalName());

			Field[] fields = sourceClazz.getDeclaredFields();
			for (Field field : fields) {
				String fieldName = field.getName();

				if (ignore(fieldName)) {
					continue;
				}

				if (Flag.INCLUDE == flag) {
					if (propertyList.contains(fieldName)) {
						Method getter = null;
						Method setter = null;

						try {
							getter = sourceClazz.getMethod(GETTER_PREFIX + StringUtils.capitalize(field.getName()),
									(Class<?>[]) null);
						} catch (NoSuchMethodException e) {
							logger.warn("Property [" + field.getName() + "] ignored.", e);
						}

						if (getter != null) {
							try {
								setter = targetClazz.getMethod(SETTER_PREFIX + StringUtils.capitalize(field.getName()),
										getter.getReturnType());
							} catch (NoSuchMethodException e) {
								logger.warn("Property [" + field.getName() + "] ignored.", e);

							}

							// copy from source to target
							if (logger.isDebugEnabled()) {
								logger.debug("Source: " + source);
								logger.debug("Target: " + target);
								logger.debug("Getter: " + getter);
								logger.debug("Setter: " + setter);
							}

							if (setter != null) {
								setter.invoke(target, getter.invoke(source, (Object[]) null));
							}
						} else {
							logger.error("Failed to retrieve getter.");
						}
					}
				} else if (Flag.EXCLUDE == flag) {
					if (!propertyList.contains(fieldName)) {
						Method getter = null;
						Method setter = null;

						try {
							getter = sourceClazz.getMethod(GETTER_PREFIX + StringUtils.capitalize(field.getName()),
									(Class<?>[]) null);
						} catch (NoSuchMethodException e) {
							logger.warn("Property [" + field.getName() + "] ignored (source).", e);
						}

						if (getter != null) {
							try {
								setter = targetClazz.getMethod(SETTER_PREFIX + StringUtils.capitalize(field.getName()),
										getter.getReturnType());
							} catch (NoSuchMethodException e) {
								logger.warn("Property [" + field.getName() + "] ignored (target).", e);
							}

							if (setter != null) {
								setter.invoke(target, getter.invoke(source, (Object[]) null));
							}
						}
					}
				} else {
					logger.error("Not handled flag. [" + flag + "]");
				}
			}
		} catch (ClassNotFoundException e) {
			logger.error("Failed to copy.", e);
		} catch (SecurityException e) {
			logger.error("Failed to copy.", e);
		} catch (IllegalArgumentException e) {
			logger.error("Failed to copy.", e);
		} catch (IllegalAccessException e) {
			logger.error("Failed to copy.", e);
		} catch (InvocationTargetException e) {
			logger.error("Failed to copy.", e);
		}
	}

	/**
	 * Copy the property values of the given source object into the given target object, ignoring the given
	 * "ignoreProperties".
	 *
	 * @param source
	 *            the source object, cannot be null
	 * @param target
	 *            the target object, cannot be null
	 * @param sourceFieldPrefix
	 *            the source object field's prefix, optional
	 * @param targetFieldPrefix
	 *            the target object field's prefix, optional
	 * @param withoutPrefixProperties
	 *            the array of (target) property names without prefix
	 * @param ignoreProperties
	 *            the array of property names to ignore
	 */
	public static void copyProperties(Object source, Object target, String sourceFieldPrefix, String targetFieldPrefix,
			String[] withoutPrefixProperties, String[] ignoreProperties) {
		boolean hasWithoutPrefixProperties = false;
		boolean hasIgnoreProperties = false;

		if (withoutPrefixProperties != null && withoutPrefixProperties.length > 0) {
			hasWithoutPrefixProperties = true;
		}
		if (ignoreProperties != null && ignoreProperties.length > 0) {
			hasIgnoreProperties = true;
		}

		List<String> withoutPrefixPropertyList = new ArrayList<String>();
		if (hasWithoutPrefixProperties) {
			withoutPrefixPropertyList = Arrays.asList(withoutPrefixProperties);
		}

		List<String> ignorePropertyList = new ArrayList<String>();
		if (hasIgnoreProperties) {
			ignorePropertyList = Arrays.asList(ignoreProperties);
		}

		copyProperties(source, target, sourceFieldPrefix, targetFieldPrefix, withoutPrefixPropertyList,
				ignorePropertyList);
	}

	/**
	 * Copy the property values of the given source object into the given target object, ignoring the given
	 * "ignoreProperties".
	 *
	 * @param source
	 *            the source object, cannot be null
	 * @param target
	 *            the target object, cannot be null
	 * @param sourceFieldPrefix
	 *            the source object field's prefix, optional
	 * @param targetFieldPrefix
	 *            the target object field's prefix, optional
	 * @param withoutPrefixProperties
	 *            the list of (target) property names without prefix
	 * @param ignoreProperties
	 *            the list of property names to ignore
	 */
	public static void copyProperties(Object source, Object target, String sourceFieldPrefix, String targetFieldPrefix,
			List<String> withoutPrefixProperties, List<String> ignoreProperties) {
		if (source == null) {
			throw new IllegalArgumentException("'source' is required");
		}
		if (target == null) {
			throw new IllegalArgumentException("'target' is required");
		}

		boolean hasSourcePrefix = false;
		boolean hasTargetPrefix = false;
		boolean hasWithoutPrefixProperties = false;
		boolean hasIgnoreProperties = false;
		if (StringUtils.isNotBlank(sourceFieldPrefix)) {
			hasSourcePrefix = true;
		}
		if (StringUtils.isNotBlank(targetFieldPrefix)) {
			hasTargetPrefix = true;
		}
		if (withoutPrefixProperties != null && withoutPrefixProperties.size() > 0) {
			hasWithoutPrefixProperties = true;
		}
		if (ignoreProperties != null && ignoreProperties.size() > 0) {
			hasIgnoreProperties = true;
		}

		List<String> withoutPrefixPropertyList = new ArrayList<String>();
		if (hasWithoutPrefixProperties) {
			withoutPrefixPropertyList.addAll(withoutPrefixProperties);
		}

		List<String> ignorePropertyList = new ArrayList<String>();
		if (hasIgnoreProperties) {
			ignorePropertyList.addAll(ignoreProperties);
		}

		try {
			Class<?> sourceClazz = Class.forName(source.getClass().getCanonicalName());
			Class<?> targetClazz = Class.forName(target.getClass().getCanonicalName());

			Field[] fields = sourceClazz.getDeclaredFields();
			for (Field field : fields) {
				String fieldName = field.getName();

				if (ignore(fieldName)) {
					continue;
				}

				if (!ignorePropertyList.contains(fieldName)) {
					Method getter = null;
					Method setter = null;

					try {
						if (hasSourcePrefix) {
							getter = sourceClazz.getMethod(GETTER_PREFIX + StringUtils.capitalize(sourceFieldPrefix)
									+ StringUtils.capitalize(field.getName()), (Class<?>[]) null);
						} else {
							getter = sourceClazz.getMethod(GETTER_PREFIX + StringUtils.capitalize(field.getName()),
									(Class<?>[]) null);
						}
					} catch (NoSuchMethodException e) {
						logger.warn("Property [" + field.getName() + "] ignored (source).", e);
					}

					if (getter != null) {
						try {
							if (hasTargetPrefix && !withoutPrefixPropertyList.contains(fieldName)) {
								setter = targetClazz.getMethod(SETTER_PREFIX
										+ StringUtils.capitalize(targetFieldPrefix)
										+ StringUtils.capitalize(field.getName()), getter.getReturnType());
							} else {
								setter = targetClazz.getMethod(SETTER_PREFIX + StringUtils.capitalize(field.getName()),
										getter.getReturnType());
							}
						} catch (NoSuchMethodException e) {
							logger.warn("Property [" + field.getName() + "] ignored (target).", e);
						}

						if (setter != null) {
							setter.invoke(target, getter.invoke(source, (Object[]) null));
						}
					}
				}
			}
		} catch (ClassNotFoundException e) {
			logger.error("Failed to copy.", e);
		} catch (IllegalArgumentException e) {
			logger.error("Failed to copy.", e);
		} catch (IllegalAccessException e) {
			logger.error("Failed to copy.", e);
		} catch (InvocationTargetException e) {
			logger.error("Failed to copy.", e);
		} catch (SecurityException e) {
			logger.error("Failed to copy.", e);
		}
	}
}
