import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Map;

import org.rsbot.bot.Bot;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.script.Calculations;
import org.rsbot.script.Constants;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSInterface;
import org.rsbot.script.wrappers.RSInterfaceChild;
import org.rsbot.script.wrappers.RSItemTile;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSPlayer;
import org.rsbot.script.wrappers.RSTile;

@ScriptManifest(authors = { "Weirded" }, category = "Hunter", name = "WeirdMultiHunter", description = "<html><head><style type='text/css'> body {font-family: 'Tahoma'; font-size: 10px; margin-left: 10px;}</style></head><body><h3>WeirdMultiHunter by Weirded</h3><br /><p><select name='thehunted'><option>Crimson Swift</option><option>Golden Warbler</option><option>Copper Longtail</option><option>Cerulean Twitch</option><option>Tropical Wagtail</option><option>Ferret</option><option>Grey Chinchompa</option><option>Red Chinchompa</option></select><br /><input type='checkbox' name='burybones' value='true'><b>Bury Bones?</b><br /><input type='checkbox' name='lagthing' value='true'><b>Check this if script lags</b><br /></p><p><b>Notice: </b>Make sure you turn off BreakHandler unless you want to lose alot of traps!</p><br /><p>Log in and stand where you want to hunt before starting.</p><br /></body></html>", version = 2.0)
public class WeirdMultiHunter extends Script implements PaintListener, ServerMessageListener{
	public int TrapItem;
	public int TrapFailed;
	public int TrapCaught;
	public int TrapLaid;

	public int whichTrap = 0;
	public int loopyThing = 0;
	public int checkThing = 0;
	
	public int bones = 526;
	public int birdmeat = 9978;
	public int ferret = 10092;
	
	public int TrapLayAnim = 5208;
	public int TrapRetrieveAnim;

	public int numCaught = 0;
	public double expPer = 0;
	public int dropStuff = 0;
	public boolean buryBones = false;
	public boolean lagThing = false;

	public boolean[] Trap = {false, false, false, false, false};
	public RSTile[] TrapPos = new RSTile[5];
	public int numTraps = 0;

	public RSTile HuntingArea = new RSTile(-1, -1);
	public RSTile oldPosition = new RSTile(-1, -1);
	public int standingCount = 0;
	public boolean logoutCheck = false;

	public long runTime = 0, seconds = 0 ,minutes = 0, hours = 0;
	public int gainedExp = 0;
	public int startLevel = 0;
	public int startExp = 0;
	public int expToLevel = 0;
	public long secToLevel = 0;
	public long minutesToLevel = 0;
	public long hoursToLevel = 0;
	public long startTime = 0;
	public float secExp = 0;
	public float minuteExp = 0;
	public float hourExp = 0;

	public String getName(){
		return "WeirdMultiHunter";
	}

	public String getAuthor(){
		return "Weirded";
	}

	public String getScriptCategory(){
		return "Hunter";
	}

	public double getVersion(){
		return 2.0;
	}

	public String getScriptDescription() {
		String html = "<html>";
		html += "<head>";
		html += "<style type=\"text/css\"> body {font-family: \"Tahoma\"; font-size: 10px; margin-left: 10px;}</style>";
		html += "</head>";
		html += "<body>";
		html += "<h3>" + getName() + " v" + getVersion() + " by " + getAuthor() + "</h3><br />";
		html += "<p>";
		html += "<select name=\"thehunted\">";
		html += "<option>Crimson Swift</option>";
		html += "<option>Golden Warbler</option>";
		html += "<option>Copper Longtail</option>";
		html += "<option>Cerulean Twitch</option>";
		html += "<option>Tropical Wagtail</option>";
		html += "<option>Ferret</option>";
		html += "<option>Grey Chinchompa</option>";
		html += "<option>Red Chinchompa</option>";
		html += "</select><br />";
		html += "<input type=\"checkbox\" name=\"burybones\" value=\"true\"><b>Bury Bones?</b><br />";
		html += "<input type=\"checkbox\" name=\"lagthing\" value=\"true\"><b>Check this if script lags</b><br />";
		html += "</p>";
		html += "<p><b>Notice: </b>Make sure you turn off BreakHandler unless you want to lose alot of traps!</p><br />";
		html += "<p>Log in and stand where you want to hunt before starting.</p><br />";
		html += "</body>";
		html += "</html>";        

		return html;
	}

