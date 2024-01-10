import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
 
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
 
import org.rsbot.bot.Bot;
import org.rsbot.bot.input.Mouse;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.script.Calculations;
import org.rsbot.script.Constants;
import org.rsbot.script.GEItemInfo;
import org.rsbot.script.Methods;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSInterface;
import org.rsbot.script.wrappers.RSItemTile;
import org.rsbot.script.wrappers.RSNPC;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSPlayer;
import org.rsbot.script.wrappers.RSTile;
import org.rsbot.util.GlobalConfiguration;
 
@ScriptManifest(authors = { "Kildoem, Anarki" }, category = "Money-Making", name = "Epic Improved Kildoe's Swamp Toader", version = 2.1, description =
    "<html><head>" +
    "</head><body>" +
    "<center><strong><h2>Epic Improved Kildoe's Swamp Toad Picker v2.1 - Modded By Anarki</h2></strong></center>" +
    "<center>Start in the Grand Tree Bank. This script will not start properly anywhere else.</center>" +
    "</body></html>")
    //*******************************************************//
    // Please remember to give me credit if you use any
    // part of this script.
    //
    // Thanks to Garrett for helping me out on MSN and
    // being an epic script writer.
    //*******************************************************//
public class EpicKildoesSwampToads extends Script implements PaintListener{
    //*******************************************************//
    // OTHER VARIABLES
    //*******************************************************//
    private final double version = 2.1;
    private long scriptStartTime;
    private int stuck = 0;
    private int failCount = 0;
    private int runEnergy = random(35, 70);
    private int toadsGained;
    private int toadPrice;
    protected int getMouseSpeed() {
    return random(7,8); } 
    //*******************************************************//
    // TILES
    //*******************************************************//
    private final int swampMinX = 2407;
    private final int swampMaxX = 2430;
    private final int swampMinY = 3506;
    private final int swampMaxY = 3519;
    private final int treeMinX = 2462;
    private final int treeMaxX = 2469;
    private final int treeMinY = 3492;
    private final int treeMaxY = 3499;
    private final RSTile ladder = new RSTile (2466,3495);
    private final RSTile bankTile = new RSTile (2450,3481);
    private final RSTile doorTile = new RSTile (2465,3492);
    //*******************************************************//
    // PATHS
    //*******************************************************//
    private final RSTile[] bankToSwamp1 = { new RSTile (2450,3482), new RSTile (2457 + random(-1, 1),3493), new RSTile (2465,3495) }; // This is the path for walking from the bank to the ladder
    private final RSTile[] bankToSwamp2 = { new RSTile (2465 + random(-1, 1),3491), new RSTile (2455,3501), new RSTile (2451,3505), new RSTile (2445,3510), new RSTile (2432,3511), new RSTile (2431,3510), new RSTile (2420,3508) };// This is the path that walks from the tree to the swamp
    //*******************************************************//
    // OBJECTS
    //*******************************************************//
    private final int doorID = 1967;
    private final int[] toadID = { 2150 };
    private final int[] toadlegID = { 2152 };
    //*******************************************************//
    // ENUM
    //*******************************************************//
    private final int WALKTOSWAMP1 = 1;
    private final int OPENDOOR1 = 2;
    private final int WALKTOSWAMP2 = 3;
    private final int PICKUPTOADS = 4;
    private final int WALKTOBANK1 = 5;
    private final int OPENDOOR2 = 6;
    private final int WALKTOBANK2 = 7;
    private final int OPENBANK = 8;
    private final int BANK = 9;
    private int ACTION = WALKTOSWAMP1;
 
