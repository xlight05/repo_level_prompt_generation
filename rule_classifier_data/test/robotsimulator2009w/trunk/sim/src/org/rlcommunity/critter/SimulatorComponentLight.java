package org.rlcommunity.critter;

import org.rlcommunity.critter.Base.Vector2D;

public class SimulatorComponentLight implements SimulatorComponent {

    public static final String NAME = "light";

    public SimulatorComponentLight() {
    }

    /** Computes what light sensors should receive given the current state,
      *  and set the result in the next state.
      *
      * @param pCurrent The current state of the system (must not be modified)
      * @param pNext  The next state of the system (where results are stored)
      * @param delta  The number of milliseconds elapsed between pCurrent
      *   and pNext.
      */
    /** Computes what light sensors should receive given the current state,
     *  and set the result in the next state.
     *
     * @param pCurrent The current state of the system (must not be modified)
     * @param pNext  The next state of the system (where results are stored)
     * @param delta  The number of milliseconds elapsed between pCurrent
     *   and pNext.
     */
    public void apply(SimulatorState pCurrent, SimulatorState pNext, int delta) {

        Scene scene = new Scene(pCurrent);
        Vector2D rayDirection;
        RayIntersection intersectData;
        Ray r;

        SimulatorObject sensor;
        ObjectStateLightSensor lightSensor;
//        ObjectStateLightSensor oldLightSensor;
        SimulatorObject oldSensor;
        Vector2D sensorPosition;

        SimulatorObject source;
        ObjectStateLightSource lightSource;
        Vector2D srcPosition;

        for (int Ksensor = 0; Ksensor < pNext.getObjects(ObjectStateLightSensor.NAME).size(); Ksensor++) 
        {            
            sensor = pNext.getObjects(ObjectStateLightSensor.NAME).get(Ksensor);
            lightSensor = (ObjectStateLightSensor) sensor.getState(ObjectStateLightSensor.NAME);
            sensorPosition = sensor.getPosition();

            oldSensor = pCurrent.getObject(sensor);
//            oldLightSensor = (ObjectStateLightSensor) oldSensor.getState(ObjectStateLightSensor.NAME);
           
            double sensorWidth = 5.0;//oldLightSensor.getSensorWidth();
            int numPixels = 5;//oldLightSensor.getNumPixels();
            double sensorDepth = 1.0;//oldLightSensor.getSensorDepth();

            double angleBetweenRays = (Math.atan((sensorWidth / numPixels) / sensorDepth));

            double currentRayAngle=0;
            //even or odd number of pixels..compute orientation of first ray in sensor (counter clockwise rotation from orientation of sensor).
            if(numPixels % 2 == 0) currentRayAngle = sensor.getDirection() - angleBetweenRays/2.0 + (Math.floor(numPixels / 2)) * angleBetweenRays; 
            else currentRayAngle = oldSensor.getDirection() + (Math.floor(numPixels / 2)) * angleBetweenRays;                
  
            //correct if first ray rotates through PI -PI boarder
            if(currentRayAngle > Math.PI) currentRayAngle = -Math.PI + currentRayAngle - Math.PI;
           
            double sumIntensity = 0; //store total intensity of each pixel in the sensor
            //for each pixel in the sensor
            for (int Ipixel = 0; Ipixel < numPixels; Ipixel++) 
            {
                //as we rotate clockwise through pixels may cross PI -PI boarder
                if (currentRayAngle < -Math.PI) currentRayAngle = Math.PI + currentRayAngle + Math.PI;
                
                //create ray                 
                rayDirection = Vector2D.unitVector(currentRayAngle);
                rayDirection.normalize();
                r = new Ray(sensorPosition, rayDirection);

                //remove robot so it doesn't block ray
                scene.removeSubtree(oldSensor.getRoot());

                //find out first point (object) ray i intersects with
                intersectData = scene.traceRay(r);
                
                //angle between robot's sensor ray and surface normal 
                if (intersectData==null || intersectData.point==null)
                	continue;
                double angle1 = sensorPosition.minus(intersectData.point).direction();
                double angle2 = intersectData.normal.direction();
                double angleOfIncidence = Math.abs(angle1) - Math.abs(angle2);

                //put robot back in...robot may block light on intersection point
                scene.addSubtree(oldSensor.getRoot());
                
                //for each light source sum up intensity at intersection point
                for (int Jsource = 0; Jsource < pCurrent.getObjects(ObjectStateLightSource.NAME).size(); Jsource++) 
                {
                    double intensity = 0;
                    
                    source = pCurrent.getObjects(ObjectStateLightSource.NAME).get(Jsource);
                    lightSource = (ObjectStateLightSource) source.getState(ObjectStateLightSource.NAME);

                    srcPosition = source.getPosition();
                    
                    //unlikely case where ray intersects the light source directly
                    double slope = (sensorPosition.x - srcPosition.x)/(sensorPosition.y - srcPosition.y);
                    double tol = Math.pow(10,-15);
                    if(Math.abs(slope - (rayDirection.x/rayDirection.y)) < tol)
                    {
                        intensity = lightSource.getIntensity() * (1.0 / Math.pow(srcPosition.distance(sensorPosition), 2)); 
                        sumIntensity += intensity;
                    }
                    else
                    {

                        //light source should have no polygon...incase marc changes his name
                        scene.removeSubtree(source.getRoot());
                    
                        //find angle between light source and surface normal
                        angle1 = srcPosition.minus(intersectData.point).direction();
                        double angleOfIncidence2 = Math.abs(angle1) - Math.abs(angle2);        

                        //is point illuminated?
                        if (scene.isVisible(srcPosition, intersectData.point)) 
                        {
                            double totalDistance = srcPosition.distance(intersectData.point) + intersectData.point.distance(sensorPosition);
                            double bouncePenalty = 1.0;
                            //intensity at sensor is function of distance and angles of incidence
                            intensity = bouncePenalty*lightSource.getIntensity() * (1.0 / Math.pow(totalDistance, 2)) + Math.abs(Math.cos(angleOfIncidence)*Math.cos(angleOfIncidence2));
                        //sum up light from multiple sources and from each pixel
                        sumIntensity += intensity;
                            
                        } 
                    }
                    
                }//loop over sources

                currentRayAngle -= angleBetweenRays; //next ray rotating clockwise

            } //loop over pixels


            //sensor reading is average of pixel readings
            lightSensor.setLightSensorValue(sumIntensity / numPixels);
            
            //System.out.println("sensor("+Ksensor+") intensity = " + lightSensor.getLightSensorValue() );        
            
        } //loop over sensors
            //System.out.println("------");
        
    }
    

}

