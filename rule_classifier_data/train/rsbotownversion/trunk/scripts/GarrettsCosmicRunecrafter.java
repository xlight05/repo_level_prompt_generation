import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Map;

import org.rsbot.bot.Bot;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.script.Calculations;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.Skills;
import org.rsbot.script.wrappers.RSInterface;
import org.rsbot.script.wrappers.RSTile;

@ScriptManifest(
    authors = "Garrett", 
    category = "Runecraft", 
    name = "Garrett's Cosmic Runecrafter",
    version = 1.04/*version number*/,
    description ="<head></head><body>" +
        "<center><strong><h2>Garrett's Cosmic Runecrafter</h2></strong></center>" +
        "<center>Start the script in Zanaris bank.</center>" +
        "<center>You need to have cosmic tiara equipped and pure essence visible.</center>" +
        "<center>Fixed by KingCold999</center>")

public class GarrettsCosmicRunecrafter extends Script implements PaintListener {
    
    //OTHER VARIABLES
    private long scriptStartTime;
    private int startLvl;
    private int failCount = 0;
    private int runesCrafted = 0;
    private int runEnergy = random(50, 80);
    private int startXP;
    private boolean gotSkillLvl = false;
    private boolean tiaraCheck = false;
    private boolean talismanCheck = false;
    private boolean doneCheck = false;
    private int randomSelection = random(0, 4);
    int currentPathID = 0;
    int talismanUsed = 13649;
    //ITEM ID
    private int pureEssence = 7936;
    private int cosmicRune = 564;
    private int cosmicTiara = 5539;
    private int cosmicTalisman = 1454;
    private int omniTiara = 13655;
    private int omniTalisman = 13649;
	
	//Banker
	int banker = 498;
    
    //PATHS
    private RSTile path[] = {new RSTile(2385, 4458), new RSTile(2393, 4453), new RSTile(2400, 4449), new RSTile(2410, 4444), new RSTile(2414, 4433), new RSTile(2419, 4424), new RSTile(2419, 4413), new RSTile(2410, 4406), new RSTile(2399, 4407), new RSTile(2386, 4408), new RSTile(2380, 4401), new RSTile(2388, 4396), new RSTile(2400, 4392), new RSTile(2406, 4383), new RSTile(2408, 4379)};
    private RSTile bankToRuins[] = {new RSTile(2384, 4459), new RSTile(2390, 4451), new RSTile(2399, 4447), new RSTile(2409, 4442), new RSTile(2412, 4431), new RSTile(2414, 4422), new RSTile(2418, 4412), new RSTile(2410, 4407), new RSTile(2401, 4407), new RSTile(2392, 4408), new RSTile(2383, 4410), new RSTile(2383, 4399), new RSTile(2393, 4395), new RSTile(2403, 4391), new RSTile(2403, 4382), new RSTile(2406, 4378)};
    private RSTile paths[][] = {path, bankToRuins};
    
    //TILES
    private RSTile ruins[] = {new RSTile(2408, 4378), new RSTile(2408, 4377), new RSTile(2408, 4376), new RSTile(2409, 4377), new RSTile(2407, 4377)};
    private RSTile altar[] = {new RSTile(2142, 4832), new RSTile(2142, 4833), new RSTile(2142, 4834), new RSTile(2143, 4833), new RSTile(2141, 4833)};
    private RSTile portal[] = {new RSTile(2142, 4812), new RSTile(2163, 4833), new RSTile(2142, 4854), new RSTile(2121, 4833)};
    private RSTile theBank[] = {new RSTile(2385, 4459), new RSTile(2382, 4457), new RSTile(2385, 4457), new RSTile(2385, 4460)};

