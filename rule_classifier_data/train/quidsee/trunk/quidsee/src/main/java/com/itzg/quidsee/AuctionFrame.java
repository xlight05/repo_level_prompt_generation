package com.itzg.quidsee;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class AuctionFrame extends JFrame {
	private static final String PREF_SHOWING_BID_TYPE = "showing-bidType";
	private static final String PREF_SHOWING_VESTED = "showing-vested";
	private static final Color AUCTION_TITLE_BLUE = new Color(0x2E5981);
	private static final Color AUCTION_GOING_GREEN = new Color(0x4cb301);
	private static final Color AUCTION_ENDED_RED = new Color(0xCF0000);
	private static final long serialVersionUID = 1L;
	private static final int[] INTERVALS = {15,60,120,300};
	private final Auction auction;
	private RecentIntervalsTableModel recentIntervalsTableModel;
	private JLabel lblTitle;
	private JLabel lblProductImg;
	private boolean dirty;
	private Timer refreshTimer;
	private JLabel lblPrice;
	private BiddersPanel biddersPanel;
	
	private final Preferences prefs = Preferences.userNodeForPackage(AuctionFrame.class).node("auctionFrame");
	private JLabel lblSold;
	private JLabel lblCurrentTime;
	
	private DateFormat currentTimeFormatter = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.SHORT, SimpleDateFormat.SHORT);
	private JPopupMenu showPopup;
	
	public AuctionFrame(Auction auction) {
		getContentPane().setBackground(Color.WHITE);
		this.auction = auction;
		auction.addPropertyChangeListener(new PropertyChangeListener() {
			
			public void propertyChange(PropertyChangeEvent evt) {
				auctionPropertyChanged(evt);
			}
		});
		setTitle("Auction");

		GridBagLayout gridBagLayout = new GridBagLayout();
		getContentPane().setLayout(gridBagLayout);
		
		lblTitle = new JLabel("Auction");
		final Font bigFont = new Font("Dialog", Font.BOLD, 16);
		lblTitle.setFont(bigFont);
		lblTitle.setForeground(AUCTION_TITLE_BLUE);
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.weightx = 1.0;
		gbc_lblTitle.insets = new Insets(5, 5, 15, 0);
		gbc_lblTitle.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblTitle.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTitle.gridwidth = 2;
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		getContentPane().add(lblTitle, gbc_lblTitle);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		getContentPane().add(panel, gbc_panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		lblPrice = new JLabel("$0.00");
		lblPrice.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblPrice);
		lblPrice.setFont(bigFont);
		lblPrice.setForeground(AUCTION_GOING_GREEN);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut);
		
		lblSold = new JLabel("SOLD");
		lblSold.setForeground(AUCTION_ENDED_RED);
		lblSold.setFont(bigFont);
		lblSold.setVisible(false);
		panel.add(lblSold);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut_1);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panel.add(horizontalGlue);
		
		lblCurrentTime = new JLabel("");
		lblCurrentTime.setForeground(AUCTION_TITLE_BLUE);
		lblCurrentTime.setVisible(false);
		panel.add(lblCurrentTime);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(5);
		panel.add(horizontalStrut_2);
		
		lblProductImg = new JLabel("");
		lblProductImg.setMinimumSize(new Dimension(125, 100));
		lblProductImg.setPreferredSize(new Dimension(125, 100));
		GridBagConstraints gbc_lblProductImg = new GridBagConstraints();
		gbc_lblProductImg.gridheight = 2;
		gbc_lblProductImg.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblProductImg.insets = new Insets(0, 5, 5, 5);
		gbc_lblProductImg.gridx = 0;
		gbc_lblProductImg.gridy = 1;
		getContentPane().add(lblProductImg, gbc_lblProductImg);
		
		JPanel intervalPanel = new JPanel();
		intervalPanel.setBackground(Color.WHITE);
		intervalPanel.setMinimumSize(new Dimension(280, 100));
		
		JTable intervalTable = new JTable(getRecentIntervalsTableModel());
		intervalTable.setPreferredSize(new Dimension(280, 100));
		TableColumnModel columnModel = intervalTable.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(50);
		columnModel.getColumn(1).setPreferredWidth(50);
		columnModel.getColumn(1).setCellRenderer(new AligningTableCellRenderer(SwingConstants.CENTER));
		preparePriceRateColumn(columnModel.getColumn(2));
		preparePriceRateColumn(columnModel.getColumn(3));
		intervalPanel.setLayout(new BoxLayout(intervalPanel, BoxLayout.Y_AXIS));
		intervalPanel.add(intervalTable.getTableHeader());
		intervalPanel.add(intervalTable);
		intervalTable.setFont(new Font("Dialog", Font.PLAIN, 12));
		intervalTable.setShowVerticalLines(false);
		intervalTable.setTableHeader(new JTableHeader());
		GridBagConstraints gbc_intervalPanel = new GridBagConstraints();
		gbc_intervalPanel.anchor = GridBagConstraints.NORTHWEST;
		gbc_intervalPanel.insets = new Insets(0, 5, 5, 0);
		gbc_intervalPanel.gridx = 1;
		gbc_intervalPanel.gridy = 2;
		getContentPane().add(intervalPanel, gbc_intervalPanel);
		
		GridBagConstraints gbc_bidderPanels = new GridBagConstraints();
		gbc_bidderPanels.gridwidth = 2;
		gbc_bidderPanels.weighty = 1.0;
		gbc_bidderPanels.weightx = 1.0;
		gbc_bidderPanels.anchor = GridBagConstraints.NORTHWEST;
		gbc_bidderPanels.insets = new Insets(0, 5, 5, 0);
		gbc_bidderPanels.fill = GridBagConstraints.BOTH;
		gbc_bidderPanels.gridx = 0;
		gbc_bidderPanels.gridy = 3;
		getContentPane().add(getBiddersPanel(), gbc_bidderPanels);
		
		JPanel actionsPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) actionsPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		GridBagConstraints gbc_actionsPanel = new GridBagConstraints();
		gbc_actionsPanel.weightx = 1.0;
		gbc_actionsPanel.gridwidth = 2;
		gbc_actionsPanel.anchor = GridBagConstraints.NORTHWEST;
		gbc_actionsPanel.insets = new Insets(10, 0, 0, 0);
		gbc_actionsPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_actionsPanel.gridx = 0;
		gbc_actionsPanel.gridy = 4;
		getContentPane().add(actionsPanel, gbc_actionsPanel);
		
		JButton btnExport = new JButton("Export Trace");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				export();
			}
		});
		
		final JButton btnShow = new JButton("Show...");
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPopup.show(btnShow, 0, btnShow.getHeight());
			}
		});
		actionsPanel.add(btnShow);
		actionsPanel.add(btnExport);
		
		updateAuctionTitle();
		
		refreshTimer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					recompute();
				} catch (Error err) {
					err.printStackTrace();
				}
			}
		});
		refreshTimer.start();
		
		loadSize();
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				saveSize();
			}
		});
		
		initShowPopup();
	}

	private void initShowPopup() {
		showPopup = new JPopupMenu();
		
		Action showVestedAction = new AbstractAction("Vested") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Boolean value = (Boolean)getValue(SELECTED_KEY);
				biddersPanel.showVested(value);
				prefs.putBoolean(PREF_SHOWING_VESTED, value);
			}
		};
		Boolean showingVested = new Boolean(prefs.getBoolean(PREF_SHOWING_VESTED, false));
		showVestedAction.putValue(Action.SELECTED_KEY, showingVested);
		biddersPanel.showVested(showingVested);
		showPopup.add(new JCheckBoxMenuItem(showVestedAction));
		
		Action showBidTypeAction = new AbstractAction("Preferred bid type") {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Boolean value = (Boolean)getValue(SELECTED_KEY);
				biddersPanel.showBidType(value);
				prefs.putBoolean(PREF_SHOWING_BID_TYPE, value);
			}
		};
		Boolean showingBidType = new Boolean(prefs.getBoolean(PREF_SHOWING_BID_TYPE, true));
		showBidTypeAction.putValue(Action.SELECTED_KEY, showingBidType);
		biddersPanel.showBidType(showingBidType);
		showPopup.add(new JCheckBoxMenuItem(showBidTypeAction));
	}

	private void loadSize() {
		setSize(prefs.getInt("width", 600),
				prefs.getInt("height", 450));
	}

	protected void export() {
		try {
			File outFile = File.createTempFile("Qwatch-"+auction.getId()+"-", ".csv", new File(System.getProperty("user.home")));
			if (auction.export(outFile)) {
				JOptionPane.showMessageDialog(this, "Exported to "+outFile);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void saveSize() {
		prefs.putInt("width", getWidth());
		prefs.putInt("height", getHeight());
	}

	private void preparePriceRateColumn(TableColumn column) {
		column.setPreferredWidth(80);
		column.setCellRenderer(new PriceRateCellRenderer());
	}

	protected void recompute() {
		dirty = false;
		final Date nowAsDate = new Date();
		lblCurrentTime.setText(currentTimeFormatter.format(nowAsDate));
		final long now = nowAsDate.getTime();
		final int[] offsets = recentIntervalsTableModel.getIntervals();
		List<QuiBid> bids = new ArrayList<QuiBid>(auction.getBids());
		Collections.sort(bids, new Comparator<QuiBid>() {
			public int compare(QuiBid o1, QuiBid o2) {
				if (o1.getAmount() == o2.getAmount()) return 0;
				else if (o1.getAmount() > o2.getAmount()) return -1;
				else return 1;
			}
		});
		
		lblPrice.setText(NumberFormat.getCurrencyInstance().format(bids.get(0).getAmount()));
		
		if (auction.getBids().size() < offsets.length) {
			return;
		}
		
		final long[] thresholds = new long[offsets.length];
		final int[] thresholdBidPos = new int[offsets.length];
		for (int i = 0; i < offsets.length; ++i) {
			thresholds[i] = now - offsets[i] * 1000;
		}
		@SuppressWarnings("unchecked")
		final Set<String>[] bidders = new Set[thresholds.length];
		for (int i = 0; i < bidders.length; ++i) {
			bidders[i] = new HashSet<String>();
		}
		
		float[] prices = new float[thresholds.length];
		for (int i = 0; i < prices.length; ++i) {
			prices[i] = bids.get(0).getAmount();
		}

		int thPos = 0;
		for (int i = 0; i < bids.size(); ++i) {
			QuiBid oldBid = bids.get(i);
			if (thPos < thresholds.length) {
				if (oldBid.getTimestamp().getTime() < thresholds[thPos]) {
					prices[thPos] = oldBid.getAmount();
					++thPos;
					if (thPos >= thresholds.length) {
						break;
					}
				}
				else {
					thresholdBidPos[thPos] = i;
				}
			}
			for (int bidderSlot = thPos; bidderSlot < bidders.length; ++bidderSlot) {
				bidders[bidderSlot].add(oldBid.getUser());
			}
		}

		final float currentPrice = bids.get(0).getAmount();
		for (int i = 0; i < thresholds.length; ++i) {
			recentIntervalsTableModel.setBidders(i, bidders[i].size());
			recentIntervalsTableModel.setPriceRate(i, (currentPrice-prices[i])/(offsets[i]/60f));
		}
		
		Collection<BidderInfo> bidderStats = auction.getBidders().values();
		biddersPanel.updateBidderStats(bidderStats);
		
		repaint();
	}

	protected void auctionPropertyChanged(final PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(Auction.PROP_BID_COUNT)) {
			if (!dirty) {
				dirty = true;
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						recompute();
					}
				});
			}
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if (evt.getPropertyName().equals(Auction.PROP_TITLE)) {
						updateAuctionTitle();
					} else if (evt.getPropertyName().equals(
							Auction.PROP_PRODUCT_IMG)) {
						try {
							Image image;
							image = ImageIO.read(new URL(auction
									.getProductImageUrl()));
							image = image.getScaledInstance(
									lblProductImg.getWidth(),
									lblProductImg.getHeight(), Image.SCALE_FAST);
							lblProductImg.setIcon(new ImageIcon(image));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else if (evt.getPropertyName().equals(Auction.PROP_SOLD)) {
						changeToSold();
					}
				}
			});
		}
	}

	protected void changeToSold() {
		lblPrice.setForeground(AUCTION_ENDED_RED);
		lblSold.setVisible(true);
		lblCurrentTime.setVisible(true);
		refreshTimer.stop();
	}

	private void updateAuctionTitle() {
		String title = "Auction #"+auction.getId();
		
		if (auction.getTitle() != null) {
			title += ": "+auction.getTitle();
		}
		
		setTitle(title);
		lblTitle.setText(title);
	}

	private TableModel getRecentIntervalsTableModel() {
		if (recentIntervalsTableModel == null) {
			recentIntervalsTableModel = new RecentIntervalsTableModel(INTERVALS);
		}

		return recentIntervalsTableModel;
	}
	
	public BiddersPanel getBiddersPanel() {
		if (biddersPanel == null) {
			biddersPanel = new BiddersPanel();
		}

		return biddersPanel;
	}
}
