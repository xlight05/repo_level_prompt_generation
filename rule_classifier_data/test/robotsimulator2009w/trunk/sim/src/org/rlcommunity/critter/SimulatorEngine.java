package org.rlcommunity.critter;

/**
  * SimulatorEngine
  *
  * Defines the core of the simulator engine. This class should call the
  *  simulator components in turn, keep track of existing objects and agents, 
  *  etc.
  *
  * @author Marc G. Bellemare
  */

//import java.awt.geom.Line2D;
//import java.awt.geom.Point2D;
import java.util.Collections;
//import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.rlcommunity.critter.Base.Vector2D;

public class SimulatorEngine
{
	public static FloorMap floorMap;
	
  protected SimulatorState aState, aNextState;
  protected LinkedList<SimulatorComponent> aComponents;

  private long last_time;
  
  //protected SimulatorVizEvents vizHandler;
  
  private double agentPosX;
  private double agentPosY;
  private double agentAngle;

  public SimulatorEngine(double agentPosX, double agentPosY, double agentAngle)
  {
	  this.agentAngle=agentAngle;
	  this.agentPosX=agentPosX;
	  this.agentPosY=agentPosY;
	  
    aState = new SimulatorState();
    aComponents = new LinkedList<SimulatorComponent>();

    //vizHandler = new SimulatorVizEvents();

    // Construct the simulator state by adding objects to it
    // MGB: I moved all of this code to debugCreateStuff because it was
    //  getting fairly bulky
    debugCreateStuff();

    // Clone the current state; we will use it to do state-state transitions
    aNextState = (SimulatorState)aState.clone();
  }

  /** Returns a list of existing agents */
  public List<SimulatorAgent> getAgentList() { 
    return Collections.unmodifiableList(aState.getAgents()); 
  }

  /** Returns a list of existing objects */
  public List<SimulatorObject> getObjectList() { 
    return Collections.unmodifiableList(aState.getObjects()); 
  }

  /** Returns a list of objects influenced by the given component
    *
    * @param pComponent The identifier of the component of interest
    * @return A list of objects as per SimulatorState.getObjects(pComponent)
    */
  public List<SimulatorObject> getObjects(String pComponent)
  {
    return aState.getObjects(pComponent);
  }

  /**
    * Returns the current state of the simulator. 
    */
  public SimulatorState getState()
  {
    return aState;
  }

  public void addComponent(SimulatorComponent pComponent)
  {
    aComponents.add(pComponent);
  }

  /** Takes one 'step' in the simulation.
    *  The main part of the step() method is to invoke each SimulatorComponent
    *  in turn, to do its job. The components are handed the current state,
    *  the next state and the time elapsed in between (in ms). Following this,
    *  the next state becomes the current state.
    *
    * On top of that, the step() method is currently providing some hacked
    *  together functionality which will be progressively removed. All of the
    *  debug* functions must be taken out sooner or later. For now, the
    *  step() method also handles:
    *
    *   - Arrow-controlled driving of the robot
    *   - Silly physics involving crude collision detection and force fields
    *   - A clock synchronization mechanism
    */
  public void step()
  {
    // Determine how much time has elapsed
    // @@@ Note: the step() function per se should not be called unless
    //  we DO want to take a time step (so the clocking mechanisms should be
    //  somewhere else, e.g. in a run()-like function)
    int ms = debugGetElapsedTime();
    //System.out.println(ms);
    if (ms <= 0) return;
    
    ms = Math.min(ms, 20); // steps longer than 20 msec are not supported (debugging)
    if (!SimulatorMain.ASYNCHRONOUS_MODE)
    	ms=20;
  
    // Re-initialize the next state to be filled with data
    aNextState.clear();

    /** Begin new (real) simulator code - everything above has to be moved
      *  (more or less) */
    // Apply each component in turn (order matters!)
    for (SimulatorComponent comp : aComponents)
    {
      comp.apply(aState, aNextState, ms);
    }

    SimulatorState tmpState = aState;

    // Replace the current state by the new state
    aState = aNextState;
    // Be clever and reuse what was the current state as our next 'new state'
    aNextState = tmpState;
  }



  protected void createWall()
  {
	  Wall w = new Wall("Wall", 1); // these points are used for drawing only
	  w.addPoint(20,20);
	  w.addPoint(20,480);
	  w.addPoint(480,480);
	  w.addPoint(480,20);
	  w.addPoint(20,20);

	  // Make a polygon for the wall as well
	  Polygon wallShape = new Polygon(); // these points determine the physical shape
	  // Exterior
	  wallShape.addPoint(0, 0);
	  wallShape.addPoint(0, 500);
	  wallShape.addPoint(500, 500);
	  wallShape.addPoint(500, 0);
	  wallShape.addPoint(0, 0);
	  // Interior (notice! the interior must be given in counter-clockwise
	  // order)
	  wallShape.addPoint(20, 20);
	  wallShape.addPoint(480, 20);
	  wallShape.addPoint(480, 480);
	  wallShape.addPoint(20, 480);
	  wallShape.addPoint(20, 20);		
	  System.out.println("Wall");
	  wallShape.doneAddPoints();

	  // Note that this polygon self-intersects at the duplicated edge
	  //  (0,0)-(20,20)
	  // This polygon is also evil because everything falls within its bounding
	  //  box
	  w.setShape(wallShape);

	  // Make the wall react to dynamics
	  // @@@ TODO (CS): Is this necessary? (Or is this here for testing?)
	  ObjectStateDynamics wallDyn = new ObjectStateDynamics(10000,10000);
	  wallDyn.setMaxSpeed(0);
	  w.addState(wallDyn);
	  aState.addObject(w);  
  }

