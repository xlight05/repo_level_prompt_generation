import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;  
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.util.Map;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Point;

import org.rsbot.event.listeners.PaintListener;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;


import org.rsbot.script.wrappers.RSTile;

 

import org.rsbot.bot.Bot;
import org.rsbot.bot.input.Mouse;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.script.Calculations;
import org.rsbot.script.Constants;
import org.rsbot.script.Methods;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSInterface;
import org.rsbot.script.wrappers.RSItemTile;
import org.rsbot.script.wrappers.RSNPC;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSTile;
import java.awt.Point; 
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Map;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.script.Bank;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.Skills;
import org.rsbot.script.Calculations;
import org.rsbot.script.wrappers.RSItemTile;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSTile;
import org.rsbot.script.wrappers.RSInterface;

@ScriptManifest(authors = "Tnwrestler360", category = "Woodcutting", name = "Draynor Willowz V3", version = 3.0, description = "<html><head>" +
        "</head><body>" +
        "<b> Draynor Willowz V3 </b>" +
        "<br>by <i>Tnwrestler aka RIPfagex</i>" +
        "<br><b>Would you like to power chop?</b> <select name='Power'><option>No<option>Yes"+
        "<br><b>Are you weilding your axe?</b> <select name='Axe'><option>Yes<option>No"+
        "<br><b>Mouse speed?</b> <select name='Speed'><option>1<option>2<option>3<option>4<option>5<option>6<option>7<option>8<option>9<option>10<option>11<option>12<option>13<option>14<option>15"+
        "<b>to</b> <select name='Speed2'><option>1<option>2<option>3<option>4<option>5<option>6<option>7<option>8<option>9<option>10<option>11<option>12<option>13<option>14<option>15"+
        "<br>These variables will set your mouse speed to the range you input. Rsbot default is 10" +
        "<br>*Please note* First int must be smaller then second int, if it's not the script will return an error *Please note*" +
        "</body></html>")
public class DraynorWillowzV3 extends Script implements PaintListener{

    public RSObject tree;

