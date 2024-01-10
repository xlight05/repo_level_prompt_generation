import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.rsbot.script.Calculations;
import org.rsbot.script.Constants;
import org.rsbot.script.ScriptManifest;

import org.rsbot.accessors.Model;
import org.rsbot.accessors.RSAnimable;
import org.rsbot.bot.Bot;
import org.rsbot.bot.input.Mouse;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.script.Script;
import org.rsbot.script.wrappers.RSInterface;
import org.rsbot.script.wrappers.RSInterfaceChild;
import org.rsbot.script.wrappers.RSNPC;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSPlayer;
import org.rsbot.script.wrappers.RSTile;

@ScriptManifest(authors = { "FiLThY_FreaK_" }, category = "Runecraft", name = "FreakyGraahkCrafter", version = 1.1, 
		description = "More updates coming soon.")

public class FreakyNatureCrafter extends Script implements PaintListener, ServerMessageListener {

//Int//
	private int BrokenPouchID1 = 5513;
	private int BrokenPouchID2 = 5511;
	private int BrokenPouchID3 = 5515;
	private int GiantPouchID = 5514;
	private int LargePouchID = 5512;
	private int MediumPouchID = 5510;
	private int SmallPouchID = 5509;
	private int GraahkPouchID = 12810;
	private int DuelRing8 = 2552;
	private int DuelRing1 = 2566;
	private int PureEssID = 7936;
	private int ObseliskID = 29938;
	private int check = 0;
	private int check1 = 0;
	private int check2 = 0;
	private int check3 = 0;
	private int check4 = 0;
	private int check5 = 5;
	private int check6 = 0;
	private int GraahkID = 7363;
	private int ran = random(1, 5);
	private String CurrentSum;
	private int CurrentSum2 = 0;
	private int BankChest = 4483;
	private int AirRuneID = 556;
	private int CosmicRuneID = 564;
	private int AstralRuneID = 9075;
	private int NatureRuneID = 561;
	private int StrangeRockID1 = 15530;
	private int StrangeRockID2 = 15531;
	private int test1 = 0;
	private int random = random(6, 7);
	private int RandomSummon = random(15, 30);
	private int DuelRing = 0;
	private RSTile BankTile = new RSTile (2444, 3083);
	private RSTile GraahkTeleportTile = new RSTile (2786, 3005);
	private RSTile AltarClickTile = new RSTile (2869, 3019);
	private RSTile obseliskTile = new RSTile (2850, 3027);
	private RSTile InsideAltarTile = new RSTile (2400, 4835);
	private RSTile InsideAltarClickTile = new RSTile (2400, 4841);
	RSTile[] path;
	private RSTile PathToObselisk[] = { new RSTile(2797, 3009), new RSTile(2805, 3009), new RSTile(2814, 3009), 
									new RSTile(2821, 3011), new RSTile(2829, 3015), new RSTile(2836, 3022),
									new RSTile(2843, 3027), new RSTile(2854, 3028), new RSTile(2861, 3025),
									new RSTile(2867, 3020)};
	private RSTile PathToClickObselisk[] = { new RSTile(2797, 3009), new RSTile(2805, 3009), new RSTile(2814, 3009), 
									new RSTile(2821, 3011), new RSTile(2829, 3015), new RSTile(2836, 3022),
									new RSTile(2843, 3027), new RSTile(2850, 3027)};
	private RSTile PathToAltar[] = { new RSTile(2856, 3025), new RSTile(2865, 3021)};
	private RSTile RandomBankTile;
	private String Status;
	private int testcraft = 0;
	private int testcraft2 = 0;
	private int testbank = 0;
	private int cycle = 0;
	private int cycle1 = 0;
	private int cycle2 = 0;
	private int cycle3 = 0;
	private int cycle5 = 0;
	private int cycle6 = 0;
	private int cycle8 = 0;
	private int cycle9 = 0;
	private int cycle10 = 0;
	private int cycle12 = 0;
	long startTime = 0;
	private int startXP = 0;
	private int TotalNats = 0;
	private int CalcNats = 0;
	private int startLevel = 0;
	private int f = 0;
	private int PaintSwitch = 0;
	private int ProfitCalc = 0;
	private int Repair = 0;
	private int Summon = 0;
	private int Ring = 0;
	private int test2 = 0;
	//private int esscount1 = 0;
	
	public boolean onStart(Map<String, String>args) {
		startLevel = skills.getCurrentSkillLevel(STAT_RUNECRAFTING);
	 	startXP = skills.getCurrentSkillExp(STAT_RUNECRAFTING);
	 	ProfitCalc = grandExchange.loadItemInfo(NatureRuneID).getMarketPrice() - grandExchange.loadItemInfo(PureEssID).getMarketPrice();
	 	startTime = System.currentTimeMillis();
		log("Script initialized!");
		
		if (equipmentContainsOneOf(2552)) {
			log("Start ring = 8");
			DuelRing = 2552;
			
		}
		
		if (equipmentContainsOneOf(2554)) {
			log("Start ring = 7");
			DuelRing = 2554;
			
		}
		
		if (equipmentContainsOneOf(2556)) {
			log("Start ring = 6");
			DuelRing = 2556;
			
		}
		
		if (equipmentContainsOneOf(2558)) {
			log("Start ring = 5");
			DuelRing = 2558;
			
		}
		
		if (equipmentContainsOneOf(2560)) {
			log("Start ring = 4");
			DuelRing = 2560;
			
		}
		
		if (equipmentContainsOneOf(2562)) {
			log("Start ring = 3");
			DuelRing = 2562;
			
		}
		
		if (equipmentContainsOneOf(2564)) {
			log("Start ring = 2");
			DuelRing = 2564;
			
		}
		
		if (equipmentContainsOneOf(2566)) {
			log("Start ring = 1");
			DuelRing = 2566;
			check++;
			
		}
		
		return true;
		
	}
	
	public void onFinish()	{
		log("Powering down");
		
	}
	
