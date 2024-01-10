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
	 * Aufl�sung einer ViewID zu einem DocumentMeta Bean.
	 * Die Bean, die zur�ckgegeben wird, enth�lt sowohl
	 * eine interne Referenz auf das Dokument, um den Inhalt holen zu k�nnen
	 * 
	 * @param viewID
	 * @return
	 * @throws ViewIdNotManagedBySeamletsException
	 */
	public SeamletsPage getPage (String viewID) throws ViewIdNotManagedBySeamletsException;

}
