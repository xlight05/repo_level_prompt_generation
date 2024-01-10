package org.rlcommunity.critter.Controls;

import java.util.Vector;

import org.rlcommunity.critter.SimulatorObject;
import org.rlcommunity.critter.Base.Vector2D;
import org.rlcommunity.critter.WRobotModels.TwoWheeledRobotModel;

public class HTController extends DefaultTwoWheeledController{
	
	public SimulatorObject floorMap;
	
	public HTController(TwoWheeledRobot robot){
		super(robot);
	}
	
	public void computeControl()
	{
		// assumption: the tape is a polygon with identifiable turns
		
		// robot's internal state
		TwoWheeledRobotModel curRobotModel = new TwoWheeledRobotModel();
		Node node = new Node(null, robot.getPosition(), robot.getAngleL(), 
				robot.getAngleR(), robot.getVelL(), robot.getVelR());
		curRobotModel.setWheelState(node.wheelAngle1, node.wheelAngle2, node.wheelAngleVel1, node.wheelAngleVel2);
		curRobotModel.setRigidBodyDynamics(node);
		Vector2D curVel=curRobotModel.computeVel();
		//double vel_norm=Math.sqrt(curVel.x*curVel.x + curVel.y*curVel.y);
		
		// find the distance to the next turn and its angle: 0: distance, 1:angle
		Vector next_turn_data=nextTurn();
		Vector2D nTurnPos=(Vector2D)next_turn_data.get(0);
		Vector2D nTurnInAng=(Vector2D) next_turn_data.get(1);
		Vector2D nTurnOutAng=(Vector2D) next_turn_data.get(2);
		
		// for higher speeds, closer turns, sharper turn angles --> lower the speed sooner
		// big assumption: we are moving toward the turn... no deviation
		//double time_to_turn=next_turn_data[0]/vel_norm; // time left to turn: distance over speed
		
		// we set the torque directly in the first try... finally we want to set the velocity and use the 2-layered idea
		Vector2D turnVel=turnVel(nTurnInAng, nTurnOutAng);
		
		double[] torques=getTorque(robot.getPosition(), curVel, nTurnPos, turnVel);
		robot.setTorques(torques[0], torques[1]);
		
	}
	
	// the distance to the next turn and the angle of this turn: 0: position, 1:in angle, 2: out angle
	private Vector nextTurn(){
		return null;
	}
	
	// what should be the velocity at turn, depending on its angle
	private Vector2D turnVel(Vector2D nTurnInAng, Vector2D nTurnOutAng){
		return null;
	}
	
	private double[] getTorque(Vector2D curPos, Vector2D curVel, Vector2D nextPos, Vector2D nextVel){
		return null;
	}
}
