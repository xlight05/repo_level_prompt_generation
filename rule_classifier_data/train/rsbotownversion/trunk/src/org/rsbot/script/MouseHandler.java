package org.rsbot.script;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.util.logging.Logger;

/*
 * Credits to BenLand100 for writing the Spline Generation methods.
 */
public class MouseHandler {
	private static final Logger log = Logger.getLogger(MouseHandler.class.getName());
	private static final Methods methods = new Methods();

	/**
	 * Set to false in order to use Sweed_Raver's mouse methods by default.
	 */
	public static final boolean DEFAULT_MOUSE_PATH = true;

	/**
	 * The default mouse speed. This is the delay in ms between actual mouse
	 * moves. Lower is faster.
	 */
	public static final int DEFAULT_MOUSE_SPEED = 10;

	/**
	 * The maximum distance (in pixels) to move the mouse after clicks in either
	 * direction on both axes.
	 */
	public static final int DEFAULT_MAX_MOVE_AFTER = 0;

	/**
	 * @deprecated since 2009-03-09. You should not be accessing this directly.
	 *             It doesn't actually do anything anyway.
	 */
	@Deprecated
	public static int MouseSpeed = MouseHandler.DEFAULT_MOUSE_SPEED;

	/**
	 * The amount of time (in ms) it takes per bit of difficulty (look up Fitts
	 * Law) to move the mouse. This appears to partially control the speed of
	 * mouse movement.
	 */
	public static final int msPerBit = 105;

	/**
	 * The amount of time (in ms) it takes an average mouse user to realise the
	 * mouse needs to be moved
	 */
	public static final int reactionTime = 0; // Maybe bad!

	private static java.util.Random staticRandom = new java.util.Random();

	/**
	 * Applies a midpoint algorithm to the Vector of points to ensure pixel to
	 * pixel movement
	 * 
	 * @param points
	 *            The vector of points to be manipulated
	 */
	private static void adaptiveMidpoints(final java.util.Vector<Point> points) {
		int i = 0;
		while (i < points.size() - 1) {
			final Point a = points.get(i++);
			final Point b = points.get(i);
			if ((Math.abs(a.x - b.x) > 1) || (Math.abs(a.y - b.y) > 1)) {
				if (Math.abs(a.x - b.x) != 0) {
					final double slope = (double) (a.y - b.y) / (double) (a.x - b.x);
					final double incpt = a.y - slope * a.x;
					for (int c = a.x < b.x ? a.x + 1 : b.x - 1; a.x < b.x ? c < b.x : c > a.x; c += a.x < b.x ? 1 : -1) {
						points.add(i++, new Point(c, (int) Math.round(incpt + slope * c)));
					}
				} else {
					for (int c = a.y < b.y ? a.y + 1 : b.y - 1; a.y < b.y ? c < b.y : c > a.y; c += a.y < b.y ? 1 : -1) {
						points.add(i++, new Point(a.x, c));
					}
				}
			}
		}
	}

	/**
	 * Omits points along the spline in order to move in steps rather then pixel
	 * by pixel
	 * 
	 * @param spline
	 *            The pixel by pixel spline
	 * @param msForMove
	 *            The ammount of time taken to traverse the spline. should be a
	 *            value from {@link #fittsLaw}
	 * @param msPerMove
	 *            The ammount of time per each move
	 * @return The stepped spline
	 */
	public static Point[] applyDynamism(final Point[] spline, final int msForMove, final int msPerMove) {
		final int numPoints = spline.length;
		final double msPerPoint = (double) msForMove / (double) numPoints;
		final double undistStep = msPerMove / msPerPoint;
		final int steps = (int) Math.floor(numPoints / undistStep);
		final Point[] result = new Point[steps];
		final double[] gaussValues = MouseHandler.gaussTable(result.length);
		double currentPercent = 0;
		for (int i = 0; i < steps; i++) {
			currentPercent += gaussValues[i];
			final int nextIndex = (int) Math.floor(numPoints * currentPercent);
			if (nextIndex < numPoints) {
				result[i] = spline[nextIndex];
			} else {
				result[i] = spline[numPoints - 1];
			}
		}
		if (currentPercent < 1D) {
			result[steps - 1] = spline[numPoints - 1];
		}
		return result;
	}

