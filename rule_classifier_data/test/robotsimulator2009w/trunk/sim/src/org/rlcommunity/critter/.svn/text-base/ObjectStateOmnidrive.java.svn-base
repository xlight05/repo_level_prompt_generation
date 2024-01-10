package org.rlcommunity.critter;

/**
  * ObjectStateOmnidrive
  *
  * A placeholder for control inputs for an omni-drive.
  *  This implementation uses target velocities (linear and angular). 
  *  Initialized from a "drop" (that may originate from the keyboard). 
  *  The control inputs persist for a duration of 
  *  aCommandDuration milliseconds.
  *
  * @author Marc G. Bellemare
  */

import org.rlcommunity.critter.Base.Force;
import org.rlcommunity.critter.Base.Vector2D;
import org.rlcommunity.critter.Drops.CritterControlDrop;
import org.rlcommunity.critter.Drops.SimulatorDrop;

import java.awt.Graphics;
import java.awt.Color;

public class ObjectStateOmnidrive implements ObjectState, Drivable
{
  protected static int aCommandDuration = 500; // [ms]
  public static final String NAME = Drivable.NAME;
  protected static final double forceCap = 10; // maximum force this PID can produce
  /** Target velocity */
  protected Vector2D aVel;
  /** Target angular velocity */
  protected double aAngVel;

  protected int aTimeLeftSinceCommand; 

  /** 'Persistent' data */
  /** How much the PID should compensate for velocity errors; for now
    * a single value, most likely to be changed when other parts are in place 
    */
  protected double pidForXY;
  protected double pidForTorque;

  public ObjectStateOmnidrive()
  {
    this(0.5*50);
  }

  public ObjectStateOmnidrive(double pPIDCoeff)
  {
    aVel = new Vector2D(0,0);
    aAngVel = 0;
    aTimeLeftSinceCommand = 0;

    pidForXY = pPIDCoeff;
    pidForTorque = pPIDCoeff/100.0;
  }

  /** Returns the target velocity for this omni-drive
    *
    * @return Target velocity
    */
  public Vector2D getVelocity() { return aVel; }

  /** Returns the target angular velocity for this omni-drive
    *
    * @return Target angular velocity
    */
  public double getAngVelocity() { return aAngVel; }

  // @@@ to copy pVel or not to copy?
  /** Sets the target velocity for this omni-drive
    * @param pVel New target velocity
    */
  public void setVelocity(Vector2D pVel) { aVel = new Vector2D(pVel); }

  /** Sets the target angular velocity for this omni-drive
    * @param pAngVel New target angular velocity
    */
  public void setAngVelocity(double pAngVel) { aAngVel = pAngVel; }

  /**
    * Sets the time-since-command counter.
    *
    * @param pSeconds The new number of ms since the last command was
    *  given.
    */
  public void setTime(int pMillis) { aTimeLeftSinceCommand = pMillis; }
  /** Resets the time-since-command counter.
    */
  public void clearTime() { aTimeLeftSinceCommand = 0; }

  public int getTime() { return aTimeLeftSinceCommand; }

  public double getPIDCoefficient() { return pidForXY; }

  /** Copies over the relevant data from the given drop. Should probably
    *  be moved somewhere else, e.g. into a separate object which transforms
    *  drops into states.
    *
    *  IMPORTANT: If this gets removed, clearTime() should be called after
    *   setting the drive values.
    *
    * @param pDrop The drop containing the data of interest
    */
  public void setFromDrop(SimulatorDrop drop)
  {
	CritterControlDrop pDrop = (CritterControlDrop)drop;
    // Clear the number of steps since the last command
    clearTime();

    // Based on the motor mode, set velocities appropriately
    switch (pDrop.motor_mode)
    {
      case XYTHETA_SPACE:
        // Units for the drop's x,y velocity are in cm/s, but for now 
        //  I'm putting them in m/s - don't forget to change it!
    	// @@@ TODO
        aVel = new Vector2D(pDrop.x_vel, pDrop.y_vel);
        // Units for the drop's angular velocity are in 1/(18PI) of a circle 
        //  per second, which is 1/9th of a radian per second
        aAngVel = pDrop.theta_vel/9.0;
//        System.out.printf("setFromDrop aAngVel=%f\n", aAngVel);
        break;
      case WHEEL_SPACE:
      default:
        System.err.println ("Unimplemented motor mode: "+pDrop.motor_mode);
        break;
    }
	aTimeLeftSinceCommand = aCommandDuration;
  }

  /** ObjectState interface */
  public String getName()
  {
    return NAME;
  }

  public Object clone()
  {
    ObjectStateOmnidrive newDrive = new ObjectStateOmnidrive();
    newDrive.copyFrom(this);
    return newDrive;
  }

