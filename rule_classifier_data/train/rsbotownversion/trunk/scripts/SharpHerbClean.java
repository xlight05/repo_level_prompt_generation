import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.Map;
import java.awt.Color;

import org.rsbot.bot.Bot;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSNPC;
import org.rsbot.script.wrappers.RSObject;


@ScriptManifest(authors = "Raiderboy51 & thesharp", category = "Herblore", name = "sharp HerbCleaner", version = 2, description = "<html><head>"
        + "</head><body style='font-family: Eurostile; margin: 10px;'>"
        + "<center><img src=\"http://img19.imageshack.us/img19/477/herbcleaner.png\" /></center>"
        + "<center>Start the script near the Bank chest in Soul wars. Make sure that your herbs are visible in the bank.</center>"
        + "<center><b>Choose your herb you want to clean:</b></center><center><select    name='Herb'><option>Guam</option><option>Marrentill</option><option>Tarromin</option><option>Harralander</option><option>Ranarr</option><option>Toadflax</option><option>Irit</option><option>Avantoe</option><option>Kwuarm</option><option>Snapdragon</option><option>Cadantine</option><option>Lantadyme</option><option>Dwarf Weed</option><option>Torstol</option></center>"
        + "<center><b>Choose your speed, see topic for more info:</b></center>"
        + "<br/><center><select    name='speed'><option>Elite</option><option>Fast</option><option>Regular</option><option>Slow</option><option>Get a new pc</option></center>"
        + "<center><b>Do You wish to enable antiban?</b>"
        + "<br/><center><select name='anti'><option>Yes</option><option>No</option>"
        + "<center>Thanks for using my script!</center>")
