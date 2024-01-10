import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Map;
import org.rsbot.bot.Bot;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.script.Calculations;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.Skills;
import org.rsbot.script.wrappers.RSInterface;
import org.rsbot.script.wrappers.RSTile;
@ScriptManifest(authors = { "Garrett" }, category = "Agility", name = "Winner's Barbarian Agility", version = 1.6, description = 
 "<html><head>" + 
 "</head><body>" + 
 "<center><strong><h2>Garrett's Barbarian Agility</h2></strong></center>" + 
 "<strong>Start at the Barbarian Agility Course at the Rope Swing</strong><br />" + 
 "<strong>Improved/fixed by Bloddyharry And Refixed by The_winner88</strong><br><br>" + 
 "Food ID: <input name='FOODID' type='text' width='10' value='7946' />" + 
 "Eat Food Between: <input name='HEALTH1' type='text' width='3' value='200' /> - <input name='HEALTH2' type='text' width='3' value='300' /> HP<br><br>" + 
"<strong>When running this you should be using max cpu, RESIZABLE screen NOT FIXED-fixed causes rope errors, and have all other settings at minimum</strong>" + 
 "</body></html>")
public class WinnersBarbAgi extends Script implements PaintListener {
 final ScriptManifest properties = getClass().getAnnotation(ScriptManifest.class);
 
 //OTHER VARIABLES
 private long scriptStartTime = 0;
 private int runEnergy = random(40, 95);
 private int randomHealth = random(150, 250);
 private boolean setAltitude = true;
 private int FOODID;
 private int HEALTH1;
 private int HEALTH2;
 private int startXP = 0;
 private int startLvl = 0;
 public String status = "";
 private enum State { rope, log, net, ledge, ladder, wall, error; };
 
 protected int getMouseSpeed() {
        return random(5, 7);
     }
 
 private State getState() {
  if (!inventoryContains(FOODID) && getCurrentLifePoints() < randomHealth) {
   log("You do not have any food in your inventory and your health is low.");
   return State.error;
  }
  if (playerInArea(2555, 3559, 2543, 3550))
   return State.rope;
  if (playerInArea(2553, 3549, 2544, 3542))
   return State.log;
  if (playerInArea(2542, 3547, 2533, 3545) && getPlane() == 0)
   return State.net;
  if (playerInArea(2538, 3547, 2536, 3545) && getPlane() == 1)
   return State.ledge;
  if (playerInArea(2532, 3547, 2532, 3546) && getPlane() == 1)
   return State.ladder;
  if (playerInArea(2537, 3551, 2532, 3548) || (playerInArea(2532, 3549, 2532, 3546) && getPlane() == 0) || playerInArea(2542, 3556, 2532, 3550))
   return State.wall;
  return State.rope;
 }
 public int getCurrentLifePoints() {
  return Integer.parseInt(RSInterface.getInterface(748).getChild(8).getText());  
 }
 
    //*******************************************************//
    // ON START
    //*******************************************************//
    public boolean onStart( Map<String,String> args ) {
     FOODID = Integer.parseInt(args.get("FOODID"));
     HEALTH1 = Integer.parseInt(args.get("HEALTH1"));
     HEALTH2 = Integer.parseInt(args.get("HEALTH2"));
     randomHealth = random(HEALTH1, HEALTH2);
     scriptStartTime = System.currentTimeMillis();
     return true;
    }
    //*******************************************************//
    // MAIN LOOP
    //*******************************************************//
    public int loop() {
     if (!isLoggedIn())
      return 50;
     if (startLvl == 0) {
      startXP = skills.getCurrentSkillExp(Skills.getStatIndex("agility"));
      startLvl = skills.getCurrentSkillLevel(Skills.getStatIndex("agility"));
      return 50;
     }
     if (setAltitude) {
      setCameraAltitude(true);
      wait(random(250, 500));
      setAltitude = false;
      return 50;
     }
     if (getCurrentLifePoints() < randomHealth) {
   leftClickInventoryItem(FOODID);
   randomHealth = random(HEALTH1, HEALTH2);
   wait(random(600, 800));
   return 50;
  }
     startRunning(runEnergy);
     switch(getState()) {
  case rope:
   doRope();
   return 50;
  case log:
   doLog();
   return 50;
  case net:
   doNet();
   return 50;
  case ledge:
   doLedge();
   return 50;
  case ladder:
   doLadder();
   return 50;
  case wall:
   doWall();
   return 50;
  case error: 
   return -1;
  }
 
        return 50;
    }
 
 
    //*******************************************************//
    // OTHER METHODS
    //*******************************************************//
    private void doRope() {
     status = "Rope";
     final RSTile rope = new RSTile(2551 + random(0, 2), 3553);
     final RSTile ladder = new RSTile(2547, 9951);
     final RSTile walkHere = new RSTile(2551, 3554);
     if (playerInArea(2555, 9955, 2546, 9948)) {
      if (getCameraAngle() < 85 || getCameraAngle() > 95) {
       setCameraRotation(random(85, 95));
       wait(random(100, 200));
       return;
      }
   if (onTile(ladder, "Ladder", "Climb-up", 0.4, 0.4, 40))
    log("oh crap, We fell off the rope :/");
    wait(random(3000, 3700));
   return;
     }
     if (!playerInArea(2554, 3555, 2549, 3554)) {
   walkTile(walkHere);
   wait(random(50, 500));
   return;
  }
     setCompass('s');
     if (onTile(rope, "Ropeswing", "Swing-on", 0.5, 0, 450))
      wait(random(500, 1000));
      moveMouse(random(50, 700), random(50, 450), 2, 2);
      wait(random(1600, 1800));
     while(getMyPlayer().getAnimation() == 751 || getMyPlayer().isMoving())
   wait(100);
     return;
    }
 
