package com.itzg.quidsee;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.littleshoot.proxy.DefaultHttpFilter;
import org.littleshoot.proxy.DefaultHttpProxyServer;
import org.littleshoot.proxy.HttpFilter;
import org.littleshoot.proxy.HttpRequestBasePathMatcher;

public class QuidseeMain implements AuctionListener {
	AuctionTracker tracker = new AuctionTracker();
	private AppFrame appFrame;
	private PacServer pacServer;

	public QuidseeMain(int port) {
		appFrame = new AppFrame(tracker, port);
		pacServer = new PacServer();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final int port = Integer.parseInt(System.getProperty("port", "8181"));
		QuidseeMain me = new QuidseeMain(port);
		
		final DefaultHttpProxyServer server = new DefaultHttpProxyServer(port);
		
		HttpFilter quiBidsUpdateFilter = new DefaultHttpFilter(new QuiBidUpdateResponseFilter(me.getTracker()), 
				new HttpRequestBasePathMatcher("/ajax/u\\.php"));
		server.addFilter("www.quibids.com", quiBidsUpdateFilter);
		
		HttpFilter quiBidsAuctionPageFilter = new DefaultHttpFilter(
				new QuiBidsAuctionPageResponseFilter(me.getTracker()), 
				new HttpRequestBasePathMatcher("/auctions/\\d+-"));
		server.addFilter("www.quibids.com", quiBidsAuctionPageFilter);
		
		server.start(true, false);

		me.start();
	}
	
	public void start() {
		Logger.getLogger("org.restlet").setLevel(Level.WARNING);
		// TODO figure out how to override this from command line
		Logger.getLogger("com.itzg").setLevel(Level.WARNING);
		appFrame.setVisible(true);
		pacServer.start();
		pacServer.installPac();
	}

	public void auctionAdded(Auction newAuction) {
		
	}

	public void bidAdded(Auction auction, QuiBid bid) {
	}
	
	public AuctionTracker getTracker() {
		return tracker;
	}
}