	public boolean onStart(Map<String, String> args) {
		int i = 0;
		int j = 0;
		if(!isLoggedIn()){
			log("Log in and stand where you want to hunt before starting.");
		}
		if (args.get("thehunted").equals("Crimson Swift")){ i = 0; j = 0;}
		if (args.get("thehunted").equals("Golden Warbler")){ i = 1; j = 0;}
		if (args.get("thehunted").equals("Copper Longtail")){ i = 2; j = 0;}
		if (args.get("thehunted").equals("Cerulean Twitch")){ i = 3; j = 0;}
		if (args.get("thehunted").equals("Tropical Wagtail")){ i = 4; j = 0;}
		if (args.get("thehunted").equals("Ferret")){ i = 5; j = 1;}
		if (args.get("thehunted").equals("Grey Chinchompa")){ i = 6; j = 1;}
		if (args.get("thehunted").equals("Red Chinchompa")){ i = 7; j = 1;}
			
		switch(i){
			case 0:
				TrapCaught = 19180;
				expPer = 34;
				dropStuff = 1;
			break;
			case 1:
				TrapCaught = 19184;
				expPer = 48;
				dropStuff = 1;
			break;
			case 2:
				TrapCaught = 19186;
				expPer = 61;
				dropStuff = 1;
			break;
			case 3:
				TrapCaught = 19182;
				expPer = 64.67;
				dropStuff = 1;
			break;
			case 4:
				TrapCaught = 19178;
				expPer = 95.2;
				dropStuff = 1;
			break;
			case 5:
				TrapCaught = 19191;
				expPer = 115;
				dropStuff = 2;
			break;
			case 6:
				TrapCaught = 19189;
				expPer = 198;
			break;
			case 7:
				TrapCaught = 19190;
				expPer = 265;
			break;
			default:
			break;
		}
		switch(j){
			case 0:
				TrapItem = 10006;
				TrapFailed = 19174;
				TrapLaid = 19175;
				TrapRetrieveAnim = 5207;
			break;
			case 1:
				TrapItem = 10008;
				TrapFailed = 19192;
				TrapLaid = 19187;
				TrapRetrieveAnim = 5212;
			break;
			default:
			break;
		}
		buryBones = args.get("burybones") != null ? true : false;
		lagThing = args.get("lagthing") != null ? true : false;
		HuntingArea = getMyPlayer().getLocation();
		return true;
	}    

	public void onFinish(){
		return;
	}

	@Override
	protected int getMouseSpeed() {//Thanks to RcZhang
		return random(6,7);
	}

	public boolean clickInventoryItem(int itemID) {//Thanks to RcZhang
		if (getCurrentTab() != TAB_INVENTORY
							 && !RSInterface.getInterface(INTERFACE_BANK).isValid()
							 && !RSInterface.getInterface(INTERFACE_STORE).isValid()
							 && getMyPlayer().getAnimation() == -1) {
					openTab(TAB_INVENTORY);
					wait(random(100, 200));
		}
		int[] items = getInventoryArray();
		java.util.List<Integer> possible = new ArrayList<Integer>();
		for (int i = 0; i < items.length; i++) {
				  if (items[i] == itemID) {
							 possible.add(i);
				  }
		}
		if (possible.size() == 0) return false;
		int idx = possible.get(0);
		Point t = getInventoryItemPoint(idx);
		moveMouse(t.x + 5, t.y + 5, 5, 5);
		wait(random(40, 50));
		clickMouse(true);
		wait(random(80, 100));
		return true;
	}

