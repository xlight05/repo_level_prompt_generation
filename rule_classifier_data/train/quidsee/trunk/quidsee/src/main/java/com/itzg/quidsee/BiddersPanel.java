package com.itzg.quidsee;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collection;

import javax.swing.JPanel;
import java.awt.Component;
import javax.swing.Box;

public class BiddersPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private BiddersTablePanel topBiddersPanel;

	private BiddersTablePanel recentBiddersPanel;

	
	public BiddersPanel() {
		initComponent();
	}
	
	protected void initComponent() {
		setBackground(Color.WHITE);
		GridBagLayout gbl_bidderPanels = new GridBagLayout();
		setLayout(gbl_bidderPanels);

		GridBagConstraints gbc_recentPnl = new GridBagConstraints();
		gbc_recentPnl.weighty = 1.0;
		gbc_recentPnl.fill = GridBagConstraints.VERTICAL;
		gbc_recentPnl.anchor = GridBagConstraints.NORTHWEST;
		gbc_recentPnl.insets = new Insets(0, 0, 5, 5);
		gbc_recentPnl.gridx = 0;
		gbc_recentPnl.gridy = 0;
		add(getRecentBiddersPanel(), gbc_recentPnl);
		
		GridBagConstraints gbc_topBiddersPnl = new GridBagConstraints();
		gbc_topBiddersPnl.insets = new Insets(0, 0, 5, 0);
		gbc_topBiddersPnl.weighty = 1.0;
		gbc_topBiddersPnl.fill = GridBagConstraints.VERTICAL;
		gbc_topBiddersPnl.anchor = GridBagConstraints.NORTHWEST;
		gbc_topBiddersPnl.gridx = 1;
		gbc_topBiddersPnl.gridy = 0;
		add(getTopBiddersPanel(), gbc_topBiddersPnl);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		GridBagConstraints gbc_horizontalGlue = new GridBagConstraints();
		gbc_horizontalGlue.weightx = 1.0;
		gbc_horizontalGlue.gridx = 2;
		gbc_horizontalGlue.gridy = 0;
		add(horizontalGlue, gbc_horizontalGlue);
	}

	private BiddersTablePanel getRecentBiddersPanel() {
		if (recentBiddersPanel == null) {
			recentBiddersPanel = new BiddersTablePanel(BiddersTableModel.Sort.RECENT);
		}

		return recentBiddersPanel;
	}

	private BiddersTablePanel getTopBiddersPanel() {
		if (topBiddersPanel == null) {
			topBiddersPanel = new BiddersTablePanel(BiddersTableModel.Sort.ACTIVE);
		}

		return topBiddersPanel;
	}

	public void updateBidderStats(Collection<BidderInfo> bidderStats) {
		getRecentBiddersPanel().updateBidderStats(bidderStats);
		getTopBiddersPanel().updateBidderStats(bidderStats);
	}

	public void showVested(boolean value) {
		getRecentBiddersPanel().showVested(value);
		getTopBiddersPanel().showVested(value);
		revalidate();
	}
	
	public void showBidType(boolean value) {
		getRecentBiddersPanel().showBidType(value);
		getTopBiddersPanel().showBidType(value);
		revalidate();
	}
}
