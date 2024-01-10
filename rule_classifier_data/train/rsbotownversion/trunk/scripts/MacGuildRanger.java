import java.util.Map;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;
import java.util.List;

import org.rsbot.bot.Bot;
import org.rsbot.bot.input.Mouse;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.Script;
import org.rsbot.script.Constants;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.script.wrappers.RSTile;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSNPC;
import org.rsbot.script.wrappers.RSItemTile;
import org.rsbot.script.wrappers.RSInterfaceChild;

@ScriptManifest(authors = "maclof", name = "MacGuildRanger", category = "Combat", description = "<center><span style='font-size: 13px;'><strong>MacGuildRanger</strong><br />By Maclof</span></center><br /><br />" + "<strong>Options:</strong>" + "<p>Mouse Speed (Smaller = Faster):<br /><select name='mouseSpeed'><option>1</option><option>2</option><option>3</option><option selected>4</option><option>5</option><option>6</option><option>7</option><option>8</option><option>9</option><option>10</option></select></p>" + "<p>Shooting Style:<br /><select name='shootStyle'><option>Spam</option><option>Single Click</option></select></p>" + "<p>Use Anti-Ban:<br /><input type='checkbox' name='useAntiban' value='yes' checked /></p>")
public class MacGuildRanger extends Script implements PaintListener
{
    private double scriptVersion = 0.03;
    
    private enum BotState
    {
        WALK_TO_LOCATION
        {
            public String toString()
            {
                return "Walking to Ranging Location";
            }
        },
        
        INTERACT_WITH_NPC
        {
            public String toString()
            {
                return "Interacting with Competition Judge";
            }
        },
        
        EQUIP_ARROWS
        {
            public String toString()
            {
                return "Equipping the Arrows";
            }
        },
        
        SHOOT_AT_TARGET
        {
            public String toString()
            {
                return "Shooting at Target";
            }
        }
    }
    
    private BotState currentState = BotState.INTERACT_WITH_NPC;
    
    private int judgeID = 693;
    private int arrowID = 882;
    private int targetID = 2513;
    private int ticketID = 1464;
    
    private int enterInterfaceID = 236;
    private int targetInterfaceID = 325;
    private int[] completeInterfaceID = {242, 243};
    
    private RSTile rangingLocation = new RSTile(2672, 3417);
    
    private long botStartTime = 0;
    private int botStartExp = 0;
    private int timesCompleted = 0;
    
    private int mouseSpeed = 4;
    private int shootStyle = 1;
    private boolean useAntiban = true;
    
    /**
     * 
     * @return <tt>true</tt> if the bot is ready to start;
     *         otherwise <tt>false</tt>.
     */
    public boolean onStart(Map<String, String> args)
    {
        botStartTime = System.currentTimeMillis();
        botStartExp = skills.getCurrentSkillExp(Constants.STAT_RANGE);
        
        mouseSpeed = Integer.parseInt(args.get("mouseSpeed"));
        
        if(args.get("shootStyle") == "Single Click")
            shootStyle = 2;
        
        if(args.get("useAntiban") == null)
            useAntiban = false;
        
        return true;
    }
    
    protected int getMouseSpeed()
    {
        return mouseSpeed;
    }
    
    private boolean atPoint(Point loc, String action)
    {
        moveMouse(loc);
        
        wait(random(15, 75));
        
        final List<String> items = getMenuItems();
        
        if(items.get(0).contains(action))
        {
            clickMouse(true);
            
            return true;
        }
        else
        {
            clickMouse(false);
            
            return atMenu(action);
        }
    }
    
    /**
     * The main loop of the bot with all the logic and operations.
     * 
     * @return Delay in milliseconds before looping again.
     */
    
