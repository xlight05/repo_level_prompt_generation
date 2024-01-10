import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.Map;

import org.rsbot.bot.Bot;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.script.Constants;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.Skills;
import org.rsbot.script.wrappers.RSInterface;
import org.rsbot.script.wrappers.RSNPC;
import org.rsbot.script.wrappers.RSTile;
 
@ScriptManifest(authors = { "Crikey" }, category = "Hunter", name = "iFalconry", version = 1.0, description = ("<html><head>"+
       "</head><body>"
        + "<center><strong><h2>" + "iFalconry" + " v" + 1.0 + "</h2></strong></center>"
       + "<br><br>" 
       + "Script configuration<br>"
       + "Type of Kebbit:<br> <select name=\"TYPE\"><option selected>Spotted Kebbit (43)<option>Dark Kebbit (57)<option>Dashing Kebbit (69)</select> <br><br>"
       + "Would you like to bury the bones? <br><select name=\"DROPBONES\"><option selected>Yes<option>No</select> <br><br>"
       + "Enable antiban: <br><select name =\"ANTIBANCHECK\"><option selected>Yes<option>No</select>"
       + "</center>"
        + "</body></html>"))

public class iFalconry extends Script implements PaintListener, ServerMessageListener {
 
	final RSTile TO_MATTHIAS[] =  { new RSTile(2373, 3602)};
	final RSTile TO_KEBBITS[] = { new RSTile(2375, 3590) };
	final RSTile TO_KEBBITSA[] = { new RSTile(2375,3591) };
	public boolean FullInv = false;
	public int KEBBITS;
	public static int SPOTTEDKEBBIT = 5098;
	public static int DARKKEBBIT = 5099;
	public static int DASHINGKEBBIT = 5100;
	public int FURS; 
	public static int SPOTTEDFUR = 10125; 
	public static int DARKFUR = 10115; 
	public static int DASHINGFUR = 10127; 
	public int BONES; 
	public static int KEBBITBONES = 526;
	int DROPBONES;
	public int FALCONS; 
	public static int FALCONSPOTTED = 5094; 
	public static int FALCONDARK = 5096; 
	public static int FALCONDASHING = 5095; 
	public int logcount;
	final int S_CATCHKEBBITS = 1000;
	final int S_RETRIEVEFALCONS = 9000;
	final int S_DROPITEMS = 3000;
	final int COLLECTFALCON = 2000;
	int scriptState = S_CATCHKEBBITS; 
	int GambleInt;
	int TYPE;
	boolean GOTFALCON = false;
	int startLvl;
	int lvlNow;
	long gainedExp = 0;
	int startExp;
	int oldExp;
	int caught;
	long hours = 0, minutes = 0, seconds = 0, time, startXP, oldXP, xpGained;
	long timeGained = 0;
	int paint = 0;
	long startTime;
	int xpPerCatch = 0;
	int currentFails = 0;
	public int antiBanOn = 1;
	boolean GettingFalcon;
	boolean Walked;
	boolean Talked;
	public int levelsgained = 0;
	public int status = 1;
	public boolean hasFalcon;
	
	final ScriptManifest properties = getClass().getAnnotation(ScriptManifest.class);
	

	public boolean onStart(Map<String, String> args) {
		Bot.getEventManager().registerListener(this);
		logcount = 0;
		if (args.get("DROPBONES").equals("Yes")) {
			DROPBONES = 0;
		} else if (args.get("DROPBONES").equals("No")) {
			DROPBONES = 1;
		}
		if (args.get ("ANTIBANCHECK").equals("Yes")){
			antiBanOn = 1;
		}
		else if (args.get("ANTIBANCHECK").equals("No")) {
			antiBanOn = 0;
		}
		if (args.get("TYPE").equals("Spotted Kebbit (43)")) {
			KEBBITS = SPOTTEDKEBBIT;
			FURS = SPOTTEDFUR;
			FALCONS = FALCONSPOTTED;
			BONES = KEBBITBONES;
			System.out.println("Kebbit Catcher started, catching Spottted Kebbits.");
			return true;
		} else if (args.get("TYPE").equals("Dark Kebbit (57)")) {
			KEBBITS = DARKKEBBIT;
			FURS = DARKFUR;
			FALCONS = FALCONDARK;
			BONES = KEBBITBONES;
			System.out.println("Kebbit Catcher started, catching Dark Kebbits.");
			return true;
		} else if (args.get("TYPE").equals("Dashing Kebbit (69)")) {
			KEBBITS = DASHINGKEBBIT;
			FURS = DASHINGFUR; 
			FALCONS = FALCONDASHING;
			BONES = KEBBITBONES;
			System.out.println("Kebbit Catcher started, catching Dashing Kebbits.");
			return true;
		}
		System.out.println("Choose a type of Kebbit first!");
		return false;
	} 
 
