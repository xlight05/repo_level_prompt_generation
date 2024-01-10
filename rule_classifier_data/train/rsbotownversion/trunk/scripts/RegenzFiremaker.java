import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Map;

import org.rsbot.bot.Bot;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.script.Calculations;
import org.rsbot.script.Constants;
import org.rsbot.script.Methods;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.Skills;
import org.rsbot.script.wrappers.RSInterface;
import org.rsbot.script.wrappers.RSInterfaceChild;
import org.rsbot.script.wrappers.RSInterfaceComponent;
import org.rsbot.script.wrappers.RSTile;

@ScriptManifest(authors = { "Regenz" }, category = "Firemaking", name = "Regenz Firemaker", version = 1.17, description =
"<html><head>" +
"</head><body>" +
"<center><strong><h2>Regenz Firemaker</h2></strong></center>" +
"<br /><strong>Location?</strong>" +
"<br /><select name='LOC'>" +
"<option>Fist of Guthix" +
"<option>Grand Exchange North" +
"<option>Grand Exchange South" +
"<option>Varrock East Bank" +
"<option>Varrock West Bank</select>" +
"<br /><br /><strong>Log Type?</strong>" +
"<br /><select name='LOG'>" +
"<option>Normal" +
"<option>Oak" +
"<option>Willow" +
"<option>Maple" +
"<option>Yew" +
"<option>Magic</select>" +
"</body></html>")

