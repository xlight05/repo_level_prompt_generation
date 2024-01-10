package com.itzg.quidsee;

public interface QuiBidListener {
	void handleNewBid(QuiBid bid);

	void handleAuctionSold(int auctionId);
}