	public void onFinish() {
		Bot.getEventManager().removeListener(PaintListener.class, this);
		Bot.getEventManager().removeListener(ServerMessageListener.class, this);
	}
	
	@SuppressWarnings({ "deprecation", "null" })
	public int loop() {
		if (getInventoryCount() >= 27) {
			scriptState = S_DROPITEMS;
		}
		if (currentFails >= 25) {
			log("Error code: #3. Please report this to Lambda ASAP.");
			if(checkForLogout())
				stopAllScripts();
		}
		if (getEnergy() >= random(30, 100) && !isRunning()) {
			setRun(true);
		}
		RSNPC Gyr_Falcon = getNearestNPCByID(FALCONS);
		if (Gyr_Falcon != null) {
			turnToTile(Gyr_Falcon.getLocation());
			walkTo(Gyr_Falcon.getLocation());
			status = 2;
			atNPC(Gyr_Falcon, "Retrieve");
		}
		switch (scriptState) {
		case S_CATCHKEBBITS:
			RSNPC kebbit = getNearestNPCByID(KEBBITS); 
			//RSNPC Gyr_Falcon = getNearestNPCByID(FALCONS);
			if (kebbit != null && Gyr_Falcon == null) {
				turnToTile(kebbit.getLocation());
				atNPC(kebbit, "Catch");
				scriptState = S_RETRIEVEFALCONS;
				return(random(100, 200)); 
			} else {
			return(random(100, 200)); 
			}
		case COLLECTFALCON:
			if (GettingFalcon) {
				walkPathMM(TO_MATTHIAS, 20);
				Walked = true;
				log("Walking to get falcon.");
				} else {
				}
			if (Walked && GettingFalcon && !Talked) {
				RSNPC Matthias = getNearestNPCByName("Matthias");
				turnToTile(Matthias.getLocation());
				atNPC(Matthias, "Quick-falconry");
				Talked = true;
				log("Talking to Matthias for falcon.");
			} else {
				scriptState = COLLECTFALCON;
				Talked = false;
				log("Failed previous time to talk.");
			}
			if(Walked && GettingFalcon && Talked && RSInterface.getInterface(228).isValid()) {
				atInterface(228,2);
				GOTFALCON = true;
				log("Clicking you do want a falcon.");
				return random(200,700);
			} else {
			}
			if(Walked && GettingFalcon && Talked && RSInterface.getInterface(230).isValid()) {
				atInterface(230,4);
				GOTFALCON = true;
				log("Clicking you do want a falcon.");
				return random(50,100);
			} else {
			}
			if (Walked && GettingFalcon && Talked && GOTFALCON) {
				scriptState = S_DROPITEMS;
				walkPathMM(TO_KEBBITS, 20);
				Walked = false;
				GettingFalcon = false;
				Talked = false;
				GOTFALCON = false;
				currentFails = 0;
				log("Got falcon, going back to hunting kebbits.");
			} else {
				scriptState = COLLECTFALCON;
				log("Failed to get falcon, Trying again.");
				currentFails += 5;
				Walked = false;
				GettingFalcon = true;
				Talked = false;
				GOTFALCON = false;
			}
			return(random(100, 200));
		case S_RETRIEVEFALCONS:
			//RSNPC Gyr_Falcon = getNearestNPCByID(FALCONS);
			
			try {
				boolean isff = Gyr_Falcon.isOnScreen();
			while (isff = false){
				turnToTile(Gyr_Falcon.getLocation());
			} 
				 
			 }catch (Exception Ex){
				 if (Gyr_Falcon != null) {
				turnToTile(Gyr_Falcon.getLocation());
				walkTo(Gyr_Falcon.getLocation());
				atNPC(Gyr_Falcon, "Retrieve");
				currentFails = 0;
				
			
				scriptState = S_CATCHKEBBITS;
				return(random(200, 1000));
			}
			}
		
				
			
		case S_DROPITEMS:
			if (getInventoryCount(BONES) == 0 && getInventoryCount(FURS) == 0) {
				scriptState = S_CATCHKEBBITS;
				return(random(200, 1000));
			}
			try {
				dropItems();
			} catch (IOException e) {
				
				e.printStackTrace();
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			currentFails = 0;
			return(random(100, 1000));
		}
		return(random(100, 1000));
	}

	public static int pausegame(boolean wait,int low, int high) throws IOException, InterruptedException{
	if (wait == false){
		double rnd = Math.random();
		double sumf = low+high;
		sumf = sumf/2.5;
		rnd = rnd * 3;
		sumf = (int) (sumf * rnd);
		if (sumf > high){
			while (sumf > high){
			sumf = sumf/1.15;
			
			}
			
		Thread.sleep((long) sumf);
		}
		else if (sumf < low) {
			while (sumf < low){
			sumf = sumf * 1.15;
			}
			
		Thread.sleep((long) sumf);
		}
		else {
			
		Thread.sleep((long) sumf);
		}
		
	}
	else if (wait == true){
		double rnd = Math.random();
	    rnd = rnd * 500;
	    double kbg = 200;
	    kbg = kbg + rnd;
	    
	    
	    Thread.sleep((long) kbg);
	}
	else {
		System.out.println("Error");
	}
	return 1;
}
 public int antiBan() throws IOException, InterruptedException {
    	double rndnmbr;
    	rndnmbr = Math.random();
		
		rndnmbr = rndnmbr *1000;
		rndnmbr = (int)rndnmbr;
		
		//Start of the loops
		
		if (rndnmbr > 700 && rndnmbr < 730){
			 //wc icon
			status = 4;
			input.dragMouse(566, 188);
			input.clickMouse(true);
			try {
				pausegame(false,600,900);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			input.dragMouse(707,366);
			try {
				pausegame(false,600,900);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			input.dragMouse(656, 192);
			input.clickMouse(true);
			
		}
		else if (rndnmbr > 990 && rndnmbr <994){
			status = 5;
			pausegame(false,30000,50000);
		}
		else if (rndnmbr > 830 && rndnmbr < 860){
			status = 4;
			input.dragMouse(540,485);
			input.clickMouse(true);
			try {
				pausegame(false,500,1000);
			} catch (IOException e) {
				
				e.printStackTrace();
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			input.dragMouse(645,396);
			
			try {
				pausegame(false,500,1100);
			} catch (IOException e) {
				
				e.printStackTrace();
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			input.dragMouse(656, 192);
			input.clickMouse(true);
			
			
			
		}
				
				
		else {
			try {
				pausegame(true,0,0);
			} catch (IOException e) {
				
				e.printStackTrace();
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
		status = 1;
		return 1;
		// end of the loops
		
	}
   public void dropItems() throws IOException, InterruptedException {
	   status =3;
		if (getCurrentTab() != Constants.TAB_INVENTORY) {
			openTab(Constants.TAB_INVENTORY);
		} else {
			if (getInventoryCount(FURS) > 0) {
				if (atInventoryItem(FURS, "Drop")) {
					if (antiBanOn == 1){
					antiBan();
					}
					else {
						pausegame(false,400,600);
					}
				}
			}
			if (DROPBONES == 1 && getInventoryCount(BONES) > 0) {
				if (atInventoryItem(BONES, "Drop")) {
					if (antiBanOn == 1){
					antiBan();
					}
					else {
						pausegame(false,400,600);
					}
					
				}
			} else if (DROPBONES == 0 && getInventoryCount(BONES) > 0) {
				if (atInventoryItem(BONES, "Bury")) {
					if (antiBanOn == 1){
					antiBan();
					}
					else {
						pausegame(false,400,600);
					}
				}
			}
		}
   }
 
	public boolean checkForLogout() {
		for(int failed = 0; failed < 3; failed++) {
			logout();
			wait(500);
			if(!isLoggedIn()) {
				return true;
			}
		}
		return false;
	}


	
	@SuppressWarnings("unused")
	public void onRepaint(Graphics g) {
		int exp = 0;
		RSNPC npc = getNearestNPCByID(FALCONS);
				int id, x, y;
				if (npc != null) {

					RSTile t = new RSTile(npc.getLocation().getX() + 1, npc.getLocation().getY());

					x = (int) (npc.getLocation().getScreenLocation().getX() - 4);
					y = (int) (npc.getLocation().getScreenLocation().getY() - 10);

						g.setColor(new Color(25, 255, 25, 175));
						g.fillRoundRect(x, y, 20, 20, 3, 3);
				}
		if (startTime == 0) {
			startTime = System.currentTimeMillis();
		}
		time = System.currentTimeMillis() - startTime;
		seconds = time / 1000;
		if (seconds >= 60) {
			minutes = seconds / 60;
			seconds -= minutes * 60;
		}
		if (minutes >= 60) {
			hours = minutes / 60;
			minutes -= hours * 60;
		}
		int index = Skills.getStatIndex("Hunter");
		if (startExp == 0) {
			startExp = skills.getCurrentSkillExp(index);
			
			oldExp = 0;
		}
		if (startXP == 0) {
			startXP = skills.getCurrentSkillExp(index);
		}
		xpGained = skills.getCurrentSkillExp(index) - startXP;
		if (xpGained > oldXP) {
			oldXP = xpGained;
		}
		exp = skills.getCurrentSkillExp(index) - startExp;
		if (exp > oldExp) {
			xpPerCatch = exp - oldExp;
			oldExp = exp;
			caught++;
		}
		if (isLoggedIn()) {
			int x1 = 9;
			int y1= 245;
			lvlNow = skills.getCurrentSkillLevel(index);
			if (paint == 0){
				int show = 1;
				if (show == 1){
					Color BG = new Color(0, 0, 0, 125);
			g.setColor(BG);
			g.drawRoundRect(10, 30, 190, 60, 10, 15);
			g.fillRoundRect(10, 30, 190, 60, 10, 15);
			
			g.setColor(Color.GREEN); 
			g.drawString("iFalconry 1.0 " , 30 , 50);
			g.setColor(Color.WHITE);
			switch ( status ) { //YES, i know...I'm using case...
			case 0: g.drawString("Status: Not Running " , 30 , 66); break;
			case 1: g.drawString("Status: Working " , 30 , 66); break;
			case 3: g.drawString("Status: Dropping items " , 30 , 66); break;
			case 4: g.drawString("Status: Running Antiban", 30, 66); break;
			case 5: g.drawString("Status: AFK Mode", 30, 66); break;
			default: g.drawString("Null " , 30 , 66); break;
		 
			
			}
			g.drawString("Run time: " + hours + ":" + minutes + ":" + seconds, 30, 82);
			
		    g.setColor(BG);
			g.drawRoundRect(220, 30, 190, 60, 10, 15);
			g.fillRoundRect(220, 30, 190, 60, 10, 15);
			g.setColor(Color.GREEN);
			g.drawString("Information  ", 240, 50);
			g.setColor(Color.WHITE);
			g.drawString("Current level:" + skills.getCurrentSkillLevel(index),240,66 );
			if (DROPBONES == 0) {
						g.drawString("Set to Bury Bones", 240 , 82);
					} else if (DROPBONES == 1) {
						g.drawString("Set to Drop Bones", 240 , 82);
					}
			//exp progress
			       g.setColor(BG);
			        double var = skills.getPercentToNextLevel(Constants.STAT_HUNTER);
			        var = (int) var * 3.8;
					g.drawRoundRect(22, 105, 380, 11,3,3);
					g.setColor(Color.GREEN);
					g.fillRoundRect(22,105, (int) var, 11, 3,3); 
					g.setColor(Color.WHITE);
					g.drawString(skills.getPercentToNextLevel(Constants.STAT_HUNTER) + "%  to " + (skills.getCurrentSkillLevel(Constants.STAT_HUNTER) + 1) + " ( Gained: " + levelsgained + " level(s) )" , 150,115);

					
				}
			}
		}
	}
 
	public void serverMessageRecieved(ServerMessageEvent e) {
		String message = e.getMessage();
		if (message.contains("Your falcon has left its prey.")) {
			scriptState = COLLECTFALCON;
			GettingFalcon = true;
		}
		if (message.contains("You try to catch the creature but it is too quick for you.")) {
			currentFails += 1;
			if (currentFails == 20) {
				scriptState = COLLECTFALCON;
				GettingFalcon = true;
			}
		}
		
		if (message.contains("your bird")){
			try {
				pausegame(true,500,700);
			} catch (IOException e1) {
				
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
		}
		if (message.contains("advanced a Hunter")){
			levelsgained++;
		}
		
		}
	}