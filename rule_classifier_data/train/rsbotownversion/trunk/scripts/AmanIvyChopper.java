import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Map;

import org.rsbot.bot.Bot;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.script.Calculations;
import org.rsbot.script.Constants;
import org.rsbot.script.Methods;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSInterface;
import org.rsbot.script.wrappers.RSItemTile;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSTile;


@ScriptManifest(authors = { "aman" }, category = "Woodcutting", name = "Aman's Ivy Chopper", version = 1.1)
public class AmanIvyChopper extends Script implements PaintListener{



	
	 final RSArea taverly = new RSArea(new RSTile(2943, 3416), new RSTile(2943, 3420)); 
	 final RSArea vsouth = new RSArea(new RSTile(3233, 3456), new RSTile(3233, 3461)); 
	//46322, 46320, 46324
	 final RSArea vnorth = new RSArea(new RSTile(3216, 3498), new RSTile(3219, 3498));
	 final RSArea fsouth = new RSArea(new RSTile(3044, 3328), new RSTile(3052, 3328));
	//46322
	 final RSArea fnorth = new RSArea(new RSTile(3011, 3392), new RSTile(3018, 3392));
	//46322
	 final RSArea ardougne = new RSArea(new RSTile(2622, 3304), new RSTile(2622, 3310));
	//46318
	 final RSArea castlewars = new RSArea(new RSTile(2423, 3068), new RSTile(2430, 3068));
	//46324
	 final RSArea yanille = new RSArea(new RSTile(2591, 3111), new RSTile(2597, 3111));
	//46324, 46320, 46318, 46322, 
	
	private long startTime = 0;
	private int startXP = 0;
	private int wcLvl = 0;
	final RSArea[] locations = {taverly, vnorth, vsouth, fnorth, fsouth, ardougne, castlewars, yanille};
	final int[] ivyID = {46318, 46320, 46322, 46324};
	
	int IS_CHOPPING = 872;
	final int[] NEST = { 5070, 5071, 5072, 5073, 5074, 5075, 5076, 7413, 11966 };
	antiban Antiban = new antiban();
	
	protected int getMouseSpeed() {
		return 5;
	}
	
