package org.seamlets.cache.impl;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

import org.jboss.cache.Cache;
import org.jboss.cache.jmx.JmxRegistrationManager;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.cache.JbossCache2Provider;
import org.seamlets.cache.SeamletsFacletsCache;
import org.seamlets.facelets.SeamletsFacelet;

@Name("org.seamlets.cache.faceletsCache")
@Scope(ScopeType.APPLICATION)
@Startup
@Install(value = false, precedence = Install.BUILT_IN, classDependencies = { "org.jboss.cache.Cache",
		"org.jgroups.MembershipListener" })
public class JbossSeamletsFaceletsCache implements SeamletsFacletsCache {

	@In
	private JbossCache2Provider		cacheProvider;

	private JmxRegistrationManager	jmxManager;

	@SuppressWarnings("unchecked")
	@Create
	public void registerJbossCacheManager() throws Exception {
		MBeanServer mBeanServer = (MBeanServer) MBeanServerFactory.findMBeanServer(null).get(0);

		Cache cache = cacheProvider.getDelegate();

		ObjectName on = new ObjectName("jboss.cache:service=JbossCache");
		jmxManager = new JmxRegistrationManager(mBeanServer, cache, on);
		jmxManager.registerAllMBeans();

	}

	@Destroy
	public void unregisterJbossCacheManager() {
		jmxManager.unregisterAllMBeans();
	}

	public void cache(String viewId, SeamletsFacelet facelet) {
		cacheProvider.put(viewId, facelet);
	}

	public SeamletsFacelet load(String viewId) {
		return (SeamletsFacelet) cacheProvider.get(viewId);
	}

}
