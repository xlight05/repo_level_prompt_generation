package org.rlcommunity.critter;

/**
 * SimulatorComponentDiffuseLight
 *
 * This component deals with diffusive light-emitting objects and 
 *  the corresponding light sensors.
 *  The idea is that the whole surface of such objects is light
 *  emitting. If a sensor is above such a surface, the sensor
 *  responds to the surface.
 *
 * @author csaba
 */

//import java.awt.geom.Point2D;
//import java.util.*;

public class SimulatorComponentDiffuseLight implements SimulatorComponent {

    public static final String NAME = "difflight";

    public SimulatorComponentDiffuseLight() {
    }

    /** Computes what light sensors should receive given the current state,
      *  and set the result in the next state.
      *
      * @param pCurrent The current state of the system (must not be modified)
      * @param pNext  	The next state of the system (where results are stored)
      * @param delta  	The number of milliseconds elapsed between pCurrent
      *   				and pNext.
      */
    public void apply(SimulatorState pCurrent, SimulatorState pNext, int delta) 
    {
    	// enumerate all sensors
    	for (SimulatorObject sensor : pNext.getObjects(ObjectStateDiffuseLightSensor.NAME))
    	{
    		ObjectStateDiffuseLightSensor sensorState 
    			= (ObjectStateDiffuseLightSensor) sensor.getState(ObjectStateDiffuseLightSensor.NAME);
    		if (sensorState==null)
    			continue; // @@@ TODO: throw an exception
    		sensorState.setLightSensorValue(0);
    		// enumerate all the sources
    		for (SimulatorObject source : pNext.getObjects(ObjectStateDiffuseLightSource.NAME))
    		{
    			sensorState.sense( source, sensor );
    		}
    	}
    }
}

