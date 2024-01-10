package org.rlcommunity.critter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

import org.rlcommunity.critter.Base.Force;
import org.rlcommunity.critter.Base.RigidBodyDynamicsData;
import org.rlcommunity.critter.Base.Vector2D;
import org.rlcommunity.critter.WRobotModels.TwoWheeledRobotModel;

/** ObjectStateWRobotDynamics implements a two-wheeled mobile robot,
 *   controllable through specifying torques that make the wheels rotate.
 *   The implementation uses generalized coordinates (Langrangian dynamics),
 *   so that the robot will never actually move sideways.
 *   
 *   The implementation is based on the following article:
 *   X. Yun and Y. Yamamoto: Internal Dynamics of a Wheeled Mobile Controllable.
 *   Proc. of the 1993 IEEE/RSJ Int. Conf. on Intelligent Robost and Systems,
 *   Yokohama, Japan, July 26-30, 1993, pp. 1288-1294.
 *   
 * @author csaba
 */
public class ObjectStateWRobotDynamics  extends ObjectStateDynamics   {
			
	boolean teleporting = SimulatorMain.runSensorCalibrationExperiment; // for experimenting with the robot
	boolean newTorques = false;
	class RigidBodyDynamicsDataImpl implements RigidBodyDynamicsData
	{
		private SimulatorObject obj;
		private ObjectStateDynamics dyn;

		RigidBodyDynamicsDataImpl( SimulatorObject obj, ObjectStateDynamics dyn )
		{
			this.obj = obj;
			this.dyn = dyn;
		}
		public Object shallowClone()
		{
			RigidBodyDynamicsDataImpl newCopy = new RigidBodyDynamicsDataImpl(obj,dyn);
			return newCopy;
		}
		public double getAngVelocity() {
			return dyn.getAngVelocity();
		}

		public double getDirection() {
			return obj.getDirection();
		}

		public Vector2D getPosition() {
			return obj.getPosition();
		}

		public Vector2D getVelocity() {
			return dyn.getVelocity();
		}

		public void resetState() {
			dyn.resetState();
		}

		public void setAngVelocity(double omega) {
			dyn.setAngVelocity(omega);
		}

		public void setDirection(double dir) {
			obj.setDirection(dir);
		}

		public void setPosition(Vector2D pos) {
			obj.setPosition(pos);
		}

		public void setVelocity(Vector2D vel) {
			dyn.setVelocity(vel);
		}
	}
//	class TwoWheeledRobotModelImpl extends TwoWheeledRobotModel
//	{
//		RigidBodyDynamicsDataImpl rbdDynamics;
//		TwoWheeledRobotModelImpl() {}
//		TwoWheeledRobotModelImpl( SimulatorObject obj, ObjectStateDynamics dyn)
//		{
//			this.rbdDynamics = new RigidBodyDynamicsDataImpl( obj, dyn );
//		}
//		void setRigidBodyDynamics(SimulatorObject obj, ObjectStateDynamics dyn)
//		{
//			this.rbdDynamics = new RigidBodyDynamicsDataImpl( obj, dyn );
//		}
//		void resetRigidBodyDynamics() 
//		{
//			rbdDynamics = null;
//		}
//		@Override
//		public RigidBodyDynamicsData getRigidBodyDynamicsData() {
//			return rbdDynamics;
//		}	
//		public Object clone() 
//		{
//			TwoWheeledRobotModelImpl newcopy = new TwoWheeledRobotModelImpl();
//			newcopy.copyFrom(this);
//			return newcopy;
//		}
//		public void copyFrom( TwoWheeledRobotModelImpl that )
//		{
//			super.copyFrom(that);
//			rbdDynamics = that.rbdDynamics;
//		}
//	}
//	TwoWheeledRobotModelImpl robotModel = new TwoWheeledRobotModelImpl();
	TwoWheeledRobotModel robotModel = new TwoWheeledRobotModel();
	
	// teleporting
	protected LinkedList<Double> posArray;
	protected LinkedList<Double> angleArray;
	protected int counter;
	protected String teleportFile = "all.dat"; // alternative is mili_LS_linedense.dat
//	public ObjectStateWRobotDynamics(double mass, double momentInertia) {
//		super(mass, momentInertia);
//	}

