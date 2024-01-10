package org.rlcommunity.critter.WRobotModels;

import org.rlcommunity.critter.Base.Force;
import org.rlcommunity.critter.Base.RigidBodyDynamicsData;
import org.rlcommunity.critter.Base.Vector2D;

/** TwoWheeledRobotModel implements a two-wheeled mobile robot,
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
public class TwoWheeledRobotModel {
	//	private static final double TOL_VEL = 1E-5; // below this velocity, friction better to stop the robot
//	private static final double rollingResistance = 0.25E-5; // rolling resistance constant [m]
//	private static final double LOW_VEL = 2.0*Math.PI/10; // ~0.6 [rad/sec]; this is 1/10 rps;
//	private static final double rollingResistance2 = 0.2E-2; // quadratic rolling resistance factor
//	private static final double dragConstant = 1E-5; // aerodynamic drag
//	// the robot is symmetric (left/right)
//	// P0 is the intersection of the axis of symmetry with
//	// the driving wheel axis.
//	// This (P0) is the point of reference.
	/** Displacement from each of the driving wheels to P0 */
	private double b = 5.0E-2; // [m]
	/** Displacement from point P0 to the center of gravity; positive values: heading direction(??) */
	private double d = 0.0E-2; // [m]
	/** Radius of the driving wheels */
	private double r = 2.0E-2; // [m]
	/** Mass of the robot without the wheels (+rotors) */
	private double mc  = 1.0; //[kg]
	/** Mass of the wheel and rotors */
	private double mw  = 0.05; // [kg] 
	/** Moment of inertia of the robot without the wheels, about the vertical axis going through P0 */
	//private double Ic  = 5.0/12.0*1E-2*mc; // [kg m^2]
        private double Ic  = mc*(b*b+b*b)/12.0; // [kg m2]; m(h2+w2)/12 is the m.i. of a rectangle with sides h,w around its center of mass
	/** Moment of inertia of the wheels about the wheel axis (horizontal axis) */
	//private double Iw  = 0.025; // [kg m^2]
        private double Iw  = mw*r*r/2.0; // [kg m2] m*r2/2 if the wheel is a solid, thin disc
    private static final double rollingResistance = 0.25E-5; // rolling resistance constant [m]
    private static final double LOW_VEL = 2.0*Math.PI/60; // ~0.104 [rad/sec]; this is 1 rpm;
    private static final double rollingResistance2 = 0.2E-2; // quadratic rolling resistance factor
    private static final double dragConstant = 1E-5; // aerodynamic drag
    // the robot is symmetric (left/right)
    // P0 is the intersection of the axis of symmetry with
    // the driving wheel axis.
    // This (P0) is the point of reference.
