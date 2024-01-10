import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Map;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.*;
import org.rsbot.bot.Bot;
import org.rsbot.script.*;
import org.rsbot.script.wrappers.*;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.script.Calculations;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSNPC;
import org.rsbot.script.wrappers.RSPlayer;
import org.rsbot.script.wrappers.RSTile;


@ScriptManifest(authors = {"Ikin"}, category = "Hunter", name = "Imp locator v 1.5", version = 1.5, description = "<html><font color = Black><center><h2>Impling Locator</h2> Choose what to find:"
        + "<tr><td><b>Baby impling: </b></td><td><center><select name=\"imparg1\">"
        + "<option>No"
        + "<option>Yes"
        + "</select></center></td></tr>"
        + "<tr><td><b>Young impling: </b></td><td><center><select name=\"imparg2\">"
        + "<option>No"
        + "<option>Yes"
        + "</select></center></td></tr>"
        + "<tr><td><b>Gourmet impling: </b></td><td><center><select name=\"imparg3\">"
        + "<option>No"
        + "<option>Yes"
        + "</select></center></td></tr>"
        + "<tr><td><b>Earth impling: </b></td><td><center><select name=\"imparg4\">"
        + "<option>No"
        + "<option>Yes"
        + "</select></center></td></tr>"
        + "<tr><td><b>Magpie impling: </b></td><td><center><select name=\"imparg11\">"
        + "<option>No"
        + "<option>Yes"
        + "</select></center></td></tr>"
        + "<tr><td><b>Essence impling: </b></td><td><center><select name=\"imparg5\">"
        + "<option>No"
        + "<option>Yes"
        + "</select></center></td></tr>"
        + "<tr><td><b>Eclectic impling: </b></td><td><center><select name=\"imparg6\">"
        + "<option>No"
        + "<option>Yes"
        + "</select></center></td></tr>"
        + "<tr><td><b>Nature impling: </b></td><td><center><select name=\"imparg7\">"
        + "<option>No"
        + "<option>Yes"
        + "</select></center></td></tr>"
        + "<tr><td><b>Ninja impling: </b></td><td><center><select name=\"imparg12\">"
        + "<option>No"
        + "<option>Yes"
        + "</select></center></td></tr>"
        + "<tr><td><b>Spirit impling: </b></td><td><center><select name=\"imparg8\">"
        + "<option>No"
        + "<option>Yes"
        + "</select></center></td></tr>"
        + "<tr><td><b>Zombie impling: </b></td><td><center><select name=\"imparg9\">"
        + "<option>No"
        + "<option>Yes"
        + "</select></center></td></tr>"
        + "<tr><td><b>Dragon impling: </b></td><td><center><select name=\"imparg13\">"
        + "<option>No"
        + "<option>Yes"
        + "</select></center></td></tr>"
        + "<tr><td><b>Kingly impling: </b></td><td><center><select name=\"imparg10\">"
        + "<option>No"
        + "<option>Yes"
        + "</select></center></td></tr>")
public class impLocator extends Script implements PaintListener {

    private boolean imp1, imp2, imp3, imp4, imp5, imp6, imp7, imp8, imp9, imp10, imp11, imp12, imp13 = false;

    public boolean onStart(Map<String, String> args) {
        if (args.get("imparg1").equals("Yes")) {
            imp1 = true;
        }
        if (args.get("imparg2").equals("Yes")) {
            imp2 = true;
        }
        if (args.get("imparg3").equals("Yes")) {
            imp3 = true;
        }
        if (args.get("imparg4").equals("Yes")) {
            imp4 = true;
        }
        if (args.get("imparg5").equals("Yes")) {
            imp5 = true;
        }
        if (args.get("imparg6").equals("Yes")) {
            imp6 = true;
        }
        if (args.get("imparg7").equals("Yes")) {
            imp7 = true;
        }
        if (args.get("imparg8").equals("Yes")) {
            imp8 = true;
        }
        if (args.get("imparg9").equals("Yes")) {
            imp9 = true;
        }
        if (args.get("imparg10").equals("Yes")) {
            imp10 = true;
        }
        if (args.get("imparg11").equals("Yes")) {
            imp11 = true;
        }
        if (args.get("imparg12").equals("Yes")) {
            imp12 = true;
        }
        if (args.get("imparg13").equals("Yes")) {
            imp13 = true;
        }
        return true;
    }

    public void onFinish() {
        Bot.getEventManager().removeListener(PaintListener.class, this);
    }


    public int loop() {
        //nothing here lol :(
        return 700;

    }

    private void overlayTile(final Graphics g, final RSTile t, final Color c, final String NameImp) {
        final Point p = Calculations.tileToScreen(t);
        final Point pn = Calculations.tileToScreen(t.getX(), t.getY(), 0, 0, 0);
        final Point px = Calculations.tileToScreen(t.getX() + 1, t.getY(), 0,
                0, 0);
        final Point py = Calculations.tileToScreen(t.getX(), t.getY() + 1, 0,
                0, 0);
        final Point pxy = Calculations.tileToScreen(t.getX() + 1, t.getY() + 1,
                0, 0, 0);
        final Point[] points = {p, pn, px, py, pxy};
        for (final Point point : points) {
            if (!pointOnScreen(point)) {
                return;
            }
        }
        g.setColor(c);
        g.fillPolygon(new int[]{py.x, pxy.x, px.x, pn.x}, new int[]{py.y,
                pxy.y, px.y, pn.y}, 4);
        g.drawPolygon(new int[]{py.x, pxy.x, px.x, pn.x}, new int[]{py.y,
                pxy.y, px.y, pn.y}, 4);

        g.setColor(new Color(0, 0, 0));
        g.drawString(NameImp, px.x - 5, py.y);
        g.setColor(new Color(255, 255, 255));
        g.drawString(NameImp, px.x - 5 - 1, py.y - 1);

    }