	private static long binCoe(final int n, final int m) {

		/*
		 * int[] b = new int[n + 1]; b[0] = 1; for(int i = 1; i <= n; ++i) {
		 * b[i] = 1; for(int j = i - 1; j > 0; --j) b[j] += b[j - 1]; } return
		 * b[m];
		 */

		int x, y, z;
		if ((m >= 0) && (m <= n)) {
			x = MouseHandler.fract(n);
			y = MouseHandler.fract(m);
			z = MouseHandler.fract(n - m);
			return x / (y * z);
		} else
			return 1L;
	}

	public static int Distance(final int x, final int y, final int x1, final int y1) {
		return (int) Math.sqrt(Math.pow(x1 - x, 2) + Math.pow(y1 - y, 2));
	}

	/**
	 * Factorial ("n!").
	 * 
	 * @param n
	 *            The integer.
	 * @return The factorial.
	 */
	private static double fact(final int n) {
		double result = 1;
		for (int i = 1; i <= n; i++) {
			result *= i;
		}
		return result;
	}

	/**
	 * Calculates the ammount of time a movement should Mousetake based on
	 * Fitts' Law TIP: Do not add/subtract random values from this result,
	 * rather varry the targetSize value or do not move the same distance each
	 * time ;)
	 * 
	 * @param targetDist
	 *            The distance from the current position to the center of the
	 *            target
	 * @param targetSize
	 *            The maximum distence from the center of the target within
	 *            which the end point could be
	 * @return the ammount of time (in ms) the movement should take
	 */
	public static long fittsLaw(final double targetDist, final double targetSize) {
		return (long) (MouseHandler.reactionTime + MouseHandler.msPerBit * Math.log10(targetDist / targetSize + 1) / Math.log10(2));
	}

	private static int fract(final int n) {
		int i, result;
		result = 1;
		if (!(n == 0)) {
			for (i = 1; i <= n; i++) {
				result = result * i;
			}
		}
		return result;
	}

	/**
	 * Satisfies Integral[gaussian(t),t,0,1] == 1D Therefore can distribute a
	 * value as a bell curve over the intervel 0 to 1
	 * 
	 * @param t
	 *            = A value, 0 to 1, representing a percent along the curve
	 * @return The value of the gaussian curve at this position
	 */
	private static double gaussian(double t) {
		t = 10D * t - 5D;
		return 1D / (Math.sqrt(5D) * Math.sqrt(2D * Math.PI)) * Math.exp(-t * t / 20D);
	}

	/**
	 * Returns an array of gaussian values that add up to 1 for the number of
	 * steps Solves the problem of having using an intergral to distribute
	 * values
	 * 
	 * @param steps
	 *            Number of steps in the distribution
	 * @return An array of values that contains the percents of the distribution
	 */
	private static double[] gaussTable(final int steps) {
		final double[] table = new double[steps];
		final double step = 1D / steps;
		double sum = 0;
		for (int i = 0; i < steps; i++) {
			sum += MouseHandler.gaussian(i * step);
		}
		for (int i = 0; i < steps; i++) {
			table[i] = MouseHandler.gaussian(i * step) / sum;
		}
		return table;
	}

