import java.awt.Container;
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.lang.Math;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

import org.rsbot.util.ScreenshotUtil;
import org.rsbot.script.Script;
import org.rsbot.script.Constants;
import org.rsbot.script.GEItemInfo;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSNPC;
import org.rsbot.script.wrappers.RSTile;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSInterface;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.PaintListener;

@ScriptManifest(
		authors = { "Paradox" },
		category = "Money-Making",
		name = "Paradox' Al Kharid Tanner",
		version = 2.01,
		description =
"<html>" +
"<head>" +
    "<title>Paradox Al Kharid Tanner</title>" +
    "<style type=\"text/css\">" +
    "body {" +
		"background: #EFEFEF;" +
		"color: #222;" +
		"font-family: Trebushet MS, Verdana, sans-serif;" +
		"font-size: 10px;" +
		"padding: 0;" +
		"margin: 0;" +
	"}" +
    "select {" +
		"font-family: Trebushet MS, Verdana, sans-serif;" +
	"}" +
	"div#header {" +
		"background: #222;" +
		"color: #EFEFEF;" +
		"text-align: center;" +
		"padding: 7px;" +
	"}" +
	"div#content {" +
		"text-align: center;" +
		"padding: 5px;" +
		"font-family: Trebushet MS, Verdana, sans-serif;" +
		"font-size: 9px;" +
	"}" +
	"div#footer {" +
		"background: #222;" +
		"padding: 3.5px;" +
		"color: #EFEFEF;" +
		"text-align: center;" +
	"}" +
    "</style>" +
"</head>" +
"<body>" +
	"<div id=\"header\">" +
        "<h2>Paradox Al Kharid Tanner</h2>" +
   	"</div>" +
    "<div id=\"content\">" +
    "<h3>Instructions</h3>" +
    "Start in the bank of Al Kharid with enough money equipped.<br />Options will be set with the Java GUI." +

    "<br /><br />Profit / Hide<br />" +
    "<img src='http://rsbot.paradox-productions.net/ParadoxAlkharidTanner/signatures/current_rates.png' alt='Hide Rates' />" +
    /*    "<h3>Instructions</h3>" +
        "Start the script in the bank of Al Karid with some money equipped. Make sure the hides you want to use are visible when you open your bank account.<br />" +
        "<table>" +
        "<tr>" +
        "<td>Enable Antiban:</td>" +
        "<td>" +
        "<select name=\"useAntiban\">" +
            "<option>No</option>" +
            "<option>Yes</option>" +
        "</select>" +
        "</td>" +
        "</tr>" +
        "<tr>" +
        "<td>Enable Rest:</td>" +
        "<td>" +
        "<select name=\"useRest\">" +
        	"<option>No</option>" +
            "<option>Yes</option>" +
        "</select>" +
        "</td>" +
        "</tr>" +
        "<tr>" +
        "<td>Enable Debugpaint:</td>" +
        "<td>" +
        "<select name=\"useDebugPaint\">" +
            "<option>Yes</option>" +
            "<option>No</option>" +
        "</select>" +
        "</td>" +
        "</tr>" +
        "<tr>" +
        "<td>Type of the hides:</td>" +
        "<td>" +
        "<select name=\"typeOfHides\">" +
            "<option>Cowhides (Soft Leather)</option>" +
            "<option>Cowhides (Hard Leather)</option><option>Green Dragonhides</option>" +
            "<option>Blue Dragonhides</option><option>Red Dragonhides</option>" +
            "<option>Black Dragonhides</option>" +
        "</select>" +
        "</td>" +
        "</tr>" +
        "<tr>" +
        "<td>Nickname <span style=\"color: #FF0000;\">*</span></td>" +
        "<td><input type=\"text\" name=\"stats_nickname\" style=\"text-align: center;\" /></td>" +
        "</tr>" +
        "<tr>" +
        "<td>Lightweight Paint:</td>" +
        "<td>" +
        "<select name=\"lightweightDebugPaint\">" +
        	"<option>No</option>" +
            "<option>Yes</option>" +
        "</select>" +
        "</td>" +
        "</tr>" +
        "</table>" +
        "<div style=\"background: #FFE5E5; color: #222222; font-size: 9px; margin: 10px 5px 0 5px; padding: 5px;\">" +
            "<span style=\"color: #FF0000;\">* The script will communicate with an external webserver and collect data about runs, times, hides/hour ratio etc..<br />" +
            "This will allow you to see stats on the project page and help me improve the script.<br />If left blank it will count as \"default\" user.<br /><b>DO NOT</b> USE YOUR RUNESCAPE USERNAME.</span>" +
        "</div>" +*/
        "<div style=\"background: #FFF5CC; font-size: 8px; padding: 5px; margin: 5px;\">This script will check automatically for updates and will open a page with stats from the current session on finish.</div>" +
 	"</div>" +
    "<div id=\"footer\">" +
        "<a href=\"http://paradox-projects.tk/\" title=\"www.paradox-projects.tk\" style=\"font-size: 9px; color: #FFFFFF;\">www.paradox-projects.tk</a>" +
  	"</div>" +
"</body>" +
"</html>\n"
		)

public class ParadoxAlkharidTanner extends Script implements PaintListener {

	/* Variables
	************/
	// Items
	private final int utcow = 1739; // Cowhide Untanned
	private final int tcows = 1741; // Cowhide Tanned Soft
	private final int tcowh = 1743; // Cowhide Tanned Hard
	
	private final int utgred = 1753; // Green Dragon Hide Untanned
	private final int tgred = 1745; // Green Dragon Hide Tanned

	private final int utblud = 1751; // Blue Dragon Hide Untanned
	private final int tblud = 2505; // Blue Dragon Hide Tanned

	
	private final int utredd = 1749; // Red Dragon Hide Untanned
	private final int tredd = 2507; // Red Dragon Hide Tanned

	private final int utblad = 1747; // Black Dragon Hide Untanned
	private final int tblad = 2509; // Black Dragon Hide Tanned

	// Money
	private final int money = 995;
	
	// Objects
	private final int bankID = 35647;

	// In Program Used
	private int uth = 0; // Untanned
	private int ttc = 0; // Tanned

