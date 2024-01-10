package org.rlcommunity.critter;

/**
  * ObjectStateMotor
  *
  * Defines what constitutes the state of a motor:
  *  The state is just defined as the angular velocity, the input to the motor
  *  and whatever else is needed to specify the behavior of the motor.
  *  The input to the motor is initialized from a "drop"
  *  (that may originate from the keyboard). 
  *
  * @author csaba
  */

import org.rlcommunity.critter.Drops.CritterControlDrop;

import java.awt.Graphics;
//import java.awt.Color;

public class ObjectStateMotor implements ObjectState
{
  public static final String NAME = SimulatorComponentWheel.NAME+"motor";
  /** Angular velocity */
  protected double aAngVel;
  /** Input to the motor */
  protected double aInput;
  /** Torque on the shaft */
  protected double aTorque;

  protected int aTimeSinceCommand; 

//  /** 'Persistent' data */
//  /** How much the PID should compensate for velocity errors; for now
//    * a single value, most likely to be changed when other parts are in place 
//    */
//  protected double pidForXY;
  
  public ObjectStateMotor()
  {
	  this(0);
  }
  
  public ObjectStateMotor( double defaultInput )
  {
    aAngVel = aTorque = 0;
    aTimeSinceCommand = 0;
  
    aInput = defaultInput; // simula

//    pidForXY = pPIDCoeff;
  }

  /** Returns the angular velocity for this motor
    *
    * @return Angular velocity
    */
  public double getAngVelocity() { return aAngVel; }

  /** Sets the angular velocity for this motor
    * @param pAngVel New angular velocity
    */
  public void setAngVelocity(double pAngVel) { 
	  aAngVel = pAngVel;
	  aTorque = getMaxTorque() * aInput;
  }
  
  protected double aNominalLoad = 16.7E-2; // N m
  protected double aNomMechPower = 2.03; // W
  protected double aNomAngVel = 117 * (2 * 3.14159265) / 60.0; // rad/sec; ~ 117 rpm
  
  /** Returns the motor's maximum torque at aNominalLoad,
   *  and given the current angular velocity
   * @return motor's maximum torque
   */
  public double getMaxTorque() {
	  return aNomMechPower/aNomAngVel;
  }

  /**
    * Sets the time-since-command counter.
    *
    * @param pSeconds The new number of ms since the last command was
    *  given.
    */
  public void setTime(int pMillis) { aTimeSinceCommand = pMillis; }
  /** Resets the time-since-command counter.
    */
  public void clearTime() { aTimeSinceCommand = 0; }

  public int getTime() { return aTimeSinceCommand; }

//  public double getPIDCoefficient() { return pidForXY; }

  /** Copies over the relevant data from the given drop. Should probably
    *  be moved somewhere else, e.g. into a separate object which transforms
    *  drops into states.
    *
    *  IMPORTANT: If this gets removed, clearTime() should be called after
    *   setting the drive values.
    *
    * @param pDrop The drop containing the data of interest
    */
  public void setFromDrop(CritterControlDrop pDrop)
  {
    // Clear the number of steps since the last command
    clearTime();

    // Based on the motor mode, set velocities appropriately
    switch (pDrop.motor_mode)
    {
      case WHEEL_SPACE:
    	aInput = pDrop.m100_vel;
    	break;
      case XYTHETA_SPACE:
      default:
        System.err.println ("Unimplemented motor mode: "+pDrop.motor_mode);
        break;
    }
  }


  /** ObjectState interface */
  public String getName()
  {
    return NAME;
  }

  public Object clone()
  {
    ObjectStateMotor newDrive = new ObjectStateMotor();
    newDrive.copyFrom(this);
    
    return newDrive;
  }

  protected void copyFrom(ObjectStateMotor org)
  {
    this.aAngVel = org.aAngVel;
    this.aInput = org.aInput;
    this.aTorque = org.aTorque;
    this.aTimeSinceCommand = org.aTimeSinceCommand;
    this.aNomAngVel = org.aNomAngVel;
    this.aNominalLoad = org.aNominalLoad;
    this.aNomMechPower = org.aNomMechPower;

//    this.aPIDCoefficient = org.aPIDCoefficient;
  }

  public void draw(Graphics g, SimulatorObject parent)
  {
      // protected Vector2D aVel;
//      Vector2D objPos = parent.getPosition();
//      double objDir = parent.getDirection();
//
//      if (aVel.x != 0)
//      {
//	      Color tempC = g.getColor();
//	      g.setColor(Color.red);
//	      // We're only drawing the x component here!
//	      int endX = (int)(objPos.x + aVel.x * Math.cos(objDir));
//	      int endY = (int)(objPos.y + aVel.x * Math.sin(objDir));
//
//	      g.drawLine((int)objPos.x, (int)objPos.y, endX, endY);
//	      g.setColor(tempC);
//      }
  }

  /** Provides a mean of clearing whatever data this ObjectState contains
    *  and resetting it to the default values. Meant to be used when 
    *  re-initializing a state.
    * 
    * For the Motor, we do not clear the data per-se as we do want it 
    *  to be persistent across time steps.
    */
  public void clear()
  {
  }

  public double getTorque() {
	return aTorque;	
  }
  public void resetState() {
	  aAngVel = 0;
  }

}
