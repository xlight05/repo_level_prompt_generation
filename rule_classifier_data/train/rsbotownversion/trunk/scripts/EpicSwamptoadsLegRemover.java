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


@ScriptManifest(authors = "Raiderboy51 , thesharp , Anarki", category = "Money-Making", name = "Epic Swamp toads' Leg Remover", version = 2.0, description = "<html><head>"
        + "</head><body style='font-family: Eurostile; margin: 10px;'>"
        + "<center><h2><b>Epic Swamp toads' Leg Remover v2.0</b></h2></center>"
        + "<center>Start the script near the Bank chest in Soul wars or start in the place which have banktooth will also work. Make sure that your Swamp toads are visible in the bank.</center>"
        + "<br><center><select name='Toads'><option>Swamp toads</option></center>"
        + "<br><center><b>Choose your speed, see topic for more info:</b></center>"
        + "<center><select    name='speed'><option>Elite</option><option>Fast</option><option>Regular</option><option>Slow</option><option>Get a new pc</option></center>"
        + "<br><center><b>Do You wish to enable antiban?</b>"
        + "<center><select name='anti'><option>Yes</option><option>No</option>"
        + "<br><center>Thanks for using my script!</center>")
public class EpicSwamptoadsLegRemover extends Script implements PaintListener,
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
    public int SwampToadID;
    public int ToadsLegsID;
    public long startTime = System.currentTimeMillis();
    public int amountCleaned;
    private boolean booleanOpen;
    private boolean antiban;
    double y =1;//y= speed

    public boolean onStart(Map<String, String> args) {
        log("Hope you're enjoying my script! ;D");
        final String Toads = args.get("Toads");
        final String SPEED = args.get("speed");
        final String ABAN = args.get("anti");
        startTime = System.currentTimeMillis();
        setCameraRotation(143);
        setCameraAltitude(true);
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
    //picking Toads ideas
        if (Toads.equals("Swamp toads")) {
            SwampToadID = 2150;
            ToadsLegsID = 2152;
        }
        return true;
    }

    @Override
    public int loop() {
        open();
        check();
        Banking();
        Removing();
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
    if (bank.isOpen() && bank.getCount(SwampToadID) < 28) {
    wait(random(1000,2000));
      log("You appear to be out of Swamp Toads. Will try again.");
    }
    if(bank.isOpen() && bank.getCount(SwampToadID) < 28) {
        log("Out of Swamp Toads, logging out.");
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
                bank.withdraw(SwampToadID, 0);
                wait(random(500, 600));
                wait((int) Math.floor(random(1500, 1700)*y));
                }
            bank.close();
            wait((int) Math.floor(random(75, 150)*y));
        }
        return false;
    }

    protected int getMouseSpeed() {
        return random(6, 8);
    }

    public boolean Removing() {
        try {
          if(getInventoryCount(SwampToadID)==28 && !bank.isOpen()) {
            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));

            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));

            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));

            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);

            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));
            moveMouse(random(691, 717), random(440, 440), true);
            clickMouse(true);
            wait(random(10, 30));
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
            int ToadsPerHour = 0;

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

            ToadsPerHour = (int) ((3600000.0 / (double) runTime) * amountCleaned);

            ((Graphics2D) g).setRenderingHints(rh);
            g.setColor(Color.WHITE);
            g.drawRoundRect(6, 344, 489, 114, 4, 4);
            g.setColor(new Color(51, 153, 0, 180));
            g.fillRoundRect(7, 345, 487, 112, 4, 4);
            g.setFont(new Font("Base 02", 0, 18));
            g.setColor(Color.WHITE);
            g.drawString("Epic Swamp toads' Leg Remover v2.0", 84, 367);
            g.setFont(new Font("Arial Black", 0, 12));
            g.setColor(Color.WHITE);
            g.drawString("Time running: " + hours + ":" + minutes + ":"
                    + seconds, 25, 385);
            g.setFont(new Font("Arial Black", 0, 12));
            g.setColor(Color.WHITE);
            g.drawString("Toads' leg removed: " + amountCleaned, 25, 410);
            g.setFont(new Font("Arial Black", 0, 12));
            g.setColor(Color.WHITE);
            g.drawString("Toads per hour: " + ToadsPerHour, 25, 435);
            g.setFont(new Font("Arial Black", 0, 10));
            g.setColor(Color.WHITE);
            g.drawString("Rewrite by Anarki", 245  , 452);
        }
    }

    @Override
    public void serverMessageRecieved(final ServerMessageEvent arg0) {
        final String serverString = arg0.getMessage();
        if (serverString.contains("Poor toad. At least they'll grow back.")) {
            amountCleaned++;
        }
    }
}