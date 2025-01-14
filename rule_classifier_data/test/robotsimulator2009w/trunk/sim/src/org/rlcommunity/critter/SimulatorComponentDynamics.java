package org.rlcommunity.critter;

import org.rlcommunity.critter.Base.Force;
import org.rlcommunity.critter.Base.Vector2D;

/**
 * SimulatorComponentDynamics
 *
 * This SimulatorComponent encodes all the dynamics interactions. As well
 *  as modifying data for the ObjectStateDynamics state of objects, 
 *  it also is in charge of modifying position.
 *
 * @author Marc G. Bellamre
 * @author Anna Koop
 */
public class SimulatorComponentDynamics implements SimulatorComponent {

    public static final String NAME = "dynamics";

    public SimulatorComponentDynamics( ) {
    }

    /** Applies physics to the current state to obtain the next state.
     * Roughly speaking, the following happens (in order):
     * 
     *  1. Go through the list of forces for each object, assume each 
     *  force was applied for a unit of time, and calculate the
     *  resulting change in velocity and position. Store these values in pNext.
     *  2. Check for collisions in pNext. For every collision detected, 
     *  calculate the collision response as a force in pNext 
     *  and reset the position in pNext to the current position.
     * 
     *  Note that pNext will have bad information in it until this method
     *  modifies it.
     *
     *  @param pCurrent The current state of the system
     *  @param pNext    The next state of the system (modified by this method)
     *  @param delta    The amount of time (in ms) between the current and
     *         next states.
     */
    public void apply(SimulatorState pCurrent, SimulatorState pNext, int delta) {
        // Walk through all the objects in the current state that can be 
        //  affected by physics
        for (SimulatorObject obj : pCurrent.getObjects()) {
            // If no physics data, ignore this object
            ObjectState os = obj.getState(SimulatorComponentDynamics.NAME);
            if (os == null) 
                continue;

            ObjectStateDynamics dynData = (ObjectStateDynamics) os;

            // Find the corresponding object in the next state
            SimulatorObject newObj = pNext.getObject(obj);
            ObjectStateDynamics newDynData =
                    (ObjectStateDynamics) newObj.getState(SimulatorComponentDynamics.NAME);

            // clean up any residue from before, the next state is solely 
            // determined by the current state and the forces acting on it
            // need some way of making sure this is function doesn't exit
            // without setting the position correctly
            newDynData.clearAll();
            
            double dt = delta/1000.0; // elapsed time in seconds; remember delta is in milliseconds.

            dynData.update( obj, newDynData, newObj, dt, SimulatorMain.length_scale );
            
        }
        // at this point, all the dyn objects in pNext should have their position
        // direction, ang velocity and  velocity set

        checkForCollisions(pCurrent, pNext, delta);

    }

