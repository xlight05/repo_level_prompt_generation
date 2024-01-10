import java.util.List;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import org.rsbot.bot.Bot;
import org.rsbot.bot.input.CanvasWrapper;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.script.Constants;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSItemTile;
import org.rsbot.script.wrappers.RSTile;
import org.rsbot.script.wrappers.RSInterface;
import org.rsbot.script.wrappers.RSNPC;
import org.rsbot.script.wrappers.RSArea;

import org.rsbot.util.ScreenshotUtil;

import javax.swing.JOptionPane;
import javax.swing.JFileChooser;  




import java.text.NumberFormat;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javax.swing.WindowConstants;


//

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.URLConnection;




//





@ScriptManifest(authors = { "Vamp" }, category = "Combat", name = "Vamps Crawler Destroyer", version = 1.154, description = "<style type='text/css'>body {background:url('http://i5.photobucket.com/albums/y161/insain/miss-1.jpg') no-repeat}</style><html><head><center><head><center>"
	+ "<center>"
	+ "<b><font size=\"6\" color=\"#347C17\">"
	+""
	+"<br><br><br>"
	+ "<center><img src=\"http://jdrpg.exofire.net/jdrpg/images/title.gif\" /></center>"
	+ "</font></b><br>"
	+ "<font size=\"3\" color=\"white\">Start the script any were between crawlers and edge bank.<br></font>"
	+ "<center><h2>All settings can be set on the GUI, select your character and Start!</h2></center></td></tr><tr><td colspan=\"4\"><hr></td></tr>"
	+ "<tr><td><font size=\"6\" color=\"000000\"><b>Working Features:</b><br><font size=\"6\" color=\"FF6600\">"
	+ "<tr><td><font size=\"4\" color=\"59E817\">Bank when out of food"
	+ "<tr><td><font size=\"4\" color=\"59E817\"> Range Mode"
	+ "<tr><td><font size=\"4\" color=\"59E817\"> Melee Mode"
	+ "<tr><td><font size=\"4\" color=\"59E817\">Arrow Pick up supposed up to Adamant arrows due to only able to test F2p"
	+ "<tr><td><font size=\"4\" color=\"59E817\">System Update Detection!"
	+ "<tr><td><font size=\"4\" color=\"59E817\"><Built in AntiBan!"
	+ "<tr><td><font size=\"4\" color=\"59E817\">Eats and logsout if no food in bag!"
	+ "<tr><td><font size=\"6\" color=\"000000\"><b>Soon To Come</b>"
	+ "<tr><td><font size=\"4\" color=\"FF0000\"> -Script more flawless :D"
	+ "<tr><td><font size=\"4\" color=\"FF0000\"> -Addeding run control and the special attack!"
	+ "<tr><td><font size=\"4\" color=\"FF0000\"> -Smother Npc Attack"
	+ "<tr><td><font size=\"4\" color=\"FF0000\"> -100% supports range!"
	+ "<tr><td><font size=\"4\" color=\"FF0000\"> -Support for B2P"
	+ "<tr><td><font size=\"4\" color=\"FF0000\"> -Pick Items Up (Currently only picks up Arrows)"
	+ "<tr><td><font size=\"4\" color=\"FF0000\"> -Creating a Updater</td></tr>"
	+ "<br>"
	+ "</table>"
	+ "<tr><td colspan=2><b><font size=\"3\" color=\"FF6600\">&nbsp&nbsp Well Here we are First relase of Vamp Crawler Destroyer rember this is a Beta Script and being beta tested</font></b></td></tr></table>"
	+ "</center>" + "</body></html>")
	

	
	
//	"<html><head>"
//	+ "</head><body>"
//	+ "<center><img src=\"http://jdrpg.exofire.net/jdrpg/images/title.gif\" /></center>"
//	+ "</body></html>"
public class CrawlerDestroyer extends Script implements PaintListener, ServerMessageListener {
	final ScriptManifest properties = getClass().getAnnotation(
			ScriptManifest.class);
	VampCrawlerGUI gui;
	//Gui Stuff


	private int attackStyle = -1, strGained, atkGained, rgeGained, defGained;
	private final Rectangle ATTACK = new Rectangle(602, 273, 5, 5),
	STRENGTH = new Rectangle(683, 273, 5, 5), DEFENCE = new Rectangle(
			685, 324, 5, 5), RANGE = new Rectangle(683, 273, 5, 5);
	private String Status = "Starting";
	public String arrowtype = "Not in use";
	public String aused = "None";
	public boolean burybones = false,guiWait = true, guiExit;
	private JTextField txtFA;
	//end gui stuff

	private long startTime = System.currentTimeMillis();
	private long breakme = System.currentTimeMillis();
	private int levelup = 0;
	private int Amount;
	public int	Xmin = 2037;
	public int	Xmax = 2044;
	public int	Ymin = 5185;
	public int	Ymax = 5198;
	private int[] bankerid = {5912,5913};
	public String type = "Take Iron arrow";
	public String style = "";
	private RSTile bankToDungeon[] = new RSTile[] {new RSTile(3093,3494), new RSTile(3091,3491), new RSTile(3087,3490), new RSTile(3080,3484), new RSTile(3080,3476), new RSTile(3082,3465),
	new RSTile(3087,3452), new RSTile(3084,3440), new RSTile( 3079,3425),new RSTile(3085, 3432), new RSTile(3081,3421) };
	private RSArea outside = new RSArea(new RSTile(3072, 3415), new RSTile(3102, 3507));
	private RSTile ohcrap = new RSTile(2036, 5185);
	private RSTile ohcrap2 = new RSTile(2036, 5186);

	public int arrowID = -1, bronzeArrow = 882, ironArrow = 884, steelArrow = 886, mithrilArrow = 888, adamantArrow = 890;
	public boolean itemPresent(final int itemID) {

		RSItemTile item = getNearestGroundItemByID(arrowID);

		if (item != null) {
			int itemX = item.getX();
			int itemY = item.getY();
			if (Xmin <= itemX && itemX <= Xmax && Ymax >= itemY
					&& itemY >= Ymin) {

				return true;

			} else {

				return false;

			}

		}

		return false;

	}
	
/*	public void statlevel() {
		if (ranging
				&& skills.getCurrentSkillLevel(STAT_RANGE) == 70
				) {
			log("70 Range stoping script");
			logout();
			stopScript();
		} 
	}
*/


	private final int atOutside = 0, atFirstRoom= 1, goSecondLevel = 2, nearStairs = 3, atFDoors = 4, atLaberint= 5, atAfterLaberint = 6, atSDoors = 7, 
	atBeforeCrawlers = 8, atTDoors = 9, atSpot = 10;

	
	//paint
	private int startLevel = 0, startXP = 0, atkExp, 
	defExp,  hpExp, strExp, rangedExp, 
	 hpGained, startAtkExp,
	startDefExp, startStrExp, startRangedExp, startHpExp, crawlersKilled,
	crawlersPerHour;
final int XPChange = skills.getCurrentSkillExp(14) - startXP,
	levelChange = skills.getCurrentSkillLevel(14) - startLevel;
private long  time = System
	.currentTimeMillis(), hours, minutes, seconds;
private long  breaktime = System
.currentTimeMillis(), bhours, bminutes, bseconds;









	
	
	
	//1 floor
	private final int entranceID = 16154;
	private final RSTile entranceTile = new RSTile(3079, 3422);
	private final int exitID = 16148;
	private final int portalFirstLevelID = 16150;
	private final int stairToSecondLevel = 16149;
	private final RSArea firstRoom = new RSArea(new RSTile(1858, 5239), new RSTile(1865, 5244));
	private final RSArea almostSecondLevel= new RSArea(new RSTile(1901, 5212), new RSTile(1915, 5227));
	//
	
	//2 floor
	private final int doorID =16065; //16066 the other one
	private final int ropeID =16078; //drives you to start of the level so you can go up again, climb the ladder , climb another ladder and be outside
	private final int stairIDF2 =16080; //drives you to start of the level so you can go up again, climb the ladder , climb another ladder and be outside
	private RSNPC crawler = getNearestFreeNPCToAttackByID(4390); //level 35
	
