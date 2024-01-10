import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.rsbot.bot.Bot;
import org.rsbot.bot.input.Mouse;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.script.Constants;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.Skills;
import org.rsbot.script.wrappers.RSInterface;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSTile;
import org.rsbot.util.ScreenshotUtil;

@ScriptManifest(authors = { "Bloddyharry" }, name = "Bloddy master Farmer Stealer", category = "Thieving", version = 2.2, description = "<html>\n"
		+ "<body style='font-family: Calibri; color:white; padding: 0px; text-align: center; background-color: black;'>"
		+ "<h2>"
		+ "Bloddy Master Farmer Stealer 2.2"
		+ "</h2>\n"
		+ "Made by Bloddyharry" + "<br><br>\n" + "<b>Settings in GUI.</b>\n")
public class BloddyMasterFarmerStealer extends Script implements PaintListener,
		ServerMessageListener {

	final ScriptManifest properties = getClass().getAnnotation(
			ScriptManifest.class);

	BufferedImage normal = null;
	BufferedImage clicked = null;
	public int farmerID = 2234;
	public int bBoothID = 2213;
	public int addFail = 0;
	public int glovesID = 10075;
	private boolean doDropSeeds = true;
	public boolean showInventory = false;
	private boolean withdraw10 = false;
	public String stopReason;
	private boolean canSay = true;
	public boolean logOut = false;
	public int wantedHours, wantedMinutes, wantedSeconds, wantedLevel;
	public int gainedLvls;
	public boolean finishAt, finishWhenLevel;
	private int startXP = 0;
	public int[] seedID = { 5304, 5296, 5300, 5295, 5303, 5302, 5100, 5323,
			5299, 5301, 5298, 5320, 5321, 1161, 1965, 1969, 1967, 1895, 1893,
			1891, 1971, 4293, 2142, 4291, 2140, 3228, 9980, 7223, 6297, 6293,
			6295, 6299, 7521, 9988, 7228, 2878, 7568, 2343, 1861, 13433, 315,
			325, 319, 3144, 347, 355, 333, 339, 351, 329, 3381, 361, 10136,
			5003, 379, 365, 373, 7946, 385, 397, 391, 3369, 3371, 3373, 2309,
			2325, 2333, 2327, 2331, 2323, 2335, 7178, 7180, 7188, 7190, 7198,
			7200, 7208, 7210, 7218, 7220, 2003, 2011, 2289, 2291, 2293, 2295,
			2297, 2299, 2301, 2303, 1891, 1893, 1895, 1897, 1899, 1901, 7072,
			7062, 7078, 7064, 7084, 7082, 7066, 7068, 1942, 6701, 6703, 7054,
			6705, 7056, 7060, 2130, 1985, 1993, 1989, 1978, 5763, 5765, 1913,
			5747, 1905, 5739, 1909, 5743, 1907, 1911, 5745, 2955, 5749, 5751,
			5753, 5755, 5757, 5759, 5761, 2084, 2034, 2048, 2036, 2217, 2213,
			2205, 2209, 2054, 2040, 2080, 2277, 2225, 2255, 2221, 2253, 2219,
			2281, 2227, 2223, 2191, 2233, 2092, 2032, 2074, 2030, 2281, 2235,
			2064, 2028, 2187, 2185, 2229 };
	public int[] junkSeedID = { 5319, 5307, 5305, 5322, 5099, 5310, 5308, 5102,
			5101, 5096, 5324, 5306, 5291, 5103, 5292, 5097, 5281, 5098, 5294,
			5105, 5106, 5280, 5297, 5311, 5104, 5293, 5318, 5282, 5309 };
	private int FOODID;
	private int ANIMATIONID = 11974;
	private int ANIMATIONID2 = 378;
	private int ANIMATIONID3 = 424;
	private int ANIMATIONID4 = 12030;
	RSTile[] farmerToBank = { new RSTile(3081, 3250), new RSTile(3092, 3244) };
	RSTile[] bankToFarmer = reversePath(farmerToBank);
	RSTile farmerTile = new RSTile(3081, 3250);
	RSTile bankTile = new RSTile(3092, 3244);
	String status = "";
	private int HP;
	private int startLvl;
	public int foodEated = 0;
	public int pickpockets = 0;
	public int failPickpocket = 0;
	public int somethingID = 5295;
	public long startTime = System.currentTimeMillis();
	public int[] pickpocketing = { 11942, 378 };
	private boolean guiWait = true, guiExit;
	BloddyMasterFarmerGUI gui;

	protected int getMouseSpeed() {
		return random(5, 7);
	}

	public boolean onStart(Map<String, String> args) {
		try {
			final URL cursorURL = new URL("http://i48.tinypic.com/313623n.png");
			final URL cursor80URL = new URL("http://i46.tinypic.com/9prjnt.png");
			normal = ImageIO.read(cursorURL);
			clicked = ImageIO.read(cursor80URL);
		} catch (MalformedURLException e) {
			log("Unable to buffer cursor.");
		} catch (IOException e) {
			log("Unable to open cursor image.");
		}
		URLConnection url = null;
		BufferedReader in = null;
		BufferedWriter out = null;
		try {
			url = new URL(
					"http://www.bloddyharry.webs.com/scripts/BloddyMasterFarmerStealerVERSION.txt")
					.openConnection();
			in = new BufferedReader(new InputStreamReader(url.getInputStream()));
			if (Double.parseDouble(in.readLine()) > properties.version()) {
				JOptionPane.showMessageDialog(null,
						"Update found, please update.");
				JOptionPane
						.showMessageDialog(
								null,
								"Please choose 'BloddyMasterFarmerStealer.java' in your scripts folder and hit 'Open'");
				JFileChooser fc = new JFileChooser();
				if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					url = new URL(
							"http://www.bloddyharry.webs.com/scripts/BloddyMasterFarmerStealer.java")
							.openConnection();
					in = new BufferedReader(new InputStreamReader(url
							.getInputStream()));
					out = new BufferedWriter(new FileWriter(fc
							.getSelectedFile().getPath()));
					String inp;
					while ((inp = in.readLine()) != null) {
						out.write(inp);
						out.newLine();
						out.flush();
					}
					log("Script successfully downloaded. Please recompile and reload your scripts!");
					return false;
				} else
					log("Update canceled");
			} else
				log("You have the latest version :)");
			if (in != null)
				in.close();
			if (out != null)
				out.close();
		} catch (IOException e) {
			log("Problem getting version :/");
			return false;
		}
		gui = new BloddyMasterFarmerGUI();
		gui.setVisible(true);
		while (guiWait) {
			wait(100);
		}
		startTime = System.currentTimeMillis();
		if (isLoggedIn()) {
			startXP = skills
					.getCurrentSkillExp(Skills.getStatIndex("thieving"));

			startLvl = skills.getCurrentSkillLevel(Skills
					.getStatIndex("thieving"));
		}
		return !guiExit;
	}

	public void onFinish() {
		ScreenshotUtil.takeScreenshot(true);
		log("Thank you for using Bloddy Master Farmer Stealer!");
	}

	private int getCurrentLifepoint() {
		if (RSInterface.getInterface(748).getChild(8).isValid()) {
			if (RSInterface.getInterface(748).getChild(8).getText() != null) {
				HP = Integer.parseInt(RSInterface.getInterface(748).getChild(8)
						.getText());
			} else {
				log.severe("Getting lifepoints Error");
			}
		} else {
			log.warning("HP Interface is not valid");
		}

		return HP;
	}

	public int checkCombat() {
		if (getMyPlayer().isInCombat()) {
			if (distanceTo(getDestination()) < random(5, 12)
					|| distanceTo(getDestination()) > 40) {
				if (!walkPathMM(farmerToBank)) {
					walkToClosestTile(randomizePath(farmerToBank, 2, 2));
					return random(1400, 1600);
				}
			}
		}
		return 0;
	}

	private int checkEat() {
		int CurrHP = getCurrentLifepoint() / 10;
		int RealHP = skills.getRealSkillLevel(STAT_HITPOINTS);
		if (CurrHP <= random(RealHP / 2, RealHP / 1.5)) {
			status = "Eating Food";
			atInventoryItem(FOODID, "Eat");
			log("ate food..");
		} else if (!inventoryContains(FOODID)) {
			status = "Getting food";
			if (atBank()) {
				openBank();
				bank();
			} else if (!atBank()) {
				if (distanceTo(getDestination()) < random(5, 12)
						|| distanceTo(getDestination()) > 40) {
					if (!walkPathMM(farmerToBank)) {
						walkToClosestTile(randomizePath(farmerToBank, 2, 2));
						return random(1400, 1600);
					}
				}
			}

		}
		return 0;
	}

	public int checkInventoryFull() {
		if (bank.isOpen() && bank.getCount(FOODID) == 0
				&& !inventoryContains(FOODID) && isLoggedIn()) {
			ScreenshotUtil.takeScreenshot(true);
			log("Out of food! logging out..");
			wait(random(4000, 5000));
			bank.close();
			logOut();
		}
		if (isInventoryFull()) {
			if (!atBank()) {
				status = "Walking to Bank";
				if (getEnergy() == random(60, 100)) {
					setRun(true);
				}
				if (distanceTo(getDestination()) < random(5, 12)
						|| distanceTo(getDestination()) > 40) {
					if (!walkPathMM(farmerToBank)) {
						walkToClosestTile(randomizePath(farmerToBank, 2, 2));
						return random(1400, 1600);
					}
				}
			} else if (atBank()) {
				openBank();
				bank();
			}
		}
		return 0;
	}

	public void checkFail() {
		if (addFail == 3) {
			log("out of food! logging out!");
			bank.close();
			logOut();
			stopScript();
		}
	}

	private void logOut() {
		moveMouse(754, 10, 10, 10);
		clickMouse(true);
		moveMouse(642, 378, 100, 20);
		clickMouse(true);
		wait(random(2000, 3000));
		stopScript();
	}

	@Override
	public int loop() {
		if (logOut == false) {
			setCameraAltitude(true);
			checkFail();
			checkEat();
			checkInventoryFull();
			if (getMyPlayer().getAnimation() == ANIMATIONID
					|| getMyPlayer().getAnimation() == ANIMATIONID2
					|| getMyPlayer().getAnimation() == ANIMATIONID3
					|| getMyPlayer().getAnimation() == ANIMATIONID4) {
				stunned();
			}
			if (!isInventoryFull() && inventoryContains(FOODID)) {
				if (distanceTo(getNearestNPCByName("Master Farmer")) <= 5) {
					pickPocket();
				} else if (distanceTo(getNearestNPCByName("Master Farmer")) >= 6) {
					status = "walking to Farmer";
					walkTo(
							(getNearestNPCByName("Master Farmer").getLocation()),
							1, 1);
				}
			}
		} else {
			logOut();
		}
		return random(100, 200);
	}

	private boolean atBank() {
		return distanceTo(bankTile) <= 5;
	}

	public int openBank() {
		final RSObject bankBooth = getNearestObjectByID(bBoothID);
		if (!(bank.isOpen())) {
			if (bankBooth != null) {
				atObject(bankBooth, "Use-Quickly");
				wait(random(200, 300));
			}
			if (bankBooth == null) {
				log("cant find bank :/");
				return random(150, 350);
			}
		}
		return random(150, 350);
	}

	public int bank() {
		status = "Banking";
		if (bank.isOpen()) {
			if (getInventoryCount() >= 1) {
				bank.depositAll();
				wait(random(300, 400));
			}
			if (!withdraw10) {
				bank.atItem(FOODID, "Withdraw-5");
				wait(random(300, 400));
			} else {
				bank.atItem(FOODID, "Withdraw-10");
				wait(random(300, 400));
			}
		}
		return 0;
	}

	public boolean antiBan2() {
		int randomNumber = random(1, 25);
		if (randomNumber <= 8) {
			if (randomNumber == 1) {
				setCameraRotation(random(1, 360));
			}
			if (randomNumber == 2) {
				moveMouse(random(50, 700), random(50, 450), 2, 2);
				wait(random(200, 300));
				moveMouse(random(50, 700), random(50, 450), 2, 2);
			}
			if (randomNumber == 3) {
				setCameraRotation(random(1, 50));
			}
			if (randomNumber == 4) {
				setCameraRotation(random(1, 360));
				moveMouse(random(50, 700), random(50, 450), 2, 2);
			}
			if (randomNumber == 6) {
				moveMouse(random(50, 700), random(50, 450), 2, 2);
			}
			if (randomNumber == 7) {
				moveMouse(random(50, 700), random(50, 450), 2, 2);
			}
			if (randomNumber == 8) {
				setCameraRotation(random(1, 360));
			}
		}
		return true;
	}

	private boolean stunned() {
		final int random = random(1, 5);
		status = "failed";
		checkEat();
		if (doDropSeeds) {
			if (inventoryContainsOneOf(junkSeedID)) {
				try {
					dropAllExcept(seedID);
				} catch (Exception e) {

				}
			} else if (!inventoryContainsOneOf(junkSeedID)) {
				antiBan();
			}
			if (random == 2) {
				antiBan();
			}
		} else {
			antiBan();
		}
		return true;
	}

	private boolean pickPocket() {
		if (getMyPlayer().getAnimation() == ANIMATIONID
				|| getMyPlayer().getAnimation() == ANIMATIONID2
				|| getMyPlayer().getAnimation() == ANIMATIONID3
				|| getMyPlayer().getAnimation() == ANIMATIONID4) {
			stunned();
		} else if (getNearestNPCByID(farmerID) != null
				&& getMyPlayer().getAnimation() != ANIMATIONID
				&& getMyPlayer().isInCombat() == false) {
			status = "pickpocketing";
			doSomethingNPC(farmerID, "Pickpocket");
			antiBan2();
			wait(random(400, 600));
		}
		if (getMyPlayer().getAnimation() == ANIMATIONID
				|| getMyPlayer().getAnimation() == ANIMATIONID2
				|| getMyPlayer().getAnimation() == ANIMATIONID3
				|| getMyPlayer().getAnimation() == ANIMATIONID4) {
			stunned();
		}
		return true;

	}

	public boolean doSomethingNPC(int id, String action) {
		org.rsbot.script.wrappers.RSNPC NPC = getNearestNPCByID(id);
		if (NPC == null) {
			return antiBan();
		}
		if (NPC != null) {
			if (NPC.isOnScreen()) {
				atNPC(NPC, action);
				return true;
			} else {
				walkTo(NPC.getLocation());
				atNPC(NPC, action);
				return true;
			}
		}
		return false;
	}

	public boolean antiBan() {
		int randomNumber = random(1, 16);
		if (randomNumber <= 16) {
			if (randomNumber == 1) {
				openRandomTab();
				wait(random(100, 500));
				moveMouse(631, 254, 50, 100);
				wait(random(2200, 2700));
			}
			if (randomNumber == 2) {
				moveMouse(random(50, 700), random(50, 450), 2, 2);
				wait(random(200, 400));
				moveMouse(random(50, 700), random(50, 450), 2, 2);
			}
			if (randomNumber == 3) {
				setCameraRotation(random(1, 360));
				moveMouse(random(50, 700), random(50, 450), 2, 2);
			}
			if (randomNumber == 4) {
				wait(random(100, 200));
				moveMouse(random(50, 700), random(50, 450), 2, 2);
				setCameraRotation(random(1, 360));
				moveMouse(random(50, 700), random(50, 450), 2, 2);
			}
			if (randomNumber == 6) {
				setCameraRotation(random(1, 360));
			}
			if (randomNumber == 7) {
				moveMouse(random(50, 700), random(50, 450), 2, 2);
			}
			if (randomNumber == 8) {
				wait(random(100, 200));
				moveMouse(631, 278);
				moveMouse(random(50, 700), random(50, 450), 2, 2);
				wait(random(200, 500));
				if (randomNumber == 9) {
					wait(random(100, 200));
					moveMouse(random(50, 700), random(50, 450), 2, 2);
					if (randomNumber == 10) {
						moveMouse(random(50, 700), random(50, 450), 2, 2);
					}
					if (randomNumber == 11) {
						setCameraRotation(random(1, 360));
						moveMouse(random(50, 700), random(50, 450), 2, 2);
					}
					if (randomNumber == 12) {
						openTab(TAB_STATS);
						wait(random(1000, 2000));
					}
					if (randomNumber == 13) {
						moveMouse(random(50, 700), random(50, 450), 2, 2);
						setCameraRotation(random(1, 360));
					}

				}
			}
		}
		return true;
	}

	private void openRandomTab() {
		int randomNumber = random(1, 14);
		if (randomNumber <= 14) {
			if (randomNumber == 1) {
				openTab(TAB_STATS);
			}
			if (randomNumber == 2) {
				openTab(TAB_ATTACK);
			}
			if (randomNumber == 3) {
				openTab(TAB_EQUIPMENT);
			}
			if (randomNumber == 4) {
				openTab(TAB_FRIENDS);
			}
			if (randomNumber == 6) {
				openTab(TAB_MAGIC);
			}
			if (randomNumber == 7) {
				openTab(TAB_NOTES);
			}
		}
	}

	// Credits to Garrett
	@Override
	public void onRepaint(Graphics g) {
		long runTime = 0;
		long seconds = 0;
		long minutes = 0;
		long hours = 0;
		int pickpocketsHour = 0;
		int currentXP = 0;
		int currentLVL = 0;
		int gainedXP = 0;
		int gainedLVL = 0;
		int xpPerHour = 0;
		runTime = System.currentTimeMillis() - startTime;
		seconds = runTime / 1000;
		if (seconds >= 60) {
			minutes = seconds / 60;
			seconds -= (minutes * 60);
		}
		if (minutes >= 60) {
			hours = minutes / 60;
			minutes -= (hours * 60);
		}

		currentXP = skills.getCurrentSkillExp(Skills.getStatIndex("thieving"));
		currentLVL = skills.getCurrentSkillLevel(Skills
				.getStatIndex("thieving"));
		gainedXP = currentXP - startXP;
		gainedLVL = currentLVL - startLvl;
		xpPerHour = (int) ((3600000.0 / (double) runTime) * gainedXP);
		pickpocketsHour = (int) ((3600000.0 / (double) runTime) * pickpockets);

		if (getCurrentTab() == TAB_INVENTORY) {
			if (showInventory == false) {
				g.setColor(new Color(0, 0, 0, 190));
				g.fillRoundRect(555, 210, 175, 250, 0, 0);

				g.setColor(Color.RED);
				g.draw3DRect(555, 210, 175, 250, true);

				g.setColor(Color.WHITE);
				int[] coords = new int[] { 225, 240, 255, 270, 285, 300, 315,
						330, 345, 360, 375, 390, 405, 420, 435, 450, 465, 480 };
				g.setColor(Color.RED);
				g.setFont(new Font("Segoe Print", Font.BOLD, 14));
				g.drawString("Master Farmer Stealer", 561, coords[0]);
				g
						.drawString("Version: " + properties.version(), 561,
								coords[1]);
				g.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 12));
				g.setColor(Color.LIGHT_GRAY);
				g.drawString("Run Time: " + hours + ":" + minutes + ":"
						+ seconds, 561, coords[2]);
				g.setColor(Color.RED);
				g.drawString(pickpockets + " pickpockets", 561, coords[4]);
				g.setColor(Color.LIGHT_GRAY);
				g.drawString("pickpockets/hour: " + pickpocketsHour, 561,
						coords[5]);
				g.setColor(Color.RED);
				g.drawString("XP Gained: " + gainedXP, 561, coords[6]);
				g.setColor(Color.LIGHT_GRAY);
				g.drawString("XP/Hour: " + xpPerHour, 561, coords[7]);
				g.setColor(Color.RED);
				g.drawString("Your level is " + currentLVL, 561, coords[8]);
				g.setColor(Color.LIGHT_GRAY);
				g.drawString("Lvls Gained: " + gainedLVL, 561, coords[9]);
				g.setColor(Color.RED);
				g.drawString("failed " + failPickpocket + " times", 561,
						coords[10]);
				g.drawString("XP To Next Level: "
						+ skills.getXPToNextLevel(Skills
								.getStatIndex("thieving")), 561, coords[12]);
				g.setColor(Color.LIGHT_GRAY);
				g.drawString("% To Next Level: "
						+ skills.getPercentToNextLevel(Skills
								.getStatIndex("thieving")), 561, coords[13]);
				g.setColor(Color.RED);
				g.drawString("Status: " + status, 561, coords[14]);
				g.setColor(Color.LIGHT_GRAY);
				g.drawString("By Bloddyharry", 561, coords[15]);
			}
			g.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 12));
			g.setColor(new Color(0, 0, 0, 195));
			g.fillRoundRect(6, 315, 120, 20, 0, 0);
			g.setColor(Color.red);
			g.draw3DRect(6, 315, 120, 20, true);
			g.setColor(Color.white);
			g.drawString("See inventory", 10, 330);

			Mouse m = Bot.getClient().getMouse();
			if (m.x >= 6 && m.x < 6 + 120 && m.y >= 315 && m.y < 315 + 30) {
				showInventory = true;
			} else {
				showInventory = false;
			}
		}
		if (hours == 5 && minutes == 0 && seconds == 0) {
			log("w00t! ran for 5 hour! taking screenie :)");
			ScreenshotUtil.takeScreenshot(true);
		}
		if (hours == 6 && minutes == 0 && seconds == 0) {
			log("awesome! ran for 6 hours! taking screenie :)");
			ScreenshotUtil.takeScreenshot(true);
		}
		if (hours == 7 && minutes == 0 && seconds == 0) {
			log("Epic! ran for 7 hours! taking screenie :)");
			ScreenshotUtil.takeScreenshot(true);
		}
		if (hours == 8 && minutes == 0 && seconds == 0) {
			log("Hell yeaH! ran for 8 hours! taking screenie :)");
			ScreenshotUtil.takeScreenshot(true);
		}
		if (hours == wantedHours && minutes == wantedMinutes
				&& seconds == wantedSeconds && finishAt) {
			logOut = true;
			if (canSay) {
				log(wantedHours + " hours " + wantedMinutes + " minutes "
						+ wantedSeconds + " seconds past, stopping script.");
				canSay = false;
			}
		}
		if (normal != null) {
			final Mouse mouse = Bot.getClient().getMouse();
			final int mouse_x = mouse.getMouseX();
			final int mouse_y = mouse.getMouseY();
			final int mouse_x2 = mouse.getMousePressX();
			final int mouse_y2 = mouse.getMousePressY();
			final long mpt = System.currentTimeMillis()
					- mouse.getMousePressTime();
			if (mouse.getMousePressTime() == -1 || mpt >= 1000) {
				g.drawImage(normal, mouse_x - 8, mouse_y - 8, null);
			}
			if (mpt < 1000) {
				g.drawImage(clicked, mouse_x2 - 8, mouse_y2 - 8, null);
				g.drawImage(normal, mouse_x - 8, mouse_y - 8, null);
			}
		}
	}

	public int getGloves() {
		if (!atBank()) {
			status = "Walking to Bank";
			if (getEnergy() == random(60, 100)) {
				setRun(true);
			}
			if (distanceTo(getDestination()) < random(5, 12)
					|| distanceTo(getDestination()) > 40) {
				if (!walkPathMM(farmerToBank)) {
					walkToClosestTile(randomizePath(farmerToBank, 2, 2));
					return random(1400, 1600);
				}
			}
		} else if (atBank()) {
			openBank();
			bank2();
		}
		return 0;
	}

	private int bank2() {
		status = "Banking";
		if (bank.isOpen()) {
			bank.depositAll();
			wait(random(700, 1200));
			bank.atItem(FOODID, "Withdraw-10");
			wait(random(500, 700));
			bank.atItem(glovesID, "Withdraw-1");
			wait(random(1000, 1200));
			bank.close();
			wait(random(200, 500));
			atInventoryItem(glovesID, "Wear");
			wait(random(500, 800));
			if (inventoryContainsOneOf(glovesID)) {
				openBank();
				bank.deposit(glovesID, somethingID);
				wait(random(500, 800));
			}
		}
		return 0;
	}

	@Override
	public void serverMessageRecieved(ServerMessageEvent e) {
		final String serverString = e.getMessage();
		if (serverString.contains("You pick the")) {
			pickpockets++;
		}
		if (serverString.contains("You can't do that")) {
			walkTileMM(bankTile);
			wait(random(1500, 2000));
		}
		if (serverString.contains("You fail")) {
			failPickpocket++;
		}
		if (serverString.contains("You eat")) {
			foodEated++;
		}
		if (serverString.contains("Your gloves are about to fall")) {
			getGloves();
		}
		if (serverString.contains("You've just advanced")) {
			log("Congrats on level up, Screenshot taken!");
			ScreenshotUtil.takeScreenshot(true);
			wait(random(1500, 2500));
			if (canContinue()) {
				clickContinue();
			}
			gainedLvls++;
			if (skills.getCurrentSkillLevel(Constants.STAT_THIEVING) == wantedLevel
					&& finishWhenLevel) {
				logOut = true;
				if (canSay) {
					log("Reached level "
							+ skills
									.getCurrentSkillLevel(Constants.STAT_THIEVING)
							+ " in thieving, stopping script");
					canSay = false;
				}
			}
		}

	}

	public class BloddyMasterFarmerGUI extends JFrame {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public BloddyMasterFarmerGUI() {
			initComponents();
		}

		private void button2ActionPerformed(ActionEvent e) {
			guiWait = false;
			guiExit = true;
			dispose();
		}

		private void button1ActionPerformed(ActionEvent e) {
			FOODID = Integer.parseInt(textField1.getText());
			finishAt = checkBox3.isSelected();
			wantedHours = Integer.parseInt(textField3.getText());
			wantedMinutes = Integer.parseInt(textField4.getText());
			wantedSeconds = Integer.parseInt(textField5.getText());
			finishWhenLevel = checkBox1.isSelected();
			wantedLevel = Integer.parseInt(textField2.getText());
			guiWait = false;
			dispose();
		}

		private void checkBox2ActionPerformed(ActionEvent e) {
			if (checkBox2.isSelected()) {
				doDropSeeds = true;
			} else {
				doDropSeeds = false;
			}
		}

		private void checkBox1ActionPerformed(ActionEvent e) {
			if (checkBox1.isSelected()) {
				textField2.setEnabled(true);
			} else {
				textField2.setEnabled(false);
			}
		}

		private void checkBox3ActionPerformed(ActionEvent e) {
			if (checkBox3.isSelected()) {
				label7.setEnabled(true);
				label8.setEnabled(true);
				label9.setEnabled(true);
				textField3.setEnabled(true);
				textField4.setEnabled(true);
				textField5.setEnabled(true);
			} else {
				label7.setEnabled(false);
				label8.setEnabled(false);
				label9.setEnabled(false);
				textField3.setEnabled(false);
				textField4.setEnabled(false);
				textField5.setEnabled(false);
			}
		}

		private void comboBox1ActionPerformed(ActionEvent e) {
			if (comboBox1.getSelectedItem() == "Withdraw-10") {
				withdraw10 = true;
			}
		}

		private void initComponents() {
			// GEN-BEGIN:initComponents
			label1 = new JLabel();
			button2 = new JButton();
			button1 = new JButton();
			tabbedPane1 = new JTabbedPane();
			panel1 = new JPanel();
			label2 = new JLabel();
			label5 = new JLabel();
			label6 = new JLabel();
			checkBox2 = new JCheckBox();
			label4 = new JLabel();
			textField1 = new JTextField();
			label3 = new JLabel();
			label10 = new JLabel();
			comboBox1 = new JComboBox();
			panel2 = new JPanel();
			checkBox1 = new JCheckBox();
			textField2 = new JTextField();
			checkBox3 = new JCheckBox();
			label7 = new JLabel();
			label8 = new JLabel();
			label9 = new JLabel();
			textField3 = new JTextField();
			textField4 = new JTextField();
			textField5 = new JTextField();

			// ======== this ========
			setResizable(false);
			setTitle("Bloddy Master Farmer Stealer GUI");
			Container contentPane = getContentPane();
			contentPane.setLayout(null);

			// ---- label1 ----
			label1.setText("Bloddy Master Farmer Stealer "
					+ properties.version());
			label1.setFont(new Font("Lucida Calligraphy", Font.BOLD, 13));
			label1.setForeground(Color.red);
			contentPane.add(label1);
			label1.setBounds(new Rectangle(new Point(5, 10), label1
					.getPreferredSize()));

			// ---- button2 ----
			button2.setText("Cancel");
			button2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					button2ActionPerformed(e);
				}
			});
			contentPane.add(button2);
			button2.setBounds(new Rectangle(new Point(140, 210), button2
					.getPreferredSize()));

			// ---- button1 ----
			button1.setText("Start");
			button1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					button1ActionPerformed(e);
					button1ActionPerformed(e);
				}
			});
			contentPane.add(button1);
			button1.setBounds(210, 210, 58, button1.getPreferredSize().height);

			// ======== tabbedPane1 ========
			{

				// ======== panel1 ========
				{
					panel1.setLayout(null);

					// ---- label2 ----
					label2
							.setText("Start in the draynor bank with all your seeds \n");
					label2.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
					panel1.add(label2);
					label2.setBounds(new Rectangle(new Point(5, 5), label2
							.getPreferredSize()));

					// ---- label5 ----
					label5.setText("and food in one tab.");
					label5.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
					panel1.add(label5);
					label5.setBounds(new Rectangle(new Point(5, 25), label5
							.getPreferredSize()));

					// ---- label6 ----
					label6
							.setText("Also make sure you fill in the right food ID.");
					label6.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
					panel1.add(label6);
					label6.setBounds(new Rectangle(new Point(5, 45), label6
							.getPreferredSize()));

					// ---- checkBox2 ----
					checkBox2.setText("Drop junkseeds");
					checkBox2
							.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
					checkBox2.setSelected(true);
					checkBox2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							checkBox2ActionPerformed(e);
						}
					});
					panel1.add(checkBox2);
					checkBox2.setBounds(new Rectangle(new Point(3, 60),
							checkBox2.getPreferredSize()));

					// ---- label4 ----
					label4.setText("Food ID*:");
					label4.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
					panel1.add(label4);
					label4.setBounds(5, 100, 265, 18);

					// ---- textField1 ----
					textField1.setText("7946");
					panel1.add(textField1);
					textField1.setBounds(140, 100, 90, textField1
							.getPreferredSize().height);

					// ---- label3 ----
					label3.setText("*In RSBot client go to: view > inventory");
					label3.setFont(label3.getFont().deriveFont(
							label3.getFont().getStyle() | Font.ITALIC,
							label3.getFont().getSize() - 2f));
					panel1.add(label3);
					label3.setBounds(new Rectangle(new Point(10, 125), label3
							.getPreferredSize()));

					// ---- label10 ----
					label10.setText("Withdraw food:");
					label10.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
					panel1.add(label10);
					label10.setBounds(new Rectangle(new Point(4, 85), label10
							.getPreferredSize()));

					// ---- comboBox1 ----
					comboBox1.setModel(new DefaultComboBoxModel(new String[] {
							"Withdraw-5", "Withdraw-10" }));
					comboBox1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							comboBox1ActionPerformed(e);
						}
					});
					panel1.add(comboBox1);
					comboBox1.setBounds(140, 80, 90, comboBox1
							.getPreferredSize().height);

					{ // compute preferred size
						Dimension preferredSize = new Dimension();
						for (int i = 0; i < panel1.getComponentCount(); i++) {
							Rectangle bounds = panel1.getComponent(i)
									.getBounds();
							preferredSize.width = Math.max(bounds.x
									+ bounds.width, preferredSize.width);
							preferredSize.height = Math.max(bounds.y
									+ bounds.height, preferredSize.height);
						}
						Insets insets = panel1.getInsets();
						preferredSize.width += insets.right;
						preferredSize.height += insets.bottom;
						panel1.setMinimumSize(preferredSize);
						panel1.setPreferredSize(preferredSize);
					}
				}
				tabbedPane1.addTab("Main", panel1);

				// ======== panel2 ========
				{
					panel2.setLayout(null);

					// ---- checkBox1 ----
					checkBox1.setText("When reached level:");
					checkBox1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							checkBox1ActionPerformed(e);
						}
					});
					panel2.add(checkBox1);
					checkBox1.setBounds(new Rectangle(new Point(5, 10),
							checkBox1.getPreferredSize()));

					// ---- textField2 ----
					textField2.setText("0");
					textField2.setEnabled(false);
					panel2.add(textField2);
					textField2.setBounds(140, 12, 40, textField2
							.getPreferredSize().height);

					// ---- checkBox3 ----
					checkBox3.setText("After:");
					checkBox3.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							checkBox3ActionPerformed(e);
						}
					});
					panel2.add(checkBox3);
					checkBox3.setBounds(new Rectangle(new Point(5, 45),
							checkBox3.getPreferredSize()));

					// ---- label7 ----
					label7.setText("H:");
					label7.setEnabled(false);
					panel2.add(label7);
					label7.setBounds(new Rectangle(new Point(30, 75), label7
							.getPreferredSize()));

					// ---- label8 ----
					label8.setText("M:");
					label8.setEnabled(false);
					panel2.add(label8);
					label8.setBounds(new Rectangle(new Point(95, 75), label8
							.getPreferredSize()));

					// ---- label9 ----
					label9.setText("S:");
					label9.setEnabled(false);
					panel2.add(label9);
					label9.setBounds(new Rectangle(new Point(165, 75), label9
							.getPreferredSize()));

					// ---- textField3 ----
					textField3.setEnabled(false);
					textField3.setText("0");
					panel2.add(textField3);
					textField3.setBounds(50, 74, 30, textField3
							.getPreferredSize().height);

					// ---- textField4 ----
					textField4.setEnabled(false);
					textField4.setText("0");
					panel2.add(textField4);
					textField4.setBounds(115, 74, 30, 20);

					// ---- textField5 ----
					textField5.setEnabled(false);
					textField5.setText("0");
					panel2.add(textField5);
					textField5.setBounds(185, 74, 30, 20);

					{ // compute preferred size
						Dimension preferredSize = new Dimension();
						for (int i = 0; i < panel2.getComponentCount(); i++) {
							Rectangle bounds = panel2.getComponent(i)
									.getBounds();
							preferredSize.width = Math.max(bounds.x
									+ bounds.width, preferredSize.width);
							preferredSize.height = Math.max(bounds.y
									+ bounds.height, preferredSize.height);
						}
						Insets insets = panel2.getInsets();
						preferredSize.width += insets.right;
						preferredSize.height += insets.bottom;
						panel2.setMinimumSize(preferredSize);
						panel2.setPreferredSize(preferredSize);
					}
				}
				tabbedPane1.addTab("On Finish", panel2);

			}
			contentPane.add(tabbedPane1);
			tabbedPane1.setBounds(5, 35, 265, 170);

			contentPane.setPreferredSize(new Dimension(290, 270));
			setSize(290, 270);
			setLocationRelativeTo(getOwner());
			// GEN-END:initComponents
		}

		// GEN-BEGIN:variables
		private JLabel label1;
		private JButton button2;
		private JButton button1;
		private JTabbedPane tabbedPane1;
		private JPanel panel1;
		private JLabel label2;
		private JLabel label5;
		private JLabel label6;
		private JCheckBox checkBox2;
		private JLabel label4;
		private JTextField textField1;
		private JLabel label3;
		private JPanel panel2;
		private JLabel label10;
		private JComboBox comboBox1;
		private JCheckBox checkBox1;
		private JTextField textField2;
		private JCheckBox checkBox3;
		private JLabel label7;
		private JLabel label8;
		private JLabel label9;
		private JTextField textField3;
		private JTextField textField4;
		private JTextField textField5;
		// GEN-END:variables
	}
}