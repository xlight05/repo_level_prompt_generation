import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;

import javax.imageio.ImageIO;

import org.rsbot.bot.Bot;
import org.rsbot.bot.input.Mouse;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.script.Constants;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSInterface;
import org.rsbot.script.wrappers.RSInterfaceChild;
import org.rsbot.script.wrappers.RSNPC;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSTile;
import org.rsbot.util.ScreenshotUtil;

@ScriptManifest(authors = { "Gribonn53" }, category = "Woodcutting", name = "Gribonn's Magics Chopper", version = 1.10, description = "<html>"
		+ "<center><h2>Gribonn's Magics Chopper 1.1</h2></center>"
		+ "<center>Start This Script near Magic trees, bank or anywhere between them</center>"
		+ "<center>Location: "
		+ "<select name='location'>"
		+ "<option>Seers village (not Sorcerer's Tower)"
		+ "<option>Seers village (Sorcerer's Tower)"
		+ "</select>"
		+ "</center>"
		+ "<center>Chat responder: "
		+ "<select name='responder'>"
		+ "<option>Yes"
		+ "<option>No"
		+ "</select>"
		+ "</center>"
		+ "<center>Show paint: "
		+ "<select name='showpaint'>"
		+ "<option>Yes"
		+ "<option>No"
		+ "</select>" + "</center>" + "</html>")
