import org.rsbot.bot.Bot;
import org.rsbot.bot.input.Mouse;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.script.*;
import org.rsbot.script.wrappers.*;
import org.rsbot.script.wrappers.RSInterface;
import org.rsbot.script.wrappers.RSInterfaceChild;
import org.rsbot.script.wrappers.RSInterfaceComponent;
import org.rsbot.script.wrappers.RSNPC;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSTile;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
@ScriptManifest(authors = {"Zenzie/DREAM"}, category = "Mini-Game", name = "AIOSoulwarsDREAM", version = 0.1, description =
        "<html><head>" +
                "<style type=\"text/css\"> body {background-color: #FAF8CC; color: #4E387E; padding: 5px; font-family: Arial; text-align: center;}" +
                "h1 {font-weight: bold; color: #25383C; font-size: 16px; padding: 0px; margin: 0px; margin-top: 4px; }" +
                "h2 {color: #151B8D; padding: 0px; margin: 0px; margin-top: 1px; margin-bottom: 5px; font-weight: normal; font-size: 10px;}" +
                "td {font-weight: bold; width: 50%;}</style>" +
                "</head><body>" +
                "<div style=\"width: 100%, height: 80px; background-color: #C9C299; text-align: center; padding: 5px;\">" +
                "<h1>AIOSoulwarsDREAM 0.1 *BETA*</h1>" +
                "<h2>Zenzie/DREAM</h2></div><br />" +
                "1) Make sure that you are already using the combat style that you want to train.<br />" +
                "2) Make sure you are starting it by the green portal!<br />" +
                "3) Atk players is working great now leaving both graveyards<br />" +
                "4) Now quick prayer options works for curses too" +
                "<br /><br /><table style=\"border: 0px; width: 200px; margin: auto;\"><tr>" +
                "</select><tr><td>Play Style:</td>" +
                "<td><select name=\"playStyle\">" +
                "<option>Attack Players</option>" +
                "<option>Attack NPCs</option>" +
                "</select><tr><td>Turn on Quick-Prayer at:</td>" +
                "<td><select name=\"quickPrayAt\">" +
                "<option>Nowhere</option>" +
                "<option>Obelisk</option>" +
                "<option>Bandages</option>" +
                "<option>Jellies</option>" +
                "<option>Pyres</option>" +
                "</select><tr><td>Join team:</td><td><select name=\"joinTeam\">" +
                "<option>Red</option>" +
                "<option>Blue</option>" +
                "<option>Last won</option>" +
                "<option>Last lost</option>" +
                "<option>Clan chat *DONT USE SLOW FIXING*</option>" +
                "</select><tr><td>Pick Bandages One-by-one:</td>" +
                "<td><select name=\"oneByOne\">" +
                "<option>Yes</option>" +
                "<option>No</option>" +
                "</select></table>" +
                "<hr>" +
                "<h2> News! </h2>" +
                "- Got Zenzie's permission to release an updated version also iam nt a pro scripter but i got a lil knowledge about how to edit em so please be patient for updates as i have my own life so i dont have the full day to edit scripts.<br />" +
                "- Attacking players is now working good also fixed the graveyard problem, it now leaves both graveyards.<br />" +
                "- Gonna work on fixing attacking NPCs after that gonna try to add bone collector!" +
                "</body></html>")

public class AIOSoulwarsDREAM extends Script implements PaintListener, ServerMessageListener {

    public enum actions {
        CHOOSING_TEAM, JOINING_BLUE, JOINING_RED, WAITING_FOR_GAME, LEAVING_SPAWN, WALKING_TO_BANDAGES,
        GETTING_BANDAGES, USING_BANDAGES, ATTACKING_PLAYERS,LEAVING_GRAVEYARD,
        ATTACKING_MONSTER, WAIT
    }



    int selectedTab = 0;
    long startTime = System.currentTimeMillis();

    String onTeam = "None";

    int won = 0;
    int tie = 0;
    int lost = 0;
    int zeals = 0;
    int kickedGames = 0;
    int playedGames = 0;
    public int pyrefiendID = 8598;
    public int jellyID = 8599;
    String whatToJoin = "Nothing";
    String lastWon = "Dunno";

    String howToPlayInGame = "";
    String turnOnQuickPrayAt = "";
    boolean inGame = false;
    int cantAttack = 0;
    int antiBanTimer = 0;

    String actionName = "";

    int callBlue = 0;
    int callRed = 0;
    public String lastCCMessage = "";
  public boolean continueChat = true;

    int startXPSTR = skills.getCurrentSkillExp(STAT_STRENGTH);
    int startXPDEF = skills.getCurrentSkillExp(STAT_DEFENSE);
    int startXMAGE = skills.getCurrentSkillExp(STAT_MAGIC);
    int startXRANGE = skills.getCurrentSkillExp(STAT_RANGE);
    int startXPATT = skills.getCurrentSkillExp(STAT_ATTACK);
    int startHPXP = skills.getCurrentSkillExp(STAT_HITPOINTS);

    int[] dropTheseItems = {229,14643,14644,14638,14640};
    String BandagesClickByClick;

    public void serverMessageRecieved(ServerMessageEvent e) {
        String message = e.getMessage();
        if (message.contains("You receive 1 Zeal")) {
            inGame = false;
            zeals += 1;
            lost += 1;
            playedGames += 1;
            if (onTeam.equals("Blue")) {
                lastWon = "Red";
            } else if (onTeam.equals("Red")) {
                lastWon = "Blue";
            }
            actionName = "Choosing Team";
        }
        if (message.contains("You receive 2 Zeal")) {
            inGame = false;
            zeals += 2;
            playedGames += 1;
            tie += 1;
            lastWon = "Dunno";
            actionName = "Choosing Team";
        }
        if (message.contains("You receive 3 Zeal")) {
            inGame = false;
            zeals += 3;
            won += 1;
            playedGames += 1;
            if (onTeam.equals("Blue")) {
                lastWon = "Blue";
            } else if (onTeam.equals("Red")) {
                lastWon = "Red";
            }
            actionName = "Choosing Team";
        }
        if (message.contains(("You receive 0 Zeal"))) {
            inGame = false;
            playedGames += 1;
            if (onTeam.equals("Blue")) {
                lastWon = "Red";
            } else if (onTeam.equals("Red")) {
                lastWon = "Blue";
            }
        }
    }