public class RegenzFiremaker extends Script implements ServerMessageListener, PaintListener {

final ScriptManifest properties = getClass().getAnnotation(ScriptManifest.class);
final GarrettsPaint thePainter = new GarrettsPaint();
final TheWalker theWalker = new TheWalker();

// OTHER VARIABLES
long scriptStartTime;
int runEnergy = random(40, 95);
int log = 0;
int useTile = 99;
int startXP = 0;
int startLvl = 0;
double XPperLog = 0;
boolean newSpot = true;
boolean hovering = false;
boolean setAltitude = true;
String LOC;
String LOG;

// ITEM ID
final int tinderBox = 590;
final int normalLog = 1511;
final int oakLog = 1521;
final int willowLog = 1519;
final int mapleLog = 1517;
final int yewLog = 1515;
final int magicLog = 1513;

//OBJECT ID
final int burningFire = 2732;

//ANIMATION ID
final int startingFire = 733;

//LOCATIONS
RSTile startTiles[];
RSTile bankTiles[];
RSTile bankFront;

final RSTile vebStart[] = {new RSTile(3264 + random(-2, 3), 3428), new RSTile(3264 + random(-2, 3), 3429)};
final RSTile vebBooth[] = {new RSTile(3251, 3419), new RSTile(3252, 3419), new RSTile(3253, 3419), new RSTile(3254, 3419), new RSTile(3255, 3419), new RSTile(3256, 3419)};
final RSTile vebFront = new RSTile(3253, 3428);

final RSTile vwbStart[] = {new RSTile(3198 + random(-2, 3), 3431), new RSTile(3203 + random(-6, 5), 3430), new RSTile(3203 + random(-6, 5), 3429), new RSTile(3203 + random(-6, 5), 3428)};
final RSTile vwbBooth[] = {new RSTile(3181, 3436), new RSTile(3181, 3438), new RSTile(3181, 3440), new RSTile(3190, 3435), new RSTile(3190, 3437), new RSTile(3190, 3439)};
final RSTile vwbFront = new RSTile(3186, 3430);

final RSTile ge2Start[] = {new RSTile(3176 + random(0, 3), 3483), new RSTile(3176 + random(0, 3), 3482)};
final RSTile ge2Booth[] = {new RSTile(3166, 3489), new RSTile(3163, 3490)};
final RSTile ge2Front = new RSTile(3162, 3488);

final RSTile ge1Start[] = {new RSTile(3176 + random(0, 3), 3496), new RSTile(3176 + random(0, 3), 3497)};
final RSTile ge1Booth[] = {new RSTile(3166, 3489), new RSTile(3163, 3490)};
final RSTile ge1Front = new RSTile(3162, 3488);

final RSTile fogStart[] = {new RSTile(1718, 5597), new RSTile(1718, 5598), new RSTile(1718, 5599), new RSTile(1718, 5600), new RSTile(1717, 5601)};
final RSTile fogBooth[] = {new RSTile(1705, 5599)};
final RSTile fogFront = new RSTile(1705, 5599);

public enum State { burnLogs, walkBank, walkFront, walkSpot, bank, newSpot, error; }

public State getState() {
if (!inventoryContains(tinderBox)) {
return State.error;
}
if (newSpot)
return State.newSpot;
if (inventoryContains(tinderBox, log)) {
if (tileOnScreen(closestTile(startTiles)))
return State.burnLogs;
return State.walkSpot;
}
if (tileOnScreen(closestTile(bankTiles)))
return State.bank;
else if (tileOnMap(closestTile(bankTiles)))
return State.walkBank;
else if (!tileOnMap(closestTile(bankTiles)))
return State.walkFront;
return null;
}

public boolean onStart(Map<String, String> args) {
scriptStartTime = System.currentTimeMillis();
LOC = args.get("LOC");
LOG = args.get("LOG");

if (LOG.equals("Normal")) {
log = normalLog;
XPperLog = 40;
} else if (LOG.equals("Oak")) {
log = oakLog;
XPperLog = 60;
} else if (LOG.equals("Willow")) {
log = willowLog;
XPperLog = 90;
} else if (LOG.equals("Maple")) {
log = mapleLog;
XPperLog = 135;
} else if (LOG.equals("Yew")) {
log = yewLog;
XPperLog = 202.5;
} else if (LOG.equals("Magic")) {
log = magicLog;
XPperLog = 303.8;
}

if (LOC.equals("Varrock East Bank")) {
startTiles = vebStart;
bankTiles = vebBooth;
bankFront = vebFront;
} else if (LOC.equals("Varrock West Bank")) {
startTiles = vwbStart;
bankTiles = vwbBooth;
bankFront = vwbFront;
} else if (LOC.equals("Grand Exchange North")) {
startTiles = ge1Start;
bankTiles = ge1Booth;
bankFront = ge1Front;
} else if (LOC.equals("Grand Exchange South")) {
startTiles = ge2Start;
bankTiles = ge2Booth;
bankFront = ge2Front;
} else if (LOC.equals("Fist of Guthix")) {
startTiles = fogStart;
bankTiles = fogBooth;
bankFront = fogFront;
}

return true;
}

public int loop() {
if(!isLoggedIn())
return random(1000, 2000);

if(startLvl == 0) {
startXP = skills.getCurrentSkillExp(Skills.getStatIndex("firemaking"));
startLvl = skills.getCurrentSkillLevel(Skills.getStatIndex("firemaking"));
}

if(setAltitude) {
setCameraAltitude(true);
wait(random(250, 500));
setAltitude = false;
}

thePainter.scriptRunning = true;

if (!thePainter.savedStats)
thePainter.saveStats();

startRunning(runEnergy);

switch(getState()) {
case newSpot:
nextSpot(startTiles);
newSpot = false;
return random(40, 60);
case burnLogs:
burnLogs();
return random(40, 60);
case walkFront:
walkTile(bankFront);
return random(40, 60);
case walkSpot:
walkTile(startTiles[useTile]);
return random(40, 60);
case walkBank:
walkTile(closestTile(bankTiles));
return random(40, 60);
case bank:
doBank();
return random(40, 60);
case error:
return random(1000, 2000);
}
return random(40, 60);
}

public int getSelectedItem() {
for(RSInterfaceComponent com : getInventoryInterface().getComponents()){
if(com.getBorderThickness() == 2)
return com.getComponentID();
}
return 0;
}

public boolean isItemSelected() {
return (getSelectedItem() > 0);
}

public void doBank() {
int failCount = 0;
try {
if (isItemSelected())
unSelect();
if (!bank.isOpen()) {
if (bank.open()) {
wait(random(300, 600));
}
failCount = 0;
while(!bank.isOpen() && failCount < 20) {
if (getMyPlayer().isMoving())
failCount = 0;
wait(random(90, 110));
failCount++;
}
return;
}
if (bank.isOpen()) {
if (bank.depositAllExcept(tinderBox, log)) {
wait(random(500, 750));
}
if (bank.atItem(log, "Withdraw-All")) {
wait(random(500, 750));
}
failCount = 0;
while(!inventoryContains(log) && failCount < 30) {
wait(random(90, 110));
failCount++;
}
}
} catch (final Exception e) { }
}

public void burnLogs() {
RSTile currentTile = null;
int invCount = 0;
int failCount = 0;
boolean firstRun = true;
if (checkFires(10)) {
newSpot = true;
return;
} else if (getMyPlayer().getLocation().getX() != startTiles[useTile].getX() || getMyPlayer().getLocation().getY() != startTiles[useTile].getY()) {
walkTile(startTiles[useTile]);
return;
}
if (getMyPlayer().isMoving()) {
wait(random(300, 500));
return;
}
unSelect();
while(getInventoryCount(log) > 0) {
failCount = 0;
if (newSpot)
break;
while(getMyPlayer().getAnimation() == startingFire)
wait(50);
try {
invCount = getInventoryCount();
} catch (final Exception e) { }
if (isItemSelected()) {
if (hovering && !isMenuOpen()) {
wait(random(50, 100));
clickMouse(true);
wait(random(50, 100));
hovering = false;
} else {
wait(random(50, 100));
clickItem(log, "Use");
wait(random(50, 100));
}
try {
currentTile = getMyPlayer().getLocation();
} catch (final Exception e) { }
}
if (!isItemSelected() && inventoryContains(log)) {
wait(random(50, 100));
clickItem(tinderBox, "Use");
wait(random(50, 100));
}
try {
while(invCount == getInventoryCount() && !firstRun) {
wait(50);
failCount++;
if (failCount > 40)
break;
}
} catch (final Exception e) { }
failCount = 0;
if (isItemSelected())
if (hoverItem(log))
hovering = true;
try {
while(currentTile.getX() == getMyPlayer().getLocation().getX() && currentTile.getY() == getMyPlayer().getLocation().getY()) {
wait(50);
failCount++;
if (failCount > 40)
break;
}
} catch (final Exception e) { }
firstRun = false;
if(checkForFire() && getMyPlayer().getAnimation() != startingFire)
newSpot = true;
}
}

// Made by Speed, Small change by Regenz
public boolean hoverItem(final int itemID) {
try {
if (getCurrentTab() != Constants.TAB_INVENTORY
&& !RSInterface.getInterface(Constants.INTERFACE_BANK)
.isValid()
&& !RSInterface.getInterface(Constants.INTERFACE_STORE)
.isValid()) {
openTab(Constants.TAB_INVENTORY);
}

final RSInterfaceChild inventory = getInventoryInterface();
if (inventory == null || inventory.getComponents() == null) {
return false;
}

final java.util.List<RSInterfaceComponent> possible = new ArrayList<RSInterfaceComponent>();
for (final RSInterfaceComponent item : inventory.getComponents()) {
if (item != null && item.getComponentID() == itemID) {
possible.add(item);
}
}

if (possible.size() == 0) {
return false;
}

final RSInterfaceComponent item = possible.get(0);
return hoverInterface(item);
} catch (final Exception e) {
log("atInventoryItem(final int itemID, final String option) Error: "
+ e);
return false;
}
}

//From Methods.java, Small change by Regenz
public boolean hoverInterface(final RSInterfaceChild i) {
if (!i.isValid()) {
return false;
}
final Rectangle pos = i.getArea();
if (pos.x == -1 || pos.y == -1 || pos.width == -1 || pos.height == -1) {
return false;
}

final int dx = (int) (pos.getWidth() - 4) / 2;
final int dy = (int) (pos.getHeight() - 4) / 2;
final int midx = (int) (pos.getMinX() + pos.getWidth() / 2);
final int midy = (int) (pos.getMinY() + pos.getHeight() / 2);

moveMouse(midx + random(-dx, dx), midy + random(-dy, dy));
wait(random(50, 60));
return true;
}

// Made by Speed, Small change by Regenz
public boolean clickItem(final int itemID, final String option) {
try {
if (getCurrentTab() != Constants.TAB_INVENTORY
&& !RSInterface.getInterface(Constants.INTERFACE_BANK)
.isValid()
&& !RSInterface.getInterface(Constants.INTERFACE_STORE)
.isValid()) {
openTab(Constants.TAB_INVENTORY);
}

final RSInterfaceChild inventory = getInventoryInterface();
if (inventory == null || inventory.getComponents() == null) {
return false;
}

final java.util.List<RSInterfaceComponent> possible = new ArrayList<RSInterfaceComponent>();
for (final RSInterfaceComponent item : inventory.getComponents()) {
if (item != null && item.getComponentID() == itemID) {
possible.add(item);
}
}

if (possible.size() == 0) {
return false;
}

final RSInterfaceComponent item = possible.get(0);
return atInterface(item, option);
} catch (final Exception e) {
log("atInventoryItem(final int itemID, final String option) Error: "
+ e);
return false;
}
}

public boolean checkForFire() {
RSTile thisTile = getMyPlayer().getLocation();
try {
int tileObj = getObjectAt(thisTile.getX() - 1, thisTile.getY()).getID();
if (tileObj == burningFire)
return true;
} catch (final Exception e) { }
return false;
}

public boolean checkFires(final int check) {
RSTile thisTile = startTiles[useTile];
for (int i = 0; i <= check; i++) {
try {
int tileObj = getObjectAt(thisTile.getX() - i, thisTile.getY()).getID();
if (tileObj == burningFire)
return true;
} catch (final Exception e) { }
}
return false;
}

public void unSelect() {
if (!isItemSelected())
return;
if (getCurrentTab() != TAB_INVENTORY)
openTab(TAB_INVENTORY);
clickMouse(random(750, 760), random(210, 406), true);
}

public void nextSpot(final RSTile tiles[]) {
int nextTile = useTile + 1;
int length = tiles.length - 1;
if (nextTile > length)
nextTile = 0;
useTile = nextTile;
}

public void walkTile(final RSTile tile) {
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
waitToMove(1000);
return;
} else {
theWalker.walkTo(new RSTile[] {tile}, true);
return;
}
}

