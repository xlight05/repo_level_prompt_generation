import java.awt.*;
import org.rsbot.script.*;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSInterface;
import org.rsbot.script.wrappers.RSNPC;
import org.rsbot.script.wrappers.RSTile;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.event.listeners.*;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.bot.Bot;
import java.util.Map;
import java.awt.event.KeyEvent;
 
@ScriptManifest(authors = "KrisKros", category = "Hunter", name = "KrisKros Red Sals", version = 1.00, description = 
("<html><head>"+
"</head><body>"
+ "<center><strong><h2>" + "KrisKros Salamander Hunter" + " v" + 1.00 + "</h2></strong></center>"
+ "<br><br><br><br>"
+ "Max Walking Point <input type=\"text\" name=\"MAXDIST\" value=\"9\"/><br><br>"
+ "I Will Hunt : <br><select name=\"TYPE\"><option selected>Red Sals<br><br>"
//+ "<center>Debugging <br><select name=\"DEBUG\"><option selected>Yes<option>No</select> <br><br>"
+ "</center>"
+ "</body></html>"))
public class kkredsal extends Script implements PaintListener, ServerMessageListener {
int ropeID = 954;
int netID = 303;
int redSalamander = 10147;
int salamanderID = 10147;
int caughtTrapID = 19659;
int treeSetID = 19662;
int treeUnsetID = 19663;
int setAnim = 5215;
int equipSets = 2;
int maxtraps = 3;
int hunterLevel = 49;
int startingExp = skills.getCurrentSkillExp(STAT_HUNTER);
int startingLevel = skills.getCurrentSkillLevel(STAT_HUNTER);
int gainedExp = 0;
int trapcount = 0;
int maxDist = 9;
RSTile startingTile = new RSTile(0,0);
RSTile traplocs[] = new RSTile[5];
char left = KeyEvent.VK_LEFT;
char right = KeyEvent.VK_LEFT;
char down = KeyEvent.VK_DOWN;
char up = KeyEvent.VK_UP;
boolean haveRopeAndNet = false;
boolean debug = false;
long starttime;
AntiBan antiban = new AntiBan();
Thread t = new Thread(antiban);
RandUD randUD = new RandUD();
Thread t2 = new Thread(randUD);
public boolean needToFetch = false;
boolean gettingGlove = false;
public boolean onStart(Map<String, String> args) {
Bot.getEventManager().registerListener(this);
starttime = System.currentTimeMillis();
startingTile = getMyPlayer().getLocation();
maxDist = Integer.parseInt(args.get("MAXDIST"));
if(args.get("TYPE").contains("Red Sals"))
{
salamanderID = redSalamander;
}
else
{
return false;
}
// if (args.get("DEBUG").equals("Yes")) {
// debug = true;
// } else if (args.get("DEBUG").equals("No")) {
// debug = false;
// }
equipSets = checkEquipment();
maxtraps = checkMaxTraps();
hunterLevel = skills.getCurrentSkillLevel(STAT_HUNTER);
startingLevel = skills.getCurrentSkillLevel(STAT_HUNTER);
traplocs[0] = new RSTile(0,0);
traplocs[1] = new RSTile(0,0);
traplocs[2] = new RSTile(0,0);
traplocs[3] = new RSTile(0,0);
traplocs[4] = new RSTile(0,0);
return true;
} 
public void onFinish() {
Bot.getEventManager().removeListener(PaintListener.class, this);
Bot.getEventManager().removeListener(ServerMessageListener.class, this);
}
public int loop() {
 
//random 1:30 chance of antiban
if(random(0,18) == 1)
{
antiban();
return(random(800, 1000));
}
if(getMyPlayer().isMoving() || getMyPlayer().getAnimation() == setAnim)
{
return(random(800, 1000));
}
//check inventory full
if (getInventoryCount() >= 26) {
releaseAll();
return(random(800, 1000));
}
//check energy
if (getEnergy() >= random(65, 100) && !isRunning()) {
setRun(true);
return(random(300, 500));
}
//check for ropes or nets
if(getNearestGroundItemByID(ropeID) != null || getNearestGroundItemByID(netID) != null)
{
RSTile tempRope = getNearestGroundItemByID(ropeID);
RSTile tempNet = getNearestGroundItemByID(netID);
if(getNearestGroundItemByID(ropeID) != null)
{
if(debug)
log("Trying to pick up Rope");
if(!atTile(tempRope, "Take"))
{
turnCamera(tempRope);
wait(random(600,1200));
if(!atTile(tempRope, "Take"))
{
if(debug)
log("Walking to Target");
walkTileMM(new RSTile(tempRope.getX() + random(-3,3),tempRope.getY() + random(-3,3)));
}
}
}
else if(getNearestGroundItemByID(netID) != null)
{
if(debug)
log("Trying to pick up Net");
if(!atTile(tempNet, "Take"))
{
turnCamera(tempNet);
wait(random(600,1200));
if(!atTile(tempNet, "Take"))
{
if(debug)
log("Walking to Target");
walkTileMM(new RSTile(tempNet.getX() + random(-3,3),tempNet.getY() + random(-3,3)));
}
}
}
return(random(1600,2700));
}
//check for traps
if(getNearestObjectByID(caughtTrapID) != null)
{
RSObject tempTrap = getNearestObjectByID(caughtTrapID);
if(tempTrap != null)
{
if(!atObject(tempTrap,"Check"))
{
if(debug)
log("failed to get trap, trying to walk to it.");
//randomise where we click
wait(random(600,1000));
if(!atObject(tempTrap,"Check"))
{
turnCamera(tempTrap.getLocation());
wait(random(600,1200));
if(!atObject(tempTrap,"Check"))
{
if(debug)
log("Walking to Target");
walkTileMM(new RSTile(tempTrap.getLocation().getX() + 
random(-3,3),tempTrap.getLocation().getY() + random(-3,3)));
}
}
}
}
return(random(1200,2600));
}
//check for empty trees if we have more nets and rope
if(getNearestObjectByID(treeUnsetID) != null)
{
int temptrapcount = 0;
for(int i = 0; i < 5; i++)
{
if(traplocs[i].getX() != 0 && traplocs[i].getY() != 0)
{
int obj = getObjectAt(traplocs[i]).getID();
if(obj == treeUnsetID)
{
traplocs[i] = new RSTile(0,0);
 
}
else
{
temptrapcount++;
}
 
}
}
trapcount = temptrapcount;
if((trapcount < maxtraps) && (trapcount < equipSets))
{
RSObject targettree = getNearestObjectByID(treeUnsetID);
int distTo = distanceBetween(new 
RSTile(startingTile.getX(),startingTile.getY()),targettree.getLocation());
if(debug)
log("distance between starting tile and target: " + distTo);
 
if(distTo < maxDist)
{
 
if(atObject(targettree,"Set-trap"))
{
for(int i = 0; i < 5; i++)
{
if(traplocs[i] != new RSTile(0,0))
{
traplocs[i] = targettree.getLocation();
i = 5;
}
}
}
else
{
turnCamera(targettree.getLocation());
wait(random(600,1200));
if(!atObject(targettree,"Set-trap"))
{
if(debug)
log("Walking to Target");
walkTileMM(new RSTile(targettree.getLocation().getX() + 
random(-3,3),targettree.getLocation().getY() + random(-3,3)));
}
}
}
else
{
walkTileMM(startingTile);
}
return random(1200,2400);
}
}
//antiban
if(random(0,50) == 1)
{
antiban();
return(random(800, 1000));
}
return(random(600,800));
}
public void releaseAll() {
if (getCurrentTab() != Constants.TAB_INVENTORY) {
openTab(Constants.TAB_INVENTORY);
wait(random(500, 700));
}
boolean allDropped;
do{
allDropped = true;
if(debug)
log("inventory count: " + getInventoryCount(salamanderID));
if(getInventoryCount(salamanderID) > 0)
{
atInventoryItem(salamanderID, "Release");
allDropped = false;
wait(random(500, 700));
}
}while(!allDropped);
if(debug)
log("finished dropping");
}
public int checkMaxTraps()
{
int maxTrapCount = 0;
if(hunterLevel < 40)
maxTrapCount = 2;
if(hunterLevel >= 40 && hunterLevel< 60)
maxTrapCount = 3;
if(hunterLevel >= 60 && hunterLevel< 80)
maxTrapCount = 4;
if(hunterLevel >= 80)
maxTrapCount = 5;
return maxTrapCount;
}
public int checkEquipment()
{
int equipCount = 0;
int ropes = getInventoryCount(ropeID);
int nets = getInventoryCount(netID);
if(ropes == nets)
{
equipCount = ropes;
}
else if(ropes > nets)
{
equipCount = nets;
}
else
{
equipCount = ropes;
}
return equipCount;
}
public void turnCamera(RSTile target)
{
if(debug)
log("Trying to face object");
// find which direction the taregt tile is
int myX = getMyPlayer().getLocation().getX();
int myY = getMyPlayer().getLocation().getY();
int targX = target.getX();
int targY = target.getY();
int targYaw = 0;
int dircount = 0;
 
boolean isNorth = false;
boolean isEast = false;
int min = 0;
int max = 0;
 
if(myX < targX)
{
isNorth = true;
dircount++;
}
if(myY < targY)
{
isEast = true;
dircount += 2;
}
 
switch(dircount)
{
case 0:
//SouthWest ~ 8140 - 12210
min = 8140;
max = 12210;
targYaw = 10175;
break;
case 1:
//NorthWest ~ 12210 - 16280
min = 12210;
max = 16280;
targYaw = 14245;
break;
case 2:
//SouthEast ~ 4070 - 8140
min = 4070;
max = 8140;
targYaw = 6105;
break;
case 3:
//NorthEast ~ 0 - 4070
min = 0;
max = 4070;
targYaw = 2035;
break;
default:
log("switch statement failed");
break;
}
 
// find which is better to turn left or right
int distleft = 0;
int distright = 0;
int currYaw = Bot.getClient().getCameraYaw();
randUD.run();
if(targYaw < currYaw)
{
distleft = currYaw - targYaw;
distright = targYaw + (16280 - currYaw);
}
else
{
distright = targYaw - currYaw;
distleft = currYaw + (16280 - targYaw);
}
 
 
char turnkey = right;
if(distleft < distright)
{
turnkey = left;
}
else
{
turnkey = right;
}
// random chance to turn the longer way cause we are human ofcourse
if(random(0,20) == 0)
{
if(turnkey == right)
turnkey = left;
else
turnkey = right;
}
//turn go go
if(debug)
log("starting turn" + turnkey);
wait(random(0,500));
Bot.getInputManager().pressKey(turnkey);
if(debug)
log("rotation " + Bot.getClient().getCameraYaw() + " : " + min + "-" + max);
while(Bot.getClient().getCameraYaw() > max || Bot.getClient().getCameraYaw() < min)
{
if(debug)
log("looping in turncamera function" + Bot.getClient().getCameraYaw());
wait(random(200,500));
}
Bot.getInputManager().releaseKey(turnkey);
if(debug)
log("stopping turn" + turnkey);
}
public void antiban()
{
 
t.run();
int randnum = random(1,20);
if(randnum == 4)
{
if (getCurrentTab() != Constants.TAB_STATS)
openTab(Constants.TAB_STATS);
wait(random(500,800));
for(int i = 0; i < 3; i++)
{
clickMouse(random(722, 734), random(440, 451), true);
wait(random(200, 400));
}
Point location = new Point(random(610,655),random(425,448));
moveMouse(location, 0, 0);
wait(random(700, 2700));
}
else
{
if(debug)
log("skill check not activated");
}
}
 
public void onRepaint(Graphics g) {
// Script Timer
long millis = System.currentTimeMillis() - starttime;
long hours = millis / (1000 * 60 * 60);
millis -= hours * (1000 * 60 * 60);
long minutes = millis / (1000 * 60);
millis -= minutes * (1000 * 60);
long seconds = millis / 1000;
 
g.setColor(new Color(0, 0, 0, 208));
g.fillRect(279, 248, 232, 87);
g.setFont(new Font("Arial Black", 0, 19));
g.setColor(new Color(255, 255, 255, 217));
g.drawString("KKHunter", 340, 248);
 
//This paint was made using Enfilade's Paint Maker
g.setColor(new Color(0, 0, 0, 208));
g.fillRect(279, 248, 232, 87);
g.setFont(new Font("Verdana", 0, 11));
g.setColor(new Color(255, 0, 0));
g.drawString("Running: " + hours + ":" + minutes + ":" + seconds, 287, 265);
gainedExp = skills.getCurrentSkillExp(STAT_HUNTER) - startingExp;
g.setFont(new Font("Verdana", 0, 11));
g.setColor(new Color(255, 0, 0));
g.drawString("Exp gained: " + gainedExp, 287, 283);
g.setFont(new Font("Verdana", 0, 11));
g.setColor(new Color(255, 0, 0));
g.drawString("Level: " + skills.getCurrentSkillLevel(STAT_HUNTER), 287, 300);
g.setFont(new Font("Verdana", 0, 11));
g.setColor(new Color(255, 0, 0));
g.drawString("Lvls Up: " + (skills.getCurrentSkillLevel(STAT_HUNTER) - startingLevel), 286, 321);
}
public void serverMessageRecieved(ServerMessageEvent e) {
String message = e.getMessage();
if(message.contains("You need a net and rope")){
if(debug)
log("you don't have enough nets or ropes to set that many traps");
equipSets = checkEquipment();
}
if(message.contains("You need a net and rope")){
if(debug)
{
log("You have run out of room in your inventory, this should not happen");
log("Trying to fix it");
 
if (getCurrentTab() != Constants.TAB_INVENTORY) 
{
openTab(Constants.TAB_INVENTORY);
wait(random(500, 900));
atInventoryItem(salamanderID, "Release");
wait(random(500, 900));
atInventoryItem(salamanderID, "Release");
wait(random(500, 900));
}
}
}
}
private class RandUD implements Runnable{
public void run() {
char currUDKey;
int i = random(0,2);
try{
Thread.sleep(random(0,500));
switch (i){
case 0:
currUDKey = up;
Bot.getInputManager().pressKey(currUDKey);
Thread.sleep(random(100,2000));
Bot.getInputManager().releaseKey(currUDKey);
break;
case 1:
currUDKey = down;
Bot.getInputManager().pressKey(currUDKey);
Thread.sleep(random(100,2000));
Bot.getInputManager().releaseKey(currUDKey);
break;
default:
//do nothing
break;
}
}catch(Throwable e)
{
log("Error: "+ e);
}
}
}
private class AntiBan implements Runnable{
 
char currLRKey;
char currUDKey;
boolean alternateUD = false;
boolean alternateLR = false;
int i;
public void run() {
log("antiban thread in action");
i = random(0,6);
switch (i){
case 0:
currLRKey = left;
currUDKey = up;
alternateUD = false;
alternateLR = false;
break;
case 1:
currLRKey = right;
currUDKey = up;
alternateUD = false;
alternateLR = false;
break;
case 2:
currLRKey = left;
currUDKey = down;
alternateUD = false;
alternateLR = false;
break;
case 3:
currLRKey = left;
currUDKey = down;
alternateUD = false;
alternateLR = false;
break;
case 4:
currLRKey = left;
currUDKey = down;
alternateUD = true;
alternateLR = false;
break;
case 5:
currLRKey = left;
currUDKey = down;
alternateUD = false;
alternateLR = true;
break;
case 6:
currLRKey = left;
currUDKey = down;
alternateUD = true;
alternateLR = true;
break;
default:break;
 
}
try{
i = random(0,3);
if(i>0)
{
Bot.getInputManager().pressKey(currLRKey);
i = random(0,3);
if(i<1)
{
Thread.sleep(random(100,1000));
Bot.getInputManager().pressKey(currUDKey);
i = random(0,1);
if(i==0)
{
Bot.getInputManager().releaseKey(currLRKey);
Thread.sleep(random(100,2000));
Bot.getInputManager().releaseKey(currUDKey);
}
else{
Bot.getInputManager().releaseKey(currUDKey);
Thread.sleep(random(100,2000));
Bot.getInputManager().releaseKey(currLRKey);
}
}
}
else
{
Bot.getInputManager().pressKey(currUDKey);
i = random(0,3);
if(i<1)
{
Thread.sleep(random(100,1000));
Bot.getInputManager().pressKey(currLRKey);
i = random(0,1);
if(i==0)
{
Bot.getInputManager().releaseKey(currUDKey);
Thread.sleep(random(100,2000));
Bot.getInputManager().releaseKey(currLRKey);
}
else{
Bot.getInputManager().releaseKey(currLRKey);
Thread.sleep(random(100,2000));
Bot.getInputManager().releaseKey(currUDKey);
}
}
}
}catch(Throwable e)
{
log("Error: " + e);
}
Bot.getInputManager().releaseKey(left);
Bot.getInputManager().releaseKey(right);
Bot.getInputManager().releaseKey(up);
Bot.getInputManager().releaseKey(down);
log("End of Antiban");
}
}
}