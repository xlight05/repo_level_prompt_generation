package org.rlcommunity.critter;

/**
  * ObjectStateDiffuseLightSource
  *
  * State component for a diffusive light-emitting object.
  *  The idea is that the full surface of the object is light
  *  emitting that can be sensed with special sensors.
  *  
  *  See also: ObjectStateDiffuseLightSensor
  *  
  *  @author csaba
  */

import java.awt.Color;
import java.awt.Graphics;

public class ObjectStateDiffuseLightSource implements ObjectState
{
  public static final String NAME = SimulatorComponentDiffuseLight.NAME + "source";

  /** Intensity of the source (scale: [0,1]) */
  protected double aIntensity;
  
  public ObjectStateDiffuseLightSource()
  {
    aIntensity = 0.0;
  }

  public double getIntensity() { return aIntensity; }
  public void setIntensity(double pInt) { aIntensity = pInt; }

  /** ObjectState interface */
  
  /** Returns a unique identifier for this type of state. */
  public String getName() { return NAME; }

  public Object clone()
  {
    ObjectStateDiffuseLightSource newSource = new ObjectStateDiffuseLightSource();
    newSource.copyFrom(this);

    return newSource;
  }

  protected void copyFrom(ObjectState os)
  {
    ObjectStateDiffuseLightSource src = (ObjectStateDiffuseLightSource)os;
  
    this.aIntensity = src.aIntensity;
  }



  /** (Potentially) draw something about the state; may be null. This
    *  most likely should be moved out of here when we have time.
    *
    * @param g The canvas to draw on
    * @param parent The owner of this state
    */
  public void draw(Graphics g, SimulatorObject parent)
  {
	  if (parent.getShape() != null)
	  {
		  Color col = new Color(250, 200, 200
				      , Math.max(0, Math.min(255,(int)(255.0*aIntensity))));
		  parent.getShape().drawFilled(g,col);
	  }
  }

  /** Provides a mean of clearing whatever data this ObjectState contains
    *  and resetting it to the default values. Meant to be used when 
    *  re-initializing a state.
    */
  public void clear()
  {
  }
  
  public void resetState() {
  }
  
}
