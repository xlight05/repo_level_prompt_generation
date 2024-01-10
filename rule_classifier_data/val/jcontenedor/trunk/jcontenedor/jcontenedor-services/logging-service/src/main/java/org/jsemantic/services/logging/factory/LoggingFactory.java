package org.jsemantic.services.logging.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.jsemantic.jservice.core.component.Component;
import org.jsemantic.jservice.core.component.exception.ComponentException;
import org.jsemantic.jservice.core.component.skeletal.AbstractComponent;
import org.jsemantic.services.logging.factory.LoggingFactory;
import org.jsemantic.services.logging.factory.LoggingFactoryException;

public class LoggingFactory extends AbstractComponent implements Component {
	
	public LoggingFactory() {
		init();
	}
	
	private final String LOG_INTERFACE = "org.apache.commons.logging.Log";

	public static final String LOG_PROVIDER_LOG4J = "org.apache.commons.logging.impl.Log4JLogger";

	public static final String LOG_PROVIDER_SIMPLE = "org.apache.commons.logging.impl.SimpleLog";

	@SuppressWarnings("unchecked")
	private Constructor logConstructor = null;

	@SuppressWarnings("unchecked")
	private Class[] logConstructorSignature = { java.lang.String.class };

	private Method cLogMethod = null;

	@SuppressWarnings("unchecked")
	private Class[] cLogMethodSignature = { LogFactory.class };
	
	private String logType = LOG_PROVIDER_SIMPLE;

	public void setLogType(String logType) {
		this.logType = logType;
	}
	
	public Log getLogInstance()
			throws LoggingFactoryException {
		Log newInstance = null;
		try {
			Object[] constructorParams = new Object[] { logType};
			newInstance = (Log) getConstructor(logType).newInstance(
					constructorParams);
			if (cLogMethod != null) {
				constructorParams[0] = this;
				cLogMethod.invoke(newInstance, constructorParams);
			}
			return (newInstance);
		} catch (Throwable e) {
			throw new LoggingFactoryException(e);
		}
	}
	@SuppressWarnings("unchecked")
	private Constructor getConstructor(String pName)
			throws LoggingFactoryException {
		if (logConstructor != null) {
			return logConstructor;
		}
		String logClassName = pName;
		Class logClass = null;
		Class logInterface = null;
		try {
			logInterface = this.getClass().getClassLoader().loadClass(
					LOG_INTERFACE);
			logClass = loadClass(logClassName);
			if (logClass == null) {
				throw new LoggingFactoryException(
						"No suitable Log implementation for " + logClassName);
			}
			if (!logInterface.isAssignableFrom(logClass)) {
				Class[] interfaces = logClass.getInterfaces();
				for (int i = 0; i < interfaces.length; i++) {
					if (LOG_INTERFACE.equals(interfaces[i].getName())) {
						throw new LoggingFactoryException(
								"Invalid class loader hierarchy.  "
										+ "You have more than one version of '"
										+ LOG_INTERFACE
										+ "' visible, which is "
										+ "not allowed.");
					}
				}
				throw new LoggingFactoryException("Class " + logClassName
						+ " does not implement '" + LOG_INTERFACE + "'.");
			}
		} catch (Throwable e) {
			throw new LoggingFactoryException(e);
		}
		try {
			cLogMethod = logClass.getMethod("setLogFactory",
					cLogMethodSignature);
		} catch (Throwable t) {
			cLogMethod = null;
		}
		try {
			logConstructor = logClass.getConstructor(logConstructorSignature);
			return (logConstructor);
		} catch (Throwable t) {
			throw new LoggingFactoryException("No suitable Log constructor "
					+ logConstructorSignature + " for " + logClassName, t);
		}
	}

	@SuppressWarnings("unchecked")
	private Class loadClass(final String clazzName)
			throws ClassNotFoundException {

		Object result = AccessController.doPrivileged(new PrivilegedAction() {

			public Object run() {
				Log log = LogFactory.getLog(LoggingFactory.class);
				ClassLoader threadCL = getContextClassLoader();
				if (threadCL != null) {
					try {
						return threadCL.loadClass(clazzName);
					} catch (ClassNotFoundException ex) {
						log.error(ex);
					}
				}
				try {
					return Class.forName(clazzName);
				} catch (ClassNotFoundException e) {
					return e;
				}
			}
		});
		if (result instanceof Class) {
			return (Class) result;
		}
		throw (ClassNotFoundException) result;
	}

	private ClassLoader getContextClassLoader() throws LoggingFactoryException {
		ClassLoader classLoader = null;
		try {
			Method method = Thread.class.getMethod("getContextClassLoader",
					null);
			try {
				classLoader = (ClassLoader) method.invoke(Thread
						.currentThread(), null);
			} catch (IllegalAccessException e) {
				throw new LoggingFactoryException(
						"Unexpected IllegalAccessException", e);
			} catch (InvocationTargetException e) {
				if (e.getTargetException() instanceof SecurityException) {
					; // ignore
				} else {
					throw new LoggingFactoryException(
							"Unexpected InvocationTargetException", e
									.getTargetException());
				}
			}
		} catch (NoSuchMethodException e) {
			classLoader = LogFactory.class.getClassLoader();
		}
		return classLoader;
	}

	@Override
	protected void postConstruct() throws ComponentException {
	}

	@Override
	protected void release() throws ComponentException {
		this.cLogMethod = null;
		this.cLogMethodSignature = null;
		this.logConstructor = null;
		this.logConstructorSignature = null;
	}
}
