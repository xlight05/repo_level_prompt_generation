import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import org.rsbot.bot.input.Mouse;
import org.rsbot.event.listeners.PaintListener;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Map;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.rsbot.script.wrappers.RSInterface;
import org.rsbot.bot.Bot;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import javax.imageio.ImageIO;

import org.rsbot.bot.Bot;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.script.Calculations;
import org.rsbot.script.Constants;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.Skills;
import org.rsbot.script.wrappers.RSInterface;
import org.rsbot.script.wrappers.RSInterfaceChild;
import org.rsbot.script.wrappers.RSInterfaceComponent;
import org.rsbot.script.wrappers.RSArea;
import org.rsbot.script.wrappers.RSItem;
import org.rsbot.script.wrappers.RSNPC;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSPlayer;
import org.rsbot.script.wrappers.RSTile;
import org.rsbot.util.ScreenshotUtil;



@ScriptManifest(authors = { "Humpy , Anarki" }, category = "@Mini-Game", name = "Epic Garden of Magic Perfect Version", version = 3.31, description = "<html><head></head><body><center><h2><b>Epic Garden of Magic Perfect Version</b></h2></center><br/><b>Main Author\uFF1A</b> Humpy ( credit to Blurner & Jankal )<br/><b>Script Improver\uFF1A</b> Anarki<br/><b>Paint Author\uFF1A</b> Fletch To 99 , Humpy & Anarki<br/><b>Version\uFF1A</b>3.31 ( Perfect Version )<br><br><input type='radio' name='group1' value='Fruit' checked> Fruit<br><input type='radio' name='group1' value='Herb'> Herbs<br><br>Select which item you would like to pick then click OK. This script will figure out the best garden you are able to enter. If you would like to make it to auto banking , please equip a Ring of kinship ( the one can teleport to Daemonheim ).<br><br><b>Note\uFF1AYou must have the broomstick from Swept Away equipped and enchanted to teleport to the Sroceress's Garden , and have at least 45 lv thieving to enter Autumn Garden or Summer Garden.</b><br><br><center><img src=\"http://img827.imageshack.us/img827/8940/inventory.png\" /></center></body></html>")
public class EpicGardenOfMagic extends Script implements ServerMessageListener, PaintListener {

