package com.itzg.quidsee;

public interface AuctionListener {
	void auctionAdded(Auction newAuction);
	void bidAdded(Auction auction, QuiBid bid);
}
