package org.rlcommunity.critter;

import org.rlcommunity.critter.Base.Force;
import org.rlcommunity.critter.Base.Vector2D;

/**
 * SimulatorComponentWheel
 *
 * This SimulatorComponent computes torques and forces arising from motors
 *  and wheels attached to the motors. It also updates the angular velocities
 *  of the wheels and motors.
 *
 * @author csaba
 */
public class SimulatorComponentWheel implements SimulatorComponent {

    public static final String NAME = "wheel";
	private double time = 0;// for debug 


    public SimulatorComponentWheel() {
    }

    /** Applies physics to the current state to obtain the next state.
     * Roughly speaking, the following happens (in order):
     * 
     *  1. Go through all the objects that have wheels and motors
     *  attached to them. For each such object, the resulting forces
     *  and torques are calculated based on the state of the wheels and motors.
     *  Further, the state of wheels and motors is also updated (the updates
     *  are done on pNext). However, all the calculated forces and torques 
     *  are stored in pCurrent.
     * 
     *  @param pCurrent The current state of the system (modified by this method)
     *  @param pNext    The next state of the system (also modified by this method)
     *  @param delta    The amount of time (in ms) between the current and
     *         next states.
     */
    public void apply(SimulatorState pCurrent, SimulatorState pNext, int delta) {
        // Walk through all the objects in the current state that can be 
        //  affected by physics
        for (SimulatorObject obj : pCurrent.getObjects()) {
            // If no physics data, ignore this object
            ObjectState os = obj.getState(SimulatorComponentDynamics.NAME);
            if (os == null) {
                continue;
            }
            ObjectStateDynamics dynData = (ObjectStateDynamics) os;
            // traverse all the wheels and motors in this object
            for (SimulatorObject wheel : obj.getChildren(SimulatorComponentWheel.NAME)) {
            	os = wheel.getState(ObjectStateMotor.NAME);
            	if (os==null) {
            		continue;
            	}
            	ObjectStateMotor motorState = (ObjectStateMotor)os;

            	os = wheel.getState(SimulatorComponentWheel.NAME);
            	if (os==null) {
            		continue;
            	}
            	ObjectStateWheel wheelState = (ObjectStateWheel)os;
            	// what we will update:
                SimulatorObject newWheel = pNext.getObject(wheel);
                // primitive friction
                ObjectStateWheel newWheelState =
                	(ObjectStateWheel) newWheel.getState(SimulatorComponentWheel.NAME);
                double dt = delta/1000.0; // elapsed time in seconds
            	// physics:
            	// http://regedit.gamedev.pl/Mirror/Car%20Physics%20for%20Games/Car%20Physics%20for%20Games.html
            	// http://www.myphysicslab.com/collision.html (rigid body collisions)
            	// http://www.gamedev.net/reference/programming/features/2dcarphys/page4.asp
            	// http://www.marts100.com/eqmotion.htm
            	// http://www.school-for-champions.com/science/friction_rolling_start.htm
            	// http://www.wsu.edu/~jtd/Physics205/Chap4/Chap4.htm
            	// http://www.mathworks.com/access/helpdesk/help/toolbox/physmod/drive/index.html?/access/helpdesk/help/toolbox/physmod/drive/tire.html&http://www.google.ca/search?hl=en&client=firefox-a&rls=org.mozilla:en-US:official&pwst=1&q=wheel+motion+friction+torque&start=60&sa=N
            	
            	double wheelAngVelocity = wheelState.getAngVelocity();
            	double wheelDirection = wheel.getDirection();
            	// update the motor angular velocity based on the current angular velocity of the wheel
            	motorState.setAngVelocity( wheelAngVelocity/wheelState.getGearRatioFromMotorToWheel() );
            	
            	// compute the motor torque
            	double torqueDrive = 1E-1 * motorState.getTorque() * wheelState.getGearRatioFromMotorToWheel() * wheelState.getTransMissionEfficiency();
//            	if (torqueDrive!=0)
//            		torqueDrive = 0.0051; // heck @@@ TODO

            	Vector2D axis_long = Vector2D.unitVector(wheelDirection);
            	Vector2D axis_side = Vector2D.unitVector(wheelDirection+Math.PI/2);
            	
            	// compute traction forces
            	
            	// first compute the components of the speed of the car in the direction
            	// of the wheel (the longitudinal component) and in the orthogonal direction;
            	// start with translation speed of car.

            	// for this, first compute the speed pertaining to the chassis rotating.
            	// we need the vector pointing from the CG to the wheel in the global coordinate frame
            	// (since velocity and forces are always given in the global coordinate frame)
        		Vector2D wpos = wheel.getLocalPosition().rotate(obj.getDirection()).times(SimulatorMain.length_scale);
        		// wheel axes:
        		Vector2D wlong_axis = wpos; 
        		wlong_axis.normalize(); 
        		Vector2D wlat_axis  = wlong_axis.rotate(Math.PI/2);
        		
        		// how much the wheel moves due to rotation
            	double v_rot = dynData.getAngVelocity() * wpos.length();            	            	
            	Vector2D vel_atwheel = wlat_axis.times(v_rot);
            	// compute total velocity at wheel
            	vel_atwheel.plusEquals(dynData.getVelocity());
            	// longitudinal and lateral velocities at the wheel
            	double v_long = vel_atwheel.lengthInDirection(wheelDirection);
            	double v_lat = vel_atwheel.lengthInDirection(wheelDirection+Math.PI/2.0);
            	double v_long_abs = Math.abs(v_long);
            	// wheel (effective) radius
            	double wr = wheelState.getRadius();
            	// velocity of the patch of the wheel touching the ground
        		double v_wheel = wheelAngVelocity * wr;
        		// how much faster the car wants to go than the wheel (longitudinal direction)
        		double v_slip_long =  v_long-v_wheel;
        		
        		// calculate contact point slip ratio
        		double u = wheelState.getDeformationLong(); // diff. of wheel and contact point slip
        		
        		double vlow = wheelState.getLowSpeedLimit();
        		double kvlow0 = wheelState.getForcePerSpeed();        // [N/(m/s)]; how much force generated per unit speed
        		double blendfactor0 = 0.5*(v_long_abs<=vlow ? 1.0+Math.cos(Math.PI*v_long_abs/vlow) : 0.0 );
        		double blendfactor = 1.0;
        		double kvlow = kvlow0 * blendfactor;
        		
        		// hacking with u; compute the equilibrium soln (almost)
        		double relaxationLength = wheelState.getRelaxationLength();
        		double forcePerSlip = wheelState.getForcePerSlip();
        		double u1 = - relaxationLength* ((1.0-blendfactor) * v_slip_long/(v_long_abs==0?1.0:v_long_abs) + kvlow/forcePerSlip * v_slip_long); 
        		
        		double slipRatio = u/relaxationLength // normal speeds
        				+ forcePerSlip * v_slip_long; // accounts for low speeds

        		// longitudinal force exerted by the tire on the wheel
        		double loadBoost = wheelState.getLoad()/wheelState.getRatedLoad();
        		double F_x0 = 1E2*wheelState.getTireLongStiffness() * u1 * loadBoost;  
        		double mass = wheelState.getLoad()/10.0;
        		// the force corresponding to perfect traction:
        		double F_x_lim = torqueDrive/(wheelState.getMomentInertia()/(wr*mass)+wr);
        		// blend F_x_lim and F_x:
        		double F_x = F_x0;
        		if (Math.abs(F_x)>Math.abs(F_x_lim)) // exerted force can never exceeed F_x_lim:
        			F_x = F_x_lim;
        		F_x = blendfactor0 * F_x_lim + (1.0-blendfactor0)*F_x;        		
        		{
        			System.out.printf("%f(%f): v_long=%f,v_wheel=%f,v_slip=%f, blend=%f, F_x=%f,F_x0=%f, F_x_lim=%f\n"
        					,time/1000.0,1.0*delta,v_long,v_wheel,v_slip_long,blendfactor0,F_x,F_x0,F_x_lim);
        		}
        		// update deformation
        		newWheelState.setDeformationLong( u + dt * (-slipRatio*v_long_abs-v_slip_long) );
        		
        		// compute the same for the lateral direction
        		double v_lat_abs = Math.abs(v_lat);
        		u = wheelState.getDeformationLat();
        		blendfactor = 0.5*(v_lat_abs<=vlow ? 1.0+Math.cos(Math.PI*v_lat_abs/vlow) : 0.0 );
        		kvlow =  kvlow0 * (v_long_abs<=vlow ? 1.0+Math.cos(Math.PI*v_lat_abs/vlow) : 0.0 );
        		u1 = -relaxationLength* ((1.0-blendfactor) * v_lat/(v_lat_abs==0?1.0:v_lat_abs) + kvlow/forcePerSlip * v_lat); 
        		slipRatio = u/relaxationLength 	  // normal speeds
				    + kvlow/forcePerSlip * v_lat; // accounts for low speeds

        		// lateral force exerted by the tire on the wheel
        		double F_y = wheelState.getTireLongStiffness() * u1;
		
        		// update deformation
        		newWheelState.setDeformationLat( u + dt * (-slipRatio*v_lat_abs-v_lat) );
        		
        		// update wheel speed
        		double torqueTraction = wr * F_x;
            	// temporary simulation of a braked wheel
            	if (torqueDrive==0) // braked wheel
            	{
            	    //torqueDrive = - 0.001 * wheelAngVelocity; // braking the wheel
            		//if (torqueDrive>0.16) torqueDrive = 0.16; else if (torqueDrive<-0.16) torqueDrive = -0.16;
            		torqueDrive = torqueTraction; //perfect cancellation
            	}
        		// limit the torque traction by the input
        		if ((torqueDrive>0 && torqueTraction>torqueDrive) || (torqueDrive<0 && torqueTraction<torqueDrive))
        		{
        			torqueTraction = torqueDrive;
        			F_x = wr * torqueTraction;
        		}
        		double torqueTotal = torqueDrive - torqueTraction;
        		
            	wheelState.setTorque(torqueTotal);
//            	{
//            		System.out.printf("torqueDrive=%f, wheelAVel=%f, v_rot=%f, v_long=%f, v_side=%f, wvel=%f, slipRatio=%f\n\tforceTractLong=%f, forceTractSide=%f\ttorqueTract=%f, torqueTot=%f\n"
//            				         ,torqueDrive,wheelAngVelocity,  v_rot,    v_long,   v_lat, v_wheel,slipRatio,   F_x,F_y,torqueTraction, torqueTotal);
//            	}
                newWheelState.setAngVelocity(wheelAngVelocity + dt * torqueTotal / wheelState.getMomentInertia());            	
            	
            	// add the resulting traction force to the object's state
            	Vector2D vForceTraction = axis_long.times(F_x);
            	vForceTraction.plus(axis_side.times(F_y));
            	
            	dynData.addForce( new Force(vForceTraction) );
            	// add the resulting torque to the object's state
        		double t = wpos.crossProduct(vForceTraction);
            	dynData.addTorque( t );
//            	{
//            		System.out.printf("wheel-pos-x=%f,wheel-pos-y=%f,trac-x=%f,trac-y=%f,torque=%f\n"
//            				         , lp.x, lp.y, vForceTraction.x, vForceTraction.y, t );
//            	}
            }
        }
        time += delta;
    }

