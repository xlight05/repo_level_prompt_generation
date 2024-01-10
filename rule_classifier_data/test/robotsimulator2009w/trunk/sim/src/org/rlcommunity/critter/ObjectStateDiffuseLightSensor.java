package org.rlcommunity.critter;

/**
  * ObjectStateDiffuseLightSensor
  *
  * State component for an object which senses diffuse light sources.
  *
  * @author csaba
  */

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.rlcommunity.critter.Base.Vector2D;

public class ObjectStateDiffuseLightSensor implements ObjectState
{
  public static final String NAME = SimulatorComponentDiffuseLight.NAME + "sensor";
  enum SensorType { POINT, POINTSET, LINE, ARRAY, CIRC };
  final private SensorType sensorType = SensorType.CIRC;

  /** Sensing length (in the same unit as used by object positions), i.e., in cm */
  private static final double sensingLength = 10; 
  private int nxh = 4, nyh = 4; // half the number of points, x and y directions
  private double dx = 0.70/nxh, dy = 0.70/nyh; // [cm] resolution
  private int nr = 5;//3; // we will have nr circles + middle point
  private double dr = 0.70/nr;
  int cn = 4; // use cn * i points on the i-th circle; altogether we will have 1+cn * sum_{i=1}^nr i = 1 +cn *nr * (nr+1)/2.0 points
  
  private boolean 	collectFeatures = SimulatorMain.runSensorCalibrationExperiment;
  private int 		collectFeaturesCounter = 0;
  final private int collectFeaturesCounterLimit = 6524; // this is for all.dat, for mili_LS_linedense.dat use 1998;
  
  double weightArray[][] = new double[2*nxh+1][2*nyh+1];

  protected double aData;

  public ObjectStateDiffuseLightSensor()
  {
    aData = 0;
    double a = 1.0/(2*nxh+1)/(2*nyh+1);
    for (int i=-nxh; i<=nxh; ++i)
    {
    	for (int j=-nyh; j<=nyh; ++j)
    	{
    		weightArray[i+nxh][j+nxh]  = a;
    	}
    }
  }

  /** ObjectState interface */
  
  /** Returns a unique identifier for this type of state. */
  public String getName() { return NAME; }

  public Object clone()
  {
    ObjectStateDiffuseLightSensor newSensor = new ObjectStateDiffuseLightSensor();
    newSensor.copyFrom(this);

    return newSensor;
  }

  protected void copyFrom(ObjectState os)
  {
    ObjectStateDiffuseLightSensor sensor = (ObjectStateDiffuseLightSensor)os;
  
    this.aData = sensor.aData;
  }
  
  protected void setLightSensorValue (double intensity)
  {
      aData = intensity;
  }

  protected void addLightSensorValue (double intensity)
  {
      aData += intensity;
  }
  
  protected double getLightSensorValue ()
  {
   return aData;   
  }


  /** (Potentially) draw something about the state; may be null. This
    *  most likely should be moved out of here when we have time.
    *
    * @param g The canvas to draw on
    * @param parent The owner of this state
    */
  public void draw(Graphics g, SimulatorObject parent)
  {
	  Vector2D objPos = parent.getPosition();	  
      double objDir = parent.getDirection();

	  Color tempC = g.getColor();
	  final int radius = 2;
	  g.setColor( (aData>0 ? Color.YELLOW : Color.LIGHT_GRAY ) );
	  g.fillOval((int)objPos.x-radius, (int)objPos.y-radius, 2*radius+1, 2*radius+1);
	  g.setColor(Color.RED.darker());
	  g.drawOval((int)objPos.x-radius, (int)objPos.y-radius, 2*radius+1, 2*radius+1); 
      
	  g.setColor(Color.BLACK);
	  Vector2D dir = new Vector2D(sensingLength* Math.cos(objDir), sensingLength* Math.sin(objDir));
	  Vector2D start = objPos.minus(dir);
	  Vector2D end   = objPos.plus(dir);
      g.drawLine( (int)start.x, (int)start.y, (int)end.x, (int)end.y );

      g.setColor(Color.LIGHT_GRAY);
	  g.drawString(parent.getLabel(), (int)objPos.x + 5, (int)objPos.y + 5);

      g.setColor(tempC);	  
  }

  /** Provides a mean of clearing whatever data this ObjectState contains
    *  and resetting it to the default values. Meant to be used when 
    *  re-initializing a state.
    */
  public void clear()
  {
    aData = 0;
  }
  public void resetState() {
  }
  
