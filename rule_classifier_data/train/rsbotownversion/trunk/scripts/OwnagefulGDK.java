import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Map;
import org.rsbot.accessors.Node;
import org.rsbot.accessors.RSNPCNode;
import org.rsbot.bot.Bot;
import org.rsbot.bot.input.CanvasWrapper;
import org.rsbot.script.wrappers.RSCharacter;
import org.rsbot.script.wrappers.RSNPC;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.script.Calculations;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.Timer;
import org.rsbot.script.wrappers.RSInterfaceComponent;
import org.rsbot.script.wrappers.RSItemTile;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSTile;
import org.rsbot.util.ScreenshotUtil;

@ScriptManifest(authors = {"Ownageful"}, category = "Combat", name = "Ownageful Green Dragon Killer", version = 2.2, description = "<html><head><style type='text/css'> hr {color: white} p"
+ " {margin-left: 20px}</style></head><body><center><b><font size='4' color='red'>Ownageful GDK<"
+ "/font></b><br></center><center><table border='0'><tr><td colspan='2'><center><font size='4'><b>::"
+ " Initial Script Settings :</b>"
+ "</font><BR><font size='2' color='green'>Set rest of the settings in the GUI!</font></center>"
+ "<center><tr><td><b>Approximate Respawn Time (milliseconds).</b><tr><td><center>"
+ "<input type='text' name='arrowID' value=''></center><br></table></center></body></html>")
public class OwnagefulGDK extends Script implements PaintListener, ServerMessageListener {
    //dragon : 4677,941,4678

    public double version = 2.2;
    private boolean startScript;
    boolean useSupers = false;
    boolean useAnti = false;
    boolean eat = false;
    public int food = 7946;
    int withdraw;
    boolean ntele = false;
    boolean pstr, patt, panti, potted = false;
    int[] drags = {4677, 941, 4678};
    int[] geBoundry = {3141, 3486, 3199, 3516};
    int[] ditchBoundry = {3135, 3515, 3140, 3520};
    int[] wildBoundry = {3134, 3523, 3171, 3566};
    int[] dung1Boundry = {3290, 5472, 3304, 5480};
    int[] dungBoundry = {3284, 5462, 3296, 5471};
    int[] dungANTBoundry = {3298, 5483, 3304, 5487};
    int[] dungPOISONBoundry = {3282, 5473, 3286, 5475};
    int[] dBoundry = {3299, 5442, 3322, 5470};
    int[] nw = {3299, 5456, 3311, 5470};
    int[] sw = {3299, 5443, 3309, 5455};
    int[] ne = {3311, 5452, 3322, 5464};
    int[] se = {3311, 5442, 3322, 5451};
    int[] loot = {536, 1753};
    public RSTile[] toRope = new RSTile[]{new RSTile(3140, 3530), new RSTile(3147, 3538), new RSTile(3155, 3546), new RSTile(3161, 3554), new RSTile(3164, 3562)};
    public RSTile[] toPort = {new RSTile(3293, 5470), new RSTile(3289, 5462)};
    public RSTile[] toGE = {new RSTile(3207, 3435), new RSTile(3197, 3444), new RSTile(3187, 3454), new RSTile(3180, 3458), new RSTile(3168, 3466), new RSTile(3163, 3476), new RSTile(3161, 3489)};
    public RSTile[] toDitch = {new RSTile(3152, 3502), new RSTile(3143, 3513)};
    int holeAnim = 2599;
    int ditchAnim = 6132;
    public int ditch = 23271;
    public int hole = 9312;
    public int rope = 28892;
    public RSTile portal = new RSTile(3290, 5463);
    final int[] strengthPots = {2440, 157, 159, 161};
    final int[] attackPots = {2436, 145, 147, 149};
    final int[] antiPots = {2452, 2454, 2456, 2458};
    public int vTabs = 8007;
    RSNPC banker;
    private boolean nanti;
    Timer tse = new Timer(0);
    Timer tsw = new Timer(0);
    Timer tne = new Timer(0);
    Timer tnw = new Timer(0);
    public int rTime;
    String status;
    RSNPC[] gd;
    int[] loots = {536, 1753, 534, 1201, 207, 987, 985, 1213, 1163, 18778};
    String[] names = {"Dragon bones", "hide", "Baby", "Rune k", "Ran", "Loop", "Tooth", "Rune da", "Rune ful", "Ancient"};
    int[] prices = new int[10];
    public int[] charms = {12158, 12159, 12160, 12161, 12162, 12163, 12164, 12165, 12166, 12167};
    int hAmount = 0;
    int bAmount = 0;
    int profit;
    private long runTime;
    private long seconds;
    private long minutes;
    private long hours;
    private double profitPerSecond;
    private double totalLoot;
    public int startSTR, startDEF, startHP, startATT, startMAG, startRNG, STRPH, DEFPH, HPPH, ATTPH, RNGPH, MAGPH, STRpr, DEFpr, HPpr, ATTpr, RNGpr, MAGpr, currentSTR, currentDEF, currentHP, currentATT, currentMAG, currentRNG, currentSTRLVL, currentRNGLVL, currentMAGLVL, currentDEFLVL, currentHPLVL, currentATTLVL;
    public boolean usingFood = true;
    private boolean paintSTR = false;
    private boolean paintATT = false;
    private boolean paintDEF = false;
    private boolean paintHP = false;
    private boolean paintRNG = false;
    private boolean paintMAG = false;
    private long initialStartTime;
    private int bankRuns;
    private GDK gui;
    int hp;
    RSItemTile charmz;
    RSItemTile lootz;
    RSItemTile lootzd;
    boolean lCharms = false;
    boolean tabTaken;
    int tab;

