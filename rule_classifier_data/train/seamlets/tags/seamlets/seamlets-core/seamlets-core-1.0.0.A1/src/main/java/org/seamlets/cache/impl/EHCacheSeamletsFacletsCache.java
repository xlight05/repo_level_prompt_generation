package org.seamlets.cache.impl;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;

import net.sf.ehcache.management.ManagementService;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.cache.EhCacheProvider;
import org.seamlets.cache.SeamletsFacletsCache;
import org.seamlets.facelets.SeamletsFacelet;

@Name("org.seamlets.cache.faceletsCache")
@Scope(ScopeType.APPLICATION)
@Startup
@Install(value = false, precedence = Install.BUILT_IN, classDependencies="net.sf.ehcache.Cache")
public class EHCacheSeamletsFacletsCache implements SeamletsFacletsCache{
	
	@In
	private EhCacheProvider cacheProvider;
	
	@Create
	public void registerEhCacheManager() {
		MBeanServer mBeanServer = MBeanServerFactory.findMBeanServer(null).get(0);
	    ManagementService.registerMBeans(cacheProvider.getDelegate(), mBeanServer, true, true, true, true);
	}

	public void cache(String viewId, SeamletsFacelet facelet) {
		cacheProvider.put(viewId, facelet);
	}

	public SeamletsFacelet load(String viewId) {
		return (SeamletsFacelet) cacheProvider.get(viewId);
	}
}
