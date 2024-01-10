import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.rsbot.bot.Bot;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.script.Calculations;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.Skills;
import org.rsbot.script.wrappers.RSItemTile;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSTile;


@ScriptManifest(authors = { "Hidendra Fixed by nobody" }, name = "iSalamander", category = "Hunter", version = 1.00, description = "<style>body{background-color:black;color:yellow;}h1{text-align:right;color:red;}</style><h1>iSalamander by Hidendra <br/> hidendra@rsbot.org</h1><br/><center><h2><i>Simple, yet elegant.</i></h2> <br/> <br/> Radius: <input type=\"text\" name=\"radius\" value=\"5\"/> <br/> Mouse Speed: <input type=\"text\" name=\"mousespeed\" value=\"7\"/> <br/> Timeout: <input type=\"text\" name=\"timeout\" value=\"3000\"/> </center> ")
public class HSalamanders extends Script implements ServerMessageListener, PaintListener {

	static {
		TREE_ACTION = "Set-trap";

		ORANGE_SALAMANDER = 10146;

		RED_SALAMANDER = 10147;

		BLACK_SALAMANDER = 10148;

		SWAMP_SALAMANDER = 10149;

		ROPE = 954;

		SMALL_NET = 303;

		DROP_AT = 26;
	}

	{

		TREES_AFTER = new int[] { 19659, 19675, 19654 };

		TREES = new int[] { 19663, 19679, 19652 };

		TO_DROP = new int[] { ORANGE_SALAMANDER, RED_SALAMANDER,
				BLACK_SALAMANDER, SWAMP_SALAMANDER };
	}

	private final List<RSTile> setTraps = new ArrayList<RSTile>();
	private RSTile startTile = null;
	private long startedAt;
	private final int[] TREES_AFTER;
	private final int[] TREES;
	private final int[] TO_DROP;
	private static final String TREE_ACTION;
	private static final int ORANGE_SALAMANDER;
	private static final int RED_SALAMANDER;
	private static final int BLACK_SALAMANDER;
	private static final int SWAMP_SALAMANDER;
	private static final int ROPE;
	private static final int SMALL_NET;
	private static final int DROP_AT;
	private int hunterXP = 0;
	private int startXP = 0;
	private int radius = 5;
	private int mouseSpeed = 7;
	private int timeout;
	private int trapsSet = 0;
	private int salamandersCaught = 0;
	private int salamandersReleased = 0;
	private boolean dropNow = false;

	@Override
	protected int getMouseSpeed() {
		return mouseSpeed;
	}

	@Override
	public boolean onStart(Map<String, String> args) {
		log("Starting HSalamanders by Hidendra...");

		radius = Integer.parseInt(args.get("radius"));
		mouseSpeed = Integer.parseInt(args.get("mousespeed"));
		timeout = Integer.parseInt(args.get("timeout"));

		if (!isLoggedIn()) {
			log.severe("You must be logged in before starting HSalamanders!");
			return false;
		} else {
			if (!canTrap()) {
				log
						.severe("You need rope(s) and a small net. Do not have any traps set up first.");
				return false;
			}

		}

		startTile = getMyPlayer().getLocation();

		if (findTree() == null) {
			log
					.severe("Looks like there are no available trees. Maybe try world hopping?");
			log
					.severe("If you have your own traps set up, take them down !! :)");
			log
					.severe("Also, start within the radius of trees you wish to utilize");
			return false;
		}

		startXP = skills.getCurrentSkillExp(Skills.getStatIndex("Hunter"));

		log("Looks like we're good to go. Current XP: " + startXP);
		hunterXP = startXP;
		startedAt = System.currentTimeMillis();

		return true;
	}