	private RSArea getNearestIvyLocation(){
		RSTile[] loc = new RSTile[8];
		for(int i = 0; i < 8; i++){
			loc[i] = locations[i].getRandomTile();
		}
		int maximum = 999;
		RSTile tile = null;
		RSTile cur = getMyPlayer().getLocation();
		for (int i = 0; i < loc.length; i++) {
			if (distanceBetween(cur, loc[i])  <= maximum) {
				maximum = distanceBetween(cur, loc[i]);
				tile = loc[i];
			}
		}
		for(int i = 0; i < 8; i++){
			if(locations[i].contains(tile))
				return locations[i];
		}
		return null;
		
	}
	// credits to killa
	private RSObject getIvyAt(int x, int y) { 
		org.rsbot.accessors.RSObject rsObj;
		org.rsbot.accessors.RSInteractable obj;
		RSObject thisObject = null;
		final org.rsbot.accessors.Client client = Bot.getClient();
		final org.rsbot.accessors.RSGround rsGround = client.getRSGroundArray()[client
				.getPlane()][x - client.getBaseX()][y - client.getBaseY()];

		obj = rsGround.getRSObject3_0();
		if (obj != null) {
			rsObj = (org.rsbot.accessors.RSObject) obj;
			if (rsObj.getID() != -1) {
				thisObject = new RSObject(rsObj, x, y, 3);
			}
		}
		return thisObject;
	}
	private boolean isChopping(){
		if(getMyPlayer().getAnimation() == -1){
			wait(random(300,500));
			if(getMyPlayer().getAnimation() == -1)
				return false;
		}
		return true;
	}
	private boolean checkForNest() {
        RSItemTile nest = getGroundItemByID(NEST);
        if(nest != null) {
            wait(random(1000, 4000));
            if(!tileOnScreen(nest))
                turnToTile(nest);
            if(!tileOnScreen(nest) || isInventoryFull())
                return false;
            atTile(nest, "take bird's nest");
            wait(random(1000,2000));
            return true;
        }
        return false;
    }
	private String getFormattedTime(final long timeMillis) {
		long millis = timeMillis;
		final long seconds2 = millis / 1000;
		final long hours = millis / (1000 * 60 * 60);
		millis -= hours * 1000 * 60 * 60;
		final long minutes = millis / (1000 * 60);
		millis -= minutes * 1000 * 60;
		final long seconds = millis / 1000;
		String hoursString = "";
		String minutesString = "";
		String secondsString = seconds + "";
		String type = "seconds";
		if (minutes > 0) {
			minutesString = minutes + ":";
			type = "minutes";
		} else if (hours > 0 && seconds2 > 0) {
			minutesString = "0:";
		}
		if (hours > 0) {
			hoursString = hours + ":";
			type = "hours";
		}
		if (minutes < 10 && !type.equals("seconds")) {
			minutesString = "0" + minutesString;
		}
		if (hours < 10 && type.equals("hours")) {
			hoursString = "0" + hoursString;
		}
		if (seconds < 10 && !type.equals("seconds")) {
			secondsString = "0" + secondsString;
		}

		if (timeMillis == 1000) {
			type = "second";
		} else if (timeMillis == 60000) {
			type = "minute";
		} else if (timeMillis == 3600000) {
			type = "hour";
		}

		return hoursString + minutesString + secondsString + " " + type;
	}
	public boolean onStart(Map<String, String> args) {
		if(!isLoggedIn()){
			login();
		}else
			return true;
		
		return false;
	}
	public void onFinish() {
		super.onFinish();
		log("XP gained " + (skills.getCurrentSkillExp(STAT_WOODCUTTING) - startXP));
		log("Ran for "
				+ getFormattedTime(System.currentTimeMillis() - startTime));
		
		startScript = false;
		while (CAMERA_Thread.isAlive())
			CAMERA_Thread.interrupt();
		
	}
	
	RSArea finallocation = null;
	public boolean startScript = false;
	public int loop() {
		if(startScript == false){
			startTime = System.currentTimeMillis();
			startXP = skills.getCurrentSkillExp(STAT_WOODCUTTING);
			wcLvl = skills.getRealSkillLevel(STAT_WOODCUTTING);
			CAMERA_Thread.start();
			
			startScript = true;
		}
		if(finallocation == null){
			finallocation = getNearestIvyLocation();
		}
		checkForNest();
		if(!isChopping() && getMyPlayer().isIdle()){
			RSTile tile = finallocation.getRandomTile();
			RSObject ivy = getIvyAt(tile.getX(), tile.getY());
			if(distanceTo(finallocation.getRandomTile()) > 20){
				walkTo(finallocation.getRandomTile());
			}
			if(ivy != null){
				//Tree ivyTree = new Tree(ivy.getLocation(), 2, 2, ivy.getID());
				turnToObject(ivy);
				/*moveMouse(ivyTree.getScreenLocation());
				ArrayList<String> menu = new ArrayList<String>();
				menu = getMenuActions();
				if (menu.size() > 0 && menu.get(0).equalsIgnoreCase("Chop")) {
					clickMouse(true);
					wait(random(500,700));
				}*/
				atTree(ivy, "Chop");
				wait(random(500,700));
			}
		}
		if(finallocation.contains(getMyPlayer().getLocation()) && isChopping()){
			Antiban.run();
		}
		return random(100,300);
	}

	private Font defaultFont;
	
