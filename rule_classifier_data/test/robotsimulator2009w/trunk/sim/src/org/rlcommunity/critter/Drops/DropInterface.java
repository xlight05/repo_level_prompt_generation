package org.rlcommunity.critter.Drops;

/**
  * DropInterface implements approximately the Observer (or publish/subscribe) pattern:
  *  Clients interested in sending and receiving information in the form of SimulationDrops
  *  to each other can "subscribe" to this class.
  *   
  *  Subscribers are used in two ways:
  *  - Upon a call to sendDrop you can notify all subscribers about a piece of information,
  *    or "drop"
  *  - Upon a call to receiveDrop you can request information from all subscribers
  *    and collect these. The drop-lists of the subscribers is supposedly emptied
  *    as a result.
  *  
  * @author Marc G. Bellemare
  */

import java.util.LinkedList;
import java.util.List;

import org.rlcommunity.critter.Clients.*;

public class DropInterface
{
  protected LinkedList<DropClient> aClients;
  
  public DropInterface()
  {
    aClients = new LinkedList<DropClient>();
  }

  /**
    * Send a given Drop out to all DropClients, which may be Disco interfaces,
    *  keyboard clients, RL agents, etc.
    */
  public void sendDrop(SimulatorDrop pDrop)
  {
    // Simply call each client handler's send method
    for (DropClient ch : aClients)
      ch.send(pDrop);
  }

  /**
    * Returns a (possibly empty) list of drops that were received from all
    *  connected clients since the last call to receiveDrops.
    */
  public List<SimulatorDrop> receiveDrops()
  {
    LinkedList<SimulatorDrop> drops = new LinkedList<SimulatorDrop>();
    // 1. Collect  drops into list from client handlers
    // 2. Clear the client handlers' lists

    for (DropClient ch : aClients)
    {
      // Add all drops returned by the client to our list
      List<SimulatorDrop> clientDrops = ch.receive();
      if (clientDrops!=null)
	      for (SimulatorDrop drop : clientDrops)
	        drops.add(drop);
    }

    return drops;
  }

  public void addClient(DropClient newClient){
      aClients.add(newClient);
  }
}

