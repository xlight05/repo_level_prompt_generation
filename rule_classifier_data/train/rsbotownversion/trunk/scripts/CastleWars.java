import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.util.Map;

import org.rsbot.bot.Bot;
import org.rsbot.bot.input.Mouse;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.script.Calculations;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSInterface;
import org.rsbot.script.wrappers.RSInterfaceChild;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSPlayer;
import org.rsbot.script.wrappers.RSTile;

@ScriptManifest(authors = { "Speed" }, name = "CastleWars", version = 1.01, category = "Mini-Game", description = "<html"
		+ "><body><center><h1>CastleWars</h1></center>"
		+ "<p>At the moment, it just leeches, start outside"
		+ " of castlewars without a cape or helmet on.<br><select name=wat><option>Leech</option><option>Heal teammates</option></p></body></html>")
public class CastleWars extends Script implements PaintListener,
		ServerMessageListener {
	
	RSTile portalTile = new RSTile(2373, 3127);
	public static final int GUTHIX_PORTAL = 4388, BANDAGE_ROOM_PARENT = 59,
			BANDAGE_ROOM_CHILD = 7, ZAMMY_BARRIER = 4470, SARA_BARRIER = 4469,
			TIME_TILL_NEXT_PARENT = 57, TIME_TILL_NEXT_CHILD = 0,
			BANDAGE_TABLE = 36579, BANDAGE_TABLE2 = 36586, BANDAGES_ID = 4049;
	private int gamesWon, gamesLost, gamesDrew;
	private long startTime, seconds, minutes, hours, runTime;
	private final RenderingHints rh = new RenderingHints(
			RenderingHints.KEY_TEXT_ANTIALIASING,
			RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	private String status = "Starting up...";
	private State state = State.LEECH;

	public boolean onStart(final Map<String, String> args) {
		setAutoRetaliate(false);
		startTime = System.currentTimeMillis();
		if (args.get("wat") != null) {
			if (args.get("wat").equals("Heal teammates")) {
				state = State.HEAL;
			}
		}
		return true;
	}

	private enum State {
		LEECH, HEAL
	}

	private void setAutoRetaliate(final boolean on) {
		final RSInterfaceChild autoRetal = RSInterface.getInterface(884)
				.getChild(15);
		if (on && isRetaliateEnabled() || !on && !isRetaliateEnabled()) {
			return;
		} else {
			if (getCurrentTab() != TAB_ATTACK) {
				openTab(TAB_ATTACK);
			}
			if (getCurrentTab() == TAB_ATTACK && autoRetal != null) {
				atInterface(autoRetal);
			}
		}

	}

	@Override
	public int loop() {
		final RSInterfaceChild bandagetime = RSInterface.getInterface(
				BANDAGE_ROOM_PARENT).getChild(BANDAGE_ROOM_CHILD);
		final RSObject bandage = getNearestObjectByID(BANDAGE_TABLE,
				BANDAGE_TABLE2);
		final RSObject portal = getNearestObjectByID(GUTHIX_PORTAL);
		final RSInterfaceChild timeLeft = RSInterface.getInterface(58)
				.getChild(6);
		final RSInterfaceChild nextGame = RSInterface.getInterface(
				TIME_TILL_NEXT_PARENT).getChild(TIME_TILL_NEXT_CHILD);
		final RSObject barrier = getNearestObjectByID(ZAMMY_BARRIER,
				SARA_BARRIER);
		final RSInterface wat = RSInterface.getInterface(985);
		final RSInterfaceChild wat2 = wat.getChild(77);
		setAutoRetaliate(false);
		if (portal != null && !getMyPlayer().isInCombat()) {
			if (wat != null)
				atInterface(wat2);
			status = "Entering portal";
			clickObject(portal, "nter");
			return random(600, 1300);
		} else if (portal == null && nextGame.getText() != null
				&& nextGame.getText().contains("next") && nextGame != null
				&& !getMyPlayer().isInCombat()) {
			status = "Waiting...";
			randomWalkingTurningMoving();
			return random(3500, 5000);
		} else if (portal == null && bandage != null
				&& !getMyPlayer().isInCombat()) {
			if (!isInventoryFull()
					&& !bandagetime.getText().contains("WARNING")) {
				status = "Getting bandages";
				clickObject(bandage, "Take-5");
				atMenu("Take-5");
				return random(600, 1300);
			} else {
				if (barrier != null
						&& bandagetime.getText().toLowerCase().contains("have")) {
					status = "Leaving room";
					if (getMyPlayer().isMoving())
						return random(400, 500);
					walkTileMM(portalTile);
					turnToObject(barrier);
					clickObject(barrier, "Pass");
					return random(600, 800);
				}
			}
		} else if (getMyPlayer().isInCombat() && timeLeft != null) {
			status = "Combat";
			if (getHPPercent() <= 60) {
				status = "Healing self";
				if (inventoryContains(BANDAGES_ID)) {
					clickInventoryItem(BANDAGES_ID);
				}
			}
		}
		if (timeLeft != null
				&& !bandagetime.getText().toLowerCase().contains("have")) {
			switch (state) {
			case LEECH:
				status = "Leeching";
				randomWalkingTurningMoving();
				return random(800, 1150);
			case HEAL:
				status = "Healing others";
				RSPlayer p = getClosestPlayerOnTeam(getMyPlayer().getTeam());
				if (inventoryContains(BANDAGES_ID)) {
					if (p != null) {
						useItemWithPlayer(BANDAGES_ID, p);
					}
				} else {
					if (barrier != null) {
						if (bandagetime == null) {
							walkTileMM(portalTile);
							turnToObject(barrier);
							clickObject(barrier, "Pass");
							while (getMyPlayer().isMoving())
								wait(random(400, 500));
							if (bandagetime != null
									&& bandage != null
									&& !isInventoryFull()
									&& !bandagetime.getText().contains(
											"WARNING")) {
								clickObject(bandage, "Take-5");
								atMenu("Take-5");
							}
							if (isInventoryFull()
									|| bandagetime.getText()
											.contains("WARNING")) {
								walkTileMM(portalTile);
								turnToObject(barrier);
								clickObject(barrier, "Pass");
							}

						}
					}
				}
				break;
			}
		}
		return random(300, 500);

	}

	private void useItemWithPlayer(final int item, final RSPlayer p) {
		if (atInventoryItem(item, "Use")) {
			clickCharacter(p, "Use");
		}
	}

	private RSPlayer getClosestPlayerOnTeam(final int team) {
		int Dist = 20;
		RSPlayer closest = null;
		int[] validPlayers = Bot.getClient().getRSPlayerIndexArray();
		org.rsbot.accessors.RSPlayer[] players = Bot.getClient()
				.getRSPlayerArray();

		for (int element : validPlayers) {
			if (players[element] == null) {
				continue;
			}
			RSPlayer player = new RSPlayer(players[element]);
			try {
				if (team != player.getTeam()
						&& !player.getName().equals(getMyPlayer().getName())) {
					continue;
				}
				int distance = distanceTo(player);
				if (distance < Dist) {
					Dist = distance;
					closest = player;
				}
			} catch (Exception ignored) {
			}
		}
		return closest;
	}

	private void clickInventoryItem(final int id) {
		for (int i : getInventoryArray()) {
			if (i == id) {
				Point p = getInventoryItemPoint(i);
				p = randomiseInventoryItemPoint(p);
				if (p.x != -1 && p.y != -1) {
					moveMouse(p);
					clickMouse(true);
				}
			}
		}

	}

	public int getHPPercent() {
		return Math.round((skills.getCurrentSkillLevel(STAT_HITPOINTS) / skills
				.getRealSkillLevel(STAT_HITPOINTS)) * 100);
	}

	private boolean clickObject(final RSObject object, final String action) {
		RSTile objects = object.getLocation();
		if (objects.getX() != -1 && objects.getY() != -1 && objects != null) {
			if (tileOnScreen(objects)) {
				moveMouse(Calculations.tileToScreen(objects));
				if (getMenuActions().get(0).contains(action)) {
					clickMouse(true);
					return true;
				} else {
					clickMouse(false);
					atMenu(action);
					return true;
				}
			} else {
				if (distanceTo(objects) > 5) {
					walkTo(objects);
				} else {
					turnToObject(object);
				}
				return clickObject(object, action);
			}

		}
		return false;
	}

	private void randomWalkingTurningMoving() {
		if (random(0, 3) <= 2) {
			switch (random(0, 5)) {
			case 0:
				RSTile to = new RSTile(getLocation().getX() + random(-3, 4),
						getLocation().getY() + random(-3, 4));
				walkTileMM(to);
				wait(random(300, 800));
				break;
			case 1:
				turnCameraRandomly();
				break;
			case 2:
				moveMouse(random(0, 768), random(0, 503));
				break;
			default:
				wait(random(800, 1600));
				break;
			}
		}
	}

	private void turnCameraRandomly() {
		final char[] LR = new char[] { KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT };
		final char[] UD = new char[] { KeyEvent.VK_UP, KeyEvent.VK_DOWN };
		final char[] LRUD = new char[] { KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT,
				KeyEvent.VK_UP, KeyEvent.VK_DOWN };
		final int random2 = random(0, 2);
		final int random1 = random(0, 2);
		final int random4 = random(0, 4);

		if (random(0, 3) == 0) {
			Bot.getInputManager().pressKey(LR[random1]);
			try {
				wait(random(100, 400));
			} catch (final Exception ignored) {
			}
			Bot.getInputManager().pressKey(UD[random2]);
			try {
				wait(random(300, 600));
			} catch (final Exception ignored) {
			}
			Bot.getInputManager().releaseKey(UD[random2]);
			try {
				wait(random(100, 400));
			} catch (final Exception ignored) {
			}
			Bot.getInputManager().releaseKey(LR[random1]);
		} else {
			Bot.getInputManager().pressKey(LRUD[random4]);
			if (random4 > 1) {
				try {
					wait(random(300, 600));
				} catch (final Exception ignored) {
				}
			} else {
				try {
					wait(random(500, 900));
				} catch (final Exception ignored) {
				}
			}
			Bot.getInputManager().releaseKey(LRUD[random4]);
		}
	}

	public boolean isMouseInPaint(final int x, final int y, final int width,
			final int height) {
		Mouse m = Bot.getClient().getMouse();
		return m.x <= x + width && m.y <= y + height && m.x >= x && m.y >= y;
	}

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
		int players = getMyPlayer().getTeam();
		g.setColor(new Color(0, 0, 0));
		g.drawString("Team Number = " + players, 209, 56);
		g.setColor(new Color(255, 255, 255));
		g.drawString("Team Number = " + players, 208, 55);
		// This paint was mostly made using Enfilade's Paint Maker
		((Graphics2D) g).setRenderingHints(rh);
		if (isMouseInPaint(130, 350, 80, 35)) {
			g.setColor(new Color(0, 0, 0));
			g.fillRect(220, 345, 265, 130);
			g.setFont(new Font("Agency FB", 0, 14));
			g.setColor(new Color(255, 255, 255));
			g.drawString("CastleWars by Speed", 225, 365);
			g.setFont(new Font("Agency FB", 0, 12));
			g.setColor(new Color(255, 255, 255));
			g.drawString("Games lost: " + gamesLost, 225, 380);
			g.setFont(new Font("Agency FB", 0, 12));
			g.setColor(new Color(255, 255, 255));
			g.drawString("Games won: " + gamesWon, 225, 395);
			g.setFont(new Font("Agency FB", 0, 12));
			g.setColor(new Color(255, 255, 255));
			g.drawString("Games drawn:" + gamesDrew, 225, 410);
			g.setFont(new Font("Agency FB", 0, 12));
			g.setColor(new Color(255, 255, 255));
			g.drawString("Run time: " + hours + ":" + minutes + ":" + seconds,
					225, 425);
			g.drawString("Status: " + status, 225, 440);
			g.drawString("Mode: " + state.toString(), 225, 455);
		}
		g.setColor(new Color(0, 0, 0));
		g.fillRoundRect(130, 350, 80, 35, 4, 4);
		g.setFont(new Font("Arial", 0, 12));
		g.setColor(new Color(255, 255, 255));
		g.drawString(isMouseInPaint(130, 350, 80, 35) ? "Hide Paint"
				: "Show Paint", 135, 370);
	}

	@Override
	public void serverMessageRecieved(final ServerMessageEvent e) {
		String message = e.getMessage();
		if (message.contains("won")) {
			gamesWon++;
		} else if (message.contains("lost")) {
			gamesLost++;
		} else if (message.contains("draw")) {
			gamesDrew++;
		}

	}

}