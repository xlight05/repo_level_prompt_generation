package org.rlcommunity.critter;

/**
  * SimulatorAgent
  *
  * This class defines agent-specific methods and values. This includes
  *  sensors (which should be specific classes, or at least bundled into a
  *  Sensor class), actions and such. For the time being the class only
  *  defines an image that is drawn.
  *
  * @author Marc G. Bellemare
  */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
//import java.awt.image.BufferedImage;
//import java.awt.image.BufferedImageOp;
//import java.awt.image.RescaleOp;
//import java.io.File;
import java.net.URL;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.rlcommunity.critter.Drops.SimulatorDrop;

import java.awt.geom.AffineTransform;

// @@@ TODO SimulatorAgent actually should not implement Drivable
public class SimulatorAgent extends SimulatorObject implements Drivable {
	static protected int DROP_DT = 100; // time to elapse before next observation is sent
  /** Three values representing the current robot command */
  // protected double aCmdX, aCmdY, aCmdTheta; // CS: I commented these out -- they are not used actually
  
  /** Controllable Image (for now)*/
  private Image robotop;
  
  private StopperImpl stopperImpl = new StopperImpl(DROP_DT);
  /** Creates a new instance of a SimulatorAgent
    */
  public SimulatorAgent(String pLabel, int pId)
  {
    super(pLabel, pId);
    // aCmdX = aCmdY = aCmdTheta;
    try{ 
      URL url = this.getClass().getResource("robotop.png");
      System.out.println("Read image: " + url.toString());
      robotop = ImageIO.read(url);
      robotop = robotop.getScaledInstance(40,68,Image.SCALE_SMOOTH);
    }
    catch(IOException ioe) {
      System.out.println(ioe.toString());
    }
    
    if (!SimulatorMain.ASYNCHRONOUS_MODE){
    	DROP_DT=1;
    }
  }
  
  public void draw(Graphics g) {
    if (true)
    {
      Graphics2D g2 = (Graphics2D)g;
      AffineTransform oldXform = g2.getTransform();
      AffineTransform newXform = (AffineTransform)(oldXform.clone());
      newXform.rotate(Math.PI / 2 + aDir, aPos.x, aPos.y);
      g2.setTransform(newXform);
      g2.drawImage(robotop, (int)aPos.x - 19, (int)aPos.y - 21, null);
      g2.setTransform(oldXform);
	    
      Color tempC = g.getColor();
	  g.setColor(Color.lightGray);
	  g.drawString(aLabel, (int)aPos.x + 20, (int)aPos.y + 20);
	  g.setColor(tempC);
      super.draw(g);
    }
    else
    {
	    Color tempC = g.getColor();
	    g.setColor(Color.black);
	    g.drawOval((int)aPos.x - 10, (int)aPos.y - 10, 20, 20);
	    g.setColor(Color.red);
	    g.drawLine((int)aPos.x, (int)aPos.y, (int)(aPos.x + 10 * Math.cos(aDir)), (int)(aPos.y + 10 * Math.sin(aDir)));
	    g.setColor(Color.lightGray);
	    g.drawString(aLabel, (int)aPos.x + 10, (int)aPos.y + 20);
	    g.setColor(tempC);
    }
    //super.draw(g);
  }

  public Object clone()
  {
    SimulatorAgent sa = new SimulatorAgent(this.aLabel, this.aId);
    sa.copyFrom(this);
//
//    // Clone the object's children
//    for (SimulatorObject c : aChildren)
//    {
//      SimulatorObject childClone = (SimulatorObject)c.clone();
//      sa.addChild(childClone);
//    }

    return sa;
  }

  protected void copyFrom(SimulatorObject obj)
  {
    super.copyFrom(obj);
    SimulatorAgent sa = (SimulatorAgent)obj;
    stopperImpl.copyFrom(sa.stopperImpl);
    // @@@ TODO: should we copy the image?
    
    //SimulatorAgent org = (SimulatorAgent)obj;

    // Make a copy of relevant agent data
    // this.aCmdX = org.aCmdX;
    // this.aCmdY = org.aCmdY;
    // this.aCmdTheta = org.aCmdTheta;
  }

    /** Returns null object. */
	public SimulatorDrop makeDrop() {
		return null;
	}
	
	/** Finds the drivable state and forwards the call. */
	public void setFromDrop(SimulatorDrop drop) {
		// find the recipient
		String recpt = drop.getRecipient();
		Drivable drivable = (Drivable)getState(recpt);
		// send info
		if (drivable!=null)
			drivable.setFromDrop(drop);
	}

	public boolean hasData(int delta) {
		stopperImpl.elapsed(delta);
		return stopperImpl.timeout();
	}

	public void copyTimeSinceLastDropFrom(SimulatorAgent that) {
		stopperImpl.copyTime(that.stopperImpl);
	}

	public void update(Drivable driveState, int delta) {
	}

	public void update(SimulatorObject obj, ObjectStateDynamics dynState,
			int delta) {
		// TODO Auto-generated method stub
		
	}

}