	// NPC
	private final int tanner = 2824; // Tanner

	// Time Vars
	private long scriptStartTime = 0;
	private long runTime = 0;
	private long seconds = 0;
	private long minutes = 0;
	private long hours = 0;
	
	// Paint Vars
	private int hidesLeft = 0;
	private int runCount = 0;
	private double profitH = 0;
	private double hidesH = 0;
	private int hidesTanned = 0;
	private double profitMade = 0;
	private int hideTanningCost = 20;
	boolean paintBgSucceed = true;
	boolean lightWeightPaint = false;
	BufferedImage paintBg;
	
	// Update Vars
	private double lastProfit = 0;
	private int lastTanned = 0;
	private long lastUpdate = System.currentTimeMillis();
	private long lastAntiban = System.currentTimeMillis();
	private long nextAntiban = 150000;
	private long lastRuntime = 0;
	private int lastRuncount = 0;
	
	// Profit Calc Vars
	private int tannedPrice = 0;
	private int untannedPrice = 0;
	private int profitPerHide = 0;
	
	// Other
	private String action = null;
	private String prevAction = null;
	private String errorOnFinish= "";
	private String statsNick = "default";
	private String API_URL = "http://rsbot.paradox-productions.net/API.php?action=";
	private String hidesType;
	private int actionID = 1;
	private int prevActionID;
	private int nextAntibanChance = 8;
	
	// GUI Vars
	private boolean debugPaint = false;
	private boolean AntiBan = false;
	private boolean Rest = false;
	private GUI frame;
	private boolean guiOpen = true;
	private double scriptVersion = getClass().getAnnotation(ScriptManifest.class).version();
	private String scriptName = getClass().getAnnotation(ScriptManifest.class).name();
	private boolean hidesCheck = true;
	private boolean disableStats = false;

	// Paths
	RSTile[] BankToTanner = new RSTile[] { new RSTile(3278, 3170), new RSTile(3282, 3181), new RSTile(3274, 3191) };
	RSTile[] TannerToBank = new RSTile[] { new RSTile(3281, 3180), new RSTile(3278, 3170), new RSTile(3270, 3166) };

    public boolean onStart(Map<String, String> args) {		    	
    	// Startup Check
    	if(isLoggedIn()) {
    		
			// Check version
			getVersionOnline();
			
    		// Graphical User Interface
        	frame = new GUI();
    		frame.setVisible(true);
    		while(guiOpen == true) {
    			wait(10);
    		}
    		
			// Check money
			if(!inventoryContains(money)) {
				log("You must have money in your inventory to start the script!");
				return false;
			}
			
			// Hide check state
			else if(hidesCheck == false) {
				log("No hides type found.");
				return false;
			}
			else {
	    		// Start timer script
	    		scriptStartTime = System.currentTimeMillis();
	    		
				log("Startup check: OK");
				log("Note: NEVER sell hides below mid, try selling at max!");
				return true;
			}
    	}
    	else {
    		log("Please login first.");
			log("Startup check: FAILED");
    		return false;
    	}
    }

	public void getVersionOnline() {
		double version;
        try {
			action = "Updating Stats..";
            final URL url = new URL(API_URL + "version");
            final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			version = Double.parseDouble(in.readLine());
			if(version == getClass().getAnnotation(ScriptManifest.class).version()) {
				log("Script is up to date.");
			}
			else if(version == 0) {
				log("Something went wrong while checking for update.");
			}
			else {
				log("There is a new update available.");
				if(JOptionPane.showConfirmDialog(null, "There is an update available. Do you want to go to the download page?", "Update Available!", JOptionPane.YES_NO_OPTION) == 0) {
					try {
						java.awt.Desktop.getDesktop().browse(new URI("http://rsbot.paradox-productions.net/ParadoxAlkharidTanner/?p=Download")); 
		        		errorOnFinish = "Stopping script to update.";
		        		stopScript();
					}
					catch(Exception e) {
						log("Unable to open the download page.");
					}
				}
			}
        }
		catch (Exception e) {
			log("Could not check for updates.");
		}
		wait(100);
		action = prevAction;
		return;
    }
	
	public int calcProfit(final int untanned, final int tanned) {
		try {
			GEItemInfo untannedInfo = grandExchange.loadItemInfo(untanned);
			untannedPrice = untannedInfo.getMarketPrice();
			
			GEItemInfo tannedInfo = grandExchange.loadItemInfo(tanned);
			tannedPrice = tannedInfo.getMarketPrice();
		}
		catch(Exception e) {
			return 0;
		}
		return (tannedPrice - (untannedPrice + (hidesTanned * hideTanningCost)));
	}
	
    public boolean moneyCheck() {
    	if(hidesType.equals("Cowhides (Soft Leather)")) {
        	if(getInventoryCount(money) < (1 * getInventoryCount(uth))) {
        		errorOnFinish = "Not enough money to tan hides.";
        		stopScript();
        	}
    	}
    	else if(hidesType.equals("Cowhides (Hard Leather)")) {
        	if(getInventoryCount(money) < (3 * getInventoryCount(uth))) {
        		errorOnFinish = "Not enough money to tan hides.";
        		stopScript();
        	}    		
    	}
    	else {
        	if(getInventoryCount(money) < (20 * getInventoryCount(uth))) {
        		errorOnFinish = "Not enough money to tan hides.";
        		stopScript();
        	}    	
    	}
    	return true;
    }
    
