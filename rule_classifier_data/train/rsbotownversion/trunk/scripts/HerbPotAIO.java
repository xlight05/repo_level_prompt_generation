import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;

import org.rsbot.script.*;
import org.rsbot.script.wrappers.*;
import org.rsbot.bot.Bot;
import org.rsbot.util.ScreenshotUtil;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.event.EventMulticaster;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.bot.input.Mouse;

//Updating Imports! Yea Updates...
import java.io.*;
import java.net.*;
import java.util.Map;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.lang.reflect.Method;




@ScriptManifest(authors = { "Fruggle" }, category = "Herblore", name = "HerbPotAIO", version = 2.3,
description =
"<html><body><center><u><h2>Fruggle's HerbPotAIO</h2></u></center>" 
		+"The script has the options to do either finish or unfinished potions"
		+"<BR><BR>" 
		+"<B>Unfinished</B> <BR>" 
		+"<select name='SelUnf' style='margin-top: 2px;'>"
		+"<option>Guam"						
		+"<option>Marrentill"				
		+"<option>Tarromin"					
		+"<option>Harralander"				
		+"<option>Ranarr"					
		+"<option>Toadflax"					
		+"<option>Toadflax & Milk"			
		+"<option>Irit Leaf"				
		+"<option>Irit Leaf & Milk"			
		+"<option>Wergali"					
		+"<option>Avantoe"					
		+"<option>Kwuarm"					
		+"<option>Snapdragon"				
		+"<option>Cadantine"				
		+"<option>Lantadyme"				
		+"<option>DwarfWeed"				
		+"<option>Torstol"					
		+"<option>Cactus spine & Milk"		
		+"<option selected=selected>None"	
		+"</select>"
		+"<BR><BR>"
		+"<B>Finished</B> <BR>"
		+"<select name='Selfin' style='margin-top: 2px;'>"
		+"<option>Attack"					
		+"<option>Antipoison"				
		+"<option>Relicym's Balm"			
		+"<option>Strength"					
		+"<option>Serum207"					
		+"<option>Stat Restore"				
		+"<option>Energy"					
		+"<option>Defence"					
		+"<option>Agility"					
		+"<option>Combat"					
		+"<option>Prayer"					
		+"<option>Super Attack"				
		+"<option>Super AntiPosion"			
		+"<option>Fishing"					
		+"<option>Super Energy"				
		+"<option>Hunter"					
		+"<option>Super Strength"			
		+"<option>Fletching"				
		+"<option>Weapon Poison"			
		+"<option>Super Restore"			
		+"<option>Super Defence"			
		+"<option>ES AntiPoison"			
		+"<option>AntiFire"					
		+"<option>Ranging"					
		+"<option>ES Weapon Poison"			
		+"<option>Magic"					
		+"<option>Zamorak"					
		+"<option>SS Anti-Poison"			
		+"<option>Saradomin's Brew"			
		+"<option>SS Weapon Poison"			
		+"<option>Recover Special Potion"	
		+"<option>ExtremeAttackPotion"		
		+"<option>ExtremeStrengthPotion"	
		+"<option>ExtremeDefencePotion"		
		+"<option>ExtremeMagicPotion"		
		+"<option selected=selected>None"	
		+"</select>"
		+"<BR><HR><BR><center><h2>OR</h2></center>Item 1: <input name='Manual1' type='text' size='10' maxlength='6' value='0' />"
		+"<BR>Item 2: <input name='Manual2' type='text' size='10' maxlength='6' value='0' /><BR>"
		+"<BR><BR>Mouse Speed: <input name='speedtype' type='text' size='10' maxlength='2' value='9' /><BR>"
		+"<BR><BR>SET WITHDRAW TO 14"
		+"</body></html>")

public class HerbPotAIO extends Script implements PaintListener, ServerMessageListener {
	//VARIABLES
	public String SelectedUnf;
	public String SelectedFin;
	public String OutofString;
	public String SoMuchString;
	public String TempString;
	private String PotName1 = "None";
	private String PotName2 = "None";
	private String CurrentStat = "None";
	private boolean StartIDBank = false;
	private boolean takeshotbollean = false;
	private boolean popups = true;
	
	
	//Set variables to intergers Misc. 
	private int amountFinished = 0;
	private int amountUnfinished = 0;


	public int TempTotalPotions = 0;
	public int missedBanks = 0;
	public int countMissedItem = 0;
	public int randomPick = 0;
	public boolean crappedout = true;
	public boolean DidWeSwap = false;
	public boolean TempBoolean = true;
	public boolean PotBoolean = true;

	//private int AMOUNT = 0; //Wait I'll get it up soon!
	
	//Precent bar
	private double valntnxtLevel = 0;
	private double precentntnxtLevel = 0;
	private double bartntnxtLevel = 0;
	private long barlenght = 0;
	private int barlenghtint = 0;
	
	//Paint Math Area
	private int ExpGained;
	private int LevGained;
	private int MadGained;
	private int ExpHour;
	private int LevHour;
	private int MadHour;
		//BoxMath
		private int xStaVal = 0;
		private int xEndVal = 0;
		private int yStaVal = 0;
		private int yEndVal = 0;
		
		private int xDisVal = 0;
		private int yDisVal = 0;
		
		//Arrow Math
		private int xStaArrow = 0;
		private int yStaArrow = 0;
		private int xEndArrow = 0;
		private int yEndArrow = 0;

		private int xDisArrow = 0;
		private int yDisArrow = 0;
		
		//DebugMath
		private int xStaDebug;
		private int xEndDebug;
		private int yStaDebug;
		private int yendDebug;
		
		//Color
		private int redme = random(20, 230);
		private boolean redmeBoolean = true;
		
		private int greenme = random(20, 230);
		private boolean greenmeBoolean = false;
		
		private int blueme = random(20, 230);
		private boolean bluemeBoolean = true;
	
	//Set Item IDs to 0
	private int vialID = 227; //227, "Vial of water"
	private int herbID = 0;
	private int unfiID = 0;
	private int doneID = 0;
	private int itemID = 0;
	private int Man1ID = 0;
	private int Man2ID = 0;
	private int speedme = 9;
	
	private int FirstID = 0;
	private int SeconID = 0;
	private int EnditID = 0;
	
	//setting up RS banking identies
	private int BANKBOOTH[] = { 11758, 11402, 34752, 35647, 2213, 25808, 2213, 26972, 27663, 4483, 14367, 19230, 29085, 12759, 6084 };
	private int CHEST[] = { 27663, 4483, 12308, 21301, 42192 };
	private int BANKER[] = { 7605, 6532, 6533, 6534, 6535, 5913, 5912, 2271, 14367, 3824, 44, 45, 2354, 2355, 499, 5488, 8948, 958, 494, 495, 6362, 5901 };
	
