package org.rlcommunity.critter;

import org.rlcommunity.critter.Base.Vector2D;
import org.rlcommunity.critter.Drops.CritterStateDrop;

/** The critterbot agent -- constructs whatever is needed for this agent +
 *  implements producing observations for an interested controller.
 *  
 * @author csaba
 */
public class SimulatorAgentCritterBot extends SimulatorAgent {

	/**
	 * @param label
	 * @param id
	 */
	public SimulatorAgentCritterBot(String label, int id) {
		super(label, id);
		createAgent();
	}

	static protected Polygon createAgentShape()
	{
		Polygon agentShape = new Polygon();
		agentShape.addPoint (-0,20);
		agentShape.addPoint (-7.5,18.5);
		agentShape.addPoint (-14,14);
		agentShape.addPoint (-18.5,7.5);
		agentShape.addPoint (-20,0);
		agentShape.addPoint (-18.5,-6.5);
		agentShape.addPoint (-16.5,-16);
		agentShape.addPoint (-13,-26);
		agentShape.addPoint (-8,-35.5);
		agentShape.addPoint (-1,-47);
		agentShape.addPoint (0,-48);
		agentShape.addPoint (-2,-40.5);
		agentShape.addPoint (-4,-32.5);
		agentShape.addPoint (-4.5,-20);
		agentShape.addPoint (-3,-20);
		agentShape.addPoint (3.5,-16);
		agentShape.addPoint (9,-16);
		agentShape.addPoint (15.5,-12.5);
		agentShape.addPoint (19,-6);
		agentShape.addPoint (20,0);
		agentShape.addPoint (18.5,7.5);
		agentShape.addPoint (14,14);
		agentShape.addPoint (7.5,18.5);
		System.out.println ("Agent");
		agentShape.rotate (-Math.PI/2, new Vector2D(0,0));

		agentShape.doneAddPoints();
		return agentShape;
	}

	protected void addSensors()
	{
		// Create an external light sensor
		SimulatorObject sensor = new SimulatorObject("LightSensor1", 5);
		/*shape = new Polygon();

		    shape.addPoint(0.0, -1.0);
		    shape.addPoint(20.0, 0.0);
		    shape.addPoint(0.0, 1.0);
		    shape.doneAddPoints();

		    sensor.setShape(shape); */
//		// These three light sensors have no shape!
//		sensor.setPosition(new Vector2D(20.001,0));
//		sensor.addState(new ObjectStateLightSensor());
//		addChild(sensor);
//
//		// Create two more light sensors
//		sensor = sensor.makeCopy("LightSensor2", 6);
//		sensor.setPosition(new Vector2D(0,-20.00));
//		addChild(sensor);
//
//		sensor = sensor.makeCopy("LightSensor3", 7);
//		sensor.setPosition(new Vector2D(0,20.001));
//		addChild(sensor);

		if(true)
		{
			// create a light sensor (in the "front") that senses objects 
			// that emit diffuse light 
			sensor = new SimulatorObject("L", 8);
			//sensor.setPosition(new Vector2D(22.0,-10.0));
			sensor.setPosition(new Vector2D(30,-SimulatorMain.hw-1));
			//sensor.setDirection(Math.PI/2.0);
			sensor.setDirection(0);
			sensor.addState(new ObjectStateDiffuseLightSensor());
			addChild(sensor);

			sensor = new SimulatorObject("R", 9);
			//sensor.setPosition(new Vector2D(22.0,+10.0));
			sensor.setPosition(new Vector2D(30,SimulatorMain.hw+1));
			//sensor.setDirection(Math.PI/2.0);
			sensor.setDirection(0);
			sensor.addState(new ObjectStateDiffuseLightSensor());
			addChild(sensor);
			
			sensor = new SimulatorObject("M", 10);
			//sensor.setPosition(new Vector2D(22.0,+10.0));
			sensor.setPosition(new Vector2D(30,0));
			//sensor.setDirection(Math.PI/2.0);
			sensor.setDirection(0);
			sensor.addState(new ObjectStateDiffuseLightSensor());
			addChild(sensor);
		}

//		sensor = new SimulatorObject("M", 10);
//		sensor.setPosition(new Vector2D(0.0,0.0)); // in the middle
//		sensor.setDirection(Math.PI/2.0);
//		sensor.addState(new ObjectStateDiffuseLightSensor());
//		addChild(sensor);
	}