    public void onRepaint(Graphics g) {

        int x = Bot.getInputManager().getX();
        int y = Bot.getInputManager().getY();

        g.setColor(Color.BLUE);
        if(x != -1 && y != -1) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(x - 5, y, x + 5, y);
            g2.drawLine(x, y - 5, x, y + 5);
            g2.setStroke(new BasicStroke(1));
        }

        g.setColor(Color.WHITE);
        int attXP = skills.getCurrentSkillExp(STAT_ATTACK) - startXPATT;
        int strXP = skills.getCurrentSkillExp(STAT_STRENGTH) - startXPSTR;
        int defXP = skills.getCurrentSkillExp(STAT_DEFENSE) - startXPDEF;
        int mageXP = skills.getCurrentSkillExp(STAT_MAGIC) - startXMAGE;
        int rangeXP = skills.getCurrentSkillExp(STAT_RANGE) - startXRANGE;
        int gainedHPXP = skills.getCurrentSkillExp(STAT_HITPOINTS) - startHPXP;
        int barsize = 100;
        int barheight = 15;
        int transparancy = 170;
        int xBar = 560;
        int yBar = 360;

        long millis = System.currentTimeMillis() - startTime;
        long hours = millis / (1000 * 60 * 60);
        millis -= hours * 1000 * 60 * 60;
        long minutes = millis / (1000 * 60);
        millis -= minutes * 1000 * 60;
        long seconds = millis / 1000;

        selectedTab = getMouseTab();



        g.setColor(new Color(0, 0, 0, 90));
        g.fillRect(550, 205, 184, 30);
        g.fill3DRect(550, 234, 185, 233, true);
        g.setColor(Color.BLACK);
        g.drawRect(550, 205, 184, 30);
        g.drawLine(610, 205, 610, 235);
        g.drawLine(680, 205, 680, 235);

        g.setColor(Color.LIGHT_GRAY);
        g.drawString("Game", 564, 225);
        g.drawString("Gained", 624, 225);
        g.drawString("Info", 695, 225);