	@Override
    public boolean atInventoryItem(final int itemID, final String option) {
        if (getCurrentTab() != Constants.TAB_INVENTORY
                && !RSInterface.getInterface(Constants.INTERFACE_BANK)
                .isValid()
                && !RSInterface.getInterface(Constants.INTERFACE_STORE)
                .isValid()) {
            openTab(Constants.TAB_INVENTORY);
        }
        final int[] items = getInventoryArray();
        final java.util.List<Integer> possible = new ArrayList<Integer>();
        for (int i = 0; i < items.length; i++) {
            if (items[i] == itemID) {
                possible.add(i);
            }
        }
        if (possible.size() == 0) {
            return false;
        }
        final int idx = possible.get(random(0, possible.size()));
        final Point t = getInventoryItemPoint(idx);
        moveMouse(t.x + 5, t.y + 5, 5, 5);
		  wait(random(40, 50));
        return atMenu(option);
    }
	
	/**
	 * Loops through all menu items to find the longest
	 * then returns the longest menu item. Since each character
	 * is roughly 7 pixels it returns length*7.
	 * Weirded :]
	 * */
	public int getMenuLengthEstimate(){
		int i, longest = 0;
		for(i = 1; i < getMenuItems().size(); i++){
			if(getMenuItems().get(i).length() > getMenuItems().get(longest).length()){
				longest = i;
			}
		}
		
		return getMenuItems().get(longest).length()*7;
	}
	
