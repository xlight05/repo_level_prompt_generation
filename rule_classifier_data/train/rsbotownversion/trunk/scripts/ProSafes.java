import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Map;
import java.util.ArrayList;
import java.text.*;
import org.rsbot.bot.Bot;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSPlayer;
import org.rsbot.script.wrappers.RSTile;
import org.rsbot.script.wrappers.RSNPC;
import org.rsbot.bot.input.Mouse;
import org.rsbot.script.wrappers.RSInterface;
import org.rsbot.script.wrappers.RSInterfaceChild;
import org.rsbot.script.wrappers.RSInterfaceComponent;
import org.rsbot.util.ScreenshotUtil;
 
@ScriptManifest(authors = { "Proresearch" }, category = "Pro's Scripts", name = "Proresearch's Safe Cracker 1.8", description = "Start with a stethoscope near the bank and withdraw 10 "
        + "<select name='foodtype'><option>Monkfish</option><option>Tuna</option><option>Lobster</option><option>Shark</option><option>Swordfish</option></select>"
        + "<html><body><h1>Proresearch's Safe Cracker<br><center><img src=http://img195.imageshack.us/img195/1926/smallnn.png></html>", version = 1.8)
public class ProSafes extends Script implements PaintListener,
        ServerMessageListener {
 
    int wallSafe = 7235;
    int stethoscope = 5560;
    int money = 995;
    int minHP = 25;
    int upTo = 63;
    int sapphire = 1623, emerald = 1621, ruby = 1619, diamond = 1617;
    int banker = 2271;
    int food;
    boolean usingStethoscope = true, cracked = false;
    int totalSapps, totalEms, totalRubs, totalDiamonds;
    int lastSapps, lastEms, lastRubs, lastDiamonds;
    int priceSapps, priceEms, priceRubs, priceDiamonds;
    // paint
    int crackFailed = 0, crackedCount = 0, startLevel;
    final int banking = 0, eating = 1, cracking = 2;
    long startMoney, startExp, startTime;
    Point stetP = new Point(0, 0);
    long expGained;
    int curHP;
    RSTile tile = new RSTile(3055, 4977);
    final RSTile tile1 = new RSTile(3055, 4977),
            tile2 = new RSTile(3057, 4977);
 
    // ///////////booleans//////////
 
    public boolean onStart(final Map<String, String> args) {
        // get prices
        if (!isLoggedIn()) {
            log("START LOGGED IN");
            return false;
        }
        if (args.get("foodtype").equals("Monkfish")) {
            food = 7946;
        }
        if (args.get("foodtype").equals("Tuna")) {
            food = 361;
        }
        if (args.get("foodtype").equals("Lobster")) {
            food = 379;
        }
        if (args.get("foodtype").equals("Shark")) {
            food = 385;
        }
        if (args.get("foodtype").equals("Swordfish")) {
            food = 373;
        }
        log("Getting prices.. wait 5-10 seconds (calculating price)");
        priceSapps = grandExchange.loadItemInfo(sapphire).getMaxPrice();
        priceEms = grandExchange.loadItemInfo(emerald).getMaxPrice();
        priceRubs = grandExchange.loadItemInfo(ruby).getMarketPrice();
        priceDiamonds = grandExchange.loadItemInfo(diamond).getMaxPrice();
        log("We've got the prices");
        usingStethoscope = args.get("nostet") != null;
        usingStethoscope = !usingStethoscope;
        log("Using stethoscope? " + usingStethoscope);
        lastSapps = getInventoryCount(sapphire);
        lastEms = getInventoryCount(emerald);
        lastRubs = getInventoryCount(ruby);
        lastDiamonds = getInventoryCount(diamond);
        minHP = (int) (random(22,
                skills.getRealSkillLevel(STAT_HITPOINTS) * 0.5));
        upTo = minHP
                + random(2,
                        (skills.getRealSkillLevel(STAT_HITPOINTS) - 20 - minHP));
        startMoney = getInventoryCount(money);
        startTime = System.currentTimeMillis();
        return true;
    }
 
    public boolean antiBan2() {
        int randomNumber = random(1, 11);
        if (randomNumber <= 11) {
            if (randomNumber == 1) {
                setCameraRotation(random(1, 360));
            }
            if (randomNumber == 2) {
                moveMouse(random(50, 700), random(50, 450), 2, 2);
                wait(random(200, 400));
                moveMouse(163, 111, 150, 150);
            }
            if (randomNumber == 3) {
                moveMouse(163, 111, 150, 150);
            }
            if (randomNumber == 4) {
                moveMouse(163, 111, 150, 150);
            }
            if (randomNumber == 5) {
                moveMouse(163, 111, 150, 150);
            }
        }
        return true;
    }
 
    public int loop() {
        setCameraRotation(180);
        setCameraAltitude(true);// ////////////////////////////////
        switch (getState()) {
        case banking:
            lastSapps = 0;
            lastEms = 0;
            lastRubs = 0;
            lastDiamonds = 0;
            if (!bank.isOpen()) {
                RSNPC bankerNPC = getNearestNPCByID(banker);
                if (bankerNPC == null)
                    return 1000;
                if (!tileOnScreen(bankerNPC.getLocation())) {
                    walkTileMM(getClosestTileOnMap(bankerNPC.getLocation()), 0,
                            0);
                    return 2000;
                }
                if (!tileOnScreen(bankerNPC.getLocation())) {
                    walkTileMM(bankerNPC.getLocation(), 0, 0);
                    antiBan2();
                    return 2000;
                }
                if (distanceTo(bankerNPC.getLocation()) > 7) {
                    walkTileOnScreen(bankerNPC.getLocation());
                    return 2000;
                }
                if (atNPC(bankerNPC, "Bank"))
                    return 1000;
            }
            if (!bank.isOpen())
                return 100;
            if (getInventoryCountExcept(food, money, stethoscope) > 0)
                if (bank.depositAllExcept(food, money, stethoscope))
                    return 1800;
            if (bank.getCount(food) < 10) {
                log("Almost out of food");
                stopScript();
            } else {
                if (getInventoryCount(food) < 10) {
                    bank.withdraw(food, 10);
                    return 800;
                }
            }
            if (getInventoryCount(food) >= 10)
                bank.close();
            return 100;
        case eating:
            if (bank.isOpen())
                return 100;
            if (getInventoryCount(food) == 0)
                return 100;
            if (curHP > (skills.getRealSkillLevel(STAT_HITPOINTS) * 0.75))
                return 100;
            if (curHP < minHP)
                while (curHP < upTo && getInventoryCount(food) > 0) {
                    atInventoryItem(food, "Eat");
                    wait(1400);
                    curHP = (int) (Integer.parseInt(RSInterface.getInterface(
                            748).getChild(8).getText()) / 10);
                }
            minHP = (int) (random(22,
                    skills.getRealSkillLevel(STAT_HITPOINTS) * 0.5));
            upTo = minHP
                    + random(
                            2,
                            (skills.getRealSkillLevel(STAT_HITPOINTS) - 20 - minHP));
            return 100;
        case cracking:
            if (getMyPlayer().isMoving())
                return 100;
            RSObject safe = getObjectAt(tile.getX(), tile.getY() - 1);
            if (safe == null)
                return 100;
            checkGems();
            if (!tileOnMap(safe.getLocation())) {
                walkTileMM(getClosestTileOnMap(safe.getLocation()), 0, 0);
                return 2000;
            }
            if (!tileOnScreen(safe.getLocation())) {
                if (getPlayerAtTile(tile))
                    tile = changeTile();
                walkTileMM(tile, 0, 0);
                return 2000;
            }
            cracked = false;
            if (usingStethoscope) {
                if (getMouseLocation().distance(stetP) > 10) {
                    wait(280);
                } else {
                    clickMouse(true);
                }
            }
            if (getMouseLocation().distance(
                    safe.getLocation().getScreenLocation()) > 10) {
                moveMouse(safe.getLocation().getScreenLocation(), 5, 5);
                wait(280);
            }
            clickMouse(true);
            moveMouse(random(220, 280), random(114, 186));
            wait(1000);
            antiBan2();
            int j = 0,
            animCounter = 0;
            while (j < 5 && !cracked && animCounter < 4) {
                wait(1000);
                j++;
                if (getMyPlayer().getAnimation() == -1)
                    animCounter++;
            }
            return 100;
        }
        return 1;
    }
 
    private RSTile changeTile() {
        log("Someone at the tile, time to move.");
        if (tile.equals(tile1))
            return tile2;
        if (tile.equals(tile2))
            return tile1;
        log("wat-> " + tile);
        return tile;
    }
 
    private void checkGems() {
        if (getInventoryCount(sapphire) > lastSapps) {
            lastSapps = getInventoryCount(sapphire);
            totalSapps++;
        }
        if (getInventoryCount(emerald) > lastEms) {
            lastEms = getInventoryCount(emerald);
            totalEms++;
        }
        if (getInventoryCount(ruby) > lastRubs) {
            lastRubs = getInventoryCount(ruby);
            totalRubs++;
        }
        if (getInventoryCount(diamond) > lastDiamonds) {
            lastDiamonds = getInventoryCount(diamond);
            totalDiamonds++;
        }
    }
 
    public void onFinish() {
        ScreenshotUtil.takeScreenshot(true);
        long millis = System.currentTimeMillis() - startTime;
        long hours = millis / (1000 * 60 * 60);
        millis -= hours * 1000 * 60 * 60;
        long minutes = millis / (1000 * 60);
        millis -= minutes * 1000 * 60;
        long seconds = millis / 1000;
        log("After " + hours + ":" + minutes + ":" + seconds);
        log("Exp gained: " + expGained);
        long p = (priceSapps * totalSapps) + (priceEms * totalEms)
                + (priceRubs * totalRubs) + (priceDiamonds * totalDiamonds);
        log("Total gems: "
                + (totalSapps + totalEms + totalRubs + totalDiamonds) + " ("
                + (int) (p / 1000) + "k made)");
        log("Gained " + (skills.getRealSkillLevel(STAT_THIEVING) - startLevel)
                + " levels");
    }
 
    // //////////////////////////////paint goes
    // here////////////////////////////////////
    public void onRepaint(Graphics g) {
        if (!isLoggedIn())
            return;
        long millis = System.currentTimeMillis() - startTime;
        if (startExp == 0)
            startExp = skills.getCurrentSkillExp(STAT_THIEVING);
        if (startLevel == 0)
            startLevel = skills.getRealSkillLevel(STAT_THIEVING);
        long totalseconds = millis / 1000;
        long hours = millis / (1000 * 60 * 60);
        millis -= hours * 1000 * 60 * 60;
        long minutes = millis / (1000 * 60);
        millis -= minutes * 1000 * 60;
        long seconds = millis / 1000;
        int x = 285, y = 235;
        if (totalseconds < 1)
            totalseconds = 1;
        expGained = skills.getCurrentSkillExp(STAT_THIEVING) - startExp;
        int expHour = (int) (expGained * 3.6 / totalseconds);
 
        g.setColor(new Color(0, 0, 255, 90));
        g.fillRect(360, 321, 75, 17);
        g.fillRect(330, 2, 180, 17);
        g.setColor(Color.white);
        g.drawString("Loot", 382, 333);
        g.drawString("Proresearch's Safe Cracker 1.8", 330, 15);
        if (expHour == 0)
            return;
        g.setColor(new Color(0, 0, 255, 90));
        g.fillRoundRect(280, 210, 235, 108, 10, 10);
        g.fillRoundRect(280, 150, 235, 200, 10, 10);
        g.setColor(Color.white);
        g.drawString("Time running: " + hours + ":" + minutes + ":" + seconds
                + " -  State: " + stateToString(getState()), x, y - 10);
        g.setColor(Color.white);
        g
                .drawString(
                        "Gold picked: "
                                + (getInventoryCount(money) - startMoney)
                                + " ("
                                + (int) ((getInventoryCount(money) - startMoney) * 3.6 / totalseconds)
                                + "k/hr)", x, y + 5);
        g.drawString("Sapphires picked: " + totalSapps + " (Worth "
                + (int) (priceSapps * totalSapps / 1000) + "k)", x, y + 20);
        g.drawString("Emeralds picked: " + totalEms + " (Worth "
                + (int) (priceEms * totalEms / 1000) + "k)", x, y + 35);
        g.drawString("Rubys picked: " + totalRubs + " (Worth "
                + (int) (priceRubs * totalRubs / 1000) + "k)", x, y + 50);
        g.drawString("Diamonds picked: " + totalDiamonds + " (Worth "
                + (int) (priceDiamonds * totalDiamonds / 1000) + "k)", x,
                y + 65);
        g.setColor(Color.yellow);
        long profit = (priceSapps * totalSapps) + (priceEms * totalEms)
                + (priceRubs * totalRubs) + (priceDiamonds * totalDiamonds);
        g.drawString("Total gems: "
                + (totalSapps + totalEms + totalRubs + totalDiamonds) + " ("
                + (int) (profit / 1000) + "k made | "
                + (int) (profit * 3.6 / totalseconds) + "k/hr)", x, y + 80);
 
        g
                .drawString(
                        "Exp to level: "
                                + skills.getXPToNextLevel(STAT_THIEVING)
                                + "("
                                + ((skills.getXPToNextLevel(STAT_THIEVING) * 60) / (expHour * 1000))
                                + "min)", 281, 150 + 35);
        // bar :D
        g.setColor(Color.orange);
        g.fillRect(281, 190, 225, 15);
        g.setColor(Color.green);
        g.fillRect(281, 190,
                (int) (skills.getPercentToNextLevel(STAT_THIEVING) * 2.25), 15);
        g.setColor(Color.white);
        g.drawString("" + skills.getPercentToNextLevel(STAT_THIEVING) + "%",
                290, 203);
        g.drawString("Exp gained: " + expGained + " (" + expHour + "k/hour)",
                282, 174);
 
    }
 
    @Override
    public int getMouseSpeed() {
        return 6;
    }
 
    private String stateToString(int a) {
        if (a == 0)
            return "Banking";
        if (a == 1)
            return "Eating";
        if (a == 2)
            return "Cracking";
        return "W00T";
    }
 
    private int getState() {
        curHP = (int) (Integer.parseInt(RSInterface.getInterface(748).getChild(
                8).getText()) / 10);
        if (curHP <= minHP) {
            if (bank.isOpen())
                return banking;
            if (!bank.isOpen() && getInventoryCount(food) == 0)
                return banking;
            if (!bank.isOpen() && getInventoryCount(food) > 0)
                return eating;
        }
        if (isInventoryFull())
            return banking;
        return cracking;
    }
 
    public void serverMessageRecieved(ServerMessageEvent e) {
        if (e.getMessage().contains("You get some loot")) {
            cracked = true;
            crackedCount++;
        }
        if (e.getMessage().contains("slip and trigger")) {
            cracked = true;
            crackFailed++;
        }
    }
 
    public boolean getPlayerAtTile(RSTile t) {
        int[] validPlayers = Bot.getClient().getRSPlayerIndexArray();
        org.rsbot.accessors.RSPlayer[] players = Bot.getClient()
                .getRSPlayerArray();
        for (int element : validPlayers) {
            if (players[element] == null) {
                continue;
            }
            RSPlayer player = new RSPlayer(players[element]);
            try {
                if (player.getLocation().equals(tile)) {
                    if (!player.equals(getMyPlayer())) {
                        log("Someone at my spot.");
                        return true;
                    }
                }
            } catch (Exception ignored) {
            }
        }
        return false;
    }
}