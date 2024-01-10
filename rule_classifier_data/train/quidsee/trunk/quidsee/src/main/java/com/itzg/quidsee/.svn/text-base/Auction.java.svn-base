package com.itzg.quidsee;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Auction {
	public static final String PROP_TITLE = "title";
	public static final String PROP_PRODUCT_IMG = "productImageUrl";
	public static final String PROP_BID_COUNT = "bidCount";
	public static final String PROP_USER_COUNT = "userCount";
	public static final String PROP_SOLD = "sold";

	private int id;
	private String title;
	private String productImageUrl;
	
	private List<QuiBid> bids = new ArrayList<QuiBid>();
	private Map<String/*user*/,BidderInfo> bidders = new HashMap<String, BidderInfo>();
	
	private PropertyChangeSupport propertyChangeSupport = 
		new PropertyChangeSupport(this);
	private boolean sold;

	public Auction(int auctionId) {
		id = auctionId;
	}
	
	@Override
	public int hashCode() {
		return id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
		if (obj instanceof Auction) {
			return this.id == ((Auction)obj).id;
		}
		else return false;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public synchronized void addBid(QuiBid bid) {
		try {
			// Check for duplicate bid, which will happen if the same auction is
			// open twice.
			for (int i = bids.size()-1; i >= 0; --i) {
				if (bid.getBidId() == bids.get(i).getBidId()) {
					return;
				}
			}
			bids.add(bid);

			BidderInfo bidderInfo = bidders.get(bid.getUser());
			if (bidderInfo == null) {
				bidderInfo = new BidderInfo(bid);
				bidders.put(bid.getUser(), bidderInfo);
				propertyChangeSupport.firePropertyChange(PROP_USER_COUNT, bidders.size()-1, bidders.size());
			}
			else {
				bidderInfo.updateBid(bid);
			}

			propertyChangeSupport.firePropertyChange(PROP_BID_COUNT, bids.size()-1, bids.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<QuiBid> getBids() {
		return bids;
	}
	
	public int getBidCount() {
		return bids.size();
	}

	public int getId() {
		return id;
	}

	public void setTitle(String title) {
		final String oldTitle = this.title;
		this.title = title;
		propertyChangeSupport.firePropertyChange(PROP_TITLE, oldTitle, title);
	}

	public void setProductImageUrl(String productImageUrl) {
		final String oldValue = this.productImageUrl;
		this.productImageUrl = productImageUrl;
		propertyChangeSupport.firePropertyChange(PROP_PRODUCT_IMG, oldValue, productImageUrl);
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getProductImageUrl() {
		return productImageUrl;
	}
	
	public Map<String, BidderInfo> getBidders() {
		return bidders;
	}

	public boolean export(File outFile) {
		try {
			PrintWriter writer = new PrintWriter(outFile);
			try {
				/*
				 * amount
				 * timestamp
				 * user
				 * clockAtBid
				 */
				writer.println("Amount,Timestamp,User,BidType,ClockAtBid");
				List<QuiBid> sortedBids = new ArrayList<QuiBid>(bids);
				Collections.sort(sortedBids, new Comparator<QuiBid>() {
					public int compare(QuiBid o1, QuiBid o2) {
						float p1 = o1.getAmount();
						float p2 = o2.getAmount();
						if (p1 == p2) return 0;
						else if (p1 < p2) return -1;
						else return 1;
					}
				});
				
				long prevTimestamp = 0;
				Float prevSlew = null;
				
				for (QuiBid bid : sortedBids) {
					writer.print(bid.getAmount());
					writer.print(",");
					writer.print(bid.getTimestamp());
					writer.print(",");
					writer.print(bid.getUser());
					writer.print(",");
					writer.print(bid.getBidType());
					writer.print(",");
					int clockAtBid = bid.getClockSlew() != null ? bid.getClockSlew().intValue() : 0;
					if (prevSlew != null) {
						long delta = ((long)(prevSlew*1000)) - (bid.getTimestamp().getTime() - prevTimestamp);
						clockAtBid = (int) (delta/1000);
					}
					writer.print(clockAtBid);
					writer.println();
					
					prevTimestamp = bid.getTimestamp().getTime();
					prevSlew = bid.getClockSlew();
				}
				return true;
			}
			finally {
				writer.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void sold() {
		sold = true;
		propertyChangeSupport.firePropertyChange(PROP_SOLD, false, true);
	}
	
	public boolean isSold() {
		return sold;
	}
}