	@Override
	public int loop() {
		if (distanceTo(BankTile) <= 15) {
			cycle2 = 0;
			
		}
		
		if (getMyPlayer().getAnimation() == 8939 || getMyPlayer().getAnimation() == 4413 || getMyPlayer().getAnimation() == 9603) {
			wait(random(200,300));
			
		}
		
		if (getMyPlayer().getAnimation() == 791) {
			testcraft++;
			wait(random(200,300));
		
		}
		
		if (getState() != State.TELEPORTGRAAHK) {
			if (check3 != 0) {
				check3 = 0;
				
			}
			
		}

		switch (getState())	{
		
		case STOCK:
			try {
				cycle6 = 1;
				check4 = 0;

				if (cycle3 == 0) {
					if (getInventoryCount(PureEssID) <= 8 && cycle1 == 0) {
						if (!bank.isOpen()) {
							if (!getMyPlayer().isMoving()) {
								RSObject Chest = getNearestObjectByID(BankChest);
								if (!tileOnScreen(Chest.getLocation())) {
									setCameraRotation(random(180, 240));
									
								}
								
								clickObject(Chest, "Use");
								waitForInterface(762, random(4000, 5000));
								//wait(random(1000, 1200));
							
							}
							
						}
						
						if (bank.isOpen()) {
							wait(random(300, 600));
							if (inventoryContains(NatureRuneID)) {
								bank.deposit(NatureRuneID, 0);
									
							}
								
							if (inventoryContains(StrangeRockID1)) {
								bank.deposit(StrangeRockID1, 0);
									
							}
								
							if (inventoryContains(StrangeRockID2)) {
								bank.deposit(StrangeRockID2, 0);
									
							}
							
							if (inventoryContains(DuelRing1)) {
								bank.deposit(DuelRing1, 0);
									
							}

							if (repairPouches() == true) {
								if (getInventoryCount(AirRuneID) <= 1) {
									bank.atItem(AirRuneID, "Withdraw-1");
									wait(random(1000, 1200));
							
								}
							
								if (getInventoryCount(AirRuneID) <= 1) {
									bank.atItem(AirRuneID, "Withdraw-1");
									wait(random(1000, 1200));
							
								}
							
								if (getInventoryCount(CosmicRuneID) == 0) {
									bank.atItem(CosmicRuneID, "Withdraw-1");
									waitForInventoryItem(CosmicRuneID, random(2000, 2500));
							
								}
							
								if (getInventoryCount(AstralRuneID) == 0) {
									bank.atItem(AstralRuneID, "Withdraw-1");
									waitForInventoryItem(AstralRuneID, random(2000, 2500));
							
								}
								
							}

							if (replaceRing() == true) {
								if (!inventoryContains(DuelRing8)) {
									bank.atItem(DuelRing8, "Withdraw-1");
									waitForInventoryItem(DuelRing8, random(2000, 2500));
									
								}
								
							}
							
							if (graahkDisappeared() == true) {
								if (getInventoryCount(GraahkPouchID) == 0) {
									bank.atItem(GraahkPouchID, "Withdraw-1");
									waitForInventoryItem(GraahkPouchID, random(2000, 2500));
									
								}
								
							}
							//esscount1 = getInventoryCount(PureEssID);
							if (repairPouches() == true && replaceRing() == false && graahkDisappeared() == false) {
								if (getInventoryCount(AstralRuneID) >= 1 && getInventoryCount(CosmicRuneID) >= 1 
										&& getInventoryCount(AirRuneID) >= 2) {
									if (getInventoryCount(NatureRuneID) != 0) {
										bank.deposit(NatureRuneID, 0);
										break;
										
									}
									
									bank.atItem(PureEssID, "Withdraw-All");
									waitForInventoryItem(PureEssID, random(2000, 2500));
								
									if (getInventoryCount(PureEssID) >= 20) {
										Repair++;
										cycle1++;
									
									}
							
								}
							
							}
							
							if (replaceRing() == true && repairPouches() == false && graahkDisappeared() == false) {
								if (inventoryContains(DuelRing8)) {
									if (getInventoryCount(DuelRing8) != 1) {
										bank.deposit(DuelRing8, 1);
										break;
										
									}
									
									if (getInventoryCount(NatureRuneID) != 0) {
										bank.deposit(NatureRuneID, 0);
										break;
										
									}
									
									bank.atItem(PureEssID, "Withdraw-All");
									waitForInventoryItem(PureEssID, random(2000, 2500));
									
									if (getInventoryCount(PureEssID) >= 20) {
										Ring++;
										cycle1++;
									
									}
								
								}
								
							}
							
							if (graahkDisappeared() == true && replaceRing() == false && repairPouches() == false) {
								if (getInventoryCount(GraahkPouchID) != 0) {
									if (getInventoryCount(GraahkPouchID) != 1) {
										bank.deposit(GraahkPouchID, 1);
										break;
										
									}
									
									if (getInventoryCount(NatureRuneID) != 0) {
										bank.deposit(NatureRuneID, 0);
										break;
										
									}
									
									bank.atItem(PureEssID, "Withdraw-All");
									waitForInventoryItem(PureEssID, random(2000, 2500));
									
									if (getInventoryCount(PureEssID) >= 20) {
										Summon++;
										cycle1++;
									
									}
								
								}
									
							}
							
							if (repairPouches() == true && replaceRing() == true && graahkDisappeared() == false) {
								if (getInventoryCount(AstralRuneID) >= 1 && getInventoryCount(CosmicRuneID) >= 1 
										&& getInventoryCount(AirRuneID) >= 2 && inventoryContains(DuelRing8)) {
									if (getInventoryCount(DuelRing8) != 1) {
										bank.deposit(DuelRing8, 1);
										break;
										
									}
									
									if (getInventoryCount(NatureRuneID) != 0) {
										bank.deposit(NatureRuneID, 0);
										break;
										
									}
									
									bank.atItem(PureEssID, "Withdraw-All");
									waitForEss(random(2000, 2500));
								
									if (getInventoryCount(PureEssID) >= 20) {
										Repair++;
										Ring++;
										cycle1++;
									
									}
							
								}
							
							}
							
							if (repairPouches() == true && graahkDisappeared() == true && replaceRing() == false) {
								if (getInventoryCount(AstralRuneID) >= 1 && getInventoryCount(CosmicRuneID) >= 1 
										&& getInventoryCount(AirRuneID) >= 2 && getInventoryCount(GraahkPouchID) != 0) {
									if (getInventoryCount(GraahkPouchID) != 1) {
										bank.deposit(GraahkPouchID, 1);
										break;
										
									}
									
									if (getInventoryCount(NatureRuneID) != 0) {
										bank.deposit(NatureRuneID, 0);
										break;
										
									}
									
									bank.atItem(PureEssID, "Withdraw-All");
									waitForInventoryItem(PureEssID, random(2000, 2500));
								
									if (getInventoryCount(PureEssID) >= 20) {
										Repair++;
										Summon++;
										cycle1++;
									
									}
							
								}
							
							}
							
							if (replaceRing() == true && graahkDisappeared() == true && repairPouches() == false) {
								if (getInventoryCount(GraahkPouchID) != 0 && inventoryContains(DuelRing8)) {
									if (getInventoryCount(GraahkPouchID) != 1) {
										bank.deposit(GraahkPouchID, 1);
										break;
										
									}
									
									if (getInventoryCount(DuelRing8) != 1) {
										bank.deposit(DuelRing8, 1);
										break;
										
									}
									
									if (getInventoryCount(NatureRuneID) != 0) {
										bank.deposit(NatureRuneID, 0);
										break;
										
									}
									
									bank.atItem(PureEssID, "Withdraw-All");
									waitForInventoryItem(PureEssID, random(2000, 2500));
								
									if (getInventoryCount(PureEssID) >= 20) {
										Ring++;
										Summon++;
										cycle1++;
									
									}
							
								}
							
							}
							
							if (repairPouches() == true && graahkDisappeared() == true && replaceRing() == true) {
								if (getInventoryCount(AstralRuneID) >= 1 && getInventoryCount(CosmicRuneID) >= 1 
										&& getInventoryCount(AirRuneID) >= 2 && inventoryContains(DuelRing8) 
										&& getInventoryCount(GraahkPouchID) != 0) {
									if (getInventoryCount(GraahkPouchID) != 1) {
										bank.deposit(GraahkPouchID, 1);
										break;
										
									}
									
									if (getInventoryCount(GraahkPouchID) != 1) {
										bank.deposit(GraahkPouchID, 1);
										break;
										
									}
									
									if (getInventoryCount(NatureRuneID) != 0) {
										bank.deposit(NatureRuneID, 0);
										break;
										
									}
									
									bank.atItem(PureEssID, "Withdraw-All");
									waitForInventoryItem(PureEssID, random(2000, 2500));
								
									if (getInventoryCount(PureEssID) >= 20) {
										Repair++;
										Ring++;
										Summon++;
										cycle1++;
									
									}
							
								}
							
							}
							
						}
						
					} 

					if (getInventoryCount(PureEssID) >= 8 && cycle1 != 0) {
						if (Ring != 0) {
							if (bank.isOpen()) {
								bank.close();
								
							}

							if (!bank.isOpen()) {
								atInventoryItem(DuelRing8, "Wear");
								waitForInventoryItem(DuelRing1, random(2000, 2500));

								if (getInventoryCount(DuelRing1) != 0) {
									Ring = 0;
									
								}
								
								if (getInventoryCount(DuelRing8) != 0 && !bank.isOpen() && test1 != 0) {
									atInventoryItem(DuelRing8, "Wear");
									waitForInventoryItem(DuelRing1, random(2000, 2500));
									Ring = 0;
									
								}
							
							}
							
						}

						if (Repair != 0 && Ring == 0) {
							if (cycle10 == 0) {
							if (!getInterface(88).isValid()) {
									bank.close();
									atInterface(548, 130); //Magic Spellbook
									atInterface(430, 26); //Cast
									openTab(Constants.TAB_INVENTORY);
									wait(random(400, 600));
							
							}

							if (getInterface(88).isValid()) {
								if (getMouseLocation().getX() <= 454 | getMouseLocation().getX() >= 466 | getMouseLocation().getY() <= 279 
										| getMouseLocation().getY() >= 296) {
									moveMouse(random(455, 465), random(280, 295));
							
								}

								if (getMouseLocation().getX() >= 454 && getMouseLocation().getX() <= 466 && getMouseLocation().getY() >= 279
										&& getMouseLocation().getY() <= 296) {
									clickMouse(true);
									wait(random(300, 600));
									clickRSComponent(getInterface(88).getChild(22), 4, true); //Speak-to (Dark mage)
									wait(random(800, 1000));
									if (getMyPlayer().getAnimation() == 4413) {
										waitForIdle(random(5000,6000));
									
									}

									cycle10++;
								
								}
							
							}
							
							}

							talk();
							if (talk() == true && getInventoryCount(BrokenPouchID1) != 0 | getInventoryCount(BrokenPouchID2) != 0 
									| getInventoryCount(BrokenPouchID3) != 0 && cycle10 != 0) {

								if (getInterface(64).getChild(5).isValid()) {
									atInterface(64, 5);
									waitForInterface(242, 2000);
							
								}
						
								if (getInterface(242).getChild(6).isValid()) {
									atInterface(242, 6);
									waitForInterface(65, 2000);
							
								}
						
								if (getInterface(65).getChild(6).isValid() && getInterface(65, 5).containsText("with you.")) {
									atInterface(65, 6);
									wait(random(400, 600));
							
								}
								
								if (getInterface(65).getChild(6).isValid() && getInterface(65, 5).containsText("you.")) {
									atInterface(65, 6);
									wait(random(400, 600));
							
								}
						
								if (getInterface(65).getChild(6).isValid() && getInterface(65, 5).containsText("something quickly.")) {
									atInterface(65, 6);
									wait(random(400, 600));
							
								}
						
								if (getInterface(243).getChild(7).isValid()) {
									atInterface(243, 7);
									wait(random(400, 600));
							
								}
						
								if (getInterface(230).getChild(3).isValid()) {
									atInterface(230, 3);
									wait(random(400, 600));
							
								}
								
								if (getInterface(228).getChild(2).isValid() && getInterface(228, 2).containsText("Can you repair my pouches?")) {
									atInterface(228, 2);
									wait(random(400, 600));
							
								}
						
								if (getInterface(65).getChild(6).isValid() 
										&& getInterface(65, 5).containsText("I think they might be degrading.")) {
									atInterface(65, 6);
									wait(random(400, 600));

									if (repairPouches() == false) {
										Repair = 0;
										cycle10 = 0;
										
									}
							
								}
						
							}
						
						}

						if (Summon != 0 && Repair == 0 && Ring == 0) {
							if (bank.isOpen()) {
								bank.close();
							
							}

							if (getSetting(448) != 12810) {
								atInventoryItem(GraahkPouchID, "Summon");
								waitForInventoryItemDisapear(GraahkPouchID, random(1300, 1600));
								
							} else {
								
								atInterface(747, 11);
								wait(random(200, 300));
								
							}

							if (getInventoryCount(GraahkPouchID) == 0) {
								Summon = 0;

							}
							
						}
						
					}

					if (getInventoryCount(PureEssID) >= 8 && Ring == 0 && Summon == 0 && Repair == 0 && cycle1 != 0) {
						atInventoryItem(GiantPouchID, "Fill");
						atInventoryItem(LargePouchID, "Fill");
						atInventoryItem(MediumPouchID, "Fill");
						atInventoryItem(SmallPouchID, "Fill");
						cycle1 = 0;
						cycle3++;
						
					} else if (getInventoryCount(DuelRing8) != 0) {
						atInventoryItem(DuelRing8, "Wear");
						waitForInventoryItem(DuelRing1, random(2000, 2500));
						Ring = 0;
						
					}
					
				}
				
				if (cycle3 != 0) {
					if (cycle == 0 && getInventoryCount(PureEssID) <= 8) {
						if (!bank.isOpen()) {
							RSObject Chest = getNearestObjectByID(BankChest);
							if (!tileOnScreen(Chest.getLocation())) {
								setCameraRotation(random(180, 240));
								
							}

							clickObject(Chest, "Use");
							waitForInterface(762, random(4000, 5000));
							//wait(random(1000, 1200));
							
						}

						if (bank.isOpen()) {
							wait(random(300, 600));
							if (inventoryContains(NatureRuneID)) {
								bank.deposit(NatureRuneID, 0);
								wait(random(400, 600));
									
							}
								
							if (inventoryContains(StrangeRockID1)) {
								bank.deposit(StrangeRockID1, 0);
								wait(random(400, 600));
									
							}
								
							if (inventoryContains(StrangeRockID2)) {
								bank.deposit(StrangeRockID2, 0);
								wait(random(400, 600));
									
							}
							
							if (inventoryContains(DuelRing1)) {
								bank.deposit(DuelRing1, 0);
								wait(random(400, 600));
									
							}
							
							if (inventoryContains(AstralRuneID)) {
								bank.deposit(AstralRuneID, 0);
								wait(random(400, 600));
								
							}
							
							if (inventoryContains(CosmicRuneID)) {
								bank.deposit(CosmicRuneID, 0);
								wait(random(400, 600));
								
							}
							
							if (inventoryContains(AirRuneID)) {
								bank.deposit(AirRuneID, 0);
								wait(random(400, 600));
								
							}
							
							//esscount1 = getInventoryCount(PureEssID);

							if (getInventoryCount(PureEssID) <= 20) {
								bank.atItem(PureEssID, "Withdraw-All");
								waitForEss(random(2000, 2500));
								test2++;
							
							}
							
						}
						
					}

					if (getInventoryCount(PureEssID) >= 8 && test2 != 0) {
						if (!inventoryContains(DuelRing8)) {
							check = 0;
							
						}
						
						if (!inventoryContains(GraahkPouchID)) {
							check2 = 0;
							
						}

						bank.close();
						cycle6 = 0;
						test2 = 0;
						cycle3 = 0;
						cycle = 0;
						cycle12++;
						testbank++;
						
					}
					
				}
				
			}catch (final Exception e){
				log("Avoided: Nullpointer at case STOCK");
				return(random(20,30));
				
			}
			
			break;
		
			case FILLINVENTORY:
				try {	
					cycle12++;
					if (cycle3 == 0) {
						if (getInventoryCount(PureEssID) <= 8) {
							if (!bank.isOpen()) {
								if (!getMyPlayer().isMoving()) {
									RSObject Chest = getNearestObjectByID(BankChest);
									if (!tileOnScreen(Chest.getLocation())) {
										setCameraRotation(random(180, 240));
										
									}
									
									clickObject(Chest, "Use");
									waitForInterface(762, random(4000, 5000));
									//wait(random(1000, 1200));
								
								}
									
							}
							
							if (bank.isOpen()) {
								wait(random(300, 600));
								
								if (inventoryContains(NatureRuneID)) {
									bank.deposit(NatureRuneID, 0);
										
								}
									
								if (inventoryContains(StrangeRockID1)) {
									bank.deposit(StrangeRockID1, 0);
										
								}
									
								if (inventoryContains(StrangeRockID2)) {
									bank.deposit(StrangeRockID2, 0);
										
								}
								
								if (inventoryContains(DuelRing1)) {
									bank.deposit(DuelRing1, 0);
										
								}
								
								bank.atItem(PureEssID, "Withdraw-All");
								waitForInventoryItem(PureEssID, random(2000, 2500));
								
							}
							
						}
						
						if (getInventoryCount(PureEssID) >= 8) {
							bank.close();
							atInventoryItem(GiantPouchID, "Fill");
							atInventoryItem(LargePouchID, "Fill");
							atInventoryItem(MediumPouchID, "Fill");
							atInventoryItem(SmallPouchID, "Fill");
							cycle3 = 1;
							check4 = 0;
							cycle8++;
							
						}
							
					}
					
					if (cycle3 == 1) {
						if (cycle == 0 && getInventoryCount(PureEssID) <= 20) {
							
							if (!bank.isOpen()) {
								RSObject Chest = getNearestObjectByID(BankChest);
								if (!tileOnScreen(Chest.getLocation())) {
									setCameraRotation(random(180, 240));
									
								}
								
								clickObject(Chest, "Use");
								waitForInterface(762, random(4000, 5000));
								//wait(random(1000, 1200));
								
							}
							
							if (bank.isOpen()) {
								wait(random(300, 600));
								if (inventoryContains(NatureRuneID)) {
									bank.deposit(NatureRuneID, 0);
										
								}
									
								if (inventoryContains(StrangeRockID1)) {
									bank.deposit(StrangeRockID1, 0);
										
								}
									
								if (inventoryContains(StrangeRockID2)) {
									bank.deposit(StrangeRockID2, 0);
										
								}
								
								if (inventoryContains(DuelRing1)) {
									bank.deposit(DuelRing1, 0);
										
								}
								
								//esscount1 = getInventoryCount(PureEssID);
								
								if (getInventoryCount(PureEssID) <= 20) {
									bank.atItem(PureEssID, "Withdraw-All");
									waitForEss(random(2000, 2500));
									test2++;
								
								}
								
							}
							
						}
						
						if (getInventoryCount(PureEssID) >= 14 && test2 != 0) {
							bank.close();
							testbank++;
							cycle = 0;
							test2 = 0;
							cycle3 = 0;
							cycle6 = 0;
							
						}
						
					}
					
				}catch (final Exception e){
					log("Avoided: Nullpointer at case FILLPOUCHES");
					return(random(20,30));
					
				}
				
				break;
		
			case WALKTOOBSELISK:
				try {
					testbank = 0;
					cycle8 = 0;
					check5 = 0;
					cycle3 = 0;
					cycle12 = 0;
					if (distanceTo(AltarClickTile) <= (15)) {
						if (!tileOnScreen(AltarClickTile)) {
							setCameraRotation(random(248, 215));

						}
						
						if (distanceTo(AltarClickTile) <= random) {
							if (!tileOnScreen(AltarClickTile)) {
								setCameraRotation(random(248, 215));
								
							}

							if (!atTile(AltarClickTile, "Enter")) {
								atTile(AltarClickTile, "Enter");

							}

							cycle9++;

							if (cycle5 == 0) {
								setCameraRotation(random(335, 359 + 32));
								cycle5++;

							}

							if (getMyPlayer().isMoving() && cycle9 >= 1) {
								wait(random(200, 300));

							}

							//setCameraAltitude(0);
							random = random(6, 7);

							}

						} else if (distanceTo(AltarClickTile) > 15 && distanceTo(InsideAltarClickTile) >= 20) {
							if (distanceTo(InsideAltarClickTile) >= 20) {
								walkPath(PathToObselisk);
								waitToMove(1000);
								
							}

						}

				}catch (final Exception e){
					log("Avoided: Nullpointer at case WALKTOBANK");
					return(random(20,30));
					
				}
				
				break;
		
			case WALKTOALTAR:
				try {
					testbank = 0;
					cycle8 = 0;
					check5 = 0;
					cycle3 = 0;
					cycle12 = 0;

					if (distanceTo(AltarClickTile) <= (15)) {

						if (!tileOnScreen(AltarClickTile)) {

							setCameraRotation(random(248, 215));
								
						}

						if (distanceTo(AltarClickTile) <= random) {

							if (!tileOnScreen(AltarClickTile)) {

								setCameraRotation(random(248, 215));
									
							}

							if (!atTile(AltarClickTile, "Enter")) {
								atTile(AltarClickTile, "Enter");
								
							}

							cycle9++;
								
							if (cycle5 == 0) {

								setCameraRotation(random(335, 359 + 32));
								cycle5++;
								
							}

							if (getMyPlayer().isMoving() && cycle9 >= 1) {
								wait(random(200, 300));
								
							}

							//setCameraAltitude(0);
							random = random(6, 7);
							
							}

						} else if (distanceTo(AltarClickTile) > 15 && distanceTo(InsideAltarClickTile) >= 20) {
							if (distanceTo(InsideAltarClickTile) >= 20) {
								walkPath(PathToAltar);
								waitToMove(1000);
							
							}
							
						}
					
					walkPath(PathToAltar);
					waitToMove(1000);
												
				}catch (final Exception e){
					log("Avoided: Nullpointer at case WALKTOALTAR");
					return(random(20,30));
					
				}
				
				break;
				
			case CLICKOBSELISK:
				try {
					testbank = 0;
					cycle8 = 0;
					check5 = 0;
					cycle3 = 0;
					cycle12 = 0;
					
					if (distanceTo(obseliskTile) >= 15) {
						walkPath(PathToClickObselisk);
						waitToMove(1000);
						
					} else {
						
						RSObject Obselisk = getNearestObjectByID(ObseliskID);
						RSTile obseliskClickTile = Obselisk.getLocation();
						if (!tileOnScreen(obseliskClickTile)) {
							setCameraRotation(random(248, 215));
								
						}
						
						atTile(obseliskClickTile, "Renew");
						RandomSummon = random(15, 30);
						wait(random(600, 800));
						
					}	
					
				}catch (final Exception e){
					log("Avoided: Nullpointer at case CLICKOBSELISK");
					return(random(20,30));
					
				}
				
				break;
		
			case CLICKALTAR:
				try {
					//setCameraAltitude(0);					
					if (!getMyPlayer().isMoving() && !tileOnScreen(AltarClickTile)) {
						setCameraRotation(random(248, 215));
							
					}
						
					if (!atTile(AltarClickTile, "Enter"));
					cycle9++;
						
					if (cycle5 == 0) {
						setCameraRotation(random(335, 359 + 32));
						cycle5++;
						
					}
					
					if (getMyPlayer().isMoving() && cycle9 >= 1) {
						wait(random(200, 300));
						
					}
					//setCameraAltitude(0);
					
				}catch (final Exception e){
					log("Avoided: Nullpointer at case CLICKALTAR");
					return(random(20,30));
					
				}
				
				break;
		
			case CRAFTNATS:
				try {		
					cycle9 = 0;
					check1 = 0;
					if (!tileOnScreen(InsideAltarClickTile)) {
						setCameraRotation(random(335, 359 + 32));
						
					}
					
					if (tileOnScreen(InsideAltarClickTile) && getInventoryCount(PureEssID) >= 20 && getMyPlayer().getAnimation() != 791 
							&& !getMyPlayer().isMoving() && cycle2 == 0) {
						atTile(InsideAltarClickTile, "Craft");
						wait(random(500, 800));
						
					}
					
					wait(random(1000, 1200));
					if (getInventoryCount(PureEssID) <= 20 && !getMyPlayer().isMoving() && getMyPlayer().getAnimation() != 791) {
						cycle2++;
						
					}
					
					if (getMyPlayer().getAnimation() != 791 && !getMyPlayer().isMoving() && cycle2 >= 1 && check4 == 0 && testcraft >= 1) {
						testcraft = 0;
						if (getInventoryCount(BrokenPouchID3) >= 1) {
							atInventoryItem(BrokenPouchID3, "Empty");
							
						}
						
						if (getInventoryCount(BrokenPouchID1) >= 1) {
							atInventoryItem(BrokenPouchID1, "Empty");
							
						}
						
						if (getInventoryCount(BrokenPouchID2) >= 1) {
							atInventoryItem(BrokenPouchID2, "Empty");
							
						}
						
						atInventoryItem(GiantPouchID, "Empty");
						atInventoryItem(LargePouchID, "Empty");
						atInventoryItem(MediumPouchID, "Empty");
						atInventoryItem(SmallPouchID, "Empty");
						
						atTile(InsideAltarClickTile, "Craft");
						testcraft2 = 1;
						wait(random(600, 800));
					
					}
					
				}catch (final Exception e){
					log("Avoided: Nullpointer at case CRAFTNATS");
					return(random(20,30));
					
				}
				
				break;
		
			case TELEPORTBANK:
				try {
					cycle5 = 0;
					CalcNats = getInventoryItemByID(NatureRuneID).getStackSize();
					if (TotalNats != 0 && check5 != 1) {
						TotalNats = CalcNats + TotalNats;
						check5++;
					
					} else {
						if (check5 != 1) {
						TotalNats = CalcNats;
						check5++;
						
						}
						
					}
					
					if (getMyPlayer().getAnimation() != 9603) {
						openTab(Constants.TAB_EQUIPMENT);
						if (equipmentContainsOneOf(2552)) {
							DuelRing = 2552;
							
						}
						
						if (equipmentContainsOneOf(2554)) {
							DuelRing = 2554;
							
						}
						
						if (equipmentContainsOneOf(2556)) {
							DuelRing = 2556;
							
						}
						
						if (equipmentContainsOneOf(2558)) {
							DuelRing = 2558;
							
						}
						
						if (equipmentContainsOneOf(2560)) {
							DuelRing = 2560;
							
						}
						
						if (equipmentContainsOneOf(2562)) {
							DuelRing = 2562;
							
						}
						
						if (equipmentContainsOneOf(2564)) {
							DuelRing = 2564;
							
						}
						
						RSInterfaceChild[] equip = getEquipmentInterface().getChildren();
						for (int i = 0; i < 11; i++) {
							if (equip[i * 3 + 8].getComponentID() == DuelRing) {
								int x = equip[i * 3 + 8].getAbsoluteX() + 2;
								int y = equip[i * 3 + 8].getAbsoluteY() + 2;
								int width = equip[i * 3 + 8].getWidth() - 2;
								int height = equip[i * 3 + 8].getHeight() - 2;
								if (getMouseLocation().getX() <= x | getMouseLocation().getX() >= width | getMouseLocation().getY() <= y 
										| getMouseLocation().getY() >= height) {
									moveMouse(new Point(random(x, x + width), random(y, y + height)));
								
								}
								
								if (getMouseLocation().getX() >= x | getMouseLocation().getX() <= width | getMouseLocation().getY() >= y 
										| getMouseLocation().getY() <= height) {
									atEquippedItem(DuelRing, "Castle");
									//setCameraAltitude(random(50, 70));
									
								}
							
							}
						
						}
						
					}
					
					if (getMyPlayer().getAnimation() == 9603) {
						setCameraRotation(random(180, 240));
						openTab(Constants.TAB_INVENTORY);
						testcraft2 = 0;
						
					}
					
				}catch (final Exception e){
					log("Avoided: Nullpointer at case TELEPORTBANK");
					return(random(20,30));
					
				}
				
				break;
				
			case TELEPORTGRAAHK:
				try {
					if (getMyPlayer().getAnimation() != 8939) {
						if (check3 == 0) {
							if (!getInterface(228).isValid()) {
								if (graahk() == false) {
									RSNPC Graahk = getNPCInteracting(GraahkID);
									atNPC(Graahk, "Interact Spirit graahk");
									waitForInterface(228, random(1000, 1500));
									//wait(random(700, 800));
							
								}
							
							}
							
						}
						
						if (check3 >= 1 && !getInterface(228).isValid()) {
							if (!getMyPlayer().isMoving()) {
								if (distanceTo(BankTile) <= 2) {
									//walkTo(randomizeTile(GraahkRetrietTile, 5, 5));
									randomizedTile();
									walkTo(randomizeTile(RandomBankTile, 1, 1));
									wait(random(2000, 2200));
								
								}
								
								RSNPC Graahk = getNPCInteracting(GraahkID);
								if (distanceTo(Graahk) <= 3) {
								atNPC(Graahk, "Interact Spirit graahk");
								waitForInterface(228, random(1000, 1500));
								//wait(random(700, 800));
								
								}
							
							}
							
						}
						
						if (getInterface(228).isValid()) {
							check3 = 0;
							atInterface(228, 3);
							wait(random(600, 800));
							
						}
						
					}
					
				}catch (final Exception e){
					log("Avoided: Nullpointer at case TELEPORTGRAAHK");
					return(random(20,30));
					
				}
				
				break;
				
			case WAIT:
				try {
				wait(random(150, 250));
				}catch (final Exception e){
					log("Avoided: Nullpointer at case WAIT");
					return(random(20,30));
				}
				
				break;
				
		}
		
		return random(150,250);
		
	}
	