	/** Applies physics to the current state to obtain the next state.
	     * Roughly speaking, the following happens (in order):
	     * 
	     *  1. Go through all the objects that have wheels and motors
	     *  attached to them. For each such object, the resulting forces
	     *  and torques are calculated based on the state of the wheels and motors.
	     *  Further, the state of wheels and motors is also updated (the updates
	     *  are done on pNext). However, all the calculated forces and torques 
	     *  are stored in pCurrent.
	     * 
	     *  @param pCurrent The current state of the system (modified by this method)
	     *  @param pNext    The next state of the system (also modified by this method)
	     *  @param delta    The amount of time (in ms) between the current and
	     *         next states.
	     */
	    public void apply2(SimulatorState pCurrent, SimulatorState pNext, int delta) {
	        // Walk through all the objects in the current state that can be 
	        //  affected by physics
	        for (SimulatorObject obj : pCurrent.getObjects()) {
	            // If no physics data, ignore this object
	            ObjectState os = obj.getState(SimulatorComponentDynamics.NAME);
	            if (os == null) {
	                continue;
	            }
	            ObjectStateDynamics dynData = (ObjectStateDynamics) os;
	            double mass = dynData.getMass();
	            // traverse all the wheels and motors in this object
	            for (SimulatorObject wheel : obj.getChildren(SimulatorComponentWheel.NAME)) {
	            	os = wheel.getState(ObjectStateMotor.NAME);
	            	if (os==null) {
	            		continue;
	            	}
	            	ObjectStateMotor motorState = (ObjectStateMotor)os;
	
	            	os = wheel.getState(SimulatorComponentWheel.NAME);
	            	if (os==null) {
	            		continue;
	            	}
	            	ObjectStateWheel wheelState = (ObjectStateWheel)os;
	            	// physics:
	            	// http://regedit.gamedev.pl/Mirror/Car%20Physics%20for%20Games/Car%20Physics%20for%20Games.html
	            	// http://www.myphysicslab.com/collision.html (rigid body collisions)
	            	// http://www.gamedev.net/reference/programming/features/2dcarphys/page4.asp
	            	// http://www.marts100.com/eqmotion.htm
	            	// http://www.school-for-champions.com/science/friction_rolling_start.htm
	            	// http://www.wsu.edu/~jtd/Physics205/Chap4/Chap4.htm
	            	// http://www.mathworks.com/access/helpdesk/help/toolbox/physmod/drive/index.html?/access/helpdesk/help/toolbox/physmod/drive/tire.html&http://www.google.ca/search?hl=en&client=firefox-a&rls=org.mozilla:en-US:official&pwst=1&q=wheel+motion+friction+torque&start=60&sa=N
	            	
	            	double wheelAngVelocity = wheelState.getAngVelocity();
	            	double wheelDirection = wheel.getDirection();
	            	// update the motor angular velocity based on the current angular velocity of the wheel
	            	motorState.setAngVelocity( wheelAngVelocity/wheelState.getGearRatioFromMotorToWheel() );
	            	
	            	// compute the motor torque
	            	double torqueDrive =  motorState.getTorque() * wheelState.getGearRatioFromMotorToWheel() * wheelState.getTransMissionEfficiency();
	//            	if (torqueDrive!=0)
	//            		torqueDrive = 0.0051; // heck @@@ TODO
	
	            	Vector2D axis_long = Vector2D.unitVector(wheelDirection);
	            	Vector2D axis_side = Vector2D.unitVector(wheelDirection+Math.PI/2);
	            	
	            	// compute traction forces
	            	
	            	// first compute the components of the speed of the car in the direction
	            	// of the wheel (the longitudinal component) and in the orthogonal direction;
	            	// start with translation speed of car.
	
	            	// for this, first compute the speed pertaining to the chassis rotating.
	            	// we need the vector pointing from the CG to the wheel in the global coordinate frame
	            	// (since velocity and forces are always given in the global coordinate frame)
//	            	final double TOL = 1E-6;
	        		Vector2D lp = wheel.getLocalPosition().rotate(obj.getDirection()).times(SimulatorMain.length_scale);
	        		Vector2D lp_lat = lp.rotate(Math.PI/2); 
	        		lp_lat.normalize();
	            	double v_rot = dynData.getAngVelocity() * lp.length();
	            	            	
	            	Vector2D vel_atwheel = lp_lat.times(v_rot);
	            	vel_atwheel.plusEquals(dynData.getVelocity());
	            	
	            	double v_long = vel_atwheel.lengthInDirection(wheelDirection);
	            	double v_side = vel_atwheel.lengthInDirection(wheelDirection+Math.PI/2.0);
	            	
	            	double wr = wheelState.getRadius();
	            	// velocity of the patch of the wheel touching the ground
	        		double v_wheel = wheelAngVelocity * wr;
	        		// how much faster the wheel wants to go than the car is going
	        		double v_diff = v_wheel - v_long;
	        		
	        		// simple string model (good at low speeds) for the resulting traction forces:
	        		// - longitudinal traction resulting from friction is just proportional to v_diff
	        		// - except when v_diff==0 whereas we will maintain the rolling state
	        		final double beta = 0.5;
	        		double c = mass/(2.0*beta*wheelState.getWheelMass()+mass);
	            	double forceTractionLong = c*torqueDrive/wr;
	            	if (Math.abs(v_diff)>Math.abs(forceTractionLong)) {
	            		forceTractionLong = v_diff;
	            	}
	//            	// or use a friction model:
	//            	double frictionStatic = 0.1;
	//            	double frictionKinetic = 0.08; //http://en.wikipedia.org/wiki/Rolling_resistance
	//            									// Typical BMX bicycle tire used for solar cars
	//            	double forceTractionLongStatic = wheelState.getLoad() * frictionStatic;
	////            	if (forceTractionLongStatic * wr>Math.abs(torqueDrive))
	////            		forceTractionLongStatic = torqueDrive/wr;
	//            	double forceTractionLongKinetic = wheelState.getLoad() * frictionKinetic * Math.signum(v_diff);
	////            	if (Math.abs(forceTractionLongKinetic)>Math.abs(forceTractionLongStatic)) // the kinetic friction force is always smaller..
	////            		forceTractionLongKinetic = forceTractionLongStatic;
	//            	
	//            	forceTractionLong = forceTractionLongKinetic;
	//            	if (v_diff==0) 
	//            	{
	//            		forceTractionLong = forceTractionLongStatic;
	//                	if (forceTractionLong * wr > Math.abs(torqueDrive)) 
	//                	{
	//                		// all forces, torques cancel (too small torqueDrive; forces do not overcome static friction)
	//                		forceTractionLong = 0;
	//                		torqueDrive = 0;
	//                	}
	//            	}
	            		
	            	// - side (lateral) traction is larger (larger resistance against sideway movements)
	            	double forceTractionSide = -100.0 * v_side; 
	            	            	
	            	double slipRatio = 0.03; // simple implementation of the Pacejka model
	//            	if (Math.abs(v_long)<1.0)        		
	//            	if (v_long>TOL || v_long<-TOL) {
	//            		slipRatio = v_diff/Math.abs(v_long);
	//            	}
	//            	if (slipRatio>0.06) slipRatio=0.06;
	//            	if (slipRatio<-0.06) slipRatio=-0.06;
	//            	forceTraction = wheelState.getLoad() * //wheelState.getSlipToForceCoeff()*slipRatio;
	//            				Math.min( wheelState.getSlipToForceCoeff()*Math.abs(slipRatio), wheelState.getMaxForce() )
	//            				* Math.signum(slipRatio);
	            	
	            	// @@@ TODO test out the other model for higher speeds
	
	            	// compute the torque exercised by the ground on the wheel:
	            	double torqueTraction = -forceTractionLong * wr;
	//            	if (Math.signum(torqueDrive)!=Math.signum(torqueDrive+torqueTraction)) // too large a traction
	//            	{
	//            		torqueTraction = - torqueDrive;
	//            		forceTractionLong = torqueTraction / wr;
	//            	}
	            	
	            	// temporary simulation of a braked wheel
	            	if (torqueDrive==0) // braked wheel
	            	{
	            		forceTractionLong *= 100.0; // stopped wheel -> huge friction
	            	    //torqueDrive = - 0.001 * wheelAngVelocity; // braking the wheel
	            		//if (torqueDrive>0.16) torqueDrive = 0.16; else if (torqueDrive<-0.16) torqueDrive = -0.16;
	            		torqueDrive = - torqueTraction; //perfect cancellation
	            	}
	            	
	            	double torqueTotal = torqueTraction + torqueDrive; // @@@ TODO: add friction here
	            	wheelState.setTorque(torqueTotal);
	            	{
	            		System.out.printf("torqueDrive=%f, wheelAVel=%f, v_rot=%f, v_long=%f, v_side=%f, wvel=%f, slipRatio=%f\n\tforceTractLong=%f, forceTractSide=%f\ttorqueTract=%f, torqueTot=%f\n"
	            				         ,torqueDrive,wheelAngVelocity,  v_rot,    v_long,   v_side, v_wheel,slipRatio,   forceTractionLong,forceTractionSide,torqueTraction, torqueTotal);
	            	}
	            	
	            	// add the resulting traction force to the object's state
	            	Vector2D vForceTraction = axis_long.times(forceTractionLong);
	            	vForceTraction.plus(axis_side.times(forceTractionSide));
	            	
	            	dynData.addForce( new Force(vForceTraction) );
	            	// add the resulting torque to the object's state
	        		double t = lp.crossProduct(vForceTraction);
	            	dynData.addTorque( t );
	            	
	//            	{
	//            		System.out.printf("wheel-pos-x=%f,wheel-pos-y=%f,trac-x=%f,trac-y=%f,torque=%f\n"
	//            				         , lp.x, lp.y, vForceTraction.x, vForceTraction.y, t );
	//            	}
	
	            	// do the integration of the angular velocity of the wheel
	            	// nextWheelState.set
	                SimulatorObject newWheel = pNext.getObject(wheel);
	                // primitive friction
	                ObjectStateWheel newWheelState =
	                	(ObjectStateWheel) newWheel.getState(SimulatorComponentWheel.NAME);
	                double dt = delta/1000.0; // elapsed time in seconds
	                newWheelState.setAngVelocity(wheelAngVelocity + dt * torqueTotal / wheelState.getMomentInertia());            	
	            }
	        }
	    }
}
