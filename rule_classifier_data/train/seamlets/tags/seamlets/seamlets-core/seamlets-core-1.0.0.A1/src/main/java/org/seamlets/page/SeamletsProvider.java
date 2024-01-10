/**
 * 
 */
package org.seamlets.page;

import org.seamlets.exceptions.ViewIdNotManagedBySeamletsException;

/**
 * @author dzwicker
 *
 */
public interface SeamletsProvider {
	
	/**
	 * Auflösung einer ViewID zu einem DocumentMeta Bean.
	 * Die Bean, die zurückgegeben wird, enthält sowohl
	 * eine interne Referenz auf das Dokument, um den Inhalt holen zu können
	 * 
	 * @param viewID
	 * @return
	 * @throws ViewIdNotManagedBySeamletsException
	 */
	public SeamletsPage getPage (String viewID) throws ViewIdNotManagedBySeamletsException;

}
