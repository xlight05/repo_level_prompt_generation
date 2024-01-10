package org.rlcommunity.critter.Base;

/**
 * These exist all over the place, but for the sake of portability
 * and the fact that it is quite easy to implement, here it is.
 * 
 * @author Mike Sokolsky
 *
 */
public class Vector2D {
    
    public static final Vector2D ZERO = new Vector2D(0,0);
    /** The tolerance under which we consider a coordinate to be 0; 
      * most likely not the proper place for this */
    public static final double ZERO_TOL = 10E-16;

    public double x;
    public double y;

    /**
     * New Vector2D with 0 length
     */
    public Vector2D() {
        x = 0;
        y = 0;
    }

    /**
     * New Vector2D
     * @param _x X Value
     * @param _y Y Value
     */
    public Vector2D(double _x, double _y) {
        x = _x;
        y = _y;
    }

    /**
     * New Vector2D
     * @param vect Copy value of this vector
     */
    public Vector2D(Vector2D vect) {
        x = vect.x;
        y = vect.y;
    }

    /**
     * Adds the given vector to this one.
     * @param vect The vector to be added to this vector.
     * @return The result (as this)
     */
    public Vector2D plusEquals(Vector2D vect) {
        this.x += vect.x;
        this.y += vect.y;
        return this;
    }
    
    /**
     * Subtracts the given vector from this one.
     * @param vect The vector to be subtracted from this vector.
     * @return The result (as this)
     */
	public Vector2D minusEquals(Vector2D vect) {
        this.x -= vect.x;
        this.y -= vect.y;
        return this;
	}
    

    /**
     * Adds the given vector to this one and returns the result as a 
     *  new object.
     *
     * @param vect The vector to be added to this vector.
     * @return The result (as a new vector)
     */
    public Vector2D plus(Vector2D vect) {
        return new Vector2D(this.x + vect.x, this.y + vect.y);
    }

    /**
     * Subtracts the given vector from this one; does not modify this vector.
     * @param vect The vector to be subtracted this vector.
     * @return The result
     */
    public Vector2D minus(Vector2D vect) {
        return new Vector2D(this.x - vect.x, this.y - vect.y);
    }
    
    /** Returns the 2-norm of the difference of this vector and v */
    public double distance(Vector2D v){
        return Math.sqrt( Math.pow(this.x - v.x,2.0) + Math.pow(this.y - v.y, 2.0) );
    }

    /**
     * Returns the dot product between this vector and the argument
     * @param v The vector to be dot-producted
     * @return the result
     */
    public double dot(Vector2D v) {
        return this.x*v.x+this.y*v.y;
    }
    
    public Vector2D times(double d) {
        return new Vector2D(d*x, d*y);
    }

    public Vector2D timesEquals(double d) {
        this.x = d*x;
        this.y = d*y;
        return this;
    }

    public Vector2D minus(double d) {
        return new Vector2D(x-d, y-d);
    }

    public Vector2D plus(double d) {
        return new Vector2D(x+d, y+d);
    }
    
    /** Rotates this vector by a given angle, and returns it (as a new instance).
     *
     * @param dir The angle of rotation
     * @return A rotated version of this vector
     */
    public Vector2D rotate(double angle) {
        return new 
          Vector2D(this.x * Math.cos(angle) - this.y * Math.sin(angle),
                   this.y * Math.cos(angle) + this.x * Math.sin(angle));
    }

    /**
     * length()
     * @return Length of the vector
     */
    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * direction()
     * @return Direction of the vector (-PI, PI)
     */
    public double direction() {
        return Math.atan2(y, x);
    }

    /** Normalizes the vector so that it has unit length.
     *  If this is the (0,0) vector, it is left unchanged.
     */
    public void normalize() {
        double l = length();
        if (l > 0) {
            x /= l;
            y /= l;
        }
    }

    /** Quantizes the vector so that its coordinates are set to 0 if they 
      *  are below the ZERO_TOL threshold.
      *
      * @return This vector (not a copy)
      */
    public Vector2D zeroize() {
      if (Math.abs(this.x) < ZERO_TOL)
        this.x = 0;
      if (Math.abs(this.y) < ZERO_TOL)
        this.y = 0;

      return this;
    }

