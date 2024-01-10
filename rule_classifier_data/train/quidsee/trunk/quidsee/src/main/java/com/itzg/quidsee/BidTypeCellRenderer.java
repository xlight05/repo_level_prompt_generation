package com.itzg.quidsee;

import java.awt.Color;

import javax.swing.SwingConstants;

public class BidTypeCellRenderer extends AligningTableCellRenderer {
	private static final long serialVersionUID = 1L;

	private Color normalColor = Color.BLACK;
	private Color atypicalColor = Color.ORANGE;
	
	public BidTypeCellRenderer() {
		super(SwingConstants.CENTER);
	}

	@Override
	protected void setValue(Object value) {
		if (value instanceof BidTypeValue) {
			BidTypeValue btv = (BidTypeValue) value;
			setText(btv.getPreferred().getShortForm());
			setForeground(btv.isTypical() ? normalColor : atypicalColor);
		}
		else {
			super.setValue(value);
		}
	}

	public Color getNormalColor() {
		return normalColor;
	}

	public void setNormalColor(Color normalColor) {
		this.normalColor = normalColor;
	}

	public Color getAtypicalColor() {
		return atypicalColor;
	}

	public void setAtypicalColor(Color atypicalColor) {
		this.atypicalColor = atypicalColor;
	}
}