 /** Get the intensity of light emitted from the surface. */
final static double getSourceIntensity( SimulatorObject source )
  {
	  ObjectStateDiffuseLightSource sourceState
	  	= (ObjectStateDiffuseLightSource)source.getState(ObjectStateDiffuseLightSource.NAME);
	  return sourceState.getIntensity();
  }

/** Update the response of this sensor pertaining to light emitted from source. 
 * 
 * @param source	The source emitting diffuse light.
 * @param parent	The parent of the sensor (needed for position and orientation)
 */
	public void sense(SimulatorObject source, SimulatorObject parent) 
	{
		//parent.setPosition(new Vector2D(250,200));
		//parent.setDirection(Math.PI/2.0);

		Vector2D p = parent.getPosition();
		
		switch (sensorType)
		{
		case POINT: { 
			// a very simple implementation: If the sensor's position is above
			// the light source position, the sensor responds
			if (source.getShape().contains(p))
			{
				aData = Math.max(aData, 0.5+0.5*getSourceIntensity(source));
			}
		}
		break;
		case POINTSET: { // same as above, but use a finite number of points on a line
			double dir = parent.getDirection();
			Vector2D sdir = new Vector2D(sensingLength* Math.cos(dir),sensingLength* Math.sin(dir));
			p.minusEquals(sdir);
			final int TESTPOINTS = 10;
			sdir.timesEquals(2.0/TESTPOINTS);
			Polygon shape = source.getShape();
			double response = 0;
			for (int i=0; i<=TESTPOINTS; ++i)
			{
				response += (shape.contains(p) ? 1 : 0);
				p.plusEquals(sdir); 
			}
			response /= TESTPOINTS;
			if (response>0)
			{
				response = 0.2+0.8*response*getSourceIntensity(source);
				aData = Math.max(aData, response);
			}
		}
		break;
		case ARRAY: {
			// the array has (2 nxh + 1) (2 nyh + 1) points
			// dx is the displacement between the array's sensing points in the ("x") horizontal direction
			// dy is the displacement between the array's sensing points in the ("y") vertical direction
			double dir = parent.getDirection();
			Vector2D hdir = new Vector2D( dx* Math.cos(dir),dx* Math.sin(dir));
			Vector2D vdir = new Vector2D(-dy* Math.sin(dir),dy* Math.cos(dir));
			Vector2D hdirs = hdir.times(-nxh);
			Polygon shape = source.getShape();
			double intensity = getSourceIntensity(source);
			double response = 0;
			for (int j=-nyh; j<=nyh; ++j) // scanning line by line
			{
				Vector2D sp = new Vector2D(p);
				sp.plusEquals(vdir.times(j));
				sp.plusEquals(hdirs);
				for (int i=-nxh; i<=nxh; ++i)
				{
					double r = (shape.contains(p) ? intensity : 0);
					response += weightArray[i+nxh][j+nyh] * r;
					sp.plusEquals(hdir);
				}
			}
			aData = Math.max(aData, response);
		}
		break;
		case CIRC: {
			// we sense in concentric circles around the center p
			Polygon shape = source.getShape();
			double intensity = getSourceIntensity(source);
			double dir = parent.getDirection();
			double response = 0;
			int c = 0;
			double r = (shape.contains(p) ? intensity : 0);
			response += r;
			if (collectFeatures) { openFile(); writeFile(r); }
			++c;
			Vector2D v = new Vector2D();
			for (int i=1; i<=nr; ++i)
			{
				double radius = dr * i;
				int n = cn * i;
				double a = Math.PI/n;
				double a0 = dir+(i%2==1?0.0:a/2.0);
				for (int j=0; j<n; ++j)
				{
					v.x = radius * Math.cos(a0+a*j);
					v.y = radius * Math.sin(a0+a*j);
					r = (shape.contains(p.plus(v)) ? intensity : 0);
					if (collectFeatures) { writeFile(r); }
					response += r;
					++c;
				}
			}
			if (collectFeatures) 
			{ 
				writeFileLn(); 
				++collectFeaturesCounter; 
				if (collectFeaturesCounter==collectFeaturesCounterLimit) 
				{ 
					closeFile(); 
					collectFeatures=false; 
				} 
			}
			response/=c;
			aData = Math.max(aData, response);			
		}
		break;
		default: {
			// sensing happens along a line
			double dir = parent.getDirection();
			Vector2D sdir = new Vector2D(sensingLength* Math.cos(dir),sensingLength* Math.sin(dir));
			Ray r1 = new Ray(p,sdir);	
			Ray r2 = new Ray(p,sdir.timesEquals(-1.0));	
			RayIntersection ri1 = source.getShape().intersect(r1);
			RayIntersection ri2 = source.getShape().intersect(r2);
			double alpha1 = (ri1==null ? -1 : ri1.rayAlpha);
			double alpha2 = (ri2==null ? -1 : ri2.rayAlpha);
			if (!source.getShape().contains(p)) 
			{
				// point outside of the source
				// alpha1, alpha2 are distances in the direction
				// of the sensor to the source
				double r = 0;
				if (alpha1>0 && alpha1<1) // segment intersects!
					r += 0.25*(1.0-alpha1); // response is highly nonlinear..:) and cumulative
				if (alpha2>0 && alpha2<1) // segment intersects!
					r += 0.25*(1.0-alpha2);
				if (r>0)
					aData = Math.max( aData, 0.5+r ); // response is highly nonlinear..:) and cumulative				
			}
			else
			{
				// point inside the source: the intersection determines the overlap
				alpha1 = Math.min(alpha1, 1.0); // limit response to the length of the sensing segment
				alpha2 = Math.min(alpha2, 1.0);
				aData = Math.max( aData, 0.5+0.5*(alpha1+alpha2));
			}
		} // closing bracket of switch
	 }
  }
	FileWriter fstream;
	BufferedWriter out;
	protected void openFile()
	{
		try 
		{
			if (fstream==null)
			{
				fstream = new FileWriter("features.dat");
				out = new BufferedWriter(fstream);
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

	}
	protected void closeFile()
	{
		if (out!=null)
		{
			System.out.println("DONE!");
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		out = null;
	}
	
	protected void writeFile(double r)
	{
		try {
			out.write(r+"\t");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	protected void writeFileLn()
	{
		try {
			out.write("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