        g.setColor(Color.WHITE);
        switch (selectedTab) {
            case 0:
                g.drawString("Game", 564, 225);
                g.drawString("Games completed: " + playedGames, 560, 255);
                g.drawString("Games kicked: " + kickedGames, 560, 270);
                g.drawString("Won " + won + " games", 560, 300);
                g.drawString("Tied " + tie + " games", 560, 315);
                g.drawString("Lost " + lost + " games", 560, 330);

                if(getActivityBarPercent() != -1 && inGame) {
                    g.drawString("Activity Bar " + getActivityBarPercent() + "%", 565, 372);
                    g.setColor(new Color(255, 0, 0, transparancy));
                    g.fillRect(xBar, yBar, barsize, barheight);
                    g.setColor(new Color(0, 255, 0, transparancy));
                    g.fillRect(xBar, yBar, barsize/100* getActivityBarPercent(), barheight);
                    g.setColor(new Color(255, 255, 255, transparancy));
                    g.drawRect(xBar, yBar, barsize, barheight);
                    g.setColor(Color.WHITE);
                }

                break;
            case 1:
                g.drawString("Gained", 624, 225);
                g.drawString("Gained " + zeals + " zeals", 560, 255);
                g.drawString("Hitpoints XP gained : " + gainedHPXP, 560, 285);
                g.drawString("Attack XP gained: " + attXP, 560, 300);
                g.drawString("Strength XP gained: " + strXP, 560, 315);
                g.drawString("Defense XP gained: " + defXP, 560, 330);
                g.drawString("Ranged XP gained: " + rangeXP, 560, 345);
                g.drawString("Magic XP gained: " + mageXP, 560, 360);
                break;
            case 2:
                g.drawString("Info", 695, 225);
                g.drawString("Run time: " + hours + ":" + minutes + ":" + seconds, 560, 255);
                g.drawString("State: " + actionName, 560, 270);
                g.drawString("Blue calls: " + callBlue, 560, 300);
                g.drawString("Red calls: " + callRed, 560, 315);
                g.drawString("Joining: " + whatToJoin, 560, 345);
                g.drawString("In-Game Style: " + howToPlayInGame, 560, 360);
                g.drawString("In-Game: " + String.valueOf(inGame), 560,375);
                g.drawString("AIOSoulwarsDREAM V0.1 BETA", 560, 435);
                g.drawString("By Zenzie/DREAM", 560, 450);
                break;
        }
    }

    private int getMouseTab() {
        final Mouse mouse = Bot.getClient().getMouse();
        if (mouse.x > 550 && mouse.x < 610 && mouse.y > 205 && mouse.y < 230) {
            selectedTab = 0;
        }
        if (mouse.x > 610 && mouse.x < 680 && mouse.y > 205 && mouse.y < 230) {
            selectedTab = 1;
        }
        if (mouse.x > 680 && mouse.x < 735 && mouse.y > 205 && mouse.y < 230) {
            selectedTab = 2;
        }
        return selectedTab;
    }

    @Override
    public boolean onStart(final Map<String, String> args) {
        setCameraAltitude(true);
        turnOnQuickPrayAt = args.get("quickPrayAt");
        howToPlayInGame = args.get("playStyle");
        whatToJoin = args.get("joinTeam");
        BandagesClickByClick = args.get("oneByOne");
        actionName = "Choosing Team";
        if(whatToJoin.equals("Clan chat")) {
          log("Started reading clan chat");
          new clanChat().start();
        }
        return true;
    }

    public void onFinish() {
    continueChat = false;
    Bot.getEventManager().removeListener(PaintListener.class, this);
    Bot.getEventManager().removeListener(ServerMessageListener.class, this);
  }

    public actions getAction() {

        if (actionName.equals("Choosing Team") && !actionName.equals("Waiting for game") && !inGame) {
            return actions.CHOOSING_TEAM;
        }
        if (actionName.contains("Joining") && !actionName.equals("Choosing Team") && !inArea(1870,1879,3158,3166) &&
                !inArea(1900,1909,3157,3166) && !inArea(1816,1823,3220,3230) && !inArea(1951,1958,3234,3244) && !inGame) {
            if (actionName.equals("Joining blue team")) {
                return actions.JOINING_BLUE;
            }
            if (actionName.equals("Joining red team")) {
                return actions.JOINING_RED;
            }
        }
        if (inArea(1870,1879,3158,3166) || inArea(1900,1909,3157,3166)) {
            return actions.WAITING_FOR_GAME;
        }
        if (inArea(1816,1823,3220,3230) || inArea(1951,1958,3234,3244)) {
            return actions.LEAVING_SPAWN;
        }
        if((inArea(1798,1811,3250,3260) || inArea(1963,1974,3200,3212)) && howToPlayInGame.equals("Bandage") && inGame ) {
            if (inventoryContains(14640)) {
                return actions.USING_BANDAGES;
            } else {
                return actions.GETTING_BANDAGES;
            }
        }
        if (!inArea(1816,1823,3220,3230) && !inArea(1951,1958,3234,3244) && !inArea(1798,1811,3250,3260)
                && !inArea(1963, 1974, 3200, 3212) && howToPlayInGame.equals("Bandage") && inGame) {
            return actions.WALKING_TO_BANDAGES;
        }
        if (howToPlayInGame.equals("Attack Players") && inGame) {
            if(!inArea(1841, 1843, 3217, 3219) && !inArea(1932, 1934, 3244, 3246) && !inArea(1816,1823,3220,3230) && !inArea(1951,1958,3234,3244)) {
                return actions.ATTACKING_PLAYERS;
            }
        }
       /*blue*/ if ((inArea(1841, 1843, 3217, 3219) ||/*red*/ inArea(1932, 1934, 3244, 3246)) && inGame) {
            return actions.LEAVING_GRAVEYARD;
        }
        if(!inArea(1816,1823,3220,3230) && !inArea(1951,1958,3234,3244) && !inArea(1841, 1843, 3217, 3219) &&
                !inArea(1932, 1934, 3244, 3246) && howToPlayInGame.equals("Attack NPCs") && inGame) {
            return actions.ATTACKING_MONSTER;
        }
        return actions.WAIT;
    }

    public int loop() {

        if(!actionName.equals("Choosing Team") && !inArea(1770,2000,3180,3610) &&
                !actionName.contains("Joining") && !inArea(1870,1879,3158,3166) && !inArea(1900,1909,3157,3166)) {
            inGame = false;
            actionName = "Choosing Team";
        }

        if(!inGame && inventoryContains(dropTheseItems)) {
            for (final int id : dropTheseItems) {
        if (getInventoryCount(id) > 0) {
          clickInventoryItem(dropTheseItems, "Drop");
        }
            }
        }

        actions act = getAction();

        if (!isRunning() && getEnergy() >= random(45, 60)) {
            setRun(true);
            wait(random(250,500));
        }

        if (RSInterface.getChildInterface(243, 4).isValid() &&
                RSInterface.getChildInterface(243, 4).containsText("You were removed from the game due") || RSInterface.getChildInterface(243, 4).containsText("I chucked you out")) {
            kickedGames += 1;
            actionName = "Choosing Team";
            inGame = false;

        }

            String time = RSInterface.getInterface(836).getChild(27).getText();
            if (time.equals("2 mins") || time.equals(" 1 min") || time.equals("3 mins")) {
                if (whatToJoin.equals("Clan chat")) {
                    continueChat = true;
                }
            }

        if(inGame) {
            if(random(0,30) == 20) {
                antiBan(0,6);
            }
        }

        switch (act) {

            case CHOOSING_TEAM:
                if (whatToJoin.equals("Red")) {
                    actionName = "Joining red team";
                } else if (whatToJoin.equals("Blue")) {
                    actionName = "Joining blue team";
                } else if (whatToJoin.equals("Last won")) {
                    if (lastWon.equals("Blue")) {
                        actionName = "Joining blue team";
                    } else if (lastWon.equals("Red")) {
                        actionName = "Joining red team";
                    } else if (lastWon.equals("Dunno")) {
                        actionName = "Joining blue team";
                    }
                } else if (whatToJoin.equals("Last lost")) {
                    if (lastWon.equals("Blue")) {
                        actionName = "Joining red team";
                    } else if (lastWon.equals("Red")) {
                        actionName = "Joining blue team";
                    } else if (lastWon.equals("Dunno")) {
                        actionName = "Joining blue team";
                    }
                } else if (whatToJoin.equals("Clan chat") && callBlue == 0 && callRed == 0) {
                    continueChat = true;
                } else if(callBlue > callRed && whatToJoin.equals("Clan chat")) {
                    actionName = "Joining blue team";
                } else if(callRed > callBlue && whatToJoin.equals("Clan chat")) {
                     actionName = "Joining red team";
                }
                break;

            case JOINING_BLUE:
                onTeam = "Blue";
                if(distanceTo(new RSTile(1882, 3162)) > 3) {
                    walkTo2(randomizeTile(new RSTile(1882, 3162),2,2));
                }
                joinBlue();
                wait (random(1000, 2000));
                clickInterfaces();
                wait (random(1000, 2000));
            break;

            case JOINING_RED:
                onTeam = "Red";
                if(distanceTo(new RSTile(1899, 3162)) > 3) {
                    walkTo2(randomizeTile(new RSTile(1899, 3162),2,2));
                }
               joinRed();
               random(1000,2000);
               clickInterfaces();
               random(1000, 2000);
                break;

            case WAITING_FOR_GAME:
                callBlue = 0;
                callRed = 0;
                inGame = true;
                continueChat = false;
                actionName = "Waiting for game";
                if (actionName.equals("Waiting for game")) {
                    if (random(1, 30) == 2) {
                        if (onTeam.equals("Blue")) {
                            int blueX = random(1870, 1879);
                            int blueY = random(3159, 3165);
                            walkTile(new RSTile(blueX, blueY));
                        }
                        if (onTeam.equals("Red")) {
                            int redX = random(1900, 1909);
                            int redY = random(3157, 3165);
                            walkTile(new RSTile(redX, redY));
                        }
                    }
                    wait(random(1666, 5000));
                    antiBanTimer += 1;
                    if (antiBanTimer >= 4) {
                        antiBan(0, 6);
                        antiBanTimer = 0;
                    }
                }
                break;

            case LEAVING_SPAWN:
                setCameraAltitude(true);
                actionName = "Leaving spawn";
                if (onTeam.equals("Blue")) {
                  leaveBlueStart();
                  random(1000, 2000);
                            }


                if (onTeam.equals("Red")) {
                  leaveRedStart(); 
                        random(1000, 2000);

                }
                break;

            case WALKING_TO_BANDAGES:
                actionName = "Walking to bandages";
                if (onTeam.equals("Blue")) {
                    walkTo2(randomizeTile(new RSTile(1810, 3254),2 ,2));
                }
                if (onTeam.equals("Red")) {
                    walkTo2(randomizeTile(new RSTile(1963,3207),2 ,2));
                }
                break;

            case GETTING_BANDAGES:
                if(!isInCameraAngleForBandages()) {
                    int randomCom = random(1,3);
                    if(randomCom == 2) {
                        setCameraRotation(random(240,280));
                    } else {
                        setCameraRotation(random(60,100));
                    }
                }
                actionName = "Getting bandages";
                setCameraAltitude(true);
                if (onTeam.equals("Blue")) {
                    RSTile bandageTableBlue = new RSTile(1810, 3254);
                    if (bandageTableBlue != null) {
                        if (distanceTo(new RSTile(1810, 3254)) > 2 && !getMyPlayer().isMoving()) {
                            walkTileMM(randomizeTile(new RSTile(1810, 3254), 2, 2));
                        }
                        if (distanceTo(bandageTableBlue) <= 2 && !getMyPlayer().isMoving()) {
                            if(BandagesClickByClick.equals("No")) {
                                atTile(bandageTableBlue, "Take-x");
                                wait(random(2000, 5000));
                                if (RSInterface.getInterface(752).getChild(4).isValid() && RSInterface.getInterface(752).getChild(4).containsText("How many bandages would")
                                    && !getMyPlayer().isInCombat()) {
                                    if(distanceTo(bandageTableBlue) <= 2) {
                                        sendText(Integer.toString(random(28, 100)), true);
                                    }
                                    wait(random(600, 950));
                                }
                            } else {
                                atTile(bandageTableBlue, "Take-from");
                                wait(random(1000,1500));
                            }
                        } else if (!getMyPlayer().isMoving()) {
                            walkTile(randomizeTile(new RSTile(1810, 3254), 2, 2));
                        }
                    }
                } else {
                    RSTile bandageTableRed = new RSTile(1963, 3207);
                    if (bandageTableRed != null) {
                        if (distanceTo(new RSTile(1963, 3207)) > 2 && !getMyPlayer().isMoving()) {
                            walkTileMM(randomizeTile(new RSTile(1962,3207), 2, 2));
                        }
                        if (distanceTo(bandageTableRed) <= 2 && !getMyPlayer().isMoving()) {
                            if(BandagesClickByClick.equals("No")) {
                                wait(random(1000, 1500));
                                atTile(bandageTableRed, "Take-x");
                                wait(random(1500, 3000));
                                if (RSInterface.getInterface(752).getChild(4).isValid() && RSInterface.getInterface(752).getChild(4).containsText("How many bandages would")
                                        && !getMyPlayer().isInCombat()) {
                                    if(distanceTo(bandageTableRed) <= 2) {
                                        sendText(Integer.toString(random(28, 100)), true);
                                    }
                                    wait(random(600, 950));
                                }
                            } else {
                                atTile(bandageTableRed, "Take-from");
                                wait(random(1000,1500));
                            }
                        } else if (!getMyPlayer().isMoving()) {
                            walkTile(randomizeTile(new RSTile(1963, 3207), 2, 2));
                        }
                    }
                }
                break;

            case USING_BANDAGES:
                    if(turnOnQuickPrayAt.equals("Bandages")) {
                        if(getSetting(1396) == 0 && skills.getCurrentSkillLevel(Constants.STAT_PRAYER) > 0) {
                            atInterface(749, 4, "Turn quick");
                        }
                    }
                    useBandagesOnOtherPlayer();
                break;

            case ATTACKING_PLAYERS:
                if(!inArea(1872,1901,3218,3245) && !inArea(1798,1811,3250,3260) && !inArea(1963, 1974, 3212, 3200)) {
                    actionName = "Walking to obelisk";
                    if (onTeam.equals("Blue")) {
                        if(getMyPlayer().getLocation().getX() < 1816
                                && getMyPlayer().getLocation().getY() < 3235) {
                            walkTo2(randomizeTile(new RSTile(1810,3245),2,2));
                        } else if (getMyPlayer().getLocation().getX() > 1816
                                && getMyPlayer().getLocation().getX() < 1843) {
                            walkTo2(randomizeTile(new RSTile(1853, 3234),2 , 2));
                        } else {
                            walkTo2(randomizeTile(new RSTile(1885,3232),2,2));
                        }
                    }
                    if (onTeam.equals("Red")) {
                        if(getMyPlayer().getLocation().getX() >= 1959) {
                            walkTo2(randomizeTile(new RSTile(1955,3212),2,2));
                        } else if(getMyPlayer().getLocation().getX() > 1929
                                && getMyPlayer().getLocation().getX() < 1950) {
                            walkTo2(randomizeTile(new RSTile(1920,3231),2,2));
                        } else {
                            walkTo2(randomizeTile(new RSTile(1885,3232),2,2));
                        }
                    }
                }
                if(inArea(1872,1901,3218,3245)) {
                    actionName = "Killing players";
                    setCameraAltitude(true);
                    if(turnOnQuickPrayAt.equals("Obelisk")) {
                        if(getSetting(1396) == 0 && skills.getCurrentSkillLevel(Constants.STAT_PRAYER) > 0) {
                            atInterface(749, 4, "Turn quick");
                        }
                    }
                    if(!getMyPlayer().isMoving() && !getMyPlayer().isInCombat()) {
                        getAttackAblePlayer();
                    }
                }
                break;

            case LEAVING_GRAVEYARD:
                actionName = "Leaving graveyard";
                cantAttack = 0;


                if (actionName.equals("Leaving graveyard")) {
                  if (inArea(1932, 1934, 3244, 3246)) {
                    leaveRedGrave();
                      random(1000, 2000);
                        }
                  if (inArea(1841, 1843, 3217, 3219)) {
                    leaveBlueGrave();
                      random(1000, 2000);
                        }
                }
                break;

            case ATTACKING_MONSTER:
                int slayerLevel = skills.getRealSkillLevel(STAT_SLAYER);

                if(slayerLevel < 30) {
                    howToPlayInGame = "Attack Players";
                }

                if(slayerLevel >= 29 && slayerLevel <= 59 && !inArea(1835,1855,3241,3256) && !inArea(1920,1935,3206,3218)) {
                    actionName = "Walking to pyres";
                }
                if(slayerLevel >= 60 && !inArea(1874,1893,3203,3216) && !inArea(1887,1908,3248,3255)) {
                    actionName = "Walking to jellies";
                }

                if(actionName.equals("Walking to pyres")) {
                    if(onTeam.equals("Blue")) {
                        if(getMyPlayer().getLocation().getX() < 1816
                                && getMyPlayer().getLocation().getY() < 3235) {
                            walkTo2(randomizeTile(new RSTile(1810,3245),2,2));
                        } else {
                            walkTo2(randomizeTile(new RSTile(1842,3246),2,2));
                       }
                    }
                    if(onTeam.equals("Red")) {
                        if(getMyPlayer().getLocation().getX() >= 1959) {
                            walkTo2(randomizeTile(new RSTile(1955,3212),2,2));
                        } else {
                            walkTo2(randomizeTile(new RSTile(1927,3214),2,2));
                        }
                    }
                }
                if(actionName.equals("Walking to jellies")) {
                    if(onTeam.equals("Blue")) {
                        if(getMyPlayer().getLocation().getX() < 1816
                                && getMyPlayer().getLocation().getY() < 3235) {
                            walkTo2(randomizeTile(new RSTile(1810,3245),2,2));
                        } else {
                            walkTo2(randomizeTile(new RSTile(1881,3206),2,2));
                       }
                    }
                    if(onTeam.equals("Red")) {
                        if(getMyPlayer().getLocation().getX() >= 1959) {
                            walkTo2(randomizeTile(new RSTile(1955,3212),2,2));
                        } else {
                            walkTo2(randomizeTile(new RSTile(1897,3252),2,2));
                        }
                    }
                }
                if(inArea(1835,1855,3241,3256) || inArea(1920,1935,3206,3218)) {
                    actionName = "Killing pyrefiens";
                    if(getMyPlayer().getInteracting() == null && getMyPlayer().getInteracting() instanceof RSNPC) {
                        RSNPC pyre = getNearestNPCByID(8598);
                        if(pyre != null) {
                          attackPyrefiends();
                        }
                    }
                }
                if(inArea(1874,1893,3203,3216) || inArea(1887,1908,3248,3255)) {
                    actionName = "Killing jellies";
                    if(getMyPlayer().getInteracting() == null && getMyPlayer().getInteracting() instanceof RSNPC) {
                        RSNPC jelly = getNearestNPCByID(8599);
                        if(jelly != null) {
                            attackJellies();
                        }
                    }
                }
                break;

            case WAIT:
                wait(random(500,900));
                break;
        }
        return random(500, 900);
    }
    public void attackPyrefiends() {
        RSNPC pyrefiend = getNearestNPCByID(pyrefiendID);
        if (clickTheCharacter(pyrefiend, "Pyrefiend", "Attack Pyrefiend"))
          wait(random(500, 1000));
      }
      public void attackJellies() {
        RSNPC jelly = getNearestNPCByID(jellyID);
        if (clickTheCharacter(jelly, "Jelly", "Attack Jelly"))
          wait(random(500, 1000));
      }
      //Creds to Peach
      public boolean clickTheCharacter(RSCharacter npc, String npcName, String action) {
          if (npc == null) {
                          return false;
                  }

          RSTile tile = npc.getLocation();

          if (!tile.isValid()) {
                          return false;
                  }

          if (distanceTo(tile) > 6) {
                          walkTileMM(tile);
                          wait(random(340, 1310));
                  }

          try {
                          Point screenLoc = null;

                          for (int i = 0; i < 30; i++) {
                                          screenLoc = npc.getScreenLocation();

                                          if (!npc.isValid() || !pointOnScreen(screenLoc)) {
                                                          //not on screen
                                                          return false;
                                                  }

                                          if (getMenuItems().get(0).toLowerCase().contains(npcName)) {
                                                          break;
                                                  }

                                          if (getMouseLocation().equals(screenLoc)) {
                                                          break;
                                                  }

                                          moveMouse(screenLoc);
                                  }

                          screenLoc = npc.getScreenLocation();

                          if (getMenuItems().size() <= 1) {
                                          return false;
                                  }

                          if (getMenuItems().get(0).toLowerCase().contains(action)) {
                                          clickMouse(true);
wait(random(200,300));
                                          return true;

                                  } else {
                                          clickMouse(false);
                                          return atMenu(action);
                                  }

                  } catch (Exception e) {
                          e.printStackTrace();
                          return false;
                  }
  }
    public boolean onPoly(Polygon p, final String action) {
        try{
        getMenuItems();
        if (p == null) {
            return false;
        }
        if(p != null && !getMyPlayer().isMoving()) {
            for(int i = 0; i < getMenuItems().size();) {
                if(!getMenuItems().get(i).contains(action)) {
                    moveMouse(p);
                    wait(random(150,200));
                }
          final int idx = getMenuIndex(action);
            if (!isMenuOpen()) {
              if (idx == -1) {
                return false;
              }
              if (idx == 0) {
                clickMouse(true);
              } else {
                clickMouse(false);
                atMenuItem(idx);
              }
              return true;
            } else {
              if (idx == -1) {
                while (isMenuOpen()) {
                  moveMouseRandomly(750);
                  wait(random(750, 1200));
                }
            return false;
              } else {
                atMenuItem(idx);
                    return true;
              }
            }
            }
        }
       } catch(NullPointerException ignored) {}
       return false;
  }

    public boolean walkTo2(RSTile dest) {
        if(isRandDest(3, dest)) {
            return false;
        }
        if(!getMyPlayer().isMoving() || distanceTo(getDestination()) < random(5,10)
                && !isRandDest(3, dest)) {
            if(getMyPlayer().getInteracting() == null) {
                return walkTo(dest);
            }
        }
        return false;
    }

    public boolean isRandDest(final int range, final RSTile dest) {
    final int minX = getMyPlayer().getLocation().getX() - range;
    final int minY = getMyPlayer().getLocation().getY() - range;
    final int maxX = getMyPlayer().getLocation().getX() + range;
    final int maxY = getMyPlayer().getLocation().getY() + range;
    for (int x = minX; x < maxX; x++) {
      for (int y = minY; y < maxY; y++) {
                if(getDestination() == dest) {
                    log("Walking to random dest");
                    return true;
                }
      }
    }
    return false;
  }

    private int antiBan(int minC, int maxC) {

        final int randomAction = random(minC, maxC);
        switch (randomAction) {
            case 0:
                return random(1250,2500);
            case 1:
                Point randomMouse;
                final int rndMovement = random(1, 5);
                for (int a = 0; a < rndMovement; a++) {
                    randomMouse = new Point(random(15, 730), random(15, 465));
                    moveMouse(randomMouse);
                    wait(random(50, 800));
                }
                return random(130, 810);
            case 2:
                final int currentAngle = getCameraAngle();
                switch (random(0, 1)) {
                    case 0:
                        setCameraRotation(currentAngle + random(0, 650));
                        return random(710, 1700);
                    case 1:
                        setCameraRotation(currentAngle - random(0, 650));
                        return random(710, 1700);
                }
            case 3:
                final int currentAlt = Bot.getClient().getCamPosZ();
                final int random = random(0, 10);
                if (random <= 7) {
                    setCameraAltitude(currentAlt - random(0, 100));
                    return random(410, 2130);
                } else {
                    setCameraAltitude(currentAlt + random(0, 100));
                    return random(410, 2130);
                }
            case 4:
                final int currentAngle2 = getCameraAngle();
                Bot.getClient().getCamPosZ();
                switch (random(0, 1)) {
                    case 0:
                        setCameraRotation(currentAngle2 + random(0, 650));
                        setCameraAltitude(random(80, 100));
                        return random(410, 2130);
                    case 1:
                        setCameraRotation(currentAngle2 - random(0, 650));
                        setCameraAltitude(random(40, 80));
                        return random(410, 2130);
                }
            case 5:
                return random(310, 2400);

            case 6:
                final int currentAlt2 = Bot.getClient().getCamPosZ();
                final int random2 = random(0, 2);
                if (random2 <= 2) {
                    setCameraAltitude(currentAlt2 - random(-100, +100));
                    return random(410, 2130);
                } else {
                    setCameraAltitude(currentAlt2 + random(-100, +100));
                    return random(410, 2130);
                }
        }
        return random(100, 200);
    }

    public boolean clickInventoryItem(int itemID, String option) {
        if (getCurrentTab() != TAB_INVENTORY) {
            openTab(TAB_INVENTORY);
        }
        int[] items = getInventoryArray();
        java.util.List<Integer> possible = new ArrayList<Integer>();
        for (int i = 0; i < items.length; i++) {
            if (items[i] == itemID) {
                possible.add(i);
            }
        }
        if (possible.size() == 0) {
            return false;
        }
        int idx = possible.get(random(0, possible.size()));
        Point t = getInventoryItemPoint(idx);
        moveMouse(t, 5, 5);
        long waitTime = System.currentTimeMillis() + random(50, 250);
        boolean found = false;
        while (!found && System.currentTimeMillis() < waitTime) {
            wait(15);
                if ((getMenuItems()).get(0).toLowerCase().equals(
                        option.toLowerCase())) {
                    found = true;
                }
            }
        if (found) {
            clickMouse(true);
            wait(random(150, 250));
            return true;
        }
        clickMouse(false);
        wait(random(150, 250));
        return atMenu(option);
    }

    public boolean isInCameraAngleForGraveYard() {
        int angle = getCameraAngle();
        return angle < 380 && angle > 330 || angle < 200 && angle > 150;
    }

    public boolean isInCameraAngleForBandages() {
        int angle = getCameraAngle();
        return angle < 240 && angle > 280 || angle < 60 && angle > 100;
    }

    public int waitTime() {
        if (RSInterface.getChildInterface(211, 1).isValid() &&
                RSInterface.getChildInterface(211, 1).containsText("You left a game of Soul Wars early")) {
            String getLine = RSInterface.getChildInterface(211, 1).getText();
            String subLine = getLine.substring(getLine.indexOf("wait ") + 5,
                    getLine.indexOf(" minutes"));
            return Integer.parseInt(subLine);
        }
        return 0;
    }

    public void walkTile(RSTile walkTo) {
        if (!getMyPlayer().isMoving() && distanceTo(walkTo) > 5) {
                walkTileMM(walkTo);
        }
    }

    @SuppressWarnings("deprecation")
  public boolean useBandagesOnOtherPlayer() {
        RSPlayer player = null;
        int[] validPlayers = Bot.getClient().getRSPlayerIndexArray();
        org.rsbot.accessors.RSPlayer[] players = Bot.getClient().getRSPlayerArray();

        for (int element : validPlayers) {
            if (players[element] == null) {
                continue;
            }

            setCameraAltitude(true);
            player = new RSPlayer(players[element]);
            try {
                RSTile bandaging = player.getLocation();
                Point checkPlayer = Calculations.tileToScreen(bandaging);
                String targName = player.getName();
                if (distanceTo(bandaging) > 10 || !pointOnScreen(checkPlayer) || player.getTeam() != getMyPlayer().getTeam()) {
                    continue;
                }
                if (actionName.equals("Choosing team") || !inventoryContains(14640)) {
                    break;
                }
                if (onTeam.equals("Blue")) {
                    RSObject bandageTableBlue = findObject(42023);
                    if (bandageTableBlue != null) {
                        if (distanceTo(bandageTableBlue.getLocation()) >= 8 && !getMyPlayer().isMoving()) {
                        walkTo(randomizeTile(new RSTile(1810, 3254),2 ,2));
                        }
                    } else {
                        walkTo(randomizeTile(new RSTile(1810, 3254),2 ,2));
                    }
                } else {
                    RSObject bandageTableRed = findObject(42024);
                    if (bandageTableRed != null) {
                        if (distanceTo(bandageTableRed.getLocation()) >= 8 && !getMyPlayer().isMoving()) {
                            walkTo(randomizeTile(new RSTile(1963,3207),2 ,2));
                        }
                    } else {
                        walkTo(randomizeTile(new RSTile(1963,3207),2 ,2));
                    }
                }
                if (inventoryContains(14640) && getActivityBarPercent() <= random(40,80)
                        && player.getTeam() == getMyPlayer().getTeam()) {
                    if(!isItemSelected()) {
                        clickInventoryItem(14640, "use");
                    }
                    wait(random(700, 900));
                    actionName = "Bandaging People";
                    log("Using bandages on " + targName);
                    moveMouse(checkPlayer, 2, 2);
                    wait(random(500, 750));
                    clickMouse(false);
                    atMenu("use bandages -> ");
                }
                return true;
            } catch (Exception ignored) {
            }
        }
        return player != null;
    }
    public void joinBlue() {
        setCompass('w');
        if(!getMyPlayer().isMoving()) {
          myatTile(new RSTile(1879, 3162), "Pass");
          wait(random(300, 500));
        }
    }

    public void joinRed() {
        setCompass('e');
        if(!getMyPlayer().isMoving()) {
          myatTile(new RSTile(1900, 3162), "Pass");
          wait(random(300, 500));
        }
    }

    public void leaveRedGrave() {
        setCompass('s');
        myatTile(new RSTile(1933, 3243), "Pass");
        wait(random(800, 1500));
    }

    public void leaveBlueGrave() {
        setCompass('n');
        myatTile(new RSTile(1842, 3220), "Pass");
        wait(random(800, 1500));
    }

    public void leaveRedStart() {
      setCameraRotation(random(260, 280));
        if (distanceTo(new RSTile(1959, 3239)) > 3) {
          walkTileMM(new RSTile(1958, 3239));
        } else if (distanceTo(new RSTile(1960, 3239)) <= 4) {
          myatTile(new RSTile(1959, 3239), "Pass");
          wait(random(800, 1500));
        }
      }

    public void leaveBlueStart() {
      setCameraRotation(random(80, 100));
        if (distanceTo(new RSTile(1815, 3225)) > 3) {
          walkTileMM(new RSTile(1816, 3225));
        } else if (distanceTo(new RSTile(1815, 3225)) <= 4) {
          myatTile(new RSTile(1815, 3225), "Pass");
          wait(random(800, 1500));
        }
    }
    public boolean atMenuBandages(final String optionContains) {
    final int idx = getMenuIndex(optionContains);
    if (!isMenuOpen()) {
      if (idx == -1) {
        return false;
      }
      if (idx == 0) {
        clickMouse(true);
      } else {
        clickMouse(false);
        atMenuItem(idx);
      }
      return true;
    } else {
      if (idx == -1) {
        while (isMenuOpen()) {
          moveMouseRandomly(750);
          wait(random(100, 500));
        }
        return false;
      } else {
        atMenuThingBandages(idx);
        return true;
      }
    }
  }
    public void clickInterfaces() {
        while (RSInterface.getChildInterface(211, 3).getAbsoluteX() > 20
            || RSInterface.getChildInterface(228, 2).isValid()) {
          wait (random(1000, 2000));
          if (RSInterface.getChildInterface(211, 1).isValid()) {
            if (RSInterface.getChildInterface(211, 1).containsText("early")) {
              wait(random(5000, 10000));
            }
          }
          if (RSInterface.getChildInterface(211, 3) != null) {
            clickMouse(190 + random(0, 140), 445 + random(0, 15), true);
          }
          wait(random(800, 1000));
          if (RSInterface.getChildInterface(228, 2) != null) {
            clickMouse(220 + random(0, 80), 395 + random(0, 15), true);
          }
          wait(random(800, 1000));
        }
      }
    public boolean atMenuThingBandages(int i) {
    if (!isMenuOpen()) {
      return false;
    }
    try {
      if (getMenuItems().size() < i) {
        return false;
      }
      final RSTile menu = getMenuLocation();
            final int randomIdx = random(1, getMenuItems().size());
      final int xOff = random(4, getMenuItems().get(randomIdx).length() * 4);
      final int yOff = 21 + 15 * i + random(3, 12);
      moveMouse(menu.getX() + xOff, menu.getY() + yOff, 2, 2);
      if (!isMenuOpen()) {
        return false;
      }
      clickMouse(true);
      return true;
    } catch (final Exception e) {
      e.printStackTrace();
      return false;
    }
  }

    public int getActivityBarPercent() {
      RSInterfaceChild c = RSInterface.getInterface(836).getChild(56);
      if (c != null && c.isValid() && c.getAbsoluteX() > -1) {
            return ((c.getArea().height * 100) / 141) - 2;
        }
        return -1;
    }

    public boolean isItemSelected(){
      for(RSInterfaceComponent com : getInventoryInterface().getComponents()){
        if(com.getBorderThickness() == 2)
          return true;
      }
      return false;
    }

    public boolean getAttackAblePlayer() {
        RSPlayer player;
        int[] validPlayers = Bot.getClient().getRSPlayerIndexArray();
        org.rsbot.accessors.RSPlayer[] players = Bot.getClient().getRSPlayerArray();

        for (int element : validPlayers) {
            player = new RSPlayer(players[element]);
            if (players[element] == null) {
                continue;
            }
            RSTile targetLocation = player.getLocation();
            Point targetLoc = Calculations.tileToScreen(targetLocation);
            String targName = player.getName();
            if (!pointOnScreen(targetLoc)) {
                continue;
            }
            if (getMyPlayer().getInteracting() == null && player.getHPPercent() > 10) {
                if(player.getTeam() != getMyPlayer().getTeam()) {
                    moveMouse(targetLoc, 5, 5);
                    cantAttack = 0;
                    atMenu("Attack " + targName);
                    log("Attacking " + targName);
                    wait(random(1500,2000));
                }
            }
            if (!inGame || getMyPlayer().isMoving()) {
                break;
            }
        }
        return true;
    }
    public boolean AonTile(RSTile tile, String action) {
        if (!tile.isValid()) {
            return false;
        }
        if (distanceTo(tile) > 5) {
            walkTileMM(tile);
            wait(random(340, 1310));
        }

        try {
            Point screenLoc = null;
            for (int i = 0; i < 30; i++) {
                screenLoc = Calculations.tileToScreen(tile);
                if (!pointOnScreen(screenLoc)) {
                    return false;
                }               
                if(getMenuItems().get(0).toLowerCase().contains(action)) {
                        break;                 
                }                  
                if (getMouseLocation().equals(screenLoc)) {
                    break;
                }
                moveMouse(screenLoc);
            }
            screenLoc = Calculations.tileToScreen(tile);
            if (getMenuItems().size() <= 1) {
                return false;
            }
            if (getMenuItems().get(0).toLowerCase().contains(action)) {
                clickMouse(true);
                return true;
            } else {
                clickMouse(false);
                return atMenu(action);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
  public boolean myatTile(final RSTile tile , String action) {
      try {
        Point location = Calculations.tileToScreen(tile);
        if (location.x == -1 || location.y == -1)
          return false;
        moveMouse(location, 3, 3);
        wait(random(500, 1000));
        if (getMenuItems().get(0).toLowerCase().contains(
            action.toLowerCase())) {
          clickMouse(true);
          wait(random(1000, 2000));
        } else {
          clickMouse(false);
          if (!atMenu(action))
            return false;
        }
        wait(random(500, 1000));
        while (true) {
          if (!getMyPlayer().isMoving())
            break;
          wait(random(500, 1000));
        }
        return true;
      } catch (Exception e) {
        return false;
      }
    }


    public boolean attackNPC(final RSNPC npc) {
        final RSTile tile = npc.getLocation();
        tile.randomizeTile(1, 1);
        try {
            final int hoverRand = random(8, 13);
            for (int i = 0; i < hoverRand; i++) {
                if (distanceTo(tile) <= 1) {
                    return false;
                }
                final Point screenLoc = npc.getScreenLocation();
                if (!pointOnScreen(screenLoc)) {
                    turnToTile(npc.getLocation());
                    return true;
                }

                moveMouse(screenLoc, 15, 15);

                final java.util.List<String> menuItems = getMenuItems();
                if (menuItems.isEmpty() || menuItems.size() <= 1) {
                    continue;
                }
                if (menuItems.get(0).toLowerCase().contains(
                        npc.getName().toLowerCase())
                        && getMyPlayer().getInteracting() == null) {
                    clickMouse(true);
                    return true;
                } else {
                    for (int a = 1; a < menuItems.size(); a++) {
                        if (menuItems.get(a).toLowerCase().contains(
                                npc.getName().toLowerCase())
                                && getMyPlayer().getInteracting() == null) {
                            clickMouse(false);
                            return atMenu("Attack " + npc.getName());
                        }
                    }
                }
            }
        } catch (final Exception E) {
            return false;
        }
        return false;
    }

    public void moveMouse(Polygon p){
        try {
            moveMouse(p.getBounds());
        } catch(Exception E) {
            log(E.getMessage());
        }
    }

    public void moveMouse(Rectangle r) {
        try {
            int x = r.x + random(0, r.width);
            int y = r.y + random(0, r.height);
            moveMouse(x, y);
        } catch(Exception E) {
            log(E.getMessage());
        }
    }

    public boolean inArea(int lowX, int hiX, int lowY, int hiY) {
        int x = getMyPlayer().getLocation().getX();
        int y = getMyPlayer().getLocation().getY();
    return x >= lowX && x <= hiX && y >= lowY && y <= hiY;
    }

    private boolean clickInventoryItem(final int[] ids, final String command) {
    try {
      if (getCurrentTab() != Constants.TAB_INVENTORY
          && !RSInterface.getInterface(Constants.INTERFACE_BANK)
              .isValid()
          && !RSInterface.getInterface(Constants.INTERFACE_STORE)
              .isValid()) {
        openTab(Constants.TAB_INVENTORY);
      }
      final int[] items = getInventoryArray();
      final java.util.List<Integer> possible = new ArrayList<Integer>();
      for (int i = 0; i < items.length; i++) {
        for (final int item : ids) {
          if (items[i] == item) {
            possible.add(i);
          }
        }
      }
      if (possible.size() == 0) {
        return false;
      }
      final int idx = possible.get(random(0, possible.size()));
      final Point t = getInventoryItemPoint(idx);
      moveMouse(t, 5, 5);
      wait(random(100, 290));
      if (getMenuActions().get(0).equals(command)) {
        clickMouse(true);
        return true;
      } else {
        return atMenu(command);
      }
    } catch (final Exception e) {
      log("error clicking inventory");
      return false;
    }
  }

    public class clanChat extends Thread {

        @Override
    public void run() {
            if(continueChat) {
        String ccmessage = getLastClanChatMessage();
                String[] chars = {"=", "-","_","*","@","#"};
                for (String aChar : chars) {
                    ccmessage.replace(aChar, "");
                }
            if(!ccmessage.equals(lastCCMessage) && !ccmessage.equals("")) {
                    log(ccmessage);
              parseInComingData(ccmessage);
              lastCCMessage = ccmessage;
            }
                if(!inGame) {
                    if(random(0,30) == 20) {
                        antiBan(0,6);
                    }
                }
            }
    }
        private String getLastClanChatMessage() {
          for(int i = 155; i >= 56; i --) {
            String text = RSInterface.getInterface(INTERFACE_CHAT_BOX).getChild(i).getText();
            if (text.contains("<col=800000>")) {
              return text.substring(text.indexOf("<col=800000>") + 12);
            }
          }
          return "";
        }

        public void parseInComingData(String text) {
            String lower = text.toLowerCase();
            if (lower.contains("blue") || lower.contains("b l u e")) {
                callBlue += 1;
            } else if (lower.contains("red") || lower.contains("r e d")) {
                callRed += 1;
            }
        }
  }
}