	@Override
	public int loop() {

		run();

		pickup();

		if (findCaughtTrap() == null && isInventoryFull()
				|| getInventoryCount() >= DROP_AT || dropNow) {
			if (dropNow) {
				dropNow = false;
			}
			dropItems();
		}

		RSObject tree = null;
		if (findCaughtTrap() == null && canTrap()
				&& (tree = findTree()) != null) {
			if (tileOnScreen(tree.getLocation())) {
				if (getMyPlayer().getAnimation() == -1
						&& atObject(tree, TREE_ACTION)) {
					log("Setting up a trap..");
					waitForAnim(timeout);
					if (getMyPlayer().getAnimation() != -1
							&& !setTraps.contains(tree.getLocation())) {
						setTraps.add(tree.getLocation());
						trapsSet++;
					}
					hoverNextTree(tree);
					while (getMyPlayer().getAnimation() != -1) {
						wait(random(50, 124));
					}
					wait(random(274, 572));
				}
			} else {
				walk(tree.getLocation());
			}
		}

		if ((tree = findCaughtTrap()) != null) {
			if (tileOnScreen(tree.getLocation())) {
				if (atObject(tree, "Check")) {
					log("Taking trap & salamander..");
					waitForAnim(timeout);
					if (getMyPlayer().getAnimation() != -1
							&& setTraps.contains(tree.getLocation())) {
						setTraps.remove(tree.getLocation());
					}
					while (getMyPlayer().getAnimation() != -1) {
						wait(random(50, 124));
					}
					wait(random(300, 623));
				}
			} else {
				walk(tree.getLocation());
			}
		}

		return random(100, 300);
	}

	@Override
	public void onRepaint(Graphics graphics) {
		long millis = System.currentTimeMillis() - startedAt;
		final long hours = millis / (1000 * 60 * 60);
		millis -= hours * 1000 * 60 * 60;
		final long minutes = millis / (1000 * 60);
		millis -= minutes * 1000 * 60;
		final long seconds = millis / 1000;

		hunterXP = skills.getCurrentSkillExp(Skills.getStatIndex("Hunter"));
		final int xptl = skills.getXPToNextLevel(Skills.getStatIndex("Hunter"));
		graphics.drawString("Time running: " + hours + ":" + minutes + ":"
				+ seconds, 571, 375);
		graphics.drawString("Current set traps: " + setTraps.size(), 571, 390);
		graphics.drawString("Overall set traps: " + trapsSet, 571, 405);
		graphics.drawString("XP gained: " + (hunterXP - startXP), 571, 420);
		graphics.drawString(
				"XP til level "
						+ (skills.getCurrentSkillLevel(Skills
								.getStatIndex("Hunter")) + 1) + ": " + xptl,
				571, 435);
		graphics.drawString("Sallies caught: " + salamandersCaught, 571, 450);
		graphics.drawString("Sallies released: " + salamandersReleased, 571,
				465);
	}

	/**
	 * Let's hover the next tree !!
	 * 
	 * @param curr
	 *            The tree we clicked before calling this method.
	 */
	private void hoverNextTree(RSObject curr) {
		final RSObject hover = findTree(curr);
		if (hover == null) {
			return;
		}

		final Point point = Calculations.tileToScreen(hover.getLocation());
		if (point.x == -1 || point.y == -1) {
			return;
		}

		moveMouse(point, 5, 5);

	}

	/**
	 * Check if there are items to pick up, and then pick them up if needed.
	 */
	private void pickup() {
		RSItemTile tile = null;
		final int[] drops = { ROPE, SMALL_NET };

		while ((tile = getNearestGroundItemByID(drops)) != null) {
			final int before = getInventoryCount();
			if (atTile(tile, "Take")) {
				waitForPickup(before);
				final RSObject t = findTree();
				if (t != null) {
					if (setTraps.contains(t.getLocation())) {
						setTraps.remove(t.getLocation());
					}
				}
			}
		}
	}

	/**
	 * @return If we have rope and a small fishing net.
	 */
	private boolean canTrap() {
		return inventoryContains(ROPE, SMALL_NET);
	}

	/**
	 * If we have enough energy to run and we aren't running, let's run!1
	 */
	private void run() {
		if (!isRunning()) {
			if (getEnergy() > 20) {
				setRun(true);
				wait(random(800, 1200));
			}
		}
	}

	/**
	 * Find a sprung trap with a successful sally and grab it.
	 * 
	 * @return The RSObject.
	 */
	private RSObject findCaughtTrap() {
		RSObject cur = null;
		double dist = -1;
		for (int x = 0; x < 104; x++) {
			for (int y = 0; y < 104; y++) {
				final RSObject o = getObjectAt(x + Bot.getClient().getBaseX(),
						y + Bot.getClient().getBaseY());
				if (o != null) {
					boolean isObject = false;
					for (final int id : TREES_AFTER) {
						if (o.getID() == id) {
							isObject = true;
							break;
						}
					}
					if (isObject) {
						final double distTmp = calculateDistance(getMyPlayer()
								.getLocation(), o.getLocation());
						if (!setTraps.contains(o.getLocation())) {
							continue;
						}
						if (cur == null) {
							dist = distTmp;
							cur = o;
						} else if (distTmp < dist) {
							cur = o;
							dist = distTmp;
						}
					}
				}
			}
		}
		return cur;
	}