    //*******************************************************//
    // ON START
    //*******************************************************//
    @SuppressWarnings("deprecation")
    public boolean onStart(Map<String, String> args) {
        scriptStartTime = System.currentTimeMillis();
        Bot.getEventManager().addListener(PaintListener.class, this);
        final GEItemInfo stringGE = grandExchange.loadItemInfo(toadlegID[0]);
        toadPrice = stringGE.getMaxPrice();
        log.info("Each Swamp Toad will be valued at the current GE market price of " + toadPrice + " coins.");
        log.info("Setting Camera Rotation and Altitude");
        setCameraRotation(230);
        setCameraAltitude(true);
        log.info("Script Started Successfully");
        return true;
    }
    //*******************************************************//
    // ON FINISH
    //*******************************************************//
    public void onFinish(){
        Bot.getEventManager().removeListener(PaintListener.class, this);
        return;
    }
    //*******************************************************//
    // MAIN LOOP
    //*******************************************************//
    public int loop() {
        //***********************//
        // Walk Out Of Bank
        //***********************//
        try {
        if (ACTION == WALKTOSWAMP1) {
            if (getPlane() == 1) {
                if (!tileOnScreen(ladder)) {
                    if (needToWalk()) {
                        walkPathMM(bankToSwamp1);
                        startRunning(runEnergy);
                        log.info("Walking to ladder");
                        return random(100,200);
                    }
                }
                else {
                    atTile(ladder,"Climb-down");
                    log.info("Clicking ladder");
                    return random(800,1200);
                }
            }
            if (getPlane() == 0) {
                if (inTree()) {
                    ACTION = OPENDOOR1;
                    return random (50,150);
                }
            }
            if (getPlane() == 2) {
                unstuck();
                return random (900,1200);
            }
        }
        //***********************//
        // Leave The Tree
        //***********************//
        if (ACTION == OPENDOOR1) {
            if (!inTree()) { ACTION = WALKTOSWAMP2; return random (300,500); }
            RSObject door = getNearestObjectByID(doorID);
            if (door == null) { return random (300,500); }
            if (!tileOnScreen(doorTile)) {  return random (300,600); }
            if (getMyPlayer().isMoving()) { return random (300,600); }
            if (!atObject(door, "Open")) {  return random (300,600); }
        }
        //***********************//
        // Walk To The Swamp
        //***********************//
        if (ACTION == WALKTOSWAMP2) {
            if (!inSwamp()) {
                if (!inTree()) {
                    if (needToWalk()) {
                        walkPathMM(bankToSwamp2);
                        startRunning(runEnergy);
                        return random(100,150);
                    }
                }
                else { ACTION = OPENDOOR1; return random(100,150); }
            }
            else {
                ACTION = PICKUPTOADS;
                log.info("Picking up toads");
                return random(400,700);
            }
        }
        //***********************//
        // Pick Up Toads
        //***********************//
        if (ACTION == PICKUPTOADS) {
            if (isInventoryFull()) { toadsGained = toadsGained + getInventoryCount(toadID); ACTION = WALKTOBANK1; return random(150,250); }
            else {
                startRunning(runEnergy);
                setCameraRotation(230);
                RSItemTile swampToadTile = getNearestGroundItemByID(toadID);
                if (swampToadTile != null) {
                    if (tileOnScreen(swampToadTile)) {
                        if (getMyPlayer().isMoving() == false) {
                            atTile(swampToadTile, "toad");
                            return random(300,600);
                        }
                    }
                    else {
                        if (getMyPlayer().isMoving() == false) {
                            walkTileMM(swampToadTile);
                            return random(400,600);
                        }
                    }
                }
            }
        }
        //***********************//
        // Walk To Tree
        //***********************//
        if (ACTION == WALKTOBANK1) {
            RSObject door = getNearestObjectByID(doorID);
            if (door != null) {
                if (distanceTo(door) <= 7) {
                    ACTION = OPENDOOR2;
                    log.info("Changed action to OPENDOOR2");
                    return random(250,350);
                }
            }
            if (needToWalk()) {
                walkPathMM(reversePath(bankToSwamp2));
                startRunning(runEnergy);
                return random(100,150);
            }
        }
        //***********************//
        // Enter The Tree
        //***********************//
        if (ACTION == OPENDOOR2) {
            if (inTree()) { ACTION = WALKTOBANK2; return random (300,500); }
            RSObject door = getNearestObjectByID(doorID);
            if (door == null) { return random (300,500); }
            if (!tileOnScreen(doorTile)) {  return random (300,600); }
            if (getMyPlayer().isMoving()) { return random (300,600); }
            if (!atObject(door, "Open")) {  return random (300,600); }
        }
        //***********************//
        // Walk Back To The Bank
        //***********************//
        if (ACTION == WALKTOBANK2) {
            if (getPlane() == 0) {
                if (inTree()){
                    if (!getMyPlayer().isMoving()) {
                        atTile(ladder,"Climb-up");
                        log.info("Clicking ladder");
                        return random(800,1200);
                    }
                }
                else {
                    ACTION = OPENDOOR2;
                }
            }
            if (getPlane() == 1) {
                if (distanceTo(bankTile) < 3) {
                    ACTION = OPENBANK;
                }
                else {
                    if (needToWalk()) {
                        walkPathMM(reversePath(bankToSwamp1));
                        log.info("Walking to bank");
                        return random(100,200);
                    }
                }
            }
            if (getPlane() == 2) {
                unstuck();
                return random(900,1200);
            }
        }
        //***********************//
        // Open The Bank
        //***********************//
        if (ACTION == OPENBANK) {
            if (!bank.isOpen()) {
                atTile(bankTile,"Use-quickly");
                return random(800,1200);
            }
            else { ACTION = BANK; return random(150,250); }
        }
        //***********************//
        // Deposit Toads
        //***********************//
        if (ACTION == BANK) {
            if (bank.isOpen()) {
                if (isInventoryFull()){
                    bank.depositAll();
                    return random(150,250);
                }
                else {
                    ACTION = WALKTOSWAMP1;
                }
            }
            else {
                if (isInventoryFull()) {
                    ACTION = OPENBANK;
                    return random(150,250);
                }
                else ACTION = WALKTOSWAMP1;
            }
        }
        } catch( Exception e ) { }
        return random(50,150);
    }
    //*******************************************************//
    // OTHER METHODS
    //*******************************************************//
    public boolean needToWalk() {
        if ((getMyPlayer().isMoving() == false) ||
           (distanceTo(getDestination()) <= random(3,6))) return true;
        return false;
    }
    public boolean inTree() {
        RSTile playerLoc = getMyPlayer().getLocation();
        if ((playerLoc.getX() >= treeMinX) &&
            (playerLoc.getX() <= treeMaxX) &&
            (playerLoc.getY() >= treeMinY) &&
            (playerLoc.getY() <= treeMaxY)) return true;
        else return false;
 
    }
    public boolean inSwamp() {
        RSTile playerLoc = getMyPlayer().getLocation();
        if ((playerLoc.getX() >= swampMinX) &&
            (playerLoc.getX() <= swampMaxX) &&
            (playerLoc.getY() >= swampMinY) &&
            (playerLoc.getY() <= swampMaxY)) return true;
        else return false;
    }
    private void startRunning(final int energy) { // Method by Garrett - Used with permission. Thanks Garrett
        if (getEnergy() >= energy && !isRunning()) {
            runEnergy = random(35, 70);
            setRun(true);
            wait(random(500, 750));
        }
    }
    private void unstuck() {
        if (ladder != null) {
            if (distanceTo(ladder) <= 7) {
                atTile(ladder, "Climb-down");
                log.info("Climbing down ladder");
                failCount++;
                return;
            }
            else {
                walkTileMM(ladder);
                failCount++;
                return;
            }
        }
        return;
    }