public class GibsMagicsChopper extends Script implements PaintListener,
		ServerMessageListener {
	public double getVersion() {
		return 1.1;
	}

	public String status = "";
	public String location = "";
	public int treeID = 1306;
	public int[] hatchetID = { 1351, 1349, 1353, 1361, 1355, 1357, 1359, 4031,
			6739, 13470, 14108 };
	public int[] bankBoothID = { 11758, 11402, 34752, 35647, 2213, 25808, 2213,
			26972, 27663, 4483, 14367, 19230, 29085, 12759, 6084, 24914 };
	public int logs = 0;
	public int levels = 0;
	public int magicID = 1513;
	public int[] bankerID = { 494, 495 };
	public int profit;
	public int startexp;
	public int magicCost = grandExchange.loadItemInfo(magicID).getMarketPrice();
	public long startTime;
	public boolean showPaint, showAveraging, showGained, end = false,
			chatResponder;
	public RSTile banktileS1 = new RSTile(2724, 3492);
	public RSTile treetileS1 = new RSTile(2694, 3425);
	public RSTile treetileS2 = new RSTile(2701, 3396);
	public RSTile[] magicsToBankS1 = { new RSTile(2694, 3425),
			new RSTile(2701, 3437), new RSTile(2707, 3447),
			new RSTile(2717, 3456), new RSTile(2719, 3469),
			new RSTile(2719, 3479), new RSTile(2724, 3492) };
	public RSTile[] bankTomagicsS1 = reversePath(magicsToBankS1);
	public RSTile[] magicsToBankS2 = { new RSTile(2701, 3396),
			new RSTile(2713, 3398), new RSTile(2718, 3409),
			new RSTile(2722, 3422), new RSTile(2727, 3435),
			new RSTile(2728, 3451), new RSTile(2726, 3464),
			new RSTile(2726, 3478), new RSTile(2725, 3490) };
	public RSTile[] bankTomagicsS2 = reversePath(magicsToBankS2);
	public String lastUser = "";
	BufferedImage normal = null;
	BufferedImage clicked = null;

	@Override
	protected int getMouseSpeed() {
		return random(4, 6);
	}

	public boolean onStart(Map<String, String> args) {
		URLConnection url = null;
		BufferedReader in = null;
		BufferedWriter out = null;
		try {
			url = new URL(
					"http://gribonn53.webs.com/scripts/GibsMagicsChopperVersion.txt")
					.openConnection();
			in = new BufferedReader(new InputStreamReader(url.getInputStream()));
			if (Double.parseDouble(in.readLine()) > getVersion()) {
				JOptionPane
						.showMessageDialog(null,
								"Update found. Please check the thread to get newer version");
			} else
				in.close();
			if (out != null)
				out.close();
		} catch (IOException e) {
			log("Problem getting version");
		}
		startTime = System.currentTimeMillis();
		final String paint = args.get("showpaint");
		if (paint.equals("Yes")) {
			showPaint = true;
		} else if (paint.equals("No")) {
			showPaint = false;
		}
		final String chatRespond = args.get("responder");
		if (chatRespond.equals("Yes")) {
			chatResponder = true;
		} else if (chatRespond.equals("No")) {
			chatResponder = false;
		}
		final String place = args.get("location");
		if (place.equals("Seers village (not Sorcerer's Tower)")) {
			location = "S1";
		} else if (place.equals("Seers village (Sorcerer's Tower)")) {
			location = "S2";
		}
		try {
			final URL cursorURL = new URL("http://i44.tinypic.com/2z7l8k0.png");
			final URL cursor80URL = new URL(
					"http://i44.tinypic.com/29usn4k.png");
			normal = ImageIO.read(cursorURL);
			clicked = ImageIO.read(cursor80URL);
		} catch (MalformedURLException e) {
			log("Unable to buffer cursor.");
		} catch (IOException e) {
			log("Unable to open cursor image.");
		}
		return true;
	}

	public boolean chatResponderFunc() {
		final int random = random(1, 13);
		final String lastChatMsg = lastChatMessage().toLowerCase();
		if (lastChatMsg.contains("wc")
				|| lastChatMsg.contains("wood")
				|| lastChatMsg.contains("cut")) {
			if (lastChatMsg.contains("lvl")
					|| lastChatMsg.contains("levl")
					|| lastChatMsg.contains("level")) {
				if (lastChatMsg.contains("?")
						|| lastChatMsg.equals("wc lvls")) {
					if (random == 1) {
						sendText("My wc lvl is " + skills.getCurrentSkillLevel(Constants.STAT_WOODCUTTING), true);
					} else if (random == 2) {
						sendText("My woodcutting level is " + skills.getCurrentSkillLevel(Constants.STAT_WOODCUTTING), true);
					} else if (random == 3) {
						sendText("" + skills.getCurrentSkillLevel(Constants.STAT_WOODCUTTING), true);
					} else if (random == 4) {
						sendText("Mines " + skills.getCurrentSkillLevel(Constants.STAT_WOODCUTTING), true);
					} else if (random == 5) {
						sendText("My woodcuttin level is " + skills.getCurrentSkillLevel(Constants.STAT_WOODCUTTING), true);
					} else if (random == 6) {
						sendText("My woodcutting is " + skills.getCurrentSkillLevel(Constants.STAT_WOODCUTTING), true);
					} else if (random == 7) {
						sendText("Mine woodcutting is " + skills.getCurrentSkillLevel(Constants.STAT_WOODCUTTING), true);
					} else if (random == 8) {
						sendText("my wc level is " + skills.getCurrentSkillLevel(Constants.STAT_WOODCUTTING), true);
					} else if (random == 9) {
						sendText("My woodcutting lvl is " + skills.getCurrentSkillLevel(Constants.STAT_WOODCUTTING), true);
					} else if (random == 10) {
						sendText("My wc is " + skills.getCurrentSkillLevel(Constants.STAT_WOODCUTTING), true);
					} else if (random == 11) {
						sendText("My wc level is " + skills.getCurrentSkillLevel(Constants.STAT_WOODCUTTING), true);
					} else if (random == 12) {
						sendText("Mines wc lvl is " + skills.getCurrentSkillLevel(Constants.STAT_WOODCUTTING), true);
					} else if (random == 13) {
						sendText("The wc of mine is " + skills.getCurrentSkillLevel(Constants.STAT_WOODCUTTING), true);
					} else {
						log("??");
					}
				}
			}
		}
		return true;
	}

	private String lastChatMessage() {
		String messages = "";
		RSInterface chatinterface = RSInterface.getInterface(137);
		for (RSInterfaceChild child : chatinterface.getChildren()) {
			if (child.getText().contains("<col=0000ff>")) {
				String text = child.getText().substring(
						child.getText().indexOf("<col=0000ff>") + 12);
				messages = text;
			}
		}
		return messages;
	}

	public void onFinish() {
		end = true;
		showGained = true;
		wait(10);
		ScreenshotUtil.takeScreenshot(true);
		long millis = System.currentTimeMillis() - startTime;
		long hours = millis / (1000 * 60 * 60);
		millis -= hours * (1000 * 60 * 60);
		long minutes = millis / (1000 * 60);
		millis -= minutes * (1000 * 60);
		long seconds = millis / 1000;
		log("Thank You for using Gribonns Magics Chopper!");
		log("Time ran: " + hours + ":" + minutes + ":" + seconds);
		log("Logs cut: " + logs);
		log("Levels Gained: " + levels);
		log("Profit got: " + profit);
	}

	@Override
	public int loop() {
		if (chatResponder == true) {
			chatResponderFunc();
		}
		setCameraAltitude(true);
		antiBan();
		if (getEnergy() >= 65) {
			setRun(true);
		}
		if (isInventoryFull()) {
			if (atBank()) {
				bank();
			} else {
				toBank();
			}
		}
		if (!isInventoryFull()) {
			if (atTrees()) {
				chop();
			} else {
				toTrees();
			}
		}
		return (random(0, 10));
	}

	// METHODS
	public boolean atBank() {
		if (location == "S1" || location == "S2") {
			return distanceTo(banktileS1) <= 7;
		}
		return false;
	}

	public boolean atTrees() {
		if (location == "S1") {
			return distanceTo(treetileS1) <= 10;
		}
		if (location == "S2") {
			return distanceTo(treetileS2) <= 15;
		}
		return false;
	}

	public void antiBan() {
		int randomNumber = random(1, 50);
		if (randomNumber <= 4) {
			status = "Antiban";
			if (randomNumber == 1) {
				setCameraRotation(random(1, 360));
			}
			if (randomNumber == 2) {
				moveMouse(random(50, 700), random(50, 450), 2, 2);
			}
			if (randomNumber == 3) {
				setCameraRotation(random(1, 360));
				moveMouse(random(50, 700), random(50, 450), 2, 2);
			}
			if (randomNumber == 4) {
				moveMouse(random(50, 700), random(50, 450), 2, 2);
				setCameraRotation(random(1, 360));
				moveMouse(random(50, 700), random(50, 450), 2, 2);
			}
		}
		int randomnumber2 = random(1, 500);
		if (randomnumber2 == 1) {
			if (getCurrentTab() != TAB_STATS) {
				openTab(TAB_STATS);
				wait(random(100, 200));
				moveMouse(random(700, 750), random(310, 330), 2, 2);
				wait(random(750, 1000));
				openTab(TAB_INVENTORY);
			}
		}
	}

	public boolean chop() {
		if (getMyPlayer().getAnimation() == 2846
				|| getMyPlayer().getAnimation() == 867) {
			status = "Chopping";
			if (getEnergy() >= 20) {
				setRun(true);
			}
			wait(random(500, 1000));
		}
		if (getMyPlayer().getAnimation() != 2846
				&& getMyPlayer().getAnimation() == 867) {
			status = "Waiting";
			if (getEnergy() >= 20) {
				setRun(true);
			}
			wait(random(500, 1000));
		}
		if (!(getMyPlayer().getAnimation() == 2846 || getMyPlayer()
				.getAnimation() == 867)) {
			final RSObject magic = getNearestObjectByID(treeID);
			if (magic != null && distanceTo(magic) <= 10) {
				if (distanceTo(magic) >= 4) {
					walkTo(magic.getLocation());
					wait(random(500, 800));
					at4TiledObject(magic, "Chop down Magic");
				} else {
					at4TiledObject(magic, "Chop down Magic");
				}
				wait(random(500, 800));
			}
		}
		return true;
	}

	public boolean at4TiledObject(RSObject obj, String action) {
		RSTile map[][] = new RSTile[5][5];
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				RSObject fObj = getObjectAt(obj.getLocation().getX() - 2 + x,
						obj.getLocation().getY() - 2 + y);
				if (fObj != null && fObj.getID() == obj.getID()) {
					map[x][y] = fObj.getLocation();
				}
			}
		}
		RSTile tTile = null;
		int tx = 0;
		int ty = 0;
		for (int x = 0; x <= 3; x++) {
			for (int y = 0; y <= 3; y++) {
				if (map[x][y] != null && map[x + 1][y] != null
						&& map[x][y + 1] != null && map[x + 1][y + 1] != null) {
					if (map[x][y].equals(obj.getLocation())
							|| map[x + 1][y].equals(obj.getLocation())
							|| map[x][y + 1].equals(obj.getLocation())
							|| map[x + 1][y + 1].equals(obj.getLocation())) {
						tTile = map[x][y];
						tx = x;
						ty = y;
						break;
					}
				}
			}
		}

		if (tTile == null) {
			return false;
		}
		int xSum = 0;
		int ySum = 0;

		for (int x = 0; x <= 1; x++) {
			for (int y = 0; y <= 1; y++) {
				xSum += (int) map[tx + x][ty + y].getScreenLocation().getX();
				ySum += (int) map[tx + x][ty + y].getScreenLocation().getY();
			}
		}
		moveMouse(xSum / 4 + random(-2, 2), ySum / 4 + random(-2, 2));
		return atMenu(action);
	}

	public int toBank() {
		status = ("Walking to bank");
		if (getEnergy() >= 65) {
			setRun(true);
		}
		if (location == "S1") {
			if (distanceTo(getDestination()) < random(5, 12)
					|| distanceTo(getDestination()) > 40) {
				if (!walkPathMM(magicsToBankS1)) {
					walkToClosestTile(randomizePath(magicsToBankS1, 2, 2));
					return random(400, 600);
				}
			}
		}
		if (location == "S2") {
			if (distanceTo(getDestination()) < random(5, 12)
					|| distanceTo(getDestination()) > 40) {
				if (!walkPathMM(magicsToBankS2)) {
					walkToClosestTile(randomizePath(magicsToBankS2, 2, 2));
					return random(400, 600);
				}
			}
		}
		return random(100, 250);
	}

	public int toTrees() {
		status = ("Walking to magics");
		if (getEnergy() >= 65) {
			setRun(true);
		}
		if (location == "S1") {
			if (distanceTo(getDestination()) < random(5, 12)
					|| distanceTo(getDestination()) > 40) {
				if (!walkPathMM(randomizePath(bankTomagicsS1, 2, 2))) {
					walkToClosestTile(randomizePath(bankTomagicsS1, 2, 2));
					return random(400, 600);
				}
			}
		}
		if (location == "S2") {
			if (distanceTo(getDestination()) < random(5, 12)
					|| distanceTo(getDestination()) > 40) {
				if (!walkPathMM(randomizePath(bankTomagicsS2, 2, 2))) {
					walkToClosestTile(randomizePath(bankTomagicsS2, 2, 2));
					return random(400, 600);
				}
			}
		}
		return random(100, 250);
	}

	public int bank() {
		status = "Banking";
		if (bank.isOpen()) {
			bank.depositAllExcept(hatchetID);
			wait(random(800, 1000));
		}
		if (!(bank.isOpen())) {
			final int random = random(1, 2);
			if (random == 1) {
				final RSObject bankBooth = getNearestObjectByID(bankBoothID);
				atObject(bankBooth, "Use-Quickly");
			} else if (random == 2) {
				final RSNPC banker = getNearestNPCByID(bankerID);
				atNPC(banker, "Bank Banker");
			} else {
				final RSNPC banker = getNearestNPCByID(bankerID);
				atNPC(banker, "Bank Banker");
			}
		}
		return random(150, 350);
	}

	// METHODS

	@Override
	public void onRepaint(Graphics g) {
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		if (isLoggedIn()) {
			int xpGained = 0;
			if (startexp == 0) {
				startexp = skills
						.getCurrentSkillExp(Constants.STAT_WOODCUTTING);
			}
			profit = (logs * magicCost);
			xpGained = skills.getCurrentSkillExp(Constants.STAT_WOODCUTTING)
					- startexp;
			long millis = System.currentTimeMillis() - startTime;
			long hours = millis / (1000 * 60 * 60);
			millis -= hours * (1000 * 60 * 60);
			long minutes = millis / (1000 * 60);
			millis -= minutes * (1000 * 60);
			long seconds = millis / 1000;
			float xpsec = 0;
			if ((minutes > 0 || hours > 0 || seconds > 0) && xpGained > 0) {
				xpsec = ((float) xpGained)
						/ (float) (seconds + (minutes * 60) + (hours * 60 * 60));
			}
			float xpmin = xpsec * 60;
			float xphour = xpmin * 60;
			final int magicHour = (int) ((logs) * 3600000D / (System
					.currentTimeMillis() - startTime));
			final int profitHour = (int) ((profit) * 3600000D / (System
					.currentTimeMillis() - startTime));
			if (showPaint == true) {
				g.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
				if (showGained == true) {
					g.setColor(new Color(0, 0, 0, 150));
					g.fillRoundRect(200, 213 + 18, 150, 100 - 18, 0, 0);
					g.setColor(Color.white);
					g.drawString("XP Gained: " + xpGained, 208, 230 + 18);
					g.drawString("Levels Gained: " + levels, 208, 248 + 18);
					g.drawString("Logs chopped: " + logs, 208, 284);
					g.drawString("Profit: " + profit + "gp", 208, 284 + 18);
				}
				if (showAveraging == true) {
					g.setColor(new Color(0, 0, 0, 150));
					g.fillRoundRect(286, 213 + 18, 200, 100 - 18, 0, 0);
					g.setColor(Color.white);
					g.drawString("Time running: " + hours + ":" + minutes + ":"
							+ seconds, 294, 230 + 18);
					g
							.drawString("XP Hour: " + (int) xphour, 294,
									230 + 18 + 18);
					g.drawString("Logs/Hour: " + magicHour, 294, 266 + 18);
					g.drawString("Profit/Hour: " + profitHour + "gp", 294,
							284 + 18);
				}
				final int percent = skills
						.getPercentToNextLevel(Constants.STAT_WOODCUTTING);
				g.setColor(new Color(0, 0, 0, 150));
				g.fillRoundRect(4, 313, 511, 24, 0, 0);
				g.setColor(Color.white);
				g.draw3DRect(4, 313, 511, 24, true);
				g.setColor(new Color(149, 7, 17, 200));
				g.fillRoundRect(7, 315, 100, 20, 0, 0);
				g.setColor(new Color(0, 79, 0, 200));
				g.fillRoundRect(7, 315, percent, 20, 0, 0);
				g.setColor(Color.black);
				g.drawRect(7, 315, 100, 20);
				g.setColor(Color.white);
				g.drawString(percent + "% Done", 117, 330);
				g.draw3DRect(200, 313, 1, 24, true);
				g.drawString("Gained", 220, 330);
				g.draw3DRect(285, 313, 1, 24, true);
				g.drawString("Averaging", 305, 330);
				g.draw3DRect(390, 313, 1, 24, true);
				g.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
				g.drawString("" + status, 395, 330);
			}
		}
		Mouse m = Bot.getClient().getMouse();
		if (m.x >= 200 && m.x < 200 + 85 && m.y >= 313 && m.y < 313 + 24) {
			showGained = true;
		} else {
			if (end == false) {
				showGained = false;
			}
		}
		if (m.x >= 286 && m.x < 286 + 105 && m.y >= 313 && m.y < 313 + 24) {
			showAveraging = true;
		} else {
			showAveraging = false;
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

	@Override
	public void serverMessageRecieved(final ServerMessageEvent e) {
		final String serverString = e.getMessage();
		if (serverString.contains("logs.")) {
			logs++;
		}
		if (serverString.contains("You've just advanced")) {
			levels++;
		}
		if (serverString.contains("trade")) {
			final int randomNumber = random(1, 10);
			if (randomNumber == 1) {
				sendText("nty", true);
			} else if (randomNumber == 1) {
				sendText("no", true);
			} else if (randomNumber == 1) {
				sendText("no thanks", true);
			} else if (randomNumber == 1) {
				sendText("i dont wanna trade", true);
			} else if (randomNumber == 1) {
				sendText("i dont want!", true);
			} else if (randomNumber == 1) {
				sendText("nope", true);
			}
		}
	}
}