	private boolean randomizedTile() {
		if (ran == 1) {
			RandomBankTile = new RSTile (2440, 3083);
			ran = random(1, 5);
			
		} else if (ran == 2) {
			RandomBankTile = new RSTile (2440, 3085);
			ran = random(1, 5);
			
		} else if (ran == 3) {
			RandomBankTile = new RSTile (2440, 3087);
			ran = random(1, 5);
		
		} else if (ran == 4) {
			RandomBankTile = new RSTile (2442, 3087);
			ran = random(1, 5);
	
		} else if (ran == 5) {
		RandomBankTile = new RSTile (2444, 3087);
		ran = random(1, 5);
	
		}
		
		return false;
		
		}
	
	private boolean repairPouches() {
		if (getInventoryCount(BrokenPouchID1) != 0 |  getInventoryCount(BrokenPouchID2) != 0 |  getInventoryCount(BrokenPouchID3) != 0 
				&& cycle12 == 0) {
			return true;
			
		} else
		
		return false;
		
	}
	
	private boolean replaceRing() {
		if (check >= 1) {
			return true;
			
		} else
		
		return false;
		
	}
	
	private boolean graahkDisappeared() {
		if (check2 >= 1 | getSetting(448) != 12810 && cycle12 == 0) {
			return true;
			
		} else
		
		return false;
		
	}
	