    private String getStatus() {
        if (ACTION == WALKTOSWAMP1) return "Leaving the bank...";
        if (ACTION == OPENDOOR1) return "Leaving the tree...";
        if (ACTION == WALKTOSWAMP2) return "Walking to the swamp...";
        if (ACTION == PICKUPTOADS) return "Picking up toads...";
        if (ACTION == WALKTOBANK1) return "Walking to the tree...";
        if (ACTION == OPENDOOR2) return "Entering the tree...";
        if (ACTION == WALKTOBANK2) return "Walking to the bank...";
        if (ACTION == OPENBANK) return "Opening the bank...";
        if (ACTION == BANK) return "Banking...";
        return "None";
    }
    public void onRepaint(Graphics g) {
               ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                       ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
                       ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY); 
        long runTime = 0;
        long seconds = 0;
        long minutes = 0;
        long hours = 0;
        int toadsPerHour = 0;
        int moneyGained = 0;
        int moneyPerHour = 0;
        runTime = System.currentTimeMillis() - scriptStartTime;
        seconds = runTime / 1000;
        if ( seconds >= 60 ) {
            minutes = seconds / 60;
            seconds -= (minutes * 60);
        }
        if ( minutes >= 60 ) {
            hours = minutes / 60;
            minutes -= (hours * 60);
        }
        if ((runTime / 1000) > 0) {
            toadsPerHour = (int) ((3600000.0 / (double) runTime) * toadsGained);
        }
        moneyGained = toadsGained * toadPrice;
        moneyPerHour = toadsPerHour * toadPrice;
        if(getCurrentTab() == TAB_INVENTORY) {
            g.setColor(new Color(0, 0, 0, 175));
            g.fillRoundRect(555, 210, 175, 250, 10, 10);
            g.setColor(Color.WHITE);
 
            int[] coords = new int[] {225, 240, 255, 270, 285, 300, 315, 330, 345, 360, 375, 390, 405, 420, 435, 450};
            g.setFont(new Font("Calibri", Font.BOLD, 14));
            g.drawString("Epic Swamp Toad Picker", 561, coords[0]);
            g.setFont(new Font("Calibri", Font.PLAIN, 12));
            g.drawString("Version : " + version, 561, coords[2]);
            g.drawString("Run Time : " + hours + ":" + minutes + ":" + seconds, 561, coords[3]);
            g.drawString("Toads Picked : " + toadsGained, 561, coords[5]);
            g.drawString("Money Earned : " + moneyGained, 561, coords[6]);
            g.drawString("Toads/Hour : " + toadsPerHour, 561, coords[8]);
            g.drawString("Money/Hour : " + moneyPerHour, 561, coords[9]);
            g.drawString("Status : " + getStatus(), 561, coords[11]);
            g.drawString("Times Stuck : " + stuck, 561, coords[13]);
        }
    }
}