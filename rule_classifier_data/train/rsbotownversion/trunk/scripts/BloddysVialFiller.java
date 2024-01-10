import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;
import java.io.*;
import java.net.*;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;  

import org.rsbot.bot.Bot;
import org.rsbot.bot.input.Mouse;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.script.GrandExchange;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSInterface;
import org.rsbot.script.wrappers.RSNPC;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSTile;
import org.rsbot.util.ScreenshotUtil;

@ScriptManifest(authors = { "Bloddyharry" }, name = "Bloddy Vial Filler", category = "Money-Making", version = 2.2, description = "<html>\n"
	+ "<body style='font-family: Calibri; color:white; padding: 0px; text-align: center; background-color: black;'>"
	+ "<h2>"
	+ "Bloddy Vial Filler 2.2"
	+ "</h2>\n"
	+ "Made by Bloddyharry"
	+ "<br><br>\n"
	+ "<b>start in the east varrock bank.</b>\n"
	+ "</b></b>\n"
	+ "Amount to make: <input name='AMOUNTID' type='text' width='10' value='4000' />"
	+ "<b>\nCredits go to Gribonn53.</b>")
public class BloddysVialFiller extends Script implements PaintListener, ServerMessageListener {
    final RSObject fountain = getNearestObjectByID(24214);
    final GrandExchange grandExchange = new GrandExchange();
    public String status = "";
    private int AMOUNTID;
    private int amountVials = 0;
    public int emptyVialID = 229;
    public int waterVialID = 227;
    public int animation = 832;
    public int bankBoothID = 11402;
    public int fountainID = 24214;
    public int profit = 0;
    BufferedImage normal = null;
    BufferedImage clicked = null;
    public int vialsFilled = 0;
    public int runEnergy = random(65, 90);
    public int vialCost = grandExchange.loadItemInfo(waterVialID).getMarketPrice();
    public boolean useRest = false;
    int[] tabs = {TAB_ATTACK,TAB_CLAN,TAB_IGNORE,TAB_FRIENDS,TAB_OPTIONS,TAB_QUESTS,TAB_MAGIC,
            TAB_MUSIC,TAB_PRAYER,TAB_EQUIPMENT,INTERFACE_TAB_EMOTES};
    int[] stats = { STAT_ATTACK, STAT_DEFENSE, STAT_STRENGTH,
            STAT_HITPOINTS, STAT_RANGE, STAT_PRAYER, STAT_MAGIC, STAT_COOKING, STAT_WOODCUTTING,
            STAT_FLETCHING, STAT_FISHING, STAT_FIREMAKING, STAT_CRAFTING, STAT_SMITHING,
            STAT_MINING, STAT_HERBLORE, STAT_AGILITY, STAT_THIEVING, STAT_SLAYER, STAT_FARMING,
            STAT_RUNECRAFTING, STAT_HUNTER, STAT_CONSTRUCTION, STAT_SUMMONING};
    public boolean addedVials = false;
    RSTile bankTile = new RSTile(3253, 3420);
    RSTile fountainTile = new RSTile(3240, 3433);
    RSTile [] bankToFountainPath = {new RSTile(3253, 3420), new RSTile (3249, 3429), new RSTile (3240, 3432)};
    RSTile [] fountainToBankPath = reversePath(bankToFountainPath);
    public long startTime = System.currentTimeMillis();
    final RSObject bankBooth = getNearestObjectByID(bankBoothID);
    
