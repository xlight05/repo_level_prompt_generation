import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;  
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.rsbot.script.Calculations;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.script.wrappers.RSNPC;
import org.rsbot.script.wrappers.RSTile;






    @ScriptManifest(authors = { "Shadow Storm" }, category = "Thieving", name = "Thieves Guild Pickpocketer 2.2", version = 2.2, description = 
       "<html><body style=\"font-family: Arial; padding: 7px;\">"
       + "<center><strong>Thieve's Guild Pickpocketer 2.0</strong>"
       + "<br></br>"
       + "<br></br>"
       + "<input type='checkbox' name='using' value='true'> Using gloves of silence"
       + "<br></br>"
       + "<br>If using gloves of silence, please have enough replacements in bag.</br>"
       + "<br></br>"
       + "<br>If you find ANY bug, report it on forums.</br>"
       + "<br></br>"
       + "<br>Enjoy thieving!"
       + "<br></br>"
       + "<br></br></center>"
    )
    public class ThievesGuildPP extends Script implements PaintListener, ServerMessageListener {

        public int[] trainers = {11281, 11283, 11287, 11285};
        public int gloves = 10075;
        public long startTime = System.currentTimeMillis();
        public int timesPickpocketed, glovesUsed, gloveCount;
        public int ppcketPerHour;
        public int startLevel = skills.getCurrentSkillLevel(STAT_THIEVING);
        public int startExp = skills.getCurrentSkillExp(STAT_THIEVING);
        public int gainedLevels;
        public int expGained;
        public int expPerHour;
        public int failures, check, lastPCount, lastExp, lastPicks;
        public int failurePerHour;
        public long seconds, minutes, hours, trigerTime, lastPick;
        public boolean needGloves = false, usingGloves = false;
        public String scriptRunner = "Shadow Storm";
        
        public boolean onStart(Map<String, String> args) {
            setCameraAltitude(true);
            getMouseSpeed();
            log("Starting up");
            URLConnection url = null;
            BufferedReader in = null;
            BufferedWriter out = null;
            if(JOptionPane.showConfirmDialog(null, "Would you like to check for updates? Please Note this requires an internet connection and the script will write files to your hard-drive!") == 0){ 
                try{
                    url = new URL("http://www.m-scripting.webs.com/ThievesGuildPPVERSION.txt").openConnection();
                    in = new BufferedReader(new InputStreamReader(url.getInputStream()));
                    if(Double.parseDouble(in.readLine()) > getClass().getAnnotation(ScriptManifest.class).version()) {
                        if(JOptionPane.showConfirmDialog(null, "Update found. Do you want to update?") == 0) {
                               JOptionPane.showMessageDialog(null, "Please choose 'ThievesGuildPP.java' in your scripts folder and hit 'Open'");
                               JFileChooser fc = new JFileChooser();
                               if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                                   url = new URL("http://www.m-scripting.webs.com/scripts/ThievesGuildPP.java").openConnection();
                                in = new BufferedReader(new InputStreamReader(url.getInputStream()));
                                out = new BufferedWriter(new FileWriter(fc.getSelectedFile().getPath()));
                                String inp;
                                while((inp = in.readLine()) != null){
                                    out.write(inp);
                                    out.newLine();
                                    out.flush();
                                }
                                log("Script successfully downloaded. Please recompile and reload your scripts!");
                                return false;
                               } else log("Update canceled. Why?");
                        } else log("Update canceled. Why?");
                    } else
                        JOptionPane.showMessageDialog(null, "You have the latest version. Good work!"); 
                    if(in != null)
                        in.close();
                    if(out != null)
                        out.close();
                } catch (IOException e){
                    log("Problem getting version. Please retry or tell me, Shadow Storm!");
                    return false; 
                }
            }  
            gloveCount = getInventoryCount(gloves);
            scriptRunner = args.get("userToStats");
            usingGloves = args.get("using") != null ? true : false;
            log("Enjoy");
            return true;
        }
        
        public void updateStats(int runntime, int done, int profitgain, int xpgain) {
        	try {
				new URL(
						"http://waterwolf.freehostia.com/rdstats/submit.php?sid=13"
								+ "&runner=" + scriptRunner
								+ "&time=" + runntime
								+ "&done=" + done
								+ "&profit="+ profitgain
								+ "&xp=" + xpgain)
						.openStream();
			} catch (final MalformedURLException e1) {
			} catch (final IOException e1) {
			}
		}
	

        @Override
        public int getMouseSpeed() {
            return random(5, 6);
        }

        public int loop() {
        	if (System.currentTimeMillis() - lastPick >= 6000) {
            	failsafe();
            } else {
                pickPocket();
                performStun();
                useGloves();
            }
        	return random(400, 500);
        }

        public void pickPocket() {
        	RSNPC theNPC = getNearestNPCByID(trainers);
            if (theNPC != null) {
                if (distanceTo(theNPC.getLocation()) <= 6) {
                    atNPC(theNPC, "Pickpocket Pick");
                    wait(random(500, 600));
                    while (getMyPlayer().isMoving()) {
                             wait(random(400, 450));
                    }
                    } else if (distanceTo(theNPC.getLocation()) >= 7) {
                    walkTileMM(theNPC.getLocation());
                    while(getMyPlayer().isMoving()) {
                        wait(random(300, 350));
                    }
                }
            } else {
                log("Not at trainers. Stopping");
                stopScript();
            }
        }

        public boolean performStun() {
             while (System.currentTimeMillis() - trigerTime < 4500) {
                antiBan();
             }
             return false;
        }

        public boolean useGloves() {
            if (usingGloves = true && needGloves != false && inventoryContains(gloves)) {
           			atInventoryItem(gloves, "Wear");
           			glovesUsed ++;
           		} else {
           			return false;
           	}
           	needGloves = false;
           	return false;
        }

        public boolean failsafe() {
        	RSNPC theNPC = getNearestNPCByID(trainers);
        	if (distanceTo(theNPC.getLocation()) >= 7) {
                walkTileMM(theNPC.getLocation());
                while(getMyPlayer().isMoving()) {
                      wait(random(300, 350));
                }
        	} else {
        	    pickPocket();
        	}
        	return false;
        }
        
        public void onFinish() {
        	if (lastExp == 0) {
        		lastExp = skills.getCurrentSkillExp(STAT_THIEVING);
        	}
        	if (lastPicks == 0) {
        		lastPicks = timesPickpocketed;
        	}
        	updateStats(Math.round(System.currentTimeMillis() - startTime), lastPicks, 0, (lastExp - startExp));
        	log(" [-------------- Finished ---------------]");
            log(" [ Experience gained = " + expGained);
            log(" [ Levels gained = " + gainedLevels);
            log(" [ Time ran = " + hours + ":" + minutes + ":" + seconds);
            log(" [ Experience p/hour = " + expPerHour);
            log(" [--------- Thanks for using me! --------]");
            
        }
       
        private void drawTile(Graphics render, RSTile tile, Color color, boolean drawCardinalDirections) {
            Point southwest = Calculations.tileToScreen(tile, 0, 0, 0);
            Point southeast = Calculations.tileToScreen(new RSTile(tile.getX() + 1, tile.getY()), 0, 0, 0);
            Point northwest = Calculations.tileToScreen(new RSTile(tile.getX(), tile.getY() + 1), 0, 0, 0);
            Point northeast = Calculations.tileToScreen(new RSTile(tile.getX() + 1, tile.getY() + 1), 0, 0, 0);
            
            if (pointOnScreen(southwest) && pointOnScreen(southeast) && pointOnScreen(northwest) && pointOnScreen(northeast)) {
                render.setColor(Color.BLACK);
                render.drawPolygon(new int[] { (int) northwest.getX(), (int) northeast.getX(), (int) southeast.getX(), (int) southwest.getX() }, new int[] { (int) northwest.getY(), (int) northeast.getY(), (int) southeast.getY(), (int) southwest.getY() }, 4);
                render.setColor(color);
                render.fillPolygon(new int[] { (int) northwest.getX(), (int) northeast.getX(), (int) southeast.getX(), (int) southwest.getX() }, new int[] { (int) northwest.getY(), (int) northeast.getY(), (int) southeast.getY(), (int) southwest.getY() }, 4);
                
                if (drawCardinalDirections) {
                    render.setColor(Color.BLACK);
                    render.drawString(".", southwest.x, southwest.y);
                    render.drawString(".", southeast.x, southeast.y);
                    render.drawString(".", northwest.x, northwest.y);
                    render.drawString(".", northeast.x, northeast.y);
                }
            }
        }
        
        public void onRepaint(Graphics render) {
        	RSNPC theNPC = getNearestNPCByID(trainers);
            expGained = skills.getCurrentSkillExp(STAT_THIEVING) - startExp;
            ppcketPerHour = (int) (3600000D / (System.currentTimeMillis() - startTime) * (timesPickpocketed));
            long millis = System.currentTimeMillis() - startTime;
            expPerHour = (int) ((expGained) * 3600000D / (System.currentTimeMillis() - startTime));
            gainedLevels = skills.getCurrentSkillLevel(STAT_THIEVING) - startLevel;
            hours = millis / (1000 * 60 * 60);
            millis -= hours * (1000 * 60 * 60);
            minutes = millis / (1000 * 60);
            millis -= minutes * (1000 * 60);
            seconds = millis / 1000;

            render.setColor(new Color(0, 0, 0, 146));
            render.fillRect(6, 6, 163, 225);
            render.setFont(new Font("Tempus Sans ITC", 0, 13));
            render.setColor(new Color(204, 0, 204));
            render.drawString("Thieves Guild Pickpocketer", 13, 28);
            render.setFont(new Font("Tempus Sans ITC", 0, 12));
            render.drawString("Exp. gained: " + expGained, 13, 54);
            render.drawString("Exp. per hour: " + expPerHour, 13, 76);
            render.drawString("Exp. to level: " + skills.getXPToNextLevel(STAT_THIEVING), 13, 98);
            render.drawString("Levels gained: " + gainedLevels, 13, 120);
            render.drawString("Current level: " + skills.getCurrentSkillLevel(STAT_THIEVING), 13, 144);
            render.drawString("Pickpockets made: " + timesPickpocketed, 13, 164);
            render.drawString("Pickpockets failed: " + failures, 13, 185);
            render.drawString("Gloves used: " + glovesUsed, 13, 204);
            render.drawString("Time running: " + hours + ":" + minutes + ":" + seconds, 13, 225);
            render.drawLine(11, 211, 160, 211);
            render.drawLine(160, 211, 160, 211);
    
            //Mouse
            Point m = getMouseLocation();
            render.setFont(new Font("Tempus Sans ITC", 0, 13));
            render.setColor(new Color(204, 0, 204));
            render.drawString("TGPP", m.x + 2, m.y + 12);
            render.drawRect(m.x, m.y, 33, 13);
            render.drawOval(m.x, m.y, 3, 2);
            render.fillOval(m.x - 2, m.y - 2, 5, 5);
	
	        //Tile
	        if (theNPC != null) {
	            if (tileOnScreen(theNPC.getLocation())) {
			        drawTile(render, theNPC.getLocation(), new Color(204, 0, 204, 190), true);
		        }
	        }
        }

        public void serverMessageRecieved(final ServerMessageEvent e) {
            final String message = e.getMessage();
            if (message.contains("You retrieve")) {
                timesPickpocketed ++;
                lastPick = System.currentTimeMillis();
            }
            if (message.contains("fail")) {
                failures ++;
                check ++;
                trigerTime = System.currentTimeMillis();
            }
            if (message.contains("worn out")) {
            	needGloves = true;
            }
        }
        public boolean antiBan() {
            int randomNumber = random(1, 4);
            if (randomNumber <= 15) {
                if (randomNumber == 1) {
                    openTab(TAB_STATS);
                    moveMouse(random(620, 665), random(300, 320));
                    wait(random(2000, 2100));
                }
                if (randomNumber == 2) {
                    moveMouse(random(50, 700), random(50, 450), 2, 2);
                    wait(random(1200, 1300));
                    moveMouse(random(50, 700), random(50, 450), 2, 2);
                }
                if (randomNumber == 3) {
                    moveMouse(522, 188, 220, 360);
                    wait(random(1000, 1250));
                }
                if (randomNumber == 4) {
                    setCameraRotation(random(1, 360));
                }
                if (randomNumber == 5) {
                    openTab(TAB_STATS);
                    moveMouse(random(619, 665), random(300, 320));
                    wait(random(1000, 1500));
                }
            }
            return true;
        }
    }