	timer timerClass = new timer();
	Thread  timer = new Thread(timerClass);
	public boolean beepActive = true;
	String  firstMessage = getLastMessage().toLowerCase();
	private boolean isPressed = false;
	private final Color color1 = new Color(0, 0, 0);
	private final Color color2 = new Color(102, 0, 153, 163);
	private final Color color3 = new Color(255, 255, 255);
	public BufferedImage img1 = null;
	BufferedImage cursor = null;
	BufferedImage cursor80 = null;
	BufferedImage cursor60 = null;
	BufferedImage cursor40 = null;
	BufferedImage cursor20 = null;
	int paintInt = 1;
	int HidePanelOrNot = 0;
	public int startLevel;
	int levelsGained = 0;
	int nextLevel;
	long hours;
	long minutes;
	long seconds;
	private final RenderingHints rh = new RenderingHints(
			RenderingHints.KEY_TEXT_ANTIALIASING,
			RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

	private int getDirection(RSNPC n) {
	if(n != null) {
		final int x1 = n.getLocation().getX();
		final int y1 = n.getLocation().getY();
		while(n.getLocation().getX()==x1 && n.getLocation().getY()==y1) wait(50);
		final int x2 = n.getLocation().getX();
		final int y2 = n.getLocation().getY();
		if(x2==x1 && y2>y1) {
			return 1024;
		} else if(x2>x1 && y2==y1) {
			return 1536;
		} else if(x2==x1 && y2<y1) {
			return 0;
		} else if(x2<x1 && y2==y1) {
			return 512;
		} else {
			return -1;
		}
		} else {
			return -1;
		}
	}

	protected int getMouseSpeed() {
		if (Teleporting) { 
			return random(8, 9);
		} else {
			return random(6, 8);
		}
	}

        // Autumn Garden Case Variables ( Case of "Unable to getCaught" )
        private final int AUTUMNATSTART = 1;
        private final int AUTUMNGOPOSITION1 = 3;
        private final int AUTUMNOUTSIDEGATE = 2;


        // Autumn Garden Case Variables ( Case of "Able to getCaught" )
        private int autumnPhase = 0;
        private final int AUTUMNPICKFRUIT = 10;
        private final int AUTUMNPICKHERBPATH = 11;
        private final int AUTUMNPICKHERB = 12;
        private final int AUTUMNPICKHERBCLICK = 13;
        private final int AUTUMNWAITPOSITION7 = 9;
        private final int AUTUMNWAITPOSITION2 = 4;
        private final int AUTUMNWAITPOSITION3 = 5;
        private final int AUTUMNWAITPOSITION4 = 6;
        private final int AUTUMNWAITPOSITION5 = 7;
        private final int AUTUMNWAITPOSITION6 = 8;


        // Banking Area Variables
        private final RSArea DaemonheimAreaA = new RSArea(3441, 3694, 10, 10);
        private final RSArea DaemonheimAreaB = new RSArea(3442, 3694, 10, 10);
        private final RSArea DaemonheimAreaC = new RSArea(3443, 3696, 10, 10);
        private final RSArea DaemonheimAreaD = new RSArea(3445, 3696, 10, 10);
        private final RSArea DaemonheimAreaE = new RSArea(3446, 3700, 10, 10);
        private final RSArea DaemonheimAreaF = new RSArea(3446, 3701, 10, 10);
        private final RSArea DaemonheimAreaG = new RSArea(3446, 3702, 10, 10);
        private final RSArea DaemonheimAreaH = new RSArea(3447, 3696, 10, 10);
        private final RSArea DaemonheimAreaI = new RSArea(3447, 3697, 10, 10);
        private final RSArea DaemonheimAreaJ = new RSArea(3448, 3694, 10, 10);
        private final RSArea DaemonheimAreaK = new RSArea(3448, 3696, 10, 10);
        private final RSArea DaemonheimAreaL = new RSArea(3448, 3697, 10, 10);
        private final RSArea DaemonheimAreaM = new RSArea(3449, 3695, 10, 10);
        private final RSArea DaemonheimAreaN = new RSArea(3449, 3702, 10, 10);
        private final RSArea DaemonheimAreaO = new RSArea(3450, 3698, 10, 10);
        private final RSArea DaemonheimAreaP = new RSArea(3450, 3700, 10, 10);
        private final RSArea DaemonheimAreaQ = new RSArea(3450, 3702, 10, 10);
        private final RSArea DaemonheimAreaR = new RSArea(3451, 3697, 10, 10);

        private final RSTile[] bankBeforeTile = { new RSTile(3449, 3698), new RSTile(3450, 3702)};
        private final RSTile[] bankTile = { new RSTile(3450, 3702), new RSTile(3450, 3704), new RSTile(3450, 3706) , new RSTile(3450, 3708)};


        // NPC Variables
        private final int elemental1Autumn = 5533;
        private final int elemental2Autumn = 5534;
        private final int elemental3Autumn = 5535;
        private final int elemental4Autumn = 5536;
        private final int elemental5Autumn = 5537;
        private final int elemental6Autumn = 5538;

        private final int elemental1Summer = 5547;
        private final int elemental2Summer = 5548;
        private final int elemental3Summer = 5549;
        private final int elemental4Summer = 5550;
        private final int elemental5Summer = 5551;
        private final int elemental6Summer = 5552;


        // System and Garden Setting Variables
        private String garden = "Summer";
        private int bankPhase = 0;
        private double averageRunTime = 0;
        private boolean banking = false;
        private boolean caught = false;
        private int completeRuns = 0;
        private int errorCount = 0;
        private double expPerJuice = 0;
        private double FarmingExpPerPick = 0;
        private final int broom = 14057;
        private int fruitID;
        private final int fruitIDAutumn = 10846;
        private final int fruitIDSummer = 10845;
        private int numFruitNeeded;
        private int fruitPicked = 0;
        private final int glassID = 1919;
        private final int PestleAndMortarID = 233;
        private RSNPC npc1, npc2;
        private RSPlayer player;
        private int EnergyPower;
        private final int[] restingAnimations = { 11786, 5713 };
        private final int[] RingOfKinshipIDs = { 15707, 15707, 15707, 15707, 15707, 15707, 15707, 15707 };

        private static int speed;
        private RSNPC FremennikBanker;
        private static final int FremennikBanker_ID = 9710;

        // Autumn Garden & Summer Garden's Path Variables
        private final RSTile[] fruitTreeAutumn = { new RSTile(2913, 5451),
                        new RSTile(2912, 5451), new RSTile(2912, 5450) };
        private final RSTile[] fruitTreeSummer = { new RSTile(2915, 5491),
                        new RSTile(2916, 5492) };

        private final RSTile herbSummer = new RSTile(2923, 5483);
        private final RSTile herbAutumn = new RSTile(2916, 5458);

        private final RSTile HerbsPickingPostionSummer = new RSTile(2923, 5484);
        private final RSTile HerbsPickingPostionAutumn = new RSTile(2916, 5457);

        private final RSTile FruitPickingPostion1Summer = new RSTile(2915, 5490);
        private final RSTile FruitPickingPostion2Summer = new RSTile(2916, 5490);
        private final RSTile FruitPickingPostion3Summer = new RSTile(2917, 5491);
        private final RSTile FruitPickingPostion4Summer = new RSTile(2917, 5492);

        private final RSTile gatePositionAutumn = new RSTile(2913, 5463);
        private final RSTile gatePositionSummer = new RSTile(2910, 5481);

        private final RSArea inFrontOfBank1 = new RSArea(3449, 3708, 2, 3);
        private final RSArea inFrontOfBank2 = new RSArea(3449, 3718, 2, 3);

        private final RSTile inFrontOfBankTile = new RSTile(3449, 3719);

        private final RSArea inFrontOfTreeAutumn = new RSArea(2909, 5448, 8, 7);
        private final RSArea inFrontOfTreeSummer = new RSArea(2916, 5490, 3, 3);
        private final RSTile inFrontOfTreeSummerTile = new RSTile(2916, 5490);

        private final RSArea outSideGateAutumn = new RSArea(2913, 5463, 3, 3);
        private final RSArea outSideGateSummer = new RSArea(2910, 5478, 1, 2);

        private final RSTile AutumnGateTile = new RSTile(2913, 5463);
        private final RSTile SummerGateTile = new RSTile(2910, 5480);

        private final RSTile CaughtSummerPostion = new RSTile(2910, 5478);
        private final RSTile CaughtAutumnPostion = new RSTile(2913, 5465);

        private final RSTile pastGateAutumn = new RSTile(2913, 5462);
        private final RSTile pastGateSummer = new RSTile(2910, 5481);

        private final RSTile AfterpastGateAutumn = new RSTile(2913, 5461);

        private final RSTile BeforePastGateSummer = new RSTile(2910, 5480);

        private final RSTile ErrorPastGatePostionSummer = new RSTile(2910, 5482);

        private final RSTile position1Autumn = new RSTile(2908, 5461);
        private final RSTile position2Autumn = new RSTile(2902, 5461);
        private final RSTile position3Autumn = new RSTile(2901, 5455);
        private final RSTile position4Autumn = new RSTile(2901, 5451);
        private final RSTile position5Autumn = new RSTile(2903, 5450);
        private final RSTile position6Autumn = new RSTile(2908, 5456);
        private final RSTile position7Autumn = new RSTile(2913, 5452);
        private final RSTile position8Autumn = new RSTile(2913, 5458);

        private final RSTile position1Summer = new RSTile(2906, 5486);
        private final RSTile position2Summer = new RSTile(2906, 5492);
        private final RSTile position3Summer = new RSTile(2909, 5490);
        private final RSTile position4Summer = new RSTile(2911, 5485);
        private final RSTile position5Summer = new RSTile(2919, 5485);
        private final RSTile position6Summer = new RSTile(2921, 5485);
        private final RSTile position7Summer = new RSTile(2924, 5487);
        private final RSTile position8Summer = new RSTile(2920, 5488);


        // Starting Variables
        private final RSArea startPosition = new RSArea(2905, 5465, 14, 14);
        private final RSTile startPositionTile = new RSTile(2905, 5465);
        private long startRunTime = 0;
        private long startTime;
        private String status = "";

        // Summer Case Variables ( Case of "Unable to getCaught" )
        private final int SUMMERATSTART = 1;
        private final int SUMMERGOPOSITION9 = 11;
        private final int SUMMEROUTSIDEGATE = 2;

        // Summer Case Variables ( Case of "Able to getCaught" )
        private int summerPhase = 0;
	private int getherbs = 0;
	private int getGarden = 0;

        private final int SUMMERPICKFRUIT = 12;
        private final int SUMMERPICKHERB = 13;
        private final int SUMMERPICKHERBCLICK = 14;
        private final int SUMMERWAITPOSITION8 = 10;

        private final int SUMMERWAITPOSITION1 = 3;
        private final int SUMMERWAITPOSITION2 = 4;

        private final int SUMMERWAITPOSITION3 = 5;

        private final int SUMMERWAITPOSITION4 = 6;

        private final int SUMMERWAITPOSITION5 = 7;
        private final int SUMMERWAITPOSITION6 = 8;

        private final int SUMMERWAITPOSITION7 = 9;

        // Banking & Speical Case Variables
        private final int TELETODAEMONHEIM = 2;
        private final int TELETOSG = 12;

        private final int GRINDJUICE = 1;
        private final int WEARRING = 8;
        private final int OPENBANK = 5;
        private final int DEPOSITALL = 6;
        private final int CLOSEBANK = 9;

        private final int WALKTOBANK1 = 3;
        private final int WALKTOBANK2 = 4;

        private final int WITHDRAWGLASSES = 10;

        private final int WITHDRAWMANDP  = 11;

        private final int WITHDRAWRING = 7;

        private final int MAXERROR = 20;

        // System Default Setting
        private int timesCaught = 0;
        private int waitTimeOut = 0;

        private boolean Teleporting = false;
        private boolean waitingForRun = false;
        private boolean enteringTheGate = false;
        private boolean waitingForGoAgain = false;

        // End Summer Variables
	private int started = 0;

        private int antiBan() {
                // Not Implemented
                return random(300, 400);
        }

        public boolean atEquipment(final String action, final int... itemID) {
                for (final int item : itemID) {
                        if (atEquipment(action, item)) {
                                return true;
                        }
                }
                return false;
        }

        private boolean atEquipment(final String action, final int itemID) {
                final int[] equipmentArray = getEquipmentArray();
                int pos = 0;
                while (pos < equipmentArray.length) {
                        if (equipmentArray[pos] == itemID) {
                                break;
                        }
                        pos++;
                }
                if (pos == equipmentArray.length) {
                        return false;
                }
                Point tl, br;
                switch (pos) {
                case 3:
                        tl = new Point(575, 292);
                        br = new Point(599, 317);
                        break;
                case 9:
                        tl = new Point(686, 372);
                        br = new Point(711, 396);
                        break;
                default:
                        log("Currently atEquipment is only implemented for Weapons and Rings. Pos:" + pos);
                        return false;
                }
                int error = 0;
                while (getCurrentTab() != Constants.TAB_EQUIPMENT) {
                        openTab(Constants.TAB_EQUIPMENT);
                        wait(random(50, 100));
                        error++;
                        if (error > 5) {
                                return false;
                        }
                }
                moveMouse(random(tl.x, br.x), random(tl.y, br.y));
                wait(random(50, 100));
                return atMenu(action);
        }

        @Override
        public boolean atInventoryItem(final int itemID, final String option) {
                if (getCurrentTab() != Constants.TAB_INVENTORY) {
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
                moveMouse(t, 5, 5);
                return atMenu(option);
        }

        @Override
        public boolean atTile(final RSTile tile, final int h, final double xd,
                        final double yd, final String action) {
                try {
                        final Point location = Calculations.tileToScreen(tile.getX(), tile
                                        .getY(), .5, .5, h);
                        if (location.x == -1 || location.y == -1) {
                                return false;
                        }
                        moveMouse(location, 3, 3);
                        if (getMenuItems().get(0).toLowerCase().contains(
                                        action.toLowerCase())) {
                                clickMouse(true);
                        } else {
                                clickMouse(false);
                                if (!atMenu(action)) {
                                        return false;
                                }
                        }
                        wait(random(500, 1000));
                        return true;
                } catch (final Exception e) {
                        return false;
                }
        }

	public RSInterfaceChild getInterface(int index, int indexChild) {
		return RSInterface.getChildInterface(index, indexChild);
	}

        private boolean Bank() {
                banking = true;
                int i = 0;
                switch (bankPhase) {
                case 0:
                        bankPhase = determineBankPhase();
                        if (bankPhase == 0) {
                                errorCount++;
                        }
                        break;

                case GRINDJUICE:
                        status = "Grinding Juice";
                        if (inventoryContains(glassID)
                                        && getInventoryCount(fruitID) >= numFruitNeeded) {
                                final int fruitnum = getInventoryCount(fruitID);
                                useInventoryItems(PestleAndMortarID, fruitID);
                                wait(1000, 1200);
                                if (fruitnum == getInventoryCount(fruitID)) {
                                        errorCount++;
                                }
                        } else if (isInventoryFull()) {
                                bankPhase = TELETODAEMONHEIM;
                                errorCount = 0;
                        } else {
                                banking = false;
                        }
                        break;

                case TELETODAEMONHEIM:
                        status = "Teleporting To Daemonheim";
                        Teleporting = true;
                        if (!isRingEquiped()) {
                                log("No Ring of kinship Equiped Local Banking Not Implemented Yet EXIT");
                                return false;
                        }
                        if (atEquipment("heim Ring", RingOfKinshipIDs)) {
                                atEquipment("heim Ring", RingOfKinshipIDs);
                                Teleporting = false;
                                wait(7900, 8000);
                                walkPathMM(bankBeforeTile);
                                walkPathMM(bankTile);
                                openTab(Constants.TAB_INVENTORY);
                                bankPhase = WALKTOBANK1;
                                errorCount = 0;
                                break;
                        } else {
                                errorCount++;
                        }
                        break;

                case WALKTOBANK1:
                        status = "Walking To Bank";
                        if (inFrontOfBank1.contains(player.getLocation())) {
                                bankPhase = WALKTOBANK2;
                                errorCount = 0;
                                break;
                        }
                        if (moveToTile(inFrontOfBank1.getRandomTile())) {
                                bankPhase = WALKTOBANK2;
                                errorCount = 0;
                                break;
                        }
                        if (!(DaemonheimAreaA.contains(player.getLocation()) 
                        	|| DaemonheimAreaB.contains(player.getLocation()) 
                        	|| DaemonheimAreaC.contains(player.getLocation()) 
                        	|| DaemonheimAreaD.contains(player.getLocation())
                        	|| DaemonheimAreaE.contains(player.getLocation())
                        	|| DaemonheimAreaF.contains(player.getLocation())
                        	|| DaemonheimAreaG.contains(player.getLocation())
                        	|| DaemonheimAreaH.contains(player.getLocation())
                        	|| DaemonheimAreaI.contains(player.getLocation())
                        	|| DaemonheimAreaJ.contains(player.getLocation())
                        	|| DaemonheimAreaK.contains(player.getLocation())
                        	|| DaemonheimAreaL.contains(player.getLocation())
                        	|| DaemonheimAreaM.contains(player.getLocation())
                        	|| DaemonheimAreaN.contains(player.getLocation())
                        	|| DaemonheimAreaO.contains(player.getLocation())
                        	|| DaemonheimAreaP.contains(player.getLocation())
                        	|| DaemonheimAreaQ.contains(player.getLocation())
                        	|| DaemonheimAreaR.contains(player.getLocation())) 
                        	&& isInventoryFull()) {
                                bankPhase = TELETODAEMONHEIM;
                                errorCount = 0;
                                break;
                        }
                        if ((DaemonheimAreaA.contains(player.getLocation()) 
                        	|| DaemonheimAreaB.contains(player.getLocation()) 
                        	|| DaemonheimAreaC.contains(player.getLocation()) 
                        	|| DaemonheimAreaD.contains(player.getLocation())
                        	|| DaemonheimAreaE.contains(player.getLocation())
                        	|| DaemonheimAreaF.contains(player.getLocation())
                        	|| DaemonheimAreaG.contains(player.getLocation())
                        	|| DaemonheimAreaH.contains(player.getLocation())
                        	|| DaemonheimAreaI.contains(player.getLocation())
                        	|| DaemonheimAreaJ.contains(player.getLocation())
                        	|| DaemonheimAreaK.contains(player.getLocation())
                        	|| DaemonheimAreaL.contains(player.getLocation())
                        	|| DaemonheimAreaM.contains(player.getLocation())
                        	|| DaemonheimAreaN.contains(player.getLocation())
                        	|| DaemonheimAreaO.contains(player.getLocation())
                        	|| DaemonheimAreaP.contains(player.getLocation())
                        	|| DaemonheimAreaQ.contains(player.getLocation())
                        	|| DaemonheimAreaR.contains(player.getLocation())
                        	&& !(getMyPlayer().isMoving())) 
                        	&& isInventoryFull()) {
                                status = "Walking To Bank";
                                walkPathMM(bankBeforeTile);
                                walkPathMM(bankTile);
                                if (!(getMyPlayer().isMoving())) {
                                        bankPhase = OPENBANK;
                                        errorCount = 0;
                                        break;
                                }
                        }
                        errorCount++;
                        break;

                case WALKTOBANK2:
                        status = "Walking To Bank";
                        if (inFrontOfBank2.contains(player.getLocation())) {
                                bankPhase = OPENBANK;
                                errorCount = 0;
                                break;
                        }
                        if (moveToTile(inFrontOfBank2.getRandomTile())) {
                                bankPhase = OPENBANK;
                                errorCount = 0;
                                break;
                        }
                        if ((DaemonheimAreaA.contains(player.getLocation()) 
                        	|| DaemonheimAreaB.contains(player.getLocation()) 
                        	|| DaemonheimAreaC.contains(player.getLocation()) 
                        	|| DaemonheimAreaD.contains(player.getLocation())
                        	|| DaemonheimAreaE.contains(player.getLocation())
                        	|| DaemonheimAreaF.contains(player.getLocation())
                        	|| DaemonheimAreaG.contains(player.getLocation())
                        	|| DaemonheimAreaH.contains(player.getLocation())
                        	|| DaemonheimAreaI.contains(player.getLocation())
                        	|| DaemonheimAreaJ.contains(player.getLocation())
                        	|| DaemonheimAreaK.contains(player.getLocation())
                        	|| DaemonheimAreaL.contains(player.getLocation())
                        	|| DaemonheimAreaM.contains(player.getLocation())
                        	|| DaemonheimAreaN.contains(player.getLocation())
                        	|| DaemonheimAreaO.contains(player.getLocation())
                        	|| DaemonheimAreaP.contains(player.getLocation())
                        	|| DaemonheimAreaQ.contains(player.getLocation())
                        	|| DaemonheimAreaR.contains(player.getLocation())
                        	&& !(getMyPlayer().isMoving())) 
                        	&& isInventoryFull()) {
                                status = "Walking To Bank";
                                walkPathMM(bankBeforeTile);
                                walkPathMM(bankTile);
                                if (!(getMyPlayer().isMoving())) {
                                        bankPhase = OPENBANK;
                                        errorCount = 0;
                                        break;
                                }
                        }
                        errorCount++;
                        break;

                case OPENBANK:
                        status = "Walking To Bank";
                        FremennikBanker = getNearestNPCByID(FremennikBanker_ID);
                        if (atNPC(FremennikBanker, "k Fremennik")) {
                                status = "Opening Bank";
                                wait(1200, 1300);
                                if (bank.isOpen()) {
                                        bankPhase = DEPOSITALL;
                                        errorCount = 0;
                                        break;
                                }
                        }
                        if (bank.isOpen()) {
                                bankPhase = DEPOSITALL;
                                errorCount = 0;
                                break;
                        }
                        errorCount++;
                        if (errorCount > 5) {
                                bankPhase = WALKTOBANK2;
                        }
                        break;

                case DEPOSITALL:
                        status = "Depositing All";
                        if (bank.isOpen()) {
                                if (bank.depositAll()) {
                                        wait(500, 1000);
                                        bankPhase = CLOSEBANK;
                                        errorCount = 0;
                                        break;
                                }
                        }
                        if (!bank.isOpen()) {
                                bankPhase = OPENBANK;
                                errorCount = 0;
                                break;
                        }
                        errorCount++;
                        break;

                case WITHDRAWRING:
                        status = "Withdrawing Ring";
                        if (!bank.isOpen()) {
                                bankPhase = OPENBANK;
                                errorCount++;
                                break;
                        }
                        RSInterfaceChild iface = getInterface( 762, 17 );
                        atInterface( iface, "Search" );
                        wait(2000);
                        Bot.getInputManager().sendKeysInstant("Ring of kinship", true);
                        wait(2000);
                        i = 0;
                        while (i < RingOfKinshipIDs.length) {
                                if (bank.getCount(RingOfKinshipIDs[i]) >= 1) {
                                        break;
                                }
                                i++;
                        }
                        if (i >= RingOfKinshipIDs.length) {
                                errorCount++;
                                break;
                        }
                        bank.withdraw(RingOfKinshipIDs[i], 1);
                        wait(2000);
                        if (inventoryContainsOneOf(RingOfKinshipIDs)) {
                                bankPhase = WEARRING;
                                errorCount = 0;
                                break;
                        }
                        errorCount++;
                        break;

                case WEARRING:
                        status = "Wearing Ring";
                        if (bank.isOpen()) {
                                bankPhase = CLOSEBANK;
                                errorCount = 0;
                                break;
                        }
                        if (isRingEquiped()) {
                                bankPhase = TELETOSG;
                                errorCount = 0;
                                break;
                        }
                        if (!inventoryContainsOneOf(RingOfKinshipIDs)) {
                                bankPhase = OPENBANK;
                                errorCount = 0;
                                break;
                        }
                        i = 0;
                        while (i < RingOfKinshipIDs.length) {
                                if (getInventoryCount(RingOfKinshipIDs[i]) >= 1) {
                                        break;
                                }
                                i++;
                        }
                        atInventoryItem(RingOfKinshipIDs[i], "");
                        wait(200, 500);
                        if (isRingEquiped()) {
                                bankPhase = TELETOSG;
                                errorCount = 0;
                                break;
                        }
                        errorCount++;
                        break;

                case CLOSEBANK:
                        status = "Closing Bank";
                        if (bank.isOpen()) {
                                bank.close();
                        }
                        if (inventoryContainsOneOf(RingOfKinshipIDs)) {
                                bankPhase = WEARRING;
                                errorCount = 0;
                                break;
                        }
                        bankPhase = TELETOSG;
                        errorCount = 0;
                        break;

                case WITHDRAWMANDP:
                        status = "Withdrawing PestleAndMortar";
                        if (!bank.isOpen()) {
                                bankPhase = OPENBANK;
                                errorCount = 0;
                                break;
                        }
                        if (inventoryContains(PestleAndMortarID)) {
                                bankPhase = TELETOSG;
                                errorCount = 0;
                                break;
                        }
                        bank.withdraw(PestleAndMortarID, 1);
                        wait(500, 700);
                        if (inventoryContains(PestleAndMortarID)) {
                                bankPhase = TELETOSG;
                                errorCount = 0;
                                break;
                        }
                        errorCount++;
                        break;

                case TELETOSG:
                        status = "Teleporting To SG";
                        Teleporting = true;
                        if (bank.isOpen()) {
                                bankPhase = CLOSEBANK;
                                errorCount = 0;
                                break;
                        }
                        if (startPosition.contains(player.getLocation())) {
                                bankPhase = 0;
                                banking = false;
                                break;
                        }
                        atEquipment("Garden Broomstick", broom);
                        Teleporting = false;
                        wait(random(4700, 4800));
                        openTab(Constants.TAB_INVENTORY);
                        if (startPosition.contains(player.getLocation())) {
                                bankPhase = 0;
                                banking = false;
                                break;
                        }
                        errorCount++;
                        break;
                }
                if (errorCount >= MAXERROR) {
                        log("Errored out at Banking Phase: " + bankPhase);
                }
                return true;
        }

        private void clearMenu() {
                final Point po = getMouseLocation();
                final Point p = po;
                p.x += random(120, 140);
                if (random(1, 2) == 2) {
                        p.y += random(1, 140);
                } else {
                        p.y -= random(1, 140);
                }
                if (p.x > 761) {
                        p.x = 761;
                }
                if (p.y < 1) {
                        p.y = 1;
                }
                if (p.y > 500) {
                        p.y = 500;
                }
                moveMouse(4, p.x, p.y, 5, 5);
                wait(random(50, 100));
                moveMouse(4, po.x, po.y, 5, 5);
        }

        private void clearTrackingVars() {
                npc1 = null;
                npc2 = null;
        }

        private int determineBankPhase() {
                if (inventoryContains(glassID)
                                && getInventoryCount(fruitID) >= numFruitNeeded) {
                        return GRINDJUICE;
                }
                if (((getNearestNPCByName("Fremennik banker") != null) || (getNearestObjectByID(9710) != null)) && isInventoryFull()) {
                        moveToTile(inFrontOfBankTile);
                        return OPENBANK;
                }
                if (!(DaemonheimAreaA.contains(player.getLocation()) 
                	|| DaemonheimAreaB.contains(player.getLocation()) 
                	|| DaemonheimAreaC.contains(player.getLocation()) 
                	|| DaemonheimAreaD.contains(player.getLocation())
                	|| DaemonheimAreaE.contains(player.getLocation())
                	|| DaemonheimAreaF.contains(player.getLocation())
                	|| DaemonheimAreaG.contains(player.getLocation())
                	|| DaemonheimAreaH.contains(player.getLocation())
                	|| DaemonheimAreaI.contains(player.getLocation())
                	|| DaemonheimAreaJ.contains(player.getLocation())
                	|| DaemonheimAreaK.contains(player.getLocation())
                	|| DaemonheimAreaL.contains(player.getLocation())
                	|| DaemonheimAreaM.contains(player.getLocation())
                	|| DaemonheimAreaN.contains(player.getLocation())
                	|| DaemonheimAreaO.contains(player.getLocation())
                	|| DaemonheimAreaP.contains(player.getLocation())
                	|| DaemonheimAreaQ.contains(player.getLocation())
                	|| DaemonheimAreaR.contains(player.getLocation())) 
                	&& isInventoryFull()) {
                        return TELETODAEMONHEIM;
                }
                if (!inFrontOfBank1.contains(player.getLocation()) && isInventoryFull()) {
                        return WALKTOBANK1;
                }
                if (!inFrontOfBank2.contains(player.getLocation()) && isInventoryFull()) {
                        return WALKTOBANK2;
                }
                if (inventoryContainsOneOf(RingOfKinshipIDs) && !isRingEquiped()) {
                        return WEARRING;
                }
                if (!inventoryContains(PestleAndMortarID)) {
                        return WITHDRAWMANDP;
                }
                if (inventoryContains(PestleAndMortarID)
                                && isRingEquiped() && isBroomEquiped()) {
                        return TELETOSG;
                }
                return 0;
        }

        private boolean isBroomEquiped() {
                if (equipmentContains(broom)) {
                        return true;
                }
                return false;
        }

        private boolean isRingEquiped() {
                int i = 0;
                for (i = 0; i < RingOfKinshipIDs.length; i++) {
                        if (equipmentContains(RingOfKinshipIDs[i])) {
                                break;
                        }
                }
                if (i < RingOfKinshipIDs.length) {
                        return true;
                }
                return false;
        }

        private final double getEnergyPower() {
                if (RSInterface.getInterface(750).getChild(5).isValid()) {
                        if (RSInterface.getInterface(750).getChild(5).getText() != null) {
                                EnergyPower = Integer.parseInt(RSInterface.getInterface(750).getChild(5).getText());
                        } else {
                                log("getEnergyPower() Error");
                        }
                } else {
                        log("EnergyPower Interface is not valid");
                }

                return EnergyPower;
        }

        @Override
        public int loop() {

                if (errorCount > MAXERROR) {
                        log("Errored Out EXIT");
                        return -1;
                }

                if (getEnergyPower() > 0) {
                        setRun(true);
                }

                if (!isLoggedIn()) {
                        log("You have logged out.");
                        return antiBan();
                }

                // Energy Check
                if (getEnergyPower() < 11 || waitingForRun) {
                        if (getEnergyPower() >= 99) {
                                waitingForRun = false;
                                return antiBan();
                        } else {
                                status = "Waiting for Energy";
                                waitingForRun = true;
                                while (getMyPlayer().isMoving()) {
                                        wait(random(20, 30));
                                }
                                if (!playerIsResting() || player.getAnimation() == -1) {
                                        moveMouse(random(711, 732), random(95, 116));
                                        wait(random(700, 800));
                                        atMenu("Rest");
                                        wait(random(1300, 1500));
                                }
                        }
                        return antiBan();
                }

                // Grind all our fruit and future banking
                if (isInventoryFull() || banking) {
                        banking = true;
                        if (!Bank()) {
                                log("Banking Proccess Failed");
                                return -1;
                        }
                        return antiBan();
                }

                if (!playerIsNear(new RSTile(2905, 5465), 80)) {
                        status = "Teleporting To SG";
                        if (bank.isOpen()) {
                                return antiBan();
                        }
                        if (startPosition.contains(player.getLocation())) {
                                return antiBan();
                        }
                        atEquipment("Sorceress's Garden", broom);
                        wait(random(4500, 5500));
                        if (startPosition.contains(player.getLocation())) {
                                return antiBan();
                        }
                        errorCount++;
                        return antiBan();
                }

                if (garden.equals("Autumn")) {
                        if (runTheMazeAutumn()) {
                                return antiBan();
                        }
                } else if (garden.equals("Summer")) {
                        if (runTheMazeSummer()) {
                                return antiBan();
                        }
                } else {
                        log.severe("You need to have at least 45 lv thieving to run the script.");
                        return -1;
                }
                return -1;
        }

        private boolean moveToMiniMapTile(final RSTile t) {
                Point p = Calculations.worldToMinimap(t.getX(), t.getY());
                if (p.x == -1 || p.y == -1) {
                        return false;
                }
                moveMouse(p);
                p = Calculations.worldToMinimap(t.getX(), t.getY());
                if (p.x == -1 || p.y == -1) {
                        return false;
                }
                moveMouse(2, p.x, p.y, 0, 0);
                clickMouse(true);
                return true;
        }

        private boolean moveToScreenTile(final RSTile t) {
                Point p = Calculations.tileToScreen(t);
                if (Calculations.onScreen(p)) {
                        moveMouse(p.x, p.y, 5, 5);
                        p = Calculations.tileToScreen(t);
                        if (Calculations.onScreen(p)) {
                                moveMouse(2, p.x, p.y, 5, 5);
                                clickMouse(true);
                                return true;
                        } else {
                                return false;
                        }
                } else {
                        return false;
                }
        }

        private boolean moveToTile(final RSTile t) {
                Point p = Calculations.tileToScreen(t);
                if (Calculations.onScreen(p)) {
                        moveMouse(p.x, p.y, 5, 5);
                        p = Calculations.tileToScreen(t);
                        if (Calculations.onScreen(p)) {
                                moveMouse(2, p.x, p.y, 5, 5);
                                clickMouse(true);
                                return true;
                        } else {
                                return moveToMiniMapTile(t);
                        }
                } else {
                        return moveToMiniMapTile(t);
                }
        }

        @Override
        public void onFinish() {
                Bot.getEventManager().removeListener(PaintListener.class, this);
                if(getherbs==0) {
                        log("Total Fruit Picked\uFF1A" + fruitPicked);
                        log("Times Caught\uFF1A" + timesCaught);
                } else {
                        log("Total Herbs Picked\uFF1A" + fruitPicked * 2);
                        log("Times Caught\uFF1A" + timesCaught);
                } 
        }

        public void onRepaint(Graphics g) {

                ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, 
                RenderingHints.VALUE_COLOR_RENDER_QUALITY);

                ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_RENDERING, 
                RenderingHints.VALUE_RENDER_QUALITY);  

                if (isLoggedIn()) {

                        final double elapsedTime = System.currentTimeMillis() - startTime;
                        final Formatter f = new Formatter();
                        final Formatter f2 = new Formatter();

                        final String time = f.format("%d : %02d : %02d",
                                                new Integer((int) (elapsedTime / 1000 / 60 / 60)),
                                                new Integer((int) (elapsedTime / 1000 / 60 % 60)),
                                                new Integer((int) (elapsedTime / 1000) % 60)).toString();

                        if (startLevel == 0) {
                                startLevel = skills.getCurrentSkillLevel(Constants.STAT_FARMING);
                        }

                        levelsGained = skills.getCurrentSkillLevel(STAT_FARMING) - startLevel;
                        nextLevel = skills.getCurrentSkillLevel(STAT_FARMING) + 1;

                        Mouse m = Bot.getClient().getMouse();

                        // Paint Control Panel's Button
                        if (HidePanelOrNot == 0) {
                                if (m.x >= 300 && m.x < 300 + 70 && m.y >= 32 && m.y < 32 + 15) {
                                        paintInt = 1;
                                }
                                if (m.x >= 370 && m.x < 370 + 70 && m.y >= 32 && m.y < 32 + 15) {
                                        paintInt = 2;
                                }
                                if (m.x >= 420 && m.x < 420 + 70 && m.y >= 32 && m.y < 32 + 15) {
                                        paintInt = 3;
                                }
                        }

                        // Piant Control Panel
                        if (HidePanelOrNot == 1) {
                                g.setColor(color1);
                                g.fillRoundRect(420, 3, 96, 30, 16, 16);
                                g.setColor(color3);
                                g.drawRoundRect(420, 3, 96, 30, 16, 16);

                                g.setColor(new Color(255, 255, 255));
                                g.drawString("Display back", 434, 22);

                                // Paint Control Panel's Button
                                if (m.x >= 420 && m.x < 420 + 90 && m.y >= 10 && m.y < 10 + 15) {
                                        HidePanelOrNot = 0;
                                }

                        } else {

                                g.setColor(color1);
                                g.fillRoundRect(300, 3, 216, 108, 16, 16);
                                g.setColor(color3);
                                g.drawRoundRect(300, 3, 216, 108, 16, 16);

                                g.setColor(new Color(255, 255, 255));
                                g.drawString("-- Paint Control Panel --", 347, 22);
                                g.setColor(new Color(255, 255, 255));
                                g.drawString("Full Size", 315, 42);
                                g.setColor(new Color(255, 255, 255));
                                g.drawString("Mini Size", 385, 42);
                                g.setColor(new Color(255, 255, 255));
                                g.drawString("Paint Off", 455, 42);
                                g.setColor(new Color(255, 255, 255));
                                g.drawString("Hide Control Panel", 359, 62);
                                g.setColor(new Color(255, 255, 255));
                                g.drawString("Paint By\uFF1AFletch To 99", 315, 82);
                                g.setColor(new Color(255, 255, 255));
                                g.drawString("Modified By\uFF1AHumpy & Anarki", 315, 97);

                                // Paint Control Panel's Button
                                if (m.x >= 347 && m.x < 347 + 130 && m.y >= 50 && m.y < 50 + 15) {
                                        HidePanelOrNot = 1;
                                }

                        }

                        // Full Sized Paint
                        if (paintInt == 1) {

                                if(getherbs==0) {

                                        final int x = 533;
                                        int y = 245;

                                        g.setColor(color1);
                                        g.fillRoundRect(x - 10, 204, 238, 263, 16, 16);
                                        g.setColor(color3);
                                        g.drawRoundRect(x - 10, 204, 238, 263, 16, 16);
                                        g.drawImage(img1, 677, 250, null);
                                        g.setColor(new Color(255, 255, 255));

                                        if (getGarden == 2) {
                                                g.drawString("-- Autumn Garden --", x + 57, y - 20);
                                        } else if (getGarden == 1) {
                                                g.drawString("-- Summer Garden --", x + 57, y - 20);
                                        } else if (getGarden == 0) {
                                                g.drawString(" -- Invalid Garden --", x + 57, y - 20);
                                        }

                                        g.drawString("Running Time\uFF1A" + time, x, y);
                                        g.drawString("Fruit Picked\uFF1A" + fruitPicked, x, y += 15);
                                        g.drawString("Times Caught\uFF1A" + timesCaught, x, y += 15);
                                        g.drawString("Thieving Exp / Hour\uFF1A" + new Long((long) (fruitPicked * expPerJuice / (elapsedTime / 1000 / 60 / 60))) + " xp".toString(), x, y += 15);
                                        g.drawString("Farming Exp / Hour\uFF1A" + new Long((long) (fruitPicked * FarmingExpPerPick / ( elapsedTime / 1000 / 60 / 60))) + " xp".toString(), x, y += 15);
                                        g.drawString(f2.format("Average Lap Speed\uFF1A%.3f sec", averageRunTime / 1000).toString(), x, y += 15);
                                        g.drawString("Farming Levels Gained\uFF1A" + levelsGained, x, y + 30);
                                        g.drawString("Status\uFF1A" + status , x, y += 75);
                                        g.drawRect(x + 20, y + 10, 155, 20);
                                        g.setColor(new Color(31, 150, 31));
                                        g.fillRect(x + 21, y + 11, (skills.getPercentToNextLevel(STAT_FARMING) * 155 / 100), 19);
                                        g.setColor(new Color(255, 255, 255));
                                        g.drawString("" + skills.getPercentToNextLevel(STAT_FARMING) + "% to " + nextLevel + " lv Farming", x + 34, y += 25);
                                        g.drawString("By\uFF1AHumpy & Anarki", x, y += 30);

                                } else {

                                        final int x = 533;
                                        int y = 245;

                                        g.setColor(color1);
                                        g.fillRoundRect(x - 10, 204, 238, 263, 16, 16);
                                        g.setColor(color3);
                                        g.drawRoundRect(x - 10, 204, 238, 263, 16, 16);
                                        g.drawImage(img1, 677, 250, null);
                                        g.setColor(new Color(255, 255, 255));

                                        if (getGarden == 2) {
                                                g.drawString("-- Autumn Garden --", x + 57, y - 20);
                                        } else if (getGarden == 1) {
                                                g.drawString("-- Summer Garden --", x + 57, y - 20);
                                        } else if (getGarden == 0) {
                                                g.drawString(" -- Invalid Garden --", x + 57, y - 20);
                                        }

                                        g.drawString("Running Time\uFF1A" + time, x, y);
                                        g.drawString("Herbs Picked\uFF1A" + fruitPicked * 2 , x, y += 15);
                                        g.drawString("Times Caught\uFF1A" + timesCaught, x, y += 15);
                                        g.drawString("Farming Exp / Hour\uFF1A" + new Long((long) (fruitPicked * FarmingExpPerPick / ( elapsedTime / 1000 / 60 / 60))) + " xp".toString(), x, y += 15);
                                        g.drawString(f2.format("Average Lap Speed\uFF1A%.3f sec", averageRunTime / 1000).toString(), x, y += 15);
                                        g.drawString("Farming Levels Gained\uFF1A" + levelsGained, x, y + 30);
                                        g.drawString("Status\uFF1A" + status , x, y += 90);
                                        g.drawRect(x + 20, y + 10, 155, 20);
                                        g.setColor(new Color(31, 150, 31));
                                        g.fillRect(x + 21, y + 11, (skills.getPercentToNextLevel(STAT_FARMING) * 155 / 100), 19);
                                        g.setColor(new Color(255, 255, 255));
                                        g.drawString("" + skills.getPercentToNextLevel(STAT_FARMING) + "% to " + nextLevel + " lv Farming", x + 34, y += 25);
                                        g.drawString("By\uFF1AHumpy & Anarki", x, y += 30);
                                }
                        }

                        // Mini Sized Paint
                        if (paintInt == 2) {

                                if(getherbs==0) {

                                        final int x = 295;
                                        int y = 199;

                                        g.setColor(color1);
                                        g.fillRoundRect(x - 2, y, 222, 138, 16, 16);
                                        g.setColor(color3);
                                        g.drawRoundRect(x - 2, y, 222, 138, 16, 16);
                                        g.setColor(new Color(255, 255, 255));

                                        if (getGarden == 2) {
                                                g.drawString("                 -- Autumn Garden --", x, y += 18);
                                        } else if (getGarden == 1) {
                                                g.drawString("                 -- Summer Garden --", x, y += 18);
                                        } else if (getGarden == 0) {
                                                g.drawString("                 -- Invalid Garden --", x, y += 18);
                                        }

                                        g.drawString("Status\uFF1A" + status, x + 10, y += 20);
                                        g.drawString("Run Time\uFF1A" + time, x + 10, y += 15);
                                        g.drawString("Fruit Picked\uFF1A" + fruitPicked, x + 10, y += 15);
                                        g.drawString("Times Caught\uFF1A" + timesCaught, x + 10, y += 15);
                                        g.drawString(f2.format("Average Lap Speed\uFF1A%.3f sec", averageRunTime / 1000).toString(), x + 10, y += 15);
                                        g.drawString("By\uFF1AHumpy & Anarki", x + 10, y += 30);

                                } else {

                                        final int x = 295;
                                        int y = 199;

                                        g.setColor(color1);
                                        g.fillRoundRect(x - 2, y, 222, 138, 16, 16);
                                        g.setColor(color3);
                                        g.drawRoundRect(x - 2, y, 222, 138, 16, 16);
                                        g.setColor(new Color(255, 255, 255));

                                        if (getGarden == 2) {
                                                g.drawString("                 -- Autumn Garden --", x, y += 18);
                                        } else if (getGarden == 1) {
                                                g.drawString("                 -- Summer Garden --", x, y += 18);
                                        } else if (getGarden == 0) {
                                                g.drawString("                 -- Invalid Garden --", x, y += 18);
                                        }

                                        g.drawString("Status\uFF1A" + status, x + 10, y += 20);
                                        g.drawString("Run Time\uFF1A" + time, x + 10, y += 15);
                                        g.drawString("Herbs Picked\uFF1A" + fruitPicked*2, x + 10, y += 15);
                                        g.drawString("Times Caught\uFF1A" + timesCaught, x + 10, y += 15);
                                        g.drawString(f2.format("Average Lap Speed\uFF1A%.3f sec", averageRunTime / 1000).toString(), x + 10, y += 15);
                                        g.drawString("By\uFF1AHumpy & Anarki", x + 10, y += 30);

                                }
                        }

                        if (cursor != null) {
                                ((Graphics2D) g).setRenderingHints(rh);
                                final Mouse mouse = Bot.getClient().getMouse();
                                final int mouse_x = mouse.getMouseX();
                                final int mouse_y = mouse.getMouseY();
                                final long mpt = System.currentTimeMillis()
                                                - mouse.getMousePressTime();

                                if (mouse.getMousePressTime() == -1 || mpt >= 1000)
                                        g.drawImage(cursor, mouse_x, mouse_y, null);

                                if (mpt < 200)
                                        g.drawImage(cursor20, mouse_x, mouse_y, null);

                                if (mpt < 400 && mpt >= 200)
                                        g.drawImage(cursor40, mouse_x, mouse_y, null);

                                if (mpt < 600 && mpt >= 400)
                                        g.drawImage(cursor60, mouse_x, mouse_y, null);

                                if (mpt < 1000 && mpt >= 600)
                                        g.drawImage(cursor80, mouse_x, mouse_y, null);

                        }
                }
        }