    public int chopped;
    public int bankerID [] = {2213};
    RSTile[] bankToTrees = {new RSTile(3092, 3243), new RSTile(3087, 3237)
};
    public int Bank;
    RSTile[] treesToBank = reversePath(bankToTrees);
    public int logID = 1515;
    public int[] hatchetID = {6739,1359, 1357, 1361, 1351, 1349, 1355, 1353};
    public int wcAnimation = 867;
    RSTile trees = new RSTile(3086,3232);
    int sAdj;
    public long startTime = System.currentTimeMillis();
    public int startexp;
    public int exp;
    public int expGained;
    public int energyToRunAt = random(40, 100);
    public int speed = 10;
    private int[] nestID = {5070, 5071, 5072, 5073, 5074, 5075, 5076, 7413, 11966};
    private int mouseSpeed1;
    private int mouseSpeed2;
    public int currentEXP;
    public int currentLVL;
    public int nextLVL;
    RSTile[] treeLocs = new RSTile[] {new RSTile(3084, 3238), new RSTile(3086, 3236), new RSTile(3089, 3234), new RSTile(3088, 3232), new RSTile (3089, 3227)};
    int[] willowTree = new int[] { 5551, 5553, 5552};
    int[] treeID = new int []{5551, 5553, 5552, 5551};
    int lastTreeIndex;
    DraynorWillowzAntiBan antiban;
    Thread t;
    public int weilding;

    
    ///on-start//////
    public boolean onStart( Map<String,String> args ) {
         URLConnection url = null;
         BufferedReader in = null;
         BufferedWriter out = null;
         //Ask the user if they'd like to check for an update...
         if(JOptionPane.showConfirmDialog(null, "Would you like to check for updates?\nPlease Note this requires an internet connection and the script will write files to your harddrive!") == 0){ //If they would, continue
             try{
                 //Open the version text file
                 url = new URL("http://draynorwillowz.webs.com/scripts/DraynorWillowzV2VERSION.txt").openConnection();
                 //Create an input stream for it
                 in = new BufferedReader(new InputStreamReader(url.getInputStream()));
                 //Check if the current version is outdated
                 if(Double.parseDouble(in.readLine()) > getVersion()) {
                     //If it is, check if the user would like to update.
                     if(JOptionPane.showConfirmDialog(null, "Update found. Do you want to update?") == 0){
                         //If so, allow the user to choose the file to be updated.
                            JOptionPane.showMessageDialog(null, "Please choose 'DraynorWillowzV2.java' in your scripts folder and hit 'Open'");
                            JFileChooser fc = new JFileChooser();
                            //Make sure "Open" was clicked.
                            if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                                //If so, set up the URL for the .java file and set up the IO.
                                url = new URL("http://draynorwillowz.webs.com/scripts/DraynorWillowzV2.java").openConnection();
                             in = new BufferedReader(new InputStreamReader(url.getInputStream()));
                             out = new BufferedWriter(new FileWriter(fc.getSelectedFile().getPath()));
                             String inp;
                             /* Until we reach the end of the file, write the next line in the file
                              * and add a new line. Then flush the buffer to ensure we lose
                              * no data in the process.
                              */
                             while((inp = in.readLine()) != null){
                                 out.write(inp);
                                 out.newLine();
                                 out.flush();
                             }
                             //Notify the user that the script has been updated, and a recompile and reload is needed.
                                log("Script successfully downloaded. Please recompile and reload your scripts!");
                             return false;
                            } else log("Update canceled");
                     } else log("Update canceled");
                 } else
                     JOptionPane.showMessageDialog(null, "You have the latest version. :)"); //User has the latest version. Tell them!
                 if(in != null)
                     in.close();
                 if(out != null)
                     out.close();
             } catch (IOException e){
                 log("Problem getting version :/");
                 return false; //Return false if there was a problem
             }
         }  
         antiban = new DraynorWillowzAntiBan();
         t = new Thread(antiban);
        if (args.get("Power").equals("Yes")){
            
            Bank = 0;
            
        }
        if (args.get("Power").equals("No")){
                Bank = 1;
                
            }
        if (args.get("Speed").equals("1")){
            mouseSpeed1 = 1;
            
        }
        if (args.get("Speed").equals("2")){
            mouseSpeed1 = 2;
            
        }
        if (args.get("Speed").equals("3")){
            mouseSpeed1 = 3;
            
        }
        if (args.get("Speed").equals("4")){
            mouseSpeed1 = 4;
            
        }
        if (args.get("Speed").equals("5")){
            mouseSpeed1 = 5;
            
        }
        if (args.get("Speed").equals("6")){
            mouseSpeed1 = 6;
            
        }
        if (args.get("Speed").equals("7")){
            mouseSpeed1 = 7;
            
        }
        if (args.get("Speed").equals("8")){
            mouseSpeed1 = 8;
            
        }
        if (args.get("Speed").equals("9")){
            mouseSpeed1 = 9;
            
        }
        if (args.get("Speed").equals("10")){
            mouseSpeed1 = 10;
            
        }
        if (args.get("Speed").equals("11")){
            mouseSpeed1 = 11;
            
        }
        if (args.get("Speed").equals("12")){
            mouseSpeed1 = 12;
            
        }if (args.get("Speed").equals("13")){
            mouseSpeed1 = 13;
            
        }if (args.get("Speed").equals("14")){
            mouseSpeed1 = 14;
            
        }if (args.get("Speed").equals("15")){
            mouseSpeed1 = 15;
            
        }
        if (args.get("Speed2").equals("1")){
            mouseSpeed2 = 1;
            
        }
        if (args.get("Speed2").equals("2")){
            mouseSpeed2 = 2;
            
        }
        if (args.get("Speed2").equals("3")){
            mouseSpeed2 = 3;
            
        }
        if (args.get("Speed2").equals("4")){
            mouseSpeed2 = 4;
            
        }
        if (args.get("Speed2").equals("5")){
            mouseSpeed2 = 5;
            
        }
        if (args.get("Speed2").equals("6")){
            mouseSpeed2 = 6;
            
        }
        if (args.get("Speed2").equals("7")){
            mouseSpeed2 = 7;
            
        }
        if (args.get("Speed2").equals("8")){
            mouseSpeed2 = 8;
            
        }
        if (args.get("Speed2").equals("9")){
            mouseSpeed2 = 9;
            
        }
        if (args.get("Speed2").equals("10")){
            mouseSpeed2 = 10;
            
        }
        if (args.get("Speed2").equals("11")){
            mouseSpeed2 = 11;
            
        }
        if (args.get("Speed2").equals("12")){
            mouseSpeed2 = 12;
            
        }if (args.get("Speed2").equals("13")){
            mouseSpeed2 = 13;
            
        }if (args.get("Speed2").equals("14")){
            mouseSpeed2 = 14;
            
        }if (args.get("Speed2").equals("15")){
            mouseSpeed2 = 15;
            
        }
        if(args.get("Axe").equals("Yes")){
            weilding = 1;
            
        }else if(args.get("Axe").equals("No")){
            weilding = 0;
        }
        
        
        return true;
    }
    
    public int closestTree(final RSTile[] tiles) {
        int closest = -1;
        for (int i = 0; i < tiles.length; i++) {
            final RSTile tile = tiles[i];
            if (getObjectAt(tile) != null) {
                if (closest == -1 && isTree(getObjectAt(tile).getID())) {
                    closest = i;
                    continue;
                }
                if (closest == -1) {
                    continue;
                }
                if (isTree(getObjectAt(tile).getID())
                        && distanceTo(getObjectAt(tile)) < distanceTo(getObjectAt(tiles[closest]))) {
                    closest = i;
                }
            }
        }
        return closest;
    }

    public boolean isTree(final int treeID) {
        for(final int id : willowTree) {
            if (id == treeID) {
                return true;
            }
        }
        return false;
    }


    

    
    
    
    private double getVersion() {
        
        return 3.0;
    }


    





    public int gatherNest(){
        RSItemTile birdNest = getGroundItemByID(nestID);
        if (birdNest != null && !isInventoryFull()){
            atTile(birdNest, "Take ");
            wait(random(1000, 1500));
        }
        return 100;
    }
    private boolean openBank() {
        final RSObject bank = getNearestObjectByID(bankerID);
        if (bank == null) {
            return false;
        }
        if (!tileOnScreen(bank.getLocation())) {
            wait(random(50,100));
            
        }
        return atTile(bank.getLocation(), "Use-quickly");
    }

    

        
        

