package org.seamlets.cache;

import java.io.Serializable;

import org.seamlets.facelets.SeamletsFacelet;


public interface SeamletsFacletsCache extends Serializable{

	public SeamletsFacelet load(String viewId);

	public void cache(String viewId, SeamletsFacelet facelet);

}
