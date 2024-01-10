package com.itzg.quidsee;

import java.util.Date;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

public class BidderInfo {
	int bidCount = 0;
	final float enterPrice;
	QuiBid mostRecentBid;
	Map<BidType, AtomicInteger> bidTypeCounts = new EnumMap<BidType, AtomicInteger>(BidType.class);
	
	public BidderInfo(QuiBid firstBid) {
		this.mostRecentBid = firstBid;
		this.enterPrice = firstBid.getAmount();
		updateBid(firstBid);
	}
	
	public void updateBid(QuiBid bid) {
		bidCount++;
		this.mostRecentBid = bid;
		AtomicInteger counter = bidTypeCounts.get(bid.getBidType());
		if (counter == null) {
			counter = new AtomicInteger(1);
			bidTypeCounts.put(bid.getBidType(), counter);
		}
		else {
			counter.incrementAndGet();
		}
	}

	public int getBidCount() {
		return bidCount;
	}

	public float getEnterPrice() {
		return enterPrice;
	}

	public QuiBid getMostRecentBid() {
		return mostRecentBid;
	}
	
	public Date getMostRecentBidTimestamp() {
		return mostRecentBid.getTimestamp();
	}
	
	public String getUser() {
		return mostRecentBid.getUser();
	}
	
	public BidType getPreferredBidType() {
		int max = 0;
		BidType maxType = null;
		for (Entry<BidType, AtomicInteger> entry : bidTypeCounts.entrySet()) {
			final int value = entry.getValue().intValue();
			if (maxType == null) {
				maxType = entry.getKey();
				max = value;
			}
			else if (value > max) {
				max = value;
				maxType = entry.getKey();
			}
		}
		return maxType;
	}
}
