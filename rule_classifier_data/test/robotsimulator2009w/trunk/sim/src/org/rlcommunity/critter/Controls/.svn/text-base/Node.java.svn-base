package org.rlcommunity.critter.Controls;

import java.util.Vector;

import org.rlcommunity.critter.Base.RigidBodyDynamicsData;
import org.rlcommunity.critter.Base.Vector2D;

public class Node implements RigidBodyDynamicsData{
	private Node parent;
	private Vector<Node> children;
	private double reward; // reward that we receive when playing this children in its parent node
	private int depth;
	private double u; 	// u: the sum of discounted rewards obtained along the (nite) path from the root to this node

	double[] torques=new double[2]; // this node is the result of this action from its parent node
	double speed;
	double angle;
	
	Node bestChild;
	
	Vector2D position;
	double direction;
	
	//Vector2D velociy;
	//double angVelocity;
	
	double wheelAngle1;
	double wheelAngle2;
	double wheelAngleVel1;
	double wheelAngleVel2;
	
	int id;
	
	public Node(Node parent, int anId){
		id = anId;
		this.parent=parent;
		this.children=new Vector<Node>();
		if (this.parent!=null)
			this.depth=parent.depth+1;
		else this.depth=0;
	}
	
	public Node(Node parent, int anId, Vector2D position, double wheelAngle1, 
			double wheelAngle2, double wheelAngleVel1, double wheelAngleVel2, double direction){
		id = anId;
		this.parent=parent;
		
		this.children=new Vector<Node>();
		if (this.parent!=null)
			this.depth=parent.depth+1;
		else 
			this.depth=0;
		setState(position,wheelAngle1,wheelAngle2,wheelAngleVel1,wheelAngleVel2,direction);
	}
	
	public void setState(Vector2D position, double wheelAngle1, 
			double wheelAngle2, double wheelAngleVel1, double wheelAngleVel2, double direction) {
		this.position=position;
		this.direction=direction;
		this.wheelAngle1=wheelAngle1;
		this.wheelAngle2=wheelAngle2;
		this.wheelAngleVel1=wheelAngleVel1;
		this.wheelAngleVel2=wheelAngleVel2;
	}
	
	
	public int getDepth(){
		return this.depth;
	}
	
	public Node getParent(){
		return this.parent;
	}
	
	public void setParent(Node parent){
		this.parent=parent;
	}
	
	public Vector<Node> getChildren(){
		return this.children;
	}
	
	public void setU(){
		if (parent!=null)
			this.u=parent.u+Math.pow(MBController.GAMMA, this.depth)*this.reward;
		else
			this.u=this.reward;
	}
	
	public void setU(double u){
		this.u=u;
	}
	
	public double getU(){
		return this.u;
	}
	
	public boolean equals(Node node){
//		if (position.x==node.position.x && position.y==node.position.y && wheelAngle1==node.wheelAngle1 &&
//				wheelAngle2==node.wheelAngle2 && wheelAngleVel1==node.wheelAngleVel1 && wheelAngleVel2==node.wheelAngleVel2)
//			return true;
//		return false;
		return (node.id==id);
	}
	
	public void setRewardU(double reward){
		this.reward=reward;
		setU();
	}
	
	public double getReward(){
		return this.reward;
	}
	
	public void addChildren(Node child){
		this.children.add(child);
	}
	
	public void setPosition(Vector2D pos){
		this.position=pos;
	}
    public void setDirection(double dir){
    	
    }
	public void setVelocity(Vector2D vel){
		
	}
    public void setAngVelocity(double omega){
    	
    }
    
    public Vector2D getPosition(){
    	return position;
    }
    public double   getDirection(){
    	return direction;
    }
    public Vector2D getVelocity(){
    	return new Vector2D();
    }
    
    /////////////////////////////////////////////////////////////////////////
    public double   getAngVelocity(){
    	return -1;
    }
	public void resetState(){
		// do something!
	}
	/** Note that this intentionally does not perform a deep copy */
	public Object shallowClone(){
		// who cares!
		return null;
	}

	// visit all children and set this nodes u value to the max of those, plus update bestChild
	public double updateBestChild() {
		u = Double.NEGATIVE_INFINITY;
		for (Node n : children)
		{
			if (n.getB()>u)
			{
				bestChild = n;
				u = n.getB();
			}
		}
		return u;
	}

	private double getB() {
		// TODO Auto-generated method stub
		if (children.size()==0)
			return u+Math.pow(MBController.GAMMA, this.depth+1.0)/(1.0-MBController.GAMMA);
		else
			return u;
	}

}
