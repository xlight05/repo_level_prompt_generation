import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
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
import org.rsbot.bot.input.Mouse;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSNPC;
import org.rsbot.util.ScreenshotUtil;

@ScriptManifest(authors = { "mr_sharingan" }, 
		name = "Sharingan Black Jacker", 
		
		category = "Thieving", version = 1.05, 
		
		description = 
			"")

public class SharinganBlackJacker extends Script implements PaintListener, ServerMessageListener {
	
	RSNPC fatty = getNearestNPCByID(11297);
	public int knocked = 12413;
	private boolean lured = false, knockedO = false;
	final int random2 = random(1, 10);
	int movedcam;
	//Paint
	public int stolen = 0;
	public int knockedOutTimes = 0;
	public long startTime = System.currentTimeMillis();
	private final ScriptManifest properties = getClass().getAnnotation(ScriptManifest.class);
	private String status = "Starting Up Script";
	private int gainedExp = 0;
	private int startexp = 0;
	private int startLvl;
	private int currentLvl;
	private int lvlsGained;
	private int xpToLvl;
	
	protected int getMouseSpeed() {
		return random(6, 9);
	}
	  public boolean onStart(Map<String, String>args){
		  
		  	startLvl = skills.getCurrentSkillLevel(STAT_THIEVING);
		    gainedExp = skills.getCurrentSkillExp(STAT_MINING);
		        startTime = System.currentTimeMillis();
		        log("Let's go whack that fat guy");
		        
		     
		        URLConnection url = null;
		        BufferedReader in = null;
		        BufferedWriter out = null;
		        // Ask the user if they'd like to check for an update...
		        if (JOptionPane.showConfirmDialog(null, "Would you like to check for updates?\n\nNOTE: This requires an internet connection and the script will write files to your hard drive!") == 0) { //If they would, continue
		            try {
		                // Open the version text file
		                url = new URL("http://sharingan-scripting.webs.com/scripts/SharinganBlackJackerVERSION.txt").openConnection();
		                // Create an input stream for it
		                in = new BufferedReader(new InputStreamReader(url.getInputStream()));
		                // Check if the current version is outdated
		                if (Double.parseDouble(in.readLine()) > properties.version()) {
		                    // If it is, check if the user would like to update.
		                    if (JOptionPane.showConfirmDialog(null, "Update found. Do you want to update?") == 0) {
		                        // If so, allow the user to choose the file to be updated.
		                        JOptionPane.showMessageDialog(null, "Please choose 'SharinganClayBanker.java' in your scripts folder and click 'Open.'");
		                        JFileChooser fc = new JFileChooser();
		                        // Make sure "Open" was clicked.
		                        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		                            //If so, set up the URL for the .java file and set up the IO.
		                            url = new URL("http://sharingan-scripting.webs.com/scripts/SharinganBlackJacker.java").openConnection();
		                            in = new BufferedReader(new InputStreamReader(url.getInputStream()));
		                            out = new BufferedWriter(new FileWriter(fc.getSelectedFile().getPath()));
		                            String inp;
		                            /* Until we reach the end of the file, write the next line in the file
		                             * and add a new line. Then flush the buffer to ensure we lose
		                             * no data in the process.
		                             */
		                            while ((inp = in.readLine()) != null) {
		                                out.write(inp);
		                                out.newLine();
		                                out.flush();
		                            }
		                            // Notify the user that the script has been updated, and a recompile and reload is needed.
		                            log("Script successfully downloaded! Please recompile and reload your scripts.");
		                            return false;
		                        } else log("Update canceled.");
		                    } else log("Update canceled.");
		                } else
		                    JOptionPane.showMessageDialog(null, "You have the latest version. :)"); //User has the latest version. Tell them!
		                if (in != null)
		                    in.close();
		                if (out != null)
		                    out.close();
		            } catch (IOException e) {
		                log("Problem getting version. :/");
		                return false; // Return false if there was a problem
		            }
		        }
		        return true;
	  }

	public int loop() {
		getMouseSpeed();
		if(fatty != null){
			lure();
			wait(random(50, 100));
		}
		knockedOut();
		knockOut();
		wait(random(50, 100));
		loot();
		return 0;
	}
	public void loot() {
		if(fatty != null){
			if (fatty.getAnimation() == 12413) {
				if(knockedO = true) {
				atNPC(fatty, "Loot");
				status = "Looting";
				wait(random(1, 50));
			}
			}
		}
		}
	
	public void knockedOut() {
		if (fatty.getAnimation() == 12413) {
			knockedO = true;
	} else{
		knockedO = false;
	}
		if ((getInterface(64).getChild(4).containsText("I need to divert his attention first."))) {
			clickContinue();
			wait(250 + random(250, 500));
			knockedO = false;
			lured = false;
	}
		if (fatty.getAnimation() == 12414) {
			lured = false;
			knockedO = false;
		}
	}
	
	public void lure() {
	if(fatty != null){
		if(lured == false){
			if (fatty.getAnimation() != 12413) {
				status = "Luring";
				if ((!getInterface(64).getChild(5).containsText("Click here to continue"))) {	
			atNPC(fatty, "Lure");
			wait(random(1000, 1250));
				}
			if (random2 == 1) {
				antiBan2();
			}
			while ((getInterface(64).getChild(5).containsText("Click here to continue"))) {
				clickContinue();
				wait(250 + random(250, 500));
		}
			while ((getInterface(241).getChild(4).containsText("Wha'?"))) {
				clickContinue();
				wait(250 + random(250, 500));
				lured = true;
		}
				
		}
	}
	}
	}
	public void knockOut() {
		if(fatty != null) {
			if(lured == true) {
				if (fatty.getAnimation() != 12413) {
					status = "Attempting to Knock-Out";
				atNPC(fatty, "Knock-out");
				wait(random(1000, 1250));
				if ((getInterface(64).getChild(4).containsText("I need to divert his attention first."))) {
					lured = false;
			}
			}
			}
		}
	}

	  public void serverMessageRecieved(ServerMessageEvent e) {
	    String msg = e.getMessage();
	    if(msg.contains("He seems preoccupied.")){
	    	lured = true;
	    } else {
	    	lured = false;
	    }
	    if(msg.contains("Your blow only glances off his head.")){
	    	lured = true;
	    }
	    if(msg.contains("You should knock him out before attempting to loot him.")){
	    	lured = false;
	    }
	    if(msg.contains("You empty the man's pocket and carefully drape the red handkerchief over him.")) stolen++;
	    if(msg.contains("You smack the trainer over the head as poilitely as you can.")) knockedOutTimes++;

	  }
	  
		  public void antiBan2() {
			    int rnd = random(0, 30);
			    switch(rnd) {
			    case 0:
			      wait(random(50, 150));
			      moveMouseSlightly();
			      break;
			    case 1:
			      wait(random(100, 200));
			      wait(random(10,20));
			      if(random(1, 30) !=1) break;
			      int angle =  getCameraAngle();
			      setCameraRotation(angle + random(30, -30));
			  	case 3:
			  			int px = random(20,50);
			  			int nx = random(-20,-50);
			  			if(movedcam == 0){
			  				setCameraRotation(px);
			  				movedcam++;
			  			}else{
			  				setCameraRotation(nx);
			  				movedcam--;
			  			}
			  			random(500,800);
			  			}
			  		
			  		random(500,800);
			  	}
	  @Override
	  public void onRepaint(Graphics g) {
			    if(isLoggedIn()) {
			      long millis = System.currentTimeMillis() - startTime;
			            long hours = millis / (1000 * 60 * 60);
			            millis -= hours * (1000 * 60 * 60);
			            long minutes = millis / (1000 * 60);
			            millis -= minutes * (1000 * 60);
			            long seconds = millis / 1000;
			            long runTime = System.currentTimeMillis() - startTime;
			            int stolenPerHour = 0;
			            int xpPerHour = 0;
			            if (runTime / 1000 > 0) {
			                stolenPerHour = (int) (3600000.0 / runTime * stolen);
			                xpPerHour = (int) (3600000.0 / runTime * gainedExp);
			                			            }

			            if ( startexp == 0) {
			                startexp = skills.getCurrentSkillExp(STAT_THIEVING);
			           }
		Mouse m = Bot.getClient().getMouse();
	    gainedExp = skills.getCurrentSkillExp(STAT_THIEVING) - startexp;
	    currentLvl = skills.getCurrentSkillLevel(STAT_THIEVING);
	    xpToLvl = skills.getXPToNextLevel(STAT_THIEVING);
	    lvlsGained = currentLvl - startLvl;
	    g.setColor(new Color(0, 0, 255, 70));
	    //Main
	    g.fillRect(7, 40, 100 ,40);    
	    //Skills
	    g.fillRect(7, 95, 100, 40);    
	    //Cash

	    //text
	    g.setColor(Color.white);
	    g.drawString("Main", 43, 65);
	    g.drawString("Stats", 43, 119);
	    
	    //text within interactive boxes
	    if (m.x >= 7 && m.x < 7 + 100 && m.y >= 40 && m.y < 40 + 40) {
	    	g.setColor(new Color(255, 0, 0, 70));
	        g.fillRoundRect(120, 40, 172, 150, 10, 10);
	        g.setColor(Color.white);
	        g.setFont(new Font("sansserif", Font.BOLD, 12));
	        g.drawString("SharinganBlackJacker" + Double.toString(properties.version()), 130, 60);
	        g.setFont(new Font("Tahoma", Font.PLAIN, 10));
	        g.drawString("Mr_Sharingan", 170, 70);
	        g.drawString("Run time: " + hours + ":" + minutes + ":" + seconds, 160, 90);
	        g.drawString("Status: " + status, 160, 115);
	    } else if (m.x >= 7 && m.x < 7 + 100 && m.y >= 95 && m.y < 95 + 40) {
	    	g.setColor(new Color(0, 255, 0, 70));
	        g.fillRoundRect(120, 40, 172, 150, 10, 10);
	        g.setColor(Color.white);
	        g.setFont(new Font("sansserif", Font.BOLD, 12));
	        g.drawString("SharinganBlackJacker" + Double.toString(properties.version()), 130, 60);
	        g.setFont(new Font("Tahoma", Font.PLAIN, 10));
	        g.drawString("Mr_Sharingan", 170, 70);
	        g.drawString("Thieving Experience gained: " + gainedExp, 145, 90); 
	        g.drawString("Time Stolen: " + stolen, 170, 105);
	        g.drawString("Stolen Per Hour: " + stolenPerHour, 160, 120);
	        g.drawString("EXP per hour: " + xpPerHour, 155, 135);
	        g.drawString("Thieving Level: " + currentLvl + "("+lvlsGained+")", 155, 150);
	        g.drawString("EXP to Level " + xpToLvl , 155, 165);
			

	  
	  }
			    }	 

	 }

}