	private final RSArea nearStair = new RSArea(new RSTile(2040,5240), new RSTile(2046,5246));
	private final RSArea firstDoors = new RSArea(new RSTile(2044,5237), new RSTile(2045,5239));
	private final RSArea laberint = new RSArea(new RSTile(2037,5211), new RSTile(2046,5236));
	private final RSArea afterLaberint = new RSArea(new RSTile(2036,5204), new RSTile(2039,5210));
	private final RSArea secondDoors = new RSArea(new RSTile(2036,5201), new RSTile(2037,5203));
	private final RSArea beforeCrawlers = new RSArea(new RSTile(2036,5198), new RSTile(2046,5208));
	private final RSArea thirdDoors = new RSArea(new RSTile(2045,5195), new RSTile(2046,5197));
	private final RSArea spot = new RSArea(new RSTile(2035, 5185), new RSTile(2046, 5194));
	private final RSTile safeSpot = new RSTile(2037,5185);
	
//	private RSTile laberintP[] = { new RSTile (2044,5236), new RSTile(2045,5221), new RSTile(2037,5208)  } ;
	private final RSTile laberintP[] = { new RSTile (2044,5236), new RSTile(2045,5227), new RSTile(2040,5216) ,new RSTile(2037,5208) } ;
	private final RSTile Door1EnA = new RSTile (2044,5240);
	private final RSTile Door1EnB = new RSTile (2044,5239);
	private final RSTile Door1ExA = new RSTile (2044,5237);
	private final RSTile Door1ExB = new RSTile (2044,5236);
	
	private final RSTile Door2EnA = new RSTile (2037,5204);
	private final RSTile Door2EnB = new RSTile (2037,5203);
	private final RSTile Door2ExA = new RSTile (2037,5201);
	private final RSTile Door2ExB = new RSTile (2037,5200);
	
	private final RSTile Door3EnA = new RSTile (2046,5198);
	private final RSTile Door3EnB = new RSTile (2046,5197);
	private final RSTile Door3ExA = new RSTile (2045,5195);
	private final RSTile Door3ExB = new RSTile (2045,5194);
	//
	//AREA 7,5 -> ROPE

	private double startExp[] = null;

	private double expGained[] = null;

	private float totalSec = 0;
	
	
	private final int tuna=361, lobster=379, salmon =329, trout =333;
	//private int[] food = { tuna, lobster, sf};
	private int chosenfood;
	private int healfood;
	public String foodtype = "";
	////////////////////////////HERE-STATES///////////////////////////
	int area, state = 1; //HERE CHECK THIS TODO
	final int banking = 0, walkingtospot = 1, fighting = 2, runningaway = 3;
	private boolean camefromfighting=false, ranging = false,   ooam = false;
	private int trips = 0 ;

	//////////////////////////////////////////////////////////////////////
	
	int minHP;
	boolean pickingUp=true;

	@Override
	public int loop() {
		if (ooam) {
			log("Stopping script: we ran out of arrows !");
			stopScript(true);

		}
		if(Amount <= 9) {
			Amount = 10;
		}
		if(!isLoggedIn()) {
			if(crawler !=null) {
			crawler = null;
			log("Making crawler Null!");
			return 100;
			}
			wait(4000);
			return 2000;
		}
		if (!getMyPlayer().isInCombat()){
		antiban();
		}
		if (distanceTo(ohcrap) <= 0) {
			atDoorTiles(ohcrap, safeSpot);
			wait(500);
		}
		if (distanceTo(ohcrap2) <= 0) {
			atDoorTiles(ohcrap2, safeSpot);
			wait(500);
		}
		if (getInterface(579).isValid()) {
			atInterface(getInterface(579).getChild(17), "Yes");
			return 500;
		}
		if (!isRunning() && getEnergy() > random(50, 75)) {
			setRun(true);
			wait(random(400, 800));
		}
		if (getInterface(748).getChild(8).isValid()){
		minHP =(int)(Integer.parseInt(RSInterface.getInterface(748).getChild(8).getText())/10);
		}
		switch (state) {
			case banking:
				manageBank();
				return 500;
			case walkingtospot:
				manageArea();
				return 500;
			case fighting:
				if (ranging) {
					manageRanged();
				} else {
					manageFight();
				}
				return 500;
			case runningaway:
				manageRunAway();
				return 500;
			}
		antiban();
		return (random(100, 200));
	}
	
	private int whereTheHeckAmI() {
		if (outside.contains(getMyPlayer().getLocation())) return atOutside;
		if (firstRoom.contains(getMyPlayer().getLocation())) return atFirstRoom; //first level, climb down to the flesh level
		if (almostSecondLevel.contains(getMyPlayer().getLocation())) return goSecondLevel;
		if (nearStair.contains(getMyPlayer().getLocation())) return nearStairs;
		if (firstDoors.contains(getMyPlayer().getLocation())) return atFDoors;
		if (laberint.contains(getMyPlayer().getLocation())) return atLaberint;
		if (secondDoors.contains(getMyPlayer().getLocation())) return atSDoors;
		if (afterLaberint.contains(getMyPlayer().getLocation())) return atAfterLaberint; //swapped
		if (thirdDoors.contains(getMyPlayer().getLocation())) return atTDoors;//these two are swapped because of the overlap 
		if (beforeCrawlers.contains(getMyPlayer().getLocation())) return atBeforeCrawlers; //these two are swapped because of the overlap 
		if (spot.contains(getMyPlayer().getLocation())) return atSpot;

		return -1;
	}

	public void arrowpickup() {
		if(ranging) {
			if(getMyPlayer().getInteracting() == null)  {
			RSTile tile = getNearestGroundItemByID(arrowID);
			 if(!(getInventoryCount() == 28)) {
				 if(distanceTo(tile) <= 5) {
			if(getMyPlayer().isMoving()) {
			wait(random(400,800));
			} else {
				atTile(tile, "Take " + arrowtype);
				wait(random(1000,1500));
			}
				 }
			 }
			}
		}
	}
	public void pickItem(final int itemID, final String action) {
		RSItemTile item = getNearestGroundItemByID(arrowID);

		if (item != null) {
			int itemX = item.getX();
			int itemY = item.getY();
			RSTile itemtile = new RSTile(itemX, itemY);
			if (!tileOnScreen(itemtile)) {
				turnToTile(itemtile, 5);
				if (!tileOnScreen(itemtile)) {
					walkTileOnScreen(randomizeTile(itemtile, 1, 1));
					moveMouseSlightly();
					wait(random(800, 1200));

				}
			} else {
				atTile(itemtile, action);
				if (waitToMove(random(500, 1000))) {
					if (item != null) {
						while (getMyPlayer().isMoving()) {
							wait(random(20, 30));
						}
					}

				}
			}
		}
	}

