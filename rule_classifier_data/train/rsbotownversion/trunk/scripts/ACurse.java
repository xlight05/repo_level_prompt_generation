import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import org.rsbot.bot.Bot;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSNPC;

@ScriptManifest(authors = { "Antic" }, category = "Magic", name = "ACurse", version = 1.04, description = "<b>ACurse</b><br/>")
public class ACurse extends Script implements PaintListener,
		ServerMessageListener {

	ACurseGUI gui;
	public long startTime;
	public boolean guiWait = true, guiExit;
	public boolean antiban;
	public boolean stopAfter;
	public boolean logout;
	public boolean stopAfterLvl;
	public boolean stopAfterCasts;
	public boolean planning;
	public boolean advanced;
	public int castsLimit = 0;
	public int planint;
	xAntiBan antiban2;
	Thread t;
	public int MAGICstartexp;
	public int stopAfterLvl2;
	public int mouseSpeed;
	public int spellInterface;
	public int casts;
	public int NPCID;
	public String spell;
	private final ScriptManifest properties = getClass().getAnnotation(
			ScriptManifest.class);

	public boolean onStart(Map<String, String> args) {
		gui = new ACurseGUI();
		gui.setVisible(true);
		while (guiWait) {
			wait(100);
		}
		startTime = System.currentTimeMillis();
		return guiExit;
	}

	public void onFinish() {
		try {
			antiban2.stopThread = true;
		} catch (NullPointerException e) {
		}
		Bot.getEventManager().removeListener(PaintListener.class, this);
	}

	private class xAntiBan implements Runnable {
		public boolean stopThread;

		public void run() {
			while (!stopThread) {
				try {
					if (random(0, 15) == 0) {
						final char[] LR = new char[] { KeyEvent.VK_LEFT,
								KeyEvent.VK_RIGHT };
						final char[] UD = new char[] { KeyEvent.VK_DOWN,
								KeyEvent.VK_UP };
						final char[] LRUD = new char[] { KeyEvent.VK_LEFT,
								KeyEvent.VK_RIGHT, KeyEvent.VK_UP,
								KeyEvent.VK_UP };
						final int random2 = random(0, 2);
						final int random1 = random(0, 2);
						final int random4 = random(0, 4);

						if (random(0, 3) == 0) {
							Bot.getInputManager().pressKey(LR[random1]);
							Thread.sleep(random(100, 400));
							Bot.getInputManager().pressKey(UD[random2]);
							Thread.sleep(random(300, 600));
							Bot.getInputManager().releaseKey(UD[random2]);
							Thread.sleep(random(100, 400));
							Bot.getInputManager().releaseKey(LR[random1]);
						} else {
							Bot.getInputManager().pressKey(LRUD[random4]);
							if (random4 > 1) {
								Thread.sleep(random(300, 600));
							} else {
								Thread.sleep(random(500, 900));
							}
							Bot.getInputManager().releaseKey(LRUD[random4]);
						}
					} else {
						Thread.sleep(random(200, 2000));
					}
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected int getMouseSpeed() {
		return random(mouseSpeed, mouseSpeed + 1);
	}

	public void cast() {
		if (getNearestNPCByID(NPCID) != null
				&& getNearestNPCByID(NPCID).isOnScreen()) {
			if (getCurrentTab() != TAB_MAGIC) {
				openTab(TAB_MAGIC);
			}
			atInterface(192, spellInterface);
			wait(random(500, 800));
			atNPC(getNearestNPCByID(NPCID), spell);
			wait(random(1000, 1200));
			casts++;
		} else {
			for (int i = 0; i < 2; i++) {
			log("Cannot find the NPC. Please walk near it or use different NPC ID.");
			wait(1000);
			}
			gui.setVisible(true);
			while (guiWait) {
				wait(100);
			}
		}
	}

	public void advanced() {
		if (getNearestNPCByID(NPCID) != null
				&& getNearestNPCByID(NPCID).isOnScreen()) {
			if (getCurrentTab() != TAB_MAGIC) {
				openTab(TAB_MAGIC);
			}
			if (skills.getCurrentSkillLevel(STAT_MAGIC) > 3
					&& skills.getCurrentSkillLevel(STAT_MAGIC) < 11) {
				atInterface(192, 26);
				wait(random(500, 800));
				atNPC(getNearestNPCByID(NPCID), "Cast Confuse");
				wait(random(1000, 1200));
			} else if (skills.getCurrentSkillLevel(STAT_MAGIC) > 11
					&& skills.getCurrentSkillLevel(STAT_MAGIC) < 19) {
				atInterface(192, 31);
				wait(random(500, 800));
				atNPC(getNearestNPCByID(NPCID), "Cast Weaken");
				wait(random(1000, 1200));
			} else if (skills.getCurrentSkillLevel(STAT_MAGIC) > 19
					&& skills.getCurrentSkillLevel(STAT_MAGIC) < 66) {
				atInterface(192, 35);
				wait(random(500, 800));
				atNPC(getNearestNPCByID(NPCID), "Cast Curse");
				wait(random(1000, 1200));
			} else if (skills.getCurrentSkillLevel(STAT_MAGIC) > 66
					&& skills.getCurrentSkillLevel(STAT_MAGIC) < 73) {
				atInterface(192, 75);
				wait(random(500, 800));
				atNPC(getNearestNPCByID(NPCID), "Cast Vulnerability");
				wait(random(1000, 1200));
			} else if (skills.getCurrentSkillLevel(STAT_MAGIC) > 73
					&& skills.getCurrentSkillLevel(STAT_MAGIC) < 80) {
				atInterface(192, 78);
				wait(random(500, 800));
				atNPC(getNearestNPCByID(NPCID), "Cast Enfeeble");
				wait(random(1000, 1200));
			} else if (skills.getCurrentSkillLevel(STAT_MAGIC) > 80) {
				atInterface(192, 82);
				wait(random(500, 800));
				atNPC(getNearestNPCByID(NPCID), "Cast Stun");
				wait(random(1000, 1200));
			}
		} else {
			log("Cannot find the NPC. Please walk near it or use different NPC ID.");
			gui.setVisible(true);
			while (guiWait) {
				wait(100);
			}
		}
	}

	public void stopAfter() {
		if (stopAfterLvl) {
			if (skills.getCurrentSkillLevel(STAT_MAGIC) > stopAfterLvl2) {
				log("Desired Magic level achieved! Logging out...");
				logout();
				stopScript();
			}
		}
		if (stopAfter) {
			if (logout == true) {
				log("No Runes left. Logging out...");
				logout();
				stopScript();
			}
		}
		if (stopAfterCasts) {
			if (casts >= castsLimit) {
				log(casts + "/" + castsLimit + " Casts done! Logging out...");
				logout();
				stopScript();
			}
		}
	}

	public void upstairs() {
		try {
			if (getPlane() == 1) {
				if (getNearestObjectByID(24359) != null) {
					atObject(getNearestObjectByID(24359), "Climb-down");
				}
			}
		} catch (NullPointerException e) {
		}
	}

	@Override
	public int loop() {
		stopAfter();
		upstairs();
		getMouseSpeed();
		if (advanced) {
			advanced();
		} else {
			cast();
		}
		return 100;
	}

	private void drawMouse(final Graphics g) {
		final Point loc = getMouseLocation();
		if (System.currentTimeMillis()
				- Bot.getClient().getMouse().getMousePressTime() < 100) {
			g.setColor(new Color(0, 0, 0, 200));
			g.drawString("C", loc.x - 5, loc.y - 5);
			g.setColor(Color.BLACK);
			g.drawLine(0, loc.y, 766, loc.y);
			g.drawLine(loc.x, 0, loc.x, 505);
		} else if (System.currentTimeMillis()
				- Bot.getClient().getMouse().getMousePressTime() < 200) {
			g.setColor(new Color(0, 0, 0, 175));
			g.drawString("Cl", loc.x - 5, loc.y - 5);
			g.setColor(Color.BLACK);
			g.drawLine(0, loc.y, 766, loc.y);
			g.drawLine(loc.x, 0, loc.x, 505);
		} else if (System.currentTimeMillis()
				- Bot.getClient().getMouse().getMousePressTime() < 300) {
			g.setColor(new Color(0, 0, 0, 150));
			g.drawString("Cli", loc.x - 5, loc.y - 5);
			g.setColor(Color.BLACK);
			g.drawLine(0, loc.y, 766, loc.y);
			g.drawLine(loc.x, 0, loc.x, 505);
		} else if (System.currentTimeMillis()
				- Bot.getClient().getMouse().getMousePressTime() < 400) {
			g.setColor(new Color(0, 0, 0, 125));
			g.drawString("Clic", loc.x - 5, loc.y - 5);
			g.setColor(Color.BLACK);
			g.drawLine(0, loc.y, 766, loc.y);
			g.drawLine(loc.x, 0, loc.x, 505);
		} else if (System.currentTimeMillis()
				- Bot.getClient().getMouse().getMousePressTime() < 500) {
			g.setColor(new Color(0, 0, 0, 100));
			g.drawString("Click", loc.x - 5, loc.y - 5);
			g.setColor(Color.BLACK);
			g.drawLine(0, loc.y, 766, loc.y);
			g.drawLine(loc.x, 0, loc.x, 505);
		} else {
			g.setColor(Color.BLACK);
			g.drawLine(0, loc.y, 766, loc.y);
			g.drawLine(loc.x, 0, loc.x, 505);
		}

	}

	@Override
	public void onRepaint(Graphics g) {
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		drawMouse(g);
		if (isLoggedIn()) {

			long millis = System.currentTimeMillis() - startTime;
			long hours = millis / (1000 * 60 * 60);
			millis -= hours * (1000 * 60 * 60);
			long minutes = millis / (1000 * 60);
			millis -= minutes * (1000 * 60);
			long seconds = millis / 1000;

			try {
				RSNPC farmer = getNearestFreeNPCByID(NPCID);
				Point Xx = farmer.getLocation().getScreenLocation();
				if (pointOnScreen(Xx)) {
					g.setColor(new Color(0, 0, 0, 150));
					g.fillRect(Xx.x - 8, Xx.y - 18, 30, 30);
					g.setColor(Color.black);
					g.drawRect(Xx.x - 8, Xx.y - 18, 30, 30);
					org.rsbot.script.wrappers.RSPlayer Me = getMyPlayer();
					Point Xx1 = Me.getLocation().getScreenLocation();
					if (pointOnScreen(Xx1)) {
						g.setColor(new Color(200, 0, 0, 150));
						g.fillRect(Xx1.x - 8, Xx1.y - 15, 30, 30);
						g.setColor(Color.black);
						g.drawRect(Xx1.x - 8, Xx1.y - 15, 30, 30);
					}
					g.setColor(Color.white);
					g.drawLine(Xx.x, Xx.y, Xx1.x, Xx1.y);
				}
			} catch (NullPointerException e5) {
			}
			try {
				org.rsbot.script.wrappers.RSPlayer Me = getMyPlayer();
				Point Xx = Me.getLocation().getScreenLocation();
				if (pointOnScreen(Xx)) {
					g.setColor(new Color(200, 0, 0, 200));
					g.fillRect(Xx.x - 8, Xx.y - 15, 30, 30);
					g.setColor(Color.black);
					g.drawRect(Xx.x - 8, Xx.y - 15, 30, 30);
				}
			} catch (NullPointerException e5) {
			}
			int MAGICexpgained = 0;
			if (MAGICstartexp == 0) {
				MAGICstartexp = skills.getCurrentSkillExp(STAT_MAGIC);
			}
			MAGICexpgained = skills.getCurrentSkillExp(STAT_MAGIC)
					- MAGICstartexp;

			int x1 = 165;
			int y1 = 300;
			g.setColor(new Color(0, 0, 0, 200));// 0, 0, 0,150
			g.fillRect(x1, y1 - 12, 200, 10);
			g.setColor(new Color(20, 20, 20, 200)); // 20, 20, 20, 150
			g.fillRect(x1, y1 - 22, 200, 10);
			g.drawRect(x1, y1 - 22, 200, 20);
			g.setColor(new Color(255, 255, 255, 255));
			g.drawString(
					properties.name() + " Version " + properties.version(),
					x1 + 25, y1 -= 22);
			g.drawString("Time running: " + hours + ":" + minutes + ":"
					+ seconds, x1 + 55, y1 += 15);

			int x2 = 130;
			int y2 = 320;
			g.setColor(new Color(0, 0, 0, 200));
			g.fillRect(x2, y2 - 12, 135, 10);
			g.setColor(new Color(20, 20, 20, 200));
			g.fillRect(x2, y2 - 22, 135, 10);
			g.drawRect(x2, y2 - 22, 135, 20);
			g.setColor(new Color(255, 255, 255, 255));
			g.drawString("Xp Gained: " + MAGICexpgained, x2 + 15, y2 -= 7);

			int x3 = 265;
			int y3 = 320;
			int perHourXP = (int) ((MAGICexpgained) * 3600000D / (System
					.currentTimeMillis() - startTime));
			g.setColor(new Color(0, 0, 0, 200));
			g.fillRect(x3, y3 - 12, 135, 10);
			g.setColor(new Color(20, 20, 20, 200));
			g.fillRect(x3, y3 - 22, 135, 10);
			g.drawRect(x3, y3 - 22, 135, 20);
			g.setColor(new Color(255, 255, 255, 255));
			g.drawString("Xp/h: " + perHourXP, x3 + 40, y3 -= 7);

			int x4 = 379;
			int y4 = 25;
			int perHourXP2 = (int) ((casts) * 3600000D / (System
					.currentTimeMillis() - startTime));
			g.setColor(new Color(0, 0, 0, 200));
			g.fillRect(x4, y4 - 12, 135, 10);
			g.setColor(new Color(20, 20, 20, 200));
			g.fillRect(x4, y4 - 22, 135, 10);
			g.drawRect(x4, y4 - 22, 135, 20);
			g.setColor(new Color(255, 255, 255, 255));
			g.drawString("Casts/H: " + perHourXP2, x4 + 18, y4 -= 7);

			int x5 = 379;
			int y5 = 45;
			g.setColor(new Color(0, 0, 0, 200));
			g.fillRect(x5, y5 - 12, 135, 10);
			g.setColor(new Color(20, 20, 20, 200));
			g.fillRect(x5, y5 - 22, 135, 10);
			g.drawRect(x5, y5 - 22, 135, 20);
			g.setColor(new Color(255, 255, 255, 255));
			g
					.drawString("Casts: " + casts + "/" + castsLimit, x5 + 18,
							y5 -= 7);

			if (planning) {
				try {
					int x6 = 379;
					int y6 = 65;
					double targetxp = planint / perHourXP;
					g.setColor(new Color(0, 0, 0, 200));
					g.fillRect(x6, y6 - 12, 135, 10);
					g.setColor(new Color(20, 20, 20, 200));
					g.fillRect(x6, y6 - 22, 135, 10);
					g.drawRect(x6, y6 - 22, 135, 20);
					g.setColor(new Color(255, 255, 255, 255));
					g.setColor(new Color(0, 0, 0, 200));
					g.fillRect(x6, y6 + 20 - 12, 135, 10);
					g.setColor(new Color(20, 20, 20, 200));
					g.fillRect(x6, y6 + 20 - 22, 135, 10);
					g.drawRect(x6, y6 + 20 - 22, 135, 20);
					g.setColor(new Color(255, 255, 255, 255));
					g.drawString("Time Till Target XP:", x6 + 18, y6 - 7);
					if (MAGICexpgained > planint) {
						g.drawString("Achieved!", x6 + 18, y6 + 13);
					} else if (targetxp < 1) {
						g.drawString("Less than 1 hour", x6 + 18, y6 + 13);
					} else if (targetxp > 1) {
						g.drawString(targetxp + " Hours", x6 + 18, y6 + 13);
					}
				} catch (ArithmeticException e) {
				}
			}

			int x = 1;
			int y = 338;
			g.setColor(new Color(20, 20, 20, 200));
			g.fillRect(x, y - 20, 516, 10);
			g.setColor(new Color(0, 0, 0, 200));
			g.fillRect(x, y - 10, 516, 10);
			g.drawRect(x, y - 20, 516, 20);
			g.setColor(new Color(13, 13, 13, 25));
			g.drawRect(x, y - 20, 516, 10);
			g.setColor(new Color(50, 50, 50, 25));
			g.drawRect(x, y - 20, 516, 10);
			g.setColor(new Color(255, 255, 255, 50));
			g.fillRect(x + 5, y - 15,
					skills.getPercentToNextLevel(STAT_MAGIC) * 5, 12);
			g.setColor(Color.white);
			g.drawString("Magic Level: "
					+ skills.getCurrentSkillLevel(STAT_MAGIC) + " "
					+ skills.getPercentToNextLevel(STAT_MAGIC) + "%", x + 215,
					y - 5);

		}
	}

	public class ACurseGUI extends JFrame {
		public ACurseGUI() {
			initComponents();
		}

		private void Startbutton(ActionEvent e) {
			try {
				stopAfterLvl2 = Integer.parseInt(textField1.getText());
			} catch (NumberFormatException e1) {
			}
			try {
				castsLimit = Integer.parseInt(textField2.getText());
			} catch (NumberFormatException e2) {
			}
			try {
				planint = Integer.parseInt(textField3.getText());
			} catch (NumberFormatException e3) {
			}
			try {
				NPCID = Integer.parseInt(textField4.getText());
			} catch (NumberFormatException e4) {
			}
			mouseSpeed = slider1.getValue();
			antiban = checkBox1.isSelected();
			advanced = checkBox5.isSelected();
			planning = checkBox6.isSelected();
			stopAfterLvl = checkBox2.isSelected();
			stopAfter = checkBox3.isSelected();
			stopAfterCasts = checkBox4.isSelected();

			if (antiban) {
				antiban2 = new xAntiBan();
				t = new Thread(antiban2);

				if (!t.isAlive()) {
					t.start();
					log("AntiBan Enabled");
				}
			}

			if (comboBox1.getSelectedIndex() == 0) {
				spellInterface = 26;
				spell = "Cast " + comboBox1.getSelectedItem();
			} else if (comboBox1.getSelectedIndex() == 1) {
				spell = "Cast " + comboBox1.getSelectedItem();
				spellInterface = 31;
			} else if (comboBox1.getSelectedIndex() == 2) {
				spellInterface = 35;
				spell = "Cast " + comboBox1.getSelectedItem();
			} else if (comboBox1.getSelectedIndex() == 3) {
				spellInterface = 75;
				spell = "Cast " + comboBox1.getSelectedItem();
			} else if (comboBox1.getSelectedIndex() == 4) {
				spellInterface = 78;
				spell = "Cast " + comboBox1.getSelectedItem();
			} else if (comboBox1.getSelectedIndex() == 5) {
				spellInterface = 82;
				spell = "Cast " + comboBox1.getSelectedItem();
			}

			guiWait = false;
			guiExit = true;
			dispose();
		}

		private void Close(ActionEvent e) {
			guiWait = false;
			guiExit = true;
			dispose();
			stopScript();
		}

		private void initComponents() {
			// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
			panel1 = new JPanel();
			label6 = new JLabel();
			checkBox1 = new JCheckBox();
			checkBox2 = new JCheckBox();
			checkBox3 = new JCheckBox();
			textField1 = new JTextField();
			comboBox1 = new JComboBox();
			label2 = new JLabel();
			separator1 = new JSeparator();
			separator3 = new JSeparator();
			separator2 = new JSeparator();
			separator4 = new JSeparator();
			label1 = new JLabel();
			label3 = new JLabel();
			slider1 = new JSlider();
			button1 = new JButton();
			label4 = new JLabel();
			label5 = new JLabel();
			checkBox4 = new JCheckBox();
			textField2 = new JTextField();
			checkBox5 = new JCheckBox();
			label7 = new JLabel();
			separator5 = new JSeparator();
			separator6 = new JSeparator();
			separator7 = new JSeparator();
			separator8 = new JSeparator();
			checkBox6 = new JCheckBox();
			textField3 = new JTextField();
			label8 = new JLabel();
			textField4 = new JTextField();
			label9 = new JLabel();
			button2 = new JButton();

			//======== this ========
			setBackground(Color.black);
			setTitle("ACurse");
			setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
			Container contentPane = getContentPane();
			contentPane.setLayout(null);

			//======== panel1 ========
			{
				panel1.setBackground(Color.black);
				panel1.setBorder(LineBorder.createBlackLineBorder());
				panel1.setLayout(null);

				//---- label6 ----
				label6.setText("Main Settings");
				label6.setForeground(Color.red);
				panel1.add(label6);
				label6.setBounds(100, 45, 70, 16);

				//---- checkBox1 ----
				checkBox1.setText("Enable Antiban");
				checkBox1.setBackground(Color.black);
				checkBox1.setForeground(Color.red);
				panel1.add(checkBox1);
				checkBox1.setBounds(70, 85, 110, checkBox1.getPreferredSize().height);

				//---- checkBox2 ----
				checkBox2.setText("Stop After Level");
				checkBox2.setBackground(Color.black);
				checkBox2.setForeground(Color.red);
				panel1.add(checkBox2);
				checkBox2.setBounds(35, 105, 105, 24);

				//---- checkBox3 ----
				checkBox3.setText("Stop after out of runes");
				checkBox3.setBackground(Color.black);
				checkBox3.setForeground(Color.red);
				panel1.add(checkBox3);
				checkBox3.setBounds(75, 155, 155, 24);

				//---- textField1 ----
				textField1.setBackground(Color.black);
				textField1.setForeground(Color.red);
				panel1.add(textField1);
				textField1.setBounds(40, 130, 90, textField1.getPreferredSize().height);

				//---- comboBox1 ----
				comboBox1.setBackground(Color.black);
				comboBox1.setForeground(Color.red);
				comboBox1.setModel(new DefaultComboBoxModel(new String[] {
					"Confuse",
					"Weaken",
					"Curse",
					"Vulnerability",
					"Enfeeble",
					"Stun"
				}));
				panel1.add(comboBox1);
				comboBox1.setBounds(75, 220, 105, 20);

				//---- label2 ----
				label2.setText("Spell to use");
				label2.setForeground(Color.red);
				panel1.add(label2);
				label2.setBounds(75, 205, 105, label2.getPreferredSize().height);

				//---- separator1 ----
				separator1.setBackground(new Color(51, 102, 0));
				separator1.setForeground(new Color(0, 102, 0));
				separator1.setOrientation(SwingConstants.VERTICAL);
				panel1.add(separator1);
				separator1.setBounds(30, 60, 10, 225);

				//---- separator3 ----
				separator3.setBackground(new Color(51, 102, 0));
				separator3.setForeground(new Color(0, 102, 0));
				panel1.add(separator3);
				separator3.setBounds(30, 285, 212, 10);

				//---- separator2 ----
				separator2.setBackground(new Color(51, 102, 0));
				separator2.setForeground(new Color(0, 102, 0));
				separator2.setOrientation(SwingConstants.VERTICAL);
				panel1.add(separator2);
				separator2.setBounds(240, 60, 10, 225);

				//---- separator4 ----
				separator4.setBackground(new Color(51, 102, 0));
				separator4.setForeground(new Color(0, 102, 0));
				panel1.add(separator4);
				separator4.setBounds(30, 60, 212, 15);

				//---- label1 ----
				label1.setText("ACurse");
				label1.setFont(new Font("Traditional Arabic", Font.PLAIN, 45));
				label1.setForeground(Color.white);
				panel1.add(label1);
				label1.setBounds(65, 5, 145, 50);

				//---- label3 ----
				label3.setText("Mouse speed");
				label3.setForeground(Color.red);
				panel1.add(label3);
				label3.setBounds(75, 245, 105, 16);

				//---- slider1 ----
				slider1.setBackground(Color.black);
				slider1.setValue(8);
				slider1.setForeground(Color.red);
				slider1.setMaximum(15);
				slider1.setMajorTickSpacing(1);
				panel1.add(slider1);
				slider1.setBounds(70, 260, 120, 20);

				//---- button1 ----
				button1.setText("Start");
				button1.setBackground(Color.black);
				button1.setForeground(Color.red);
				button1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Startbutton(e);
					}
				});
				panel1.add(button1);
				button1.setBounds(30, 360, 105, 30);

				//---- label4 ----
				label4.setText("Fast");
				label4.setForeground(Color.red);
				panel1.add(label4);
				label4.setBounds(50, 260, 30, 16);

				//---- label5 ----
				label5.setText("Slow");
				label5.setForeground(Color.red);
				panel1.add(label5);
				label5.setBounds(190, 260, 35, 16);

				//---- checkBox4 ----
				checkBox4.setText("Stop After Casts");
				checkBox4.setBackground(Color.black);
				checkBox4.setForeground(Color.red);
				panel1.add(checkBox4);
				checkBox4.setBounds(135, 105, 105, 23);

				//---- textField2 ----
				textField2.setBackground(Color.black);
				textField2.setForeground(Color.red);
				panel1.add(textField2);
				textField2.setBounds(140, 130, 90, 20);

				//---- checkBox5 ----
				checkBox5.setText("Advanced mode");
				checkBox5.setBackground(Color.black);
				checkBox5.setForeground(Color.red);
				panel1.add(checkBox5);
				checkBox5.setBounds(75, 180, 110, 23);

				//---- label7 ----
				label7.setText("Planning Mode");
				label7.setForeground(Color.red);
				panel1.add(label7);
				label7.setBounds(100, 285, 80, 16);

				//---- separator5 ----
				separator5.setBackground(new Color(51, 102, 0));
				separator5.setForeground(new Color(0, 102, 0));
				separator5.setOrientation(SwingConstants.VERTICAL);
				panel1.add(separator5);
				separator5.setBounds(30, 300, 10, 55);

				//---- separator6 ----
				separator6.setBackground(new Color(51, 102, 0));
				separator6.setForeground(new Color(0, 102, 0));
				separator6.setOrientation(SwingConstants.VERTICAL);
				panel1.add(separator6);
				separator6.setBounds(240, 300, 10, 55);

				//---- separator7 ----
				separator7.setBackground(new Color(51, 102, 0));
				separator7.setForeground(new Color(0, 102, 0));
				panel1.add(separator7);
				separator7.setBounds(30, 355, 212, 10);

				//---- separator8 ----
				separator8.setBackground(new Color(51, 102, 0));
				separator8.setForeground(new Color(0, 102, 0));
				panel1.add(separator8);
				separator8.setBounds(30, 300, 212, 10);

				//---- checkBox6 ----
				checkBox6.setText("Enable");
				checkBox6.setBackground(Color.black);
				checkBox6.setForeground(Color.red);
				panel1.add(checkBox6);
				checkBox6.setBounds(105, 305, 65, 23);

				//---- textField3 ----
				textField3.setBackground(Color.black);
				textField3.setForeground(Color.red);
				panel1.add(textField3);
				textField3.setBounds(125, 330, 90, 20);

				//---- label8 ----
				label8.setText("Target XP");
				label8.setForeground(Color.red);
				panel1.add(label8);
				label8.setBounds(55, 335, 65, 14);

				//---- textField4 ----
				textField4.setBackground(Color.black);
				textField4.setForeground(Color.red);
				panel1.add(textField4);
				textField4.setBounds(115, 65, 90, 20);

				//---- label9 ----
				label9.setText("NPC ID");
				label9.setForeground(Color.red);
				panel1.add(label9);
				label9.setBounds(70, 65, 70, 21);

				//---- button2 ----
				button2.setText("Close");
				button2.setBackground(Color.black);
				button2.setForeground(Color.red);
				button2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Startbutton(e);
						Close(e);
					}
				});
				panel1.add(button2);
				button2.setBounds(140, 360, 105, 30);

				{ // compute preferred size
					Dimension preferredSize = new Dimension();
					for(int i = 0; i < panel1.getComponentCount(); i++) {
						Rectangle bounds = panel1.getComponent(i).getBounds();
						preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
						preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
					}
					Insets insets = panel1.getInsets();
					preferredSize.width += insets.right;
					preferredSize.height += insets.bottom;
					panel1.setMinimumSize(preferredSize);
					panel1.setPreferredSize(preferredSize);
				}
			}
			contentPane.add(panel1);
			panel1.setBounds(0, 0, 285, 410);

			{ // compute preferred size
				Dimension preferredSize = new Dimension();
				for(int i = 0; i < contentPane.getComponentCount(); i++) {
					Rectangle bounds = contentPane.getComponent(i).getBounds();
					preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
					preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
				}
				Insets insets = contentPane.getInsets();
				preferredSize.width += insets.right;
				preferredSize.height += insets.bottom;
				contentPane.setMinimumSize(preferredSize);
				contentPane.setPreferredSize(preferredSize);
			}
			setSize(290, 435);
			setLocationRelativeTo(getOwner());
			// JFormDesigner - End of component initialization  //GEN-END:initComponents
		}

		// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
		private JPanel panel1;
		private JLabel label6;
		private JCheckBox checkBox1;
		private JCheckBox checkBox2;
		private JCheckBox checkBox3;
		private JTextField textField1;
		private JComboBox comboBox1;
		private JLabel label2;
		private JSeparator separator1;
		private JSeparator separator3;
		private JSeparator separator2;
		private JSeparator separator4;
		private JLabel label1;
		private JLabel label3;
		private JSlider slider1;
		private JButton button1;
		private JLabel label4;
		private JLabel label5;
		private JCheckBox checkBox4;
		private JTextField textField2;
		private JCheckBox checkBox5;
		private JLabel label7;
		private JSeparator separator5;
		private JSeparator separator6;
		private JSeparator separator7;
		private JSeparator separator8;
		private JCheckBox checkBox6;
		private JTextField textField3;
		private JLabel label8;
		private JTextField textField4;
		private JLabel label9;
		private JButton button2;
		// JFormDesigner - End of variables declaration  //GEN-END:variables
	}


	@Override
	public void serverMessageRecieved(ServerMessageEvent arg0) {
		String serverString = arg0.getMessage();
		if (serverString.contains("You do not have enough")
				|| serverString.contains("Runes to cast this spell.")) {
			logout = true;
		}
	}
}
