package org.rlcommunity.critter.Controls;

public class Observation {
	//public double angleL;
	//public double anlgeR;
	public double lightL;
	public double lightR;
	public double lightM;
	//public double velL;
	//public double velR;
	public double[] action=new double[2];
	
	public final static int LENGTH=4;
	
	public void copy(TwoWheeledRobot robot, double[] action){
		//this.angleL=robot.getAngleL();
		//this.anlgeR=robot.getAngleR();
		this.lightL=robot.getLightL();
		this.lightR=robot.getLightR();
		this.lightM=robot.getLightM();
		//this.velL=robot.getVelL();
		//this.velR=robot.getVelR();
		this.action[0]=action[0];
		this.action[1]=action[1];
	}
	
	public String toString(){
		//String str=this.angleL+" "+this.anlgeR+" "+this.lightL+" "+this.lightR+" "+this.velL+" "+this.velR;
		String str=this.lightL+" "+lightM+" "+lightR+" "+action[1];
		return str;
	}
}
