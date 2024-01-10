package swin.metrictool;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * @author Ian Fieldhouse
 */
public class ChartMaker
{

	private Vector<ExtractedVersionData> m_versColl;

	private int m_chartXRes = 800;

	private int m_chartYRes = 600;

	public ChartMaker()
	{
	}

	// public void set_SummaryDataCollection(Vector<ExtractedVersionData> coll)
	// {
	// m_versColl = coll;
	// }

	public void create_FanChart(String filename, FanType ft, int start, int finish,
			int interval) throws NullPointerException, ArrayIndexOutOfBoundsException
	{

		if (filename == null) throw new NullPointerException("filename is equal to null.");
		if (ft == null) throw new NullPointerException("ft is equal to null.");
		if (finish < 1) throw new ArrayIndexOutOfBoundsException("finish < 1");
		if (interval < 1) throw new ArrayIndexOutOfBoundsException("interval < 1");
		if (start < 0) throw new ArrayIndexOutOfBoundsException("start < 0");

		// this assumes version data is in sequence order
		XYSeriesCollection dataset = new XYSeriesCollection();
		for (int idx = start; idx <= finish; idx = idx + interval)
		{
			ExtractedVersionData vd = this.m_versColl.get(idx);
			if (ft == FanType.FanOut)
				dataset.addSeries(this.createSeries(vd.getFanOutSummmary(), new String("RSN"
						+ vd.getSequenceNumber()), false));
			else if (ft == FanType.FanOutCumulative)
				dataset.addSeries(this.createSeries(vd.getFanOutSummmary(), new String("RSN"
						+ vd.getSequenceNumber()), true));
			else if (ft == FanType.FanIn)
				dataset.addSeries(this.createSeries(vd.getFanInSummary(), new String("RSN"
						+ vd.getSequenceNumber()), false));
			else if (ft == FanType.FanInCumulative)
				dataset.addSeries(this.createSeries(vd.getFanInSummary(), new String("RSN"
						+ vd.getSequenceNumber()), true));
		}

		JFreeChart chart = ChartFactory.createXYLineChart("Fan Out vs # Classes", // Title
				"Fan Out", // x-axis Label
				"# Classes", // y-axis Label
				dataset, // Dataset
				PlotOrientation.VERTICAL, // Plot Orientation
				true, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
		);

		this.createChartFile(chart, filename);
	}

	private XYSeries createSeries(HashMap<Integer, Integer> data, String name,
			boolean cumulative)
	{

		XYSeries series = new XYSeries(name);
		int total = 0;

		Vector<Integer> theKeys = new Vector<Integer>(data.keySet());
		Collections.sort(theKeys);

		for (Integer key : theKeys)
		{
			if (cumulative)
				total += data.get(key);
			else
				total = data.get(key);

			series.add(key.doubleValue(), total);
		}
		return series;
	}

	public boolean create_GrowthChart(String filename, GrowthType gt, int start, int finish,
			int interval)
	{

		if (filename == null) throw new NullPointerException("filename is equal to null.");
		if (gt == null) throw new NullPointerException("gt is equal to null.");
		if (finish < 1) throw new ArrayIndexOutOfBoundsException("finish < 1");
		if (interval < 1) throw new ArrayIndexOutOfBoundsException("interval < 1");
		if (start < 0) throw new ArrayIndexOutOfBoundsException("start < 0");
		// this assumes version data is in sequence order
		// need some parameter checking here
		ExtractedVersionData vd;
		int val = 0;

		XYSeries series = new XYSeries(gt.toString());
		for (int ver = start; ver <= finish; ver += interval)
		{
			vd = this.m_versColl.get(ver);

			if (gt == GrowthType.Classes)
				val = vd.getTotalClasses();
			else if (gt == GrowthType.Methods)
				val = vd.getTotalPrivateMethodCount() + vd.getTotalPrivateMethodCount();
			else if (gt == GrowthType.Packages)
				val = vd.getTotalPackageCount();
			else if (gt == GrowthType.ByteCodes) val = vd.getTotalByteCode();
			series.add(ver, val);
		}
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);

		JFreeChart chart = ChartFactory.createXYLineChart("Growth of " + gt.toString()
				+ " vs # Release", // Title
				"RSN", // x-axis Label
				"# " + gt.toString(), // y-axis Label
				dataset, // Dataset
				PlotOrientation.VERTICAL, // Plot Orientation
				true, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
		);
		return this.createChartFile(chart, filename);
	}

	private boolean createChartFile(JFreeChart chart, String filename)
	{
		try
		{
			ChartUtilities.saveChartAsJPEG(new File(filename), chart, this.m_chartXRes,
					this.m_chartYRes);
			return true;
		}
		catch (IOException ex)
		{
			return false;
		}
	}

	public int get_chartXResolution()
	{
		return this.m_chartXRes;
	}

	public void set_chartXResolution(int res)
	{
		this.m_chartXRes = res;
	}

	public int get_chartYResolution()
	{
		return this.m_chartYRes;
	}

	public void set_chartYResolution(int res)
	{
		this.m_chartYRes = res;
	}

	public void set_VersionData(Vector<ExtractedVersionData> coll)
	{
		this.m_versColl = coll;
	}

}