	//Money Variables
	private int CalcUnfinished;
	private int CalcFinished;
	private int CalcUnfinishedHour;
	private int vialIDPrice;
	private int herbIDPrice;
	private int unfiIDPrice;
	private int itemIDPrice;
	private int doneIDPrice;
	
	//Make All Pointer Variables!
	private int MakeMouseX;
	private int MakeMouseY;

	private int MakeMouseXDif;
	private int MakeMouseYDif;	
			
	//RS Variables
	//private RSInterface INTERFACE_POTOION = RSInterface.getInterface(513);
	private RSInterfaceChild POTOION_AREA = RSInterface.getChildInterface(513, 3);
	
	private RSInterface INTERFACE_TEXT = RSInterface.getInterface(752);
	private RSInterfaceChild TEXT_AREA = RSInterface.getChildInterface(752, 3);
	
	//RS banking location retrival
	private RSObject bankBooth = getNearestObjectByID(BANKBOOTH);
	private RSObject bankChest = getNearestObjectByID(CHEST);
	private RSNPC banker = getNearestNPCByID(BANKER);
	
	//PAINT VARIABLES
	public long startTime = System.currentTimeMillis();
	public long meuper = System.currentTimeMillis();
	private String CusHours;
	private String CusMins;
	private String CusSec;

	private long waitTimer;
	private long DynamicwaitTimer = 0;
	private long fixedwaitTimer;
	private int startXP = skills.getCurrentSkillExp(STAT_HERBLORE);
	private int startLevel = skills.getCurrentSkillLevel(STAT_HERBLORE);
	
	private double getVersion() {
		return getClass().getAnnotation(ScriptManifest.class).version();
	}