	/**
	 * Creates random control points for a spline. Written by Benland100
	 * 
	 * @param sx
	 *            Begining X position
	 * @param sy
	 *            Begining Y position
	 * @param ex
	 *            Begining X position
	 * @param ey
	 *            Begining Y position
	 * @param ctrlSpacing
	 *            Distance between control origins
	 * @param ctrlVariance
	 *            Max X or Y variance of each control point from its origin
	 * @return An array of Points that represents the control points of the
	 *         spline
	 */
	public static Point[] generateControls(final int sx, final int sy, final int ex, final int ey, int ctrlSpacing, int ctrlVariance) {
		final double dist = Math.sqrt((sx - ex) * (sx - ex) + (sy - ey) * (sy - ey));
		final double angle = Math.atan2(ey - sy, ex - sx);
		int ctrlPoints = (int) Math.floor(dist / ctrlSpacing);
		ctrlPoints = ctrlPoints * ctrlSpacing == dist ? ctrlPoints - 1 : ctrlPoints;
		if (ctrlPoints <= 1) {
			ctrlPoints = 2;
			ctrlSpacing = (int) dist / 3;
			ctrlVariance = (int) dist / 2;
		}
		final Point[] result = new Point[ctrlPoints + 2];
		result[0] = new Point(sx, sy);
		for (int i = 1; i < ctrlPoints + 1; i++) {
			final double radius = ctrlSpacing * i;
			final Point cur = new Point((int) (sx + radius * Math.cos(angle)), (int) (sy + radius * Math.sin(angle)));
			double percent = 1D - (double) (i - 1) / (double) ctrlPoints;
			percent = percent > 0.5 ? percent - 0.5 : percent;
			percent += 0.25;
			final int curVariance = (int) (ctrlVariance * percent);
			/**
			 * Hopefully {@link java.util.Random} is thread safe. (it is in Sun
			 * JVM 1.5+)
			 */
			cur.x = (int) (cur.x + curVariance * 2 * MouseHandler.staticRandom.nextDouble() - curVariance);
			cur.y = (int) (cur.y + curVariance * 2 * MouseHandler.staticRandom.nextDouble() - curVariance);
			result[i] = cur;
		}
		result[ctrlPoints + 1] = new Point(ex, ey);
		return result;
	}

	/**
	 * Generates a spline that moves no more then one pixel at a time TIP: For
	 * most movements, this spline is not good, use <code>applyDynamism</code>
	 * 
	 * @param controls
	 *            An array of control points
	 * @return An array of Points that represents the spline
	 */
	public static Point[] generateSpline(final Point[] controls) {
		final double degree = controls.length - 1;
		final java.util.Vector<Point> spline = new java.util.Vector<Point>();
		boolean lastFlag = false;
		for (double theta = 0; theta <= 1; theta += 0.01) {
			double x = 0;
			double y = 0;
			for (double index = 0; index <= degree; index++) {
				final double probPoly = MouseHandler.nCk((int) degree, (int) index) * Math.pow(theta, index) * Math.pow(1D - theta, degree - index);
				x += probPoly * controls[(int) index].x;
				y += probPoly * controls[(int) index].y;
			}
			final Point temp = new Point((int) x, (int) y);
			try {
				if (!temp.equals(spline.lastElement())) {
					spline.add(temp);
				}
			} catch (final Exception e) {
				spline.add(temp);
			}
			lastFlag = theta != 1.0;
		}
		if (lastFlag) {
			spline.add(new Point(controls[(int) degree].x, controls[(int) degree].y));
		}
		MouseHandler.adaptiveMidpoints(spline);
		return spline.toArray(new Point[spline.size()]);
	}

	private static Point getSplinePt(final Point[] mp, final double Theta) {
		int i, n;
		long XTemp = 0L;
		long YTemp = 0L;
		n = mp.length - 1;
		for (i = 0; i < n; i++) {
			XTemp = XTemp + (long) (MouseHandler.binCoe(n, i) * mp[i].x * Math.pow((1 - Theta), n - i) * Math.pow(Theta, i));
			YTemp = YTemp + (long) (MouseHandler.binCoe(n, i) * mp[i].y * Math.pow((1 - Theta), n - i) * Math.pow(Theta, i));
		}
		return new Point(Math.round(XTemp), Math.round(YTemp));
	}

	public static boolean isOnCanvas(final int x, final int y) {
		return (x > 0) && (x < 765) && (y > 0) && (y < 500);
	}