	public void onRepaint(Graphics g2) {
		int minigameX = 5, minigameY = 180, minigameHeight = 50;
		if (defaultFont == null)
			defaultFont = g2.getFont();
		Graphics2D g = (Graphics2D) g2;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g.setFont(new Font(defaultFont.getName(), defaultFont.getStyle(), 10));
		/*
		 * MOUSE
		 */
		Point p = getMouseLocation();
		g.setColor(Color.GREEN);
		g.drawRoundRect(p.x - 6, p.y, 15, 3, 5, 5);
		g.drawRoundRect(p.x, p.y - 6, 3, 15, 5, 5);
		g.setColor(new Color(90, 45, 0, 200));
		g.fillRoundRect(p.x - 6, p.y, 15, 3, 5, 5);
		g.fillRoundRect(p.x, p.y - 6, 3, 15, 5, 5);
		long timeSince = Bot.getClient().getMouse().getMousePressTime();
		if (timeSince > System.currentTimeMillis() - 500) {
			g.setColor(new Color(90, 45, 0, 100));
			g.fillRect(p.x - 5, p.y - 5, 13, 13);
		}

		g.setColor(Color.GREEN);
		g.drawRect(minigameX, 30, 125, 20);

		g.setColor(new Color(90, 45, 0, 200));
		g.fillRect(minigameX, 30, 125, 20);

		g.setColor(new Color(0, 0, 0, 60));
		g.fillRect(minigameX, 40, 125, 10);

		g.setColor(Color.GREEN);
		g.drawString(getClass().getAnnotation(
				ScriptManifest.class).name() +" "+ getClass().getAnnotation(
				ScriptManifest.class).version(), 10, 43);

		/*
		 * Main paint
		 */
		if (isLoggedIn() && startScript) {
			g.setColor(Color.GREEN);
			g.drawRect(minigameX, 60, 125, 100);

			g.setColor(new Color(90, 45, 0, 200));
			g.fillRect(minigameX, 60, 125, 100);

			g.setColor(new Color(0, 0, 0, 60));
			g.fillRect(minigameX, 110, 125, 50);

			g.setColor(Color.GREEN);
			long runTime = System.currentTimeMillis() - startTime;
			g.drawString("Run time: " + getFormattedTime(runTime), 10, 75);
			g.drawString("Level: " + skills.getCurrentSkillLevel(STAT_WOODCUTTING)
					+ "/" + wcLvl + "   ("
					+ skills.getPercentToNextLevel(STAT_WOODCUTTING) + "%)", 10,
					90);
			int xpGain = skills.getCurrentSkillExp(STAT_WOODCUTTING) - startXP;
			g.drawString("XP gain: " + xpGain, 10, 105);
			if (xpGain > 1000) {
				try {
					long xpRate = (xpGain * 60 * 60) / runTime;
					g.drawString("XP/Hour: " + xpRate + "k", 10, 120);
					int XPToGo = skills.getXPToNextLevel(STAT_WOODCUTTING);
					g
							.drawString(
									"TTNL: "
											+ getFormattedTime((long) (60 * 60 * (XPToGo / (double) xpRate))),
									10, 135);
				} catch (Exception e) {
				}
			} else {
				g.drawString("XP/Hour: Calculating..", 10, 120);
				g.drawString("TTNL: Calculating..", 10, 135);
			}
			if(getMyPlayer().getAnimation() == -1)
				g.drawString("Chopping: false", 10, 150);
			else
				g.drawString("Chopping: true", 10, 150);
			
		}
		
	}
	private class Tree {

        public final RSTile location;
        public final int objectID;
        public final int height;
        public final int size;

        public Tree(RSTile location, int size, int height, int objectID) {
            this.location = location;
            this.objectID = objectID;
            this.size = size;
            this.height = height;
        }

        public Point getScreenLocation() {
            Point pn;
            if (size % 2 == 0) {
                int off = size / 2;
                pn = Calculations.tileToScreen(new RSTile(location.getX()
                        + off, location.getY() - off), 0, 1, height);
                if (pn.x != -1)
                    return pn;
                return Calculations.tileToScreen(new RSTile(location.getX()
                        + off, location.getY() - off), 0, 1, 0);
            } else {
                int off = (size - 1) / 2;
                pn = Calculations.tileToScreen(new RSTile(location.getX()
                        + off, location.getY() - off), 0.5, 0.5, height);
                if (pn.x != -1)
                    return pn;
                return Calculations.tileToScreen(new RSTile(location.getX()
                        + off, location.getY() - off), 0.5, 0.5, 0);
            }
        }
    }
	public class RSArea {