    /**
     * This runs the objects in pNext to see if they are in collision with anything
     * If there is a collision, the next position of the object(s) are reset to their
     * current positions.
     * The forces resulting from the collision are added to the force list for the 
     * state.
     * We assume that pCurrent contains no collisions.
     * This should result in pNext containing no collisions
     * 
     *  @param pCurrent The current state of the system
     *  @param pNext    The next state of the system (modified by this method)
     *  @param delta    The amount of time (in ms) between the current and
     *         next states.
     **/
    private void checkForCollisions(SimulatorState pCurrent, SimulatorState pNext, int delta) {
        boolean positionReset = true;
        while (positionReset) {
            positionReset = false;

            for (SimulatorObject obj : pNext.getObjects(SimulatorComponentDynamics.NAME)) {
                // we only need to check for collisions if the object
                // has moved
                if (!obj.geometryEquals(pCurrent.getObject(obj))) {
                    for (SimulatorObject compObj : pNext.getObjects(SimulatorComponentDynamics.NAME)) {

                        //ignore this if it is the same object

                        if (compObj.getId() == obj.getId()) {
                            continue;
                        }


                        //check for a collision between these
                        Collision pt = obj.collidesWith(compObj);
                        if (pt != null) {
                            // Added by MGB
                            //  Anna, you can take this part out. I apologize
                            //  for conflicts!
                            debugAddBump(obj, pt.point);
                            debugAddBump(compObj, pt.point);
                            // End added by MGB
 //                           Vector2D n = pt.normal;

                            positionReset = true;
                            SimulatorObject objP = pCurrent.getObject(obj);
                            SimulatorObject compObjP = pCurrent.getObject(compObj);
                            

                            //@TODO!!!
                            System.out.println("Collision at " + pt.point +
                                    "between " + obj + " and " + compObj + "!");

                            Collision checkP = objP.collidesWith(compObjP);
                            if( checkP != null ) {
                                System.out.println(" ORIGINAL POSITION IN COLLISION AT " + checkP.point);
                            }
                            obj.setGeometry(objP);
                            compObj.setGeometry(compObjP);

                            //now double-check that collision has been resolved
                            // this check should fail. Pt should be null.
                            pt = obj.collidesWith(compObj);
                            if (pt != null) {
                                //should probably throw exception here
                                System.out.println("Still in collision at " + pt.point + "!!!");
                            }

                            // now get the thing we can actually modify
                            ObjectStateDynamics o1 = (ObjectStateDynamics) obj.getState(SimulatorComponentDynamics.NAME);
                            ObjectStateDynamics o2 = (ObjectStateDynamics) compObj.getState(SimulatorComponentDynamics.NAME);

                            /**
                             * TRYING OUT COLLISION FORCES
                             *
                            // calculate forces

                            double ma = o1.getMass();
                            double mb = o2.getMass();
                            double Ia = o1.getMomentInertia();
                            double Ib = o2.getMomentInertia();
                            double e = o1.getCoefficientRestitution(o2);

                            // this will have to be extended when I add angular motion
                            ObjectStateDynamics o1p = (ObjectStateDynamics) objP.getState(SimulatorComponentDynamics.NAME);
                            ObjectStateDynamics o2p = (ObjectStateDynamics) compObjP.getState(SimulatorComponentDynamics.NAME);
                            Vector2D vab = o1p.getVelocity().minus(o2p.getVelocity());

                            // might want to check here for infinite mass?
                            // or just let it fall out of the math
                            double impulse = (-(1 + e) * vab.dot(n)) /
                                    n.dot(n) * (1 / ma + 1 / mb);
                            // this means post-thrust velocities aren't used. hmm.
                            Vector2D vap = o1.getVelocity().plus(n.times(impulse / ma));
                            Vector2D vbp = o2.getVelocity().minus(n.times(impulse / mb));

                            checkSpeed(vap,o1);
                            checkSpeed(vbp,o2);
                            o1.setVelocity(vap);
                            o2.setVelocity(vbp);
                              */
                            /**
                             * COLLISIONS AS ENERGY SINKS
                             *   */
                            // Collisions are energy sinks
                            obj.clearVelocities();
                            compObj.clearVelocities();
//                            o1.setVelocity(new Vector2D(0,0));
//                            o1.setAngVelocity(0);
//                            o2.setVelocity(new Vector2D(0,0));
//                            o2.setAngVelocity(0);
                           
                            System.out.println("Post collision velocity " + o1.getVelocity() + " " + o2.getVelocity());
                        }
                    }
                }
            }
        } //end while we need to check for collisions
    }

    /**
     * This is a helper function for setting the speed within legal ranges
     *
     * @param v - the vector to change the speed of
     * @param oDyn - the Dynamics state object that gives the max and min speed
     */
    public void checkSpeed(Vector2D v, ObjectStateDynamics oDyn) {
        double speed = v.length();
        if(speed > oDyn.getMaxSpeed()) {
            v.normalize();
            v.timesEquals(oDyn.getMaxSpeed());
        }
        if(speed < oDyn.getMinSpeed()) {
            v.normalize();
            v.timesEquals(oDyn.getMinSpeed());
        }
    }

    /** Added by MGB - remove at your leisure */
    public void debugAddBump(SimulatorObject obj, Vector2D pt) {
        ObjectState os = obj.getState(ObjectStateBumpSensor.NAME);

        if (os == null) {
            return;
        }
        ObjectStateBumpSensor bs = (ObjectStateBumpSensor) os;

        bs.addForce(new Force(new Vector2D(5, 5), pt));
    }
}
