package com.itzg.quidsee;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.NumberFormat;
import java.util.Collection;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.itzg.quidsee.BiddersTableModel.Sort;

public class BiddersTablePanel extends JPanel {
	private static final int PREF_HEIGHT = 180;
	private static final int PREF_WIDTH_BID_COUNT = 10;
	private static final int PREF_WIDTH_BID_TYPE = 10;
	private static final int PREF_WIDTH_ELAPSED = 30;
	private static final int PREF_WIDTH_VESTED_WIDTH = 25;
	
	

	private static final long serialVersionUID = 1L;

	private static final int BASE_WIDTH = 200+PREF_WIDTH_BID_COUNT+PREF_WIDTH_ELAPSED;
	
	private JTable table;
	private BiddersTableModel tableModel;
	private final Sort sort;

	private OptionalColumn bidTypeColumn;
	private OptionalColumn vestedColumn;

	private TableColumnModel columnModel;

	private JScrollPane scroller;

	public BiddersTablePanel(BiddersTableModel.Sort sort) {
		this.sort = sort;
		initComponent();
	}

	protected void initComponent() {
		setBackground(null);
		setLayout(new BorderLayout(0, 5));
		
		JLabel lblNewLabel_1 = new JLabel(getLabelForSort());
		lblNewLabel_1.setFont(lblNewLabel_1.getFont().deriveFont(lblNewLabel_1.getFont().getStyle() | Font.BOLD));
		add(lblNewLabel_1, BorderLayout.NORTH);
		
		table = new JTable(getTableModel());
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		columnModel = table.getColumnModel();
		prepareBidCountColumn(columnModel.getColumn(BiddersTableModel.COL_BID_COUNT));
		prepareElapsedColumn(columnModel.getColumn(BiddersTableModel.COL_ELAPSED));
		
		bidTypeColumn = new OptionalColumn(columnModel.getColumn(BiddersTableModel.COL_BID_TYPE), PREF_WIDTH_BID_TYPE);
		vestedColumn = new OptionalColumn(columnModel.getColumn(BiddersTableModel.COL_VESTED), PREF_WIDTH_VESTED_WIDTH);

		prepareBidTypeColumn(bidTypeColumn.getColumn());
		columnModel.removeColumn(bidTypeColumn.getColumn());
		
		prepareVestedColumn(vestedColumn.getColumn());
		columnModel.removeColumn(vestedColumn.getColumn());
		
		scroller = new JScrollPane(table);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.getViewport().setBackground(Color.WHITE);
		
		add(scroller, BorderLayout.CENTER);
		
		updateMinPrefSize();
	}

	private void updateMinPrefSize() {
		int width = BASE_WIDTH+bidTypeColumn.getActiveWidth()+vestedColumn.getActiveWidth();
		Dimension d = new Dimension(width, PREF_HEIGHT);
		setMinimumSize(d);
		setPreferredSize(d);
	}

	private String getLabelForSort() {
		return sort == Sort.ACTIVE ? "Top Bidders" : "Recent Bidders";
	}

	private void prepareVestedColumn(TableColumn column) {
		column.setPreferredWidth(PREF_WIDTH_VESTED_WIDTH);
		column.setCellRenderer(new AligningTableCellRenderer(SwingConstants.RIGHT) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void setValue(Object value) {
				setText(NumberFormat.getCurrencyInstance().format(value));
			}
		});
	}

	public void updateBidderStats(Collection<BidderInfo> bidderStats) {
		getTableModel().updateBidders(bidderStats);
	}

	private BiddersTableModel getTableModel() {
		if (tableModel == null) {
			tableModel = new BiddersTableModel(sort);
		}

		return tableModel;
	}

	private void prepareBidCountColumn(TableColumn column) {
		column.setPreferredWidth(PREF_WIDTH_BID_COUNT);
		column.setCellRenderer(new AligningTableCellRenderer(SwingConstants.CENTER));
	}

	private void prepareElapsedColumn(TableColumn column) {
		column.setPreferredWidth(PREF_WIDTH_ELAPSED);
		column.setCellRenderer(new AligningTableCellRenderer(
				SwingConstants.RIGHT) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void setValue(Object value) {
				if (value instanceof Number) {
					long seconds = ((Number) value).longValue();
					if (seconds < 60) {
						setText(Long.toString(seconds) + " sec");
					} else {
						// we desire the truncation in division here
						long minutes = seconds / 60;
						setText(Long.toString(minutes) + " min");
					}
				} else {
					super.setValue(value);
				}
			}

		});
	}

	private void prepareBidTypeColumn(TableColumn column) {
		column.setPreferredWidth(PREF_WIDTH_BID_TYPE);
		column.setCellRenderer(new BidTypeCellRenderer());
		//TODO tooltip
	}
	
	public void showBidType(boolean value) {
		showHideColumn(value, bidTypeColumn);
	}

	public void showVested(boolean value) {
		showHideColumn(value, vestedColumn);
	}

	private void showHideColumn(boolean value, OptionalColumn column) {
		if (value != column.isVisible()) {
			if (value) {
				columnModel.addColumn(column.getColumn());
			}
			else {
				columnModel.removeColumn(column.getColumn());
			}
			revalidate(); // the whole panel
			column.setVisible(value);
			updateMinPrefSize();
		}
	}

}