  protected void copyFrom(ObjectStateOmnidrive org)
  {
    this.aVel = new Vector2D(org.aVel);
    this.aAngVel = org.aAngVel;
    this.aTimeLeftSinceCommand = org.aTimeLeftSinceCommand;
    this.pidForXY = org.pidForXY;
  }

  public void draw(Graphics g, SimulatorObject parent)
  {
      // protected Vector2D aVel;
      Vector2D objPos = parent.getPosition();
      double objDir = parent.getDirection();

      if (aVel.x != 0)
      {
	      Color tempC = g.getColor();
	      g.setColor(Color.red);
	      // We're only drawing the x component here!
	      int endX = (int)(objPos.x + aVel.x * Math.cos(objDir));
	      int endY = (int)(objPos.y + aVel.x * Math.sin(objDir));

	      g.drawLine((int)objPos.x, (int)objPos.y, endX, endY);
	      g.setColor(tempC);
      }
  }

  /** Provides a mean of clearing whatever data this ObjectState contains
    *  and resetting it to the default values. Meant to be used when 
    *  re-initializing a state.
    * 
    * For the Omnidrive, we do not clear the data per-se as we do want it 
    *  to be persistent across time steps.
    */
  public void clear()
  {
  }
  public void resetState() {
  }

  public SimulatorDrop makeDrop() {
	return null;
  }

  /** Method for computing the force that the omnidrive generates given
   *  a target velocity. Not quite there though, but at least it
   *  (for now) achieves the desired velocity, rather than creating some
   *  force.
   */
  protected Force simpleXYPid( Vector2D curVel )
  {
	  // @@@ TODO include a last error, as in pid_control, to make things smoother
	  Vector2D err = aVel.minus(curVel);
	  err.timesEquals(pidForXY);
	  //   final double TOL=1E-5;
	  //   if (Math.abs(err.x)>TOL || Math.abs(err.y)>TOL)
	  //   {
	  //   	System.out.printf("Force x=%f, y=%f\n", err.x,err.y);
	  //   }

	  // Finally, cap the maximum force this PID produces to forceCap
	  err.x = trunc( err.x, -forceCap, forceCap );
	  err.y = trunc( err.y, -forceCap, forceCap );

//	     final double TOL = 1E-5;
//	     if (Math.abs(err.x)>TOL || Math.abs(err.y)>=TOL)
//	     {
//	     	System.out.printf( "Force: x=%f, y=%f\n", err.x, err.y);
//	     }

	  return new Force(err);
  }
  
  final static double trunc( double val, double low, double high )
  {
	  return (val<low? low: (val>high?high:val));
  }

  /** Method for computing the torque necessary to achieve the desired
   * angular velocity. See comment in simpleXYPID above.
   */
  protected double simpleTPID(double curVel)
  {
//	  if (true)
//	  {
//		  double t = pidForTorque * (aAngVel - curVel);
//		  if (Math.abs(t)>1E-5)
//			  System.out.printf("aAngVel=%f, curVel=%f, torque=%f\t", aAngVel,curVel,t);
//	  }
//      System.out.printf("simpleTPID targVel=%f, curVel=%f\n", aAngVel, curVel);
	  return pidForTorque * (aAngVel - curVel);
  }

  public void update(Drivable p_nextDriveState, int delta) 
  {
	  ObjectStateOmnidrive nextDriveState = (ObjectStateOmnidrive)p_nextDriveState;
	  //  System.out.printf("implementControl aTimeLeftSinceCommand=%d\n", aTimeLeftSinceCommand);
	  if (aTimeLeftSinceCommand<=0)
		  return;
	  aTimeLeftSinceCommand -= delta;
	  // command too old?
	  if (aTimeLeftSinceCommand<0)
	  {
		  aTimeLeftSinceCommand = 0;
		  // then set targets to zero (this will force to stop the robot)
//		  System.out.print("Timeout\n");
		  nextDriveState.setVelocity(new Vector2D(0, 0));
		  nextDriveState.setAngVelocity(0);
	  }
	  else
	  {
		  nextDriveState.setVelocity( aVel );
		  nextDriveState.setAngVelocity( aAngVel );
	  }
  }

  public void update(SimulatorObject statState, ObjectStateDynamics dynState, int delta) 
  {
      // Produce a force to provide the required velocity
      double dir = statState.getDirection();
      Vector2D localVel = dynState.getVelocity().rotate(-dir);
      Force fPID = simpleXYPid(localVel);
      dynState.addForce(new Force(fPID.vec.rotate(dir)));
     
      // Produce a torque to achieve the required target angular velocity
      double torquePID = simpleTPID( dynState.getAngVelocity() );
      dynState.addTorque(torquePID);	  
  }

}
