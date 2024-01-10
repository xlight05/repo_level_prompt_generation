import java.util.ArrayList;
import java.util.Formatter;
import java.util.Map;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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



@ScriptManifest(authors = { "Humpy" }, category = "@Mini-Game", name = "Garden of Magic", version = 1.94, description = "<html><head></head><body><h2><b>Garden of Magic</b></h2><br/><b>Author:</b> Humpy (credit to Blurner and Jankal)<br/><input type='radio' name='group1' value='Fruit' checked> Fruit<br><input type='radio' name='group1' value='Herb'> Herb<br>Select which item you would like to pick then click OK. This script will figure out the best garden you are able to enter. If you would like to make the banking process faster please equip a ring of dueling and have more in your bank visible when opened.<br /><b>Note: You must have the broom from swept away equipped and enchanted to teleport to the Sroceress's Garden.</b></body></html>")
public class GardenOfMagic extends Script implements ServerMessageListener, PaintListener {

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
	return random(6, 8);
	}

        private int EnergyPower;

        private final int AUTUMNATSTART = 1;
        private final int AUTUMNGOPOSITION1 = 3;
        private final int AUTUMNGOPOSITION4 = 6;
        private final int AUTUMNOUTSIDEGATE = 2;
        // Autumn Variables------------------------------------------------
        private int autumnPhase = 0;
        private final int AUTUMNPICKFRUIT = 13;
        private final int AUTUMNPICKHERB = 14;
        private final int AUTUMNWAITPOSITION10 = 12;
        private final int AUTUMNWAITPOSITION2 = 4;
        private final int AUTUMNWAITPOSITION3 = 5;
        private final int AUTUMNWAITPOSITION5 = 7;
        private final int AUTUMNWAITPOSITION6 = 8;
        private final int AUTUMNWAITPOSITION7 = 9;
        private final int AUTUMNWAITPOSITION8 = 10;
        private final int AUTUMNWAITPOSITION9 = 11;

        private double averageRunTime = 0;
        private boolean banking = false;
        private int bankPhase = 0;
        private final RSTile bankTile = new RSTile(2445, 3083);
        private final int broom = 14057;
        private boolean caught = false;
        private final int CLOSEBANK = 8;
        private int completeRuns = 0;
        // Banking Variables
        private final RSArea CWArea = new RSArea(2439, 3082, 10, 17);
        private final int DEPOSITALL = 5;
        private final int[] duelRingIDs = { 2566, 2564, 2562, 2560, 2558, 2556,
                        2554, 2552 };
        private final int elemental1Autumn = 5533;
        private final int elemental1Summer = 5547;
        private final int elemental2Autumn = 5534;
        private final int elemental2Summer = 5548;
        private final int elemental3Autumn = 5535;
        private final int elemental3Summer = 5549;
        private final int elemental4Autumn = 5536;
        private final int elemental4Summer = 5550;
        private final int elemental5Autumn = 5537;
        private final int elemental5Summer = 5551;
        private final int elemental6Autumn = 5538;

        private int errorCount = 0;
        private double expPerJuice = 0;
        private double ThievingExpPerPick = 0;
        private int fruitID;
        private final int fruitIDAutumn = 10846;

        private final int fruitIDSummer = 10845;
        private int fruitPicked = 0;
        private final RSTile[] fruitTreeAutumn = { new RSTile(2913, 5451),
                        new RSTile(2912, 5451), new RSTile(2912, 5450) };
        // End Autumn Variables
        private final RSTile[] fruitTreeSummer = { new RSTile(2915, 5491),
                        new RSTile(2916, 5492) };
        private final RSTile herbSummer = new RSTile(2923, 5483);
        private final RSTile herbAutumn = new RSTile(2916, 5458);
	private String garden = "Summer";
        private final RSTile gatePositionAutumn = new RSTile(2913, 5463);
        private final RSTile gatePositionSummer = new RSTile(2910, 5481);
        private final int glassID = 1919;
        private final int GRINDJUICE = 1;
        private final RSArea inFrontOfBank = new RSArea(2443, 3082, 2, 3);
        private final RSArea inFrontOfTreeAutumn = new RSArea(2909, 5448, 8, 7);
        private final RSArea inFrontOfTreeSummer = new RSArea(2916, 5490, 3, 3);
        private final int mandpID = 233;
        private final int MAXERROR = 10;

        private RSNPC npc1, npc2;

        private int numFruitNeeded;
        private final int OPENBANK = 4;
        private final RSArea outSideGateAutumn = new RSArea(2913, 5463, 3, 3);

        private final RSArea outSideGateSummer = new RSArea(2910, 5478, 1, 3);
        private final RSTile pastGateAutumn = new RSTile(2913, 5462);

        private final RSTile pastGateSummer = new RSTile(2910, 5481);

        private RSPlayer player;

        private final RSTile position10Autumn = new RSTile(2910, 5453);
        private final RSTile position10Summer = new RSTile(2920, 5488);

        private final RSTile position11Autumn = new RSTile(2913, 5458);

        private final RSTile position1Autumn = new RSTile(2908, 5461);

        private final RSTile position1Summer = new RSTile(2908, 5482);
        private final RSTile position2Autumn = new RSTile(2902, 5461);

        private final RSTile position2Summer = new RSTile(2906, 5486);
        private final RSTile position3Autumn = new RSTile(2899, 5459);

        private final RSTile position3Summer = new RSTile(2906, 5492);
        private final RSTile position4Autumn = new RSTile(2901, 5455);

        private final RSTile position4Summer = new RSTile(2909, 5490);

        private final RSTile position5Autumn = new RSTile(2901, 5451);
        private final RSTile position5Summer = new RSTile(2909, 5486);

        private final RSTile position6Autumn = new RSTile(2903, 5450);

        private final RSTile position6Summer = new RSTile(2911, 5484);

        private final RSTile position7Autumn = new RSTile(2902, 5453);
        private final RSTile position7Summer = new RSTile(2913, 5482);
        private final RSTile position8Autumn = new RSTile(2906, 5458);
        private final RSTile position8Summer = new RSTile(2919, 5484);
        private final RSTile position9Autumn = new RSTile(2908, 5456);
        private final RSTile position9Summer = new RSTile(2924, 5487);
        private final int[] restingAnimations = { 11786, 5713 };
        // Start Variables
        private final RSArea startPosition = new RSArea(2905, 5465, 14, 14);
        private long startRunTime = 0;
        private long startTime;
        private String status = "";
        private final int SUMMERATSTART = 1;
        private final int SUMMERGOPOSITION1 = 3;
        private final int SUMMERGOPOSITION11 = 13;
        private final int SUMMERGOPOSITION6 = 8;

        private final int SUMMEROUTSIDEGATE = 2;

        // Summer Variables
        private int summerPhase = 0;
	private int getherbs = 0;
	private int getGarden = 0;
        private final int SUMMERPICKFRUIT = 14;
        private final int SUMMERPICKHERB = 15;
        private final int SUMMERWAITPOSITION10 = 12;

        private final int SUMMERWAITPOSITION2 = 4;
        private final int SUMMERWAITPOSITION3 = 5;

        private final int SUMMERWAITPOSITION4 = 6;

        private final int SUMMERWAITPOSITION5 = 7;

        private final int SUMMERWAITPOSITION7 = 9;
        private final int SUMMERWAITPOSITION8 = 10;

        private final int SUMMERWAITPOSITION9 = 11;
        private final int TELETOCW = 2;

        private final int TELETOSG = 11;

        private int timesCaught = 0;
        private boolean waitingForRun = false;

        private int waitTimeOut = 0;

        private final int WALKTOBANK = 3;
        private final int WEARRING = 7;

        private final int WITHDRAWGLASSES = 9;

        private final int WITHDRAWMANDP = 10;

        private final int WITHDRAWRING = 6;

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
                                useInventoryItems(mandpID, fruitID);
                                wait(1000, 1200);
                                if (fruitnum == getInventoryCount(fruitID)) {
                                        errorCount++;
                                }
                        } else if (isInventoryFull()) {
                                bankPhase = TELETOCW;
                                errorCount = 0;
                        } else {
                                banking = false;
                        }
                        break;
                case TELETOCW:
                        status = "Teleporting To Castle Wars";
                        if (CWArea.contains(player.getLocation())) {
                                bankPhase = WALKTOBANK;
                                errorCount = 0;
                                break;
                        }
                        if (!isRingEquiped()) {
                                log("No Dueling Ring Equiped Local Banking Not Implemented Yet EXIT");
                                return false;
                        }
                        if (atEquipment("Castle Wars", duelRingIDs)) {
                                wait(3000, 5000);
                        } else {
                                errorCount++;
                        }
                        break;
                case WALKTOBANK:
                        status = "Walking To Bank";
                        if (inFrontOfBank.contains(player.getLocation())) {
                                bankPhase = OPENBANK;
                                errorCount = 0;
                                break;
                        }
                        if (moveToTile(inFrontOfBank.getRandomTile())) {
                                bankPhase = OPENBANK;
                                errorCount = 0;
                                break;
                        }
                        errorCount++;
                        break;
                case OPENBANK:
                        status = "Opening Bank";
                        if (openBank(bankTile, "Bank")) {
                                bankPhase = DEPOSITALL;
                                errorCount = 0;
                                break;
                        }
                        errorCount++;
                        if (errorCount > 5) {
                                bankPhase = WALKTOBANK;
                        }
                        break;
                case DEPOSITALL:
                        status = "Depositing All";
                        if (!bank.isOpen()) {
                                bankPhase = OPENBANK;
                                errorCount = 0;
                                break;
                        }
                        bank.depositAll();
                        wait(500, 1000);
                        if (!(getInventoryCount() >= 1)) {
                        	if (!isRingEquiped()) {
                               		bankPhase = WITHDRAWRING;
                                	errorCount = 0;
                                	break;
				} else {
                                	bankPhase = TELETOSG;
                                	errorCount = 0;
                                	break;
				}
                        }
                        errorCount++;
                        break;
                case WITHDRAWRING:
                        status = "Withdrawing Ring";
                        if (!bank.isOpen()) {
                        if (!openBank(bankTile, "Bank")) {
                                errorCount++;
                                break;
                        }
                        }
			RSInterfaceChild iface = getInterface( 762, 17 );
			atInterface( iface, "Search" );
			wait(2000);
			Bot.getInputManager().sendKeysInstant("ring of duelling", true);
			wait(2000);
                        i = 0;
                        while (i < duelRingIDs.length) {
                                if (bank.getCount(duelRingIDs[i]) >= 1) {
                                        break;
                                }
                                i++;
                        }
                        if (i >= duelRingIDs.length) {
                                errorCount++;
                                break;
                        }
                        bank.withdraw(duelRingIDs[i], 1);
                        wait(2000);
                        if (inventoryContainsOneOf(duelRingIDs)) {
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
                        if (!inventoryContainsOneOf(duelRingIDs)) {
                                bankPhase = OPENBANK;
                                errorCount = 0;
                                break;
                        }
                        i = 0;
                        while (i < duelRingIDs.length) {
                                if (getInventoryCount(duelRingIDs[i]) >= 1) {
                                        break;
                                }
                                i++;
                        }
                        atInventoryItem(duelRingIDs[i], "");
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
                        if (inventoryContainsOneOf(duelRingIDs)) {
                                bankPhase = WEARRING;
                                errorCount = 0;
                                break;
                        }
                        bankPhase = TELETOSG;
                        errorCount = 0;
                        break;
                case WITHDRAWMANDP:
                        status = "Withdrawing MandP";
                        if (!bank.isOpen()) {
                                bankPhase = OPENBANK;
                                errorCount = 0;
                                break;
                        }
                        if (inventoryContains(mandpID)) {
                                bankPhase = TELETOSG;
                                errorCount = 0;
                                break;
                        }
                        bank.withdraw(mandpID, 1);
                        wait(500, 700);
                        if (inventoryContains(mandpID)) {
                                bankPhase = TELETOSG;
                                errorCount = 0;
                                break;
                        }
                        errorCount++;
                        break;
                case TELETOSG:
                        status = "Teleporting To SG";
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
                        atEquipment("Sorceress's Garden", broom);
                        wait(random(4500, 5500));
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
                if (!CWArea.contains(player.getLocation()) && isInventoryFull()) {
                        return TELETOCW;
                }
                if (!inFrontOfBank.contains(player.getLocation()) && isInventoryFull()) {
                        return WALKTOBANK;
                }
                if (inventoryContainsOneOf(duelRingIDs) && !isRingEquiped()) {
                        return WEARRING;
                }
                if (!inventoryContains(mandpID)) {
                        return WITHDRAWMANDP;
                }
                if (inventoryContains(mandpID)
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
                for (i = 0; i < duelRingIDs.length; i++) {
                        if (equipmentContains(duelRingIDs[i])) {
                                break;
                        }
                }
                if (i < duelRingIDs.length) {
                        return true;
                }
                return false;
        }

	final double getEnergyPower() {
		if (RSInterface.getInterface(750).getChild(5).isValid()) {
			if (RSInterface.getInterface(750).getChild(5).getText() != null) {
				EnergyPower = Integer.parseInt(RSInterface.getInterface(750).getChild(5)
						.getText());
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

                // Energy Check
                if (getEnergyPower() < 11 || waitingForRun) {
                        if (getEnergyPower() >= 99) {
                                waitingForRun = false;
                        } else {
                                status = "Waiting for Run Energy";
                                waitingForRun = true;
                                while (getMyPlayer().isMoving()) {
                                        wait(random(20, 30));
                                }
                                if (!playerIsResting()) {
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
                        log("Invalid Garden");
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
                ScreenshotUtil.takeScreenshot(true);
                Bot.getEventManager().removeListener(PaintListener.class, this);
                log("Total Fruit Picked: " + fruitPicked);
                log("Times Caught: " + timesCaught);
        }

        public void onRepaint(final Graphics g) {

                if (!isLoggedIn() || g == null) {
                        return;
                }

                if(getherbs==0) {

                        final int x = 7;
                        int y = 210;
                        final double elapsedTime = System.currentTimeMillis() - startTime;
                        final Formatter f = new Formatter();
                        final Formatter f2 = new Formatter();

                        final String time = f.format("%d : %02d : %02d",
                                                new Integer((int) elapsedTime / 1000 / 60 / 60),
                                                new Integer((int) (elapsedTime / 1000 / 60 % 60)),
                                                new Integer((int) (elapsedTime / 1000) % 60)).toString();

                        g.setColor(new Color(110, 110, 110, 170));
                        g.fill3DRect(x - 2, y, 211, 128, true);
                        g.setColor(Color.white);
                        if (getGarden == 2) {
                                g.drawString("                 -- Autumn Garden --", x, y += 15);
                        } else if (getGarden == 1) {
                                g.drawString("                 -- Summer Garden --", x, y += 15);
                        } else if (getGarden == 0) {
                                g.drawString("", x, y += 15);
                        }
                        g.drawString("Status\uFF1A" + status, x, y += 15);
                        g.drawString("Run Time\uFF1A" + time, x, y += 15);
                        g.drawString("Fruit Picked\uFF1A" + fruitPicked, x, y += 15);
                        g.drawString("Times Caught\uFF1A" + timesCaught, x, y += 15);
                        g.drawString("Thieving Exp / Hour\uFF1A" + new Long((long) (fruitPicked * expPerJuice / (elapsedTime / 1000 / 60 / 60))) + " xp".toString(), x, y += 15);
                        g.drawString("Farming Exp / Hour\uFF1A" + new Long((long) (fruitPicked * ThievingExpPerPick / ( elapsedTime / 1000 / 60 / 60))) + " xp".toString(), x, y += 15);
                        g.drawString(f2.format("Average Run Time\uFF1A%.3f sec", averageRunTime / 1000).toString(), x, y += 15);

                } else {

                        final int x = 7;
                        int y = 225;
                        final double elapsedTime = System.currentTimeMillis() - startTime;
                        final Formatter f = new Formatter();
                        final Formatter f2 = new Formatter();

                        final String time = f.format("%d : %02d : %02d",
                                                new Integer((int) elapsedTime / 1000 / 60 / 60),
                                                new Integer((int) (elapsedTime / 1000 / 60 % 60)),
                                                new Integer((int) (elapsedTime / 1000) % 60)).toString();

                        g.setColor(new Color(110, 110, 110, 170));
                        g.fill3DRect(x - 2, y, 211, 113, true);
                        g.setColor(Color.white);
                        if (getGarden == 2) {
                                g.drawString("                 -- Autumn Garden --", x, y += 15);
                        } else if (getGarden == 1) {
                                g.drawString("                 -- Summer Garden --", x, y += 15);
                        } else if (getGarden == 0) {
                                g.drawString("", x, y += 15);
                        }
                        g.drawString("Status\uFF1A" + status, x, y += 15);
                        g.drawString("Run Time\uFF1A" + time, x, y += 15);
                        g.drawString("Herbs Picked \uFF1A" + fruitPicked*2, x, y += 15);
                        g.drawString("Times Caught\uFF1A" + timesCaught, x, y += 15);
                        g.drawString("Farming Exp / Hour\uFF1A" + new Long((long) (fruitPicked * ThievingExpPerPick / ( elapsedTime / 1000 / 60 / 60))) + " xp".toString(), x, y += 15);
                        g.drawString(f2.format("Average Run Time\uFF1A%.3f sec", averageRunTime / 1000).toString(), x, y += 15);

                }
        }

        @Override
        public boolean onStart(final Map<String, String> args) {

                if(args.get("group1").equals("Fruit")) {
                        getherbs = 0;
                } else {
                        getherbs = 1;
                }

                player = getMyPlayer();
                inFrontOfBank.createWeightedTileArray(new int[] { 1, 2, 1 }, new int[] { 1, 2 });
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
                                ThievingExpPerPick = 60;
                        } else {
                                ThievingExpPerPick = 50;
                        }
                } else if (tlevel >= 45) {
                        getGarden = 2;
                        garden = "Autumn";
                        fruitID = fruitIDAutumn;
                        numFruitNeeded = 3;
                        expPerJuice = 783.33;
                        ThievingExpPerPick = 50;
                } else {
                        garden = "";
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
                        if (moveToTile(outSideGateAutumn.getRandomTile())) {
                                setCameraRotation(45);
                                autumnPhase = AUTUMNOUTSIDEGATE;
                                errorCount = 0;
                                startRunTime = System.currentTimeMillis();
                                clearTrackingVars();
                                wait(1000, 1500);
                                break;
                        }
                        errorCount++;
                        break;
                case AUTUMNOUTSIDEGATE:
                        status = "Opening Gate";
                        if (playerIsAt(pastGateAutumn)) {
                                autumnPhase = AUTUMNGOPOSITION1;
                                errorCount = 0;
                                break;
                        }
                        if (atTile(gatePositionAutumn, 40, 0.5, 1, "")) {
                                wait(1000, 1500);
                                if (playerIsAt(pastGateAutumn)) {
                                        autumnPhase = AUTUMNGOPOSITION1;
                                        errorCount = 0;
                                        break;
                                }
                        }
                        wait(500, 1000);
                        errorCount++;
                        break;
                case AUTUMNGOPOSITION1:
                        status = "Moving To Position 1";
                        if (moveToScreenTile(position1Autumn)) {
                                autumnPhase = AUTUMNWAITPOSITION2;
                                clearTrackingVars();
                                errorCount = 0;
                                break;
                        }
                        errorCount++;
                        break;
                case AUTUMNWAITPOSITION2:
                        status = "Moving To Position 2";
                        if (caught) {
                                autumnPhase = 1;
                                break;
                        }
                        if (npc1 == null) {
                                npc1 = getNearestNPCByID(elemental1Autumn);
                                wait(100);
                                if (npc1 == null) {
                                        break;
                                }
                        }
                        setMouse(position2Autumn);
                        if (getDirection(npc1) == 512
                                        && npc1.getLocation().getX() <= 2906
                                        && npc1.getLocation().getX() >= 2903
                                        && playerIsAt(position1Autumn)) {
                                if (!moveToScreenTile(position2Autumn)) {
                                        errorCount++;
                                        break;
                                }
                                autumnPhase = AUTUMNWAITPOSITION3;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        waitTimeOut++;
                        if (waitTimeOut > 10) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        break;
                case AUTUMNWAITPOSITION3:
                        status = "Moving To Position 3";
                        if (caught) {
                                autumnPhase = 1;
                                break;
                        }
                        if (npc1 == null) {
                                npc1 = getNearestNPCByID(elemental1Autumn);
                                wait(100);
                                if (npc1 == null) {
                                        break;
                                }
                        }
                        setMouse(position3Autumn);
                        if (getDirection(npc1) == 1536
                                        && npc1.getLocation().getX() <= 2907
                                        && npc1.getLocation().getX() >= 2902
                                        && playerIsAt(position2Autumn)) {
                                if (!moveToScreenTile(position3Autumn)) {
                                        errorCount++;
                                        break;
                                }
                                autumnPhase = AUTUMNGOPOSITION4;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        waitTimeOut++;
                        if (waitTimeOut > 10) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        break;
                case AUTUMNGOPOSITION4:
                        status = "Moving To Position 4";
                        setMouse(position4Autumn);
                        if (moveToScreenTile(position4Autumn)) {
                                autumnPhase = AUTUMNWAITPOSITION5;
                                clearTrackingVars();
                                errorCount = 0;
                                break;
                        }
                        errorCount++;
                        break;
                case AUTUMNWAITPOSITION5:
                        status = "Moving To Position 5";
                        if (caught) {
                                autumnPhase = 1;
                                break;
                        }
                        if (npc1 == null) {
                                npc1 = getNearestNPCByID(elemental2Autumn);
                                wait(100);
                                if (npc1 == null) {
                                        break;
                                }
                        }
                        setMouse(position5Autumn);
                        if (npc1.getLocation().getY() == 5455
                                        && getDirection(npc1) == 0
                                        && playerIsAt(position4Autumn)) {
                                if (!moveToScreenTile(position5Autumn)) {
                                        errorCount++;
                                        break;
                                }
                                autumnPhase = AUTUMNWAITPOSITION6;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        waitTimeOut++;
                        if (waitTimeOut > 10) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        break;
                case AUTUMNWAITPOSITION6:
                        status = "Moving To Position 6";
                        if (caught) {
                                autumnPhase = 1;
                                break;
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
                        setMouse(position6Autumn);
                        if (npc2.getLocation().getX() == 2899
                                        && (getDirection(npc1) == 1024
					|| npc1.getLocation().getY() == 5450)
                                        && npc1.getLocation().getY() <= 5454) {
                                if (!moveToScreenTile(position6Autumn)) {
                                        errorCount++;
                                        break;
                                }
                                autumnPhase = AUTUMNWAITPOSITION7;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        waitTimeOut++;
                        if (waitTimeOut > 25) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        break;
                case AUTUMNWAITPOSITION7:
                        status = "Moving To Position 7";
                        if (caught) {
                                autumnPhase = 1;
                                break;
                        }
                        if (npc1 == null) {
                                npc1 = getNearestNPCByID(elemental4Autumn);
                                wait(100);
                                if (npc1 == null) {
                                        break;
                                }
                        }
                        setMouse(position7Autumn);
                        if (npc1.getLocation().getY() >= 5453
                                        && playerIsAt(position6Autumn)) {
                                if (!moveToScreenTile(position7Autumn)) {
                                        errorCount++;
                                        break;
                                }
                                autumnPhase = AUTUMNWAITPOSITION8;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        waitTimeOut++;
                        if (waitTimeOut > 10) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        break;
                case AUTUMNWAITPOSITION8:
                        status = "Moving To Position 8";
                        if (caught) {
                                autumnPhase = 1;
                                break;
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
                        setMouse(position8Autumn);
                        if ((npc1.getLocation().getY() == 5455 || npc1.getLocation().getY() >= 5452
                                        && npc1.getLocation().getX() == 2905)
                                        && getDirection(npc2) == 1536
                                        && playerIsAt(position7Autumn)) {
                                if (!moveToScreenTile(position8Autumn)) {
                                        errorCount++;
                                        break;
                                }
                                autumnPhase = AUTUMNWAITPOSITION9;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        waitTimeOut++;
                        if (waitTimeOut > 10) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        break;
                case AUTUMNWAITPOSITION9:
                        status = "Moving To Position 9";
                        if (caught) {
                                autumnPhase = 1;
                                break;
                        }
                        if (npc1 == null) {
                                npc1 = getNearestNPCByID(elemental5Autumn);
                                wait(100);
                                if (npc1 == null) {
                                        break;
                                }
                        }
                        setMouse(position9Autumn);
                        if ((getDirection(npc1) == 512
                                        && npc1.getLocation().getX() == 2906
                                        || npc1.getLocation().getX() >= 2914 || npc1.getLocation()
                                        .getX() >= 2908
                                        && getDirection(npc1) == 1536)
                                        && playerIsAt(position8Autumn)) {
                                if (!moveToScreenTile(position9Autumn)) {
                                        errorCount++;
                                        break;
                                }
                                autumnPhase = AUTUMNWAITPOSITION10;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        waitTimeOut++;
                        if (waitTimeOut > 10) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        break;
                case AUTUMNWAITPOSITION10:
                        status = "Moving To Position 10";
			if (getherbs==1) {
				autumnPhase = AUTUMNPICKHERB;
				break;
			}
                        if (caught) {
                                autumnPhase = 1;
                                break;
                        }
                        if (npc1 == null) {
                                npc1 = getNearestNPCByID(elemental6Autumn);
                                wait(100);
                                if (npc1 == null) {
                                        break;
                                }
                        }
                        setMouse(position10Autumn);
                        if (getDirection(npc1) == 1536
                                        && npc1.getLocation().getX() >= 2910
					&& npc1.getLocation().getX() <= 2914
                                        && playerIsAt(position9Autumn)) {
                                if (!moveToScreenTile(position10Autumn)) {
                                        errorCount++;
                                        break;
                                }
                                autumnPhase = AUTUMNPICKFRUIT;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        waitTimeOut++;
                        if (waitTimeOut > 10) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        break;
                case AUTUMNPICKFRUIT:
                        status = "Picking Fruit";
                        if (caught) {
                                autumnPhase = 1;
                                break;
                        }
                        if (startPosition.contains(player.getLocation())) {
                                errorCount++;
                                autumnPhase = 1;
                                break;
                        }
                        if (!moveToScreenTile(fruitTreeAutumn[random(1, 200) % 2])) {
                                errorCount++;
                                break;
                        }
                        wait(2000, 2500);
                        break;
                case AUTUMNPICKHERB:
                        status = "Picking Herb";
                        if (caught) {
                                autumnPhase = 1;
                                break;
                        }
                        if (startPosition.contains(player.getLocation())) {
                                errorCount++;
                                autumnPhase = 1;
                                break;
                        }
			if (playerIsAt(position11Autumn)) {
                        	if (!moveToScreenTile(herbAutumn)) {
                                	errorCount++;
                                	break;
			}
			} else { 
				setMouse(position11Autumn);
				npc1 = getNearestNPCByID(elemental5Autumn);
				if(getDirection(npc1)==512
					&& npc1.getLocation().getX()<=2908
					&& npc1.getLocation().getX()>=2906
					|| getDirection(npc1)==1536
					&& npc1.getLocation().getX()>=2911
					&& npc1.getLocation().getX()<=2915) {
                        		if (!moveToScreenTile(position11Autumn)) {
                                		errorCount++;
                                		break;
					}
				}
			}
                        wait(2000, 2500);
                        break;
                default:
                        if (startPosition.contains(player.getLocation())) {
                                autumnPhase = AUTUMNATSTART;
                        } else if (outSideGateAutumn.contains(player.getLocation())) {
                                autumnPhase = AUTUMNOUTSIDEGATE;
                        } else if (playerIsNear(pastGateAutumn, 1)) {
                                autumnPhase = AUTUMNGOPOSITION1;
                        } else if (playerIsNear(position1Autumn, 1)) {
                                autumnPhase = AUTUMNGOPOSITION1;
                        } else if (playerIsNear(position2Autumn, 1)) {
                                autumnPhase = AUTUMNWAITPOSITION3;
                        } else if (playerIsNear(position3Autumn, 1)) {
                                autumnPhase = AUTUMNGOPOSITION4;
                        } else if (playerIsNear(position4Autumn, 1)) {
                                autumnPhase = AUTUMNWAITPOSITION5;
                        } else if (playerIsNear(position5Autumn, 1)) {
                                autumnPhase = AUTUMNWAITPOSITION6;
                        } else if (playerIsNear(position6Autumn, 1)) {
                                autumnPhase = AUTUMNWAITPOSITION7;
                        } else if (playerIsNear(position7Autumn, 1)) {
                                autumnPhase = AUTUMNWAITPOSITION8;
                        } else if (playerIsNear(position8Autumn, 1)) {
                                autumnPhase = AUTUMNWAITPOSITION9;
                        } else if (playerIsNear(position9Autumn, 1)) {
                                autumnPhase = AUTUMNWAITPOSITION10;
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
                                summerPhase = SUMMEROUTSIDEGATE;
                                errorCount = 0;
                                startRunTime = System.currentTimeMillis();
                                clearTrackingVars();
                                break;
                        }
                        errorCount++;
                        break;
                case SUMMEROUTSIDEGATE:
                        status = "Opening Gate";
                        if (playerIsAt(pastGateSummer)) {
                                summerPhase = SUMMERGOPOSITION1;
                                errorCount = 0;
                                break;
                        }
                        if (atTile(gatePositionSummer, 10, 0.5, 1, "")) {
                                wait(1000, 1500);
                                if (playerIsAt(pastGateSummer)) {
                                        summerPhase = SUMMERGOPOSITION1;
                                        errorCount = 0;
                                        break;
                                }
                        }
                        wait(500, 1000);
                        errorCount++;
                        break;
                case SUMMERGOPOSITION1:
                        status = "Moving To Position 1";
                        if (moveToScreenTile(position1Summer)) {
                                summerPhase = SUMMERWAITPOSITION2;
                                clearTrackingVars();
                                errorCount = 0;
                                break;
                        }
                        errorCount++;
                        break;
                case SUMMERWAITPOSITION2:
                        status = "Moving To Position 2";
                        if (caught) {
                                summerPhase = 1;
                                break;
                        }
                        if (npc1 == null) {
                                npc1 = getNearestNPCByID(elemental1Summer);
                                wait(100);
                                if (npc1 == null) {
                                        break;
                                }
                        }
                        setMouse(position2Summer);
                        if ((getDirection(npc1) == 1024
                                        && npc1.getLocation().getY() <= 5485
                                        && npc1.getLocation().getY() >= 5483
					|| npc1.getLocation().getY() == 5483)
                                        && playerIsAt(position1Summer)) {
                                if (!moveToScreenTile(position2Summer)) {
                                        errorCount++;
                                        break;
                                }
                                summerPhase = SUMMERWAITPOSITION3;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        waitTimeOut++;
                        if (waitTimeOut > 10) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        break;
                case SUMMERWAITPOSITION3:
                        status = "Moving To Position 3";
                        if (caught) {
                                summerPhase = 1;
                                break;
                        }
                        if (npc1 == null) {
                                npc1 = getNearestNPCByID(elemental1Summer);
                                wait(100);
                                if (npc1 == null) {
                                        break;
                                }
                        }
                        setMouse(position3Summer);
                        if ((getDirection(npc1) == 0
                                        && npc1.getLocation().getY() <= 5488
                                        && npc1.getLocation().getY() >= 5486
					|| npc1.getLocation().getY() == 5488)
                                        && playerIsAt(position2Summer)) {
                                if (!moveToScreenTile(position3Summer)) {
                                        errorCount++;
                                        break;
                                }
                                summerPhase = SUMMERWAITPOSITION4;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        waitTimeOut++;
                        if (waitTimeOut > 10) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        break;
                case SUMMERWAITPOSITION4:
                        status = "Moving To Position 4";
                        if (caught) {
                                summerPhase = 1;
                                break;
                        }
                        if (npc1 == null) {
                                npc1 = getNearestNPCByID(elemental2Summer);
                                wait(100);
                                if (npc1 == null) {
                                        break;
                                }
                        }
                        setMouse(position4Summer);
                        if (getDirection(npc1) == 0
                                        && npc1.getLocation().getY() <= 5494
                                        && npc1.getLocation().getY() >= 5492
                                        && playerIsAt(position3Summer)) {
                                if (!moveToScreenTile(position4Summer)) {
                                        errorCount++;
                                        break;
                                }
                                summerPhase = SUMMERWAITPOSITION5;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        waitTimeOut++;
                        if (waitTimeOut > 10) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        break;
                case SUMMERWAITPOSITION5:
                        status = "Moving To Position 5";
                        if (caught) {
                                summerPhase = 1;
                                break;
                        }
                        if (npc1 == null) {
                                npc1 = getNearestNPCByID(elemental3Summer);
                                wait(100);
                                if (npc1 == null) {
                                        break;
                                }
                        }
                        setMouse(position5Summer);
                        if ((getDirection(npc1) == 1024
                                        && npc1.getLocation().getY() <= 5490)
					|| (getDirection(npc1) == 0
					&& npc1.getLocation().getY() == 5490)) {
                                if (!moveToScreenTile(position5Summer)) {
                                        errorCount++;
                                        break;
                                }
                                summerPhase = SUMMERGOPOSITION6;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        waitTimeOut++;
                        if (waitTimeOut > 10) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        break;
                case SUMMERGOPOSITION6:
                        status = "Moving To Position 6";
                        setMouse(position6Summer);
                        if (moveToScreenTile(position6Summer)) {
                                summerPhase = SUMMERWAITPOSITION7;
                                clearTrackingVars();
                                errorCount = 0;
                                break;
                        }
                        errorCount++;
                        break;
                case SUMMERWAITPOSITION7:
                        status = "Moving To Position 7";
                        if (caught) {
                                summerPhase = 1;
                                break;
                        }
                        if (npc1 == null) {
                                npc1 = getNearestNPCByID(elemental4Summer);
                                wait(100);
                                if (npc1 == null) {
                                        break;
                                }
                        }
                        setMouse(position7Summer);
                        if (!(npc1.getLocation().getY() == 5483 && getDirection(npc1) == 512)
                                        && npc1.getLocation().getX() != 2915
                                        && playerIsAt(position6Summer)) {
                                if (!moveToScreenTile(position7Summer)) {
                                        errorCount++;
                                        break;
                                }
                                summerPhase = SUMMERWAITPOSITION8;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        waitTimeOut++;
                        if (waitTimeOut > 10) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        break;
                case SUMMERWAITPOSITION8:
                        status = "Moving To Position 8";
                        if (caught) {
                                summerPhase = 1;
                                break;
                        }
                        if (npc1 == null) {
                                npc1 = getNearestNPCByID(elemental4Summer);
                                wait(100);
                                if (npc1 == null) {
                                        break;
                                }
                        }
                        setMouse(position8Summer);
                        if ((npc1.getLocation().getX() >= 2917 || npc1.getLocation().getX() <= 2913)
                                        && playerIsAt(position7Summer)) {
                                if (!moveToScreenTile(position8Summer)) {
                                        errorCount++;
                                        break;
                                }
                                summerPhase = SUMMERWAITPOSITION9;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        waitTimeOut++;
                        if (waitTimeOut > 10) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        break;
                case SUMMERWAITPOSITION9:
                        status = "Moving To Position 9";
                        if (caught) {
                                summerPhase = 1;
                                break;
                        }
                        if (npc1 == null) {
                                npc1 = getNearestNPCByID(elemental5Summer);
                                wait(100);
                                if (npc1 == null) {
                                        break;
                                }
                        }
                        setMouse(position9Summer);
                        if ((getDirection(npc1) == 1024 || getDirection(npc1) == 1536)
                                        && playerIsAt(position8Summer)) {
                                if (!moveToScreenTile(position9Summer)) {
                                        errorCount++;
                                        break;
                                }
                                if (getherbs==1) {
					summerPhase = SUMMERPICKHERB;	
				} else {
					summerPhase = SUMMERWAITPOSITION10;
				}
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        waitTimeOut++;
                        if (waitTimeOut > 10) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        break;
                case SUMMERWAITPOSITION10:
                        status = "Moving To Position 10";
                        if (caught) {
                                summerPhase = 1;
                                break;
                        }
                        if (npc1 == null) {
                                npc1 = getNearestNPCByID(elemental5Summer);
                                wait(100);
                                if (npc1 == null) {
                                        break;
                                }
                        }
                        setMouse(position10Summer);
                        if (getDirection(npc1) == 0
                                        && npc1.getLocation().getY() == 5488
                                        && playerIsAt(position9Summer)) {
                                if (!moveToScreenTile(position10Summer)) {
                                        errorCount++;
                                        break;
                                }
                                summerPhase = SUMMERGOPOSITION11;
                                clearTrackingVars();
                                errorCount = 0;
                        }
                        waitTimeOut++;
                        if (waitTimeOut > 10) {
                                errorCount++;
                                waitTimeOut = 0;
                        }
                        break;
                case SUMMERGOPOSITION11:
                        status = "Moving To Tree";
                        if (caught) {
                                summerPhase = 1;
                                break;
                        }
                        if (!moveToTile(inFrontOfTreeSummer.getRandomTile())) {
                                errorCount++;
                                break;
                        }
                        summerPhase = SUMMERPICKFRUIT;
                        clearTrackingVars();
                        errorCount = 0;
                        break;
                case SUMMERPICKFRUIT:
                        status = "Picking Fruit";
                        if (caught) {
                                summerPhase = 1;
                                break;
                        }
                        if (startPosition.contains(player.getLocation())) {
                                errorCount++;
                                summerPhase = SUMMERATSTART;
                                break;
                        }
                        if (!moveToScreenTile(fruitTreeSummer[random(1, 200) % 2])) {
                                errorCount++;
                                break;
                        }
                        wait(2000, 2500);
                        break;
		case SUMMERPICKHERB:
                        status = "Picking Herb";
                        if (caught) {
                                summerPhase = 1;
                                break;
                        }
                        if (startPosition.contains(player.getLocation())) {
                                errorCount++;
                                summerPhase = SUMMERATSTART;
                                break;
                        }
                        if (!moveToScreenTile(herbSummer)) {
                                errorCount++;
                                break;
                        }
                        wait(2000, 2500);
                        break;			
                default:
                        if (startPosition.contains(player.getLocation())) {
                                summerPhase = SUMMERATSTART;
                        } else if (outSideGateSummer.contains(player.getLocation())) {
                                summerPhase = SUMMEROUTSIDEGATE;
                        } else if (playerIsNear(pastGateSummer, 1)) {
                                summerPhase = SUMMERGOPOSITION1;
                        } else if (playerIsNear(position1Summer, 1)) {
                                summerPhase = SUMMERWAITPOSITION2;
                        } else if (playerIsNear(position2Summer, 1)) {
                                summerPhase = SUMMERWAITPOSITION3;
                        } else if (playerIsNear(position3Summer, 1)) {
                                summerPhase = SUMMERWAITPOSITION4;
                        } else if (playerIsNear(position4Summer, 1)) {
                                summerPhase = SUMMERWAITPOSITION5;
                        } else if (playerIsNear(position5Summer, 1)) {
                                summerPhase = SUMMERGOPOSITION6;
                        } else if (playerIsNear(position6Summer, 1)) {
                                summerPhase = SUMMERWAITPOSITION7;
                        } else if (playerIsNear(position7Summer, 1)) {
                                summerPhase = SUMMERWAITPOSITION8;
                        } else if (playerIsNear(position8Summer, 1)) {
                                summerPhase = SUMMERWAITPOSITION9;
                        } else if (playerIsNear(position9Summer, 1)) {
                                summerPhase = SUMMERWAITPOSITION10;
                        } else if (playerIsNear(position10Summer, 2)) {
                                summerPhase = SUMMERGOPOSITION11;
                        } else if (inFrontOfTreeSummer.contains(player.getLocation())) {
                                summerPhase = SUMMERPICKFRUIT;
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
                        log("Summer Garden Run Failed at Phase: " + autumnPhase + ", "
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
                        if (startRunTime != 0) {
                                completeRuns++;
                                final long runTime = System.currentTimeMillis() - startRunTime;
                                averageRunTime = ((completeRuns - 1) * averageRunTime + runTime)
                                                / completeRuns;
                                summerPhase = 0;
                        }
                } else if (mess.startsWith("You've been spotted")) {
                        caught = true;
                        summerPhase = 0;
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
}