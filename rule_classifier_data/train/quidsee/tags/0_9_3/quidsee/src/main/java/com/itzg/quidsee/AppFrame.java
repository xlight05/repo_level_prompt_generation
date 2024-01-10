package com.itzg.quidsee;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;

public class AppFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private Map<Auction, Info> auctionInfo = new HashMap<Auction, AppFrame.Info>();
	private JPanel auctionsPanel;

	private JPopupMenu removalPopup;
	private Info selectedInfo;

	public AppFrame(AuctionTracker tracker, int port) {
		setTitle("Quidsee");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.WHITE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[]{0.0, 0.0};
		gridBagLayout.columnWeights = new double[]{1.0};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("Keeping tallies on...");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 18));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.weightx = 1.0;
		gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		JPanel fillerPanel = new JPanel();
		fillerPanel.setBackground(Color.WHITE);
		GridBagConstraints gbc_fillerPanel = new GridBagConstraints();
		gbc_fillerPanel.insets = new Insets(10, 0, 0, 0);
		gbc_fillerPanel.weightx = 1.0;
		gbc_fillerPanel.weighty = 1.0;
		gbc_fillerPanel.fill = GridBagConstraints.BOTH;
		gbc_fillerPanel.gridx = 0;
		gbc_fillerPanel.gridy = 1;
		getContentPane().add(fillerPanel, gbc_fillerPanel);
		fillerPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) buttonPanel.getLayout();
		flowLayout.setHgap(10);
		fillerPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("Exit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton.setFont(btnNewButton.getFont().deriveFont(btnNewButton.getFont().getStyle() | Font.BOLD, 12f));
		btnNewButton.setPreferredSize(new Dimension(100, 40));
		buttonPanel.add(btnNewButton);
		
		JButton btnAbout = new JButton("About");
		btnAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAbout();
			}
		});
		btnAbout.setPreferredSize(new Dimension(100, 40));
		btnAbout.setFont(btnAbout.getFont().deriveFont(btnAbout.getFont().getStyle() | Font.BOLD, 12f));
		buttonPanel.add(btnAbout);

		JPanel keepAuctionBtnsToTopPnl = new JPanel();
		keepAuctionBtnsToTopPnl.setBackground(Color.WHITE);
		keepAuctionBtnsToTopPnl.setLayout(new BorderLayout(0, 0));
		JScrollPane auctionPanelScroller = new JScrollPane(keepAuctionBtnsToTopPnl);
		auctionPanelScroller.setBorder(null);
		auctionPanelScroller.setViewportBorder(null);
		fillerPanel.add(auctionPanelScroller, BorderLayout.CENTER);
		auctionPanelScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		auctionsPanel = new JPanel();
		keepAuctionBtnsToTopPnl.add(auctionsPanel, BorderLayout.NORTH);
		auctionsPanel.setAlignmentX(0.0f);
		auctionsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		auctionsPanel.setBackground(Color.WHITE);
		auctionsPanel.setLayout(new BoxLayout(auctionsPanel, BoxLayout.Y_AXIS));
		
		removalPopup = new JPopupMenu();
		JMenuItem miRemove = new JMenuItem("Remove");
		miRemove.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				auctionsPanel.remove(selectedInfo.button);
				auctionsPanel.revalidate();
				selectedInfo.frame.setVisible(false);
				selectedInfo.frame.dispose();
			}
		});
		removalPopup.add(miRemove);
		
		tracker.addListener(new AuctionTrackerListener() {
			
			public void newAuctionAdded(final Auction auction, final AuctionFrame frame) {
				SwingUtilities.invokeLater(new Runnable() {
					
					public void run() {
						addAuction(auction, frame);
					}

				});
			}
			
			public void discoveredAuctionInfo(final Auction auction) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						auctionInfo.get(auction).button.setText(buildButtonName(auction));
						auctionsPanel.repaint();
					}
				});
			}
		});
		
		setSize(400, 400);
	}

	protected void showAbout() {
		new AboutDlg().setVisible(true);
	}

	private static String buildButtonName(final Auction auction) {
		return "Auction #"+auction.getId()+
		 (auction.getTitle() != null ? ": "+auction.getTitle() : "");
	}
	
	private static class Info {
		JButton button;
		AuctionFrame frame;
	}
	
	private class AuctionButtonListener implements ActionListener {
		Info info;
		
		public AuctionButtonListener(Info info) {
			this.info = info;
		}

		public void actionPerformed(ActionEvent e) {
			info.frame.setVisible(true);
		}
	}

	private void addAuction(final Auction auction, final AuctionFrame frame) {
		if (!auctionInfo.containsKey(auction)) {
			final Info info = new Info();
			info.button = new JButton(buildButtonName(auction));
			info.button.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					selectedInfo = info;
					if (e.isPopupTrigger()) {
						removalPopup.show(info.button, e.getX(), e.getY());
					}
				}
			});
			info.button.addActionListener(new AuctionButtonListener(info));
			info.frame = frame;
			auctionInfo.put(auction, info);
			
			auctionsPanel.add(Box.createVerticalStrut(5));
			auctionsPanel.add(info.button);
			auctionsPanel.revalidate();
		}
	}
}
