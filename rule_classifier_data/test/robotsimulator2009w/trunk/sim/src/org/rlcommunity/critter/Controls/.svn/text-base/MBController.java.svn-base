package org.rlcommunity.critter.Controls;

import java.awt.Window;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.text.Position;

import org.rlcommunity.critter.Polygon;
import org.rlcommunity.critter.Ray;
import org.rlcommunity.critter.RayIntersection;
import org.rlcommunity.critter.SimulatorEngine;
import org.rlcommunity.critter.SimulatorMain;
import org.rlcommunity.critter.SimulatorObject;
import org.rlcommunity.critter.Turn;
import org.rlcommunity.critter.Base.Force;
import org.rlcommunity.critter.Base.Vector2D;
import org.rlcommunity.critter.WRobotModels.TwoWheeledRobotModel;

// model based predictive controller
public class MBController extends DefaultTwoWheeledController{
	
	private static final int WINDOW_SIZE=100;
	
	// features:
	// being on the edge, edge on right side
	// being on the edge, edge on the left side
	// close to a turn, to right
	// close to a turn, to left
	// both sensors on the tape
	// out of tape
	// how close to the next turn
	final int ON_EDGE_RIGHT=0;		// on edge, edge on the right side
	final int ON_EDGE_LEFT=1;		// on edge, edge on the left side
	final int TURN_RIGHT=2;		// next turn: right
	final int TURN_LEFT=3;		// next turn: left
	final int BOTH_ON_TAPE=4;		// both sensors on tape
	final int OFF_TAPE=5;		// off tape
	final int OFF_RIGHT=6;		// just went off tape, on the right side of the tape
	final int OFF_LEFT=7; 		// just went off tape, on the right side of the tape 
	final int JUST_TURNED_RIGHT=8;		// just finished turning right
	final int DIST_TURN=9;		// distance to turn

	//final int NUMBER_OF_FEATURES=10;	
	
	final int NUM_LAST_ACTIONS=0;
	
	
	// planning constants
	final int NUM_NODES=2000; 	// number of nodes that will be expanded
	
	final double[] SPEED={0.1};
	final double[] ANGLE={0,Math.PI/2, Math.PI, 3*Math.PI/2};
	
	final double EPSILON=0.01;
	Vector<Observation> history=new Vector();
		
	// reward function parameters
	final double ON_TAPE_WEIGHT=1.0; // 1
	final double AWAY_FROM_EDGE_WEIGHT=1.0; // change this to 10 and the below threshold to 1
	final double SPEED_WEIGHT=10.0; //1
	//final double DISTANCE_WEIGHT=1.0; //1
	final double TURN_WEIGHT=1.0; //1
	final double HEADING_WEIGHT=0.1; // if we move forward 0.01
	
	public static final double GAMMA=0.5; // discount factor
		
	private int numberOfNodes = 0;

	//double[] last_highlevel_action=new double[2]; // last speed and angle taken
	
	public boolean USING_NEURAL_NET=false;
	private NeuralNet neuralNet; 	
	private Vector2D initTorque;	
	
	public MBController(TwoWheeledRobot robot, boolean NN, String neuralNet) {
		super(robot);
		this.USING_NEURAL_NET=NN;
		if (this.USING_NEURAL_NET){
			this.neuralNet=new NeuralNet(neuralNet);
		}
	}
	