public class SharpHerbClean extends Script implements PaintListener,
        ServerMessageListener {

    // VARIABLES
    private int CHEST[] = { 27663, 4483, 12308, 21301, 42192 };
    private int BANKER[] = { 7605, 6532, 6533, 6534, 6535, 5913, 5912, 2271,
            14367, 3824, 44, 45, 2354, 2355, 499, 5488, 8948, 958, 494, 495,
            6362, 5901 };
    private int BANKBOOTH[] = { 11758, 11402, 34752, 35647, 2213, 25808, 2213,
            26972, 27663, 4483, 14367, 19230, 29085, 12759, 6084 };
    // RSVARIABLES
    private RSObject bankBooth = getNearestObjectByID(BANKBOOTH);
    private RSObject bankChest = getNearestObjectByID(CHEST);
    private RSNPC banker = getNearestNPCByID(BANKER);
    public int GrimyHerbID;
    public int CleanHerbID;
    public long startTime = System.currentTimeMillis();
    public int amountCleaned;
    private boolean booleanOpen;
    private boolean antiban;
    private int startXP = 0 ;
    private int startLvl = 0;
    double y =1;//y= speed

    public boolean onStart(Map<String, String> args) {
        log("Hope you're enjoying my script! ;D");
        final String Herb = args.get("Herb");
        final String SPEED = args.get("speed");
        final String ABAN = args.get("anti");
        startXP = skills.getCurrentSkillExp(STAT_HERBLORE);
        startLvl = skills.getCurrentSkillLevel(STAT_HERBLORE);
        startTime = System.currentTimeMillis();
        //enable or disable anti ban
        if(ABAN.equals("Yes"))
          antiban = true;
        else
          antiban = false;
    //setting speed values
    if(SPEED.equals("Elite")) {
    y=1;
    } else if(SPEED.equals("Fast")) {
    y=1.3;
    } else if(SPEED.equals("Regular")){
    y=1.7;
    } else if(SPEED.equals("Slow")){
    y=2;
    } else {
    y=2.3;
    }
    //picking herb ideas
        if (Herb.equals("Guam")) {
            GrimyHerbID = 199;
            CleanHerbID = 249;
        }

        if (Herb.equals("Marrentill")) {
            GrimyHerbID = 201;
            CleanHerbID = 251;
        }

        if (Herb.equals("Tarromin")) {
            GrimyHerbID = 203;
            CleanHerbID = 253;
        }

        if (Herb.equals("Harralander")) {
            GrimyHerbID = 205;
            CleanHerbID = 255;
        }

        if (Herb.equals("Ranarr")) {
            GrimyHerbID = 207;
            CleanHerbID = 257;
        }

        if (Herb.equals("Toadflax")) {
            GrimyHerbID = 3049;
            CleanHerbID = 2998;
        }

        if (Herb.equals("Irit")) {
            GrimyHerbID = 209;
            CleanHerbID = 259;
        }

        if (Herb.equals("Avantoe")) {
            GrimyHerbID = 211;
            CleanHerbID = 261;
        }

        if (Herb.equals("Kwuarm")) {
            GrimyHerbID = 213;
            CleanHerbID = 263;
        }

        if (Herb.equals("Snapdragon")) {
            GrimyHerbID = 3051;
            CleanHerbID = 3000;
        }

        if (Herb.equals("Cadantine")) {
            GrimyHerbID = 215;
            CleanHerbID = 265;
        }

        if (Herb.equals("Lantadyme")) {
            GrimyHerbID = 2485;
            CleanHerbID = 2461;
        }

        if (Herb.equals("Dwarf Weed")) {
            GrimyHerbID = 217;
            CleanHerbID = 267;
        }

        if (Herb.equals("Torstol")) {
            GrimyHerbID = 219;
            CleanHerbID = 269;
        }
        return true;
    }

    @Override
    public int loop() {
        open();
        check();
        Banking();
        Cleaning();
        if(antiban) {
        antiban();
        }
        return (int) Math.floor(random(100, 200)*y);
    }

    public boolean open() {
      booleanOpen = false;
      while(booleanOpen ==false){
        if (bankBooth != null && !bank.isOpen()) {
            atObject(bankBooth, "Use-quickly ");
            wait(random(400, 500));
            wait((int)Math.floor(random(600, 1000)*y));
       booleanOpen=true;
        } else if (bankChest != null && !bank.isOpen()) {
            atObject(bankChest, "Use ");
            wait(random(400, 500));
            wait((int)Math.floor(random(600, 1000)*y));
            booleanOpen=true;
        } else {
            atNPC(banker, "Bank ");
            wait(random(400, 500));
            wait((int) Math.floor(random(600, 1000)*y));
            booleanOpen=true;
        }
  }

        return true;

    }

    public boolean check() {
      wait(random(200,300));
    if (bank.isOpen() && bank.getCount(GrimyHerbID) < 28) {
    wait(random(1000,2000));
      log("You appear to be out of herbs. Will try again.");
    }
    if(bank.isOpen() && bank.getCount(GrimyHerbID) < 28) {
        log("Out of herbs, logging out.");
        bank.close();
        moveMouse(755, 13);
        wait(random(700, 800));
        atMenu("Exit");
        moveMouse(755, 13);
        moveMouse(random(575, 699), random(408, 427));
        wait(random(700, 800));
        atMenu("Exit to Login");
        wait(random(2000,3000));
        stopScript();
    }
    return false;
    }


    public boolean Banking() {
        if (bank.isOpen()) {
          if(getInventoryCount()>0) {
            bank.depositAll();
            wait(random(300, 400));
            wait((int) Math.floor(random(700, 900)*y));
          }
            while(getInventoryCount() != 28) {
                bank.withdraw(GrimyHerbID, 0);
                wait(random(500, 600));
                wait((int) Math.floor(random(1200, 1700)*y));
                }
            bank.close();
            wait((int) Math.floor(random(75, 150)*y));
        }
        return false;
    }

    protected int getMouseSpeed() {
        return random(6, 8);
    }

    public boolean Cleaning() {
        try {
          if(getInventoryCount(GrimyHerbID)==28 && !bank.isOpen()) {
            moveMouse(random(563, 590), random(226, 226), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(563, 590), random(263, 263), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(563, 590), random(298, 298), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(563, 590), random(335, 335), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(563, 590), random(370, 370), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(563, 590), random(405, 405), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(563, 590), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));

            moveMouse(random(610, 633), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(610, 633), random(405, 405), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(610, 633), random(370, 370), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(610, 633), random(335, 335), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(610, 633), random(298, 298), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(610, 633), random(263, 263), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(610, 633), random(226, 226), true);
            clickMouse(true);
            wait(random(10, 30));

            moveMouse(random(647, 675), random(226, 226), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(647, 675), random(263, 263), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(647, 675), random(298, 298), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(647, 675), random(335, 335), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(647, 675), random(370, 370), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(647, 675), random(405, 405), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(647, 675), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));

            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(405, 405), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(370, 370), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(335, 335), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(298, 298), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(263, 263), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(226, 226), true);
            clickMouse(true);
            wait(random(700, 800));
          }
    } catch (Exception e) {
       log("Mouse event fucked up. The error is now cought and your script will continue.");
       log(" If it would stop after this error inform me");
       if (bank.isOpen()) {
            bank.depositAll();
                wait(random(600, 700));
                wait((int) Math.floor(random(200, 400)*y));
       }
    }
        return true;
    }

    public void antiban() {
      switch(random(0, 1800)) {
    case 0:
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
      setCameraRotation(getCameraAngle()+random(-30, 30));break;
    case 6:
    case 7:
    case 8:
    case 10:
    case 11:
    case 12:
    case 13:
    case 14:
    case 15:
      setCameraRotation(getCameraAngle()+random(-15, 60));break;
    case 17:
    case 18:
    case 19:
    case 20:
    case 21:
      moveMouseSlightly();
    break;
    case 22:
    case 23:
    case 24:
    case 25:
    case 26:
      moveMouse(566, 180, true);
          clickMouse(true);
          wait(random(25, 40));
          moveMouse(random(620,660),random(270,280));
          break;
    default:
    break;
    }
    }

   