//    /** Displacement from each of the driving wheels to P0 */
//    private double b = 9.2E-2; // [m]
//    /** Displacement from point P0 to the center of gravity; positive values: heading direction(??) */
//    private double d = 0.0E-2;//7.0E-2; // [m]
//    /** Radius of the driving wheels */
//    private double r = 5.6E-2; // [m]
//    /** Mass of the robot without the wheels (+rotors) */
//    private double mc  = 0.4; //[kg]
//    /** Mass of the wheel and rotors */
//    private double mw  = 0.05*0.1; // [kg]
//    /** Moment of inertia of the robot without the wheels, about the vertical axis going through P0 */
//    private double Ic  = mc*(b*b+b*b)/12.0; // [kg m^2]; m(h^2+w^2)/12 is the m.i. of a rectangle with sides h,w around its center of mass
//    /** Moment of inertia of the wheels about the (horizontal) wheel axis */
//    private double Iw  = mw*r*r/2.0; // [kg m^2] m*r^2/2 if the wheel is a solid, thin disc
    
	/** The moment of inertia of each driving wheel and the motor rotor about a wheel diameter (vertical axis) */
	private double Im  = mw*r*r/4.0; // [kg m^2]; mw*r^2/4 if the wheel is a solid, thin disc
	private double wheelAngle1 = 0.0;
	private double wheelAngle2 = 0.0;
	private double wheelAngleVel1 = 0.0;
	private double wheelAngleVel2 = 0.0;
	private Vector2D wheelAngleAcc = new Vector2D();
	private Vector2D linearAcc = new Vector2D();
		
	private double torque1 = 0;//10 * r; // 1N test force at a distance of r from the center of the wheel
	private double torque2 = 0; //10 * 0.75 * r;

	// temporary data for computations
	double[][] S1 = new double[2][2];
	double[][] S1t = new double[2][2];
	double[][] S1d = new double[2][2];
	double[][] M11 = new double[2][2];
	double[][] M12 = new double[2][2];
	double[][] M21 = new double[2][2];
	double[][] M22 = new double[2][2];
	double[][] P = new double[2][2];
	Vector2D V = new Vector2D();
	Vector2D wha = new Vector2D();	

	RigidBodyDynamicsData rbdDynamics=null;
	public void setRigidBodyDynamics(RigidBodyDynamicsData rbdDynamics)
	{
		this.rbdDynamics = rbdDynamics;
	}
	public void resetRigidBodyDynamics() 
	{
		rbdDynamics = null;
	}
	public RigidBodyDynamicsData getRigidBodyDynamicsData() {
		return rbdDynamics;
	}	
	
	// some constants used in the calculations
	double c;
	double m;
	double I;
	double cb;
	double phi, cphi, sphi, phid; // orientation + cos and sin of it, and its time derivative
	
	/** This must be called to set the values of c,m,I,cb -- before using any other 'compute' methods */
	protected void computeConsts()
	{
		c   = r/(2.0*b);
		m = getMass(); //mc + 2 * mw;
		I = getMomentInertia(); //Ic + 2 * mw * b * b + 2 * Im;
		cb = r/2.0; // cause c*b= r/2.0 !	
	}
	
	/** Compute matrices S, M and the vector V */
	protected void computeSMV( Vector2D vel )
	{
		computePhiS1();
		S1t = Vector2D.transpose(S1);
		S1d[0][0] = S1d[0][1] = - cb * sphi * phid;
		S1d[1][0] = S1d[1][1] =   cb * cphi * phid;
		M11[0][0] = M11[1][1] = m; M11[0][1] = M11[1][0] = 0;
		M12[0][0] = - mc * c * d * sphi; M12[0][1] = -M12[0][0];
		M12[1][0] =   mc * c * d * cphi; M12[1][1] = -M12[1][0];
		M21 = M12;
		M22[0][0] = M22[1][1] = I * c*c + Iw; M22[1][0] = M22[0][1] = - I * c*c;

		V.x = - mc * d * phid * phid * cphi + dragConstant * vel.x; 
		V.y = - mc * d * phid * phid * sphi + dragConstant * vel.y;
	}
	
	/** Compute phi, cphi, sphi and S1 */
	protected void computePhiS1()
	{
		phi  = c * (wheelAngle1-wheelAngle2);
		cphi = Math.cos(phi); 
		sphi = Math.sin(phi);
		phid = c * (wheelAngleVel1 - wheelAngleVel2);
		S1[0][0] = S1[0][1] = cb * cphi;
		S1[1][0] = S1[1][1] = cb * sphi;
	}
		
	// P = S^T M S, wha = S^T M Sd + S^T V
	protected void computeSTMSwha()
	{
		P = Vector2D.mul(M11, S1); P = Vector2D.mul( S1t, P);
		Vector2D.addTo(P, Vector2D.mul(M21,S1));
		Vector2D.addTo(P, Vector2D.mul(S1t,M12)); // actually, Vector2D.mul(S1t,M12) == transpose(Vector2D.mul(M21,S1))
		Vector2D.addTo(P, M22);
		
		Vector2D wheelVel = new Vector2D(wheelAngleVel1, wheelAngleVel2); // actually, eta
		wha = wheelVel.timesLeft(S1d); 			// S1d * eta
		Vector2D wha2 = wha.timesLeft(M11);     // M11*S1d*eta
		wha2.plus(V);							// M11*S1d*eta+V
		wha2.timesLeftEquals(S1t);				// S1t* (M11*S1d*eta+V)
		wha.timesLeftEquals(M21);			    // M21 * S1d *eta
		wha.plus(wha2);							// S1t* (M11*S1d*eta+V) + M21 * S1d * eta
												// = S^T M Sd eta + S^T V [notation in paper]
	}

	/** Compute the velocity of the robot given some wheel angles and velocities 
	  */
	public Vector2D computeVel()
	{
		computeConsts();
		computePhiS1();
		Vector2D wheelVel = new Vector2D(wheelAngleVel1, wheelAngleVel2); // actually, eta
		return wheelVel.timesLeft(S1);
	}
	
	public Vector2D computeRefptVel(double refptdisp) {
		computeConsts();
		computePhiS1();
		Vector2D v = new Vector2D(wheelAngleVel1, wheelAngleVel2); 
		v.timesLeftEquals(S1);
		v.x -= refptdisp*phid*sphi;
		v.y += refptdisp*phid*cphi;
		return v;
	}
	// assumes that  computeRefptVel was called before this call
	public Vector2D computeRefptAcc(double refptdisp) {
		double phidd = c*(wheelAngleAcc.x-wheelAngleAcc.y);
		double a = refptdisp * phidd;
		double b = refptdisp * phid * phid;
		Vector2D v1 = new Vector2D(- a * sphi, a * cphi);
		v1.minusEquals(new Vector2D(b*cphi,b*sphi));
		v1.plusEquals(linearAcc);
		return v1;
	}
	
	/** Updates newDynData and newObj
	 * 
	 *  Performs Euler integration step.
	 *  
	 * @param force        External forces acting at the reference point of the robot
	 * @param nextTWRMData Next state to be updated
	 * @param dt		   Integration time (in seconds)
	 * @param length_scale Change of scales; 1 position unit (as used in objects) is how many meters
	 *
	 *  A requirement for calling this method is that getRigidBodyDynamicsData should return a non-null pointer;
	 *  so use this only in an appropriate derived class 
	 */
	public void update(Force 	   force
			, TwoWheeledRobotModel nextTWRMData
			, double 			   dt
			, double 			   length_scale
			) 
	{
//		System.out.printf("torque:  %f, %f\n", torque1, torque2);
		RigidBodyDynamicsData currentRBData = getRigidBodyDynamicsData();
		Vector2D vel = currentRBData.getVelocity();

		computeConsts();
		computeSMV( vel );
		// collision forces should come in here, too:
		V.x += force.vec.x;
		V.y += force.vec.y;
		
		// computing the wheel angular momentums is fun:
		computeSTMSwha(); // P=S^T M S, wha = ..
		Vector2D.invert(P);		
		wheelAngleAcc.x=torque1;
		wheelAngleAcc.y=torque2;
		wheelAngleAcc.minus(wha);
		wheelAngleAcc.timesLeftEquals(P);
		
		// stupid friction effect (should apply before multiplying with P;
		// however, we don't want to get in trouble because of the discontinuity
		// caused by friction, hence we 'apply' friction
		wheelAngleAcc.x += frictionTorque(wheelAngleVel1, m/2.0);
		wheelAngleAcc.y += frictionTorque(wheelAngleVel2, m/2.0);
		
		// Euler integration
		nextTWRMData.wheelAngle1 = wheelAngle1 + dt * wheelAngleVel1;
		nextTWRMData.wheelAngle2 = wheelAngle2 + dt * wheelAngleVel2;
		nextTWRMData.wheelAngleVel1 = wheelAngleVel1 + dt * wheelAngleAcc.x;
		nextTWRMData.wheelAngleVel2 = wheelAngleVel2 + dt * wheelAngleAcc.y;
		
		// normalize wheelAngles?
		
		Vector2D newVel = nextTWRMData.computeVel();

		RigidBodyDynamicsData nextRBData = nextTWRMData.getRigidBodyDynamicsData();
		linearAcc = new Vector2D(nextRBData.getVelocity());
		linearAcc.timesEquals(-1.0);
		linearAcc.plusEquals(newVel);
		linearAcc.timesEquals(1.0/dt);
		
		nextRBData.setVelocity(newVel);
		nextRBData.setAngVelocity(nextTWRMData.phid);
        Vector2D curPos = currentRBData.getPosition();
        if (curPos==null){
        	System.out.println("HERE");
        }
		nextRBData.setPosition(new Vector2D( curPos.x + dt * newVel.x /length_scale
				   					       , curPos.y + dt * newVel.y /length_scale)
        );
		nextRBData.setDirection(nextTWRMData.phi);		
	}
	
	public Vector2D feedbacklinControl( Vector2D u, double L )
	{
//		System.out.printf("u:  %f, %f\n",u.x,u.y);
		
		// Step 1: from u to mu
		
		// compute matrices Phi, Phid (Phid is the time derivative of Phi)
		double[][] Phi = new double[2][2];
		double[][] Phid = new double[2][2];

		computeConsts();
		// we do not compensate for the drag coming from the 
		// velocity when computing the control; so prepare phi, phid and matrices S, M
		computeSMV( new Vector2D(0,0) ); 
		
		double cL = c*L;
		double cbc = cb*cphi, cbs = cb*sphi;
		double cLc = cL*cphi, cLs = cL*sphi;
		Phi[0][0] = cbc - cLs; Phid[0][0] = phid * (-cbs -cLc);
		Phi[0][1] = cbc + cLs; Phid[0][1] = phid * (-cbs +cLc);
		Phi[1][0] = cbs + cLc; Phid[1][0] = phid * ( cbc -cLs);
		Phi[1][1] = cbs - cLc; Phid[1][1] = phid * ( cbc +cLs);
		
		Vector2D.invert(Phi);
		Vector2D eta = new Vector2D(wheelAngleVel1, wheelAngleVel2); 
		u.minus( eta.timesLeft(Phid));
		Vector2D mu = u.timesLeft(Phi);

//		System.out.printf("mu: %f, %f\n",mu.x,mu.y);
		
		// Step 2: from mu to tau (the torques)
		// P = S^T M S, wha = S^T M Sd + S^T V
		computeSTMSwha();
		
		// tau = wha + P mu
		Vector2D tau = wha.plus(mu.timesLeft(P)); 
//		System.out.printf("tau: %f, %f\n",tau.x,tau.y);
		return tau;		
	}

	private double frictionTorque(double vel, double massSupported) {
		double velabs = Math.abs(vel);
		double torque = rollingResistance*massSupported;
		if (velabs<LOW_VEL)// && velabs>TOL_VEL)
		{
			// linear response, bad as it is..
			torque *= vel/LOW_VEL;
		}
		else
			torque *= Math.signum(vel);
		torque += Math.signum(vel)*vel*vel*rollingResistance2;
		return -torque;
	}

	public void resetState()
	{
		RigidBodyDynamicsData rbd = getRigidBodyDynamicsData();
		if (rbd!=null)
			rbd.resetState();
		wheelAngleVel1 = wheelAngleVel2 = 0;
	}
	
	public Object clone()
	{
		TwoWheeledRobotModel newCopy = new TwoWheeledRobotModel();
		newCopy.copyFrom(this);
		return newCopy;
	}
		
    protected void copyFrom(TwoWheeledRobotModel dyn) {
        this.b = dyn.b; 
    	this.d = dyn.d; 
    	this.r = dyn.r; 
    	this.mc  = dyn.mc; 
    	this.mw  = dyn.mw; 
    	this.Ic  = dyn.Ic; 
    	this.Iw  = dyn.Iw; 
    	this.Im  = dyn.Im;  
    	
    	this.torque1 = dyn.torque1;
        this.torque2 = dyn.torque2;
        this.wheelAngle1 = dyn.wheelAngle1;
        this.wheelAngle2 = dyn.wheelAngle2;
        this.wheelAngleVel1 = dyn.wheelAngleVel1;
        this.wheelAngleVel2 = dyn.wheelAngleVel2;
        if (dyn.rbdDynamics!=null)
        {
        	// RigidBodyDynamicsData is treated like a real pointer;
        	// no deep copy! (cut circles)
        	rbdDynamics = (RigidBodyDynamicsData) dyn.rbdDynamics.shallowClone();
        }
    }
	public Vector2D getWheelAngle() {
    	return new Vector2D( wheelAngle1, wheelAngle2 );
	}

    public Vector2D getWheelAngleVel()
    {
    	return new Vector2D( wheelAngleVel1, wheelAngleVel2 );
    }

	public void setTorques(double t1, double t2) {
		torque1 = t1;
		torque2 = t2;		
	}

	/** Set the wheel angles appropriately so that the direction of heading is dir */
	public void setDirection(double dir) {
		double c   = r/(2.0*b);
		this.wheelAngle1 = this.wheelAngle2 + dir/c;
	}

	// Setters, getters
	
	public void setWheelState( double wa1, double wa2, double wav1, double wav2 )
	{
		wheelAngle1 = wa1;
		wheelAngle2 = wa2;
		wheelAngleVel1 = wav1;
		wheelAngleVel2 = wav2;
	}
	
	
	public double getMass() { return mc + 2*mw; }
	public double getMomentInertia() { return Ic + 2 * mw * b * b + 2 * Im; }
 	
	public double getWheelDisplacement() { return b; }
	public void   setWheelDisplacement( double b ) { this.b = b; }
	public double getRefPtToCGDisplacement() { return d; }
	public void   setRefPtToCGDisplacement( double d ) { this.d = d; }
	public double getWheelRadius() { return r; }
	public void   setWheelRadius( double r ) { this.r = r; }
	public double getMassWithoutWheels() { return mc; }
	public void   setMassWithoutWheels( double mc ) { this.mc = mc; }
	public double getMassOfWheels() { return mw; }
	public void   setMassOfWheels( double mw ) { this.mw = mw; }
	public double getMInertiaWithoutWheels() { return Ic; }
	public void   setMInertiaWithoutWheels( double Ic ) { this.Ic = Ic; }
	public double getMInertiaOfWheels() { return Iw; }
	public void   setMInertiaOfWheels( double Iw ) { this.Iw = Iw; }
	public double getMInertiaOfWheelsAboutDiameter() { return Im; }
	public void   setMInertiaOfWheelsAboutDiameter( double Im ) { this.Im = Im; }
	
	public double getWheelAngle1() { return wheelAngle1; }
	public void   setWheelAngle1( double wheelAngle1 ) { this.wheelAngle1 = wheelAngle1; }
	public double getWheelAngle2() { return wheelAngle2; }
	public void   setWheelAngle2( double wheelAngle2 ) { this.wheelAngle2 = wheelAngle2; }
	public double getWheelAngleVel1() { return wheelAngleVel1; }
	public void   setWheelAngleVel1( double wheelAngleVel1 ) { this.wheelAngleVel1 = wheelAngleVel1; }
	public double getWheelAngleVel2() { return wheelAngleVel2; }
	public void   setWheelAngleVel2( double wheelAngleVel2 ) { this.wheelAngleVel2 = wheelAngleVel2; }

	public double getTorque1() { return torque1; }
	public void   setTorque1( double torque1 ) { this.torque1 = torque1; }
	public double getTorque2() { return torque2; }
	public void   setTorque2( double torque2 ) { this.torque2 = torque2; }

}
