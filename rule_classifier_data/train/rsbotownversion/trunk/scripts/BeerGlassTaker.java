import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.*;

import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSArea;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSTile;

@ScriptManifest(authors = {"Ploxxii"}, category = "Money-Making", name = "Beer Glass Taker", version = 1.0, description = ("Start at Al Kharid bank with Inventory empty"))
    public class BeerGlassTaker extends Script implements PaintListener, ServerMessageListener{
        
        //Here is all the integers
        public int BeerGlassID = 1919;
        public int BankID = 35647;
        public int ShelfID = 21794;
        public int GlassTaken = 0;
        public long startTime = System.currentTimeMillis();
            
            
            
            //Here is all the RSTiles,RSObjects, RSNPC and so on...
            RSTile[] ToHouse = { new RSTile(3277, 3159), new RSTile(3284, 3153), new RSTile(3295, 3148), new RSTile(3306, 3146), new RSTile(3316, 3144), new RSTile(3322, 3138)}; 
            RSTile[] ToBank = { new RSTile(3316, 3144), new RSTile(3306, 3146), new RSTile(3295, 3148), new RSTile(3284, 3153), new RSTile(3277, 3159), new RSTile(3270, 3166)};
            RSObject Shelves = getNearestObjectByID(ShelfID);
            RSObject bankbooth = getNearestObjectByID(BankID);
            RSTile houseSW = new RSTile(3318, 3137);
            RSTile houseNE = new RSTile(3324, 3141);
            RSArea houseArea = new RSArea(houseSW, houseNE);
        
        
        
            //What will happen when the script starts
        public boolean onStart(Map<String, String> args) {
            log("Thank you for using my script :D");
            return true;
        }
        public void onFinish(){
            log("Glass taken: " + GlassTaken);
            return;
        }
        
        private final RenderingHints rh = new RenderingHints(                      //This is just paint
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        public void onRepaint(Graphics g) {
             if (isLoggedIn()) {
                    long millis = System.currentTimeMillis() - startTime;
                    long hours = millis / (1000 * 60 * 60);
                    millis -= hours * (1000 * 60 * 60);
                    long minutes = millis / (1000 * 60);
                    millis -= minutes * (1000 * 60);
                    long seconds = millis / 1000;
            ((Graphics2D)g).setRenderingHints(rh);
            g.setColor(new Color(0, 0, 0, 170));
            g.fillRoundRect(3, 266, 146, 73, 5, 5);
            g.setFont(new Font("Byington", 0, 20));
            g.setColor(new Color(0, 0, 0, 251));
            g.drawString("Ploxxii's", 6, 285);
            g.setFont(new Font("Byington", 0, 11));
            g.setColor(new Color(0, 0, 0, 251));
            g.drawString("GlassTaker", 87, 284);
            g.setColor(new Color(0, 0, 0, 251));
            g.drawLine(3, 289, 148, 289);
            g.setFont(new Font("Byington", 0, 13));
            g.setColor(new Color(0, 0, 0, 251));
            g.drawString("Time Running: ", 5, 320);
            g.setFont(new Font("Byington", 0, 13));
            g.setColor(new Color(0, 0, 0, 251));
            g.drawString("Glass Taken: ", 5, 305);
            g.setFont(new Font("Byington", 0, 13));
            g.setColor(new Color(0, 0, 0, 251));
            g.drawString("" + GlassTaken, 90, 305);
            g.setFont(new Font("Byington", 0, 13));
            g.setColor(new Color(0, 0, 0, 251));
            g.drawString("" + hours + ":" + minutes+ ":" + seconds , 10, 335);            
             }
        }
        
        public void serverMessageRecieved(ServerMessageEvent e) {         //This will get all the server message, the little text that comes when you e.g mine an ore
            String message = e.getMessage();
            if(message.contains("You search the shelves")){
                wait(200);
            }
            if(message.contains("and among the strange paraphernalia, you find an empty beer glass")){    //Checks if the message is in the chatbox
                GlassTaken++; //This will add 1 to GlassTaken, which is in the integers
            }
            }
        
        public void walkToBank(){        //The void for walking to bank
                walkPathMM(ToBank);      //If it is then walkPathMM
        }
        public void walkToHouse(){                    //The void for walking to bank
                walkPathMM(ToHouse);                  //If it doesn't then walkPathMM
        }
        public void banking(){                    //The void for banking
                atObject(bankbooth, "e-qu");    //Then open the Bank
                bank.depositAll();                //Deposit all the items in Inventory
                bank.close();                    //Then close the bank
        }
        public void Taking(){    
            setCompass('N');                        //The void for Taking the beer glasses
                while (!isInventoryFull()){            //If we found the Shelves
                    atObject(Shelves, "Search");    //We will click at the Shelves until Inventory is full
                    wait(2500);                        //after it clicks the Shelves it will wait for 2,5 seconds
            }
        }
        
        
        public int loop() {                                    //This is the loop, here is the stuff that will be executed in the script
            if(getMyPlayer().isMoving()) return 250;
            
            if(!isInventoryFull()){    
                if(!houseArea.contains(getMyPlayer().getLocation())){                        //checks if inventory is not full and if we cannot shelves

                    walkToHouse();    
                }                                            //if we don't then execute the Void "walkToHouse"
            }
            if(!isInventoryFull()){                            //Checks if inventory is not full
                if(houseArea.contains(getMyPlayer().getLocation())){

                    Taking();                                //If it's not then execute the void "Taking"
                }
            }
            if(getInventoryCount(BeerGlassID) == 28){        //Checks if inventory is full and if we cannot find bankbooth

                walkToBank();                                //if inventory is full and we don't find the bankbooth the execute the void "walkToBank"
            }
            if(isInventoryFull()){                            //Checks if inventory is full and if we can find bankbooth
                if(bankbooth != null){
                banking();                                    //if inventory is full and we found the bankbooth then execute the void "banking"
                }
            }

            return 500;                                        //This will return to the beginning of the loop after 0,5 seconds
        }
    
}