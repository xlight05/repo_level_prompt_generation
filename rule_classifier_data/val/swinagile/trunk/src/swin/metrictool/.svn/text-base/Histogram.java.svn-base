package swin.metrictool;

/** This class provides the bare essentials for a histogram.* */
public class Histogram
{
	protected String fTitle = "Histogram";

	protected String fXLabel = "Data";

	protected int[] fBins;

	protected int fNumBins;

	protected int fUnderflows;

	protected int fOverflows;

	protected double fLo;

	protected double fHi;

	protected double fRange;

	/**
	 * The constructor will create an array of a given number of bins. The range of the
	 * histogram given by the upper and lower limit values.
	 */
	public Histogram(int numBins, double lo, double hi)
	{
		// Check for bad range values.
		// Could throw an exception but will just
		// use default values;
		if (hi < lo)
		{
			lo = 0.0;
			hi = 1.0;
		}
		if (numBins <= 0) numBins = 1;
		this.fNumBins = numBins;
		this.fBins = new int[this.fNumBins];
		this.fLo = lo;
		this.fHi = hi;
		this.fRange = this.fHi - this.fLo;
	} // ctor

	// This constructor includes the title and horizontal
	// axis label.
	public Histogram(String title, String xLabel, int fNumBins, double lo, double hi)
	{
		this(fNumBins, lo, hi);// Invoke overloaded constructor
		this.fTitle = title;
		this.fXLabel = xLabel;
	} // ctor

	// --- Histogram description --------------------------------
	/** Get to title string. * */
	public String getTitle()
	{
		return this.fTitle;
	}

	/** Set the title. * */
	public void setTitle(String title)
	{
		this.fTitle = title;
	}

	/** Get to the horizontal axis label. * */
	public String getXLabel()
	{
		return this.fXLabel;
	}

	/** Set the horizontal axis label. * */
	public void setXLabel(String xLabel)
	{
		this.fXLabel = xLabel;
	}

	// --- Bin info access --------------------------------------
	/** Get the low end of the range. * */
	public double getLo()
	{
		return this.fLo;
	}

	/** Get the high end of the range.* */
	public double getHi()
	{
		return this.fHi;
	}

	/** Get the number of entries in the largest bin. * */
	public int getMax()
	{
		int max = 0;
		for (int i = 0; i < this.fNumBins; i++)
			if (max < this.fBins[i]) max = this.fBins[i];
		return max;
	}

	/**
	 * This method returns a reference to the fBins. Note that this means the values of the
	 * histogram could be altered by the caller object.
	 */
	public int[] getBins()
	{
		return this.fBins;
	}

	/** Get the number of entries in the smallest bin.* */
	public int getMin()
	{
		int min = this.getMax();
		for (int i = 0; i < this.fNumBins; i++)
			if (min > this.fBins[i]) min = this.fBins[i];
		return min;
	}

	/**
	 * Get the total number of entries not counting overflows and underflows.
	 */
	public int getTotal()
	{
		int total = 0;
		for (int i = 0; i < this.fNumBins; i++)
			total += this.fBins[i];
		return total;
	}

	/**
	 * Add an entry to a bin.
	 * @param x double value added if it is in the range: lo <= x < hi
	 */
	public void add(double x)
	{
		if (x >= this.fHi)
			this.fOverflows++;
		else if (x < this.fLo)
			this.fUnderflows++;
		else
		{
			double val = x - this.fLo;

			// Casting to int will round off to lower
			// integer value.
			int bin = (int) (this.fNumBins * val / this.fRange);

			// Increment the corresponding bin.
			this.fBins[bin]++;
		}
	}

	/** Clear the histogram bins and the over and under flows.* */
	public void clear()
	{
		for (int i = 0; i < this.fNumBins; i++)
		{
			this.fBins[i] = 0;
			this.fOverflows = 0;
			this.fUnderflows = 0;
		}
	}

	/**
	 * Provide access to the value in the bin element specified by bin_num.<br>
	 * Return the underflows if bin value negative, Return the overflows if bin value more than
	 * the number of bins.
	 */
	public int getValue(int bin_num)
	{
		if (bin_num < 0)
			return this.fUnderflows;
		else if (bin_num >= this.fNumBins)
			return this.fOverflows;
		else
			return this.fBins[bin_num];
	}

	/**
	 * Get the average and standard deviation of the distribution of entries.
	 * @return double array
	 */
	public double[] getStats()
	{
		int total = 0;

		double wt_total = 0;
		double wt_total2 = 0;
		double[] stat = new double[2];
		double bin_width = this.fRange / this.fNumBins;

		for (int i = 0; i < this.fNumBins; i++)
		{
			total += this.fBins[i];

			double bin_mid = (i - 0.5) * bin_width + this.fLo;
			wt_total += this.fBins[i] * bin_mid;
			wt_total2 += this.fBins[i] * bin_mid * bin_mid;
		}

		if (total > 0)
		{
			stat[0] = wt_total / total;
			double av2 = wt_total2 / total;
			stat[1] = Math.sqrt(av2 - stat[0] * stat[0]);
		}
		else
		{
			stat[0] = 0.0;
			stat[1] = -1.0;
		}

		return stat;
	}// getStats()

	/**
	 * Create the histogram from a user derived array along with the under and overflow values.
	 * The low and high range values that the histogram corresponds to must be in passed as
	 * well.<br>
	 * @param userBins array of int values.
	 * @param under number of underflows.
	 * @param over number of overflows.
	 * @param lo value of the lower range limit.
	 * @param hi value of the upper range limit.
	 */
	public void pack(int[] user_bins, int under, int over, double lo, double hi)
	{
		this.fNumBins = user_bins.length;
		this.fBins = new int[this.fNumBins];
		for (int i = 0; i < this.fNumBins; i++)
			this.fBins[i] = user_bins[i];

		this.fLo = lo;
		this.fHi = hi;
		this.fRange = this.fHi - this.fLo;
		this.fUnderflows = under;
		this.fOverflows = over;
	}// pack()

}// class Histogram
