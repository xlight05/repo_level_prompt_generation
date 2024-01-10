package org.rlcommunity.critter.Controls;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class NeuralNet {
	private int numLayers;
	private double[][] threshold;
	private double[][][] links;
	private double rescaleOutput;
	private double outputMidPoint;
		
	public NeuralNet(String file){
		Scanner scanner;
		try {
			File f=new File(file);
			scanner = new Scanner(f);
			String line=scanner.nextLine(); // skip the header
			numLayers=Integer.valueOf(scanner.nextLine());
			threshold=new double[numLayers][];
			int layerSize;
			double thresh;
			for (int i=0; i<numLayers; i++){
				layerSize=Integer.valueOf(scanner.nextLine());
				threshold[i]=new double[layerSize];
				for (int j=0; j<layerSize; j++){
					thresh=Double.valueOf(scanner.nextLine());
					threshold[i][j]=thresh;
				}
			}
			int numLinks=Integer.valueOf(scanner.nextLine());
			// assume we have 3 layers
			if (threshold[0].length*threshold[1].length+threshold[1].length*threshold[2].length!=numLinks){
				System.out.println("ERROR: number of links doesn't match");
			}
			links=new double[numLayers-1][][];
			for (int i=0; i<links.length; i++){
				links[i]=new double[threshold[i].length][threshold[i+1].length];
			}
			int inputLayer, inputNeuronIndex, outputNeuronIndex;
			double weight;
			for (int i=0; i<numLinks; i++){
				inputLayer=Integer.valueOf(scanner.nextLine());
				inputNeuronIndex=Integer.valueOf(scanner.nextLine());
				outputNeuronIndex=Integer.valueOf(scanner.nextLine());
				weight=Double.valueOf(scanner.nextLine());
				links[inputLayer][inputNeuronIndex][outputNeuronIndex]=weight;
			}
			
			// two additional numbers
			rescaleOutput=Double.valueOf(scanner.nextLine());
			outputMidPoint=Double.valueOf(scanner.nextLine());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void predict(String testFileName, int inputLength){
		Scanner scanner;
		StringTokenizer tokenizer;
		String line;
		double[] input=new double[inputLength];
		int numTokens, lineCount=0;
		double sumErr=0, pred, target;
		try {
			scanner=new Scanner(new File(testFileName));
			while(scanner.hasNext()){
				line=scanner.nextLine();
				lineCount++;
				tokenizer=new StringTokenizer(line);
				numTokens=tokenizer.countTokens();
				for(int i=0; i<numTokens-1; i++){
					input[i]=Double.valueOf(tokenizer.nextToken());
				}
				target=Double.valueOf(tokenizer.nextToken());
				pred=predict(input);
				System.out.println("Target value: "+target+" Prediction: "+pred);
				sumErr+=Math.abs(target-pred);
			}
			System.out.println("++++++++++++++++++++++");
			System.out.println(sumErr/lineCount);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public double predict(double[] input){
		double[][] nodes=new double[threshold.length][];
		for (int i=0; i<nodes.length; i++){
			nodes[i]=new double[threshold[i].length];
		}
		for (int i=0; i<nodes[0].length; i++){
			nodes[0][i]=input[i];
		}
		double sum=0;
		for (int i=1; i<nodes.length; i++){
			for (int j=0; j<nodes[i].length; j++){
				sum=0;
				for (int k=0; k<nodes[i-1].length; k++){
					sum+=links[i-1][k][j]*nodes[i-1][k];	
				}
				sum+=threshold[i][j];
				if (i<nodes.length-1)
					nodes[i][j]=sigmoid(sum);
				else
					nodes[i][j]=sum;
			}
		}
		
		// only one output
		return nodes[nodes.length-1][0];
	}
	
	private double sigmoid(double input){
		return Math.exp(input)/(1.0+Math.exp(input));
	}
}
