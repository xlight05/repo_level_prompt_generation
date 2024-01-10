import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSInterface;
import org.rsbot.script.wrappers.RSNPC;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSTile;
import org.rsbot.util.ScreenshotUtil;
import org.rsbot.script.wrappers.RSInterfaceChild;


@ScriptManifest(authors = "Fletch To 99", category = "Fletching", name = "Universal Fletcher", version = 2.0, description = "<html>"
	+ "<head>"
	+ "<style>"
	+ "body {"
	+ "background-image: url(http://fletchto99scripting.webs.com/pictures/background.png);"
	+ "}"
	+ "div.c3 {text-align: center}"
	+ "p.c2 {color: #FFFFFF}"
	+ "span.c1 {color: #FFFFFF}"
	+ "</style>"
	+ "</head>"
	+ "<body>"
	+ "</body>" + "</html>")

	public class UniversalFletcher extends Script implements ServerMessageListener, PaintListener {

	//Variables
	int logID;
    	private String Bow;
	int bowID;
	int knifeID;
    	public int BowString = 1777;
	int fletchingAnimation = 1248;
	RSTile bankTile = new RSTile (3094, 3491);
	private RSInterface INTERFACE_FLETCH = RSInterface.getInterface(513);
	private RSInterface INTERFACE_LEVELUP = RSInterface.getInterface(740);
	BufferedImage normal = null;
	BufferedImage clicked = null;
	private final RenderingHints rh = new RenderingHints(
			RenderingHints.KEY_TEXT_ANTIALIASING,
			RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    	final int percent = skills.getPercentToNextLevel(Constants.STAT_FLETCHING);
	int bowsCut = 0;
	int bowshr = 0;
	private int mouseSpeed = random(5, 7);
	long startTime = System.currentTimeMillis();
	public int startExp;
	public int startLevel;
	int guiStatus = 0;
	private gui gui;
	public boolean startScript = false;
	int amountToFletch;
	long hours;
	long minutes;
	long seconds;
	int logprice;
	int profit;
	int profitper;
	int bowprice;
	int expGained = 0;
	int expTill99 = 0;
	int xpPerbow;
	int levelsGained = 0;
	String sendText;
	int logstnl;
	int nextLevel;
	int xpToLevel;
	int timeUntil99;
	int hoursTNL, secsTNL, minsTNL;
	int paintInt = 1;
	int totalxp = 13034431;
	private String status = "";
	public int timer = 0;
	private final Color color1 = new Color(0, 0, 0, 150);
	private final Color color2 = new Color(102, 0, 153, 163);
	private final Color color3 = new Color(255, 0, 0);
	public BufferedImage img1 = null;
	public boolean maySayHi = true, chatResponder; 
	private int BANKCHEST[] = { 4483, 12308, 21301, 42192 };
	private int BANKBOOTH[] = { 11402, 2271, 36831, 26972, 42192, 11758,
			25808, 2213, 34752, 34752, 4735, 4736, 1252, 1189, 1187, 4483,
			27663, 12308, 14367, 35647, 36786 };
	private int BANKER[] = { 9710, 7605, 6532, 6533, 6534, 6535,
			2271, 14367, 3824, 44, 45, 2354, 2355, 499, 958, 5488, 8948, 958,
			6362, 5901 };
	public boolean string;
;	boolean gotPrices = false;



	private double getVersion() {
		return 2.0;
	}

	public boolean onStart(final Map<String, String> args) {
		mouseSpeed = random(5, 7);
		gui = new gui();
		gui.setVisible(true);
		paintInt = 1;
		Bow = args.get("bows");

		try {
			final URL cursorURL = new URL("http://fletchto99scripting.webs.com/pictures/cursor.gif");
			normal = ImageIO.read(cursorURL);
		} catch (MalformedURLException e) {
			log("Unable to buffer cursor.");
		} catch (IOException e) {
			log("Unable to open cursor image.");
		}
		while (guiStatus != 1) {
			wait(10);
		}

		try {
			final URL url = new URL(
			"http://fletchto99scripting.webs.com/pictures/pic.png");
			img1 = ImageIO.read(url);
		} catch (final IOException e) {
			log("Failed to get Fletch Pic.");
			e.printStackTrace();
		}

		/** MAJOR credits to OneThatWalks for this AMAZING auto updater. 
		 * 
		 */
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
				"http://fletchto99scripting.webs.com/Scripts/UniversalFletcherVERSION.txt")
				.openConnection();
				// Create an input stream for it
				in = new BufferedReader(new InputStreamReader(url
						.getInputStream()));
				// Check if the current version is outdated
				if (Double.parseDouble(in.readLine()) > getVersion()) {
					// If it is, check if the user would like to update.
					if (JOptionPane.showConfirmDialog(null,
					"Update found. Do you want to update?") == 0) {
						// If so, allow the user to choose the file to be
						// updated.
						JOptionPane
						.showMessageDialog(null,
						"Please choose 'UniversalFletcher.java' in your scripts folder.");
						JFileChooser fc = new JFileChooser();
						// Make sure "Open" was clicked.
						if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
							// If so, set up the URL for the .java file and set
							// up the IO.
							url = new URL(
							"http://fletchto99scripting.webs.com/Scripts/UniversalFletcher.java")
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
							JOptionPane.showMessageDialog(null, "Script successfully downloaded. Please recompile and reload your scripts!");
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
				startScript = true;
			} catch (IOException e) {
				log("Problem getting version.");
				return false; // Return false if there was a problem
			}
		} else {
			startScript = true;
		}
		while (!startScript) {
			wait(10);
		}
		return true;
	}

	public void onFinish() {
		if(isLoggedIn()) {
			if(gui.ssCheck.isSelected()) {
				ScreenshotUtil.takeScreenshot(true);
			}
		}
		log("Thanks for using my script!");
		log("There are " + logstnl + " Maple longs (u) until your next level." );
		log("You fletched " + bowsCut + " bows.");
		log("You gained " + expGained + " exp and " + levelsGained + " fletching level(s).");
		log("The script ran for " + hours + ":" + minutes + ":" + seconds);
	}

	public int getMouseSpeed() {
		return mouseSpeed;
	}

	public boolean bank() {
		status = "Banking";
		wait(random(200, 400));
		RSObject bankBooth = getNearestObjectByID(BANKBOOTH);
		RSObject bankChest = getNearestObjectByID(BANKCHEST);
		final RSNPC banker = getNearestNPCByID(BANKER);
		wait(random(200, 400));
		if (bankChest != null) {
			if (!tileOnScreen(bankChest.getLocation())) {
				turnToTile(bankChest.getLocation(), 15);
			}
			return atTile(bankChest.getLocation(), "Use Bank chest");
		} else if (banker != null) {
			if (tileOnScreen(banker.getLocation())) {
				turnToTile(banker.getLocation(), 15);
			}
			return atTile(banker.getLocation(), "Bank ");
		} else {
			if (!tileOnScreen(bankBooth.getLocation())) {
				turnToTile(bankBooth.getLocation(), 15);
			}
			return atTile(bankBooth.getLocation(), "Use-quickly");
		}
	}


	public boolean chatResponderFunc() {
		status = "Chatting";
		try {
			final int random = random(1, 13);
			String message = "";
			String userName = "";
			RSInterface chatinterface = RSInterface.getInterface(137);
			for (RSInterfaceChild child : chatinterface.getChildren()) {
				if (child.getText().contains("<col=0000ff>")) {
					String user = child.getText().substring(0,
							child.getText().indexOf(":"));
					String text = child.getText().substring(
							child.getText().indexOf("<col=0000ff>") + 12);
					message = text.toLowerCase();
					userName = user.toLowerCase();
				}
			}
			if (userName.contains(getMyPlayer().getName().toLowerCase()) == false) {
				if (message.contains("Fletch") || message.contains("Fletchin")
						|| message.contains("cut")) {
					if (message.contains("lvl") || message.contains("levl")
							|| message.contains("level")) {
						if (message.contains("?") || message.equals("fletchin lvls")
								|| message.equals("flechin lvl")
								|| message.equals("fletching lvl")
								|| message.equals("fletching level")
								|| message.equals("fletchin level")
								|| message.equals("fletching levels")) {
							if (random == 1) {
								sendText(
										"My Fletchin lvl is "
										+ skills
										.getCurrentSkillLevel(Constants.STAT_FLETCHING),
										true);
								log("ChatResponder answered to " + userName
										+ ": '" + message + "'");
								log("ChatResponder's answer: 'My Fletchin lvl is "
										+ skills
										.getCurrentSkillLevel(Constants.STAT_FLETCHING)
										+ "'");
							} else if (random == 2) {
								sendText(
										"My Fletching level is "
										+ skills
										.getCurrentSkillLevel(Constants.STAT_FLETCHING),
										true);
								log("ChatResponder answered to " + userName
										+ ": '" + message + "'");
								log("ChatResponder's answer: 'My fletching level is "
										+ skills
										.getCurrentSkillLevel(Constants.STAT_FLETCHING)
										+ "'");
							} else if (random == 3) {
								sendText(
										""
										+ skills
										.getCurrentSkillLevel(Constants.STAT_FLETCHING),
										true);
								log("ChatResponder answered to " + userName
										+ ": '" + message + "'");
								log("ChatResponder's answer: '"
										+ skills
										.getCurrentSkillLevel(Constants.STAT_FLETCHING)
										+ "'");
							} else if (random == 4) {
								sendText(
										"Mines "
										+ skills
										.getCurrentSkillLevel(Constants.STAT_FLETCHING),
										true);
								log("ChatResponder answered to " + userName
										+ ": '" + message + "'");
								log("ChatResponder's answer: 'Mines "
										+ skills
										.getCurrentSkillLevel(Constants.STAT_FLETCHING)
										+ "'");
							} else if (random == 5) {
								sendText(
										"My fletchin level is "
										+ skills
										.getCurrentSkillLevel(Constants.STAT_FLETCHING),
										true);
								log("ChatResponder answered to " + userName
										+ ": '" + message + "'");
								log("ChatResponder's answer: 'My fletchin level is "
										+ skills
										.getCurrentSkillLevel(Constants.STAT_FLETCHING)
										+ "'");
							} else if (random == 6) {
								sendText(
										"My fletching is "
										+ skills
										.getCurrentSkillLevel(Constants.STAT_FLETCHING),
										true);
								log("ChatResponder answered to " + userName
										+ ": '" + message + "'");
								log("ChatResponder's answer: 'My fletching is "
										+ skills
										.getCurrentSkillLevel(Constants.STAT_FLETCHING)
										+ "'");
							} else if (random == 7) {
								sendText(
										"My fletchin is "
										+ skills
										.getCurrentSkillLevel(Constants.STAT_FLETCHING),
										true);
								log("ChatResponder answered to " + userName
										+ ": '" + message + "'");
								log("ChatResponder's answer: 'My fletchin is "
										+ skills
										.getCurrentSkillLevel(Constants.STAT_FLETCHING)
										+ "'");
							} else if (random == 8) {
								sendText(
										"My fletching level is "
										+ skills
										.getCurrentSkillLevel(Constants.STAT_FLETCHING),
										true);
								log("ChatResponder answered to " + userName
										+ ": '" + message + "'");
								log("ChatResponder's answer: 'My fletching level is "
										+ skills
										.getCurrentSkillLevel(Constants.STAT_FLETCHING)
										+ "'");
							} else if (random == 9) {
								sendText(
										"My fletchy lvl is "
										+ skills
										.getCurrentSkillLevel(Constants.STAT_FLETCHING),
										true);
								log("ChatResponder answered to " + userName
										+ ": '" + message + "'");
								log("ChatResponder's answer: 'My fletchy lvl is "
										+ skills
										.getCurrentSkillLevel(Constants.STAT_FLETCHING)
										+ "'");
							} else if (random == 10) {
								sendText(
										"My fletchin is "
										+ skills
										.getCurrentSkillLevel(Constants.STAT_FLETCHING),
										true);
								log("ChatResponder answered to " + userName
										+ ": '" + message + "'");
								log("ChatResponder's answer: 'My fletchin is "
										+ skills
										.getCurrentSkillLevel(Constants.STAT_FLETCHING)
										+ "'");
							} else if (random == 11) {
								sendText(
										"My fletch level is "
										+ skills
										.getCurrentSkillLevel(Constants.STAT_FLETCHING),
										true);
								log("ChatResponder answered to " + userName
										+ ": '" + message + "'");
								log("ChatResponder's answer: 'My fletch level is "
										+ skills
										.getCurrentSkillLevel(Constants.STAT_FLETCHING)
										+ "'");
							} else if (random == 12) {
								sendText(
										"My fletchin lvl is "
										+ skills
										.getCurrentSkillLevel(Constants.STAT_FLETCHING),
										true);
								log("ChatResponder answered to " + userName
										+ ": '" + message + "'");
								log("ChatResponder's answer: 'My fletchin lvl is "
										+ skills
										.getCurrentSkillLevel(Constants.STAT_FLETCHING)
										+ "'");
							} else if (random == 13) {
								sendText(
										"My fletchin Level is "
										+ skills
										.getCurrentSkillLevel(Constants.STAT_FLETCHING),
										true);
								log("ChatResponder answered to " + userName
										+ ": '" + message + "'");
								log("ChatResponder's answer: 'The fletchin of mine is "
										+ skills
										.getCurrentSkillLevel(Constants.STAT_FLETCHING)
										+ "'");
							}
						}
					}
				} else if (message.contains("boting")
						|| message.contains("botting")
						|| message.contains("bottting")
						|| message.contains("botttting")
						|| message.contains("bottttting")
						|| message.contains("botin")
						|| message.contains("bottin")) {
					if (message.contains("?")
							|| message.contains(getMyPlayer().getName()
									.toLowerCase())
									|| message.contains("tough")) {
						final int random3 = random(1, 7);
						if (random3 == 1) {
							sendText("no, why?", true);
							log("ChatResponder answered to " + userName + ": '"
									+ message + "'");
							log("ChatResponder's answer: 'No, why?'");
						} else if (random3 == 2) {
							sendText("no, ignoring", true);
							log("ChatResponder answered to " + userName + ": '"
									+ message + "'");
							log("ChatResponder's answer: 'No, ignoring'");
						} else if (random3 == 3) {
							sendText("no, why do u think that?", true);
							log("ChatResponder answered to " + userName + ": '"
									+ message + "'");
							log("ChatResponder's answer: 'No, why do u think that?'");
						} else if (random3 == 4) {
							sendText("no, just ignoring chat", true);
							log("ChatResponder answered to " + userName + ": '"
									+ message + "'");
							log("ChatResponder's answer: 'No, just ignoring chat'");
						} else if (random3 == 5) {
							sendText("no", true);
							log("ChatResponder answered to " + userName + ": '"
									+ message + "'");
							log("ChatResponder's answer: 'No'");
						} else if (random3 == 6) {
							sendText("lol, no", true);
							log("ChatResponder answered to " + userName + ": '"
									+ message + "'");
							log("ChatResponder's answer: 'Lol, no'");
						} else if (random3 == 7) {
							sendText("nope", true);
							log("ChatResponder answered to " + userName + ": '"
									+ message + "'");
							log("ChatResponder's answer: 'Nope'");
						}
					}
				} else if (message.contains("sup")
						|| message.contains("what's up")
						|| message.contains("whats up")
						|| message.contains("wat's up")
						|| message.contains("wats up")) {
					final int random3 = random(1, 5);
					if (random3 == 1) {
						sendText("fletchin", true);
						log("ChatResponder answered to " + userName + ": '"
								+ message + "'");
						log("ChatResponder's answer: 'fletchin'");
					} else if (random3 == 2) {
						sendText("Fletching", true);
						log("ChatResponder answered to " + userName + ": '"
								+ message + "'");
						log("ChatResponder's answer: 'fletching'");
					} else if (random3 == 3) {
						sendText("fletchy :)", true);
						log("ChatResponder answered to " + userName + ": '"
								+ message + "'");
						log("ChatResponder's answer: 'Fletchy :)'");
					} else if (random3 == 4) {
						sendText("Fletchin :D", true);
						log("ChatResponder answered to " + userName + ": '"
								+ message + "'");
						log("ChatResponder's answer: 'Fletching :D'");
					} else if (random3 == 5) {
						sendText("just fletchin", true);
						log("ChatResponder answered to " + userName + ": '"
								+ message + "'");
						log("ChatResponder's answer: 'Just fletchin'");
					}
				} else if (message.equals("hi") || message.equals("hello")
						|| message.equals("hello!") || message.equals("hello.")
						|| message.equals("hi!") || message.equals("hi.")) {
					final int random2 = random(1, 6);
					if (maySayHi == true) {
						if (random2 == 1) {
							sendText("hi!", true);
							log("ChatResponder answered to " + userName + ": '"
									+ message + "'");
							log("ChatResponder's answer: 'Hi!'");
						} else if (random2 == 2) {
							sendText("hi.", true);
							log("ChatResponder answered to " + userName + ": '"
									+ message + "'");
							log("ChatResponder's answer: 'Hi.'");
						} else if (random2 == 3) {
							sendText("hi", true);
							log("ChatResponder answered to " + userName + ": '"
									+ message + "'");
							log("ChatResponder's answer: 'Hi'");
						} else if (random2 == 4) {
							sendText("hello!", true);
							log("ChatResponder answered to " + userName + ": '"
									+ message + "'");
							log("ChatResponder's answer: 'Hello!'");
						} else if (random2 == 5) {
							sendText("hello.", true);
							log("ChatResponder answered to " + userName + ": '"
									+ message + "'");
							log("ChatResponder's answer: 'Hello.'");
						} else if (random2 == 6) {
							sendText("hello", true);
							log("ChatResponder answered to " + userName + ": '"
									+ message + "'");
							log("ChatResponder's answer: 'Hello'");
						}
						timer = 1;
					}
				} else if (message.equals(getMyPlayer().getName().toLowerCase()
						+ "?")
						|| message.equals(getMyPlayer().getName().toLowerCase()
								+ "=")
								|| message.contains(getMyPlayer().getName()
										.toLowerCase())
										|| message.contains(getMyPlayer().getName()
												.toLowerCase()
												+ " ")
												|| message.contains(" "
														+ getMyPlayer().getName().toLowerCase())) {
					final int random2 = random(1, 6);
					if (random2 == 1) {
						sendText("what?", true);
						log("ChatResponder answered to " + userName + ": '"
								+ message + "'");
						log("ChatResponder's answer: 'What?'");
					} else if (random2 == 2) {
						sendText("?", true);
						log("ChatResponder answered to " + userName + ": '"
								+ message + "'");
						log("ChatResponder's answer: '?'");
					} else if (random2 == 3) {
						sendText("wat?", true);
						log("ChatResponder answered to " + userName + ": '"
								+ message + "'");
						log("ChatResponder's answer: 'wat?'");
					}
				} else if (message.contains(getMyPlayer().getName()
						.toLowerCase())) {
					final int random2 = random(1, 2);
					if (random2 == 1) {
						sendText("I'm busy srry", true);
						log("ChatResponder answered to " + userName + ": '"
								+ message + "'");
						log("ChatResponder's answer: 'I'm busy srry'");
					} else if (random2 == 1) {
						sendText("busy...sry can't talk", true);
						log("ChatResponder answered to " + userName + ": '"
								+ message + "'");
						log("ChatResponder's answer: 'busy...sry can't talk'");
					}
				}
			}
		} catch (Exception e) {

		}
		return true;
	}

	public boolean useKnifeOnLogs() {
		if (getCurrentTab() != Constants.TAB_INVENTORY
				&& !RSInterface.getInterface(Constants.INTERFACE_BANK)
				.isValid()) {
			openTab(Constants.TAB_INVENTORY);
		}
		if (getCurrentTab() == Constants.TAB_INVENTORY
				&& !RSInterface.getInterface(Constants.INTERFACE_BANK)
				.isValid()
				&& getMyPlayer().getAnimation() == -1
				&& inventoryContainsOneOf(knifeID)) {
			atInventoryItem(knifeID, "Use");
			wait(random(700, 900));
			if (isItemSelected()) {
				atInventoryItem(logID, "Use");
				wait(random(1000, 1300));
			}
		}
		return false;
	}

	public boolean cutLogs() {
			   status = "Fletching";
		if (gui.logType.getSelectedItem() == "Normal") {
			if(gui.bowType.getSelectedItem() == "Short Bow") {
				moveMouse(random(175, 220), random(410, 460));
			}
			if(gui.bowType.getSelectedItem() == "Long Bow") {
				moveMouse(random(300, 340), random(410, 460));
			}	
		} else if(gui.logType.getSelectedItem() == "Magic") {
			if (gui.bowType.getSelectedItem() == "Long Bow") {
				moveMouse(random(365, 410), random(400, 460));
			} else if (gui.bowType.getSelectedItem() == "Short Bow") {
				moveMouse(random(105, 150), random(400, 460));
			}
		} else {
			if (gui.bowType.getSelectedItem() == "Long Bow") {
				moveMouse(random(240, 280), random(400, 460));
			} else if (gui.bowType.getSelectedItem() == "Short Bow") {
				moveMouse(random(85, 130), random(400, 460));
			}
		}
		wait(random(400, 800));
		atMenu("Make X");
		wait(random(1700, 2000));
		if(INTERFACE_FLETCH.isValid()) {
			wait(random(800, 1200));
		}
		sendText = Integer.toString(random(27, 100));
		sendText(sendText, true);
		wait(2000);
		if (getMyPlayer().getAnimation() == fletchingAnimation) {
			return true;
		}
		return false;
	}

	public void loadGEInfo(final int bowID, final int logID) {
		try {
			new Thread(new Runnable() {
				public void run() {
					bowprice = grandExchange.loadItemInfo(bowID)
							.getMarketPrice();
					logprice = grandExchange.loadItemInfo(logID)
							.getMarketPrice();
				}
			}).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


    public void getKnife() {
        bank();
        if (!inventoryContains(knifeID)) {
            status = "Getting Knife";
            bank.depositAll();
            bank.withdraw(knifeID, 1);
            wait(random(450, 600));
	}

    }

	public int loop() {
		if (!inventoryContains(knifeID)) {
                status = "Getting Knife";
                getKnife();
            }
		amountToFletch = Integer.parseInt(gui.amountToFletch.getText());
		if(INTERFACE_FLETCH.isValid()) {
			cutLogs();
			antiban();
		}
		if (chatResponder == true) {
				while (getMyPlayer().getAnimation() == fletchingAnimation) {
			chatResponderFunc();
			}
		}

		if(INTERFACE_LEVELUP.isValid()) {
			moveMouse(random(240, 340), random(465, 469));
			wait(random(400, 800));
			clickMouse(true);
			wait(random(2400, 3000));
		}

		if (bowsCut >= amountToFletch && amountToFletch != 0) {
			if(gui.ssCheck.isSelected()) {
				ScreenshotUtil.takeScreenshot(true);
			}
			bank.close();
			clickMouse(757, 3, true);
			clickMouse(757, 3, true);
			clickMouse(757, 3, true);
			clickMouse(757, 3, true);
			clickMouse(757, 3, true);
			clickMouse(637, 417, true);
			clickMouse(637, 417, true);
			clickMouse(637, 417, true);
			clickMouse(637, 417, true);
			clickMouse(637, 417, true);
			stopScript();
		}

		if(!inventoryContainsOneOf(logID)
				&& getMyPlayer().getAnimation() == -1
				&& !RSInterface.getInterface(Constants.INTERFACE_BANK)
				.isValid()) {
			if(isItemSelected()) {
				moveMouse(600, 212);
				clickMouse(true);
			}
			bank();
		}
		if(bank.isOpen()
				&& inventoryContains(knifeID) && !inventoryContains(logID)) {
			if(inventoryContains(knifeID)) {
				bank.depositAllExcept(knifeID);
				wait(random(450, 500));
							}
			if(!inventoryContains(bowID)) {
				if(bank.getItemByID(logID) == null) {
					if(gui.ssCheck.isSelected()) {
						ScreenshotUtil.takeScreenshot(true);
					}
					bank.close();
					clickMouse(757, 3, true);
					clickMouse(757, 3, true);
					clickMouse(757, 3, true);
					clickMouse(757, 3, true);
					clickMouse(757, 3, true);
					clickMouse(637, 417, true);
					clickMouse(637, 417, true);
					clickMouse(637, 417, true);
					clickMouse(637, 417, true);
					clickMouse(637, 417, true);
					stopScript();
				}
				bank.withdraw(logID, 0);
				wait(random(850, 1000));
			}
		}
		if(inventoryContains(logID)
				&& RSInterface.getInterface(Constants.INTERFACE_BANK).isValid()) {
			bank.close();
		}
		if (Bot.getClient().getCameraPitch() != 3072) {
			setCameraAltitude(true);
			wait(random(500, 750));
		}
		if(inventoryContains(logID) && inventoryContainsOneOf(knifeID)) {
			useKnifeOnLogs();
		}
		return random(200, 400);

	}

	private int antiban() {
		status = "Antiban";
		int i = random(1, 5);
		if(i == 1) {
			openTab(Constants.INTERFACE_TAB_STATS);
			wait(random(800, 1200));
			moveMouse(random(620, 660), random(355, 370));
			wait(random(1200, 1700));
			return random(200, 400);
		}
		if(i == 3) {
			openTab(Constants.INTERFACE_TAB_STATS);
			wait(random(800, 1200));
			moveMouse(random(560, 600), random(220, 450));
			wait(random(1400, 1800));
			return random(200, 400);
		}
		if(i == 2 || i == 4) {
			moveMouse(random(60, 360), -1);
			wait(random(3400, 4000));
			return random(200, 400);
		}
		if(i == 5) {
			openTab(Constants.INTERFACE_TAB_FRIENDS);
			wait(random(800, 1324));
			moveMouse(random(380, 521), random(120, 321));
			wait(random(1400, 1800));
			return random(200, 400);
		}
		return(random(1000, 1500));
	}

	@Override
	public void serverMessageRecieved(ServerMessageEvent e) {
		String word = e.getMessage().toLowerCase();
		if (word.contains("longbow") || word.contains("shortbow")) {
			bowsCut++;
		}
	}

	@Override
	public void onRepaint(Graphics g) {
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);  
		if (isLoggedIn() && startScript) {

			if (gui.knifetype.getSelectedItem() == "Normal") {
				knifeID = 946;
			} else if (gui.knifetype.getSelectedItem() == "Sacred Clay") {
				knifeID = 14111;
			}

			if (gui.locationBox.getSelectedItem().toString().equals("Yes")) {
				chatResponder = true;
			} else if (gui.locationBox.getSelectedItem().toString().equals("No")) {
				chatResponder = false;
			}

			if (gui.logType.getSelectedItem() == "Normal") {
				logID = 1511;
				if (gui.bowType.getSelectedItem() == "Short Bow") {
					bowID = 50;
					xpPerbow = 5;
				}
				if (gui.bowType.getSelectedItem() == "Long Bow") {
					bowID = 48;
					xpPerbow = 10;
				}
			}
			if (gui.logType.getSelectedItem() == "Oak") {
				logID = 1521;
				if (gui.bowType.getSelectedItem() == "Short Bow") {
					bowID = 54;
					xpPerbow = 16;
				}
				if (gui.bowType.getSelectedItem() == "Long Bow") {
					bowID = 56;
					xpPerbow = 25;
				}
			}
			if (gui.logType.getSelectedItem() == "Willow") {
				logID = 1519;
				if (gui.bowType.getSelectedItem() == "Short Bow") {
					bowID = 60;
					xpPerbow = 33;
				}
				if (gui.bowType.getSelectedItem() == "Long Bow") {
					bowID = 58;
					xpPerbow = 41;
				}
			}
			if (gui.logType.getSelectedItem() == "Maple") {
				logID = 1517;
				if (gui.bowType.getSelectedItem() == "Short Bow") {
					bowID = 64;
					xpPerbow = 50;
				}
				if (gui.bowType.getSelectedItem() == "Long Bow") {
					bowID = 62;
					xpPerbow = 58;
				}
			}
			if (gui.logType.getSelectedItem() == "Yew") {
				logID = 1515;
				if (gui.bowType.getSelectedItem() == "Short Bow") {
					bowID = 68;
					xpPerbow = 67;
				}
				if (gui.bowType.getSelectedItem() == "Long Bow") {
					bowID = 66;
					xpPerbow = 75;
				}
			}
			if (gui.logType.getSelectedItem() == "Magic") {
				logID = 1513;
				if (gui.bowType.getSelectedItem() == "Short Bow") {
					bowID = 72;
					xpPerbow = 83;
				}
				if (gui.bowType.getSelectedItem() == "Long Bow") {
					bowID = 70;
					xpPerbow = 91;
				}
			}



			if (startTime == 0) {
				startTime = System.currentTimeMillis();
			}
			if (startExp == 0) {
				startExp = skills.getCurrentSkillExp(Constants.STAT_FLETCHING);
			}
			if (startLevel == 0) {
				startLevel = skills.getCurrentSkillLevel(Constants.STAT_FLETCHING);
			}

			levelsGained = skills.getCurrentSkillLevel(STAT_FLETCHING)
			- startLevel;
			expTill99 = totalxp
			 - skills.getCurrentSkillExp(STAT_FLETCHING);
			logstnl = (int) (skills.getXPToNextLevel(Constants.STAT_FLETCHING) / xpPerbow + 1);
			bowshr = (int)(new Double(bowsCut) / new Double(System.currentTimeMillis() - startTime) * new Double(60 * 60 * 1000));
			expGained = skills.getCurrentSkillExp(STAT_FLETCHING) - startExp;
			long millis = System.currentTimeMillis() - startTime;
			hours = millis / (1000 * 60 * 60);
			millis -= hours * (1000 * 60 * 60);
			minutes = millis / (1000 * 60);
			millis -= minutes * (1000 * 60);
			seconds = millis / 1000;
			float xpsec = 0;
			if ((minutes > 0 || hours > 0 || seconds > 0) && expGained > 0) {
				xpsec = ((float) expGained)
				/ (float) (seconds + (minutes * 60) + (hours * 60 * 60));
			}
			float xpmin = xpsec * 60;
			float xphour = xpmin * 60;
			timeUntil99 = (int) Math.floor(expTill99/xphour);
			nextLevel = skills.getCurrentSkillLevel(STAT_FLETCHING) + 1;
			xpToLevel = skills.getXPToNextLevel(STAT_FLETCHING);
			hoursTNL = (int) Math.floor(xpToLevel/xphour);
			minsTNL = (int) Math.floor(((xpToLevel/xphour) - hoursTNL) * 60);
			secsTNL = (int) Math.floor(((((xpToLevel/xphour) - hoursTNL) * 60) - minsTNL) * 60);
			profitper = bowprice - logprice;
			profit = profitper * bowsCut;
			Mouse m = Bot.getClient().getMouse();
			if (m.x >= 400 && m.x < 400 + 50 && m.y >= 25 && m.y < 25 + 15) {
				paintInt = 1;
			}
			if (m.x >= 450 && m.x < 450 + 50 && m.y >= 25 && m.y < 25 + 15) {
				paintInt = 2;
			}
			if (m.x >= 496 && m.x < 496 + 14 && m.y >= 345 && m.y < 345 + 113) {
				paintInt = 3;
			}

			if (bowID != 0 && logID != 0 && !gotPrices) {
				loadGEInfo(bowID, logID);
				gotPrices = true;
			}

			if(xphour == 0) {
				hoursTNL = 0;
				minsTNL = 0;
				secsTNL = 0;
			}

			g.setColor(color1);
			g.fillRoundRect(410, 5, 88, 55, 16, 16);
			g.setColor(color3);
			g.drawRoundRect(410, 5, 88, 55, 16, 16);

			g.setFont(new Font("Tahoma", 0, 12));
			g.setColor(new Color(255, 255, 255));
			g.drawString("Paint", 438, 22);
			g.setFont(new Font("Tahoma", 0, 12));
			g.setColor(new Color(255, 255, 255));
			g.drawString("On", 416, 38);
			g.setFont(new Font("Tahoma", 0, 12));
			g.setColor(new Color(255, 255, 255));
			g.drawString("Off", 467, 38);
			g.setFont(new Font("Tahoma", 0, 9));
			g.setColor(new Color(255, 255, 255));
			g.drawString("By: Fletch To 99", 420, 53);
        		g.setColor(color1);
        		g.fillRect(496, 345, 14, 113);
        		g.setColor(color3);;
        		g.drawRect(496, 345, 14, 113);
			g.setFont(new Font("Tahoma", 0, 12));
			g.setColor(new Color(255, 255, 255));
			g.drawString("M", 500, 360);
			g.drawString("I", 502, 370);
			g.drawString("N", 500, 380);
			g.drawString("I", 502, 390);
			g.drawString("P", 500, 410);
			g.drawString("A", 500, 420);
			g.drawString("I", 502, 430);
			g.drawString("N", 500, 440);
			g.drawString("T", 500, 450);

			if (paintInt == 1) {
				g.setColor(color1);
				g.fillRoundRect(550, 204, 184, 260, 16, 16);
				g.setColor(color3);
				g.drawRoundRect(550, 204, 184, 260, 16, 16);
				g.drawImage(img1, 650, 250, null);
				g.setFont(new Font("Tahoma", 0, 22));
				g.setColor(new Color(250, 0, 0));
				g.drawString("Universal Fletcher", 555, 230);
				g.setColor(new Color(250, 0, 0));
				g.setFont(new Font("Tahoma", 0, 12));
				g.setColor(new Color(250, 0, 0));
				g.drawString("Runtime: " + hours + ":" + minutes + ":" + seconds, 560, 245);
				g.setFont(new Font("Tahoma", 0, 12));
				g.setColor(new Color(250, 0, 0));
				g.drawString("Logs Cut: " + bowsCut, 560, 260);
				g.setFont(new Font("Tahoma", 0, 12));
				g.setColor(new Color(250, 0, 0));
				g.drawString("Exp Gained: " + expGained, 560, 275);
				g.setFont(new Font("Tahoma", 0, 12));
				g.setColor(new Color(250, 0, 0));
				g.drawString("Exp/hr: " + (int) xphour, 560, 290);
				g.setFont(new Font("Tahoma", 0, 12));
				g.setColor(new Color(250, 0, 0));
				g.drawString("Hours to 99: " + timeUntil99, 560, 305);
				g.setFont(new Font("Tahoma", 0, 12));
				g.setColor(new Color(250, 0, 0));
				g.drawString("Lvls Gained: " + levelsGained, 560, 320);
				g.setFont(new Font("Tahoma", 0, 12));
				g.setColor(new Color(250, 0, 0));
                        	g.drawString("Logs/hr: " + "" + bowshr, 560, 335);
				g.setFont(new Font("Tahoma", 0, 12));
				g.setColor(new Color(250, 0, 0));
                    		g.drawString( "Logs TNL: " + "" + logstnl, 560, 350);
				g.setFont(new Font("Tahoma", 0, 12));
				g.setColor(new Color(250, 0, 0));
				g.drawString("Exp TNL: " + xpToLevel, 560, 365);
				g.setFont(new Font("Tahoma", 0, 12));
				g.setColor(new Color(250, 0, 0));
				g.drawString("Time TNL: " + hoursTNL + ":" + minsTNL + ":" + secsTNL, 560, 380);
				g.drawString("Status: " + status , 560, 395);
				g.setColor(new Color(250, 0, 0));
				g.drawRect(565, 405, 155, 20);
				g.setColor(new Color(31, 150, 31));
				g.fillRect(566, 406, (skills
						.getPercentToNextLevel(STAT_FLETCHING) * 155 / 100), 19);
				g.setFont(new Font("Tahoma", 0, 12));
				g.setColor(new Color(250, 0, 0));
				g.drawString("" + skills.getPercentToNextLevel(STAT_FLETCHING) +
						"% until " + nextLevel + " fletching", 582, 420);
				g.setFont(new Font("Tahoma", 0, 12));
				g.setColor(new Color(250, 0, 0));
				g.drawString("Profit: " + profit + "gp", 560, 450);
			}
            if (paintInt == 3) {
        g.setColor(color1);
        g.fillRoundRect(368, 215, 147, 120, 16, 16);
        g.setColor(color3);
        g.drawRoundRect(368, 215, 147, 120, 16, 16);
	g.setFont(new Font("Tahoma", 0, 12));
	g.setColor(new Color(250, 0, 0));
	g.drawString("Profit: " + profit + "gp", 378, 227);
	g.drawString("Logs Cut: " + bowsCut, 378, 242);
        g.drawString( "Logs TNL: " + "" + logstnl, 378, 257);
	g.drawString("Time TNL: " + hoursTNL + ":" + minsTNL + ":" + secsTNL, 378, 272);
        g.drawString( "Level: " + "" + (skills.getCurrentSkillLevel(STAT_FLETCHING)), 378, 287);
	g.drawString("Lvls Gained: " + levelsGained, 378, 302);
	g.drawString("Status: " + status , 378, 317);
	g.drawString("Runtime: " + hours + ":" + minutes + ":" + seconds, 378, 333);

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
					g.drawImage(normal, mouse_x - 8, mouse_y - 8, null); // this
					// show
					// the
					// mouse
					// when
					// its
					// not
					// clicked
				}
				if (mpt < 1000) {
					g.drawImage(clicked, mouse_x2 - 8, mouse_y2 - 8, null); // this
					// show
					// the
					// four
					// squares
					// where
					// you
					// clicked.
					g.drawImage(normal, mouse_x - 8, mouse_y - 8, null); // this
					// show
					// the
					// mouse
					// as
					// normal
					// when
					// its/just
					// clicked
				}
				((Graphics2D) g).setRenderingHints(rh);
			}
		}
	}



	@SuppressWarnings("serial")
	public class gui extends javax.swing.JFrame {

		/** Creates new form GuiForm */
		public gui() {
			initComponents();
		}

		/** This method is called from within the constructor to
		 * initialize the form.
		 * WARNING: Do NOT modify this code. The content of this method is
		 * always regenerated by the Form Editor.
		 */
		private void initComponents() {

			new javax.swing.ButtonGroup();
			jLabel1 = new javax.swing.JLabel();
			logType = new javax.swing.JComboBox();
			jLabel2 = new javax.swing.JLabel();
			bowType = new javax.swing.JComboBox();
			jLabel3 = new javax.swing.JLabel();
			amountToFletch = new javax.swing.JTextField();
			jLabel4 = new javax.swing.JLabel();
			startButton = new javax.swing.JButton();
			jLabel5 = new javax.swing.JLabel();
			ssCheck = new javax.swing.JCheckBox();
			jLabel8 = new javax.swing.JLabel();
			knifetype = new javax.swing.JComboBox();
			locationBox = new javax.swing.JComboBox();
			jLabel7 = new javax.swing.JLabel();

			setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
			setTitle("UniversalFletcher");

			jLabel1.setText("Log Type:");

			logType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Oak", "Willow", "Maple", "Yew", "Magic" }));
			logType.setSelectedIndex(3);
			logType.setFocusable(false);
			logType.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					logTypeActionPerformed(evt);
				}
			});

			jLabel2.setText("Bow Type:");

			bowType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Short Bow", "Long Bow" }));
			bowType.setSelectedIndex(1);
			bowType.setFocusable(false);
			bowType.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					bowTypeActionPerformed(evt);
				}
			});

			jLabel3.setText("Amount to Fletch:");

			amountToFletch.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
			amountToFletch.setText("0");
			amountToFletch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					amountToFletchActionPerformed(evt);
				}
			});

			jLabel4.setText("(0 if non-stop fletching)");

			startButton.setText("Start Script!");
			startButton.setFocusable(false);
			startButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					startButtonActionPerformed(evt);
				}
			});

			jLabel5.setFont(new java.awt.Font("Arial", 0, 30)); // NOI18N
			jLabel5.setText("By Fletch To 99");
			jLabel5.setFocusable(false);

			ssCheck.setSelected(false);
			ssCheck.setText("Screenshot when done?");
			ssCheck.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
			ssCheck.setFocusable(false);
			ssCheck.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			ssCheck.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
			ssCheck.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					ssCheckActionPerformed(evt);
				}
			});
			jLabel8.setText("Knife:");

			knifetype.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Sacred clay" }));
			knifetype.setFocusable(false);
			knifetype.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					knifetypeActionPerformed(evt);
				}
			});

			locationBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Yes", "No" }));
			locationBox.setSelectedIndex(1);
			locationBox.setFocusable(false);
			locationBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					locationBoxActionPerformed(evt);
				}
			});

			jLabel7.setText("Chat Responder:");
			jLabel7.setFocusable(false);

			javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
			getContentPane().setLayout(layout);
			layout.setHorizontalGroup(
					layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addGroup(layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
									.addGroup(layout.createSequentialGroup()
											.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
													.addGroup(layout.createSequentialGroup()
															.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																	.addGroup(layout.createSequentialGroup()
																			.addComponent(jLabel3)
																			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																			.addComponent(amountToFletch, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
																			.addGroup(layout.createSequentialGroup()
																					.addComponent(jLabel2)
																					.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																					.addComponent(bowType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
																					.addGroup(layout.createSequentialGroup()
																							.addComponent(jLabel8)
																							.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																							.addComponent(knifetype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
																							.addGroup(layout.createSequentialGroup()
																											.addComponent(jLabel1)
																											.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																											.addComponent(logType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
																											.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																											.addComponent(jLabel4)
																											.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE))
																											.addGroup(layout.createSequentialGroup()
																													.addComponent(jLabel7)
																													.addGap(11, 11, 11)
																													.addComponent(locationBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																													.addGap(38, 38, 38)
																													.addComponent(ssCheck)
																													.addGap(58, 58, 58)))
																													.addComponent(startButton))
																													.addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)))
			);
			layout.setVerticalGroup(
					layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addGroup(layout.createSequentialGroup()
							.addGap(5, 5, 5)
							.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel1)
														.addComponent(logType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																.addComponent(jLabel2)
																.addComponent(bowType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																		.addComponent(jLabel3)
																		.addComponent(amountToFletch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(jLabel4))
																		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
									.addComponent(locationBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
									.addComponent(jLabel7))
									.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
											.addComponent(jLabel8)
											.addComponent(knifetype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
											.addGroup(layout.createSequentialGroup()
												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																							.addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
																							.addComponent(ssCheck))
																							.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
																							.addComponent(jLabel5))
			);

			pack();
		}// </editor-fold>

		private void logTypeActionPerformed(java.awt.event.ActionEvent evt) {                                        
			// TODO add your handling code here:
		}                                       

		private void amountToFletchActionPerformed(java.awt.event.ActionEvent evt) {                                               
			// TODO add your handling code here:
		}                                              

		private void bowTypeActionPerformed(java.awt.event.ActionEvent evt) {                                        
			// TODO add your handling code here:
		}                                       

		private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
			guiStatus = 1;
			gui.setVisible(false);
		}                                           

		private void ssCheckActionPerformed(java.awt.event.ActionEvent evt) {                                        
			// TODO add your handling code here:
		}                                       
		private void knifetypeActionPerformed(java.awt.event.ActionEvent evt) {                                          
			// TODO add your handling code here:
		}                                         

		private void locationBoxActionPerformed(java.awt.event.ActionEvent evt) {
			// TODO add your handling code here:
		}

		/**
		 * @param args the command line arguments
		 */
		public void main(String args[]) {
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					new gui().setVisible(true);
				}
			});
		}

		// Variables declaration - do not modify
		private javax.swing.JTextField amountToFletch;
		private javax.swing.JComboBox bowType;
		private javax.swing.JLabel jLabel1;
		private javax.swing.JLabel jLabel2;
		private javax.swing.JLabel jLabel3;
		private javax.swing.JLabel jLabel4;
		private javax.swing.JLabel jLabel5;
		private javax.swing.JLabel jLabel7;
		private javax.swing.JLabel jLabel8;
		private javax.swing.JComboBox knifetype;
		private javax.swing.JComboBox locationBox;
		private javax.swing.JComboBox logType;
		private javax.swing.JCheckBox ssCheck;
		private javax.swing.JButton startButton;
		// End of variables declaration
	}
}