	public boolean onStart(Map<String, String> args) {
		URLConnection url;
		BufferedReader in;
		//if (JOptionPane.showConfirmDialog( null,"Would you like to check for updates?\nPlease Note this requires an internet connection!") == 0) {
			try {
				url = new URL("http://www.members.cox.net/junk2913/HerbPotUpdate.txt").openConnection();
				in = new BufferedReader(new InputStreamReader(url.getInputStream()));
				if (Double.parseDouble(in.readLine()) > getVersion()) {
					JOptionPane.showMessageDialog(null, "Script is outdated. reupdate the script from the thread!!");
					openURL("http://www.powerbot.org/vb/showthread.php?t=325510");
					crappedout = false;
				} else {
					log.severe("Kick ass you are up to date!");
				}
				if (in != null) {
					in.close();
				}
			} catch (final IOException e) { 
				JOptionPane.showMessageDialog(null, "Script/link is broken. Reupdate the script from the thread!!");
				crappedout = false;
				openURL("http://www.powerbot.org/vb/showthread.php?t=325510");
			}
		//}
			try {
				url = new URL("http://www.members.cox.net/junk2913/HerbPotpop.txt").openConnection();
				in = new BufferedReader(new InputStreamReader(url.getInputStream()));
				if (Double.parseDouble(in.readLine()) == 1) {
					log.severe("Pop Ups Enabled");
					popups = true;
				} else {
					log.severe("Pop Ups Disabled");
					popups = false;
				}
				if (in != null) {
					in.close();
				}
			} catch (final IOException e) { 
				log.severe("Pop Ups Enabled");
				popups = true;
			}
		
		
		log("Going to get ready to render information one second...");
		wait(random(200, 500));
		//DECLARING VARIABLES
		startTime = System.currentTimeMillis();
		waitTimer = System.currentTimeMillis();
				
		//Convert selections to strings
		SelectedUnf = args.get("SelUnf");
		SelectedFin = args.get("Selfin");
		
		//Convert selections to integers		
		Man1ID = Integer.parseInt(args.get("Manual1"));
		Man2ID = Integer.parseInt(args.get("Manual2"));
		
		speedme = Integer.parseInt(args.get("speedtype"));
		
		///////////////////

		///////////////////
		
		if (args.get("SelUnf").equals("Guam")) {
			herbID = 249; //249, "Clean guam"
			unfiID = 91; //91, "Guam potion (unf)"
			PotName1 = "Guam";
			log("We're going to be making Guam potion.");
			
		} else if (args.get("SelUnf").equals("Marrentill")) {
			herbID = 251; //251, "Clean marrentill"
			unfiID = 93; //93, "Marrentill potion (unf)"
			PotName1 = "Marrentill";
			log("We're going to be making Marrentill potion.");
			
		} else if (args.get("SelUnf").equals("Tarromin")) {
			herbID = 253; //253, "Clean tarromin"
			unfiID = 95; //95, "Tarromin potion (unf)"
			PotName1 = "Tarromin";
			log("We're going to be making Tarromin potion.");
			
		} else if (args.get("SelUnf").equals("Harralander")) {
			herbID = 255; //255, "Clean harralander"
			unfiID = 97; //97, "Harralander potion (unf)"
			PotName1 = "Harralander";
			log("We're going to be making Harralander potion.");
			
		} else if (args.get("SelUnf").equals("Ranarr")) {
			herbID = 257; //257, "Clean ranarr"
			unfiID = 99; //99, "Ranarr potion (unf)"
			PotName1 = "Ranarr";
			log("We're going to be making Ranarr potion.");
			
		} else if (args.get("SelUnf").equals("Toadflax")) {
			herbID = 2998; //2998, "Clean toadflax"
			unfiID = 3002; //3002, "Toadflax potion (unf)"
			PotName1 = "Toadflax";
			log("We're going to be making Toadflax potion.");
			
		} else if (args.get("SelUnf").equals("Toadflax & Milk")) {
			vialID = 5935; //5935, "Coconut milk"
			herbID = 2998; //2998, "Clean toadflax"
			unfiID = 3002; //3002, "Toadflax potion (unf)"
			PotName1 = "Toadflax";
			log("We're going to be making Toadflax potion.");
			
		} else if (args.get("SelUnf").equals("Irit Leaf")) {
			herbID = 259; //259, "Clean irit
			unfiID = 101; //101, "Irit potion (unf)"
			PotName1 = "Irit";
			log("We're going to be making Irit Leaf potion.");
			
		} else if (args.get("SelUnf").equals("Irit Leaf & Milk")) {
			vialID = 5935; //5935, "Coconut milk"
			herbID = 259; //259, "Clean irit
			unfiID = 101; //101, "Irit potion (unf)"
			PotName1 = "Irit & Milk";
			log("We're going to be making Irit Leaf potion.");
			
		} else if (args.get("SelUnf").equals("Wergali")) {
			herbID = 14854; //14854, "Clean wergali"
			unfiID = 14856; //14856, "Wergali potion (unf)"
			PotName1 = "Wergali";
			log("We're going to be making Wergali potion.");
			
		} else if (args.get("SelUnf").equals("Avantoe")) {
			herbID = 261; //261, "Clean avantoe"
			unfiID = 103; //103, "Avantoe potion (unf)"
			PotName1 = "Avantoe";
			log("We're going to be making Avantoe potion.");
			
		} else if (args.get("SelUnf").equals("Kwuarm")) {
			herbID = 263; //263, "Clean kwuarm"
			unfiID = 105; //105, "Kwuarm potion (unf)"
			PotName1 = "Kwuarm";
			log("We're going to be making Kwuarm potion.");
			
		} else if (args.get("SelUnf").equals("Snapdragon")) {
			herbID = 3000; //3000, "Clean snapdragon"
			unfiID = 3004; //3004, "Snapdragon potion (unf)"
			PotName1 = "Snapdragon";
			log("We're going to be making Snapdragon potion.");
			
		} else if (args.get("SelUnf").equals("Cadantine")) {
			herbID = 265; //265, "Clean cadantine"
			unfiID = 107; //107, "Cadantine potion (unf)"
			PotName1 = "Cadantine";
			log("We're going to be making Cadantine potion.");
			
		} else if (args.get("SelUnf").equals("Lantadyme")) {
			herbID = 2481; //2481, "Clean lantadyme"
			unfiID = 2483; //2483, "Lantadyme potion (unf)"
			PotName1 = "Lantadyme";
			log("We're going to be making Lantadyme potion.");
			
		} else if (args.get("SelUnf").equals("DwarfWeed")) {
			herbID = 267; //267, "Clean dwarf weed"
			unfiID = 109; //109, "Dwarf weed potion (unf)"
			PotName1 = "DwarfWeed";
			log("We're going to be making DwarfWeed potion.");
			
		} else if (args.get("SelUnf").equals("Torstol")) {
			herbID = 269; //269, "Clean torstol"
			unfiID = 111; //111, "Torstol potion (unf)"
			PotName1 = "Torstol";
			log("We're going to be making Torstol potion.");
			
		} else if (args.get("SelUnf").equals("Cactus spine & Milk")) {
			vialID = 5935; //5935, "Coconut milk"
			herbID = 6016; //6016, "Cactus spine"
			unfiID = 0000; //111, "Torstol potion (unf)"
			PotName1 = "Torstol";
			log("We're going to be making Torstol potion.");
			log("No big deal but missing UnfID... post Please");
		}
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		if (args.get("Selfin").equals("Attack")) {
			unfiID = 91; //91, "Guam potion (unf)"
			itemID = 221; //221, "Eye of newt"
			doneID = 121; //121, "Attack potion (3)"
			PotName2 = "Attack";
			log("We're going to be making Attack potion.");
			
		} else if (args.get("Selfin").equals("Antipoison")) {
			unfiID = 93; //93, "Marrentill potion (unf)"
			itemID = 235; //235, "Unicorn horn dust"
			doneID = 175; //175, "Antipoison (3)"
			PotName2 = "Antipoison";
			log("We're going to be making Antipoison potion.");
			
		} else if (args.get("Selfin").equals("Relicym's Balm")) {
			unfiID = 4840; //4840, "Rogue's purse potion (unf)"
			itemID = 1526; //1526, "Snake weed"
			doneID = 4844; //4844, "Relicym's balm (3)"
			PotName2 = "Relicym's balm";
			log("We're going to be making Relicym's balm.");
			
		} else if (args.get("Selfin").equals("Strength")) {
			unfiID = 95; //95, "Tarromin potion (unf)"
			itemID = 225; //225, "Limpwurt root"
			doneID = 115; //115, "Strength potion (3)"
			PotName2 = "Strength";
			log("We're going to be making Strength potion.");
			
		} else if (args.get("Selfin").equals("Serum207")) {
			unfiID = 95; //95, "Tarromin potion (unf)"
			itemID = 592; //592, "Ashes"
			doneID = 3410; //3410, "Serum 207 (3)"
			PotName2 = "Serum207";
			log("We're going to be making Serum207.");
			
		} else if (args.get("Selfin").equals("Stat Restore")) {
			unfiID = 97; //97, "Harralander potion (unf)"
			itemID = 223; //223, "Red spiders' eggs"
			doneID = 127; //127, "Restore potion (3)"
			PotName2 = "Stat Restore";
			log("We're going to be making Stat Restore potion.");
			
		} else if (args.get("Selfin").equals("Energy")) {
			unfiID = 97; //97, "Harralander potion (unf)"
			itemID = 1975; //1975, "Chocolate dust"
			doneID = 3010; //3010, "Energy potion (3)"
			PotName2 = "Energy";
			log("We're going to be making Energy potion.");
			
		} else if (args.get("Selfin").equals("Defence")) {
			unfiID = 99; //99, "Ranarr potion (unf)"
			itemID = 239; //239, "White berries"
			doneID = 133; //133, "Defence potion (3)"
			PotName2 = "Defence";
			log("We're going to be making Defence potion.");
			
		} else if (args.get("Selfin").equals("Agility")) {
			unfiID = 3002; //3002, "Toadflax potion (unf)"
			itemID = 1975; //897, "Toad Legs"
			doneID = 3034; //3034, "Agility potion (3)"
			PotName2 = "Agility";
			log("We're going to be making Agility potion.");
			
		} else if (args.get("Selfin").equals("Combat")) {
			unfiID = 97; //97, "Harralander potion (unf)"
			itemID = 9736; //9736, "Goat horn dust"
			doneID = 9741; //9741, "Combat potion (3)"
			PotName2 = "Combat";
			log("We're going to be making Combat potion.");
			
		} else if (args.get("Selfin").equals("Prayer")) {
			unfiID = 99; //99, "Ranarr potion (unf)"
			itemID = 231; //231, "Snape grass"
			doneID = 139; //139, "Prayer potion (3)"
			PotName2 = "Prayer";
			log("We're going to be making Prayer potion.");
			
		} else if (args.get("Selfin").equals("Super Attack")) {
			unfiID = 101; //101, "Irit potion (unf)"
			itemID = 221; //221, "Eye of newt"
			doneID = 145; //145, "Super attack (3)"
			PotName2 = "Super Attack";
			log("We're going to be making Super Attack potion.");
			
		} else if (args.get("Selfin").equals("Super AntiPosion")) {
			unfiID = 101; //101, "Irit potion (unf)"
			itemID = 235; //235, "Unicorn horn dust"
			doneID = 181; //181, "Super antipoison (3)"
			PotName2 = "Super AntiPosion";
			log("We're going to be making Super AntiPosion potion.");
			
		} else if (args.get("Selfin").equals("Fishing")) {
			unfiID = 103; //103, "Avantoe potion (unf)"
			itemID = 231; //231, "Snape grass"
			doneID = 151; //151, "Fishing potion (3)"
			PotName2 = "Fishing";
			log("We're going to be making Fishing potion.");
			
		} else if (args.get("Selfin").equals("Super Energy")) {
			unfiID = 103; //103, "Avantoe potion (unf)"
			itemID = 2970; //2970, "Mort myre fungus"
			doneID = 3018; //3018, "Super energy (3)"
			PotName2 = "Super Energy";
			log("We're going to be making Super Energy potion.");
			
		} else if (args.get("Selfin").equals("Hunter")) {
			unfiID = 103; //103, "Avantoe potion (unf)"
			itemID = 10109; //10109, "Kebbit teeth"
			doneID = 10000; //10000, "Hunter potion (3)"
			PotName2 = "Hunter";
			log("We're going to be making Hunter potion.");
			
		} else if (args.get("Selfin").equals("Super Strength")) {
			unfiID = 105; //105, "Kwuarm potion (unf)"
			itemID = 225; //225, "Limpwurt root"
			doneID = 157; //157, "Super strength (3)"
			PotName2 = "Super Strength";
			log("We're going to be making Super Strength potion.");
			
		} else if (args.get("Selfin").equals("Fletching")) {
			unfiID = 14856; //14856, "Wergali potion (unf)"
			itemID = 11525; //11525, "Wimpy feather"
			doneID = 14848; //14848, "Fletching potion (3)"
			PotName2 = "Fletching";
			log("We're going to be making Fletching potion.");
			
		} else if (args.get("Selfin").equals("Weapon Poison")) {
			unfiID = 105; //105, "Kwuarm potion (unf)"
			itemID = 241; //241, "Dragon scale dust"
			doneID = 187; //187, "Weapon poison"
			PotName2 = "Weapon";
			log("We're going to be making Weapon Posion. (Ouch)...");
			
		} else if (args.get("Selfin").equals("Super Restore")) {
			unfiID = 3004; //3004, "Snapdragon potion (unf)"
			itemID = 223; //223, "Red spiders' eggs"
			doneID = 3026; //3026, "Super restore (3)"
			PotName2 = "Super Restore";
			log("We're going to be making Super Restore potion.");
			
		} else if (args.get("Selfin").equals("Super Defence")) {
			unfiID = 107; //107, "Cadantine potion (unf)"
			itemID = 239; //239, "White berries"
			doneID = 163; //163, "Super defence (3)"
			PotName2 = "Super Defence";
			log("We're going to be making Super Defence potion.");
			
		} else if (args.get("Selfin").equals("ES AntiPoison")) {
			unfiID = 3002; //3002, "Toadflax potion (unf)"
			itemID = 6049; //6049, "Yew roots"
			doneID = 181; //181, "Super antipoison (3)"
			PotName2 = "Ex-Str AntiPoison";
			log("We're going to be making Extra-Strong AntiPoison.");
			
		} else if (args.get("Selfin").equals("AntiFire")) {
			unfiID = 2483; //2483, "Lantadyme potion (unf)"
			itemID = 241; //241, "Dragon scale dust"
			doneID = 2454; //2454, "Antifire (3)"
			PotName2 = "AntiFire";
			log("We're going to be making AntiFire potion.");
			
		} else if (args.get("Selfin").equals("Ranging")) {
			unfiID = 109; //109, "Dwarf weed potion (unf)"
			itemID = 245; //245, "Wine of zamorak"
			doneID = 169; //169, "Ranging potion (3)"
			PotName2 = "Ranging";
			log("We're going to be making Ranging potion.");
			
		} else if (args.get("Selfin").equals("ES Weapon Poison")) {
			unfiID = 0000; //
			itemID = 223; //223, "Red spiders' eggs"
			doneID = 5940; //5940, "Weapon poison++"
			PotName2 = "Ex-Str Weapon Poison";
			log("We're going to be making Extra Strength Weapon Poison.");
			log("We are missing unfid... Post Item ID plase");
			
		} else if (args.get("Selfin").equals("Magic")) {
			unfiID = 2483; //2483, "Lantadyme potion (unf)"
			itemID = 3138; //3138, "Potato cactus"
			doneID = 3042; //3042, "Magic potion (3)"
			PotName2 = "Magic";
			log("We're going to be making Lantadyme mix.");
			
		} else if (args.get("Selfin").equals("Zamorak")) {
			unfiID = 111; //111, "Torstol potion (unf)"
			itemID = 247; //247, "Jangerberries"
			doneID = 189; //189, "Zamorak brew (3)"
			PotName2 = "Zamorak";
			log("We're going to be making Zamorak potion.");
			
		} else if (args.get("Selfin").equals("SS Anti-Poison")) {
			unfiID = 101; //101, "Irit potion (unf)"
			itemID = 6051; //6051, "Magic roots"
			doneID = 5954; //5954, "Antipoison++ (3)"
			PotName2 = "SS Anti-Poison";
			log("We're going to be making SS Anti-Poison.");
			
		} else if (args.get("Selfin").equals("Saradomin's Brew")) {
			unfiID = 3002; //3002, "Toadflax potion (unf)"
			itemID = 6693; //6693, "Crushed nest"
			doneID = 6687; //6687, "Saradomin brew (3)"
			PotName2 = "Saradomin's Brew";
			log("We're going to be making Saradomin's Brew.");
			
		} else if (args.get("Selfin").equals("SS Weapon Poison")) {
			vialID = 0000; //0000, "____"
			herbID = 0000; //0000, "____"
			unfiID = 0000; //0000, "____"
			itemID = 0000; //0000, "____"
			doneID = 0000; //0000, "____"
			PotName2 = "SS Weapon Poison";
			log("We're going to be making SS Weapon Poison.");
			log("We are missing everything... Post Item IDs plase...");
			
		} else if (args.get("Selfin").equals("Recover Special Potion")) {
			unfiID = 3018; //3018, "Super energy(3)"
			itemID = 5972; //5972, "Papaya fruit"
			doneID = 15301; //15301, "Recover Special Potion"
			DynamicwaitTimer = 10000;
			PotName2 = "Recover Special Potion";
			log("We're going to be making Recover Special Potion.");
			log("Thanks to Kalvinc10... Posted the Raw Items!");
			log("Thanks to Clover87... Posted the Finished Item ID!");
			
		} else if (args.get("Selfin").equals("ExtremeAttackPotion")) {
			unfiID = 145; //145, "Super attack (3)"
			itemID = 261; //261, "Clean avantoe"
			doneID = 0000; //unknown
			PotName2 = "ExtremeAttackPotion";
			log("We're going to be making ExtremeAttackPotion mix.");
			log("We are missing Finial Potion... Post Item ID plase...");
			
		} else if (args.get("Selfin").equals("ExtremeStrengthPotion")) {
			unfiID = 157; //157, "Super strength (3)"
			itemID = 267; //267, "Clean dwarf weed"
			doneID = 0000; //unknown
			PotName2 = "ExtremeStrengthPotion";
			log("We're going to be making ExtremeStrengthPotion mix.");
			log("We are missing Finial Potion... Post Item ID plase...");
			
		} else if (args.get("Selfin").equals("ExtremeDefencePotion")) {
			unfiID = 163; //163, "Super defence (3)"
			itemID = 2481; //2481, "Clean lantadyme"
			doneID = 0000; //unknown
			PotName2 = "ExtremeDefencePotion";
			log("We're going to be making ExtremeDefencePotion mix.");
			log("We are missing Finial Potion... Post Item ID plase...");
			
		} else if (args.get("Selfin").equals("ExtremeMagicPotion")) {
			unfiID = 3042; //3042, "Magic potion (3)"
			itemID = 9594; //9594, "Ground mud runes"
			doneID = 0000; //
			PotName2 = "ExtremeMagicPotion";
			log("We're going to be making ExtremeMagicPotion mix.");
			log("We are missing Finial Potion... Post Item ID plase...");
		}
		
		
		
		///////////////////

		///////////////////
		
		setCameraAltitude(true);
		wait(500);
		log("Your starting level is: " + skills.getRealSkillLevel(STAT_HERBLORE));
		wait(500);
		setCameraAltitude(true);
		CurrentStat = "Setting Information";
		
		if (!args.get("SelUnf").equals("None")) {
			FirstID = vialID;
			SeconID = herbID;
			EnditID = unfiID;
			if (DynamicwaitTimer == 0) {
				fixedwaitTimer = 10000;
			} else {
				fixedwaitTimer = DynamicwaitTimer;
			}
			wait(200);
			log("Set for Unfinished Potions!");
		} else if(!args.get("Selfin").equals("None")) {
			FirstID = unfiID;
			SeconID = itemID;
			EnditID = doneID;
			if (DynamicwaitTimer == 0) {
				fixedwaitTimer = 10000;
				log("Generic Timer set.");
			} else {
				fixedwaitTimer = DynamicwaitTimer;
				log("Advanced Timer set.");
			}
			wait(200);
			log("Set for Finished Potions!");
		} else {
			FirstID = Man1ID;
			SeconID = Man2ID;
			if (DynamicwaitTimer == 0) {
				fixedwaitTimer = 10000;
				log("Generic Timer set.");
			} else {
				fixedwaitTimer = DynamicwaitTimer;
				log("Advanced Timer set.");
			}
			PotName2 = "Fixed Potion ID's";
			wait(200);
			log.severe("Set for Manual Potions!");
			DidWeSwap = true;
		}
		
		log.severe("Item ID Set");
		wait(200);
		log("Fisrt Item ID: " + FirstID);
		log("Second Item ID: " + SeconID);
		wait(random(300,500));
		return true;
	}
	
	
	//Mouse Speed Settings
	@Override
	public int getMouseSpeed(){
		return random(speedme, speedme+1);
	}
	
