package org.rlcommunity.critter;

/**
  * SimulatorComponentControl
  *
  * Facilitates communication between controllers and controlled objects
  * (can be said to implement the Mediator pattern of [GoF])
  * 
  * The main functionality is to send the information from the "agents"
  * in the simulation to controllers and back.
  * 
  * Original description:
  * A (very long-named) class that converts drops received from Disco
  *  (via a DropServer) into simulator data, and vice-versa.
  *
  * @author Marc G. Bellemare
  */

import org.rlcommunity.critter.Drops.*;

//import java.util.LinkedList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SimulatorComponentControl implements SimulatorComponent
{
  public static final String NAME = Drivable.NAME;

  protected DropInterface aDropInterface; 

  /** A simple method of slowing down the speed at which state-drops are sent;
    * @@@ TODO needs to be fixed */
  public int aStateThrottle;

  public SimulatorComponentControl(DropInterface pInterface)
  {
    aDropInterface = pInterface;
    aStateThrottle = 0;
  }

  /** Send the information from the "agents" in the simulation 
   *  to (potential) controllers and back.
   *  The communication is done with the help of drops.
   *   
   *   @param pCurrent	The current simulator state (used to find the agents)
   *   @param pNext		The next simulator state (used to find recipient objects)
   *   @param delta		The time elapsed between the two states [ms]
   * 
   */
  public void apply (SimulatorState pCurrent, SimulatorState pNext, int delta)
  {
	  if (aDropInterface != null)
	  {
		  // Step 1: send state information about the current state of all agents
		  // to all listeners (potential controllers) of the drop interface
		  // @@@ TODO why agents; maybe Drivable.NAME objects??
		  for (SimulatorAgent agent: pCurrent.getAgents())
		  {
			  if (agent.hasData(delta)) // if enough time has elapsed..
				  aDropInterface.sendDrop(agent.makeDrop());
			  // find agent in the next state
			  SimulatorAgent nextAgent = (SimulatorAgent)pNext.getObject(agent);
			  nextAgent.copyTimeSinceLastDropFrom(agent);
		  }		  

		  // Step 2: All drivable objects should copy their persistent
		  // 		 data to the next state (e.g., control states with
		  //		 persistent data must be copied)
		  //
		  // Get all drivable objects from pCurrent
		  LinkedList<SimulatorObject> drivables = pCurrent.getObjects(Drivable.NAME);
		  for (SimulatorObject obj : drivables)
		  {
			  // find the drivable state:
			  Drivable driveState = (Drivable)obj.getState(Drivable.NAME);
			  // find matching object and state in the next simulator state
			  SimulatorObject nextObj = pNext.getObject(obj);
			  if (nextObj==null) continue;
			  Drivable nextDriveState = (Drivable)nextObj.getState(Drivable.NAME);
			  driveState.update(nextDriveState,delta);
		  }
		  
		  // Step 3: get controls from interested parties and update the next state with them
		  // so that they can be applied later
		  
		  // collect drops from all the current clients of aDropInterface:
		  List<SimulatorDrop> drops = aDropInterface.receiveDrops();

//		  // collect all potentially interested parties at the next time step
//		  List<SimulatorObject> omnidrivable = 
//			  pNext.getObjects(SimulatorComponentOmnidrive.NAME);

		  // caching the objects in case we have multiple drops etc.;
		  // @@@ TODO the cache should really be implemented in SimulatorState,
		  // though this might be complicated because then if the objects
		  // are modified in any way, then how do you know that the cache needs an update?
		  // One solution: the objects could just store a bit that would show if they were modified
		  // since the last time they were queried by SimulatorState.
		  Map<String,List<SimulatorObject>> talkingTo = new TreeMap<String,List<SimulatorObject>>();

		  // all drops are forwarded to all interested parties; actually
		  // to the appropriate state components
		  for (SimulatorDrop drop : drops)
		  {
			  // get the recipients
			  String recpt = drop.getRecipient();
			  List<SimulatorObject> recptObjs = talkingTo.get(recpt);
			  if (recptObjs==null)
			  {
				  recptObjs = pNext.getObjects(recpt);
				  talkingTo.put(recpt, recptObjs);
			  }
			  // send drop to all the recipients
			  for (SimulatorObject obj : recptObjs)
			  {
				  Drivable drivable = (Drivable)obj;
				  if (drivable!=null)
					  drivable.setFromDrop(drop);
			  }
		  }
		  
		  // Step 4: Let the drivable objects compute the control forces
		  //         and torques that will be applied in the next state
		  //
		  // get all drivable objects from pNext
		  drivables = pNext.getObjects(Drivable.NAME);
		  for (SimulatorObject obj : drivables)
			  implementControl(obj,delta);
		  
		  //        // @@@ icky
		  //        if (drop instanceof CritterControlDrop)
		  //        {
		  //          CritterControlDrop command = (CritterControlDrop)drop;
		  //          for (SimulatorObject obj : omnidrivable)
		  //          {
		  //            ObjectStateOmnidrive driveState = 
		  //            	(ObjectStateOmnidrive)obj.getState(SimulatorComponentOmnidrive.NAME);
		  //
		  //            driveState.setFromDrop(command);
		  //          }
		  //        }
	  }

//	  // Send the state of agents to all clients of aDropInterface.
//	  // Technically we should do this with each object which... ?
//	  SimulatorAgent agent = pCurrent.getAgents().getFirst();
//
//	  if (agent != null)
//	  {
//		  if (++aStateThrottle >= 20)
//		  {
//			  aDropInterface.sendDrop(makeStateDrop(agent));
//			  aStateThrottle = 0;
//		  }
//	  }
  }
  
  protected void implementControl(SimulatorObject obj, int delta) 
  {
	  // find control state
	  
	  Drivable controlState = (Drivable)obj.getState(Drivable.NAME);
	  if (controlState!=null)
	  {
//		  if (controlState.getTime()>0)
//		  {
//			  int a = 0;
//		  }
		  // find dynamic 'state'
		  ObjectStateDynamics dynState = (ObjectStateDynamics)obj.getState(ObjectStateDynamics.NAME);
		  // forward request to the control state
		  controlState.update(obj,dynState, delta);
	  }
  }
  
        
//  public CritterStateDrop makeStateDrop(SimulatorObject pObject)
//  {
//	  CritterStateDrop stateDrop = new CritterStateDrop();
//
//	  // Set the dynamics data
//	  if (pObject.getState(SimulatorComponentDynamics.NAME) != null)
//	  {
//		  ObjectStateDynamics dynData = 
//			  (ObjectStateDynamics)pObject.getState(SimulatorComponentDynamics.NAME);
//		  //Force f = dynData.getForceSum();
//		  // @@@ Needs to be converted into proper units (I believe in g's)
//		  //      stateDrop.accel.x = (int)(f.vec.x * 1000);
//		  //      stateDrop.accel.y = (int)(f.vec.y * 1000);
//		  Vector2D V=dynData.getVelocity();
//		  stateDrop.accel.x=(int) V.x;
//		  stateDrop.accel.y=(int) V.y;
//	  }
//	  // @@@ TODO (CS): Does this make sense? This is only called for an agent
//	  // that will typically have a number of SimulatorObject children,
//	  // one for each light sensor..
//	  if (pObject.getState(ObjectStateLightSensor.NAME) != null)
//	  {
//		  ObjectStateLightSensor sData = (ObjectStateLightSensor)
//		  pObject.getState(ObjectStateLightSensor.NAME);
//		  stateDrop.light[0] = (int)(sData.getLightSensorValue() * 100);
//	  }
//
//	  return stateDrop;
//  }

}
