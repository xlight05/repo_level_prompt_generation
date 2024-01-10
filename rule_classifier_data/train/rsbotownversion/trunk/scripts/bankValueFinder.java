import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Map;
import java.util.Vector;

import org.rsbot.bot.Bot;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.script.GEItemInfo;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;

@ScriptManifest(authors = { "SpeedWing" }, category = "Other", name = "SpeedWing's Bankvalue Finder", version = 1.0, description = "<html>"
        + "<body style='font-family: Calibri; color:white; padding: 0px; text-align: center; background-color: black;'>"
        + "<h2>"
        + "Bankvalue finder By SpeedWing"
        + "</h2>\n"
        + "Make sure to open to bank before starting the script, and keep it open.<br><br>"
        + "Please wait untill all threads are finished." + "</html>")
public class bankValueFinder extends Script implements PaintListener {
    int ITEM_ID_ARRAY[], itemsChecked = 0, itemAmount = 0;
    long midValue = 0, maxValue = 0, minValue = 0;
    int threadSleepTime = 1;
    int THREADS = 15;
    long startTime = 0, realStartTime = 0;
    boolean started = false;
    Vector<Integer> validItems = new Vector<Integer>();
    Vector<Integer> checkedItems = new Vector<Integer>();
    PriceCheckerThread[] checker = null;
    Thread[] threads;

    public void drawStringWithShadow(final String text, final int x,
            final int y, final Graphics g) {
        final Color col = g.getColor();
        g.setColor(new Color(0, 0, 0));
        g.drawString(text, x + 1, y + 1);
        g.setColor(col);
        g.drawString(text, x, y);
    }

    public String[] getFormattedTime(final long timeMillis) {
        long millis = timeMillis;
        final long days = millis / (24 * 1000 * 60 * 60);
        millis -= days * (24 * 1000 * 60 * 60);
        final long hours = millis / (1000 * 60 * 60);
        millis -= hours * 1000 * 60 * 60;
        final long minutes = millis / (1000 * 60);
        millis -= minutes * 1000 * 60;
        final long seconds = millis / 1000;
        String dayString = String.valueOf(days);
        String hoursString = String.valueOf(hours);
        String minutesString = String.valueOf(minutes);
        String secondsString = String.valueOf(seconds);
        if (hours < 10)
            hoursString = 0 + hoursString;
        if (minutes < 10)
            minutesString = 0 + minutesString;
        if (seconds < 10)
            secondsString = 0 + secondsString;
        return new String[] { dayString, hoursString, minutesString,
                secondsString };
    }

    public String getFormattedMoney(long money) {
        String cur = reverseString(String.valueOf(money));
        char[] charArray = cur.toCharArray();
        cur = "";
        for (int i = 0; i < charArray.length; i++)
            if (i == 2 || i == 5 || i == 8 || i == 11 || i == 14 || i == 17
                    || i == 20) {
                cur = cur + charArray[i];
                if (charArray.length > i + 1)
                    cur = cur + ".";
            } else
                cur = cur + charArray[i];

        return reverseString(cur);
    }

    public String reverseString(String other) {
        char[] charArray = other.toCharArray();
        String t = "";
        for (int i = 0; i < charArray.length; i++)
            t = charArray[i] + t;

        return t.toString();
    }

    public void findPrice(final int id, final int amount) {

        boolean added = false;
        while (!added) {
            for (int i = 0; i < checker.length; i++)
                if (added)
                    break;
                else if (checker[i].foundPrice && !added) {
                    checker[i].foundPrice = false;
                    checker[i].id = id;
                    checker[i].amount = amount;
                    added = true;
                }

            wait(1);
        }

    }

    public String find(boolean bla) {
        return bla ? "Done" : "Search";
    }

    public int loop() {

        ITEM_ID_ARRAY = bank.getItemArray();

        for (int i = 0; i < ITEM_ID_ARRAY.length; ++i)
            if (ITEM_ID_ARRAY[i] != -1)
                validItems.add(ITEM_ID_ARRAY[i]);

        itemAmount = validItems.size();

        if (itemAmount <= THREADS) {
            THREADS = itemAmount;
            if (validItems.contains(995))
                THREADS--;
        }

        checker = new PriceCheckerThread[THREADS];

        threads = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            checker[i] = new PriceCheckerThread();
            threads[i] = new Thread(checker[i]);
        }

        for (int i = 0; i < threads.length; i++)
            if (!threads[i].isAlive())
                threads[i].start();

        started = true;

        realStartTime = System.currentTimeMillis();