	public void openURL(final String url) { // Credits to Dave who gave credits
		// to
		// some guy who made this.
		final String osName = System.getProperty("os.name");
		try {
			if (osName.startsWith("Mac OS")) {
				final Class<?> fileMgr = Class
						.forName("com.apple.eio.FileManager");
				final Method openURL = fileMgr.getDeclaredMethod("openURL",
						new Class[]{String.class});
				openURL.invoke(null, new Object[]{url});
			} else if (osName.startsWith("Windows")) {
				Runtime.getRuntime().exec(
						"rundll32 url.dll,FileProtocolHandler " + url);
			} else { // assume Unix or Linux
				final String[] browsers = {"firefox", "opera", "konqueror",
						"epiphany", "mozilla", "netscape"};
				String browser = null;
				for (int count = 0; count < browsers.length && browser == null; count++) {
					if (Runtime.getRuntime().exec(
							new String[]{"which", browsers[count]})
							.waitFor() == 0) {
						browser = browsers[count];
					}
				}
				if (browser == null) {
					throw new Exception("Could not find web browser");
				} else {
					Runtime.getRuntime().exec(new String[]{browser, url});
				}
			}
		} catch (final Exception e) {
		}
	}


///////////////////////////////////////////////////////////////////////////////
	public void defineRandomNum() {
		randomPick = (int) (random(0, 2) + 1);
	}
	
