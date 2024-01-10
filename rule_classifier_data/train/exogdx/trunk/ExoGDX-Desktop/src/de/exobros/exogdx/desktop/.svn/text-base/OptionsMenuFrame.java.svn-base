package de.exobros.exogdx.desktop;

import javax.swing.JFrame;

import de.exobros.exogdx.sbg.StateBasedGame;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JTextField;

public class OptionsMenuFrame extends JFrame implements ActionListener {

	private StateBasedGame sbg;
	private String gameTitle;
	private JTextField txtResolution;
	private JTextField txtFullscreen;
	private JTextField txtVsync;
	private JTextField txtMultisamples;
	private AppConfig config;
	private JButton btnPlay;
	private JButton btnExit;
	private JButton btnResolution;
	private JButton btnFullscreen;
	private JButton btnVsync;
	private JButton btnMultisamples;

	protected int[][] resCycle = new int[][] { { 600, 400 }, { 800, 600 }, { 1280, 720 }, { 1680, 1050 }, { 1920, 1080 } };
	protected int[] msCycle = new int[] { 1, 2, 4, 8, 16 };

	public OptionsMenuFrame(StateBasedGame sbg, String title) {

		config = new AppConfig("config.ini");
		try {
			config.load();
		} catch (IOException e) {
			e.printStackTrace();
			try {
				config.save();
			} catch (IOException e1) {
				e1.printStackTrace();
				System.exit(-1);
			}
		}

		this.sbg = sbg;
		this.gameTitle = title;
		setResizable(false);
		getContentPane().setLayout(null);

		btnPlay = new JButton("Play");
		btnPlay.setBounds(292, 157, 89, 23);
		getContentPane().add(btnPlay);
		btnPlay.addActionListener(this);

		btnExit = new JButton("Save & Exit");
		btnExit.setBounds(167, 157, 115, 23);
		getContentPane().add(btnExit);
		btnExit.addActionListener(this);

		btnResolution = new JButton("Resolution");
		btnResolution.setBounds(26, 21, 201, 23);
		getContentPane().add(btnResolution);
		btnResolution.addActionListener(this);

		btnFullscreen = new JButton("Fullscreen");
		btnFullscreen.setBounds(26, 55, 201, 23);
		getContentPane().add(btnFullscreen);
		btnFullscreen.addActionListener(this);

		btnVsync = new JButton("Vsync");
		btnVsync.setBounds(26, 89, 201, 23);
		getContentPane().add(btnVsync);
		btnVsync.addActionListener(this);

		btnMultisamples = new JButton("Multisamples");
		btnMultisamples.setBounds(26, 123, 201, 23);
		getContentPane().add(btnMultisamples);
		btnMultisamples.addActionListener(this);

		txtResolution = new JTextField();
		txtResolution.setEditable(false);
		txtResolution.setText("Resolution");
		txtResolution.setBounds(237, 22, 147, 22);
		getContentPane().add(txtResolution);
		txtResolution.setColumns(10);

		txtFullscreen = new JTextField();
		txtFullscreen.setEditable(false);
		txtFullscreen.setText("Fullscreen");
		txtFullscreen.setBounds(237, 56, 147, 23);
		getContentPane().add(txtFullscreen);
		txtFullscreen.setColumns(10);

		txtVsync = new JTextField();
		txtVsync.setEditable(false);
		txtVsync.setText("Vsync");
		txtVsync.setBounds(237, 90, 147, 22);
		getContentPane().add(txtVsync);
		txtVsync.setColumns(10);

		txtMultisamples = new JTextField();
		txtMultisamples.setEditable(false);
		txtMultisamples.setText("Multisamples");
		txtMultisamples.setBounds(237, 124, 147, 22);
		getContentPane().add(txtMultisamples);
		txtMultisamples.setColumns(10);
		this.setVisible(true);
		this.setBounds(400, 300, 401, 217);
		this.setTitle(title + "-Optionsmenu");

		int xRes = 0;
		int yRes = 0;
		boolean fullscreen = false;
		boolean vSync = false;
		int multisamples = 0;

		try {
			xRes = config.getXRes();
			yRes = config.getYRes();
			fullscreen = config.getFullscreen();
			vSync = config.getVSync();
			multisamples = config.getMultisamples();

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			config = new AppConfig("config.ini");
			try {
				config.save();
			} catch (IOException e1) {
				e1.printStackTrace();
				System.exit(-1);
			}

			xRes = config.getXRes();
			yRes = config.getYRes();
			fullscreen = config.getFullscreen();
			vSync = config.getVSync();
			multisamples = config.getMultisamples();

		}

		txtResolution.setText(xRes + "x" + yRes);
		if (fullscreen) {
			txtFullscreen.setText("Enabled");
		} else {
			txtFullscreen.setText("Disabled");
		}
		if (vSync) {
			txtVsync.setText("Enabled");
		} else {
			txtVsync.setText("Disabled");
		}
		txtMultisamples.setText(String.valueOf(multisamples));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnExit) {
			try {
				config.save();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			this.dispose();
		} else if (e.getSource() == this.btnPlay) {
			try {
				config.save();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			this.dispose();
			DesktopUtils.startGame(sbg, gameTitle);
		} else if (e.getSource() == this.btnResolution) {
			this.resCyle();
			txtResolution.setText(config.getXRes() + "x" + config.getYRes());
		} else if (e.getSource() == this.btnFullscreen) {
			config.setFullscreen(!config.getFullscreen());
			if (config.getFullscreen()) {
				txtFullscreen.setText("Enabled");
			} else {
				txtFullscreen.setText("Disabled");
			}
		} else if (e.getSource() == this.btnVsync) {
			config.setVSync(!config.getVSync());
			if (config.getVSync()) {
				txtVsync.setText("Enabled");
			} else {
				txtVsync.setText("Disabled");
			}
		} else if (e.getSource() == this.btnMultisamples) {
			this.msCycle();
			txtMultisamples.setText(String.valueOf(config.getMultisamples()));
		}
	}

	private void msCycle() {
		int c = -1;

		for (int a = 0; a < msCycle.length; a++) {
			if (config.getMultisamples() == msCycle[a]) {
				c = a;
				a = msCycle.length;
			}
		}

		if (c == -1 || c >= msCycle.length - 1) {
			config.setMultisamples(msCycle[0]);
		} else {
			config.setMultisamples(msCycle[c + 1]);
		}
	}

	private void resCyle() {
		int c = -1;

		for (int a = 0; a < resCycle.length; a++) {
			if (config.getXRes() == resCycle[a][0] && config.getYRes() == resCycle[a][1]) {
				c = a;
				a = resCycle.length;
			}
		}

		if (c == -1 || c >= resCycle.length - 1) {
			config.setXRes(resCycle[0][0]);
			config.setYRes(resCycle[0][1]);
		} else {
			config.setXRes(resCycle[c + 1][0]);
			config.setYRes(resCycle[c + 1][1]);
		}

	}
}
