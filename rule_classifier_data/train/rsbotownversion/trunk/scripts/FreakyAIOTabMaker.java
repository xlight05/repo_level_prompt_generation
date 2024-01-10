import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Map;

import org.rsbot.bot.Bot;
import org.rsbot.bot.input.Mouse;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSInterface;
import org.rsbot.script.wrappers.RSNPC;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSTile;

@ScriptManifest(authors = { "FiLThY_FreaK_" }, category = "Magic", name = "FreakyAIOTabMaker", version = 1.8, 
		description = "<tr><td><b>AntiBan: </b></td><td><center><select name='antiBanType'><option>Off<option>Only Camera<option>Only Mouse<option>Camera and Mouse</select></center></td></tr><tr><td><b>Tablet: </b></td><td><center><select name='tabName'><option>B2P Tab<option>Varrock Tele-tab<option>Falador Tele-tab<option>House Tele-tab<option>Camelot Tele-tab<option>Lumbridge Tele-tab<option>Ardougne Tele-tab</select></center></td></tr><tr><td><b>Butler: </b></td><td><center><select name='butlerType'><option>Demon butler<option>Regular butler</select></center></td></tr><tr><td><b>Paint:</b></td><td><center><input type='checkbox' name='usePaint' checked='true' value='true'><B>Yes</b></center></td></tr></table>")

	public class FreakyAIOTabMaker extends Script implements PaintListener, ServerMessageListener {
	
	public int[] BookID = {13642, 13643, 13645, 13647, 13648, 13644, 13646};
	private RSTile outsideTile1 = new RSTile(2544,3096);
	private RSTile outsideTile2 = new RSTile(2953,3224);
	private RSTile outsideTile3 = new RSTile(2757,3178);
	private RSTile outsideTile4 = new RSTile(2893,3465);
	private RSTile outsideTile5 = new RSTile(3340,3003);
	private RSTile outsideTile6 = new RSTile(2670,3631);
	public int BookAnimation = 3652;
	public int antiBanOption = 0;
	public int SoftClayPrice = 0;
	public int CombinedLoss = 0;
	public int Animation = 4068;
	public int PaintSwitch = 0;
	public int SoftClay = 1761;
	public String antiBanType;
	public int Rune2Price = 0;
	public int startLevel = 0;
	public String butlerType;
	public String ButlerName;
	public int BankTrips = 0;
	public int BankPrice = 0;
	public int RunePrice = 0;
	public boolean usePaint;
	public int ItemLoss = 0;
	public int ButlerID = 0;
	public int startTab = 0;
	public int TabPrice = 0;
	public int BankLoss = 0;
	public int BankCalc = 0;
	public int childID = 0;
	public int startXP = 0;
	public String tabName;
	public int Profit = 0;
	public int Rune2 = 0;
	public int Rune = 0;
	long startTime = 0;
	public int Tab = 0;
	public int XP = 0;
	public int f = 0;
	
	public boolean onStart(Map<String, String>args){
		log("Script initialized!");
		butlerType = args.get("butlerType");
		tabName = args.get("tabName");
		antiBanType = args.get("antiBanType");
		usePaint = args.get("usePaint") != null ? true : false;
		
		if (antiBanType.equals("Off")) {
			antiBanOption = 1;
			
		}
		
		if (antiBanType.equals("Only Camera")) {
			antiBanOption = 2;
			
		}
		
		if (antiBanType.equals("Only Mouse")) {
			antiBanOption = 3;
			
		}

		if (antiBanType.equals("Camera and Mouse")) {
			antiBanOption = 4;
			
		}
		
        if (butlerType.equals("Demon butler")) {
            ButlerID = 4243;
            BankPrice = 10000;
            BankCalc = 1250;
            ButlerName = ("Demon butler");
            
        }
        
        if (butlerType.equals("Regular butler")) {
            ButlerID = 4241;
            BankPrice = 5000;
            BankCalc = 625;
            ButlerName = ("Regular butler");
            
        }    
        
        if (tabName.equals("B2P Tab")) {
            Rune = 561;
            Rune2 = 0;
            childID = 4;
            Tab = 8015;
            XP = (int) 35.5;
            
        }
        
        if (tabName.equals("Varrock Tele-tab")) {
           	Rune = 563;
           	Rune2 = 554;
           	childID = 15;
           	Tab = 8007;
           	XP = 35;
           	
        }
        
        if (tabName.equals("Falador Tele-tab")) {
           	Rune = 563;
           	Rune2 = 555;
           	childID = 12;
           	Tab = 8009;
           	XP = 48;
           	
        }
        
        if (tabName.equals("House Tele-tab")) {
           	Rune = 563;
           	Rune2 = 555;
           	childID = 14;
           	Tab = 8013;
           	XP = 30;
           	
        }
        
        if (tabName.equals("Camelot Tele-tab")) {
           	Rune = 563;
           	Rune2 = 0;
           	childID = 5;
           	Tab = 8010;
           	XP = (int) 55.5;
           	
        }
        
        if (tabName.equals("Lumbridge Tele-tab")) {
           	Rune = 563;
           	Rune2 = 557;
           	childID = 13;
           	Tab = 8008;
           	XP = 41;
           	
        }
        
        if (tabName.equals("Ardougne Tele-tab")) {
           	Rune = 563;
           	Rune2 = 0;
           	childID = 2;
           	Tab = 8011;
           	XP = 61;
           	
        }
		
		if (!isLoggedIn()) {
			log("Please start the script while logged in!");
			stopScript();
			
		}	
		
		return true;
		
	}
	
	public void onFinish()	{
		log("Script Ended");
		
	}
	
    private void calculateProfit() {
    	TabPrice = grandExchange.loadItemInfo(Tab).getMarketPrice();
    	
    	if (Tab == 8015 | Tab == 8011) {
    		RunePrice = ((grandExchange.loadItemInfo(Rune).getMarketPrice()) * 2);
    		
    	} else if (Tab != 8015 | Tab != 8011) {
    		RunePrice = grandExchange.loadItemInfo(Rune).getMarketPrice();
    		
    	}
    	
        Rune2Price = grandExchange.loadItemInfo(Rune2).getMarketPrice();
        SoftClayPrice = grandExchange.loadItemInfo(SoftClay).getMarketPrice();
        ItemLoss = (RunePrice + SoftClayPrice + Rune2Price);
        BankLoss = (BankTrips / 8);
        
        if (BankLoss != 0) {
        	CombinedLoss = (ItemLoss + BankLoss);
        	Profit = (TabPrice - CombinedLoss);
        	
        } else if (BankLoss == 0){
        	Profit = (TabPrice - ItemLoss);
        	
        }
    	
    }
    
    public void calculateXP() {
 	   startLevel = skills.getCurrentSkillLevel(STAT_MAGIC);
 	   startXP = skills.getCurrentSkillExp(STAT_MAGIC);
 	   startTime = System.currentTimeMillis();
 	   
 	   if (inventoryContains(Tab)) {
 	   startTab = getInventoryItemByID(Tab).getStackSize();
 	   
 	   } else {
 		   log("Start the script with atleast 1 Tab!");
 		   stopScript();
 		   
 	   }
 	   
    }
    
	
////////////////LOOP START////////////////
	@Override
	public int loop() {
		if (startTab == 0) {
			calculateXP();
			
		}
		
		try {
			
		if (animationIs(BookAnimation)){
			wait(random(450, 650));
			
		}
		
		if (getMyPlayer().getAnimation() == Animation){
			calculateProfit();
			antiBan();
		
		}
		
		switch (getState())	{
		
			case WAITCLAY:
				wait(random(1800, 2000));
				
			break;
			
			case WAIT:
				wait(random(150, 200));
				
			break;
			
			case MAKEB2P:
				try {
				atInterface(400, childID, "Make-All");
				wait(random(650, 850));
				antiBan();
				
				} catch (final Exception e) {
					log("Avoided: Nullpointer at making Tab");
					return(random(20,30));
					
				}
				
			break;
			
			case OPENBOOK:
				try {
				RSObject Book = getNearestObjectByID(BookID);
				atTile(Book.getLocation(), "Study");
				antiBan();
				wait(random(1400, 1600));
				
				} catch (final Exception e) {
					log("Avoided: Nullpointer at opening book");
					return(random(20,30));
					
				}
				
			break;
			
			case SENDBUTLER1:
				try {
				atInterface(234, 2);
				BankTrips++;
				antiBan();
				wait(random(7000, 8000));
				
				} catch (final Exception e) {
					log("Avoided: Nullpointer at making sending butler 2");
					return(random(20,30));
					
				}
				
			break;
			
			case SENDBUTLER2:
				try {
				atInterface(232, 5);
				} catch (final Exception e) {
					
					log("Avoided: Nullpointer at interface 232 5");
					return(random(20,30));
					
				}
				
			break;
				
			case SENDBUTLER3:
				try {
				atInterface(234, 3);
				} catch (final Exception e) {
					
					log("Avoided: Nullpointer at interface 234 3");
					return(random(20,30));
					
				}
				
			break;
				
			case SENDBUTLER4:
				try {
					if (Rune2 == 0 && ButlerID == 4243) {
						sendText("26", true);
						BankTrips++;
						antiBan();
						wait(random(7000, 8000));
						
					} else
						
					if (Rune2 >= 1 && ButlerID == 4243) {
						sendText("25", true);
						BankTrips++;
						antiBan();
						wait(random(7000, 8000));
						
					} else
						
					if (ButlerID == 4241) {
						sendText("20", true);
						BankTrips++;
						antiBan();
						wait(random(7000, 8000));
						
					}
					
				} catch (final Exception e) {
					log("Avoided: Nullpointer at sending butler 1");
					return(random(20,30));
					
				}
				
			break;
			
			case SENDBUTLER5:
				try {
				atInterface(243, 7);
				wait(random(350, 450));
				
				} catch (final Exception e) {
					log("Avoided: Nullpointer at clicking continue 1");
					return(random(20,30));
					
				}
				
			break;
			
			case SENDBUTLER6:
				try {
				atInterface(232, 3);
				
				} catch (final Exception e) {
					log("Avoided: Nullpointer at interface 232 3");
					return(random(20,30));
					
				}
				
			break;
			
			case SENDBUTLER7:
				try {
					if (RSInterface.getInterface(241).isValid()) {
						atInterface(241, 5);
						
					} else
						
					if (RSInterface.getInterface(242).isValid()) {
						atInterface(242, 6);
						
					}
					
				wait(random(350, 450));
				
				} catch (final Exception e) {
					log("Avoided: Nullpointer at clicking continue 2");
					return(random(20,30));
					
				}
				
			break;
			
			case SENDBUTLER8:
				try {
				atInterface(242, 6);
				wait(random(350, 450));
				
				} catch (final Exception e) {
					log("Avoided: Nullpointer at clicking continue 3");
					return(random(20,30));
					
				}
				
			break;
			
			case CLICKBUTLER:
				try {
				RSNPC Butler = getNearestNPCByID(ButlerID);
					if (distanceTo(Butler.getLocation()) <= 1) {
						atNPC(Butler, "Fetch-from-bank");
						wait(random(750, 950));
						
					} else 
						
					antiBan();
					wait(random(750, 950));
					
					} catch (final Exception e) {
					log("Avoided: Nullpointer at clicking butler");
					return(random(20,30));
					
				}
				
			break;
			
			case STOP:
				stopScript();
				
			break;
			
		}	
		
		} catch (final Exception e) {
			
			//log("Avoided: Nullpointer at the end of the loop");
			return(random(20,30));
			
		}
		
		return random(250, 350);
		
	}
	
////////////////LOOP END////////////////
	
	private enum State {
		WAITCLAY, MAKEB2P, OPENBOOK, SENDBUTLER1, SENDBUTLER2, SENDBUTLER3, SENDBUTLER4, 
		SENDBUTLER5, SENDBUTLER6, SENDBUTLER7, SENDBUTLER8, CLICKBUTLER, STOP, WAIT
		
		}
	
	private State getState() {
		
		RSNPC Butler = getNearestNPCByID(ButlerID);
		
		if (getInventoryItemByID(Rune).getStackSize() <=2) {
			log("Out of runes!");
			return State.STOP;
			
		} else if (distanceTo(outsideTile1) <=10 | distanceTo(outsideTile2) <= 10 | distanceTo(outsideTile3) <=10 | distanceTo(outsideTile4)
			<=10 | distanceTo(outsideTile5) <=10 | distanceTo(outsideTile6) <=10 ) {
			log("Something messed up. Probably a random event.");
			return State.STOP;
			
		} else if (inventoryContains(SoftClay) && RSInterface.getInterface(400).isValid()) {
			return State.MAKEB2P;
			
		} else if (ButlerID == 4243 && Rune2 == 0 && getInventoryCount(SoftClay) == 26 && !RSInterface.getInterface(400).isValid()) {
			return State.OPENBOOK;
			
		} else if (ButlerID == 4243 && Rune2 >= 1 && getInventoryCount(SoftClay) == 25 && !RSInterface.getInterface(400).isValid()) {
			return State.OPENBOOK;
			
		} else if (ButlerID == 4241 && getInventoryCount(SoftClay) >= 20 && !RSInterface.getInterface(400).isValid()) {
			return State.OPENBOOK;
			
		} else if (!inventoryContains(SoftClay) && distanceTo(Butler.getLocation()) <= 2 && !RSInterface.getInterface(234).isValid()
				&& !RSInterface.getInterface(232).isValid() && !RSInterface.getInterface(215).isValid()
				&& !RSInterface.getInterface(243).isValid() && !RSInterface.getInterface(241).isValid()
				&& !getInterface(242, 5).containsText("notice, sir. My fee is 5000 coins.")
				&& !getInterface(242, 5).containsText("notice, madam. My fee is 5000 coins.")
				&& waitForButler() == false) {
			//Info: Interface: 234
			return State.CLICKBUTLER;
			
		} else if (Rune2 == 0 && !inventoryContains(SoftClay) && getInterface(234, 2).containsText("Fetch another 26 pieces of soft clay.")) {
			//Info: Interface: 234, Child: 2, Text: "Fetch another 26 pieces of soft clay."
			return State.SENDBUTLER1;
			
		} else if (Rune2 >= 1 && !inventoryContains(SoftClay) && getInterface(234, 2).containsText("Fetch another 25 pieces of soft clay.")) {
			//Info: Interface: 234, Child: 2, Text: "Fetch another 25 pieces of soft clay."
			return State.SENDBUTLER1;
			
		} else if (!inventoryContains(SoftClay) && getInterface(234, 2).containsText("Fetch another 20 pieces of soft clay.")) {
			//Info: Interface: 234, Child: 2, Text: "Fetch another 20 pieces of soft clay."
			return State.SENDBUTLER1;
			
		} else if (!inventoryContains(SoftClay) && getInterface(232, 5).containsText("More...")
				&& !getInterface(234, 3).containsText("Soft Clay.")) {
			//Info: Interface: 234, Child: 6, Text: "More..."
			return State.SENDBUTLER2;
			
		} else if (!inventoryContains(SoftClay) && getInterface(234, 3).containsText("Soft clay.")) {
			//Info: Interface: 234. Child: 3 Text: "Soft Clay."
			return State.SENDBUTLER3;
			
		} else if (!inventoryContains(SoftClay) && RSInterface.getInterface(215).isValid()) {
			//Info: Interface: 215. (Enter amount of soft clay to withdraw)
			return State.SENDBUTLER4;
			
		} else if (!inventoryContains(SoftClay) && RSInterface.getInterface(243).isValid()) {
			//Info: Interface: 243, Child: 7, Text "Click here to continue"
			return State.SENDBUTLER5;
			
		} else if (!inventoryContains(SoftClay) && getInterface(232, 3).containsText("Pay servant 10000 coins from your bank.")) {
			//Info: Interface: 232, Child: 3, Text "Pay servant 10000 coins from your bank."
			return State.SENDBUTLER6;
			
		} else if (!inventoryContains(SoftClay) && getInterface(232, 3).containsText("Pay servant 5000 coins from your bank.")) {
			//Info: Interface: 232, Child: 3, Text "Pay servant 5000 coins from your bank."
			return State.SENDBUTLER6;
			
		} else if (!inventoryContains(SoftClay) && RSInterface.getInterface(241).isValid()) {
			//Info: Interface: 241, Child: 5, Text :Click here to continue"
			return State.SENDBUTLER7;
			
		} else if (!inventoryContains(SoftClay) && getInterface(242, 5).containsText("notice, sir. My fee is 5000 coins.")) {
			//Info: Interface: 242, Child: 5, Text :Click here to continue"
			return State.SENDBUTLER7;
			
		} else if (!inventoryContains(SoftClay) && getInterface(242, 5).containsText("notice, madam. My fee is 5000 coins.")) {
			//Info: Interface: 242, Child: 5, Text :Click here to continue"
			return State.SENDBUTLER7;
			
		} else if (inventoryContains(SoftClay) && RSInterface.getInterface(242).isValid()) {
			//Info: Interface: 242, Child: 6, Text "Click here to continue"
			return State.SENDBUTLER8;
			
		} else if (animTest() == false && inventoryContains(SoftClay) && !RSInterface.getInterface(400).isValid()) {
			return State.OPENBOOK;
			
		} else if (inventoryContains(SoftClay) && animTest() == true) {
			return State.WAITCLAY;
			
		}
		
		return State.WAITCLAY;
		
	}
	
		public boolean waitForButler() {
			RSNPC Butler = getNearestNPCByID(ButlerID);
	
			if (Butler.isMoving()) {
				return true;
	
			} else 
				return false;
	
		}
	
		public boolean animTest() {
				if (waitForAnim(Animation) >= random(25,65)) {
					return true;
					
				}
				
				return false;
				
		}

	@Override
		public void onRepaint(final Graphics g) {
		Color Green = new Color(34, 139, 34);
		long runTime = 0;
		long seconds = 0;
		long minutes = 0;
		long hours = 0;
		runTime = System.currentTimeMillis() - startTime;
		seconds = runTime / 1000;
		
		if (seconds >= 60) {
			minutes = seconds / 60;
			seconds -= (minutes * 60);
			
		}
		
		if (minutes >= 60) {
			hours = minutes / 60;
			minutes -= (hours * 60);
			
		}
		
	    final int XPToLVL = skills.getXPToNextLevel(STAT_MAGIC);
		final int XPChange = skills.getCurrentSkillExp(STAT_MAGIC) - startXP;
		final int TabChange = XPChange / XP;
		final int LevelChange = skills.getCurrentSkillLevel(STAT_MAGIC) - startLevel;
		final int finalProfit = TabChange * Profit;
		final float totalMin = hours * 60 * 60 + minutes * 60 + seconds;
		final float perHourXP = XPChange / (totalMin / 60);
		final float perHourTab = TabChange / (totalMin / 60);
		final float perHourProfit = finalProfit / (totalMin / 60);
		if (!usePaint) return;
		if (isLoggedIn()) {		
			PaintSwitch = paintTab();
			
			switch (PaintSwitch) {
			
			//Give credit if you are going to use this -FiLThY_FreaK_
			
			case 0: //MAIN TAB
				g.setColor(new Color(0, 0, 0, 95));
				g.fillRect(545, 430, 40, 30); //Main Tab
				g.fillRect(595, 430, 40, 30); //Stats Tab
				g.fillRect(645, 430, 40, 30); //Script Info
				g.fillRect(697, 430, 40, 30); //Paint Off
				g.setColor(Color.white);
				g.drawString("Main", 552, 450);
				g.drawString("Stats", 600, 450);
				g.drawString("Info", 657, 450);
				g.drawString("Hide", 704, 450);
				g.setColor(new Color(0, 0, 0, 95));
				g.fillRect(545, 250, 192, 170); //737
				g.fillRect(545, 420, 40, 10);
				g.setColor(Color.white);
				g.setFont(new Font("dialog", Font.BOLD, 14));
				g.drawString("Freak's AIO Tab Maker", 565, 270);
				g.setFont(new Font("dialog", Font.PLAIN, 12));
				g.drawString("Runtime:", 550, 295);
				g.drawString(hours + " hours " + minutes + " minutes " + seconds + " seconds.", 550, 310);
				g.drawString("Making: " + tabName, 550, 330);
				g.drawString("Tabs made: " + TabChange, 550, 345);
				g.drawString("Profit made: " + finalProfit, 550, 360);
				g.drawString("Profiting " + perHourProfit * 60 + "GP P/H", 550, 375);
				g.drawString("Averaging " + perHourTab * 60 + " tabs P/H.", 550, 390);
				g.drawString("Averaging " + perHourXP * 60 + " XP P/H.", 550, 405);
				
				break;
				
			case 1: //STATS TAB
				g.setColor(new Color(0, 0, 0, 95));
				g.fillRect(545, 430, 40, 30); //Main Tab
				g.fillRect(595, 430, 40, 30); //Stats Tab
				g.fillRect(645, 430, 40, 30); //Script Info
				g.fillRect(697, 430, 40, 30); //Paint Off
				g.setColor(Color.white);
				g.drawString("Main", 552, 450);
				g.drawString("Stats", 600, 450);
				g.drawString("Info", 657, 450);
				g.drawString("Hide", 704, 450);
				g.setColor(new Color(0, 0, 0, 95));
				g.fillRect(545, 250, 192, 170); //737
				g.fillRect(595, 420, 40, 10);
				g.setColor(Color.white);
				g.setFont(new Font("dialog", Font.BOLD, 14));
				g.drawString("Freak's AIO Tab Maker", 565, 270);
				g.setFont(new Font("dialog", Font.PLAIN, 12));
				g.drawString("Butler: " + ButlerName, 550, 295);
				g.drawString("Bank Trips: " + BankTrips, 550, 310);
				g.drawString("Current Magic lvl: " + skills.getCurrentSkillLevel(STAT_MAGIC), 550, 325);
				g.drawString("Gained " + LevelChange + " Magic levels.", 550, 340);
				g.drawString("Gained " + XPChange + " XP.", 550, 355);
				g.drawString("We are " + XPToLVL + " XP away from", 550, 370);
				g.drawString("your next Magic level.", 550, 385);
				g.setColor(Color.red);
				g.fillRect(550, 390, 100, 11);
				g.setColor(Green);
				g.fillRect(550, 390, skills.getPercentToNextLevel(STAT_MAGIC), 11);
				g.setColor(Color.white);
				g.drawString(skills.getPercentToNextLevel(STAT_MAGIC) + "%", 595, 400);
				
				break;
				
			case 2: //SCRIPT INFO
				g.setColor(new Color(0, 0, 0, 95));
				g.fillRect(545, 430, 40, 30); //Main Tab
				g.fillRect(595, 430, 40, 30); //Stats Tab
				g.fillRect(645, 430, 40, 30); //Script Info
				g.fillRect(697, 430, 40, 30); //Paint Off
				g.setColor(Color.white);
				g.drawString("Main", 552, 450);
				g.drawString("Stats", 600, 450);
				g.drawString("Info", 657, 450);
				g.drawString("Hide", 704, 450);
				g.setColor(new Color(0, 0, 0, 95));
				g.fillRect(545, 250, 192, 170); //737
				g.fillRect(645, 420, 40, 10);
				g.setColor(Color.white);
				g.setFont(new Font("dialog", Font.BOLD, 14));
				g.drawString("Freak's AIO Tab Maker", 565, 270);
				g.setFont(new Font("dialog", Font.PLAIN, 12));
				g.drawString("Author: FiLThY_FreaK_", 550, 295);
				g.drawString("Version: 1.8.1", 550, 310);
				g.drawString("Website: www.teamaussie.net", 550, 325);
				g.drawString("Catagory: Magic/Money Making", 550, 340);
				
				break;
				
			case 3: //PAINT OFF
				g.setColor(new Color(0, 0, 0, 95));
				g.fillRect(697, 430, 40, 30); //Paint On
				g.setColor(Color.white);
				g.drawString("Show", 704, 450);
				
				break;
				
			case 4: //PAINT ON
				g.setColor(new Color(0, 0, 0, 95));
				g.fillRect(545, 430, 40, 30); //Main Tab
				g.fillRect(595, 430, 40, 30); //Stats Tab
				g.fillRect(645, 430, 40, 30); //Script Info
				g.fillRect(697, 430, 40, 30); //Paint Off
				g.setColor(Color.white);
				g.drawString("Main", 552, 450);
				g.drawString("Stats", 600, 450);
				g.drawString("Info", 657, 450);
				g.drawString("Hide", 704, 450);
				
				break;
				
				}
			
			}
		
	}
		
	public int paintTab() { //Half credit to Taha
		Mouse m = Bot.getClient().getMouse();
		
		if (m.x >= 545 && m.x < 545 + 40 && m.y >= 430 && m.y < 430 + 30 && f != 2) {
			//550, 400 - 630, 430
			return 0;
			
		} else
		
		if (m.x >= 595 && m.x < 595 + 40 && m.y >= 430 && m.y < 430 + 30 && f != 2) {
			return 1;
			
		} else
			
		if (m.x >= 645 && m.x < 645 + 40 && m.y >= 430 && m.y < 430 + 30 && f != 2) {
			return 2;
			
		} else
				
		if (m.x >= 697 && m.x < 697 + 40 && m.y >= 430 && m.y < 430 + 30 && f == 0) {
			f = 1;
			return 3;
			
		} else
			
		if (m.x <= 696 | m.x > 698 + 40 | m.y <= 429 | m.y > 431 + 30 && f == 1) {
			f = 2;
			return 3;

		} else
			
		if (m.x >= 697 && m.x < 697 + 40 && m.y >= 430 && m.y < 430 + 30 && f == 2) {
			f = 3;
			return 4;
			
		} else
			
		if (m.x <= 696 | m.x > 698 + 40 | m.y <= 429 | m.y > 431 + 30 && f == 3) {
			f = 0;
			return 4;
					
		}
		
		return PaintSwitch;
			//650, 400 - 730, 430
		
	}
	
	public void antiBan() {
		int rn = random(1, 20);
		
		if (rn <= 20 && antiBanOption != 1) {
			if (rn == 1) {
				if (antiBanOption == 2 | antiBanOption == 4) {
				wait(random(100, 200));
				setCameraRotation(random(1,360));
				wait(random(100, 200));
				
				}
				
				if (antiBanOption == 3 | antiBanOption == 4) {
					moveMouse(random(50, 700), random(50, 450), 2, 2);
					
				}
				
			}
			
			if (rn == 2) {
				if (antiBanOption == 2 | antiBanOption == 4) {
				setCameraRotation(random(1,360));
				
				}
				
			}
			
			if (rn == 3) {
				if (antiBanOption == 3 | antiBanOption == 4) {
					moveMouse(random(50, 700), random(50, 450), 2, 2);
					
				}
				
			}
			
			if (rn == 4) {
				if (antiBanOption == 3 | antiBanOption == 4) {
					wait(random(100, 200));
					moveMouse(random(50, 700), random(50, 450), 2, 2);
            	
				}
				
				if (antiBanOption == 2 | antiBanOption == 4) {
            	wait(random(200, 1200));
            	setCameraRotation(random(1,360));
            	
				}
            	
            	if (antiBanOption == 3 | antiBanOption == 4) {
            	wait(random(200, 300));
            	moveMouse(random(50, 700), random(50, 450), 2, 2);
            	
            	}
            	
			}
			
			if (rn == 5) {
				if (antiBanOption == 2 | antiBanOption == 4) {
				wait(random(100, 200));
				setCameraRotation(random(1,360));
				
				}
				
				if (antiBanOption == 3 | antiBanOption == 4) {
	            	wait(random(200, 800));
	            	moveMouse(random(50, 700), random(50, 450), 2, 2);
	            	
				}
				
				if (antiBanOption == 2 | antiBanOption == 4) {
				wait(random(100, 400));
				setCameraRotation(random(1,360));
				
				}
				
			}
			
			if (rn == 6) {
				if (antiBanOption == 3 | antiBanOption == 4) {
					wait(random(100, 600));
					moveMouse(random(50, 700), random(50, 450), 2, 2);
            	
				}
				
				if (antiBanOption == 2 | antiBanOption == 4) {
            	wait(random(200, 1200));
            	setCameraRotation(random(1,360));
            	
				}
            	
			}
			
		}
		
	}
	
	@Override
	public void serverMessageRecieved(ServerMessageEvent e) {
		//Nothing here
		
	}

}