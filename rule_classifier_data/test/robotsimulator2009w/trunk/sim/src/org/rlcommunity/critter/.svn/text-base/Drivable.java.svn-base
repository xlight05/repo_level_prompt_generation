package org.rlcommunity.critter;

import org.rlcommunity.critter.Drops.SimulatorDrop;

/** Interface for drivable objects, i.e., objects that can be controlled.
 *  The control comes from a "drop". Further, drivable objects
 *  must be able to create drops, which is used to send observations
 *  to the controller.
 *  
 * @author csaba
 *
 */
public interface Drivable {
	public static final String NAME = "drivable";
	/** Sets the inputs to the object from the drop */
	void setFromDrop( SimulatorDrop drop );
	/** Collects observations for sending; should reset time elapsed */
	SimulatorDrop makeDrop();
	/** A drivable component might need to maintain a persistant component. 
	 * This is where this method comes in. */
	public void update(Drivable nextDriveState, int delta);
	/** A drivable component sometimes wants to update the state.. */
	void update(SimulatorObject obj, ObjectStateDynamics dynState, int delta); 
	
}