	@Override
	public boolean atMenuItem(final int i) {
		if (!isMenuOpen()) {
			return false;
		}
		try {
			final RSTile menu = getMenuLocation();
			final int menuLength = getMenuLengthEstimate();
			final int xOff = random((menuLength/2 - 15), (menuLength/2 + 15)); //clicks near the middle of the menu
			final int yOff = random(23, 27) + 15 * i;
			moveMouse(menu.getX() + xOff, menu.getY() + yOff);
			if (!isMenuOpen()) {
				return false;
			}
			wait(random(20,25));
			clickMouse(true);
			wait(random(20,25));
			return true;
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean HasTrapSet(RSTile tile){
		RSObject obj = getObjectAt(tile);
		if(obj == null){
			return false;
		}
		if(obj.getID() == TrapLaid){
			return true;
		}
		return false;
	}

	public int HasTrap(RSTile tile){
		if(tile == null){
			return -1;
		}
		RSObject obj = getObjectAt(tile);
		if(obj == null){
			return -1;
		}
		return obj.getID();
	}

	public boolean HasTrapLaid(RSTile tile){
		if(tile == null){
			return false;
		}
		int i = HasTrap(tile);
		if(i == -1){
			return false;
		}
		if(i >= 19170 && i <= 19210){
			return true;
		}
		return false;
	}
	
	public boolean HasTrapItem(RSTile tile){
		if(tile == null){
			return false;
		}
		RSItemTile[] itemtile = getGroundItemsAt(tile);
		int i = 0;
		for(i = 0; i < itemtile.length; i++){
			if(itemtile[i].getItem().getID() == TrapItem){
				return true;
			}
		}
		
		return false;
	}

	public void SetTrapFalse(int TrapNum){
		Trap[TrapNum] = false;
	}

	public int CheckTrap(int TrapNum){
		if(Trap[TrapNum] == true){
			if(HasTrap(TrapPos[TrapNum]) == TrapCaught || HasTrap(TrapPos[TrapNum]) == TrapFailed){
				 return HasTrap(TrapPos[TrapNum]);
			}
			if(HasTrapItem(TrapPos[TrapNum])){
				 return TrapItem;
			}
		}
		return -1;
	}

	public int TrapsLaid(){
		int i, count = 0;
			for(i=0; i<numTraps; i++){
				 if(Trap[i] == true){
					  count++;
				 }
			}
		return count;
	}

	public int LaidTrapsLaid(){
		int i, count = 0;
			for(i=0; i<numTraps; i++){
				 if(Trap[i] == true && HasTrapSet(TrapPos[i]) == true){
					  count++;
				 }
			}
		return count;
	}

	public boolean TrapClick(RSTile tile, boolean lay){
		if(HasTrapItem(tile) == true && lay == true){
			weirdMouseMove(tile, "Lay");
			return true;
		}else if(HasTrapItem(tile) == true && lay == false){
			weirdMouseMove(tile, "Take");
			return true;
		}else if(HasTrap(tile) == TrapCaught){
			weirdMouseMove(tile, "Check");
			return true;
		}else if(HasTrap(tile) == TrapFailed){
			weirdMouseMove(tile, "Dismantle");
			return true;
		} 
		return false;
	}

	public boolean TrapRetrieve(int TrapNum){
		if(Trap[TrapNum] == false){
			return false;
		}
		if(CheckTrap(TrapNum) == -1){
			return false;
		}
		int i = 0;
		if(HasTrapLaid(TrapPos[TrapNum]) && HasTrapItem(TrapPos[TrapNum])){
			TrapClick(TrapPos[TrapNum], false);
			waitToMove(random(1000, 2000));
			for(; getMyPlayer().isMoving(); wait(random(20, 25)));
			whichTrap--;
			return true;
		}
		TrapClick(TrapPos[TrapNum], true);
		if(HasTrapItem(TrapPos[TrapNum])){
			for(i = 0;HasTrapSet(TrapPos[TrapNum]) == false;i++){
				wait(random(20,25));
				if(i > 200){
					 return false;
				}
			}
			checkThing = 0;
			return true;
		}
		for(i = 0;CheckTrap(TrapNum) != -1;i++){
			wait(random(20,25));
			if(i > 200){
				 return false;
			}
		}
		SetTrapFalse(TrapNum);
		checkThing = 0;
		whichTrap--;
		wait(random(200,250));
		if(!tileCompare(getMyPlayer().getLocation(), TrapPos[TrapNum])){
			 weirdWalk(TrapPos[TrapNum]);
			 if(waitToMove(random(1000, 2000)) == false){
				  return false;
			 }
			 AntiBan(12, false);
			 for(; getMyPlayer().isMoving(); wait(random(20, 25)));
		}
		return true;
	}

	public boolean TrapLay(int TrapNum) {
		if(Trap[TrapNum] == true){
			return false;
		}
		int i = 0;
		RSTile Spot = TrapPos[TrapNum];
		Point TrapPoint = new Point(-1, -1);
		if(HasTrapLaid(Spot) || HasTrapItem(Spot)){
				for(;waitForAnim(20) != -1; wait(random(20,25)));
				for(;getMyPlayer().isMoving(); wait(random(20,25)));
				TrapClick(Spot, false);
				if(HasTrap(Spot) == TrapFailed || HasTrap(Spot) == TrapCaught){
					waitForAnim(random(1000, 2000));
					for(;waitForAnim(20) == TrapRetrieveAnim; wait(random(20,25)));
				}else if(HasTrapItem(Spot)){
					waitToMove(random(1000, 2000));
					for(;getMyPlayer().isMoving(); wait(random(20,25)));
				}
			return false;
		}
		if(!tileCompare(getMyPlayer().getLocation(), Spot)){
			weirdWalk(Spot);
			if(waitToMove(random(1000, 2000)) == false){
				 return false;
			}
			AntiBan(12, false);
			for(; getMyPlayer().isMoving(); wait(random(20, 25)));
		}
		if(!tileCompare(getMyPlayer().getLocation(), Spot)){
			return false;
		}
		clickInventoryItem(TrapItem);
		AntiBan(13, false);
		for(i = 0;HasTrapSet(Spot) == false;i++){
			wait(random(20,25));
			if(i > 200){
				 return false;
			}
		}
		checkThing = 0;
		if(Trap[TrapNum] == false){
		  Trap[TrapNum] = true;
		  return true;
		}else{
		  return false;
		}
	}

	public void ResetStuff(){
		int i;
		for(i=0; i<5; i++){
			if(Trap[i] == true && !HasTrapLaid(TrapPos[i]) && !HasTrapItem(TrapPos[i])){
				 SetTrapFalse(i);
			}
		}
	}

	public boolean weirdWalk(RSTile tile){
		Point p = new Point(-1, -1);
		do{
			p = Calculations.tileToScreen(tile);
			if(p.x == -1 || p.y == -1){
				 return false;
			}
			moveMouse(p,random(-3,3),random(-3,3));
			wait(random(20, 25));
		}while(!atMenu("Walk Here"));
		wait(random(40,50));
		return true;        
	}

	public boolean weirdMouseMove(RSTile tile, String action){
	  Point p = new Point(-1, -1);
	  int i = 0;
	  boolean check = false;
	  do{
			if(i > 2){
				for(;waitForAnim(20) != -1; wait(random(20,25)));
				for(;getMyPlayer().isMoving(); wait(random(20,25)));
			}
			if(i > 30){
				return false;
			}
			p = Calculations.tileToScreen(tile);
			if(p.x == -1 || p.y == -1){
				return false;
			}
			moveMouse(p,random(-10,10),random(-10,10));
			wait(random(20, 25));
			if(getMenuIndex(action) != -1){
				//if (getMenuItems().get(0).toLowerCase().contains(action.toLowerCase())) {
					//clickMouse(true);
					//check = true;
				//} else {
					//clickMouse(false);
					wait(random(20,25));
					check = atMenu(action);
				//}
			}
			i++;
			if(i % 10 == 0){
				setCameraRotation(random(1, 359));
				setCameraAltitude(random(60.0, 80.0));
			}
	  }while(!check);
	  return true;
	}

	public boolean tileCompare(RSTile tile1, RSTile tile2){
		if(tile1 == null || tile2 == null){
			return false;
		}
		if(tile1.getX() == tile2.getX() && tile1.getY() == tile2.getY()){
			return true;
		}
		return false;
	}

	public RSTile nearestTile(RSTile[] tiles){
		int i, closest = 0;
		for(i=1;i<tiles.length;i++){
			if(distanceTo(tiles[i]) < distanceTo(tiles[closest])){
				 closest = i;
			}
		}
		return tiles[closest];
	}

	public void SetupSpots(){
		if(skills.getCurrentSkillLevel(Constants.STAT_HUNTER) < 20){
			numTraps = 1;
		}else if(skills.getCurrentSkillLevel(Constants.STAT_HUNTER) < 40){
			numTraps = 2;
		}else if(skills.getCurrentSkillLevel(Constants.STAT_HUNTER) < 60){
			numTraps = 3;
		}else if(skills.getCurrentSkillLevel(Constants.STAT_HUNTER) < 80){
			numTraps = 4;
		}else{
			numTraps = 5;
		}
		
		switch(numTraps){
			case 5:
				TrapPos[4] = new RSTile(HuntingArea.getX(), HuntingArea.getY());
			case 4:
				TrapPos[3] = new RSTile(HuntingArea.getX() - 1, HuntingArea.getY() - 1);
			case 3:
				TrapPos[2] = new RSTile(HuntingArea.getX() + 1, HuntingArea.getY() - 1);
			case 2:
				TrapPos[1] = new RSTile(HuntingArea.getX() - 1, HuntingArea.getY() + 1);				
			case 1:
				TrapPos[0] = new RSTile(HuntingArea.getX() + 1, HuntingArea.getY() + 1);
			break;
			default:
			break;
		}
	}

	public void AntiBan(int r, final boolean Random) { // Made By Drizzt1112, edited a bit
		if (Random) {
			r = random(0, 20);
		}
		switch (r) {
		case 1:
			setCameraRotation(random(1, 359));
			break;
		case 2:
			setCameraAltitude(random(60.0, 80.0));
			break;
		case 3:
		case 4:
		case 5:
		case 6:
			moveMouse(random(1, 760), random(1, 499));
			break;
		case 7:
			//openTab(random(0, 12));
			//break;
		case 8:
			setCameraRotation(random(1, 359));
			setCameraAltitude(random(60.0, 80.0));
			break;
		case 9:
			int x = input.getX();
			int y = input.getY();
			moveMouse(x + random(-65, 75), y + random(-60, 70));
			x = input.getX();
			y = input.getY();
			moveMouse(x + random(-75, 70), y + random(-78, 69));
			x = input.getX();
			y = input.getY();
			moveMouse(x + random(-72, 74), y + random(-64, 72));
			x = input.getX();
			y = input.getY();
			moveMouse(x + random(-65, 64), y + random(-78, 66));
			break;
		case 10:
		case 11:
			if (getCurrentTab() != Constants.TAB_STATS) {
				 openTab(Constants.TAB_STATS);
			}
			clickMouse(random(716, 721), random(415, 430), true);
			wait(random(100,200));
			moveMouse(random(613, 633), random(421, 441));
			wait(random(400,500));
			if (getCurrentTab() != Constants.TAB_INVENTORY) {
				 openTab(Constants.TAB_INVENTORY);
			}
			break;
		case 12:
			moveMouse(650, 250, random(-80, 80), random(-60, 60));
			break;
		case 13:
			Point p = Calculations.tileToScreen(HuntingArea);
			if(p.x == -1 || p.y == -1){
				 break;
			}
			moveMouse(p, random(-100, 100), random(-100, 100));
			break;
		case 14:
		case 15:
			final int x2 = input.getX();
			final int y2 = input.getY();
			moveMouse(x2 + random(-80, 80), y2 + random(-80, 80));
			break;
		case 16:
		case 17:
		case 18:
		case 19:
			final int x3 = random(0, 710);
			final int y3 = random(0, 420);
			moveMouse(40, 80, x3, y3);
			break;
		default:
			break;
		}
	}

	public int loop() {
		if (!isLoggedIn() || RSInterface.getInterface(378).getChild(45).getAbsoluteX() > 20 || !RSInterface.getInterface(149).isValid()) {//Credits to Ruski
			return random(1200, 3100);
		}
		
		if(logoutCheck == true){
			logout();
			stopAllScripts();
		}

		if(isRunning() == false){
			setRun(true);
			wait(random(500,1000));
		}

		/*if(loopyThing == 0){
			HuntingArea = nearestTile(HuntingAreas);
		}*/
		
		if(loopyThing == 0){
			SetupSpots();
		}
		
		if(dropStuff == 1){
			if(getInventoryCount(bones) > 0){
				if(buryBones){
					clickInventoryItem(bones);
				}else{
					atInventoryItem(bones, "Drop");
				}
				wait(random(1000,1500));
			}
			if(getInventoryCount(birdmeat) > 0){
				atInventoryItem(birdmeat, "Drop");
				return(random(1000,1500));
			}
		}else if(dropStuff == 2){
			if(getInventoryCount(ferret) > 0){
				atInventoryItem(ferret, "Release");
				return(random(1000,1500));
			}
		}

		if(distanceTo(HuntingArea) >= 4) {
			if(!tileOnScreen(HuntingArea)){
				 walkTileMM(HuntingArea);
			}else{
				 weirdWalk(HuntingArea);
			}
			waitToMove(random(1000, 2000));
			for(; getMyPlayer().isMoving(); wait(random(200, 250)));
			return random(200, 250);
		}

		if(inventoryContains(TrapItem)) {
			if(TrapsLaid() != numTraps){
			  if(TrapLay(whichTrap)){
			  }
			}
		}
		
		if(TrapRetrieve(whichTrap)){
		}

		if(whichTrap == -1){
			 whichTrap = numTraps - 1;
		}

		whichTrap++;
		checkThing++;

		if(whichTrap == numTraps){
			 whichTrap = 0;
		}
		
		if(checkThing >= 4 || lagThing){
			for(;waitForAnim(20) != -1; wait(random(20,25)));
			for(;getMyPlayer().isMoving(); wait(random(20,25)));
		}

		if(LaidTrapsLaid() == numTraps && checkThing >= random(100,200)){
			 if(!tileCompare(getMyPlayer().getLocation(), HuntingArea)){
				  weirdWalk(HuntingArea);
				  waitToMove(random(1000, 2000));
				  for(; getMyPlayer().isMoving(); wait(random(200, 250)));
			 }
		}

		if(RSInterface.getInterface(149).isValid()){
			if(TrapsLaid() < numTraps && !inventoryContains(TrapItem)){
				logoutCheck = true;
				log("Not enough Traps.");
			}
		}else{
			return random(1800,2000);
		}

		loopyThing++;
		if(loopyThing == (5*numTraps+20)){
			AntiBan(0, true);
			loopyThing = 0;
			for(;waitForAnim(20) != -1; wait(random(20,25)));
			for(;getMyPlayer().isMoving(); wait(random(20,25)));
			ResetStuff();
		}
		
		return random((140/numTraps), (160/numTraps));
	}

	public void onRepaint(final Graphics g) {
		if (g == null){
			return;
		}
		if(!isLoggedIn()){
			return;
		}

		final Color BG = new Color(0, 0, 0, 75);
		final Color RED = new Color(255, 0, 0, 255);
		final Color GREEN = new Color(0, 255, 0, 255);
		final Color BLACK = new Color(0, 0, 0, 255);

		if (startTime == 0) {
			startTime = System.currentTimeMillis();
		}

		if (startExp == 0) {
			startExp = skills.getCurrentSkillExp(Constants.STAT_HUNTER);
		}

		if (startLevel == 0) {
			startLevel = skills.getCurrentSkillLevel(Constants.STAT_HUNTER);
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

		gainedExp = skills.getCurrentSkillExp(Constants.STAT_HUNTER) - startExp;
		expToLevel = skills.getXPToNextLevel(Constants.STAT_HUNTER);

		if ((minutes > 0 || hours > 0 || seconds > 0) && gainedExp > 0) {
		secExp = ((float) gainedExp)/(float)(seconds + (minutes*60) + (hours*60*60));
		}
		minuteExp = secExp * 60;
		hourExp = minuteExp * 60;
		
		if(secExp > 0){
		secToLevel = (int)(expToLevel/(secExp));
		}
		if (secToLevel >= 60) {
			minutesToLevel = secToLevel / 60;
			secToLevel -= minutesToLevel * 60;
		}else{
			minutesToLevel = 0;
		}
		if (minutesToLevel >= 60) {
			hoursToLevel = minutesToLevel / 60;
			minutesToLevel -= hoursToLevel * 60;
		}else{
			hoursToLevel = 0;
		}
		
		secToLevel = secToLevel;
		minutesToLevel = minutesToLevel;
		hoursToLevel = hoursToLevel;

		g.setFont(new Font("Tahoma", Font.PLAIN, 10));
		g.setColor(BG);
		g.fill3DRect(345, 10, 160, 140, true);
		g.setColor(BLACK);
		g.drawString(getName() + " v" + getVersion(), 350+1, 25+1);
		g.setColor(Color.white);
		g.drawString(getName() + " v" + getVersion(), 350, 25);
		g.drawString("Running for: " + hours + ":" + minutes + ":" + seconds, 350, 40);
		g.drawString("Exp Gained: " + gainedExp + " (" + (skills.getCurrentSkillLevel(Constants.STAT_HUNTER) - startLevel) + ")", 350, 55);
		g.drawString("Stuff Caught: " + numCaught, 350, 70);
		g.drawString("Exp per hour: " + (int)hourExp, 350, 85);
		g.drawString("Exp to level: " + expToLevel + " (" + (int)((expToLevel/expPer)+0.5) + " catches)", 350, 100);
		g.drawString("Time to level: " + hoursToLevel + ":" + minutesToLevel + ":" + secToLevel, 350, 115);
		g.drawString("Progress to next level:", 350, 130);
		g.setColor(RED);
		g.fill3DRect(350, 135, 100, 10, true);
		g.setColor(GREEN);
		g.fill3DRect(350, 135, skills.getPercentToNextLevel(Constants.STAT_HUNTER), 10, true); 
		g.setColor(BLACK);
		g.drawString(skills.getPercentToNextLevel(Constants.STAT_HUNTER) + "%  to " + (skills.getCurrentSkillLevel(Constants.STAT_HUNTER) + 1), 380, 144);
		try{
			int i = 0;
			g.setColor(Color.white);
			g.drawString("Which Trap: " + whichTrap, 5, 120);
			g.drawString("Check Thing: " + checkThing, 5, 135);
			for(i=0; i<numTraps; i++){
				g.drawString("TrapPos" + i + ": " + TrapPos[i].toString(), 5, 150+(i*15));
			}
			for(i=0; i<numTraps; i++){
				g.drawString("Trap" + i + ": " + Boolean.toString(Trap[i]), 5, 150+(numTraps*15)+(i*15));
			}
		} catch (final Exception e) {
		}
	}

	public void serverMessageRecieved(ServerMessageEvent e) {
		String message = e.getMessage().toLowerCase();
		if(message.contains("caught")){
			numCaught++;
		}
	}

} 