  /// @@@ TODO move somewhere
  final static public double momentOfInertiaOfCylinder( double mass, double radius )
  {
	  return mass*radius*radius/2.0;
  }
  
  /// @@@ TODO move somewhere
  final static public double momentOfInertiaOfRectangle( double mass, double xsize, double ysize )
  {
	  return mass*(xsize*xsize+ysize*ysize)/12.0;
  }
  
  protected void createAgent()
  {
	  SimulatorAgent sa = new SimulatorAgentCritterBot("Motor Agi",2);
	  aState.addObject(sa);
	  if (SimulatorMain.ASYNCHRONOUS_MODE){
		  sa.setPosition(new Vector2D(250-2.0,250));
		  sa.setDirection(3.0*Math.PI/2.0);//-Math.PI/4.0);//-Math.PI/6.0); // corresponding to +45 degrees
	  }else{
		  sa.setPosition(new Vector2D(agentPosX, agentPosY));
		  sa.setDirection(agentAngle);//-Math.PI/4.0);//-Math.PI/6.0); // corresponding to +45 degrees
	  }
  }

  protected void createHex()
  {
	  // Add a hexagonal obstacle
	  SimulatorObject hex = new SimulatorObject("Hex", 3);

	  // Create the hex polygon
	  Polygon hexShape = new Polygon();
	  hexShape.addPoint(0,0);
	  hexShape.addPoint(-8,-6);
	  hexShape.addPoint(-8,-16);
	  hexShape.addPoint(0,-22);
	  hexShape.addPoint(8,-16);
	  hexShape.addPoint(8,-6);
	  hexShape.translate(new Vector2D(0, 11));
	  System.out.println("Hex");
	  hexShape.doneAddPoints();

	  /*hexShape.addPoint(0,0);
	    hexShape.addPoint(40,0);
	    hexShape.addPoint(40,40);
	    hexShape.addPoint(0, 40);
	    hexShape.addPoint(0, 35);
	    hexShape.addPoint(35,35);
	    hexShape.addPoint(35, 5);
	    hexShape.addPoint(0, 5);*/


	  hex.setShape(hexShape);

	  // Important - set position after setting shape
	  hex.setPosition(new Vector2D(355,255));

	  // Add dynamics to this object
	  hex.addState(new ObjectStateDynamics(0.05,0.2));

	  aState.addObject(hex);

  }

  protected void createLightSource()
  {
	  SimulatorObject lightSource = new SimulatorObject("light", 4);

	  Polygon shape = new Polygon();

	  shape.addPoint(0.0, 0.0);
	  shape.addPoint(3.0, 3.0);
	  shape.addPoint(6.0, 0.0);
	  System.out.println("Shape");
	  shape.doneAddPoints();
	  lightSource.setShape(shape);
	  lightSource.setPosition(new Vector2D(50.0, 50.0));

	  ObjectStateLightSource lightSourceState = new ObjectStateLightSource();
	  lightSourceState.setIntensity(10000.0);
	  lightSource.addState(lightSourceState);

	  aState.addObject(lightSource);

  }

  protected void createDiffuseLightSourceHex()
  {
	  SimulatorObject lightSource = new SimulatorObject("difflight", 5);

	  Polygon shape = new Polygon();

	  shape.addPoint(0,0);
	  shape.addPoint(-8,-6);
	  shape.addPoint(-8,-16);
	  shape.addPoint(0,-22);
	  shape.addPoint(8,-16);
	  shape.addPoint(8,-6);
	  shape.translate(new Vector2D(0, 11));
	  System.out.println("DiffusiveLightSource");
	  shape.doneAddPoints();
	  	  
	  lightSource.setShape(shape);
	  lightSource.setIsOnTheFloor(true);
	  lightSource.setPosition(new Vector2D(300.0, 300.0));

	  ObjectStateDiffuseLightSource lightSourceState = new ObjectStateDiffuseLightSource();
	  lightSourceState.setIntensity(1.0);
	  lightSource.addState(lightSourceState);

	  aState.addObject(lightSource);	  
  }
  