        @Override
        public boolean onStart(final Map<String, String> args) {

                status = "Starting Up";

                if (!isLoggedIn()) {
                        log.severe("Please start the script after you had already logged in.");
                        stopScript();
                }

                timer.start();
                if(args.get("group1").equals("Fruit")) {
                        getherbs = 0;
                } else {
                        getherbs = 1;
                }

                player = getMyPlayer();
                paintInt = 1;
                HidePanelOrNot = 0;

                inFrontOfBank2.createWeightedTileArray(new int[] { 1, 2, 1 }, new int[] { 1, 2 });
                outSideGateSummer.createWeightedTileArray(new int[] { 1, 1, 2 }, new int[] { 1 });
                outSideGateAutumn.createWeightedTileArray(new int[] { 1, 1, 1 }, new int[] { 1, 1, 1 });
                inFrontOfTreeSummer.createWeightedTileArray(new int[] { 3, 1, 1 }, new int[] { 3, 1, 1 });
                startTime = System.currentTimeMillis();
                int tlevel = skills.getCurrentSkillLevel(Skills.getStatIndex("thieving"));

                if (tlevel >= 65) {
                        getGarden = 1;
                        garden = "Summer";
                        fruitID = fruitIDSummer;
                        numFruitNeeded = 2;
                        expPerJuice = 1500;
                        if(getherbs==0) {
                                FarmingExpPerPick = 60;
                        } else {
                                FarmingExpPerPick = 50;
                        }
                } else if (tlevel >= 45) {
                        getGarden = 2;
                        garden = "Autumn";
                        fruitID = fruitIDAutumn;
                        numFruitNeeded = 3;
                        expPerJuice = 783.33;
                        FarmingExpPerPick = 50;
                } else {
                        garden = "";
                }

                try {
                        final URL url = new URL(
                                        "http://i288.photobucket.com/albums/ll166/anarki999/ThievingSkillcape.png");
                        img1 = ImageIO.read(url);
                } catch (final IOException e) {
                        log("Unable to get Thieving Skillcape picture.");
                        e.printStackTrace();
                }

                try {
                        final URL cursorURL = new URL(
                                        "http://i288.photobucket.com/albums/ll166/anarki999/arrow.png");
                        final URL cursor80URL = new URL(
                                        "http://i288.photobucket.com/albums/ll166/anarki999/arrow80.png");
                        final URL cursor60URL = new URL(
                                        "http://i288.photobucket.com/albums/ll166/anarki999/arrow60.png");
                        final URL cursor40URL = new URL(
                                        "http://i288.photobucket.com/albums/ll166/anarki999/arrow40.png");
                        final URL cursor20URL = new URL(
                                        "http://i288.photobucket.com/albums/ll166/anarki999/arrow20.png");
                        cursor = ImageIO.read(cursorURL);
                        cursor80 = ImageIO.read(cursor80URL);
                        cursor60 = ImageIO.read(cursor60URL);
                        cursor40 = ImageIO.read(cursor40URL);
                        cursor20 = ImageIO.read(cursor20URL);
                } catch (MalformedURLException e) {
                        log("Unable to buffer cursor.");
                } catch (IOException e) {
                        log("Unable to open cursor image.");
                }

                setCameraRotation(45);
                setCameraAltitude(true);
                setRun(true);

                return true;
        }

