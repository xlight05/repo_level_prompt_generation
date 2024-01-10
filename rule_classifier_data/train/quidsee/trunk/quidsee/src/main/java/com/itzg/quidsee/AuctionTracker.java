package com.itzg.quidsee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

public class AuctionTracker implements QuiBidListener {
	private Map<Integer,Auction> auctions = new HashMap<Integer, Auction>();
	
	private Map<Integer/*auctionId*/,AuctionFrame> frames = 
		new HashMap<Integer, AuctionFrame>();
	
	private List<AuctionTrackerListener> listeners = new ArrayList<AuctionTrackerListener>();
	
	public void addListener(AuctionTrackerListener l) {
		if (!listeners.contains(l)) {
			listeners.add(l);
		}
	}
	
	public synchronized void handleNewBid(final QuiBid bid) {
		Auction auction = auctions.get(bid.getAuctionId());
		if (auction != null) {
			auction.addBid(bid);
			showAuctionWindow(bid.getAuctionId());
		}
	}

	private void showAuctionWindow(final int auctionId) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				AuctionFrame auctionFrame = frames.get(auctionId);
				if (!auctionFrame.isVisible()) {
					auctionFrame.setVisible(true);
				}
			}
		});
	}
	
	@Override
	public void handleAuctionSold(int auctionId) {
		Auction auction = auctions.get(auctionId);
		if (auction != null) {
			auction.sold();
			showAuctionWindow(auctionId);
		}
	}

	private Auction resolveAuction(final int auctionId) {
		Auction auction = auctions.get(auctionId);
		if (auction == null) {
			auction = new Auction(auctionId);
			handleNewAuction(auction);
			auctions.put(auctionId, auction);
		}
		return auction;
	}

	private void handleNewAuction(final Auction auction) {
		AuctionFrame frame = new AuctionFrame(auction);
		frames.put(auction.getId(), frame);
		frame.setVisible(true);
		
		for (AuctionTrackerListener l : listeners) {
			l.newAuctionAdded(auction, frame);
		}
	}

	public void handleAuctionInfo(int auctionId, String title,
			String productImageUrl) {
		Auction auction = resolveAuction(auctionId);
		auction.setTitle(title);
		auction.setProductImageUrl(productImageUrl);
		
		for (AuctionTrackerListener l : listeners) {
			l.discoveredAuctionInfo(auction);
		}
	}

}
