import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.rsbot.bot.Bot;
import org.rsbot.bot.input.Mouse;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.script.Constants;
import org.rsbot.script.GrandExchange;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.Skills;
import org.rsbot.script.wrappers.RSNPC;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSPlayer;
import org.rsbot.script.wrappers.RSTile;
import org.rsbot.util.ScreenshotUtil;

@ScriptManifest(authors = { "Bloddyharry" }, name = "Bloddy Cooker", category = "Cooking", version = 2.0, description = "<html>\n"
		+ "<body style='font-family: Calibri; background-color: black; color:white; padding: 0px; text-align; center;'>"
		+ "<h2>"
		+ "Bloddy Cooker 2.0"
		+ "</h2>\n"
		+ "<b>Made by Bloddyharry</b>\n"
		+ "<br><br>\n"
		+ "Start at the selected location. has great antiban and paint."
		+ "<br><br>\n"
		+ "<select name='location'>"
		+ "<option>Rogue's Den"
		+ "<option>Al-kharid"
		+ "<option>Draynor Village"
		+ "<option>Edgeville"
		+ "<option>Catherby"
		+ "<option>Neitiznot"
		+ "</select><br /><br/>"
		+ "<br><br>\n"
		+ "Raw Food ID: <input name='FOODID' type='text' width='10' value='317' /><br />"
		+ "<br><br>\n")
