package com.itzg.quidsee;

import javax.swing.table.TableColumn;

public class OptionalColumn {
	private TableColumn column;
	private int width;
	private boolean visible;
	public OptionalColumn(TableColumn column, int width) {
		super();
		this.column = column;
		this.width = width;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public TableColumn getColumn() {
		return column;
	}
	public int getWidth() {
		return width;
	}
	public int getActiveWidth() {
		return visible ? width : 0;
	}
}