		private final int X, Y, Width, Height;
		public RSArea(final int x, final int y, final int width, final int height) {
			X = x;
			Y = y;
			Width = width;
			Height = height;
		}

		public RSArea(final RSTile sw, final RSTile ne) {
			this(sw.getX(), sw.getY(), ne.getX() - sw.getX(), ne.getY() - sw.getY());
		}

		public boolean contains(final int x, final int y) {
			return x >= X && x <= X + Width && y >= Y && y <= Y + Height;
		}
		
		public boolean contains(final RSTile tile) {
			return contains(tile.getX(), tile.getY());
		}
		public RSTile getRandomTile() {
			final int minX = X;
			final int minY = Y;
			final int maxX = X + Width;
			final int maxY = Y + Height;

			final int possibleX = maxX - minX;
			final int possibleY = maxY - minY;
			if(possibleY == 0){
				final int[] tileX = new int[possibleX];
				for (int i = 0; i < tileX.length; i++) {
					tileX[i] = minX + i;
				}
				final int x = (int) (Math.random() * possibleX + 0);
				return new RSTile(tileX[x], minY);
				
			}
			if(possibleX == 0){
				final int[] tileY = new int[possibleY];
				for (int i = 0; i < tileY.length; i++) {
					tileY[i] = minY + i;
				}
				final int y = (int) (Math.random() * possibleY + 0);
				return new RSTile(minX, tileY[y]);
				
			}
			final int[] tileY = new int[possibleY];
			final int[] tileX = new int[possibleX];
			for (int i = 0; i < tileY.length; i++) {
				tileY[i] = minY + i;
			}
			for (int i = 0; i < tileX.length; i++) {
				tileX[i] = minX + i;
			}
			final int x = (int) (Math.random() * possibleX + 0);
			final int y = (int) (Math.random() * possibleY + 0);
			return new RSTile(tileX[x], tileY[y]);
		}

	}
	long cameraTime = 0;
	private final Thread CAMERA_Thread = new Thread() {

		private final Methods methods = new Methods();

		public void run() {
			while (startScript) {
				if (getMyPlayer().getAnimation() == -1 && cameraTime == 0) {
					cameraTime = System.currentTimeMillis();
				} else if (getMyPlayer().getAnimation() != -1)
					cameraTime = 0;
				if (System.currentTimeMillis() > cameraTime
						+ random(4000, 6000)
						&& cameraTime != 0) {
					final int camAlt = Bot.getClient().getCamPosZ();
					char LR = KeyEvent.VK_LEFT;
					char UD;
					if (camAlt > -1600) {
						UD = KeyEvent.VK_UP;
					} else if (camAlt < -2215 || methods.random(0, 2) == 0) {
						UD = KeyEvent.VK_DOWN;
					} else {
						UD = KeyEvent.VK_UP;
					}
					if (methods.random(0, 2) == 0) {
						LR = KeyEvent.VK_RIGHT;
					}

					Bot.getInputManager().pressKey(LR);
					try {
						Thread.sleep(methods.random(50, 400));
					} catch (final Exception ignored) {
					}
					Bot.getInputManager().pressKey(UD);
					try {
						Thread.sleep(methods.random(300, 700));
					} catch (final Exception ignored) {
					}
					Bot.getInputManager().releaseKey(UD);
					try {
						Thread.sleep(methods.random(100, 400));
					} catch (final Exception ignored) {
					}
					Bot.getInputManager().releaseKey(LR);

					cameraTime = 0;
				} else {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
					}
				}
			}
		}

	};
	public long timeToNext;
	boolean rest = false;
	public class antiban extends Thread {

		public void checkFriendsList() {
			openTab(Constants.TAB_FRIENDS);
			moveMouse(random(554, 709), random(227, 444));
		}

		public void checkSkills() {
			openTab(Constants.TAB_STATS);
			moveMouse(random(552, 603), random(420, 449));
		}

		public int getRandomMouseX(final int maxDistance) {
			final Point p = getMouseLocation();
			if (random(0, 2) == 0) {
				return p.x - random(0, p.x < maxDistance ? p.x : maxDistance);
			} else {
				return p.x
						+ random(1, 762 - p.x < maxDistance ? 762 - p.x
								: maxDistance);
			}
		}

		public int getRandomMouseY(final int maxDistance) {
			final Point p = getMouseLocation();
			if (random(0, 2) == 0) {
				return p.y - random(0, p.y < maxDistance ? p.y : maxDistance);
			} else {
				return p.y
						+ random(1, 500 - p.y < maxDistance ? 500 - p.y
								: maxDistance);
			}
		}

		public boolean moveMouseRandomly(int maxDistance) {
			if (maxDistance == 0) {
				return false;
			}
			maxDistance = random(1, maxDistance);
			final Point p = new Point(getRandomMouseX(maxDistance),
					getRandomMouseY(maxDistance));
			if (p.x < 1 || p.y < 1) {
				p.x = p.y = 1;
			}
			moveMouse(p);
			if (random(0, 2) == 0) {
				return false;
			}
			return moveMouseRandomly(maxDistance / 2);
		}

		public long nextTime(final int waitTime) {
			return time() + waitTime;
		}

		public long nextTime(final int min, final int max) {
			return nextTime(random(min, max));
		}

		@Override
		public void run() {
			try {
				final int roll = (int) (Math.random() * 1000);
				if (timeToNext < time()) {
					if (roll > 995) {
					} else if (roll > 990 && getInventoryCount() < 23) {
						checkSkills();
						timeToNext = System.currentTimeMillis()
								+ random(2000, 25000);
					} else if (roll > 985
							&& getInventoryCount() < 23
							&& !RSInterface.getInterface(751).getChild(15)
									.getText().contains("Off")) {
						checkFriendsList();
						timeToNext = System.currentTimeMillis()
								+ random(2000, 25000);
					} else if (roll > 980 && getInventoryCount() < 23) {
						checkSkills();
						timeToNext = System.currentTimeMillis()
								+ random(2000, 25000);
					} else if (roll > 960) {
						if (random(0, 2) == 0) {
							setCameraRotation((int) (getCameraAngle() + (Math
									.random() * 50 > 25 ? 1 : -1)
									* (30 + Math.random() * 90)));
						} else {
							final int key = random(0, 3) < 0 ? KeyEvent.VK_UP
									: KeyEvent.VK_DOWN;
							Bot.getInputManager().pressKey((char) key);
							Thread.sleep(random(1000, 1500));
							Bot.getInputManager().releaseKey((char) key);
						}
					} else if (roll > 940) {
						timeToNext = System.currentTimeMillis()
								+ random(2000, 25000);
						openTab(Constants.TAB_INVENTORY);
					} else if (roll > 890 && !isRunning()
							&& getMyPlayer().isMoving()) {
						if (getEnergy() > 50) {
							clickMouse(random(707, 762), random(90, 121), true);
							timeToNext = nextTime(500, 1200);
						} else if (rest) {
							Rest(100);
							Run(true);
							timeToNext = nextTime(500, 1200);
						}
						Thread.sleep(random(300, 1000));
					} else if (roll > 780) {
						moveMouseRandomly(500);
						timeToNext = nextTime(500, 7500);
					}
				} else {
					Thread.yield();
				}
			} catch (final InterruptedException e) {
				log("Interrupted");
			}
		}

		public long time() {
			return System.currentTimeMillis();
		}

		public boolean timePassed(final long time) {
			return time() > time;
		}

		public void turnCameraRandom() {
			setCameraRotation((int) (getCameraAngle() + (Math.random() * 50 > 25 ? 1
					: -1)
					* (30 + Math.random() * 90)));
		}
	}

	public void Rest(final int stopEnergy) {
		rest(stopEnergy);
	}

	public boolean Run(final boolean running) {
		try {
			final long startTime = System.currentTimeMillis();
			while (System.currentTimeMillis() - startTime < 2000) {
				if (running) {
					while (!isRunning()) {
						wait(random(200, 230));
					}
					return true;
				} else {
					while (isRunning()) {
						wait(random(200, 230));
					}
					return true;
				}
			}
			return false;
		} catch (final Exception e) {
			return false;
		}
	}


}