    public int loop()
    {
        try
        {
            currentState = getCurrentState();
            
            if(currentState == BotState.WALK_TO_LOCATION)
            {
                walkTileMM(rangingLocation);
                
                waitToMove(1000);
                
                while(getMyPlayer().isMoving())
                {
                    wait(100);
                }
                
                currentState = BotState.SHOOT_AT_TARGET;
            }
            else if(currentState == BotState.EQUIP_ARROWS)
            {
                atInventoryItem(arrowID, "Wield");
                
                currentState = BotState.SHOOT_AT_TARGET;
            }
            else if(currentState == BotState.INTERACT_WITH_NPC)
            {
                RSNPC judgeNPC = getNearestNPCByID(judgeID);
                
                if(judgeNPC == null)
                    return random(1500, 2000);
                
                if(judgeNPC.getScreenLocation() == null)
                    turnToCharacter(judgeNPC);
                
                if(getInterface(enterInterfaceID).isValid())
                {
                    atInterface(enterInterfaceID, 1);
                }
                else if(!atPoint(judgeNPC.getScreenLocation(), "Compete"))
                {
                    currentState = BotState.SHOOT_AT_TARGET;
                }
            }
            else if(currentState == BotState.SHOOT_AT_TARGET)
            {
                RSObject targetObject = getNearestObjectByID(targetID);
                
                if(targetObject == null)
                    return random(1500, 2000);
                
                turnToObject(targetObject);
                setCameraAltitude(false);
                
                if(getInterface(targetInterfaceID).isValid())
                    atInterface(targetInterfaceID, 40);
                
                atPoint(targetObject.getLocation().getScreenLocation(), "Fire-at");
                
                if(shootStyle == 1)
                {
                    while(!getInterface(targetInterfaceID).isValid())
                    {
                        if(isCompleted() || inventoryContains(arrowID) || (distanceTo(rangingLocation) > 2))
                            break;
                        
                        atPoint(targetObject.getLocation().getScreenLocation(), "Fire-at");
                        
                        wait(random(100, 200));
                    }
                }
                
                if(isCompleted())
                {
                    timesCompleted++;
                }
                
                if(shootStyle == 2)
                {
                    waitForIface(getInterface(targetInterfaceID), 1500);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return random(250, 500);
    }
    
    private BotState getCurrentState()
    {
        if(distanceTo(rangingLocation) > 2)
            return BotState.WALK_TO_LOCATION;
        
        if(inventoryContainsOneOf(arrowID))
            return BotState.EQUIP_ARROWS;
        
        if(isCompleted())
            return BotState.INTERACT_WITH_NPC;
        
        return currentState;
    }
    
    private boolean isCompleted()
    {
        return (getInterface(completeInterfaceID[0]).isValid() || getInterface(completeInterfaceID[1]).isValid()) ? true : false;
    }
    
    public void onRepaint(Graphics g)
    {
        long botRunningTime = System.currentTimeMillis() - botStartTime;
        long botHoursRunning = botRunningTime / (1000 * 60 * 60);
        botRunningTime -= botHoursRunning * (1000 * 60 * 60);
        long botMinutesRunning = botRunningTime / (1000 * 60);
        botRunningTime -= botMinutesRunning * (1000 * 60);
        long botSecondsRunning = botRunningTime / 1000;
        
        int botExpGained = skills.getCurrentSkillExp(Constants.STAT_RANGE) - botStartExp;
        float botExpHour = ((float) botExpGained) / (float) (botSecondsRunning + (botMinutesRunning * 60) + (botHoursRunning * 60 * 60)) * 60 * 60;
        float completedPerHour = ((float) timesCompleted) / (float) (botSecondsRunning + (botMinutesRunning * 60) + (botHoursRunning * 60 * 60)) * 60 * 60;
        
        drawOurMouse(g);
        g.setColor(new Color(0, 0, 0, 90));
        g.fillRoundRect(20, 20, 285, 125, 5, 5);
        g.setColor(Color.white );
        
        g.drawString("MacGuildRanger (Version " + scriptVersion + ")", 25, 35);
        
        g.drawString("Current state: " + currentState.toString(), 25, 65);
        g.drawString("Time running: " + botHoursRunning + ":" + botMinutesRunning + ":" + botSecondsRunning, 25, 80);
        g.drawString("Exp gained /hour: " + (int) botExpHour, 25, 95);
        g.drawString("Total exp gained: " + botExpGained, 25, 110);
        g.drawString("Times completed: " + timesCompleted, 25, 125);
        g.drawString("Completed /hour: " + (int) completedPerHour, 25, 140);
    }
    
    /**
     * Draws our mouse position onto the screen, Changing colour whenever
     *         the mouse is pressed.
     */
    private void drawOurMouse(Graphics g)
    {
        final Point mouseLocation = getMouseLocation();
        
        if (System.currentTimeMillis() - Bot.getClient().getMouse().getMousePressTime() < 500)
        {
            g.setColor(Color.red);
        }
        else
        {
            g.setColor(Color.black);
        }
        
        g.drawLine(0, mouseLocation.y, 766, mouseLocation.y);
        
        g.drawLine(mouseLocation.x, 0, mouseLocation.x, 505);
    }
}