    //ENUM
    private final int WALKTOALTAR = 1;
    private final int GETINTOALTAR = 2;
    private final int CRAFTRUNES = 3;
    private final int LEAVERUINS = 4;
    private final int WALKTOBANK = 5;
    private final int OPENBANK = 6;
    private final int BANK = 7;
    private int ACTION = WALKTOBANK;
    

    
    //*******************************************************//
    // ON START
    //*******************************************************//
    public boolean onStart( Map<String,String> args ) {
        
        scriptStartTime = System.currentTimeMillis();
        
        Bot.getEventManager().addListener( PaintListener.class, this );
        return true;
    }
    
    //*******************************************************//
    // MAIN LOOP
    //*******************************************************//
    public int loop() {
        
        if(isLoggedIn() && !gotSkillLvl) {
            startLvl = skills.getCurrentSkillLevel(20);
            startXP = skills.getCurrentSkillExp(20);
            gotSkillLvl = true;
            return random(50,150);
        }
        
        if(isLoggedIn() && !doneCheck) {
            if (equipmentContains(cosmicTiara) || equipmentContains(omniTiara)) {
                tiaraCheck = true;
                doneCheck = true;
                return random(50,150);
            } else if (inventoryContains(cosmicTalisman)) {
                talismanUsed = cosmicTalisman;
                talismanCheck = true;
                doneCheck = true;
                return random(50,150);
            } else if(inventoryContains(omniTalisman)) {
                talismanCheck = true;
                doneCheck = true;
                return random(50,150);
            } else {
                log("You need to have a cosmic tiara or omni tiara equipped, or have a cosmic or omni talisman.");
                wait(10000);
                return random(50,150);
            }
        }

        if (energyCheck()) {
            setRun(true);
            wait(random(750,1000));
            return random(50, 150);
        }
        
        doCosmicCrafting(false);
        
        return random(50,150);
    }
    