    private void doLog() {
     status = "Log";
     final RSTile log = new RSTile(2550, 3546);
     if (onTile(log, "Log balance", "Walk-across", 0.5, 0.4, 0))
      wait(random(100, 200));
      setCameraRotation(random(1, 360));
      wait(random(100, 300));
      moveMouse(random(50, 700), random(50, 450), 2, 2);
            wait(random(200, 700));
            moveMouse(random(50, 700), random(50, 450), 2, 2);
      wait(random(1300, 1600));
 setCompass('w');
     while(getMyPlayer().isMoving() || playerInArea(2550, 3546, 2542, 3546))
   wait(100);
     return;
    }
    private void doNet() {
     status = "Net";
     final RSTile net = new RSTile(2537, 3546);
 final RSTile walkHere = new RSTile(2539, 3546);
 if (getPlane() == 0 && playerInArea(2538, 3547, 2533, 3545)) {
   walkTile(walkHere);
   wait(random(350, 450));
   while(getMyPlayer().isMoving())
   wait(100);
   return;
  }
     if (onTile(net, "Obstacle net", "Climb-over", random(0.81, 0.61), 0, 200))
      wait(random(1800, 2500));
     while(getMyPlayer().getAnimation() == 828 || getMyPlayer().isMoving())
   wait(100);
     return;}
    private void doLedge() {
     status = "Ledge";
     final RSTile ledge = new RSTile(2535, 3547);
     if (onTile(ledge, "Balancing ledge", "Walk-across", 0.5, 0.75, 0))
      wait(random(200, 700));
      moveMouse(random(50, 700), random(50, 450), 2, 2);
      wait(random(1400, 2000));
     while(getMyPlayer().isMoving() || (playerInArea(2535, 3547, 2532, 3547) && getPlane() == 1))
   wait(100);
     return;
    }
 
    private void doLadder() {
     status = "Laddder";
     final RSTile ladder = new RSTile(2532, 3545);
     if (onTile(ladder, "Ladder", "Climb-down", 0.5, 0.6, 0))
      wait(random(50, 200));
     setCameraRotation(random(180, 240));
      moveMouse(random(50, 700), random(50, 450), 2, 2);
      wait(random(1000, 1400));
     while(getMyPlayer().isMoving() || getMyPlayer().getAnimation() == 827)
   wait(100);
     return;
    }
 
    private void doWall() {
     status = "Wall";
     if (getMyPlayer().isMoving())
      return;
     final RSTile wall1 = new RSTile(2537, 3553);
     final RSTile wall2 = new RSTile(2542, 3553);
     final RSTile walkHere = new RSTile(2535, 3552);
 final RSTile walkHere2 = new RSTile(2540, 3553);
 if (!tileOnScreen(wall1)) {
      walkTile(walkHere);
      wait(random(500, 600));
      return;
     }
     if (playerInArea(2542, 3554, 2538, 3552)) {
 if (!tileOnScreen(wall2)) {
  walkTile(walkHere2);
  wait(random(800, 900));
  return;
 } 
 if (onTile(wall2, "Crumbling wall", "Climb-over", 0.90, 0.50, 5))
       wait(random(500, 1500));
       log("Succesfully Finished a round.");
      while(getMyPlayer().isMoving() || getMyPlayer().getAnimation() == 4853)
       wait(100);
      return;
     }
 
 if (onTile(wall1, "Crumbling wall", "Climb-over", 0.9, 0.5, 5))
      wait(random(200, 600));
      moveMouse(random(50, 700), random(50, 450), 2, 2);
   wait(random(1000, 1100));
  while(getMyPlayer().isMoving() || getMyPlayer().getAnimation() == 4853)
   wait(150);
     return;
    }
 
    private void startRunning(final int energy) {
  if (getEnergy() >= energy && !isRunning()) {
   runEnergy = random(40, 95);
   setRun(true);
   wait(random(500, 750));
  }
 }
 