    protected int getMouseSpeed() {
        return random(6, 7);
     }
    public boolean onStart(Map<String, String>args){
    	try {
            final URL cursorURL = new URL(
                    "http://i48.tinypic.com/313623n.png");
            final URL cursor80URL = new URL(
                    "http://i46.tinypic.com/9prjnt.png");
            normal = ImageIO.read(cursorURL);
            clicked = ImageIO.read(cursor80URL);
        } catch (MalformedURLException e) {
            log("Unable to buffer cursor.");
        } catch (IOException e) {
            log("Unable to open cursor image.");
        }
        URLConnection url = null;
        BufferedReader in = null;
        BufferedWriter out = null;
        //Ask the user if they'd like to check for an update...
        if(JOptionPane.showConfirmDialog(null, "Would you like to check for updates?\nPlease Note this requires an internet connection and the script will write files to your harddrive!") == 0){ //If they would, continue
            try{
                //Open the version text file
                url = new URL("http://www.bloddyharry.webs.com/scripts2/BloddysVialFillerVERSION.txt").openConnection();
                //Create an input stream for it
                in = new BufferedReader(new InputStreamReader(url.getInputStream()));
                //Check if the current version is outdated
                if(Double.parseDouble(in.readLine()) > getVersion()) {
                    //If it is, check if the user would like to update.
                    if(JOptionPane.showConfirmDialog(null, "Update found. Do you want to update?") == 0){
                        //If so, allow the user to choose the file to be updated.
                           JOptionPane.showMessageDialog(null, "Please choose 'BloddysVialFiller.java' in your scripts folder and hit 'Open'");
                           JFileChooser fc = new JFileChooser();
                           //Make sure "Open" was clicked.
                           if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                               //If so, set up the URL for the .java file and set up the IO.
                               url = new URL("http://www.bloddyharry.webs.com/scripts2/BloddysVialFiller.java").openConnection();
                            in = new BufferedReader(new InputStreamReader(url.getInputStream()));
                            out = new BufferedWriter(new FileWriter(fc.getSelectedFile().getPath()));
                            String inp;
                            /* Until we reach the end of the file, write the next line in the file
                             * and add a new line. Then flush the buffer to ensure we lose
                             * no data in the process.
                             */
                            while((inp = in.readLine()) != null){
                                out.write(inp);
                                out.newLine();
                                out.flush();
                            }
                            //Notify the user that the script has been updated, and a recompile and reload is needed.
                               log("Script successfully downloaded. Please recompile and reload your scripts!");
                            return false;
                           } else log("Update canceled");
                    } else log("Update canceled");
                } else
                    JOptionPane.showMessageDialog(null, "You have the latest version. :)"); //User has the latest version. Tell them!
                if(in != null)
                    in.close();
                if(out != null)
                    out.close();
            } catch (IOException e){
                log("Problem getting version :/");
                return false; //Return false if there was a problem
            }
        }  
        AMOUNTID = Integer.parseInt(args.get("AMOUNTID"));
    	startTime = System.currentTimeMillis();
		return true;}
    
    public void onFinish() {
    	ScreenshotUtil.takeScreenshot(true);
		log("Thank you for using Bloddy Vial Filler!");
	}
    public void logOut(){
    		moveMouse(754, 10, 10, 10);
    		clickMouse(true);
    		moveMouse(642, 378, 20, 15);
    		clickMouse(true);
    		wait(random(2000, 3000));
    		stopScript();
    	}
    
    @Override
    public int loop() {
        setCameraAltitude(true);
        if(getMyPlayer().getAnimation() != -1) {
			wait(random(1000, 2500));
    		antiBan();
    	}
        if(vialsFilled >= AMOUNTID){
        	ScreenshotUtil.takeScreenshot(true);
        	log("We filled the amount of vials you filled in!");
        	log("Logging out!");
        	logOut();
        }
        if (getInventoryCount(waterVialID) == 28){
            if (addedVials == false){
            	vialsFilled += 28;
                addedVials = true;
            }
        if (!atBank()){
                status = "Walking to Bank";
                if (getEnergy() == random(60,100)){
                    setRun(true);
                }
                if (distanceTo(getDestination()) < random(5, 12) || distanceTo(getDestination()) > 40) {
                    if (!walkPathMM(fountainToBankPath)) {
                        walkToClosestTile(randomizePath(fountainToBankPath, 2, 2));
                        return random(250, 500);
                    }
                }
            }
            else if (atBank()){
            	Resting();
                bank();
            }
        }
        if (getInventoryCount(emptyVialID) == 28){
            if (addedVials == true){
                addedVials = false;
            }
            if (!atFountain()){
                status = "Walking to Fountain";
                if (getEnergy() >= 65){
                    setRun(true);
                }
                if (distanceTo(getDestination()) < random(5, 12) || distanceTo(getDestination()) > 40) {
                    if (!walkPathMM(bankToFountainPath)) {
                        walkToClosestTile(randomizePath(bankToFountainPath, 2, 2));
                        return random(250, 300);
                    }
                }
            }
            else if (atFountain()){
                fill();
                antiBan();
                
            }
        }
        if (getInventoryCount(waterVialID) == 0 && getInventoryCount(emptyVialID) == 0){
                if(atBank()){
                    bank();
                }
                else if(!atBank()){
                    status = "Walking to Bank";
                    if (getEnergy() == random(65,100)){
                        setRun(true);
                    }
                    if (distanceTo(getDestination()) < random(5, 12) || distanceTo(getDestination()) > 40) {
                        if (!walkPathMM(fountainToBankPath)) {
                            walkToClosestTile(randomizePath(fountainToBankPath, 2, 2));
                            return random(250, 300);
                        }
                    }
                }
        return random(750, 1000);}{{
        }}
		return 100;
        
    }
        public boolean Resting() {
    if (restCheck() && atBank()) {
		moveMouse(726, 110, random(10, 10));
		clickMouse(false);
		moveMouse(674, 154, random(10, 10));
		clickMouse(true);
		wait(random(2300, 3000));
		while (getMyPlayer().getAnimation() != -1) {
			status = "Resting";
			wait(random(1000, 2500));
			antiBan();
			if (onEnergyCheck() > 94) {
				break;
			}
		}}
	return true;}
    
       
    
    //Methods
    public boolean atInventoryItemUse(int vialID) {
		if (getCurrentTab() != TAB_INVENTORY
		&& !RSInterface.getInterface(INTERFACE_BANK).isValid()
		&& !RSInterface.getInterface(INTERFACE_STORE).isValid()) {
		     openTab(TAB_INVENTORY);
		}
		int[] items = getInventoryArray();
		java.util.List<Integer> possible = new ArrayList<Integer>();
		for (int i = 0; i < items.length; i++) {
		if (items[i] == vialID) {
		possible.add(i);
		}
		}
		if (possible.size() == 0) return false;
		int idx = possible.get(random(0, possible.size()));
		Point t = getInventoryItemPoint(idx);
		clickMouse(t, 5, 5, true);
		 return true;
		}
    public void antiBan(){
    	status = " AntiBan";
    	int randomNumber = random(1, 13);
        if (randomNumber <= 13) {
            if (randomNumber == 1) {
            	openRandomTab();
                wait(random(100, 500));
                moveMouse(631, 254, random(60, 200));
                wait(random(2200, 2700));
            }
            if (randomNumber == 2) {
                moveMouse(random(50, 700), random(50, 450), 2, 2);
                wait(random(200, 700));
                moveMouse(random(50, 700), random(50, 450), 2, 2);
            }
            if (randomNumber == 3) {
            	wait(random(100, 200));
            	setCameraRotation(random(1,360));
            	wait(random(100, 200));
            	moveMouse(random(50, 700), random(50, 450), 2, 2);
    			}  	
    			if (randomNumber == 4) {
                	wait(random(100, 200));
                	moveMouse(random(50, 700), random(50, 450), 2, 2);
                	wait(random(500, 1200));
                	setCameraRotation(random(1,360));
                	wait(random(200, 300));
                	moveMouse(random(50, 700), random(50, 450), 2, 2);
        			}         	
            				if (randomNumber == 6) {
            	                setCameraRotation(random(1,360));
            	            }
            				if (randomNumber == 7) {
            	                moveMouse(random(50, 700), random(50, 450), 2, 2);
            				}
            	                if (randomNumber == 8) {
                                	wait(random(100, 200));
                        				moveMouse(631, 278);
                        				wait(random(1000, 2000));
                        				moveMouse(random(50, 700), random(50, 450), 2, 2);
                        				wait(random(200, 500));
                        				if (randomNumber == 9) {
                                        	wait(random(100, 200));
                                				moveMouse(random(50, 700), random(50, 450), 2, 2);
                                				wait(random(500, 1000));
                                				if (randomNumber == 10) {
                                	                moveMouse(random(50, 700), random(50, 450), 2, 2);
                                				}
                                				if (randomNumber == 11) {
                                					setCameraRotation(random(1,360));
                                	                moveMouse(random(50, 700), random(50, 450), 2, 2);
                                				}
                                				if (randomNumber == 12) {
                                	                moveMouse(random(50, 700), random(50, 450), 2, 2);
                                				}
                                				if (randomNumber == 13) {
                                	                moveMouse(random(50, 700), random(50, 450), 2, 2);
                                	                wait(random(100, 400));
                                	                setCameraRotation(random(1,360));
                                				}
                                
            			}}}}
    
    private void openRandomTab() {
    	int randomNumber = random(1, 7);
        if (randomNumber <= 8) {
            if (randomNumber == 1) {
                openTab(TAB_STATS);
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
            					openTab(TAB_NOTES);
            				}
            				if (randomNumber == 8) {
            					openTab(TAB_OPTIONS);
            				}
        }
    }

    
    public boolean atBank(){
        return distanceTo(bankTile) <=5;
    }
    public boolean atFountain(){
        return distanceTo(fountainTile) <=5;
    }
    public boolean atFountainRest() {
		final RSObject fountain = getNearestObjectByID(fountainID);
		return fountain != null && distanceTo(fountain) <= 7;
	}
    
    private int onEnergyCheck() {
		return Integer
		.parseInt(RSInterface.getChildInterface(750, 5).getText());
	}
    private boolean energyCheck() {
		try {
			if (onEnergyCheck() >= runEnergy && !isRunning()) {
				runEnergy = random(65, 90);
				return true;
			} else {
				return false;
			}
		} catch (final Exception e) {
			return false;
		}
	}
    
    private boolean restCheck() {
		return onEnergyCheck() < 15;
	}
    public int bank(){
        status = "Banking";
        final RSObject bankBooth = getNearestObjectByID(bankBoothID);
        if(bank.isOpen()){
            bank.depositAll();
            wait(random(800,1000));
            bank.atItem(emptyVialID, "Withdraw-All");
            wait(random(1000,1300));
        }
        if(!(bank.isOpen())){
            if(bankBooth != null){
            atObject(bankBooth, "Use-Quickly");
            wait(random(200, 300));
            }
            if(bankBooth == null){
                return random(100, 200);
            }
        }
        return random(150, 350);
    }
    public boolean fill(){
    	RSObject obj = getNearestObjectByID(fountainID);
    	status = "filling";
    	if(obj != null && getMyPlayer().getAnimation() == -1){
		if(atInventoryItemUse(emptyVialID));
		if(doSomethingObj(fountainID, "Use"));
		wait(random(1000,2000));
		setCameraRotation(random(1,360));
		log("filling vials..");
		wait(random(1000, 2000));
		moveMouse(random(50, 700), random(50, 450), 2, 2);
		antiBan();
            return true;}
		return true;
    }
           
    //Methods
    public boolean doSomethingObj(int fountainID, String action) {
		RSObject obj = getNearestObjectByID(fountainID);
		if(obj == null) return false;
		
		if(obj != null && getMyPlayer().getAnimation() == -1){
			int random = random(1, 20);
			if(random > 16) antiBan();
			atObject(obj, action);
			return true;
		}
		return false;
	}

    @Override
    public void onRepaint(Graphics g) {
        profit = (vialCost * vialsFilled);
        long millis = System.currentTimeMillis() - startTime;
        long hours = millis / (1000 * 60 * 60);
        millis -= hours * (1000 * 60 * 60);
        long minutes = millis / (1000 * 60);
        millis -= minutes * (1000 * 60);
        long seconds = millis / 1000;
        if(isLoggedIn()){
        g.setColor(new Color(0, 0, 0, 175));
        g.fillRoundRect(312, 4, 203, 100, 0, 0);
        g.setColor(Color.cyan);
        g.draw3DRect(312, 4, 203, 100, true);
        g.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
        g.drawString("Bloddy Vial Filler 2.1", 317, 23);
        g.setColor(Color.white);
        g.drawString("Time running: " + hours + ":" + minutes + ":" + seconds, 317, 41);
        g.setColor(Color.cyan);
        g.drawString("Vials Filled: " + vialsFilled, 317, 59);
        g.setColor(Color.white);
        g.drawString("Profit: " + profit + "GP", 317, 77);
        g.setColor(Color.cyan);
        g.drawString("Status: " + status, 317, 95);
        }
        if(hours == 2 && minutes == 0 && seconds == 0){
        	log("w00t! ran for 2 hours! taking screenie :)");
        	ScreenshotUtil.takeScreenshot(true);
        }
        if(hours == 3 && minutes == 0 && seconds == 0){
        	log("awesome! ran for 3 hours! taking screenie :)");
        	ScreenshotUtil.takeScreenshot(true);
        }
        if(hours == 4 && minutes == 0 && seconds == 0){
        	log("Epic! ran for 4 hours! taking screenie :)");
        	ScreenshotUtil.takeScreenshot(true);
        }
        if(hours == 5 && minutes == 0 && seconds == 0){
        	log("Hell yeaH! ran for 5 hours! taking screenie :)");
        	ScreenshotUtil.takeScreenshot(true);
        }
        if(hours == 6 && minutes == 0 && seconds == 0){
        	log("keep it up! ran for 6 hours! taking screenie :)");
        	ScreenshotUtil.takeScreenshot(true);
        }
        if(hours == 7 && minutes == 0 && seconds == 0){
        	log("NICE NICE! ran for 7 hours! taking screenie :)");
        	ScreenshotUtil.takeScreenshot(true);
        }
        if(hours == 8 && minutes == 0 && seconds == 0){
        	log("SICK! ran for 8 hours! taking screenie :)");
        	ScreenshotUtil.takeScreenshot(true);
        }
        if(hours == 9 && minutes == 0 && seconds == 0){
        	log("DA PERFECT PROGGY! ran for 9 hours! taking screenie :)");
        	ScreenshotUtil.takeScreenshot(true);
        }
        if(hours == 10 && minutes == 0 && seconds == 0){
        	log("FUCKING AWESOME DUDE! ran for 10 hours! taking screenie :)");
        	ScreenshotUtil.takeScreenshot(true);
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
                g.drawImage(normal, mouse_x - 8, mouse_y - 8, null); //this show the mouse when its not clicked
            }
            if (mpt < 1000) {
                g.drawImage(clicked, mouse_x2 - 8, mouse_y2 - 8, null); //this show the four squares where you clicked.
                g.drawImage(normal, mouse_x - 8, mouse_y - 8, null); //this show the mouse as normal when its/just clicked
            }
        }
    }

    public double getVersion(){
    return 2.2;
    }
    @Override
    public void serverMessageRecieved(final ServerMessageEvent e) {
        final String serverString = e.getMessage();
        }
        
    }