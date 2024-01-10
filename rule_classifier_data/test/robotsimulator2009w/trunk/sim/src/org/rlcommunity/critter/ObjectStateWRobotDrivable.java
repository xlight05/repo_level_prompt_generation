package org.rlcommunity.critter;

import java.awt.Graphics;

import org.rlcommunity.critter.Drops.CritterControlDrop;
import org.rlcommunity.critter.Drops.SimulatorDrop;
import org.rlcommunity.critter.Drops.CritterControlDrop.MotorMode;

/** Set the torques in the robot represented by ObjectStateWRobotDynamics */
public class ObjectStateWRobotDrivable implements ObjectState,  Drivable {

	double torque1;
	double torque2;
	
	boolean newTorques = true;
	
	public SimulatorDrop makeDrop() {
		return null;
	}
	public void update(Drivable p_nextDriveState, int delta) {
		ObjectStateWRobotDrivable nextDriveState = (ObjectStateWRobotDrivable)p_nextDriveState;
		nextDriveState.torque1 = torque1;
		nextDriveState.torque2 = torque2;
		nextDriveState.newTorques = newTorques;
	}


	public Object clone()	{
		return new ObjectStateWRobotDrivable();
	}
	public void clear() {
	}

	public void draw(Graphics g, SimulatorObject parent) {
	}

	public String getName() {
		return Drivable.NAME;
	}

	public void resetState() {
	}
	public void update(SimulatorObject obj, ObjectStateDynamics dynState,
			int delta) {
		ObjectStateWRobotDynamics wrobot = (ObjectStateWRobotDynamics)dynState;
		if (SimulatorMain.ASYNCHRONOUS_MODE || newTorques)
			wrobot.setWheelTorques(torque1,torque2);
		newTorques = false;
	}


	public void setFromDrop(SimulatorDrop drop) {
		CritterControlDrop aDrop = (CritterControlDrop)drop;
		assert(aDrop.motor_mode==MotorMode.WHEEL_SPACE);
		torque1 = ((double)aDrop.m100_vel)/CritterControlDrop.REALTOINTCONV;
		torque2 = ((double)aDrop.m220_vel)/CritterControlDrop.REALTOINTCONV;
		newTorques = true;
	}

}