	private boolean rechargeSummoning() {
		CurrentSum = getInterface(747, 5).getText();
		CurrentSum2 = Integer.parseInt(CurrentSum);
		
		if (CurrentSum2 <= RandomSummon) {
			return true;
			
		} else
		
		return false;
		
	}

	private boolean talk() {
		if (getInterface(64).isValid() | getInterface(65).isValid() |  getInterface(230).isValid() 
		| getInterface(242).isValid() | getInterface(243).isValid() | getInterface(228, 2).containsText("Can you repair my pouches?")) {
			return true;
		
		} else
		return false;
		
	}
	
	private boolean graahk() {
		if (check3 >= 1) {
			return true;
			
		}
		
		return false;
		
	}
	
	private boolean teleported() {
		if (check1 != 0 | distanceTo(GraahkTeleportTile) <= 3 && distanceTo(InsideAltarClickTile) >= 20) {
			check1++;
			return true;
			
		}
		
		return false;
		
	}
	
	private enum State {
		STOCK, CLICKOBSELISK, WAIT, TELEPORTBANK,
		FILLINVENTORY, WALKTOOBSELISK, WALKTOALTAR,
		CLICKALTAR, CRAFTNATS, TELEPORTGRAAHK
		
		}
	
	private State getState() {
		if (distanceTo(BankTile) <= 10 && repairPouches() == true | replaceRing() == true | graahkDisappeared() == true){
			Status = "Pouches/Ring/Summon";
			return State.STOCK;
			
		} else if (distanceTo(BankTile) <= 10 && graahkDisappeared() == false && replaceRing() == false
				&& repairPouches() == false  && testbank == 0){
			Status = "FillInventory";
			return State.FILLINVENTORY;
			
		} else if (distanceTo(BankTile) <= 20 && graahkDisappeared() == false && replaceRing() == false
				&& cycle6 == 0 && getInventoryCount(PureEssID) >= 20 && testbank >= 1){
			Status = "TeleportGraahk";
			return State.TELEPORTGRAAHK;
			
		/*} else if (distanceTo(AltarClickTile) <= 8){
			Status = "ClickAltar";
			return State.CLICKALTAR;*/
			
		} else if (distanceTo(InsideAltarTile) <= 10 && testcraft2 == 0){
			Status = "CraftNats";
			return State.CRAFTNATS;
			
		} else if (distanceTo(InsideAltarTile) <= 20 && !inventoryContains(PureEssID) && check4 >= 1){
			Status = "TeleportBank";
			return State.TELEPORTBANK;
			
		} else if (teleported() == true | distanceTo(obseliskTile) <= 15 && rechargeSummoning() == true){
			Status = "ClickObselisk";
			return State.CLICKOBSELISK;
			
		} else if (teleported() == true | distanceTo(AltarClickTile) <= 8 && distanceTo(InsideAltarClickTile) >= 15  && rechargeSummoning() == false){
			Status = "WalkToObselisk";
			return State.WALKTOOBSELISK;
			
		} else if (distanceTo(obseliskTile) <= 15 | distanceTo(AltarClickTile) <= 8 && rechargeSummoning() == false){
			Status = "WalkToAltar";
			return State.WALKTOALTAR;
		
		}
		
		return State.WAIT;
		
	}