	public static Point[] makeMouseSplinePath(final int Sx, final int Sy, final int Ex, final int Ey, final int MaxDist, int CtrlDist, int CtrlVar) {
		MouseHandler.log.info("Moving from: " + Sx + ", " + Sy + " to: " + Ex + ", " + Ey);
		final ArrayList<Point> Controls = new ArrayList<Point>();
		int i, pts;
		boolean done = false;
		long dist, XInc, slope, b;
		double ThetaInc;
		dist = (long) Math.sqrt(Math.pow((Sx - Ex), 2) + Math.pow((Sy - Ey), 2));
		pts = Math.round(dist / CtrlDist);
		if (pts > 12) {
			pts = 12;
			CtrlDist = Math.round(dist / pts);
		}
		if (pts <= 1) {
			pts = 1;
			CtrlDist = Math.round(dist / 2L) - 5;
			CtrlVar = CtrlDist;
		}
		while (!done) {
			done = true;
			if (CtrlDist * pts > Math.round(dist)) {
				done = false;
				pts = pts - 1;
			}
		}
		Controls.add(new Point(Sx, Sy));
		if (Ex - Sx != 0) {
			slope = (Ey - Sy) / (Ex - Sx);
			b = Sy - slope * Sx;
			XInc = (Ex - Sx) / pts;
			for (i = 1; i <= pts; i++) {
				Controls.add(new Point(Math.round(Sx + XInc * i + (new java.util.Random().nextInt(CtrlVar * 2) - CtrlVar)), Math.round(slope * (Sx + XInc * i) + b + (new java.util.Random().nextInt(CtrlVar * 2) - CtrlVar))));
			}
		} else {
			for (i = 1; i <= pts; i++) {
				Controls.add(new Point(new java.util.Random().nextInt(CtrlVar * 2) - CtrlVar, Sy + (Ey - Sy) * i + new java.util.Random().nextInt(CtrlVar * 2) - CtrlVar));
			}
		}
		Controls.add(new Point(Ex, Ey));
		if (dist == 0) {
			dist = 1L;
		}
		ThetaInc = 1.0 / (dist / 5.0);
		MouseHandler.log.info(String.valueOf(1.0 / (dist / 5.0)));

		for (int d = 0; d < Controls.toArray().length; d++) {
			MouseHandler.log.info("X: " + Controls.get(d).x + ", Y:" + Controls.get(d).y);
		}
		final Point[] mp = new Point[Controls.size()];
		return MouseHandler.makeSplinePath(Controls.toArray(mp), ThetaInc);
	}

	public static Point[] makeSplinePath(final Point[] mp, final double ThetaInc) {
		double t = 0.0;
		Point temp;
		final Point last = new Point(99, 99);
		boolean done = false;
		Point[] result;
		final Vector<Point> m = new Vector<Point>();
		MouseHandler.log.info(String.valueOf(ThetaInc));
		while (!done) {
			// log.info((t);
			if (t >= 1) {
				t = 1;
				done = true;
			}
			temp = MouseHandler.getSplinePt(mp, t);
			if ((temp.x != last.x) && (temp.y != last.y)) {
				m.add(temp);
				last.x = temp.x;
				last.y = temp.y;
				MouseHandler.log.info("this happened");
			}
			t = t + ThetaInc;
		}
		result = new Point[m.size()];
		return m.toArray(result);
	}

	/**
	 * Binomial Coefficient.
	 * 
	 * @param n
	 *            The superset element count.
	 * @param k
	 *            The subset size.
	 * @return <code>n</code> choose <code>k</code>.
	 */
	private static double nCk(final int n, final int k) {
		return MouseHandler.fact(n) / (MouseHandler.fact(k) * MouseHandler.fact(n - k));
	}

	private final InputManager inputManager;

	private final java.util.Random random = new java.util.Random();

	MouseHandler(final InputManager inputManager) {
		this.inputManager = inputManager;
	}

	public Point[] generateMousePath(final int Amount, final Point destination) {
		return generateMousePath(Amount, MouseHandler.methods.getMouseLocation(), destination);
	}