	private void manageBank() {
		if (!bank.isOpen()) {
			state = runningaway;
			return;
		}
		
		if (getInventoryCount(chosenfood) >= Amount) ;
		wait(300);
		if (getInventoryCount(chosenfood) >= Amount) {
			if (camefromfighting) {
				trips++;
				camefromfighting = false;
			}
			if (getInventoryCount(chosenfood) >= Amount) ;
			state = walkingtospot;
			return;
		}
		if (bank.getCount(chosenfood) < Amount && getInventoryCount(chosenfood) < Amount) {
			log("not enough food");
			logout();
			stopScript();
			return;
		}
		
		bank.depositAll();
		log("dumping items in bank");
		wait(400);
		bank.withdraw(chosenfood,Amount);
	}
	private void manageArea() {
		area = whereTheHeckAmI();
		state = walkingtospot;
		switch (area) {
			case atOutside:
				if (distanceTo(bankToDungeon[bankToDungeon.length -1]) > 5) {
					if (!tileOnScreen(bankToDungeon[bankToDungeon.length -1])) walk(bankToDungeon);
				}
				if (getNearestObjectByID(entranceID)!=null) {
					if (!tileOnScreen(entranceTile)) {
						walkTileMM(entranceTile,0,0);
						wait(500);
						return;
					} else {
						atObject(getNearestObjectByID(entranceID), "Climb-down");
						wait(700);
						return;
					}
				}
			case atFirstRoom:
				if (getNearestObjectByID(portalFirstLevelID)!=null) {
					if (!tileOnScreen(getNearestObjectByID(portalFirstLevelID).getLocation())) {
						walkTileMM(getNearestObjectByID(portalFirstLevelID).getLocation(),0,0);
						wait(500);
						return;
					} else {
						atObject(getNearestObjectByID(portalFirstLevelID), "Enter");
						wait(500);
						return;
					}
				}
			case goSecondLevel:
				if (getNearestObjectByID(stairToSecondLevel)!=null) {
					if (!tileOnScreen(getNearestObjectByID(stairToSecondLevel).getLocation())) {
						walkTileMM(getNearestObjectByID(stairToSecondLevel).getLocation(),0,0);
						wait(500);
						return;
					} else {
						atObject(getNearestObjectByID(stairToSecondLevel), "Climb-down");
						wait(700);
						return;
					}
				}
			case nearStairs:
				if(distanceTo(new RSTile(2044,5240)) > 2) {
					if(tileOnScreen(new RSTile(2044,5240))) {
						walkTileOnScreen(new RSTile(2044,5240)); //don't do it on MM
					} else {
						walkTileMM(new RSTile(2044,5240));
					}
					wait(500);
					return;
				}
				if(getMyPlayer().isMoving()) {
					wait(random(400,800));
					}
				if (distanceTo(Door1EnA) <= 2) {
					atDoorTiles(Door1EnA, Door1EnB);
					wait(500);
					return;
				}
			case atFDoors:
				if(distanceTo(new RSTile(2044,5238)) >2) {
					walkTileOnScreen(new RSTile(2044,5238));
					wait(500);
					return;
				}
				if(getMyPlayer().isMoving()) {
					wait(random(400,800));
					}
				if (distanceTo(Door1ExA) <= 2) {
					atDoorTiles(Door1ExA, Door1ExB);
					wait(500);
					return;
				}
				
			case atLaberint:
				if (distanceTo(laberintP[laberintP.length -1]) < 5 && distanceTo(new RSTile(2037,5204)) > 1) {
					if (tileOnScreen(new RSTile(2037,5204))){
						walkTileOnScreen(new RSTile(2037,5204));
						wait(500);
						return;
					} else {
						walkTileMM(new RSTile(2037,5204),0,0);
						wait(500);
						return;
					}
				}
				walk(laberintP);
				wait(100);
				return;
				
			case atAfterLaberint:
				if (tileOnScreen(Door2EnA) && distanceTo(Door2EnA) >1) {
					walkTileOnScreen(Door2EnA);
				} else if (!tileOnScreen(Door2EnA)) {
					walkTileMM(Door2EnA);
				}
				if(getMyPlayer().isMoving()) {
					wait(random(400,800));
					}
				if (distanceTo(new RSTile(2037,5204)) <= 1) {
						atDoorTiles(Door2EnA, Door2EnB);
						wait(750);
						return;
				}
				
			case atSDoors:
				if(distanceTo(new RSTile(2037,5201)) > 1) {
					walkTileOnScreen(new RSTile(2037,5201));
					wait(500);
					return;
				}
				if(getMyPlayer().isMoving()) {
					wait(random(400,800));
					}
				if (distanceTo(Door2ExA) <= 1) {
					atDoorTiles(Door2ExA, Door2ExB);
					wait(750);
					return;
				}
			case atBeforeCrawlers:
				if (distanceTo(new RSTile(2046,5198)) > 1 && !tileOnScreen(new RSTile(2046,5198))) {
					walkTileMM(new RSTile(2046,5198),0,0);
					wait(750);
					return;
				}
				if (distanceTo(new RSTile(2046,5198)) > 1) {
					walkTileOnScreen(new RSTile(2046,5198));
					wait(750);
					return;
				}
				if(getMyPlayer().isMoving()) {
					wait(random(400,800));
					}
				atDoorTiles(Door3EnA, Door3EnB);
				wait(500);
				return;
			case atTDoors:
				if(distanceTo(new RSTile(2045,5195)) >1) {
					walkTileOnScreen(new RSTile(2045,5195));
					wait(750);
					return;
				}
				if(getMyPlayer().isMoving()) {
					wait(random(400,800));
					}
				if (distanceTo(Door3ExA) <= 1) {
					atDoorTiles(Door3ExA,Door3ExB);
					wait(750);
					return;
				}
			case	atSpot:
				state = fighting;
			
		}
		wait(600);
		return;
	}
	private void manageFight() {
		if (getInventoryCount(chosenfood) < 1) {
			state = runningaway;
			//RUN AWAY
		}
		if (minHP <= 10) { // eat, we are dying
			//RUN AWAY
			state = runningaway;
		}
		if (whereTheHeckAmI() != atSpot && getInventoryCount(chosenfood) > 0) state = walkingtospot;
		manageEating();
		if (!camefromfighting) camefromfighting = true;
		
		if (getMyPlayer().getInteracting() == null) { // doing nothing .. go attack
			crawler = getNearestFreeNPCToAttackByID(4390);
			if (crawler == null) {
				wait (500);
				return ;
			}// no crawler what the hell
			
			if (!tileOnScreen(crawler.getLocation()) && !getMyPlayer().isMoving()){// crawler is away, go there
				walkTileMM(crawler.getLocation(),0,0);
				wait(500);
				return ;
			}
			if(crawler != null ) AttackCrawler(crawler, "Attack"); // we are near the crawler, DIE!
			wait(800);
			return;
		}
	}
	private int manageRanged() {
		if (crawler == null) {
			wait (3000);
			crawler = getNearestFreeNPCToAttackByID(4390);
			return 200;
		}
		if (getInventoryCount(chosenfood) < 1) {
			state = runningaway;
			//RUN AWAY
		}
		if (minHP <= 10) { // eat, we are dying
			//RUN AWAY
			state = runningaway;
		}
		if (whereTheHeckAmI() != atSpot && getInventoryCount(chosenfood) > 0) state = walkingtospot;
		manageEating();
		if (!camefromfighting) camefromfighting = true;
		
		if (getInventoryCount(arrowID) > 50) {
			atInventoryItem(arrowID, "Wield");
			return 200;
		}
		if (itemPresent(arrowID)&& !getMyPlayer().isInCombat()&& getMyPlayer().getInteracting()== null){
			//pickItem(arrowID, type);
			//bronzeArrow = 882, ironArrow = 884, steelArrow = 886, mithrilArrow = 888, adamantArrow = 890;
			if (arrowID == 882){
				pickItem(bronzeArrow, "Take Bronze arrow");
				return 200;}
			if (arrowID == 884){
				pickItem(ironArrow, "Take Iron arrow");
				return 200;}
			if (arrowID == 886){
				pickItem(steelArrow, "Take Steel arrow");
				return 200;}
			if (arrowID == 888){
				pickItem(mithrilArrow, "Take Mithril arrow");
				return 200;}
			if (arrowID == 890){
				pickItem(adamantArrow, "Take Adamant arrow");
				return 200;}
		}
		if (getMyPlayer().isMoving()) return 200;
		if (getMyPlayer().getInteracting()== null && !getMyPlayer().getLocation().equals(safeSpot)) {
			if (tileOnScreen(safeSpot)) {
				walkTileOnScreen(safeSpot);
			} else {
				walkTileMM(safeSpot);
			}
			return random(1500, 2500);
		}
		
		if(getMyPlayer().isInCombat() && !getMyPlayer().getLocation().equals(safeSpot)) {
			if (tileOnScreen(safeSpot)) {
				walkTileOnScreen(safeSpot);
			} else {
				walkTileMM(safeSpot);
			}
			return random(1500, 2500);
		}
		
		if (!getMyPlayer().isInCombat() && getMyPlayer().getLocation().equals(safeSpot) && getMyPlayer().getInteracting() == null && crawler != null) {
			crawler = getNearestFreeNPCToAttackByID(4390);
		}
		
		if (getMyPlayer().getLocation().equals(safeSpot) && getMyPlayer().getInteracting() == null && crawler != null) {

			if (tileOnScreen(crawler.getLocation())) AttackCrawler(crawler, "Attack");
			//isInteractingWithLocalPlayer
			//if this is in combat, will run to the spot and try to fight to another crawler
			//mb add global "interactnpc" and if its in combat (before walking back) make the attacker (.getIteracting()) the interactnp
			// and when on at safespot, check, if it's not null, raep it

		}

		return random(500, 750);
	}
	
		
	private void manageEating() {
		
		if (getInventoryCount(chosenfood) == 0) {
			state = runningaway;
			wait(500);
			return;
		}
		if (hplost() - (int)(healfood/4) > healfood ) {
			openTab(Constants.TAB_INVENTORY);
			wait(750);
			if (atInventoryItem(chosenfood, "Eat")) {
				if (getMyPlayer().getInteracting() != null) wait(1400);
			}
			wait(600);
			return;
		}
	}
	private int hplost() {
		return (skills.getRealSkillLevel(STAT_HITPOINTS) - minHP);
		}
	private void manageRunAway() {
	/*	if(Bot.disableBreakHandler == false){
		breakme = System.currentTimeMillis();
		log("Running Away Break Handaler Disabled for 30 Min's");

*/		
		state = runningaway;
		switch (whereTheHeckAmI()) {
			case atSpot:
				if (distanceTo(new RSTile(2045,5194)) > 2) {
					if (!tileOnScreen(new RSTile(2045,5194))) {
						walkTileMM(new RSTile(2045,5194));
						return;
					} else {
						walkTileOnScreen(new RSTile(2045,5194));
						return;
					}
				}
				
				if (distanceTo(getNearestObjectByID(doorID).getLocation()) <= 2) {
					atDoorTiles(Door3ExA, Door3ExB);
					return;
				}
			case atTDoors:
				if(distanceTo(new RSTile(2046,5197)) > 1) {
					walkTileOnScreen(new RSTile(2046,5197));
					return;
				}
				if (distanceTo(Door3EnA) <= 2) {
					atDoorTiles(Door3EnA, Door3EnB);
					return;
				}
			case atBeforeCrawlers:
				if (distanceTo(new RSTile(2041,5208)) > 2) {
					if (tileOnScreen(new RSTile(2041,5208))) {
						walkTileOnScreen(new RSTile(2041,5208));
					} else {
						walkTileMM(new RSTile(2041,5208));
					}
					return;
				}
				if (getNearestObjectByID(ropeID) != null) {
					atObject(getNearestObjectByID(ropeID), "Climb-up");
					wait(700);
					return;
				}
			case nearStairs:
				if (getNearestObjectByID(stairIDF2) != null) {
					atObject(getNearestObjectByID(stairIDF2), "Climb-up");
					wait(700);
					return;
				}
			//case goSecondLevel: stairs up go directly to 1 floor
			case atFirstRoom:
				if (getNearestObjectByID(exitID) != null) {
					atObject(getNearestObjectByID(exitID), "Climb-up");
					wait(700);
					return;
				}
			case atOutside:
				if (bank.isOpen()) {
					state = banking;
					wait(1000);
					return;
				}
				if (!tileOnScreen(bankToDungeon[0])) {
					walk(reversePath(bankToDungeon));
					wait(600);
					return;
				}
				if (distanceTo(bankToDungeon[0]) > 5) {
					walkTileOnScreen(bankToDungeon[0]);
					wait(800);
					return ;
				}

				if (!bank.isOpen()) {
					if(getMyPlayer().isMoving()) {
						wait(random(400,800));
						return;
						}
					log("opening bank!");
					bank.open();
					wait(random(1000, 2000));
					return;
				}else {
					state = banking;
					return;
				}
			
		}}
	 