private final RenderingHints rh = new RenderingHints(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    public void drawMouse(final Graphics g) {
        final Point loc = getMouseLocation();
        //Color WHITE = new Color(255, 255, 255);
        if (System.currentTimeMillis() - Bot.getClient().getMouse().getMousePressTime() < 500) {
            g.setColor(Color.WHITE);
            g.fillRect(loc.x - 3, loc.y - 3, 5, 5);
            g.fillRect(loc.x - 7, loc.y - 1, 17, 5);
            g.fillRect(loc.x - 1, loc.y - 7, 5, 17);
        }
        g.setColor(Color.WHITE);
        g.fillRect(loc.x - 6, loc.y, 15, 3);
        g.fillRect(loc.x, loc.y - 6, 3, 15);
    }



    public void onRepaint(Graphics g) {

        if (isLoggedIn()) {
            drawMouse(g);
            long runTime = 0;
            long seconds = 0;
            long minutes = 0;
            long hours = 0;
            int gainedXP = 0;
            int gainedLVL = 0;
            int xpPerHour = 0;
            int herbsPerHour = 0;

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

            gainedXP = skills.getCurrentSkillExp(STAT_HERBLORE) - startXP;
            gainedLVL = skills.getCurrentSkillLevel(STAT_HERBLORE) - startLvl;
            xpPerHour = (int) ((3600000.0 / (double) runTime) * gainedXP);
            herbsPerHour = (int) ((3600000.0 / (double) runTime) * amountCleaned);

            ((Graphics2D) g).setRenderingHints(rh);
            g.setColor(Color.WHITE);
            g.drawRoundRect(6, 344, 489, 114, 4, 4);
            g.setColor(new Color(51, 153, 0, 180));
            g.fillRoundRect(7, 345, 487, 112, 4, 4);
            g.setFont(new Font("Base 02", 0, 18));
            g.setColor(Color.WHITE);
            g.drawString("HerbCleaner v2.3", 171, 367);
            g.setFont(new Font("Arial Black", 0, 12));
            g.setColor(Color.WHITE);
            g.drawString("Time running: " + hours + ":" + minutes + ":"
                    + seconds, 25, 385);
            g.setFont(new Font("Arial Black", 0, 12));
            g.setColor(Color.WHITE);
            g.drawString("Herbs cleaned: " + amountCleaned, 25, 410);
            g.setFont(new Font("Arial Black", 0, 12));
            g.setColor(Color.WHITE);
            g.drawString("Exp gained: " + gainedXP+ "  (" + (gainedLVL)+ ")", 25, 434);
            g.setFont(new Font("Arial Black", 0, 12));
            g.setColor(Color.WHITE);
            g.drawString("Exp per hour: " + xpPerHour, 250, 435);
            g.setFont(new Font("Arial Black", 0, 12));
            g.setColor(Color.WHITE);
            g.drawString("Herbs per hour: " + herbsPerHour, 250, 410);
            g.setFont(new Font("Arial Black", 0, 12));
            g.setColor(Color.WHITE);
            g.drawString("Current level: "
                    + skills.getCurrentSkillLevel(STAT_HERBLORE), 250, 385);
            g.setFont(new Font("Arial Black", 0, 10));
            g.setColor(Color.WHITE);
            g.drawString("by Raiderboy, modified by Thesharp", 250  , 452);
        }
    }

    @Override
    public void serverMessageRecieved(final ServerMessageEvent arg0) {
        final String serverString = arg0.getMessage();
        if (serverString.toLowerCase().contains("clean the dirt from the")) {
            amountCleaned++;
        }
    }
}