	private void getPrices() {
		vialIDPrice = grandExchange.loadItemInfo(vialID).getMarketPrice();
		herbIDPrice = grandExchange.loadItemInfo(herbID).getMarketPrice();
		unfiIDPrice = grandExchange.loadItemInfo(unfiID).getMarketPrice();
		itemIDPrice = grandExchange.loadItemInfo(itemID).getMarketPrice();
		doneIDPrice = grandExchange.loadItemInfo(doneID).getMarketPrice();
	}


	public void openBank() {
		if(bankBooth != null && !bank.isOpen()) {
			CurrentStat = "Opening Bankbooth";
			atObject(bankBooth, "Use-quickly ");
			wait(random(1000, 1200));
		} 
		if (bankChest != null && !bank.isOpen()) {
			CurrentStat = "Opening Chest";
			atObject(bankChest, "Use ");
			wait(random(1000, 1200));
		}
		if (banker != null && !bank.isOpen()) {
			CurrentStat = "Using Banker";
			atNPC(banker, "Bank ");
			wait(random(1000, 1200));
		}
	}
	
	public void EmptyInventory() {
		CurrentStat = "Depositting";
		bank.depositAll();
		wait(random(1000, 1200));
		countMissedItem = 0;
	}
	
	public void TextTo14Withdraw() {
		CurrentStat = "Setting Withdraw 14";
		defineRandomNum();
		wait(random(400, 800));
		// Little extra antiban
		if (randomPick == 1) {
			bank.atItem(FirstID, "raw-X");
		} else {
			bank.atItem(SeconID, "raw-X");
		}
		wait(random(2400, 2800));
		sendText("14", true);
		wait(random(1200, 1400));
		getPrices();
		StartIDBank = true;
	}
	
