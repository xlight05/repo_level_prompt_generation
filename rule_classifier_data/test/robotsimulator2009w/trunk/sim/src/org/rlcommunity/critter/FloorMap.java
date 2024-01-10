package org.rlcommunity.critter;


public class FloorMap extends SimulatorObject{
	
	public double hw; // half width
	public Turn[] turns;
	public int ux;
	public int uy;
	public int lx;
	public int ly;
	
	public FloorMap(String pLabel, int pId, double hw, int ux, int uy, int lx, int ly){
		super(pLabel, pId);
		this.hw=hw;
		this.ux=ux;
		this.uy=uy;
		this.lx=lx;
		this.ly=ly;
	}
}