    public void checkMilestone(final int hides) {/*
    	if(hides > 1000) {
	    		ScreenshotUtil.takeScreenshot(true);
	    		log("Milestone 1000 hides reached, taking screenshot.");
    	}
    	else if(hides > 2500) {
	    		ScreenshotUtil.takeScreenshot(true);
	    		log("Milestone 2500 hides reached, taking screenshot.");
    	}
    	else if(hides > 5000) {
	    		ScreenshotUtil.takeScreenshot(true);
	    		log("Milestone 5000 hides reached, taking screenshot.");
    	}
    	else if(hides > 7500) {
	    		ScreenshotUtil.takeScreenshot(true);
	    		log("Milestone 7500 hides reached, taking screenshot.");
    	}
    	else if(hides > 10000) {
	    		ScreenshotUtil.takeScreenshot(true);
	    		log("Milestone 10000 hides reached, taking screenshot.");
    	}
    	else if(hides > 15000) {
	    		ScreenshotUtil.takeScreenshot(true);
	    		log("Milestone 15000 hides reached, taking screenshot.");
    	}
    	else if(hides > 20000) {
	    		ScreenshotUtil.takeScreenshot(true);
	    		log("Milestone 20000 hides reached, taking screenshot.");
    	}
    	else if(hides > 25000) {
	    		ScreenshotUtil.takeScreenshot(true);
	    		log("Milestone 25000 hides reached, taking screenshot.");
    	}
    	else if(hides > 30000) {
	    		ScreenshotUtil.takeScreenshot(true);
	    		log("Milestone 30000 hides reached, taking screenshot.");
    	}
    	else if(hides > 35000) {
	    		ScreenshotUtil.takeScreenshot(true);
	    		log("Milestone 35000 hides reached, taking screenshot.");
    	}
    	else if(hides > 40000) {
	    		ScreenshotUtil.takeScreenshot(true);
	    		log("Milestone 40000 hides reached, taking screenshot.");
    	}
    	else if(hides > 45000) {
	    		ScreenshotUtil.takeScreenshot(true);
	    		log("Milestone 45000 hides reached, taking screenshot.");
    	}
    	else if(hides > 50000) {
	    		ScreenshotUtil.takeScreenshot(true);
	    		log("Milestone 50000 hides reached, taking screenshot.");
    	}
    	*/
    }
    
	public boolean openBank() {
		final RSObject bank = getNearestObjectByID(bankID);
		if(bank == null) {
			return false;
		}
		if(!tileOnScreen(bank.getLocation())) {
			turnToTile(bank.getLocation(), 15);
			return false;
		}
		else {
			if(!getMyPlayer().isMoving()) {
				atTile(bank.getLocation(), "Use-quickly");
				waitBank(random(500, 600));
				return true;
			}
		}
		return false;
	}
	
	public boolean waitBankItem(final int itemID, final int endTime) {
		final long start = System.currentTimeMillis();
		while (System.currentTimeMillis() - start < endTime) {
				return true;
		}
		return false;
	}

	public boolean waitBank(int endTime) {
		final long counting = System.currentTimeMillis();
		while(System.currentTimeMillis() - counting < endTime) {
			if(!getMyPlayer().isMoving() && (System.currentTimeMillis() - counting) > 1200) {
				return false;
			}
			else if(bank.isOpen()) {
				return true;
			}
		}
		return false;
	}
	
    public boolean turnRunOn() {
    	if(isRunning() == false && actionID != 5) {
    		if(Rest == false) {
    			if(getEnergy() > 85) {
    	    		setRun(true);
    	    		return true;
    			}
    		}
    		else {
	    		setRun(true);
	    		return true;
    		}
    	}
		return false;
    }
    
    public boolean resting() {
		if(Rest == true) {
	    	if(isRunning() == false) {
	    		if(getEnergy() < random(10, 25)) {
	    			prevAction = action;
	    			prevActionID = actionID;
	    			action = "Resting..";
	    			actionID = 5;
	    			if(rest(random(random(80, 85), random(95, 100)))) {
	    				action = prevAction;
	    				actionID = prevActionID;
	    				return true;
	    			}
	    			wait(random(125, 250));
	    		}
	    	}
		}
    	return false;
    }
    
    public boolean banking() {
    	if(RSInterface.getInterface(Constants.INTERFACE_BANK).isValid()) {
    		if(inventoryContains(ttc) == true) {
				hidesTanned = hidesTanned + getInventoryCount(ttc);
    			runCount++;
				bank.depositAllExcept(money);
				wait(250);
    		}
    		else if(inventoryContains(uth) != true) {
    			if(bank.getCount(uth) == 0) {
    				bank.close();
    				errorOnFinish = "All hides have been tanned, mission accomplished.";
    				stopScript();
    			}
    			else {
					bank.atItem(uth, "Withdraw-All");
					waitBankItem(uth, random(900, 1100));
					hidesLeft = bank.getCount(uth);
					moneyCheck();
					bank.close();
				}
    		}
    	}
    	else {
    		openBank();
			wait(random(200, 500));
    	}
    	return true;
    }
	
	private boolean walkPath2(final RSTile[] path, final int randX, final int randY) {
		while(distanceTo(path[path.length - 1]) > 4) {
			if(!walkPathMM(path, randX, randY) || !waitToMove(random(1500, 2200))) {
				return false;
			}
			RSTile dest;
			while((dest = getDestination()) != null && distanceTo(dest) > random(2, 6)) {
				wait(random(100, 250));
			}
		}
		return true;
	}	
	