    public void onRepaint(Graphics g) {

        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        if (isLoggedIn()) {
            g.fillOval(tileToMinimap(getMyPlayer().getLocation()).x - 3, tileToMinimap(getMyPlayer().getLocation()).y - 3, 3, 3);

            for (RSNPC npc : getNPCArray(false)) {

                if (imp1) {
                    if (npc.getID() == 6055) { //Baby
                        overlayTile(g, npc.getLocation(), new Color(217, 167, 215, 160), "Baby Impling");
                        g.setColor(new Color(217, 167, 215));
                        g.fillOval(tileToMinimap(npc.getLocation()).x - 3, tileToMinimap(npc.getLocation()).y - 3, 3, 3);
                    }
                }
                if (imp2) {
                    if (npc.getID() == 6056) { //Young
                        overlayTile(g, npc.getLocation(), new Color(245, 250, 24, 160), "Young Impling");
                        g.setColor(new Color(245, 250, 24));
                        g.fillOval(tileToMinimap(npc.getLocation()).x - 3, tileToMinimap(npc.getLocation()).y - 3, 3, 3);
                    }
                }
                if (imp3) {
                    if (npc.getID() == 6057) { //Gourmet
                        overlayTile(g, npc.getLocation(), new Color(216, 187, 165, 160), "Gourmet Impling");
                        g.setColor(new Color(216, 187, 165));
                        g.fillOval(tileToMinimap(npc.getLocation()).x - 3, tileToMinimap(npc.getLocation()).y - 3, 3, 3);
                    }
                }
                if (imp4) {
                    if (npc.getID() == 6058) { //Earth impling
                        overlayTile(g, npc.getLocation(), new Color(30, 255, 30, 160), "Earth impling");
                        g.setColor(new Color(30, 255, 30));
                        g.fillOval(tileToMinimap(npc.getLocation()).x - 3, tileToMinimap(npc.getLocation()).y - 3, 3, 3);
                    }
                }
                if (imp5) {
                    if (npc.getID() == 6059) { //Essence impling
                        overlayTile(g, npc.getLocation(), new Color(25, 133, 128, 160), "Essence impling");
                        g.setColor(new Color(25, 133, 128));
                        g.fillOval(tileToMinimap(npc.getLocation()).x - 3, tileToMinimap(npc.getLocation()).y - 3, 3, 3);
                    }
                }
                if (imp6) {
                    if (npc.getID() == 6060) { //Eclectic impling
                        overlayTile(g, npc.getLocation(), new Color(52, 29, 74, 160), "Eclectic impling");
                        g.setColor(new Color(52, 29, 74));
                        g.fillOval(tileToMinimap(npc.getLocation()).x - 3, tileToMinimap(npc.getLocation()).y - 3, 3, 3);
                    }
                }

                if (imp7) {
                    if (npc.getID() == 6061) { //Nature Impling
                        overlayTile(g, npc.getLocation(), new Color(216, 187, 165, 160), "Nature Impling");
                        g.setColor(new Color(216, 187, 165));
                        g.fillOval(tileToMinimap(npc.getLocation()).x - 3, tileToMinimap(npc.getLocation()).y - 3, 3, 3);
                    }
                }
                if (imp8) {
                    if (npc.getID() == 7904) { //Spirit
                        overlayTile(g, npc.getLocation(), new Color(149, 234, 255, 160), "Spirit impling");
                        g.setColor(new Color(149, 234, 255));
                        g.fillOval(tileToMinimap(npc.getLocation()).x - 3, tileToMinimap(npc.getLocation()).y - 3, 3, 3);
                    }
                }
                if (imp9) {
                    if (npc.getID() == 7905) { //Zombie impling
                        overlayTile(g, npc.getLocation(), new Color(170, 170, 219, 160), "Zombie impling");
                        g.setColor(new Color(170, 170, 219));
                        g.fillOval(tileToMinimap(npc.getLocation()).x - 3, tileToMinimap(npc.getLocation()).y - 3, 3, 3);
                    }
                }
                if (imp10) {
                    if (npc.getID() == 7906) { //King impling
                        overlayTile(g, npc.getLocation(), new Color(255, 0, 0, 160), "King impling");
                        g.setColor(new Color(255, 0, 0));
                        g.fillOval(tileToMinimap(npc.getLocation()).x - 3, tileToMinimap(npc.getLocation()).y - 3, 3, 3);
                    }
                }
                if (imp11) {
                    if (npc.getID() == 6062) { //Magpie
                        overlayTile(g, npc.getLocation(), new Color(255, 0, 0, 160), "Magpie impling");
                        g.setColor(new Color(255, 0, 0));
                        g.fillOval(tileToMinimap(npc.getLocation()).x - 3, tileToMinimap(npc.getLocation()).y - 3, 3, 3);
                    }
                }
                if (imp12) {
                    if (npc.getID() == 6063) { //Ninja
                        overlayTile(g, npc.getLocation(), new Color(146, 129, 216, 160), "Ninja impling");
                        g.setColor(new Color(146, 129, 216));
                        g.fillOval(tileToMinimap(npc.getLocation()).x - 3, tileToMinimap(npc.getLocation()).y - 3, 3, 3);
                    }
                }
                if (imp13) {
                    if (npc.getID() == 6064) { //Dragon
                        overlayTile(g, npc.getLocation(), new Color(7, 160, 252, 160), "Dragon impling");
                        g.setColor(new Color(7, 160, 252));
                        g.fillOval(tileToMinimap(npc.getLocation()).x - 3, tileToMinimap(npc.getLocation()).y - 3, 3, 3);
                    }
                } 
            }
        }
    }

}