	public void computeControl(){
//		System.out.println(robot.getLightL()+" "+robot.getLightM()+" "+robot.getLightR());
//		System.exit(1);
		
		TwoWheeledRobotModel curRobotModel;
		Vector2D torque;
		double[] action=new double[2];		// the high level action
		double[] feats;
		
		if (SimulatorMain.episoide.stepCnt<WINDOW_SIZE){ // this condition is necessary even for planning...
			action[0]=SPEED[0];
			action[1]=0; // wrt robot
		}else{
			if (USING_NEURAL_NET){
				feats=features(history);
				action[0]=SPEED[0];
				action[1]=neuralNet.predict(feats);	 
			}else{
				action=plan();
			}
		}
		
		curRobotModel = new TwoWheeledRobotModel();
		curRobotModel.setWheelState(robot.getAngleL(), robot.getAngleR(), robot.getVelL(), robot.getVelR());		
		torque=getTorque(curRobotModel, action[0], robot.getDirection()+action[1]);
		
		//double[] torques=plan();
		robot.setTorques(torque.x, torque.y);
				
		Observation obs=new Observation();
		obs.copy(robot, action);
		String str="";
		
		//if (SimulatorMain.episoide.stepCnt<WINDOW_SIZE){
		if (history.size()<WINDOW_SIZE){
			history.add(obs);
		}else{
			feats=features(history);
			for (int i=0; i<feats.length; i++){
				str+=feats[i]+" ";
			}
			str+=history.get(WINDOW_SIZE-1).action[1];
			try {
				SimulatorMain.out.write(str+"\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			history.remove(0);
			history.add(obs);
		}
		SimulatorMain.episoide.stepCnt++;		
	}

	// history should be complete
	private double[] features(Vector<Observation> history){
		//final int NUMBER_OF_FEATURES=WINDOW_SIZE*Observation.LENGTH-1;		// minus 1: last element is the target value
		final int NUMBER_OF_FEATURES=WINDOW_SIZE*3;		// minus 1: last element is the target value
		double[] f=new double[NUMBER_OF_FEATURES];
		for (int i=0; i<history.size()-1; i++){
			f[3*i]=history.get(i).lightL;
			f[3*i+1]=history.get(i).lightM;
			f[3*i+2]=history.get(i).lightR;
			//f[3*i+2]=history.get(i).action[1];
		}
		f[NUMBER_OF_FEATURES-3]=history.lastElement().lightL;
		f[NUMBER_OF_FEATURES-2]=history.lastElement().lightM;
		f[NUMBER_OF_FEATURES-1]=history.lastElement().lightR;
		
		return f;
		
		// features:
//		double[] features=new double[NUMBER_OF_FEATURES+NUM_LAST_ACTIONS];
//		features[DIST_TURN]=100;
//		int index=WINDOW_SIZE-1;
//		while(index>=0 && history.get(index).lightL<EPSILON && history.get(index).lightR<EPSILON){
//			index--;
//		}
//		if (index>=0){
//			if (index< WINDOW_SIZE-1){		//
//				if (history.get(index).lightL<EPSILON && history.get(index).lightR>EPSILON){
//					features[TURN_RIGHT]=1; 			// close to a turn, to right
//				}else if (history.get(index).lightL>EPSILON && history.get(index).lightR<EPSILON){
//					features[TURN_LEFT]=1; 			// close to a turn, to left
//				}
//				features[DIST_TURN]=1.0/(WINDOW_SIZE-1-index);
//			}else{
//				if (history.get(index).lightL>EPSILON && history.get(index).lightR>EPSILON){
//					features[BOTH_ON_TAPE]=1; 			// both sensors on the tape 
//					while(index>=0 && history.get(index).lightL>EPSILON && history.get(index).lightR>EPSILON){
//						index--;
//					}
//					if (index>=0){
//						while(index>=0 && history.get(index).lightL<EPSILON && history.get(index).lightR>EPSILON){
//							index--;
//						}
//						if (index>=0){
//							while(index>=0 && history.get(index).lightL<EPSILON && history.get(index).lightR<EPSILON){
//								index--;
//							}
//							if (index>=0){
//								if(index>=0 && history.get(index).lightL<EPSILON && history.get(index).lightR>EPSILON){
//									features[JUST_TURNED_RIGHT]=1;
//								}
//							}
//						}
//					}
//				} else if (history.get(index).lightL<EPSILON && history.get(index).lightR>EPSILON){
//					features[ON_EDGE_RIGHT]=1; 			// being on the edge, edge on right side
//				} else if (history.get(index).lightL>EPSILON && history.get(index).lightR<EPSILON){
//					features[ON_EDGE_LEFT]=1; 			// being on the edge, edge on the left side
////					if (history.get(index-1).lightL>EPSILON && history.get(index-1).lightR>EPSILON){
////						features[GO_RIGHT]=1;
////					}
//				}
//			}
//		}else{
//			features[OFF_TAPE]=1; 			// out of tape		
//		}
//		int hLength=history.size();
//		for(int i=0; i<NUM_LAST_ACTIONS; i++){
//			features[NUMBER_OF_FEATURES+i]=history.get(hLength-1-NUM_LAST_ACTIONS+i).action[1];
//		}
//		return features;
	}
	
	private double[] plan(){
		numberOfNodes = 0;
		// build the tree and perform the planning
		Node root = new Node(null, ++numberOfNodes, robot.getPosition(), robot.getAngleL(), 
				robot.getAngleR(), robot.getVelL(), robot.getVelR(), robot.getDirection());
		root.setRewardU(0);
		
		Vector<Node> leaf_nodes=new Vector<Node>(), 
					 max_node_children=new Vector<Node>();
		leaf_nodes.add(root);
		
//		double d;
//		Vector<Node> leaf_children, new_leaf_nodes=new Vector();
//		while(numberOfNodes<NUM_NODES){
//			new_leaf_nodes=new Vector();
//			for (Node leaf:leaf_nodes){
//				leaf_children=setChildren(leaf);
//				new_leaf_nodes.addAll(leaf_children);
//			}
//			leaf_nodes=new_leaf_nodes;
//		}
//		
//		// find the action in the leaves with the biggest u
//		double max_u=-Double.MAX_VALUE;
//		Node max_node=new Node(null, 0);
//		for (Node leaf:leaf_nodes){
//			if (leaf.getU()>max_u){
//				max_u=leaf.getU();
//				max_node=leaf;
//			}
//		}
//		
//		Node temp=new Node(null,0);
//		temp=max_node;
//		Node bp=temp;
//
//		while(max_node.getParent().getParent()!=null){
//			max_node=max_node.getParent();
//		}
//		
//		return max_node.torques;
		
		
		// build the tree
		Node max_node=new Node(null, ++numberOfNodes);
		double b, max_u;
		while(numberOfNodes<NUM_NODES){
			max_node=root;
			while(max_node.getChildren().size()>0){
				max_node=max_node.bestChild;
			}
			max_node_children=setChildren(max_node);		
			// we no longer need parent's u values
			double max_b=-Double.MAX_VALUE;
			for (Node node: max_node_children){
				b=node.getU()+Math.pow(GAMMA, node.getDepth()+1.0)/(1-GAMMA); 
				if (b>max_b)
				{
					max_node.bestChild = node;
					max_b = b;
				}
			}
			max_node.setU(max_b);
			Node node = max_node;
			// now propagate the new value up in the tree
			while(node.getParent()!=null){
				Node parent = node.getParent();
				if (max_b<parent.getU()) // the upper bound decreased
				{
					max_b = parent.updateBestChild();
				}
				else if (max_b>parent.getU())
				{
					throw new RuntimeException("B value growing from " + parent.getU() + " to " + max_b);
				}
				else
					break; // nothing to propagate				
				node = parent; // move up in the tree
			}
		}
			
		//last_highlevel_action[0]=root.bestChild.speed;
		//last_highlevel_action[1]=root.bestChild.angle;
		
		//return root.bestChild.torques;
		double[] a=new double[2];
		a[0]=root.bestChild.speed;
		a[1]=root.bestChild.angle;
		return a;
	}
	
	private Vector<Node> setChildren(Node node){
		TwoWheeledRobotModel curRobotModel = new TwoWheeledRobotModel();
		TwoWheeledRobotModel nextRobotModel = new TwoWheeledRobotModel();
		
		Node nextNode;
		Force force=new Force(0,0);
		Vector2D vel, torque;
		double normVel, reward, dir, angle;
		for (int i=0; i<SPEED.length; i++){
			for (int j=0; j<ANGLE.length; j++){
				curRobotModel.setWheelState(node.wheelAngle1, node.wheelAngle2, node.wheelAngleVel1, node.wheelAngleVel2);
				
				//angle=node.getDirection()+ANGLE[j];
				angle=ANGLE[j];
				torque=getTorque(curRobotModel, SPEED[i], angle);
				curRobotModel.setTorques(torque.x, torque.y);
				
				curRobotModel.setRigidBodyDynamics(node);
				nextNode = new Node(node,++numberOfNodes);
				nextRobotModel.setRigidBodyDynamics(nextNode);				 
				curRobotModel.update(force, nextRobotModel, 20.0/1000.0, SimulatorMain.length_scale);
				
				vel=nextRobotModel.computeVel();
				normVel=Math.sqrt(vel.x*vel.x + vel.y*vel.y);
				dir=Math.atan2(vel.y, vel.x);
				
				nextNode.setState( nextNode.position, nextRobotModel.getWheelAngle1(), nextRobotModel.getWheelAngle2(),
						nextRobotModel.getWheelAngleVel1(), nextRobotModel.getWheelAngleVel2(),dir);
				nextNode.torques[0]=torque.x;
				nextNode.torques[1]=torque.y;				
				nextNode.speed=SPEED[i];
				// hey! save the angle wrt robot
				nextNode.angle=angle-node.getDirection();
				
				reward=getReward(nextNode, normVel);
				
				// robot or nextNode???				
				Turn turn=getNextTurn(robot.getPosition(), robot.getDirection()); 
				//Turn turn=getNextTurn(nextNode.getPosition(), nextNode.getDirection());
				double minDif=Double.MAX_VALUE, bestDir=-1;
				Vector2D v1;
				Vector2D v2;
				// ???? can be improved
				if (nextNode.getPosition().distance(turn.center)<Math.sqrt(2)*SimulatorEngine.floorMap.hw){
					for (Turn tn:turn.nextTurns){					
						v1=new Vector2D(Math.cos(nextNode.getDirection()), Math.sin(nextNode.getDirection()));
						v2=new Vector2D(tn.center.x-turn.center.x, tn.center.y-turn.center.y);
						if (Math.acos(v1.dot(v2)/(v1.length()*v2.length())) < minDif){
							minDif=Math.acos(v1.dot(v2)/(v1.length()*v2.length()));
							bestDir=Math.atan2(tn.center.y-turn.center.y, tn.center.x-turn.center.x);
						}
					}
					//????????????????????????////
					if (Math.abs(shift(angle)-shift(bestDir))<EPSILON){
						reward+=TURN_WEIGHT;
					}
				}
				
				if (Math.abs(shift(angle)-shift(robot.getDirection()))<EPSILON){
					reward+=HEADING_WEIGHT;
				}
				nextNode.setRewardU(sigmoid(reward));				
				
				node.addChildren(nextNode);
			}//////////////////////////////////////
		}
		
		return node.getChildren();
	}
	
	private double shift(double angle){
		if (angle>=0 && angle<= 2*Math.PI)
			return angle;
		if (angle>2*Math.PI)
			return shift(angle-2*Math.PI);
		if (angle<0)
			return shift(angle+2*Math.PI);
		return angle;
	}
	
	private double getReward(Node nextNode, double normVel){
		double reward, disVert, disHor, norm_up, norm_down, norm_right, norm_left;
		Vector2D intersect_up, intersect_down, intersect_right, intersect_left, temp;

		LinkedList<Vector2D> corners;
		Vector2D corner;
		double minDif=Double.MAX_VALUE;
		
		// set the reward
		if (SimulatorEngine.floorMap==null){
			System.out.println("BADBAD");
		}
		boolean onTape1 = SimulatorEngine.floorMap.getShape().contains(nextNode.position.plus(new Vector2D(EPSILON,0)));
		boolean onTape2 = SimulatorEngine.floorMap.getShape().contains(nextNode.position.plus(new Vector2D(-EPSILON,0)));
		boolean onTape3 = SimulatorEngine.floorMap.getShape().contains(nextNode.position.plus(new Vector2D(0,EPSILON)));
		boolean onTape4 = SimulatorEngine.floorMap.getShape().contains(nextNode.position.plus(new Vector2D(0,-EPSILON)));
		
		double up, down, right, left;
		up=SimulatorEngine.floorMap.uy-SimulatorEngine.floorMap.hw;
		down=SimulatorEngine.floorMap.ly+SimulatorEngine.floorMap.hw;
		right=SimulatorEngine.floorMap.lx+SimulatorEngine.floorMap.hw;
		left=SimulatorEngine.floorMap.ux-SimulatorEngine.floorMap.hw;
		
		reward=0;
		if (onTape1 || onTape2 || onTape3 || onTape4){
			reward+=ON_TAPE_WEIGHT;
			
			corners=SimulatorEngine.floorMap.getShape().getPoints();
			
			intersect_up=new Vector2D(nextNode.position.x,up);
			intersect_down=new Vector2D(nextNode.position.x,down);
			intersect_right=new Vector2D(right,nextNode.position.y);
			intersect_left=new Vector2D(left,nextNode.position.y);
			
			temp=nextNode.position.minus(intersect_up);
			norm_up=Math.sqrt(temp.x*temp.x+temp.y*temp.y);
			temp=nextNode.position.minus(intersect_down);
			norm_down=Math.sqrt(temp.x*temp.x+temp.y*temp.y);
			temp=nextNode.position.minus(intersect_right);
			norm_right=Math.sqrt(temp.x*temp.x+temp.y*temp.y);
			temp=nextNode.position.minus(intersect_left);
			norm_left=Math.sqrt(temp.x*temp.x+temp.y*temp.y);
			
			disVert=Math.min(norm_up, norm_down);
			disHor=Math.min(norm_right, norm_left);
			
			if (Math.min(disVert, disHor)<SimulatorEngine.floorMap.hw)
				reward+=AWAY_FROM_EDGE_WEIGHT*Math.min(disVert, disHor);
			else reward+=AWAY_FROM_EDGE_WEIGHT*SimulatorEngine.floorMap.hw;

			reward+=SPEED_WEIGHT*normVel/*+DISTANCE_WEIGHT*normDis*/;
		}else{
			reward-=ON_TAPE_WEIGHT;
			disVert=Math.min(Math.abs(nextNode.position.y-up), Math.abs(nextNode.position.y-down));
			disHor=Math.min(Math.abs(nextNode.position.x-left), Math.abs(nextNode.position.x-right));

			
			reward+=-AWAY_FROM_EDGE_WEIGHT*Math.max(disVert, disHor);
		}
		
		return reward;
	}
	
	// a very simple implementation
	private Turn getNextTurn(Vector2D position, double direction) {
		Polygon temp;
		Vector2D p,p1,p2,p3,p4, src, dest;
		double dir, hw;
		double minDif=Double.MAX_VALUE;
		Turn t2=new Turn(null);
		for (Turn turn:SimulatorEngine.floorMap.turns){
			for (Turn nt:turn.nextTurns){
				// improve it
				// check if it is between them
				src=turn.center;
				dest=nt.center;
				hw=SimulatorEngine.floorMap.hw;
				dir=Math.atan2(dest.y-src.y, dest.x-src.x);
				src=src.plus(new Vector2D(hw*Math.cos(dir-Math.PI), hw*Math.sin(dir-Math.PI)));
				dest=dest.plus(new Vector2D(hw*Math.cos(dir), hw*Math.sin(dir)));
				p1=new Vector2D(src.x+hw*Math.cos(dir+Math.PI/2),src.y+hw*Math.sin(dir+Math.PI/2));
				p2=new Vector2D(src.x+hw*Math.cos(dir-Math.PI/2),src.y+hw*Math.sin(dir-Math.PI/2));
				p3=new Vector2D(dest.x+hw*Math.cos(dir-Math.PI/2),dest.y+hw*Math.sin(dir-Math.PI/2));
				p4=new Vector2D(dest.x+hw*Math.cos(dir+Math.PI/2),dest.y+hw*Math.sin(dir+Math.PI/2));
				
				temp=new Polygon();
				temp.addPoint(p1.x, p1.y);
				temp.addPoint(p2.x, p2.y);
				temp.addPoint(p3.x, p3.y);
				temp.addPoint(p4.x, p4.y);
				if (temp.contains(position)){
					Vector2D v1=new Vector2D(Math.cos(direction), Math.sin(direction));
					Vector2D v2=new Vector2D(dest.x-src.x, dest.y-src.y);
					if (Math.acos(v1.dot(v2)/(v1.length()*v2.length())) < minDif){
						minDif=Math.acos(v1.dot(v2)/(v1.length()*v2.length()));
						t2=nt;
					}										
				}
			}
		}
		
		minDif=Double.MAX_VALUE;
		if (t2.center==null){
			for (Turn turn:SimulatorEngine.floorMap.turns){
				if (position.distance(turn.center)<minDif){
					t2=turn;
					minDif=position.distance(turn.center);
				}
			}
		}
		
		return t2;
	}

	private Vector2D getTorque(TwoWheeledRobotModel model, double speed, double angle){
		//TwoWheeledRobotModel model = new TwoWheeledRobotModel();
		//model.setWheelState(robot.getAngleL(), robot.getAngleR(), robot.getVelL(), robot.getVelR() );
		final double refptdisp = 10.0E-2; //3.2E-2; //20E-2;
		Vector2D currVel = model.computeRefptVel(refptdisp);
		//double speed = 0.3;
		//double angle = 3.0*Math.PI/2.0+2.0*Math.PI/3.0;
		Vector2D err = new Vector2D( speed*Math.cos(angle), speed*Math.sin(angle) );
		err.minusEquals(currVel);
		Vector2D control = new Vector2D(err);
		control.timesEquals(10.000);
		Vector2D currAcc = model.computeRefptAcc(refptdisp);
		currAcc.timesEquals(-0.0); // a sensible value is -1.0
		control.plusEquals(currAcc);
		//Vector2D accel = model.computeRefptAcc(refptdisp);
		Vector2D torque = model.feedbacklinControl(control, refptdisp);

		return torque;
	}
	
	private double sigmoid(double x){
		return Math.exp(x)/(1+Math.exp(x));
	}
}