	/**
	 * Find a tree.
	 * 
	 * @return The closest tree.
	 */
	private RSObject findTree() {
		return findTree(null);
	}

	/**
	 * Find a tree, and possibly (except) one.
	 * 
	 * @param except
	 *            The tree to exempt.
	 * @return The RSObject
	 */
	private RSObject findTree(RSObject except) {
		if (except == null) {
			except = new RSObject(null, 0, 0, 0);
		}
		RSObject cur = null;
		double dist = -1;
		for (int x = 0; x < 104; x++) {
			for (int y = 0; y < 104; y++) {
				final RSObject o = getObjectAt(x + Bot.getClient().getBaseX(),
						y + Bot.getClient().getBaseY());
				if (o != null) {
					boolean isObject = false;
					for (final int id : TREES) {
						if (o.getID() == id) {
							isObject = true;
							break;
						}
					}
					if (isObject) {
						final double distTmp = calculateDistance(getMyPlayer()
								.getLocation(), o.getLocation());
						if (calculateDistance(o.getLocation(), startTile) > radius) {
							continue;
						}
						if (cur == null) {
							dist = distTmp;
							cur = o;
						} else if (distTmp < dist) {
							cur = o;
							dist = distTmp;
						}
					}
				}
			}
		}
		return cur;
	}

	/**
	 * [caps lock] FUCK YEAH ITEM DROPPING
	 */
	private void dropItems() {
		log("Time to drop items! Bahah, good bye GP!");
		while (getInventoryCount(TO_DROP) > 0) {
			final int[] items = getInventoryArray();
			openTab(TAB_INVENTORY);

			for (int i = 0; i < items.length; i++) {
				final int id = items[i];
				final int itemCount = getInventoryCount(id);
				if (isIn(TO_DROP, id)) {
					final Point t = getInventoryItemPoint(i);
					moveMouse(t, 5, 5);
					if (atMenu("Release")) {
						waitForDrop(id, itemCount);
						break;
					}
				}
			}

			wait(random(100, 250));
		}
	}

	/**
	 * Simply wait for a drop to be dropped.
	 * 
	 * @param id
	 *            The ID to wait to be dropped.
	 * @param itemCount
	 *            The item count of it before we tried to drop it.
	 */
	private void waitForDrop(int id, int itemCount) {
		final long start = System.currentTimeMillis();
		while (itemCount == getInventoryCount(id)) {
			wait(50);
			if (System.currentTimeMillis() - start > timeout
					&& !getMyPlayer().isMoving()) {
				break;
			}
		}
		wait(random(50, 100));
	}

	/**
	 * Simply wait for a drop to be picked up.
	 * 
	 * @param id
	 *            The ID to wait to be dropped.
	 * @param itemCount
	 *            The item count of it before we tried to drop it.
	 */
	private void waitForPickup(int before) {
		final long start = System.currentTimeMillis();
		while (before == getInventoryCount()) {
			wait(50);
			if (System.currentTimeMillis() - start > timeout
					&& !getMyPlayer().isMoving()) {
				break;
			}
		}
		wait(random(50, 100));
	}

	/**
	 * If a value is inside of an array.
	 */
	private boolean isIn(int[] arr, int find) {
		for (final int temp : arr) {
			if (temp == find) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Walk to a tile and return when the player is finished walking and/or the
	 * tile is now on screen.
	 * 
	 * @param to
	 *            The tile to walk to.
	 */
	private void walk(RSTile to) {
		while (getDestination() == null || !tileOnScreen(to)) {
			if (getDestination() == null && !tileOnScreen(to)) {
				walkTileMM(randomizeTile(to, 2, 2));
			}
			wait(random(50, 163));
		}
	}

	@Override
	public void serverMessageRecieved(ServerMessageEvent e) {
		final String message = e.getMessage();
		if (message.contains("caught")) {
			salamandersCaught++;
		} else if (message.contains("release")) {
			salamandersReleased++;
		} else if (message.contains("full")) {
			dropNow = true;
		}
	}

}