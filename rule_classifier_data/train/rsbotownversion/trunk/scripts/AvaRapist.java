import java.awt.Color;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Map;
import javax.swing.JOptionPane;
import org.rsbot.accessors.RSCharacter;
import org.rsbot.bot.Bot;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSInterface;
import org.rsbot.script.wrappers.RSInterfaceChild;
import org.rsbot.script.wrappers.RSItem;
import org.rsbot.script.wrappers.RSNPC;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSTile;
import org.rsbot.script.Constants;
import org.rsbot.util.ScreenshotUtil;

@ScriptManifest(authors = { "Creative" }, category = "Other", name = "Ava's Accumulator Buyer/Banker", version = 1.01, description = "<html><body bgcolor = White><font color=Black><center><"
	+ "<h2>" + "Humidify Rapist" + " V 1.01</h2>"
	+ "Author: " + "Creative"
	+ "<br><br>Start at Ava with Cash,Steel Arrows, and a Charged Glory.")

	public class AvaRapist extends Script implements PaintListener {
	
	public int startXP;
	public long startTime = 0;
	public long startXp; 
	public int ava = 5199;
	public int attractor = 10498;
	public int accumulator = 10499;
	public int coins = 995;
	public int arrows = 886;
	public int glory4 = 1712;
	public int glory3 = 1710;
	public int glory2 = 1708;
	public int glory1 = 1706;
	public int glory0 = 1704;
	public int BankID = 2213;
	public int RockID = 336;
	public int DoorID = 47663;
	public int Door2 = 47508;
	private long waitTimer;
	public String status = "";
	String day, hour, minute, second;
	
	public RSTile PathToBank[] = { new RSTile(3104,3250), new RSTile(3099,3250), new RSTile(3095,3248), new RSTile(3092,3245)};
	public RSTile PathToHouse[] = {new RSTile(3092,3245), new RSTile(3098,3252), new RSTile(3103,3260), new RSTile(3103,3268), new RSTile(3105,3278), new RSTile(3109,3286), new RSTile(3108,3294), new RSTile(3112,3301), new RSTile(3111,3309), new RSTile(3110,3315), new RSTile(3113,3322), new RSTile(3111,3326), new RSTile(3109,3333), new RSTile(3109,3340), new RSTile(3110,3350)};
	public RSTile PathToDoor[] = { new RSTile(3108,3354), new RSTile(3107,3357), new RSTile(3105,3359)};
	public RSTile PathTolever[] = {new RSTile(3105,3360), new RSTile(3103,3357), new RSTile(3097,3357)};
	
	public boolean atAva(){
		RSNPC Ava = getNearestNPCByID(ava);
	    if(Ava == null) return false;
	    return tileOnScreen(Ava.getLocation());
	}
	
	public boolean atBank() {
		RSObject Banker = getNearestObjectByID(BankID);
	    if(Banker == null) return false;
	    return tileOnScreen(Banker.getLocation());
	 }
	
	public boolean atRock(){
		RSObject thing = getNearestObjectByID(RockID);
	    if(thing == null) return false;
	    return tileOnScreen(thing.getLocation());
		
	}
	
	public boolean atDoor(){
		RSObject open = getNearestObjectByID(DoorID);
	    if(open == null) return false;
	    return tileOnScreen(open.getLocation());
		
	}
	
	public boolean atDoor2(){
		RSObject open = getNearestObjectByID(Door2);
	    if(open == null) return false;
	    return tileOnScreen(open.getLocation());
		
	}
		
	public String[] getFormattedTime(final long timeMillis) {
		long millis = timeMillis;
		final long days = millis / (24 * 1000 * 60 * 60);
		millis -= days * (24 * 1000 * 60 * 60);
		final long hours = millis / (1000 * 60 * 60);
		millis -= hours * 1000 * 60 * 60;
		final long minutes = millis / (1000 * 60);
		millis -= minutes * 1000 * 60;
		final long seconds = millis / 1000;
		String dayString = String.valueOf(days);
		String hoursString = String.valueOf(hours);
		String minutesString = String.valueOf(minutes);
		String secondsString = String.valueOf(seconds);
		if (hours < 10)
			hoursString = 0 + hoursString;
		if (minutes < 10)
			minutesString = 0 + minutesString;
		if (seconds < 10)
			secondsString = 0 + secondsString;
		return new String[] { dayString, hoursString, minutesString,
				secondsString };
		
	}
	
	public boolean onStart(Map<String, String>args) {
		setCompass('w');
		setCameraAltitude(true);
		startXp = skills.getCurrentSkillExp(STAT_MAGIC);
		startTime = System.currentTimeMillis();
		waitTimer = System.currentTimeMillis();
		return true;
	}
 
	 protected int getMouseSpeed() {
	   return 9; 
	 	}
	 
		
		public void onRepaint(Graphics g) {
			  	long millis = System.currentTimeMillis() - startTime;
					  	
			  	day = getFormattedTime(millis)[0];
				hour = getFormattedTime(millis)[1];
				minute = getFormattedTime(millis)[2];
				second = getFormattedTime(millis)[3];
		        
				long hours = millis / (1000 * 60 * 60);
		        millis -= hours * (1000 * 60 * 60);
		        long minutes = millis / (1000 * 60);
		        millis -= minutes * (1000 * 60);
		        long seconds = millis / 1000;
		        if(isLoggedIn()){
		        g.setColor(new Color(0, 0, 0, 175));
		        g.fillRoundRect(312, 4, 203, 64, 0, 0);
		        g.setColor(Color.cyan);
		        g.draw3DRect(312, 4, 203, 64, true);
		        g.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		        g.drawString("Ava Rapist 1.01", 317, 23);
		        g.setColor(Color.white);
		        g.drawString("Time running: " + hours + ":" + minutes + ":" + seconds, 317, 41);
		        g.setColor(Color.cyan);
		        g.drawString("Status: " + status, 317, 59);
		        }
			}

	 public int loop() {
			 final RSInterfaceChild talkage = RSInterface.getChildInterface(244, 8);
							
			setCameraAltitude(true);
			if(atAva() && (!isInventoryFull())){
				status = "Get Attractors...";
					RSNPC Ava = getNearestNPCByID(ava);
		           if(Ava.isOnScreen()){
		           atNPC(Ava, "Talk");
		           wait(1500);
		           if(talkage.isValid()){
		           clickContinue();
		           wait(1500);
		           moveMouse((int) random(159, 350), (int) random(404, 408));
		           clickMouse(true);
		           wait(1500);
		           clickContinue();
		           wait(1500);
		           clickContinue();
		           wait(1500);
		           clickContinue();
		           wait(1500);
		           clickContinue();
		           wait(1500);
		           clickContinue();
		           wait(1500);
		           moveMouse((int) random(201, 314), (int) random(399, 406));
		           clickMouse(true);
		           wait(1500);
		           clickContinue();
		           wait(1500);
		           clickContinue();
		           wait(1500);
		           clickContinue();
		           		}
		           }
			}
			
			if(atAva() && (isInventoryFull())){
					status = "Get Accumulators...";
				   RSNPC Ava = getNearestNPCByID(ava);
		           if(Ava.isOnScreen()){
		           atNPC(Ava, "Talk");
		           wait(1500);
		           if(talkage.isValid()){
		           clickContinue();
		           wait(1500);
		           moveMouse((int) random(141, 362), (int) random(418, 423));
		           clickMouse(true);
		           wait(1500);
		           clickContinue();
		           wait(1500);
		           clickContinue();
		           wait(1500);
		           moveMouse((int) random(201, 315), (int) random(401, 405));
		           clickMouse(true);
		           wait(1500);
		           clickContinue();
		           }
		        }
			}
			
			if(getInventoryCount(accumulator) >= 25 && (inventoryContains(glory4) && (atAva()))){
				status = "Teleporting...";
				atInventoryItem(glory4, "Rub" );
				wait(2000);
				moveMouse((int) random(207, 307), (int) random(412, 419));
				clickMouse(true);
				wait(1500);
			}
			
			if(getInventoryCount(accumulator) >= 25 && (inventoryContains(glory3) && (atAva()))){
				status = "Teleporting...";
				atInventoryItem(glory3, "Rub" );
				wait(2000);
				moveMouse((int) random(207, 307), (int) random(412, 419));
				clickMouse(true);
				wait(1500);
			}
			
			if(getInventoryCount(accumulator) >= 25 && (inventoryContains(glory2) && (atAva()))){
				status = "Teleporting...";
				atInventoryItem(glory2, "Rub" );
				wait(2000);
				moveMouse((int) random(207, 307), (int) random(412, 419));
				clickMouse(true);
				wait(1500);
			}
			
			if(getInventoryCount(accumulator) >= 25 && (inventoryContains(glory1) && (atAva()))){
				status = "Teleporting...";
				atInventoryItem(glory1, "Rub" );
				wait(2000);
				moveMouse((int) random(207, 307), (int) random(412, 419));
				clickMouse(true);
				wait(1500);
			}
						
			if(atBank() && (inventoryContains(glory0))){
				final RSObject Banker = getNearestObjectByID(BankID);
				status = "Banking...";
				if(!(bank.isOpen())){
		        if(Banker != null){
		        atObject(Banker, "Use-Quickly");
		        wait(random(1200, 1600));
		        status = "Despositing...";
		     	bank.deposit(glory0, 1);
		     	wait(1500);
		     	bank.deposit(accumulator, 0);
		     	wait(1500);
		       	bank.withdraw(glory4, 1);
		       	wait(1500);
		        		}
					}
				}
			
			if(atBank() && (inventoryContains(accumulator))){
				final RSObject Banker = getNearestObjectByID(BankID);
				status = "Banking...";
				if(!(bank.isOpen())){
		        if(Banker != null){
		        atObject(Banker, "Use-Quickly");
		        wait(random(1200, 1600));
		        status = "Despositing...";
		     	bank.deposit(accumulator, 0);
		        		}
					}
				}
			if(!atAva() && (inventoryContains(accumulator))){
				status = "To Bank...";
				walkPathMM(PathToBank);
			}
			
			if(!atAva() && (!inventoryContains(accumulator))){
				status = "To House...";
				walkPathMM(PathToHouse);
			}
			
			atDoorTiles(new RSTile(3444,3457),new RSTile(3444,3458));
			
			if(atDoor()){
				setCompass('n');
				setCameraAltitude(0);
				status = "Opening Door...";
				final RSObject door = getNearestObjectByID(DoorID);
				atObject(door, "Open");
				wait(2000);
				status = "To Door...";
				walkPathMM(PathToDoor);
				wait(4000);
			}
			
			
			if(atDoor2()){
				setCompass('w');
				setCameraAltitude(true);
				status = "Opening Door...";
				final RSObject door = getNearestObjectByID(Door2);
				atObject(door, "Open");
				wait(1000);
				status = "To Lever...";
				walkPathMM(PathTolever);
				wait(6000);
				if(isIdle()){
				setCompass('n');
				setCameraAltitude(0);
				status = "Clicking...";
				moveMouse((int) random(238, 264), (int) random(41, 45));
				clickMouse(true);
				wait(3500);
				}
					
			}
			    
			return random(300,400);
		}
		 
 
	public void onFinish() {
		ScreenshotUtil.takeScreenshot(true);
		log("Thanks for using my script - Creative.");
		log("Ran for " + day + ":" + hour + ":" + minute + ":" + second+ "" );
			}
  }