    private boolean playerInArea(int maxX, int maxY, int minX, int minY) { 
  int x = getMyPlayer().getLocation().getX(); 
  int y = getMyPlayer().getLocation().getY(); 
  if (x >= minX && x <= maxX && y >= minY && y <= maxY) 
  { 
   return true; 
  } 
  return false; 
 }
 
    public boolean leftClickInventoryItem(int itemID) {
     if(getCurrentTab() != TAB_INVENTORY)
      return false;
  int[] items = getInventoryArray();
  java.util.List<Integer> possible = new ArrayList<Integer>();
  for (int i = 0; i < items.length; i++) {
   if (items[i] == itemID) {
    possible.add(i);
   }
  }
  if (possible.size() == 0) return false;
  int idx = possible.get(possible.size() - 1);
  Point t = getInventoryItemPoint(idx);
  clickMouse(t, 5, 5, true);
  return true;
 }
 
    private int getHealth() {
     try {
      return Integer.parseInt(RSInterface.getChildInterface(748, 8).getText());
     } catch(Exception e) { return 99; }
    }
 
    public boolean onTile(RSTile tile, String search, String action, double dx, double dy, int height) {
        if (!tile.isValid()) {
            return false;
        }
 
        Point checkScreen = null;
        checkScreen = Calculations.tileToScreen(tile, dx, dy, height);
        if (!pointOnScreen(checkScreen)) {
         walkTile(tile);
            wait(random(340, 1310));
        }
        try {
            Point screenLoc = null;
            for (int i = 0; i < 30; i++) {
                screenLoc = Calculations.tileToScreen(tile, dx, dy, height);
                if (!pointOnScreen(screenLoc)) {
                    return false;
                }               
                if(getMenuItems().get(0).toLowerCase().contains(search.toLowerCase())) {
                        break;                 
                }                  
                if (getMouseLocation().equals(screenLoc)) {
                    break;
                }
                moveMouse(screenLoc);
            }
            screenLoc = Calculations.tileToScreen(tile, height);
            if (getMenuItems().size() <= 1) {
                return false;
            }
            wait(random(100, 200));
            if (getMenuItems().get(0).toLowerCase().contains(action.toLowerCase())) {
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
 
    private void walkTile(final RSTile tile) {
  if (!(distanceTo(getDestination()) <= random(4, 7))) {
   if (getMyPlayer().isMoving())
    return;
  }
     Point screen = Calculations.tileToScreen(tile);
     if (pointOnScreen(screen)) {
      if (getMyPlayer().isMoving())
    return;
         moveMouse(screen, random(-3, 4), random(-3, 4));
         onTile(tile, "here", "alk");
         wait(random(500, 750));
            return;
        } else {
         walkTileMM(tile);
         wait(random(500, 750));
         return;
        }
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
        long runTime = 0;
        long seconds = 0;
        long minutes = 0;
        long hours = 0;
        int laps = 0;
        int currentXP = 0;
        int currentLVL = 0;
        int gainedXP = 0;
        int gainedLVL = 0;
        int lapsPerHour = 0;
        final double courseXP = 153.2;
 
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
 
     currentXP = skills.getCurrentSkillExp(Skills.getStatIndex("agility"));
     currentLVL = skills.getCurrentSkillLevel(Skills.getStatIndex("agility"));
     gainedXP = currentXP - startXP;
     gainedLVL = currentLVL - startLvl;
     laps = (int) (gainedXP / courseXP);
     lapsPerHour = (int) ((3600000.0 / (double) runTime) * laps);
 
        if(getCurrentTab() == TAB_INVENTORY) {
         g.setColor(new Color(0, 0, 0, 175)); 
         g.fillRoundRect(555, 210, 175, 250, 10, 10);
         g.setColor(Color.RED);
         int[] coords = new int[] {225, 240, 255, 270, 285, 300, 315, 330, 345, 360, 375, 390, 405, 420, 435, 450};
         g.drawString(properties.name(), 561, coords[0]);
         g.drawString("Version: " + properties.version(), 561, coords[1]);
         g.drawString("Run Time: " + hours + ":" + minutes + ":" + seconds, 561, coords[2]);
         g.drawString("Total Laps: " + laps, 561, coords[4]);
         g.drawString("Laps/Hour: " + lapsPerHour, 561, coords[5]);
         g.drawString("Current Lvl: " + currentLVL, 561, coords[7]);
         g.drawString("Lvls Gained: " + gainedLVL, 561, coords[8]);
         g.drawString("XP Gained: " + gainedXP, 561, coords[9]);
         g.drawString("XP To Next Level: " + skills.getXPToNextLevel(Skills.getStatIndex("agility")), 561, coords[10]);
         g.drawString("% To Next Level: " + skills.getPercentToNextLevel(Skills.getStatIndex("agility")), 561, coords[11]);
         g.drawString("Status: " + status, 561, coords[12]);
        }
 }
}