/**
 * 
 */
package org.rlcommunity.critter;

/** A Stopper can be used to keep track of time elapsed since the last timeout.
 * 
 * @author csaba
 */
public interface Stopper {
	/** Accumulate time.
	 * @param time 	elapsed time in milliseconds
	 */
	void elapsed( int time );
	/** Returns true if enough time has elapsed, in which case it
	 *  also resets the Stopper 
	 */
	boolean timeout();
	/** Returns the total time elapsed */
	int elapsed();
	/** Copy the time elapsed from another stopper */
	void copyTime( Stopper that );
}