	@Override
	public void serverMessageRecieved(ServerMessageEvent e) {
		final String message = e.getMessage();
	    if (message.contains("Your ring of duelling has one use left.")) {
	      check++;
	      DuelRing = 2552;
	      
	    }
	    
	    if (message.contains("Your ring of duelling has two uses left.")) {
		      DuelRing = 2564;
		      
		}
	    
	    if (message.contains("Your ring of duelling has three uses left.")) {
		      DuelRing = 2562;
		      
		}
	    
	    if (message.contains("Your ring of duelling has four uses left.")) {
		      DuelRing = 2560;
		      
		}
	    
	    if (message.contains("Your ring of duelling has five uses left.")) {
		      DuelRing = 2558;
		      
		}
	    
	    if (message.contains("Your ring of duelling has six uses left.")) {
		      DuelRing = 2556;
		      
		}
	    
	    if (message.contains("Your ring of duelling has seven uses left.")) {
		      DuelRing = 2554;
		      
		}
	    	
	    if (message.contains("<col=7f0000>You have 1 minute before your familiar vanishes.")) {
	    	check2++;
	    	
	    }
	    
	    if (message.contains("You don't have enough inventory space")) {
	    	check6++;
	    	
	    }
	    
	    if (message.contains("You Summon")) {
	    	check2 = 0;
	    	
	    }

	    if (message.contains("This is not your familiar.")) {
	    	check3++;
		
		}
	    
	    if (message.contains("Your pouch has no essence left in it.") ||
	        (message.contains("There are no essences in your pouch."))) {
	    	check4++;
	    	
	    }
	    
	}
	
