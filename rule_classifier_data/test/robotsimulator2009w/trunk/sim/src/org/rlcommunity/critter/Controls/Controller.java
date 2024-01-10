package org.rlcommunity.critter.Controls;

/** Abstract interface class for controllers.
 *  All controllers should be able to compute their controls.
 * @author csaba
 *
 */
public interface Controller {
	/** Should compute the controls */
	void computeControl();
}