	public ObjectStateWRobotDynamics() {
        super( MIN_MASS, MIN_MOMENT_INERTIA);
        super.setMass(robotModel.getMass());
        super.setMomentInertia(robotModel.getMomentInertia());
        
        if (teleporting)
        {
        	readTeleportingFile();
        }
	}
	protected void readTeleportingFile()
	{
		Scanner scan;
		try {
			File infile = new File(teleportFile);
			scan = new Scanner(infile);
		}
		catch( FileNotFoundException ex )
		{
			System.out.println("Cannot open the input file mili_LS_linedense.dat");
			return;   
		}
		posArray = new LinkedList<Double>();
		angleArray = new LinkedList<Double>();
		//int line = 0;
		while (scan.hasNext())
		{
			//System.out.print(line++,"\n");
			double p = scan.nextDouble();
			double a = scan.nextDouble();
			a = a/180.0*Math.PI;
			scan.nextDouble(); // response
			posArray.add(p);
			angleArray.add(a);			
			counter = 0;
		}
	}
	
	/** Updates newDynData and newObj
	 * 
	 *  Performs Euler integration step.
	 *  
	 * @param obj		  Needed for current state and direction
	 * @param newDynData Updated with new velocities
	 * @param newObj	  Updated with new position and direction 
	 * @param dt		  Integration time (in seconds)
	 * @param length_scale Change of scales; 1 position unit (as used in objects) is how many meters
	 */
	public void update(SimulatorObject obj
			, ObjectStateDynamics newDynData, SimulatorObject newObj, double dt, double length_scale) 
	{
		if (!SimulatorMain.ASYNCHRONOUS_MODE && !newTorques){
			newObj.copyFrom(obj);
			return;
		}
//		//System.out.println("HERE1");
//		
//		if (!SimulatorMain.flag2){
//			SimulatorMain.flag2=true;
//			SimulatorMain.t1=new Date().getTime();
//			//System.out.println("HERE2");
//		}
		
		if (!teleporting)
		{
			// @@@ TODO a better solution is to set this once and store these pointers
			// they should not change (ideally)
			robotModel.setRigidBodyDynamics(new RigidBodyDynamicsDataImpl(obj, this));
			ObjectStateWRobotDynamics nextRobotState = (ObjectStateWRobotDynamics)newDynData;
			nextRobotState.robotModel.setRigidBodyDynamics(new RigidBodyDynamicsDataImpl(newObj, newDynData));
			// collision and other external forces come in here (sadly, external torques are not modeled):
			Force force = getForceSum();
			robotModel.update(force, nextRobotState.robotModel, dt, length_scale);
			robotModel.resetRigidBodyDynamics();
			nextRobotState.robotModel.resetRigidBodyDynamics();
//			System.out.println( "Sim::     Torques: " +
//					robotModel.getTorque1() + " " + robotModel.getTorque2() + " " + obj.aPos );
		}
		else
		{
			newObj.setPosition(new Vector2D(250+posArray.get(counter)/10.0,250));
			newObj.setDirection(3.0*Math.PI/2.0-angleArray.get(counter));
			++counter;
			counter = counter % posArray.size();
		}
		
		newTorques=false;
		
//		long t2=new Date().getTime()-SimulatorMain.t1;
//		//System.out.println(t2);
//		if (t2>threshold){
//			SimulatorMain.flag=false;
//			SimulatorMain.flag2=false;
//			//System.out.println("HERE3");
//		}
	}

	public void resetState()
	{
		super.resetState();
		robotModel.resetState();
	}
	
    public Object clone() {
    	ObjectStateWRobotDynamics newRobot = new ObjectStateWRobotDynamics();
        newRobot.copyFrom(this);
        return newRobot;
    }
	
	
    protected void copyFrom(ObjectState os) {
    	super.copyFrom(os);
        ObjectStateWRobotDynamics dyn = (ObjectStateWRobotDynamics) os;
        robotModel = (TwoWheeledRobotModel) dyn.robotModel.clone();        
    }

    public Vector2D getWheelAngleVel()
    {
    	return robotModel.getWheelAngleVel();
    }

	public void setWheelTorques(double t1, double t2) {
		robotModel.setTorques(t1,t2);		
		newTorques = true;
	}

	/** Set the wheel angles appropriately so that the direction of heading is dir */
	public void setDirection(double dir) {
		robotModel.setDirection(dir);
	}

	public Vector2D getWheelAngle() {
		return robotModel.getWheelAngle();
	}


}
