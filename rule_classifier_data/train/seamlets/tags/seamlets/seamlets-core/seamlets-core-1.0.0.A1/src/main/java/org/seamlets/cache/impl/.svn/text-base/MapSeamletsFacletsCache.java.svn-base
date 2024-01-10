package org.seamlets.cache.impl;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.seamlets.cache.SeamletsFacletsCache;
import org.seamlets.facelets.SeamletsFacelet;
import org.seamlets.util.ExpiringMap;

@Name("org.seamlets.cache.faceletsCache")
@Scope(ScopeType.APPLICATION)
@Startup
@Install(precedence = Install.BUILT_IN)
public class MapSeamletsFacletsCache implements SeamletsFacletsCache {

	private ExpiringMap<String, SeamletsFacelet>	cache	= new ExpiringMap<String, SeamletsFacelet>();

	@Create
	public void create() {
		cache.getExpirer().startExpiring();
	}
	
	public void cache(String viewId, SeamletsFacelet facelet) {
		cache.put(viewId, facelet);
	}

	public SeamletsFacelet load(String viewId) {
		return cache.get(viewId);
	}

	public void setTimeToLive(int timeToLive) {
		cache.setTimeToLive(timeToLive);
	}

	public int getTimeToLive() {
		return cache.getTimeToLive();
	}
}