	static protected SimulatorObject createWheel(String wheelName, int wheelId, double defaultInput
			, double load, double posX, double posY, double dir )
	{
		SimulatorObject wheel = new SimulatorObject(wheelName,wheelId);
		wheel.setDirection(dir);
		wheel.setPosition(new Vector2D(posX,posY));
		ObjectStateWheel wheelState = new ObjectStateWheel();
		ObjectStateMotor motorState = new ObjectStateMotor(defaultInput);
		wheel.addState(wheelState);
		wheelState.setLoad(load);
		wheel.addState(motorState);
		return wheel;
	}


	protected void createAgent()
	{
		setShape(createAgentShape());
		final boolean addOmniDrive = false;
		final boolean diffDrive = false;
		if (addOmniDrive) 
		{
			// Give the agent a 'physics' state, with mass 4 and assume it is spherical
			double mass = 4;
			double r    = 15E-2;
			double I = mass*r*r/2.0;
			ObjectStateDynamics dyn = new ObjectStateDynamics(mass,I);
			addState(dyn);
			// Give the agent an omnidirectional drive
			addState(new ObjectStateOmnidrive());
		}
		else if (diffDrive)// differential drive; constant input
		{	
			// Give the agent a 'physics' state, with mass 4 and mom. of inertia 2
			ObjectStateDynamics dyn = new ObjectStateDynamics(4,2);
			addState(dyn);
			SimulatorObject wheelL, wheelR;
			double load = 10; // [N]
			// params: name, id, defaultInput, posX, posY, dir
			wheelL = createWheel("WheelL",10, 1.0, load, 0, -20.00, 0.0);
			wheelR = createWheel("WheelR",11, 1.0, load, 0,  20.00, 0.0);
			addChild( wheelL );
			addChild( wheelR );
			dyn.setLoad( 2.0*load );
			// friction should be applied at the wheels (if any)
			//		  dyn.setCoefficientFrictionDyn(0.0);
			//		  dyn.setCoefficientFrictionStatic(0.0);
		}
		else 
		{
			ObjectStateWRobotDynamics owr = new ObjectStateWRobotDynamics();
			addState(owr);
			addState(new ObjectStateWRobotDrivable()); // so that the agent can receive messages
		}
		addState(new ObjectStateBumpSensor());
		addSensors();
	}

	protected CritterStateDrop collectWRobotState()
	{
		// find diffuse light sensors
		double left=0, right=0, middle=0;
		for (SimulatorObject obj : this.getChildren(ObjectStateDiffuseLightSensor.NAME))
		{
			ObjectStateDiffuseLightSensor sensor =
				(ObjectStateDiffuseLightSensor)obj.getState(ObjectStateDiffuseLightSensor.NAME);
			double v = sensor.getLightSensorValue();
			if (obj.getLabel().equals("L"))
				left = v;
			else if (obj.getLabel().equals("R"))
				right = v;
			else
				middle=v;
		}
		ObjectStateWRobotDynamics wrobot = (ObjectStateWRobotDynamics)getState(ObjectStateWRobotDynamics.NAME);
		Vector2D wvel = wrobot.getWheelAngleVel();
		Vector2D angle = wrobot.getWheelAngle();

		CritterStateDrop stateDrop = new CritterStateDrop();

		stateDrop.light[0] = (int)(CritterStateDrop.REALTOINTCONV*left);
		stateDrop.light[1] = (int)(CritterStateDrop.REALTOINTCONV*middle);;
		stateDrop.light[2] = (int)(CritterStateDrop.REALTOINTCONV*right);
		stateDrop.accel.x = (int)(CritterStateDrop.REALTOINTCONV*wvel.x);
		stateDrop.accel.y = (int)(CritterStateDrop.REALTOINTCONV*wvel.y);
		stateDrop.wheelAngle.x = (int)(CritterStateDrop.REALTOINTCONV*angle.x);
		stateDrop.wheelAngle.y = (int)(CritterStateDrop.REALTOINTCONV*angle.y);

		stateDrop.position=getPosition();
		stateDrop.direction=getDirection();
		
		return stateDrop;
	}