    public boolean equals(Object o) {
        if (o.getClass() != this.getClass()) {
            return false;
        }
        if (x == ((Vector2D) o).x && y == ((Vector2D) o).y) {
            return true;
        } else {
            return false;
        }
    }

    public boolean equalsTolerance(Vector2D v, double tol) {
        if(Math.abs(x-v.x)>tol)
            return false;
        else if (Math.abs(y-v.y)>tol)
            return false;
        else
            return true;
    }

    @Override
    public String toString() {
        return "<" + Double.toString(x) + "," + Double.toString(y) + ">";
    }

    @Override
    public Object clone() {
        return new Vector2D(x, y);
    }

    public Vector2D reverse() {
        return new Vector2D(-x, -y);
    }
    
    public Vector2D min(Vector2D v) {
        if(this.length()<=v.length())
            return this;
        else
            return v;
        
    }
    
    public Vector2D max(Vector2D v) {
        if(this.length()>=v.length())
            return this;
        else
            return v;
    }

    public Vector2D getUnit() {
        Vector2D v = (Vector2D) this.clone();
        v.normalize();
        return v;
    }

    /** Returns a unit vector pointing into the specified direction */
	public static Vector2D unitVector(double direction) {
		return new Vector2D( Math.cos(direction), Math.sin(direction) );
	}
	
	/** Returns the length of the component of this vector
	 *   pointing in the specified direction 
	 */ 
	public double lengthInDirection( double direction ) {
		return x*Math.cos(direction)+y*Math.sin(direction);
	}

	public double crossProduct(Vector2D v) {
		return x*v.y-y*v.x;
	}
	
	public Vector2D timesLeftEquals( double[][] mat )
	{
		double xp = mat[0][0]*x + mat[0][1]*y;
		double yp = mat[1][0]*x + mat[1][1]*y;
		x = xp;
		y = yp;
		return this;
	}
	
	public Vector2D timesLeft( double[][] mat )
	{
		return new Vector2D(this).timesLeftEquals(mat);
	}

	public Vector2D timesLeftT( double[][] mat )
	{
		return new Vector2D(this).timesLeftTEquals(mat);
	}
	
	public Vector2D timesLeftTEquals( double[][] mat )
	{
		double xp = mat[0][0]*x + mat[1][0]*y;
		double yp = mat[0][1]*x + mat[1][1]*y;
		x = xp;
		y = yp;
		return this;
	}
	
	public static void invert( double[][] mat )
	{
		double det = mat[0][0]*mat[1][1]-mat[0][1]*mat[1][0];
		if (Math.abs(det)<ZERO_TOL)
			throw new RuntimeException("Matrix to be inverted is singular");
		// all element will be divided by det.
		// swap diagonals
		double z = mat[0][0]/det; mat[0][0]=mat[1][1]/det; mat[1][1] = z;
		// take opposite of off-diagonals
		mat[0][1] = -mat[0][1]/det;
		mat[1][0] = -mat[1][0]/det;
 	}
	public static double[][] mul(double[][] m1, double[][] m2)
	{
		double[][] ret = new double[2][2];
		ret[0][0] = m1[0][0]*m2[0][0]+m1[0][1]*m2[1][0];
		ret[0][1] = m1[0][0]*m2[0][1]+m1[0][1]*m2[1][1];
		ret[1][0] = m1[1][0]*m2[0][0]+m1[1][1]*m2[1][0];
		ret[1][1] = m1[1][0]*m2[0][1]+m1[1][1]*m2[1][1];
		return ret;
	}
	public static void addTo( double[][] m1, double[][] m2 )
	{
		m1[0][0] += m2[0][0];
		m1[0][1] += m2[0][1];
		m1[1][0] += m2[1][0];
		m1[1][1] += m2[1][1];
	}
	public static double[][] transpose( double[][] m )
	{
		double[][] ret = new double[2][2];
		ret[0][0] = m[0][0];
		ret[1][1] = m[1][1];
		ret[0][1] = m[1][0];
		ret[1][0] = m[0][1];
		return ret;
	}

}