    @Override
    protected int getMouseSpeed() {
        return random(6, 7);
    }

    @Override
    public boolean onStart(final Map<String, String> args) {
        initialStartTime = System.currentTimeMillis();
        if (isLoggedIn()) {
            startSTR = skills.getCurrentSkillExp(STAT_STRENGTH);
            startDEF = skills.getCurrentSkillExp(STAT_DEFENSE);
            startHP = skills.getCurrentSkillExp(STAT_HITPOINTS);
            startATT = skills.getCurrentSkillExp(STAT_ATTACK);
            startRNG = skills.getCurrentSkillExp(STAT_RANGE);
            startMAG = skills.getCurrentSkillExp(STAT_MAGIC);
            gui = new GDK();
            try {
                rTime = Integer.parseInt(args.get("arrowID").substring(0));
            } catch (NumberFormatException numberFormatException) {
                rTime = 32000;
            } catch (StringIndexOutOfBoundsException s) {
                rTime = 32000;
            }
            rTime = rTime + 5000;
            gui.setVisible(true);

            while (!startScript) {
                wait(10);
            }
            log("Loading prices");
            for (int i = 0; i < prices.length; i++) {
                prices[i] = grandExchange.loadItemInfo(loots[i]).getMaxPrice();
            }
            return true;
        } else {
            log("Please log in first.");
            return false;
        }

    }