	/**
	 * Generates a randomized point array, used as a mouse path. Written by
	 * Sweed_Raver.
	 * 
	 * @param Amount
	 *            The amount of points for the mouse path to contain, including
	 *            the final destination.
	 * @param curPos
	 *            The current position.
	 * @param destination
	 *            The final destination.
	 * @return A point array, used as a mouse path or null if failed (Most
	 *         likely because the amount was negative or 0). If you enter 1 as
	 *         amount, the return would be the destination.
	 */
	public Point[] generateMousePath(final int Amount, final Point curPos, final Point destination) {

		if (Amount <= 0)
			return null;

		try {

			final Point[] path = new Point[Amount];
			for (int i = 0; i < path.length; i++) {
				path[i] = new Point();
				if (i == path.length - 1) {
					path[i].setLocation(destination);
				} else if (i != 0) {
					path[i].setLocation(path[i - 1].x > destination.x ? MouseHandler.methods.random(destination.x, path[i - 1].x) : MouseHandler.methods.random(path[i - 1].x, destination.x), path[i - 1].y > destination.y ? MouseHandler.methods.random(destination.y, path[i - 1].y) : MouseHandler.methods.random(path[i - 1].y, destination.y));
				} else {
					path[i].setLocation(curPos.x > destination.x ? MouseHandler.methods.random(destination.x, curPos.x) : MouseHandler.methods.random(curPos.x, destination.x), curPos.y > destination.y ? MouseHandler.methods.random(destination.y, curPos.y) : MouseHandler.methods.random(curPos.y, destination.y));
				}
			}
			final Vector<Point> unsorted = new Vector<Point>();
			unsorted.addAll(Arrays.asList(path));
			final Vector<Point> sorted = new Vector<Point>();
			for (final Point element : path) {
				if (element == null) {
					continue;
				}
				int b = 0;
				int dist = 0;
				for (int a = 0; a < unsorted.size(); a++) {
					if ((int) Math.hypot(unsorted.get(a).getX() - destination.getX(), unsorted.get(a).getY() - destination.getY()) >= dist) {
						dist = (int) Math.hypot(unsorted.get(a).getX() - destination.getX(), unsorted.get(a).getY() - destination.getY());
						b = a;
					}
				}
				sorted.add(unsorted.get(b));
				unsorted.remove(b);
			}
			final Point[] Path = new Point[sorted.size()];
			for (int i = 0; i < sorted.size(); i++) {
				Path[i] = sorted.get(i);
			}
			return Path;
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public void moveMouse(final int sx, final int sy, final int dx, final int dy, final int randX, final int randY) {
		moveMouse(MouseHandler.DEFAULT_MOUSE_SPEED, sx, sy, dx, dy, randX, randY);
	}

	/**
	 * Moves the mouse from a position to another position with randomness
	 * applied.
	 * 
	 * @param speed
	 *            the speed to move the mouse. Anything under
	 *            {@link #DEFAULT_MOUSE_SPEED} is faster than normal.
	 * @param x1
	 *            from x
	 * @param y1
	 *            from y
	 * @param x2
	 *            to x
	 * @param y2
	 *            to y
	 * @param randX
	 *            randomness in the x direction
	 * @param randY
	 *            randomness in the y direction
	 */
	public void moveMouse(final int speed, final int x1, final int y1, final int x2, final int y2, int randX, int randY) {
		if ((x2 == -1) && (y2 == -1))
			// MouseHandler.log
			// .warning("Non-fatal error. Please post log on forums. ("
			// + x2 + "," + y2 + ")");
			return;
		if (randX <= 0) {
			randX = 1;
		}
		if (randY <= 0) {
			randY = 1;
		}
		try {
			if ((x2 == x1) && (y2 == y1))
				return;
			final Point[] controls = MouseHandler.generateControls(x1, y1, x2 + random.nextInt(randX), y2 + random.nextInt(randY), 50, 120);
			final Point[] spline = MouseHandler.generateSpline(controls);
			final long timeToMove = MouseHandler.fittsLaw(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)), 10);
			final Point[] path = MouseHandler.applyDynamism(spline, (int) timeToMove, MouseHandler.DEFAULT_MOUSE_SPEED);
			for (final Point aPath : path) {
				inputManager.hopMouse(aPath.x, aPath.y);
				try {
					Thread.sleep(Math.max(0, speed - 2 + random.nextInt(4)));
				} catch (final InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		} catch (final Exception e) {
			// MouseHandler.log.info("Error moving mouse: " + e);
			// MouseHandler.log.info("Source: " + x1 + "," + y1);
			// MouseHandler.log.info("Dest:   " + x2 + "," + y2);
			// MouseHandler.log.info("Randx/Randy: " + randX + "/" + randY);
			// e.printStackTrace();
		}
	}
}