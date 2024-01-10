import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.rsbot.bot.Bot;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.script.Calculations;
import org.rsbot.script.Constants;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSArea;
import org.rsbot.script.wrappers.RSItemTile;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSTile;

@ScriptManifest(authors = "Master8899", name = "Master's Crimson Swift Hunter", version = 1.00, category = "Hunter", description = "<font size='6' color='Black' face='Arial'>"
		+ "<center>Master's Bird Hunter</font>"
		+ "<font size='4' color='Black' face='Arial'>"
		+ "<br><br>"
		+ "Start at the Crimson Swift hunting spot."
		+ "<br><br>"
		+ "<input type='checkbox' name='boneCheck' id='boneCheck' value='true' /><label for=\"debug\">Bury Bones?</label>"
		+ "<br><br>" + "</font>")
public class MasterSwiftHunter extends Script implements PaintListener,
		ServerMessageListener {

	//VARIABLES
	private final int birdSnare = 10006;
	private RSArea swiftArea = new RSArea(new RSTile(2601, 2914), new RSTile(
			2620, 2941));
	private final int meatID = 9978;
	RSTile snareTile;
	RSObject snareObj;
	private boolean layedTrap = false;
	private int birdsCaught = 0;
	private int timesFailed = 0;
	private int timesFallen = 0;
	private String status = "";
	private int levelsGained = 0;
	private int startingLevel;
	private int boneID = 526;
	private boolean droppingBones;
	private long startTime;
	private int startExp;
	private long xpGained, xpHour;
	private boolean pickUp;
	private Color tileColor = new Color(255, 255, 0, 115);;

	//END VARIABLES

	public boolean onStart(Map<String, String> args) {
		URLConnection url = null;
		BufferedReader in = null;
		BufferedWriter out = null;
		// Ask the user if they'd like to check for an update...
		if (JOptionPane.showConfirmDialog(null,
				"Would you like to check for updates?") == 0) { // If
			// they
			// would,
			// continue
			try {
				// Open the version text file
				url = new URL(
						"http://masterscripter.webs.com/scripts/MasterCrimsonSwiftHunterVERSION.txt")
						.openConnection();
				// Create an input stream for it
				in = new BufferedReader(new InputStreamReader(url
						.getInputStream()));
				// Check if the current version is outdated
				if (Double.parseDouble(in.readLine()) > 1.0) {
					// If it is, check if the user would like to update.
					if (JOptionPane.showConfirmDialog(null,
							"Update found. Do you want to update?") == 0) {
						// If so, allow the user to choose the file to be
						// updated.
						JOptionPane
								.showMessageDialog(null,
										"Please choose 'MasterSwiftHunter.java' in your scripts folder.");
						JFileChooser fc = new JFileChooser();
						// Make sure "Open" was clicked.
						if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
							// If so, set up the URL for the .java file and set
							// up the IO.
							url = new URL(
									"http://www.masterscripter.webs.com/scripts/MasterSwiftHunter.java")
									.openConnection();
							in = new BufferedReader(new InputStreamReader(url
									.getInputStream()));
							out = new BufferedWriter(new FileWriter(fc
									.getSelectedFile().getPath()));
							String inp;
							/*
							 * Until we reach the end of the file, write the
							 * next line in the file and add a new line. Then
							 * flush the buffer to ensure we lose no data in the
							 * process.
							 */
							while ((inp = in.readLine()) != null) {
								out.write(inp);
								out.newLine();
								out.flush();
							}
							// Notify the user that the script has been updated,
							// and a recompile and reload is needed.
							log("Script successfully downloaded. Please recompile and reload your scripts!");
							return false;
						} else
							log("Update canceled");
					} else
						log("Update canceled");
				} else
					JOptionPane.showMessageDialog(null,
							"You have the latest version.");
				// User has the
				// latest
				// version. Tell
				// them!
				if (in != null)
					in.close();
				if (out != null)
					out.close();
			} catch (IOException e) {
				log("Problem getting version.");
				return false; // Return false if there was a problem
			}
		}
		droppingBones = args.get("boneCheck") != null ? true : false;
		startTime = System.currentTimeMillis();
		return true;
	}

	public boolean isLagging() {
		return Bot.getClient().getLoginIndex() == 11;
	}

	public int loop() {
		try {
			if (!isLoggedIn() || isLagging()) {
				return random(600, 1200);
			}
			if (!layedTrap && !pickUp && !inventoryContains(birdSnare)) {
				log("You do not have any snares! Stopping script.");
				stopScript();
			}
			if (pickUp) {
				if (!pickupTrap()) {
					return random(300, 600);
				} else {
					pickUp = false;
				}
			}
			if (isInventoryFull()) {
				invCheck();
			}
			if (atSwifts() || layedTrap) {
				if (!layedTrap) {
					if (swiftLayTrap()) {
						swiftSnareHandler();
					}
				} else {
					swiftSnareHandler();
				}
			} else {
				if (!layedTrap)
					walkToSwifts();
			}
		} catch (NullPointerException e) {
			throw e;
		}
		return 100;
	}

	private void swiftSnareHandler() {
		//19175 normal trap
		//19179, 19180 when bird caught
		//19176,19174 when trap down
		try {
			if (!(getObjectAt(snareTile).getID() == 19175)) {
				wait(random(400, 750));
				if ((getObjectAt(snareTile).getID() == 19179)
						|| (getObjectAt(snareTile).getID() == 19180)) {
					tileColor = new Color(0, 255, 0, 115);
					status = "Getting bird...";
					wait(random(400, 900));
					if (!swiftRetreiveBird()) {
						return;
					} else {
						tileColor = new Color(0, 0, 255, 115);
					}
				} else if ((getObjectAt(snareTile).getID() == 19176)
						|| (getObjectAt(snareTile).getID() == 19174)) {
					tileColor = new Color(255, 0, 0, 115);
					status = "Getting snare...";
					timesFailed++;
					wait(random(400, 900));
					if (!swiftRetrieveTrap()) {
						return;
					} else {
						tileColor = new Color(0, 0, 255, 115);
					}
				}
				layedTrap = false;
			} else {
				tileColor = new Color(255, 255, 0, 115);
				invCheck();
				antiBan();
			}
		} catch (Exception e) {

		}
	}

	private boolean invCheck() {
		if (getInventoryCount(meatID) > 3) {
			status = "Dropping meat...";
			int amountOfMeat = getInventoryCount(meatID);
			int r = random(1, amountOfMeat + 1);
			for (int i = 0; i <= r; i++) {
				atInventoryItem(meatID, "Drop");
				wait(random(500, 900));
			}
			return true;
		}
		if (!droppingBones && getInventoryCount(boneID) > 3) {
			status = "Dropping bones...";
			int amountOfBones = getInventoryCount(boneID);
			int r = random(1, amountOfBones + 1);
			for (int i = 0; i <= r; i++) {
				atInventoryItem(boneID, "Drop");
				wait(random(500, 900));
			}
			return true;
		} else if (getInventoryCount(boneID) > 3) {
			status = "Burying bones...";
			int amountOfBones = getInventoryCount(boneID);
			int r = random(1, amountOfBones + 1);
			for (int i = 0; i <= r; i++) {
				atInventoryItem(boneID, "Bury");
				wait(random(500, 900));
				waitForAnim(3000);
				wait(random(500, 900));
			}
			return true;
		}
		return false;
	}

	private boolean pickupTrap() {
		status = "Picking up snare...";
		RSItemTile itemTile = getNearestGroundItemByID(birdSnare);
		if (itemTile == null)
			return true;
		if (atTile(itemTile, "Lay Bird snare")) {
			wait(random(750, 1100));
			while (animationIs(5208)) {
				wait(100);
			}
			wait(random(500, 900));
		} else {
			return false;
		}
		if (getObjectAt(snareTile) != null)
			return true;
		return false;
	}

	private boolean swiftRetrieveTrap() {
		RSObject brokenSnare = getObjectAt(snareTile);
		if (atObject(brokenSnare, "Dismantle Bird snare")) {
			wait(random(1000, 1400));
			while (animationIs(5207)) {
				wait(100);
			}
			wait(random(500, 900));
			if (getObjectAt(snareTile) == null)
				return true;
		}
		return false;
	}

	private boolean swiftRetreiveBird() {
		RSObject brokenSnare = getObjectAt(snareTile);
		if (brokenSnare == null)
			return true;
		if (atObject(brokenSnare, "Check Bird snare")) {
			wait(random(1000, 1400));
			while (animationIs(5207)) {
				wait(100);
			}
			wait(random(500, 900));
			if (getObjectAt(snareTile) == null)
				return true;
		}
		return false;
	}

	private boolean swiftLayTrap() {
		status = "Laying snare...";
		while (getMyPlayer().isMoving()) {
			wait(100);
		}
		snareTile = getMyPlayer().getLocation();
		int before = getInventoryCount(birdSnare);
		if (atInventoryItem(birdSnare, "Lay")) {
			wait(random(750, 1100));
			while (animationIs(5208)) {
				wait(100);
			}
			wait(random(500, 900));
			if (getInventoryCount(birdSnare) < before)
				layedTrap = true;
			return true;
		}
		return false;
	}

	private boolean atSwifts() {
		if (swiftArea.contains(getMyPlayer().getLocation()))
			return true;
		return false;
	}

	private void walkToSwifts() {
		status = "Walking to swifts...";
		RSTile walk = new RSTile(2608, 2923);
		if (tileOnMap(walk)) {
			walkTileMM(walk, 5, 5);
			wait(random(650, 1000));
			while (getMyPlayer().isMoving()) {
				wait(100);
			}
			wait(random(250, 500));
		} else {
			RSTile[] path = generateFixedPath(walk);
			Path(path);
		}
	}

	public Boolean Path(RSTile[] tiles) {
		int length = tiles.length, i = 0, error = 0;
		while (i < length) {
			Point myTile = tileToMinimap(tiles[i]);

			if (distanceTo(tiles[i]) > 5) {
				moveMouse(myTile);
				try {
					clickMouse(true);
				} catch (NullPointerException e) {
				}
			}
			if (waitToMove(1000))
				while (getMyPlayer().isMoving() && distanceTo(tiles[i]) > 5)
					if (!isRunning() && getEnergy() > random(60, 100)) {
						setRun(true);
						wait(random(500, 1000));
					} else
						wait(random(25, 30));

			if (distanceTo(tiles[i]) <= 5) {
				error = 0;
				i++;
			} else
				error++;

			if (error >= 100) {
				log.info("There was a error walking to: " + tiles[i].getX()
						+ "," + tiles[i].getY());
				return false;
			}

		}

		if (distanceTo(tiles[length - 1]) <= 5) {
			return true;
		} else
			return false;

	}

	public void antiBan() {
		status = "Antiban...";
		final int ranNo = random(0, 1900);
		switch (ranNo) {
		case 1:
		case 2:
		case 5:
			wait(random(2000, 2500));
			break;
		case 94:
		case 95:
		case 96:
		case 97:
			int angle = getCameraAngle() + random(-90, 90);
			if (angle < 0) {
				angle = 0;
			}
			if (angle > 359) {
				angle = 0;
			}
			setCameraRotation(angle);
			break;
		case 47:
		case 48:
			if (getCurrentTab() != Constants.TAB_STATS) {
				openTab(Constants.TAB_STATS);
				wait(random(500, 750));
			}
			break;
		case 131:
			if (getCurrentTab() != Constants.TAB_INVENTORY) {
				openTab(Constants.TAB_INVENTORY);
			}
			break;
		case 23:
		case 25:
			moveMouse(random(0, 750), random(0, 500), 20);
			wait(random(100, 300));
			break;
		case 167:
			openTab(Constants.TAB_FRIENDS);
		}
	}

	private void highlightTile(final Graphics g, final RSTile t,
			final Color outline, final Color fill) {
		if (Calculations.tileToScreen(t) == null) {
			return;
		}
		final Point pn = Calculations.tileToScreen(t.getX(), t.getY(), 0, 0, 0);
		final Point px = Calculations.tileToScreen(t.getX(), t.getY(), 1, 0, 0);
		final Point py = Calculations.tileToScreen(t.getX(), t.getY(), 0, 1, 0);
		final Point pxy = Calculations
				.tileToScreen(t.getX(), t.getY(), 1, 1, 0);
		if (py.x == -1 || pxy.x == -1 || px.x == -1 || pn.x == -1) {
			return;
		}
		g.setColor(outline);
		g.drawPolygon(new int[] { py.x, pxy.x, px.x, pn.x }, new int[] { py.y,
				pxy.y, px.y, pn.y }, 4);
		g.setColor(fill);
		g.fillPolygon(new int[] { py.x, pxy.x, px.x, pn.x }, new int[] { py.y,
				pxy.y, px.y, pn.y }, 4);
	}

	@Override
	public void onRepaint(Graphics g) {
		long time = (System.currentTimeMillis() - startTime) / 1000;
		if (time == 0)
			return;
		long millis = System.currentTimeMillis() - startTime;
		long hours = millis / (1000 * 60 * 60);
		millis -= hours * (1000 * 60 * 60);
		long minutes = millis / (1000 * 60);
		millis -= minutes * (1000 * 60);
		long seconds = millis / 1000;
		if (startExp == 0) {
			startExp = skills.getCurrentSkillExp(STAT_HUNTER);
		}
		xpGained = skills.getCurrentSkillExp(STAT_HUNTER) - startExp;
		if (startingLevel == 0) {
			startingLevel = skills.getCurrentSkillLevel(STAT_HUNTER);
		}
		levelsGained = (skills.getCurrentSkillLevel(STAT_HUNTER) - startingLevel);
		if (time > 0) {
			xpHour = (xpGained * 60 * 60) / time;
		}
		if (snareTile != null) {
			highlightTile(g, snareTile, tileColor, tileColor);
			final Point p = tileToMinimap(snareTile);
			if (p.x != -1 || p.y != -1) {
				g.setColor(new Color(0, 180, 0, 255));
				g.fillRect(p.x + 1, p.y + 1, 4, 4);
			}
		}
		//This paint was made using Enfilade's Paint Maker
		g.setColor(new Color(178, 34, 34, 150));
		g.fillRoundRect(546, 204, 190, 261, 20, 20);
		g.setFont(new Font("Gill Sans MT", 0, 21));
		g.setColor(new Color(255, 255, 255));
		g.drawString("Master's Swift Hunter", 549, 226);
		g.setFont(new Font("Gill Sans MT", 0, 18));
		g.drawString("Run Time: " + hours + " : " + minutes + " : " + seconds,
				548, 253);
		g.setFont(new Font("Gill Sans MT", 0, 17));
		g.drawString("Snares Succeeded: " + birdsCaught, 548, 285);
		g.drawString("Total Failed Snares: " + timesFailed, 548, 313);
		g.drawString("Total Fallen Snares: " + timesFallen, 548, 341);
		g.drawString("XP Gained: " + xpGained, 548, 369);
		g.drawString("XP / Hour: " + xpHour, 548, 400);
		g.drawString("Levels Gained: " + levelsGained, 548, 425);
		g.setFont(new Font("Gill Sans MT", 0, 19));
		g.drawString("Status: " + status, 548, 453);
		//BAR
		g.setFont(new Font("Calibri", 1, 20));
		g.setColor(new Color(255, 0, 0, 175)); //RED
		g.fillRoundRect(3, 317, 513, 22, 20, 20);
		g.setColor(new Color(0, 255, 0, 200)); //GREEN
		g.fillRoundRect(3, 317, (int) (skills
				.getPercentToNextLevel(STAT_HUNTER) * 5.13), 22, 20, 20);
		g.setColor(new Color(255, 255, 255, 100)); //GLOSS		
		g.drawString(skills.getPercentToNextLevel(STAT_HUNTER) + "% to: "
				+ (skills.getCurrentSkillLevel(STAT_HUNTER) + 1) + " Hunter",
				183, 333);
		g.fillRoundRect(3, 317, 511, 11, 15, 15);
		g.setColor(new Color(0, 0, 0, 255));
		g.drawString(skills.getPercentToNextLevel(STAT_HUNTER) + "% to: "
				+ (skills.getCurrentSkillLevel(STAT_HUNTER) + 1) + " Hunter",
				182, 333);
	}

	@Override
	public void serverMessageRecieved(ServerMessageEvent e) {
		String message = e.getMessage().toLowerCase();
		if (message.contains("caught")) {
			birdsCaught++;
		}
		if (message.contains("fallen")) {
			timesFallen++;
			pickUp = true;
			pickupTrap();
		}
	}
}