public RSTile getClosestTileOnMap(final RSTile tile) {
if (distanceTo(tile) > 16 && isLoggedIn()) {
try {
final RSTile loc = getMyPlayer().getLocation();
final RSTile walk = new RSTile((loc.getX() + tile.getX()) / 2, (loc.getY() + tile.getY()) / 2);
return (distanceTo(walk) <= 16) ? walk : getClosestTileOnMap(walk);
} catch (final Exception e) {
}
}
return tile;
}

public RSTile closestTile(final RSTile tiles[]) {
int dist = 999;
RSTile closest = null;
for (int i = 0; i < tiles.length; i++) {
try {
int distance = distanceTo(tiles[i]);
if (distance < dist) {
dist = distance;
closest = tiles[i];
}
} catch (final Exception e) { }
}
return closest;
}

public void startRunning(final int energy) {
if (getEnergy() >= energy && !isRunning()) {
runEnergy = random(40, 95);
setRun(true);
wait(random(500, 750));
}
}

public void onFinish() {
Bot.getEventManager().removeListener(PaintListener.class, this);
}

public void serverMessageRecieved(ServerMessageEvent e)
{
if(e.getMessage().contains("You can't light a fire here."))
newSpot = true;
}


public void onRepaint(Graphics g) {
theWalker.drawMap(g);
thePainter.paint(g);
}