	public void OutofItems() {
			log("You are out... of the " + OutofString + "!");
			bank.depositAll();
			bank.close();
			moveMouse(755, 13);
			wait(random(700, 800));
			atMenu("Exit");
			moveMouse(755, 13);
			moveMouse(random(575, 699), random(408, 427));
			wait(random(700, 800));
			atMenu("Exit to Login");
			wait(random(2000,3000));
			stopScript(false);
	}
	
	public void ToManyItems() {
		log("You" + SoMuchString + "!");
		wait(random(1200, 1400));
		EmptyInventory();
		missedBanks++;
	}
	
	public void TimeToSwap() {
			FirstID = unfiID;
			SeconID = itemID;
			EnditID = doneID;
			bank.depositAll();
			if (DynamicwaitTimer == 0) {
				fixedwaitTimer = 22500;
				log("Generic Timer set.");
			} else {
				fixedwaitTimer = DynamicwaitTimer;
				log("Advanced Timer set.");
			}
			wait(500);
			log("Set for Finished Potions!");
			DidWeSwap = true;
			log("Thanks Cruor for the advice...");
	}
	
	public void ItemClickItem() {
		defineRandomNum();
		if (randomPick == 1) {
			TempString = "First";
			atInventoryItem(FirstID, "Use");
		} else {
			TempString = "Second";
			atInventoryItem(SeconID, "Use");
		}
		CurrentStat = TempString + " Selected Object";
		wait(random(300, 500));
		if (isItemSelected()) {
			CurrentStat = "Using " + TempString + " on other object";
			waitTimer = System.currentTimeMillis();
			if (randomPick == 1) {
				atInventoryItem(SeconID, "Use");
			} else {
				atInventoryItem(FirstID, "Use");
			}
			wait(random(500, 800));
			waitTimer = System.currentTimeMillis();
		} else {
			moveMouse(random(650, 660), random(180, 190));
			clickMouse(true);
		}
	}
	
	
//	public void ItemClickItem() {
//		defineRandomNum();
//		if (randomPick == 1) {
//			TempString = "First";
//			atInventoryItem(FirstID, "Use");
//			TempBoolean = true;
//		} else {
//			TempString = "Second";
//			atInventoryItem(SeconID, "Use");
//			TempBoolean = false;
//		}
//		CurrentStat = TempString + " Selected Object";
//		wait(random(300, 500));
//		if (isItemSelected()) {
//			CurrentStat = "Using " + TempString + " on other object";
//			waitTimer = System.currentTimeMillis();
//			if (TempBoolean == true) {
//				atInventoryItem(SeconID, "Use");
//			} else {
//				atInventoryItem(FirstID, "Use");
//			}
//			wait(random(500, 800));
//			waitTimer = System.currentTimeMillis();
//		} else {
//			moveMouse(random(650, 660), random(180, 190));
//			clickMouse(true);
//		}
//	}
	
	public void ItemMakeAll() {
		MakeMouseX = random(280,250);
		MakeMouseY = random(400,464);
		MakeMouseXDif = 10;
		MakeMouseYDif = 20;
		
		CurrentStat = "Going to make all";
		moveMouse(MakeMouseX, MakeMouseY, 0, 0);
		wait(random(400, 600));
		clickMouse(false);
		wait(random(400, 600));
		moveMouse(MakeMouseX+random(-40,40), 465, 0, 4);
		clickMouse(true);
		wait(random(500, 800));
		CurrentStat = "Compleated make all";
	}
	
//	public void ItemMakeAll() {
//		CurrentStat = "Going to make all";
//		moveMouse(244, 394, 10, 20);
//		wait(random(400, 600));
//		atMenu("Make All");
//		wait(random(500, 800));
//		CurrentStat = "Compleated make all";
//	}
	
	public void colorme() {
		//Color Red
		if (redme > 245) { redmeBoolean = false; }
		if (redme < 15) { redmeBoolean = true; }
		
		if (redmeBoolean == true) { redme = redme + random(0, 3); }
		if (redmeBoolean == false) { redme = redme - random(0, 5); }
		
		//Color Green
		if (greenme > 245) { greenmeBoolean = false; }
		if (greenme < 15) { greenmeBoolean = true; }
		
		if (greenmeBoolean == true) { greenme = greenme + random(0, 4); }
		if (greenmeBoolean == false) { greenme = greenme - random(0, 2); }
		
		//Color Blue
		if (blueme > 245) { bluemeBoolean = false; }
		if (blueme < 15) { bluemeBoolean = true; }
		
		if (bluemeBoolean == true) { blueme = blueme + random(0, 7); }
		if (bluemeBoolean == false) { blueme = blueme - random(0, 4); }
		
		
	}
	
	
///////////////////////////////////////////////////////////////////////////////


	public void BankNow() {
		openBank();
		if (bank.isOpen()) {
			EmptyInventory();
		}
		if (bank.isOpen() && StartIDBank == false) {
			TextTo14Withdraw();
		}
		GetItems();
	}
	
	public void GetItems() {
		if (bank.isOpen() && !inventoryContains(FirstID)) {
			CurrentStat = "Withdrawing First Item";
			wait(random(400, 800));
			bank.atItem(FirstID, "raw-14");
			wait(random(1200, 1400));
		}
		if (bank.isOpen() && !inventoryContains(SeconID) && inventoryContains(FirstID)) {
			CurrentStat = "Withdrawing Second Item";
			wait(random(400, 800));
			bank.atItem(SeconID, "raw-14");
			wait(random(1200, 1400));
		}
		
		//Check for Items AKA Fail Safes
		if (bank.isOpen()) {
			//Kill over Scripts			
			if ( !inventoryContains(FirstID) && bank.getCount(FirstID) == 0 ) {
				if( unfiID != 0 && itemID != 0 && DidWeSwap == false) {
					TimeToSwap();
					ScreenshotUtil.takeScreenshot(true);
				} else {
					OutofString = "First Item";
					OutofItems();
				}
			}
			if ( !inventoryContains(SeconID) && bank.getCount(SeconID) == 0 ) {
				if( unfiID != 0 && itemID != 0 && DidWeSwap == false) {
					TimeToSwap();
					ScreenshotUtil.takeScreenshot(true);
				} else {
					OutofString = "Second Item";
					OutofItems();
				}
			}
			//End of Kill Over Scripts
			//Counting Area
			if (getInventoryCount(FirstID) > 14 | getInventoryCount(SeconID) > 14) {
				SoMuchString = " grabed to much of something";
				ToManyItems();
			}
			if (!inventoryContains(FirstID) | !inventoryContains(SeconID)) {
				if( countMissedItem < 4) {
					log("Missed an item! How many times: " + (countMissedItem + 1));
					countMissedItem++;
					GetItems();
				} else {
					log("Missed an item too many times... Fail Safe deposit all!");
					EmptyInventory();
				}
			}
			
		}

	}
	


