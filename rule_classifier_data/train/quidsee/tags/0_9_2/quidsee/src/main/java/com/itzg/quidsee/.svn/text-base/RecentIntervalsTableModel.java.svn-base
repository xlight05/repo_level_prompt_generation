package com.itzg.quidsee;

import javax.swing.table.AbstractTableModel;

public class RecentIntervalsTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private static final String[] COLUMN_NAMES = {
		"Interval", "Bidders", "Price Rate", "Peak"
	};
	private static final Class<?>[] COLUMN_CLASSES = {
		String.class, Integer.class, Float.class, Float.class
	};
	private final int[] intervals;
	private int[] bidders;
	private float[] priceRate;
	private float[] peakPriceRate;

	public RecentIntervalsTableModel(int[] intervals/*in sec*/) {
		this.intervals = intervals/*in sec*/;
		this.bidders = new int[intervals.length];
		this.priceRate = new float[intervals.length];
		this.peakPriceRate = new float[intervals.length];
	}
	
	public int[] getIntervals() {
		return intervals;
	}
	
	public int getRowCount() {
		return intervals.length;
	}

	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
			case 0:
				int interval = intervals[rowIndex];
				return Integer.toString(interval) + " sec";
				
			case 1:
				return bidders[rowIndex];
				
			case 2:
				return priceRate[rowIndex];
				
			case 3:
				return peakPriceRate[rowIndex];
		}
		return null;
	}
	
	public void setBidders(int intervalPos, int value) {
		bidders[intervalPos] = value;
		fireTableRowsUpdated(intervalPos, intervalPos);
	}
	
	public void setPriceRate(int intervalPos, float value) {
		priceRate[intervalPos] = value;
		if (value > peakPriceRate[intervalPos]) {
			peakPriceRate[intervalPos] = value;
		}
		fireTableRowsUpdated(intervalPos, intervalPos);
	}
	
	@Override
	public String getColumnName(int column) {
		return COLUMN_NAMES[column];
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return COLUMN_CLASSES[columnIndex];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
}