//If you use my paint please give credit.
class GarrettsPaint {

final Rectangle r = new Rectangle(7, 345, 408, 114);
final Rectangle r1 = new Rectangle(420, 345, 77, 25);
final Rectangle r2 = new Rectangle(420, 374, 77, 26);
final Rectangle r3 = new Rectangle(420, 404, 77, 26);
final Rectangle r4 = new Rectangle(420, 434, 77, 25);
final Rectangle r2c = new Rectangle(415, 374, 5, 26);
final Rectangle r3c = new Rectangle(415, 404, 5, 26);
final Rectangle r4c = new Rectangle(415, 434, 5, 25);
final Rectangle sb1 = new Rectangle(12, 350, 398, 12);
final Rectangle sb2 = new Rectangle(12, 363, 398, 12);
final Rectangle sb3 = new Rectangle(12, 376, 398, 12);
final Rectangle sb4 = new Rectangle(12, 389, 398, 12);
final Rectangle sb5 = new Rectangle(12, 402, 398, 12);
final Rectangle sb6 = new Rectangle(12, 415, 398, 12);
final Rectangle sb7 = new Rectangle(12, 428, 398, 12);
final Rectangle sb8 = new Rectangle(12, 441, 398, 12);
final Rectangle sb1s = new Rectangle(12, 350, 196, 12);
final Rectangle sb2s = new Rectangle(12, 363, 196, 12);
final Rectangle sb3s = new Rectangle(12, 376, 196, 12);
final Rectangle sb4s = new Rectangle(12, 389, 196, 12);
final Rectangle sb5s = new Rectangle(12, 402, 196, 12);
final Rectangle sb6s = new Rectangle(12, 415, 196, 12);
final Rectangle sb7s = new Rectangle(12, 428, 196, 12);
final Rectangle sb8s = new Rectangle(12, 441, 196, 12);
final Rectangle sb9s = new Rectangle(213, 350, 196, 12);
final Rectangle sb10s = new Rectangle(213, 363, 196, 12);
final Rectangle sb11s = new Rectangle(213, 376, 196, 12);
final Rectangle sb12s = new Rectangle(213, 389, 196, 12);
final Rectangle sb13s = new Rectangle(213, 402, 196, 12);
final Rectangle sb14s = new Rectangle(213, 415, 196, 12);
final Rectangle sb15s = new Rectangle(213, 428, 196, 12);
final Rectangle sb16s = new Rectangle(213, 441, 196, 12);
Rectangle[] skillBars = new Rectangle[] {sb1, sb2, sb3, sb4, sb5, sb6, sb7, sb8};
boolean savedStats = false;
boolean scriptRunning = false;
boolean checkedCount = false;
int currentTab = 0;
int lastTab = 0;
int[] barIndex = new int[16];
int[] start_exp = null;
int[] start_lvl = null;
int[] gained_exp = null;
int[] gained_lvl = null;

Thread mouseWatcher = new Thread();
final NumberFormat nf = NumberFormat.getInstance();

final long time_ScriptStart = System.currentTimeMillis();
long runTime = System.currentTimeMillis() - time_ScriptStart;

int sine = 0;
int sineM = 1;
void paint(final Graphics g) {
if (!isLoggedIn() || !scriptRunning)
return;

//credits to Jacmob for the pulsing
if (sine >= 84) {
sine = 84;
sineM *= -1;
} else if (sine <= 1) {
sine = 1;
sineM *= -1;
}
sine += sineM;

runTime = System.currentTimeMillis() - time_ScriptStart;
final String formattedTime = formatTime((int) runTime);

currentTab = paintTab();

switch(currentTab) {
case -1: //PAINT OFF
g.setColor(new Color(0, 0, 0, 150));
g.fillRect(r1.x, r1.y, r1.width, r1.height);
g.setColor(Color.WHITE);
drawString(g, "Show Paint", r1, 5);
break;
case 0: //DEFAULT TAB - MAIN
drawPaint(g, r2c);
g.setColor(new Color(100, 100, 100, 200));
g.drawLine(r.x + 204, r.y + 22, r.x + 204, r.y + 109);
g.setColor(Color.WHITE);
g.setFont(new Font("sansserif", Font.BOLD, 14));
drawString(g, properties.name(), r, -40);
g.setFont(new Font("sansserif", Font.PLAIN, 12));
drawStringMain(g, "Runtime: ", formattedTime, r, 20, 35, 0, true);
final int currentXP = skills.getCurrentSkillExp(Skills.getStatIndex("firemaking"));
final int gainedXP = currentXP - start_exp[Skills.getStatIndex("firemaking")];
final int firesLit = (int) (gainedXP / XPperLog);
int firesPerHour = 0;
if ((runTime / 1000) > 0) {
firesPerHour = (int) ((3600000.0 / (double) runTime) * firesLit);
}
drawStringMain(g, "Fires Lit: ", Integer.toString(firesLit), r, 20, 35, 2, true);
drawStringMain(g, "Fires / Hour: ", Integer.toString(firesPerHour), r, 20, 35, 3, true);
drawStringMain(g, "Log Type: ", LOG, r, 20, 35, 3, false);
drawStringMain(g, "Location: ", "", r, 20, 35, 4, true);
drawStringMain(g, "", LOC, r, 20, 35, 4, false);
break;
case 1: //INFO
drawPaint(g, r3c);
g.setColor(new Color(100, 100, 100, 200));
g.drawLine(r.x + 204, r.y + 22, r.x + 204, r.y + 109);
g.setColor(Color.WHITE);
g.setFont(new Font("sansserif", Font.BOLD, 14));
drawString(g, properties.name(), r, -40);
g.setFont(new Font("sansserif", Font.PLAIN, 12));
drawStringMain(g, "Version: ", Double.toString(properties.version()), r, 20, 35, 0, true);
break;
case 2: //STATS
drawPaint(g, r4c);
drawStats(g);
hoverMenu(g);
break;
}
}

void saveStats() {
nf.setMinimumIntegerDigits(2);
final String[] stats = Skills.statsArray;
start_exp = new int[stats.length];
start_lvl = new int[stats.length];
for (int i = 0; i < stats.length; i++) {
start_exp[i] = skills.getCurrentSkillExp(i);
start_lvl[i] = skills.getCurrentSkillLevel(i);
}
for (int i = 0; i < barIndex.length; i++) {
barIndex[i] = -1;
}
savedStats = true;
}

int paintTab() {
final Point mouse = new Point(Bot.getClient().getMouse().x, Bot.getClient().getMouse().y);
if (mouseWatcher.isAlive())
return currentTab;
if (r1.contains(mouse)) {
mouseWatcher = new Thread(new MouseWatcher(r1));
mouseWatcher.start();
if (currentTab == -1) {
return lastTab;
} else {
lastTab = currentTab;
return -1;
}
}
if (currentTab == -1)
return currentTab;
if (r2.contains(mouse))
return 0;
if (r3.contains(mouse))
return 1;
if (r4.contains(mouse))
return 2;
return currentTab;
}

void drawPaint(final Graphics g, final Rectangle rect) {
g.setColor(new Color(0, 0, 0, 230));
g.fillRect(r1.x, r1.y, r1.width, r1.height);
g.fillRect(r2.x, r2.y, r2.width, r2.height);
g.fillRect(r3.x, r3.y, r3.width, r3.height);
g.fillRect(r4.x, r4.y, r4.width, r4.height);
g.fillRect(rect.x, rect.y, rect.width, rect.height);
g.fillRect(r.x, r.y, r.width, r.height);
g.setColor(Color.WHITE);
drawString(g, "Hide Paint", r1, 5);
drawString(g, "MAIN", r2, 5);
drawString(g, "INFO", r3, 5);
drawString(g, "STATS", r4, 5);
g.setColor(new Color(0, 0, 0, 230));
}

void drawStat(final Graphics g, final int index, final int count) {
if (count >= skillBars.length && !checkedCount) {
skillBars = new Rectangle[] {sb1s, sb2s, sb3s, sb4s, sb5s, sb6s, sb7s, sb8s, sb9s, sb10s, sb11s, sb12s, sb13s, sb14s, sb15s, sb16s};
checkedCount = true;
}
if (count >= skillBars.length)
return;
g.setFont(new Font("serif", Font.PLAIN, 11));
g.setColor(new Color(100, 100, 100, 150));
g.fillRect(skillBars[count].x, skillBars[count].y, skillBars[count].width, skillBars[count].height);
final int percent = skills.getPercentToNextLevel(index);
g.setColor(new Color(255 - 2 * percent, (int) (1.7 * percent + sine), 0, 150));
g.fillRect(skillBars[count].x, skillBars[count].y, (int) (((double) skillBars[count].width / 100.0) * (double) percent), skillBars[count].height);
g.setColor(Color.WHITE);
final String name = Skills.statsArray[index];
final String capitalized = name.substring(0, 1).toUpperCase() + name.substring(1);
g.drawString(capitalized, skillBars[count].x + 2, skillBars[count].y + 10);
drawStringEnd(g, percent + "%", skillBars[count], -2, 4);
barIndex[count] = index;
}

void drawStats(final Graphics g) {
final String[] stats = Skills.statsArray;
int count = 0;
gained_exp = new int[stats.length];
gained_lvl = new int[stats.length];
for (int i = 0; i < stats.length; i++) {
gained_exp[i] = skills.getCurrentSkillExp(i) - start_exp[i];
gained_lvl[i] = skills.getCurrentSkillLevel(i) - start_lvl[i];
if (gained_exp[i] > 0) {
drawStat(g, i, count);
count++;
}
}
}

void hoverMenu(final Graphics g) {
final Point mouse = new Point(Bot.getClient().getMouse().x, Bot.getClient().getMouse().y);
final Rectangle r_main = new Rectangle(mouse.x, mouse.y - 150, 300, 150);
for (int i = 0; i < barIndex.length; i++) {
if (barIndex[i] > -1) {
if (skillBars[i].contains(mouse)) {
final int xpTL = skills.getXPToNextLevel(barIndex[i]);
final int xpHour = ((int) ((3600000.0 / (double) runTime) * gained_exp[barIndex[i]]));
final int TTL = (int) (((double) xpTL / (double) xpHour) * 3600000);
g.setColor(new Color(50, 50, 50, 240));
g.fillRect(r_main.x, r_main.y, r_main.width, r_main.height);
g.setColor(Color.WHITE);
g.setFont(new Font("sansserif", Font.BOLD, 15));
drawString(g, Skills.statsArray[barIndex[i]].toUpperCase(), r_main, -58);
g.setFont(new Font("sansserif", Font.PLAIN, 12));
hoverDrawString(g, "Current Level: ", skills.getCurrentSkillLevel(barIndex[i]) + "", r_main, 40, 0);
hoverDrawString(g, "XP Gained: ", gained_exp[barIndex[i]] + "xp", r_main, 40, 1);
hoverDrawString(g, "XP / Hour: ", xpHour + "xp", r_main, 40, 2);
hoverDrawString(g, "LVL Gained: ", gained_lvl[barIndex[i]] + " lvls", r_main, 40, 3);
hoverDrawString(g, "XPTL: ", xpTL + "xp", r_main, 40, 4);
hoverDrawString(g, "TTL: ", formatTime(TTL), r_main, 40, 5);
}
}
}
}

void hoverDrawString(final Graphics g, final String str, final String val, final Rectangle rect, final int offset, final int index) {
g.setColor(Color.WHITE);
final FontMetrics font = g.getFontMetrics();
final Rectangle2D bounds = font.getStringBounds(val, g);
final int width = (int) bounds.getWidth();
final int y = rect.y + offset + (20 * index);
g.drawString(str, rect.x + 5, y);
g.drawString(val, (rect.x + rect.width) - width - 5, y);
if (index < 5) {
g.setColor(new Color(100, 100, 100, 200));
g.drawLine(rect.x + 5, y + 5, rect.x + rect.width - 5, y + 5);
}
}

void drawString(final Graphics g, final String str, final Rectangle rect, final int offset) {
final FontMetrics font = g.getFontMetrics();
final Rectangle2D bounds = font.getStringBounds(str, g);
final int width = (int) bounds.getWidth();
g.drawString(str, rect.x + ((rect.width - width) / 2), rect.y + ((rect.height / 2) + offset));
}

void drawStringEnd(final Graphics g, final String str, final Rectangle rect, final int xOffset, final int yOffset) {
final FontMetrics font = g.getFontMetrics();
final Rectangle2D bounds = font.getStringBounds(str, g);
final int width = (int) bounds.getWidth();
g.drawString(str, (rect.x + rect.width) - width + xOffset, rect.y + ((rect.height / 2) + yOffset));
}

void drawStringMain(final Graphics g, final String str, final String val, final Rectangle rect, final int xOffset, final int yOffset, final int index, final boolean leftSide) {
final FontMetrics font = g.getFontMetrics();
final Rectangle2D bounds = font.getStringBounds(val, g);
final int indexMult = 17;
final int width = (int) bounds.getWidth();
if (leftSide) {
g.drawString(str, rect.x + xOffset, rect.y + yOffset + (index * indexMult));
g.drawString(val, rect.x + (rect.width / 2) - width - xOffset, rect.y + yOffset + (index * indexMult));
} else {
g.drawString(str, rect.x + (rect.width / 2) + xOffset, rect.y + yOffset + (index * indexMult));
g.drawString(val, rect.x + rect.width - width - xOffset, rect.y + yOffset + (index * indexMult));
}
}

String formatTime(final int milliseconds) {
final long t_seconds = milliseconds / 1000;
final long t_minutes = t_seconds / 60;
final long t_hours = t_minutes / 60;
final int seconds = (int) (t_seconds % 60);
final int minutes = (int) (t_minutes % 60);
final int hours = (int) (t_hours % 60);
return (nf.format(hours) + ":" + nf.format(minutes) + ":" + nf.format(seconds));
}

class MouseWatcher implements Runnable {

Rectangle rect = null;

MouseWatcher(final Rectangle rect) {
this.rect = rect;
}

public void run() {
Point mouse = new Point(Bot.getClient().getMouse().x, Bot.getClient().getMouse().y);
while (rect.contains(mouse)) {
try {
mouse = new Point(Bot.getClient().getMouse().x, Bot.getClient().getMouse().y);
Thread.sleep(50);
} catch(Exception e) { }
}
}

}

}

