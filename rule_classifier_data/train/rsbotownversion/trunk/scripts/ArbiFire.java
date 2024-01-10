import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.util.*;
import java.io.*;

import org.rsbot.bot.Bot;
import org.rsbot.script.*;
import org.rsbot.script.wrappers.*;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.util.ScreenshotUtil;

//TRIGOON
import org.rsbot.bot.input.Mouse;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.net.URL;
import java.io.IOException;

// TRIGOON

@ScriptManifest(authors = { "Arbiter, Trigoon, John" }, category = "Firemaking", name = "ArbiFIRE [Universal FMer]", version = 1.35, description = "<html><head>"
		+ "<style type=\"text/css\"> body {background-color: #E56717; color: #e9d5af; padding: 5px; font-family: Century Gothic; text-align: center;}"
		+ "h1 {font-weight: bold; color: #e9d5af; font-size: 14px; padding: 0px; margin: 0px; margin-top: 4px; }"
		+ "h2 {font-weight: bold; color: #E56717; padding: 0px; margin: 0px; margin-top: 1px; margin-bottom: 5px; font-weight: normal; font-size: 10px;}"
		+ "td {font-weight: bold; width: 50%;}</style>"
		+ "</head><body>"
		+ "<div style=\"width: 100%, height: 80px; background-color: #e9d5af; text-align: center; padding: 5px;\">"
		+ "<h1><img src='http://img256.imageshack.us/img256/5065/fireh.png' alt='ArbiFire' /> </h1>"
		+ "<h2><b>By Arbiter</b></h2></div><br />"
		+ "<center><b><FONT COLOR='#800517'>WARNING: I AM SMARTER THAN YOU.</FONT></b><br />"
		+ "Revolutionizing Firemaking as you humans know it, I am the first of my kind to <b>THINK</b> and <b>LEARN</b>. I run anywhere. I learn my environment. I find the longest strings of available FMing spots and even randomize them for you. If I ever find a tile I can't FM on I automatically learn it and save it to my memory. I never make the same mistake twice, unlike you stupid humans. The more you run me, the more I learn... And remember: I report to Arbiter. "
		+ "<br /><br />Note: All settings in GUI."
		+ "</center>"
		+ "</body></html>")
