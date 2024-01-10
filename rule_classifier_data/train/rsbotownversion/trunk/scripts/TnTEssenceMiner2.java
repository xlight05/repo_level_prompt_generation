import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.script.Calculations;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSInterface;
import org.rsbot.script.wrappers.RSNPC;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSPlayer;
import org.rsbot.script.wrappers.RSTile;

@ScriptManifest(authors = { "TwistedMind" }, name = "TnTEssenceMiner 2", version = 2.00, category = "Mining")
public class TnTEssenceMiner2 extends Script implements PaintListener,
		ServerMessageListener {

	boolean mineYan;
	public int nextEnergy = random(60, 90);
	final RSTile yanilleDoor = new RSTile(2597, 3088);
	final RSTile varrockDoor = new RSTile(3253, 3398);
	public int[] essenceArea = { 2950, 4860, 2870, 4800 };
	final int[] varBankArea = { 3257, 3423, 3250, 3419 };
	final int[] yanBankArea = { 2613, 3097, 2609, 3088 };
	final int[] mageGuildX = { 2590, 2593, 2597, 2597, 2597, 2593, 2586, 2585,
			2585, 2586, 2588 };
	final int[] mageGuildY = { 3094, 3094, 3090, 3088, 3085, 3081, 3082, 3087,
			3088, 3090, 3092 };
	final Polygon mageGuild = new Polygon(mageGuildX, mageGuildY, 11);
	final RSTile[] miningTiles = { new RSTile(2927, 4818),
			new RSTile(2931, 4818), new RSTile(2931, 4814),
			new RSTile(2927, 4814), new RSTile(2897, 4816),
			new RSTile(2897, 4812), new RSTile(2893, 4812),
			new RSTile(2893, 4816), new RSTile(2895, 4847),
			new RSTile(2891, 4847), new RSTile(2891, 4851),
			new RSTile(2895, 4851), new RSTile(2925, 4848),
			new RSTile(2925, 4852), new RSTile(2929, 4852),
			new RSTile(2929, 4848) };
	final int[] auburyHut = { 3255, 3404, 3252, 3399 };
	final int varrockUpStairsArea[] = { 3257, 3423, 3250, 3416 };
	final int yanilleDownStairsArea[] = { 2594, 9489, 2582, 9484 };
	final int varrockSmithArea[] = { 3251, 3410, 3246, 3403 };
	final int pickaxe[] = { 1265, 1267, 1269, 1296, 1273, 1271, 1275, 15259 };
	final int[] tilesX = { 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0 };
	final int[] tilesY = { 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1 };
	int useX = 0;
	int useY = 0;
	final RSTile varrockPath[] = { new RSTile(3253, 3421),
			new RSTile(3254, 3427), new RSTile(3262, 3421),
			new RSTile(3262, 3415), new RSTile(3259, 3411),
			new RSTile(3257, 3404), new RSTile(3254, 3398),
			new RSTile(3253, 3401) };
	final RSTile yanillePath[] = { new RSTile(2611, 3093),
			new RSTile(2604, 3089), new RSTile(2597, 3088) };
	public BufferedImage paintBG;
	public long startTime = System.currentTimeMillis();
	public int value = -1, startXP;
	public String status = "Loading";
	public boolean bool = false;

	public enum State {
		AUBURY, DISTENTOR, WALK_TO_BANK_VAR, WALK_TO_BANK_YAN, MINE, EXIT_PORTAL, BANK, ANTIBAN, WAIT
	}

	public State getState() {
		if (isLoggedIn())
			return State.WAIT;
		if (getMyPlayer().getAnimation() != -1)
			return State.ANTIBAN;
		if (isInvFull()) {
			if (isInArea(essenceArea)) {
				status = "Exiting mines";
				return State.EXIT_PORTAL;
			} else {
				if (isInArea(varBankArea) || isInArea(yanBankArea)) {
					status = "Banking";
					return State.BANK;
				} else {
					status = "Walking to bank";
					if (mineYan) {
						return State.WALK_TO_BANK_YAN;
					}
					return State.WALK_TO_BANK_VAR;
				}
			}
		} else {
			if (isInArea(essenceArea)) {
				status = "Mining";
				return State.MINE;
			} else {
				if (mineYan) {
					status = "Walking to Distentor";
					return State.DISTENTOR;
				}
				status = "Walking to Aubury";
				return State.AUBURY;
			}
		}
	}

	public boolean isInArea(int[] area) {
		int x = getMyPlayer().getLocation().getX();
		int y = getMyPlayer().getLocation().getY();
		if (x >= area[2] && x <= area[0] && y >= area[3] && y <= area[1])
			return true;
		return false;
	}

	public boolean isInvFull() {
		return getInventoryCount() >= 28;
	}

	public boolean depositAll() {
		return atInterface(RSInterface.getInterface(762).getChild(32), "eposit");
	}

	public void antiban(int min, int max) {
		// Check run:
		if (getEnergy() >= nextEnergy) {
			setRun(true);
			nextEnergy = random(50, 70);
		}
		// Move Camera:
		if (random(1, 10) == 1) {
			setCameraRotation(random(1, 359));
			wait(random(500,1500));
		}
		// Move mouse:
		final int gamble = random(0, 10);
		if (gamble < 2) {
			moveMouse(random(7, 12), random(50, 500), random(100, 500), 30);
			wait(random(500,1500));
		}
		// Right click other players
		final int chance2 = random(1, 10);
		Point mousePosition;
		if (chance2 == 1) {
			mousePosition = getMouseLocation();
			RSPlayer player = getNearestPlayerByLevel(random(3, 80), random(80,
					126));
			if (player != null && distanceTo(player) != 0) {
				moveMouse(player.getScreenLocation(), 5, 5);
				wait(random(300, 700));
				clickMouse(false);
				wait(random(750, 1000));
				moveMouse(mousePosition, 15, 15);
			}
		}
		// Check Mining level:
		if (random(1, 50) == 1 && getMyPlayer().getAnimation() != -1) {
			if (getCurrentTab() != 1) {
				openTab(1);
				moveMouse(new Point(703, 222), 29, 11);
				wait(random(1500, 3000));
			}
		}
		// Check Friends list
		if (random(1, 100) == 1 && getMyPlayer().getAnimation() != -1) {
			if (getCurrentTab() != 9) {
				openTab(9);
			}
			if (random(0, 5) == 5) {
				moveMouse(random(573, 620), random(439, 457));
				clickMouse(true);
				wait(random(750, 1000));
				sendText("", true);
			} else {
				moveMouse(random(552, 569), random(227, 420));
				wait(random(750, 1000));
				Point mousel2 = getMouseLocation();
				moveMouse(random(584, 624), mousel2.y + random(-3, 3));
			}
		}
			try {
				wait(min, max);
			} catch (InterruptedException e) {
				//Nothing
			}
		}

	// Credits to Garrett
	public boolean playerInArea(final Polygon area) {
		return area.contains(new Point(getMyPlayer().getLocation().getX(),
				getMyPlayer().getLocation().getY()));
	}

	public RSTile findNearestEssenceTile() {
		RSTile tile = null;
		int closest = 999;
		for (int i = 0; i < miningTiles.length; i++) {
			if (distanceTo(miningTiles[i]) < closest) {
				closest = distanceTo(miningTiles[i]);
				tile = miningTiles[i];
				useX = tilesX[i];
				useY = tilesY[i];
			}
		}
		return tile;
	}

	public boolean onTile(final RSTile tile, final String action,
			final double dx, final double dy, final int height) {
		if (!tile.isValid()) {
			return false;
		}
		Point checkScreen;
		try {
			checkScreen = Calculations.tileToScreen(tile, dx, dy, height);
			if (!pointOnScreen(checkScreen)) {
				if (distanceTo(tile) <= 8) {
					if (getMyPlayer().isMoving()) {
						return false;
					}
					walkTileMM(tile);
					waitToMove(1000);
					return false;
				}
				return false;
			}
		} catch (final Exception e) {
		}
		try {
			boolean stop = false;
			for (int i = 0; i <= 50; i++) {
				checkScreen = Calculations.tileToScreen(tile, dx, dy, height);
				if (!pointOnScreen(checkScreen)) {
					return false;
				}
				moveMouse(checkScreen);
				final Object[] menuItems = getMenuItems().toArray();
				for (int a = 0; a < menuItems.length; a++) {
					if (menuItems[a].toString().toLowerCase().contains(
							action.toLowerCase())) {
						stop = true;
						break;
					}
				}
				if (stop) {
					break;
				}
			}
		} catch (final Exception e) {
		}
		try {
			return atMenu(action);
		} catch (final Exception e) {
		}
		return false;
	}

	// -----------------------------------------------

	@Override
	public int loop() {
		try {
			if (skills.getCurrentSkillExp(STAT_MINING) <= 0
					&& skills.getCurrentSkillExp(STAT_WOODCUTTING) <= 0) {
				log("Not fully logged in yet! Mine 1 essence if this occurs!");
				return random(5000, 7000);
			}
			if (!bool) {
				startXP = skills.getCurrentSkillExp(STAT_MINING);
				bool = true;
			}
			if (value == -1) {
				if (inventoryContainsOneOf(1436)) {
					value = grandExchange.loadItemInfo(1436).getMaxPrice();
				} else if (inventoryContainsOneOf(7936)) {
					value = grandExchange.loadItemInfo(7936).getMaxPrice();
				}
			}
			// Interface checks!
			if (RSInterface.getInterface(620).isValid()) {
				log("Aubury shop window detected! Closing...");
				atInterface(RSInterface.getInterface(620).getChild(18));
				return random(1000, 2000);
			}
			if (RSInterface.getInterface(109).isValid()) {
				log("Collect window detected! Closing...");
				atInterface(RSInterface.getInterface(109).getChild(13));
				return random(1000, 2000);
			}
			if (RSInterface.getInterface(335).isValid()) {
				log("Trade window detected! Closing...");
				atInterface(RSInterface.getInterface(109).getChild(19));
				return random(1000, 2000);
			}
			if (getPlane() == 1 && isInArea(varrockUpStairsArea)) {
				if (onTile(new RSTile(3256, 3421), "Climb", 0.5, 0.5, 0)) {
					wait(random(1500, 2000));
					while (getMyPlayer().isMoving()) {
						wait(random(90, 110));
					}
					wait(random(1500, 2000));
				}
				return random(50, 100);
			}
			if (isInArea(yanilleDownStairsArea)) {
				RSObject ladder = getNearestObjectByID(1757);
				if (ladder != null) {
					if (tileOnScreen(ladder.getLocation())) {
						atObject(ladder, "Climb");
					} else {
						walkTo(ladder.getLocation());
					}
					return random(1000, 2000);
				}
			}
			if (playerInArea(mageGuild) && getPlane() == 1) {
				RSObject ladder = getNearestObjectByID(1723);
				if (ladder != null) {
					if (tileOnScreen(ladder.getLocation())) {
						atObject(ladder, "Climb");
					} else {
						walkTo(ladder.getLocation());
					}
					return random(1000, 2000);
				}
			}
			if (isInArea(varrockSmithArea)) {
				int failCount;
				if (getObjectAt(new RSTile(3248, 3411)) != null) {
					if (onTile(new RSTile(3248, 3410), "Open", random(0.39,
							0.61), random(0, 0.05), random(20, 50))) {
						failCount = 0;
						while (getObjectAt(new RSTile(3248, 3410)) == null
								&& failCount < 40) {
							wait(random(50, 100));
							failCount++;
						}
					}
					if (getObjectAt(new RSTile(3248, 3411)) == null) {
						wait(random(50, 100));
					}
				}
				return random(1000, 2000);
			}
			//
			switch (getState()) {
			case MINE:
				if (getInventoryCount() > 1 && getInventoryCount() < 28
						&& getMyPlayer().getAnimation() == -1) {
					wait(random(2000, 3000));
					if (getMyPlayer().getAnimation() != -1 || isInvFull()) {
						return 1;
					}
				}
				RSTile tile = findNearestEssenceTile();
				if (!tileOnScreen(tile)) {
					walkPathMM(generateProperPath(tile), 10);
					while (distanceTo(getDestination()) > random(3, 7)
							&& distanceTo(getDestination()) < 40) {
						if(random(1,6) == 1)antiban(100, 200);
						else wait(random(100,200));
					}
				} else {
					if (onTile(tile, "Mine", useX, useY, 0)) {
						waitToMove(1000);
						int i = 0;
						while (getMyPlayer().getAnimation() == -1 && i < 50) {
							i++;
							wait(100);
						}
					}
				}
				return 0;
			case AUBURY:
				if (getObjectAt(varrockDoor) != null) {
					if (!isInArea(auburyHut)) {
						if (tileOnScreen(varrockDoor)) {
							onTile(varrockDoor, "Open", random(0.39, 0.61),
									random(0, 0.05), random(20, 50));
							while (getMyPlayer().isMoving()) {
								wait(100);
							}
							return random(1000, 2000);
						} else {
							walkPathMM(
									generateProperPath(new RSTile(3256, 3398)),
									10);
							while (getMyPlayer().isMoving()) {
								wait(100);
							}
							return 0;
						}
					}
				} else {
					RSNPC aubury = getNearestNPCByID(553);
					if (aubury != null) {
						RSTile loc = aubury.getLocation();
						if (aubury != null) {
							if (tileOnScreen(loc)) {
								if (atNPC(aubury, "Teleport Aubury")) {
									int i = 0;
									while (!isInArea(essenceArea) && i < 40) {
										i++;
										wait(100);
									}
								}
							} else {
								walkPathMM(varrockPath);
								while (distanceTo(getDestination()) > random(3,
										7)
										&& distanceTo(getDestination()) < 40) {
									if(random(1,6) == 1)antiban(100, 200);
									else wait(random(100,200));
								}
							}
						}
					} else {
						walkPathMM(varrockPath, 2, 2);
						while (distanceTo(getDestination()) > random(3, 7)
								&& distanceTo(getDestination()) < 40) {
							if(random(1,6) == 1)antiban(100, 200);
							else wait(random(100,200));
						}
					}
				}
				return 0;
			case DISTENTOR:
				if (!playerInArea(mageGuild)) {
					if (!onTile(yanilleDoor, "Open", random(0.1, 0.2), random(
							-0.5, 0.5), random(40, 50))) {
						walkPathMM(yanillePath);
						int i = 0;
						while (getMyPlayer().isMoving() || i > 20) {
							if (random(1, 5) == 1)
								antiban(100, 300);
							wait(100);
						}
						return 0;
					}
					int failCount = 0;
					while (!playerInArea(mageGuild) || failCount >= 20) {
						wait(100);
						failCount++;
						if (getMyPlayer().isMoving())
							failCount = 0;
					}
				} else {
					RSNPC Distentor = getNearestNPCByID(462);
					if (Distentor != null) {
						if (!atNPC(Distentor, "Teleport")) {
							walkTileOnScreen(Distentor.getLocation());
							return random(1000, 2000);
						}
						int failCount = 0;
						while (!isInArea(essenceArea) || failCount >= 20) {
							wait(100);
							failCount++;
							if (getMyPlayer().isMoving()) {
								failCount = 0;
							}
						}
					}
				}
				return 0;
			case WALK_TO_BANK_VAR:
				if (walkPathMM(reversePath(varrockPath), 15, 2, 2)) {
					while (distanceTo(getDestination()) > random(3, 7)
							&& distanceTo(getDestination()) < 40) {
						if (random(1, 6) == 1)
							antiban(100, 200);
						else
							wait(random(100, 200));
					}
				}
				return 0;
			case WALK_TO_BANK_YAN:
				walkPathMM(reversePath(yanillePath), 15, 2, 2);
				while (distanceTo(getDestination()) > random(3, 7)
						&& distanceTo(getDestination()) < 40) {
					if (random(1, 6) == 1)
						antiban(100, 200);
					else
						wait(random(100, 200));
				}
				return 0;
			case EXIT_PORTAL:
				RSObject portal = getNearestObjectByID(2492);
				RSTile loc = portal.getLocation();
				if (!tileOnScreen(loc)) {
					walkPathMM(generateProperPath(loc));
					int i = 0;
					while (getMyPlayer().isMoving() || i > 20) {
						if (random(1, 5) == 1)
							antiban(100, 300);
						wait(100);
					}
				} else {
					onTile(getNearestObjectByName("Portal").getLocation(),
							"Enter", 0.5, 0.5, 0);
					int i = 0;
					while (isInArea(essenceArea) || i < 20) {
						i++;
						if (random(1, 5) == 1)
							antiban(100, 300);
						wait(100);
					}
				}
				return 0;
			case BANK:
				if (bank.isOpen()) {
					if (getInventoryCount(1436) == 28
							|| getInventoryCount(7936) == 28) {
						bank.depositAll();
						int i = 0;
						while (getInventoryCount() <= 1 && i < 10) {
							i++;
							wait(100);
						}
						return 0;
					} else {
						bank.depositAllExcept(pickaxe);
						int i = 0;
						while (getInventoryCount() <= 1 && i < 10) {
							i++;
							wait(100);
						}
						return 0;
					}
				} else {
					if (random(1, 3) == 1) {
						RSObject booth = getNearestObjectByID(11402);
						if (tileOnScreen(booth.getLocation())) {
							if (!atObject(booth, "se-quickly")) {
								return random(100, 500);
							}
							int i = 0;
							while (!bank.isOpen() || i < 20) {
								i++;
								if (random(1, 5) == 1)
									antiban(100, 300);
								wait(100);
							}
						} else {
							walkTo(booth.getLocation());
							int i = 0;
							while (getMyPlayer().isMoving() || i < 20) {
								i++;
								if (random(1, 5) == 1)
									antiban(100, 300);
								wait(100);
							}
						}
					} else {
						int[] ids = { 5912, 5913 };
						RSNPC banker = getNearestNPCByID(ids);
						if (tileOnScreen(banker.getLocation())) {
							if (atNPC(banker, "Bank banker")) {
								int i = 0;
								while (!bank.isOpen() || i < 20) {
									i++;
									if (random(1, 5) == 1)
										antiban(100, 300);
									wait(100);
								}
							}
						} else {
							walkTo(banker.getLocation());
							int i = 0;
							while (getMyPlayer().isMoving() || i < 20) {
								i++;
								if (random(1, 5) == 1)
									antiban(100, 300);
								wait(100);
							}
						}
					}
				}
				return 0;
			case ANTIBAN:
				if (random(1, 5) == 1) {
					antiban(1000, 1500);
				}
			case WAIT:
			}
			return 0;
		} catch (java.lang.Throwable t) {
			t.printStackTrace();
			return 1;
		}
	}

	@Override
	public boolean onStart(Map<String, String> args) {
		try {
			final URL url = new URL("http://i45.tinypic.com/23r7pyr.png");
			paintBG = ImageIO.read(url);
		} catch (final IOException e) {
			log("Failed to download paint background!");
			e.printStackTrace();
		}
		Object[] options = { "Yanille", "Varrock" };
		if (JOptionPane.showOptionDialog(null,
				"Are you mining in Varrock or Yanille?", "Varrock or Yanille?",
				0, 3, null, options, options[1]) == 0) {
			mineYan = true;
		}
		if (mineYan) {
			log("You will be mining in Yanille");
		} else {
			log("You will be mining in Varrock");
		}

		return true;
	}

	@Override
	public void onRepaint(Graphics g) {
		if (isLoggedIn()) {
			if (startXP < 0) {
				startXP = skills.getCurrentSkillExp(STAT_MINING);
			}
			// ---------Timer--------------------
			long millis = System.currentTimeMillis() - startTime;
			long runTime = millis;
			long hours = millis / (1000 * 60 * 60);
			millis -= hours * 1000 * 60 * 60;
			long minutes = millis / (1000 * 60);
			millis -= minutes * 1000 * 60;
			long seconds = millis / 1000;
			int expGained = skills.getCurrentSkillExp(STAT_MINING) - startXP;
			int totalEss = expGained / 5;
			int totalMoney = totalEss * value;
			// Ensure a high quality and Smooth paint :)
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			((Graphics2D) g).setRenderingHint(
					RenderingHints.KEY_COLOR_RENDERING,
					RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY);
			g.drawImage(paintBG, 7, 345, null);
			// Draw data @ 180 x!!!
			g.setFont(new Font("Lithos Pro", Font.BOLD, 10));
			g.setColor(new Color(3, 87, 130, 255));
			g.drawString("" + hours + ":" + minutes + ":" + seconds, 180, 392);
			g.drawString(Integer.toString(totalEss), 180, 404);
			g.drawString(Integer.toString(totalMoney), 180, 417);
			g.drawString(Integer.toString(expGained), 180, 429);
			int essTillNextLvl = (int) (skills.getXPToNextLevel(STAT_MINING) / 5);
			g.drawString(Integer.toString(essTillNextLvl), 180, 442);
			g.drawString(status, 180, 454);
			// Draw data @ 440 x!!!
			final int essPerHour = (int) (3600000.0 / runTime * totalEss);
			g.drawString(Integer.toString(essPerHour), 440, 404);
			final int moneyPerHour = (int) (3600000.0 / runTime * totalMoney);
			g.drawString(Integer.toString(moneyPerHour), 440, 417);
			g.drawString(Integer.toString(essPerHour * 5), 440, 429);
			g.drawString(Integer.toString(value), 440, 442);
			int timeUntilLevel = ((skills.getXPToNextLevel(STAT_MINING) / ((essPerHour * 5) + 1)) * 3600000);
			int hoursToLevel = timeUntilLevel / (1000 * 60 * 60);
			timeUntilLevel -= hoursToLevel;
			int minutesToLevel = timeUntilLevel / (1000 * 60);
			timeUntilLevel -= minutesToLevel;
			int secondsToLevel = timeUntilLevel / 1000;
			g.drawString("-Under construction-", 440, 454);
			// 497,345 to 511,359 = close button
		}
	}

	@Override
	public void serverMessageRecieved(ServerMessageEvent e) {
		String msg = e.getMessage();
		if (msg.contains("You've advanced a")) {
			if (!isInvFull()) {
				wait(random(1000, 3000));
				onTile(findNearestEssenceTile(), "Mine", useX, useY, 0);
			}
		}
	}

}