//Please give credit if you decide to use
public class TheWalker {

Methods methods = new Methods();
Thread walker = null;
RSTile[] path = null;

public void drawMap(final Graphics g) {
if (walker != null && walker.isAlive()) {
Point myTile = tileToMinimap(getMyPlayer().getLocation());
Point center = new Point(myTile.x + 2, myTile.y + 2);
g.drawOval(center.x - 70, center.y - 70, 140, 140);
if (path == null) return;
for (int i = 0; i < path.length; i++) {
final RSTile tile = path[i];
final Point p = tileToMinimap(tile);
if (p.x != -1 && p.y != -1) {
g.setColor(Color.BLACK);
g.fillRect(p.x + 1, p.y + 1, 3, 3);
if (i > 0) {
final Point p1 = tileToMinimap(path[i - 1]);
g.setColor(Color.ORANGE);
if (p1.x != -1 && p1.y != -1)
g.drawLine(p.x + 2, p.y + 2, p1.x + 2, p1.y + 2);
}
}
}
Point tile = tileToMinimap(nextTile(path));
g.setColor(Color.RED);
if (tile.x != -1 && tile.y != -1) {
g.fillRect(tile.x + 1, tile.y + 1, 3, 3);
}
g.setColor(Color.BLACK);
}
}

public boolean walkTo(final RSTile[] path, final boolean waitUntilDest) {
Walker walkto = new Walker(path, 5, 10000);
walker = new Thread(walkto);
walker.start();
waitToMove(random(800, 1200));
if (waitUntilDest) {
while (walker.isAlive()) {
methods.wait(random(300, 600));
}
return walkto.done;
} else return true;
}

public Point tileToMM(RSTile tile) {
return new Point(tileToMinimap(tile).x + 2, tileToMinimap(tile).y + 2);
}

public boolean tileOnMM(RSTile tile) {
return pointOnMM(tileToMM(tile));
}

public boolean pointOnMM(Point point) {
Point myTile = tileToMM(getMyPlayer().getLocation());
Point center = new Point(myTile.x, myTile.y);
return (center.distance(point) < 70) ? true : false;
}

public RSTile getClosestTileOnMap(final RSTile tile) {
if (isLoggedIn() && !tileOnMM(tile)) {
try {
final RSTile loc = getMyPlayer().getLocation();
final RSTile walk = new RSTile((loc.getX() + tile.getX()) / 2, (loc.getY() + tile.getY()) / 2);
return tileOnMM(walk) ? walk : getClosestTileOnMap(walk);
} catch (final Exception e) { }
}
return tile;
}

public RSTile nextTile(RSTile[] path) {
for (int i = path.length - 1; i >= 0; i--) {
if (tileOnMM(path[i])) {
return path[i];
}
}
return getClosestTileOnMap(path[0]);
}

public class Walker implements Runnable {

RSTile tile = null;
boolean done = false;
boolean stop = false;
int movementTimer = 10000;
int distanceTo = 5;

Walker(final RSTile[] userpath) {
this.tile = userpath[userpath.length - 1];
path = userpath;
}

Walker(final RSTile[] userpath, final int distanceTo, final int movementTimer) {
this.tile = userpath[userpath.length - 1];
this.movementTimer = movementTimer;
this.distanceTo = distanceTo;
path = userpath;
}

public void run() {
long timer = System.currentTimeMillis();
RSTile lastTile = getMyPlayer().getLocation();
int randomReturn = random(5, 8);
while (distanceTo(tile) > distanceTo && !stop) {
if (!getMyPlayer().isMoving() || getDestination() == null || distanceTo(getDestination()) < randomReturn) {
RSTile nextTile = nextTile(path);
if (getDestination() != null && distanceBetween(getDestination(), nextTile) <= distanceTo) {
continue;
}
walkTileMM(nextTile);
waitToMove(random(800, 1200));
randomReturn = random(5, 8);
}
final RSTile myLoc = getMyPlayer().getLocation();
if (myLoc != lastTile) {
if (distanceBetween(myLoc, lastTile) > 30) {
stop = true;
}
timer = System.currentTimeMillis();
lastTile = myLoc;
}
if (System.currentTimeMillis() - timer > movementTimer) {
stop = true;
}
methods.wait(random(20, 40));
}
if (distanceTo(tile) <= distanceTo) {
done = true;
}
}

}

}

}