package com.itzg.quidsee;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class PriceRateCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;

	public PriceRateCellRenderer() {
		getFont().deriveFont(Font.PLAIN);
	}
	
	@Override
	protected void setValue(Object value) {
		if (value instanceof Float) {
			Float floatValue = (Float) value;
			setText(String.format("+$%05.3f/min", floatValue));
		} else {
			super.setValue(value);
		}
	}
}