    //*******************************************************//
    // OTHER METHODS
    //*******************************************************//
    private void doCosmicCrafting(boolean abyssal) {
        int randomTile = random(0,5);
        if (abyssal) {
            
        } else {
            if (ACTION == WALKTOALTAR) {
                if (playerInArea(2418,4383,2401,4371)) {
                    ACTION = GETINTOALTAR;
                    return;
                }
                if (playerInArea(2165,4856,2119,4810)) {
                    ACTION = CRAFTRUNES;
                    return;
                }
                walkPath(bankToRuins, false);
                return;
            }
            if (ACTION == GETINTOALTAR) {
                if (playerInArea(2165,4856,2119,4810)) {
                    ACTION = CRAFTRUNES;
                    return;
                }
                if (distanceTo(ruins[randomTile]) <= 5) {
                    if (!getMyPlayer().isMoving()) {
                        if(talismanCheck) {
                            atInventoryItem(talismanUsed, "Use");
                            onTile(ruins[randomTile], "Use");
                            wait(random(400,600));
                            return;
                        } else {
                            onTile(ruins[randomTile], "Enter");
                            wait(random(400,600));
                            return;
                        }
                    } else antiBan();
                } else {
                    if (!getMyPlayer().isMoving()) {
                        walkTileMM(ruins[randomTile]);
                        wait(random(150,300));
                        return;
                    } else antiBan();
                }
                return;
            }
            if (ACTION == CRAFTRUNES) {
                if (getInventoryCount(cosmicRune) != 0 || getInventoryCount(pureEssence) == 0) {
                    ACTION = LEAVERUINS;
                    return;
                }
                Point location = Calculations.tileToScreen(altar[randomTile]);
                if (pointOnScreen(location)) {
                    if (!getMyPlayer().isMoving()) {
                        int mult = multiplyVal();
                        int invcount = getInventoryCount(pureEssence);
                        if (onTile(altar[randomTile], "Craft-rune")) {
                            int fs = 0;
                            while(fs<10) {
                                wait(random(200, 500));
                                if(inventoryContains(cosmicRune)) {
                                    runesCrafted += getInventoryCount(true);
                                    if(talismanCheck) {runesCrafted--;}
                                    break;
                                }
                                fs++;
                            }
                            if(fs>=10) {log("Failed to count cosmics. error?");}
                        }
                        wait(random(400,600));
                        return;
                    } else antiBan();
                } else {
                    if (!getMyPlayer().isMoving()) {
                        if (!walkTileMM(altar[randomTile])) {
                            if(playerInArea(2144, 4856, 2140, 4852)) {
                                //north
                                walkTileMM(new RSTile(2142 + random(-1, 2), 4844 + random(-1, 2)));
                            }
                            if(playerInArea(2144, 4814, 2140, 4810)) {
                                //south
                                walkTileMM(new RSTile(2142 + random(-1, 2), 4822 + random(-1, 2)));
                            }
                            if(playerInArea(2123, 4835, 2119, 4831)) {
                                //west
                                walkTileMM(new RSTile(2132 + random(-1, 2), 4833 + random(-1, 2)));
                            }
                            if(playerInArea(2165, 4835, 2161, 4831)) {
                                //east
                                walkTileMM(new RSTile(2153 + random(-1, 2), 4833 + random(-1, 2)));
                            }
                        }
                        return;
                    } else antiBan();
                }
                return;
            }
            if (ACTION == LEAVERUINS) {
                if (playerInArea(2418,4383,2401,4371)) {
                    ACTION = WALKTOBANK;
                    return;
                }
                Point location = Calculations.tileToScreen(portal[randomSelection]);
                if (pointOnScreen(location)) {
                    if (!getMyPlayer().isMoving()) {
                        onTile(portal[randomSelection], "Enter");
                        wait(random(400,600));
                        return;
                    } else antiBan();
                } else {
                    if (!getMyPlayer().isMoving()) {
                        if (!walkTileMM(portal[randomSelection]) || distanceTo(portal[randomSelection]) > 17) {
                            if (randomSelection == 0) {
                                //south
                                walkTileMM(new RSTile(2142 + random(-1, 2), 4822 + random(-1, 2)));
                            }
                            else if (randomSelection == 1) {
                                //east
                                walkTileMM(new RSTile(2153 + random(-1, 2), 4833 + random(-1, 2)));
                            }
                            else if (randomSelection == 2) {
                                //north
                                walkTileMM(new RSTile(2142 + random(-1, 2), 4844 + random(-1, 2)));
                            }
                            else if (randomSelection == 3) {
                                //west
                                walkTileMM(new RSTile(2132 + random(-1, 2), 4833 + random(-1, 2)));
                            }
                        }
                        return;
                    } else antiBan();
                }
                return;
            }
            if (ACTION == WALKTOBANK) {
                if(getInventoryCount(pureEssence) > 0)
                {
                    ACTION = WALKTOALTAR;
                    failCount = 0;
                    return;
                }
                Point location = Calculations.tileToScreen(theBank[random(0, theBank.length)]);
                if (pointOnScreen(location)) {
                    ACTION = OPENBANK;
                    return;
                }
                walkPath(bankToRuins, true);
                return;
            }
            if (ACTION == OPENBANK) {
                if(getInventoryCount(pureEssence) > 0)
                {
                    ACTION = WALKTOALTAR;
                    failCount = 0;
                    return;
                }
                if (bank.isOpen()) {
                    ACTION = BANK;
                    return;
                }
                openBank(theBank);
                wait(random(500,750));
            }
            if (ACTION == BANK) {
                if(getInventoryCount(pureEssence) > 0)
                {
                    bank.close();
                    wait(random(150,300));
                    ACTION = WALKTOALTAR;
                    failCount = 0;
                    return;
                }
                if (bank.isOpen()) {
                    if (getInventoryCount() != 0)
                        if(talismanCheck) {
                            bank.depositAllExcept(cosmicTalisman, omniTalisman);
                        } else {
                            bank.depositAll();
                        }
                    wait(random(750,1000));
                    if (bank.atItem(pureEssence, "Withdraw-All")) {
                        wait(random(750,1000));
                        failCount = 0;
                        return;
                    } else {
                        failCount++;
                        if (failCount >= 5)
                            stopAllScripts();
                        else
                            return;
                    }
                    return;
                }
                else ACTION = OPENBANK;
                return;
            }
        }
    }
    