        private boolean openBank(final RSTile bankTile, final String action) {
                if (bank.isOpen()) {
                        return true;
                }
                if (isMenuOpen()) {
                        clearMenu();
                }
                Point p = Calculations.tileToScreen(bankTile, 0);
                if (p.x == -1 || p.y == -1) {
                        wait(random(1000, 1500));
                        return false;
                }
                moveMouse(p.x, p.y, 3, 3);
                if (bank.isOpen()) {
                        return true;
                }
                p = Calculations.tileToScreen(bankTile, 0);
                if (p.x == -1 || p.y == -1) {
                        wait(random(1000, 1500));
                        return false;
                }
                moveMouse(2, p.x, p.y, 3, 3);
                if (bank.isOpen()) {
                        return true;
                }
                clickMouse(false);
                wait(random(50, 100));
                atMenu(action);
                wait(random(1000, 1500));
                if (bank.isOpen()) {
                        return true;
                }
                return false;
        }

        private boolean playerIsAt(final RSTile loc) {
                if (player.getLocation().equals(loc)) {
                        return true;
                }
                return false;
        }

        public boolean playerIsNear(final RSArea here, final int tolerance) {
                return here.contains(player.getLocation());
        }

        public boolean playerIsNear(final RSTile here, final int tolerance) {
                final int px = player.getLocation().getX();
                final int py = player.getLocation().getY();
                final int x = here.getX();
                final int y = here.getY();
                if (px >= x - tolerance && px <= x + tolerance && py >= y - tolerance
                                && py <= y + tolerance) {
                        return true;
                }
                return false;
        }

