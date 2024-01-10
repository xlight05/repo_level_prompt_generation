package org.rlcommunity.critter.Clients;

import java.util.LinkedList;
import java.util.List;

import org.rlcommunity.critter.SimulatorMain;
import org.rlcommunity.critter.Drops.CritterControlDrop;
import org.rlcommunity.critter.Drops.CritterStateDrop;
import org.rlcommunity.critter.Drops.SimulatorDrop;
import org.rlcommunity.critter.Base.Vector2D;
import org.rlcommunity.critter.Controls.Controller;
import org.rlcommunity.critter.Controls.DefaultTwoWheeledController;
import org.rlcommunity.critter.Controls.MBController;
import org.rlcommunity.critter.Controls.TwoWheeledRobot;

/** Organizes the communication between a controller and 
 * the simulator pertaining to controlling two-wheeled robots.
 * @author csaba
 *
 */
public class TwoWheeledRobotControllerClient extends Thread implements DropClient, TwoWheeledRobot {

	public static long controlSamplingInterval = 100;

	boolean hasControlToSend = false;
	boolean isInitialized = false;
	
	// controls
	double torqueL; 
	double torqueR; 
	
	// sensors
	double lightL;
	double lightR;
	double lightM;
	double angleL;
	double angleR;
	double velL;
	double velR;
	
	Vector2D position;
	double direction;
	
	// the controller that computes the controls
	public Controller controller;

	private boolean hasNewData=false;
	
	public TwoWheeledRobotControllerClient( Controller controller )
	{
		this.controller = controller;
		if (!SimulatorMain.ASYNCHRONOUS_MODE)
			controlSamplingInterval=9;
	}

	public TwoWheeledRobotControllerClient(boolean NN, String neuralNet)
	{
		// temporary hack; the default controller is DefaultTwoWheeledController
		if (!SimulatorMain.ASYNCHRONOUS_MODE){
			this.controller = new MBController((TwoWheeledRobot)this, NN, neuralNet);
			controlSamplingInterval=9;
		}else{
			this.controller = new DefaultTwoWheeledController((TwoWheeledRobot)this);
		}
	}
	
	// DropClient interface
	synchronized public List<SimulatorDrop> receive() {
		if (hasControlToSend)
		{
			hasControlToSend = false; // data has been sent
			//package up a drop and send it
			LinkedList<SimulatorDrop> dropList = new LinkedList<SimulatorDrop>();
			CritterControlDrop controlDrop = new CritterControlDrop();
			controlDrop.motor_mode = CritterControlDrop.MotorMode.WHEEL_SPACE;
			controlDrop.m100_vel = (int) (CritterControlDrop.REALTOINTCONV * torqueL);
			controlDrop.m220_vel = (int) (CritterControlDrop.REALTOINTCONV * torqueR);
			dropList.add(controlDrop);
			return dropList;
		}
		else
			return null;
	
	}

	synchronized public void send(SimulatorDrop data) {
//		System.out.println("in send");
		
		CritterStateDrop stateDrop = (CritterStateDrop)data;
		lightL = (double)stateDrop.light[0] / CritterStateDrop.REALTOINTCONV;
		lightM = (double)stateDrop.light[1] / CritterStateDrop.REALTOINTCONV;
		lightR = (double)stateDrop.light[2] / CritterStateDrop.REALTOINTCONV;
		velL = (double)stateDrop.accel.x / CritterStateDrop.REALTOINTCONV;
		velR = (double)stateDrop.accel.y / CritterStateDrop.REALTOINTCONV;
		
		angleL = (double)stateDrop.wheelAngle.x / CritterStateDrop.REALTOINTCONV;
		angleR = (double)stateDrop.wheelAngle.y / CritterStateDrop.REALTOINTCONV;
		isInitialized = true;
		
		position=stateDrop.position;
		direction=stateDrop.direction;
		
		hasNewData = true;
		
		// received info from the robot; let's thank it
//		System.out.print("Thanks for the observation\n");
	}
	
	synchronized boolean isInitialized()
	{
		return isInitialized;
	}
	

	// Thread interface
	 
	public void run() {
		while (true)
		{
			try {
				sleep(controlSamplingInterval);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (isInitialized())
				if (SimulatorMain.ASYNCHRONOUS_MODE || hasNewData)
				{
					controller.computeControl();
					hasNewData = false;
				}
		}
	}

	// TwoWheeledRobot interface
	
	public double getLightL() {
		return lightL;
	}

	public double getLightR() {
		// TODO Auto-generated method stub
		return lightR;
	}
	
	public double getLightM() {
		return lightM;
	}

	public double getVelL() {
		// TODO Auto-generated method stub
		return velL;
	}

	public double getVelR() {
		// TODO Auto-generated method stub
		return velR;
	}

	synchronized public void setTorques(double torqueL, double torqueR) {
		this.torqueL = torqueL;
		this.torqueR = torqueR;
		hasControlToSend = true;		
	}

	public double getAngleL() {
		return angleL;
	}

	public double getAngleR() {
		return angleR;
	}
	
	public Vector2D getPosition(){
		return position;
	}

	public double getDirection() {
		return direction;
	}

}
