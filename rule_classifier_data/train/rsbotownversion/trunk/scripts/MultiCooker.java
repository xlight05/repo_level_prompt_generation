import java.awt.Color;
    import java.awt.Font;
    import java.awt.Graphics;
    import java.awt.Point;
    import java.io.File;
    import java.io.FileInputStream;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.util.ArrayList;
    import java.util.Map;
    import java.util.Properties;
    import org.rsbot.bot.Bot;
    import org.rsbot.event.events.ServerMessageEvent;
    import org.rsbot.event.listeners.PaintListener;
    import org.rsbot.event.listeners.ServerMessageListener;
    import org.rsbot.script.Calculations;
    import org.rsbot.script.Constants;
    import org.rsbot.script.Script;
    import org.rsbot.script.ScriptManifest;
    import org.rsbot.script.wrappers.RSInterface;
    import org.rsbot.script.wrappers.RSObject;
    import org.rsbot.script.wrappers.RSNPC;
    import org.rsbot.script.wrappers.RSTile;

    @ScriptManifest(authors = { "Sean" }, name = "Multi-Cooker",
    category = "Cooking", version = 1.03,
    description = "<html><body><b><center>Multi-Cooker</center></b><br />" +
            "<center>by Sean</center><p>" +
        "Select Location:<br>" +
        "<select name='location'>" +
            "<option>Al Kharid</option>" +
            "<option>Rogues Den</option>" +
        "</select><p>" +
            "Select the type of food to cook:<br>" +
            "<select name='food'>" +
                "<option>Shrimp</option" +
                "<option>Crayfish</option>" +
                "<option>Chicken</option>" +
                "<option>Rabbit</option>" +
                "<option>Anchovies</option>" +
		"<option>Sardine</option>" +
                "<option>Karambwan</option>" +
                "<option>Herring</option>" +
                "<option>Mackerel</option>" +
                "<option>Trout</option>" +
                "<option>Cod</option>" +
                "<option>Pike</option>" +
                "<option>Salmon</option>" +
                "<option>Tuna</option>" +
                "<option>Rainbow Fish</option>" +
                "<option>Lobster</option>" +
                "<option>Bass</option>" +
                "<option>Swordfish</option>" +
                "<option>Monkfish</option>" +
                "<option>Shark</option>" +
                "<option>Sea Turtle</option>" +
                "<option>Cave Fish</option>" +
                "<option>Manta Ray</option>" +
                "<option>Rocktail</option>" +
            "</select><p>If Cooking at Al-Kharid:<br>Start in Al-Kharid Bank, at range," + 
	    " or in-between!<p>If Cooking at Rogues Den:<br> Start at " +
	    "Den near fire and banker!")

    public class MultiCooker extends Script implements ServerMessageListener,
            PaintListener{

        /////PATHS/////
        private RSTile[] toRange = { new RSTile(3269, 3169), new RSTile(3276, 3175), new
            RSTile(3274, 3180) };
        private RSTile[] toBank = reversePath(toRange);
        private RSTile Bank;
        private RSTile Range;
        private RSTile rogueFire = new RSTile(3043, 4973);
        ///////////////

        /////OTHER/////
        private int RANGE;
        private int BANK;
        private static final int COOKANIM = 896;
        private int cookXP = 0;
        private int startXP = 0;
        private int foodCooked = 0;
        private int foodBurnt = 0;
        private int startLevel;
        private int levelsGained = 0;
        private int foodID = 0;
        private int foodXP;
        private String cooking;
        private String spot;

        public int tries = 0;
        public int count;
        public int roll = 0;
        private enum Status {
            bankstate, cookstate, walkbankstate,walkrangestate;
        }
        ///////////////

        /////PAINT STUFF/////
        public long startTime = System.currentTimeMillis();
        public String status = "";
        /////////////////////

        @Override
        public boolean onStart(Map<String, String> args) {
            log("Starting the Multi-Cooker");
        if (args.get("location").equals("Al Kharid")) {
            BANK = 35647;
            RANGE = 25730;
            spot = "kharid";
        } else if (args.get("location").equals("Rogues Den")) {
            RANGE = 2732;
            BANK = 2271;
            spot = "rogue";
        }
            if (args.get("food").equals("Shrimp")) {
                foodID = 317;
                foodXP = 30;
                cooking = "Shrimp";
            } else if (args.get("food").equals("Sardine")) {
                foodID = 327;
                foodXP = 40;
                cooking = "Sardine";
	    } else if (args.get("food").equals("Crayfish")) {
                foodID = 13435;
                foodXP = 30;
                cooking = "Crayfish";
            } else if (args.get("food").equals("Chicken")) {
                foodID = 2138;
                foodXP = 30;
                cooking = "Chicken";
            } else if (args.get("food").equals("Rabbit")) {
                foodID = 0;
                foodXP = 30;
                cooking = "Rabbit";
            } else if (args.get("food").equals("Anchovies")) {
                foodID = 321;
                foodXP = 30;
                cooking = "Anchovies";
            } else if (args.get("food").equals("Karambwan")) {
                foodID = 3142;
                foodXP = 190;
                cooking = "Karambwan";
            } else if (args.get("food").equals("Herring")) {
                foodID = 345;
                foodXP = 50;
                cooking = "Herring";
            } else if (args.get("food").equals("Mackerel")) {
                foodID = 353;
                foodXP = 60;
                cooking = "Mackerel";
            } else if (args.get("food").equals("Trout")) {
                foodID = 335;
                foodXP = 70;
                cooking = "Trout";
            } else if (args.get("food").equals("Cod")) {
                foodID = 341;
                foodXP = 75;
                cooking = "Cod";
            } else if (args.get("food").equals("Pike")) {
                foodID = 349;
                foodXP = 80;
                cooking = "Pike";
            } else if (args.get("food").equals("Salmon")) {
                foodID = 331;
                foodXP = 90;
                cooking = "Salmon";
            } else if (args.get("food").equals("Tuna")) {
                foodID = 359;
                foodXP = 100;
                cooking = "Tuna";
            } else if (args.get("food").equals("Rainbow Fish")) {
                foodID = 10138;
                foodXP = 110;
                cooking = "Rainbow Fish";
            } else if (args.get("food").equals("Lobster")) {
                foodID = 377;
                foodXP = 120;
                cooking = "Lobster";
            } else if (args.get("food").equals("Bass")) {
                foodID = 363;
                foodXP = 130;
                cooking = "Bass";
            } else if (args.get("food").equals("Swordfish")) {
                foodID = 371;
                foodXP = 140;
                cooking = "Swordfish";
            } else if (args.get("food").equals("Monkfish")) {
                foodID = 7944;
                foodXP = 150;
                cooking = "Monkfish";
            } else if (args.get("food").equals("Shark")) {
                foodID = 383;
                foodXP = 210;
                cooking = "Shark";
            } else if (args.get("food").equals("Sea Turtle")) {
                foodID = 395;
                foodXP = 211;
                cooking = "Sea Turtle";
            } else if (args.get("food").equals("Cave Fish")) {
                foodID = 15264;
                foodXP = 214;
                cooking = "Cave Fish";
            } else if (args.get("food").equals("Manta Ray")) {
                foodID = 389;
                foodXP = 216;
                cooking = "Manta Ray";
            } else if (args.get("food").equals("Rocktail")) {
                foodID = 15270;
                foodXP = 225;
                cooking = "Rocktail";
            }
            if (isLoggedIn()) {
                startLevel = skills.getCurrentSkillLevel(Constants.STAT_COOKING);
                startXP = skills.getCurrentSkillExp(Constants.STAT_COOKING);
            }
            return true;
        }

        public void onFinish() {
            Bot.getEventManager().removeListener(PaintListener.class, this);
            return;
        }

        public void serverMessageRecieved(final ServerMessageEvent e) {
            final String word = e.getMessage().toLowerCase();
            if (word.contains("successfully cook")) {
                foodCooked++;
                cookXP = cookXP + foodXP;
            }
            if (word.contains("manage to cook")) {
                foodCooked++;
                cookXP = cookXP + foodXP;
            }
	    if (word.contains("roast")) {
                foodCooked++;
                cookXP = cookXP + foodXP;
            }
            if (word.contains("manage to burn")) {
                foodBurnt++;
            }
            if (word.contains("accidentally burn")) {
                foodBurnt++;
            }
        }

        /////////////////////BASIC CHECKS/////////////////////
        public void stopScripts() {
            System.out.println("All Scripts Stopped");
            Bot.getScriptHandler().stopScript();
        }

        public boolean animationCheck(final int id) {
            final int anim = getMyPlayer().getAnimation();
            if (id == anim) {
                return true;
            }
            return false;
        }

        public void antiban2() {
            roll = random(0, 20);
            if (roll == 7) {
                setCameraRotation(random(1, 90));
            }
        }

        public void checkenergy() {
            if (!isRunning()) {
                if (getEnergy() >= random(50, 100)) {
                    log("You have enough energy, turning on 'run'");
                    setRun(true);
                }
            }
        }

        private boolean playerInArea(final int maxX, final int maxY,
            final int minX, final int minY) {
            final int x = getMyPlayer().getLocation().getX();
            final int y = getMyPlayer().getLocation().getY();
            if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
                return true;
            }
            return false;
        }
        //////////////////////////////////////////////////////

        ///////////////////////BANK STUFF/////////////////////
        private int Banking() {
            try {
                if (!bank.isOpen()) {
                    openBank();
                    wait(random(400, 500));
                    useBank();
                    return 400;
                } else if (bank.isOpen()) {
                    useBank();
                    return 400;
                }
            } catch (final Exception e) {
            }
            return 30;
        }

        public boolean nearBank() {
            if (spot == "kharid") {
                RSObject banker = findObject(BANK);
                if (banker == null || distanceTo(banker) >= 5) {
                    return false;
                } else {
                    return true;
                }
            } else {
                RSNPC banker = getNearestNPCByID(BANK);
                if (banker == null || distanceTo(banker) >= 7) {
                    return false;
                } else {
                    return true;
                }
            }
        }

        public void openBank() {
            try {
                if (spot == "kharid") {
                    final RSObject bankbooth = findObject(BANK);
                    if (bankbooth != null) {
                        while (getMyPlayer().isMoving()) {
                            wait(10);
                        }
                        if (nearBank()) {
                            status = "Opening bank...";
                            Bank = bankbooth.getLocation();
                            final Point location = Calculations.tileToScreen(Bank);
                            if (location.x == -1 || location.y == -1) {
                                wait(500);
                            }
                            clickMouse(location, 2, 2, false);
                            wait(200);
                            if (getMenuActions().contains("Use-quickly")) {
                                atMenu("Use-quickly");
                                wait(500);
                            } else {
                                atMenu("Cancel");
                                setCameraRotation(random(1, 360));
                            }
                        }
                    } else {
                        walkPathMM(randomizePath(toBank, 2, 2), 17);
                    }
                } else {
                    final RSNPC bankbooth = getNearestNPCByID(BANK);
                    if (bankbooth != null) {
                        while (getMyPlayer().isMoving()) {
                            wait(10);
                        }
                        if (nearBank()) {
                            status = "Opening bank...";
                            Bank = bankbooth.getLocation();
                            final Point location = Calculations.tileToScreen(Bank);
                            if (location.x == -1 || location.y == -1) {
                                wait(500);
                            }
                            clickMouse(location, 2, 2, false);
                            wait(200);
                            if (getMenuActions().contains("Bank")) {
                                atMenu("Bank");
                                wait(500);
                            } else {
                                atMenu("Cancel");
                                setCameraRotation(random(1, 360));
                            }
                        }
                    }
                }
            } catch (final Exception e) {
            }
        }

        public void useBank() {
            try {
                if (RSInterface.getInterface(Constants.INTERFACE_BANK).isValid()) {
                    status = "Using Bank...";
                    if (getInventoryCount(foodID) > 1) {
                        bank.close();
                        return;
                    } else if (getInventoryCount(foodID) == 0) {
                        if (getInventoryCount() == 0) {
                            if (bank.getCount(foodID) == 0) {
                                log("No food left, stopping!");
                                stopScripts();
                            }
                            for (int i = 0; i < 1; i++) {
                                bank.withdraw(foodID, 0);
                                wait(1000);
                                bank.close();
                            }
                        } else {
                            bank.depositAll();
                            wait(200);
                            if (bank.getCount(foodID) == 0) {
                                log("No food left, stopping!");
                                stopScripts();
                            }
                            bank.withdraw(foodID, 0);
                            wait(1000);
                            bank.close();
                        }
                    }
                }
                bank.close();
            }catch (final Exception e) {
            }
        }
        //////////////////////////////////////////////////////

            ///////////////////////COOK STUFF/////////////////////
        public boolean nearRange() {
            if (spot == "kharid") {
                final RSObject range = findObject(RANGE);
                if (range == null || distanceTo(range) >= 5) {
                    return false;
                } else {
                    return true;
                }
            } else {
                final RSObject range = getObjectAt(rogueFire);
                if (range == null || distanceTo(range) >= 7) {
                    return false;
                } else {
                    return true;
                }
            }
        }

        public boolean atInventoryItemUse(int itemID) {
            if (getCurrentTab() != TAB_INVENTORY
                    && !RSInterface.getInterface(INTERFACE_BANK).isValid()
                    && !RSInterface.getInterface(INTERFACE_STORE).isValid()) {
                openTab(TAB_INVENTORY);
            }
            int[] items = getInventoryArray();
            java.util.List<Integer> possible = new ArrayList<Integer>();
            for (int i = 0; i < items.length; i++) {
                if (items[i] == itemID) {
                    possible.add(i);
                }
            }
            if (possible.size() == 0) return false;
            int idx = possible.get(random(0, possible.size()));
            Point t = getInventoryItemPoint(idx);
            int x = (int) t.getX();
            int y = (int) t.getY();
            moveMouse(x + 15, y + 15, 2, 2);
            wait(100);
            if (getMenuIndex("Use") == 0) {
                clickMouse(true);
            } else {
                atInventoryItemUse(foodID);
            }
            return true;
        }

        public void moveRange() {
            if (getInventoryCount(foodID) > 0) {
                if (spot == "kharid") {
                    final RSObject range = findObject(RANGE);
                    Range = range.getLocation();
                    final Point location = Calculations.tileToScreen(Range);
                    if (location.x == -1 || location.y == -1) {
                        wait(100);
                    }
                    moveMouse(location, 2, 2);
                    wait(100);
                    if (getMenuIndex("Use") == 0) {
                        clickMouse(false);
                        atMenu("Range");
                        tries = 0;
                    } else {
                        if (tries <= 5) {
                            tries++;
                            moveRange();
                        } else {
                            tries = 0;
                            Cook();
                        }
                    }
                } else {
                    final RSObject range = getObjectAt(rogueFire);
                    Range = range.getLocation();
                    final Point location = Calculations.tileToScreen(Range);
                    if (location.x == -1 || location.y == -1) {
                        wait(100);
                    }
                    moveMouse(location, 2, 2);
                    wait(100);
                    if (getMenuIndex("Use") == 0) {
                        clickMouse(false);
                        atMenu("Fire");
                        tries = 0;
                    } else {
                        if (tries <= 5) {
                            tries++;
                            moveRange();
                        } else {
                            tries = 0;
                            Cook();
                        }
                    }
                }
            }
        }

        public void Cook() {
            try {
            if (getInventoryCount(foodID) > 0) {
                if (spot == "kharid") {
                    final RSObject range = findObject(RANGE);
                    if (range != null) {
                        while (getMyPlayer().isMoving()) {
                            wait(10);
                        }
                        if (nearRange()) {
                            status = "Ready to cook...";
                            atInventoryItemUse(foodID);
                            moveRange();
                            wait(1000);
                            while (getMyPlayer().isMoving()) {
                                wait(20);
                            }
                            wait(500);
                            if (RSInterface.getInterface(513).isValid()) {
                                clickMouse(260, 415, 10, 10, false);
                                if (getMenuActions().contains("Cook All")) {
                                    atMenu("Cook All");
                                    status = "Cooking...";
                                    count = getInventoryCount(foodID);
                                    for (int i = 0; i <= count; i++) {
					    if (RSInterface.getInterface(Constants.INTERFACE_LEVELUP).isValid()) {
					    	clickContinue();
	   				    }
					    antiBan();
                                            wait(2250);

                                    }
                                } else {
                                    atMenu("Cancel");
                                    setCameraRotation(random(1, 360));
                                }

                            }
                        }
                    } else {
                        walkPathMM(randomizePath(toRange, 2, 2), 17);
                    }
                } else {
                    final RSObject range = getObjectAt(rogueFire);
                    if (range != null) {
                        while (getMyPlayer().isMoving()) {
                            wait(10);
                        }
                        if (nearRange()) {
                            status = "Ready to cook...";
                            atInventoryItemUse(foodID);
                            moveRange();
                            wait(1000);
                            if (getMyPlayer().isMoving()) {
                                wait(500);
                            }
                            wait(1000);
                            if (RSInterface.getInterface(513).isValid()) {
                                clickMouse(260, 415, 10, 10, false);
                                if (getMenuActions().contains("Cook All")) {
                                    atMenu("Cook All");
                                    status = "Cooking...";
                                    count = getInventoryCount(foodID);
                                    for (int i = 0; i <= count; i++) {
					    if (RSInterface.getInterface(Constants.INTERFACE_LEVELUP).isValid()) {
					    	clickContinue();
	   				    }
					    antiBan();
                                            wait(2250);

                                    }
                                } else {
                                    atMenu("Cancel");
                                    setCameraRotation(random(1, 360));
                                }

                            }
                        }
                    }
                }
            }
            } catch (final Exception e) {
            }
        }

        //////////////////////////////////////////////////////

        ////////////////////////WALKING///////////////////////
        private int walkToBank() {
            try {
                if (distanceTo(getDestination()) > 2) {
                    if (getMyPlayer().isMoving()) {
                        return 500;
                    }
                }
                walkPathMM(randomizePath(toBank, 2, 2), 18);
            } catch (final Exception e) {
            }
            return 50;
        }

        private int walkToRange() {
            try {
                if (distanceTo(getDestination()) > 1) {
                    if (getMyPlayer().isMoving()) {
                        return 500;
                    }
                }
                walkPathMM(randomizePath(toRange, 1, 1), 18);
            } catch (final Exception e) {
            }
            return 50;
        }
        //////////////////////////////////////////////////////


        private Status getState() {
            if (getInventoryCount(foodID) == 0 && nearBank()) {
                return Status.bankstate;
            }
            if (getInventoryCount(foodID) >= 1 && nearRange() && !RSInterface.getInterface
(Constants.INTERFACE_BANK).isValid()) {
                return Status.cookstate;
            }
            if (spot == "kharid") {
                if (getInventoryCount(foodID) >= 1 && !nearRange()) {
                    return Status.walkrangestate;
                }
                if (getInventoryCount(foodID) == 0 && !nearBank()) {
                    return Status.walkbankstate;
                }
            }
            return null;
        }

        public int loop() {
        try {
            if (!isLoggedIn()) {
                return random(1000, 15000);
            }
           if (RSInterface.getInterface(Constants.INTERFACE_BANK).isValid() && 
getInventoryCount(foodID) >= 1) {
                bank.close();
           }
	   if (RSInterface.getInterface(Constants.INTERFACE_LEVELUP).isValid()) {
		clickContinue();
	   }

                while (animationCheck(COOKANIM)) {
                        wait(1238);
                }
            checkenergy();
            antiBan();
	    antiban2();
            setCameraAltitude(true);

                switch (getState()) {
                case bankstate:
                    status = "Banking...";
                    Banking();
                    return 50;
                case walkbankstate:
                    status = "Walking to bank...";
                    walkToBank();
                    return 50;
                case cookstate:
                    status = "Cooking food...";
                    Cook();
                    return 50;
                case walkrangestate:
                    status = "Walking to range...";
                    walkToRange();
                    return 50;
                default:
                    return 50;
                }

        } catch (final Exception e) {
        }
        return 4;
        }

    public boolean clickcontinue() {
        if (getContinueChildInterface() != null) {
            if (getContinueChildInterface().getText().contains("to continue")) {
                return atInterface(getContinueChildInterface());
            }
        }
        return false;
    }

        public void onRepaint(final Graphics render) {
            long millis = System.currentTimeMillis() - startTime;
            final long hours = millis / (1000 * 60 * 60);
            millis -= hours * 1000 * 60 * 60;
            final long minutes = millis / (1000 * 60);
            millis -= minutes * 1000 * 60;
            final long seconds = millis / 1000;
            float cooksec = 0;
            if ((minutes > 0 || hours > 0 || seconds > 0) && cookXP > 0) {
                cooksec = (float) foodCooked
                        / (float) (seconds + minutes * 60 + hours * 60 * 60);
            }
            final float cookmin = cooksec * 60;
            final float cookhour = cookmin * 60;

            float expsec = 0;
            if ((minutes > 0 || hours > 0 || seconds > 0) && cookXP > 0) {
                expsec = (float) cookXP
                        / (float) (seconds + minutes * 60 + hours * 60 * 60);
            }
            final float expmin = expsec * 60;
            final float exphour = expmin * 60;

            final int LevelChange = skills
                    .getCurrentSkillLevel(Constants.STAT_COOKING)
                    - startLevel;

                render.setColor(new Color(215, 218, 231, 85));
                render.fill3DRect(4, 133, 210, 205, true);
                render.setColor(Color.black);
                render.setFont(new Font("sansserif", Font.BOLD, 12));
                render.drawString("Multi-Cooker", 7, 152);
                render.setColor(Color.cyan);
                render.drawString("Multi-Cooker", 8, 151);
                render.setColor(Color.black);
                render.setFont(new Font("sansserif", Font.PLAIN, 12));
                render.drawString("Time running: " + hours + " hrs " + minutes
                        + " mins " + seconds + " secs", 7, 169);
                render.drawString("Cooking: " + cooking, 7, 187);
                render.drawString("Food Cooked: " + foodCooked, 7, 205);
                render.drawString("Food Burnt: " + foodBurnt, 7, 223);
                render.drawString("Cooked per hour: " + (int) cookhour, 7, 241);

                render.drawString("Exp. Gained: " + cookXP, 7, 259);
                render.drawString("Exp. per hour: " + (int) exphour,
                        7, 277);
                render.drawString("Levels Gained: " + LevelChange, 7, 295);
                render.setColor(Color.cyan);
                render.drawString("Status: " + status, 7, 313);
            }

    private int antiBan() {
    	int random = random(1, 4);
        switch (random) {
            case 1:
            	if (random(1, 4) == 2)
            	{
	            	moveMouseRandomly(300);
	            	return random(100, 500);
            	}

            case 2:
            	if (random(1, 10) == 5)
            	{
	                if (getCurrentTab() != TAB_INVENTORY) {
	                    openTab(TAB_INVENTORY);
	                    return random(100, 500);
	                } else {
	                	return random(100, 500);
	                }
            	}

            case 3:
            	if (random(1, 200) == 100)
            	{
	                if (getMyPlayer().isMoving()) {
	                	return random(100, 500);
	                }
	                if (getCurrentTab() != TAB_STATS) {
	                    openTab(TAB_STATS);
	                }
	                moveMouse(686, 343, 20, 10);
	                wait(random(3000, 6000));
			if (getCurrentTab() != TAB_INVENTORY) {
	                    openTab(TAB_INVENTORY);
	                }
	                return random(100, 500);
            	}
            	
            case 4:
            	if (random(1, 150) == 75)
            	{
                    openTab(random(0, 8));
                    wait(random(3000, 5000));
                    return random(100, 500);
            	}

            /*case 4:   PROBLEMS WITH ROTATION ON 566 UPDATE
                if (random(1, 13) == 5) {
                    int angle = getCameraAngle() + random(-90, 90);
                    if (angle < 0) {
                        angle = 0;
                    }
                    if (angle > 359) {
                        angle = 0;
                    }

                    setCameraRotation(angle);
                return random(100, 500);
                }*/
          }
	return 50;
	}
    }