package com.shautvast.realtimegc;

import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RefineryUtilities;

enum Type {
	SUN("SUN"), IBM("IBM");

	private String name;

	Type(String name) {
		this.name = name;
	}

	String getName() {
		return name;
	}
}

class ReaderGraph {
	TimeSeries freeMemSeries, totalMemSeries, usedMemSeries;
	GcLogReader logReader;
	JFreeChart chart;
	ChartPanel chartPanel;

	public ReaderGraph(GcLogReader logReader) {
		this.logReader = logReader;
		freeMemSeries = new TimeSeries("free", Second.class);
		usedMemSeries = new TimeSeries("used", Second.class);
		totalMemSeries = new TimeSeries("total", Second.class);

		final TimeSeriesCollection dataset = new TimeSeriesCollection(
				freeMemSeries);
		dataset.addSeries(usedMemSeries);
		dataset.addSeries(totalMemSeries);

		chart = createChart(dataset);
		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
	}

	private JFreeChart createChart(final XYDataset dataset) {
		final JFreeChart result = ChartFactory.createTimeSeriesChart(
				"java memory", "Time", "bytes", dataset, true, true, false);
		final XYPlot plot = result.getXYPlot();
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		axis.setFixedAutoRange(600000D); // 600 seconds
		axis = plot.getRangeAxis();
		return result;
	}
}

public class Main {
	private JFrame frame;

	private List<ReaderGraph> readerGraphs;
	private String filename;
	private Type type;

	private String title;

	public static void main(String[] args) throws Exception {
		if (args.length == 3) {
			try{
				new Main(args[0], args[1], args[2]).run();
			} catch (Exception e) {
				JFrame frame = new JFrame("error");
				frame.getContentPane().add(new Label(e.getMessage()));
				frame.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						System.exit(-1);
					}
				});
				frame.pack();
				RefineryUtilities.centerFrameOnScreen(frame);
				frame.setVisible(true);
			}
			
		} else {
			System.out.println("usage:");
			System.out.println("rtcg [file] [SUN|IBM] [title]");
		}
	}

	public Main() {
		readerGraphs = new ArrayList<ReaderGraph>();
	}

	public Main(String filename, String type, String title) {
		this();
		this.filename = filename;
		try {
			this.type = Type.valueOf(type);
		} catch (Exception e) {
			throw new RuntimeException("unknown filetype: "+type);
		}
		
		this.title=title;
	}

	private void run() {
		init();

		for (;;) {
			for (ReaderGraph rg : readerGraphs) {
				Measurement m = rg.logReader.read();
				if (m != null) {
					rg.freeMemSeries.addOrUpdate(new Millisecond(m.getTime()),
							m.getFreeMem());
					rg.totalMemSeries.addOrUpdate(new Millisecond(m.getTime()),
							m.getTotalMem());
					rg.usedMemSeries.addOrUpdate(new Millisecond(m.getTime()),
							m.getUsedMem());
				}
			}
		}
	}

	private void init() {
		GcLogReader logReader = null;
		if (type == Type.IBM) {
			logReader = new IbmGcLogReader(filename);
		} else if (type == Type.SUN) {
			logReader = new SimpleSunGcLogReader(filename);
		} else {
			throw new RuntimeException("format not understood " + type);
		}
		readerGraphs.add(new ReaderGraph(logReader));

		frame = new JFrame(title);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});


		final JPanel content = new JPanel(new FlowLayout());
		for (ReaderGraph rg : readerGraphs) {
			content.add(rg.chartPanel);
		}

		frame.setContentPane(content);
		frame.pack();
		RefineryUtilities.centerFrameOnScreen(frame);
		frame.setVisible(true);
	}

}