	/** Sets the direction of heading of the robot.
	 *  If the robots is implemented with ObjectStateWRobotDynamics
	 *  then the call is propagated to this state components
	 *  since the wheel angles then are coupled to the direction 
	 *  of heading. 
	 */
	public void setDirection( double dir )
	{
		super.setDirection(dir);
		ObjectStateWRobotDynamics wrobot = getObjectStateWRobot();
		if (wrobot!=null)
			wrobot.setDirection(dir);
	}


	final double trunc( double v, double low, double high )
	{
		return (v<low ? low: (v>high ? high : v));
	}

	public CritterStateDrop makeDrop()
	{
		// the real thing -- but just a temporary hack
		//		collectStateAndComputeControl();

		CritterStateDrop stateDrop = new CritterStateDrop();

		// Get the dynamics data
		ObjectStateDynamics dynData = 
			(ObjectStateDynamics)getState(SimulatorComponentDynamics.NAME);
		if (dynData != null)
		{
			ObjectState os = getState(ObjectStateWRobotDynamics.NAME);
			if (ObjectStateWRobotDynamics.class.isAssignableFrom(os.getClass()))
			{
				return collectWRobotState();
			}
			//Force f = dynData.getForceSum();
			// @@@ Needs to be converted into proper units (I believe in g's)
			//	      stateDrop.accel.x = (int)(f.vec.x * 1000);
			//	      stateDrop.accel.y = (int)(f.vec.y * 1000);
			Vector2D V=dynData.getVelocity();
			stateDrop.accel.x=(int) V.x;
			stateDrop.accel.y=(int) V.y;
		}
		// @@@ TODO (CS): Does this make sense? This is only called for an agent
		// that will typically have a number of SimulatorObject children,
		// one for each light sensor..
		if (getState(ObjectStateLightSensor.NAME) != null)
		{
			ObjectStateLightSensor sData = (ObjectStateLightSensor)
			getState(ObjectStateLightSensor.NAME);
			stateDrop.light[0] = (int)(sData.getLightSensorValue() * 100);
		}

		return stateDrop;
	}	
	
	/// @@@ TODO use this method everywhere where you need this state component
	ObjectStateWRobotDynamics getObjectStateWRobot()
	{
		ObjectStateDynamics dynData = 
			(ObjectStateDynamics)getState(SimulatorComponentDynamics.NAME);
		if (dynData != null)
		{
			ObjectState os = getState(ObjectStateWRobotDynamics.NAME);
			if (ObjectStateWRobotDynamics.class.isAssignableFrom(os.getClass()))
			{
				return (ObjectStateWRobotDynamics)os;
			}
		}
		return null;
	}

//	/** Finds the drivable state and forwards the call. */
//	public void setFromDrop(SimulatorDrop drop) {
//		ObjectStateWRobotDynamics wrobot = getObjectStateWRobot();
//		if (wrobot==null)
//			super.setFromDrop(drop);
//		else
//			wrobot.setFromDrop(drop);
//	}

	public Object clone()
	{
		SimulatorAgentCritterBot sa = new SimulatorAgentCritterBot(this.aLabel, this.aId);
		sa.copyFrom(this);
		return sa;
	}
}