        for (int i = 0; i < validItems.size(); i++) {
            if (validItems.get(i) == 995) {
                minValue += bank.getCount(validItems.get(i));
                midValue += bank.getCount(validItems.get(i));
                maxValue += bank.getCount(validItems.get(i));
                checkedItems.addElement(995);
                itemsChecked++;
            } else
                findPrice(validItems.get(i), bank.getCount(validItems.get(i)));

            wait(10);
        }

        while (checkedItems.size() < validItems.size())
            wait(10);

        log.warning("Items checked "
                + itemAmount
                + ", Finished in: "
                + getFormattedMoney((System.currentTimeMillis() - realStartTime))
                + " Seconds.");

        log.warning("MIN: " + getFormattedMoney(Long.valueOf(minValue))
                + " GP - " + "MID: "
                + getFormattedMoney(Long.valueOf(midValue)) + " GP - "
                + "MAX: " + getFormattedMoney(Long.valueOf(maxValue)) + " GP.");
        Bot.getScriptHandler().stopScript();
        return -1;
    }

    public void onFinish() {
        for (int i = 0; i < checker.length; i++)
            checker[i].stopPrice = true;
    }

    public void onRepaint(Graphics g) {
        if (isLoggedIn() && started) {
            if (startTime == 0)
                startTime = System.currentTimeMillis();
            long realmillis = System.currentTimeMillis() - startTime;
            int xl = 187, yl = 82, procentbarh = 20, x = 548, y = 210, extra = 5;
            g.setColor(new Color(30, 255, 0, 80));
            g
                    .fillRoundRect(
                            x
                                    + Math
                                            .round((((100 * itemsChecked) / itemAmount) * xl) / 100),
                            y,
                            xl
                                    - Math
                                            .round((((100 * itemsChecked) / itemAmount) * xl) / 100),
                            procentbarh, extra, extra);
            g.setColor(new Color(30, 255, 0, 147));
            g.fillRoundRect(x, y, Math
                    .round((((100 * itemsChecked) / itemAmount) * xl) / 100),
                    procentbarh, extra, extra);
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_OFF);
            g.setColor(new Color(255, 255, 255));
            g.setFont(new Font("Calibri", Font.PLAIN, 13));
            drawStringWithShadow(Math.round((100 * itemsChecked) / itemAmount)
                    + " %", x + xl / 2 - 7, y + 14, g);
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawRoundRect(x, y, xl, procentbarh, extra, extra);
            y += procentbarh + 4;
            g.setColor(new Color(0, 0, 0, 147));
            g.fillRoundRect(x, y, xl, yl, extra, extra);
            g.setColor(new Color(255, 255, 255));
            g.drawRoundRect(x, y, xl, yl, extra, extra);
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_OFF);
            drawStringWithShadow("SpeedWing's Bankvalue Finder", x + 10,
                    y += 15, g);
            drawStringWithShadow("Runtime: " + getFormattedTime(realmillis)[2]
                    + ":" + getFormattedTime(realmillis)[3], x + 10, y += 15, g);
            drawStringWithShadow("Items Done: " + itemsChecked + "/"
                    + itemAmount, x + 10, y += 15, g);
            drawStringWithShadow("Bank Value: " + getFormattedMoney(midValue),
                    x + 10, y += 15, g);
            int inUse = 0;
            for (int i = 0; i < checker.length; i++)
                if (!checker[i].foundPrice)
                    inUse++;
            drawStringWithShadow("Threads busy: " + inUse + "/" + THREADS,
                    x + 10, y += 15, g);
        }

    }

    public boolean onStart(Map<String, String> paramMap) {
        if (!bank.isOpen()) {
            log.warning("Please open your bank before you start the script");
            return false;
        } else
            return true;

    }

    public class PriceCheckerThread implements Runnable {
        public int id = -1;
        public int amount = -1;
        public boolean stopPrice = false;
        public boolean foundPrice = true;

        public void run() {
            while (!stopPrice) {
                if (id != -1) {
                    GEItemInfo cur = grandExchange.loadItemInfo(id);
                    int minPrice = cur.getMinPrice();
                    int midPrice = cur.getMarketPrice();
                    int maxPrice = cur.getMaxPrice();
                    if (minPrice > 0) {
                        minValue += minPrice * amount;
                        midValue += midPrice * amount;
                        maxValue += maxPrice * amount;
                    }
                    itemsChecked++;
                    checkedItems.addElement(id);
                    id = -1;
                    foundPrice = true;
                }
                try {
                    Thread.sleep(0, threadSleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}  