    @Override
    public int loop() {

        if (inventoryContains(229)) {
            atInventoryItem(229, "Drop");
            waitr();

        }

        if (Bot.getClient().getCameraYaw() < 2500) {
            setCameraAltitude(true);
        }
        if (getEnergy() > random(20, 40)) {
            setRun(true);
        }

        if (ntele) {
            atInventoryItem(vTabs, "Break");
            wait(random(7000, 8500));
            walkTileMM(new RSTile(3208, 3435));
            waitToMove();
            ntele = false;
        }
        if (isLoggedIn()) {
            if (getMyPlayer().getHPPercent() < hp && !isInArea(dBoundry)
                    && getMyPlayer().isInCombat()) {
                if (isInArea(dungANTBoundry) || isInArea(dungPOISONBoundry) || isInArea(dung1Boundry) || isInArea(dungBoundry)) {
                    wait(random(100, 200));
                    if (getMyPlayer().getHPPercent() <= hp) {
                        atInventoryItem(vTabs, "Break");
                        wait(random(7000, 8500));
                        walkTileMM(new RSTile(3208, 3435));
                        waitToMove();
                        patt = false;
                        pstr = false;
                        panti = false;
                    }
                } else {
                    wait(random(2000, 3000));
                    if (inventoryContains(food)) {
                        atInventoryItem(food, "Eat");
                        wait(random(600, 700));
                    } else {
                        atInventoryItem(vTabs, "Break");
                        wait(random(7000, 8500));
                        walkTileMM(new RSTile(3208, 3435));
                        waitToMove();
                    }
                    patt = false;
                    pstr = false;
                    panti = false;
                }
            }
        }

        if (isInArea(geBoundry)) {
            if (!patt || !pstr || !panti) {
                status = "Banking";
                banker = getNearestNPCByID(6535);
                if (banker != null) {
                    if (banker.isOnScreen()) {
                        if (!bank.isOpen()) {
                            if (atNPC(banker, "Bank Banker")) {
                                if (waitToMove(1500)) {
                                    while (getMyPlayer().isMoving()) {
                                        wait(random(50, 100));
                                    }
                                }
                            }
                        } else {
                            if (!tabTaken) {
                                wait(random(2000, 2000));
                                tab = bank.getCurrentTab();
                                tabTaken = true;
                            } else {
                                if (bank.getCurrentTab() != tab) {
                                    bank.openTab(tab + 1);
                                    wait(random(2000, 3000));
                                }
                            }

                            if (bank.depositAllExcept(vTabs)) {
                                waitr();
                                wait(random(1000, 2000));
                                if (eat && bank.isOpen()) {
                                    while (!inventoryContains(food)) {
                                        if (bank.getCurrentTab() != tab) {
                                            bank.openTab(tab + 1);
                                            wait(random(2000, 3000));
                                        }
                                        bank.withdraw(food, withdraw);
                                        wait(random(1900, 2300));
                                    }
                                }
                                if (useSupers) {
                                    if (!pstr) {
                                        if (bank.getCurrentTab() != tab) {
                                            bank.openTab(tab + 1);
                                            wait(random(2000, 3000));
                                        }
                                        bank.withdraw(gap(strengthPots), 1);
                                        waitr();
                                        wait(random(1000, 2000));

                                    }
                                    if (!patt) {
                                        if (bank.getCurrentTab() != tab) {
                                            bank.openTab(tab + 1);
                                            wait(random(2000, 3000));
                                        }
                                        bank.withdraw(gap(attackPots), 1);
                                        waitr();
                                        wait(random(1000, 2000));
                                    }
                                } else {
                                    patt = true;
                                    pstr = true;
                                }
                                if (useAnti) {
                                    if (!panti) {
                                        if (bank.getCurrentTab() != tab) {
                                            bank.openTab(tab + 1);
                                            wait(random(2000, 3000));
                                        }
                                        bank.withdraw(gap(antiPots), 1);
                                        waitr();
                                        wait(random(1000, 2000));
                                    }
                                } else {
                                    panti = true;
                                }

                                if (bank.close()) {
                                    wait(random(600, 700));
                                    if (useSupers) {
                                        if (didPot(strengthPots)) {
                                            waiti();
                                        }
                                        pstr = true;
                                        if (didPot(attackPots)) {
                                            waiti();
                                        }
                                        patt = true;
                                    }
                                    if (useAnti) {
                                        if (didPot(antiPots)) {
                                            nanti = false;
                                            waiti();
                                        }
                                        panti = true;
                                    } else {
                                        nanti = false;
                                    }
                                    if (pstr && patt && panti) {
                                        if (useAnti || useSupers) {
                                            if (atNPC(banker, "Bank Banker")) {
                                                if (waitToMove(1500)) {
                                                    while (getMyPlayer().isMoving()) {
                                                        wait(random(50, 100));
                                                    }
                                                }
                                            }
                                            if (bank.isOpen()) {
                                                if (bank.getCurrentTab() != tab) {
                                                    bank.openTab(tab + 1);
                                                    wait(random(2000, 3000));
                                                }
                                                if (!inventoryContainsOneOf(antiPots[0], antiPots[1], antiPots[2], antiPots[3]) && useAnti) {
                                                    bank.depositAllExcept(vTabs, food);
                                                    bank.withdraw(gap(antiPots), 1);
                                                    wait(random(500, 600));
                                                } else {
                                                    bank.depositAllExcept(vTabs, food, antiPots[0], antiPots[1], antiPots[2], antiPots[3]);
                                                    wait(random(500, 600));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        walkTileMM(new RSTile(3162, 3487));
                        waitToMove();
                    }
                }
            } else {
                if (bank.isOpen()) {
                    bank.close();
                    wait(random(500, 1000));
                    if (isLoggedIn()) {
                        if (getMyPlayer().getHPPercent() <= 75) {
                            while (getMyPlayer().getHPPercent() <= 80 && inventoryContains(food)) {
                                atInventoryItem(food, "Eat");
                                wait(random(600, 800));
                            }
                        }
                    }
                } else {
                    status = "Walking to hole.";
                    RSObject hol = getNearestObjectByID(hole);
                    if (isLoggedIn()) {
                        if (getMyPlayer().getHPPercent() <= 75) {
                            while (getMyPlayer().getHPPercent() <= 80 && inventoryContains(food)) {
                                atInventoryItem(food, "Eat");
                                wait(random(600, 800));
                            }
                        }
                    }
                    if (hol != null) {
                        if (distanceTo(hol) <= 3 && tileOnScreen(hol.getLocation())) {
                            atObject(hol, "Climb");
                            waitToMove();
                            wait(random(4000, 4500));
                        } else {
                            antiban();
                            walkPath(toDitch);
                        }
                    } else {
                        antiban();
                        walkPath(toDitch);
                    }
                }
            }
        } else if (isInArea(ditchBoundry)) {
            status = "Crossing ditch.";
            antiban();
            RSObject dich = getNearestObjectByID(ditch);
            if (dich != null) {
                if (tileOnScreen(dich.getLocation())) {
                    atObject(dich, "Cross");
                    waitToMove();
                    wait(random(2500, 3500));
                } else {
                    walkTileMM(dich.getLocation());
                    waitToMove();
                }
            }
        } else if (isInArea(wildBoundry)) {
            antiban();
            try {
                if (getInterface(676) != null) {
                    if (getInterface(676).getChild(17).containsText("Proceed")) {
                        atInterface(676, 17);
                        wait(random(200, 300));
                    }
                }
            } catch (NullPointerException e) {
                //null
            }
            status = "Walking to tunnel.";
            RSObject ropez = getNearestObjectByID(rope);
            if (ropez != null) {
                if (tileOnScreen(ropez.getLocation())) {
                    atObject(ropez, "Enter");
                    waitToMove();
                } else {
                    walkPath(toRope);
                    walkTileMM(ropez.getLocation());
                }
            } else {
                walkPath(toRope);
            }
        } else if (isInArea(dung1Boundry)) {
            antiban();
            status = "Walking to portal";
            doPort();
        } else if (isInArea(dungBoundry)) {
            antiban();
            status = "Walking to portal";
            doPort();
        } else if (isInArea(dungANTBoundry)) {
            status = "Walking to correct portal";
            doPort1();
        } else if (isInArea(dungPOISONBoundry)) {
            status = "Walking to correct portal";
            doPort2();
        } else if (isInArea(dBoundry)) {
            status = "Fighting drags";
            if (fight() == 101) {
                patt = false;
                pstr = false;
                panti = false;
            }
        } else {
            status = "Walking to G.E";
            walkPath(toGE);
        }
        return 100;
    }

    private boolean isInArea(int[] xy) {
        final int x = getMyPlayer().getLocation().getX();
        final int y = getMyPlayer().getLocation().getY();
        if (x >= xy[0] && x <= xy[2] && y >= xy[1] && y <= xy[3]) {
            return true;
        } else {
            return false;

        }
    }

    private boolean npcIsInArea(int[] xy, RSCharacter npc) {
        final int x = npc.getLocation().getX();
        final int y = npc.getLocation().getY();
        if (x >= xy[0] && x <= xy[2] && y >= xy[1] && y <= xy[3]) {
            return true;
        } else {
            return false;

        }
    }

    private boolean itemIsInArea(int[] xy, RSItemTile t) {
        final int x = t.getX();
        final int y = t.getY();
        if (x >= xy[0] && x <= xy[2] && y >= xy[1] && y <= xy[3]) {
            return true;
        } else {
            return false;
        }
    }

    public int gap(int[] pots) {
        if (bank.isOpen()) {
            if (bank.getCount(pots[0]) > 0) {
                return pots[0];
            } else if (bank.getCount(pots[1]) > 0) {
                return pots[1];
            } else if (bank.getCount(pots[2]) > 0) {
                return pots[2];
            } else if (bank.getCount(pots[3]) > 0) {
                return pots[3];
            }
        } else {
            return 0;
        }
        return 0;
    }

    public void waitr() {
        wait(random(1120, 1090));
    }

    public void waiti() {
        while (waitForAnim(400) != -1) {
            wait(random(100, 200));
        }
    }

    public void waitToMove() {
        if (waitToMove(2000)) {
            while (getMyPlayer().isMoving()) {
                wait(random(100, 200));
            }
        }
    }

    public boolean didPot(int[] pots) {
        if (inventoryContainsOneOf(pots)) {
            if (doInventoryItem(pots, "Drink")) {
                if (waitForAnim(random(1800, 1900)) == 829) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean walkPath(RSTile[] path) {
        if (!getMyPlayer().isMoving() || distanceTo(getDestination()) <= 1) {
            return walkPathMM(path);
        }
        return false;
    }

    private boolean doInventoryItem(int[] ids, String action) {
        ArrayList<RSInterfaceComponent> possible = new ArrayList<RSInterfaceComponent>();
        for (RSInterfaceComponent com : getInventoryInterface().getComponents()) {
            for (int i : ids) {
                if (i == com.getComponentID()) {
                    possible.add(com);
                }
            }
        }
        if (possible.size() == 0) {
            return false;
        }
        RSInterfaceComponent winner = possible.get(random(0,
                possible.size() - 1));
        Rectangle loc = winner.getArea();
        moveMouse(
                (int) loc.getX() + 3, (int) loc.getY() + 3, (int) loc.getWidth() - 3, (int) loc.getHeight() - 3);
        wait(
                random(100, 300));
        String top = getMenuItems().get(0).toLowerCase();
        if (top.contains(action.toLowerCase())) {
            clickMouse(true);
            return true;

        } else if (menuContains(action)) {
            return atMenu(action);
        }
        return false;
    } //Credits to foul.

    private boolean menuContains(String item) {
        try {
            for (String s : getMenuItems()) {
                if (s.toLowerCase().contains(item.toLowerCase())) {
                    return true;
                }
            }
        } catch (Exception e) {
            return menuContains(item);
        }
        return false;
    }//credits to foul

    public void onRepaint(Graphics g) {
        if (isLoggedIn()) {
            g.setFont(new java.awt.Font("Tahoma", 1, 10));
            runTime = System.currentTimeMillis() - initialStartTime;
            seconds = runTime / 1000;
            if (seconds >= 60) {
                minutes = seconds / 60;
                seconds -= (minutes * 60);
            }
            if (minutes >= 60) {
                hours = minutes / 60;
                minutes -= (hours * 60);
            }

            double h = (3600 * hours) + (60 * minutes) + seconds;
            profitPerSecond = totalLoot / h;
            if (!paintSTR) {
                currentSTR = skills.getCurrentSkillExp(STAT_STRENGTH) - startSTR;
                if (currentSTR > 0) {
                    paintSTR = true;
                }
            } else {
                currentSTR = skills.getCurrentSkillExp(STAT_STRENGTH) - startSTR;
                float STRXPperSec = 0;
                if ((minutes > 0 || hours > 0 || seconds > 0) && currentSTR > 0) {
                    STRXPperSec = ((float) currentSTR) / (float) (seconds + (minutes * 60) + (hours * 60 * 60));

                }
                float STRXPperMin = STRXPperSec * 60;
                float STRXPperHour = STRXPperMin * 60;
                currentSTRLVL = skills.getRealSkillLevel(STAT_STRENGTH);
                STRpr = skills.getPercentToNextLevel(STAT_STRENGTH);
                g.setColor(new Color(255, 102, 0));
                g.fill3DRect(187, 290, 329, 13, true);
                g.setColor(Color.WHITE);
                g.setFont(new java.awt.Font("Tahoma", 1, 10));
                g.drawString("  Level: " + currentSTRLVL + "   " + " Xp Gained: " + currentSTR + "   " + "Xp/Hour: " + (int) STRXPperHour + "   " + STRpr + "%.", 190, (300));
            }
            if (!paintDEF) {
                currentDEF = skills.getCurrentSkillExp(STAT_DEFENSE) - startDEF;
                if (currentDEF > 0) {
                    paintDEF = true;
                }
            } else {
                currentDEF = skills.getCurrentSkillExp(STAT_DEFENSE) - startDEF;
                float DEFXPperSec = 0;
                if ((minutes > 0 || hours > 0 || seconds > 0) && currentDEF > 0) {
                    DEFXPperSec = ((float) currentDEF) / (float) (seconds + (minutes * 60) + (hours * 60 * 60));
                }
                float DEFXPperMin = DEFXPperSec * 60;
                float DEFXPperHour = DEFXPperMin * 60;
                currentDEFLVL = skills.getRealSkillLevel(STAT_DEFENSE);
                DEFpr = skills.getPercentToNextLevel(STAT_DEFENSE);
                g.setColor(new Color(102, 102, 255));
                g.fill3DRect(187, 302, 329, 13, true);
                g.setColor(Color.WHITE);
                g.setFont(new java.awt.Font("Tahoma", 1, 10));
                g.drawString("  Level: " + currentDEFLVL + "   " + " Xp Gained: " + currentDEF + "   " + "Xp/Hour: " + (int) DEFXPperHour + "   " + DEFpr + "%.", 190, (312));
            }
            if (!paintHP) {
                currentHP = skills.getCurrentSkillExp(STAT_HITPOINTS) - startHP;
                if (currentHP > 0) {
                    paintHP = true;
                }
            } else {
                currentHP = skills.getCurrentSkillExp(STAT_HITPOINTS) - startHP;
                float HPXPperSec = 0;
                if ((minutes > 0 || hours > 0 || seconds > 0) && currentHP > 0) {
                    HPXPperSec = ((float) currentHP) / (float) (seconds + (minutes * 60) + (hours * 60 * 60));

                }
                float HPXPperMin = HPXPperSec * 60;
                float HPXPperHour = HPXPperMin * 60;
                currentHPLVL = skills.getRealSkillLevel(STAT_HITPOINTS);
                HPpr = skills.getPercentToNextLevel(STAT_HITPOINTS);
                g.setColor(new Color(255, 0, 102));
                g.fill3DRect(187, 266, 329, 13, true);
                g.setColor(Color.WHITE);
                g.setFont(new java.awt.Font("Tahoma", 1, 10));
                g.drawString("  Level: " + currentHPLVL + "   " + " Xp Gained: " + currentHP + "   " + "Xp/Hour: " + (int) HPXPperHour + "   " + HPpr + "%.", 190, (276));
            }
            if (!paintATT) {
                currentATT = skills.getCurrentSkillExp(STAT_ATTACK) - startATT;
                if (currentATT > 0) {
                    paintATT = true;
                }
            } else {
                currentATT = skills.getCurrentSkillExp(STAT_ATTACK) - startATT;
                float ATTXPperSec = 0;
                if ((minutes > 0 || hours > 0 || seconds > 0) && currentATT > 0) {
                    ATTXPperSec = ((float) currentATT) / (float) (seconds + (minutes * 60) + (hours * 60 * 60));

                }
                float ATTXPperMin = ATTXPperSec * 60;
                float ATTXPperHour = ATTXPperMin * 60;
                currentATTLVL = skills.getRealSkillLevel(STAT_ATTACK);
                ATTpr = skills.getPercentToNextLevel(STAT_ATTACK);
                g.setColor(new Color(255, 51, 0));
                g.fill3DRect(187, 278, 329, 13, true);
                g.setColor(Color.WHITE);
                g.setFont(new java.awt.Font("Tahoma", 1, 10));
                g.drawString("  Level: " + currentATTLVL + "   " + " Xp Gained: " + currentATT + "   " + "Xp/Hour: " + (int) ATTXPperHour + "   " + ATTpr + "%.", 190, (288));

            }

            if (!paintRNG) {
                currentRNG = skills.getCurrentSkillExp(STAT_RANGE) - startRNG;
                if (currentRNG > 0) {
                    paintRNG = true;
                }
            } else {
                currentRNG = skills.getCurrentSkillExp(STAT_RANGE) - startRNG;
                float RNGXPperSec = 0;
                if ((minutes > 0 || hours > 0 || seconds > 0) && currentRNG > 0) {
                    RNGXPperSec = ((float) currentRNG) / (float) (seconds + (minutes * 60) + (hours * 60 * 60));
                }
                float RNGXPperMin = RNGXPperSec * 60;
                float RNGXPperHour = RNGXPperMin * 60;
                currentRNGLVL = skills.getRealSkillLevel(STAT_RANGE);
                RNGpr = skills.getPercentToNextLevel(STAT_RANGE);
                g.setColor(new Color(51, 153, 0));
                g.fill3DRect(187, 314, 329, 13, true);
                g.setColor(Color.WHITE);
                g.setFont(new java.awt.Font("Tahoma", 1, 10));
                g.drawString("  Level: " + currentRNGLVL + "   " + "Xp Gained: " + currentRNG + "   " + "Xp/Hour: " + (int) RNGXPperHour + "   " + RNGpr + "%.", 190, (324));
            }

            if (!paintMAG) {
                currentMAG = skills.getCurrentSkillExp(STAT_MAGIC) - startMAG;
                if (currentMAG > 0) {
                    paintMAG = true;
                }
            } else {
                currentMAG = skills.getCurrentSkillExp(STAT_MAGIC) - startMAG;
                float MAGXPperSec = 0;
                if ((minutes > 0 || hours > 0 || seconds > 0) && currentMAG > 0) {
                    MAGXPperSec = ((float) currentMAG) / (float) (seconds + (minutes * 60) + (hours * 60 * 60));

                }
                float MAGXPperMin = MAGXPperSec * 60;
                float MAGXPperHour = MAGXPperMin * 60;
                currentMAGLVL = skills.getRealSkillLevel(STAT_MAGIC);
                MAGpr = skills.getPercentToNextLevel(STAT_MAGIC);
                g.setColor(new Color(51, 0, 255));
                g.fill3DRect(187, 327, 329, 12, true);
                g.setColor(Color.WHITE);
                g.setFont(new java.awt.Font("Tahoma", 1, 10));
                g.drawString("  Level: " + currentMAGLVL + "   " + " Xp Gained: " + currentMAG + "   " + "Xp/Hour: " + (int) MAGXPperHour + "   " + MAGpr + "%.", 190, (337));

            }
            g.setColor(new Color(0, 0, 0, 100));
            g.fill3DRect(3, 266, 184, 73, true);
            g.setColor(new Color(227, 100, 45));
            g.drawString("Ownageful GDK", 15, 277);
            g.setColor(Color.white);

            g.drawString("Time running: " + hours + ":" + minutes + ":" + seconds + ".", 15, 289);
            g.drawString("Status: " + status, 15, 301);
            g.drawString("Total Loot: " + totalLoot, 15, 313);
            g.drawString("Loot Per Hour: " + (int) (profitPerSecond * 3600), 15, 325);
            g.drawString("Bank Runs: " + bankRuns + " times.", 15, 337);
            g.setFont(new java.awt.Font("Tahoma", 1, 10));
            if (paintHP) {
                g.setColor(new Color(255, 0, 102));
                g.drawString("Hp", 164, (276));
            }
            if (paintATT) {
                g.setColor(new Color(255, 51, 0));
                g.drawString("Att", 164, (288));
            }
            if (paintSTR) {
                g.setColor(new Color(255, 102, 0));
                g.drawString("Str", 164, (300));
            }
            if (paintDEF) {
                g.setColor(new Color(102, 102, 255));
                g.drawString("Def", 164, (312));
            }
            if (paintRNG) {
                g.setColor(new Color(51, 153, 0));
                g.drawString("Rng", 164, (324));
            }
            if (paintMAG) {
                g.setColor(new Color(51, 0, 255));
                g.drawString("Mgc", 164, (336));
            }
        }

    }

    public void serverMessageRecieved(ServerMessageEvent sme) {
        String str = sme.getMessage();
        if (str.contains("super strength")) {
            pstr = true;
        }
        if (str.contains("super attack")) {
            patt = true;
        }
        if (str.contains("antifire")) {
            panti = true;
        }
        if (str.contains("fiery breath") && panti) {
            nanti = true;
        }
        if (str.contains("unknown portal")) {
            ntele = true;
        }
    }

    @Override
    public void onFinish() {
        // Takes a screen shot when u stop the script.
        ScreenshotUtil.takeScreenshot(true);
        // Remove listeners.
        Bot.getEventManager().removeListener(PaintListener.class, this);
        Bot.getEventManager().removeListener(ServerMessageListener.class, this);
    }

    private int fight() {
        updateTimers();
        if (nanti) {
            if (inventoryContainsOneOf(antiPots[0], antiPots[1], antiPots[2], antiPots[3])) {
                if (doInventoryItem(antiPots, "Drink")) {
                    wait(random(800, 900));
                    nanti = false;
                }
            }
        }
        if (isInventoryFull() && getInventoryCount(food) == 0) {
            atInventoryItem(vTabs, "Break");
            wait(random(7000, 8500));
            walkTileMM(new RSTile(3208, 3435));
            waitToMove();
            return 101;
        }
        if (isLoggedIn()) {
            if (getMyPlayer().getHPPercent() < hp) {
                if (inventoryContains(food)) {
                    atInventoryItem(food, "Eat");
                    wait(random(600, 800));
                    return 100;
                } else {
                    atInventoryItem(vTabs, "Break");
                    wait(random(7000, 8500));
                    walkTileMM(new RSTile(3208, 3435));
                    waitToMove();
                    return 101;
                }
            }
        }
        if (lCharms) {
            charmz = getNearestGroundItemByID(charms);
            if (charmz != null) {
                if (tileOnScreen(charmz)) {
                    if (isInventoryFull() && inventoryContains(food) && !inventoryContains(charmz.getItem().getID())) {
                        atInventoryItem(food, "Eat");
                        wait(random(100, 300));
                    }
                    atTile(charmz, "charm");
                    waitToMove();
                    return 100;
                } else {
                    walkTileMM(charmz);
                    waitToMove();
                    return 100;
                }
            }
        }
        lootzd = getNearestGroundItemByID(loots[0]);
        if (lootzd != null) {
            if (tileOnScreen(lootzd)) {
                if (isInventoryFull() && inventoryContains(food)) {
                    atInventoryItem(food, "Eat");
                    wait(random(600, 800));
                }
                atTile(lootzd, names[0]);
                waitToMove();
                totalLoot = totalLoot + prices[0];
                return 100;
            } else {
                walkTileMM(lootzd);
                waitToMove();
                return 100;
            }

        }
        lootz = getNearestGroundItemByID(loots);
        if (lootz != null) {
            if (itemIsInArea(dBoundry, lootz)) {
                for (int i = 0; i < loots.length; i++) {
                    if (lootz.getItem().getID() == loots[i]) {
                        if (tileOnScreen(lootz)) {
                            if (isInventoryFull() && inventoryContains(food)) {
                                atInventoryItem(food, "Eat");
                                wait(random(600, 800));
                            }
                            atTile(lootz, names[i]);
                            waitToMove();
                            totalLoot = totalLoot + prices[i];
                            return 100;
                        } else {
                            walkTileMM(lootz);
                            waitToMove();
                            return 100;
                        }
                    }
                }
            }
        }
        RSCharacter inter = getMyPlayer().getInteracting();
        if (inter != null) {
            if (inter.getName().contains("Green")) {
                if (inter.getAnimation() == 92 || inter.getHPPercent() == 0) {
                    if (getArea(inter) == se) {
                        tse = new Timer(rTime);
                        tse.reset();
                    } else if (getArea(inter) == sw) {
                        tsw = new Timer(rTime);
                        tsw.reset();
                    } else if (getArea(inter) == ne) {
                        tne = new Timer(rTime);
                        tne.reset();
                    } else if (getArea(inter) == nw) {
                        tnw = new Timer(rTime);
                        tnw.reset();
                    }
                    wait(random(4200, 4300));
                    return 100;
                } else {
                    wait(random(200, 300));
                }
            } else {
                goTArea(getLTA());
                RSNPC drag = getNearestNPCToAttackByID(drags);
                if (drag != null) {
                    if (getArea(drag) == getArea(getMyPlayer())) {
                        if (!atNPC(drag, "Attack")) {
                            waitToMove();
                        } else {
                            atNPC(drag, "Attack");
                            waitToMove();
                        }
                        return 100;
                    }
                }
            }
        } else {
            goTArea(getLTA());
            if (random(1, 125) == 25) {
                antiban();
            }
            RSNPC drag = getNearestNPCToAttackByID(drags);
            if (drag != null) {
                if (getArea(drag) == getArea(getMyPlayer())) {
                    if (!atNPC(drag, "Attack")) {
                        waitToMove();
                    } else {
                        atNPC(drag, "Attack");
                        waitToMove();
                    }
                    return 100;
                }
            } else {
                wait(random(200, 300));
            }
        }
        return 100;
    }

    private int antiban() {
        int i = random(0, 30);
        int ii = random(0, 25);
        if (i == 2) {
            moveMouse(random(0, CanvasWrapper.getGameWidth()), random(0,
                    CanvasWrapper.getGameHeight()));
            return random(0, 400);
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
        }
        return random(1000, 1500);

    }

    private void goTArea(int[] a) {
        RSCharacter nc = getMyPlayer().getInteracting();
        if (nc != null) {
            if (!nc.getName().contains("Green")) {
                if (a == ne) {
                    if (!isInArea(ne)) {
                        walkTileMM(new RSTile(3316, 5456));
                        waitToMove();
                    }
                } else if (a == se) {
                    if (!isInArea(se)) {
                        walkTileMM(new RSTile(3315, 5449));
                        waitToMove();
                    }
                } else if (a == sw) {
                    if (!isInArea(sw)) {
                        walkTileMM(new RSTile(3305, 5450));
                        waitToMove();
                    }
                } else if (a == nw) {
                    if (!isInArea(nw)) {
                        walkTileMM(new RSTile(3306, 5461));
                        waitToMove();
                    }
                }
            }
        } else {
            if (a == ne) {
                if (!isInArea(ne)) {
                    walkTileMM(new RSTile(3316, 5456));
                    waitToMove();
                }
            } else if (a == se) {
                if (!isInArea(se)) {
                    walkTileMM(new RSTile(3315, 5449));
                    waitToMove();
                }
            } else if (a == sw) {
                if (!isInArea(sw)) {
                    walkTileMM(new RSTile(3305, 5450));
                    waitToMove();
                }
            } else if (a == nw) {
                if (!isInArea(nw)) {
                    walkTileMM(new RSTile(3306, 5461));
                    waitToMove();
                }
            }
        }
    }

    public int[] getLTA() {
        Timer[] timers = {tse, tsw, tne, tnw};
        int n = timers.length;
        Timer t;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (timers[j - 1].getTimeRemaining() > timers[j].getTimeRemaining()) {
                    t = timers[j - 1];
                    timers[j - 1] = timers[j];
                    timers[j] = t;
                }
            }
        }
        for (int i = 0; i < timers.length; i++) {
            if (timers[i].getTimeRemaining() != 0) {
                if (timers[i] == tse) {
                    return se;
                } else if (timers[i] == tsw) {
                    return sw;
                } else if (timers[i] == tne) {
                    return ne;
                } else if (timers[i] == tnw) {
                    return nw;
                }
            }
        }

        return null;
    }

    public int[] getArea(RSCharacter drag) {
        if (drag != null) {
            if (npcIsInArea(ne, drag)) {
                return ne;
            } else if (npcIsInArea(se, drag)) {
                return se;
            } else if (npcIsInArea(sw, drag)) {
                return sw;
            } else if (npcIsInArea(nw, drag)) {
                return nw;
            }
        }
        return null;
    }

    public RSNPC[] getDA() {
        int[] validNPCs = Bot.getClient().getRSNPCIndexArray();
        final ArrayList<RSNPC> RSNPCList = new ArrayList<RSNPC>();

        for (int element : validNPCs) {
            Node node = Calculations.findNodeByID(Bot.getClient().getRSNPCNC(), element);
            if (node == null || !(node instanceof RSNPCNode)) {
                continue;
            }
            RSNPC monster = new RSNPC(((RSNPCNode) node).getRSNPC());
            if (monster.getName().contains("Green")) {
                RSNPCList.add(monster);
            }
        }
        return RSNPCList.toArray(new RSNPC[RSNPCList.size()]);
    }

    private void updateTimers() {
        gd = getDA();
        for (int i = 0; i < gd.length; i++) {
            RSNPC inter = gd[i];
            if (inter != null) {
                if (getArea(inter) == se) {
                    if (inter.getAnimation() == 92 || inter.getHPPercent() == 0) {
                        tse = new Timer(rTime);
                        tse.reset();
                    }
                } else if (getArea(inter) == sw) {
                    if (inter.getAnimation() == 92 || inter.getHPPercent() == 0) {
                        tsw = new Timer(rTime);
                        tsw.reset();
                    }
                } else if (getArea(inter) == ne) {
                    if (inter.getAnimation() == 92 || inter.getHPPercent() == 0) {
                        tne = new Timer(rTime);
                        tne.reset();
                    }
                } else if (getArea(inter) == nw) {
                    if (inter.getAnimation() == 92 || inter.getHPPercent() == 0) {

                        tnw = new Timer(rTime);
                        tnw.reset();
                    }
                }
            }
        }
    }

    private void doPort() {
        RSObject port = getObjectAt(portal);
        if (port != null) {
            if (tileOnScreen(port.getLocation()) && distanceTo(port.getLocation()) <= 3) {
                waitToMove();
                atObject(port, "Enter");
                waitToMove();
                wait(random(2500, 3000));
                walkTileMM(new RSTile(3307, 5460));
                waitToMove();
            } else {
                walkPath(toPort);
            }
        } else {
            walkPath(toPort);
        }
    }

    private void doPort1() {
        RSObject port1 = getObjectAt(new RSTile(3299, 5484));
        if (port1 != null) {
            if (tileOnScreen(port1.getLocation())) {
                atObject(port1, "Enter");
                wait(random(4000, 4500));
                walkTileMM(new RSTile(3298, 5477));
                waitToMove();
            }
        }
    }

    private void doPort2() {
        RSObject port1 = getObjectAt(new RSTile(3285, 5474));
        if (port1 != null) {
            if (tileOnScreen(port1.getLocation())) {
                atObject(port1, "Enter");
                wait(random(4000, 4500));
                walkTileMM(new RSTile(3289, 5463));
                waitToMove();
            }
        }
    }

    public class GDK extends javax.swing.JFrame {

        public GDK() {
            initComponents();
        }

        private void initComponents() {
            jPanel1 = new javax.swing.JPanel();
            jLabel1 = new javax.swing.JLabel();
            jCheckBox1 = new javax.swing.JCheckBox();
            jCheckBox2 = new javax.swing.JCheckBox();
            jCheckBox3 = new javax.swing.JCheckBox();
            jButton1 = new javax.swing.JButton();
            jCheckBox4 = new javax.swing.JCheckBox();
            jTextField1 = new javax.swing.JTextField();
            jTextField2 = new javax.swing.JTextField();
            jSlider1 = new javax.swing.JSlider();
            jLabel2 = new javax.swing.JLabel();

            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

            jPanel1.setBackground(new java.awt.Color(0, 0, 0));

            jLabel1.setFont(new java.awt.Font("Viner Hand ITC", 0, 36));
            jLabel1.setForeground(new java.awt.Color(204, 0, 0));
            jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            jLabel1.setText("Ownageful GDK");

            jCheckBox1.setBackground(new java.awt.Color(0, 0, 0));
            jCheckBox1.setForeground(new java.awt.Color(255, 0, 0));
            jCheckBox1.setSelected(true);
            jCheckBox1.setText("Eat Food");

            jCheckBox2.setBackground(new java.awt.Color(0, 0, 0));
            jCheckBox2.setForeground(new java.awt.Color(255, 0, 0));
            jCheckBox2.setSelected(true);
            jCheckBox2.setText("Drink Supers");

            jCheckBox3.setBackground(new java.awt.Color(0, 0, 0));
            jCheckBox3.setForeground(new java.awt.Color(255, 0, 0));
            jCheckBox3.setSelected(true);
            jCheckBox3.setText("Drink AntiFire");

            jButton1.setBackground(new java.awt.Color(0, 0, 0));
            jButton1.setText("Start");
            jButton1.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton1ActionPerformed(evt);
                }
            });

            jCheckBox4.setBackground(new java.awt.Color(0, 0, 0));
            jCheckBox4.setForeground(new java.awt.Color(255, 51, 0));
            jCheckBox4.setSelected(true);
            jCheckBox4.setText("Loot Charms");

            jTextField1.setText("Food ID");
            jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jTextField1MouseClicked(evt);
                }

                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    jTextField1MouseEntered(evt);
                }
            });

            jTextField2.setText("Amount");
            jTextField2.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jTextField2MouseClicked(evt);
                }

                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    jTextField2MouseEntered(evt);
                }
            });

            jSlider1.setBackground(new java.awt.Color(0, 0, 0));
            jSlider1.setForeground(new java.awt.Color(204, 0, 0));
            jSlider1.setPaintLabels(true);

            jLabel2.setForeground(new java.awt.Color(204, 0, 0));
            jLabel2.setText("Eat food at (%)");

            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE).addContainerGap()).addGroup(jPanel1Layout.createSequentialGroup().addGap(10, 10, 10).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jCheckBox3).addComponent(jCheckBox1).addComponent(jCheckBox2)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(jPanel1Layout.createSequentialGroup().addGap(10, 10, 10).addComponent(jCheckBox4))).addGap(51, 51, 51))).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel2).addGroup(jPanel1Layout.createSequentialGroup().addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jButton1))).addGap(27, 27, 27)))));
            jPanel1Layout.setVerticalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(jLabel1).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jCheckBox1).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jCheckBox3).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jCheckBox2).addComponent(jCheckBox4))).addGroup(jPanel1Layout.createSequentialGroup().addGap(82, 82, 82).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jTextField1).addComponent(jTextField2)).addGap(40, 40, 40))).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabel2).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jButton1)).addContainerGap()));

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE));

            pack();
        }

        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
            try {
                if (jCheckBox1.isSelected()) {
                    food = Integer.parseInt(jTextField1.getText());
                    withdraw = Integer.parseInt(jTextField2.getText());
                    eat = true;
                }
                if (jCheckBox3.isSelected()) {
                    useAnti = true;
                }
                if (jCheckBox2.isSelected()) {
                    useSupers = true;
                }
                if (jCheckBox4.isSelected()) {
                    lCharms = true;
                }
                hp = jSlider1.getValue();
                this.dispose();
                startScript = true;
            } catch (NumberFormatException numberFormatException) {
                log("Invalid food id format.");
            }
        }

        private void jTextField1MouseClicked(java.awt.event.MouseEvent evt) {
            jTextField1.setText("");
        }

        private void jTextField2MouseClicked(java.awt.event.MouseEvent evt) {
            jTextField2.setText("");
        }

        private void jTextField1MouseEntered(java.awt.event.MouseEvent evt) {
            jTextField1.setText("");
        }

        private void jTextField2MouseEntered(java.awt.event.MouseEvent evt) {
            jTextField2.setText("");
        }
        private javax.swing.JButton jButton1;
        private javax.swing.JCheckBox jCheckBox1;
        private javax.swing.JCheckBox jCheckBox2;
        private javax.swing.JCheckBox jCheckBox3;
        private javax.swing.JCheckBox jCheckBox4;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JSlider jSlider1;
        private javax.swing.JTextField jTextField1;
        private javax.swing.JTextField jTextField2;
    }
}