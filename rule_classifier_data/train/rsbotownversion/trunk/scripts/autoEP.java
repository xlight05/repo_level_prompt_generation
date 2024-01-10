/* autoEP by Myr
 * Do not reproduce this script in any way.
 *
 * Enjoy gaining EP!
 *
 * REVISIONS:
 * v1.0: released script
 * v1.1: Added disable teleport option
 * v1.2: Made delay between anti-logout longer
 */

import java.awt.*; 
import java.util.*; 

import org.rsbot.script.*; 
import org.rsbot.bot.*; 
import org.rsbot.script.wrappers.*; 
import org.rsbot.event.listeners.*; 
import org.rsbot.event.events.*;
import org.rsbot.event.listeners.PaintListener;

@ScriptManifest(authors = { "Myr" }, category = "PvP", name = "autoEP", version = 1.2, description = 
"<body bgcolor = Black><font color = White><center><h2>autoEP V1.2</h2><h3>by Myr</h3></center><br><center>Hit OK to start gaining EP<br>Run this script without anything equipped.<br>Teleport from danger?: <select name=\"teleport\"><option>Yes<option>No</select></center>")
public class autoEP extends Script implements PaintListener {        
	
    ///////////////////////////////////
    ////////////INTEGERS///////////////
    ///////////////////////////////////
	
	//Items
	public int tab = 8008; 
	
	//Everything else
	public int antiban;
	public boolean runTeleport;
    public int defenceAnimation = 424; 
	public int deathAnimation = 836;
	public long startTime = System.currentTimeMillis();

	private String status = "";
	
	
	///////////////////////////////////
	/////////////PAINT/////////////////
	///////////////////////////////////
	
	public void onRepaint(Graphics g) {
        if (isLoggedIn()) {		
			
			long millis = System.currentTimeMillis() - startTime;
            long hours = millis / (1000 * 60 * 60);
            millis -= hours * (1000 * 60 * 60);
            long minutes = millis / (1000 * 60);
            millis -= minutes * (1000 * 60);
            long seconds = millis / 1000;
            long minutes2 = minutes + (hours * 60);

			g.setColor(Color.yellow);
			g.drawString("autoEP" , 8, 45);
			g.drawString("Status: " + status , 8, 61);
			g.drawString("Time running: " + hours + ":" + minutes + ":" + seconds , 8, 77);		
        }
    }	
	
	
	///////////////////////////////////
	//////onStart and onFinish/////////
	///////////////////////////////////
 			@SuppressWarnings("deprecation")
	public boolean onStart(Map<String, String> args) {
		
	Bot.getEventManager().addListener(PaintListener.class, this);
		log("********************");
		log("***Started autoEP***");
		log("********************");
		
		if(args.get("teleport").equals("Yes"))
			runTeleport = true;
		else runTeleport = false;
		
		return true; 
    } 
    
	
    public void onFinish(){ 
	Bot.getEventManager().removeListener(PaintListener.class, this);			
		log("********************");
		log("***Stopped autoEP***");
		log("********************");
	    return; 
	}
	
	///////////////////////////////////
	/////////////METHODS//////////////
	///////////////////////////////////

	private void antiBan() {
		int randomNum = random(1, 30);
		int r = random(1, 35);
		if (randomNum == 6) {
			if (r == 1) {
				if (getCurrentTab() != TAB_INVENTORY) {
					openTab(TAB_INVENTORY);
				}
			}
			if (r == 2) {
				openTab(random(1, 14));
			}
			if (r == 3) {
				int x = input.getX();
				int y = input.getY();
				moveMouse(x + random(-90, 90), y + random(-90, 90));
			}
			if (r == 4) {
				int x2 = input.getX();
				int y2 = input.getY();
				moveMouse(x2 + random(-90, 90), y2 + random(-90, 90));
			}
			if (r == 5) {
				int x3 = input.getX();
				int y3 = input.getY();
				moveMouse(x3 + random(-80, 80), y3 + random(-80, 80));
			}
			if (r == 6) {
				int x3 = input.getX();
				int y3 = input.getY();
				moveMouse(x3 + random(-100, 100), y3 + random(-100, 100));
			}
			if (r == 7) {
				int x3 = input.getX();
				int y3 = input.getY();
				moveMouse(x3 + random(-100, 100), y3 + random(-80, 80));
			}
			if (r == 8) {
				setCameraRotation(random(100, 360));
			}
			if (r == 9) {
				setCameraRotation(random(100, 360));
			}
			if (r == 10) {
				setCameraRotation(random(100, 360));
			}
		}
	}
		
	///////////////////////////////////
	////////////////LOOP///////////////
	///////////////////////////////////
 
	public int loop() { 
			
		antiBan();
		if (getEnergy() == random(31, 56) || getEnergy() > 56) {
			setRun(true);
		}
		
		if (getMyPlayer().isMoving()) { 
		    status = "Walking";
		} else {
			antiBan();
			status = "Gaining EP";
		}
		
		if (runTeleport) {
		if (getMyPlayer().getAnimation() == defenceAnimation) {
			status = "Teleporting";
			atInventoryItem(tab, "Break");
			return 100;
		}
		
		if (getMyPlayer().getAnimation() == deathAnimation) {
			status = "Dead!";
			atInventoryItem(tab, "Break");
			return 100;
		}
		}
		return 5;
}
}