  protected void createFloorMap()
  {
	  int ux = 150, uy = 100, lx = 430-150, ly = 500-100; 
	  double hw=SimulatorMain.hw;
	  floorMap = new FloorMap("floormap", 25, hw, ux, uy, lx, ly);

	  Polygon shape = new Polygon(); // these points determine the physical shape
	  // Exterior
	  shape.addPoint(ux-hw,uy-hw);
	  shape.addPoint(ux-hw,ly+hw);
	  shape.addPoint(lx+hw,ly+hw);
	  shape.addPoint(lx+hw,uy-hw);
	  shape.addPoint(ux-hw,uy-hw);
	  // Interior
	  shape.addPoint(ux+hw,uy+hw);
	  shape.addPoint(lx-hw,uy+hw);
	  shape.addPoint(lx-hw,ly-hw);
	  shape.addPoint(ux+hw,ly-hw);
	  shape.addPoint(ux+hw,uy+hw);

	  System.out.println("FloorMap");
	  shape.doneAddPoints();
  
	  floorMap.setShape(shape);
	  floorMap.setIsOnTheFloor(true);
//	  floorMap.setPosition(new Vector2D(300.0, 300.0));

	  ObjectStateDiffuseLightSource lightSourceState = new ObjectStateDiffuseLightSource();
	  lightSourceState.setIntensity(1.0);
	  floorMap.addState(lightSourceState);

	  aState.addObject(floorMap);
	  
	  // add turns
	 // double[] directions={Math.PI/2, Math.PI};
	  Turn turn_ur=new Turn(new Vector2D(lx,uy));
	  //directions[0]=Math.PI; directions[1]=3*Math.PI/2;
	  Turn turn_lr=new Turn(new Vector2D(lx,ly));
	  //directions[0]=0; directions[1]=3*Math.PI/2;
	  Turn turn_ll=new Turn(new Vector2D(ux,ly));
	  //directions[0]=0; directions[1]=Math.PI/2;
	  Turn turn_ul=new Turn(new Vector2D(ux,uy));
	  
	  Turn[] nextTurns=new Turn[2];
	  nextTurns[0]=turn_lr; nextTurns[1]=turn_ul;
	  turn_ur.nextTurns=nextTurns;
	  nextTurns=new Turn[2];
	  nextTurns[0]=turn_ll; nextTurns[1]=turn_ur;
	  turn_lr.nextTurns=nextTurns;
	  nextTurns=new Turn[2];
	  nextTurns[0]=turn_ul; nextTurns[1]=turn_lr;
	  turn_ll.nextTurns=nextTurns;
	  nextTurns=new Turn[2];
	  nextTurns[0]=turn_ur; nextTurns[1]=turn_ll;
	  turn_ul.nextTurns=nextTurns;
	  
	  floorMap.turns=new Turn[4];
	  floorMap.turns[0]=turn_ur;
	  floorMap.turns[1]=turn_lr;
	  floorMap.turns[2]=turn_ll;
	  floorMap.turns[3]=turn_ul;
 }

  protected void createTape()
  {
	  SimulatorObject tape = new SimulatorObject("tape", 35);
	  double w = 2.4; // half width
	  int cx = 250, uy = 160, ly = 500-80;
	  Polygon shape = new Polygon(); // these points determine the physical shape
	  shape.addPoint(cx-0, uy);
	  shape.addPoint(cx+w, uy);
	  shape.addPoint(cx+w, ly);
	  shape.addPoint(cx-0, ly);
	  shape.addPoint(cx-0, uy);

	  System.out.println("tape");
	  shape.doneAddPoints();
  
	  tape.setShape(shape);
	  tape.setIsOnTheFloor(true);

	  ObjectStateDiffuseLightSource lightSourceState = new ObjectStateDiffuseLightSource();
	  lightSourceState.setIntensity(1.0);
	  tape.addState(lightSourceState);

	  aState.addObject(tape);
 }
  
  protected void createTapeSensor()
  {
	  SimulatorObject tapeSensor = new SimulatorObject("tapeSensor", 39);
	  System.out.println("tapeSensor");
	  ObjectStateDiffuseLightSensor lightSourceSensor = new ObjectStateDiffuseLightSensor();
	  tapeSensor.addState(lightSourceSensor);
	  tapeSensor.setPosition(new Vector2D(250,270));
	  tapeSensor.setDirection(Math.PI*3.0/2.0);
	  aState.addObject(tapeSensor);
  }
  
  /** Temporary methods (used mainly until a proper replacement is written */
  protected void debugCreateStuff()
  {
    //createDiffuseLightSourceHex();
	  if (SimulatorMain.ASYNCHRONOUS_MODE){
		  createTape();
	  }else{
		  createFloorMap();
	  }
    createWall();
    createAgent();
    //createTapeSensor();
    //createHex();
    //createLightSource();
  }

  protected int debugGetElapsedTime()
  {
    // This needs to be turned into a proper clocking mechanism
	  long time = System.currentTimeMillis();
	  if(last_time == 0) {
		  last_time = time;
		  return 0;
	  }
	  int ms = (int)(time - last_time); 
	  
	  /** Don't run too fast */
	  if(ms < 10)
		  return 0;
	  else
    {
      last_time = time;
      return ms;
    }
  }
}




