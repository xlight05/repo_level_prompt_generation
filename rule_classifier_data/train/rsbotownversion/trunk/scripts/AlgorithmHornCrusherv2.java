import java.util.Map;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSInterface;
import org.rsbot.script.wrappers.RSInterfaceChild;
import org.rsbot.script.wrappers.RSNPC;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.script.GrandExchange;


@ScriptManifest(authors = { "Algorithm" }, category = "Money-Making", name = "Horn Crusher v2", version = 1.1, description = "<html><body style=\"background-color: #000000; color: #FFFFFF;"
    + "font-family: Arial; text-align: center;\">"
    + "<h2 style=\"margin-bottom: 0px;\">Horn Crusher V1.2</h2>"
    + "<h2 style=\"margin-bottom: 0px;\">Payed version</h2>"
    + "<strong>By Algorithm</strong><br /><br />"
    + "Script currently only works at G.E<br />"
    + "Start at G.E with bank closed<br />"
    + "<small>I hope you enjoy.</small><br />")


public class AlgorithmHornCrusherv2 extends Script implements PaintListener, ServerMessageListener {
    
    //Declaring variables
    private long scriptStartTime;

    public int pestle = 233;
    //Pestle ID
    
    public int horn = 9735;
    //Goat horn id
    
    public int bankbooth = 0;
    
    public int crushedhorn = 9736;
    //id of crushed horn
    
    public int horncrushed;
    //Counter for how many crushed
    
    public String crushstatus = "";
    
    public int banked = 0;
    
    public int starting = 1;
    
    public int needstobank = 1;
    
    private RSInterfaceChild crush_area = RSInterface.getChildInterface(513, 3);
    
    private int banknpc[] = { 6533, 6535, 6529, 6528};
    
    private RSNPC banker = getNearestNPCByID(banknpc);

    public int bankisopen = 0;

    public int DustPrice = grandExchange.loadItemInfo(crushedhorn).getMarketPrice();
    public int HornPrice = grandExchange.loadItemInfo(horn).getMarketPrice(); 
    
    public int openBank() {
        if(banker != null && !bank.isOpen() && needstobank == 1) {
            atNPC(banker, "Bank ");
            wait(random(800, 1000));
            moveMouseSlightly();
            wait(random(500, 800));
            bank.depositAllExcept(pestle);
            wait(random(700, 1000));
            moveMouseSlightly();
            bank.atItem(horn, "Withdraw-All");
            wait(random(700, 1000));
            bankisopen = 1;
            if (bankisopen == 1) {
                bank.close();
                if (!bank.isOpen()) {
                    bankisopen = 0;;
                }
            }

            needstobank = 0;

            
        }
        return 0;
    }
        
    
    public boolean onStart(final Map<String, String> args) {
        scriptStartTime = System.currentTimeMillis();
        log("We're gonna say horns cost you : " + HornPrice);
        log("We're gonna say dust sells for : " + DustPrice);
        log("Which means a profit of " + (DustPrice - HornPrice) + " per horn");
        return true;
    }
    
    public int loop() {
        openBank();
        if (inventoryContains(pestle) && (inventoryContains(horn) && (getMyPlayer().getAnimation() == -1) && (getInventoryCount(crushedhorn) == 0))) {
            atInventoryItem(pestle, "Use");
            wait(random(800, 1000));
            if(isItemSelected()){
                atInventoryItem(horn, "Use");
                wait(random(800, 1000));
                if(crush_area != null){
                    moveMouse(244, 394, 10, 20);
                    wait(random(500, 800));
                    atMenu("Make All");        
                    wait(random(2000, 3000));
                } else {
                    moveMouse(random(650, 660), random(180, 190));
                    clickMouse(true);
                }
                if (getInventoryCount(horn) == 0) {
                    needstobank = 1;
                    log.info("needs to bank");
                    openBank();
                }
                }
            } 
        if (getInventoryCount(crushedhorn) == 27 || getInventoryCount(horn) == 0) {
                needstobank = 1;
                openBank();
            }
        return 0;

    }
    
    
    

    public void serverMessageRecieved(ServerMessageEvent e) {
        String word = e.getMessage().toLowerCase();
        if (word.contains("dust")) {
            banked++;
        }
    }

    public void onRepaint(Graphics g) {
        
    int profit = 0;
        long runTime = 0;
        long seconds = 0;
        long minutes = 0;
        long hours = 0;
        runTime = System.currentTimeMillis() - scriptStartTime;
        profit = ((DustPrice - HornPrice) * banked); 
        seconds = runTime / 1000;
        if ( seconds >= 60 ) {
            minutes = seconds / 60;
            seconds -= (minutes * 60);
        }
        if ( minutes >= 60 ) {
            hours = minutes / 60;
            minutes -= (hours * 60);
        }
            g.setColor(new Color(0, 0, 0, 175));    
            g.fillRoundRect(555, 210, 175, 250, 10, 10);
            g.setColor(Color.WHITE);
                
            int[] coords = new int[] {225, 240, 255, 270, 285, 300, 315, 330, 345, 360, 375, 390, 405, 420, 435, 450};
            
            g.drawString("Algorithm's Horn Crusher v2 ", 561, coords[1]);
            g.drawString("Run Time: " + hours + ":" + minutes + ":" + seconds, 561, coords[2]);
            g.drawString("Horns crushed: " + (banked), 561, coords[3]);
            g.drawString("You've made " + profit + " GP", 561, coords[4]);
        }
    

    }