	//START PAINT

	@Override
	public void onRepaint(final Graphics g) {
		//if(usePaint.equals("On (High Quality)")) {
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
	        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    
		//}
	
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
	
    final int XPToLVL = skills.getXPToNextLevel(STAT_RUNECRAFTING);
	final int XPChange = skills.getCurrentSkillExp(STAT_RUNECRAFTING) - startXP;
	//final int TabChange = XPChange / XP;
	final int NatChange = TotalNats;
	final int LevelChange = skills.getCurrentSkillLevel(STAT_RUNECRAFTING) - startLevel;
	final int Profit = ProfitCalc;
	final float totalMin = hours * 60 * 60 + minutes * 60 + seconds;
	final float perHourXP = XPChange / (totalMin / 60);
			
	//if (!usePaint.equals("Off")) {
		if (isLoggedIn()) {		
			PaintSwitch = paintTab();
		
			switch (PaintSwitch) {
		
			//Give credit if you are going to use this -FiLThY_FreaK_
		
			case 0: //MAIN TAB
				g.setColor(new Color(0, 0, 0, 150));
				g.fillRect(545, 430, 40, 30); //Main Tab
				g.fillRect(595, 430, 40, 30); //Stats Tab
				g.fillRect(645, 430, 40, 30); //Script Info
				g.fillRect(697, 430, 40, 30); //Paint Off
				g.setColor(Color.white);
				g.drawString("Main", 552, 450);
				g.drawString("Stats", 600, 450);
				g.drawString("Info", 657, 450);
				g.drawString("Hide", 704, 450);
				g.setColor(new Color(0, 0, 0, 150));
				g.fillRect(545, 250, 192, 170); //737
				g.fillRect(545, 420, 40, 10);
				g.setColor(Color.white);
				g.setFont(new Font("dialog", Font.BOLD, 14));
				g.drawString("Freak's Graahk Crafter", 565, 270);
				g.setFont(new Font("dialog", Font.PLAIN, 12));
				g.drawString("Runtime:", 550, 295);
				g.drawString(hours + " hours " + minutes + " minutes " + seconds + " seconds.", 550, 310);
				g.drawString("Status: " + Status, 550, 330);
				g.drawString("Nats made: " + NatChange, 550, 345);
				if (NatChange != 0) {
					final int finalProfit = NatChange * Profit;
					final float perHourNat = NatChange / (totalMin / 60);
					final float perHourProfit = finalProfit / (totalMin / 60);
					g.drawString("Profit made: " + finalProfit, 550, 360);
					g.drawString("Profiting " + perHourProfit * 60 + "GP P/H", 550, 375);
					g.drawString("Averaging " + perHourNat * 60 + " nats P/H.", 550, 390);
					g.drawString("Averaging " + perHourXP * 60 + " XP P/H.", 550, 405);
					
				} else {
					g.drawString("Profit made: 0", 550, 360);
					g.drawString("Profiting 0 GP P/H", 550, 375);
					g.drawString("Averaging 0 nats P/H.", 550, 390);
					g.drawString("Averaging 0 XP P/H.", 550, 405);
					
				}
			
				break;
			
			case 1: //STATS TAB
				g.setColor(new Color(0, 0, 0, 150));
				g.fillRect(545, 430, 40, 30); //Main Tab
				g.fillRect(595, 430, 40, 30); //Stats Tab
				g.fillRect(645, 430, 40, 30); //Script Info
				g.fillRect(697, 430, 40, 30); //Paint Off
				g.setColor(Color.white);
				g.drawString("Main", 552, 450);
				g.drawString("Stats", 600, 450);
				g.drawString("Info", 657, 450);
				g.drawString("Hide", 704, 450);
				g.setColor(new Color(0, 0, 0, 150));
				g.fillRect(545, 250, 192, 170); //737
				g.fillRect(595, 420, 40, 10);
				g.setColor(Color.white);
				g.setFont(new Font("dialog", Font.BOLD, 14));
				g.drawString("Freak's Graahk Crafter", 565, 270);
				g.setFont(new Font("dialog", Font.PLAIN, 12));
				//g.drawString("Butler: " + ButlerName, 550, 295);
				//g.drawString("Bank Trips: " + BankTrips, 550, 310);
				g.drawString("Current RuneCrafting lvl: " + skills.getCurrentSkillLevel(STAT_RUNECRAFTING), 550, 325);
				g.drawString("Gained " + LevelChange + " RuneCrafting levels.", 550, 340);
				g.drawString("Gained " + XPChange + " XP.", 550, 355);
				g.drawString("We are " + XPToLVL + " XP away from", 550, 370);
				g.drawString("your next RuneCrafting level.", 550, 385);
				g.setColor(Color.red);
				g.fillRect(550, 390, 100, 11);
				g.setColor(Green);
				g.fillRect(550, 390, skills.getPercentToNextLevel(STAT_RUNECRAFTING), 11);
				g.setColor(Color.white);
				g.drawString(skills.getPercentToNextLevel(STAT_RUNECRAFTING) + "%", 595, 400);
			
				break;
			
			case 2: //SCRIPT INFO
				g.setColor(new Color(0, 0, 0, 150));
				g.fillRect(545, 430, 40, 30); //Main Tab
				g.fillRect(595, 430, 40, 30); //Stats Tab
				g.fillRect(645, 430, 40, 30); //Script Info
				g.fillRect(697, 430, 40, 30); //Paint Off
				g.setColor(Color.white);
				g.drawString("Main", 552, 450);
				g.drawString("Stats", 600, 450);
				g.drawString("Info", 657, 450);
				g.drawString("Hide", 704, 450);
				g.setColor(new Color(0, 0, 0, 150));
				g.fillRect(545, 250, 192, 170); //737
				g.fillRect(645, 420, 40, 10);
				g.setColor(Color.white);
				g.setFont(new Font("dialog", Font.BOLD, 14));
				g.drawString("Freak's Graahk Crafter", 565, 270);
				g.setFont(new Font("dialog", Font.PLAIN, 12));
				g.drawString("Author: FiLThY_FreaK_", 550, 295);
				g.drawString("Version: 1.1", 550, 310);
				g.drawString("Website: www.teamaussie.net", 550, 325);
				g.drawString("Catagory: RuneCrafting", 550, 340);
			
				break;
			
			case 3: //PAINT OFF
				g.setColor(new Color(0, 0, 0, 150));
				g.fillRect(697, 430, 40, 30); //Paint On
				g.setColor(Color.white);
				g.drawString("Show", 704, 450);
			
				break;
			
			case 4: //PAINT ON
				g.setColor(new Color(0, 0, 0, 150));
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
	
	//}
	
private int paintTab() { //Half credit to Taha
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

//END PAINT
//START WALKING + CLICKING
	
	private RSNPC getNPCInteracting(final int... ids) {
	    for(final RSNPC npc : getNPCArray(false)) {
	        if (npc == null || !getMyPlayer().equals(npc.getInteracting())) continue;
	        for(final int id : ids) {
	            if (npc.getID() == id) {
	                return npc;
	                
	            }
	            
	        }
	        
	    }

	    return null;
	    
	}
	
	//Credits to RuneSpeed for walking methods <3
	
	private RSTile checkTile(final RSTile tile) {
		if (tileOnMap(tile)) {
			return tile;
		}
		
		final RSTile loc = getMyPlayer().getLocation();
		final RSTile walk = new RSTile((loc.getX() + tile.getX()) / 2, (loc
				.getY() + tile.getY()) / 2);
		return tileOnMap(walk) ? walk : checkTile(walk);
		
	}
	
	private int start(final RSTile[] path) {
		int start = 0;
		for (int a = path.length - 1; a > 0; a--) {
			if (tileOnMinimap(path[a])) {
				start = a;
				break;
				
			}
			
		}
		
		return start;
		
	}
	
	private int waitForIdle(int timeout) {
		long start = System.currentTimeMillis();
		RSPlayer myPlayer = getMyPlayer();
		int anim = 1;

		while (System.currentTimeMillis() - start < timeout) {
			if ((anim = myPlayer.getAnimation()) == -1) {
				break;
				
			}
			
			wait(15);
		}
		
		return anim;
		
	}
	
	private int waitForEss(int timeout) {
		long start = System.currentTimeMillis();
		int anim = 1;

		while (System.currentTimeMillis() - start < timeout) {
			if (check6 != 0) {
				check6 = 0;
				break;
				
			}
			
			wait(15);
		}
		
		return anim;
		
	}
	
	private int waitForInterface(int id, int timeout) {
		long start = System.currentTimeMillis();
		int anim = 1;

		while (System.currentTimeMillis() - start < timeout) {
			if (RSInterface.getInterface(id).isValid()) {
				break;
				
			}
			
			wait(15);
		}
		
		return anim;
		
	}
	
	private int waitForInventoryItemDisapear(int id, int timeout) {
		long start = System.currentTimeMillis();
		int anim = 1;

		while (System.currentTimeMillis() - start < timeout) {
			if (getInventoryCount(id) == 0) {
				break;
				
			}
			
			wait(15);
		}
		
		return anim;
		
	}
	
	private int waitForInventoryItem(int id, int timeout) {
		long start = System.currentTimeMillis();
		int anim = 1;

		while (System.currentTimeMillis() - start < timeout) {
			test1++;
			if (getInventoryCount(id) != 0) {
				test1 = 0;
				break;
				
			}
			
			wait(15);
		}
		
		return anim;
		
	}

	private boolean tileOnMinimap(final RSTile tile) {
		final Point p = tileToMinimap(tile);
		return Math.sqrt(Math.pow(627 - p.x, 2) + Math.pow(85 - p.y, 2)) < random(
				60, 74);
		
	}
	
	private void clickObject(RSObject obj, String action) {
		moveMouse(getModelPoint(obj));
		List<String> menu = getMenuActions();
		if (menu.get(0).contains(action)) {
			clickMouse(true);
			
		} else {
			
			clickMouse(false);
			atMenu(action);
			
		}
		
	}
	
	private Point getModelPoint(RSObject obj) {
		if (obj == null)
			return null;
		ArrayList<Point> possible = new ArrayList<Point>();
		Model model = obj.getModel();
		RSAnimable animable = (RSAnimable) obj.getObject();
		Point pointObject = Calculations.tileToScreen(obj.getLocation());
		Point[] screenCoords = new Point[model.getXPoints().length];
		for (int i = 0; i < screenCoords.length; i++) {
			int x = model.getXPoints()[i] + animable.getX();
			int z = model.getZPoints()[i] + animable.getY();
			int y = model.getYPoints()[i]
					+ Calculations.tileHeight(animable.getX(), animable.getY());
			screenCoords[i] = Calculations.w2s(x, y, z);

			if (pointDistance(pointObject, screenCoords[i], 14)) {
				possible.add(screenCoords[i]);
				
			}
			
		}
		
		if (!possible.isEmpty())
			return possible.get(random(0, possible.size()));

		return new Point(-1, -1);
		
	}

	private boolean pointDistance(Point p, Point n, int dis) {
		int x = p.x;
		int y = p.y;
		int x1 = n.x;
		int y1 = n.y;

		int realDistance = (Math.abs(x - x1) + Math.abs(y - y1));
		if (realDistance <= dis)
			return true;
		return false;
		
	}
	
	private boolean arrayContains(String[] items, String searchString) {
		for (String item : items) {
			if (item.equalsIgnoreCase(searchString))
				return true;
			
		}
		
		return false;
		
	}
	
	@Override
	public boolean atMenu(String[] items) {
		for (String target : getMenuItems()) {
			if (arrayContains(items, target))
				return atMenu(target);
			
		}
		
		while (Bot.getClient().isMenuOpen()) {
			wait(random(500, 1000));
			
		}
		
		return false;
		
	}

	private boolean walkPath(final RSTile[] path) {
		for (int i = start(path); i < path.length; i++) {
			if (!isRunning() && getEnergy() > random(40, 60)) {
				clickMouse(random(707, 762), random(90, 121), true);
				
			}

			if (distanceTo(InsideAltarClickTile) <= 20) {
				return false;
				
			}

			walkTo(randomizeTile(path[i], 1, 1));
			waitToMove(2000);
			if (path[i] == path[path.length - 1]) {
				break;
				
			}

			while (!tileOnMinimap(path[i + 1])) {
				if (!getMyPlayer().isMoving()) {
					walkTo(checkTile(randomizeTile(path[i + 1], 1, 1)));
					
				}

			}

		}

		return distanceTo(path[path.length - 1]) <= 4;
	}

}
	
