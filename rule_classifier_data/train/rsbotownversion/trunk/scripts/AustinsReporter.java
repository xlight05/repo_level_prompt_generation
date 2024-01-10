import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Map;

import org.rsbot.bot.Bot;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.accessors.*;



@ScriptManifest(authors = { "Coder Austin" }, category = "Other", name = "Auto Reporter", version = 1.0, description = "Just run with a bad account")
public class AustinsReporter extends Script implements PaintListener, ServerMessageListener {
	int reportedPlayers = 0;
	long startTime = 0; 
	long runTime = 0, seconds = 0 ,minutes = 0, hours = 0;  //simple crap for time
	String lastReported = "";
	int randomX[] = { 99, 251};
	int randomY[] = { 244 };

	public boolean reportPlayer(String s) {
		lastReported = s;
		moveMouse(460, 491);
		clickMouse(true); //Click on report Abuse
		wait(2000);
		sendText(s, false); //Type in player
		moveMouse(398, 132);
		wait(1000);
		clickMouse(true);
		moveMouse(randomOffense());
		wait(1000);
		clickMouse(true); //Report abuse
		moveMouse(258, 183);
		wait(1000);
		clickMouse(true);
		return true;
	}
	public Point randomOffense() {
	return new Point(randomX[random(0, randomX.length - 1)], randomY[random(0, randomY.length - 1)]);
		
	}
	@Override
	public boolean onStart(Map<String, String> map) {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int loop() {
		org.rsbot.script.wrappers.RSPlayer newb = getNearestPlayer();
		if(newb == null) {
			log("Wtf noob go in a populated area");
			return 1000;
		}
		if(newb.getName().equals(getMyPlayer().getName())) return 1000;
		reportPlayer(newb.getName());
		log("Reported: " + reportedPlayers);
		return 60000;
	}
	
	  /**
	   * Lolz
	 * @return
	 */
	public org.rsbot.script.wrappers.RSPlayer getNearestPlayer() {
	        int Dist = 20;
	        org.rsbot.script.wrappers.RSPlayer closest = null;
	        final int[] validPlayers = Bot.getClient().getRSPlayerIndexArray();
	        RSPlayer[] players = Bot.getClient().getRSPlayerArray();

	        for (final int element : validPlayers) {
	            if (players[element] == null) {
	                continue;
	            }
	            final org.rsbot.script.wrappers.RSPlayer player = new org.rsbot.script.wrappers.RSPlayer(players[element]);
	            if(player == null || player.getName().equals(getMyPlayer().getName())) continue;
	            try {
	                final int distance = distanceTo(player);
	                if (distance < Dist) {
	                    Dist = distance;
	                    closest = player;
	                }
	            } catch (final Exception e) {
	                // e.printStackTrace(); TODO: Why?
	            }
	        }
	        return closest;
	    }
	public void onRepaint(final Graphics g) { 
	    if (g == null || !isLoggedIn()){ 
	        return; 
	    } 
	     
	    final Color BG = new Color(0, 0, 0, 75); 
	    final Color RED = new Color(255, 0, 0, 255); 
	    final Color GREEN = new Color(0, 255, 0, 255); 
	    final Color BLACK = new Color(0, 0, 0, 255); 
	     
	   
		if (startTime == 0) { 
	        startTime = System.currentTimeMillis(); 
	    } 
	     
	   

	    runTime = System.currentTimeMillis() - startTime; 
	    seconds = runTime / 1000; 
	    if (seconds >= 60) { 
	        minutes = seconds / 60; 
	        seconds -= minutes * 60; 
	    } 
	    if (minutes >= 60) { 
	        hours = minutes / 60; 
	        minutes -= hours * 60; 
	    } 
	     
	    
	     
	   
	    g.setFont(new Font("Tahoma", Font.PLAIN, 10)); 
	    g.setColor(BG); 
	    g.fill3DRect(335, 20, 175, 150, true); 
	    g.setColor(RED); 
	    g.drawString("Coder-Austin.com", 340+1, 35+1); 
	    g.setColor(Color.white); 
	    g.drawString("Running for: " + hours + ":" + minutes + ":" + seconds, 340, 50); 
	    g.drawString("People reported: " + reportedPlayers, 340, 70); 
	    if(!lastReported.equalsIgnoreCase(""))
	    g.drawString("Last Reported: " + lastReported, 340, 90); 

	    
	    } 
	public void serverMessageRecieved(final ServerMessageEvent e) {
		final String message = e.getMessage();
		if (message.contains("Thank-you,")) {
			reportedPlayers++; // add one to report
		}
		
		
		
	}

}