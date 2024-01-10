import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.*; 
import java.net.*; 
import javax.swing.JOptionPane; 
import javax.swing.JFileChooser;  
import javax.imageio.ImageIO;

import org.rsbot.bot.Bot;
import org.rsbot.bot.input.Mouse;
import org.rsbot.event.listeners.PaintListener;

import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.script.Script;

import org.rsbot.script.ScriptManifest;

import java.util.Map;
import org.rsbot.script.wrappers.RSNPC;

import org.rsbot.script.wrappers.RSTile;

@ScriptManifest(authors = { "matthudson" }, 
        category = "Money-Making", 
        name = "mSudokuBeast", 
        version = 0.3, 
        description = "<center><p><strong>matthudson's Sudoku Beast v0.3b</strong></p><p>Earns up to 1.2mil per hour!<br>Can solve 180 Sudoku's per hour!</p><p>NOTE: This is a BETA, please be aware that there<br>are/may be some bugs.</p><p>WARNING: This script currently does not contain<br>an Antiban, so it will be high ban rate like the other<br>Sudoku solvers.</p></center>") 

public class mSudokuBeast extends Script implements PaintListener,
        ServerMessageListener {
    private int naturePrice;
    private int cosmicPrice;
    private int bloodPrice;
    private int deathPrice;
    private int chaosPrice;
    private int mindPrice;
    private int soulPrice;
    private int bodyPrice;
    private int lawPrice;

    private int natureInvID = 561;
    private int cosmicInvID = 564;
    private int bloodInvID = 565;
    private int deathInvID = 560;
    private int chaosInvID = 562;
    private int mindInvID = 558;
    private int soulInvID = 566;
    private int bodyInvID = 559;
    private int lawInvID = 563;

    private int profit = 0;
    private int startMoney = 0;
    private int startCash = 0;
    private int startRunes = 0;

    private int currentMoney = 0;
    private int currentCash = 0;
    private int currentRunes = 0;

    private int solved = 0;
    private int failed = 0;

    boolean messedUp = false;
    boolean sudokuOpen = false;
    boolean sudokuSolved = false;
    boolean talked = false;

    private int[][] sudokuGrid = new int[9][9];

    private int mindID = 8982;
    private int fireID = 8980;
    private int airID = 8975;
    private int waterID = 8987;
    private int earthID = 8979;
    private int bodyID = 8976;
    private int deathID = 8978;
    private int chaosID = 8977;
    private int lawID = 8981;

    long scriptStartTIME = 0;

    int aliID = 1862;

    int moneyID = 995;

    private final int[] aliArea = { 3300, 3208, 3308, 3214 };
    private RSTile aliTile = new RSTile(3305, 3211);

    private enum State {
        buyRunes, openSudoku, doSudoku, toAli, end, error;
    }

        int mSpeed = 1;
        @Override
        protected int getMouseSpeed() {
                return random(4, 5);
        }

    @Override
        public void clickMouse(final int x, final int y, final int width,
            final int height, final boolean leftClick) {
        moveMouse(x, y, width, height);
        clickMouse(leftClick, 0);
    }
    @Override
    public void clickMouse(final boolean leftClick, final int moveAfterDist) {
        input.clickMouse(leftClick);
    }

    public boolean setMouseSpeed(int mouseSpeed) {
        mSpeed=mouseSpeed;
        getMouseSpeed();
        return true;
    }
    private double getVersion(){
        return getClass().getAnnotation(ScriptManifest.class).version();
    }

   private State getState() {
        try {
            if (inArea(aliArea)) {
                if (getInventoryCount(moneyID) < 70000) {
                    return State.end;
                }
                if (getInventoryCount(moneyID) >= 70000) {
                    if (sudokuOpen == false) {
                        sudokuSolved = false;
                        if (getInterface(228).getChild(2).containsText(
                                "Buy all")) {
                            return State.buyRunes;
                        }
                        if (!getInterface(228).getChild(2).containsText(
                                "Buy all")) {
                            return State.openSudoku;
                        }
                    }
                    if (sudokuOpen == true) {
                        return State.doSudoku;
                    }
                }
            }
            if (!inArea(aliArea)) {
                return State.toAli;
            }

        } catch (Exception e) {
        }
        return State.error;
    }

    @Override
    public int loop() {

        if (getInterface(288).getChild(9).containsText("open casket")) {
            sudokuOpen = true;
        }
        if (!getInterface(288).getChild(9).containsText("open casket")) {
            sudokuOpen = false;
        }
        exitGeneralStore();
        if (messedUp == true) {
            talked = false;
            sudokuOpen = false;
            messedUp = false;
            failed++;
            resetGrid();
            atInterface(288, 212);
            wait(random(500, 750));

        }

        try {
            switch (getState()) {
            case toAli:

                toAli();
                break;

            case openSudoku:

                openSudoku();

                break;
            case doSudoku:
                doSudoku();

                break;

            case buyRunes:
                buyRunes();

                break;
            case end:
                stopScript();
                break;
            }
        } catch (Exception e) {
        }
        calculateProfit();
        return 200;
    
     }

    private void getPrices() {
        naturePrice = grandExchange.loadItemInfo(natureInvID).getMarketPrice();
        cosmicPrice = grandExchange.loadItemInfo(cosmicInvID).getMarketPrice();
        bloodPrice = grandExchange.loadItemInfo(bloodInvID).getMarketPrice();
        deathPrice = grandExchange.loadItemInfo(deathInvID).getMarketPrice();
        chaosPrice = grandExchange.loadItemInfo(chaosInvID).getMarketPrice();
        mindPrice = grandExchange.loadItemInfo(mindInvID).getMarketPrice();
        soulPrice = grandExchange.loadItemInfo(soulInvID).getMarketPrice();
        bodyPrice = grandExchange.loadItemInfo(bodyInvID).getMarketPrice();
        lawPrice = grandExchange.loadItemInfo(lawInvID).getMarketPrice();

        startCash = getInventoryCount(995);
        startRunes = (naturePrice * getInventoryCount(natureInvID)
                + cosmicPrice * getInventoryCount(cosmicInvID) + bloodPrice
                * getInventoryCount(bloodInvID) + deathPrice
                * getInventoryCount(deathInvID) + chaosPrice
                * getInventoryCount(chaosInvID) + mindPrice
                * getInventoryCount(mindInvID) + soulPrice
                * getInventoryCount(soulInvID) + bodyPrice
                * getInventoryCount(bodyInvID) + lawPrice
                * getInventoryCount(lawInvID));
        startMoney = startCash + startRunes;

    }

    private void calculateProfit() {

        currentCash = getInventoryCount(995);
        currentRunes = (naturePrice * getInventoryCount(natureInvID)
                + cosmicPrice * getInventoryCount(cosmicInvID) + bloodPrice
                * getInventoryCount(bloodInvID) + deathPrice
                * getInventoryCount(deathInvID) + chaosPrice
                * getInventoryCount(chaosInvID) + mindPrice
                * getInventoryCount(mindInvID) + soulPrice
                * getInventoryCount(soulInvID) + bodyPrice
                * getInventoryCount(bodyInvID) + lawPrice
                * getInventoryCount(lawInvID));

        currentMoney = currentCash + currentRunes;

        profit = (int) ((currentMoney - startMoney));

    }

    private void toAli() {
        walkTo(aliTile, 2, 2);
    }

    private void openSudoku() {
        while (getNearestNPCByID(aliID) != null && talked == false) {
            RSNPC npc = (getNearestNPCByID(aliID));
                setMouseSpeed(6);
        atNPC(npc, "Talk-to");
        setMouseSpeed(6);
        talked = true;
            wait(00 + random(00, 00));
        }
        while ((getInterface(241).getChild(4)
                .containsText("How's the adventuring"))) {
            atInterface(241, 5);
        }
        while ((getInterface(228).getChild(2).containsText("Hi, Ali. Not bad"))) {
            atInterface(228, 2);
        }
            
        while ((getInterface(64).getChild(4).containsText("Hi Ali"))) {
            atInterface(64, 5);
        }
        while ((getInterface(241).getChild(4).containsText("Still selling"))) {
            atInterface(241, 5);

        }
        while ((getInterface(241).getChild(4).containsText("Sigh"))) {
            atInterface(241, 5);
        }
        while ((getInterface(64).getChild(4).containsText("What's up with you"))) {
            atInterface(64, 5);
        }
        while ((getInterface(243).getChild(4)
                .containsText("Well, I always fancied"))) {
            atInterface(243, 7);
        }
        if ((getInterface(64).getChild(4)
                .containsText("Is there anything I could do"))) {
            atInterface(64, 5);
        }
        while ((getInterface(241).getChild(4)
                .containsText("How's the adventuring"))) {
            atInterface(241, 5);
        }
        while ((getInterface(244).getChild(4).containsText("I have a friend"))) {
            atInterface(244, 8);
        }
        while ((getInterface(64).getChild(4)
                .containsText("Maybe I could give it a look"))) {
            atInterface(64, 5);
        }
        while ((getInterface(232).getChild(4)
                .containsText("selection of runes"))) {
            atInterface(232, 4);
        }
        while ((getInterface(232).getChild(3)
                .containsText("selection of runes"))) {
            atInterface(232, 3);
        }
        while ((getInterface(234).getChild(4)
                .containsText("selection of runes"))) {
            atInterface(234, 4);
        }
        while ((getInterface(230).getChild(2)
                .containsText("selection of runes"))) {
            atInterface(230, 2);
            wait(00 + random(00,00));
        }
        while ((getInterface(241).getChild(4).containsText("Hang on"))) {
            atInterface(241, 5);
        }
        while ((getInterface(233).getChild(3)
                .containsText("large casket of runes"))) {
            atInterface(233, 3);

        }
        while ((getInterface(231).getChild(3)
                .containsText("large casket of runes"))) {
            atInterface(231, 3);

        }
        while ((getInterface(235).getChild(3)
                .containsText("large casket of runes"))) {
            atInterface(235, 3);
        }
        while ((getInterface(64).getChild(4).containsText("On second thoughts"))) {
            talked = false;
            atInterface(64, 5);

        }
        while ((getInterface(65).getChild(4).containsText("I'm feeling lucky"))) {
            atInterface(65, 6);
        }
        while ((getInterface(230).getChild(4).containsText("Examine lock"))) {
            atInterface(230, 4);
            sudokuOpen = true;
        }
        while (getInterface(64).getChild(4).containsText("Who did you want me")) {
            talked = false;
            atInterface(64, 5);
          wait(00+random(00,00));
    }

    }



    public void solveSudoku() {
    setMouseSpeed(0);
        for (int k = 0; k < 9; k++) {
            switch (k) {
            case 0:
                atInterface(288, 205);
                for (int r = 0; r < 9; r++) {
                    for (int c = 0; c < 9; c++) {
                        int i = (11 + c * 2 + r * 18);
                        if ((sudokuGrid[r][c] == 1)
                                && !(getInterface(288).getChild(i).getModelID() == mindID)) {
                            atInterface(288, i);

                        }
                    }
                }
                break;
            case 1:
                atInterface(288, 206);
                for (int r = 0; r < 9; r++) {
                    for (int c = 0; c < 9; c++) {
                        int i = (11 + c * 2 + r * 18);
                        if (sudokuGrid[r][c] == 2
                                && !(getInterface(288).getChild(i).getModelID() == fireID)) {
                            atInterface(288, i);

                        }
                    }
                }
                break;
            case 2:
                atInterface(288, 204);
                for (int r = 0; r < 9; r++) {
                    for (int c = 0; c < 9; c++) {
                        int i = (11 + c * 2 + r * 18);
                        if (sudokuGrid[r][c] == 3
                                && !(getInterface(288).getChild(i).getModelID() == airID)) {
                            atInterface(288, i);

                        }
                    }
                }
                break;
            case 3:
                atInterface(288, 203);
                for (int r = 0; r < 9; r++) {
                    for (int c = 0; c < 9; c++) {
                        int i = (11 + c * 2 + r * 18);
                        if (sudokuGrid[r][c] == 4
                                && !(getInterface(288).getChild(i).getModelID() == waterID)) {
                            atInterface(288, i);
                        }
                    }
                }
                break;
            case 4:
                atInterface(288, 202);
                for (int r = 0; r < 9; r++) {
                    for (int c = 0; c < 9; c++) {
                        int i = (11 + c * 2 + r * 18);
                        if (sudokuGrid[r][c] == 5
                                && !(getInterface(288).getChild(i).getModelID() == earthID)) {
                            atInterface(288, i);

                        }
                    }
                }
                break;
            case 5:
                atInterface(288, 207);
                for (int r = 0; r < 9; r++) {
                    for (int c = 0; c < 9; c++) {
                        int i = (11 + c * 2 + r * 18);
                        if (sudokuGrid[r][c] == 6
                                && !(getInterface(288).getChild(i).getModelID() == bodyID)) {
                            atInterface(288, i);

                        }
                    }
                }
                break;
            case 6:
                atInterface(288, 208);
                for (int r = 0; r < 9; r++) {
                    for (int c = 0; c < 9; c++) {
                        int i = (11 + c * 2 + r * 18);
                        if (sudokuGrid[r][c] == 7
                                && !(getInterface(288).getChild(i).getModelID() == deathID)) {
                            atInterface(288, i);

                        }
                    }
                }
                break;
            case 7:
                atInterface(288, 209);
                for (int r = 0; r < 9; r++) {
                    for (int c = 0; c < 9; c++) {
                        int i = (11 + c * 2 + r * 18);
                        if (sudokuGrid[r][c] == 8
                                && !(getInterface(288).getChild(i).getModelID() == chaosID)) {
                            atInterface(288, i);

                        }
                    }
                }
                break;
            case 8:
                atInterface(288, 210);
                for (int r = 0; r < 9; r++) {
                    for (int c = 0; c < 9; c++) {
                        int i = (11 + c * 2 + r * 18);
                        if (sudokuGrid[r][c] == 9
                                && !(getInterface(288).getChild(i).getModelID() == lawID)) {
                            atInterface(288, i);

                        }
                    }
                }
                break;
            }

        }
       setMouseSpeed(6);
    }

    private void doSudoku() {

        if (sudokuOpen == true && sudokuSolved == false
                && !getInterface(228).getChild(2).containsText("Buy all")) {
            makeGrid();
            if (solve(0, 0, sudokuGrid)) {
                if (sudokuOpen == true
                        && !getInterface(228).getChild(2).containsText(
                                "Buy all")) {
                    for (int i = 0; i < 9; i++) {
            
             }
                }

                solveSudoku();
                sudokuSolved = true;
                if (getInterface(288).getChild(9).containsText("open casket")) {
                    atInterface(288, 9);
                    wait(4000+(random(200,200)));
                    sudokuOpen = false;
                    sudokuSolved = false;
                }
            }
        }
    }

    private void buyRunes() {
        atInterface(228, 2);
        resetGrid();
        talked = false;
        sudokuOpen = false;
        sudokuSolved = false;
    resetGrid();
    }

    private boolean inArea(final int[] area) {
        final int x = getMyPlayer().getLocation().getX();
        final int y = getMyPlayer().getLocation().getY();
        if (x >= area[0] && x <= area[2] && y >= area[1] && y <= area[3]) {
            return true;
        }
        return false;

    }
    private boolean inGeneralStore(){
        if(getInterface(620).getChild(20).containsText("Ali's Discount Wares")){
            return true;
        }
        else{
            return false;
            }
    }
    private void exitGeneralStore(){
        if(inGeneralStore()==true){
            if(getInterface(620).getChild(18).containsAction("Close")){
                log("missclicked, closing general store");
                atInterface(620,18);
                talked = false;
                sudokuOpen = false;
                
            }
            
        }
    }

    static boolean solve(int i, int j, int[][] cells) {
     if (i == 9) {
            i = 0;
            if (++j == 9)
                return true;
        }
        if (cells[i][j] != 0)
            return solve(i + 1, j, cells);

        for (int val = 1; val <= 9; ++val) {
            if (legal(i, j, val, cells)) {
                cells[i][j] = val;
                if (solve(i + 1, j, cells))
                    return true;
            }
        }
        cells[i][j] = 0;
        return false;
    }

    static boolean legal(int i, int j, int val, int[][] cells) {
        for (int k = 0; k < 9; ++k)
            if (val == cells[k][j])
                return false;

        for (int k = 0; k < 9; ++k)
            if (val == cells[i][k])
                return false;

        int boxRowOffset = (i / 3) * 3;
        int boxColOffset = (j / 3) * 3;
        for (int k = 0; k < 3; ++k)
            for (int m = 0; m < 3; ++m)
                if (val == cells[boxRowOffset + k][boxColOffset + m])
                    return false;

        return true;
    }

        BufferedImage normal = null;
        BufferedImage clicked = null;

    public boolean onStart(Map<String, String> args) {

 scriptStartTIME = System.currentTimeMillis();
    try {
            final URL cursorURL = new URL(
                    "http://i50.tinypic.com/b6rng7.png");
            final URL cursor80URL = new URL(
                    "http://i49.tinypic.com/35bh2rq.png");
            normal = ImageIO.read(cursorURL);
            clicked = ImageIO.read(cursor80URL);
        } catch (MalformedURLException e) {
            log("Unable to buffer cursor.");
        } catch (IOException e) {
            log("Unable to open cursor image.");
    }
        resetGrid();
        getPrices();
        return true;
    }



        
    private void makeGrid() {

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int i = (11 + c * 2 + r * 18);

                if (getInterface(288).getChild(i).getModelID() == mindID) {
                    sudokuGrid[r][c] = 1;
                }
                if (getInterface(288).getChild(i).getModelID() == fireID) {
                    sudokuGrid[r][c] = 2;
                }
                if (getInterface(288).getChild(i).getModelID() == airID) {
                    sudokuGrid[r][c] = 3;
                }
                if (getInterface(288).getChild(i).getModelID() == waterID) {
                    sudokuGrid[r][c] = 4;
                }
                if (getInterface(288).getChild(i).getModelID() == earthID) {
                    sudokuGrid[r][c] = 5;
                }
                if (getInterface(288).getChild(i).getModelID() == bodyID) {
                    sudokuGrid[r][c] = 6;
                }
                if (getInterface(288).getChild(i).getModelID() == deathID) {
                    sudokuGrid[r][c] = 7;
                }
                if (getInterface(288).getChild(i).getModelID() == chaosID) {
                    sudokuGrid[r][c] = 8;
                }
                if (getInterface(288).getChild(i).getModelID() == lawID) {
                    sudokuGrid[r][c] = 9;
                }
            }
        }
    }

    private void resetGrid() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                sudokuGrid[r][c] = 0;

            }
        }

    }

    @Override
    public void onRepaint(Graphics render) {
        long runTime = 0;
        long seconds = 0;
        long minutes = 0;
        long hours = 0;
        runTime = System.currentTimeMillis() - scriptStartTIME;
        seconds = runTime / 1000;
        if (seconds >= 60) {
            minutes = seconds / 60;
            seconds -= (minutes * 60);
        }
        if (minutes >= 60) {
            hours = minutes / 60;
            minutes -= (hours * 60);
        }
        int solvedPerHour = 0;
        int failedPerHour = 0;
        int profitPerHour = 0;

        if (runTime / 1000 > 0) {
            solvedPerHour = (int) ((3600000.0 / (double) runTime) * solved);
            failedPerHour = (int) ((3600000.0 / (double) runTime) * failed);
            
            profitPerHour = (int) ((3600000.0 / (double) runTime) * profit);

        }
        
if (normal != null) {
        final Mouse mouse = Bot.getClient().getMouse();
        final int mouse_x = mouse.getMouseX();
        final int mouse_y = mouse.getMouseY();
        final int mouse_x2 = mouse.getMousePressX();
        final int mouse_y2 = mouse.getMousePressY();
        final long mpt = System.currentTimeMillis()
                - mouse.getMousePressTime();
        if (mouse.getMousePressTime() == -1 || mpt >= 1000) {
            render.drawImage(normal, mouse_x - 8, mouse_y - 8, null); //this show the mouse when its not clicked
        }
        if (mpt < 1000) {
            render.drawImage(clicked, mouse_x2 - 8, mouse_y2 - 8, null); //this show the four squares where you clicked.
            render.drawImage(normal, mouse_x - 8, mouse_y - 8, null); //this show the mouse as normal when its/just clicked
        }
    }
        
// PAINT
render.setColor(new Color(0, 100, 0, 175));
        render.fillRoundRect(520, 4, 240, 160, 0, 0);
        render.setColor(Color.red);
        render.draw3DRect(520, 4, 240, 160, true);
        render.setColor(Color.cyan);
        render.drawString("matthudson's Sudoku Beast", 565, 22);
        render.setColor(Color.white);
        render.drawString("Runtime: " + hours + ":" + minutes + ":" + seconds, 580, 41);
        render.setColor(Color.cyan);
        render.drawString("profit: " + profit, 535, 64);
           render.drawString("failed/hr: " + failedPerHour, 535, 109);
        render.drawString("profit/hr: " + profitPerHour, 535, 87);
          render.setColor(Color.yellow);  
        render.drawString("failed: " + failed, 640, 109);
        render.drawString("solved: " + solved, 640, 64);
        render.drawString("solved/hr: " + solvedPerHour, 640, 87);
        render.drawString("BETA VERSION ONLY. v0.3b", 580, 120);
    
    }
    public void onFinish(){
        Bot.getEventManager().removeListener(ServerMessageListener.class, this);
        Bot.getEventManager().removeListener(PaintListener.class, this);
        log("Thank-you for using matthudson's Sudoku Beast.");
        log("Profit Made: " + profit);

    }

    public void serverMessageRecieved(final ServerMessageEvent arg0) {

        final String message = arg0.getMessage();
        if (message.contains("remove this rune")) {
            messedUp = true;
        }
        if (message.contains("locked")) {
            messedUp = true;
        }

        if (message.contains("hear the locking") && sudokuOpen == true) {
            solved++;
            resetGrid();
            sudokuOpen = false;
        }

    }
}