package com.itzg.quidsee;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class AboutDlg extends JDialog {
	private static final long serialVersionUID = 1L;
	
	public AboutDlg() {
		setSize(new Dimension(460, 225));
		setTitle("About QuidSee");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("Close");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AboutDlg.this.setVisible(false);
				AboutDlg.this.dispose();
			}
		});
		panel.add(btnNewButton);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBorder(new EmptyBorder(10, 10, 0, 10));
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JTextArea txtrQuidseehttpcodegooglecompquidsee = new JTextArea();
		txtrQuidseehttpcodegooglecompquidsee.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (Desktop.isDesktopSupported()) {
					Desktop desktop = Desktop.getDesktop();
					if (desktop.isSupported(Desktop.Action.BROWSE)) {
						try {
							desktop.browse(new URI("http://code.google.com/p/quidsee/"));
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		txtrQuidseehttpcodegooglecompquidsee.setFont(new Font("Dialog", Font.PLAIN, 12));
		txtrQuidseehttpcodegooglecompquidsee.setRows(10);
		txtrQuidseehttpcodegooglecompquidsee.setText("QuidSee 0.9 (http://code.google.com/p/quidsee/)\r\n\r\nKeep tallies on QuiBids auctions alongside your regular browser.\r\n\r\nCopyright (C) 2011 Geoff Bourne (itzgeoff@gmail.com)\r\n\r\nQuidSee comes with ABSOLUTELY NO WARRANTY.\r\nThis is free software, licensed under the Apache License 2.0.\r\n\r\nThis product includes software developed by LittleShoot");
		txtrQuidseehttpcodegooglecompquidsee.setEditable(false);
		panel_1.add(txtrQuidseehttpcodegooglecompquidsee, BorderLayout.CENTER);
	}
}
