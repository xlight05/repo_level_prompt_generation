package org.rlcommunity.critter;

/**
  * SimulatorMain
  *
  * Defines the main function for running the simulator.
  * Most likely to be unorganized until we figure out a proper structure for
  *  everything.
  *
  * @author Marc G. Bellemare
  */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.rlcommunity.critter.Base.Vector2D;
import org.rlcommunity.critter.Clients.*;
import org.rlcommunity.critter.Controls.MBController;
import org.rlcommunity.critter.Controls.NeuralNet;
import org.rlcommunity.critter.Drops.*;

public class SimulatorMain
{
	public static final boolean ASYNCHRONOUS_MODE=false;	
	
	private static final boolean RUN_PLANNER=false; // run the planner and write the observartions in FILE_NAME
	public static final double hw=10;
	private static FileWriter fstream;
	public static BufferedWriter out;
	private static final String[] OUTPUT_FILE_NAME={"training2", "training_nn"};
	
	private static final String NEURAL_NET_FILE_NAME="out2.bp"; // if RUN_PLANNER=false, read the neural net in this file and use at as the controller

	
	/** 1 position unit (as used in objects) is how many meters */
    public static double length_scale = 1E-2;
	public static boolean runSensorCalibrationExperiment = false;
	
	public static Episode episoide; // only one episode is running at a time, so this can remain as a static variable
		
	public static void main(String[] args) throws Exception{
//		NeuralNet nn=new NeuralNet(neuralNet);
//		Scanner scanner = new Scanner(new File(FILE_NAME));
//		String line="";
//		for (int i=0; i<18; i++)
//			line=scanner.nextLine();
//		System.out.println(line);
//		StringTokenizer tokenizer=new StringTokenizer(line);
//		int length=tokenizer.countTokens()-1;
//		double[] input=new double[length];
//		int count=0;
//		while(count<length){
//			input[count]=Double.valueOf(tokenizer.nextToken());
//			count++;
//		}
//		System.out.println(nn.predict(input));
//		System.exit(1);
		
//		NeuralNet nn=new NeuralNet(NEURAL_NET_FILE_NAME);
//		nn.predict(TRAINING_FILE_NAME, 149);
//		System.exit(1);
				
		if (RUN_PLANNER){
			fstream = new FileWriter(OUTPUT_FILE_NAME[0]);
			out=new BufferedWriter(fstream);
			runPlanner(); // run the planner and collect data
		}else{
			fstream = new FileWriter(OUTPUT_FILE_NAME[1]);
			out=new BufferedWriter(fstream);
			episoide=new Episode(2324, 255, 100-hw, 0, true, NEURAL_NET_FILE_NAME, 5000);
			episoide.run(); // use the neural net to take actions
		}
		System.exit(1);
	}
		
	private static void runPlanner() throws Exception{
	
//		out.write("@relation obs_speed_angle\n\n");
//		for (int i=0; i<WINDOW_SIZE; i++){
//			for (int j=0; j<atts.length; j++){
//				out.write("@ATTRIBUTE "+atts[j]+""+i+" NUMERIC\n");
//			}
//		}
//		out.write("\n@data\n\n");
		
//		for (int i=0; i<START_POINT.length; i++){
//			for (int j=0; j<START_ANGLES.length; j++)
//				new Episode().run(2324+i+j,START_POINT[i].x,START_POINT[i].y,START_ANGLES[j]);
//		}

		int episode_length=500;
		int init=2324;
			
		for (int i=0; i<1; i++){
			episoide=new Episode(init+4*i, 255, 100-hw, 0, false, null,episode_length);
			episoide.run();
//			episoide=new Episode(init+4*i+1, 270, 120, Math.PI/2-Math.PI/30, false, null,episode_length);
//			episoide.run();
			episoide=new Episode(init+4*i+2, 285, 120, Math.PI/2+Math.PI/20, false, null,episode_length);
			episoide.run();
//			episoide=new Episode(init+4*i+3, 280, 120, Math.PI/2+Math.PI/20, false, null,episode_length);
//			episoide.run();
//			episoide=new Episode(init+4*i+4, 285, 120, Math.PI/2+Math.PI/6, false, null,episode_length);
//			episoide.run();
//			episoide=new Episode(init+4*i+5, 280, 120, Math.PI/2+Math.PI/15, false, null,episode_length);
//			episoide.run();
//			episoide=new Episode(init+4*i+6, 255 ,100 , 0, false, null,episode_length);
//			episoide.run();
			
//			episoide=new Episode(init+4*i+7, 275, 120, Math.PI/2-Math.PI/20, false, null,episode_length);
//			episoide.run();
			
//			episoide=new Episode(init+4*i+3, 175, 100-hw, Math.PI, false, null,episode_length);
//			episoide.run();
			
//			episoide=new Episode(init+num*i+1, RIGHT[2].x, RIGHT[2].y, Math.PI/2+Math.PI/10, false, null,episode_length, hw);
//			episoide.run();
//			episoide=new Episode(init+num*i+2, RIGHT[1].x, RIGHT[1].y, Math.PI/2+Math.PI/8, false, null,episode_length);
//			episoide.run();
//			episoide=new Episode(init+num*i+3, RIGHT[3].x, RIGHT[3].y, Math.PI/2+Math.PI/10, false, null,episode_length);
//			episoide.run();
			
//			episoide=new Episode(init+num*i+4, RIGHT[3].x, RIGHT[3].y, Math.PI/2-Math.PI/10, false, null,episode_length, hw);
//			episoide.run();
//			episoide=new Episode(init+num*i+5, RIGHT[2].x, RIGHT[2].y, Math.PI/2-Math.PI/10, false, null,episode_length, hw);
//			episoide.run();
//			episoide=new Episode(init+num*i+6, RIGHT[1].x, RIGHT[1].y, Math.PI/2-Math.PI/10, false, null,episode_length);
//			episoide.run();
//			episoide=new Episode(init+num*i+2, RIGHT[1].x, RIGHT[1].y, Math.PI/2+Math.PI/8, false, null,episode_length);
//			episoide.run();
//			episoide=new Episode(init+num*i+3, RIGHT[3].x, RIGHT[3].y, Math.PI/2-Math.PI/8, false, null,episode_length);
//			episoide.run();
		}

		out.close();
	}
	
  
}
