import java.io.*;
import java.awt.*;
import java.net.URL;
import java.util.Map;
import java.awt.Font;
import java.awt.event.*;
import org.rsbot.bot.Bot;
import org.rsbot.script.*;
import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.awt.RenderingHints;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.awt.image.BufferedImage;
import org.rsbot.script.wrappers.RSNPC;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSTile;
import org.rsbot.script.wrappers.RSCharacter;
import org.rsbot.event.listeners.PaintListener;

@ScriptManifest(
authors = "WarLord Burn (Kieren Boal)",
name = "Pk Buddy",
version = 4.5,
category = "PvP",
description = "<html>"
+ "<body style='font-family: Calibri; color:white; padding: 0px; text-align: center; background-color: black;'>"
+ "<img  src='http://images3.wikia.nocookie.net/__cb20081211093740/runescape/images/4/43/Skull.PNG'><h1>Pk  Buddy 4.5</h1><img  src='http://images3.wikia.nocookie.net/__cb20081211093740/runescape/images/4/43/Skull.PNG'></img>"
+ "<h2>Made by WarLord Burn</h2>"
+ "<br><br>"
+ "<tr><td><b>Vengance Timer: </b></td><td><center><select name=\"vegtime\">"
+ "<option>Yes"
+ "<option>No"
+ "</select></center></td></tr>"
+ "<tr><td><b>Special Attack globe:  </b></td><td><center><select  name=\"specles\">"
+ "<option>Yes"
+ "<option>No"
+ "</select></center></td></tr>"
/*+ "POW button: [Doesn't work... YET! :)] "
+ "<select name=\"Veng\">"
+ "<option>Cast Vengance"
+ "<option>Don't Cast Vengance"
+ "</select>"
+ "<select name=\"Prayer\">"
+ "<option>Burst of Strength"
+ "<option>Burst of strength and clarity of though"
+ "<option>Superhuman strength"
+ "<option>Superhuman strength and improved relexes"
+ "<option>Ultimate strength"
+ "<option>Ultimate strenth and improved reflexes"
+ "<option>Chiv"
+ "<option>Piety"
+ "</select>"
+ "<select name=\"Equipt\">"
+ "<option>Bgs"
+ "<option>ZGS"
+ "<option>AGS"
+ "<option>Sgs"
+ "<option>Dds"
+ "<option>D long"
+ "<option>Claws"
+ "<option>G maul"
+ "<option>Obby maul"
+ "<option>V long"
+ "</select>"
+ "</center>"*/
+ "</html>"
)



