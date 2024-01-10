package org.rlcommunity.critter;

import java.io.BufferedWriter;
import java.io.FileWriter;

import org.rlcommunity.critter.Base.Vector2D;
import org.rlcommunity.critter.Clients.DiscoInterfaceServer;
import org.rlcommunity.critter.Clients.KeyboardClient;
import org.rlcommunity.critter.Clients.TwoWheeledRobotControllerClient;
import org.rlcommunity.critter.Controls.MBController;
import org.rlcommunity.critter.Drops.DropInterface;

public class Episode {
	
	private int episode_length;
	public int stepCnt=0; 
	
	int subjPort;
	double agentPosX;
	double agentPosY;
	double agentAngle;
	boolean nn;
	String neuralNet;
	
	public Episode(int subjPort, double agentPosX, double agentPosY, double agentAngle, 
			boolean nn, String neuralNet, int episode_length){
		this.subjPort=subjPort;
		this.agentPosX=agentPosX;
		this.agentPosY=agentPosY;
		this.agentAngle=agentAngle;
		this.nn=nn;
		this.neuralNet=neuralNet;
		this.episode_length=episode_length;
		
		this.stepCnt=0;
	}
	
	public void run(){	
		final KeyboardClient theKeyboardClient=new KeyboardClient();
	    final TwoWheeledRobotControllerClient theControllerClient = new TwoWheeledRobotControllerClient(nn, neuralNet);
	    
//	    MBController mbController; 
//	    if (!SimulatorMain.ASYNCHRONOUS_MODE){
//	    	mbController = (MBController)theControllerClient.controller;
//	    }
	    
	    // the controller is now ready to send and receive data
	    theControllerClient.start();

	    System.out.println ("Starting Disco server on port "+subjPort);
	    // Create a drop server to send and receive robot (subjective) data
	    DiscoInterfaceServer discoServer = new DiscoInterfaceServer(subjPort);
	    discoServer.start();

	    // Create the central drop interface
	    DropInterface dropInterface = new DropInterface();

	    dropInterface.addClient(discoServer);
	    dropInterface.addClient(theKeyboardClient);
	    dropInterface.addClient(theControllerClient);

	    System.out.println ("Creating simulator engine...");
	    final SimulatorEngine engine = new SimulatorEngine(agentPosX, agentPosY, agentAngle);
	    engine.addComponent(new SimulatorComponentWheel()); // this should go before dynamics (creates forces)
	    engine.addComponent(new SimulatorComponentDynamics());
	    engine.addComponent(new SimulatorComponentLight());
	    engine.addComponent(new SimulatorComponentDiffuseLight());
	    engine.addComponent(new SimulatorComponentBump());
	    engine.addComponent(new SimulatorComponentControl(dropInterface));

	    javax.swing.SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	        	new SimulatorViz(engine,theKeyboardClient);
	        }
	    });
	    
	    while (stepCnt<episode_length)
	    {
	      engine.step();

	      try {
			Thread.sleep(9);
	      } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	      }
	      //objServer.sendUpdate(engine.getState());
	      //subjServer.sendUpdate();
	      //subjServer.receiveData();
	    }
	}
}