public class ArbiFire extends Script implements PaintListener,
		ServerMessageListener {

	@SuppressWarnings("unused")
	private final ScriptManifest properties = getClass().getAnnotation(
			ScriptManifest.class);

	static int speed;
	static int realSpeed;

	protected int getMouseSpeed() {
		return speed;
	}

	public boolean boxFirst;
	public boolean walked;
	public boolean forceWalk;
	public boolean manualAvoid;
	public boolean burning;
	public boolean start = true;
	public boolean useChests;
	public boolean useDen;

	public RSTile[] spots;
	public RSTile[] badTiles = new RSTile[10000];

	public RSTile dest;
	public RSTile bankTile;

	public double[] scores;

	public int log;
	public int tinderbox = 590;
	public int randomUse = random(25, 75);
	public int randomPath;
	static int variable;
	public int bad;
	public int badTries;
	public int trainingSkill = Constants.STAT_FIREMAKING;
	public int[] chests = { 27663, 4483 };

	// TRIGOON
	public boolean show;
	public boolean NEW = true;
	public boolean fullTurn = true;

	public long expGained;
	public long lastSecond;
	public long currentExp;
	public long startExp;
	public long startLevel;
	public long st = System.currentTimeMillis();

	public int logsBurned;
	public int expHour;
	public int logsHour = 0;

	public double red = 194;
	public double green = 178;
	public double blue = 146;

	public BufferedImage fmPic = null;
	// TRIGOON

	// John
	public String logToBurn;

	public boolean guiStart;
	public boolean guiWait;
	public boolean worked;
	// John

	private FreeMemory fm = new FreeMemory();
	private Thread memory = new Thread(fm);

	ArbiFireAntiBan antiban;
	Thread t;

	public boolean onStart(Map<String, String> args) {
		// TRIGOON
		try {
			final URL url = new URL("http://wildimp.com/aiofm.png");
			fmPic = ImageIO.read(url);
		} catch (final IOException e) {
			log("Failed to get FM Pic.");
			e.printStackTrace();
		}
		// TRIGOON

		// John
		final ArbiFireGUI gui = new ArbiFireGUI();
		gui.setVisible(true);
		while (!guiStart) {
			wait(10);
		}
		if (!worked)
			return false;
		// John
		// log("" + speed);
		// log("" + variable);
		st = System.currentTimeMillis();
		antiban = new ArbiFireAntiBan();
		t = new Thread(antiban);
		memory.start();
		return true;
	}

	public int loop() {
		try {
			if (!isLoggedIn())
				return 100;
			if (start) {
				getBadTiles();
				// log("Make sure you started me right next to a bank for best speeds.");
				// log("Calculating the best spots... I won't take more than 30 seconds.");
				if (getNearestNPCByName("Banker") == null
						&& getNearestObjectByID(chests) == null
						&& getNearestNPCByName("Emerald Benedict") == null) {
					log("You failed. Start me logged in next to a bank/chest.");
					log("If you're at a chest and its not recognizing please PM the object ID to Arbiter.");
					stopScript(false);
				}
				if (bankTile == null && getNearestNPCByName("Banker") != null) {
					bankTile = getNearestNPCByName("Banker").getLocation();
				}
				if (bankTile == null
						&& getNearestNPCByName("Emerald Benedict") != null) {
					useDen = true;
					bankTile = getNearestNPCByName("Emerald Benedict")
							.getLocation();
				}
				if (bankTile == null && getNearestObjectByID(chests) != null) {
					useChests = true;
					bankTile = getNearestObjectByID(chests).getLocation();
				}
				if (!inventoryContains(tinderbox)) {
					log("You failed. Start with a tinderbox in inventory.");
					stopScript(false);
				}
				startExp = (long) skills.getCurrentSkillExp(trainingSkill);
				startLevel = (long) skills.getCurrentSkillLevel(trainingSkill);
				spots = findStart(27);
				dest = spots[0];
				realSpeed = speed;
				show = true;
				start = false;
			}
			speed = random(realSpeed - 1, realSpeed + 1);
			getMouseSpeed();
			antiBan();
			restoreRun();
			if (getMyPlayer().isInCombat()) {
				walkTileMM(getClosestTileOnMap(bankTile), 3, 3);
				wait(random(1000, 2000));
				return 100;
			}

			switch (getState()) {
			case burn:
				burning = true;
				badTries = 0;
				RSTile lastSpot = getMyPlayer().getLocation();
				/*
				 * if (!getMenuActions().get(0).contains("se") &&
				 * isItemSelected()) { int tab = random(0,16); if (tab == 4)
				 * openTab(1); else openTab(tab); return 100; }
				 */
				/*
				 * if (getMouseLocation().getX() > 550 &&
				 * getMouseLocation().getY() > 200 && isItemSelected()) {
				 * clickMouse(true); wait(random(50,100)); } else
				 */
				if (isItemSelected()) {
					if (boxFirst) {
						if (getMenuActions().get(0).contains("se"))
							clickMouse(true);
						else
							atInventoryItem(log, "");
						logsBurned++;
					} else {
						if (getMenuActions().get(0).contains("se"))
							clickMouse(true);
						else
							atInventoryItem(tinderbox, "");
						logsBurned++;
					}
					for (int i = 0; i < 100; i++) {
						if (!isItemSelected())
							break;
						wait(random(5, 15));
					}
					// wait(random(50,100));
				} else if (random(1, 100) >= randomUse) {
					atInventoryItem(tinderbox, "");
					boxFirst = true;
					for (int i = 0; i < 100; i++) {
						if (isItemSelected())
							break;
						wait(random(5, 15));
					}
					atInventoryItem(log, "");
					logsBurned++;
				} else {
					atInventoryItem(log, "");
					boxFirst = false;
					for (int i = 0; i < 100; i++) {
						if (isItemSelected())
							break;
						wait(random(5, 15));
					}
					atInventoryItem(tinderbox, "");
					logsBurned++;
				}
				int lastCount = getInventoryCount(log);
				boolean attempt = true;
				for (int i = 0; i < 200; i++) {
					if (getDistance2(lastSpot) > 0)
						break;
					if (attempt
							&& !isItemSelected()
							&& inventoryContains(log)
							&& getInventoryCount(log) < lastCount
							&& getObjectAt(
									getMyPlayer().getLocation().getX() - 1,
									getMyPlayer().getLocation().getY()) == null) {
						if (random(1, 100) >= randomUse) {
							atInventoryItem(tinderbox, "");
							boxFirst = true;
							for (int j = 0; j < 100; j++) {
								if (isItemSelected())
									break;
								wait(random(5, 15));
							}
							moveToItem(log, "");
							/*
							 * for (int j = 0; j < 100; j++) { if
							 * (getMenuActions().get(0).contains("se")) break;
							 * wait(random(1,20)); }
							 */
						} else {
							atInventoryItem(log, "");
							boxFirst = false;
							for (int j = 0; j < 100; j++) {
								if (isItemSelected())
									break;
								wait(random(5, 15));
							}
							moveToItem(tinderbox, "");
							/*
							 * for (int j = 0; j < 100; j++) { if
							 * (getMenuActions().get(0).contains("se")) break;
							 * wait(random(1,20)); }
							 */
						}
						attempt = false;
						// log(getMenuActions().get(0));
					}
					// if (getMyPlayer().getAnimation() != -1)
					// break;
					// if (getMyPlayer().isMoving())
					// break;
					wait(random(10, 20));
				}
				burning = false;
				return 100;

			case walk:
				if (getDistance2(bankTile) > 50) {
					walkTileMM(getClosestTileOnMap(bankTile), 3, 3);
					wait(random(1000, 2000));
					dest = spots[random(0, variable - 1)];
					return 100;
				}
				walked = true;
				forceWalk = false;
				// dest = spots[randomPath];
				/*
				 * if (getObjectAt(getMyPlayer().getLocation()) != null ||
				 * getObjectAt(getMyPlayer().getLocation().getX() - 1,
				 * getMyPlayer().getLocation().getY()) != null) { if
				 * (getMyPlayer().isMoving()) return 100; for (int i = 0; i <
				 * 1000; i++) { int newRandom = random(1, variable + 1);
				 * newRandom--; log(""+newRandom); if (randomPath != newRandom)
				 * { walked = false; randomPath = newRandom; dest =
				 * spots[randomPath]; break; } } }
				 */
				// log(dest.getX() + ", " + dest.getY());
				if (getMyPlayer().getLocation().equals(dest))
					return 100;
				if (!tileOnScreen(dest)) {
					if (walkTileMM(getClosestTileOnMap(dest), 0, 0)) {
						RSTile before = getMyPlayer().getLocation();
						if (waitToMove(random(1500, 2000))) {
							// wait(random(1000,1500));
							while (getMyPlayer().isMoving())
								wait(random(5, 10));
						} else {
							if (getDistance2(before) < 1
									&& !getMyPlayer().isMoving()) {
								badTries++;
								if (badTries > random(3, 4))
									manualAvoid = true;
							}
							if (badTries == 3) {
								walkTileOnScreen(dest);
								return 100;
							}
						}
					}
				} else {
					for (int i = 0; i < 100; i++) {
						if (!getMyPlayer().isMoving()) {
							wait(random(100, 200));
							break;
						}
						wait(random(50, 100));
					}
					if (getMyPlayer().getLocation().equals(dest))
						return 100;
					atTile(dest, "Walk here");
					// walkTileOnScreen(dest);
					RSTile before = getMyPlayer().getLocation();
					if (waitToMove(random(1500, 2000))) {
						// wait(random(1000,1500));
						while (getMyPlayer().isMoving())
							wait(random(5, 10));
					} else {
						if (getDistance2(before) < 1
								&& !getMyPlayer().isMoving()) {
							badTries++;
							if (badTries > random(3, 4))
								manualAvoid = true;
						}
					}
				}
				if (manualAvoid) {
					badTries = 0;
					manualAvoid = false;
					if (getMyPlayer().isMoving())
						return 100;
					forceWalk = true;
					badTiles[bad] = dest;
					int badX = badTiles[bad].getX();
					int badY = badTiles[bad].getY();
					bad++;
					try {
						BufferedWriter out = new BufferedWriter(new FileWriter(
								"badTiles.txt", true));
						out.write("" + badX);
						out.newLine();
						out.write("" + badY);
						out.newLine();
						out.close();
					} catch (IOException e) {
					}

					log("I just added badTile [" + badX + "," + badY
							+ "] to my memory.");
					log("I'll never make that mistake again!");
				}
				return 100;

			case bank:
				// dest = spots[random(0, variable - 1)];
				if (getDistance2(bankTile) > 5) {
					walkTileMM(getClosestTileOnMap(bankTile), 0, 0);
					if (waitToMove(random(1000, 1500))) {
						for (int i = 0; i < 100; i++) {
							if (getDestination() == null
									|| distanceBetween(getDestination(),
											bankTile) > 3)
								break;
							if (!getMyPlayer().isMoving()) {
								wait(random(50, 100));
								break;
							}
							wait(random(50, 150));
						}
					} else {
						walkTo2(bankTile);
						if (waitToMove(random(1000, 1500))) {
							for (int i = 0; i < 100; i++) {
								if (getDestination() == null
										|| distanceBetween(getDestination(),
												bankTile) > 3)
									break;
								if (!getMyPlayer().isMoving()) {
									wait(random(50, 100));
									break;
								}
								wait(random(50, 150));
							}
						}
					}
					for (int i = 0; i < 5; i++) {
						if (!getMyPlayer().isMoving()) {
							wait(random(50, 100));
							break;
						}
						if (tileOnScreen(bankTile))
							break;
						if (i == 4)
							walkTo2(bankTile);
						wait(random(250, 750));
					}
					return 100;
				}
				speed = random(9, 10);
				getMouseSpeed();
				if (bank.isOpen()
						&& RSInterface.getInterface(762).getChild(9)
								.getAbsoluteY() > 50) {
					for (int i = 0; i < 100; i++) {
						if (bank.getCount(log) + getInventoryCount(log) > 26)
							break;
						if (i == 99
								&& (bank.getCount(log) + getInventoryCount(log) < 27)) {
							log("FEED ME MOAR!");
							stopScript();
						}
						wait(random(20, 30));
					}
					if (getInventoryCount() > 1) {
						bank.depositAllExcept(tinderbox);
						return 100;
					}
					if (!inventoryContains(tinderbox)) {
						bank.atItem(tinderbox, "Withdraw-1");
						for (int i = 0; i < 100; i++) {
							if (inventoryContains(tinderbox))
								break;
							wait(random(30, 50));
						}
						return 100;
					}
					if (getInventoryCount() != 28) {
						if (!bank.atItem(log, "Withdraw-All")) {
							return 100;
						} else {
							for (int i = 0; i < 1000; i++) {
								int newRandom = random(1, variable + 1);
								newRandom--;
								if (randomPath != newRandom) {
									spots = findStart(27);
									walked = false;
									randomPath = newRandom;
									dest = spots[randomPath];
									break;
								}
							}
						}
					}
					for (int i = 0; i < 75; i++) {
						if (inventoryContains(log))
							break;
						wait(random(20, 30));
					}
					if (getInventoryCount() == 28) {
						bank.close();
						for (int i = 0; i < 75; i++) {
							if (!bank.isOpen()
									|| RSInterface.getInterface(762)
											.getChild(9).getAbsoluteY() < 50)
								break;
							wait(random(20, 30));
						}
					}
				}
				if (getInventoryCount() != 28
						&& ((!bank.isOpen() || RSInterface.getInterface(762)
								.getChild(9).getAbsoluteY() < 50) || RSInterface
								.getInterface(762).getChild(9).getAbsoluteY() < 50)) {
					if (useChests) {
						RSObject bankChest = getNearestObjectByID(chests);
						if (!tileOnScreen(bankChest.getLocation())) {
							walkTo2(bankChest.getLocation());
							return 100;
						}
						if (atObject(bankChest, "Bank Bank")) {
							for (int i = 0; i < 200; i++) {
								if (bank.isOpen()
										&& RSInterface.getInterface(762)
												.getChild(9).getAbsoluteY() > 50)
									break;
								wait(random(20, 30));
							}
							return 100;
						}
					} else if (useDen) {
						RSNPC bankPerson = getNearestNPCByName("Emerald Benedict");
						if (!tileOnScreen(bankPerson.getLocation())) {
							walkTo2(bankPerson.getLocation());
							return 100;
						}
						if (atNPC(bankPerson, "Bank Emerald")) {
							for (int i = 0; i < 200; i++) {
								if (bank.isOpen()
										&& RSInterface.getInterface(762)
												.getChild(9).getAbsoluteY() > 50)
									break;
								wait(random(20, 30));
							}
							return 100;
						}
					} else {
						RSNPC bankPerson = getNearestNPCByName("Banker");
						if (!tileOnScreen(bankPerson.getLocation())) {
							walkTo2(bankPerson.getLocation());
							return 100;
						}
						if (atNPC(bankPerson, "Bank Banker")) {
							for (int i = 0; i < 200; i++) {
								if (bank.isOpen()
										&& RSInterface.getInterface(762)
												.getChild(9).getAbsoluteY() > 50)
									break;
								wait(random(20, 30));
							}
							return 100;
						}
					}
					return 100;
				}
				return 100;

			case sleep:
				burning = true;
				wait(random(1, 50));
				burning = false;
				return 100;
			}
		} catch (final Exception e) {
		}
		return 100;
	}

	private enum State {
		walk, burn, bank, sleep;
	}

	private State getState() {
		if (getMyPlayer().getAnimation() != -1) {
			return State.sleep;
		} else if (inventoryContains(log) && inventoryContains(tinderbox)) {
			if (getObjectAt(getMyPlayer().getLocation()) != null) {
				spots = findStart(getInventoryCount(log));
				for (int i = 0; i < 1000; i++) {
					int newRandom = random(1, variable + 1);
					newRandom--;
					if (randomPath != newRandom) {
						walked = false;
						randomPath = newRandom;
						dest = spots[randomPath];
						break;
					}
				}
				return State.walk;
			} else if (forceWalk) {
				if (getDistance2(bankTile) > 10) {
					walkTileMM(getClosestTileOnMap(bankTile), 3, 3);
					wait(random(1000, 2000));
					return State.sleep;
				} else {
					spots = findStart(getInventoryCount(log));
					for (int i = 0; i < 1000; i++) {
						int newRandom = random(1, variable + 1);
						newRandom--;
						if (randomPath != newRandom) {
							walked = false;
							randomPath = newRandom;
							dest = spots[randomPath];
							break;
						}
					}
					return State.walk;
				}
			} else if (getMyPlayer().getLocation().getY() != dest.getY()
					|| getMyPlayer().getLocation().getX() > dest.getX()) {
				return State.walk;
			} else if (!getMyPlayer().isMoving()) {
				return State.burn;
			} else {
				return State.sleep;
			}
		} else {
			return State.bank;
		}
	}

	public RSTile[] findStart(int count) {
		spots = new RSTile[variable];
		scores = new double[variable];
		RSTile spot;
		double score = 0;
		for (int x = 0; x < 104; x++) {
			for (int y = 0; y < 104; y++) {
				score = 0;
				spot = new RSTile(x + Bot.getClient().getBaseX(), y
						+ Bot.getClient().getBaseY());
				boolean badSpot = false;
				for (int a = 0; a < bad; a++) {
					if (spot.equals(badTiles[a])) {
						badSpot = true;
						break;
					}
				}
				if (!badSpot && getObjectAt(spot) == null) {
					for (int x2 = 0; x2 < 30; x2++) {
						RSTile tempTile = new RSTile(x
								+ Bot.getClient().getBaseX() - x2, y
								+ Bot.getClient().getBaseY());
						if (!tileOnMap(tempTile))
							break;
						if (getObjectAt(tempTile) == null)
							score++;
						else
							break;
						if (score >= count)
							break;
					}
					score = score - (getDistance3(spot) / 5.0);
					for (int i = 0; i < spots.length; i++) {
						if (score > scores[i] || scores[i] == 0) {
							boolean skip = false;
							for (int z = 0; z < spots.length; z++) {
								if (spots[z] != null
										&& spot.getY() == spots[z].getY()) {
									if (score > scores[z])
										spots[z] = spot;
									skip = true;
								}
							}
							if (skip)
								break;
							for (int j = scores.length - 2; j >= i; j--) {
								scores[j + 1] = scores[j];
							}
							for (int j = spots.length - 2; j >= i; j--) {
								spots[j + 1] = spots[j];
							}
							scores[i] = score;
							spots[i] = spot;
							break;
						}
					}
				}

			}
		}
		return spots;
	}

	public void restoreRun() {
		if (getEnergy() > random(50, 60) && !isRunning()) {
			setRun(true);
			wait(random(500, 1000));
		}
	}

	private void antiBan() {
		int random = random(1, 5);
		switch (random) {
		case 1:
			if (random(1, 25) != 1)
				return;
			moveMouse(random(10, 750), random(10, 495));
			return;
		case 2:
			if (random(1, 6) != 1)
				return;
			int angle = getCameraAngle() + random(-45, 45);
			if (angle < 0) {
				angle = random(0, 10);
			}
			if (angle > 359) {
				angle = random(0, 10);
			}
			char whichDir = 37; // left
			if (random(0, 100) < 50)
				whichDir = 39; // right
			Bot.getInputManager().pressKey(whichDir);
			wait(random(100, 500));
			Bot.getInputManager().releaseKey(whichDir);
			return;
		case 3:
			if (random(1, 15) != 1)
				return;
			moveMouseSlightly();
			return;
		default:
			return;
		}
	}

	public boolean moveToItem(int itemID, String option) {
		try {
			if (getCurrentTab() != TAB_INVENTORY
					&& !RSInterface.getInterface(INTERFACE_BANK).isValid()
					&& !RSInterface.getInterface(INTERFACE_STORE).isValid()) {
				openTab(TAB_INVENTORY);
			}

			RSInterfaceChild inventory = getInventoryInterface();
			if (inventory == null || inventory.getComponents() == null)
				return false;

			java.util.List<RSInterfaceComponent> possible = new ArrayList<RSInterfaceComponent>();
			for (RSInterfaceComponent item : inventory.getComponents()) {
				if (item != null && item.getComponentID() == itemID) {
					possible.add(item);
				}
			}

			if (possible.size() == 0)
				return false;

			RSInterfaceComponent item = possible.get(random(0, Math.min(2,
					possible.size())));
			if (!item.isValid())
				return false;
			Rectangle pos = item.getArea();
			if (pos.x == -1 || pos.y == -1 || pos.width == -1
					|| pos.height == -1)
				return false;
			int dx = (int) (pos.getWidth() - 4) / 2;
			int dy = (int) (pos.getHeight() - 4) / 2;
			int midx = (int) (pos.getMinX() + pos.getWidth() / 2);
			int midy = (int) (pos.getMinY() + pos.getHeight() / 2);
			moveMouse(midx + random(-dx, dx), midy + random(-dy, dy));
			return true;
		} catch (Exception e) {
			log("atInventoryItem(int itemID, String option) Error: " + e);
			return false;
		}
	}

	public boolean walkTo2(RSTile t) {
		return walkTo2(t, 2, 2);
	}

	public boolean walkTo2(RSTile t, int x, int y) {
		Point p = tileToMinimap(t);
		if (p.x == -1 || p.y == -1) {
			for (int i = 0; i < 5; i++) {
				RSTile[] temp = cleanPath(generateFixedPath(t));
				if (distanceTo(temp[temp.length - 1]) < 6)
					return true;
				RSTile next = nextTile(temp, 15);
				if (next != null && tileOnMap(next)) {
					if (walkTileMM(next, x, y))
						return true;
				} else {
					walkTileMM(nextTile(temp, 10), x, y);
				}
				if (i == 9 && !getMyPlayer().isMoving())
					walkTileOnScreen(nextTile(temp, 10));
				wait(random(200, 400));
			}
			return false;
		}
		clickMouse(p, x, y, true);
		return true;
	}

	public int getDistance2(RSTile t) {
		return (Math.abs(getMyPlayer().getLocation().getX() - t.getX()) + Math
				.abs(getMyPlayer().getLocation().getY() - t.getY()));
	}

	public int getDistance3(RSTile t) {
		return (Math.abs(bankTile.getX() - t.getX()) + Math.abs(bankTile.getY()
				- t.getY()));
	}

	public void getBadTiles() {
		try {
			File file = new File("badTiles.txt");
			@SuppressWarnings("unused")
			boolean create = file.createNewFile();

			BufferedReader br = new BufferedReader(new FileReader(
					"badTiles.txt"));
			String thisLine = br.readLine();
			while (thisLine != null) {
				int x = Integer.parseInt(thisLine);
				thisLine = br.readLine();
				if (thisLine == null) {
					log("Somehow you managed to corrupt my badTiles memory.");
					log("Now I have to delete my brain.");
					log("Restart me and I'll be smart again I promise. :(");
					try {
						if (br != null)
							br.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					@SuppressWarnings("unused")
					boolean destroy = file.delete();
					stopScript();
					return;
				}
				int y = Integer.parseInt(thisLine);
				badTiles[bad] = new RSTile(x, y);
				bad++;
				thisLine = br.readLine();
			}
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} catch (IOException e) {
			System.err.println("Error: " + e);
		}
	}

	public void serverMessageRecieved(final ServerMessageEvent arg0) {
		final String serverString = arg0.getMessage();
		if (serverString.contains("can't reach")) {
			manualAvoid = false;
			if (getMyPlayer().isMoving())
				return;
			forceWalk = true;
			badTiles[bad] = dest;
			int badX = badTiles[bad].getX();
			int badY = badTiles[bad].getY();
			bad++;
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(
						"badTiles.txt", true));
				out.write("" + badX);
				out.newLine();
				out.write("" + badY);
				out.newLine();
				out.close();
			} catch (IOException e) {
			}
			log("I just added badTile [" + badX + "," + badY
					+ "] to my memory.");
			log("I'll never make that mistake again!");
		}
		if (serverString.contains("can't light")) {
			manualAvoid = false;
			if (getMyPlayer().isMoving())
				return;
			if (getObjectAt(getMyPlayer().getLocation()) != null)
				return;
			forceWalk = true;
			badTiles[bad] = getMyPlayer().getLocation();
			int badX = badTiles[bad].getX();
			int badY = badTiles[bad].getY();
			bad++;
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(
						"badTiles.txt", true));
				out.write("" + badX);
				out.newLine();
				out.write("" + badY);
				out.newLine();
				out.close();
			} catch (IOException e) {
			}
			log("I just added badTile [" + badX + "," + badY
					+ "] to my memory.");
			log("I'll never make that mistake again!");
		}
	}

	// TRIGOON
	private boolean mousePressed() {
		return Bot.getClient().getMouse().pressed;
	}

	private final RenderingHints rh = new RenderingHints(
			RenderingHints.KEY_TEXT_ANTIALIASING,
			RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

	// TRIGOON

	public void onRepaint(Graphics g) {
		if (!isLoggedIn() || isLoginScreen()) {
			return;
		}
		// TRIGOON
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHints(rh);

		int x = 2, y = 377;

		if ((skills.getCurrentSkillExp(trainingSkill) - startExp) > 0
				&& startExp > 0) {
			expGained = skills.getCurrentSkillExp(trainingSkill) - startExp;
		}

		long millis = System.currentTimeMillis() - st;
		long totalseconds = millis / 1000;
		long hours = millis / (1000 * 60 * 60);
		millis -= hours * 1000 * 60 * 60;
		long minutes = millis / (1000 * 60);
		millis -= minutes * 1000 * 60;
		long seconds = millis / 1000;
		if (lastSecond == 0)
			lastSecond = seconds;

		if (lastSecond != seconds) {
			if (fullTurn) {
				if (red == 238) {
					red = 238;
					green = 218;
					blue = 179;
					fullTurn = false;
				} else {
					red = red + 6.45;
					green = green + 6.15;
					blue = blue + 4.8;
				}
			} else {
				if (red == 195) {
					red = 195;
					green = 177;
					blue = 147;
					fullTurn = true;
				} else {
					red = red - 6.45;
					green = green - 6.15;
					blue = blue - 4.8;
				}
			}
		}
		if (red > 238 || green > 218 || blue > 179) {
			red = 238;
			green = 218;
			blue = 179;
		}
		if (red < 195 || green < 177 || blue < 147) {
			red = 195;
			green = 177;
			blue = 147;
		}
		lastSecond = seconds;

		long timeToLevel = 0;
		String timeToLevel2 = "Calculating...";
		if (expHour > 0) {
			timeToLevel = (skills.getXPToNextLevel(trainingSkill) * 60 / expHour);
			if (timeToLevel >= 60) {
				long thours = (int) timeToLevel / 60;
				long tmin = (timeToLevel - (thours * 60));
				timeToLevel2 = thours + ":" + tmin;
			} else {
				timeToLevel2 = timeToLevel + " Minutes";
			}
		}

		if (expGained > 0 && totalseconds > 0) {
			expHour = (int) (3600 * expGained / (totalseconds));
			logsHour = (int) (3600 * logsBurned / (totalseconds));
		}

		if (show) {
			drawMouse(g);

			g2.setColor(new Color((int) red, (int) green, (int) blue, 255));
			g2.fillRect(6, 344, 506, 128);
			g2.setColor(new Color(49, 42, 27, 255));
			g2.drawRect(6, 344, 506, 128);

			g2.setColor(new Color(187, 0, 0, 255));
			g2.setFont(new Font("Arial", Font.BOLD, 12));
			g2.drawString("Time Running:", x + 14, y - 10);
			g2.drawString("Time To Level:", x + 14, y + 5);
			g2.drawString("Logs Burned:", x + 14, y + 20);
			g2.drawString("XP Gained:", x + 14, y + 35);
			g2.drawString("XP / Hour:", x + 14, y + 50);
			g2.drawString("Logs / Hour:", x + 14, y + 65);
			g2.drawString("Tiles Avoided:", x + 14, y + 80);

			g2.setColor(Color.black);
			g2.setFont(new Font("Arial", Font.PLAIN, 12));
			g2.drawString(hours + ":" + minutes + ":" + seconds, x + 100,
					y - 10);
			g2.drawString(timeToLevel2, x + 100, y + 5);
			g2.drawString(logsBurned + " Logs", x + 100, y + 20);
			g2.drawString(expGained + " XP", x + 100, y + 35);
			g2.drawString(expHour + " XP/Hour", x + 100, y + 50);
			g2.drawString(logsHour + " Logs/Hour", x + 100, y + 65);
			g2.drawString(bad + " Tiles", x + 100, y + 80);
			g.drawImage(fmPic, 237, 311, null);
		}

		if (show) {
			g2.setColor(new Color(192, 0, 0, 255));
			g2.fillRect(498, 344, 14, 14);
			g2.setColor(new Color(49, 42, 27, 255));
			g2.drawRect(498, 344, 14, 14);
			g2.setColor(new Color(255, 255, 255, 255));
			g2.drawString("X", 502, 356);
		} else {
			g2.setColor(new Color(0, 154, 0, 255));
			g2.fillRect(498, 344, 14, 14);
			g2.setColor(new Color(49, 42, 27, 255));
			g2.drawRect(498, 344, 14, 14);
			g2.setColor(new Color(255, 255, 255, 255));
			g2.drawString("O", 501, 356);
		}

		Mouse m = Bot.getClient().getMouse();
		if (!show && mousePressed() && m.x >= 498 && m.x <= 512 && m.y >= 344
				&& m.y <= 359 && NEW) {
			show = true;
			NEW = false;
		} else if (show && mousePressed() && m.x >= 498 && m.x <= 512
				&& m.y >= 344 && m.y <= 359 && NEW) {
			show = false;
			NEW = false;
		}

		if (!mousePressed())
			NEW = true;

		if (show) {
			double percent = skills.getPercentToNextLevel(trainingSkill);
			g2.setColor(new Color(255, 164, 0, 255));
			g2.fill3DRect(x + 215, y + 57, (int) (2.845 * percent), 17, true);
			g2.setColor(new Color(187, 0, 0, 255));
			g2.drawRoundRect(x + 214, y + 55, 285, 20, 5, 5);
			g2.setColor(new Color(83, 54, 0, 255));
			g2.setFont(new Font("Arial", Font.BOLD, 16));
			g2.drawString(percent + "% TL", x + 325, y + 72);
			g.setColor(Color.black);
			g2.setFont(new Font("Arial", Font.BOLD, 10));
			if ((skills.getRealSkillLevel(trainingSkill) - startLevel) == 0)
				g2
						.drawString(
								"Currently Level "
										+ skills
												.getRealSkillLevel(trainingSkill)
										+ ", "
										+ (skills
												.getRealSkillLevel(trainingSkill) - startLevel)
										+ " Levels Gained.", x + 268, y + 86);
			else if ((skills.getRealSkillLevel(trainingSkill) - startLevel) == 1)
				g2
						.drawString(
								"Currently Level "
										+ skills
												.getRealSkillLevel(trainingSkill)
										+ ", "
										+ (skills
												.getRealSkillLevel(trainingSkill) - startLevel)
										+ " Level Gained!", x + 268, y + 86);
			else
				g2
						.drawString(
								"Currently Level "
										+ skills
												.getRealSkillLevel(trainingSkill)
										+ ", "
										+ (skills
												.getRealSkillLevel(trainingSkill) - startLevel)
										+ " Levels Gained!", x + 268, y + 86);
		}
		// TRIGOON
		if (show) {
			/*
			 * for (int i = 0; i < badTiles.length; i++) { try { if
			 * (tileOnMap(badTiles[i]) && tileOnScreen(badTiles[i]))
			 * overlayTile(g2, badTiles[i],new Color(192,0,0, 50)); } catch
			 * (final Exception e) { } }
			 */
			if (burning) {
				RSTile nextTile = new RSTile(
						getMyPlayer().getLocation().getX() - 1, getMyPlayer()
								.getLocation().getY());
				if (getObjectAt(nextTile) == null) {
					if (tileOnScreen(nextTile))
						overlayTile(g2, nextTile, new Color(0, 200, 0, 50));
				} else {
					if (tileOnScreen(nextTile))
						overlayTile(g2, nextTile, new Color(192, 0, 0, 50));
				}
			}
		}
	}

	public void drawMouse(final Graphics g) {
		final Point loc = getMouseLocation();
		if (System.currentTimeMillis()
				- Bot.getClient().getMouse().getMousePressTime() < 250) {
			g.setColor(new Color(192, 0, 0, 225));
			g.fillOval(loc.x - 5, loc.y - 5, 10, 10);
		} else {
			g.setColor(new Color(192, 0, 0, 50));
		}
		g.drawLine(0, loc.y, 766, loc.y);
		g.drawLine(loc.x, 0, loc.x, 505);
	}

	public void overlayTile(final Graphics g, final RSTile t, final Color c) {
		if (!tileOnScreen(t))
			return;
		final Point p = Calculations.tileToScreen(t);
		final Point pn = Calculations.tileToScreen(t.getX(), t.getY(), 0, 0, 0);
		final Point px = Calculations.tileToScreen(t.getX() + 1, t.getY(), 0,
				0, 0);
		final Point py = Calculations.tileToScreen(t.getX(), t.getY() + 1, 0,
				0, 0);
		final Point pxy = Calculations.tileToScreen(t.getX() + 1, t.getY() + 1,
				0, 0, 0);
		final Point[] points = { p, pn, px, py, pxy };
		for (final Point point : points) {
			if (!pointOnScreen(point)) {
				return;
			}
		}
		g.setColor(c);
		g.fillPolygon(new int[] { py.x, pxy.x, px.x, pn.x }, new int[] { py.y,
				pxy.y, px.y, pn.y }, 4);
		g.drawPolygon(new int[] { py.x, pxy.x, px.x, pn.x }, new int[] { py.y,
				pxy.y, px.y, pn.y }, 4);
	}

	public void onFinish() {
		ScreenshotUtil.takeScreenshot(true);
		while (memory.isAlive()) {
			if (memory.isAlive()) {
				memory.interrupt();
				if (memory.isInterrupted()) {
					log("Free Memory has been Stopped");
				}
			}
		}
	}

	public class ArbiFireGUI extends JFrame {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ArbiFireGUI() {
			initComponents();
		}

		private void initComponents() {

			ArbiFireGUI = new JFrame();
			panel1 = new JPanel();
			label1 = new JLabel();
			tabbedPane1 = new JTabbedPane();
			panel2 = new JPanel();
			slider1 = new JSlider();
			logSelected = new JComboBox();
			textField3 = new JTextField();
			textField4 = new JTextField();
			textField5 = new JTextField();
			slider3 = new JSlider();
			label5 = new JLabel();
			label6 = new JLabel();
			label7 = new JLabel();
			label8 = new JLabel();
			panel4 = new JPanel();
			label2 = new JLabel();
			radioButton1 = new JRadioButton();
			radioButton2 = new JRadioButton();
			label3 = new JLabel();
			label4 = new JLabel();
			formattedTextField1 = new JFormattedTextField();
			formattedTextField2 = new JFormattedTextField();
			formattedTextField3 = new JFormattedTextField();
			formattedTextField4 = new JFormattedTextField();
			textField1 = new JTextField();
			textField2 = new JTextField();
			panel3 = new JPanel();
			scrollPane1 = new JScrollPane();
			textPane1 = new JTextPane();
			button1 = new JButton();

			{

				ArbiFireGUI.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent arg0) {
						guiStart = true;
						ArbiFireGUI.dispose();
					}
				});

				ArbiFireGUI.setFont(new Font("Century Gothic", Font.PLAIN, 12));
				ArbiFireGUI.setFocusable(false);
				ArbiFireGUI.setTitle("ArbiFire");
				ArbiFireGUI.setVisible(true);
				ArbiFireGUI.setResizable(false);
				ArbiFireGUI.setAlwaysOnTop(true);
				Container ArbiFireGUIContentPane = ArbiFireGUI.getContentPane();
				ArbiFireGUIContentPane.setLayout(null);

				{
					panel1.setBackground(Color.red);
					panel1.setFont(new Font("Century Gothic", Font.PLAIN, 12));
					panel1.setFocusable(false);
					panel1.setLayout(null);

					label1.setText("ArbiFire");
					label1.setFont(new Font("Century Gothic", Font.PLAIN, 34));
					label1.setHorizontalTextPosition(SwingConstants.CENTER);
					label1.setHorizontalAlignment(SwingConstants.CENTER);
					label1.setBorder(null);
					label1.setFocusable(false);
					panel1.add(label1);
					label1.setBounds(-1, -1, 376, 75);

					{
						tabbedPane1.setBackground(Color.red);
						tabbedPane1.setFont(new Font("Century Gothic",
								Font.PLAIN, 12));
						tabbedPane1.setBorder(null);
						tabbedPane1.setFocusable(false);

						{
							panel2.setBackground(Color.red);
							panel2.setBorder(null);
							panel2.setForeground(Color.red);
							panel2.setFont(new Font("Century Gothic",
									Font.PLAIN, 12));
							panel2.setFocusable(false);
							panel2.setLayout(null);

							slider1.setMaximum(10);
							slider1.setMinimum(4);
							slider1.setBorder(null);
							slider1.setOpaque(false);
							slider1.setPaintTicks(true);
							slider1.setPaintLabels(true);
							slider1.setSnapToTicks(true);
							slider1.setMajorTickSpacing(1);
							slider1.setValue(7);
							slider1.setFocusable(false);
							panel2.add(slider1);
							slider1.setBounds(31, 106, 290, 41);

							logSelected.setModel(new DefaultComboBoxModel(
									new String[] { "Normal", "Oak", "Willow",
											"Teak", "Maple", "Mahogany", "Yew",
											"Magic" }));
							logSelected.setBorder(null);
							logSelected.setOpaque(false);
							logSelected.setFocusable(false);
							panel2.add(logSelected);
							logSelected.setBounds(131, 40, 90, 25);

							textField3.setText("Log Type");
							textField3
									.setHorizontalAlignment(SwingConstants.CENTER);
							textField3.setEditable(false);
							textField3.setOpaque(false);
							textField3.setBorder(null);
							textField3.setFont(textField3.getFont().deriveFont(
									textField3.getFont().getSize() + 3f));
							textField3.setFocusable(false);
							panel2.add(textField3);
							textField3.setBounds(126, 5, 100, 30);

							textField4.setText("Mouse Speed");
							textField4
									.setHorizontalAlignment(SwingConstants.CENTER);
							textField4.setEditable(false);
							textField4.setOpaque(false);
							textField4.setBorder(null);
							textField4.setFont(textField4.getFont().deriveFont(
									textField4.getFont().getSize() + 3f));
							textField4.setFocusable(false);
							panel2.add(textField4);
							textField4.setBounds(126, 70, 100, 30);

							textField5.setText("Path Choices");
							textField5
									.setHorizontalAlignment(SwingConstants.CENTER);
							textField5.setEditable(false);
							textField5.setOpaque(false);
							textField5.setBorder(null);
							textField5.setFont(textField5.getFont().deriveFont(
									textField5.getFont().getSize() + 3f));
							textField5.setFocusable(false);
							panel2.add(textField5);
							textField5.setBounds(126, 151, 100, 30);

							slider3.setMaximum(4);
							slider3.setMinimum(2);
							slider3.setBorder(null);
							slider3.setOpaque(false);
							slider3.setPaintTicks(true);
							slider3.setPaintLabels(true);
							slider3.setSnapToTicks(true);
							slider3.setMajorTickSpacing(1);
							slider3.setValue(3);
							slider3.setFocusable(false);
							panel2.add(slider3);
							slider3.setBounds(31, 187, 290, 41);

							label5.setText("Fastest");
							panel2.add(label5);
							label5.setBounds(31, 172, 60, label5
									.getPreferredSize().height);

							label6.setText("Fastest");
							panel2.add(label6);
							label6.setBounds(31, 91, 60, 16);

							label7.setText("Safest");
							panel2.add(label7);
							label7.setBounds(286, 172, 60, 16);

							label8.setText("Slowest");
							panel2.add(label8);
							label8.setBounds(284, 91, 60, 16);

							{
								Dimension preferredSize = new Dimension();
								for (int i = 0; i < panel2.getComponentCount(); i++) {
									Rectangle bounds = panel2.getComponent(i)
											.getBounds();
									preferredSize.width = Math
											.max(bounds.x + bounds.width,
													preferredSize.width);
									preferredSize.height = Math.max(bounds.y
											+ bounds.height,
											preferredSize.height);
								}
								Insets insets = panel2.getInsets();
								preferredSize.width += insets.right;
								preferredSize.height += insets.bottom;
								panel2.setMinimumSize(preferredSize);
								panel2.setPreferredSize(preferredSize);
							}
						}
						tabbedPane1.addTab("Main", panel2);

						{
							panel4.setBackground(Color.red);
							panel4.setFocusable(false);
							panel4.setLayout(null);

							label2.setText("Custom Breaking");
							label2
									.setHorizontalTextPosition(SwingConstants.CENTER);
							label2
									.setHorizontalAlignment(SwingConstants.CENTER);
							label2.setFont(label2.getFont().deriveFont(
									label2.getFont().getSize() + 10f));
							panel4.add(label2);
							label2.setBounds(0, 5, 350, 46);

							radioButton1.setText("Use Breaking");
							radioButton1.setBackground(Color.red);
							radioButton1.setOpaque(false);
							radioButton1.setEnabled(false);
							panel4.add(radioButton1);
							radioButton1.setBounds(112, 55, 125, 25);

							radioButton2.setText("Completely Random");
							radioButton2.setBackground(Color.red);
							radioButton2.setOpaque(false);
							radioButton2.setEnabled(false);
							panel4.add(radioButton2);
							radioButton2.setBounds(112, 85, 143, 25);

							label3.setText("Time between breaks:");
							label3.setEnabled(false);
							panel4.add(label3);
							label3.setBounds(51, 128, 130, 35);

							label4.setText("Length of Breaks:");
							label4.setEnabled(false);
							panel4.add(label4);
							label4.setBounds(51, 169, 130, 35);

							formattedTextField1.setText("120");
							formattedTextField1.setEnabled(false);
							panel4.add(formattedTextField1);
							formattedTextField1.setBounds(175, 135, 45, 25);

							formattedTextField2.setText("30");
							formattedTextField2.setEnabled(false);
							panel4.add(formattedTextField2);
							formattedTextField2.setBounds(175, 175, 45, 25);

							formattedTextField3.setText("45");
							formattedTextField3.setEnabled(false);
							panel4.add(formattedTextField3);
							formattedTextField3.setBounds(250, 135, 45, 25);

							formattedTextField4.setText("15");
							formattedTextField4.setEnabled(false);
							panel4.add(formattedTextField4);
							formattedTextField4.setBounds(250, 175, 45, 25);

							textField1.setText("+ / -");
							textField1.setOpaque(false);
							textField1.setEditable(false);
							textField1.setFocusable(false);
							textField1.setCaretColor(Color.red);
							textField1.setBorder(null);
							textField1.setEnabled(false);
							panel4.add(textField1);
							textField1.setBounds(223, 133, 30, 25);

							textField2.setText("+ / -");
							textField2.setOpaque(false);
							textField2.setEditable(false);
							textField2.setFocusable(false);
							textField2.setCaretColor(Color.red);
							textField2.setBorder(null);
							textField2.setEnabled(false);
							panel4.add(textField2);
							textField2.setBounds(223, 173, 30, 25);

							{
								Dimension preferredSize = new Dimension();
								for (int i = 0; i < panel4.getComponentCount(); i++) {
									Rectangle bounds = panel4.getComponent(i)
											.getBounds();
									preferredSize.width = Math
											.max(bounds.x + bounds.width,
													preferredSize.width);
									preferredSize.height = Math.max(bounds.y
											+ bounds.height,
											preferredSize.height);
								}
								Insets insets = panel4.getInsets();
								preferredSize.width += insets.right;
								preferredSize.height += insets.bottom;
								panel4.setMinimumSize(preferredSize);
								panel4.setPreferredSize(preferredSize);
							}
						}
						tabbedPane1.addTab("Breaking", panel4);

						{
							panel3.setBackground(Color.red);
							panel3.setFocusable(false);
							panel3.setBorder(null);
							panel3.setLayout(null);

							{

								textPane1
										.setText("Current Version: 1.35\n\nCheck here for more info about this script:\nhttp://www.rsbot.org/vb/showthread.php?t=313221");
								textPane1.setEditable(false);
								textPane1.setFont(new Font("Century Gothic",
										Font.PLAIN, 12));
								scrollPane1.setViewportView(textPane1);
							}
							panel3.add(scrollPane1);
							scrollPane1.setBounds(15, 33, 315, 180);

							{
								Dimension preferredSize = new Dimension();
								for (int i = 0; i < panel3.getComponentCount(); i++) {
									Rectangle bounds = panel3.getComponent(i)
											.getBounds();
									preferredSize.width = Math
											.max(bounds.x + bounds.width,
													preferredSize.width);
									preferredSize.height = Math.max(bounds.y
											+ bounds.height,
											preferredSize.height);
								}
								Insets insets = panel3.getInsets();
								preferredSize.width += insets.right;
								preferredSize.height += insets.bottom;
								panel3.setMinimumSize(preferredSize);
								panel3.setPreferredSize(preferredSize);
							}
						}
						tabbedPane1.addTab("Updates", panel3);

					}
					panel1.add(tabbedPane1);
					tabbedPane1.setBounds(10, 78, 355, 270);

					button1
							.addActionListener(new java.awt.event.ActionListener() {
								public void actionPerformed(
										java.awt.event.ActionEvent arg0) {
									ArbiFire.speed = (int) slider1.getValue();
									ArbiFire.variable = (int) slider3
											.getValue();
									logToBurn = logSelected.getSelectedItem()
											.toString();
									if (logToBurn.contains("Norma")) {
										log = 1511;
									}
									if (logToBurn.contains("Oa")) {
										log = 1521;
									}
									if (logToBurn.contains("Willo")) {
										log = 1519;
									}
									if (logToBurn.contains("Tea")) {
										log = 6333;
									}
									if (logToBurn.contains("Mapl")) {
										log = 1517;
									}
									if (logToBurn.contains("Mahogan")) {
										log = 6332;
									}
									if (logToBurn.contains("Ye")) {
										log = 1515;
									}
									if (logToBurn.contains("Magi")) {
										log = 1513;
									}

									guiStart = true;
									guiWait = false;
									worked = true;
									ArbiFireGUI.dispose();
								}
							});
					button1.setText("Start");
					button1.setFont(new Font("Century Gothic", Font.PLAIN, 12));
					button1.setBorder(null);
					button1.setFocusable(false);
					panel1.add(button1);
					button1.setBounds(10, 358, 355, 70);
				}

				ArbiFireGUIContentPane.add(panel1);
				panel1.setBounds(0, 0, 395, 460);

				ArbiFireGUIContentPane
						.setPreferredSize(new Dimension(375, 450));
				ArbiFireGUI.pack();
				ArbiFireGUI.setLocationRelativeTo(ArbiFireGUI.getOwner());
			}
		}

		private JFrame ArbiFireGUI;
		private JPanel panel1;
		private JLabel label1;
		private JTabbedPane tabbedPane1;
		private JPanel panel2;
		private JSlider slider1;
		private JComboBox logSelected;
		private JTextField textField3;
		private JTextField textField4;
		private JTextField textField5;
		private JSlider slider3;
		private JLabel label5;
		private JLabel label6;
		private JLabel label7;
		private JLabel label8;
		private JPanel panel4;
		private JLabel label2;
		private JRadioButton radioButton1;
		private JRadioButton radioButton2;
		private JLabel label3;
		private JLabel label4;
		private JFormattedTextField formattedTextField1;
		private JFormattedTextField formattedTextField2;
		private JFormattedTextField formattedTextField3;
		private JFormattedTextField formattedTextField4;
		private JTextField textField1;
		private JTextField textField2;
		private JPanel panel3;
		private JScrollPane scrollPane1;
		private JTextPane textPane1;
		private JButton button1;
	}

	class FreeMemory implements Runnable {

		@Override
		public void run() {
			log("The Free Momory Thread has been Started, It will monitor your free memory and clean up if neccisary");
			while (!memory.isInterrupted()) {
				try {
					if (random(0, 450) == 3) {
						// Garbage Collection - manual
						Runtime r = Runtime.getRuntime();
						log("Memory Cleaner: Current Free Memory: "
								+ ((float) r.freeMemory() / 1048576)
								+ " Mb, Using: " + r.totalMemory());
						r.gc();
						log("Memory Cleaner: Current Free Memory: "
								+ ((float) r.freeMemory() / 1048576)
								+ "Mb, Using: " + r.totalMemory()
								+ " After Garbage Colelction");
					}
					// Thread Sleep
					Thread.sleep(random(1000, 1500));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private class ArbiFireAntiBan implements Runnable {
		public boolean stopThread;

		public void run() {
			while (!stopThread) {
				try {
					if (random(0, 20) == 0) {
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
}