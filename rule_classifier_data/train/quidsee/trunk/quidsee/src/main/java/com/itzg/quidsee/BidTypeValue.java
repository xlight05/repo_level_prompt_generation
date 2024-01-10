package com.itzg.quidsee;

public class BidTypeValue {
	private BidType preferred;
	private BidType mostRecent;
	public BidTypeValue(BidType preferred, BidType mostRecent) {
		super();
		this.preferred = preferred;
		this.mostRecent = mostRecent;
	}
	public BidType getPreferred() {
		return preferred;
	}
	public BidType getMostRecent() {
		return mostRecent;
	}
	public boolean isTypical() {
		return mostRecent == preferred;
	}
}