	public void onRepaint(Graphics g) {
		final int xpGained;
		atkExp = skills.getCurrentSkillExp(Constants.STAT_ATTACK);
		strExp = skills.getCurrentSkillExp(Constants.STAT_STRENGTH);
		defExp = skills.getCurrentSkillExp(Constants.STAT_DEFENSE);
		hpExp = skills.getCurrentSkillExp(Constants.STAT_HITPOINTS);
		rangedExp = skills.getCurrentSkillExp(Constants.STAT_RANGE);
		xpGained = (atkExp - startAtkExp) + (strExp - startStrExp)
				+ (defExp - startDefExp) + (rangedExp - startRangedExp)
				+ (hpExp - startHpExp);
		atkGained = (atkExp - startAtkExp);
		strGained = (strExp - startStrExp);
		defGained = (defExp - startDefExp);
		rgeGained = (rangedExp - startRangedExp);
		hpGained = (hpExp - startHpExp);
		breaktime = System.currentTimeMillis() - breakme;
    	bseconds = breaktime / 1000;
    	if ( bseconds >= 60 ) {
    		bminutes = bseconds / 60;
    		bseconds -= (bminutes * 60);
    	}
    	if ( bminutes >= 60 ) {
    		bhours = bminutes / 60;
    		bminutes -= (bhours * 60);
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
		if (startAtkExp == 0) {
			startAtkExp = skills.getCurrentSkillExp(Constants.STAT_ATTACK);
		}
		if (startStrExp == 0) {
			startStrExp = skills.getCurrentSkillExp(Constants.STAT_STRENGTH);
		}
		if (startDefExp == 0) {
			startDefExp = skills.getCurrentSkillExp(Constants.STAT_DEFENSE);
		}
		if (startHpExp == 0) {
			startHpExp = skills.getCurrentSkillExp(Constants.STAT_HITPOINTS);
		}
		if (startRangedExp == 0) {
			startRangedExp = skills.getCurrentSkillExp(Constants.STAT_RANGE);
		}

		final int xpHour = ((int) ((3600000.0 / (double) time) * xpGained));
		float xpSec = 0;
		if ((minutes > 0 || hours > 0 || seconds > 0) && xpGained > 0) {
			xpSec = ((float) xpGained)
					/ (float) (seconds + (minutes * 60) + (hours * 60 * 60));
		}
		float xpMin = xpSec * 60;
		float xphour = xpMin * 60;
		crawlersKilled = (xpGained / 200);
		crawlersPerHour = (xpHour / 200);
		/*if(Bot.disableBreakHandler == true){
		g.drawString("Break Time: " + bhours + ":" + bminutes + ":" + bseconds, 376, 362);
		}*/
		if (getCurrentTab() == TAB_INVENTORY) {
			g.setColor(new Color(204, 0, 0,100));
			g.fillRoundRect(555, 215, 176, 242, 10, 10);
			g.setColor(new Color(0, 0, 0,100));
			g.fillRoundRect(562, 223, 162, 139, 10, 10);
			g.setColor(new Color(0, 0, 0,100));
			g.fillRoundRect(561, 395, 163, 54, 10, 10);
			g.setFont(new Font("Aharoni", 0, 11));
			g.setColor(new Color(0, 0, 0));
			g.drawString("Crawler Destroyer 1.154", 589, 373);
			g.setFont(new Font("Aharoni", 0, 11));
			g.setColor(new Color(0, 0, 0));
			g.drawString("by Vamp", 653, 385);
			g.setFont(new Font("Arial", 0, 12));
			g.setColor(new Color(255, 0, 51));
			g.drawString("Run Time: " + hours + ":" + minutes + ":" + seconds, 571, 415);
			g.setFont(new Font("Arial", 0, 12));
			g.setColor(new Color(255, 0, 51));
			g.drawString("Kills PerHour: " + Integer.toString(crawlersPerHour), 571, 425);
			g.setFont(new Font("Arial", 0, 12));
			g.setColor(new Color(255, 0, 51));
			g.drawString("Kills: " + Integer.toString(crawlersKilled), 571,435 );
			g.setFont(new Font("Arial", 0, 12));
			g.setColor(new Color(255, 0, 51));
			g.drawString("Eating: " + foodtype, 571, 232);
			g.setFont(new Font("Arial", 0, 12));
			g.setColor(new Color(255, 0, 51));
			g.drawString("Attack Type: " + style, 571, 254);
			if(attackStyle == 4){
				g.drawString("Rng Xp: " + rgeGained, 571, 275);
				g.setFont(new Font("Arial", 0, 12));
				g.setColor(new Color(255, 0, 51));
				final int percent = skills.getPercentToNextLevel(Constants.STAT_RANGE); //change the MINING to the stat u want
				final int hppercent = skills.getPercentToNextLevel(Constants.STAT_HITPOINTS);
	            g.setColor(Color.black);
	            g.fillRoundRect(571, 280, 100, 10, 15, 15); //these must be on same cordinates
	            g.setColor(Color.blue);
	            g.fillRoundRect(571, 280, percent, 10, 15, 15); //these must be on same cordinates
	            g.setColor(Color.red);
	            g.drawString("%" + percent + " Tnl", 588, 290); //this must be on the center of the bar
	            g.drawRoundRect(571, 280, 100, 10, 15, 15); //these must be on same cordinates
	            g.drawRoundRect(571, 280, percent, 10, 15, 15); //these must be on same cordinates
				g.setFont(new Font("Arial", 0, 12));
				g.setColor(new Color(255, 0, 51));
				g.drawString("Hp Xp: " + hpGained, 571, 310);
				g.setColor(Color.black);
	            g.fillRoundRect(571, 320, 100, 10, 15, 15); //these must be on same cordinates
	            g.setColor(Color.blue);
	            g.fillRoundRect(571, 320, hppercent, 10, 15, 15); //these must be on same cordinates
	            g.setColor(Color.red);
	            g.drawString("%" + hppercent + " Tnl", 588, 330); //this must be on the center of the bar
	            g.drawRoundRect(571, 320, 100, 10, 15, 15); //these must be on same cordinates
	            g.drawRoundRect(571, 320, hppercent, 10, 15, 15); //these must be on same cordinates
				g.drawString("Exp PerHour: " + Integer.toString((int) xphour), 571,
						350);
				g.drawString("Arrow Type: " + arrowtype, 554, 421);
			}
			if(attackStyle == 1){
				g.drawString("Atk Xp: " + atkGained, 571, 270);
				g.setFont(new Font("Arial", 0, 12));
				g.setColor(new Color(255, 0, 51));
				final int percent = skills.getPercentToNextLevel(Constants.STAT_ATTACK); 
				final int hppercent = skills.getPercentToNextLevel(Constants.STAT_HITPOINTS);
	            g.setColor(Color.black);
	            g.fillRoundRect(571, 280, 100, 10, 15, 15); //these must be on same cordinates
	            g.setColor(Color.blue);
	            g.fillRoundRect(571, 280, percent, 10, 15, 15); //these must be on same cordinates
	            g.setColor(Color.red);
	            g.drawString("%" + percent + " Tnl", 588, 290); //this must be on the center of the bar
	            g.drawRoundRect(571, 280, 100, 10, 15, 15); //these must be on same cordinates
	            g.drawRoundRect(571, 280, percent, 10, 15, 15); //these must be on same cordinates
				g.setFont(new Font("Arial", 0, 12));
				g.setColor(new Color(255, 0, 51));
				g.drawString("Hp Xp: " + hpGained, 571, 310);
				g.setColor(Color.black);
	            g.fillRoundRect(571, 320, 100, 10, 15, 15); //these must be on same cordinates
	            g.setColor(Color.blue);
	            g.fillRoundRect(571, 320, hppercent, 10, 15, 15); //these must be on same cordinates
	            g.setColor(Color.red);
	            g.drawString("%" + hppercent + " Tnl", 588, 330); //this must be on the center of the bar
	            g.drawRoundRect(571, 320, 100, 10, 15, 15); //these must be on same cordinates
	            g.drawRoundRect(571, 320, hppercent, 10, 15, 15); //these must be on same cordinates
				g.drawString("Exp PerHour: " + Integer.toString((int) xphour), 571,
						350);
			}
			if(attackStyle == 2){
				g.drawString("StrXp:" + strGained, 571, 270);
				g.setFont(new Font("Arial", 0, 12));
				g.setColor(new Color(255, 0, 51));
				final int percent = skills.getPercentToNextLevel(Constants.STAT_STRENGTH); 
				final int hppercent = skills.getPercentToNextLevel(Constants.STAT_HITPOINTS);
	            g.setColor(Color.black);
	            g.fillRoundRect(571, 280, 100, 10, 15, 15); //these must be on same cordinates
	            g.setColor(Color.blue);
	            g.fillRoundRect(571, 280, percent, 10, 15, 15); //these must be on same cordinates
	            g.setColor(Color.red);
	            g.drawString("%" + percent + " Tnl", 588, 290); //this must be on the center of the bar
	            g.drawRoundRect(571, 280, 100, 10, 15, 15); //these must be on same cordinates
	            g.drawRoundRect(571, 280, percent, 10, 15, 15); //these must be on same cordinates
				g.setFont(new Font("Arial", 0, 12));
				g.setColor(new Color(255, 0, 51));
				g.drawString("Hp Xp: " + hpGained, 571, 310);
				g.setColor(Color.black);
	            g.fillRoundRect(571, 320, 100, 10, 15, 15); //these must be on same cordinates
	            g.setColor(Color.blue);
	            g.fillRoundRect(571, 320, hppercent, 10, 15, 15); //these must be on same cordinates
	            g.setColor(Color.red);
	            g.drawString("%" + hppercent + " Tnl", 588, 330); //this must be on the center of the bar
	            g.drawRoundRect(571, 320, 100, 10, 15, 15); //these must be on same cordinates
	            g.drawRoundRect(571, 320, hppercent, 10, 15, 15); //these must be on same cordinates
				g.drawString("Exp PerHour: " + Integer.toString((int) xphour), 571,
						350);
			}
			if(attackStyle == 3){
				g.drawString("Def Xp:" + defGained, 571, 270);
				g.setFont(new Font("Arial", 0, 12));
				g.setColor(new Color(255, 0, 51));
				final int percent = skills.getPercentToNextLevel(Constants.STAT_DEFENSE);
				final int hppercent = skills.getPercentToNextLevel(Constants.STAT_HITPOINTS);
	            g.setColor(Color.black);
	            g.fillRoundRect(571, 280, 100, 10, 15, 15); //these must be on same cordinates
	            g.setColor(Color.blue);
	            g.fillRoundRect(571, 280, percent, 10, 15, 15); //these must be on same cordinates
	            g.setColor(Color.red);
	            g.drawString("%" + percent + " Tnl", 588, 290); //this must be on the center of the bar
	            g.drawRoundRect(571, 280, 100, 10, 15, 15); //these must be on same cordinates
	            g.drawRoundRect(571, 280, percent, 10, 15, 15); //these must be on same cordinates
				g.setFont(new Font("Arial", 0, 12));
				g.setColor(new Color(255, 0, 51));
				g.drawString("Hp Xp: " + hpGained, 571, 310);
				g.setColor(Color.black);
	            g.fillRoundRect(571, 320, 100, 10, 15, 15); //these must be on same cordinates
	            g.setColor(Color.blue);
	            g.fillRoundRect(571, 320, hppercent, 10, 15, 15); //these must be on same cordinates
	            g.setColor(Color.red);
	            g.drawString("%" + hppercent + " Tnl", 588, 330); //this must be on the center of the bar
	            g.drawRoundRect(571, 320, 100, 10, 15, 15); //these must be on same cordinates
	            g.drawRoundRect(571, 320, hppercent, 10, 15, 15); //these must be on same cordinates
				g.drawString("Exp PerHour: " + Integer.toString((int) xphour), 571,
						350);
			}
	        if(hours == 5 && minutes == 0 && seconds == 0){
	        	log("ran for 5 hours! ScreenShot!");
	        	ScreenshotUtil.takeScreenshot(true);
	        }
	      /*  if(Bot.disableBreakHandler == true){
	        if(bhours == 0 && bminutes == 30 && bseconds == 0){
	        	log("Crawlers should no longer be aggressive turning break handler on");
	        	Bot.disableBreakHandler = false;
	        }}*/
	        
	        
	        if(hours == 10 && minutes == 0 && seconds == 0){
	        	log("ran for 10 hours! ScreenShot!");
	        	ScreenshotUtil.takeScreenshot(true);
	        }


			/*
			 * 		
			 * g.drawString("StrXp", 557, 334);
		g.setFont(new Font("Arial", 0, 12));
		g.setColor(new Color(255, 0, 51));
		g.drawString("progressBar", 557, 350);
		g.setFont(new Font("Arial", 0, 12));
		g.setColor(new Color(255, 0, 51));
		g.drawString("HpXp", 557, 367);
		g.setFont(new Font("Arial", 0, 12));
		g.setColor(new Color(255, 0, 51));
		g.drawString("progressbar", 557, 382);
		g.setFont(new Font("Arial", 0, 12));
		g.setColor(new Color(255, 0, 51));
		g.drawString("StrLevel", 655, 335);
		g.setFont(new Font("Arial", 0, 12));
		g.setColor(new Color(255, 0, 51));
		g.drawString("HpLevel", 655, 367);
			 */
		}}
	/**
	 * 
	 * @param g
	 * 		graphics
	 * @param posX
	 * 		position x for the bar
	 * @param posY
	 * 		position y for the bar
	 * @param width
	 * 		width of the bar
	 * @param height
	 * 		height of the bar
	 * @param Progress
	 * 		progress variable
	 * @param color1
	 * 		primary color
	 * @param color2
	 * 		secondary color
	 * @param text
	 * 		Text color
	 */

public void ProgBar(Graphics g,int posX, int posY, int width, int height, int Progress, Color color1, Color color2, Color text){
	   
	   
	   
		int[] c1 = {color1.getRed() , color1.getGreen() , color1.getBlue() , 150};
		int[] c2 = {color2.getRed() , color2.getGreen() , color2.getBlue() , 150};
		if(c1[0]>230){c1[0]=230;}if(c1[1]>230){c1[1]=230;}if(c1[2]>230){c1[2]=230;}
		if(c2[0]>230){c2[0]=230;}if(c2[1]>230){c2[1]=230;}if(c2[2]>230){c2[2]=230;}
		
		g.setColor(new Color(c1[0],c1[1],c1[2],200));
		g.fillRoundRect(posX, posY, width, height, 5, 12);
		g.setColor(new Color(c1[0]+25,c1[1]+25,c1[2]+25,200));
		g.fillRoundRect(posX, posY, width, height/2, 5, 12);
		
		g.setColor(new Color(c2[0],c2[1],c2[2],200));
		g.fillRoundRect(posX, posY, (Progress*width)/100, height, 5, 12);
		g.setColor(new Color(c2[0]+25,c2[1]+25,c2[2]+25,150));
		g.fillRoundRect(posX, posY, (Progress*width)/100, height/2, 5, 12);
		
		g.setColor(Color.LIGHT_GRAY);
		g.drawRoundRect(posX, posY, width, height, 5, 12);
		
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, height));
		g.setColor(text);
		g.drawString(""+Progress+"%", posX+(width/6), posY+(height+height/20));
		
	}

	private String getFormattedNumber(Double p_number) { 
   NumberFormat l_nf = NumberFormat.getInstance(); 
   String  l_rVal; // Valor de retorno de la función. 

   l_nf.setMaximumFractionDigits(1);  // 2 decimales como mucho y como poco 
   l_nf.setMinimumFractionDigits(0); 

   l_rVal = new String ( l_nf.format(p_number.doubleValue())); 

   return l_rVal; 
}  



	protected int getMouseSpeed() {
		return 8;
	}
	@Override
	public boolean onStart(final Map<String, String> args) {
        URLConnection url = null;
        BufferedReader in = null;
        BufferedWriter out = null;
        //Ask the user if they'd like to check for an update...
        
            try{
                //Open the version text file
                url = new URL("http://liquidflame.webs.com/scripts/CrawlerDestroyerVERSION.txt").openConnection();
                //Create an input stream for it
                in = new BufferedReader(new InputStreamReader(url.getInputStream()));
                //Check if the current version is outdated
                if(Double.parseDouble(in.readLine()) > properties.version()) {
                    //If it is, check if the user would like to update.
                    if(JOptionPane.showConfirmDialog(null, "a new update has been found Update sctipt?") == 0){
                        //If so, allow the user to choose the file to be updated.
                           JOptionPane.showMessageDialog(null, "Please choose 'ScriptName.java' in your scripts folder and hit 'Open'");
                           JFileChooser fc = new JFileChooser();
                           //Make sure "Open" was clicked.
                           if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                               //If so, set up the URL for the .java file and set up the IO.
                               url = new URL("http://liquidflame.webs.com/scripts/CrawlerDestroyer.java").openConnection();
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
                log("Problem getting version!! oh well download server must be down... forcing script load");
                 //Return false if there was a problem
            }
        
		gui = new VampCrawlerGUI();
		if(!isLoggedIn()) {
			Status = "Logging in Gui will Appear after Login!";
			login();
			if (getInterface(906).isValid()) {
				atInterface(906, 168);
			}
			wait(random(100, 500));
		}
		wait(random(100, 500));
		gui.setVisible(true);
		Status = "Waiting on GUI Imput";
		while (guiWait) {
			wait(100);
		}
		startExp = new double[7];
		expGained = new double[7];


		for ( int i = 0; i < 7; i++ ) {
			startExp[i] = skills.getCurrentSkillExp(i);
		}
		

		startTime = System.currentTimeMillis();
		gui.setVisible(false);
		setCameraAltitude(true);
		setCompass('w');
		setAttackStyles();
		state = manageInitialState();
		crawler = getNearestNPCToAttackByID(4390);
		return true;
	}
	private int manageInitialState() {
		if (getInventoryCount(chosenfood) > 0 && whereTheHeckAmI() == atSpot) return fighting;
		if ( (whereTheHeckAmI() == atOutside && getInventoryCount(chosenfood) < 10) || getInventoryCount(chosenfood) == 0) return banking;
		if (getInventoryCount(chosenfood) >= 2) return walkingtospot;
		if (getInventoryCount(chosenfood) <= 1) return runningaway;
		return -1;
	}
	public void serverMessageRecieved( ServerMessageEvent arg0 ) {
		if (arg0.getMessage().contains("You've just") || arg0.getMessage().contains("Congratulations")) {
			log("You just advanced a level, attempting to click continue!");
			levelup++;
			wait(750);
			if (canContinue()) {
				clickContinue();
				return;
			}
		}
		if (arg0.getMessage().contains("Someone else is fighting that.")) {
			crawler = getNearestFreeNPCToAttackByID(4390);
			if (crawler != null) {
				if (tileOnScreen(crawler.getLocation())) {
					AttackCrawler(crawler, "Attack");
				} else {
					wait(100);
				}
				return;
			}
			
		}
		
		
		if (arg0.getMessage().contains("no ammo left in") || arg0.getMessage().contains("quiver")) {
			//should go to bank
			ooam = true;
			return;

		}
	}
	public void onFinish() {
	int i = 0;
			ScreenshotUtil.takeScreenshot(true);
			log ("After " + getFormattedNumber((double)(seconds / 60)) + " minutes: ");
			for ( i = 0; i < 7; i++ ) {
				if ((startExp != null) && ((skills.getCurrentSkillExp(i) - startExp[i]) > 0)) {
					log("Gained: " + expGained[i] + " exp on " + skills.statsArray[i]);
				}
			}

			
	}

	private void walk(RSTile path[]) {
		if (!getMyPlayer().isMoving() || distanceTo(getDestination()) <= random(4, 7)) walkPathMM(path, 0,0);
	}
	private boolean AttackCrawler(RSNPC npc, String action) {
        try {
            int hoverRand = random(1, 1);          
            for (int i = 0; i < hoverRand; i++) {
                Point screenLoc = npc.getScreenLocation();
                moveMouse(screenLoc, 1, 1);
                List<String> menuItems = getMenuItems();
                if (menuItems.isEmpty() || menuItems.size() <= 1) {
                    continue;
                }
                if (menuItems.get(0).toLowerCase().contains(npc.getName().toLowerCase()) && getMyPlayer().getInteracting() == null) {
                    clickMouse(true);
                    return true;
                } else {
                    for (int a = 1; a < menuItems.size(); a++) {
                        if (menuItems.get(a).toLowerCase().contains(npc.getName().toLowerCase()) && getMyPlayer().getInteracting() == null) {
                            clickMouse(false);
                            return atMenu(action);
                        }
                    }
                }
            }
        } catch (Exception e) {

            return false;
        }
        return false;
    }
	private void setAttackStyles() {
		Status = "Prefrences";
		switch (attackStyle) {
		case 1:
			openTab(Constants.TAB_ATTACK);
			wait(random(400, 600));
			clickMouse(ATTACK.x, ATTACK.y, ATTACK.width, ATTACK.height, true);
			style = "Attack";
			wait(random(400, 600));
			break;
		case 2: 
			openTab(Constants.TAB_ATTACK);
			wait(random(400, 600));
			clickMouse(STRENGTH.x, STRENGTH.y, STRENGTH.width, STRENGTH.height,
					true);
			style = "Strength";
			wait(random(400, 600));
			break;
		case 3: 
			openTab(Constants.TAB_ATTACK);
			wait(random(400, 600));
			clickMouse(DEFENCE.x, DEFENCE.y, DEFENCE.width, DEFENCE.height,
					true);
			style = "Defence";
			wait(random(400, 600));
			break;
		case 4: 
			openTab(Constants.TAB_ATTACK);
			wait(random(400, 600));
			clickMouse(RANGE.x, RANGE.y, RANGE.width, RANGE.height, true);
			wait(random(400, 600));
			style = "Range";
			ranging = true;
			break;
		}
		wait(random(600, 700));
	}
//GUI InterFace
	public class VampCrawlerGUI extends JFrame {

		private static final long serialVersionUID = 1L;

		public VampCrawlerGUI() {
			initComponents();
		}

		private void endButtonActionPerformed(ActionEvent e) {
			
			dispose();
			stopScript();
		}

		private void startButtonActionPerformed(ActionEvent e) {

			startTime = System.currentTimeMillis();
			Status = "Setting up Gui";





			if (txtFA.getText() == null || txtFA.getText().isEmpty()) {
				Amount = 10;
			if(txtFA.getText() == "Amount"){
				log("Did not select Food Amount Please Re run script");
				stopScript();
			}
			} else {
				Amount = Integer.parseInt(txtFA.getText());
			}



			if (attackbox.getSelectedItem().equals("Attack")) {
				attackStyle = 1;
			} else if (attackbox.getSelectedItem().equals("Strength")) {
				attackStyle = 2;
			} else if (attackbox.getSelectedItem().equals("Defence")) {
				attackStyle = 3;
			} else if (attackbox.getSelectedItem().equals("Range")) {
				attackStyle = 4;
			}
			//bronzeArrow = 882, ironArrow = 884, steelArrow = 886, mithrilArrow = 888, adamantArrow = 890;
			if (arrowbox.getSelectedItem().equals("Bronze Arrow")&&attackbox.getSelectedItem().equals("Range")) {
				arrowtype = "Bronze arrow";
				arrowID = bronzeArrow;
			} else if (arrowbox.getSelectedItem().equals("Iron Arrow")&&attackbox.getSelectedItem().equals("Range")) {
				arrowtype = "Iron arrow";
				arrowID = ironArrow;
			} else if (arrowbox.getSelectedItem().equals("Steel Arrow")&&attackbox.getSelectedItem().equals("Range")) {
				arrowtype = "Steel arrow";
				arrowID = steelArrow;
			} else if (arrowbox.getSelectedItem().equals("Mithril Arrow")&&attackbox.getSelectedItem().equals("Range")) {
				arrowtype = "Mithril arrow";
				arrowID = mithrilArrow;
			} else if (arrowbox.getSelectedItem().equals("Adamant Arrow")&&attackbox.getSelectedItem().equals("Range")){
				arrowtype = "Adamant arrow";
				arrowID = adamantArrow;
			}
			if (foodbox.getSelectedItem().equals("Trout")) {
				chosenfood = trout;
				healfood = 7;
				foodtype ="Trout";
			} else if (foodbox.getSelectedItem().equals("Salmon")) {
				chosenfood = salmon;
				healfood = 9;
				foodtype ="Salmon";
			} else if (foodbox.getSelectedItem().equals("Tuna")) {
				chosenfood = tuna;
				healfood = 10;
				foodtype ="Tuna";
			} else if (foodbox.getSelectedItem().equals("Lobster")) {
				chosenfood = lobster;
				healfood = 12;
				foodtype ="Lobster";
			} else if (foodbox.getSelectedItem().equals("Swordfish(Not in")) {
				chosenfood = lobster;
				healfood = 12;
				foodtype ="Lobster";
			}

			guiWait = false;
		}

		private void initComponents() {
			// GEN-BEGIN:initComponents
			attackLabel = new JLabel();
			arrowLabel = new JLabel();
			foodLabel = new JLabel();
			attackbox = new JComboBox();
			arrowbox = new JComboBox();
			foodbox = new JComboBox();


			startButton = new JButton();
			endButton = new JButton();
			label1 = new JLabel();
			label2 = new JLabel();

			setTitle("Vamp's Crawler Destroyer");
			setResizable(false);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			Container contentPane = getContentPane();
			contentPane.setLayout(null);


			attackLabel.setText("Attack Style:");
			attackLabel.setFont(new Font("Algerian", Font.PLAIN, 14));
			contentPane.add(attackLabel);
			attackLabel.setBounds(new Rectangle(new Point(25, 55), attackLabel
					.getPreferredSize()));


			arrowLabel.setText("Arrow Pickup:");
			arrowLabel.setFont(new Font("Algerian", Font.PLAIN, 14));
			contentPane.add(arrowLabel);
			arrowLabel.setBounds(new Rectangle(new Point(25, 85), arrowLabel
					.getPreferredSize()));


			foodLabel.setText("Food Options:");
			foodLabel.setFont(new Font("Algerian", Font.PLAIN, 14));
			contentPane.add(foodLabel);
			foodLabel.setBounds(new Rectangle(new Point(25, 115), foodLabel
					.getPreferredSize()));


			attackbox.setModel(new DefaultComboBoxModel(new String[] {
					"Attack", "Strength", "Defence", "Range" }));
			contentPane.add(attackbox);
			attackbox.setBounds(215, 55, 130, attackbox
					.getPreferredSize().height);


			arrowbox.setModel(new DefaultComboBoxModel(new String[] {
					"None", "Bronze Arrow", "Iron Arrow", "Steel Arrow",
					"Mithril Arrow", "Adamant Arrow" }));
			contentPane.add(arrowbox);
			arrowbox.setBounds(215, 85, 130, arrowbox
					.getPreferredSize().height);


			foodbox.setModel(new DefaultComboBoxModel(new String[] {
					"None", "Trout", "Salmon",
					"Tuna", "Lobster" }));
			contentPane.add(foodbox);
			foodbox.setBounds(215, 115, 130, foodbox
					.getPreferredSize().height);
			
			//-----Food Amount to widthdraw
			txtFA = new JTextField("Amount");
			contentPane.add(txtFA);
			txtFA.setBounds(215, 145, 130, txtFA
					.getPreferredSize().height);
			//---Food amount Label
			
			label2.setText("Food Amount:");
			label2.setFont(new Font("Algerian", Font.PLAIN, 14));
			contentPane.add(label2);
			label2.setBounds(new Rectangle(new Point(25, 145), label2
					.getPreferredSize()));
			


			// ---- startButton ----
			startButton.setText("Start Script");
			startButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					startButtonActionPerformed(e);
				}
			});
			contentPane.add(startButton);
			startButton.setBounds(50, 285, 120, 35);

			// ---- endButton ----
			endButton.setText("End Script");
			endButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					endButtonActionPerformed(e);
				}
			});
			contentPane.add(endButton);
			endButton.setBounds(215, 285, 120, 35);

			// ---- label1 ----
			label1.setText("Crawler Destroyer Controls");
			label1.setFont(new Font("Britannic Bold", Font.PLAIN, 24));
			contentPane.add(label1);
			label1.setBackground(new Color(102, 102, 102));
			label1.setBounds(new Rectangle(new Point(40, 10), label1
					.getPreferredSize()));

			{ // compute preferred size
				Dimension preferredSize = new Dimension();
				for (int i = 0; i < contentPane.getComponentCount(); i++) {
					Rectangle bounds = contentPane.getComponent(i).getBounds();
					preferredSize.width = Math.max(bounds.x + bounds.width,
							preferredSize.width);
					preferredSize.height = Math.max(bounds.y + bounds.height,
							preferredSize.height);
				}
				Insets insets = contentPane.getInsets();
				preferredSize.width += insets.right;
				preferredSize.height += insets.bottom;
				contentPane.setMinimumSize(preferredSize);
				contentPane.setPreferredSize(preferredSize);
			}
			setSize(400, 365);
			setLocationRelativeTo(getOwner());
			// GEN-END:initComponents
		}

		// GUI-BEGIN:variables
		private JLabel attackLabel;
		private JLabel arrowLabel;
		private JLabel foodLabel;
		private JComboBox attackbox;
		private JComboBox arrowbox;
		private JComboBox foodbox;
		private JButton startButton;
		private JButton endButton;
		private JLabel label1;
		private JLabel label2;
		// GUI-END:variables
	}