	public int loop() {
		if (crappedout == false) {
			moveMouse(755, 13);
			wait(random(700, 800));
			atMenu("Exit");
			moveMouse(755, 13);
			moveMouse(random(575, 699), random(408, 427));
			wait(random(700, 800));
			atMenu("Exit to Login");
			wait(random(2000,3000));
			stopScript();
			stopScript(false);
			wait(random(500,1000));
		}
		
		if ((System.currentTimeMillis() - meuper) > 180000) { 
			takeshotbollean = true; 
			meuper = System.currentTimeMillis();
		}
		if (takeshotbollean == true && (System.currentTimeMillis() - meuper) > 60000) { 
			takeshotbollean = false;
			meuper = System.currentTimeMillis(); 
		}
		if (POTOION_AREA.isValid()) { PotBoolean = true; }
		if (!POTOION_AREA.isValid()) { PotBoolean = false; }
		
		if ((amountUnfinished+amountFinished) > TempTotalPotions) {
			TempTotalPotions = (amountUnfinished+amountFinished);
			waitTimer = System.currentTimeMillis();
		}
		
		if (inventoryContainsOneOf(SeconID) && inventoryContainsOneOf(FirstID)) {
			if ((System.currentTimeMillis() - waitTimer) > 5000 && !POTOION_AREA.isValid() && !bank.isOpen()) {
				ItemClickItem();
			}
			
			if (POTOION_AREA.isValid()) {
				ItemMakeAll();
				moveMouse(255, 175, 30, 30);
			}
		}
		

		if (!inventoryContainsOneOf(FirstID) | !inventoryContainsOneOf(SeconID)) {
			CurrentStat = "Out of Item: ";
			BankNow();
		}

		
		if (inventoryContainsOneOf(SeconID) && bank.isOpen()) {
			bank.close();
			wait(random(100, 200));
		}
		
		if (isItemSelected() && (System.currentTimeMillis() - waitTimer) > random(120000, 125000)) {
			CurrentStat = "Failsafe";
			log("Doing Failsafe.");
			moveMouse(random(650, 660), random(180, 190));
			clickMouse(true);
		}
		if ((System.currentTimeMillis() - waitTimer) > random(120000, 125000)) {
			bank.close();
			waitTimer = System.currentTimeMillis();
			log.severe("WaitTimer is over 125000 miliseconds");
			wait(random(100, 200));
			BankNow();
		}
		return 0;
	}

