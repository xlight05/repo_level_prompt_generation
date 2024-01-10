package com.itzg.quidsee;

public interface AuctionTrackerListener {

	void newAuctionAdded(Auction auction, AuctionFrame frame);

	void discoveredAuctionInfo(Auction auction);

}
