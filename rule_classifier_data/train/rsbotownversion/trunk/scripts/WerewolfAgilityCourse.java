/*
 * @(#)WerewolfAgilityCourse.java	1.22 10/07/09
 * Copyright 2010 vilon@RSBot.org. All rights reserved.
 */

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.Map;

import org.rsbot.bot.Bot;
import org.rsbot.bot.input.CanvasWrapper;
import org.rsbot.bot.input.Mouse;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.script.Calculations;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSArea;
import org.rsbot.script.wrappers.RSInterface;
import org.rsbot.script.wrappers.RSItemTile;
import org.rsbot.script.wrappers.RSNPC;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSPlayer;
import org.rsbot.script.wrappers.RSTile;

@ScriptManifest(authors = {"vilon"}, name = "Werewolf Agility Course", category = "Agility", version = 1.22, description = "<html>\n<body style=\"font-family: Arial, Helvetica, sans-serif; font-size: 11px; background-color: #fde2a9;\">\n<div style=\"width: 100%; height: 20px; background-color: #ffbb28; text-align: center; vertical-align: middle\"><h3 style=\"color: #a26f00;\">Werewolf Agility Course</h3></div>\n<div style=\"width: 100%; height: 17px; background-color: #eca100; text-align: center; vertical-align: middle; color: #a26f00;\">Version 1.22 | by vilon</div>\n<div style=\"width: 100%; height: 10px; background-color: #fdcb5e;\"></div>\n<div style=\"width: 100%; background-color: #fdcb5e; text-align: left; vertical-align: middle; color: #a26f00; text-indent: 4px; padding-bottom: 3px;\">Select/enter food: <select name=\"foodList\" id=\"foodList\"><option selected> </option><option>Strawberries</option></select> <input name=\"foodID\" type=\"text\" id=\"foodID\" size=\"7\"></div>\n<div style=\"width: 100%; background-color: #fdcb5e; text-align: left; vertical-align: middle; color: #a26f00; text-indent: 4px; padding-bottom: 3px;\">Maximum runtime.: <input name=\"maxHours\" type=\"text\" id=\"maxHours\" size=\"2\"> H: <input name=\"maxMinutes\" type=\"text\" id=\"maxMinutes\" size=\"2\"> M: <input name=\"maxSeconds\" type=\"text\" id=\"maxSeconds\" size=\"2\"> S</div>\n<div style=\"width: 100%; background-color: #fdcb5e; text-align: left; vertical-align: middle; color: #a26f00; text-indent: 4px; padding-bottom: 3px;\">Minimum health....: <input name=\"minHealth\" type=\"text\" id=\"minHealth\" value=\"400\" size=\"4\"></div>\n<div style=\"width: 100%; background-color: #fdcb5e; text-align: left; vertical-align: middle; color: #a26f00; text-indent: 4px;\">Maximum laps......: <input name=\"maxLaps\" type=\"text\" id=\"maxLaps\" size=\"3\"></div>\n<div style=\"width: 100%; height: 10px; background-color: #fdcb5e;\"></div>\n</body>\n</html>")
public class WerewolfAgilityCourse extends Script implements PaintListener {	
	
	/** The amount of failures during the current obstacle. */
	private int currentFailures;
	
	/** The maximum amount of failures during an obstacle, before stopping. */
	private final int maximumFailures = 100;
	
	/** The time in milliseconds when the obstacle was initiated.  */
	private long obstacleStartTime;
	
	/** The characters agility level when starting. */
	private int startLevel;
	
	/** The characters agility experience when starting. */
	private int startExp;
	
	/** Number of completed laps at the course. */
	private int lapsCompleted;
	
	/** The time in milliseconds when the script was started. */
	private long startTime;
	
	/** The maximum time to run the script for before stopping. */
	private long maximumTime;
	
	/** The maximum number of laps to be run before stopping. */
	private int maximumLaps;
	
	/** Entered ID for a certain food, as specified by the user. */
	private int enteredFoodID;
	
