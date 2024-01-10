package org.rlcommunity.critter;

public class StopperImpl implements Stopper {
	protected int elapsed;
	protected int timeLimit;
	StopperImpl( int timeLimit )
	{
		assert(timeLimit>0);
		elapsed = 0;
		this.timeLimit = timeLimit;
	}
	public void copyTime(Stopper that) {
		elapsed = that.elapsed();
	}
	public void copyFrom(StopperImpl that) {
		elapsed = that.elapsed();
		timeLimit = that.timeLimit;
	}

	public void elapsed(int time) {
		elapsed += time;
	}

	public boolean timeout() {
		if (elapsed>timeLimit)
		{
			elapsed = 0;
			return true;
		}
		return false;
	}
	public int elapsed() {
		return elapsed;
	}

}