public class PkBuddy extends Script implements PaintListener{

int LastMage, NewMage, VenganceTimer, UpdateVeng, SpecTimer, SpecUpdate,  OldSpec, Picture, SpecPic, CurrY, CurrX, specPercent, LastAtk, NewAtk,  LastStr, NewStr, LastDef, NewDef, LastRange, NewRange, Check, IsCtrl,  XpGain, LastXp, Hit, HitDisplay, Display, myAtt, myStr, myDef, myRange,  myPrayer, myMagic, myHp, EnemyHp, EnemyAtk, EnemyStr, EnemyDef,  EnemyMage, EnemyRange, EnemyPrayer, EnemyFinalHp, EnemyHpPercent,  drawPaint, wildyLvl;

boolean VengOn, SpecOn, canClick, canClickSpec, canClickVeng, VenganceHotKey, SpecialHotKey;

String EnemyS, EnemyOld, Status, Painttype, Type, EnemyName, wildyString;

BufferedImage Vengance1, Vengance2, Vengance3, Vengance4, Vengance5,  Vengance6, Vengance7, Vengance8, Vengance9, Vengance10, Spec1, Spec2,  Spec3, Spec4, Spec5, Spec6, Spec7, Spec8, Spec9, Spec10, Next, Next2,  Report, Paint3Holder, Button1, Button2;

RSCharacter Enemy;

RSTile EnemyLocation;

int DisplayFor = 40;
int displayY=60;

public boolean onStart(Map<String, String>args) {

if (args.get("vegtime").equals("Yes")) {VengOn = true;}
if (args.get("specles").equals("Yes")) {SpecOn = true;}

log("Loading pictures, please wait");

try {
final URL veng1 = new URL ("http://img143.imageshack.us/img143/7639/veng0001.png");
final URL veng2 = new URL ("http://img535.imageshack.us/img535/5066/veng0002.png");
final URL veng3 = new URL("http://img822.imageshack.us/img822/1904/veng0003.png");
final URL veng4 = new URL("http://img594.imageshack.us/img594/4866/veng0004.png");
final URL veng5 = new URL("http://img210.imageshack.us/img210/2457/veng0005.png");
final URL veng6 = new URL("http://img267.imageshack.us/img267/2977/veng0006.png");
final URL veng7 = new URL("http://img205.imageshack.us/img205/1372/veng0007.png");
final URL veng8 = new URL("http://img709.imageshack.us/img709/3119/veng0008.png");
final URL veng9 = new URL("http://img80.imageshack.us/img80/7673/veng0009.png");
final URL veng10 = new URL("http://img713.imageshack.us/img713/7622/veng0010.png");
final URL SpecGlobe1 = new URL ("http://img717.imageshack.us/img717/5840/spec0001.png");
final URL SpecGlobe2 = new URL ("http://img143.imageshack.us/img143/1081/spec0002.png");
final URL SpecGlobe3 = new URL("http://img5.imageshack.us/img5/9501/spec0003.png");
final URL SpecGlobe4 = new URL("http://img840.imageshack.us/img840/280/spec0004.png");
final URL SpecGlobe5 = new URL("http://img267.imageshack.us/img267/364/spec0005.png");
final URL SpecGlobe6 = new URL("http://img687.imageshack.us/img687/3793/spec0006.png");
final URL SpecGlobe7 = new URL("http://img828.imageshack.us/img828/3528/spec0007.png");
final URL SpecGlobe8 = new URL("http://img199.imageshack.us/img199/8722/spec0008.png");
final URL SpecGlobe9 = new URL("http://img842.imageshack.us/img842/1538/spec0009.png");
final URL SpecGlobe10 = new URL("http://img180.imageshack.us/img180/926/spec0010.png");
final URL Nextt = new URL("http://img833.imageshack.us/img833/7121/next.png");
final URL Reportt = new URL("http://img802.imageshack.us/img802/1666/newreport.png");
final URL Nexttt = new URL("http://img821.imageshack.us/img821/4067/nextcopy.png");
final URL Paint3Holderr = new URL("http://img401.imageshack.us/img401/8896/bgholder.png");
final URL Button11 = new URL("http://img825.imageshack.us/img825/1812/buttonshow.png");
final URL Button22 = new URL("http://img641.imageshack.us/img641/552/buttonhide.png");
Vengance1 = ImageIO.read(veng1);
Vengance2 = ImageIO.read(veng2);
Vengance3 = ImageIO.read(veng3);
Vengance4 = ImageIO.read(veng4);
Vengance5 = ImageIO.read(veng5);
Vengance6 = ImageIO.read(veng6);
Vengance7 = ImageIO.read(veng7);
Vengance8 = ImageIO.read(veng8);
Vengance9 = ImageIO.read(veng9);
Vengance10 = ImageIO.read(veng10);
Spec1 = ImageIO.read(SpecGlobe1);
Spec2 = ImageIO.read(SpecGlobe2);
Spec3 = ImageIO.read(SpecGlobe3);
Spec4 = ImageIO.read(SpecGlobe4);
Spec5 = ImageIO.read(SpecGlobe5);
Spec6 = ImageIO.read(SpecGlobe6);
Spec7 = ImageIO.read(SpecGlobe7);
Spec8 = ImageIO.read(SpecGlobe8);
Spec9 = ImageIO.read(SpecGlobe9);
Spec10 = ImageIO.read(SpecGlobe10);
Next = ImageIO.read(Nextt);
Next2 = ImageIO.read(Nexttt);
Report = ImageIO.read(Reportt);
Paint3Holder = ImageIO.read(Paint3Holderr);
Button1 = ImageIO.read(Button11);
Button2 = ImageIO.read(Button22);
}
catch (final IOException e) {
log("Your internet fails. Re-load the script.");
e.printStackTrace();
}
return true;
}

//Thank you Gweetle! You fucking rock!!!!!!!!
private void getStats(String name) {

EnemyHp=1;
EnemyAtk=1;
EnemyStr=1;
EnemyDef=1;
EnemyMage=1;
EnemyRange=1;
EnemyPrayer=1;

log("New enemy found, "+EnemyS+". Getting their stats now!");
try {
URL url = new URL("http://hiscore.runescape.com/index_lite.ws?player=" + name);
BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
String inputLine;
int lineIndex = 0;
while ((inputLine = in.readLine()) != null) {
lineIndex++;
if (lineIndex == 2) {
StringTokenizer st = new StringTokenizer(inputLine, ",");
st.nextToken();
inputLine = st.nextToken();
if (Integer.parseInt(inputLine) != -1) {EnemyAtk = Integer.parseInt(inputLine);}
}
if (lineIndex == 3) {
StringTokenizer st = new StringTokenizer(inputLine, ",");
st.nextToken();
inputLine = st.nextToken();
if (Integer.parseInt(inputLine) != -1) {EnemyDef = Integer.parseInt(inputLine);}
}
if (lineIndex == 4) {
StringTokenizer st = new StringTokenizer(inputLine, ",");
st.nextToken();
inputLine = st.nextToken();
if (Integer.parseInt(inputLine) != -1) {EnemyStr = Integer.parseInt(inputLine);}
}
if (lineIndex == 5) {
StringTokenizer st = new StringTokenizer(inputLine, ",");
st.nextToken();
inputLine = st.nextToken();
if (Integer.parseInt(inputLine) != -1) {EnemyHp = Integer.parseInt(inputLine);}
}
if (lineIndex == 6) {
StringTokenizer st = new StringTokenizer(inputLine, ",");
st.nextToken();
inputLine = st.nextToken();
if (Integer.parseInt(inputLine) != -1) {EnemyRange = Integer.parseInt(inputLine);}
}
if (lineIndex == 7) {
StringTokenizer st = new StringTokenizer(inputLine, ",");
st.nextToken();
inputLine = st.nextToken();
if (Integer.parseInt(inputLine) != -1) {EnemyPrayer = Integer.parseInt(inputLine);}
}
if (lineIndex == 8) {
StringTokenizer st = new StringTokenizer(inputLine, ",");
st.nextToken();
inputLine = st.nextToken();
if (Integer.parseInt(inputLine) != -1) {EnemyMage = Integer.parseInt(inputLine);}
}
}
in.close();
} catch (Exception e) {
e.getCause();
log("Stat Reading FAILED! Retry!");
}
}

public int loop() {


/*StringTokenizer st = new StringTokenizer(getInterface(381).getChild(1).getText(), ": ");
wildyString = st.nextToken();
wildyString = st.nextToken();
wildyLvl = Integer.parseInt(wildyString);
getInterface(381).getChild(5).getText();*/

Enemy = getMyPlayer().getInteracting();

if (!(Enemy==null)) {
EnemyS = Enemy.getName();
if (EnemyHp!=1) {
EnemyHpPercent = Enemy.getHPPercent();
EnemyFinalHp =Enemy.getHPPercent()*EnemyHp/10;
} else {
EnemyFinalHp = Enemy.getHPPercent();
}
}

if (Enemy != null && EnemyS!=EnemyOld) {
if (getMyPlayer().getInteracting() instanceof RSNPC) {
} else {
EnemyOld = EnemyS;
getStats(EnemyS);
}
}

LastStr=NewStr;
LastAtk=NewAtk;
LastDef=NewDef;
LastRange=NewRange;

NewStr = skills.getCurrentSkillExp(Constants.STAT_STRENGTH);
NewAtk = skills.getCurrentSkillExp(Constants.STAT_ATTACK);
NewDef = skills.getCurrentSkillExp(Constants.STAT_DEFENSE);
NewRange = skills.getCurrentSkillExp(Constants.STAT_RANGE);

XpGain=(NewStr+NewAtk+NewDef+NewRange)-(LastStr+LastAtk+LastDef+LastRange);
Hit=(XpGain/4)*10;

if (Hit>0 && Hit<4000) {Display=DisplayFor; HitDisplay=Hit;}
if (Display>0) {Display--;}
if (Display<1) {Display=0;}

OldSpec=specPercent;
specPercent = getSetting(300)/10;
if (!(OldSpec==specPercent)) {
if (!(specPercent==100)) {SpecTimer = 30;}
}


SpecPic=Math.round(specPercent/10);
SpecUpdate++;
UpdateVeng++;
if (UpdateVeng>19) {UpdateVeng=0; VenganceTimer--;}
if (VenganceTimer<1) {VenganceTimer=0;}
if (SpecUpdate>19) {SpecUpdate=0; SpecTimer--;}
if (SpecTimer<1) {SpecTimer=0;}


Picture=VenganceTimer/3;

LastMage = NewMage;
NewMage = skills.getCurrentSkillExp(Constants.STAT_MAGIC);

if (NewMage>LastMage) {VenganceTimer=30;}

CurrX=Bot.getClient().getMouse().x;
CurrY=Bot.getClient().getMouse().y;

myAtt=skills.getRealSkillLevel(STAT_ATTACK);
myStr=skills.getRealSkillLevel(STAT_STRENGTH);
myDef=skills.getRealSkillLevel(STAT_DEFENSE);
myRange=skills.getRealSkillLevel(STAT_RANGE);
myPrayer=skills.getRealSkillLevel(STAT_PRAYER);
myMagic=skills.getRealSkillLevel(STAT_MAGIC);
myHp=skills.getRealSkillLevel(STAT_HITPOINTS) * 10;

return 47;
}

public void onFinish() {
log("Hope you fucked some noobs up! WarLord_Burn.");
}

private final RenderingHints rh = new RenderingHints(
RenderingHints.KEY_TEXT_ANTIALIASING,
RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

public void onRepaint (Graphics g) {

if (isLoggedIn()) {

Color C=new Color(255, 0, 0);

if (Picture>8 && VengOn==true) {
g.drawImage(Vengance1, 690, 127, null);
C=new Color(255, 0, 0);
}
if (Picture==8 && VengOn==true) {
g.drawImage(Vengance2, 690, 127, null);
C=new Color(220, 30, 0);
}
if (Picture==7 && VengOn==true) {
g.drawImage(Vengance3, 690, 127, null);
C=new Color(190, 60, 0);
}
if (Picture==6 && VengOn==true) {
g.drawImage(Vengance4, 690, 127, null);
C=new Color(160, 90, 0);
}
if (Picture==5 && VengOn==true) {
g.drawImage(Vengance5, 690, 127, null);
C=new Color(130, 120, 0);
}
if (Picture==4 && VengOn==true) {
g.drawImage(Vengance6, 690, 127, null);
C=new Color(110, 150, 0);
}
if (Picture==3 && VengOn==true) {
g.drawImage(Vengance7, 690, 127, null);
C=new Color(80, 180, 0);
}
if (Picture==2 && VengOn==true) {
g.drawImage(Vengance8, 690, 127, null);
C=new Color(50, 210, 0);
}
if (Picture<2 && VengOn==true) {
g.drawImage(Vengance9, 690, 127, null);
C=new Color(20, 230, 0);
}
if (VenganceTimer<1 && VengOn==true) {
g.drawImage(Vengance10, 690, 127, null);
C=new Color(0, 255, 0);
}

if (SpecPic<2 && SpecOn==true) {
g.drawImage(Spec1, 523, 120, null);
}
if (SpecPic==2 && SpecOn==true) {
g.drawImage(Spec2, 523, 120, null);
}
if (SpecPic==3 && SpecOn==true) {
g.drawImage(Spec3, 523, 120, null);
}
if (SpecPic==4 && SpecOn==true) {
g.drawImage(Spec4, 523, 120, null);
}
if (SpecPic==5 && SpecOn==true) {
g.drawImage(Spec5, 523, 120, null);
}
if (SpecPic==6 && SpecOn==true) {
g.drawImage(Spec6, 523, 120, null);
}
if (SpecPic==7 && SpecOn==true) {
g.drawImage(Spec7, 523, 120, null);
}
if (SpecPic==8 && SpecOn==true) {
g.drawImage(Spec8, 523, 120, null);
}
if (SpecPic==9 && SpecOn==true) {
g.drawImage(Spec9, 523, 120, null);
}
if (SpecPic==10 && SpecOn==true) {
g.drawImage(Spec10, 523, 120, null);
}

int CurrX=Bot.getClient().getMouse().x;
int CurrY=Bot.getClient().getMouse().y;

if (Bot.getClient().getMouse().pressed && canClick==true  && CurrX>600 && CurrX<663 && CurrY>290  && CurrY<320 && displayY==0  &&  getCurrentTab() == 12) {
displayY=60;
canClick=false;
}
if (CurrX>600 && CurrX<663 && CurrY>290  && CurrY<320 && canClick==true &&  Bot.getClient().getMouse().pressed && !(displayY==0) &&  getCurrentTab() == 12) {
displayY=0;
canClick=false;
}

if (!(Bot.getClient().getMouse().pressed)) {canClick=true;}

if (displayY==0 && getCurrentTab() == 12) {
g.drawImage(Button1, 600, 290, null);
}

if (!(displayY==0)) {
g.drawImage(Paint3Holder, 4, 309, null);
if (getCurrentTab() == 12) {
g.drawImage(Button2, 600, 290, null);
}

if (Display>0) {
g.setFont(new Font("Aharoni", 0, 30));
g.setColor(new Color(0, 0, 0, 160));
g.drawString(""+HitDisplay, 12, 332);
g.setColor(new Color(255, 255, 255));
g.drawString(""+HitDisplay, 10, 330);
}

g.setFont(new Font("Aharoni", 0, 16));

g.setColor(new Color(0, 0, 0, 160));
g.drawString(""+myAtt, 125,320);
if (myAtt>EnemyAtk) {
g.setColor(new Color(100, 255, 100));
}
if (myAtt==EnemyAtk) {
g.setColor(new Color(100, 100, 255));
}
if (myAtt<EnemyAtk) {
g.setColor(new Color(255, 100, 100));
}
g.drawString(""+myAtt, 124,319);

if (!(EnemyAtk==1)) {

g.setColor(new Color(0, 0, 0, 160));
g.drawString(""+EnemyAtk, 136, 334);
if (EnemyAtk>myAtt) {
g.setColor(new Color(100, 255, 100));
}
if (myAtt==EnemyAtk) {
g.setColor(new Color(100, 100, 255));
}
if (EnemyAtk<myAtt) {
g.setColor(new Color(255, 100, 100));
}
g.drawString(""+EnemyAtk, 135, 333);

}

g.setColor(new Color(0, 0, 0, 160));
g.drawString(""+myStr, 186,321);
if (myStr>EnemyStr) {
g.setColor(new Color(100, 255, 100));
}
if (myStr==EnemyStr) {
g.setColor(new Color(100, 100, 255));
}
if (myStr<EnemyStr) {
g.setColor(new Color(255, 100, 100));
}
g.drawString(""+myStr, 185,320);

if (!(EnemyStr==1)) {

g.setColor(new Color(0, 0, 0, 160));
g.drawString(""+EnemyStr, 196, 334);
if (EnemyStr>myStr) {
g.setColor(new Color(100, 255, 100));
}
if (myStr==EnemyStr) {
g.setColor(new Color(100, 100, 255));
}
if (EnemyStr<myStr) {
g.setColor(new Color(255, 100, 100));
}
g.drawString(""+EnemyStr, 195, 333);

}

g.setColor(new Color(0, 0, 0, 160));
g.drawString(""+myDef, 246,321);
if (myDef>EnemyDef) {
g.setColor(new Color(100, 255, 100));
}
if (myDef==EnemyDef) {
g.setColor(new Color(100, 100, 255));
}
if (myDef<EnemyDef) {
g.setColor(new Color(255, 100, 100));
}
g.drawString(""+myDef, 245,320);

if (!(EnemyDef==1)) {
g.setColor(new Color(0, 0, 0, 160));
g.drawString(""+EnemyDef, 255, 334);
if (EnemyDef>myDef) {
g.setColor(new Color(100, 255, 100));
}
if (myDef==EnemyDef) {
g.setColor(new Color(100, 100, 255));
}
if (EnemyDef<myDef) {
g.setColor(new Color(255, 100, 100));
}
g.drawString(""+EnemyDef, 255, 333);
}

g.setColor(new Color(0, 0, 0, 160));
g.drawString(""+myRange, 426,321);
if (myRange>EnemyRange) {
g.setColor(new Color(100, 255, 100));
}
if (myRange==EnemyRange) {
g.setColor(new Color(100, 100, 255));
}
if (myRange<EnemyRange) {
g.setColor(new Color(255, 100, 100));
}
g.drawString(""+myRange, 425,320);

if (!(EnemyRange==1)) {
g.setColor(new Color(0, 0, 0, 160));
g.drawString(""+EnemyRange, 436, 334);
if (EnemyRange>myRange) {
g.setColor(new Color(100, 255, 100));
}
if (myRange==EnemyRange) {
g.setColor(new Color(100, 100, 255));
}
if (EnemyRange<myRange) {
g.setColor(new Color(255, 100, 100));
}
g.drawString(""+EnemyRange, 435, 333);
}

g.setColor(new Color(0, 0, 0, 160));
g.drawString(""+myPrayer, 486,321);
if (myPrayer>EnemyPrayer) {
g.setColor(new Color(100, 255, 100));
}
if (myPrayer==EnemyPrayer) {
g.setColor(new Color(100, 100, 255));
}
if (myPrayer<EnemyPrayer) {
g.setColor(new Color(255, 100, 100));
}
g.drawString(""+myPrayer, 485,320);

if (!(EnemyPrayer==1)) {
g.setColor(new Color(0, 0, 0, 160));
g.drawString(""+EnemyPrayer, 496, 334);
if (EnemyPrayer>myPrayer) {
g.setColor(new Color(100, 255, 100));
}
if (myPrayer==EnemyPrayer) {
g.setColor(new Color(100, 100, 255));
}
if (EnemyPrayer<myPrayer) {
g.setColor(new Color(255, 100, 100));
}
g.drawString(""+EnemyPrayer, 495, 333);
}

g.setColor(new Color(0, 0, 0, 160));
g.drawString(""+myMagic, 366,321);
if (myMagic>EnemyMage) {
g.setColor(new Color(100, 255, 100));
}
if (myMagic==EnemyMage) {
g.setColor(new Color(100, 100, 255));
}
if (myMagic<EnemyMage) {
g.setColor(new Color(255, 100, 100));
}
g.drawString(""+myMagic, 365,320);

if (!(EnemyMage==1)) {
g.setColor(new Color(0, 0, 0, 160));
g.drawString(""+EnemyMage, 373, 333);
if (EnemyMage>myMagic) {
g.setColor(new Color(100, 255, 100));
}
if (myMagic==EnemyMage) {
g.setColor(new Color(100, 100, 255));
}
if (EnemyMage<myMagic) {
g.setColor(new Color(255, 100, 100));
}
g.drawString(""+EnemyMage, 372, 332);
}

g.setColor(new Color(0, 0, 0, 160));
g.drawString(""+myHp, 301,321);
if (myHp>EnemyHp) {
g.setColor(new Color(100, 255, 100));
}
if (myHp==EnemyHp) {
g.setColor(new Color(100, 100, 255));
}
if (myHp<EnemyHp) {
g.setColor(new Color(255, 100, 100));
}
g.drawString(""+myHp, 300,320);

if (!(EnemyHp==1)) {
EnemyHp=EnemyHp*10;
g.setColor(new Color(0, 0, 0, 160));
g.drawString(""+EnemyHp, 312, 333);
if (EnemyHp>myHp) {
g.setColor(new Color(100, 255, 100));
}
if (myHp==EnemyHp) {
g.setColor(new Color(100, 100, 255));
}
if (EnemyHp<myHp) {
g.setColor(new Color(255, 100, 100));
}
g.drawString(""+EnemyHp, 311, 332);
EnemyHp=EnemyHp/10;

}
}

g.drawImage(Report, 404, 481, null);

g.setFont(new Font("Aharoni", 0, 16));
g.setColor(new Color(255, 255, 255));
g.drawString(""+EnemyS, 411, 497);

g.setFont(new Font("Aharoni", 0, 24));

if (specPercent>99 && SpecOn==true) {

g.setColor(new Color(0, 0, 0));
g.drawString(""+specPercent, 528, 145);
g.setColor(new Color(255, 255, 255));
g.drawString(""+specPercent, 527, 144);
}

if (specPercent>9 && specPercent<100 && SpecOn==true) {

g.setColor(new Color(0, 0, 0));
g.drawString(""+specPercent, 532, 145);
g.setColor(new Color(255, 255, 255));
g.drawString(""+specPercent, 531, 144);

}

if (specPercent<10 && SpecOn==true) {

g.setColor(new Color(0, 0, 0));
g.drawString(""+specPercent, 536, 145);
g.setColor(new Color(255, 255, 255));
g.drawString(""+specPercent, 535, 144);

}

if (SpecOn==true) {

g.setFont(new Font("Aharoni", 0, 13));
g.setColor(new Color(0, 0, 0));

g.drawString(""+SpecTimer+" ", 539, 153);
g.setColor(new Color(255, 255, 255));
g.drawString(""+SpecTimer, 538, 152);
g.setColor(new Color(114, 107, 89));
g.fillRect(725,143,16,10);
}

g.setFont(new Font("Aharoni", 0, 20));

if (VenganceTimer>9 && VengOn==true) {
g.setColor(new Color(0, 0, 0));
g.drawString(""+VenganceTimer, 723, 153);
g.setColor(C);
g.drawString(""+VenganceTimer, 724, 152);
}
if (VenganceTimer<10 && VengOn==true) {
g.setColor(new Color(0, 0, 0));
g.drawString(""+VenganceTimer, 728, 153);
g.setColor(C);
g.drawString(""+VenganceTimer, 727, 152);
}

if (Enemy!=null) {


g.setFont(new Font("Aharoni", 0, 24));

Point M = Enemy.getLocation().getScreenLocation();

if (pointOnScreen(M)) {
if (EnemyFinalHp>100) {
int G=55+EnemyHpPercent*2;
int R=200-EnemyHpPercent*2;
int EnemyTempHp=EnemyHp*10;
int TempPitch = -34 + (Bot.getClient().getCameraPitch())/30;
g.setColor(new Color(0, 0, 0));
g.drawString(""+EnemyFinalHp+" / "+EnemyTempHp,M.x-22,67+M.y-180+TempPitch);
g.setColor(new Color(R, G, 0));
g.drawString(""+EnemyFinalHp+" / "+EnemyTempHp,M.x-20,65+M.y-180+TempPitch);
}
if (EnemyHp==1) {
int G=55+EnemyHpPercent*2;
int R=200-EnemyHpPercent*2;
int TempPitch = -34 + (Bot.getClient().getCameraPitch())/30;
g.setColor(new Color(0, 0, 0));
g.drawString("Hp: "+EnemyFinalHp+"%",M.x-20,65+M.y-180+TempPitch);
g.setColor(new Color(R, G, 0));
g.drawString("Hp: "+EnemyFinalHp+"%",M.x-22,67+M.y-180+TempPitch);
}
}
}
CurrX=Bot.getClient().getMouse().x;
CurrY=Bot.getClient().getMouse().y;
}
}
}