	/** Will be set to true if the user has decided to use strawberries. */
	private boolean isUsingStrawberries;

	/** The different states for the character during a lap. */
	private enum State {ROCKS, HURDLES, PIPES, GRABSTICK, SLIDE, GIVESTICK, SLOPE, UNKNOWN};
	
	/** Contains the last known state of the current player. */
	private State previousState;
	
	/** A global counter for states that has multiple obstacles. */
	private int stateCounter;	
	
	/** The minimum allowed health before eating/stopping. */
	private int minimumHealth;
	
	/** This is used for setting the camera angle and rotation once at the start. */
	private boolean cameraAngleRotationSet = false;
	
	/**
	 * Clicks a tile if it is on screen. It will left-click if the action is
	 * available as the default option, otherwise it will right-click and check
	 * for the action in the context menu.
	 *
	 * @param tile The RSTile that you want to click.
	 * @param action Action command to use on the Character (e.g "Attack" or Trade").
	 * @param mousePath Whether or not you want it to move the mouse using
	 *                  {@link #moveMouseByPath(Point)}.
	 * @param randX The maximum randomness in X when moving the mouse.
	 * @param randY The maximum randomness in Y when moving the mouse.
	 * @param dx The number of pixels in X to add from the center of the tile.
	 * @param dy The number of pixels in Y to add from the center of the tile.
	 * @return <tt>true</tt> if the tile was clicked; otherwise <tt>false</tt>.
	 */
	public boolean atTile(RSTile tile, String action, boolean mousePath, int randX, int randY, int dx, int dy) {
		try {
			int counter = 0;
			try {
				Point location = Calculations.tileToScreen(tile);
				if (location.x == -1 || location.y == -1)
					return false;				
				location = new Point(location.x + dx, location.y + dy);			
				
				if (!mousePath) {
					moveMouse(location, randX, randY);
				} else {
					moveMouseByPath(location, randX, randY);
				}
				while (!getMenuItems().get(0).toLowerCase().contains(action.toLowerCase()) && counter < 5) {
					location = Calculations.tileToScreen(tile);
					location = new Point(location.x + dx, location.y + dy);						
					moveMouse(location, randX, randY);
					counter++;
				}
				if (getMenuItems().get(0).toLowerCase().contains(action.toLowerCase())) {
					clickMouse(true);
				} else {
					clickMouse(false);
					atMenu(action);
				}
				return true;
			} catch (Exception e) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Clicks a tile if it is on screen. It will left-click if the action is
	 * available as the default option, otherwise it will right-click and check
	 * for the action in the context menu.
	 *
	 * @param tile The RSTile that you want to click.
	 * @param action Action command to use on the Character (e.g "Attack" or "Trade").
	 * @param mousePath Whether or not you want it to move the mouse using
	 *                  {@link #moveMouseByPath(Point)}.
	 * @param randX The maximum randomness in X when moving the mouse.
	 * @param randY The maximum randomness in Y when moving the mouse.
	 * @return <tt>true</tt> if the tile was clicked; otherwise <tt>false</tt>.
	 */
	public boolean atTile(RSTile tile, String action, boolean mousePath, int randX, int randY) {
		return atTile(tile, action, mousePath, randX, randY, 0, 0);
	}
	
	/**
	 * Returns the state of the current player, i.e. where it's located. This should be used
	 * to determine which step is the next to perform during a lap at the course.
	 * @return The current players state (location/obstacle).
	 */
	private State getPlayerState() {	
		final RSArea[] areas = {new RSArea(new RSTile(3533, 9860), new RSTile(3552, 9880)),		// Rock-area
								new RSArea(new RSTile(3534, 9881), new RSTile(3545, 9899)),		// Hurdle-area
								new RSArea(new RSTile(3535, 9900), new RSTile(3545, 9904)), 	// Pipe-area	
								new RSArea(new RSTile(3531, 9908), new RSTile(3548, 9917)), 	// Stick/slope-area
								new RSArea(new RSTile(3527, 9908), new RSTile(3530, 9912)),  	// Slide-area
								new RSArea(new RSTile(3521, 9860), new RSTile(3532, 9897))};	// Stick/rock-area	
		
		RSTile playerTile = getMyPlayer().getLocation();
		for(int n = 0; n < areas.length; n++) {
			if(areas[n].contains(playerTile)) {	
				State currentState = State.values()[n];	
				if(currentState == State.GRABSTICK && (inventoryContains(4179) || getGroundItemByID(4179) == null))
					currentState = State.SLOPE;	
				else if(currentState == State.GIVESTICK && !inventoryContains(4179))
					currentState = State.ROCKS;				
				return currentState;
			}
		}
		
		return State.UNKNOWN;
	}
	
	/**
	 * Returns the specified time with the format of HH:MM:SS.
	 * @param time The time as a <tt>long</tt> in milliseconds.
	 * @return The formatted time as a <tt>String</tt> with
	 *  	   the format of HH:MM:SS.
	 */
	private String getFormattedTime(final long time) {	
		long theTime = time;
		final long timeHours = theTime / 3600000,
		timeMinutes = (theTime -= timeHours * 3600000) / 60000,
		timeSeconds = (theTime -= timeMinutes * 60000) / 1000;
		
		return ((timeHours < 10) ? "0" : "") + (int)timeHours + ":" +
			((timeMinutes < 10) ? "0" : "") + (int)timeMinutes + ":" +
			((timeSeconds < 10) ? "0" : "") + (int)timeSeconds;
	}
	
	/**
	 * Gets the players current amount of life-points.
	 * @return The players current life-points or <tt>minimumHealth</tt> if
	 * unable to read the real value (not logged in etc.). Will stop the script
	 * and logout if a unknown exception occurred.
	 */
	public int getCurrentHP() {
		int lifePoints = minimumHealth;	
		
		try {
			lifePoints = Integer.parseInt(RSInterface.getInterface(748).getChild(8).getText());
		} catch(Exception ex) {
			if(isLoggedIn() && skills.getRealSkillLevel(STAT_AGILITY) > 1) {
				log("Unable to read character health. Script needs update.");
				stopScript();
			}
		}
		
		return lifePoints;
	}
	
	@Override
	public int getMouseSpeed() {
		return 6;
	}
	
	@Override
	public boolean onStart(final Map<String, String> args) {	
		
		/** Check if the user wants to use (basket of) strawberries as food. */
		isUsingStrawberries = (args.get("foodList") != null);
		
		/** Try to read the ID of the food the user has entered. */	
		try {
			enteredFoodID = Integer.parseInt(args.get("foodID"));
			if(enteredFoodID < 1) enteredFoodID = 0;
		} catch(NumberFormatException ex) { }
		
		/** Stop the script from running if the user didn't select his/her choice of food or selected multiple foods. */
		if((!isUsingStrawberries && enteredFoodID == 0) || (isUsingStrawberries && enteredFoodID != 0)) {
			log("You haven't managed to select your food of choice.");
			return false;
		}		
		
		/** Read minimum health and stop if not entered correctly. */
		try { minimumHealth = Integer.parseInt(args.get("minHealth")); } catch(NumberFormatException ex) { }
		if(minimumHealth < 1) {
			log("You need to specify the minimum health.");
			return false;			
		}
		
		/** Try to read maximum runtime entered by the user. */
		String[] maxLabels = {"maxHours", "maxMinutes", "maxSeconds"};
		for(int n = 0; n < maxLabels.length; n++) {
			try {
				int currentTime = Integer.parseInt(args.get(maxLabels[n]));
				if(currentTime > 0) maximumTime += currentTime * 1000 * Math.pow(60, 2 - n);	
			} catch(NumberFormatException ex) { }
		}		
		
		/** Try to read maximum number of laps, as entered by the user. */
		try {
			maximumLaps = Integer.parseInt(args.get("maxLaps"));
			if(maximumLaps < 1) maximumLaps = 0;			
		} catch(NumberFormatException ex) { }
		
		/** Show information about maximum laps and/or maximum runtime. */
		if(maximumLaps > 0 && maximumTime > 0) log("Will be running " + maximumLaps + 
				" lap(s) or a maximum " + getFormattedTime(maximumTime) + " before stopping.");			
		else if(maximumLaps > 0) log("Will be running " + maximumLaps + " lap(s) before stopping.");
		else if(maximumTime > 0) log("Will be running a maximum " + 
				getFormattedTime(maximumTime) + " before stopping.");

		return true;		
	}
	
	@Override
	public void onRepaint(Graphics render) {
		if(isLoggedIn() && skills.getRealSkillLevel(STAT_AGILITY) > 1) {
			if(startLevel == 0) {
				startTime = System.currentTimeMillis();
				startLevel = skills.getRealSkillLevel(STAT_AGILITY);
				startExp = skills.getCurrentSkillExp(STAT_AGILITY);
			} else {
			
				/** Cast object for rendering extended graphics and turn on antialiasing for text. */
				Graphics2D render2d = (Graphics2D)render;
				render2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);		
				Composite originalComposite = render2d.getComposite();
				
				/** Declare bar start position and height. */
				final int height = 20, startX = 4, startY = CanvasWrapper.getGameHeight() - 165 - height;
				
				/** Draw the black transparent bar for progress-report. */	
				render2d.setPaint(Color.BLACK);
				render2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.65F));
				render2d.fill(new Rectangle(startX, startY, CanvasWrapper.getGameWidth() - 253, height));
				render2d.setComposite(originalComposite);
				
				/** Declare padding size and the progress-bar width. */
				final int paddingSize = 2, progressWidth = 100;
				
				/** Draw the experience progress-bar outline on the black bar. */
				render2d.setColor(Color.WHITE);
				render2d.drawRect(startX + paddingSize, startY + paddingSize, progressWidth, height - 2 * paddingSize - 1);
				
				/** Determine percent to next level and select color to use for the content. */
				int percentLevel = skills.getPercentToNextLevel(STAT_AGILITY);			
				if(percentLevel < 30) render2d.setColor(Color.RED);
				else if(percentLevel < 70) render2d.setColor(Color.YELLOW);
				else render2d.setColor(Color.GREEN);
				
				/** Draw the content of the progress-bar. */
				int progressWidth2 = (int)Math.round(((percentLevel / 100D) * progressWidth));
				render2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50F));
				render2d.fillRect(startX + paddingSize + 1, startY + paddingSize + 1,
						progressWidth2, height - 2 * paddingSize - 2);
				render2d.setComposite(originalComposite);
				
				/** The text to be printed on the progress-bar. */
				String drawText;
				int modifyX = 0;
				
				/** Get statistics that are used in both modes. */
				long timeRunning = System.currentTimeMillis() - startTime;
				int expCurrent = skills.getCurrentSkillExp(STAT_AGILITY),
					expGained = expCurrent - startExp;					
				
				/** Check if the user is hovering the progress-bar. */
				Mouse m = Bot.getClient().getMouse();
				if(m.x >= startX + paddingSize && m.x <= startX + paddingSize + progressWidth && 
						m.y >= startY + paddingSize && m.y <= startY + paddingSize + height - 2 * paddingSize -1) {					
					
					/** Calculate statistics that differ from when not hovering. */
					double expMillis = expGained * (1.0D/timeRunning);					
					int expToLevel = skills.getXPToNextLevel(STAT_AGILITY),
						expNextLevel = expCurrent + expToLevel,
						expPercentDone = skills.getPercentToNextLevel(STAT_AGILITY),
						timeToLevel = (int)Math.round(expToLevel / expMillis);					
					
					/** Set the string to display on the black progress-bar. */
					drawText = "EP: " + expCurrent + "/" + expNextLevel + " (" + expPercentDone + " %) | ER: " + 
						expToLevel + " | TL: " + getFormattedTime(timeToLevel);		
					modifyX = -1;
				} else {
					
					/** Calculate information for display on the black bar. */					
					int agilityLevel = skills.getRealSkillLevel(STAT_AGILITY),
						levelsGained = agilityLevel - startLevel,
						expHour = (int)Math.round(expGained * 3600000D/timeRunning);
						
					/** Set the string to display on the black progress-bar. */
					drawText = "TR: " + getFormattedTime(timeRunning) + " | LC: " + lapsCompleted + " | AL: " +
					agilityLevel + " | LG: " + levelsGained + " | EG: " + expGained + " | EH: " + expHour;			
				}
				
				/** Show the previously calculated statistics. */
				render2d.setColor(Color.WHITE);
				render2d.setFont(new Font("Consolas", Font.PLAIN, 10));			
				render2d.drawString(drawText, startX + paddingSize + progressWidth + 7 + modifyX, startY + paddingSize + height/2 + 1);				
			}
		}
	}
	
	@Override
	public int loop() {			
		
		/** Only run the script if we are logged in and has passed the lobby. */
		if(!(isLoggedIn() && skills.getRealSkillLevel(STAT_AGILITY) > 1)) 
			return random(125, 325);
		
		/** Set maximum altitude and align camera rotation upon starting. */
		if(!cameraAngleRotationSet) {
			setCameraAltitude(true);
			setCameraRotation(0);
			cameraAngleRotationSet = true;
		}
		
		/** Stop the script in case we have failed too many times. */
		if(currentFailures > maximumFailures) {
			log("The script has failed several times.");
			stopScript();
		}
		
		/** Get player information and perform antiban and wait while animating/moving. */
		RSPlayer player = getMyPlayer();
		if(player.getAnimation() != -1 || player.isMoving()) {			
			if(!Calculations.onScreen(getMouseLocation()) && random(1, 6) == 2)
				moveMouse(random(80, CanvasWrapper.getGameWidth() - 332), random(75, CanvasWrapper.getGameHeight() - 235));
			else if(random(1, 20) == 12) moveMouseSlightly();
			return random(75, 250);		
		}
		
		/** Get current player state and check if a lap was just completed. */
		State playerState = getPlayerState();
		if(playerState == State.ROCKS && (previousState == State.SLIDE || previousState == State.GIVESTICK)) {
			if(++lapsCompleted >= maximumLaps && maximumLaps > 0) {
				log("Maximum number of laps (" + maximumLaps + ") has been reached.");
				stopScript();
			}
		}	
		
		/** Stop the script if the maximum time has passed, and we're just about to start a new lap. **/
		if(playerState == State.ROCKS && maximumTime > 0 && startTime > 0 && System.currentTimeMillis() - startTime >= maximumTime) {
			log("Maximum time has been reached.");
			stopScript();
		}

		/** Eat food after the slide has been completed or when starting the course. */		
		if(playerState == State.GIVESTICK || playerState == State.ROCKS) {		
			if(getCurrentHP() < minimumHealth) {	
				if(isUsingStrawberries) {
					for(int n = 0; n < 9; n += 2) {		
						boolean hasStrawberries = inventoryContains(5504);
						if(!hasStrawberries && isInventoryFull()) {
							int dropTries = 0;
							while(isInventoryFull() && ++dropTries < 5) {
								if(!atInventoryItem(5376, "Drop")) {  // Try to drop empty basket
									log("Inventory is full, nothing to drop.");
									stopScript();
								} else wait(985 + random(125, 295));
							}
							
							if(isInventoryFull()) {
								log("Inventory is full, failure when trying to drop.");
								stopScript();
							}
						}
						
						while(getCurrentHP() < minimumHealth && ((hasStrawberries = inventoryContains(5504)) || 
								atInventoryItem(5398 + n, (n == 0) ? "Remove-one" : "Empty"))) {										
							if(!hasStrawberries) wait(965 + random(50, 250));
							while(getCurrentHP() < minimumHealth) {			
								if(atInventoryItem(5504, "Eat")) wait(1175 + random(50, 250));
								else break;
							}
						}
						
						if(getCurrentHP() >= minimumHealth) break;
					}
				} else {				
					while(getCurrentHP() < minimumHealth && inventoryContains(enteredFoodID))
						if(atInventoryItem(enteredFoodID, "Eat")) wait(1475 + random(115, 275));								
				}
				
				if(getCurrentHP() < minimumHealth) {
					log("Character is out of food.");
					stopScript();
				}
			}
		}
		
		/** Reset counters, timers, failures and set previous state. */	
		if(!playerState.equals(previousState)) {
			stateCounter = 0;
			obstacleStartTime = 0;
			currentFailures = 0;
			previousState = playerState;
		}	
		
		/** Perform different actions depending on the state of the character. */
		switch(playerState) {
			case UNKNOWN:
				currentFailures++;
				break;
			case ROCKS:
				final RSTile[] stoneTiles = {new RSTile(3538, 9875), new RSTile(3538, 9877), 
						new RSTile(3540, 9877), new RSTile(3540, 9879), new RSTile(3540, 9881)};
				final RSTile stoneStart = new RSTile(3539, 9873);
				
				if(obstacleStartTime > 0 && System.currentTimeMillis() - obstacleStartTime > 685 && (stateCounter > 0 && !player.getLocation().equals(stoneTiles[stateCounter - 1]))) {
					stateCounter--;
					obstacleStartTime = 0;
				}
				
				if(stateCounter < stoneTiles.length) {					
					if(tileOnScreen(stoneTiles[stateCounter]) && (stateCounter == 0 || (stateCounter > 0 && player.getLocation().equals(stoneTiles[stateCounter - 1])))) {
						if(atTile(stoneTiles[stateCounter], "Jump-to", false, 3, 3, ((stateCounter < 3) ? 3 : 0), ((stateCounter < 2) ? 3 : 0))) {
							waitToMove(865);
							obstacleStartTime = System.currentTimeMillis();
							stateCounter++;
						} else currentFailures++;
					} else if(stateCounter == 0 && !walkTileMM(stoneStart, 1, 2)) {
						if(!walkPathMM(randomizePath(generateProperPath(stoneStart), 1, 1)))
							currentFailures++;
					}
				}
				break;
			case HURDLES:
				if(obstacleStartTime > 0 && System.currentTimeMillis() - obstacleStartTime > 385 && (stateCounter > 0 && player.getLocation().getY() != 9894 + 3 * (stateCounter - 1))) {
					if(player.getLocation().getY() < 9894) stateCounter = 0;
					else if(player.getLocation().getY() < 9897) stateCounter = 1;
					else stateCounter = 2;
					obstacleStartTime = 0;
				}
				
				RSObject currentHurdle = getObjectAt(new RSTile(player.getLocation().getX(), 9893 + 3 * stateCounter));				
				if(stateCounter < 3) {
					if(tileOnScreen(currentHurdle.getLocation()) && (stateCounter == 0 || (stateCounter > 0 && player.getLocation().getY() >= 9894 + 3 * (stateCounter - 1)))) {
						if(atTile(currentHurdle.getLocation(), "Jump", false, 3, 1, 0, -3)) {
							waitToMove(795);
							obstacleStartTime = System.currentTimeMillis();
							stateCounter++;
						} else currentFailures++;
					} else if(stateCounter == 0 && !walkTileMM(new RSTile(player.getLocation().getX() + 1, 9892), 1, 2)) currentFailures++;
				}
				break;
			case PIPES:
				RSObject currentPipe = (stateCounter == 0) ? (getObjectAt((random(0, 2) == 0 ? new RSTile(3541, 9905) 
					: new RSTile(3538, 9905)))) : getNearestObjectByID(5152);		
				
				if(obstacleStartTime > 0 && System.currentTimeMillis() - obstacleStartTime > 5435 && stateCounter > 0 && player.isIdle()) {
					stateCounter = 0;
					obstacleStartTime = 0;
				}
				
				if(currentPipe != null && tileOnScreen(currentPipe.getLocation()) && player.getLocation().getY() < currentPipe.getLocation().getY()) {
					if(atObject(currentPipe, "Squeeze-through")) {
						stateCounter++;
						waitToMove(2165);
						obstacleStartTime = System.currentTimeMillis();
					} else currentFailures++;
				} else if(stateCounter == 0 && !walkTileMM(new RSTile(currentPipe.getLocation().getX(), currentPipe.getLocation().getY() - 1), 1, 2)) currentFailures++;				
				break;
			case GRABSTICK:
				RSItemTile stick = getGroundItemByID(4179);
				
				if(obstacleStartTime > 0 && System.currentTimeMillis() - obstacleStartTime > 3845 && stateCounter > 0 && !inventoryContains(4179)) {
					stateCounter = 0;
					obstacleStartTime = 0;
				}
				
				if(stateCounter == 0 && atTile(stick, "")) {
					stateCounter++;
					waitToMove(2145);	
					obstacleStartTime = System.currentTimeMillis();
				} else if(stateCounter == 0 && !walkTileMM(stick)) currentFailures++;
				break;
			case SLOPE:				
				final RSObject skull = getNearestObjectByID(5136);				
				if(obstacleStartTime > 0 && System.currentTimeMillis() - obstacleStartTime > 1445 && stateCounter > 0 && player.isIdle()) {
					stateCounter = 0;
					obstacleStartTime = 0;
				}
				
				if(stateCounter == 0 && atObject(skull, "Climb-up")) {
					if(waitToMove(765)) {
						obstacleStartTime = System.currentTimeMillis();
						stateCounter++;											
					}
				} else if(stateCounter == 0 && !walkTileMM(new RSTile(skull.getLocation().getX() + 1,
						skull.getLocation().getY() - 2), 2, 1)) currentFailures++;				 
				break;
			case SLIDE:
				RSTile slide = new RSTile(3528, 9911);
				
				if(obstacleStartTime > 0 && System.currentTimeMillis() - obstacleStartTime > 4575 && stateCounter > 0 && player.getAnimation() == -1) {
					stateCounter = 0;
					obstacleStartTime = 0;
				}
				
				if(stateCounter == 0 && atTile(slide, 8, 0, 0, "Teeth-grip")) {
					stateCounter++;
					waitToMove(895);
					obstacleStartTime = System.currentTimeMillis();
				} else if(stateCounter == 0) currentFailures++;
				break;
			case GIVESTICK:				
				RSNPC trainer = getNearestNPCByID(1664);
				
				if(obstacleStartTime > 0 && System.currentTimeMillis() - obstacleStartTime > 1125 && stateCounter > 0 && inventoryContains(4179)) {
					stateCounter = 0;
					obstacleStartTime = 0;
				}
				
				if(trainer != null) {
					if(stateCounter == 0) {
						if(atNPC(trainer, "Give-Stick")) {
							stateCounter++;
							waitToMove(975);
							obstacleStartTime = System.currentTimeMillis();
						} else if(!walkTileMM(trainer.getLocation())) {
							if(!walkPathMM(randomizePath(generateProperPath(trainer.getLocation()), 1, 1)))
								currentFailures++;
							else waitToMove(935);
						} else waitToMove(945);
					}
				} else if(stateCounter == 0) {
					int tempFailures = currentFailures;
					if(!walkTileMM(new RSTile(3528, 9866)))
						if(!walkTileMM(new RSTile(player.getLocation().getX(), 9871)))
							if(!walkTileMM(new RSTile(player.getLocation().getX(), 9880)))								
									currentFailures++;
					if(tempFailures != currentFailures) waitToMove(1285);
				}
				break;
		}
		
		return random(125, 225);
	}
}