//GUI End InterFace
	
	//AntiBan
	private int antiban() {
		int i = random(0, 30);
		int ii = random(0, 25);
		int iii = random(0, 300);
		if (i == 2) {
			moveMouse(random(0, CanvasWrapper.getGameWidth()), random(0,
					CanvasWrapper.getGameHeight()));
			return random(0, 400);
		} else if ((i == 3) || (i == 6)){ 
			openTab(Constants.TAB_INVENTORY);
		}else if ((i == 9) || (i == 12)){
			
				openTab(Constants.TAB_STATS);	
		}else if ((i == 14)){
			openTab(Constants.TAB_QUESTS);
		} else if ((ii == 3) || (ii == 12)) {
			char dir = 37;
			if (random(0, 3) == 2) {
				dir = 39;
			}
			Bot.getInputManager().pressKey(dir);
			wait(random(500, 2000));
			Bot.getInputManager().releaseKey(dir);
			return random(0, 500);
		} else if ((i == 7) || (i == 4)) {
			return random(0, 500);
		} else if ((i == 5) || (i == 10) || (i == 11) || (i == 13) || (i == 18)
				|| (i == 27)) {
			moveMouseRandomly(random(-4, 4));
		} else if ((i == 1) || (i == 8) || (i == 15) || (i == 20)) {
			Thread camera = new Thread() {
				@Override
				public void run() {
					char dir = 37;
					if (random(0, 3) == 2) {
						dir = 39;
					}
					Bot.getInputManager().pressKey(dir);
					try {
						Thread.sleep(random(500, 2000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Bot.getInputManager().releaseKey(dir);
				}
			};
			Thread mouse = new Thread() {
				@Override
				public void run() {
					moveMouse(random(0, CanvasWrapper.getGameWidth()), random(
							0, CanvasWrapper.getGameHeight()));
				}
			};
			if ((i == 7) || (i == 20)) {
				camera.start();
			}
			if (i == 1) {
				mouse.start();
			}
			while (camera.isAlive() || mouse.isAlive()) {
				wait(random(100, 300));
				return random(300, 700);
			}
		} else if (iii == 10) {
		}
		return random(1000, 1500);
	}
}
/*	private void pickUpItems() {
	RSItemTile tile;
		for (int i = 0; i < ItemArray.length; i++) {
			while ((tile = getGroundItemByID(ItemArray[i])) != null) {
				if (!tileOnScreen(tile)) {
					break;
				}
				if (isInventoryFull()) {
					if (getInventoryCount(tile.getItem().getID()) == 0 || getInventoryItemByID(	tile.getItem().getID()).getStackSize() == 1) {
						if (getInventoryCount(chosenfood) > 0) {
							if (atInventoryItem(chosenfood, "Eat")) {
								log("Eating food to make space.");
								if (getMyPlayer().getInteracting() != null) wait(1400);
								wait(random(750, 1000));
							}
						}
					}
				}
				
				//we have space now and the item IS THERE
				Point p = Calculations.tileToScreen(tile, 0);
				if (getMouseLocation().distance(p) >20) {
					moveMouse(p);
					wait(random(750, 1000));
				}
				int qwer =0;
				while (!getMenuActions().contains("Take") && qwer <100) {
					moveMouseRandomly(1);
					qwer++;
					wait(10);
					if (getMouseLocation().distance(p) >20) {
						moveMouse(p);
						wait(random(750, 1000));
					}
				}
				for (int j=0; j < ItemNames.length; j++) { //clicks without opening menu
					if (getMenuIndex(ItemNames[j]) ==0) {
						clickMouse(true);
						wait(random(750, 1000));
						while(!getMyPlayer().isMoving()) wait(30);
						while(getMyPlayer().isMoving()) wait(30);
						return;
					}
				}
				
				if (!isMenuOpen()) {
					clickMouse(false);
					wait(random(750, 1000));
				}
				for (int j=0; j < ItemNames.length; j++) {
					if (getMenuIndex(ItemNames[j]) > -1) {
						atMenuItem(getMenuIndex(ItemNames[j]));
						wait(random(750, 1000));
						while(!getMyPlayer().isMoving()) wait(30);
						while(getMyPlayer().isMoving()) wait(30);
						return;
					}
				}
				while (getMyPlayer().isMoving() || getMyPlayer().getAnimation() != -1) {
					wait(random(750, 1000));
				}
			}
		}
	}
}






	/*This variable is used for Antialiasing. DO NOT DELETE!
	private final RenderingHints rh = new RenderingHints(
		RenderingHints.KEY_TEXT_ANTIALIASING,
		RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

	public void onRepaint(Graphics g) {
		//This paint was made using Enfilade's Paint Maker
		((Graphics2D)g).setRenderingHints(rh);
		g.setColor(new Color(0, 0, 0));
		g.fillRoundRect(549, 209, 188, 254, 4, 4);
		g.setFont(new Font("Arial", 0, 16));
		g.setColor(new Color(255, 0, 51));
		g.drawString("Crawler Destroyer V1_153", 580, 224);
		g.setFont(new Font("Arial", 0, 10));
		g.setColor(new Color(255, 0, 51));
		g.drawString("by Vamp", 684, 238);
		g.setFont(new Font("Arial", 0, 12));
		g.setColor(new Color(255, 0, 51));
		g.drawString("Run Time: " + hours + ":" + minutes + ":" + seconds, 558, 283);
		g.setFont(new Font("Arial", 0, 12));
		g.setColor(new Color(255, 0, 51));
		g.drawString("Kills PerHour: " + Integer.toString(crawlersPerHour), 558, 298);
		g.setFont(new Font("Arial", 0, 12));
		g.setColor(new Color(255, 0, 51));
		g.drawString("Kills: " + Integer.toString(crawlersKilled), 558, 313);
		g.setFont(new Font("Arial", 0, 12));
		g.setColor(new Color(255, 0, 51));
		g.drawString("Food Eating:" + chosenfood, 554, 448);
		g.setFont(new Font("Arial", 0, 12));
		g.setFont(new Font("Arial", 0, 12));
		g.setColor(new Color(255, 0, 51));
		g.drawString("AttackStyle:", 554, 435);

	}*/