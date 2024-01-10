package com.itzg.quidsee;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.table.AbstractTableModel;

public class BiddersTableModel extends AbstractTableModel {

	private final Sort sort;
	private ArrayList<BidderInfo> bidders;
	private static final String[] COLUMN_NAMES = {
		"Bidder", "Bids", "Elapsed", "Pref"
	};
	private static final Class<?>[] COLUMN_TYPES = {
		String.class, Integer.class, Long.class, String.class
	};
	
	private Comparator<BidderInfo> comparator;

	public static enum Sort {
		RECENT,
		ACTIVE
	}
	
	public BiddersTableModel(Sort sort) {
		this.sort = sort;
		switch (sort) {
		case RECENT:
			comparator = new Comparator<BidderInfo>() {
				public int compare(BidderInfo o1, BidderInfo o2) {
					long t1 = o1.getMostRecentBid().getTimestamp().getTime();
					long t2 = o2.getMostRecentBid().getTimestamp().getTime();
					if (t1 == t2) return 0;
					else if (t1 > t2) return -1;
					else return 1;
				}
			};
			break;
		case ACTIVE:
			comparator = new Comparator<BidderInfo>() {
				public int compare(BidderInfo o1, BidderInfo o2) {
					int b1 = o1.getBidCount();
					int b2 = o2.getBidCount();
					if (b1 == b2) return 0;
					else if (b1 > b2) return -1;
					else return 1;
				}
			};
			break;
		}
	}
	
	public void updateBidders(Collection<BidderInfo> bidders) {
		this.bidders = new ArrayList<BidderInfo>(bidders);
		Collections.sort(this.bidders, comparator);
		fireTableDataChanged();
	}
	
	public int getRowCount() {
		return bidders != null ? bidders.size() : 0;
	}

	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}
	
	@Override
	public String getColumnName(int column) {
		return COLUMN_NAMES[column];
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return COLUMN_TYPES[columnIndex];
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		BidderInfo bidderInfo = bidders.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return bidderInfo.getUser();
		case 1:
			return bidderInfo.getBidCount();
		case 2:
			return (long)((System.currentTimeMillis()-bidderInfo.getMostRecentBidTimestamp().getTime())/1000);
		case 3:
			final BidType preferredBidType = bidderInfo.getPreferredBidType();
			return preferredBidType != null ? preferredBidType.getShortForm() : "";
		}
		return null;
	}

}
