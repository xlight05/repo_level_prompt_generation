import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.imageio.ImageIO;

import org.rsbot.bot.Bot;
import org.rsbot.bot.input.Mouse;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSItemTile;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSTile;

@ScriptManifest(authors = { "Stinky123" }, category = "Woodcutting", name = "StinkyWillows", version = 1.0, description = "<html>"
        + "<body style='font-family: Calibri; color: white; padding: 0px; text-align: left; background-color: red;'>"
        + "<font size=\"5\">StinkyWillows v1.0</font>"
        + "<br></br>"
        + "<p>This script chops willow trees around the Crafting Guild, Draynor Village, Lumbridge or Port Sarim then it will banks or drop the logs If in PowerChop Mode..</p>"
        + "<br></br>"
        + "<b>What location would you like to chop at?</b>"
        + "<br></br>"
        + "<select name='selection1'>"
        + "<option>Crafting Guild"
        + "<option>Draynor Village"
        + "<option>Lumbridge"
        + "<option>Port Sarim"
        + "<br></br>"
        + "<b>Use power chop mode?</b>"
        + "<br></br>"
        + "<select name='selection2'>"
        + "<option>No"
        + "<option>Yes"
        + "</html>")
public class StinkyWillows extends Script implements PaintListener,
        ServerMessageListener {

    StinkyWillowsAntiBan antiban;
    Thread t;

    BufferedImage normal = null;
    BufferedImage clicked = null;

    public RSTile bankTile;
    public RSTile treeTile;
    public RSTile treeTileNW;
    public RSTile treeTileSE;

    public int[] hatchetID = new int[] { 1349, 1351, 1353, 1355, 1357, 1359,
            1361, 6739, 13470 };
    public int[] willowTreeID = new int[] { 1308, 5551, 5552, 5553, 8481, 8482,
            8483, 8484, 8485, 8486, 8487, 8488 };
    public int[] nestID = new int[] { 5070, 5071, 5072, 5073, 5074, 5075, 5076,
            7413, 11966 };

    public int logsChopped = 0;
    public int nestsTaken = 0;
    public int startXP = 0;
    public int gainedXP = 0;

    public float secondXP = 0;
    public float minuteXP = 0;
    public float hourXP = 0;

    public boolean powerChop;

    public long startTime = System.currentTimeMillis();

    @Override
    protected int getMouseSpeed() {
        return random(3, 5);
    }

    public boolean onStart(final Map<String, String> args) {
        final String Selection1 = args.get("selection1");
        final String Selection2 = args.get("selection2");

        if (Selection1.equals("Crafting Guild")) {
            bankTile = new RSTile(3012, 3356);
            treeTile = new RSTile(2918, 3297);
            treeTileNW = new RSTile(2913, 3301);
            treeTileSE = new RSTile(2922, 3294);
        } else if (Selection1.equals("Draynor Village")) {
            bankTile = new RSTile(3093, 3243);
            treeTile = new RSTile(3086, 3234);
            treeTileNW = new RSTile(3082, 3239);
            treeTileSE = new RSTile(3090, 3226);
        } else if (Selection1.equals("Lumbridge")) {
            bankTile = new RSTile(3093, 3243);
            treeTile = new RSTile(3161, 3267);
            treeTileNW = new RSTile(3161, 3272);
            treeTileSE = new RSTile(3167, 3264);
        } else {
            bankTile = new RSTile(3093, 3243);
            treeTile = new RSTile(3060, 3253);
            treeTileNW = new RSTile(3056, 3256);
            treeTileSE = new RSTile(3064, 3251);
        }

        try {
            final URL cursorURL = new URL("http://i46.tinypic.com/2u4n4nq.jpg");
            final URL cursor80URL = new URL("http://i45.tinypic.com/rkwuh1.jpg");
            normal = ImageIO.read(cursorURL);
            clicked = ImageIO.read(cursor80URL);
        } catch (MalformedURLException e) {
            log("Unable to buffer cursor.");
        } catch (IOException e) {
            log("Unable to open cursor image.");
        }
        
        powerChop = Selection2.equals("Yes");

        antiban = new StinkyWillowsAntiBan();
        t = new Thread(antiban);

        startTime = System.currentTimeMillis();
        return true;
    }

    public boolean nearBank() {
        return distanceTo(bankTile) <= 5 || tileOnScreen(bankTile);
    }

    public boolean nearTree() {
        return isInArea(treeTileNW, treeTileSE);
    }

    public boolean isInArea(final RSTile NW, final RSTile SE) {
        final int playerX = getMyPlayer().getLocation().getX();
        final int playerY = getMyPlayer().getLocation().getY();
        return playerX >= NW.getX() && playerX <= SE.getX()
                && playerY >= SE.getY() && playerY <= NW.getY();
    }

    public boolean chopTree(final int[] treeID) {
        if (getMyPlayer().getAnimation() == -1) {
            final RSObject Tree = getNearestObjectByID(treeID);
            if (Tree != null) {
                if (distanceTo(Tree) <= 5) {
                    atMultiTiledObject(Tree, "Chop");
                    wait(random(800, 1400));
                } else {
                    walkTo(Tree.getLocation());
                    wait(random(500, 1000));
                }
            }
        }
        return true;
    }

    public boolean atMultiTiledObject(final RSObject obj, final String action) {
        RSTile map[][] = new RSTile[5][5];
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                RSObject fObj = getObjectAt(obj.getLocation().getX() - 2 + x,
                        obj.getLocation().getY() - 2 + y);
                if (fObj != null && fObj.getID() == obj.getID()) {
                    map[x][y] = fObj.getLocation();
                }
            }
        }
        RSTile tTile = null;
        int tx = 0;
        int ty = 0;
        for (int x = 0; x <= 3; x++) {
            for (int y = 0; y <= 3; y++) {
                if (map[x][y] != null && map[x + 1][y] != null
                        && map[x][y + 1] != null && map[x + 1][y + 1] != null) {
                    if (map[x][y].equals(obj.getLocation())
                            || map[x + 1][y].equals(obj.getLocation())
                            || map[x][y + 1].equals(obj.getLocation())
                            || map[x + 1][y + 1].equals(obj.getLocation())) {
                        tTile = map[x][y];
                        tx = x;
                        ty = y;
                        break;
                    }
                }
            }
        }

        if (tTile == null) {
            return false;
        }
        int xSum = 0;
        int ySum = 0;

        for (int x = 0; x <= 1; x++) {
            for (int y = 0; y <= 1; y++) {
                xSum += (int) map[tx + x][ty + y].getScreenLocation().getX();
                ySum += (int) map[tx + x][ty + y].getScreenLocation().getY();
            }
        }
        moveMouse(xSum / 4 + random(-2, 2), ySum / 4 + random(-2, 2));
        return atMenu(action);
    }

    public void onFinish() {
        antiban.stopThread = true;
    }

    public void manageBank() {
        if (!bank.isOpen()) {
            bank.open();
            wait(random(500, 1000));
        } else {
            if (getInventoryCount() > 0) {
                bank.depositAllExcept(hatchetID);
                wait(random(500, 1000));
            }
            if (random(0, 1) == 1) {
                bank.close();
                wait(random(500, 1000));
            }
        }
    }

    public void takeNest() {
        final RSItemTile nest = getGroundItemByID(nestID);
        if (atTile(nest, "Take")) {
            nestsTaken++;
            wait(random(500, 750));
        }
    }

    public int loop() {
        if (!t.isAlive()) {
            t.start();
        }
        if (!isRunning() && getEnergy() > random(20, 30)) {
            setRun(true);
        }
        if (Bot.getClient().getCameraPitch() < 2750) {
            setCameraAltitude(true);
        }
        if (isInventoryFull()) {
            if (powerChop) {
                if (getInventoryCount() > 0) {
                    dropAllExcept(hatchetID);
                    wait(random(500, 1000));
                }
            } else {
                if (nearBank()) {
                    if (!getMyPlayer().isMoving()) {
                        manageBank();
                    }
                } else {
                    walkTile(bankTile);
                }
            }
        } else {
            if (nearTree()) {
                chopTree(willowTreeID);
            } else {
                walkTile(treeTile);
            }
            if (getGroundItemByID(nestID) != null) {
                takeNest();
            }
        }
        return random(50, 150);
    }

    public int walkTile(final RSTile areaTile) {
        final RSTile[] areaPath = (generateFixedPath(areaTile));
        if (areaPath != null
                && (!getMyPlayer().isMoving() || distanceTo(getDestination()) < 5)) {
            walkPathMM(randomizePath(areaPath, 2, 2), 15);
            return random(200, 500);
        }
        return 0;
    }

    private double getVersion() {
        return 1.2;
    }

    private String getName() {
        return "StinkyWillows";
    }

    private final RenderingHints rh = new RenderingHints(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    public void onRepaint(final Graphics g) {
        if (!isLoggedIn()) {
            return;
        }
        long millis = System.currentTimeMillis() - startTime;
        long hours = millis / (1000 * 60 * 60);
        millis -= hours * (1000 * 60 * 60);
        long minutes = millis / (1000 * 60);
        millis -= minutes * (1000 * 60);
        long seconds = millis / 1000;

        if (startXP <= 0) {
            startXP = skills.getCurrentSkillExp(STAT_WOODCUTTING);
        }
        gainedXP = (skills.getCurrentSkillExp(STAT_WOODCUTTING) - startXP);

        if ((minutes > 0 || hours > 0 || seconds > 0) && gainedXP > 0) {
            secondXP = (float) gainedXP
                    / (float) (seconds + minutes * 60 + hours * 60 * 60);
        }
        minuteXP = secondXP * 60;
        hourXP = minuteXP * 60;

        final StringBuilder botTime = new StringBuilder();
        if (hours < 10) {
            botTime.append('0');
        }
        botTime.append(hours);
        botTime.append(':');
        if (minutes < 10) {
            botTime.append('0');
        }
        botTime.append(minutes);
        botTime.append(':');
        if (seconds < 10) {
            botTime.append('0');
        }
        botTime.append(seconds);

        ((Graphics2D) g).setRenderingHints(rh);

         g.setColor(new Color(0, 0, 0, 143));
         g.fillRoundRect(295, 10, 215, 85, 2, 2);
         g.setColor(Color.red.brighter());
         g.setFont(new Font("Arial", Font.BOLD, 12));
         g.drawString("StinkyWillows Version 1.0", 305, 30); // 247
         g.setColor(Color.black);
         g.drawRoundRect(295, 10, 215, 85, 2, 2);
         g.setFont(new Font("Arial", Font.BOLD, 13));
         g.setColor(Color.gray.brighter());

        g.drawString("Timer: " + botTime, 305, 42);

        g.drawString("XP Gained: " + gainedXP + " (" + (int) hourXP + "/Hr)",
                305, 57);

        g.drawString(
                "Logs Chopped: "
                        + logsChopped
                        + " ("
                        + (int) (logsChopped * 3600000D / (System
                                .currentTimeMillis() - startTime)) + "/Hr)",
                305, 72);

        g.drawString(
                "Nests Taken: "
                        + nestsTaken
                        + " ("
                        + (int) (nestsTaken * 3600000D / (System
                                .currentTimeMillis() - startTime)) + "/Hr)",
                305, 87);


        if (normal != null) {
            final Mouse mouse = Bot.getClient().getMouse();
            final int mouse_x = mouse.getMouseX();
            final int mouse_y = mouse.getMouseY();
            final int mouse_x2 = mouse.getMousePressX();
            final int mouse_y2 = mouse.getMousePressY();
            final long mpt = System.currentTimeMillis()
                    - mouse.getMousePressTime();
            if (mouse.getMousePressTime() == -1 || mpt >= 1000) {
                g.drawImage(normal, mouse_x - 8, mouse_y - 8, null);
            }
            if (mpt < 1000) {
                g.drawImage(clicked, mouse_x2 - 8, mouse_y2 - 8, null);
                g.drawImage(normal, mouse_x - 8, mouse_y - 8, null);
            }
        }
    }

    public void serverMessageRecieved(final ServerMessageEvent arg0) {
        final String message = arg0.getMessage();

        if (message.contains("You get some")) {
            logsChopped++;
        }
    }

    private class StinkyWillowsAntiBan implements Runnable {
        public boolean stopThread;

        public void run() {
            while (!stopThread) {
                try {
                    if (random(0, 15) == 0) {
                        final char[] LR = new char[] { KeyEvent.VK_LEFT,
                                KeyEvent.VK_RIGHT };
                        final char[] UD = new char[] { KeyEvent.VK_DOWN,
                                KeyEvent.VK_UP };
                        final char[] LRUD = new char[] { KeyEvent.VK_LEFT,
                                KeyEvent.VK_RIGHT, KeyEvent.VK_UP,
                                KeyEvent.VK_UP };
                        final int random2 = random(0, 2);
                        final int random1 = random(0, 2);
                        final int random4 = random(0, 4);

                        if (random(0, 3) == 0) {
                            Bot.getInputManager().pressKey(LR[random1]);
                            Thread.sleep(random(100, 400));
                            Bot.getInputManager().pressKey(UD[random2]);
                            Thread.sleep(random(300, 600));
                            Bot.getInputManager().releaseKey(UD[random2]);
                            Thread.sleep(random(100, 400));
                            Bot.getInputManager().releaseKey(LR[random1]);
                        } else {
                            Bot.getInputManager().pressKey(LRUD[random4]);
                            if (random4 > 1) {
                                Thread.sleep(random(300, 600));
                            } else {
                                Thread.sleep(random(500, 900));
                            }
                            Bot.getInputManager().releaseKey(LRUD[random4]);
                        }
                    } else {
                        Thread.sleep(random(200, 2000));
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}