        private boolean playerIsResting() {
                final int anim = player.getAnimation();
                for (final int restingAnimation : restingAnimations) {
                        if (anim == restingAnimation) {
                                return true;
                        }
                }
                return false;
        }

        private boolean runTheMazeAutumn() {
                switch (autumnPhase) {
                case AUTUMNATSTART:
                        status = "Moving To Gate";
                        caught = false;
                        if (moveToTile(AutumnGateTile)) {
                                moveMouse(230, 259);
                                setCameraRotation(20);
                                autumnPhase = AUTUMNOUTSIDEGATE;
                                errorCount = 0;
                                startRunTime = System.currentTimeMillis();
                                clearTrackingVars();
                                break;
                        }
                        if (waitTimeOut > 25) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        errorCount++;
                        break;

                case AUTUMNOUTSIDEGATE:
                        status = "Moving To Gate";
                        if (playerIsAt(pastGateAutumn) && enteringTheGate) {
                                enteringTheGate = false;
                                status = "Waiting at Gate";
                                autumnPhase = AUTUMNGOPOSITION1;
                                errorCount = 0;
                                break;
                        }
                        if (atTile(gatePositionAutumn, 40, 0.5, 1, "")) {
                                status = "Passing the Gate";
                                setCameraRotation(45);
                                moveMouse(110, 92);
                                enteringTheGate = true;
                                wait(800, 800);
                        }
                        if (playerIsAt(pastGateAutumn) && enteringTheGate) {
                                enteringTheGate = false;
                                status = "Waiting at Gate Postion";
                                autumnPhase = AUTUMNGOPOSITION1;
                                errorCount = 0;
                                break;
                        }
                        if (waitTimeOut > 25) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        wait(300, 400);
                        errorCount++;
                        break;

                case AUTUMNGOPOSITION1:
                        setCameraRotation(45);
                        if (caught) {
                                autumnPhase = 1;
                                break;
                        }
                        if (playerIsAt(CaughtAutumnPostion) && !(getMyPlayer().isMoving())) {
                                autumnPhase = 1;
                                break;
                        }
                        if (playerIsAt(pastGateAutumn)) {
                                status = "Waiting at Gate Postion";
                        }
                        if (moveToScreenTile(position1Autumn)) {
                                status = "Moving To Position 1";
                                autumnPhase = AUTUMNWAITPOSITION2;
                                clearTrackingVars();
                                errorCount = 0;
                                break;
                        }
                        if (waitTimeOut > 25) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        errorCount++;
                        break;

                case AUTUMNWAITPOSITION2:
                        status = "Moving To Position 1";
                        setCameraRotation(45);
                        if (caught) {
                                autumnPhase = 1;
                                break;
                        }
                        if (playerIsAt(CaughtAutumnPostion) && !(getMyPlayer().isMoving())) {
                                autumnPhase = 1;
                                break;
                        }
                        if (playerIsNear(position1Autumn, 1)) {
                                status = "Waiting at Position 1";
                        }
                        if (npc1 == null) {
                                npc1 = getNearestNPCByID(elemental1Autumn);
                                wait(100);
                                if (npc1 == null) {
                                        break;
                                }
                        }
                        moveMouse(111, 52);
                        if (getDirection(npc1) == 512
                                        && npc1.getLocation().getX() <= 2906
                                        && npc1.getLocation().getX() >= 2902
                                        && playerIsAt(position1Autumn)
                                        && npc1.getAnimation() == -1) {
                                if (!moveToScreenTile(position2Autumn)) {
                                        errorCount++;
                                        break;
                                }
                                status = "Moving To Position 2";
                                autumnPhase = AUTUMNWAITPOSITION3;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        waitTimeOut++;
                        if (waitTimeOut > 25) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        break;

                case AUTUMNWAITPOSITION3:
                        status = "Moving To Position 2";
                        setCameraRotation(45);
                        if (caught) {
                                autumnPhase = 1;
                                break;
                        }
                        if (playerIsAt(CaughtAutumnPostion) && !(getMyPlayer().isMoving())) {
                                autumnPhase = 1;
                                break;
                        }
                        if (playerIsNear(position2Autumn, 1)) {
                                status = "Waiting at Position 2";
                        }
                        if (npc1 == null) {
                                npc1 = getNearestNPCByID(elemental1Autumn);
                                wait(100);
                                if (npc1 == null) {
                                        break;
                                }
                        }
                        moveMouse(60, 306);
                        if (((getDirection(npc1) == 1536
                                        && npc1.getLocation().getX() <= 2908
                                        && npc1.getLocation().getX() >= 2900)
                                        || (getDirection(npc1) == 512
                                        && ((npc1.getLocation().getX() <= 2909
                                        && npc1.getLocation().getX() >= 2908)
                                        || npc1.getLocation().getX() == 2901)))
                                        && npc1.getAnimation() == -1) {
                                if (!moveToScreenTile(position3Autumn)) {
                                        errorCount++;
                                        break;
                                }
                                status = "Moving To Position 3";
                                autumnPhase = AUTUMNWAITPOSITION4;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        waitTimeOut++;
                        if (waitTimeOut > 25) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        break;

                case AUTUMNWAITPOSITION4:
                        status = "Moving To Position 3";
                        setCameraRotation(45);
                        if (caught) {
                                autumnPhase = 1;
                                break;
                        }
                        if (playerIsAt(CaughtAutumnPostion) && !(getMyPlayer().isMoving())) {
                                autumnPhase = 1;
                                break;
                        }
                        if (playerIsNear(position3Autumn, 1)) {
                                status = "Waiting at Position 3";
                        }
                        if (npc1 == null) {
                                npc1 = getNearestNPCByID(elemental2Autumn);
                                wait(100);
                                if (npc1 == null) {
                                        break;
                                }
                        }
                        moveMouse(151, 280);
                        if (npc1.getLocation().getY() <= 5455
                                        && npc1.getLocation().getY() >= 5454
                                        && getDirection(npc1) == 0
                                        && playerIsNear(position3Autumn, 1)) {
                                if (!moveToScreenTile(position4Autumn)) {
                                        errorCount++;
                                        break;
                                }
                                status = "Moving To Position 4";
                                autumnPhase = AUTUMNWAITPOSITION5;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        waitTimeOut++;
                        if (waitTimeOut > 25) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        break;

                case AUTUMNWAITPOSITION5:
                        status = "Moving To Position 4";
                        setCameraRotation(45);
                        if (caught) {
                                autumnPhase = 1;
                                break;
                        }
                        if (playerIsAt(CaughtAutumnPostion) && !(getMyPlayer().isMoving())) {
                                autumnPhase = 1;
                                break;
                        }
                        if (playerIsNear(position4Autumn, 1)) {
                                status = "Waiting at Position 4";
                        }
                        if (npc1 == null) {
                                npc1 = getNearestNPCByID(elemental2Autumn);
                                wait(100);
                                if (npc1 == null) {
                                        break;
                                }
                        }
                        if (npc2 == null) {
                                npc2 = getNearestNPCByID(elemental3Autumn);
                                wait(100);
                                if (npc2 == null) {
                                        break;
                                }
                        }
                        moveMouse(287, 251);
                        if (npc2.getLocation().getX() == 2899
                                        && ((getDirection(npc1) == 1024
                                        && npc1.getLocation().getY() >= 5449
                                        && npc1.getLocation().getY() <= 5454)
                                        || (getDirection(npc1) == 0
                                        && npc1.getLocation().getY() <= 5451
                                        && npc1.getLocation().getY() >= 5449))
                                        && playerIsNear(position4Autumn, 1)
                                        && npc1.getAnimation() == -1) {
                                if (!moveToScreenTile(position5Autumn)) {
                                        errorCount++;
                                        break;
                                }
                                status = "Moving To Position 5";
                                autumnPhase = AUTUMNWAITPOSITION6;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        waitTimeOut++;
                        if (waitTimeOut > 25) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        break;

                case AUTUMNWAITPOSITION6:
                        status = "Moving To Position 5";
                        setCameraRotation(260);
                        if (caught) {
                                autumnPhase = 1;
                                break;
                        }
                        if (playerIsAt(CaughtAutumnPostion) && !(getMyPlayer().isMoving())) {
                                autumnPhase = 1;
                                break;
                        }
                        if (playerIsNear(position5Autumn, 1)) {
                                status = "Waiting at Position 5";
                        }
                        if (npc1 == null) {
                                npc1 = getNearestNPCByID(elemental4Autumn);
                                wait(100);
                                if (npc1 == null) {
                                        break;
                                }
                        }
                        if (npc2 == null) {
                                npc2 = getNearestNPCByID(elemental5Autumn);
                                wait(100);
                                if (npc2 == null) {
                                        break;
                                }
                        }
                        moveMouse(35, 64);
                        if ((!(npc1.getLocation().getY() < 5452)
                                        && ((getDirection(npc2) == 1536
                                        && npc2.getLocation().getX() < 2916)
                                        || getDirection(npc2) == 1024
                                        || (getDirection(npc2) == 512
                                        && npc2.getLocation().getX() < 2907)))
                                        && playerIsNear(position5Autumn, 1)) {
                                if (!moveToScreenTile(position6Autumn)) {
                                        errorCount++;
                                        break;
                                }
                                status = "Moving To Position 6";
                                if (getherbs==1) {
                                        autumnPhase = AUTUMNPICKHERBPATH;
                                } else {
                                        autumnPhase = AUTUMNWAITPOSITION7;
                                }
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        waitTimeOut++;
                        if (waitTimeOut > 30) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        break;

                case AUTUMNWAITPOSITION7:
                        status = "Moving To Position 6";
                        setCameraRotation(320);
                        if (caught) {
                                autumnPhase = 1;
                                break;
                        }
                        if (playerIsAt(CaughtAutumnPostion) && !(getMyPlayer().isMoving())) {
                                autumnPhase = 1;
                                break;
                        }
                        if (playerIsNear(position6Autumn, 1)) {
                                status = "Waiting at Position 6";
                        }
                        if (npc1 == null) {
                                npc1 = getNearestNPCByID(elemental6Autumn);
                                wait(100);
                                if (npc1 == null) {
                                        break;
                                }
                        }
                        moveMouse(488, 140);
                        if (getDirection(npc1) == 1536
                                        && npc1.getLocation().getX() >= 2910
                                        && npc1.getLocation().getX() < 2915
                                        && playerIsNear(position6Autumn, 1)
                                        && npc1.getAnimation() == -1) {
                                if (!moveToScreenTile(position7Autumn)) {
                                        errorCount++;
                                        break;
                                }
                                status = "Moving To Tree";
                                autumnPhase = AUTUMNPICKFRUIT;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        waitTimeOut++;
                        if (waitTimeOut > 25) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        break;

                case AUTUMNPICKFRUIT:
                        status = "Moving To Tree";
                        setCameraRotation(315);
                        if (caught) {
                                waitingForGoAgain = false;
                                autumnPhase = 1;
                                break;
                        }
                        if (playerIsAt(CaughtAutumnPostion) && !(getMyPlayer().isMoving())) {
                                waitingForGoAgain = false;
                                autumnPhase = 1;
                                break;
                        }
                        if (playerIsNear(position7Autumn, 1)) {
                                status = "Picking Fruit";
                        } else {
                                status = "Moving To Tree";
                        }
                        if (startPosition.contains(player.getLocation()) && waitingForGoAgain) {
                                waitingForGoAgain = false;
                                errorCount++;
                                autumnPhase = 1;
                                break;
                        }
                        if (playerIsAt(startPositionTile) && waitingForGoAgain) {
                                waitingForGoAgain = false;
                                errorCount++;
                                autumnPhase = 1;
                                break;
                        }
                        if (!moveToScreenTile(fruitTreeAutumn[random(1, 200) % 2])) {
                                errorCount++;
                                break;
                        }
                        if (playerIsNear(position7Autumn, 1)) {
                                status = "Picking Fruit";
                        } else {
                                status = "Moving To Tree";
                        }
                        if (waitTimeOut > 25) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        waitingForGoAgain = true;
                        wait(2900, 2950);
                        break;

                case AUTUMNPICKHERBPATH:
                        status = "Moving To Position 6";
                        setCameraRotation(315);
                        if (caught) {
                                autumnPhase = 1;
                                break;
                        }
                        if (playerIsAt(CaughtAutumnPostion) && !(getMyPlayer().isMoving())) {
                                autumnPhase = 1;
                                break;
                        }
                        if (playerIsNear(position6Autumn, 1)) {
                                status = "Waiting at Position 6";
                        }
                        if (npc1 == null) {
                                npc1 = getNearestNPCByID(elemental5Autumn);
                                wait(100);
                                if (npc1 == null) {
                                        break;
                                }
                        }
                        moveMouse(313, 25);
                        if(((getDirection(npc1)==512
                                        && npc1.getLocation().getX() >= 2904
                                        && npc1.getLocation().getX() <= 2910)
                                        || (getDirection(npc1)==1536
                                        && npc1.getLocation().getX() >= 2910
                                        && npc1.getLocation().getX() <= 2915))
                                        && playerIsNear(position6Autumn, 1)) {
                                if (!moveToScreenTile(position8Autumn)) {
                                        errorCount++;
                                        break;
                                }
                                status = "Moving To Position 7";
                                autumnPhase = AUTUMNPICKHERB;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        if (waitTimeOut > 25) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        break;

                case AUTUMNPICKHERB:
                        status = "Moving To Position 7";
                        setCameraRotation(315);
                        if (caught) {
                                autumnPhase = 1;
                                break;
                        }
                        if (playerIsAt(CaughtAutumnPostion) && !(getMyPlayer().isMoving())) {
                                autumnPhase = 1;
                                break;
                        }
                        if (playerIsNear(position8Autumn, 1)) {
                                status = "Waiting at Position 7";
                        }
                        if (npc1 == null) {
                                npc1 = getNearestNPCByID(elemental5Autumn);
                                wait(100);
                                if (npc1 == null) {
                                        break;
                                }
                        }
                        moveMouse(325, 101);
                        if(((getDirection(npc1)==512
                                        && npc1.getLocation().getX() >= 2904
                                        && npc1.getLocation().getX() < 2916)
                                        || (getDirection(npc1)==1536
                                        && ((npc1.getLocation().getX() >= 2911
                                        && npc1.getLocation().getX() < 2916)
                                        || ((npc1.getLocation().getX() >= 2904
                                        && npc1.getLocation().getX() <= 2908)
                                        || npc1.getLocation().getX() == 2904))))
                                        && playerIsNear(position8Autumn, 1)
                                        && npc1.getAnimation() == -1) {
                                if (!moveToScreenTile(herbAutumn)) {
                                        errorCount++;
                                        break;
                                }
                                status = "Moving To Herbs";
                                autumnPhase = AUTUMNPICKHERBCLICK;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        if (waitTimeOut > 25) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        break;

                case AUTUMNPICKHERBCLICK:
                        status = "Moving To Herbs";
                        while (playerIsNear(HerbsPickingPostionAutumn, 1)) {
                                status = "Picking Herbs";
                        }
                        if (caught) {
                                waitingForGoAgain = false;
                                autumnPhase = 1;
                                break;
                        }
                        if (playerIsAt(CaughtAutumnPostion) && !(getMyPlayer().isMoving())) {
                                waitingForGoAgain = false;
                                autumnPhase = 1;
                                break;
                        }
                        if (startPosition.contains(player.getLocation()) && waitingForGoAgain) {
                                waitingForGoAgain = false;
                                errorCount++;
                                autumnPhase = 1;
                                break;
                        }
                        if (playerIsAt(startPositionTile) && waitingForGoAgain) {
                                waitingForGoAgain = false;
                                errorCount++;
                                autumnPhase = 1;
                                break;
                        }
                        if (!moveToScreenTile(herbAutumn)) {
                                errorCount++;
                                break;
                        }
                        if (playerIsNear(HerbsPickingPostionAutumn, 1)) {
                                status = "Picking Herbs";
                        } else {
                                status = "Moving To Herbs";
                        }
                        if (waitTimeOut > 25) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        waitingForGoAgain = true;
                        wait(2800, 2900);
                        break;


                default:
                        if (startPosition.contains(player.getLocation())) {
                                autumnPhase = AUTUMNATSTART;
                        } else if (outSideGateAutumn.contains(player.getLocation())) {
                                autumnPhase = AUTUMNOUTSIDEGATE;
                        } else if (playerIsNear(pastGateAutumn, 1)) {
                                autumnPhase = AUTUMNGOPOSITION1;
                        } else if (playerIsAt(position1Autumn)) {
                                autumnPhase = AUTUMNWAITPOSITION2;
                        } else if (playerIsNear(position2Autumn, 1)) {
                                autumnPhase = AUTUMNWAITPOSITION3;
                        } else if (playerIsNear(position3Autumn, 1)) {
                                autumnPhase = AUTUMNWAITPOSITION4;
                        } else if (playerIsNear(position4Autumn, 1)) {
                                autumnPhase = AUTUMNWAITPOSITION5;
                        } else if (playerIsNear(position5Autumn, 1)) {
                                autumnPhase = AUTUMNWAITPOSITION6;
                        } else if (playerIsNear(position6Autumn, 1)) {
                                if (getherbs==1) {
                                        autumnPhase = AUTUMNPICKHERBPATH;
                                } else {
                                        autumnPhase = AUTUMNWAITPOSITION7;
                                }
                        } else if (playerIsNear(position7Autumn, 1)) {
                                autumnPhase = AUTUMNPICKFRUIT;
                        } else if (playerIsNear(position8Autumn, 1)) {
                                autumnPhase = AUTUMNPICKHERB;
                        } else if (inFrontOfTreeAutumn.contains(player.getLocation())) {
                                autumnPhase = AUTUMNPICKFRUIT;
                        } else {
                                errorCount++;
                        }
                        break;
                }

                if (errorCount == MAXERROR / 2) {
                        autumnPhase = 0;
                        errorCount++;
                }

                if (errorCount > MAXERROR) {
                        log("Autumn Garden Run Failed at Phase: " + autumnPhase + ", "
                                        + status);
                }
                return true;
        }

        private boolean runTheMazeSummer() {
                switch (summerPhase) {
                case SUMMERATSTART:
                        status = "Moving To Gate";
                        caught = false;
                        if (moveToTile(outSideGateSummer.getRandomTile())) {
                                setCameraRotation(45);
                                moveMouse(random(400, 420), random(80, 90));
                                summerPhase = SUMMEROUTSIDEGATE;
                                errorCount = 0;
                                startRunTime = System.currentTimeMillis();
                                clearTrackingVars();
                                break;
                        }
                        if (waitTimeOut > 25) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        errorCount++;
                        break;

                case SUMMEROUTSIDEGATE:
                        status = "Moving To Gate";
                        if (playerIsAt(pastGateSummer) && enteringTheGate) {
                                enteringTheGate = false;
                                status = "Waiting at Gate Postion";
                                summerPhase = SUMMERWAITPOSITION1;
                                errorCount = 0;
                                break;
                        }
                        if (atTile(gatePositionSummer, 10, 0.5, 1, "")) {
                                status = "Passing Gate";
                                summerPhase = SUMMERWAITPOSITION1;
                                errorCount = 0;
                                enteringTheGate = true;
                                break;
                        }
                        if (playerIsAt(pastGateSummer) && enteringTheGate) {
                                enteringTheGate = false;
                                status = "Waiting at Gate Postion";
                                summerPhase = SUMMERWAITPOSITION1;
                                errorCount = 0;
                                break;
                        }
                        if (waitTimeOut > 25) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        wait(600, 650);
                        errorCount++;
                        break;

                case SUMMERWAITPOSITION1:
                        moveMouse(74, 68);
                        setCameraRotation(341);
                        wait(540, 550);
                        if (caught) {
                                summerPhase = 1;
                                break;
                        }
                        if (playerIsAt(CaughtSummerPostion) && !(getMyPlayer().isMoving())) {
                                summerPhase = 1;
                                break;
                        }
                        if (playerIsAt(pastGateSummer) && enteringTheGate) {
                                enteringTheGate = false;
                        }
                        if (!(getMyPlayer().isMoving())) {
                                status = "Waiting at Gate Postion";
                        }
                        if (npc1 == null) {
                                npc1 = getNearestNPCByID(elemental1Summer);
                                wait(100);
                                if (npc1 == null) {
                                        break;
                                }
                        }
                        if (((getDirection(npc1) == 0
                                        && (npc1.getLocation().getY() == 5483
                                        || npc1.getLocation().getY() == 5484
                                        || npc1.getLocation().getY() == 5485
                                        || npc1.getLocation().getY() < 5486))
                                        || npc1.getLocation().getY() < 5485
                                        || !(npc1.getLocation().getY() <= 5488
                                        || npc1.getLocation().getY() >= 5483))
                                        && playerIsAt(pastGateSummer)
                                        && npc1.getAnimation() == -1) {
                                if (!moveToScreenTile(position1Summer)) {
                                        errorCount++;
                                        break;
                                }
                                status = "Moving To Position 1";
                                summerPhase = SUMMERWAITPOSITION2;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        if (playerIsAt(BeforePastGateSummer)) {
                                summerPhase = SUMMEROUTSIDEGATE;
                                errorCount = 0;
                                break;
                        }
                        if (playerIsAt(ErrorPastGatePostionSummer)) {
                                moveToScreenTile(pastGateSummer);
                                errorCount = 0;
                        }
                        waitTimeOut++;
                        if (waitTimeOut > 59) {
                                moveToScreenTile(position1Summer);
                                summerPhase = SUMMERWAITPOSITION2;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        break;

                case SUMMERWAITPOSITION2:
                        status = "Moving To Position 1";
                        setCameraRotation(45);
                        if (caught) {
                                summerPhase = 1;
                                break;
                        }
                        if (playerIsAt(CaughtSummerPostion) && !(getMyPlayer().isMoving())) {
                                summerPhase = 1;
                                break;
                        }
                        if (playerIsNear(position1Summer, 1)) {
                                status = "Waiting at Position 1";
                        }
                        if (playerIsAt(pastGateSummer) && enteringTheGate) {
                                status = "Waiting at Gate Postion";
                                enteringTheGate = false;
                        }
                        if (npc1 == null) {
                                npc1 = getNearestNPCByID(elemental1Summer);
                                wait(100);
                                if (npc1 == null) {
                                        break;
                                }
                        }
                        moveMouse(397, 42);
                        if (((getDirection(npc1) == 0
                                        && npc1.getLocation().getY() <= 5488
                                        && npc1.getLocation().getY() >= 5485)
                                        || npc1.getLocation().getY() > 5487
                                        || !(npc1.getLocation().getX() == 2907))
                                        && (player.getLocation().getY() < 5487
                                        && player.getLocation().getY() >= 5485)
                                        && npc1.getAnimation() == -1) {
                                if (!moveToScreenTile(position2Summer)) {
                                        errorCount++;
                                        break;
                                }
                                status = "Moving To Position 2";
                                summerPhase = SUMMERWAITPOSITION3;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        waitTimeOut++;
                        if (waitTimeOut > 25) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        break;

                case SUMMERWAITPOSITION3:
                        status = "Moving To Position 2";
                        setCameraRotation(45);
                        if (caught) {
                                summerPhase = 1;
                                break;
                        }
                        if (playerIsAt(CaughtSummerPostion) && !(getMyPlayer().isMoving())) {
                                summerPhase = 1;
                                break;
                        }
                        if (playerIsNear(position2Summer, 1)) {
                                status = "Waiting at Position 2";
                        }
                        if (npc1 == null) {
                                npc1 = getNearestNPCByID(elemental2Summer);
                                wait(100);
                                if (npc1 == null) {
                                        break;
                                }
                        }
                        moveMouse(281, 300);
                        if (((getDirection(npc1) == 0
                                        && npc1.getLocation().getY() <= 5494
                                        && npc1.getLocation().getY() >= 5492)
                                        || npc1.getLocation().getY() == 5493)
                                        && (player.getLocation().getY() < 5493
                                        && player.getLocation().getY() >= 5491)
                                        && npc1.getAnimation() == -1) {
                                if (!moveToScreenTile(position3Summer)) {
                                        errorCount++;
                                        break;
                                }
                                status = "Moving To Position 3";
                                summerPhase = SUMMERWAITPOSITION4;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        waitTimeOut++;
                        if (waitTimeOut > 25) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        break;

                case SUMMERWAITPOSITION4:
                        status = "Moving To Position 3";
                        setCameraRotation(300);
                        if (caught) {
                                summerPhase = 1;
                                break;
                        }
                        if (playerIsAt(CaughtSummerPostion) && !(getMyPlayer().isMoving())) {
                                summerPhase = 1;
                                break;
                        }
                        if (playerIsNear(position3Summer, 1)) {
                                status = "Waiting at Position 3";
                        }
                        if (npc1 == null) {
                                npc1 = getNearestNPCByID(elemental3Summer);
                                wait(100);
                                if (npc1 == null) {
                                        break;
                                }
                        }
                        moveMouse(459, 196);
                        if (((getDirection(npc1) == 1024
                                        && npc1.getLocation().getY() <= 5491
                                        && npc1.getLocation().getY() >= 5488)
                                        || (getDirection(npc1) == 0
                                        && npc1.getLocation().getY() <= 5490
                                        && npc1.getLocation().getY() >= 5489)
                                        || (getDirection(npc1) == 0
                                        && npc1.getLocation().getY() == 5490))
                                        && (player.getLocation().getY() < 5492
                                        && player.getLocation().getY() >= 5490)
                                        && npc1.getAnimation() == -1) {
                                if (!moveToScreenTile(position4Summer)) {
                                        errorCount++;
                                        break;
                                }
                                status = "Moving To Position 4";
                                summerPhase = SUMMERWAITPOSITION5;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        waitTimeOut++;
                        if (waitTimeOut > 25) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        break;

                case SUMMERWAITPOSITION5:
                        status = "Moving To Position 4";
                        setCameraRotation(328);
                        if (caught) {
                                summerPhase = 1;
                                break;
                        }
                        if (playerIsAt(CaughtSummerPostion) && !(getMyPlayer().isMoving())) {
                                summerPhase = 1;
                                break;
                        }
                        if (playerIsNear(position4Summer, 1)) {
                                status = "Waiting at Position 4";
                        }
                        if (npc1 == null) {
                                npc1 = getNearestNPCByID(elemental4Summer);
                                wait(100);
                                if (npc1 == null) {
                                        break;
                                }
                        }
                        moveMouse(466, 35);
                        if (!(npc1.getLocation().getX() == 2912
                                        || npc1.getLocation().getX() < 2913
                                        || npc1.getLocation().getX() == 2917
                                        || npc1.getLocation().getX() == 2918)
                                        && playerIsNear(position4Summer, 1)
                                        && npc1.getAnimation() == -1) {
                                if (!moveToScreenTile(position5Summer)) {
                                        errorCount++;
                                        break;
                                }
                                status = "Moving To Position 5";
                                summerPhase = SUMMERWAITPOSITION6;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        waitTimeOut++;
                        if (waitTimeOut > 25) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        break;

                case SUMMERWAITPOSITION6:
                        status = "Moving To Position 5";
                        setCameraRotation(328);
                        if (caught) {
                                summerPhase = 1;
                                break;
                        }
                        if (playerIsAt(CaughtSummerPostion) && !(getMyPlayer().isMoving())) {
                                summerPhase = 1;
                                break;
                        }
                        if (playerIsNear(position5Summer, 1)) {
                                status = "Waiting at Position 5";
                        }
                        if (moveToScreenTile(position6Summer)) {
                                status = "Moving To Position 6";
                                if (getherbs==1) {
                                        summerPhase = SUMMERPICKHERB;	
                                } else {
                                        summerPhase = SUMMERWAITPOSITION7;
                                }
                                clearTrackingVars();
                                errorCount = 0;
                                break;
                        }
                        if (waitTimeOut > 25) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        break;

                case SUMMERWAITPOSITION7:
                        status = "Moving To Position 6";
                        setCameraRotation(322);
                        if (caught) {
                                summerPhase = 1;
                                break;
                        }
                        if (playerIsAt(CaughtSummerPostion) && !(getMyPlayer().isMoving())) {
                                summerPhase = 1;
                                break;
                        }
                        if (playerIsNear(position6Summer, 1)) {
                                status = "Waiting at Position 6";
                        }
                        if (npc1 == null) {
                                npc1 = getNearestNPCByID(elemental5Summer);
                                wait(100);
                                if (npc1 == null) {
                                        break;
                                }
                        }
                        moveMouse(298, 78);
                        if ((!(getDirection(npc1) == 0
                                        && (npc1.getLocation().getY() < 5489
                                        || npc1.getLocation().getY() == 5488
                                        || npc1.getLocation().getY() == 5487
                                        || npc1.getLocation().getY() == 5486))
                                        && !(getDirection(npc1) == 512
                                        && (npc1.getLocation().getX() == 2922
                                        || npc1.getLocation().getX() == 2923)))
                                        && playerIsNear(position6Summer, 3)
                                        && npc1.getAnimation() == -1) {
                                if (!moveToScreenTile(position7Summer)) {
                                        errorCount++;
                                        break;
                                }
                                status = "Moving To Position 7";
                                summerPhase = SUMMERWAITPOSITION8;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        waitTimeOut++;
                        if (waitTimeOut > 25) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        break;

                case SUMMERWAITPOSITION8:
                        status = "Moving To Position 7";
                        setCameraRotation(322);
                        if (caught) {
                                summerPhase = 1;
                                break;
                        }
                        if (playerIsAt(CaughtSummerPostion) && !(getMyPlayer().isMoving())) {
                                summerPhase = 1;
                                break;
                        }
                        if (playerIsNear(position7Summer, 1)) {
                                status = "Waiting at Position 7";
                        }
                        if (npc1 == null) {
                                npc1 = getNearestNPCByID(elemental5Summer);
                                wait(100);
                                if (npc1 == null) {
                                        break;
                                }
                        }
                        if (npc2 == null) {
                                npc2 = getNearestNPCByID(elemental6Summer);
                                wait(100);
                                if (npc2 == null) {
                                        break;
                                }
                        }
                        moveMouse(110, 239);
                        if ((getDirection(npc1) == 0
                                        || (getDirection(npc1) == 512
                                        && !(npc1.getLocation().getX() < 2922))
                                        || (getDirection(npc1) == 1024
                                        && npc1.getLocation().getY() == 5488))
                                        && !(npc2.getLocation().getY() >= 5494)
                                        && npc1.getAnimation() == -1
                                        && npc2.getAnimation() == -1) {
                                if (!moveToScreenTile(position8Summer)) {
                                        errorCount++;
                                        break;
                                }
                                status = "Moving To Position 8";
                                summerPhase = SUMMERGOPOSITION9;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        waitTimeOut++;
                        if (waitTimeOut > 60) {
                                moveToScreenTile(position8Summer);
                                summerPhase = SUMMERGOPOSITION9;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        break;

                case SUMMERGOPOSITION9:
                        status = "Moving To Position 8";
                        if (caught) {
                                summerPhase = 1;
                                break;
                        }
                        if (playerIsAt(CaughtSummerPostion) && !(getMyPlayer().isMoving())) {
                                summerPhase = 1;
                                break;
                        }
                        if (playerIsNear(position8Summer, 1)) {
                                status = "Waiting at Position 8";
                        }
                        if (!moveToTile(inFrontOfTreeSummerTile)) {
                                errorCount++;
                                break;
                        }
                        status = "Moving To Tree";
                        summerPhase = SUMMERPICKFRUIT;
                        clearTrackingVars();
                        errorCount = 0;
                        break;

                case SUMMERPICKFRUIT:
                        setCameraRotation(345);
                        if (caught) {
                                waitingForGoAgain = false;
                                summerPhase = 1;
                                break;
                        }
                        if (playerIsAt(CaughtSummerPostion) && !(getMyPlayer().isMoving())) {
                                waitingForGoAgain = false;
                                summerPhase = 1;
                                break;
                        }
                        if (playerIsNear(FruitPickingPostion1Summer, 1)
                                        || playerIsNear(FruitPickingPostion2Summer, 1)
                                        || playerIsNear(FruitPickingPostion3Summer, 1)
                                        || playerIsNear(FruitPickingPostion4Summer, 1)) {
                                status = "Picking Fruit";
                        } else {
                                status = "Moving To Tree";
                        }
                        if (startPosition.contains(player.getLocation()) && waitingForGoAgain) {
                                waitingForGoAgain = false;
                                errorCount++;
                                summerPhase = SUMMERATSTART;
                                break;
                        }
                        if (playerIsAt(startPositionTile) && waitingForGoAgain) {
                                waitingForGoAgain = false;
                                errorCount++;
                                summerPhase = SUMMERATSTART;
                                break;
                        }
                        moveMouse(42, 226);
                        if (!moveToScreenTile(fruitTreeSummer[random(1, 200) % 2])) {
                                errorCount++;
                                break;
                        }
                        if (playerIsNear(FruitPickingPostion1Summer, 1)
                                        || playerIsNear(FruitPickingPostion2Summer, 1)
                                        || playerIsNear(FruitPickingPostion3Summer, 1)
                                        || playerIsNear(FruitPickingPostion4Summer, 1)) {
                                status = "Picking Fruit";
                        } else {
                                status = "Moving To Tree";
                        }
                        waitingForGoAgain = true;
                        wait(2400, 2450);
                        break;

		case SUMMERPICKHERB:
                        status = "Moving To Postion 6";
                        setCameraRotation(347);
                        if (caught) {
                                summerPhase = 1;
                                break;
                        }
                        if (playerIsAt(CaughtSummerPostion) && !(getMyPlayer().isMoving())) {
                                summerPhase = 1;
                                break;
                        }
                        if (playerIsNear(position6Summer, 1)) {
                                status = "Waiting at Position 6";
                        }
                        if (startPosition.contains(player.getLocation())) {
                                errorCount++;
                                summerPhase = SUMMERATSTART;
                                break;
                        }
                        if (playerIsAt(startPositionTile)) {
                                errorCount++;
                                summerPhase = SUMMERATSTART;
                                break;
                        }
                        if (npc1 == null) {
                                npc1 = getNearestNPCByID(elemental5Summer);
                                wait(100);
                                if (npc1 == null) {
                                        break;
                                }
                        }
                        moveMouse(356, 229);
                        if ((!(getDirection(npc1) == 0
                                        && (npc1.getLocation().getY() < 5489
                                        || npc1.getLocation().getY() == 5488
                                        || npc1.getLocation().getY() == 5487
                                        || npc1.getLocation().getY() == 5486))
                                        && !(getDirection(npc1) == 512
                                        && (npc1.getLocation().getX() == 2922
                                        || npc1.getLocation().getX() == 2923)))
                                        && playerIsNear(position6Summer, 3)
                                        && npc1.getAnimation() == -1) {
                                if (!moveToScreenTile(herbSummer)) {
                                        errorCount++;
                                        break;
                                }
                                status = "Moving To Herbs";
                                summerPhase = SUMMERPICKHERBCLICK;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        waitTimeOut++;
                        if (waitTimeOut > 25) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        break;

		case SUMMERPICKHERBCLICK:
                        if (caught) {
                                waitingForGoAgain = false;
                                summerPhase = 1;
                                break;
                        }
                        if (playerIsAt(CaughtSummerPostion) && !(getMyPlayer().isMoving())) {
                                waitingForGoAgain = false;
                                summerPhase = 1;
                                break;
                        }
                        if (playerIsNear(HerbsPickingPostionSummer, 1)) {
                                status = "Picking Herbs";
                        } else {
                                status = "Moving To Herbs";
                        }
                        if (startPosition.contains(player.getLocation()) && waitingForGoAgain) {
                                waitingForGoAgain = false;
                                errorCount++;
                                summerPhase = SUMMERATSTART;
                                break;
                        }
                        if (playerIsAt(startPositionTile) && waitingForGoAgain) {
                                waitingForGoAgain = false;
                                errorCount++;
                                summerPhase = SUMMERATSTART;
                                break;
                        }
                        if (!moveToScreenTile(herbSummer)) {
                                errorCount++;
                                break;
                        }
                        if (playerIsNear(HerbsPickingPostionSummer, 1)) {
                                status = "Picking Herbs";
                        } else {
                                status = "Moving To Herbs";
                        }
                        waitingForGoAgain = true;
                        wait(2100, 2150);
                        break;

                default:
                        if (startPosition.contains(player.getLocation())) {
                                summerPhase = SUMMERATSTART;
                        } else if (outSideGateSummer.contains(player.getLocation())) {
                                summerPhase = SUMMEROUTSIDEGATE;
                        } else if (playerIsNear(pastGateSummer, 1)) {
                                summerPhase = SUMMERWAITPOSITION1;
                        } else if (playerIsNear(position1Summer, 1)) {
                                summerPhase = SUMMERWAITPOSITION2;
                        } else if (playerIsNear(position2Summer, 1)) {
                                summerPhase = SUMMERWAITPOSITION3;
                        } else if (playerIsNear(position3Summer, 1)) {
                                summerPhase = SUMMERWAITPOSITION4;
                        } else if (playerIsNear(position4Summer, 1)) {
                                summerPhase = SUMMERWAITPOSITION5;
                        } else if (playerIsNear(position5Summer, 1)) {
                                summerPhase = SUMMERWAITPOSITION6;
                        } else if (playerIsNear(position6Summer, 1)) {
                                if (getherbs==1) {
                                        summerPhase = SUMMERPICKHERB;
                                } else {
                                        summerPhase = SUMMERWAITPOSITION7;
                                }
                        } else if (playerIsNear(position7Summer, 1)) {
                                summerPhase = SUMMERWAITPOSITION8;
                        } else if (playerIsNear(position8Summer, 2)) {
                                summerPhase = SUMMERGOPOSITION9;
                        } else if (inFrontOfTreeSummer.contains(player.getLocation())) {
                                summerPhase = SUMMERPICKFRUIT;
                        } else if (playerIsNear(HerbsPickingPostionSummer, 1)) {
                                summerPhase = SUMMERPICKHERBCLICK;
                        } else {
                                errorCount++;
                        }
                        break;
                }

                if (errorCount == MAXERROR / 2) {
                        summerPhase = 0;
                        errorCount++;
                }

                if (errorCount > MAXERROR) {
                        log("Summer Garden Run Failed at Phase: " + summerPhase + ", "
                                        + status);
                }
                return true;
        }

        // These Last functions should be temporary overrides until the originals
        // are fixed

        public void serverMessageRecieved(final ServerMessageEvent e) {
                final String mess = e.getMessage();
                if (mess == null) {
                        return;
                }
                if (mess.startsWith("An elemental force")) {
                        fruitPicked++;
                        summerPhase = 1;
                        if (startRunTime != 0) {
                                completeRuns++;
                                final long runTime = System.currentTimeMillis() - startRunTime;
                                averageRunTime = ((completeRuns - 1) * averageRunTime + runTime)
                                                / completeRuns;
                                summerPhase = 1;
                        }
                } else if (mess.startsWith("You've been spotted")) {
                        caught = true;
                        summerPhase = 1;
                        startRunTime = 0;
                        timesCaught++;
                }
        }

        private void setMouse(final RSTile loc) {
                final Point p = Calculations.tileToScreen(loc);
                if (Calculations.onScreen(p)) {
                        if (p.distance(getMouseLocation()) > 100) {
                                ;
                        }
                        moveMouse(p);
                }
        }

        private boolean useInventoryItems(final int item1, final int item2) {
                if (getCurrentTab() != Constants.TAB_INVENTORY) {
                        openTab(Constants.TAB_INVENTORY);
                }
                final int[] items = getInventoryArray();
                int index1, index2;
                for (index1 = 0; index1 < items.length; index1++) {
                        if (items[index1] == item1) {
                                break;
                        }
                }
                for (index2 = 0; index2 < items.length; index2++) {
                        if (items[index2] == item2) {
                                break;
                        }
                }
                if (index1 >= items.length || index2 >= items.length) {
                        return false;
                }
                Point p = getInventoryItemPoint(index1);
                clickMouse(p.x, p.y, 5, 5, true);
                p = getInventoryItemPoint(index2);
                clickMouse(p.x, p.y, 5, 5, true);
                return true;
        }

        @Override
        public boolean useItem(final RSItem item, final RSItem targetItem) {
                if (getCurrentTab() != Constants.TAB_INVENTORY) {
                        openTab(Constants.TAB_INVENTORY);
                }
                atInventoryItem(item.getID(), "Use");
                return atInventoryItem(targetItem.getID(), "Use");
        }

        public void wait(final int a, final int b) {
                wait(random(a, b));
        }

        public class timer implements Runnable {
                public void didSomeoneSaySomething(int c) {
                        if (!getLastMessage().toLowerCase().isEmpty() && !getLastMessage().toLowerCase().equals(firstMessage)) {
                                if (beepActive) {
                                        beep(c);
                                }
                                firstMessage = getLastMessage().toLowerCase();
                        }
                }

                public void beep(int count) {
                        try {
                                for (int i = 0; i < count; i++) {
                                        java.awt.Toolkit.getDefaultToolkit().beep();
                                        Thread.sleep(250);
                                }
                                Thread.sleep(random(100, 500));
                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                return;
                }

                @Override
                public void run() {
                        while (!timer.isInterrupted()) {
                                didSomeoneSaySomething(3);
                                try {
                                         Thread.sleep(random(50, 150));
                                } catch (InterruptedException e) {
                                        e.printStackTrace();
                                }
                        }
                }
        }

        @Override
        /*
        * * Access the last message spoken by a player. Fixed by Master8899.
        * 
        * @return The last message spoken by a player or "" if none
        */
        public String getLastMessage() {
                RSInterface chatBox =  RSInterface.getInterface(INTERFACE_CHAT_BOX);
                for  (int i = 281; i >= 181; i--) {// Valid text is from 181 to 281
                        String text = chatBox.getChild(i).getText();
                        if  (!text.isEmpty() && text.contains("<")) {
                                return  text;
                        } 
                } 
                return "";
        }

}