	private boolean isInArea(final int minX, final int minY, final int maxX, final int maxY) {
    	final int x = getMyPlayer().getLocation().getX();
    	final int y = getMyPlayer().getLocation().getY();
    	if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
    		return true;
    	}
    	return false;
  	}
	
	private boolean tradeTanner() {
		if(!RSInterface.getInterface(324).getChild(0).isValid()) {
			if(inventoryContains(uth)) {
				try {
					RSNPC kid =  getNearestNPCByID(tanner);
						if(kid.isOnScreen()) {
							atNPC(kid, "Trade");
							wait(1500);
							return true;
	                    }
				}
				catch(final Exception e) {
					log("Exception" + e);
				}
			}
			else {
				actionID = 1;
			}
		}
		else {
			return true;
		}
		return false;
	}
	
	private boolean tanHides(final String hides, final int unTannedHides) {
		if(RSInterface.getInterface(324).getChild(0).isValid()) {
				try {
					wait(random(300, 600));
					if(hides.equals("Cowhides (Soft Leather)") && unTannedHides == utcow) {
						atInterface(324, 1, "All");
					}
					else if(hides.equals("Cowhides (Hard Leather)") && unTannedHides == utcow) {
						atInterface(324, 2, "All");
					}
					else if(hides.equals("Green Dragonhides") && unTannedHides == utgred) {
						atInterface(324, 5, "All");
					}
					else if(hides.equals("Blue Dragonhides") && unTannedHides == utblud) {
						atInterface(324, 6, "All");
					}
					else if(hides.equals("Red Dragonhides") && unTannedHides == utredd) {
						atInterface(324, 7, "All");
					}
					else if(hides.equals("Black Dragonhides") && unTannedHides == utblad) {
						atInterface(324, 8, "All");
					}
					else {
						actionID = 1;
					}
					wait(random(1000, 1200));
				}
				catch(final Exception e) {}
			}
			else {
				while(tradeTanner() == false) {
					tradeTanner();
					wait(random(1000, 1500));
				}
			}
		return true;
	}
	
    private boolean antiBan(final int random) {
		if(AntiBan == true && System.currentTimeMillis() - lastAntiban > nextAntiban) {
			prevAction = action;
			prevActionID = actionID;
			action = "Antiban..";
			actionID = 5;
			switch(random(1, 6)) {
				case 1:
					openTab(TAB_ATTACK);
				break;
				case 2:
					openTab(TAB_EQUIPMENT);
				break;
				case 3:
					openTab(TAB_FRIENDS);
				break;
				case 4:
					openTab(TAB_MAGIC);
				break;
				case 5:
					openTab(TAB_QUESTS);
				break;
				case 6:
					openTab(TAB_MUSIC);
				break;
			}
			wait(random(250, 500));
			moveMouse(635, 335, 90, 85);
			wait(random(1500, 3000));
		    
		    if(getCurrentTab() != TAB_INVENTORY) {
				openTab(TAB_INVENTORY);
				wait(random(250, 500));
			}
			setCameraAltitude(true);
			setCompass('N');
			action = prevAction;
			actionID = prevActionID;
	   		nextAntiban = random(random(150000, 200000), random(250000, 300000)); // Min 2.5min, Max 5min
			lastAntiban = System.currentTimeMillis();
			log("Antiban finished, next antiban in " + (nextAntiban / 1000) + " seconds.");
		}
   		return true;
	}
	
	private boolean updateStats(boolean update) {
		if(disableStats == false) {
			// Update every 300000 milliseconds (5min) unless update is true
			if(System.currentTimeMillis() - lastUpdate > 300000 || update) {
				BufferedReader in = null;
				String log = "";
				prevAction = action;
				action = "Updating stats..";
				String curURL = API_URL	+ "submit_stats"
				+ "&nickname="		+ statsNick
				+ "&hidesTanned="	+ (hidesTanned - lastTanned)
				+ "&runCount="		+ (runCount - lastRuncount)
				+ "&profit="		+ (profitMade - lastProfit)
				+ "&time="			+ (runTime - lastRuntime)
				+ "&typeOfHides="	+ hidesType.replace(" ", "%20");
				try {
					in = new BufferedReader(new InputStreamReader(new URL(curURL).openStream()));
					log("Updating stats..");
				}
				catch (final Exception e) {
					log("Error while connecting to API.");
					return false;
				}
				try {
					log = in.readLine();
				}
				catch(Exception e) {
					log("Error while connecting to API.");
				}
				log("API Response: " + log);
				lastTanned = hidesTanned;
				lastProfit = profitMade;
				lastRuncount = runCount;
				lastRuntime = runTime;
				lastUpdate = System.currentTimeMillis();
				curURL = "";
				action = prevAction;
			}
		}
		return false;
	}
	
	private void mainEngine() {
		if(isInArea(3278, 3184, 3283, 3194)) {
			if(!inventoryContains(uth)) {
				action = "Walking to bank..";
				prevAction = action;
				actionID = 1;
			}
		}
		else if(isInArea(3270, 3189, 3277, 3194)) {
			action = "Tanning..";
			prevAction = action;
			if(inventoryContains(uth)) {
				actionID = 2;
			}
			else {
				if(hidesLeft != 0) {
					hidesLeft = hidesLeft - getInventoryCount(ttc);
				}
				actionID = 1;
				action = "Walking to bank..";
			}
		}
		else if(isInArea(3268, 3161, 3272, 3173)) {
			action = "Banking..";
			prevAction = action;
			if(inventoryContains(uth)) {
				actionID = 4;
				action = "Walking to tanner..";
			}
			else {
				actionID = 3;
			}
		}
		else if(isInArea(3273, 3161, 3279, 3173) && inventoryContains(uth)) {
			action = "Walking to tanner..";
			prevAction = action;
			actionID = 4;
		}
	}

	protected int getMouseSpeed() {
		return 7;
	} 
	
    public int loop() {	
		mainEngine();	
		resting();	
		antiBan(random(1, nextAntibanChance));
		switch(actionID) {
			// Walking to tanner
			case 1:
				turnRunOn();
				try {
					walkPath2(TannerToBank, 2, 2);
					antiBan(random(1, 3));
				}
				catch (final Exception e) {}			
			break;
			
			// Tanning
			case 2:
				antiBan(1);
				tanHides(hidesType, uth);
			break;
			
			// Banking
			case 3:
				// Camera Fix
				setCameraAltitude(true);
				setCompass('N');
				
				antiBan(1);
				banking();
				if(inventoryContains(uth)) {
					actionID = 4;
				}
			break;
			
			// Walking to bank
			case 4:
				turnRunOn();
				try {
					walkPath2(BankToTanner, 2, 2);
					antiBan(random(1, 3));
				}
				catch (final Exception e) {}
			break;
		}
		if(profitPerHide == 0) {
			profitPerHide = calcProfit(uth, ttc);
		}
		checkMilestone(hidesTanned);
		moneyCheck();
		updateStats(false);
		return 100;
    }
	
	public static double round(double val, int places) {
		long factor = (long)Math.pow(10,places);
		val = val * factor;
		long tmp = Math.round(val);
		
		return (double)tmp / factor;
    }
	  
    public void onRepaint(Graphics g) {
    	// fix if start time has failed
    	if(scriptStartTime == 0) {
    		scriptStartTime = System.currentTimeMillis();
    	}
		runTime = System.currentTimeMillis() - scriptStartTime;
		seconds = runTime / 1000;
		if(seconds >= 60) {
			minutes = seconds / 60;
			seconds -= minutes * 60;
		}
		if(minutes >= 60) {
			hours = minutes / 60;
			minutes -= hours * 60;
		}
		if(seconds > 0) {
			hidesH = (hidesTanned * 3600) / (hours * 60 * 60 + minutes * 60 + seconds);
		}
		profitMade = profitPerHide * hidesTanned;
		if(seconds > 0) {
			profitH = (profitMade * 3600) / (hours * 60 * 60 + minutes * 60 + seconds);
		}
		if(debugPaint == true) {
			if(paintBgSucceed == false || lightWeightPaint == true) {
				g.setColor(new Color(35, 35, 35, 155));
				g.fillRect(4, 271, 511, 66);
				g.setColor(Color.YELLOW);
				g.setFont(new Font("Tahoma", Font.BOLD, 12));
				g.drawString(getClass().getAnnotation(ScriptManifest.class).name(), 9, 286);
			}
			else {
				g.drawImage(paintBg, 5, 272, null);
			}
			NumberFormat formatter = new DecimalFormat("#0");
			g.setColor(Color.WHITE);
			g.drawRect(4, 271, 511, 66);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Tahoma", Font.PLAIN, 12));
			g.drawString("Version: " + getClass().getAnnotation(ScriptManifest.class).version(), 9, 301);
			g.drawString("Runtime: " + hours + ":" + minutes + ":" + seconds, 9, 316);
			g.drawString("Total runs: " + runCount, 9, 331);
			g.drawString("Untanned hides left: " + hidesLeft, 160, 316);
			g.drawString("Tanned hides: " + hidesTanned, 160, 301);
			g.drawString("Hides / hour: " + formatter.format(hidesH), 160, 331);
			if(profitMade > 10000) {
				g.drawString("Profit: " + round((profitMade/1000), 2) + "K", 349, 301);
			}
			else {
				g.drawString("Profit: " + formatter.format(round(profitMade, 1)) + " GP", 349, 301);
			}
			if(profitH > 10000) {
				g.drawString("Profit / hour: " + round((profitH/1000), 2) + "K/h", 349, 316);
			}
			else {
				g.drawString("Profit / hour: " + formatter.format(round(profitH, 1)) + " GP/h", 349, 316);
			}
			g.drawString("Action: " + action, 349, 331);
		}
    }
    
    public void serverMessageRecieved(final ServerMessageEvent s) {
    	
    }
    
    public void onFinish() {
		ScreenshotUtil.takeScreenshot(true);
    	wait(250);
		updateStats(true);
    	wait(250);
		if(errorOnFinish != "") {
			log(errorOnFinish);
		}
    	wait(250);
    	log("Ran for " + hours + " hours, " + minutes + " minutes, " + seconds + " seconds.");
    	wait(250);
    	log("Tanned " + hidesTanned + " " + hidesType + " in a total of " + runCount + " runs.");
    	wait(250);
    	log("You have made a profit of " + round((profitMade/1000), 2) + "K with an average of " + round((profitH/1000), 2) + "K/h.");
    	wait(250);
    	log("Thank you for using " + getClass().getAnnotation(ScriptManifest.class).name() + " V" + getClass().getAnnotation(ScriptManifest.class).version() + "");
		if(!statsNick.equals("default")) {
			try {
				java.awt.Desktop.getDesktop().browse(new URI("http://rsbot.paradox-productions.net/ParadoxAlkharidTanner/?p=Session&nickname=" + statsNick.replace(" ", "%20") + "&typeOfHides=" + hidesType.replace(" ", "%20") + "&profit=" + profitMade + "&time=" + runTime + "&runs=" + runCount + "&hcount=" + hidesTanned)); 
			}
			catch(Exception e) {
				log("Unable to open session stats page.");
			}
		}
    }
	
    @SuppressWarnings("serial")
    public class GUI extends JFrame {
    	public GUI() {
    		initComponents();
    	}
        
        public void donateURL() {
    		try {
    			java.awt.Desktop.getDesktop().browse(new URI("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=5609909")); 
    		}
    		catch(Exception e) {
    			log("Unable to open session stats page.");
    		}
        }
        
        public void projectPageURL() {
    		try {
    			java.awt.Desktop.getDesktop().browse(new URI("http://paradox-projects.tk/")); 
    		}
    		catch(Exception e) {
    			log("Unable to open session stats page.");
    		}
        }
        
        public void officialThreadURL() {
    		try {
    			java.awt.Desktop.getDesktop().browse(new URI("http://www.powerbot.org/vb/showthread.php?t=50973")); 
    		}
    		catch(Exception e) {
    			log("Unable to open session stats page.");
    		}
        }
        
        public void highscoresURL() {
    		try {
    			java.awt.Desktop.getDesktop().browse(new URI("http://rsbot.paradox-productions.net/ParadoxAlkharidTanner/?p=Hiscores")); 
    		}
    		catch(Exception e) {
    			log("Unable to open session stats page.");
    		}
        }

    	public String changeLogContent() {
    		BufferedReader in = null;
    		try {
    			in = new BufferedReader(new InputStreamReader(new URL(API_URL + "changelog").openStream()));
    		}
    		catch(MalformedURLException e1) {
    			log("Could not find changelog.");
    		}
    		catch(IOException e1) {
    			log("Error while downloading changelog.");
    		}
    		String inputline;
    		String changelog = "";
    		try {
    			while((inputline = in.readLine()) != null) {
    			    changelog = changelog + inputline + "\r\n";
    			}
    		}
    		catch(Exception e) {
    			log("Error while reading content changelog.");
    		}
    		return changelog;
    	}
    	
    	public String termsOfUseContent() {
    		BufferedReader in = null;
    		try {
    			in = new BufferedReader(new InputStreamReader(new URL(API_URL + "termsofuse").openStream()));
    		}
    		catch(MalformedURLException e1) {
    			log("Could not find Terms Of Use.");
    		}
    		catch(IOException e1) {
    			log("Error while downloading Terms Of Use.");
    		}
    		String inputline;
    		String tou = "";
    		try {
    			while((inputline = in.readLine()) != null) {
    			    tou = tou + inputline + "\r\n";
    			}
    		}
    		catch(Exception e) {
    			log("Error while reading content Terms Of Use.");
    		}
    		return tou;
    	}
    	
    	public String lastUpdate() {
    		BufferedReader in = null;
    		try {
    			in = new BufferedReader(new InputStreamReader(new URL(API_URL + "lastupdate").openStream()));
    		}
    		catch(MalformedURLException e1) {
    			log("Could not find last update time.");
    		}
    		catch(IOException e1) {
    			log("Error while downloading last update time.");
    		}
    		String inputline;
    		String lastUpdate = "";
    		try {
    			while((inputline = in.readLine()) != null) {
    				lastUpdate = lastUpdate + inputline + "\r\n";
    			}
    		}
    		catch(Exception e) {
    			log("Error while reading content last update time.");
    		}
    		return lastUpdate;
    	}
    	
    	public double lastVersion() {
    		double version;
            try {
                final URL url = new URL(API_URL + "version");
                final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
    			version = Double.parseDouble(in.readLine());
            }
            catch (Exception e) {
    			log("Could not get last version.");
    			version = 0;
    		}
            return version;
    	}

    	private void ProjectPageButtonActionPerformed(ActionEvent e) {
    		projectPageURL();
    	}

    	private void DonateButtonActionPerformed(ActionEvent e) {
    		donateURL();
    	}

    	private void OfficialThreadButtonActionPerformed(ActionEvent e) {
    		officialThreadURL();
    	}

    	private void HighscoresButtonActionPerformed(ActionEvent e) {
    		highscoresURL();
    	}
    	
    	private void CancelButtonActionPerformed(ActionEvent e) {
    		setVisible(false);
			errorOnFinish = "Canceled by user.";
			stopScript();
    	}
    	
    	private void StartButtonActionPerformed(ActionEvent e) {
    		setVisible(false);
    		guiOpen = false;
    		
    		// Debugpaint enabled?
			if(PaintCheckbox.isSelected()) {
				debugPaint = true;
				if(LightWeightPaintCheckBox.isSelected()) {
					lightWeightPaint = true;
				}
				else {
					// Debug Paint Bg
					try {
						final URL url = new URL("http://rsbot.paradox-productions.net/ParadoxAlkharidTanner/resources/debugpaint_bg.png");
						paintBg = ImageIO.read(url);
					}
					catch(final IOException er) {
						log("Failed to draw the background for the paint. Switching to lightweight paint.");
						paintBgSucceed = false;
					}
				}
			}
			// Antiban enabled?
			if(AntibanCheckBox.isSelected()) {
				AntiBan = true;
			}
			// Rest enabled?
			if(RestCheckBox.isSelected()) {
				Rest = true;
			}
			// Disable stats?
			if(StatsGatheringCheckBox.isSelected()) {
				disableStats = true;
			}
			// Set nickname for stats if not empty
			if(statsNickname.getText().equals("")) {
				statsNick = "default";
			}
			else {
				statsNick = statsNickname.getText();
			}
			// Check hides
			if(TypeOfHidesComboBox.getSelectedItem().equals("Cowhides (Soft Leather)")) {
				uth = utcow;
				ttc = tcows;
				hidesType = "Cowhides (Soft Leather)";
				hideTanningCost = 1;
			}
			else if(TypeOfHidesComboBox.getSelectedItem().equals("Cowhides (Hard Leather)")) {
				uth = utcow;
				ttc = tcowh;
				hidesType = "Cowhides (Hard Leather)";
				hideTanningCost = 3;
			}
			else if(TypeOfHidesComboBox.getSelectedItem().equals("Green Dragonhides")) {
				uth = utgred;
				ttc = tgred;
				hidesType = "Green Dragonhides";
			}
			else if(TypeOfHidesComboBox.getSelectedItem().equals("Blue Dragonhides")) {
				uth = utblud;
				ttc = tblud;
				hidesType = "Blue Dragonhides";
			}
			else if(TypeOfHidesComboBox.getSelectedItem().equals("Red Dragonhides")) {
				uth = utredd;
				ttc = tredd;
				hidesType = "Red Dragonhides";
			}
			else if(TypeOfHidesComboBox.getSelectedItem().equals("Black Dragonhides")) {
				uth = utblad;
				ttc = tblad;
				hidesType = "Black Dragonhides";
			}
			else {
				hidesCheck = false;
			}
    	}
    	
    	private void initComponents() {
    		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
    		MainPane = new JTabbedPane();
    		GeneralOptionsPanel = new JPanel();
    		label1 = new JLabel();
    		label5 = new JLabel();
    		TypeOfHidesComboBox = new JComboBox();
    		label6 = new JLabel();
    		statsNickname = new JTextField();
    		StatsGatheringCheckBox = new JCheckBox();
    		AntibanCheckBox = new JCheckBox();
    		RestCheckBox = new JCheckBox();
    		PaintCheckbox = new JCheckBox();
    		LightWeightPaintCheckBox = new JCheckBox();
    		ChangelogPanel = new JPanel();
    		label2 = new JLabel();
    		scrollPane1 = new JScrollPane();
    		ChangeLogTextPane = new JTextPane();
    		TermsOfUsePanel = new JPanel();
    		label3 = new JLabel();
    		scrollPane2 = new JScrollPane();
    		TermsOfUseTextPane = new JTextPane();
    		InfoPanel = new JPanel();
    		label4 = new JLabel();
    		label8 = new JLabel();
    		label9 = new JLabel();
    		DonateButton = new JButton();
    		OfficialThreadButton = new JButton();
    		HighscoresButton = new JButton();
    		ProjectPageButton = new JButton();
    		label10 = new JLabel();
    		label7 = new JLabel();
    		CancelButton = new JButton();
    		StartScriptButton = new JButton();

    		//======== this ========
    		setTitle(scriptName + " GUI");
    		setResizable(false);
    		setAlwaysOnTop(true);
    		setFont(new Font("Tahoma", Font.PLAIN, 12));
    		setIconImage(null);
    		Container contentPane = getContentPane();

    		//======== MainPane ========
    		{

    			//======== GeneralOptionsPanel ========
    			{

    				//---- label1 ----
    				label1.setText("General Options");
    				label1.setFont(label1.getFont().deriveFont(label1.getFont().getStyle() & ~Font.BOLD, label1.getFont().getSize() + 3f));

    				//---- label5 ----
    				label5.setText("Type Of Hides");

    				//---- TypeOfHidesComboBox ----
    				TypeOfHidesComboBox.setModel(new DefaultComboBoxModel(new String[] {
    					"Cowhides (Soft Leather)",
    					"Cowhides (Hard Leather)",
    					"Green Dragonhides",
    					"Blue Dragonhides",
    					"Red Dragonhides",
    					"Black Dragonhides"
    				}));

    				//---- label6 ----
    				label6.setText("Signature Nickname");

    				//---- StatsGatheringCheckBox ----
    				StatsGatheringCheckBox.setText("Disable Stats Gathering");
    				StatsGatheringCheckBox.setToolTipText("Exclude you from the stats, I don't see the reason, the stats are usefull for you and me.");

    				//---- AntibanCheckBox ----
    				AntibanCheckBox.setText("Enable Antiban");
    				AntibanCheckBox.setToolTipText("You might want to use this, but, this will slow down the script.");

    				//---- RestCheckBox ----
    				RestCheckBox.setText("Enable Rest");
    				RestCheckBox.setToolTipText("Theoretically this should be faster, but practical test are showing the opposite. Your choice.");

    				//---- PaintCheckbox ----
    				PaintCheckbox.setText("Enable Paint");
    				PaintCheckbox.setToolTipText("This will show a compact report of your session.");
    				PaintCheckbox.setSelected(true);

    				//---- LightWeightPaintCheckBox ----
    				LightWeightPaintCheckBox.setText("Enable Lightweight Paint");
    				LightWeightPaintCheckBox.setToolTipText("Use this if you become aware your pc can't handle the script.");

    				GroupLayout GeneralOptionsPanelLayout = new GroupLayout(GeneralOptionsPanel);
    				GeneralOptionsPanel.setLayout(GeneralOptionsPanelLayout);
    				GeneralOptionsPanelLayout.setHorizontalGroup(
    					GeneralOptionsPanelLayout.createParallelGroup()
    						.addGroup(GeneralOptionsPanelLayout.createSequentialGroup()
    							.addContainerGap()
    							.addGroup(GeneralOptionsPanelLayout.createParallelGroup()
    								.addGroup(GeneralOptionsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
    									.addGroup(GeneralOptionsPanelLayout.createSequentialGroup()
    										.addComponent(label5)
    										.addGap(43, 43, 43)
    										.addComponent(TypeOfHidesComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
    									.addComponent(label1, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
    									.addGroup(GeneralOptionsPanelLayout.createSequentialGroup()
    										.addComponent(label6)
    										.addGap(18, 18, 18)
    										.addGroup(GeneralOptionsPanelLayout.createParallelGroup()
    											.addComponent(statsNickname)
    											.addComponent(RestCheckBox)
    											.addComponent(StatsGatheringCheckBox)
    											.addComponent(LightWeightPaintCheckBox, GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))))
    								.addComponent(AntibanCheckBox)
    								.addComponent(PaintCheckbox))
    							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    				);
    				GeneralOptionsPanelLayout.setVerticalGroup(
    					GeneralOptionsPanelLayout.createParallelGroup()
    						.addGroup(GeneralOptionsPanelLayout.createSequentialGroup()
    							.addComponent(label1, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
    							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
    							.addGroup(GeneralOptionsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
    								.addComponent(label5, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
    								.addComponent(TypeOfHidesComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
    							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
    							.addGroup(GeneralOptionsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
    								.addComponent(label6)
    								.addComponent(statsNickname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
    							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
    							.addComponent(StatsGatheringCheckBox)
    							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
    							.addGroup(GeneralOptionsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
    								.addComponent(AntibanCheckBox)
    								.addComponent(RestCheckBox))
    							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
    							.addGroup(GeneralOptionsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
    								.addComponent(PaintCheckbox)
    								.addComponent(LightWeightPaintCheckBox))
    							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    				);
    			}
    			MainPane.addTab("General Options", GeneralOptionsPanel);


    			//======== ChangelogPanel ========
    			{

    				//---- label2 ----
    				label2.setText("Changelog");
    				label2.setFont(label2.getFont().deriveFont(label2.getFont().getStyle() & ~Font.BOLD, label2.getFont().getSize() + 3f));

    				//======== scrollPane1 ========
    				{

    					//---- ChangeLogTextPane ----
    					ChangeLogTextPane.setEditable(false);
    					ChangeLogTextPane.setText(changeLogContent());
    					scrollPane1.setViewportView(ChangeLogTextPane);
    				}

    				GroupLayout ChangelogPanelLayout = new GroupLayout(ChangelogPanel);
    				ChangelogPanel.setLayout(ChangelogPanelLayout);
    				ChangelogPanelLayout.setHorizontalGroup(
    					ChangelogPanelLayout.createParallelGroup()
    						.addGroup(ChangelogPanelLayout.createSequentialGroup()
    							.addContainerGap()
    							.addComponent(label2)
    							.addContainerGap(206, Short.MAX_VALUE))
    						.addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
    				);
    				ChangelogPanelLayout.setVerticalGroup(
    					ChangelogPanelLayout.createParallelGroup()
    						.addGroup(ChangelogPanelLayout.createSequentialGroup()
    							.addComponent(label2, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
    							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
    							.addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
    				);
    			}
    			MainPane.addTab("Changelog", ChangelogPanel);


    			//======== TermsOfUsePanel ========
    			{

    				//---- label3 ----
    				label3.setText("Terms Of Use");
    				label3.setFont(label3.getFont().deriveFont(label3.getFont().getStyle() & ~Font.BOLD, label3.getFont().getSize() + 3f));

    				//======== scrollPane2 ========
    				{
    					TermsOfUseTextPane.setEditable(false);
    					TermsOfUseTextPane.setText(termsOfUseContent());
    					scrollPane2.setViewportView(TermsOfUseTextPane);
    				}

    				GroupLayout TermsOfUsePanelLayout = new GroupLayout(TermsOfUsePanel);
    				TermsOfUsePanel.setLayout(TermsOfUsePanelLayout);
    				TermsOfUsePanelLayout.setHorizontalGroup(
    					TermsOfUsePanelLayout.createParallelGroup()
    						.addGroup(TermsOfUsePanelLayout.createSequentialGroup()
    							.addContainerGap()
    							.addComponent(label3)
    							.addContainerGap(189, Short.MAX_VALUE))
    						.addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
    				);
    				TermsOfUsePanelLayout.setVerticalGroup(
    					TermsOfUsePanelLayout.createParallelGroup()
    						.addGroup(TermsOfUsePanelLayout.createSequentialGroup()
    							.addComponent(label3, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
    							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
    							.addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
    				);
    			}
    			MainPane.addTab("Terms Of Use", TermsOfUsePanel);


    			//======== InfoPanel ========
    			{

    				//---- label4 ----
    				label4.setText("Info");
    				label4.setFont(label4.getFont().deriveFont(label4.getFont().getStyle() & ~Font.BOLD, label4.getFont().getSize() + 3f));

    				//---- label8 ----
    				label8.setText("Last Version: " + lastVersion());

    				//---- label9 ----
    				label9.setText("Last Update: " + lastUpdate());

    				//---- DonateButton ----
    				DonateButton.setText("Donate");

    				//---- OfficialThreadButton ----
    				OfficialThreadButton.setText("Official Thread");

    				//---- HighscoresButton ----
    				HighscoresButton.setText("Highscores");

    				//---- ProjectPageButton ----
    				ProjectPageButton.setText("Project Page");

    				//---- label10 ----
    				label10.setText("\u00a9 2010 All Rights Reserved To Paradox");
    				label10.setHorizontalAlignment(SwingConstants.CENTER);

    				// Button Actions
					ProjectPageButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							ProjectPageButtonActionPerformed(e);
						}
					});
					HighscoresButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							HighscoresButtonActionPerformed(e);
						}
					});
					DonateButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							DonateButtonActionPerformed(e);
						}
					});
					OfficialThreadButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							OfficialThreadButtonActionPerformed(e);
						}
					});
					
					GroupLayout InfoPanelLayout = new GroupLayout(InfoPanel);
					InfoPanel.setLayout(InfoPanelLayout);
					InfoPanelLayout.setHorizontalGroup(
						InfoPanelLayout.createParallelGroup()
							.addGroup(InfoPanelLayout.createSequentialGroup()
								.addContainerGap()
								.addGroup(InfoPanelLayout.createParallelGroup()
									.addGroup(InfoPanelLayout.createSequentialGroup()
										.addComponent(ProjectPageButton, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(HighscoresButton, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
									.addComponent(label4)
									.addComponent(label8)
									.addComponent(label9)
									.addGroup(InfoPanelLayout.createSequentialGroup()
										.addComponent(DonateButton, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(OfficialThreadButton, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)))
								.addContainerGap(15, Short.MAX_VALUE))
							.addComponent(label10, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
					);
					InfoPanelLayout.setVerticalGroup(
						InfoPanelLayout.createParallelGroup()
							.addGroup(InfoPanelLayout.createSequentialGroup()
								.addComponent(label4, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(label8)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(label9)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(InfoPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(DonateButton)
									.addComponent(OfficialThreadButton))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(InfoPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(ProjectPageButton)
									.addComponent(HighscoresButton))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(label10, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
					);
				}
				MainPane.addTab("Info", InfoPanel);

    		}

    		//---- label7 ----
    		label7.setText("Version " + scriptVersion);
    		label7.setFont(label7.getFont().deriveFont(label7.getFont().getSize() - 1f));

    		//---- CancelButton ----
    		CancelButton.setText("Cancel");

			CancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CancelButtonActionPerformed(e);
				}
			});

    		//---- StartScriptButton ----
    		StartScriptButton.setText("Start Script");
    		
			StartScriptButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					StartButtonActionPerformed(e);
				}
			});

    		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
    		contentPane.setLayout(contentPaneLayout);
    		contentPaneLayout.setHorizontalGroup(
    			contentPaneLayout.createParallelGroup()
    				.addGroup(contentPaneLayout.createSequentialGroup()
    					.addContainerGap()
    					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
    						.addComponent(MainPane, GroupLayout.PREFERRED_SIZE, 286, GroupLayout.PREFERRED_SIZE)
    						.addGroup(contentPaneLayout.createSequentialGroup()
    							.addComponent(label7)
    							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    							.addComponent(StartScriptButton)
    							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
    							.addComponent(CancelButton)))
    					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    		);
    		contentPaneLayout.setVerticalGroup(
    			contentPaneLayout.createParallelGroup()
    				.addGroup(contentPaneLayout.createSequentialGroup()
    					.addContainerGap()
    					.addComponent(MainPane, GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE)
    					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
    					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
    						.addComponent(label7, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
    						.addComponent(CancelButton)
    						.addComponent(StartScriptButton))
    					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    		);
    		pack();
    		setLocationRelativeTo(getOwner());
    		// JFormDesigner - End of component initialization  //GEN-END:initComponents
    	}

    	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    	private JTabbedPane MainPane;
    	private JPanel GeneralOptionsPanel;
    	private JLabel label1;
    	private JLabel label5;
    	private JComboBox TypeOfHidesComboBox;
    	private JLabel label6;
    	private JTextField statsNickname;
    	private JCheckBox StatsGatheringCheckBox;
    	private JCheckBox AntibanCheckBox;
    	private JCheckBox RestCheckBox;
    	private JCheckBox PaintCheckbox;
    	private JCheckBox LightWeightPaintCheckBox;
    	private JPanel ChangelogPanel;
    	private JLabel label2;
    	private JScrollPane scrollPane1;
    	private JTextPane ChangeLogTextPane;
    	private JPanel TermsOfUsePanel;
    	private JLabel label3;
    	private JScrollPane scrollPane2;
    	private JTextPane TermsOfUseTextPane;
    	private JPanel InfoPanel;
    	private JLabel label4;
    	private JLabel label8;
    	private JLabel label9;
    	private JButton DonateButton;
    	private JButton OfficialThreadButton;
    	private JButton HighscoresButton;
    	private JButton ProjectPageButton;
    	private JLabel label10;
    	private JLabel label7;
    	private JButton CancelButton;
    	private JButton StartScriptButton;
    	// JFormDesigner - End of variables declaration  //GEN-END:variables
    }
}