public void antiBan() {
        
        int random = random(1, 24);
        switch (random) {
        case 1:
            if (random(1, 13) != 1)
                break;
            moveMouse(random(10, 750), random(10, 495));
            break;
        
        case 2:
            if (random(1, 4) != 1)
                break;
            moveMouseSlightly();
            break;
        case 3:
            if(random(1, 4) != 1)
                break;
            if(getMyPlayer().getAnimation() == wcAnimation){
            openTab(TAB_STATS);
                moveMouse(random(676, 731), random(351, 372));
                wait(random(2000, 2500));
                openTab(TAB_INVENTORY);
            }
                break;
        case 4: 
            if(random(1, 7) != 1)
                break;
            moveMouse(random(30, 103), random(48, 189));
        
        default:
            break;
        }
        
    }
    
    

    

    
    public void onFinish() {
        antiban.stopThread = true;
    }
    
    public void bankAll() {
        if (RSInterface.getInterface(Constants.INTERFACE_BANK).isValid()) {
            if(weilding == 0){
            
            bank.depositAllExcept(hatchetID);
            wait(random(100, 200));
            }
        }
        
    }
    
    public void quickDep(){
        if (RSInterface.getInterface(Constants.INTERFACE_BANK).isValid()) {
            if(weilding == 1){
                clickMouse(random(381, 409), random(298, 317), true);
                wait(random(100, 150));
            }
        }
    }
    
    @Override
    protected int getMouseSpeed() { 
        return random(mouseSpeed1, mouseSpeed2);
    }
    
    
   
    public void checkRun(){
        if(getEnergy() >= energyToRunAt){
            setRun(true);
        }
    }
    
    private void paintPlayer(Graphics g) {
    RSTile paint = getMyPlayer().getLocation();
    int x = paint.getX();
    int y = paint.getY();
    g.setColor(Color.GREEN);
    g.fillRect(x - 1, y - 1, 4, 4);
    
    }

    
public int loop() {
    if (!t.isAlive()) {
        t.start();
        log("AntiBan initialized!");
}

   
   
   
    if(Bank == 1){
    gatherNest();
    }
    while(getMyPlayer().isMoving()){
        return random(200,250);
    }
    if(getMyPlayer().getAnimation() != -1){
        antiBan();
       return random(300, 500);
    }
    if(isInventoryFull()){
        if(Bank == 1){
        walkPathMM(treesToBank, 2, 5);
        if (RSInterface.getInterface(Constants.INTERFACE_BANK).isValid()) {
            if(isInventoryFull()){
            bankAll();
            
            }
            if(isInventoryFull()){
            quickDep();
            
            }
        } else {
            openBank();
        }
        if(!inventoryEmptyExcept(hatchetID)){
            return 500;
        }else{
            log("Succesfully banked all logs");
        }
        }else{
            dropAllExcept(hatchetID);
            if(!inventoryEmptyExcept(hatchetID)){
                return 500;
            }else{
                log("Succesfully dropped all logs");
            }
        }
    }else{
       if(distanceTo(trees) >= 8){
           walkPathMM(bankToTrees, 3, 3);
       }
            //cred to draggin   
        if (Methods.distanceBetween(getMyPlayer().getLocation(),
                treeLocs[1]) <= 15) {
            if (!isInventoryFull()) {
                final int treeIndex = closestTree(treeLocs);
                if (treeIndex != -1
                        && distanceTo(treeLocs[treeIndex]) <= 14) {
                    if (inventoryContains(1519)) {
                        if (getMyPlayer().getAnimation() != -1
                                && lastTreeIndex == treeIndex) {
                            return random(100, 200);
                        }
                    }
                    lastTreeIndex = treeIndex;
                    if (tileOnScreen(treeLocs[treeIndex])
                            && getMyPlayer().getAnimation() == -1) {
                        atTile(treeLocs[treeIndex], "Chop");
                        return random(700, 1000);
                    }
                    if (getMyPlayer().getAnimation() == -1) {

                        walkTileMM(new RSTile(treeLocs[treeIndex].getX(),
                                treeLocs[treeIndex].getY() - 1));
                    }
                    return random(350, 700);
                }
            }

        
    }
    //end cred to draggin
    
    
    
    } 
    return random(800, 1000);
}