public class BloddyCooker extends Script implements PaintListener,
		ServerMessageListener {

	final ScriptManifest properties = getClass().getAnnotation(
			ScriptManifest.class);

	final GrandExchange grandExchange = new GrandExchange();

	long runTime = 0;
	long seconds = 0;
	long minutes = 0;
	long hours = 0;
	int foodHour = 0;
	int currentXP = 0;
	int currentLVL = 0;
	int gainedXP = 0;
	int gainedLVL = 0;
	int xpPerHour = 0;
	int profitHour = 0;
	int stuffCooked = 0;
	public int expToLevel = 0;
	public long secToLevel = 0;
	public long minutesToLevel = 0;
	public long hoursToLevel = 0;
	public float secExp = 0;
	public float minuteExp = 0;
	public float hourExp = 0;

	public String status = "";
	private int startXP = 0;
	private int startLvl;
	public boolean showInventory = false;
	BufferedImage normal = null;
	BufferedImage clicked = null;
	public int guyID = 2271;
	public int[] fireID = { 2732, 25730, 2724, 12269, 2728, 21302, 2728 };
	private int FOODID;
	public int[] bBoothID = { 2213, 35647, 26972, 2213, 21301, 25808 };
	private int seersDoorID = 25819;
	RSTile rogueTile = new RSTile(3044, 4972);
	RSTile bankTile = new RSTile(3269, 3168);
	RSTile rangeTile = new RSTile(3272, 3179);
	RSTile rangeDoorTile = new RSTile(2815, 3437);
	public long startTime = System.currentTimeMillis();
	public String location = "";
	final ScriptManifest info = getClass().getAnnotation(ScriptManifest.class);
	public boolean mainClick = true;

	protected int getMouseSpeed() {
		return random(5, 8);
	}

	public boolean onStart(Map<String, String> args) {
		URLConnection url = null;
		BufferedReader in = null;
		BufferedWriter out = null;
		try {
			url = new URL(
					"http://www.bloddyharry.webs.com/scripts/BloddyCookerVERSION.txt")
					.openConnection();
			in = new BufferedReader(new InputStreamReader(url.getInputStream()));
			if (Double.parseDouble(in.readLine()) > info.version()) {
				JOptionPane.showMessageDialog(null,
						"Update found, please update.");
				JOptionPane
						.showMessageDialog(null,
								"Please choose 'BloddyCooker.java' in your scripts folder and hit 'Open'");
				JFileChooser fc = new JFileChooser();
				if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					url = new URL(
							"http://www.bloddyharry.webs.com/scripts/BloddyCooker.txt")
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
					log("Update canceled!");
			} else
				log("Update canceled!");
			if (in != null)
				in.close();
			if (out != null)
				out.close();
		} catch (IOException e) {
			log("Problem getting version :/");
			return false;
		}
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
		location = args.get("location");
		FOODID = Integer.parseInt(args.get("FOODID"));
		startTime = System.currentTimeMillis();
		if (location.equals("Edgeville")) {
			rangeTile = new RSTile(3079, 3495);
			bankTile = new RSTile(3094, 3493);
			rangeDoorTile = new RSTile(3080, 3501);
		}
		if (location.equals("Catherby")) {
			rangeTile = new RSTile(2817, 3443);
			bankTile = new RSTile(2809, 3441);
		}
		if (location.equals("Neitiznot")) {
			rangeTile = new RSTile(2342, 3809);
			bankTile = new RSTile(2337, 3807);
		}
		if (location.equals("Seers Village")) {
			rangeTile = new RSTile(2715, 3477);
			bankTile = new RSTile(2723, 3493);
			rangeDoorTile = new RSTile(2714, 3483);
		}
		if (isLoggedIn()) {
			startXP = skills.getCurrentSkillExp(Skills.getStatIndex("cooking"));
			startLvl = skills.getCurrentSkillLevel(Skills
					.getStatIndex("cooking"));
		}
		return true;
	}

	public void onFinish() {
		long millis = System.currentTimeMillis() - startTime;
		long hours = millis / (1000 * 60 * 60);
		millis -= hours * (1000 * 60 * 60);
		long minutes = millis / (1000 * 60);
		millis -= minutes * (1000 * 60);
		long seconds = millis / 1000;
		int gainedXP = skills.getCurrentSkillExp(Constants.STAT_COOKING)
				- startXP;
		JOptionPane.showMessageDialog(null,
				"Thank You For Using Bloddy Cooker!\n"
						+ "-------------------------------------------\n"
						+ "Ran for " + hours + ":" + minutes + ":" + seconds
						+ "\n" + "Cooked " + stuffCooked + " Food\n"
						+ "Gained " + gainedLVL + " levels\n"
						+ "Your level is "
						+ skills.getCurrentSkillLevel(Constants.STAT_COOKING)
						+ "\n" + "Gained " + gainedXP + "XP\n"
						+ "-------------------------------------------");

	}

	static final String[] browsers = { "google-chrome", "firefox", "opera",
			"konqueror", "epiphany", "seamonkey", "galeon", "kazehakase",
			"mozilla" };
	static final String errMsg = "Error attempting to launch web browser";

	public static void openURL(String url) {
		try {
			Class<?> d = Class.forName("java.awt.Desktop");
			d.getDeclaredMethod("browse", new Class[] { java.net.URI.class })
					.invoke(d.getDeclaredMethod("getDesktop").invoke(null),
							new Object[] { java.net.URI.create(url) });
		} catch (Exception ignore) {
			String osName = System.getProperty("os.name");
			try {
				if (osName.startsWith("Mac OS")) {
					Class.forName("com.apple.eio.FileManager")
							.getDeclaredMethod("openURL",
									new Class[] { String.class }).invoke(null,
									new Object[] { url });
				} else if (osName.startsWith("Windows"))
					Runtime.getRuntime().exec(
							"rundll32 url.dll,FileProtocolHandler " + url);
				else {
					boolean found = false;
					for (String browser : browsers)
						if (!found) {
							found = Runtime.getRuntime().exec(
									new String[] { "which", browser })
									.waitFor() == 0;
							if (found)
								Runtime.getRuntime().exec(
										new String[] { browser, url });
						}
					if (!found)
						throw new Exception(Arrays.toString(browsers));
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, errMsg + "\n"
						+ e.toString());
			}
		}
	}

	@Override
	public int loop() {
		if (!location.equals("Rogue's Den")) {
			return loop2();
		}
		setCameraAltitude(true);
		if (bank.getCount(FOODID) == 0 && bank.isOpen()
				&& !inventoryContains(FOODID)) {
			wait(random(600, 1000));
			if (bank.getCount(FOODID) == 0 && !inventoryContains(FOODID)) {
				ScreenshotUtil.takeScreenshot(true);
				log("damn, Out of raw food.. loggin out!");
				logOut();
			}
		}
		if (getMyPlayer().getAnimation() != -1) {
			status = "Cooking";
			wait(random(500, 2000));
			antiBan();
		}
		if (inventoryContains(FOODID) && getMyPlayer().getAnimation() == -1) {
			cook();
		}
		if (!inventoryContains(FOODID)) {
			bank();
		}
		return 0;
	}

	public int loop2() {
		setCameraAltitude(true);
		if (getInterface(513).getChild(2).getText().contains("How many")) {
			moveMouse(244, 394, 30, 30);
			wait(random(100, 200));
			atMenu("Make All");
		}
		if (getMyPlayer().getAnimation() != -1) {
			status = "Cooking";
			wait(random(500, 2000));
			antiBan();
		}
		if (bank.getCount(FOODID) == 0 && bank.isOpen()
				&& !inventoryContains(FOODID)) {
			wait(random(600, 1000));
			if (bank.getCount(FOODID) == 0 && !inventoryContains(FOODID)) {
				ScreenshotUtil.takeScreenshot(true);
				log("damn, Out of raw food.. loggin out!");
				logOut();
			}
		}
		if (inventoryContains(FOODID) && getMyPlayer().getAnimation() == -1) {
			if (atRange()) {
				cook();
			} else {
				walkToRange();
			}
		}
		if (!inventoryContains(FOODID)) {
			if (atBank()) {
				bank();
			} else if (!atBank() && !getMyPlayer().isMoving()) {
				walkToRange();
			}
		}
		return 0;
	}

	private int walkToRange() {
		final int random = random(1, 3);
		final int random2 = random(1, 3);

		// Catherby
		if (location.equals("Catherby")) {
			if (inventoryContains(FOODID)) {
				if (!atRange() && distanceTo(rangeDoorTile) >= 4) {
					walkTileMM(rangeDoorTile, 1, 1);
					wait(random(1, 700));
					antiBan2();
					return random(1000, 1400);
				} else if (distanceTo(rangeDoorTile) <= 4) {
					atDoorTiles(new RSTile(2816, 3439), new RSTile(2816, 3438));
					wait(random(100, 200));
					waitToStop();
					if (random == 1) {
						walkTileOnScreen(rangeTile);
						antiBan2();
						wait(random(300, 400));
					}
					if (random == 2) {
						walkTileMM(rangeTile);
						antiBan2();
						wait(random(300, 400));
					}
				}
			} else if (!inventoryContains(FOODID)) {
				if (atRange()) {
					if (random2 == 1) {
						walkTileOnScreen(new RSTile(2816, 3439));
						antiBan2();
						wait(random(200, 350));
						atDoorTiles(new RSTile(2816, 3439), new RSTile(2816,
								3438));
					}
					if (random2 == 2) {
						walkTileMM(new RSTile(2816, 3439));
						antiBan2();
						wait(random(200, 350));
						atDoorTiles(new RSTile(2816, 3439), new RSTile(2816,
								3438));
					}
				}
				if (!atRange()) {
					walkTileMM(bankTile, 1, 1);
					antiBan2();
					wait(random(1, 700));
					return random(1000, 1400);
				}
			}

		}

		// Neitiznot
		if (location.equals("Neitiznot") && !getMyPlayer().isMoving()) {
			if (inventoryContains(FOODID)) {
				if (!atRange()) {
					status = "Walking to Range";
					walkTileMM(rangeTile);
					wait(random(1, 900));
					antiBan2();
					return random(1500, 1800);
				}
			} else {
				if (!atBank()) {
					status = "Walking to Bank";
					walkTileMM(bankTile);
					wait(random(1, 900));
					antiBan2();
					return random(1500, 1800);
				}
			}
		}

		// Edgeville
		if (location.equals("Edgeville")) {
			if (inventoryContains(FOODID)) {
				if (!atRange() && distanceTo(rangeDoorTile) >= 4) {
					walkTileMM(rangeDoorTile, 1, 1);
					wait(random(1, 700));
					antiBan2();
					return random(1000, 1400);
				} else if (distanceTo(rangeDoorTile) <= 5) {
					atDoor(15514, 'n');
					wait(random(300, 400));
					waitToStop();
					walkTileOnScreen(new RSTile(3079, 3498));
					wait(random(800, 1300));
					atDoorTiles(new RSTile(3079, 3497), new RSTile(3079, 3496));
					wait(random(100, 200));
				}
			}
			if (!inventoryContains(FOODID)) {
				if (!atBank() && atRange()) {
					atDoorTiles(new RSTile(3079, 3497), new RSTile(3079, 3496));
					wait(random(100, 250));
					waitToStop();
					atDoorTiles(new RSTile(3080, 3501), new RSTile(3080, 3500));
					wait(random(100, 300));
					waitToStop();
					walkTileMM(bankTile, 2, 2);
					wait(random(1, 700));
					antiBan2();
					wait(random(1500, 2000));
					return random(1400, 1800);
				}
			}
		}

		// Al-kharid
		if (location.equals("Al-kharid") && !getMyPlayer().isMoving()) {
			if (inventoryContains(FOODID)) {
				if (!atRange()) {
					status = "Walking To Range";
					walkTo(rangeTile, 0, 2);
					wait(random(1, 800));
					antiBan2();
					return random(2000, 2500);
				}
			}
			if (!inventoryContains(FOODID)) {
				if (!atBank()) {
					status = "Walking To Bank";
					walkTo(bankTile, 2, 2);
					wait(random(1, 800));
					antiBan2();
					return random(2000, 2500);
				}
			}
		}
		
		final RSObject door = getNearestObjectByID(seersDoorID);
		// Seers Village
		if (location.equals("Seers Village") && !getMyPlayer().isMoving()) {
			if (inventoryContains(FOODID)) {
				if (!atRange() && distanceTo(rangeDoorTile) >= 4) {
					walkTileMM(rangeDoorTile, 1, 1);
					wait(random(1, 700));
					antiBan2();
					return random(1000, 1400);
				} else if (distanceTo(rangeDoorTile) <= 4) {
					if (door != null) {
						setCompass('n');
						wait(random(200, 300));
						atDoorTiles(new RSTile(2713, 3483), new RSTile(2713,
								3482));
						wait(random(650, 800));
						waitToStop();
					}
						walkTileMM(rangeTile);
						antiBan2();
						wait(random(600, 900));
					}
				}
			} else if (!inventoryContains(FOODID)) {
				if (atRange()) {
					walkTileMM(new RSTile(2713, 3482));
					antiBan2();
					wait(random(400, 500));
					atDoorTiles(new RSTile(2713, 3483), new RSTile(2713, 3482));
					wait(random(700, 800));
					walkTo(bankTile, 2, 2);
					wait(random(1, 800));
					antiBan2();
					return (random(2500, 3000));
				}
			}
		return 0;
	}

	public void bank() {

		// Rouge's Den
		if (location.equals("Rogue's Den")) {
			final RSNPC guy = getNearestNPCByID(guyID);
			if (guy.isOnScreen()) {
				mainClick = true;
				final RSNPC bBooth = getNearestNPCByName("Emerald Benedict");
				if (bank.isOpen()) {
					status = "Depositing";
					if (getInventoryCount() != 0) {
						bank.depositAll();
					}
					wait(random(300, 400));
					bank.atItem(FOODID, "Withdraw-All");
					wait(random(300, 400));
					bank.close();
				} else if (!bank.isOpen()) {
					if (isItemSelected()) {
						moveMouse(644, 170, 10, 10);
						clickMouse(true);
					}
					status = "Opening Bank";
					clickRSNPC(bBooth, "Bank");
					wait(random(200, 300));
				}
			} else if (!guy.isOnScreen()) {
				walkTo(guy.getLocation());
			}
		}

		// Al-Kharid && Draynor Village && Catherby && Seers Village
		if (location.equals("Al-kharid") || location.equals("Draynor Village")
				|| location.equals("Catherby")
				|| location.equals("Seers Village")) {
			final RSObject bBooth = getNearestObjectByID(bBoothID);
			if (bank.isOpen()) {
				status = "Depositing";
				if (getInventoryCount() != 0) {
					bank.depositAll();
				}
				wait(random(400, 500));
				bank.atItem(FOODID, "Withdraw-All");
				wait(random(400, 500));
			}
			if (distanceTo(bBooth) <= 4) {
				if (!bank.isOpen()) {
					if (bBooth != null) {
						atObject(bBooth, "Use-Quickly");
						wait(random(200, 300));
					}
					if (bBooth == null) {
					}
				}
			} else if (location.equals("Draynor Village")) {
				if (atNedHouse()) {
					atDoorTiles(new RSTile(3102, 3258), new RSTile(3101, 3258));
					if (isIdle()) {
						walkTo(new RSTile(3103, 3258), 1, 1);
						setCameraRotation(random(1, 120));
					}
				} else if (distanceTo(bBooth) >= 5 && !atNedHouse()) {
					walkTo(bBooth.getLocation());
				}
			}
		}

		// Edgeville
		if (location.equals("Edgeville")) {
			final RSObject bBooth = getNearestObjectByID(bBoothID);
			if (bank.isOpen()) {
				status = "Depositing";
				if (getInventoryCount() != 0) {
					bank.depositAll();
				}
				wait(random(400, 500));
				bank.atItem(FOODID, "Withdraw-All");
				wait(random(500, 600));
			}
			if (distanceTo(bBooth) <= 4) {
				if (!bank.isOpen()) {
					if (bBooth != null) {
						atObject(bBooth, "Use-Quickly");
						wait(random(200, 300));
					}
					if (bBooth == null) {
					}
				}
			}
		}

		// Neitiznot
		if (location.equals("Neitiznot")) {
			final RSObject bBooth = getNearestObjectByID(bBoothID);
			if (bank.isOpen()) {
				status = "Depositing";
				if (getInventoryCount() != 0) {
					bank.depositAll();
				}
				wait(random(400, 500));
				bank.atItem(FOODID, "Withdraw-All");
				wait(random(400, 500));
			}
			if (distanceTo(bBooth) <= 3) {
				if (!bank.isOpen()) {
					if (bBooth != null) {
						atObject(bBooth, "Use");
						wait(random(250, 300));
						waitToStop();
					}
					if (bBooth == null) {
					}
				}
			}
		}
	}

	public void cook() {
		if (!mainClick) {
			wait(random(600, 700));
			if (getMyPlayer().getAnimation() != -1) {
				wait(random(200, 300));
			}
		} else {
			mainClick = false;
		}
		if (getMyPlayer().getAnimation() == -1) {
			status = "Trying to cook";
			// Rouge's Den
			if (location.equals("Rogue's Den")) {
				final RSObject fire = getNearestObjectByID(fireID);
				if (distanceTo(fire) <= 4) {
					if (fire != null) {
						status = "Trying to cook";
						if (!isItemSelected()
								&& getMyPlayer().getAnimation() == -1) {
							atInventoryItem(FOODID, "Use");
							wait(random(200, 300));
						}
						if (isItemSelected()
								&& getMyPlayer().getAnimation() == -1) {
							atObject(fire, "Fire");
							wait(random(1700, 1800));
							moveMouse(244, 394, 30, 30);
							wait(random(100, 200));
							atMenu("Cook All");
							wait(random(1600, 2000));
						}
					}
				} else if (distanceTo(fire) >= 5) {
					walkTo(fire.getLocation());
				}
			}

			// Al-Kharid & Catherby & Seers Village
			if (location.equals("Al-kharid") || location.equals("Catherby")
					|| location.equals("Seers Village")) {
				final RSObject range = getNearestObjectByID(fireID);
				if (range != null) {
					status = "Trying to cook";
					if (!isItemSelected()) {
						atInventoryItem(FOODID, "Use");
						wait(random(200, 300));
					}
					if (isItemSelected()) {
						atObject(range, "Range");
						wait(random(1300, 1400));
						waitToStop();
						wait(random(150, 200));
						moveMouse(244, 394, 30, 30);
						wait(random(100, 200));
						atMenu("Cook All");
						wait(random(1600, 2000));
					}
				}
			}

			// Draynor Village
			if (location.equals("Draynor Village")) {
				final RSObject range = getNearestObjectByID(fireID);
				if (range != null && atNedHouse()) {
					status = "Trying to cook";
					if (!isItemSelected()) {
						atInventoryItem(FOODID, "Use");
						wait(random(200, 300));
					}
					if (isItemSelected()) {
						atObject(range, "Fireplace");
						wait(random(1000, 1200));
						moveMouse(244, 394, 30, 30);
						wait(random(100, 200));
						atMenu("Cook All");
						wait(random(1600, 2000));
					}
				} else if (distanceTo(new RSTile(3102, 3258)) <= 3
						&& !atNedHouse()) {
					atDoorTiles(new RSTile(3102, 3258), new RSTile(3101, 3258));
					setCameraRotation(random(1, 120));
					if (isIdle()) {
						walkTo(new RSTile(3100, 3257), 1, 1);
					}
				} else if (distanceTo(new RSTile(3102, 3258)) >= 4) {
					if (isIdle()) {
						walkTo(new RSTile(3103, 3258), 1, 1);
					}
				}
			}

			// Edgeville
			if (location.equals("Edgeville")) {
				final RSObject range = getNearestObjectByID(fireID);
				if (range != null) {
					status = "Trying to cook";
					if (!isItemSelected()) {
						atInventoryItem(FOODID, "Use");
						wait(random(200, 300));
					}
					if (isItemSelected()) {
						atObject(range, "Stove");
						wait(random(1300, 1500));
						waitToStop();
						wait(random(100, 200));
						moveMouse(244, 394, 30, 30);
						wait(random(100, 200));
						atMenu("Cook All");
						wait(random(1600, 2000));
					}
				}
			}

			// Neitiznot
			if (location.equals("Neitiznot")) {
				final RSObject range = getNearestObjectByID(fireID);
				if (range != null) {
					status = "Trying to cook";
					if (!isItemSelected()) {
						atInventoryItem(FOODID, "Use");
						wait(random(200, 300));
					}
					if (isItemSelected()) {
						atObject(range, "Clay oven");
						wait(random(1300, 1400));
						waitToStop();
						moveMouse(244, 394, 30, 30);
						wait(random(100, 200));
						atMenu("Cook All");
						wait(random(1600, 2000));
					}
				}
			}
		}
	}

	public void waitToStop() {
		while (getMyPlayer().isMoving()) {
			wait(200);
		}
	}

	public boolean atNedHouse() {
		return getMyPlayer().getLocation().getX() <= 3101
				&& getMyPlayer().getLocation().getX() >= 3095
				&& getMyPlayer().getLocation().getY() <= 3260
				&& getMyPlayer().getLocation().getY() >= 3256;
	}

	public void logOut() {
		moveMouse(754, 10, 10, 10);
		clickMouse(true);
		moveMouse(642, 378, 20, 15);
		clickMouse(true);
		wait(random(2000, 3000));
		stopScript();
	}

	public boolean atRogue() {
		return distanceTo(rogueTile) <= 6;
	}

	public boolean atBank() {
		if (location.equals("Neitiznot")) {
			return distanceTo(bankTile) <= 3;
		}
		return distanceTo(bankTile) <= 4;
	}

	public boolean atRange() {
		if (location.equals("Catherby")) {
			return distanceTo(rangeTile) <= 3;
		} 
		if (location.equals("Neitiznot")) {
			return distanceTo(rangeTile) <= 1;
		} else {
			return distanceTo(rangeTile) <= 3;
		}
	}

	public boolean antiBan() {
		int randomNumber = random(1, 18);
		if (randomNumber <= 18) {
			if (randomNumber == 1) {
				randomHoverPlayer();
			}
			if (randomNumber == 2) {
				moveMouse(random(50, 700), random(50, 450), 2, 2);
				wait(random(1000, 1500));
				moveMouse(random(50, 700), random(50, 450), 2, 2);
			}
			if (randomNumber == 3) {
				openRandomTab();
				wait(random(100, 500));
				moveMouse(522, 188, 220, 360);
				wait(random(500, 2800));
			}
			if (randomNumber == 4) {
				wait(random(100, 200));
				moveMouse(random(50, 700), random(50, 450), 2, 2);
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
				wait(random(100, 200));
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
						wait(random(50, 100));
						moveMouse(675, 268, 20, 20);
						wait(random(500, 1700));
					}
					if (randomNumber == 13) {
						moveMouse(random(50, 700), random(50, 450), 2, 2);
						setCameraRotation(random(1, 360));
					}
					if (randomNumber == 14) {
						openTab(TAB_STATS);
						wait(random(50, 100));
						moveMouse(675, 268, 20, 20);
						wait(random(500, 1700));
					}
					if (randomNumber == 15) {
						randomHoverPlayer();
					}
				}

			}
		}
		return true;
	}

	public boolean antiBan2() {
		int randomNumber = random(1, 18);
		if (randomNumber <= 18) {
			if (randomNumber == 1) {
				setCameraRotation(random(1, 360));
			}
			if (randomNumber == 2) {
				moveMouse(random(50, 700), random(50, 450), 2, 2);
				wait(random(200, 300));
				moveMouse(random(50, 700), random(50, 450), 2, 2);
			}
			if (randomNumber == 3) {
				moveMouse(522, 188, 220, 360);
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
		}
		return true;
	}

	private void randomHoverPlayer() {
		int randomNumber = random(1, 10);
		if (randomNumber <= 10) {
			if (randomNumber == 1) {
				RSPlayer p = getNearestPlayerByLevel(1, 130);
				if ((p != null) && tileOnScreen(p.getLocation())) {
					moveMouse(p.getScreenLocation(), 40, 40);
					wait(random(450, 650));
				}
				if (randomNumber == 2) {
					if ((p != null) && tileOnScreen(p.getLocation())) {
						moveMouse(p.getScreenLocation(), 40, 40);
						wait(random(100, 400));
						clickMouse(false);
						wait(random(1000, 1700));
						moveMouse(random(50, 700), random(50, 450), 2, 2);
					}
				}
			}
		}
	}

	private void openRandomTab() {
		int randomNumber = random(1, 18);
		if (randomNumber <= 18) {
			if (randomNumber == 1) {
				openTab(TAB_STATS);
				wait(random(100, 200));
				moveMouse(675, 268, 20, 20);
				wait(random(500, 1700));
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
				openTab(TAB_STATS);
			}
			if (randomNumber == 8) {
				openTab(TAB_QUESTS);
			}
			if (randomNumber == 9) {
				openTab(TAB_CLAN);
			}
			if (randomNumber == 10) {
				openTab(TAB_MUSIC);
			}
			if (randomNumber == 11) {
				openTab(TAB_ACHIEVEMENTDIARIES);
			}
		}
	}

	// Credits to Garrett
	@Override
	public void onRepaint(Graphics g) {
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

		currentXP = skills.getCurrentSkillExp(Skills.getStatIndex("cooking"));
		currentLVL = skills
				.getCurrentSkillLevel(Skills.getStatIndex("cooking"));
		gainedXP = currentXP - startXP;
		gainedLVL = currentLVL - startLvl;
		xpPerHour = (int) ((3600000.0 / (double) runTime) * gainedXP);
		foodHour = (int) ((3600000.0 / (double) runTime) * stuffCooked);
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
			if (getCurrentTab() == TAB_INVENTORY) {
				if (showInventory == false) {
					g.setColor(new Color(0, 0, 0, 190));
					g.fillRoundRect(555, 210, 175, 250, 0, 0);

					g.setColor(Color.RED);
					g.draw3DRect(555, 210, 175, 250, true);

					g.setColor(Color.WHITE);
					int[] coords = new int[] { 225, 240, 255, 270, 285, 300,
							315, 330, 345, 360, 375, 390, 405, 420, 435, 450,
							465, 480 };
					g.setColor(Color.RED);
					g.setFont(new Font("Italianate", Font.BOLD, 14));
					g.drawString(properties.name(), 561, coords[0]);
					g.drawString("Version: " + properties.version(), 561,
							coords[1]);
					g.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
					g.setColor(Color.LIGHT_GRAY);
					g.drawString("Run Time: " + hours + ":" + minutes + ":"
							+ seconds, 561, coords[2]);
					g.setColor(Color.RED);
					g.drawString(stuffCooked + " food cooked", 561, coords[4]);
					g.setColor(Color.LIGHT_GRAY);
					g.drawString("Food/hour: " + foodHour, 561, coords[5]);
					g.setColor(Color.RED);
					g.drawString("XP Gained: " + gainedXP, 561, coords[6]);
					g.setColor(Color.LIGHT_GRAY);
					g.drawString("XP/Hour: " + xpPerHour, 561, coords[7]);
					g.setColor(Color.RED);
					g.drawString("Your level is " + currentLVL, 561, coords[9]);
					g.setColor(Color.LIGHT_GRAY);
					g.drawString("Lvls Gained: " + gainedLVL, 561, coords[10]);
					g.setColor(Color.RED);
					g.drawString("XP To Next Level: "
							+ skills.getXPToNextLevel(Skills
									.getStatIndex("cooking")), 561, coords[11]);
					g.setColor(Color.LIGHT_GRAY);
					g.drawString("% To Next Level: "
							+ skills.getPercentToNextLevel(Skills
									.getStatIndex("cooking")), 561, coords[12]);
					g.setColor(Color.RED);
					g.drawString("Status: " + status, 561, coords[14]);
					g.setColor(Color.LIGHT_GRAY);
					g.drawString("Location: " + location, 561, coords[15]);
				}
				g.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
				g.setColor(new Color(0, 0, 0, 200));
				g.fillRoundRect(6, 315, 120, 20, 0, 0);
				g.setColor(Color.red);
				g.draw3DRect(6, 315, 120, 20, true);
				g.setColor(Color.white);
				g.drawString("See Inventory", 10, 330);

				Mouse m = Bot.getClient().getMouse();
				if (m.x >= 6 && m.x < 6 + 120 && m.y >= 315 && m.y < 315 + 30) {
					showInventory = true;
				} else {
					showInventory = false;
				}
				if (hours == 2 && minutes == 0 && seconds == 0) {
					log("w00t! ran for 2 hours! taking screenie :)");
					ScreenshotUtil.takeScreenshot(true);
				}
				if (hours == 3 && minutes == 0 && seconds == 0) {
					log("awesome! ran for 3 hours! taking screenie :)");
					ScreenshotUtil.takeScreenshot(true);
				}
				if (hours == 4 && minutes == 0 && seconds == 0) {
					log("Epic! ran for 4 hours! taking screenie :)");
					ScreenshotUtil.takeScreenshot(true);
				}
				if (hours == 5 && minutes == 0 && seconds == 0) {
					log("Hell yeaH! ran for 5 hours! taking screenie :)");
					ScreenshotUtil.takeScreenshot(true);
				}
				if (hours == 6 && minutes == 0 && seconds == 0) {
					log("keep it up! ran for 6 hours! taking screenie :)");
					ScreenshotUtil.takeScreenshot(true);
				}
				if (hours == 7 && minutes == 0 && seconds == 0) {
					log("NICE NICE! ran for 7 hours! taking screenie :)");
					ScreenshotUtil.takeScreenshot(true);
				}
				if (hours == 8 && minutes == 0 && seconds == 0) {
					log("SICK! ran for 8 hours! taking screenie :)");
					ScreenshotUtil.takeScreenshot(true);
				}
				if (hours == 9 && minutes == 0 && seconds == 0) {
					log("DA PERFECT PROGGY! ran for 9 hours! taking screenie :)");
					ScreenshotUtil.takeScreenshot(true);
				}
				if (hours == 10 && minutes == 0 && seconds == 0) {
					log("FUCKING AWESOME DUDE! ran for 10 hours! taking screenie :)");
					ScreenshotUtil.takeScreenshot(true);
				}
			}
		}
	}

	@Override
	public void serverMessageRecieved(ServerMessageEvent e) {
		final String serverString = e.getMessage();
		if (serverString.contains("cook") || serverString.contains("roast")) {
			stuffCooked++;
		}
		if (serverString.contains("You've just advanced")) {
			log("Congrats on level up, Screenshot taken!");
			ScreenshotUtil.takeScreenshot(true);
			wait(random(1500, 2500));
			if (canContinue()) {
				clickContinue();
			}
		}
	}
}