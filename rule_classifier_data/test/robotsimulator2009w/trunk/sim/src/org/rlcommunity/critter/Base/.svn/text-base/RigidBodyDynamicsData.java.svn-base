package org.rlcommunity.critter.Base;


public interface RigidBodyDynamicsData {
    public void setPosition(Vector2D pos);
    public void setDirection(double dir);	
	public void setVelocity(Vector2D vel);
    public void setAngVelocity(double omega);
    
    public Vector2D getPosition();
    public double   getDirection();
    public Vector2D getVelocity();
    public double   getAngVelocity();
	public void resetState();
	/** Note that this intentionally does not perform a deep copy */
	public Object shallowClone();
}
