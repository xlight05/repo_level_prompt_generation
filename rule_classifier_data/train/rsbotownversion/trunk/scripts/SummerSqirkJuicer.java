import java.awt.Color;
import java.awt.Graphics;

import org.rsbot.event.listeners.PaintListener;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSObject;

@ScriptManifest(authors = "Ploxxii", category = "@Mini-Game", name = "Summer Sqirk Juicer", version = 1.0, description = "Start at any bank with Summer Sq'irk Fruit, Beer glass and Pestle and mortar in one tab.")
public class SummerSqirkJuicer extends Script implements PaintListener{
    
    public int fruitID = 10845;
    public int pestleMortarID = 233;
    public int beerglassID = 1919;
    public int bankboothID = 36786;
    public int juiceID = 10849;
    public int startingJuice = bank.getCount(juiceID);
    public int fruitsGained = bank.getCount(startingJuice) - bank.getCount(juiceID);
    public long startTime = System.currentTimeMillis();
    
    public boolean onStart(){
        startTime = System.currentTimeMillis();
        log("Thank you for using my script :)");
        return true;
        
    }
    
    public void onRepaint(Graphics g) {
        if (isLoggedIn()) {
            long millis = System.currentTimeMillis() - startTime;
            long hours = millis / (1000 * 60 * 60);
            millis -= hours * (1000 * 60 * 60);
            long minutes = millis / (1000 * 60);
            millis -= minutes * (1000 * 60);
            long seconds = millis / 1000;
            g.setColor(Color.red);
            g.drawString("Time Running: " + hours + ":" + minutes + ":" + seconds, 9, 315);
            g.drawString("Juices Made: " + fruitsGained, 9, 299);
            g.drawString("Made By: Ploxxii" , 9, 327);
        }
    }
    
    public void banking1(){
        if(getInventoryCount(fruitID) == 0){
            bank.open();
            bank.depositAllExcept(pestleMortarID);
            bank.withdraw(beerglassID, 9);
            bank.withdraw(fruitID, 18);
            bank.withdraw(pestleMortarID, 1);
            bank.close();
                    }
    }
    public void banking2(){
        if(getInventoryCount(fruitID) == 0){
        bank.open();
        bank.depositAllExcept(pestleMortarID);
        bank.withdraw(beerglassID, 9);
        wait(250);
        bank.atItem(fruitID, "Withdraw-All");
        bank.withdraw(pestleMortarID, 1);
        bank.close();
                }
    }
    
    public void juicing(){
        if(getInventoryCount(fruitID) >= 2){
            if(getInventoryCount(beerglassID) >= 1){
            while(inventoryContains(fruitID)) {
                atInventoryItem(pestleMortarID, "Use");
                wait(500);
                atInventoryItem(fruitID, "Use");
                }
            }
        }
    }
    
    
    public int loop() {
        if(getInventoryCount(pestleMortarID) == 0){
            banking1();
            juicing();
        }
        banking2();
        juicing();
        return 500;
        
    }
}