public void onRepaint(Graphics g) {
    ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
     if (isLoggedIn()) {
         
         if ( startexp == 0) {
            startexp = skills.getCurrentSkillExp(Constants.STAT_WOODCUTTING);
       }
         
            
       expGained = skills.getCurrentSkillExp(Constants.STAT_WOODCUTTING) - startexp;

   chopped = expGained/68;
           int height;
           int width;
     drawMouse(g);
     paintPlayer(g);
     Mouse m = Bot.getClient().getMouse();
     if(m.x >= 549 && m.x < 549 + 61 && m.y >= 10 && m.y < 207 + 13){
         
         g.setColor(Color.WHITE);
         g.fillRect(549, 207, 67, 13);
         g.setColor(new Color(0, 0, 0, 140));
         g.drawString("Hide Paint", 552, 218);
         
         currentEXP = skills.getXPToNextLevel(Constants.STAT_WOODCUTTING);
         currentLVL = skills.getCurrentSkillLevel(Constants.STAT_WOODCUTTING);
         
                     
         long millis = System.currentTimeMillis() - startTime;
         long hours = millis / (1000 * 60 * 60);
         millis -= hours * (1000 * 60 * 60);
         long minutes = millis / (1000 * 60);
         millis -= minutes * (1000 * 60);
         long seconds = millis / 1000;
         long minutes2 = minutes + (hours * 60);
         double h = (3600 * hours) + (60 * minutes) + seconds;
         double logsPerSecond = ((68 * chopped) / h);
         double logsHour = (int) ((logsPerSecond * 3600) /68);
        double XPPerHour = (int) (logsPerSecond * 3600);
        double logsToLvl = (int) (currentEXP /68);
        nextLVL = currentLVL + 1;
        height = 256;
         width = 186;
         g.setColor(new Color(0, 0, 0, 140));
        g.fillRect(549, 207, width, height);
        g.setColor(new Color(47, 47, 89, 200));
        g.setColor(Color.WHITE);
        g.setFont(new Font("Palatino Linotype", Font.BOLD, 22));
         g.drawString("Draynor Willowz", 554, 237);
         g.setFont(new Font("Palatino Linotype", Font.ITALIC, 13));
         g.drawString("by Tnwrestler" , 635, 261);
        g.setFont(new Font("Trajan Pro", Font.PLAIN, 16));
        g.drawString("Time running:"+hours+":"+minutes+":"+seconds+"." , 566, 309);
        g.drawString("XP/h:"+XPPerHour+"." , 566, 329);
        g.drawString("EXP Gained:"+expGained , 566, 349);
        g.drawString("Logs Chopped:"+ chopped +"." ,566, 369);
        g.setFont(new Font("Palatino Linotype", Font.PLAIN, 13));
        g.setColor(Color.RED);  
        g.fill3DRect(566, 378, 100, 11, true); 
        g.setColor(Color.GREEN); 
        g.fill3DRect(566, 378, skills.getPercentToNextLevel(Constants.STAT_WOODCUTTING), 11, true);  
         g.setColor(Color.BLACK); 
        g.drawString(skills.getPercentToNextLevel(Constants.STAT_WOODCUTTING) + "%  to " + (skills.getCurrentSkillLevel(Constants.STAT_WOODCUTTING) + 1), 586, 388);
        g.setFont(new Font("Trajan Pro", Font.PLAIN, 16));
        g.setColor(Color.WHITE);
        g.drawString(+logsToLvl+" logs to "+nextLVL+".", 566, 408 );
     }else{
         height = 0;
         width = 0;
         g.setColor(new Color(0, 0, 0, 140));
         g.fillRect(549, 207, 67, 13);
         g.setColor(Color.WHITE);
         g.drawString("Show Paint", 552, 218);
         
     }
         
        
        
        
        
        
        
    }
}
private void drawMouse(final Graphics g) {
    final Point loc = getMouseLocation();
    if (System.currentTimeMillis()
            - Bot.getClient().getMouse().getMousePressTime() < 500) {
        g.setColor(new Color(0, 0, 0, 50));
        g.fillOval(loc.x - 5, loc.y - 5, 10, 10);
    } else {
        g.setColor(Color.BLACK);
    }
    g.drawLine(0, loc.y, 766, loc.y);
    g.drawLine(loc.x, 0, loc.x, 505);
}
private class DraynorWillowzAntiBan implements Runnable {
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