	public void onRepaint(Graphics g) { //Paint Version 1.4
		
		//TIMER VARIABLES
		long timermil = System.currentTimeMillis() - startTime;
		long millis = System.currentTimeMillis() - startTime;
		long hours = millis / (1000 * 60 * 60);
		millis -= hours * (1000 * 60 * 60);
		long minutes = millis / (1000 * 60);
		millis -= minutes * (1000 * 60);
		long seconds = millis / 1000;
	
		if(isLoggedIn()){ //Paint Version 1.1
			
			
			Mouse mousePos = Bot.getClient().getMouse();
			
			//////////////////////////////////////////////////////
			//NEW Paint//
			//////////////////////////////////////////////////////
			
			//Precent bar on bottem of screen
			valntnxtLevel = skills.getPercentToNextLevel(Constants.STAT_HERBLORE);
			precentntnxtLevel = (valntnxtLevel / 100);
			bartntnxtLevel = (precentntnxtLevel * 506);
			barlenght = Math.round(bartntnxtLevel);
			barlenghtint = (int) barlenght;
			
			//Border
			g.setColor(Color.BLACK);
			g.fillRoundRect(5,313,510,22,10,10);
			//Background
			g.setColor(Color.RED);
			g.fillRoundRect(7,315,506,18,5,5);
			
			//Fill Bar
			g.setFont(new Font("SansSerif", Font.BOLD, 15)); //Body font
			g.setColor(Color.GREEN);
			g.fillRoundRect(7,315,barlenghtint,18,5,5);
			//g.fillRoundRect(315,125, ((skills.getPercentToNextLevel(Constants.STAT_HERBLORE)* 3) / 2),15,5,5);
			g.setColor(Color.BLACK);
			g.drawString(skills.getPercentToNextLevel(STAT_HERBLORE) + "% Current Level: " + skills.getCurrentSkillLevel(STAT_HERBLORE), 190, 330);
			
			if (takeshotbollean == true && popups == true) {
				g.fillRoundRect(0,0,1000,1000,0,0);
				g.setColor(Color.WHITE);
				g.setFont(new Font("SansSerif", Font.BOLD, 25)); //Body font
				g.drawString("I'll be moving the script to VIP only", 15, 70+(25*0));
				g.drawString("unless I recive more progress shots! Version: " + getVersion(), 15, 70+(25*1));
				g.drawString("Must make over 1500 pots to count... or 2 hours...", 15, 70+(25*2));
				g.setColor(Color.BLUE);
				g.drawString("I'll disappear in 60 seconds...", 60, 70+130);
				g.setColor(Color.WHITE);
				g.setFont(new Font("SansSerif", Font.BOLD, 20)); //Body font
				g.drawString("The snapshot is at...", 40, 70+190);
				g.drawString("C:/Users/Administrator/Documents/RSBot/Screenshots", 40, 70+210);
			}
			
			//Math area
			
			ExpGained = (skills.getCurrentSkillExp(STAT_HERBLORE)-startXP);
			LevGained = (skills.getCurrentSkillLevel(STAT_HERBLORE)-startLevel);
			MadGained = (amountUnfinished+amountFinished);
				//Hour Stuff
				if (ExpGained > 0) {
					ExpHour = (int) (ExpGained * 3600000D / timermil);
				}
				if (LevGained > 0) {
					LevHour = (int) (LevGained * 3600000D / timermil);
				}
				if (MadGained > 0) {
					MadHour = (int) (MadGained * 3600000D / timermil);
				}
				
				//Covering the Chat Box
				
				xStaVal = 7;
				yStaVal = 345;
				xEndVal = 497;
				yEndVal = 458;
				
				xDisVal = (xEndVal-xStaVal);
				yDisVal = (yEndVal-yStaVal);
				
				//Covering the Up Arrow
				
				xStaArrow = 498;
				yStaArrow = 346;
				xEndArrow = 512;
				yEndArrow = 360;

				xDisArrow = (xEndArrow-xStaArrow);
				yDisArrow = (yEndArrow-yStaArrow);
				
			//Hides the chatbox
			if (mousePos.x > xStaArrow && mousePos.x < xEndArrow && mousePos.y > yStaArrow && mousePos.y < yEndArrow) {
				g.setColor(Color.BLUE);
				g.fillRoundRect(xStaArrow, yStaArrow, xDisArrow, yDisArrow,0,0);
			} else {
				colorme();
				g.setColor(new Color(redme, greenme, blueme, 255));
				g.fillRoundRect(xStaArrow, yStaArrow, xDisArrow, yDisArrow,0,0);
				
				//Reference settings!
				g.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 48)); //Title font
				g.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 20)); //Body font
			  
				//Cover The chat box
				g.setColor(new Color(85, 255, 17, 255));
				g.fillRoundRect(xStaVal, yStaVal, xDisVal, yDisVal,0,0);
				
				//<HR> line
				g.setColor(Color.BLACK);
				g.drawLine((xStaVal+5), (yStaVal+75), (xEndVal-5), (yStaVal+75));
				
				//Title Stuff
				g.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 20)); //Title font
				g.drawString("Fruggle's Potion Maker - " + getVersion(), 15, 365);
				
				g.setFont(new Font("SansSerif", Font.BOLD, 15)); //Body font
				g.drawString("Current Status: " + CurrentStat, 30, 380);
				
				//New Time 
				if (minutes < 10) { CusMins = "0" + minutes; } else { CusMins = "" + minutes; }
				if (seconds < 10) { CusSec = "0" + seconds; } else { CusSec = "" + seconds; }
				
				g.drawString("Time: " + hours + ":" + CusMins + ":" + CusSec, 15, 400);
				
				//Total Made
				g.drawString("Total made: " + MadGained, 15, 415);
				g.drawString("Unf made: " + amountUnfinished, 170, 415);
				g.drawString("Fin made: " + amountFinished, 325, 415);
	
				
				//Potion Status & Experience
				g.drawString("Making: " + PotName1 + " Potion & " + PotName2 + " Potion", 15, 435);
				g.drawString("Experience: " + ExpGained, 15, 450);
				g.drawString("Exp/Hour: " + ExpHour, 170, 450);
				g.drawString("Made/Hour: " + MadHour, 325, 450);
				
			} //End of Hide
			

			
			xStaDebug = 570;
			xEndDebug = 725;
			yStaDebug = 215;
			yendDebug = 235;
			
			
			if (mousePos.x > xStaDebug && mousePos.x < xEndDebug && mousePos.y > yStaDebug && mousePos.y < yendDebug || takeshotbollean == true && popups == true) {
				CalcUnfinished = ( (unfiIDPrice - (vialIDPrice + herbIDPrice)) * amountUnfinished);
				CalcFinished = ( (doneIDPrice - (itemIDPrice + unfiIDPrice)) * amountFinished);
				CalcUnfinishedHour = (int) ((CalcUnfinished+CalcFinished) * 3600000D / timermil);
				
			//Hidden Information
				g.setColor(Color.BLACK);
				g.fillRoundRect(xStaDebug, yStaDebug, 150, 10000,0,0);
				g.setColor(Color.WHITE);
				g.setFont(new Font("SansSerif", Font.PLAIN, 10)); //Misc. font
				g.drawString(" " + (System.currentTimeMillis() - waitTimer), xStaDebug, yendDebug);
				
				g.drawString(" Random Num: " + randomPick, xStaDebug, (yendDebug+(12*2)));
				g.drawString(" red: " + redme, xStaDebug, (yendDebug+(12*3)));
				g.drawString(" green: " + greenme, xStaDebug, (yendDebug+(12*4)));
				g.drawString(" blue: " + blueme, xStaDebug, (yendDebug+(12*5)));
				
				g.drawString(" Made: " + (CalcUnfinished+CalcFinished) + "gp!", xStaDebug, (yendDebug+(12*7)));
				g.drawString(" Made/Hour: " + CalcUnfinishedHour + "gp!", xStaDebug, (yendDebug+(12*8)));
				g.drawString(" vialID: " + vialIDPrice , xStaDebug, (yendDebug+(12*9)));
				g.drawString(" herbID: " + herbIDPrice , xStaDebug, (yendDebug+(12*10)));
				g.drawString(" unfiID: " + unfiIDPrice , xStaDebug, (yendDebug+(12*11)));
				g.drawString(" itemID: " + itemIDPrice , xStaDebug, (yendDebug+(12*12)));
				g.drawString(" doneID: " + doneIDPrice , xStaDebug, (yendDebug+(12*13)));
				g.drawString(" Pot Valid: " + PotBoolean , xStaDebug, (yendDebug+(12*14)));
				
				
			} 						
			
			//Using Mouse location for lines
			final Point m = getMouseLocation();
			Color ORANGE = new Color(255, 140, 0);
			g.setColor(ORANGE);
			//Draw Lign
			g.drawLine(0, m.y, 765, m.y);
			g.drawLine(m.x, 0, m.x, 506);
			

		} //end of if(isLoggedIn()
		
	} //end of public void onRepaint(Graphics g)

	public void serverMessageRecieved(ServerMessageEvent e) {
		String word = e.getMessage().toLowerCase();
		if (word.contains("vial")) {
			amountUnfinished++;
		}
		if (word.contains("mix") | word.contains("207") ) {
			amountFinished++;
			
		}
	}

	public void onFinish() {
		takeshotbollean = true;
		wait(random(400,500));
		ScreenshotUtil.takeScreenshot(true);
		wait(random(10000,13000));
		log("Thanks for using HerbPotAIO " + getVersion() +"!");
		wait(random(2000,3000));
		log("Some information for you:");
		if (SelectedFin.equals("None")) {
			log.severe("   Amount of unfinished potions: " + amountUnfinished);
		} 
		if (SelectedUnf.equals("None")) {
			log.severe("   Amount of finished potions: " + amountFinished);
		}
		if (!SelectedUnf.equals("None") && !SelectedFin.equals("None")) {
			log.severe("   Amount of unfinished potions: " + amountUnfinished);
			log.severe("   Amount of finished potions: " + amountFinished);
		}
	}
}