package com.itzg.quidsee;

import java.util.Date;

public class QuiBid {
	Date timestamp;
	int auctionId;
	float amount;
	String user;
	Float clockSlew;
	private final BidType bidType;
	
	public QuiBid(Date timestamp, int auctionId, float amount, String user, Float clockSlew, BidType bidType) {
		this.timestamp = timestamp;
		this.auctionId = auctionId;
		this.amount = amount;
		this.user = user;
		this.clockSlew = clockSlew;
		this.bidType = bidType;
	}
	
	@Override
	public String toString() {
		return "QuiBid:[timestamp="+timestamp+
			",auctionId="+auctionId+
			",amount="+amount+
			",user="+user+
			"]";
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public float getAmount() {
		return amount;
	}

	public String getUser() {
		return user;
	}
	
	public int getAuctionId() {
		return auctionId;
	}
	
	public Float getClockSlew() {
		return clockSlew;
	}
	
	public BidType getBidType() {
		return bidType;
	}
}