    private int gEnergy() {
        return Integer.parseInt(RSInterface.getChildInterface(750, 5).getText());
    }
    
    private boolean energyCheck() {
        try {
            if (gEnergy() >= runEnergy && !isRunning()) {
                runEnergy = random(35, 65);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    
    private int multiplyVal() {
        int RCLevel = skills.getCurrentSkillLevel(20);
        if (RCLevel >= 59) 
            return 2;
        else
            return 1;
    }
    
    private void openBank(RSTile[] loc) {
        int randomTile = random(0,loc.length);
        Point location = Calculations.tileToScreen(loc[randomTile]);
        if (pointOnScreen(location)) {
            if (!getMyPlayer().isMoving()) {
                //onTile(loc[randomTile], "Bank Banker");
				atNPC(getNearestNPCByID(banker), "Bank", true);
                wait(random(400,600));
                return;
            } else antiBan();
        } else {
            if (!getMyPlayer().isMoving()) {
                if (!walkTileMM(loc[randomTile]))
                    walkTileMM(new RSTile(2392, 4454));
                return;
            } else antiBan();
        }
    }
    
    private boolean onTile(RSTile tile, String action) {
        try {
            Point location = Calculations.tileToScreen(tile);
            if (location.x == -1 || location.y == -1) return false;
            moveMouse(location, 3, 3);
            wait(random(250, 500));
            if (getMenuItems().get(0).toLowerCase().contains(action.toLowerCase())) {
                clickMouse(true);
                wait(random(250, 500));
            } else {
                clickMouse(false);
                if (!atMenu(action)) return false;
            }
            wait(random(200, 400));
            while (true) {
                if (!getMyPlayer().isMoving()) break;
                wait(random(100, 200));
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private void walkPath(RSTile[] path, boolean reverse) {
        if (!reverse) {
            if(!getMyPlayer().isMoving() || distanceTo(getDestination()) <= random(4, 7))
            {
                    walkPathMM(randomizePath(path, 2, 2), 17);
                    return;
            } else antiBan();
        }
        else {
            if(!getMyPlayer().isMoving() || distanceTo(getDestination()) <= random(4, 7))
            {
                    walkPathMM(randomizePath(reversePath(path), 2, 2), 17);
                    return;
            } else antiBan();
        }
    }
    
    public boolean playerInArea(int maxX, int maxY, int minX, int minY) { 
        int x = getMyPlayer().getLocation().getX(); 
        int y = getMyPlayer().getLocation().getY(); 
        if (x >= minX && x <= maxX && y >= minY && y <= maxY) 
        { 
            return true; 
        } 
        return false; 
    }
    
    private int antiBan() {
        int random = random(1, 28);

        switch (random) {
            case 1:
                int x = random(0, 750);
                int y = random(0, 500);
                if (random(1, 10) == 5)
                    moveMouse(0, 0, x, y);
                return random(1000, 1500);

            case 2:
                if (getCurrentTab() != TAB_INVENTORY) {
                    openTab(TAB_INVENTORY);
                    return random(500, 750);
                } else {
                    return random(500, 750);
                }

            case 3:
                if (random(1, 40) == 30)
                {
                    if (getMyPlayer().isMoving()) {
                        return random(750, 1000);
                    }
                    if (getCurrentTab() != TAB_STATS) {
                        openTab(TAB_STATS);
                    }
                    moveMouse(560, 420, 40, 20);
                    wait(random(3000, 6000));
                    return random(100, 200);
                }

            case 4:
                if (random(1, 3) == 2) {
                    int angle = getCameraAngle() + random(-90, 90);
                    if (angle < 0) {
                        angle = 0;
                    }
                    if (angle > 359) {
                        angle = 0;
                    }

                    setCameraRotation(angle);
                return random(500, 750);
                }
          }
        return 500;
    }

    //*******************************************************//
    // ON FINISH
    //*******************************************************//
    public void onFinish() {
        Bot.getEventManager().removeListener( PaintListener.class, this );
    }

    //*******************************************************//
    // PAINT SCREEN
    //*******************************************************//
    public void onRepaint(Graphics g) {
        if( g == null || !GarrettsCosmicRunecrafter.this.isLoggedIn() ){
            return;
        }
        if( !GarrettsCosmicRunecrafter.this.isActive ) {
            onFinish();
            return;
        }
        
        long runTime = System.currentTimeMillis() - scriptStartTime;
        String runTimeS =  TimeDisplay(runTime);
        
        if(getCurrentTab() == TAB_INVENTORY) {
            g.setColor(new Color(0, 0, 0, 175));    
            g.fillRoundRect(555, 210, 175, 250, 10, 10);
            g.setColor(Color.WHITE);
                
            int[] coords = new int[] {225, 240, 255, 270, 285, 300, 315, 330, 345, 360, 375, 390, 405, 420, 435, 450};
            double green = ((skills.getPercentToNextLevel(20))*164)/100;
            int lvlPerc = skills.getPercentToNextLevel(20);
            
            double hours = runTime/3600;
            
            int expGained = skills.getCurrentSkillExp(20) - startXP;
            int lvlGained = skills.getCurrentSkillLevel(20) - startLvl;
            int xpPH = (int)(3600000.0 / runTime * expGained);
            int cPH = (int)(3600000.0 / runTime * runesCrafted);
            
            g.drawString("Garrett's Cosmic Crafter v1.04", 561, coords[0]);
            g.drawString("Run Time: " + runTimeS, 561, coords[1]);
            g.drawString("Cosmic Runes: " + runesCrafted, 561, coords[2]);
            g.drawString("Cosmics/Hour: " + cPH, 561, coords[3]);
            g.drawString("Current Lvl: " + skills.getCurrentSkillLevel(20), 561, coords[5]);
            g.drawString("Lvls Gained: " + (lvlGained), 561, coords[6]);
            g.drawString("XP Gained: " + (expGained), 561, coords[7]);
            g.drawString("EXP/Hour: " + xpPH, 561, coords[8]);
            g.setColor(new Color(0, 0, 0, 220));
            g.fillRoundRect(560, coords[9], 166, 15, 4, 4);
            g.setColor(new Color(255, 0, 0, 175));
            g.fillRoundRect(561, coords[9]+1, 164, 13, 4, 4);
            g.setColor(new Color(0, 255, 0, 175));
            g.fillRoundRect(561, coords[9]+1, (int)(green), 13, 4, 4);
            g.setColor(Color.BLACK);
            g.drawString("% To Next: " + (100-lvlPerc), 565, coords[10]-3);
            g.drawString("Fixed by KingCold999", 561, coords[14]);
            
        }
    }
    //added methods
    public int invCount(final int InvItem) {
        return getInventoryCount(InvItem);
    }
    
    public String TimeDisplay(long millis) {
        long seconds = millis/1000;
        millis -= seconds*1000;
        long minutes = seconds/60;
        seconds-=minutes*60;
        long hours = minutes/60;
        minutes-=hours*60;
        String TimeStamp = ""+hours+":";
        if(minutes < 10) {TimeStamp=TimeStamp+0;}
        TimeStamp=TimeStamp+minutes+":";
        if(seconds < 10) {TimeStamp=TimeStamp+0;}
        TimeStamp=TimeStamp+seconds+":";
        if(millis < 100) {TimeStamp=TimeStamp+0;}
        if(millis < 10) {TimeStamp=TimeStamp+0;}
        TimeStamp=TimeStamp+millis;
        
        return TimeStamp;
    }
    
}