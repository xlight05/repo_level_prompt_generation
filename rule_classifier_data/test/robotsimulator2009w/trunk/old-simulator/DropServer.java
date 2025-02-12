/**
  * SubjectiveDataServer
  *
  * This class handles the communication between the simulator and the
  *  DisCo wrapper corresponding to a particular agent. It receives 
  *  robot commands from the wrapper and sends back subjective information,
  *  such as sensor readings.
  *
  *  A new SubjectiveDataServer should first be created, then started using
  *   Thread.start().
  *
  * @author Marc G. Bellemare
  */
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import java.io.IOException;

public class DropServer extends Thread
{
  protected ServerSocket aSocket;
  protected LinkedList<ClientHandler> aClients;

  /**
    * Constructs a new DropServer which listens on the given port, sending
    *  and receiving drops via TCP/IP.
    *
    * @param pPort The port to listen to.
    */
  public DropServer(int pPort)
  {
    try
    {
      aSocket = new ServerSocket(pPort);
    }
    catch (IOException e)
    {
      System.err.println ("Failed to create DropServer socket, "+
        e.getMessage());
    }
    
    aClients = new LinkedList<ClientHandler>();
  }

  /**
    * Send a given Drop out to all connected clients.
    */
  public void sendDrop(SimulatorDrop pDrop)
  {
    // Simply call each client handler's send method
    for (ClientHandler ch : aClients)
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

    for (ClientHandler ch : aClients)
    {
      SimulatorDrop drop;
      drop = ch.receive();

      while (drop != null)
      {
        System.err.println ("Passing drop back");
        drops.add(drop);
        drop = ch.receive();
      }
    }

    return drops;
  }

  /** From the Thread class */
  public void run()
  {
    while (true)
    {
      try
      {
        // Listen for a new connection (blocks here)
        Socket clientSocket = aSocket.accept();
        System.out.println ("New client!");
        ClientHandler ch = new ClientHandler(clientSocket);
        ch.start();
        aClients.add(ch);
      }
      catch (IOException e)
      {
        throw new RuntimeException(e.getMessage());
      }
    }
  }
}
